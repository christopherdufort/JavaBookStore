package com.g3w16.entities.viewController;

import com.g3w16.actionController.UserController;
import com.g3w16.beans.ProfileBackingBean;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Action Bean used as redirect and layer of abstraction between entities and
 * backing beans.
 *
 * @author Christopher Dufort
 */
@Named
@SessionScoped
public class ProfileActionBean implements Serializable {

    @Inject
    private UserController userController;

    @Inject
    private ProfileBackingBean profileBackingBean;

    /**
     * Test method
     */
    public ProfileActionBean() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "ProfileActionBean was constructed via CDI");
    }

    /**
     * Persist the profile bean.
     *
     * @return
     */
    public String editProfile() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "EditProfile in profile action bean is invoked!");
        try {
            userController.editProfile();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage("profileForm", new FacesMessage(ex.toString()));
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Exception Occured in ProfileActionBean");
            return null; //Stay on page
        }

        return profileBackingBean.getRedirectTo();
    }
}
