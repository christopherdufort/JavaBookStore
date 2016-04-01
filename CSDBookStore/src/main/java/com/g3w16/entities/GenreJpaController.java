/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import com.g3w16.entities.exceptions.NonexistentEntityException;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author Jonas Faure
 */
@Named
@RequestScoped
public class GenreJpaController implements Serializable {

    public GenreJpaController() {
    }

    @Resource
    private UserTransaction utx;
    @PersistenceContext
    private EntityManager em;

    public void create(Genre genre) throws RollbackFailureException, Exception {
        if (genre.getBookList() == null) {
            genre.setBookList(new ArrayList<>());
        }
        try {
            utx.begin();
            List<Book> attachedBookList = new ArrayList<>();
            for (Book bookListBookToAttach : genre.getBookList()) {
                bookListBookToAttach = em.getReference(bookListBookToAttach.getClass(), bookListBookToAttach.getBookId());
                attachedBookList.add(bookListBookToAttach);
            }
            genre.setBookList(attachedBookList);
            em.persist(genre);
            for (Book bookListBook : genre.getBookList()) {
                bookListBook.getGenreList().add(genre);
                bookListBook = em.merge(bookListBook);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }

    public void edit(Genre genre) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Genre persistentGenre = em.find(Genre.class, genre.getGenreId());
            List<Book> bookListOld = persistentGenre.getBookList();
            List<Book> bookListNew = genre.getBookList();
            List<Book> attachedBookListNew = new ArrayList<Book>();
            for (Book bookListNewBookToAttach : bookListNew) {
                bookListNewBookToAttach = em.getReference(bookListNewBookToAttach.getClass(), bookListNewBookToAttach.getBookId());
                attachedBookListNew.add(bookListNewBookToAttach);
            }
            bookListNew = attachedBookListNew;
            genre.setBookList(bookListNew);
            genre = em.merge(genre);
            for (Book bookListOldBook : bookListOld) {
                if (!bookListNew.contains(bookListOldBook)) {
                    bookListOldBook.getGenreList().remove(genre);
                    bookListOldBook = em.merge(bookListOldBook);
                }
            }
            for (Book bookListNewBook : bookListNew) {
                if (!bookListOld.contains(bookListNewBook)) {
                    bookListNewBook.getGenreList().add(genre);
                    bookListNewBook = em.merge(bookListNewBook);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = genre.getGenreId();
                if (findGenre(id) == null) {
                    throw new NonexistentEntityException("The genre with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Genre genre;
            try {
                genre = em.getReference(Genre.class, id);
                genre.getGenreId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The genre with id " + id + " no longer exists.", enfe);
            }
            List<Book> bookList = genre.getBookList();
            for (Book bookListBook : bookList) {
                bookListBook.getGenreList().remove(genre);
                bookListBook = em.merge(bookListBook);
            }
            em.remove(genre);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        }
    }

    public List<Genre> findGenreEntities() {
        return findGenreEntities(true, false, -1, -1);
    }
    
    public List<Genre> findGenreEntitiesAsClient() {
        return findGenreEntities(true, true, -1, -1);
    }

    public List<Genre> findGenreEntities(int maxResults, int firstResult) {
        return findGenreEntities(false, false, maxResults, firstResult);
    }

    public List<Genre> findGenreEntitiesAsClient(int maxResults, int firstResult) {
        return findGenreEntities(false, true, maxResults, firstResult);
    }
    
    private List<Genre> findGenreEntities(boolean all, boolean asClient, int maxResults, int firstResult) {
        Query q;
        if(asClient){
            q = em.createNamedQuery("Genre.findAllAvailable");
        }else{
            q = em.createNamedQuery("Genre.findAll");
        }
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public List<Genre> findGenreEntitiesLike(String genreName){
        return findGenreEntitiesLike(genreName, true, false, -1, -1);
    }
    
    public List<Genre> findGenreEntitiesLikeAsClient(String genreName){
        return findGenreEntitiesLike(genreName, true, true, -1, -1);
    }
    
    public List<Genre> findGenreEntitiesLike(String genreName, int maxResults, int firstResult){
        return findGenreEntitiesLike(genreName, false, false, maxResults, firstResult);
    }
    
    public List<Genre> findGenreEntitiesLikeAsClient(String genreName, int maxResults, int firstResult){
        return findGenreEntitiesLike(genreName, false, true, maxResults, firstResult);
    }
    
    private List<Genre> findGenreEntitiesLike(String genreName, boolean all, boolean asClient, int maxResults, int firstResult){
        Query q;
        if(asClient){
            q = em.createNamedQuery("Genre.findByGenreNameAvailable");
        }else{
            q = em.createNamedQuery("Genre.findByGenreName");
        }
        if(!all){
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        q.setParameter("genreName", "%"+genreName+"%");
        return q.getResultList();
    }
    
    public List<Genre> findGenreEntitiesByBook(Book book){
        return findGenreEntitiesByBook(book, true, false, -1, -1);
    }
    
    public List<Genre> findGenreEntitiesByBookAsClient(Book book){
        return findGenreEntitiesByBook(book, true, true, -1, -1);
    }
    
    public List<Genre> findGenreEntitiesByBook(Book book, int maxResults, int firstResult){
        return findGenreEntitiesByBook(book, false, false, maxResults, firstResult);
    }
    
    public List<Genre> findGenreEntitiesByBookAsClient(Book book, int maxResults, int firstResult){
        return findGenreEntitiesByBook(book, false, true, maxResults, firstResult);
    }
    
    private List<Genre> findGenreEntitiesByBook(Book book, boolean all, boolean asClient, int maxResults, int firstResult){
        Query q;
        if(asClient){
            q = em.createNamedQuery("Genre.findByBookIdAvailable");
        }else{
            q = em.createNamedQuery("Genre.findByBookId");
        }
        if(!all){
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        q.setParameter("bookId", book.getBookId());
        return q.getResultList();
    }
    
    public List<Genre> findGenreEntitiesByFormat(Format format){
        return findGenreEntitiesByFormat(format, true, false, -1, -1);
    }
    
    public List<Genre> findGenreEntitiesByFormatAsClient(Format format){
        return findGenreEntitiesByFormat(format, true, true, -1, -1);
    }
    
    public List<Genre> findGenreEntitiesByFormat(Format format, int maxResults, int firstResult){
        return findGenreEntitiesByFormat(format, false, false, maxResults, firstResult);
    }
    
    public List<Genre> findGenreEntitiesByFormatAsClient(Format format, int maxResults, int firstResult){
        return findGenreEntitiesByFormat(format, false, true, maxResults, firstResult);
    }
    
    private List<Genre> findGenreEntitiesByFormat(Format format, boolean all, boolean asClient, int maxResults, int firstResult){
        Query q;
        if(asClient){
            q = em.createNamedQuery("Genre.FindByFormatIdAvailable");
        }else{
            q = em.createNamedQuery("Genre.FindByFormatId");
        }
        if(!all){
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        q.setParameter("formatId", format.getFormatId());
        return q.getResultList();
    }
    
    public List<Genre> findGenreEntitiesByAuthor(Author author){
        return findGenreEntitiesByAuthor(author, true, false, -1, -1);
    }
    
    public List<Genre> findGenreEntitiesByAuthorAsClient(Author author){
        return findGenreEntitiesByAuthor(author, true, true, -1, -1);
    }
    
    public List<Genre> findGenreEntitiesByAuthor(Author author, int maxResults, int firstResult){
        return findGenreEntitiesByAuthor(author, false, false, maxResults, firstResult);
    }
    
    public List<Genre> findGenreEntitiesByAuthorAsClient(Author author, int maxResults, int firstResult){
        return findGenreEntitiesByAuthor(author, false, true, maxResults, firstResult);
    }
    
    private List<Genre> findGenreEntitiesByAuthor(Author author, boolean all, boolean asClient, int maxResults, int firstResult){
        Query q;
        if(asClient){
            q = em.createNamedQuery("Genre.FindByAuthorIdAvailable");
        }else{
            q = em.createNamedQuery("Genre.FindByAuthorId");
        }
        if(!all){
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        q.setParameter("authorId", author.getAuthorId());
        return q.getResultList();
    }
    
    public Genre findGenre(Integer id) {
        return em.find(Genre.class, id);
    }
    
    public Genre findByGenreName(String name){
         try {
            Query q = em.createNamedQuery("Genre.findByGenreName", Genre.class);
            q.setParameter("genreName", name);
            return (Genre) q.getSingleResult();
        } catch (NoResultException e) {
            return new Genre();
        }
    }

    public int getGenreCount() {
        Query q = em.createQuery("select count(o) from Genre as o");
        return ((Long) q.getSingleResult()).intValue();
    }

}
