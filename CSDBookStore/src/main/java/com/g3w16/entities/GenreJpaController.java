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
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author 1040570
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
        return findGenreEntities(true, -1, -1);
    }

    public List<Genre> findGenreEntities(int maxResults, int firstResult) {
        return findGenreEntities(false, maxResults, firstResult);
    }

    private List<Genre> findGenreEntities(boolean all, int maxResults, int firstResult) {
        Query q = em.createQuery("select object(o) from Genre as o");
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public List<Genre> findGenreEntitiesLike(String genreName){
        return findGenreEntitiesLike(genreName, true, -1, -1);
    }
    
    public List<Genre> findGenreEntitiesLike(String genreName, int maxResults, int firstResult){
        return findGenreEntitiesLike(genreName, false, maxResults, firstResult);
    }
    
    private List<Genre> findGenreEntitiesLike(String genreName, boolean all, int maxResults, int firstResult){
        Query q = em.createNamedQuery("Genre.findByGenreName");
        if(!all){
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        q.setParameter("genreName", "%"+genreName+"%");
        return q.getResultList();
    }
    
    public List<Genre> findGenreEntitiesByBook(Book book){
        return findGenreEntitiesByBook(book, true, -1, -1);
    }
    
    public List<Genre> findGenreEntitiesByBook(Book book, int maxResults, int firstResult){
        return findGenreEntitiesByBook(book, false, maxResults, firstResult);
    }
    
    private List<Genre> findGenreEntitiesByBook(Book book, boolean all, int maxResults, int firstResult){
        Query q = em.createNamedQuery("Genre.findByBookId");
        if(!all){
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        q.setParameter("bookId", book.getBookId());
        return q.getResultList();
    }
    
    public List<Genre> findGenreEntitiesByFormat(Format format){
        return findGenreEntitiesByFormat(format, true, -1, -1);
    }
    
    public List<Genre> findGenreEntitiesByFormat(Format format, int maxResults, int firstResult){
        return findGenreEntitiesByFormat(format, false, maxResults, firstResult);
    }
    
    private List<Genre> findGenreEntitiesByFormat(Format format, boolean all, int maxResults, int firstResult){
        Query q = em.createNamedQuery("Genre.FindByFormatId");
        if(!all){
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        q.setParameter("formatId", format.getFormatId());
        return q.getResultList();
    }
    
    public List<Genre> findGenreEntitiesByAuthor(Author author){
        return findGenreEntitiesByAuthor(author, true, -1, -1);
    }
    
    public List<Genre> findGenreEntitiesByAuthor(Author author, int maxResults, int firstResult){
        return findGenreEntitiesByAuthor(author, false, maxResults, firstResult);
    }
    
    private List<Genre> findGenreEntitiesByAuthor(Author author, boolean all, int maxResults, int firstResult){
        Query q = em.createNamedQuery("Genre.FindByAuthorId");
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

    public int getGenreCount() {
        Query q = em.createQuery("select count(o) from Genre as o");
        return ((Long) q.getSingleResult()).intValue();
    }

}
