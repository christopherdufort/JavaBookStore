/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import com.g3w16.entities.exceptions.NonexistentEntityException;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author Christopher Dufort
 * @version 0.2.5-SNAPSHOT -Last modified 2/16/2016
 */
@Named
@SessionScoped
public class SurveyJpaController implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;
    
    /**
     * Default Constructor
     */
    public SurveyJpaController(){
        super();
    }

    public void create(Survey survey) throws RollbackFailureException, Exception {
        try {
            utx.begin();
            em.persist(survey);
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

    public void edit(Survey survey) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            survey = em.merge(survey);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = survey.getSurveyId();
                if (findSurveyById(id) == null) {
                    throw new NonexistentEntityException("The survey with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } 
    }
    public void destroySurvey(Survey survey)throws NonexistentEntityException, RollbackFailureException, Exception {
        this.destroy(survey.getSurveyId());
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Survey survey;
            try {
                survey = em.getReference(Survey.class, id);
                survey.getSurveyId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The survey with id " + id + " no longer exists.", enfe);
            }
            em.remove(survey);
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
    
    /**
     * This method is responsible for finding all surveys in the database.
     * It makes use of the private method.
     * 
     * @author Christopher Dufort
     * @version 0.2.5-SNAPSHOT -Last Modified 2/16/2016
     * @return 
     */
    public List<Survey> findAllSurveys() {
        return findSurveyEntities(true, -1, -1);
    }

    public List<Survey> findSurveyEntities(int maxResults, int firstResult) {
        return findSurveyEntities(false, maxResults, firstResult);
    }

    private List<Survey> findSurveyEntities(boolean all, int maxResults, int firstResult) {
        Query q = em.createQuery("select object(o) from Survey as o");
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public Survey findSurveyById(Integer id) {
        return em.find(Survey.class, id);
    }

    public int getSurveyCount() {
        Query q = em.createQuery("select count(o) from Survey as o");
        return ((Long) q.getSingleResult()).intValue();
    }
    /**
     * PROBABLY WILL NOT BE USED
     * @param question
     * @return 
     */
    public Survey findSurveyByQuestion(String question){
        //Example of named query(predefined in the entity class)
        Query query = em.createNamedQuery("Survey.findByQuestion");
 
        //binding for names parameters
        query.setParameter("question",question);
        
        //execute query returning single result
        Survey result = (Survey)query.getSingleResult(); 
        return result;
    }
    /**
     * PROBABLY WILL NOT BE USED
     * @param answerOne
     * @return 
     */
    public List<Survey> findSurveyByAnswerOne(String answerOne){
        //Example of named query(predefined in the entity class)
        Query query = em.createNamedQuery("Survey.findByAnswerOne");
 
        //binding for names parameters
        query.setParameter("answerOne",answerOne);
        
        //execute query returning single result
        List<Survey> result = (List<Survey>)query.getResultList(); 
        return result;   
    }
    /**
     * PROBABLY WILL NOT BE USED
     * @param answerTwo
     * @return 
     */
    public List<Survey> findSurveyByAnswerTwo(String answerTwo){
        //Example of named query(predefined in the entity class)
        Query query = em.createNamedQuery("Survey.findByAnswerTwo");
 
        //binding for names parameters
        query.setParameter("answerTwo",answerTwo);
        
        //execute query returning single result
        List<Survey> result = (List<Survey>)query.getResultList(); 
        return result;          
    }
    /**
     * PROBABLY WILL NOT BE USED
     */
    public List<Survey> findSurveyByAnswerThree(String answerThree){
        //Example of named query(predefined in the entity class)
        Query query = em.createNamedQuery("Survey.findByAnswerThree");
 
        //binding for names parameters
        query.setParameter("answerThree",answerThree);
        
        //execute query returning single result
        List<Survey> result = (List<Survey>)query.getResultList(); 
        return result;          
    }
    /**
     * PROBABLY WILL NOT BE USED
     * @param answerDefault
     * @return 
     */
    public List<Survey> findSurveyByAnswerDefault(String answerDefault){
         //Example of named query(predefined in the entity class)
        Query query = em.createNamedQuery("Survey.findByAnswerDefault");
 
        //binding for names parameters
        query.setParameter("answerDefault",answerDefault);
        
        //execute query returning single result
        List<Survey> result = (List<Survey>)query.getResultList(); 
        return result;         
    }
}
