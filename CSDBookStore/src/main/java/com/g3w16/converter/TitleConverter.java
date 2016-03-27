/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.converter;


import com.g3w16.entities.Title;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


/**
 *
 * @author maxin
 */
@FacesConverter("com.g3w16.converter.TitleConverter")
public class TitleConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {  
        switch (value) {
            case "Mr.":
                return new Title(1);
            case "Ms.":
                return new Title(2);
            case "Mrs.":
                return new Title(3);
            case "Miss.":
                return new Title(4);
            case "Dr.":
                return new Title(5);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }

}
