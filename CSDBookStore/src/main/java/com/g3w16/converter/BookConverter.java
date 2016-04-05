/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.converter;

import com.g3w16.entities.BookJpaController;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
/**
 *
 * @author Xin Ma
 * @author Rita Lazaar
 */
@FacesConverter("com.g3w16.converter.BookConverter")
public class BookConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        BookJpaController bookJpa = CDI.current().select(BookJpaController.class).get();
        return bookJpa.findBookByISBN(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }

}
