/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities.viewController;

import com.g3w16.entities.*;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Xin Ma
 */
@Named
@RequestScoped
public class ManagerView {
    @Inject
    BookJpaController bookJpaController;
    
    @Inject
    RegisteredUserJpaController registeredUserJpaController;
    
    @Inject
    ReviewJpaController reviewJpaController;
    
    @Inject
    Book book;
    
    @Inject 
    RegisteredUser registeredUser;
    
    @Inject
    Review review;
    
    /**
     * 
     * Review table methods.
     */
    public List<Review> getAllReview(){
        return reviewJpaController.findReviewEntities();
    }
    
    /**
     * Book table methods
     */
    public List<Book> getAllBook(){
        return bookJpaController.findBookEntities();
    }
}
