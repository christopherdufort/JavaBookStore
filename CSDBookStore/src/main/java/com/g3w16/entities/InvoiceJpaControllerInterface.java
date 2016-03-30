/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import com.g3w16.entities.exceptions.IllegalOrphanException;
import com.g3w16.entities.exceptions.NonexistentEntityException;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rita Lazaar
 * @version SNAPSHOT - 0.0.4
 */
public interface InvoiceJpaControllerInterface extends Serializable {

    void create(Invoice invoice) throws RollbackFailureException, Exception;

    Invoice findInvoice(Integer id);
    // new method
    List<Invoice> findInvoiceByDate(Date date1, Date date2);
    //new method
    List<Invoice> findInvoiceByDateAndUser(Date date1, Date date2, Integer userId);
    
    List<Invoice> findInvoiceByUserId(Integer userId);

    List<Invoice> findInvoiceEntities();

    List<Invoice> findInvoiceEntities(int maxResults, int firstResult);

    int getInvoiceCount();

}
