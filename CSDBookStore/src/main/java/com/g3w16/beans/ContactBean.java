package com.g3w16.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Bean to hold the contents of a contact form.
 * 
 * @author Giuseppe Campanelli
 */
@Named
@RequestScoped
public class ContactBean {
    private String name;
    private String email;
    private String message;

    /**
     * Gets the name
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email
     * 
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email
     * 
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the message
     * 
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message
     * 
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
