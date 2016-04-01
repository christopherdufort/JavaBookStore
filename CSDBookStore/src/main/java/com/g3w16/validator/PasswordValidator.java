/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.validator;

import com.g3w16.beans.LocalizationBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jesuisnuageux
 */
@Named
public class PasswordValidator implements Validator{
    
    @Inject
    LocalizationBean localizationBean;

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        Logger.getLogger(PasswordValidator.class.getName()).log(Level.INFO, "PasswordValidator.validate is invoked !");
        if(o.toString().length()<4){
            String locale = localizationBean.getCurrentLanguage();
            FacesMessage message = com.g3w16.util.Messages.getMessage(
                    "com.g3w16.bundles.messages_"+locale, "badPassword", null);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
    
}
