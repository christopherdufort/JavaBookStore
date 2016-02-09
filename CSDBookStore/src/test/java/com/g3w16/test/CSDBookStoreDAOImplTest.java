/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.test;

import com.g3w16.beans.AdBean;
import com.g3w16.persistence.CSDBookStoreDAOImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

/**
 * @author Xin Ma
 * @author Rita Lazaar
 * @author Christopher Dufort
 * @version 0.1.4
 * @since 0.0.1
 */
@RunWith(Arquillian.class)
public class CSDBookStoreDAOImplTest {
    
    @Deployment
    public static WebArchive deploy() {

        // Use an alternative to the JUnit assert library called AssertJ
        // Need to reference MySQL driver as it is not part of either
        // embedded or remote 
        final File[] dependencies = Maven
                .resolver()
                .loadPomFromFile("pom.xml")
                .resolve("mysql:mysql-connector-java",
                        "org.assertj:assertj-core").withoutTransitivity()
                .asFile();
        
        // The webArchive is the special packaging of your project
        // so that only the test cases run on the server or embedded
        // container
        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class)
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addPackage(CSDBookStoreDAOImpl.class.getPackage())
                .addPackage(AdBean.class.getPackage())
                //.addPackage(AuthorBean.class.getPackage())
                //.addPackage(BookBean.class.getPackage())
                //.addPackage(FormatBean.class.getPackage())
                //.addPackage(GenreBean.class.getPackage())
                //.addPackage(InvoiceBean.class.getPackage())
                //.addPackage(InvoiceDetailBean.class.getPackage())
                //.addPackage(ProvinceBean.class.getPackage())
                //.addPackage(RegisteredUserBean.class.getPackage())
                //.addPackage(ReviewBean.class.getPackage())
                //.addPackage(SurveyBean.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/main/setup/glassfish-resources.xml"), "glassfish-resources.xml")
                .addAsResource("createCSDBookStoreSQL.sql")
                .addAsLibraries(dependencies);

        return webArchive;
    }
    
    @Resource(name="java:app/jdbc/g3w16")
    //@Resource(name="jdbc/g3w16")
    private DataSource ds;

    @Inject
    private CSDBookStoreDAOImpl csdBookStoreDAOImpl;

    /**
     * @author Chris
     */
    @Test
    public void always_pass(){
        //If this wont pass nothing will..
        assertTrue(true);
    }
    
