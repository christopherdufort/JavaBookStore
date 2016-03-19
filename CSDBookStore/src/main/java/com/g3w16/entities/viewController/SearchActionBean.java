/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities.viewController;

import com.g3w16.actionController.BookController;
import com.g3w16.beans.SearchBackingBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Christopher
 */
@Named
@RequestScoped
public class SearchActionBean {

    @Inject
    BookController bookController;

    @Inject
    SearchBackingBean searchBackingBean;
    

    public String performSearch(SearchBackingBean searchBackingBean) {
        switch (searchBackingBean.getSearchContent()) {
            case "Title":
                bookController.searchByTitle(searchBackingBean.getSearchChoice());
                break;
            case "Isbn":
                bookController.searchByIsbn(searchBackingBean.getSearchChoice());
                break;
            case "Author":
                bookController.searchByAuthor(searchBackingBean.getSearchChoice());
                break;
            case "Publisher":
                bookController.searchByPublisher(searchBackingBean.getSearchChoice());
                break;
            default:          
        }
        return "results";
    }

}
