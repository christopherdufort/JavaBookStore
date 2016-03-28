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
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Christopher
 */
@Named
@SessionScoped
public class SearchActionBean implements Serializable {

    @Inject
    BookController bookController;

    @Inject
    SearchBackingBean searchBackingBean;
    
    private List<Book> searchResults;
    private String genreSearchContent;
    
    public void performSearch(SearchBackingBean searchBackingBean) throws IOException {
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
        FacesContext.getCurrentInstance().getExternalContext().redirect("results.xhtml");
    }
    //NEED TO FIGURE OUT HOW TO PASS THE GENRE HERE
    public void browseGenre(String genre) throws IOException{
        genreSearchContent = genre;
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
        FacesContext.getCurrentInstance().getExternalContext().redirect("browseGenreResults.xhtml");
    }
    
    public List<Book> getSearchResults(){
        return searchResults;
    }
    
    public String getGenreSerchContent(){
        return genreSearchContent;
    }

}
