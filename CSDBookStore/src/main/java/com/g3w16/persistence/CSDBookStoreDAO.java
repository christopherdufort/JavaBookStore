/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.persistence;

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
}

