package com.g3w16.beans;

import com.g3w16.entities.Book;
import com.g3w16.entities.Invoice;
import com.g3w16.entities.InvoiceDetail;
import com.g3w16.entities.InvoiceDetailJpaController;
import com.g3w16.entities.InvoiceJpaController;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * backing bean for checkout.xhtml
 * Manages the checkout of a user's order by validating billing info, credit card info
 * and creates an invoice.
 *
 * @author Giuseppe Campanelli
 */
@Named("checkoutBB")
@SessionScoped
public class CheckoutBackingBean implements Serializable {

    private List<Book> order = new ArrayList<Book>();
    private BigDecimal subtotal;
    private BigDecimal governmentTax = new BigDecimal(0);
    private BigDecimal provincialTax = new BigDecimal(0);
    private BigDecimal total = new BigDecimal(0);
    private String cardNumber;
    private String nameOnCard;
    private int expiryMonth = 1;
    private int expiryYear = ((new Date().getYear()+1900));
    private String securityCode;

    @Inject
    private AuthenticatedUser user;
    
    @Inject
    private CartBackingBean cartBB;
    
    @Inject
    private InvoiceJpaController invoiceJpaController;
    @Inject
    private InvoiceDetailJpaController invoiceDetailJpaController;

    /**
     * Sets the order for checkout
     * 
     * @param order order to check out
     */
    public void setOrder(List<Book> order) {
        this.order = order;
    }

    /**
     * Sets the subtotal of the order
     * 
     * @param subtotal subtotal of order
     */
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * Gets the order to check out.
     * 
     * @return order to check out
     */
    public List<Book> getOrder() {
        return order;
    }

    /**
     * Gets the subtotal of the order
     * 
     * @return subtotal of the order
     */
    public BigDecimal getSubtotal() {
        return subtotal;
    }

    /**
     * Gets the credit card number
     * 
     * @return the cardNumber
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Sets the credit card number
     * 
     * @param cardNumber the cardNumber to set
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Gets the name of the credit card
     * 
     * @return the nameOnCard
     */
    public String getNameOnCard() {
        return nameOnCard;
    }

    /**
     * Sets the name on the card
     * 
     * @param nameOnCard the nameOnCard to set
     */
    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    /**
     * Gets the expiry month of the card
     * 
     * @return the expiryMonth
     */
    public int getExpiryMonth() {
        return expiryMonth;
    }

    /**
     * Sets the expiry month on the card
     * 
     * @param expiryMonth the expiryMonth to set
     */
    public void setExpiryMonth(int expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    /**
     * Gets the expiry year of the card
     * 
     * @return the expiryYear
     */
    public int getExpiryYear() {
        return expiryYear;
    }

    /**
     * Sets the expiry month of the card
     * 
     * @param expiryYear the expiryYear to set
     */
    public void setExpiryYear(int expiryYear) {
        this.expiryYear = expiryYear;
    }

    /**
     * Gets the security code of the card
     * 
     * @return the securityCode
     */
    public String getSecurityCode() {
        return securityCode;
    }

    /**
     * Sets the security code of the card
     * 
     * @param securityCode the securityCode to set
     */
    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    /**
     * Calculates the government tax
     * 
     * @param gst government tax
     * 
     * @return gst tax
     */
    public BigDecimal calculateGst(BigDecimal gst) {
        governmentTax = (subtotal.multiply(gst)).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_UP);
        return governmentTax;
    }

    /**
     * Calculates the provincial tax
     * 
     * @param provincialTax tax of the province
     * 
     * @return provincial tax
     */
    public BigDecimal calculateProvincialTax(BigDecimal provincialTax) {
        this.provincialTax = (subtotal.multiply(provincialTax)).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_UP);
        return this.provincialTax;
    }

    /**
     * Calculates the total cost of the order
     * 
     * @return total cost of order
     */
    public BigDecimal calcTotal() {
        total = subtotal.add(governmentTax).add(provincialTax).setScale(2, BigDecimal.ROUND_UP);
        return total;
    }
    
    /**
     * Completes the purchase(order) of books.
     */
    public String confirmPurchase() throws Exception {
        Invoice invoice = new Invoice();
        invoice.setSaleDate(new Date());
        invoice.setUserId(user.getRegisteredUser().getUserId());
        invoice.setTotalNetValueOfSale(subtotal);
        invoice.setTotalGrossValueOfSale(total);
        //add invoice to db, keep using id for each individual invoice detail
        invoiceJpaController.create(invoice);
        
        BigDecimal gst = user.getRegisteredUser().getProvinceId().getGst();
        String provincialType;
        BigDecimal provincialTax;
        if (user.getRegisteredUser().getProvinceId().getPst().equals(new BigDecimal(0.0))) {
            //hst
            provincialType = "hst";
            provincialTax = user.getRegisteredUser().getProvinceId().getHst();
        } else {
            //pst
            provincialType = "pst";
            provincialTax = user.getRegisteredUser().getProvinceId().getPst();
        }
        
        int orderSize = order.size();
        InvoiceDetail invoiceDetail;
        for (int i = 0; i < orderSize; i++) {
            invoiceDetail = new InvoiceDetail();
            invoiceDetail.setInvoiceId(invoice);
            invoiceDetail.setBookId(order.get(i));
            invoiceDetail.setBookPrice(order.get(i).getSalePrice());
            invoiceDetail.setGst((((order.get(i).getSalePrice()).multiply(gst)).divide(new BigDecimal(100))).setScale(2, BigDecimal.ROUND_UP));
            if (provincialType.equals("hst")) {
                invoiceDetail.setHst((((order.get(i).getSalePrice()).multiply(provincialTax)).divide(new BigDecimal(100))).setScale(2, BigDecimal.ROUND_UP));
                invoiceDetail.setPst(new BigDecimal(BigInteger.ZERO));
            }
            else {
                invoiceDetail.setPst((((order.get(i).getSalePrice()).multiply(provincialTax)).divide(new BigDecimal(100))).setScale(2, BigDecimal.ROUND_UP));
                invoiceDetail.setHst(new BigDecimal(BigInteger.ZERO));
            }
            invoiceDetail.setQuantity(1);
            invoiceDetailJpaController.create(invoiceDetail);
        }
        
        cardNumber = "";
        nameOnCard = "";
        expiryMonth = 1;
        expiryYear = ((new Date().getYear()+1900));
        securityCode = "";
        
        cartBB.clearCart();
        return "home";
    }
}