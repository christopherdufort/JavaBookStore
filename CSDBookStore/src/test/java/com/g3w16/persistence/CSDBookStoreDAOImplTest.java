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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rita Lazaar
 * @version 0.0.1
 */
public class CSDBookStoreDAOImplTest {
    
    @Resource(name = "java:app/jdbc/g3w16")
    private DataSource ds;

    @Inject
    private CSDBookStoreDAO csdBookStoreDAO;

 

    /**
     * Test of createInvoice method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testCreateInvoice() throws Exception {
        System.out.println("createInvoice");
        
        InvoiceBean invoice = new InvoiceBean();
        invoice.setSaleNumber(1);
        invoice.setSaleDate(LocalDateTime.now());
        invoice.setTotalGrossValueOfSale(15.99);
        invoice.setTotalNetValueOfSale(13.99);
        
        int expResult = 1;
        int result = csdBookStoreDAO.createInvoice(invoice);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of findAllInvoices method, of class CSDBookStoreDAOImpl.
     */
    @Test
    public void testFindAllInvoices() throws Exception {
        System.out.println("findAllInvoices");
        
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        ArrayList<InvoiceBean> expResult = null;
        ArrayList<InvoiceBean> result = csdBookStoreDAO.findAllInvoices();
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
        ReviewBean reviewBean = new ReviewBean();
        reviewBean.setDate_submitted(LocalDateTime.now());
        reviewBean.setIsbn("978-1501104565");
        reviewBean.setApproval_id(1);
        reviewBean.setRating(4);
        reviewBean.setUser_id(1);
        reviewBean.setReview_text("good book");
        reviewBean.setReview_title("perfect");
        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
        int expResult = 1;
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
     /**
     * This routine is courtesy of Bartosz Majsak who also solved my Arquillian
     * remote server problem
     */
    @Before
    public void seedDatabase() {
        final String seedDataScript = loadAsString("createCSDBookStoreSQL.sql");

        try (Connection connection = ds.getConnection()) {
            for (String statement : splitStatements(new StringReader(
                    seedDataScript), ";")) {
                connection.prepareStatement(statement).execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed seeding database", e);
        }
        //System.out.println("Seeding works");

    }

    /**
     * The following methods support the seedDatabse method
     */
    private String loadAsString(final String path) {
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(path)) {
            return new Scanner(inputStream).useDelimiter("\\A").next();
        } catch (IOException e) {
            throw new RuntimeException("Unable to close input stream.", e);
        }
    }

    private List<String> splitStatements(Reader reader,
            String statementDelimiter) {
        final BufferedReader bufferedReader = new BufferedReader(reader);
        final StringBuilder sqlStatement = new StringBuilder();
        final List<String> statements = new LinkedList<>();
        try {
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || isComment(line)) {
                    continue;
                }
                sqlStatement.append(line);
                if (line.endsWith(statementDelimiter)) {
                    statements.add(sqlStatement.toString());
                    sqlStatement.setLength(0);
                }
            }
            return statements;
        } catch (IOException e) {
            throw new RuntimeException("Failed parsing sql", e);
        }
    }

    private boolean isComment(final String line) {
        return line.startsWith("--") || line.startsWith("//")
                || line.startsWith("/*");
    }
}
