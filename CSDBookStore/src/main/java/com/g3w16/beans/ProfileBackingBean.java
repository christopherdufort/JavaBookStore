/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.beans;

import com.g3w16.actionController.UserController;
import com.g3w16.entities.Province;
import com.g3w16.entities.ProvinceJpaController;
import com.g3w16.entities.Title;
import com.g3w16.entities.TitleJpaController;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * backing bean for profile.xhtml
 *
 * @author Christopher Dufort
 */
@Named
@SessionScoped
public class ProfileBackingBean implements Serializable {

    @Inject
    private UserController userController;

    @Inject
    private AuthenticatedUser authenticatedUser;

    //Find more abstract solution?
    @Inject
    private TitleJpaController titleJpaController;

    @Inject
    private ProvinceJpaController provinceJpaController;

    //Find more abstract solution?
    private List<Title> availableTitles;
    private List<Province> availableProvinces;

    private String emailAddress;
    private String password;
    private String firstName;
    private String lastName;
    private String companyName;
    private String addressOne;
    private String addressTwo;
    private String city;
    private String country;
    private String postalCode;
    private String homePhone;
    private String cellPhone;
    private Title titleId;
    private Province provinceId;

    public ProfileBackingBean() {
        super();
    }

    //@PostConstruct
    public void init() {
        this.emailAddress = authenticatedUser.getRegisteredUser().getEmailAddress();
        this.password = authenticatedUser.getRegisteredUser().getPassword();
        this.firstName = authenticatedUser.getRegisteredUser().getFirstName();
        this.lastName = authenticatedUser.getRegisteredUser().getLastName();
        this.companyName = authenticatedUser.getRegisteredUser().getCompanyName();
        this.addressOne = authenticatedUser.getRegisteredUser().getAddressOne();
        this.addressTwo = authenticatedUser.getRegisteredUser().getAddressTwo();
        this.city = authenticatedUser.getRegisteredUser().getCity();
        this.country = authenticatedUser.getRegisteredUser().getCountry();
        this.postalCode = authenticatedUser.getRegisteredUser().getPostalCode();
        this.homePhone = authenticatedUser.getRegisteredUser().getHomePhone();
        this.cellPhone = authenticatedUser.getRegisteredUser().getCellPhone();
        this.titleId = authenticatedUser.getRegisteredUser().getTitleId();
        this.provinceId = authenticatedUser.getRegisteredUser().getProvinceId();

        //Find more abstract solution?
        availableTitles = titleJpaController.findAll();
        availableProvinces = provinceJpaController.findAll();
    }

    //Find more abstract solution?
    public List<Title> getAvailableTitles() {
        return availableTitles;
    }

    //Find more abstract solution?
    public List<Province> getAvailableProvinces() {
        return availableProvinces;
    }

    /**
     * Gets the email of the user.
     *
     * @return email of user
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the email of a user.
     *
     * @param emailAddress Email of the user
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Gets the password of the user.
     *
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password Password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the first name of the user.
     *
     * @return first name of user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName First name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return last name of user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName Last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the company name of the user.
     *
     * @return company name of the user
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the company name of the user.
     *
     * @param companyName Company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets the first address of the user.
     *
     * @return address one of the user
     */
    public String getAddressOne() {
        return addressOne;
    }

    /**
     * Sets address one of the user.
     *
     * @param addressOne Address one
     */
    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    /**
     * Gets the second address of the user.
     *
     * @return address two of the user
     */
    public String getAddressTwo() {
        return addressTwo;
    }

    /**
     * Sets address two of the user.
     *
     * @param addressTwo Address two
     */
    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    /**
     * Gets the city of the user.
     *
     * @return city of the user
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the user.
     *
     * @param city City
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the country of the user.
     *
     * @return country of the user
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country of the user.
     *
     * @param country Country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets the postal code of the user.
     *
     * @return postal code of the user
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal code of the user.
     *
     * @param postalCode Postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets the home phone of the user.
     *
     * @return home phone of the user
     */
    public String getHomePhone() {
        return homePhone;
    }

    /**
     * Sets the home phone of the user.
     *
     * @param homePhone Home phone
     */
    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    /**
     * Gets the cell phone of the user.
     *
     * @return cell phone of the user
     */
    public String getCellPhone() {
        return cellPhone;
    }

    /**
     * Sets the cell phone of the user.
     *
     * @param cellPhone Cell phone
     */
    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    /**
     * Gets the title id of the user.
     *
     * @return title id of the user
     */
    public Title getTitleId() {
        return titleId;
    }

    /**
     * Sets the title id of the user.
     *
     * @param titleId Title id
     */
    public void setTitleId(Title titleId) {
        this.titleId = titleId;
    }

    /**
     * Gets the province id of the user.
     *
     * @return province id of the user
     */
    public Province getProvinceId() {
        return provinceId;
    }

    /**
     * Sets the province id of the user.
     *
     * @param provinceId Province id
     */
    public void setProvinceId(Province provinceId) {
        this.provinceId = provinceId;
    }

}
