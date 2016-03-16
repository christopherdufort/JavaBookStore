/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.managerController;

import com.g3w16.entities.*;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Xin Ma
 * @author Rita Lazaar
 */
@Named("m_invoices")
@RequestScoped
public class m_invoicesBackingBean {

    @Inject
    Invoice invoice;
    
    @Inject
    InvoiceJpaController invoiceJpa;

   

    /**
     * Returning all invoices
     *
     * @return
     */
    public List<Invoice> getAllInvoices() {
        return invoiceJpa.findInvoiceEntities();

    }

    /**
     * Returning all invoices
     *
     * @return
     */
    public List<Invoice> getAllInvoicesByDateAndUser(Date date1, Date date2, Integer user) {
        return invoiceJpa.findInvoiceByDateAndUser(date1, date2, user);

    }
}
