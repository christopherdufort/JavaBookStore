/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

import com.g3w16.entities.Genre;
import com.g3w16.entities.GenreJpaController;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Christopher
 */
@Named
@RequestScoped
public class BrowseGenreBackingBean {
    
    @Inject
    private GenreJpaController genreJpaController;
    
    private List<Genre> availableGenres;
 
    public BrowseGenreBackingBean(){
        super();
    }
    
    @PostConstruct
    public void init(){
        this.availableGenres = genreJpaController.findGenreEntitiesAsClient();
    }

    public List<Genre> getAvailableGenres() {
        return availableGenres;
    }

    public void setAvailableGenres(List<Genre> availableGenres) {
        this.availableGenres = availableGenres;
    }
    
}
