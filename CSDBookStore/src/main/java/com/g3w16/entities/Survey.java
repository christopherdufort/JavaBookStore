/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author 1040570
 */
@Entity
@Table(name = "survey", catalog = "g3w16", schema = "")
@NamedQueries({
    @NamedQuery(name = "Survey.findAll", query = "SELECT s FROM Survey s"),
    @NamedQuery(name = "Survey.findBySurveyId", query = "SELECT s FROM Survey s WHERE s.surveyId = :surveyId"),
    @NamedQuery(name = "Survey.findByQuestion", query = "SELECT s FROM Survey s WHERE s.question = :question"),
    @NamedQuery(name = "Survey.findByAnswerOne", query = "SELECT s FROM Survey s WHERE s.answerOne = :answerOne"),
    @NamedQuery(name = "Survey.findByAnswerTwo", query = "SELECT s FROM Survey s WHERE s.answerTwo = :answerTwo"),
    @NamedQuery(name = "Survey.findByAnswerThree", query = "SELECT s FROM Survey s WHERE s.answerThree = :answerThree"),
    @NamedQuery(name = "Survey.findByAnswerDefault", query = "SELECT s FROM Survey s WHERE s.answerDefault = :answerDefault")})
public class Survey implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "survey_id")
    private Integer surveyId;
    @Basic(optional = false)
    @Column(name = "question")
    private String question;
    @Basic(optional = false)
    @Column(name = "answer_one")
    private String answerOne;
    @Basic(optional = false)
    @Column(name = "answer_two")
    private String answerTwo;
    @Basic(optional = false)
    @Column(name = "answer_three")
    private String answerThree;
    @Basic(optional = false)
    @Column(name = "answer_default")
    private String answerDefault;

    public Survey() {
    }

    public Survey(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public Survey(Integer surveyId, String question, String answerOne, String answerTwo, String answerThree, String answerDefault) {
        this.surveyId = surveyId;
        this.question = question;
        this.answerOne = answerOne;
        this.answerTwo = answerTwo;
        this.answerThree = answerThree;
        this.answerDefault = answerDefault;
    }

    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerOne() {
        return answerOne;
    }

    public void setAnswerOne(String answerOne) {
        this.answerOne = answerOne;
    }

    public String getAnswerTwo() {
        return answerTwo;
    }

    public void setAnswerTwo(String answerTwo) {
        this.answerTwo = answerTwo;
    }

    public String getAnswerThree() {
        return answerThree;
    }

    public void setAnswerThree(String answerThree) {
        this.answerThree = answerThree;
    }

    public String getAnswerDefault() {
        return answerDefault;
    }

    public void setAnswerDefault(String answerDefault) {
        this.answerDefault = answerDefault;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (surveyId != null ? surveyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Survey)) {
            return false;
        }
        Survey other = (Survey) object;
        if ((this.surveyId == null && other.surveyId != null) || (this.surveyId != null && !this.surveyId.equals(other.surveyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.g3w16.entities.Survey[ surveyId=" + surveyId + " ]";
    }
    
}
