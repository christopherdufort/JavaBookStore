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
 */
@Named
@RequestScoped
public class AuthorJpaController implements Serializable {

    public AuthorJpaController() {

    }
    @Resource
    private UserTransaction utx;
    @PersistenceContext
    private EntityManager em;

    public void create(Author author) throws RollbackFailureException, Exception {
        if (author.getBookList() == null) {
            author.setBookList(new ArrayList<Book>());
        }
        try {
            utx.begin();
            List<Book> attachedBookList = new ArrayList<Book>();
            for (Book bookListBookToAttach : author.getBookList()) {
                bookListBookToAttach = em.getReference(bookListBookToAttach.getClass(), bookListBookToAttach.getBookId());
                attachedBookList.add(bookListBookToAttach);
            }
            author.setBookList(attachedBookList);
            em.persist(author);
            for (Book bookListBook : author.getBookList()) {
                bookListBook.getAuthorList().add(author);
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

    public void edit(Author author) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Author persistentAuthor = em.find(Author.class, author.getAuthorId());
            List<Book> bookListOld = persistentAuthor.getBookList();
            List<Book> bookListNew = author.getBookList();
            List<Book> attachedBookListNew = new ArrayList<>();
            for (Book bookListNewBookToAttach : bookListNew) {
                bookListNewBookToAttach = em.getReference(bookListNewBookToAttach.getClass(), bookListNewBookToAttach.getBookId());
                attachedBookListNew.add(bookListNewBookToAttach);
            }
            bookListNew = attachedBookListNew;
            author.setBookList(bookListNew);
            author = em.merge(author);
            for (Book bookListOldBook : bookListOld) {
                if (!bookListNew.contains(bookListOldBook)) {
                    bookListOldBook.getAuthorList().remove(author);
                    bookListOldBook = em.merge(bookListOldBook);
                }
            }
            for (Book bookListNewBook : bookListNew) {
                if (!bookListOld.contains(bookListNewBook)) {
                    bookListNewBook.getAuthorList().add(author);
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
                Integer id = author.getAuthorId();
                if (findAuthor(id) == null) {
                    throw new NonexistentEntityException("The author with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Author author;
            try {
                author = em.getReference(Author.class, id);
                author.getAuthorId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The author with id " + id + " no longer exists.", enfe);
            }
            List<Book> bookList = author.getBookList();
            for (Book bookListBook : bookList) {
                bookListBook.getAuthorList().remove(author);
                bookListBook = em.merge(bookListBook);
            }
            em.remove(author);
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

    public List<Author> findAuthorEntities() {
        return findAuthorEntities(true, -1, -1);
    }

    public List<Author> findAuthorEntities(int maxResults, int firstResult) {
        return findAuthorEntities(false, maxResults, firstResult);
    }
    
    public List<Author> findAuthorEntitiesLike(String authorName){
        return findAuthorEntitiesLike(authorName, true, -1, -1);
    }
    
    public List<Author> findAuthorEntitiesLike(String authorName, int maxResults, int firstResult){
        return findAuthorEntitiesLike(authorName, false, maxResults, firstResult);
    }
    
    private List<Author> findAuthorEntitiesLike(String authorName, boolean all, int maxResults, int firstResult){
        Query q = em.createNamedQuery("Author.findByAuthorName");
        if (!all){
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        q.setParameter("authorName", "%"+authorName+"%");
        return q.getResultList();
    }

    private List<Author> findAuthorEntities(boolean all, int maxResults, int firstResult) {
        Query q = em.createQuery("select object(o) from Author as o");
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }
    
    public List<Author> findAuthorEntitiesByBook(Book book){
        return findAuthorEntitiesByBook(book, true, -1, -1);
    }
    
    public List<Author> findAuthorEntitiesByBook(Book book, int maxResults, int firstResult){
        return findAuthorEntitiesByBook(book, false, maxResults, firstResult);
    }
    
    private List<Author> findAuthorEntitiesByBook(Book book, boolean all, int maxResults, int firstResult){
        Query q = em.createNamedQuery("Author.findByBookId");
        if (!all){
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        q.setParameter("bookId", book.getBookId());
        return q.getResultList();
    }
    
    public List<Author> findAuthorEntitiesByGenre(Genre genre){
        return findAuthorEntitiesByGenre(genre, true, -1, -1);
    }
    
    public List<Author> findAuthorEntitiesByGenre(Genre genre, int maxResults, int firstResult){
        return findAuthorEntitiesByGenre(genre, false, maxResults, firstResult);
    }
    
    private List<Author> findAuthorEntitiesByGenre(Genre genre, boolean all, int maxResults, int firstResult){
        Query q = em.createNamedQuery("Author.findByGenreId");
        if(!all){
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        q.setParameter("genreId", genre.getGenreId());
        return q.getResultList();
    }
    
    public List<Author> findAuthorEntitiesByFormat(Format format){
        return findAuthorEntitiesByFormat(format, true, -1, -1);
    }
    
    public List<Author> findAuthorEntitiesByFormat(Format format, int maxResults, int firstResult){
        return findAuthorEntitiesByFormat(format, false, maxResults, firstResult);
    }
    
    private List<Author> findAuthorEntitiesByFormat(Format format, boolean all, int maxResults, int firstResult){
        Query q = em.createNamedQuery("Author.findByFormatId");
        if (!all){
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        q.setParameter("formatId", format.getFormatId());
        return q.getResultList();
    }

    public Author findAuthor(Integer id) {
        return em.find(Author.class, id);
    }

    public int getAuthorCount() {
        Query q = em.createQuery("select count(o) from Author as o");
        return ((Long) q.getSingleResult()).intValue();
    }

}
