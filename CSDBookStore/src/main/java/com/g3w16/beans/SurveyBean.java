/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

import com.g3w16.entities.Survey;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author jesuisnuageux
 */
@Named
@RequestScoped
public class SurveyBean {
    
    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    private Survey survey;
}
