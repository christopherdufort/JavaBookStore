package com.g3w16.validator;

import com.g3w16.beans.LocalizationBean;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Joey
 */
@Named
public class CreditCardValidator implements Validator {
    
    @Inject
    LocalizationBean localizationBean;

    @Override
    public void validate(FacesContext context, UIComponent component,
            Object value) {
        if (value == null) {
            return;
        }
        String cardNumber = value.toString();
        
        String locale = localizationBean.getCurrentLanguage();
        
        if (!luhnCheck(cardNumber) || cardNumber.length() < 13 || cardNumber.length() > 19) {
            FacesMessage message = com.g3w16.util.Messages.getMessage(
                    "com.g3w16.bundles.messages_"+locale, "badLuhnCheck", null);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }

    private static boolean luhnCheck(String cardNumber) {
        int sum = 0;

        for (int i = cardNumber.length() - 1; i >= 0; i -= 2) {
            sum += Integer.parseInt(cardNumber.substring(i, i + 1));
            if (i > 0) {
                int d = 2 * Integer.parseInt(cardNumber.substring(i - 1, i));
                if (d > 9) {
                    d -= 9;
                }
                sum += d;
            }
        }

        return sum % 10 == 0;
    }
}
