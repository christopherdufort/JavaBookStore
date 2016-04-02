/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.actionController;

import com.g3w16.beans.AuthBean;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Christopher
 */
@Named
@RequestScoped
public class NavigationController implements Serializable {
    
    @Inject 
    AuthBean authBean;
    /**
     * @author Christopher Dufort
     * @return 
     */
    public String boomerangLogin(){
        String url = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        url = url.substring(1, url.length());
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "url is {0}", url);
        
        authBean.setEmail(null);
        authBean.setPassword(null);
        
        return "/login.xhtml?faces-redirect=true&backurl="+url;
    }
    
}
