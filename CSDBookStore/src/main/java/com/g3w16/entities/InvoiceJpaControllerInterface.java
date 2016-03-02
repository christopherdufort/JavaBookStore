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
import java.util.List;

/**
 *
 * @author Rita Lazaar
 * @version SNAPSHOT - 5.3.2
 */
public interface InvoiceJpaControllerInterface extends Serializable {

    void create(Invoice invoice) throws RollbackFailureException, Exception;

    void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception;

    void edit(Invoice invoice) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception;

    Invoice findInvoice(Integer id);

    List<Invoice> findInvoiceByUserNumber(Integer userNumber);

    List<Invoice> findInvoiceEntities();

    List<Invoice> findInvoiceEntities(int maxResults, int firstResult);

    int getInvoiceCount();
    
}
