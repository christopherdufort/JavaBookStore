package com.g3w16.mail.beans;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This Mail Configuration Bean class contains all the relevant information
 * needed for authentication and connection to an email server with an email
 * account. This class is sufficient for basic Gmail functionality. Additional
 * fields are required to work with other mail systems.
 * 
 * User information including Username/Email and passwords are NOT encrypted,
 * passwords are stored in simple text.
 * 
 * Modified class to be in the form of JavaFX Beans (property) from version 0.3.1 onward.
 *
 * @author Christopher Dufort
 * @version 0.5.4-SNAPSHOT , Last modified 12/02/15
 * @since 0.5.4-SNAPSHOT , Repurposed from JAG project.
 */
public class MailConfigBean {

	private StringProperty username;
	private StringProperty emailAddress;
	private StringProperty name;
	private StringProperty password;
	private StringProperty imapUrl;
	private StringProperty smtpUrl;
	private IntegerProperty imapPort;
	private IntegerProperty smtpPort;


	/**
	 * Default no parameter constructor call other constructor sending in values.
	 * Initial values are set to programs default (could also be empty instead).
	 * These settings are changable at any time during the running of the program.
	 */
	public MailConfigBean() {
		this("bibliotech.send@gmail.com", "bibliotech.send@gmail.com", "BiblioTech Send", "sofa3brick", "imap.gmail.com", "smtp.gmail.com", 993, 465);
		
	}

	public MailConfigBean(final String username, final String emailAddress, final String name, final String password,
			final String imapUrl, final String smtpUrl, final int imapPort, final int smtpPort)
	{
		super();
		this.username = new SimpleStringProperty(username);
		this.emailAddress = new SimpleStringProperty(emailAddress);
		this.name = new SimpleStringProperty(name);
		this.password = new SimpleStringProperty(password);
		this.imapUrl = new SimpleStringProperty(imapUrl);
		this.smtpUrl = new SimpleStringProperty(smtpUrl);
		this.imapPort = new SimpleIntegerProperty(imapPort);

	}


	/**
	 * @return the imapUrl
	 */
	public String getImapUrl() {
		return imapUrl.get();
	}
	
	/**
	 * 
	 * @return
	 */
	public StringProperty imapUrlProperty() {
		return imapUrl;
	}

	/**
	 * @param imapUrl
	 *            the imapUrl to set
	 */
	public void setImapUrl(final String imapUrl) {
		this.imapUrl.set(imapUrl);
	}

	/**
	 * @return the smtpUrl
	 */
	public String getSmtpUrl() {
		return smtpUrl.get();
	}
	
	/**
	 * 
	 * @return
	 */
	public StringProperty smtpUrlProperty(){
		return smtpUrl;
	}

	/**
	 * @param smtpUrl
	 *            the smtpUrl to set
	 */
	public void setSmtpUrl(final String smtpUrl) {
		this.smtpUrl.set(smtpUrl);
	}

	/**
	 * @return the imapPort
	 */
	public int getImapPort() {
		return imapPort.get();
	}
	
	/**
	 * 
	 * @return
	 */
	public IntegerProperty imapPortProperty(){
		return imapPort;
	}

	/**
	 * @param imapPort
	 *            the imapPort to set
	 */
	public void setImapPort(final int imapPort) {
		this.imapPort.set(imapPort);
	}

	/**
	 * @return the smtpPort
	 */
	public int getSmtpPort() {
		return smtpPort.get();
	}
	
	/**
	 * 
	 * @return
	 */
	public IntegerProperty smtpPortProperty(){
		return smtpPort;
	}

	/**
	 * @param smtpPort
	 *            the smtpPort to set
	 */
	public void setSmtpPort(final int smtpPort) {
		this.smtpPort.set(smtpPort);
	}

	/**
	 * 
	 * @return
	 */
	public String getUsername() {
		return username.get();
	}
	
	/**
	 * 
	 * @return
	 */
	public StringProperty usernameProperty(){
		return username;
	}
	
	/**
	 * 
	 * @param userName
	 */
	public void setUsername(final String username) {
		this.username.set(username);
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress.get();
	}
	
	/**
	 * 
	 * @return
	 */
	public StringProperty emailAddressProperty(){
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(final String emailAddress) {
		this.emailAddress.set(emailAddress);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name.get();
	}
	
	public StringProperty nameProperty(){
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name.set(name);
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password.get();
	}
	
	/**
	 * 
	 * @return
	 */
	public StringProperty passwordProperty() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(final String password) {
		this.password.set(password);
	}
	
}