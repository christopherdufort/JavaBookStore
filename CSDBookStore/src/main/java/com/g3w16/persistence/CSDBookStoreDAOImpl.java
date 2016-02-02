/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.persistence;

import com.g3w16.beans.InvoiceBean;
import com.g3w16.beans.InvoiceDetailBean;
import com.g3w16.beans.RegisteredUser;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author 1232046
 */
public class CSDBookStoreDAOImpl implements CSDBookStoreDAO{

    @Override
    public int createInvoice(RegisteredUser user) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<InvoiceBean> findAllInvoices() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<InvoiceBean> findAllInvoicesBasedOnUser() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InvoiceBean findInvoiceById(InvoiceBean invoice) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteInvoice(InvoiceBean invoice) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int createInvoiceDetails(InvoiceBean invoice) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<InvoiceDetailBean> findAllInvoiceDetails() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InvoiceBean findInvoiceDetailById(InvoiceDetailBean invoice) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<InvoiceBean> findInvoiceDetailsBasedOnInvoice(InvoiceBean invoice) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteInvoiceDetail(InvoiceBean invoice) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
   


}
