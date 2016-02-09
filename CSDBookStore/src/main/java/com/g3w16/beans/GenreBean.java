/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

/**
 *
 * @author jesuisnuageux
 * @author Christopher Dufort
 * @version 0.1.7 - last modified 2/07/2016
 */
public class GenreBean {
    private Integer genre_id;
    private String genre_name;

    public GenreBean() {
    }

    public GenreBean(final Integer id, final String name) {
        this.genre_name = name;
    }

    public String getGenre_name() {
        return genre_name;
    }

    public void setGenre_name(final String genre_name) {
        this.genre_name = genre_name;
    }

    public Integer getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(Integer genre_id) {
        this.genre_id = genre_id;
    }
    
}
