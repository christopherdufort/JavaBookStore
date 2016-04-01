/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

/**
 *
 * @author Xin Ma
 * @author Rita Lazaar
 */
@ManagedBean(name = "m_surveys")
@SessionScoped
public class m_surveysBackingBean implements Serializable {

    private Survey survey;
    private List<Survey> allSurvey;

    @Inject
    SurveyJpaController surveyJpa;

    @PostConstruct
    public void init() {
        allSurvey = surveyJpa.findAllSurveys();
    }

    public String preCreateSurvey() {
        return "m_createSurvey";
    }

    public Survey getSurvey() {
        if (survey == null) {
            survey = new Survey();
        }
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public String createSurvey() {

        try {
            surveyJpa.create(survey);
        } catch (Exception ex) {
            Logger.getLogger(m_surveysBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        allSurvey = surveyJpa.findAllSurveys();
        return "m_surveys";
    }

    public String editSurvey(Survey s) {
        survey = surveyJpa.findSurveyById(s.getSurveyId());
        return "m_editSurvey";
    }

    public String updateSurvey() {
        try {
            surveyJpa.edit(survey);
        } catch (Exception ex) {
            Logger.getLogger(m_surveysBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        allSurvey = surveyJpa.findAllSurveys();
        return "m_surveys";
    }

    public void active(Survey s) {
        survey = surveyJpa.findSurveyById(s.getSurveyId());
        survey.setActive(s.getActive());
        try {
            surveyJpa.edit(survey);
        } catch (Exception ex) {
            Logger.getLogger(m_surveysBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void destroySurvey(Survey s) {

        try {
            surveyJpa.destroy(s.getSurveyId());
        } catch (RollbackFailureException ex) {
            Logger.getLogger(m_surveysBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(m_surveysBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        allSurvey = surveyJpa.findAllSurveys();
    }

    public String cancel() {
        return "m_surveys";
    }

    public List<Survey> getAllSurvey() {
        return allSurvey;
    }

    public int getSurveyCount() {
        return surveyJpa.getSurveyCount();
    }
}
