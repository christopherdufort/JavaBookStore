
package com.g3w16.managerController;

import com.g3w16.entities.Approval;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

/**
 *
 * @author 1232046
 */

@FacesConverter("com.g3w16.managerController.ApprovalConverter")
public class ApprovalConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        
        switch (value) {
            case "Approved":               
                return new Approval(1);

            case "Pending":             
                return new Approval(2);
                
            case "Denied":
                return new Approval(3);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }
    
}
