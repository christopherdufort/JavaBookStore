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
public class ProvinceJpaController implements Serializable {

    @Resource
    private UserTransaction utx;
    @PersistenceContext
    private EntityManager em;

    public void create(Province province) throws RollbackFailureException, Exception {
        if (province.getRegisteredUserList() == null) {
            province.setRegisteredUserList(new ArrayList<RegisteredUser>());
        }
        try {
            utx.begin();
            List<RegisteredUser> attachedRegisteredUserList = new ArrayList<RegisteredUser>();
            for (RegisteredUser registeredUserListRegisteredUserToAttach : province.getRegisteredUserList()) {
                registeredUserListRegisteredUserToAttach = em.getReference(registeredUserListRegisteredUserToAttach.getClass(), registeredUserListRegisteredUserToAttach.getUserId());
                attachedRegisteredUserList.add(registeredUserListRegisteredUserToAttach);
            }
            province.setRegisteredUserList(attachedRegisteredUserList);
            em.persist(province);
            for (RegisteredUser registeredUserListRegisteredUser : province.getRegisteredUserList()) {
                Province oldProvinceIdOfRegisteredUserListRegisteredUser = registeredUserListRegisteredUser.getProvinceId();
                registeredUserListRegisteredUser.setProvinceId(province);
                registeredUserListRegisteredUser = em.merge(registeredUserListRegisteredUser);
                if (oldProvinceIdOfRegisteredUserListRegisteredUser != null) {
                    oldProvinceIdOfRegisteredUserListRegisteredUser.getRegisteredUserList().remove(registeredUserListRegisteredUser);
                    oldProvinceIdOfRegisteredUserListRegisteredUser = em.merge(oldProvinceIdOfRegisteredUserListRegisteredUser);
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

    public void edit(Province province) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Province persistentProvince = em.find(Province.class, province.getProvinceId());
            List<RegisteredUser> registeredUserListOld = persistentProvince.getRegisteredUserList();
            List<RegisteredUser> registeredUserListNew = province.getRegisteredUserList();
            List<RegisteredUser> attachedRegisteredUserListNew = new ArrayList<RegisteredUser>();
            for (RegisteredUser registeredUserListNewRegisteredUserToAttach : registeredUserListNew) {
                registeredUserListNewRegisteredUserToAttach = em.getReference(registeredUserListNewRegisteredUserToAttach.getClass(), registeredUserListNewRegisteredUserToAttach.getUserId());
                attachedRegisteredUserListNew.add(registeredUserListNewRegisteredUserToAttach);
            }
            registeredUserListNew = attachedRegisteredUserListNew;
            province.setRegisteredUserList(registeredUserListNew);
            province = em.merge(province);
            for (RegisteredUser registeredUserListOldRegisteredUser : registeredUserListOld) {
                if (!registeredUserListNew.contains(registeredUserListOldRegisteredUser)) {
                    registeredUserListOldRegisteredUser.setProvinceId(null);
                    registeredUserListOldRegisteredUser = em.merge(registeredUserListOldRegisteredUser);
                }
            }
            for (RegisteredUser registeredUserListNewRegisteredUser : registeredUserListNew) {
                if (!registeredUserListOld.contains(registeredUserListNewRegisteredUser)) {
                    Province oldProvinceIdOfRegisteredUserListNewRegisteredUser = registeredUserListNewRegisteredUser.getProvinceId();
                    registeredUserListNewRegisteredUser.setProvinceId(province);
                    registeredUserListNewRegisteredUser = em.merge(registeredUserListNewRegisteredUser);
                    if (oldProvinceIdOfRegisteredUserListNewRegisteredUser != null && !oldProvinceIdOfRegisteredUserListNewRegisteredUser.equals(province)) {
                        oldProvinceIdOfRegisteredUserListNewRegisteredUser.getRegisteredUserList().remove(registeredUserListNewRegisteredUser);
                        oldProvinceIdOfRegisteredUserListNewRegisteredUser = em.merge(oldProvinceIdOfRegisteredUserListNewRegisteredUser);
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
                Integer id = province.getProvinceId();
                if (findProvinceById(id) == null) {
                    throw new NonexistentEntityException("The province with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Province province;
            try {
                province = em.getReference(Province.class, id);
                province.getProvinceId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The province with id " + id + " no longer exists.", enfe);
            }
            List<RegisteredUser> registeredUserList = province.getRegisteredUserList();
            for (RegisteredUser registeredUserListRegisteredUser : registeredUserList) {
                registeredUserListRegisteredUser.setProvinceId(null);
                registeredUserListRegisteredUser = em.merge(registeredUserListRegisteredUser);
            }
            em.remove(province);
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
    
    public List<Province> findAll() {
        Query q = em.createNamedQuery("findAll", Province.class);
        return q.getResultList();
    }
    
    public Province findProvinceById(int id) {
        return em.find(Province.class, id);
    }
    
    public Province findProvinceByName(String name) {
        Query q = em.createNamedQuery("findByProvince", Province.class);
        q.setParameter("province", name);
        return (Province) q.getSingleResult();
    }

    public int getProvinceCount() {
        Query q = em.createQuery("select count(o) from Province as o");
        return ((Long) q.getSingleResult()).intValue();
    }
}
