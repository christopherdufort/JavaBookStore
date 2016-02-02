/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

/**
 * @author Christopher Dufort
 * @version 0.0.2 - Last modified 2/2/2016
 * @since 0.0.2 -Originally written 2/2/2016
 */
public class SurveyBean {
    
    private int surveyId;
    private String question;
    private String answerOne;
    private String answerTwo;
    private String answerThree;
    private String answerDefault;
    
    /**
     * 
     */
    public SurveyBean(){
        super();
    }
    
    /**
     * 
     * @param surveyId
     * @param question
     * @param answerOne
     * @param answerTwo
     * @param answerThree
     * @param answerDefault 
     */
    public SurveyBean(final int surveyId, final String question, final String answerOne, final String answerTwo, final String answerThree, final String answerDefault) {
        super();
        this.surveyId = surveyId;
        this.question = question;
        this.answerOne = answerOne;
        this.answerTwo = answerTwo;
        this.answerThree = answerThree;
        this.answerDefault = answerDefault;
    }
    
    /**
     * 
     * @return 
     */
    public int getSurveyId() {
        return surveyId;
    }

    /**
     * 
     * @param surveyId 
     */
    public void setSurveyId(final int surveyId) {
        this.surveyId = surveyId;
    }
    
    /**
     * 
     * @return 
     */
    public String getQuestion() {
        return question;
    }

    /**
     * 
     * @param question 
     */
    public void setQuestion(final String question) {
        this.question = question;
    }
    
    /**
     * 
     * @return 
     */
    public String getAnswerOne() {
        return answerOne;
    }

    /**
     * 
     * @param answerOne 
     */
    public void setAnswerOne(final String answerOne) {
        this.answerOne = answerOne;
    }

    /**
     * 
     * @return 
     */
    public String getAnswerTwo() {
        return answerTwo;
    }

    /**
     * 
     * @param answerTwo 
     */
    public void setAnswerTwo(final String answerTwo) {
        this.answerTwo = answerTwo;
    }
    
    /**
     * 
     * @return 
     */
    public String getAnswerThree() {
        return answerThree;
    }

    /**
     * 
     * @param answerThree 
     */
    public void setAnswerThree(final String answerThree) {
        this.answerThree = answerThree;
    }

    /**
     * 
     * @return 
     */
    public String getAnswerDefault() {
        return answerDefault;
    }

    /**
     * 
     * @param answerDefault 
     */
    public void setAnswerDefault(final String answerDefault) {
        this.answerDefault = answerDefault;
    }
    
    
    

    
}
