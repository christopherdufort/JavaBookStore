/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Christopher
 */
@Named
@SessionScoped
public class SearchBackingBean implements Serializable {

    private String searchChoice;
    private List<String> availableChoices;
    private String searchContent;

    public SearchBackingBean() {
        super();
        availableChoices = new ArrayList<String>();
        availableChoices.add("Title");
        availableChoices.add("Isbn");
        availableChoices.add("Author");
        availableChoices.add("Publisher");
    }

    public String getSearchChoice() {
        return searchChoice;
    }

    public void setSearchChoice(String searchChoice) {
        this.searchChoice = searchChoice;
    }

    public List<String> getAvailableChoices() {
        return availableChoices;
    }

    public void setAvailableChoices(List<String> availableChoices) {
        this.availableChoices = availableChoices;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }
    
}
