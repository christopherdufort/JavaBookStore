/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

import com.g3w16.entities.Book;
import com.g3w16.entities.viewController.SearchActionBean;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Christopher
 */
@Named
@SessionScoped
public class ResultsBackingBean implements Serializable {
    
    @Inject
    SearchBackingBean searchBackingBean;
    
    @Inject
    SearchActionBean searchActionBean;
    
    private String searchContent;
    private List<Book> searchResults;
    private String title;
    private String isbn;
    private String author;
    private String publishDate;
    private String salePrice;
    private String available;
    private String rating;
    private String count;
    
    private String ratingLimit;

    public ResultsBackingBean(){
    }
    
    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
    
    //@PostConstruct
    public void init(){
        searchResults = searchActionBean.getSearchResults();
        searchContent = searchBackingBean.getSearchContent();
        count = String.valueOf(searchResults.size());
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public List<Book> getSearchResults() {
        System.out.println("getSearchResults = " + searchResults);
        return searchResults;
    }

    public void setSearchResults(List<Book> searchResults) {
        this.searchResults = searchResults;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRatingLimit() {
        return ratingLimit;
    }

    public void setRatingLimit(String ratingLimit) {
        this.ratingLimit = ratingLimit;
    }
   
}
