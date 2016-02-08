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
 */
public class AuthorBean {
    private Integer author_id;
    private String author_name;

    public AuthorBean(){}
    
    public AuthorBean(final Integer id, final String name) {
        this.author_name = name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(final String author_name) {
        this.author_name = author_name;
    }

    public Integer getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }
    
}
