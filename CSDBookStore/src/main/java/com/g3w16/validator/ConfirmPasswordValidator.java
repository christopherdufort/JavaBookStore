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
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
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
public class ConfirmPasswordValidator implements Validator{
    
    @Inject
    LocalizationBean localizationBean;

    @Override
    public void validate(FacesContext fc, UIComponent component, Object o) throws ValidatorException {
        UIInput passwordInput = (UIInput) component.findComponent("passwordInput");
        String password = (String) passwordInput.getLocalValue();
        password = password==null ? "" : password;
        if (!password.equals(o.toString())){
            String locale = localizationBean.getCurrentLanguage();
            FacesMessage message = com.g3w16.util.Messages.getMessage(
                    "com.g3w16.bundles.messages_"+locale, "noPasswordMatch", null);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
    
}
