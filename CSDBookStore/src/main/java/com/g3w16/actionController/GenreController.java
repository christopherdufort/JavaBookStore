/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.actionController;

import com.g3w16.entities.Genre;
import com.g3w16.entities.GenreJpaController;
import javax.inject.Inject;

/**
 *
 * @author jesuisnuageux
 */
public class GenreController {

    @Inject
    GenreJpaController genreJpacontroller;
    
    public Genre getById(int genreId) {
        return genreJpacontroller.findGenre(genreId);
    }
    
}
