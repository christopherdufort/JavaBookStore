/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

/**
 *
 * @author Xin Ma
 * @author Rita Lazaar
 */
@ManagedBean(name = "m_titles")
@RequestScoped
public class m_titlesBackingBean implements Serializable {

    private Title title;

    private List<Title> allTitle;

    @Inject
    TitleJpaController titleJpa;

    @PostConstruct
    public void init() {
        allTitle = titleJpa.findAll();
    }

    public String preCreateTitle() {
        return "m_createTitle";
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

    public String createTitle() {
        try {
            titleJpa.create(title);
        } catch (Exception ex) {
            Logger.getLogger(m_titlesBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        allTitle = titleJpa.findAll();
        return "m_titles";
    }

    public void destroyTitle(Title t) {
        try {
            titleJpa.destroy(t.getTitleId());
        } catch (RollbackFailureException ex) {
            Logger.getLogger(m_titlesBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(m_titlesBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        allTitle = titleJpa.findAll();
    }

    public String cancel() {
        return "m_titles";
    }

    public List<Title> getAllTitle() {
        return allTitle;
    }

}
