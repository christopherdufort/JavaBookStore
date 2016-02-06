package com.g3w16.beans;

import java.util.ArrayList;
import java.sql.Date;

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
    private int pages;
    private String path_img_small;
    private double wholesale_price;
    private double list_price;
    private double sale_price;
    private Date date_of_entrance;
    private Date publish_date;
    private double overall_rating;
    private boolean available;
    private String synopsis;
    
    private ArrayList<AuthorBean> authors;
    private ArrayList<GenreBean> genres;
    private ArrayList<FormatBean> formats;

    public double getOverall_rating() {
        return overall_rating;
    }

    public void setOverall_rating(double overall_rating) {
        this.overall_rating = overall_rating;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Date getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(Date publish_date) {
        this.publish_date = publish_date;
    }

    public BookBean(){
    }

    public BookBean(final Integer id, final String isbn, 
            final String title, final String publisher,
            final Date publish_date,
            final int pages, final double wholesale_price, 
            final double list_price, final double  sale_price, 
            final Date date_of_entrance, final boolean removal_status, 
            final double overall_rating ,final String synopsis) {
        super();
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.publish_date = publish_date;
        this.pages = pages;
        this.wholesale_price = wholesale_price;
        this.list_price = list_price;
        this.sale_price = sale_price;
        this.date_of_entrance = date_of_entrance;
        this.overall_rating = overall_rating;
        this.synopsis = synopsis;
        this.authors = new ArrayList<>();
        this.genres = new ArrayList<>();
        this.formats = new ArrayList<>();
    }

    public int getId() {
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

    public int getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getPath_img_small() {
        return path_img_small;
    }

    public void setPath_img_small(String path_img_small) {
        this.path_img_small = path_img_small;
    }

    public double getWholesale_price() {
        return wholesale_price;
    }

    public void setWholesale_price(Integer wholesale_price) {
        this.wholesale_price = wholesale_price;
    }

    public double getList_price() {
        return list_price;
    }

    public void setList_price(Integer list_price) {
        this.list_price = list_price;
    }

    public double getSale_price() {
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
    public ArrayList<AuthorBean> getAuthors() {
        return authors;
    }

    /**
     * There is no set when working with collections. When you get the ArrayList
     * you can add elements to it. A set method implies changing the current
     * ArrayList for another ArrayList and this is something we rarely do with
     * collections.
     * @return 
     */
    public ArrayList<GenreBean> getGenres() {
        return genres;
    }

    /**
     * There is no set when working with collections. When you get the ArrayList
     * you can add elements to it. A set method implies changing the current
     * ArrayList for another ArrayList and this is something we rarely do with
     * collections.
     * @return 
     */
    public ArrayList<FormatBean> getFormats() {
        return formats;
    }

}
