/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jesuisnuageux
 */
@Embeddable
public class SurveyAnswerPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "survey_id")
    private int surveyId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "session_id")
    private String sessionId;

    public SurveyAnswerPK() {
    }

    public SurveyAnswerPK(int surveyId, String sessionId) {
        this.surveyId = surveyId;
        this.sessionId = sessionId;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + this.surveyId;
        hash = 23 * hash + Objects.hashCode(this.sessionId);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SurveyAnswerPK)) {
            return false;
        }
        SurveyAnswerPK other = (SurveyAnswerPK) object;
        if (this.surveyId != other.surveyId) {
            return false;
        }
        if (this.sessionId.equals(other.sessionId)){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.g3w16.entities.SurveyAnswerPK[ surveyId=" + surveyId + ", sessionId=" + sessionId + " ]";
    }
    
}
