/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;


import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author jesuisnuageux
 */
@Named
@RequestScoped
public class SubmitSurveyBean {
    private int choice;
    private int surveyId;

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }   
}
