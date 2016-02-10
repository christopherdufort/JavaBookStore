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
        return findFormatEntities(true, -1, -1);
    }

    public List<Format> findFormatEntities(int maxResults, int firstResult) {
        return findFormatEntities(false, maxResults, firstResult);
    }

    private List<Format> findFormatEntities(boolean all, int maxResults, int firstResult) {
        Query q = em.createQuery("select object(o) from Format as o");
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public Format findFormat(Integer id) {
        return em.find(Format.class, id);
    }

    public int getFormatCount() {
        Query q = em.createQuery("select count(o) from Format as o");
        return ((Long) q.getSingleResult()).intValue();
    }

}
