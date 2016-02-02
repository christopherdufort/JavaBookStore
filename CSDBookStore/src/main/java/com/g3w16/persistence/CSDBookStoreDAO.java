/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.persistence;

import com.g3w16.beans.InvoiceBean;
import com.g3w16.beans.InvoiceDetailBean;
import com.g3w16.beans.RegisteredUser;
import com.g3w16.beans.ReviewBean;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rita Lazaar
 */
public interface CSDBookStoreDAO {
    
    public int createInvoice(RegisteredUser user) throws SQLException;
    
    public ArrayList<InvoiceBean> findAllInvoices() throws SQLException;
    
    public ArrayList<InvoiceBean> findAllInvoicesBasedOnUser() throws SQLException;
    
    public InvoiceBean findInvoiceById(InvoiceBean invoice) throws SQLException;
    
    public int deleteInvoice (InvoiceBean invoice) throws SQLException; 
    
    public int createInvoiceDetails(InvoiceBean invoice) throws SQLException;
    
    public ArrayList<InvoiceDetailBean> findAllInvoiceDetails() throws SQLException;
    
    public InvoiceBean findInvoiceDetailById(InvoiceDetailBean invoice) throws SQLException;
    
    public ArrayList<InvoiceBean> findInvoiceDetailsBasedOnInvoice(InvoiceBean invoice) throws SQLException; 
    
    public int deleteInvoiceDetail (InvoiceBean invoice) throws SQLException; 
    
    /**
     * CRUD method for Review table
     * @author Xin Ma
     * @version 0.0.6
     * @param reviewBean
     * @return 
     * @throws java.sql.SQLException
     */
    public int createReview(ReviewBean reviewBean) throws SQLException;
    /**
     * CRUD method for Review table
     * @author Xin Ma
     * @version 0.0.6
     * @param review_id
     * @return
     * @throws SQLException 
     */
    public ReviewBean getReviewById(int review_id) throws SQLException;
    /**
     * CRUD method for Review table
     * @author Xin Ma
     * @version 0.0.6
     * @param user_id
     * @return
     * @throws SQLException 
     */
    public List<ReviewBean> getReviewByUserId(int user_id) throws SQLException;
    /**
     * CRUD method for Review table
     * @author Xin Ma
     * @version 0.0.6
     * @param date_submitted
     * @return
     * @throws SQLException 
     */
    public List<ReviewBean> getReviewByDateSubmitted(LocalDateTime date_submitted) throws SQLException;
    /**
     * CRUD method for Review table
     * @author Xin Ma
     * @version 0.0.6
     * @param approval_id
     * @return
     * @throws SQLException 
     */
    public List<ReviewBean> getReviewByApprovalId(int approval_id) throws SQLException;
    /**
     * CRUD method for Review table
     * @author Xin Ma
     * @version 0.0.6
     * @param isbn
     * @return
     * @throws SQLException 
     */
    public List<ReviewBean> getReviewByIsbn(String isbn) throws SQLException;
    /**
     * CRUD method for Review table
     * @author Xin Ma
     * @version 0.0.6
     * @param reviewBean
     * @return
     * @throws SQLException 
     */
    public int updateReview(ReviewBean reviewBean) throws SQLException;
    /**
     * CRUD method for Review table
     * @author Xin Ma
     * @version 0.0.6
     * @param review_id
     * @return
     * @throws SQLException 
     */
    public int deleteReviewByReviewId(int review_id) throws SQLException;
    /**
     * CRUD method for Review table
     * @author Xin Ma
     * @version 0.0.6
     * @param user_id
     * @return
     * @throws SQLException 
     */
    public int deleteReviewByUserId(int user_id) throws SQLException;
    /**
     * CRUD method for Review table
     * @author Xin Ma
     * @version 0.0.6
     * @param isbn
     * @return
     * @throws SQLException 
     */
    public int deleteReviewByIsbn(String isbn) throws SQLException;
    /**
     * CRUD method for Review table
     * @author Xin Ma
     * @version 0.0.6
     * @param date_submitted
     * @return
     * @throws SQLException 
     */
    public int deleteReviewByDateSubmitted(LocalDateTime date_submitted) throws SQLException;
}

