package com.g3w16.actionController;

import com.chrisdufort.mailaction.BasicSendAndReceive;
import com.g3w16.beans.AuthBean;
import com.g3w16.beans.ContactBean;
import com.g3w16.entities.RegisteredUser;
import com.g3w16.entities.RegisteredUserJpaController;
import com.g3w16.mail.beans.MailBean;
import com.g3w16.mail.beans.MailConfigBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This controller is the layer of abstraction between the view and model
 * layers. This Controller is responsible for calling all the back end mail
 * classes/methods.
 *
 * @author Christopher Dufort
 * @author Giuseppe Campanelli
 */
@Named
@RequestScoped
public class MailController {

    @Inject
    AuthBean authBean;
    @Inject
    ContactBean contactBean;

    @Inject
    RegisteredUserJpaController registeredUserJpaController;

    private RegisteredUser recoveryUser;

    private BasicSendAndReceive basicSendAndReceive;

    public MailController() {
        super();
        basicSendAndReceive = new BasicSendAndReceive();
    }

    /**
     * This method is used to send a recovery email if the user forgot their
     * password. Does nothing if the user does not exist in db.
     *
     * @author Christopher Dufort
     * @return retirects to login.
     */
    public String sendRecoveryEmail() {

        recoveryUser = registeredUserJpaController.findUserByEmail(authBean.getEmail());
        if (recoveryUser == null) {
            return null;
        }

        MailBean recoveryEmail = new MailBean();
        MailConfigBean recoveryConfig = new MailConfigBean();

        String from = recoveryConfig.getEmailAddress();

        String subject = "BiblioTech.com Recovery Email";
        String message = "Hello " + recoveryUser.getEmailAddress() + " You asked for a recovery email, here is your temporary password: " + recoveryUser.getPassword();

        recoveryEmail.getToField().add(recoveryUser.getEmailAddress());
        recoveryEmail.setFromField(from);
        recoveryEmail.setSubjectField(subject);
        recoveryEmail.setTextMessageField(message);

        basicSendAndReceive.sendEmail(recoveryEmail, recoveryConfig);

        return "login";
    }

    /**
     * Send the contact email from the contact page.
     *
     * @return redirect to home page
     * @author Giuseppe Campanelli
     */
    public String sendContactEmail() {

        MailBean recoveryEmail = new MailBean();
        MailConfigBean recoveryConfig = new MailConfigBean();

        String from = recoveryConfig.getEmailAddress();

        String subject = contactBean.getName() + " - " + contactBean.getEmail();
        String message = contactBean.getMessage();

        recoveryEmail.getToField().add("bibliotech.receive@gmail.com");
        recoveryEmail.setFromField(from);
        recoveryEmail.setSubjectField(subject);
        recoveryEmail.setTextMessageField(message);

        basicSendAndReceive.sendEmail(recoveryEmail, recoveryConfig);

        return "home";
    }

    /**
     * private method called by others in order to send an email based on the
     * paramaters.
     *
     * @author Christopher Dufort
     * @param to Target valid email to send the email to.
     * @param subject Subject of the email to send.
     * @param message Message of the email to send.
     */
    public void sendEmail(String to, String subject, String message) {

        MailBean recoveryEmail = new MailBean();
        MailConfigBean recoveryConfig = new MailConfigBean();

        String from = recoveryConfig.getEmailAddress();

        recoveryEmail.getToField().add(to);
        recoveryEmail.setFromField(from);
        recoveryEmail.setSubjectField(subject);
        recoveryEmail.setTextMessageField(message);

        basicSendAndReceive.sendEmail(recoveryEmail, recoveryConfig);
    }
}
