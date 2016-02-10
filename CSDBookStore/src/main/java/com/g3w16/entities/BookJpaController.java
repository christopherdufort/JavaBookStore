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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author 1040570
 */
public class BookJpaController implements Serializable {

    public BookJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

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
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Author> attachedAuthorList = new ArrayList<Author>();
            for (Author authorListAuthorToAttach : book.getAuthorList()) {
                authorListAuthorToAttach = em.getReference(authorListAuthorToAttach.getClass(), authorListAuthorToAttach.getAuthorId());
                attachedAuthorList.add(authorListAuthorToAttach);
            }
            book.setAuthorList(attachedAuthorList);
            List<Format> attachedFormatList = new ArrayList<Format>();
            for (Format formatListFormatToAttach : book.getFormatList()) {
                formatListFormatToAttach = em.getReference(formatListFormatToAttach.getClass(), formatListFormatToAttach.getFormatId());
                attachedFormatList.add(formatListFormatToAttach);
            }
            book.setFormatList(attachedFormatList);
            List<Genre> attachedGenreList = new ArrayList<Genre>();
            for (Genre genreListGenreToAttach : book.getGenreList()) {
                genreListGenreToAttach = em.getReference(genreListGenreToAttach.getClass(), genreListGenreToAttach.getGenreId());
                attachedGenreList.add(genreListGenreToAttach);
            }
            book.setGenreList(attachedGenreList);
            List<Review> attachedReviewList = new ArrayList<Review>();
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
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Book book) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Book persistentBook = em.find(Book.class, book.getBookId());
            List<Author> authorListOld = persistentBook.getAuthorList();
            List<Author> authorListNew = book.getAuthorList();
            List<Format> formatListOld = persistentBook.getFormatList();
            List<Format> formatListNew = book.getFormatList();
            List<Genre> genreListOld = persistentBook.getGenreList();
            List<Genre> genreListNew = book.getGenreList();
            List<Review> reviewListOld = persistentBook.getReviewList();
            List<Review> reviewListNew = book.getReviewList();
            List<Author> attachedAuthorListNew = new ArrayList<Author>();
            for (Author authorListNewAuthorToAttach : authorListNew) {
                authorListNewAuthorToAttach = em.getReference(authorListNewAuthorToAttach.getClass(), authorListNewAuthorToAttach.getAuthorId());
                attachedAuthorListNew.add(authorListNewAuthorToAttach);
            }
            authorListNew = attachedAuthorListNew;
            book.setAuthorList(authorListNew);
            List<Format> attachedFormatListNew = new ArrayList<Format>();
            for (Format formatListNewFormatToAttach : formatListNew) {
                formatListNewFormatToAttach = em.getReference(formatListNewFormatToAttach.getClass(), formatListNewFormatToAttach.getFormatId());
                attachedFormatListNew.add(formatListNewFormatToAttach);
            }
            formatListNew = attachedFormatListNew;
            book.setFormatList(formatListNew);
            List<Genre> attachedGenreListNew = new ArrayList<Genre>();
            for (Genre genreListNewGenreToAttach : genreListNew) {
                genreListNewGenreToAttach = em.getReference(genreListNewGenreToAttach.getClass(), genreListNewGenreToAttach.getGenreId());
                attachedGenreListNew.add(genreListNewGenreToAttach);
            }
            genreListNew = attachedGenreListNew;
            book.setGenreList(genreListNew);
            List<Review> attachedReviewListNew = new ArrayList<Review>();
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
                if (findBook(id) == null) {
                    throw new NonexistentEntityException("The book with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
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
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Book> findBookEntities() {
        return findBookEntities(true, -1, -1);
    }

    public List<Book> findBookEntities(int maxResults, int firstResult) {
        return findBookEntities(false, maxResults, firstResult);
    }

    private List<Book> findBookEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Book as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Book findBook(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Book.class, id);
        } finally {
            em.close();
        }
    }

    public int getBookCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Book as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
