package com.g3w16.actionController;

import com.g3w16.beans.LocalizationBean;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is used to manage the cookies associated with the current locale.
 * A cookie is stored to keep track of the language preference of any visitor to the site.
 * @author Christopher Dufort
 */
@Named
@SessionScoped
public class InternationalizationController implements Serializable {

    @Inject
    LocalizationBean localizationBean;
    
    /**
     * This method retrieves the language from the cookie if it exists, otherwise is sets one.
     * Defaults to English.
     */
    public void checkLocaleCookie() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> cookieMap = context.getExternalContext().getRequestCookieMap();

        // Retrieve all cookies
        if (cookieMap == null || cookieMap.isEmpty()) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "No Cookies found or cookies not supported");
            setCookie("LocaleCookie", "en", 604800);
            localizationBean.setCurrentLanguage("en");
        } else {
            // Retrieve a specific cookie
            Object localeCookie = context.getExternalContext().getRequestCookieMap().get("LocaleCookie");
            if (localeCookie != null) {
                String languageValue = ((Cookie) localeCookie).getValue();
                System.out.println("The Cookie named " + ((Cookie) localeCookie).getName() + " has a value of " + languageValue);
                localizationBean.setCurrentLanguage(languageValue);
            }
        }

    }
    
    /**
     * Thos method is used to set a locale cookie if one does not exist.
     * @param name
     *          Name of the cookie to set.
     * @param value
     *          Content of the cookie to set.
     * @param expiry 
     *          Expiry in seconds of the cookie.
     */
    public void setCookie(String name, String value, int expiry) {

        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;

        //Used to check if the cookie already exists.
        //Loop through all cookies unti you find the specfic cookie
        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0) {
            for (int i = 0; i < userCookies.length; i++) {
                if (userCookies[i].getName().equals(name)) {
                    cookie = userCookies[i];
                    break;
                }
            }
        }
        //If it find the cookie, changes the value to the selected language.
        if (cookie != null) {
            cookie.setValue(value);
        } else {
            cookie = new Cookie(name, value);
            cookie.setPath(request.getContextPath());
        }

        //Renew the cookies length.
        cookie.setMaxAge(expiry);

        //Apply the cookie
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.addCookie(cookie);
    }
}
