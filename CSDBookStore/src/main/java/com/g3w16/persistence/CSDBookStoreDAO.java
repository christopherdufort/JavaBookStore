/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.persistence;

import com.g3w16.beans.AuthorBean;
import com.g3w16.beans.BookBean;
import com.g3w16.beans.InvoiceBean;
import com.g3w16.beans.InvoiceDetailBean;
import com.g3w16.beans.RegisteredUserBean;
import com.g3w16.beans.ReviewBean;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Rita Lazaar
 * @version : 0.0.2
 */
public interface CSDBookStoreDAO {
    
    /**
     *
     * This method gets all the provinces.
     *
     * @author Giuseppe Campanelli
     * @return list of all provinces
     * @throws SQLException
     */
    public List<ProvinceBean> findAllProvinces() throws SQLException;
    
    /**
     *
     * This method gets a province bean by name.
     *
     * @author Giuseppe Campanelli
     * @param province province name to get bean of
     * @return province bean
     * @throws SQLException
     */
    public ProvinceBean findProvinceByName(final String province) throws SQLException;
    
    /**
     *
     * This method gets all titles.
     *
     * @author Giuseppe Campanelli
     * @return list of all titles
     * @throws SQLException
     */
    public List<String> findAllTitles() throws SQLException;
    
    
    /**
     *
     * This method gets a province bean by name.
     *
     * @author Giuseppe Campanelli
     * @param emailAddress email of user
     * @param password pass of user
     * @throws SQLException
     */
    public int createRegisteredUser(String emailAddress, String password) throws SQLException;
    
    /**
     *
     * This method gets all the registered users.
     *
     * @author Giuseppe Campanelli
     * @return list of all users
     * @throws SQLException
     */
    public List<RegisteredUserBean> findAllUsers() throws SQLException;
    
    /**
     *
     * This method gets a user by id.
     *
     * @author Giuseppe Campanelli
     * @param id id of user to get
     * @return registered user bean
     * @throws SQLException
     */
    public RegisteredUserBean findUserById(final int id) throws SQLException;
    
    /**
     *
     * This method updates a users account status.
     *
     * @author Giuseppe Campanelli
     * @param id id of the user
     * @param isActive account status
     * @throws SQLException
     */
    public int setAccountStatus(final int id, final boolean isActive) throws SQLException;
    
    /**
     *
     * This method updates a users manager status.
     *
     * This method gets a
     * @author Giuseppe Campanelli
     * @param id id of the user
     * @param isManager manager status
     * @throws SQLException
     */
    public int setManagerStatus(final int id, final boolean isManager) throws SQLException;
    
    /**
     *
     * This method updates a users billing info.
     *
     * @author Giuseppe Campanelli
     * @param updatedUser user to update
     * @param titleIndex index position in list box which matches db position
     * @param provinceIndex index position in list box which matches db position
     * @return rows affected
     * @throws SQLException
     */
    public int updateUserBilling(RegisteredUserBean updatedUser, int titleIndex, int provinceIndex) throws SQLException;
    
    /**
     *
     * This method updates a users password.
     *
     * @author Giuseppe Campanelli
     * @param id id of the user
     * @param password new password
     * @throws SQLException
     */
    public int updateUserPassword(final int id, final String password) throws SQLException;

    /**
     *
     * This method allows to create a new invoice.
     *
     * @author Rita Lazaar
     * @param invoice
     * @return
     * @throws SQLException
     */
    public int createInvoice(InvoiceBean invoice) throws SQLException;

    /**
     * This method allows to find all invoices existing in database.
     *
     * @author Rita Lazaar
     * @return
     * @throws SQLException
     */
    public List<InvoiceBean> findAllInvoices() throws SQLException;

    /**
     *
     * This method allows to find all invoices related to a specific user.
     *
     * @author Rita Lazaar
     * @param userNumber
     * @return
     * @throws SQLException
     */
    public List<InvoiceBean> findAllInvoicesBasedOnUser(int userNumber) throws SQLException;

    /**
     *
     * This method allows to find one invoice based on its id.
     *
     * @author Rita Lazaar
     * @param saleNumber
     * @return
     * @throws SQLException
     */
    public InvoiceBean findInvoiceById(int saleNumber) throws SQLException;

    /**
     *
     * This method allows to delete an invoice, if needed.
     *
     * @author Rita Lazaar
     * @param saleNumber
     * @return
     * @throws SQLException
     */
    public int deleteInvoice(int saleNumber) throws SQLException;

