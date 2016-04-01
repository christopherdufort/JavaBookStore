/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.validator;

import com.g3w16.beans.LocalizationBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
//@FacesValidator("com.g3w16.validator.EmailValidator") have to use named and not this to inject to be able to use bundles for appropriate language messages
public class EmailValidator implements Validator{
    
    @Inject
    LocalizationBean localizationBean;

    // from http://emailregex.com/ 
    //  following RFC 5322 Official Standard
    private static final String EMAIL_PATTERN = 
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private Pattern p;
    
    public EmailValidator(){
        p = Pattern.compile(EMAIL_PATTERN);
    }
    
    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        Logger.getLogger(EmailValidator.class.getName()).log(Level.INFO, "EmailValidator.validate is invoked !");
        Matcher m = p.matcher(o.toString());
        
        if (!m.matches()){
            String locale = localizationBean.getCurrentLanguage();
            FacesMessage message = com.g3w16.util.Messages.getMessage(
                    "com.g3w16.bundles.messages_"+locale, "badEmail", null);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
    
}
