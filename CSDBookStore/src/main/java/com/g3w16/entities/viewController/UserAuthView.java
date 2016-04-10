package com.g3w16.entities.viewController;

import com.g3w16.actionController.UserController;
import com.g3w16.actionController.exception.InvalidCredentialsException;
import com.g3w16.beans.AuthBean;
import com.g3w16.beans.AuthenticatedUser;
import com.g3w16.entities.RegisteredUser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jesuisnuageux
 * @edited Christopher Dufort
 */
@Named
@RequestScoped
public class UserAuthView {
    
    @Inject
    private AuthenticatedUser authenticatedUser;

    @Inject
    private UserController userController;

    @Inject
    private AuthBean authBean;

    
    /**
     * 
     * @throws IOException 
     */
    public void authenticate() throws IOException {
        
        Logger.getLogger(UserAuthView.class.getName()).log(Level.INFO, "Submitted email : {0}", authBean.getEmail());
        Logger.getLogger(UserAuthView.class.getName()).log(Level.INFO, "Submitted password : {0}", authBean.getPassword());
        Logger.getLogger(UserAuthView.class.getName()).log(Level.INFO, "Returning to : {0}", authBean.getBackurl());
        
        RegisteredUser registeredUser;
        try {
            registeredUser = userController.authenticate(authBean);
            // return home will redirect to the home page if authentication is successful
            //   else, we re-render the page with an h:message thing
            authenticatedUser.setRegisteredUser(registeredUser);
            if (userController.isManager(registeredUser)){
                Logger.getLogger(UserAuthView.class.getName()).log(Level.INFO, "Redirecting to manager index");
                FacesContext.getCurrentInstance().getExternalContext().redirect("m_index.xhtml");
            }else{
                Logger.getLogger(UserAuthView.class.getName()).log(Level.INFO, "Redirecting to where ever I came from");
                //FacesContext.getCurrentInstance().getExternalContext().redirect("home.xhtml");
                FacesContext.getCurrentInstance().getExternalContext().redirect(authBean.getBackurl());
            }
            }catch (InvalidCredentialsException ex) {
            FacesContext.getCurrentInstance().addMessage("auth_form", new FacesMessage(ex.toString()));
        }
    }
    
}
