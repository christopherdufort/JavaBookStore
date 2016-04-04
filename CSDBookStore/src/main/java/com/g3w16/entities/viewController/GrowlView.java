/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities.viewController;

import com.g3w16.beans.LocalizationBean;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
 
@Named
public class GrowlView implements Serializable {
    
    @Inject
    LocalizationBean localizationBean;
     
    public void notifyUserAboutReview() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        String locale = localizationBean.getCurrentLanguage();
         
        context.addMessage("growl",com.g3w16.util.Messages.getMessage("com.g3w16.bundles.messages_"+locale, "notifyUserAboutReview", null));
    }
}