    /**
     *
     * This method allows to create the details related to an invoice. This thus
     * represents one item in the order and its information.
     *
     * @author Rita Lazaar
     * @param invoice
     * @return
     * @throws SQLException
     */
    public int createInvoiceDetails(InvoiceDetailBean invoiceDetail) throws SQLException;

    /**
     *
     * This method allows to find all invoice details existing in the database.
     *
     * @author Rita Lazaar
     * @return @throws SQLException
     */
    public List<InvoiceDetailBean> findAllInvoiceDetails() throws SQLException;

    /**
     *
     * This method allows to find an invoice detail, or item, based on its id.
     *
     * @author Rita Lazaar
     * @param id
     * @return
     * @throws SQLException
     */
    public InvoiceDetailBean findInvoiceDetailById(int id) throws SQLException;

    /**
     *
     * This method allows to find all invoice details based on the invoice
     * number.
     *
     * @author Rita Lazaar
     * @param saleNumber
     * @return
     * @throws SQLException
     */
    public List<InvoiceDetailBean> findInvoiceDetailsBasedOnInvoice(int saleNumber) throws SQLException;

    /**
     * This method allows to delete the invoice detail based on its id.
     *
     * @author Rita Lazaar
     * @param id
     * @return
     * @throws SQLException
     */
    public int deleteInvoiceDetail(int id) throws SQLException;

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     * @param reviewBean
     * @return
     * @throws java.sql.SQLException
     */
    public int createReview(ReviewBean reviewBean) throws SQLException;

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     * @param review_id
     * @return
     * @throws SQLException
     */
    public ReviewBean getReviewById(int review_id) throws SQLException;

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     * @param user_id
     * @return
     * @throws SQLException
     */
    public List<ReviewBean> getReviewByUserId(int user_id) throws SQLException;

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     * @param date_submitted
     * @return
     * @throws SQLException
     */
    public List<ReviewBean> getReviewByDateSubmitted(LocalDateTime date_submitted) throws SQLException;

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     * @param approval_id
     * @return
     * @throws SQLException
     */
    public List<ReviewBean> getReviewByApprovalId(int approval_id) throws SQLException;

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     * @param isbn
     * @return
     * @throws SQLException
     */
    public List<ReviewBean> getReviewByIsbn(String isbn) throws SQLException;

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     * @param reviewBean
     * @return
     * @throws SQLException
     */
    public int updateReview(ReviewBean reviewBean) throws SQLException;

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     * @param review_id
     * @return
     * @throws SQLException
     */
    public int deleteReviewByReviewId(int review_id) throws SQLException;

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     * @param user_id
     * @return
     * @throws SQLException
     */
    public int deleteReviewByUserId(int user_id) throws SQLException;

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     * @param isbn
     * @return
     * @throws SQLException
     */
    public int deleteReviewByIsbn(String isbn) throws SQLException;

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     * @param date_submitted
     * @return
     * @throws SQLException
     */
    public int deleteReviewByDateSubmitted(LocalDateTime date_submitted) throws SQLException;
    
    /**
     * CRUD method for Author table
     * 
     * @author Jonas Faure
     * @version 0.0.12
     * @param author
     * @return 
     * @throws SQLException
     */
    public int createAuthor(AuthorBean author)throws SQLException;
    
    /**
     * CRUD method for Author table
     * 
     * @author Jonas Faure
     * @version 0.0.12
     * @param author_id
     * @return 
     * @throws SQLException
     */
    public int deleteAuthorByAuthorId(int author_id)throws SQLException;
    
    /**
     * CRUD method for Author table
     * 
     * @author Jonas Faure
     * @version 0.0.12
     * @param author
     * @return 
     * @throws SQLException
     */
    public int updateAuthor(AuthorBean author)throws SQLException;
    
    /**
     * CRUD method for Author table
     * 
     * @author Jonas Faure
     * @version 0.0.12
     * @return 
     * @throws SQLException
     */
    public List<AuthorBean> getAllAuthor()throws SQLException;
    
    /**
     * CRUD method for Author table
     * 
     * @author Jonas Faure
     * @version 0.0.12
     * @param book
     * @return 
     * @throws SQLException
     */
    public List<AuthorBean> getAuthorByBook(BookBean book)throws SQLException;
}
