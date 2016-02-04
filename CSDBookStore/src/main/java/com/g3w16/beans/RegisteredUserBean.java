package com.g3w16.beans;

/**
 * @author Giuseppe Campanelli	
 * @version 0.0.4 - Last modified 2/2/2016
 * @since 0.0.1 
 */
public class RegisteredUserBean {

    private int clientNumber;
    private String emailAddress;
    private String password;
    private String title;
    private String firstName;
    private String lastName;
    private String companyName;
    private String addressOne;
    private String addressTwo;
    private String city;
    private ProvinceBean province;
    private String country;
    private String postalCode;
    private String homePhone;
    private String cellPhone;
    private boolean isManager;
    private boolean isActive;

    public RegisteredUserBean() {
        this(0, "", "", "", "", "", "", "", "", "",
                new ProvinceBean(), "", "", "", "", false, true);
    }
    
    public RegisteredUserBean(final String emailAddress, final String password) { 
        this(0, "", emailAddress, password, "", "", "", "", "", "",
                new ProvinceBean(), "", "", "", "", false, true);
    }

    public RegisteredUserBean(final int clientNumber, final String emailAddress, final String password,
            final String title, final String firstName, final String lastName, final String companyName,
            final String addressOne, final String addressTwo, final String city, final ProvinceBean province,
            final String country, final String postalCode, final String homePhone, final String cellPhone,
            final boolean isManager, final boolean isActive) {
        super();
        this.clientNumber = clientNumber;
        this.emailAddress = emailAddress;
        this.password = password;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
        this.addressOne = addressOne;
        this.addressTwo = addressTwo;
        this.city = city;
        this.province = province;
        this.country = country;
        this.postalCode = postalCode;
        this.homePhone = homePhone;
        this.cellPhone = cellPhone;
        this.isManager = isManager;
        this.isActive = isActive;
    }

    public void setClientNumber(final int clientNumber) {
        this.clientNumber = clientNumber;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public void setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setAddressOne(final String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressTwo(final String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setProvince(final ProvinceBean province) {
        this.province = province;
    }

    public ProvinceBean getProvince() {
        return province;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setHomePhone(final String homePhone) {
        this.homePhone = homePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setCellPhone(final String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setIsManager(final boolean isManager) {
        this.isManager = isManager;
    }

    public boolean getIsManager() {
        return isManager;
    }

    public void setIsActive(final boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsActive() {
        return isActive;
    }
}
