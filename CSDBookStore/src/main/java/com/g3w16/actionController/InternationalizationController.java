/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author Christopher
 */
@Named
@SessionScoped
public class InternationalizationController implements Serializable {

    @Inject
    LocalizationBean localizationBean;

    public void checkLocaleCookie() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> cookieMap = context.getExternalContext().getRequestCookieMap();

        // Retrieve all cookies
        if (cookieMap == null || cookieMap.isEmpty()) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "No Cookies found or cookies not supported");
            setCookie("LocaleCookie", "en", 604800);
        } else {
            // Retrieve a specific cookie
            Object localeCookie = context.getExternalContext().getRequestCookieMap().get("LocaleCookie");
            if (localeCookie != null) {
                String languageValue = ((Cookie) localeCookie).getValue();
                System.out.println("The Cookie named " + ((Cookie) localeCookie).getName() + " has a value of " + languageValue);
                //writeLocaleCookie(languageValue);
                localizationBean.setCurrentLanguage(languageValue);
            }
        }

    }

    public void setCookie(String name, String value, int expiry) {

        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;

        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0) {
            for (int i = 0; i < userCookies.length; i++) {
                if (userCookies[i].getName().equals(name)) {
                    cookie = userCookies[i];
                    break;
                }
            }
        }

        if (cookie != null) {
            cookie.setValue(value);
        } else {
            cookie = new Cookie(name, value);
            cookie.setPath(request.getContextPath());
        }

        cookie.setMaxAge(expiry);

        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.addCookie(cookie);
    }
}
