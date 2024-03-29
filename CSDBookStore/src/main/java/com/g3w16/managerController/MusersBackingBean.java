/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import jodd.mail.Email;
import jodd.mail.MailException;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import jodd.mail.SmtpSslServer;

/**
 * This class is userBackingBean that will manage all the user page
 *
 * @author Xin Ma
 * @author Rita Lazaar
 */
@ManagedBean(name = "m_users")
@RequestScoped
public class MusersBackingBean implements Serializable {

    private RegisteredUser user;

    @Inject
    RegisteredUserJpaController userJpa;

    private Title title;

    private List<RegisteredUser> all;

    @Inject
    TitleJpaController titleJpa;

    @Inject
    ReviewJpaController reviewJpa;

    private Province province;

    @Inject
    ProvinceJpaController provinceJpa;

    @PostConstruct
    public void init() {
        all = userJpa.findAll();
    }

    public RegisteredUser getUser() {
        if (user == null) {
            user = new RegisteredUser();
        }
        return user;
    }

    public void setUser(RegisteredUser user) {
        this.user = user;
    }

    public Title getTitle() {
        if (title == null) {
            title = new Title();
        }
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Province getProvince() {
        if (province == null) {
            province = new Province();
        }
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public String editUser(RegisteredUser u) {
        user = userJpa.findUserById(u.getUserId());
        return "m_editUser";
    }

    public String viewUser(RegisteredUser u) {
        user = userJpa.findUserById(u.getUserId());
        return "m_viewUser";
    }

    /**
     * This method will edit the user
     *
     * @return
     */
    public String updateUser() {
        try {
            user.setReviewList(reviewJpa.findReviewByUserId(user));
            userJpa.edit(user);
        } catch (Exception ex) {
            Logger.getLogger(MusersBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(MusersBackingBean.class.getName()).log(Level.INFO, null, "Does it go here?");
        all = userJpa.findAll();
        return "m_users";
    }

    public List<RegisteredUser> getAllUsers() {
        return all;

    }

    public int getUserCount() {
        return userJpa.getUsersCount();
    }

    public List<Title> getAllTitle() {
        return titleJpa.findAll();
    }

    public List<Province> getAllProvince() {
        return provinceJpa.findAll();
    }

    /**
     * This method will send an email to notify user changed new password THIS
     * CHUNK OF CODE IS COMMENTED OUT BECAUSE: GLASSFISH CURRENTLY DOES NOT
     * ENABLE US TO SEND EMAILS.
     */
    public void sendEmail() {
        System.out.println(">>>>>send" + user.getEmailAddress());
        System.out.println(">>>>>>>>pass" + user.getPassword());

//        Email email = Email.create()
//                .from("xin.send@gmail.com")
//                .to("maxin911.ca@gmail.com")
//                .subject("password")
//                .addText("A plain text message...");
//
//        SmtpServer smtpServer = SmtpSslServer.create("smtp.gmail.com")
//                .authenticateWith("xin.send@gmail.com", "mx123456");
//
//        SendMailSession session = smtpServer.createSession();
//        session.open();
//        session.sendMail(email);
//        session.close();
    }

    public String cancel() {
        return "m_users";
    }
}
