/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import com.g3w16.entities.exceptions.NonexistentEntityException;
import com.g3w16.entities.exceptions.PreexistingEntityException;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author jesuisnuageux
 */
public class SurveyAnswerJpaController implements Serializable {

    @Resource
    private UserTransaction utx;

    @PersistenceContext
    private EntityManager em;
    
    
    public SurveyAnswerPK getPK(String sessionId, int surveyId){
        try{
            return (SurveyAnswerPK) em.createQuery("SELECT a.surveyAnswerPK FROM SurveyAnswer a WHERE a.surveyAnswerPK.sessionId = :sessionId AND a.surveyAnswerPK.surveyId = :surveyId")
                        .setParameter("sessionId", sessionId)
                        .setParameter("surveyId", surveyId)
                        .getSingleResult();
        }catch(Throwable anyofthem){
            return null;
        }
    }

    public void create(SurveyAnswer surveyAnswer, String sessionId) throws PreexistingEntityException, RollbackFailureException, Exception {
        // stuff under here aimed at let the create method be as easy to use as possible
        try {
            utx.begin();
            try {
                surveyAnswer.setSurveyAnswerPK(
                        (SurveyAnswerPK) em.createQuery("SELECT a.surveyAnswerPK FROM SurveyAnswer a WHERE a.surveyAnswerPK.sessionId = :sessionId AND a.surveyAnswerPK.surveyId = :surveyId")
                        .setParameter("sessionId", sessionId)
                        .setParameter("surveyId", surveyAnswer.getSurvey().getSurveyId())
                        .getSingleResult()
                );
            } catch (NoResultException e) {
                surveyAnswer.setSurveyAnswerPK(new SurveyAnswerPK());
                surveyAnswer.getSurveyAnswerPK().setSurveyId(surveyAnswer.getSurvey().getSurveyId());
                surveyAnswer.getSurveyAnswerPK().setSessionId(sessionId);
            }

            Survey survey = surveyAnswer.getSurvey();
            em.persist(surveyAnswer);
            if (survey != null) {
                survey.getSurveyAnswerList().add(surveyAnswer);
                em.merge(survey);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findSurveyAnswer(surveyAnswer.getSurveyAnswerPK()) != null) {
                throw new PreexistingEntityException("SurveyAnswer " + surveyAnswer + " already exists.", ex);
            }
            throw ex;
        }
    }

    public void edit(SurveyAnswer surveyAnswer) throws NonexistentEntityException, RollbackFailureException, Exception {
        surveyAnswer.getSurveyAnswerPK().setSurveyId(surveyAnswer.getSurvey().getSurveyId());
        try {
            utx.begin();
            SurveyAnswer persistentSurveyAnswer = em.find(SurveyAnswer.class, surveyAnswer.getSurveyAnswerPK());
            Survey surveyOld = persistentSurveyAnswer.getSurvey();
            Survey surveyNew = surveyAnswer.getSurvey();
            if (surveyNew != null) {
                surveyNew = em.getReference(surveyNew.getClass(), surveyNew.getSurveyId());
                surveyAnswer.setSurvey(surveyNew);
            }
            surveyAnswer = em.merge(surveyAnswer);
            if (surveyOld != null && !surveyOld.equals(surveyNew)) {
                surveyOld.getSurveyAnswerList().remove(surveyAnswer);
                surveyOld = em.merge(surveyOld);
            }
            if (surveyNew != null && !surveyNew.equals(surveyOld)) {
                surveyNew.getSurveyAnswerList().add(surveyAnswer);
                surveyNew = em.merge(surveyNew);
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
                SurveyAnswerPK id = surveyAnswer.getSurveyAnswerPK();
                if (findSurveyAnswer(id) == null) {
                    throw new NonexistentEntityException("The surveyAnswer with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(SurveyAnswerPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            SurveyAnswer surveyAnswer;
            try {
                surveyAnswer = em.getReference(SurveyAnswer.class, id);
                surveyAnswer.getSurveyAnswerPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The surveyAnswer with id " + id + " no longer exists.", enfe);
            }
            Survey survey = surveyAnswer.getSurvey();
            if (survey != null) {
                survey.getSurveyAnswerList().remove(surveyAnswer);
                survey = em.merge(survey);
            }
            em.remove(surveyAnswer);
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

    public List<SurveyAnswer> findSurveyAnswerEntities() {
        return findSurveyAnswerEntities(true, -1, -1);
    }

    public List<SurveyAnswer> findSurveyAnswerEntities(int maxResults, int firstResult) {
        return findSurveyAnswerEntities(false, maxResults, firstResult);
    }

    private List<SurveyAnswer> findSurveyAnswerEntities(boolean all, int maxResults, int firstResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(SurveyAnswer.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public SurveyAnswer findSurveyAnswer(SurveyAnswerPK id) {
        return em.find(SurveyAnswer.class, id);
    }

    public int getSurveyAnswerCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<SurveyAnswer> rt = cq.from(SurveyAnswer.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public List<SurveyAnswer> getSurveyAnswerBySurvey(int survey_id) {
        Query query = em.createNamedQuery("SurveyAnswer.findBySurveyId");
        query.setParameter("surveyId", survey_id);
        return query.getResultList();
    }

}
