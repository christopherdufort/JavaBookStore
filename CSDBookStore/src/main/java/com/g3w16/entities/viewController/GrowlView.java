package com.g3w16.entities.viewController;

import com.g3w16.beans.LocalizationBean;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
 
/**
 * Displays a growl.
 * 
 * @author Giuseppe Campanelli
 */
@Named
public class GrowlView implements Serializable {
    
    @Inject
    LocalizationBean localizationBean;
     
    /**
     * Displays a growl.
     */
    public void notifyUserAboutReview() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        String locale = localizationBean.getCurrentLanguage();
         
        context.addMessage("growl",com.g3w16.util.Messages.getMessage("com.g3w16.bundles.messages_"+locale, "notifyUserAboutReview", null));
    }
}