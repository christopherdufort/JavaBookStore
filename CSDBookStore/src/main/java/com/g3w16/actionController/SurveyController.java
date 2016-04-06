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
import com.g3w16.entities.SurveyAnswerPK;
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
import javax.persistence.NoResultException;

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
    
    /*
    public Survey getSurvey(int surveyId){
        return surveyJpaController.findSurvey(surveyId);
    }
    */
    
    public boolean hasAllreadyAnsweredThisSurvey(){
        SurveyAnswerPK pk ;
        try{
            pk = surveyAnswerJpaController.getPK(session_id, getSurvey().getSurveyId());
        }catch(NoResultException e){
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, null, e);
            return false;
        }
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "hasAllreadyAnsweredThisSurvey returned : {0}", pk!=null);
        return pk!=null;
    }
    
    public boolean hasNotAllreadyAnsweredThisSurvey(){
        return !hasAllreadyAnsweredThisSurvey();
    }
    
    public Survey getSurvey(){
        Survey currentSurvey = surveyJpaController.findSurveyByActive();
        return currentSurvey;
    }
    
    public String getSurveyResultsAsJSArray(){
        int[] votes = {0,0,0,0};
        Survey survey = getSurvey();
        for (SurveyAnswer answer : surveyAnswerJpaController.getSurveyAnswerBySurvey(survey.getSurveyId()) ){
            votes[answer.getChoice()]+=1;
        }
        HashMap<String, Integer> results = new HashMap<>();
        results.put(survey.getAnswerDefault(), votes[0]);
        results.put(survey.getAnswerOne(), votes[1]);
        results.put(survey.getAnswerTwo(), votes[2]);
        results.put(survey.getAnswerThree(), votes[3]);
        StringBuilder sb = new StringBuilder();
        for(String key : results.keySet()){
            sb.append("['").append(key).append("',").append(results.get(key).toString()).append("],");
        }
        return sb.toString();
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
