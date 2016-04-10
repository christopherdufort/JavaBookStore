package com.g3w16.actionController;

import com.g3w16.beans.AuthBean;
import com.g3w16.beans.CartBackingBean;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * This controller is used for convenient navigation. All methods in this class
 * redirect to a modified url which can be read to return to where the user came
 * from.
 *
 * @author Christopher Dufort
 */
@Named
@RequestScoped
public class NavigationController implements Serializable {

    @Inject
    AuthBean authBean;
    @Inject
    CartBackingBean cartBB;

    /**
     * All login attempts should return to where the user was before logging in.
     *
     * @author Christopher Dufort
     * @return redirect with modified url.
     */
    public String boomerangLogin() {
        //Get the current page
        String url = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        url = url.substring(1, url.length());
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "url is {0}", url);

        //Reset the authorization (session scoped) this should use a navigation bean instead.
        authBean.setEmail(null);
        authBean.setPassword(null);

        //Redirect with modified url.
        return "/login.xhtml?faces-redirect=true&backurl=" + url;
    }

    /**
     * All visits to the page to modify the users profile should return to where
     * the user was after modifications.
     *
     * @author Christopher Dufort
     * @return redirect with modified url.
     */
    public String boomerangProfile() {
        //Get the current page
        String url = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        url = url.substring(1, url.length());
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "url is {0}", url);

        //Redirect with modified url.
        return "/profile.xhtml?faces-redirect=true&backurl=" + url;
    }

    public String navigateToCart() {
        String url = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        url = url.substring(1, url.length());

        cartBB.setRedirectTo(url);

        return "cart";
    }

}
