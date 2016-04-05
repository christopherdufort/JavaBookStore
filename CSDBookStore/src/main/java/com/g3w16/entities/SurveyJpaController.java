/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import com.g3w16.entities.exceptions.IllegalOrphanException;
import com.g3w16.entities.exceptions.NonexistentEntityException;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
    public SurveyJpaController() {
        super();
    }

    public void create(Survey survey) throws RollbackFailureException, Exception {
        if (survey.getSurveyAnswerList() == null) {
            survey.setSurveyAnswerList(new ArrayList<SurveyAnswer>());
        }
        try {
            utx.begin();
            List<SurveyAnswer> attachedSurveyAnswerList = new ArrayList<SurveyAnswer>();
            for (SurveyAnswer surveyAnswerListSurveyAnswerToAttach : survey.getSurveyAnswerList()) {
                surveyAnswerListSurveyAnswerToAttach = em.getReference(surveyAnswerListSurveyAnswerToAttach.getClass(), surveyAnswerListSurveyAnswerToAttach.getSurveyAnswerPK());
                attachedSurveyAnswerList.add(surveyAnswerListSurveyAnswerToAttach);
            }
            survey.setSurveyAnswerList(attachedSurveyAnswerList);
            em.persist(survey);
            for (SurveyAnswer surveyAnswerListSurveyAnswer : survey.getSurveyAnswerList()) {
                Survey oldSurveyOfSurveyAnswerListSurveyAnswer = surveyAnswerListSurveyAnswer.getSurvey();
                surveyAnswerListSurveyAnswer.setSurvey(survey);
                surveyAnswerListSurveyAnswer = em.merge(surveyAnswerListSurveyAnswer);
                if (oldSurveyOfSurveyAnswerListSurveyAnswer != null) {
                    oldSurveyOfSurveyAnswerListSurveyAnswer.getSurveyAnswerList().remove(surveyAnswerListSurveyAnswer);
                    oldSurveyOfSurveyAnswerListSurveyAnswer = em.merge(oldSurveyOfSurveyAnswerListSurveyAnswer);
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

    public void edit(Survey survey) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Survey persistentSurvey = em.find(Survey.class, survey.getSurveyId());
            List<SurveyAnswer> surveyAnswerListOld = persistentSurvey.getSurveyAnswerList();
            List<SurveyAnswer> surveyAnswerListNew = survey.getSurveyAnswerList();
            List<String> illegalOrphanMessages = null;
            for (SurveyAnswer surveyAnswerListOldSurveyAnswer : surveyAnswerListOld) {
                if (!surveyAnswerListNew.contains(surveyAnswerListOldSurveyAnswer)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain SurveyAnswer " + surveyAnswerListOldSurveyAnswer + " since its survey field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<SurveyAnswer> attachedSurveyAnswerListNew = new ArrayList<SurveyAnswer>();
            for (SurveyAnswer surveyAnswerListNewSurveyAnswerToAttach : surveyAnswerListNew) {
                surveyAnswerListNewSurveyAnswerToAttach = em.getReference(surveyAnswerListNewSurveyAnswerToAttach.getClass(), surveyAnswerListNewSurveyAnswerToAttach.getSurveyAnswerPK());
                attachedSurveyAnswerListNew.add(surveyAnswerListNewSurveyAnswerToAttach);
            }
            surveyAnswerListNew = attachedSurveyAnswerListNew;
            survey.setSurveyAnswerList(surveyAnswerListNew);
            survey = em.merge(survey);
            for (SurveyAnswer surveyAnswerListNewSurveyAnswer : surveyAnswerListNew) {
                if (!surveyAnswerListOld.contains(surveyAnswerListNewSurveyAnswer)) {
                    Survey oldSurveyOfSurveyAnswerListNewSurveyAnswer = surveyAnswerListNewSurveyAnswer.getSurvey();
                    surveyAnswerListNewSurveyAnswer.setSurvey(survey);
                    surveyAnswerListNewSurveyAnswer = em.merge(surveyAnswerListNewSurveyAnswer);
                    if (oldSurveyOfSurveyAnswerListNewSurveyAnswer != null && !oldSurveyOfSurveyAnswerListNewSurveyAnswer.equals(survey)) {
                        oldSurveyOfSurveyAnswerListNewSurveyAnswer.getSurveyAnswerList().remove(surveyAnswerListNewSurveyAnswer);
                        oldSurveyOfSurveyAnswerListNewSurveyAnswer = em.merge(oldSurveyOfSurveyAnswerListNewSurveyAnswer);
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
                Integer id = survey.getSurveyId();
                if (findSurvey(id) == null) {
                    throw new NonexistentEntityException("The survey with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        try {
            utx.begin();
            Survey survey;
            try {
                survey = em.getReference(Survey.class, id);
                survey.getSurveyId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The survey with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<SurveyAnswer> surveyAnswerListOrphanCheck = survey.getSurveyAnswerList();
            for (SurveyAnswer surveyAnswerListOrphanCheckSurveyAnswer : surveyAnswerListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Survey (" + survey + ") cannot be destroyed since the SurveyAnswer " + surveyAnswerListOrphanCheckSurveyAnswer + " in its surveyAnswerList field has a non-nullable survey field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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

    public List<Survey> findSurveyEntities() {
        return findSurveyEntities(true, -1, -1);
    }

    public List<Survey> findSurveyEntities(int maxResults, int firstResult) {
        return findSurveyEntities(false, maxResults, firstResult);
    }

    private List<Survey> findSurveyEntities(boolean all, int maxResults, int firstResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Survey.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();
    }

    public Survey findSurvey(Integer id) {
        return em.find(Survey.class, id);
    }

    public int getSurveyCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Survey> rt = cq.from(Survey.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    /**
     * This method is responsible for finding all surveys in the database. It
     * makes use of the private method.
     *
     * @author Christopher Dufort
     * @version 0.2.5-SNAPSHOT -Last Modified 2/16/2016
     * @return
     */
    public List<Survey> findAllSurveys() {
        return findSurveyEntities(true, -1, -1);
    }

    public Survey findSurveyById(Integer id) {
        return em.find(Survey.class, id);
    }

    /**
     * PROBABLY WILL NOT BE USED
     *
     * @param question
     * @return
     */
    public Survey findSurveyByQuestion(String question) {
        //Example of named query(predefined in the entity class)
        Query query = em.createNamedQuery("Survey.findByQuestion");

        //binding for names parameters
        query.setParameter("question", question);

        //execute query returning single result
        Survey result = (Survey) query.getSingleResult();
        return result;
    }

    /**
     * PROBABLY WILL NOT BE USED
     *
     * @param answerOne
     * @return
     */
    public List<Survey> findSurveyByAnswerOne(String answerOne) {
        //Example of named query(predefined in the entity class)
        Query query = em.createNamedQuery("Survey.findByAnswerOne");

        //binding for names parameters
        query.setParameter("answerOne", answerOne);

        //execute query returning single result
        List<Survey> result = (List<Survey>) query.getResultList();
        return result;
    }

    /**
     * PROBABLY WILL NOT BE USED
     *
     * @param answerTwo
     * @return
     */
    public List<Survey> findSurveyByAnswerTwo(String answerTwo) {
        //Example of named query(predefined in the entity class)
        Query query = em.createNamedQuery("Survey.findByAnswerTwo");

        //binding for names parameters
        query.setParameter("answerTwo", answerTwo);

        //execute query returning single result
        List<Survey> result = (List<Survey>) query.getResultList();
        return result;
    }

    /**
     * PROBABLY WILL NOT BE USED
     */
    public List<Survey> findSurveyByAnswerThree(String answerThree) {
        //Example of named query(predefined in the entity class)
        Query query = em.createNamedQuery("Survey.findByAnswerThree");

        //binding for names parameters
        query.setParameter("answerThree", answerThree);

        //execute query returning single result
        List<Survey> result = (List<Survey>) query.getResultList();
        return result;
    }

    /**
     * PROBABLY WILL NOT BE USED
     *
     * @param answerDefault
     * @return
     */
    public List<Survey> findSurveyByAnswerDefault(String answerDefault) {
        //Example of named query(predefined in the entity class)
        Query query = em.createNamedQuery("Survey.findByAnswerDefault");

        //binding for names parameters
        query.setParameter("answerDefault", answerDefault);

        //execute query returning single result
        List<Survey> result = (List<Survey>) query.getResultList();
        return result;
    }

    public Survey findSurveyByActive() {
        Query query = em.createQuery("SELECT s FROM Survey s WHERE s.active = true");

        //execute query returning single result
        Survey result = (Survey) query.getSingleResult();
        return result;
    }

    public List<Survey> findRemainingSurvey(String sessionId) {
        Query query = em.createNamedQuery("Survey.findNotAnswered");
        query.setParameter("sessionId", sessionId);
        return query.getResultList();
    }
}
