/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.converter;

import com.g3w16.entities.Province;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author maxin
 */
@FacesConverter("com.g3w16.converter.ProvinceConverter")
public class ProvinceConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        switch (value) {
            case "Quebec":
                return new Province(1);
            case "Ontario":
                return new Province(2);
            case "New Brunswick":
                return new Province(3);
            case "Nova Scotia":
                return new Province(4);
            case "Prince Edward Island":
                return new Province(5);
            case "Newfoundland and Labrador":
                return new Province(6);
            case "Manitoba":
                return new Province(7);
            case "Saskatchewan":
                return new Province(8);
            case "Alberta":
                return new Province(9);
            case "British Columbia":
                return new Province(10);
            case "Yukon":
                return new Province(11);
            case "Northwest Territories":
                return new Province(12);
            case "Nunavut":
                return new Province(13);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }

}
