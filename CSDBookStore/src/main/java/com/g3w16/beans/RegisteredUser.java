package com.g3w16.beans;

public class RegisteredUser {

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
    private String province;
    private String country;
    private String postalCode;
    private String homePhone;
    private String cellPhone;
    private String lastSearchGenre;
    private boolean isManager;
    private boolean isActive;

    public RegisteredUser() {
        clientNumber = 0;
        emailAddress = "";
        password = "";
        title = "";
        firstName = "";
        lastName = "";
        companyName = "";
        addressOne = "";
        addressTwo = "";
        city = "";
        province = "";
        country = "";
        postalCode = "";
        homePhone = "";
        cellPhone = "";
        lastSearchGenre = "";
        isManager = false;
        isActive = true;
    }

    public RegisteredUser(final int clientNumber, final String emailAddress, final String password,
            final String title, final String firstName, final String lastName, final String companyName,
            final String addressOne, final String addressTwo, final String city, final String province,
            final String country, final String postalCode, final String homePhone, final String cellPhone,
            final String lastSearchGenre, final boolean isManager, final boolean isActive) {
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
        this.lastSearchGenre = lastSearchGenre;
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

    public void setProvince(final String province) {
        this.province = province;
    }

    public String getProvince() {
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

    public void setLastSearchGenre(final String lastSearchGenre) {
        this.lastSearchGenre = lastSearchGenre;
    }

    public String getLastSearchGenre() {
        return lastSearchGenre;
    }

    public void setIsManager(final boolean isManager) {
        this.isManager = isManager;
    }

    public boolean getIsManager() {
        return isManager;
    }

    public void setIsActive(final boolean isActive) {
        this.lastSearchGenre = lastSearchGenre;
    }

    public boolean getIsActive() {
        return isActive;
    }
}
