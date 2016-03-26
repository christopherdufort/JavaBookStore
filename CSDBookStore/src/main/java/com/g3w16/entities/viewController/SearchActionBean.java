/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities.viewController;

import com.g3w16.actionController.BookController;
import com.g3w16.beans.SearchBackingBean;
import com.g3w16.entities.Book;
import com.g3w16.entities.Genre;
import java.util.List;
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
    
    private List<Book> searchResults;
    
    public String performSearch(SearchBackingBean searchBackingBean) {
        switch (searchBackingBean.getSearchChoice()) {
            case "Title":
                searchResults = bookController.searchByTitle(searchBackingBean.getSearchContent());
                System.out.println("SearchResults found = " + searchResults.size());
                break;
            case "Isbn":
                searchResults = bookController.searchByIsbn(searchBackingBean.getSearchContent());
                System.out.println("SearchResults found = " + searchResults.size());
                break;
            case "Author":
                searchResults = bookController.searchByAuthor(searchBackingBean.getSearchContent());
                System.out.println("SearchResults found = " + searchResults.size());
                break;
            case "Publisher":
                searchResults = bookController.searchByPublisher(searchBackingBean.getSearchContent());
                System.out.println("SearchResults found = " + searchResults.size());
                break;
            default:     //need a default     
        }
        return "results";
    }
    //NEED TO FIGURE OUT HOW TO PASS THE GENRE HERE
    public String browseGenre(String genre){
        switch (genre){
            case "Computers & Technology":
                searchResults = bookController.browseByGenre(new Genre(1));
                break;
            case "Travel":
                searchResults = bookController.browseByGenre(new Genre(2));
                break;                
            case "Comics & Graphic Novels":
                searchResults = bookController.browseByGenre(new Genre(3));
                break;
            case "Religion & Spirituality":
                searchResults = bookController.browseByGenre(new Genre(4));
                break;
            case "Mystery, Thriller & Suspense":
                searchResults = bookController.browseByGenre(new Genre(5));
                break;  
            default:     //need a default  
        }
        return "results";
    }
    
    public List<Book> getSearchResults(){
        return searchResults;
    }

}