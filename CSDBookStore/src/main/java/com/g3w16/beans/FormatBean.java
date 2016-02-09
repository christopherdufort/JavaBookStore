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
public class FormatBean {
    private Integer format_id;
    private String extension;

    public FormatBean() {
    }

    public FormatBean(final Integer id, final String extension) {
        this.extension = extension;
    }

    public Integer getFormat_id() {
        return format_id;
    }

    public void setFormat_id(Integer format_id) {
        this.format_id = format_id;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(final String extension) {
        this.extension = extension;
    }
    
    
}
