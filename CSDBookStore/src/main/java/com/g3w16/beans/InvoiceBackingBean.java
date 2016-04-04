package com.g3w16.beans;

import com.g3w16.entities.Invoice;
import com.g3w16.entities.InvoiceDetail;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * backing bean for invoice.xhtml
 * Displays the invoice after a purchase is made
 *
 * @author Giuseppe Campanelli
 */
@Named("invoiceBB")
@SessionScoped
public class InvoiceBackingBean implements Serializable {

    private Invoice invoice;
    private List<InvoiceDetail> invoiceDetails;
    private String endingFourCardNumberDigits;

    @Inject
    private AuthenticatedUser user;

    /**
     * Gets the current invoice
     * 
     * @return the invoice
     */
    public Invoice getInvoice() {
        return invoice;
    }

    /**
     * Sets the current invoice
     * 
     * @param invoice the invoice to set
     */
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    /**
     * Gets the details for an invoice
     * 
     * @return the invoiceDetails
     */
    public List<InvoiceDetail> getInvoiceDetails() {
        return invoiceDetails;
    }

    /**
     * Sets the details for an invoice
     * 
     * @param invoiceDetails the invoiceDetails to set
     */
    public void setInvoiceDetails(List<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    /**
     * Gets the 4 last digits of a credit card
     * 
     * @return the endingFourCardNumberDigits
     */
    public String getEndingFourCardNumberDigits() {
        return endingFourCardNumberDigits;
    }

    /**
     * Sets the last 4 digits of the credit card
     * 
     * @param endingFourCardNumberDigits the endingFourCardNumberDigits to set
     */
    public void setEndingFourCardNumberDigits(String endingFourCardNumberDigits) {
        this.endingFourCardNumberDigits = endingFourCardNumberDigits;
    }
    
    /**
     * Gets the GST of the invoice
     * 
     * @return gst
     */
    public BigDecimal getGst() {
        BigDecimal gst = new BigDecimal(0);
        int size = invoiceDetails.size();
        
        for (int i = 0; i < size; i++)
            gst = gst.add(invoiceDetails.get(i).getGst());
        
        return gst;
    }
    
    /**
     * Gets the PST of the invoice
     * 
     * @return pst
     */
    public BigDecimal getPst() {
        BigDecimal pst = new BigDecimal(0);
        int size = invoiceDetails.size();
        
        for (int i = 0; i < size; i++)
            pst = pst.add(invoiceDetails.get(i).getPst());
        
        return pst;
    }
    
    /**
     * Gets the HST of the invoice
     * 
     * @return hst
     */
    public BigDecimal getHst() {
        BigDecimal hst = new BigDecimal(0);
        int size = invoiceDetails.size();
        
        for (int i = 0; i < size; i++)
            hst = hst.add(invoiceDetails.get(i).getHst());
        
        return hst;
    }
    
    /**
     * Formats a date to yyyy-MM-dd
     * 
     * @param date date for format
     * 
     * @return formatted date
     */
    public String formatDate(Date date) {
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
        return dt1.format(date);
    }
}