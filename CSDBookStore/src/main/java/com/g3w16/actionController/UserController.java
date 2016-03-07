/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.actionController;

import com.g3w16.actionController.exception.InvalidCredentialsException;
import com.g3w16.beans.AuthBean;
import com.g3w16.beans.SigninBean;
import com.g3w16.entities.RegisteredUser;
import com.g3w16.entities.RegisteredUserJpaController;
import javax.inject.Inject;

/**
 *
 * @author jesuisnuageux
 */
public class UserController {
    
    @Inject
    RegisteredUserJpaController registeredUserJpaController;
    
    public RegisteredUser create(SigninBean signinBean) throws Exception{
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setEmailAddress(signinBean.getEmail());
        registeredUser.setPassword(signinBean.getPassword());
        registeredUserJpaController.create(registeredUser);
        return registeredUser;
    }
    
    public RegisteredUser authenticate(AuthBean authBean) throws InvalidCredentialsException{
        RegisteredUser registeredUser = registeredUserJpaController.findUserByEmail(authBean.getEmail());
        if (registeredUser.getPassword().equals(authBean.getPassword())){
            throw new InvalidCredentialsException();
        }
        return registeredUser;
    }
}
