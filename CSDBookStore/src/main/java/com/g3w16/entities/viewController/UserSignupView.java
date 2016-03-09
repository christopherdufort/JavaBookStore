/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities.viewController;

import com.g3w16.actionController.UserController;
import com.g3w16.actionController.exception.AlreadyExistingUserException;
import com.g3w16.actionController.exception.PasswordConfirmationFailedException;
import com.g3w16.beans.SignupBean;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.RollbackException;

/**
 *
 * @author jesuisnuageux
 */
@Named
@RequestScoped
public class UserSignupView {
    @Inject
    UserController userController;
    
    @Inject
    SignupBean signupBean;
    
    public String signup(){
        Logger.getLogger(UserSignupView.class.getName()).log(Level.INFO, "Submitted email : {0}", signupBean.getEmail());
        Logger.getLogger(UserSignupView.class.getName()).log(Level.INFO, "Submitted password : {0}", signupBean.getPassword());
        Logger.getLogger(UserSignupView.class.getName()).log(Level.INFO, "Submitted confirm_password : {0}", signupBean.getConfirm_password());
        try {
            userController.create(signupBean);
        }catch(Exception ex){
            FacesContext.getCurrentInstance().addMessage("signup_form", new FacesMessage(ex.toString()));
            return null;
        }
        return "home"; // TODO: Remove that drity hardcoded string at one point !
    }
}
