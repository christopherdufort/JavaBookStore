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
 * This class is the backing bean used to handle all the genres in the management side. 
 * @author Xin Ma
 * @author Rita Lazaar
 */
@ManagedBean(name = "m_genres")
@RequestScoped
public class MgenresBackingBean implements Serializable {

    private Genre genre;

    private List<Genre> allGenre;

    @Inject
    GenreJpaController genreJpa;

    @PostConstruct
    public void init() {
        allGenre = genreJpa.findGenreEntities();
    }

    public String preCreateGenre() {
        return "m_createGenre";
    }

    public Genre getGenre() {
        if (genre == null) {
            genre = new Genre();
        }
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String createGenre() {
        try {
            genreJpa.create(genre);
        } catch (Exception ex) {
            Logger.getLogger(MgenresBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        allGenre = genreJpa.findGenreEntities();
        return "m_genres";
    }

    public void destroyGenre(Genre g) {
        try {
            genreJpa.destroy(g.getGenreId());
        } catch (RollbackFailureException ex) {
            Logger.getLogger(MgenresBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MgenresBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        allGenre = genreJpa.findGenreEntities();
    }

    public String cancel() {
        return "m_genres";
    }

    public List<Genre> getAllGenre() {
        return allGenre;
    }
}
