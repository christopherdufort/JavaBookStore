/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author jesuisnuageux
 */
@FacesValidator("com.g3w16.validator.ConfirmPasswordValidator")
public class ConfirmPasswordValidator implements Validator{

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        String password =(String) ((UIInput)uic.findComponent("passwordInput")).getLocalValue();
        if (!password.equals(o.toString())){
            FacesMessage msg = new FacesMessage(
                    "PasswordConfirmation doesn't match original password"
            );
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
    
}
