/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

/**
 *
 * @author jesuisnuageux
 */
public class FormatBean {
    private Integer id;
    private String extension;

    public FormatBean() {
    }

    public FormatBean(final Integer id, final String extension) {
        this.extension = extension;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(final String extension) {
        this.extension = extension;
    }
    
    
}
