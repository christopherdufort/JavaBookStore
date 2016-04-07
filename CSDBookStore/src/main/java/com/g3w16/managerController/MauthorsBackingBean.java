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
@ManagedBean(name = "m_authors")
@RequestScoped
public class MauthorsBackingBean implements Serializable {

    private Author author;

    private List<Author> allAuthor;

    @Inject
    AuthorJpaController authorJpa;

    @PostConstruct
    public void init() {
        allAuthor = authorJpa.findAuthorEntities();
    }

    public String preCreateAuthor() {
        return "m_createAuthor";
    }

    public Author getAuthor() {
        if (author == null) {
            author = new Author();
        }
        return author;
    }

    public void setAuthor(Author a) {
        this.author = a;
    }

    public String createAuthor() {
        try {
            authorJpa.create(author);
        } catch (Exception ex) {
            Logger.getLogger(MauthorsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        allAuthor = authorJpa.findAuthorEntities();
        return "m_authors";
    }

    public void destroyAuthor(Author a) {
        try {
            authorJpa.destroy(a.getAuthorId());
        } catch (RollbackFailureException ex) {
            Logger.getLogger(MauthorsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MauthorsBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        allAuthor = authorJpa.findAuthorEntities();
    }

    public String cancel() {
        return "m_authors";
    }

    public List<Author> getAllAuthor() {
        return allAuthor;
    }
 
}
