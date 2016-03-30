/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.converter;

import com.g3w16.entities.AuthorJpaController;
import com.g3w16.entities.BookJpaController;
import com.g3w16.entities.FormatJpaController;
import com.g3w16.entities.GenreJpaController;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rita Lazaar
 */
@FacesConverter("com.g3w16.converter.GenreConverter")
public class GenreConversion implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
       GenreJpaController genreJpa = CDI.current().select(GenreJpaController.class).get();
        return genreJpa.findGenre(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }

}
