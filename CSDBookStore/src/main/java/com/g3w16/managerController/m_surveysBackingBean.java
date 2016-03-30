 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Xin Ma
 * @author Rita Lazaar
 */
@Named("m_surveys")
@RequestScoped
public class m_surveysBackingBean {

    
    private Survey survey;

    @Inject
    SurveyJpaController surveyJpa;

    public String preCreateSurvey() {
        return "m_createSurvey";
    }

    public Survey getSurvey() {
        if(survey==null)
            survey=new Survey();
        return survey;
    }
    
    public void setSurvey(Survey survey){
        this.survey=survey;
    }

    public String createSurvey() {
        try {
            surveyJpa.create(survey);
        } catch (Exception ex) {
            Logger.getLogger(m_surveysBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        return "m_surveys";
    }

    public String destroySurvey(Survey s) {

        try {
           surveyJpa.destroy(s.getSurveyId());          
        } catch (RollbackFailureException ex) {
            Logger.getLogger(m_surveysBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(m_surveysBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "m_surveys";
    }

     public String cancel() { 
        return "m_surveys";
    }
    
    public List<Survey> getAllSurvey() {
        return surveyJpa.findAllSurveys();
    }

    public int getSurveyCount() {
        return surveyJpa.getSurveyCount();
    }
}
