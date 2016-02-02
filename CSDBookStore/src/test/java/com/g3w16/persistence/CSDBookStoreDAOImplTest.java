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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 1232046
 */
public class CSDBookStoreDAOImplTest {
    
    public CSDBookStoreDAOImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @org.junit.Test
    public void testSomeMethod() {
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createInvoice method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testCreateInvoice() throws Exception {
        System.out.println("createInvoice");
        RegisteredUser user = null;
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        int expResult = 0;
        int result = instance.createInvoice(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAllInvoices method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testFindAllInvoices() throws Exception {
        System.out.println("findAllInvoices");
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        ArrayList<InvoiceBean> expResult = null;
        ArrayList<InvoiceBean> result = instance.findAllInvoices();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAllInvoicesBasedOnUser method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testFindAllInvoicesBasedOnUser() throws Exception {
        System.out.println("findAllInvoicesBasedOnUser");
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        ArrayList<InvoiceBean> expResult = null;
        ArrayList<InvoiceBean> result = instance.findAllInvoicesBasedOnUser();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findInvoiceById method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testFindInvoiceById() throws Exception {
        System.out.println("findInvoiceById");
        InvoiceBean invoice = null;
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        InvoiceBean expResult = null;
        InvoiceBean result = instance.findInvoiceById(invoice);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteInvoice method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testDeleteInvoice() throws Exception {
        System.out.println("deleteInvoice");
        InvoiceBean invoice = null;
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        int expResult = 0;
        int result = instance.deleteInvoice(invoice);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createInvoiceDetails method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testCreateInvoiceDetails() throws Exception {
        System.out.println("createInvoiceDetails");
        InvoiceBean invoice = null;
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        int expResult = 0;
        int result = instance.createInvoiceDetails(invoice);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAllInvoiceDetails method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testFindAllInvoiceDetails() throws Exception {
        System.out.println("findAllInvoiceDetails");
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        ArrayList<InvoiceDetailBean> expResult = null;
        ArrayList<InvoiceDetailBean> result = instance.findAllInvoiceDetails();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findInvoiceDetailById method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testFindInvoiceDetailById() throws Exception {
        System.out.println("findInvoiceDetailById");
        InvoiceDetailBean invoice = null;
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        InvoiceBean expResult = null;
        InvoiceBean result = instance.findInvoiceDetailById(invoice);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findInvoiceDetailsBasedOnInvoice method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testFindInvoiceDetailsBasedOnInvoice() throws Exception {
        System.out.println("findInvoiceDetailsBasedOnInvoice");
        InvoiceBean invoice = null;
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        ArrayList<InvoiceBean> expResult = null;
        ArrayList<InvoiceBean> result = instance.findInvoiceDetailsBasedOnInvoice(invoice);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteInvoiceDetail method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testDeleteInvoiceDetail() throws Exception {
        System.out.println("deleteInvoiceDetail");
        InvoiceBean invoice = null;
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        int expResult = 0;
        int result = instance.deleteInvoiceDetail(invoice);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createReview method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testCreateReview() throws Exception {
        System.out.println("createReview");
        ReviewBean reviewBean = null;
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        int expResult = 0;
        int result = instance.createReview(reviewBean);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReviewById method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testGetReviewById() throws Exception {
        System.out.println("getReviewById");
        int review_id = 0;
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        ReviewBean expResult = null;
        ReviewBean result = instance.getReviewById(review_id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReviewByUserId method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testGetReviewByUserId() throws Exception {
        System.out.println("getReviewByUserId");
        int user_id = 0;
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        List<ReviewBean> expResult = null;
        List<ReviewBean> result = instance.getReviewByUserId(user_id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReviewByDateSubmitted method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testGetReviewByDateSubmitted() throws Exception {
        System.out.println("getReviewByDateSubmitted");
        LocalDateTime date_submitted = null;
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        List<ReviewBean> expResult = null;
        List<ReviewBean> result = instance.getReviewByDateSubmitted(date_submitted);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReviewByApprovalId method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testGetReviewByApprovalId() throws Exception {
        System.out.println("getReviewByApprovalId");
        int approval_id = 0;
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        List<ReviewBean> expResult = null;
        List<ReviewBean> result = instance.getReviewByApprovalId(approval_id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReviewByIsbn method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testGetReviewByIsbn() throws Exception {
        System.out.println("getReviewByIsbn");
        String isbn = "";
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        List<ReviewBean> expResult = null;
        List<ReviewBean> result = instance.getReviewByIsbn(isbn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateReview method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testUpdateReview() throws Exception {
        System.out.println("updateReview");
        ReviewBean reviewBean = null;
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        int expResult = 0;
        int result = instance.updateReview(reviewBean);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteReviewByReviewId method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testDeleteReviewByReviewId() throws Exception {
        System.out.println("deleteReviewByReviewId");
        int review_id = 0;
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        int expResult = 0;
        int result = instance.deleteReviewByReviewId(review_id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteReviewByUserId method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testDeleteReviewByUserId() throws Exception {
        System.out.println("deleteReviewByUserId");
        int user_id = 0;
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        int expResult = 0;
        int result = instance.deleteReviewByUserId(user_id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteReviewByIsbn method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testDeleteReviewByIsbn() throws Exception {
        System.out.println("deleteReviewByIsbn");
        String isbn = "";
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        int expResult = 0;
        int result = instance.deleteReviewByIsbn(isbn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteReviewByDateSubmitted method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testDeleteReviewByDateSubmitted() throws Exception {
        System.out.println("deleteReviewByDateSubmitted");
        LocalDateTime date_submitted = null;
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        int expResult = 0;
        int result = instance.deleteReviewByDateSubmitted(date_submitted);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
