/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author jesuisnuageux
 * @edited Christopher Dufort
 *
 * This bean was made sessionScoped in order to support return forwarding, after
 * a user logs in they are redirected back to where they came from.
 */
@Named
@SessionScoped
public class AuthBean implements Serializable {

    private String email;
    private String password;

    private String backurl;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @author Christopher Dufort
     * @return
     */
    public String getBackurl() {
        if (backurl != null) {
            boolean rejected = backurl.contains("//") || backurl.contains("%2F/") || backurl.contains("/%2F") || backurl.contains("%2F%2F");
            System.out.println("url is " + backurl);
            System.out.println("rejected is " + rejected);

            if (rejected) {
                backurl = "home.xhtml";
            }
        }
        return backurl;
    }

    /**
     * @author Christopher Dufort
     * @param backurl
     */
    public void setBackurl(String backurl) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "The page I came from was {0}", backurl);
        this.backurl = backurl;
    }
}
