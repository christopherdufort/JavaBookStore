package com.g3w16.beans;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Jonas Faure
 * @author Christopher Dufort
 * @version 0.0.4 - Last modified 2/2/2016
 * @since 0.0.1 - Originally written 25/01/2016.
 */
public class BookBean {
    private Integer id;
    private String isbn;
    private String title;
    private String publisher;
    private Integer pages;
    private String path_img_original;
    private String path_img_small;
    private Integer wholesale_price;
    private Integer list_price;
    private Integer sale_price;
    private Date date_of_entrance;
    private boolean removal_status;
    private String synopsis;

    private ArrayList<String> authors;
    private ArrayList<String> genres;
    private ArrayList<String> formats;

    public BookBean(){
    }

    public BookBean(final Integer id, final String isbn, final String title, final String publisher, final Integer pages, final String path_img_original, final String path_img_small, final Integer wholesale_price, final Integer list_price, final Integer sale_price, final Date date_of_entrance, final boolean removal_status, final String synopsis) {
        super();
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.pages = pages;
        this.path_img_original = path_img_original;
        this.path_img_small = path_img_small;
        this.wholesale_price = wholesale_price;
        this.list_price = list_price;
        this.sale_price = sale_price;
        this.date_of_entrance = date_of_entrance;
        this.removal_status = removal_status;
        this.synopsis = synopsis;
        this.authors = new ArrayList<String>();
        this.genres = new ArrayList<String>();
        this.formats = new ArrayList<String>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getPath_img_original() {
        return path_img_original;
    }

    public void setPath_img_original(String path_img_original) {
        this.path_img_original = path_img_original;
    }

    public String getPath_img_small() {
        return path_img_small;
    }

    public void setPath_img_small(String path_img_small) {
        this.path_img_small = path_img_small;
    }

    public Integer getWholesale_price() {
        return wholesale_price;
    }

    public void setWholesale_price(Integer wholesale_price) {
        this.wholesale_price = wholesale_price;
    }

    public Integer getList_price() {
        return list_price;
    }

    public void setList_price(Integer list_price) {
        this.list_price = list_price;
    }

    public Integer getSale_price() {
        return sale_price;
    }

    public void setSale_price(Integer sale_price) {
        this.sale_price = sale_price;
    }

    public Date getDate_of_entrance() {
        return date_of_entrance;
    }

    public void setDate_of_entrance(Date date_of_entrance) {
        this.date_of_entrance = date_of_entrance;
    }

    public boolean isRemoval_status() {
        return removal_status;
    }

    public void setRemoval_status(boolean removal_status) {
        this.removal_status = removal_status;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
    
    /**
     * There is no set when working with collections. When you get the ArrayList
     * you can add elements to it. A set method implies changing the current
     * ArrayList for another ArrayList and this is something we rarely do with
     * collections.
     * @return 
     */
    public ArrayList<String> getAuthors() {
        return authors;
    }

    /**
     * There is no set when working with collections. When you get the ArrayList
     * you can add elements to it. A set method implies changing the current
     * ArrayList for another ArrayList and this is something we rarely do with
     * collections.
     * @return 
     */
    public ArrayList<String> getGenres() {
        return genres;
    }

    /**
     * There is no set when working with collections. When you get the ArrayList
     * you can add elements to it. A set method implies changing the current
     * ArrayList for another ArrayList and this is something we rarely do with
     * collections.
     * @return 
     */
    public ArrayList<String> getFormats() {
        return formats;
    }

}
