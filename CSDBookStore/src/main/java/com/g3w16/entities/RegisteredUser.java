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
 *
 * @author 1040570
 */
@Entity
@Table(name = "registered_user", catalog = "g3w16", schema = "")
@NamedQueries({
    @NamedQuery(name = "RegisteredUser.findAll", query = "SELECT r FROM RegisteredUser r"),
    @NamedQuery(name = "RegisteredUser.findByUserId", query = "SELECT r FROM RegisteredUser r WHERE r.userId = :userId"),
    @NamedQuery(name = "RegisteredUser.findByEmailAddress", query = "SELECT r FROM RegisteredUser r WHERE r.emailAddress = :emailAddress"),
    @NamedQuery(name = "RegisteredUser.findByPassword", query = "SELECT r FROM RegisteredUser r WHERE r.password = :password"),
    @NamedQuery(name = "RegisteredUser.findByFirstName", query = "SELECT r FROM RegisteredUser r WHERE r.firstName = :firstName"),
    @NamedQuery(name = "RegisteredUser.findByLastName", query = "SELECT r FROM RegisteredUser r WHERE r.lastName = :lastName"),
    @NamedQuery(name = "RegisteredUser.findByCompanyName", query = "SELECT r FROM RegisteredUser r WHERE r.companyName = :companyName"),
    @NamedQuery(name = "RegisteredUser.findByAddressOne", query = "SELECT r FROM RegisteredUser r WHERE r.addressOne = :addressOne"),
    @NamedQuery(name = "RegisteredUser.findByAddressTwo", query = "SELECT r FROM RegisteredUser r WHERE r.addressTwo = :addressTwo"),
    @NamedQuery(name = "RegisteredUser.findByCity", query = "SELECT r FROM RegisteredUser r WHERE r.city = :city"),
    @NamedQuery(name = "RegisteredUser.findByCountry", query = "SELECT r FROM RegisteredUser r WHERE r.country = :country"),
    @NamedQuery(name = "RegisteredUser.findByPostalCode", query = "SELECT r FROM RegisteredUser r WHERE r.postalCode = :postalCode"),
    @NamedQuery(name = "RegisteredUser.findByHomePhone", query = "SELECT r FROM RegisteredUser r WHERE r.homePhone = :homePhone"),
    @NamedQuery(name = "RegisteredUser.findByCellPhone", query = "SELECT r FROM RegisteredUser r WHERE r.cellPhone = :cellPhone"),
    @NamedQuery(name = "RegisteredUser.findByManager", query = "SELECT r FROM RegisteredUser r WHERE r.manager = :manager"),
    @NamedQuery(name = "RegisteredUser.findByActive", query = "SELECT r FROM RegisteredUser r WHERE r.active = :active")})
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

    public RegisteredUser() {
    }

    public RegisteredUser(Integer userId) {
        this.userId = userId;
    }

    public RegisteredUser(Integer userId, String emailAddress, String password) {
        this.userId = userId;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public Boolean getManager() {
        return manager;
    }

    public void setManager(Boolean manager) {
        this.manager = manager;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public Title getTitleId() {
        return titleId;
    }

    public void setTitleId(Title titleId) {
        this.titleId = titleId;
    }

    public Province getProvinceId() {
        return provinceId;
    }

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
        return "com.g3w16.entities.RegisteredUser[ userId=" + userId + " ]";
    }
    
}
