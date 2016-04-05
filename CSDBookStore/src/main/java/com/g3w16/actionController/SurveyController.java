/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.actionController;

import com.g3w16.beans.SubmitSurveyBean;
import com.g3w16.beans.SurveyBean;
import com.g3w16.entities.Survey;
import com.g3w16.entities.SurveyAnswer;
import java.util.List;
import com.g3w16.entities.SurveyAnswerJpaController;
import com.g3w16.entities.SurveyJpaController;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jesuisnuageux
 */
@Named
@RequestScoped
public class SurveyController {
    
    @Inject 
    SurveyJpaController surveyJpaController;
    
    @Inject
    SurveyAnswerJpaController surveyAnswerJpaController;

    @Inject 
    SurveyBean surveyBean;
    @Inject
    SubmitSurveyBean submitSurveyBean;
    
    String session_id;
    
    public SurveyController(){
        this.session_id = FacesContext.getCurrentInstance().getExternalContext().getSessionId(true);
    }
    
    public Survey getSurvey(int surveyId){
        return surveyJpaController.findSurvey(surveyId);
    }
    
    public Survey getSurvey(){
        List<Survey> availableSurveys= surveyJpaController.findRemainingSurvey(session_id);
        if (availableSurveys.size()==0){
            return null;
        }else{
            return availableSurveys.get(
                    (new Random()).nextInt(
                            availableSurveys.size()
                    )
            );
        }
    }
    
    public HashMap<String,Integer> getSurveyResults(int surveyId){
        int[] votes = {0,0,0,0};
        Survey survey = surveyJpaController.findSurvey(surveyId);
        for (SurveyAnswer answer : surveyAnswerJpaController.getSurveyAnswerBySurvey(surveyId) ){
            votes[answer.getChoice()]+=1;
        }
        HashMap<String, Integer> results = new HashMap<>();
        results.put(survey.getAnswerDefault(), votes[0]);
        results.put(survey.getAnswerOne(), votes[1]);
        results.put(survey.getAnswerTwo(), votes[2]);
        results.put(survey.getAnswerThree(), votes[3]);
        return results;
    }
    
    /*
        Choices goes from 0 to 3 included
    */
    public void submitSurvey() throws RollbackFailureException{
        //Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Survey is : {0}", surveyBean.getSurvey());
        
        SurveyAnswer answer = new SurveyAnswer();
        answer.setChoice(submitSurveyBean.getChoice());
        answer.setSurvey(surveyJpaController.findSurvey(submitSurveyBean.getSurveyId()));
        try {
            surveyAnswerJpaController.create(answer, session_id);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
}
