/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author jesuisnuageux
 */
@Entity
@Table(name = "survey_answer")
@NamedQueries({
    @NamedQuery(name = "SurveyAnswer.findAll", query = "SELECT s FROM SurveyAnswer s"),
    @NamedQuery(name = "SurveyAnswer.findBySurveyId", query = "SELECT s FROM SurveyAnswer s WHERE s.surveyAnswerPK.surveyId = :surveyId"),
    @NamedQuery(name = "SurveyAnswer.findBySessionId", query = "SELECT s FROM SurveyAnswer s WHERE s.surveyAnswerPK.sessionId = :sessionId"),
    @NamedQuery(name = "SurveyAnswer.findByChoice", query = "SELECT s FROM SurveyAnswer s WHERE s.choice = :choice")})
public class SurveyAnswer implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SurveyAnswerPK surveyAnswerPK;
    @Column(name = "choice")
    private Integer choice;
    @JoinColumn(name = "survey_id", referencedColumnName = "survey_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Survey survey;

    public SurveyAnswer() {
    }

    public SurveyAnswer(SurveyAnswerPK surveyAnswerPK) {
        this.surveyAnswerPK = surveyAnswerPK;
    }

    public SurveyAnswer(int surveyId, String sessionId) {
        this.surveyAnswerPK = new SurveyAnswerPK(surveyId, sessionId);
    }

    public SurveyAnswerPK getSurveyAnswerPK() {
        return surveyAnswerPK;
    }

    public void setSurveyAnswerPK(SurveyAnswerPK surveyAnswerPK) {
        this.surveyAnswerPK = surveyAnswerPK;
    }

    public Integer getChoice() {
        return choice;
    }

    public void setChoice(Integer choice) {
        this.choice = choice;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (surveyAnswerPK != null ? surveyAnswerPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SurveyAnswer)) {
            return false;
        }
        SurveyAnswer other = (SurveyAnswer) object;
        if ((this.surveyAnswerPK == null && other.surveyAnswerPK != null) || (this.surveyAnswerPK != null && !this.surveyAnswerPK.equals(other.surveyAnswerPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.g3w16.entities.SurveyAnswer[ surveyAnswerPK=" + surveyAnswerPK + " ]";
    }
    
}
