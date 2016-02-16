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
public class BookJpaController implements Serializable {

    public BookJpaController() {
    }
    @Resource
    private UserTransaction utx;
    @PersistenceContext
    private EntityManager em;

    public void create(Book book) throws RollbackFailureException, Exception {
        if (book.getAuthorList() == null) {
            book.setAuthorList(new ArrayList<Author>());
        }
        if (book.getFormatList() == null) {
            book.setFormatList(new ArrayList<Format>());
        }
        if (book.getGenreList() == null) {
            book.setGenreList(new ArrayList<Genre>());
        }
        if (book.getReviewList() == null) {
            book.setReviewList(new ArrayList<Review>());
        }
        try {
            utx.begin();
            List<Author> attachedAuthorList = new ArrayList<>();
            for (Author authorListAuthorToAttach : book.getAuthorList()) {
                authorListAuthorToAttach = em.getReference(authorListAuthorToAttach.getClass(), authorListAuthorToAttach.getAuthorId());
                attachedAuthorList.add(authorListAuthorToAttach);
            }
            book.setAuthorList(attachedAuthorList);
            List<Format> attachedFormatList = new ArrayList<>();
            for (Format formatListFormatToAttach : book.getFormatList()) {
                formatListFormatToAttach = em.getReference(formatListFormatToAttach.getClass(), formatListFormatToAttach.getFormatId());
                attachedFormatList.add(formatListFormatToAttach);
            }
            book.setFormatList(attachedFormatList);
            List<Genre> attachedGenreList = new ArrayList<>();
            for (Genre genreListGenreToAttach : book.getGenreList()) {
                genreListGenreToAttach = em.getReference(genreListGenreToAttach.getClass(), genreListGenreToAttach.getGenreId());
                attachedGenreList.add(genreListGenreToAttach);
            }
            book.setGenreList(attachedGenreList);
            List<Review> attachedReviewList = new ArrayList<>();
            for (Review reviewListReviewToAttach : book.getReviewList()) {
                reviewListReviewToAttach = em.getReference(reviewListReviewToAttach.getClass(), reviewListReviewToAttach.getReviewId());
                attachedReviewList.add(reviewListReviewToAttach);
            }
            book.setReviewList(attachedReviewList);
            em.persist(book);
            for (Author authorListAuthor : book.getAuthorList()) {
                authorListAuthor.getBookList().add(book);
                authorListAuthor = em.merge(authorListAuthor);
            }
            for (Format formatListFormat : book.getFormatList()) {
                formatListFormat.getBookList().add(book);
                formatListFormat = em.merge(formatListFormat);
            }
            for (Genre genreListGenre : book.getGenreList()) {
                genreListGenre.getBookList().add(book);
                genreListGenre = em.merge(genreListGenre);
            }
            for (Review reviewListReview : book.getReviewList()) {
                Book oldIsbnOfReviewListReview = reviewListReview.getIsbn();
                reviewListReview.setIsbn(book);
                reviewListReview = em.merge(reviewListReview);
                if (oldIsbnOfReviewListReview != null) {
                    oldIsbnOfReviewListReview.getReviewList().remove(reviewListReview);
                    oldIsbnOfReviewListReview = em.merge(oldIsbnOfReviewListReview);
                }
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

    public void edit(Book book) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Book persistentBook = em.find(Book.class, book.getBookId());
            List<Author> authorListOld = persistentBook.getAuthorList();
            List<Author> authorListNew = book.getAuthorList();
            List<Format> formatListOld = persistentBook.getFormatList();
            List<Format> formatListNew = book.getFormatList();
            List<Genre> genreListOld = persistentBook.getGenreList();
            List<Genre> genreListNew = book.getGenreList();
            List<Review> reviewListOld = persistentBook.getReviewList();
            List<Review> reviewListNew = book.getReviewList();
            List<Author> attachedAuthorListNew = new ArrayList<>();
            for (Author authorListNewAuthorToAttach : authorListNew) {
                authorListNewAuthorToAttach = em.getReference(authorListNewAuthorToAttach.getClass(), authorListNewAuthorToAttach.getAuthorId());
                attachedAuthorListNew.add(authorListNewAuthorToAttach);
            }
            authorListNew = attachedAuthorListNew;
            book.setAuthorList(authorListNew);
            List<Format> attachedFormatListNew = new ArrayList<>();
            for (Format formatListNewFormatToAttach : formatListNew) {
                formatListNewFormatToAttach = em.getReference(formatListNewFormatToAttach.getClass(), formatListNewFormatToAttach.getFormatId());
                attachedFormatListNew.add(formatListNewFormatToAttach);
            }
            formatListNew = attachedFormatListNew;
            book.setFormatList(formatListNew);
            List<Genre> attachedGenreListNew = new ArrayList<>();
            for (Genre genreListNewGenreToAttach : genreListNew) {
                genreListNewGenreToAttach = em.getReference(genreListNewGenreToAttach.getClass(), genreListNewGenreToAttach.getGenreId());
                attachedGenreListNew.add(genreListNewGenreToAttach);
            }
            genreListNew = attachedGenreListNew;
            book.setGenreList(genreListNew);
            List<Review> attachedReviewListNew = new ArrayList<>();
            for (Review reviewListNewReviewToAttach : reviewListNew) {
                reviewListNewReviewToAttach = em.getReference(reviewListNewReviewToAttach.getClass(), reviewListNewReviewToAttach.getReviewId());
                attachedReviewListNew.add(reviewListNewReviewToAttach);
            }
            reviewListNew = attachedReviewListNew;
            book.setReviewList(reviewListNew);
            book = em.merge(book);
            for (Author authorListOldAuthor : authorListOld) {
                if (!authorListNew.contains(authorListOldAuthor)) {
                    authorListOldAuthor.getBookList().remove(book);
                    authorListOldAuthor = em.merge(authorListOldAuthor);
                }
            }
            for (Author authorListNewAuthor : authorListNew) {
                if (!authorListOld.contains(authorListNewAuthor)) {
                    authorListNewAuthor.getBookList().add(book);
                    authorListNewAuthor = em.merge(authorListNewAuthor);
                }
            }
            for (Format formatListOldFormat : formatListOld) {
                if (!formatListNew.contains(formatListOldFormat)) {
                    formatListOldFormat.getBookList().remove(book);
                    formatListOldFormat = em.merge(formatListOldFormat);
                }
            }
            for (Format formatListNewFormat : formatListNew) {
                if (!formatListOld.contains(formatListNewFormat)) {
                    formatListNewFormat.getBookList().add(book);
                    formatListNewFormat = em.merge(formatListNewFormat);
                }
            }
            for (Genre genreListOldGenre : genreListOld) {
                if (!genreListNew.contains(genreListOldGenre)) {
                    genreListOldGenre.getBookList().remove(book);
                    genreListOldGenre = em.merge(genreListOldGenre);
                }
            }
            for (Genre genreListNewGenre : genreListNew) {
                if (!genreListOld.contains(genreListNewGenre)) {
                    genreListNewGenre.getBookList().add(book);
                    genreListNewGenre = em.merge(genreListNewGenre);
                }
            }
            for (Review reviewListOldReview : reviewListOld) {
                if (!reviewListNew.contains(reviewListOldReview)) {
                    reviewListOldReview.setIsbn(null);
                    reviewListOldReview = em.merge(reviewListOldReview);
                }
            }
            for (Review reviewListNewReview : reviewListNew) {
                if (!reviewListOld.contains(reviewListNewReview)) {
                    Book oldIsbnOfReviewListNewReview = reviewListNewReview.getIsbn();
                    reviewListNewReview.setIsbn(book);
                    reviewListNewReview = em.merge(reviewListNewReview);
                    if (oldIsbnOfReviewListNewReview != null && !oldIsbnOfReviewListNewReview.equals(book)) {
                        oldIsbnOfReviewListNewReview.getReviewList().remove(reviewListNewReview);
                        oldIsbnOfReviewListNewReview = em.merge(oldIsbnOfReviewListNewReview);
                    }
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
                Integer id = book.getBookId();
                if (findBookEntitiesById(id) == null) {
                    throw new NonexistentEntityException("The book with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Book book;
            try {
                book = em.getReference(Book.class, id);
                book.getBookId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The book with id " + id + " no longer exists.", enfe);
            }
            List<Author> authorList = book.getAuthorList();
            for (Author authorListAuthor : authorList) {
                authorListAuthor.getBookList().remove(book);
                authorListAuthor = em.merge(authorListAuthor);
            }
            List<Format> formatList = book.getFormatList();
            for (Format formatListFormat : formatList) {
                formatListFormat.getBookList().remove(book);
                formatListFormat = em.merge(formatListFormat);
            }
            List<Genre> genreList = book.getGenreList();
            for (Genre genreListGenre : genreList) {
                genreListGenre.getBookList().remove(book);
                genreListGenre = em.merge(genreListGenre);
            }
            List<Review> reviewList = book.getReviewList();
            for (Review reviewListReview : reviewList) {
                reviewListReview.setIsbn(null);
                reviewListReview = em.merge(reviewListReview);
            }
            em.remove(book);
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

    public List<Book> findBookEntities() {
        return findBookEntities(true, -1, -1);
    }

    public List<Book> findBookEntities(int maxResults, int firstResult) {
        return findBookEntities(false, maxResults, firstResult);
    }

    private List<Book> findBookEntities(boolean all, int maxResults, int firstResult) {
        Query q = em.createNamedQuery("Book.findAll");
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }
    
    public List<Book> findBookEntitiesByISBN(String isbn){
        return findBookEntitiesByISBN(isbn, true, -1, -1);
    }
    
    public List<Book> findBookEntitiesByISBN(String isbn, int maxResults, int firstResult){
        return findBookEntitiesByISBN(isbn, false, maxResults, firstResult);
    }
    
    private List<Book> findBookEntitiesByISBN(String isbn, boolean all, int maxResults, int firstResult){
        Query q = em.createNamedQuery("Book.findByIsbn");
        if(!all){
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        q.setParameter("isbn", isbn);
        return q.getResultList();
    }
    
    public List<Book> findBookEntitiesByTitleLike(String title){
        return findBookEntitiesByTitleLike(title, true, -1, -1);
    }
    
    public List<Book> findBookEntitiesByTitleLike(String title, int maxResults, int firstResult){
        return findBookEntitiesByTitleLike(title, false, maxResults, firstResult);
    }
    
    private List<Book> findBookEntitiesByTitleLike(String title, boolean all, int maxResults, int firstResult){
        Query q = em.createNamedQuery("Book.findByTitle");
        if(!all){
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        q.setParameter("title", title);
        return q.getResultList();
    }
    
    public List<Book> findBookEntitiesByPublisherLike(String publisher){
        return findBookEntitiesByPublisherLike(publisher, true, -1, -1);
    }
    
    public List<Book> findBookEntitiesByPublisherLike(String publisher, int maxResults, int firstResult){
        return findBookEntitiesByPublisherLike(publisher, false, maxResults, firstResult);
    }
    
    private List<Book> findBookEntitiesByPublisherLike(String publisher, boolean all, int maxResults, int firstResult){
        Query q = em.createNamedQuery("Book.findByPublisher");
        if(!all){
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        q.setParameter("publisher", publisher);
        return q.getResultList();
    }
    
    public List<Book> findBookEntitiesByFormat(Format format){
        return findBookEntitiesByFormat(format, true, -1, -1);
    }
    
    public List<Book> findBookEntitiesByFormat(Format format, int maxResults, int firstResult){
        return findBookEntitiesByFormat(format, false, maxResults, firstResult);
    }
    
    private List<Book> findBookEntitiesByFormat(Format format, boolean all, int maxResults, int firstResult){
        Query q = em.createNamedQuery("Book.findByFormat");
        if(!all){
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        q.setParameter("formatId", format.getFormatId());
        return q.getResultList();
    }
    
    public List<Book> findBookEntitiesByGenre(Genre genre){
        return findBookEntitiesByGenre(genre, true, -1, -1);
    }
    
    public List<Book> findBookEntitiesByGenre(Genre genre, int maxResults, int firstResult){
        return findBookEntitiesByGenre(genre, false, maxResults, firstResult);
    }
    
    private List<Book> findBookEntitiesByGenre(Genre genre, boolean all, int maxResults, int firstResult){
        Query q = em.createNamedQuery("Book.findByGenre");
        if(!all){
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        q.setParameter("genreId", genre.getGenreId());
        return q.getResultList();
    }
    
    public List<Book> findBookEntitiesByAuthor(Author author){
        return findBookEntitiesByAuthor(author, true, -1, -1);
    }
    
    public List<Book> findBookEntitiesByAuthor(Author author, int maxResults, int firstResults){
        return findBookEntitiesByAuthor(author, false, maxResults, firstResults);
    }
    
    private List<Book> findBookEntitiesByAuthor(Author author, boolean all, int maxResults, int firstResult){
        Query q = em.createNamedQuery("Book.findByAuthor");
        if(!all){
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        q.setParameter("authorId", author.getAuthorId());
        return q.getResultList();
    }

    public Book findBookEntitiesById(Integer id) {
        return em.find(Book.class, id);
    }

    public int getBookCount() {
        Query q = em.createQuery("select count(o) from Book as o");
        return ((Long) q.getSingleResult()).intValue();
    }

}
