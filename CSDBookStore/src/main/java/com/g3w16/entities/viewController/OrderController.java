/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities.viewController;

import com.g3w16.beans.AuthenticatedUser;
import com.g3w16.beans.OrderBackingBean;
import com.g3w16.beans.OrderDetailBackingBean;
import com.g3w16.entities.Invoice;
import com.g3w16.entities.InvoiceDetail;
import com.g3w16.entities.InvoiceDetailJpaController;
import com.g3w16.entities.InvoiceJpaController;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controller used to manage the orders of a specific user. Retrieves all the
 * orders and order details from the database and created lists to be displayed
 * on the orders.xhtml page.
 *
 * @author Christopher Dufort
 */
@Named
@RequestScoped
public class OrderController {

    @Inject
    private InvoiceJpaController invoiceJpaController;

    @Inject
    private InvoiceDetailJpaController invoiceDetailJpaController;

    @Inject
    private AuthenticatedUser authenticatedUser;

    List<OrderBackingBean> ordersForCurrentUser;

    public OrderController() {
        super();
    }

    /**
     * A single user can have multiple orders: Each order consists of a sale
     * date, a total net, total gross, and the user who purchased it each order
     * has multiple details Each detail includes : a book, its pst, gst,hst and
     * its original book price, and the quantity it was ordered in.
     *
     * @return
     */
    public List<OrderBackingBean> getAllOrdersForCurrentUser() {
        ordersForCurrentUser = new ArrayList<OrderBackingBean>();
        List<Invoice> userInvoices = invoiceJpaController.findInvoiceByUserId(authenticatedUser.getRegisteredUser().getUserId());

        int countOfInvoices = userInvoices.size();
        OrderBackingBean newOrderBean = new OrderBackingBean();
        for (int i = 0; i < countOfInvoices; i++) {
            newOrderBean = new OrderBackingBean();
            newOrderBean.setClientName(authenticatedUser.getRegisteredUser().getFirstName() + " " + authenticatedUser.getRegisteredUser().getLastName());

            BigDecimal totalNet = new BigDecimal(0.0);
            BigDecimal totalPst = new BigDecimal(0.0);
            BigDecimal totalGst = new BigDecimal(0.0);
            BigDecimal totalHst = new BigDecimal(0.0);
            BigDecimal totalGross = new BigDecimal(0.0);

            List<OrderDetailBackingBean> detailsOfCurrentOrder = new ArrayList<OrderDetailBackingBean>();
            List<InvoiceDetail> detailsOfCurrentInvoice = invoiceDetailJpaController.findInvoiceDetailByInvoice(userInvoices.get(i));
            for (int j = 0; j < detailsOfCurrentInvoice.size(); j++) {
                detailsOfCurrentOrder.add(new OrderDetailBackingBean(detailsOfCurrentInvoice.get(j).getBookId(), detailsOfCurrentInvoice.get(j).getPst(), detailsOfCurrentInvoice.get(j).getGst(), detailsOfCurrentInvoice.get(j).getHst(), detailsOfCurrentInvoice.get(j).getBookPrice()));
                totalNet = totalNet.add(detailsOfCurrentInvoice.get(j).getBookPrice());
                totalPst = totalPst.add(detailsOfCurrentInvoice.get(j).getPst());
                totalGst = totalGst.add(detailsOfCurrentInvoice.get(j).getGst());
                totalHst = totalHst.add(detailsOfCurrentInvoice.get(j).getHst());
            }
            newOrderBean.setDetailsForThisOrder(detailsOfCurrentOrder);

            LocalDate saleDate = userInvoices.get(i).getSaleDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            newOrderBean.setSaleDate(saleDate);
            newOrderBean.setOrderNumber(userInvoices.get(i).getInvoiceId());

            totalGross = totalNet.add(totalPst).add(totalPst).add(totalGst).add(totalHst);

            newOrderBean.setNetTotal(totalNet);
            newOrderBean.setTotalGst(totalGst);
            newOrderBean.setTotalHst(totalHst);
            newOrderBean.setGrossTotal(totalGross);

            ordersForCurrentUser.add(newOrderBean);
        }
        return ordersForCurrentUser;
    }
}
