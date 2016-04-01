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
public class FormatJpaController implements Serializable {

    public FormatJpaController() {

    }
    @Resource
    private UserTransaction utx;
    @PersistenceContext
    private EntityManager em;

    public void create(Format format) throws RollbackFailureException, Exception {
        if (format.getBookList() == null) {
            format.setBookList(new ArrayList<Book>());
        }
        try {
            utx.begin();
            List<Book> attachedBookList = new ArrayList<Book>();
            for (Book bookListBookToAttach : format.getBookList()) {
                bookListBookToAttach = em.getReference(bookListBookToAttach.getClass(), bookListBookToAttach.getBookId());
                attachedBookList.add(bookListBookToAttach);
            }
            format.setBookList(attachedBookList);
            em.persist(format);
            for (Book bookListBook : format.getBookList()) {
                bookListBook.getFormatList().add(format);
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

    public void edit(Format format) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Format persistentFormat = em.find(Format.class, format.getFormatId());
            List<Book> bookListOld = persistentFormat.getBookList();
            List<Book> bookListNew = format.getBookList();
            List<Book> attachedBookListNew = new ArrayList<Book>();
            for (Book bookListNewBookToAttach : bookListNew) {
                bookListNewBookToAttach = em.getReference(bookListNewBookToAttach.getClass(), bookListNewBookToAttach.getBookId());
                attachedBookListNew.add(bookListNewBookToAttach);
            }
            bookListNew = attachedBookListNew;
            format.setBookList(bookListNew);
            format = em.merge(format);
            for (Book bookListOldBook : bookListOld) {
                if (!bookListNew.contains(bookListOldBook)) {
                    bookListOldBook.getFormatList().remove(format);
                    bookListOldBook = em.merge(bookListOldBook);
                }
            }
            for (Book bookListNewBook : bookListNew) {
                if (!bookListOld.contains(bookListNewBook)) {
                    bookListNewBook.getFormatList().add(format);
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
                Integer id = format.getFormatId();
                if (findFormat(id) == null) {
                    throw new NonexistentEntityException("The format with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Format format;
            try {
                format = em.getReference(Format.class, id);
                format.getFormatId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The format with id " + id + " no longer exists.", enfe);
            }
            List<Book> bookList = format.getBookList();
            for (Book bookListBook : bookList) {
                bookListBook.getFormatList().remove(format);
                bookListBook = em.merge(bookListBook);
            }
            em.remove(format);
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

    public List<Format> findFormatEntities() {
        return findFormatEntities(true, false, -1, -1);
    }

    public List<Format> findFormatEntitiesAsClient() {
        return findFormatEntities(true, true, -1, -1);
    }

    public List<Format> findFormatEntities(int maxResults, int firstResult) {
        return findFormatEntities(false, false, maxResults, firstResult);
    }

    public List<Format> findFormatEntitiesAsClient(int maxResults, int firstResult) {
        return findFormatEntities(false, false, maxResults, firstResult);
    }

    private List<Format> findFormatEntities(boolean all, boolean asClient, int maxResults, int firstResult) {
        Query q;
        if (asClient) {
            q = em.createNamedQuery("Format.findAllAvailable");
        } else {
            q = em.createNamedQuery("Format.findAll");
        }
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public List<Format> findFormatEntitiesLike(String extension) {
        return findFormatEntitiesLike(extension, true, false, -1, -1);
    }

    public List<Format> findFormatEntitiesLikeAsClient(String extension) {
        return findFormatEntitiesLike(extension, true, true, -1, -1);
    }

    public List<Format> findformatEntitiesLike(String extension, int maxResults, int firstresult) {
        return findFormatEntitiesLike(extension, false, false, maxResults, firstresult);
    }

    public List<Format> findformatEntitiesLikeAsClient(String extension, int maxResults, int firstresult) {
        return findFormatEntitiesLike(extension, false, true, maxResults, firstresult);
    }

    private List<Format> findFormatEntitiesLike(String extension, boolean all, boolean asClient, int maxResults, int firstResult) {
        Query q;
        if (asClient) {
            q = em.createNamedQuery("Format.findByExtensionAvailable");
        } else {
            q = em.createNamedQuery("Format.findByExtension");
        }
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        q.setParameter("extension", "%" + extension + "%");
        return q.getResultList();
    }

    public List<Format> findFormatEntitiesByBook(Book book) {
        return findFormatEntitiesByBook(book, true, false, -1, -1);
    }

    public List<Format> findFormatEntitiesByBookAsClient(Book book) {
        return findFormatEntitiesByBook(book, true, true, -1, -1);
    }

    public List<Format> findFormatEntitiesByBook(Book book, int maxResults, int firstResult) {
        return findFormatEntitiesByBook(book, false, false, maxResults, firstResult);
    }

    public List<Format> findFormatEntitiesByBookAsClient(Book book, int maxResults, int firstResult) {
        return findFormatEntitiesByBook(book, false, true, maxResults, firstResult);
    }

    private List<Format> findFormatEntitiesByBook(Book book, boolean all, boolean asClient, int maxResults, int firstResult) {
        Query q;
        if (asClient) {
            q = em.createNamedQuery("Format.findByBookIdAvailable");
        } else {
            q = em.createNamedQuery("Format.findByBookId");
        }
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        q.setParameter("bookId", book.getBookId());
        return q.getResultList();
    }

    public List<Format> findFormatEntitiesByGenre(Genre genre) {
        return findFormatEntitiesByGenre(genre, true, false, -1, -1);
    }

    public List<Format> findFormatEntitiesByGenreAsClient(Genre genre) {
        return findFormatEntitiesByGenre(genre, true, true, -1, -1);
    }

    public List<Format> findformatEntitiesByGenre(Genre genre, int maxResults, int firstResult) {
        return findFormatEntitiesByGenre(genre, false, false, maxResults, firstResult);
    }

    public List<Format> findformatEntitiesByGenreAsClient(Genre genre, int maxResults, int firstResult) {
        return findFormatEntitiesByGenre(genre, false, true, maxResults, firstResult);
    }

    private List<Format> findFormatEntitiesByGenre(Genre genre, boolean all, boolean asClient, int maxResults, int firstResult) {
        Query q;
        if (asClient) {
            q = em.createNamedQuery("Format.findByGenreIdAvailable");
        } else {
            q = em.createNamedQuery("Format.findByGenreId");
        }
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        q.setParameter("genreId", genre.getGenreId());
        return q.getResultList();
    }

    public List<Format> findFormatEntitiesByAuthor(Author author) {
        return findFormatEntitiesByAuthor(author, true, false, -1, -1);
    }

    public List<Format> findFormatEntitiesByAuthorAsClient(Author author) {
        return findFormatEntitiesByAuthor(author, true, true, -1, -1);
    }

    public List<Format> findFormatEntitiesByAuthor(Author author, int maxResults, int firstResult) {
        return findFormatEntitiesByAuthor(author, false, false, maxResults, firstResult);
    }

    public List<Format> findFormatEntitiesByAuthorAsClient(Author author, int maxResults, int firstResult) {
        return findFormatEntitiesByAuthor(author, false, true, maxResults, firstResult);
    }

    private List<Format> findFormatEntitiesByAuthor(Author author, boolean all, boolean asClient, int maxResults, int firstResult) {
        Query q;
        if (asClient) {
            q = em.createNamedQuery("Format.findByAuthorIdAvailable");
        } else {
            q = em.createNamedQuery("Format.findByAuthorId");
        }
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        q.setParameter("authorId", author.getAuthorId());
        return q.getResultList();
    }

    public Format findFormat(Integer id) {
        return em.find(Format.class, id);
    }

    public Format findByExtension(String extension) {
        try {
            Query q = em.createNamedQuery("Format.findByExtension", Format.class);
            q.setParameter("extension", extension);
            return (Format) q.getSingleResult();
        } catch (NoResultException e) {
            return new Format();
        }
    }

    public int getFormatCount() {
        Query q = em.createQuery("select count(o) from Format as o");
        return ((Long) q.getSingleResult()).intValue();
    }

}
