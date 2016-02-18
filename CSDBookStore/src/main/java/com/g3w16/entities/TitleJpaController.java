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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author Giuseppe Campanelli
 */
public class TitleJpaController implements Serializable {

    @Resource
    private UserTransaction utx;
    @PersistenceContext
    private EntityManager em;

    public void create(Title title) throws RollbackFailureException, Exception {
        if (title.getRegisteredUserList() == null) {
            title.setRegisteredUserList(new ArrayList<RegisteredUser>());
        }
        try {
            utx.begin();
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
        }
    }

    public void edit(Title title) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
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
                if (findTitleById(id) == null) {
                    throw new NonexistentEntityException("The title with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
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
        }
    }
    
    public List<Title> findAll() {
        Query q = em.createNamedQuery("Title.findAll", Title.class);
        return q.getResultList();
    }
    
    public Title findTitleById(int id) {
        return em.find(Title.class, id);
    }
    
    public Title findTitleByName(String title) {
        Query q = em.createNamedQuery("Title.findByTitle", Title.class);
        q.setParameter("title", title);
        return (Title) q.getSingleResult();
    }

    public int getTitleCount() {
        Query q = em.createQuery("select count(o) from Title as o");
        return ((Long) q.getSingleResult()).intValue();
    }
}
