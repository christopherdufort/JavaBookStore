/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.converter;

import com.g3w16.entities.Approval;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author maxin
 */
@FacesConverter("com.g3w16.converter.ApprovalConverter")
public class ApprovalConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>value:    "+value);
         switch (value) {
            case "Approved":
                 System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>1");
                return new Approval(1);
            case "Pending":
                 System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>2");
                return new Approval(2);
            case "Denied":
                 System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>3");
                return new Approval(3);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
       return value.toString();
    }
    
}
