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
public class TitleJpaController implements Serializable {

    public TitleJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Title title) throws RollbackFailureException, Exception {
        if (title.getRegisteredUserList() == null) {
            title.setRegisteredUserList(new ArrayList<RegisteredUser>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<RegisteredUser> attachedRegisteredUserList = new ArrayList<RegisteredUser>();
            for (RegisteredUser registeredUserListRegisteredUserToAttach : title.getRegisteredUserList()) {
                registeredUserListRegisteredUserToAttach = em.getReference(registeredUserListRegisteredUserToAttach.getClass(), registeredUserListRegisteredUserToAttach.getUserId());
                attachedRegisteredUserList.add(registeredUserListRegisteredUserToAttach);
            }
            title.setRegisteredUserList(attachedRegisteredUserList);
            em.persist(title);
            for (RegisteredUser registeredUserListRegisteredUser : title.getRegisteredUserList()) {
                Title oldTitleIdOfRegisteredUserListRegisteredUser = registeredUserListRegisteredUser.getTitleId();
                registeredUserListRegisteredUser.setTitleId(title);
                registeredUserListRegisteredUser = em.merge(registeredUserListRegisteredUser);
                if (oldTitleIdOfRegisteredUserListRegisteredUser != null) {
                    oldTitleIdOfRegisteredUserListRegisteredUser.getRegisteredUserList().remove(registeredUserListRegisteredUser);
                    oldTitleIdOfRegisteredUserListRegisteredUser = em.merge(oldTitleIdOfRegisteredUserListRegisteredUser);
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

    public void edit(Title title) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Title persistentTitle = em.find(Title.class, title.getTitleId());
            List<RegisteredUser> registeredUserListOld = persistentTitle.getRegisteredUserList();
            List<RegisteredUser> registeredUserListNew = title.getRegisteredUserList();
            List<RegisteredUser> attachedRegisteredUserListNew = new ArrayList<RegisteredUser>();
            for (RegisteredUser registeredUserListNewRegisteredUserToAttach : registeredUserListNew) {
                registeredUserListNewRegisteredUserToAttach = em.getReference(registeredUserListNewRegisteredUserToAttach.getClass(), registeredUserListNewRegisteredUserToAttach.getUserId());
                attachedRegisteredUserListNew.add(registeredUserListNewRegisteredUserToAttach);
            }
            registeredUserListNew = attachedRegisteredUserListNew;
            title.setRegisteredUserList(registeredUserListNew);
            title = em.merge(title);
            for (RegisteredUser registeredUserListOldRegisteredUser : registeredUserListOld) {
                if (!registeredUserListNew.contains(registeredUserListOldRegisteredUser)) {
                    registeredUserListOldRegisteredUser.setTitleId(null);
                    registeredUserListOldRegisteredUser = em.merge(registeredUserListOldRegisteredUser);
                }
            }
            for (RegisteredUser registeredUserListNewRegisteredUser : registeredUserListNew) {
                if (!registeredUserListOld.contains(registeredUserListNewRegisteredUser)) {
                    Title oldTitleIdOfRegisteredUserListNewRegisteredUser = registeredUserListNewRegisteredUser.getTitleId();
                    registeredUserListNewRegisteredUser.setTitleId(title);
                    registeredUserListNewRegisteredUser = em.merge(registeredUserListNewRegisteredUser);
                    if (oldTitleIdOfRegisteredUserListNewRegisteredUser != null && !oldTitleIdOfRegisteredUserListNewRegisteredUser.equals(title)) {
                        oldTitleIdOfRegisteredUserListNewRegisteredUser.getRegisteredUserList().remove(registeredUserListNewRegisteredUser);
                        oldTitleIdOfRegisteredUserListNewRegisteredUser = em.merge(oldTitleIdOfRegisteredUserListNewRegisteredUser);
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
                Integer id = title.getTitleId();
                if (findTitle(id) == null) {
                    throw new NonexistentEntityException("The title with id " + id + " no longer exists.");
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
            Title title;
            try {
                title = em.getReference(Title.class, id);
                title.getTitleId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The title with id " + id + " no longer exists.", enfe);
            }
            List<RegisteredUser> registeredUserList = title.getRegisteredUserList();
            for (RegisteredUser registeredUserListRegisteredUser : registeredUserList) {
                registeredUserListRegisteredUser.setTitleId(null);
                registeredUserListRegisteredUser = em.merge(registeredUserListRegisteredUser);
            }
            em.remove(title);
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

    public List<Title> findTitleEntities() {
        return findTitleEntities(true, -1, -1);
    }

    public List<Title> findTitleEntities(int maxResults, int firstResult) {
        return findTitleEntities(false, maxResults, firstResult);
    }

    private List<Title> findTitleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Title as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Title findTitle(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Title.class, id);
        } finally {
            em.close();
        }
    }

    public int getTitleCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Title as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
