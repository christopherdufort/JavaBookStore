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
public class FormatJpaController implements Serializable {

    public FormatJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Format format) throws RollbackFailureException, Exception {
        if (format.getBookList() == null) {
            format.setBookList(new ArrayList<Book>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
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
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Format format) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
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
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Format> findFormatEntities() {
        return findFormatEntities(true, -1, -1);
    }

    public List<Format> findFormatEntities(int maxResults, int firstResult) {
        return findFormatEntities(false, maxResults, firstResult);
    }

    private List<Format> findFormatEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Format as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Format findFormat(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Format.class, id);
        } finally {
            em.close();
        }
    }

    public int getFormatCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Format as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
