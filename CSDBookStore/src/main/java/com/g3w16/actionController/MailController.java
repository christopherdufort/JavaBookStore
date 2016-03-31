/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.actionController;

import com.chrisdufort.mailaction.BasicSendAndReceive;
import com.g3w16.beans.AuthBean;
import com.g3w16.entities.RegisteredUser;
import com.g3w16.entities.RegisteredUserJpaController;
import com.g3w16.mail.beans.MailBean;
import com.g3w16.mail.beans.MailConfigBean;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Christopher
 */
@Named
@RequestScoped
public class MailController {
    
    @Inject
    AuthBean authBean;
    
    @Inject
    RegisteredUserJpaController registeredUserJpaController;
    
    private RegisteredUser recoveryUser;
    
    private BasicSendAndReceive basicSendAndReceive;
    
    public MailController(){
        super();
        basicSendAndReceive = new BasicSendAndReceive();
    }
    
    public String sendRecoveryEmail(){
        
        recoveryUser = registeredUserJpaController.findUserByEmail(authBean.getEmail());
        if (recoveryUser == null)
            return null;
        
        MailBean recoveryEmail = new MailBean();
        MailConfigBean recoveryConfig = new MailConfigBean();
            
        String from = recoveryConfig.getEmailAddress();
        
        String subject = "BiblioTech.com Recovery Email";
	String message = "Hello " + recoveryUser.getEmailAddress()+ " You asked for a recovery email, here is your temporary password: " + recoveryUser.getPassword();
         
        recoveryEmail.getToField().add(recoveryUser.getEmailAddress());
        recoveryEmail.setFromField(from);
        recoveryEmail.setSubjectField(subject);
        recoveryEmail.setTextMessageField(message);
        
        basicSendAndReceive.sendEmail(recoveryEmail, recoveryConfig);
        
        return "login";
    }
}
