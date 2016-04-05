/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.converter;

import com.g3w16.entities.RegisteredUserJpaController;
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
@FacesConverter("com.g3w16.converter.UserConverter")
public class UserConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        RegisteredUserJpaController userJpa=CDI.current().select(RegisteredUserJpaController.class).get();
        return userJpa.findUserByEmail(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }
    
}
