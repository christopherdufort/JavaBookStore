/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.converter;

import com.g3w16.entities.Author;
import com.g3w16.entities.AuthorJpaController;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rita Lazaar
 */
@FacesConverter("com.g3w16.converter.AuthorConverter")
public class AuthorConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        AuthorJpaController authorJpa = CDI.current().select(AuthorJpaController.class).get();
        return authorJpa.findTitleByName(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }

}
