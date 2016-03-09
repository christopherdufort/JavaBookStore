/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import com.g3w16.entities.exceptions.NonexistentEntityException;
import com.g3w16.entities.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Rita Lazaar
 */
public interface InvoiceDetailJpaControllerInterface extends Serializable {

    void create(InvoiceDetail invoiceDetail) throws RollbackFailureException, Exception;

    InvoiceDetail findInvoiceDetail(Integer id);

    List<InvoiceDetail> findInvoiceDetailByInvoice(Invoice invoiceId);

    List<InvoiceDetail> findInvoiceDetailEntities();

    List<InvoiceDetail> findInvoiceDetailEntities(int maxResults, int firstResult);

    int getInvoiceDetailCount();

}
