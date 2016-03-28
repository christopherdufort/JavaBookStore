/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity of a registered user.
 * 
 * @author Giuseppe Campanelli
 */
@Entity
@Table(name = "registered_user", catalog = "g3w16", schema = "")
@NamedQueries({
    @NamedQuery(name = "RegisteredUser.findAll", query = "SELECT r FROM RegisteredUser r"),
    @NamedQuery(name = "RegisteredUser.findByUserId", query = "SELECT r FROM RegisteredUser r WHERE r.userId = :userId"),
    @NamedQuery(name = "RegisteredUser.findByEmailAddress", query = "SELECT object(r) FROM RegisteredUser r WHERE r.emailAddress = :emailAddress")})
public class RegisteredUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id")
    private Integer userId;
    @Basic(optional = false)
    @Column(name = "email_address")
    private String emailAddress;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "address_one")
    private String addressOne;
    @Column(name = "address_two")
    private String addressTwo;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "home_phone")
    private String homePhone;
    @Column(name = "cell_phone")
    private String cellPhone;
    @Column(name = "manager")
    private Boolean manager;
    @Column(name = "active")
    private Boolean active;
    @OneToMany(mappedBy = "userId")
    private List<Review> reviewList;
    @JoinColumn(name = "title_id", referencedColumnName = "title_id")
    @ManyToOne
    private Title titleId;
    @JoinColumn(name = "province_id", referencedColumnName = "province_id")
    @ManyToOne
    private Province provinceId;

    /**
     * Default constructor.
     */
    public RegisteredUser() {
    }

    /**
     * One parameter constructor.
     * 
     * @param userId Id of the user
     */
    public RegisteredUser(Integer userId) {
        this.userId = userId;
    }

    /**
     * Two parameter constructor.
     * 
     * @param userId Id of the user
     * @param emailAddress Email of the user
     * @param password Password of the user
     */
    public RegisteredUser(Integer userId, String emailAddress, String password) {
        this.userId = userId;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    /**
     * Gets the user id
     * 
     * @return user id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     * 
     * @param userId Id of user
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
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
     * Gets the manager status of the user.
     * 
     * @return manager status of the account
     */
    public Boolean getManager() {
        return manager;
    }

    /**
     * Sets the manager status of the user.
     * 
     * @param manager Manaager status
     */
    public void setManager(Boolean manager) {
        this.manager = manager;
    }

    /**
     * Gets the active status of the user.
     * 
     * @return active status of the user
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Sets the active status of the user.
     * 
     * @param active Active status
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Gets the list of all reviews associated to a user.
     * 
     * @return list of reviews
     */
    public List<Review> getReviewList() {
        return reviewList;
    }

    /**
     * Sets the list of reviews associated to a user.
     * 
     * @param reviewList List of reviews
     */
    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegisteredUser)) {
            return false;
        }
        RegisteredUser other = (RegisteredUser) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return emailAddress;
    }
}