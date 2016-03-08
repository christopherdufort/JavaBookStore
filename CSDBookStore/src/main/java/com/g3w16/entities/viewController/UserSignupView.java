/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities.viewController;

import com.g3w16.actionController.UserController;
import com.g3w16.beans.SignupBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jesuisnuageux
 */
@Named
@RequestScoped
public class UserSignupView {
    @Inject
    UserController userController;
    
    public String signin(SignupBean signinBean){
        Logger.getLogger(UserSignupView.class.getName()).log(Level.INFO, "Submitted email : {0}", signinBean.getEmail());
        Logger.getLogger(UserSignupView.class.getName()).log(Level.INFO, "Submitted password : {0}", signinBean.getPassword());
        Logger.getLogger(UserSignupView.class.getName()).log(Level.INFO, "Submitted confirm_password : {0}", signinBean.getConfirm_password());
        
        try {
            userController.create(signinBean);
        } catch (Exception ex) {
            Logger.getLogger(UserSignupView.class.getName()).log(Level.SEVERE, "Something went REAL REAL REAL wrong : {0}", ex.toString());
        }
        
        return "home"; // TODO: Remove that drity hardcoded string at one point !
    }
}
