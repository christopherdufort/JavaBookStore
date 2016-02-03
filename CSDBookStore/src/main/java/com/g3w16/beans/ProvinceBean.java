/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

/**
 *
 * @author Giuseppe Campanelli
 */
public class ProvinceBean {
    private String name;
    private double gst;
    private double pst;
    private double hst;
    
    public ProvinceBean() {
        super();
        this.name = "";
        this.gst = 0.0;
        this.pst = 0.0;
        this.hst = 0.0;
    }
    
    public ProvinceBean(String name, double gst, double pst, double hst) {
        super();
        this.name = name;
        this.gst = gst;
        this.pst = pst;
        this.hst = hst;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the gst
     */
    public double getGst() {
        return gst;
    }

    /**
     * @param gst the gst to set
     */
    public void setGst(double gst) {
        this.gst = gst;
    }

    /**
     * @return the pst
     */
    public double getPst() {
        return pst;
    }

    /**
     * @param pst the pst to set
     */
    public void setPst(double pst) {
        this.pst = pst;
    }

    /**
     * @return the hst
     */
    public double getHst() {
        return hst;
    }

    /**
     * @param hst the hst to set
     */
    public void setHst(double hst) {
        this.hst = hst;
    }
}