//    /**
//    * Test to retrieve a survey by id.
//    *@author Chris
//    */
//    @Test
//    public void find_first_survey() throws SQLException {
//        //long t = System.nanoTime();
//        SurveyBean firstSurvey = csdBookStoreDAOImpl.findSurveyById(1);
//        assertThat(firstSurvey.getQuestion()).isEqualTo("What is your favorite type of book?");
//        //double seconds = (double)(System.nanoTime() - t) / 1000000000.0;
//        //System.out.println("Completed JDBC and Servlet : " + seconds + " seconds.");
//    }
//    
//    
//    /**
//    * Test of createInvoice method, of class CSDBookStoreDAOImpl.
//    * @author Rita
//    */
//    @Test
//    public void testCreateInvoice() throws Exception{
//        System.out.println("createInvoice");
//        InvoiceBean invoice = new InvoiceBean();
//        invoice.setSaleNumber(1);
//        invoice.setSaleDate(LocalDateTime.now());
//        invoice.setTotalGrossValueOfSale(15.99);
//        invoice.setTotalNetValueOfSale(13.99);
//        
//        int expResult = 1;
//        int result = csdBookStoreDAOImpl.createInvoice(invoice);
//        assertEquals(expResult, result);
//    }
//    
//    /**
//     * @author Rita
//     * Test of findAllInvoices method, of class CSDBookStoreDAOImpl.
//     */
//    @Test
//    public void testFindAllInvoices() throws Exception {
//        System.out.println("findAllInvoices");
//        
//         CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
//         ArrayList<InvoiceBean> expResult = null;
//         List<InvoiceBean> result = csdBookStoreDAOImpl.findAllInvoices();
//         assertEquals(expResult, result);
//         // TODO review the generated test code and remove the default call to fail.
//         fail("The test case is a prototype.");
//    }
//    
//    /**
//     * Test of findAllInvoicesBasedOnUser method, of class CSDBookStoreDAOImpl
//     * @author Rita
//     */
//    @Test
//    public void testFindAllInvoicesBasedOnUser() throws Exception {
//        System.out.println("findAllInvoicesBasedOnUser");
//        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
//        ArrayList<InvoiceBean> expResult = null;
//        ArrayList<InvoiceBean> result = instance.findAllInvoicesBasedOnUser();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//    
//    /**
//     * Test of findInvoiceById method, of class CSDBookStoreDAOImpl.
//     * @author Rita
//     */
//    @Test
//    public void testFindInvoiceById() throws Exception {
//        System.out.println("findInvoiceById");
//        InvoiceBean invoice = null;
//        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
//        InvoiceBean expResult = null;
//        InvoiceBean result = instance.findInvoiceById(invoice);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deleteInvoice method, of class CSDBookStoreDAOImpl.
//     * @author Rita
//     */
//    @Test
//    public void testDeleteInvoice() throws Exception {
//        System.out.println("deleteInvoice");
//        InvoiceBean invoice = null;
//        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
//        int expResult = 0;
//        int result = instance.deleteInvoice(invoice);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of createInvoiceDetails method, of class CSDBookStoreDAOImpl.
//     * @author Rita    
//     */
//    @Test
//    public void testCreateInvoiceDetails() throws Exception {
//        System.out.println("createInvoiceDetails");
//        InvoiceBean invoice = null;
//        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
//        int expResult = 0;
//        int result = instance.createInvoiceDetails(invoice);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findAllInvoiceDetails method, of class CSDBookStoreDAOImpl.
//     * @author Rita    
//     */
//    @Test
//    public void testFindAllInvoiceDetails() throws Exception {
//        System.out.println("findAllInvoiceDetails");
//        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
//        ArrayList<InvoiceDetailBean> expResult = null;
//        ArrayList<InvoiceDetailBean> result = instance.findAllInvoiceDetails();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findInvoiceDetailById method, of class CSDBookStoreDAOImpl.
//     * @author Rita        
//     */
//    @Test
//    public void testFindInvoiceDetailById() throws Exception {
//        System.out.println("findInvoiceDetailById");
//        InvoiceDetailBean invoice = null;
//        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
//        InvoiceBean expResult = null;
//        InvoiceBean result = instance.findInvoiceDetailById(invoice);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findInvoiceDetailsBasedOnInvoice method, of class CSDBookStoreDAOImpl.
//     * @author Rita        
//     */
//    @Test
//    public void testFindInvoiceDetailsBasedOnInvoice() throws Exception {
//        System.out.println("findInvoiceDetailsBasedOnInvoice");
//        InvoiceBean invoice = null;
//        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
//        ArrayList<InvoiceBean> expResult = null;
//        ArrayList<InvoiceBean> result = instance.findInvoiceDetailsBasedOnInvoice(invoice);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deleteInvoiceDetail method, of class CSDBookStoreDAOImpl.
//     * @author Rita        
//     */
//    @Test
//    public void testDeleteInvoiceDetail() throws Exception {
//        System.out.println("deleteInvoiceDetail");
//        InvoiceBean invoice = null;
//        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
//        int expResult = 0;
//        int result = instance.deleteInvoiceDetail(invoice);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of createReview method, of class CSDBookStoreDAOImpl.
//     * @author Rita        
//     */
//    @Test
//    public void testCreateReview() throws Exception {
//        System.out.println("createReview");
//        ReviewBean reviewBean = new ReviewBean();
//        reviewBean.setDate_submitted(LocalDateTime.now());
//        reviewBean.setIsbn("978-1501104565");
//        reviewBean.setApproval_id(1);
//        reviewBean.setRating(4);
//        reviewBean.setUser_id(1);
//        reviewBean.setReview_text("good book");
//        reviewBean.setReview_title("perfect");
//        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
//        int expResult = 1;
//        int result = instance.createReview(reviewBean);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getReviewById method, of class CSDBookStoreDAOImpl.
//     * @author Rita        
//     */
//    @Test
//    public void testGetReviewById() throws Exception {
//        System.out.println("getReviewById");
//        int review_id = 0;
//        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
//        ReviewBean expResult = null;
//        ReviewBean result = instance.getReviewById(review_id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getReviewByUserId method, of class CSDBookStoreDAOImpl.
//     * @author Rita        
//     */
//    @Test
//    public void testGetReviewByUserId() throws Exception {
//        System.out.println("getReviewByUserId");
//        int user_id = 0;
//        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
//        List<ReviewBean> expResult = null;
//        List<ReviewBean> result = instance.getReviewByUserId(user_id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getReviewByDateSubmitted method, of class CSDBookStoreDAOImpl.
//     * @author Rita        
//     */
//    @Test
//    public void testGetReviewByDateSubmitted() throws Exception {
//        System.out.println("getReviewByDateSubmitted");
//        LocalDateTime date_submitted = null;
//        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
//        List<ReviewBean> expResult = null;
//        List<ReviewBean> result = instance.getReviewByDateSubmitted(date_submitted);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getReviewByApprovalId method, of class CSDBookStoreDAOImpl.
//     * @author Rita        
//     */
//    @Test
//    public void testGetReviewByApprovalId() throws Exception {
//        System.out.println("getReviewByApprovalId");
//        int approval_id = 0;
//        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
//        List<ReviewBean> expResult = null;
//        List<ReviewBean> result = instance.getReviewByApprovalId(approval_id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getReviewByIsbn method, of class CSDBookStoreDAOImpl.
//     * @author Rita        
//     */
//    @Test
//    public void testGetReviewByIsbn() throws Exception {
//        System.out.println("getReviewByIsbn");
//        String isbn = "";
//        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
//        List<ReviewBean> expResult = null;
//        List<ReviewBean> result = instance.getReviewByIsbn(isbn);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of updateReview method, of class CSDBookStoreDAOImpl.
//     * @author Rita        
//     */
//    @Test
//    public void testUpdateReview() throws Exception {
//        System.out.println("updateReview");
//        ReviewBean reviewBean = null;
//        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
//        int expResult = 0;
//        int result = instance.updateReview(reviewBean);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deleteReviewByReviewId method, of class CSDBookStoreDAOImpl.
//     * @author Rita        
//     */
//    @Test
//    public void testDeleteReviewByReviewId() throws Exception {
//        System.out.println("deleteReviewByReviewId");
//        int review_id = 0;
//        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
//        int expResult = 0;
//        int result = instance.deleteReviewByReviewId(review_id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deleteReviewByUserId method, of class CSDBookStoreDAOImpl.
//     * @author Rita        
//     */
//    @Test
//    public void testDeleteReviewByUserId() throws Exception {
//        System.out.println("deleteReviewByUserId");
//        int user_id = 0;
//        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
//        int expResult = 0;
//        int result = instance.deleteReviewByUserId(user_id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deleteReviewByIsbn method, of class CSDBookStoreDAOImpl.
//     * @author Rita        
//     */
//    @Test
//    public void testDeleteReviewByIsbn() throws Exception {
//        System.out.println("deleteReviewByIsbn");
//        String isbn = "";
//        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
//        int expResult = 0;
//        int result = instance.deleteReviewByIsbn(isbn);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deleteReviewByDateSubmitted method, of class CSDBookStoreDAOImpl.
//     * @author Rita        
//     */
//    @Test
//    public void testDeleteReviewByDateSubmitted() throws Exception {
//        System.out.println("deleteReviewByDateSubmitted");
//        LocalDateTime date_submitted = null;
//        CSDBookStoreDAOImpl instance = new CSDBookStoreDAOImpl();
//        int expResult = 0;
//        int result = instance.deleteReviewByDateSubmitted(date_submitted);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
     /**
     * This routine is courtesy of Bartosz Majsak who also solved my Arquillian
     * remote server problem
     */
    @Before
    public void seedDatabase() {
        final String seedDataScript = loadAsString("createCSDBookStoreSQL.sql");
        //TEST REMOVE THIS WHEN DONE
        if (ds == null)
            System.out.println("DS IS NULL NO CONNECTION THIS IS THE PROBLEM!");
        
        try (Connection connection = ds.getConnection()) {
            for (String statement : splitStatements(new StringReader(
                    seedDataScript), ";")) {
                connection.prepareStatement(statement).execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed seeding database", e);
        }
        System.out.println("Seeding works");

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
