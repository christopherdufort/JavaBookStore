/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Xin Ma
 * @author Rita Lazaar
 */

@Named("m_users")
@RequestScoped
public class m_usersBackingBean {

    @Inject
    RegisteredUser user;
    
    @Inject
    RegisteredUserJpaController userJpa;
    
    @Inject
    Title title;
    
    @Inject 
    TitleJpaController titleJpa;
    
    @Inject
    Province province;
    
    @Inject
    ProvinceJpaController provinceJpa;
    
    public RegisteredUser getSelectedUser() {
        if (user == null) {
            user = new RegisteredUser();
        }
        return user;
    }

    public String editUser(RegisteredUser u) {
        user = userJpa.findUserById(u.getUserId());
        return "m_editUser";
    }

    public String updateUser() {
        try {
            userJpa.edit(user);
        } catch (Exception ex) {
            Logger.getLogger(m_usersBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "m_users";
    }

    public List<RegisteredUser> getAllUsers() {
        return userJpa.findAll();

    }

    public int getUserCount() {
        return userJpa.getUsersCount();
    }

    public List<Title> getAllTitle() {
        return titleJpa.findAll();
    }
    
    public List<Province> getAllProvince(){
        return provinceJpa.findAll();
    }
}
