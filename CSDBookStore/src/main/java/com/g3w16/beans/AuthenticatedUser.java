/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

import com.g3w16.entities.Genre;
import com.g3w16.entities.RegisteredUser;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author jesuisnuageux
 */
@Named
@SessionScoped
public class AuthenticatedUser implements Serializable{
    
    RegisteredUser registeredUser;
    // last_genre is now a cookie, but I keep this here for now
    // TODO: remove it
    Genre last_genre;

    public RegisteredUser getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(RegisteredUser registeredUser) {
        this.registeredUser = registeredUser;
    }

    public Genre getLast_genre() {
        return last_genre;
    }

    public void setLast_genre(Genre last_genre) {
        this.last_genre = last_genre;
    }
    
    
}
