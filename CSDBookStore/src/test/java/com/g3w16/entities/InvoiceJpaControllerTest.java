/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import com.g3w16.entities.exceptions.RollbackFailureException;
import com.sun.jdo.spi.persistence.utility.logging.Logger;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

/**
 *
 * @author Rita Lazaar
 */
@RunWith(Arquillian.class)
public class InvoiceJpaControllerTest {

    public InvoiceJpaControllerTest() {
    }

    @Deployment
    public static WebArchive deploy() {
        // Use an alternative to the JUnit assert library called AssertJ
        // Need to reference MySQL driver as it is not part of either embedded or remote TomEE
        final File[] dependencies = Maven
                .resolver()
                .loadPomFromFile("pom.xml")
                .resolve("mysql:mysql-connector-java",
                        "org.assertj:assertj-core").withoutTransitivity()
                .asFile();

        // For testing Arquillian prefers a resources.xml file over acontext.xml
        // Actual file name is resources-mysql-ds.xml in the test/resources folder
        // The SQL script to create the database is also in this folder
        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class)
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                //.addPackage(CSDBookStoreDAOImpl.class.getPackage())
                .addPackage(AdJpaController.class.getPackage())
                .addPackage(Ad.class.getPackage())
                .addPackage(RollbackFailureException.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/main/setup/glassfish-resources.xml"), "glassfish-resources.xml")
                .addAsResource(new File("src/main/resources/META-INF/persistence.xml"), "META-INF/persistence.xml")
                .addAsResource("seed_tables.sql")
                .addAsLibraries(dependencies);

        return webArchive;
    }
    @Resource(name = "java:app/jdbc/g3w16")
    private DataSource ds;

    @Inject
    private Invoice invoice;

    @Inject
    private InvoiceJpaController invoiceJpaController;
    
    /**
     * @author Rita Lazaar 
     * 
     * This is testing the creating a new invoice. 
     */
    @Test
    public void testCreate() throws Exception {
        
        System.out.println("create");
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(100);
        invoice.setSaleDate(Date.from(Instant.MIN));
        invoice.setTotalGrossValueOfSale(BigDecimal.ZERO);
        invoice.setTotalNetValueOfSale(BigDecimal.ONE);
        invoice.setUserNumber(1);
        
       
        invoiceJpaController.create(invoice);
        
        Invoice retrieved =  invoiceJpaController.findInvoice(100);
        
        assertEquals(invoice.getInvoiceId(), retrieved.getInvoiceId());
        
      
    }


    /**
     * Test of findInvoiceEntities method, of class InvoiceJpaController.
     * Finding all invoices. 
     */
    @Test
    public void testFindInvoiceEntities_0args() {
        System.out.println("findInvoiceEntities");
       
        List<Invoice> expResult = null;
        List<Invoice> result = invoiceJpaController.findInvoiceEntities();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of findInvoiceEntities method, of class InvoiceJpaController.
     */
    @Test
    public void testFindInvoiceEntities_int_int() {
        System.out.println("findInvoiceEntities");
        int maxResults = 0;
        int firstResult = 0;
        InvoiceJpaController instance = null;
        List<Invoice> expResult = null;
        List<Invoice> result = invoiceJpaController.findInvoiceEntities(maxResults, firstResult);
        assertEquals(expResult, result);
      
    }

    /**
     * Test of findInvoice method, of class InvoiceJpaController.
     */
    @Test
    public void testFindInvoice() {
        System.out.println("findInvoice");
        Integer id = null;
        InvoiceJpaController instance = null;
        Invoice expResult = null;
        Invoice result = invoiceJpaController.findInvoice(id);
        assertEquals(expResult, result);
       
    }

    /**
     * Test of getInvoiceCount method, of class InvoiceJpaController.
     */
    @Test
    public void testGetInvoiceCount() {
        System.out.println("getInvoiceCount");
        InvoiceJpaController instance = null;
        int expResult = 0;
        int result = invoiceJpaController.getInvoiceCount();
        assertEquals(expResult, result);
       
    }
// --- END OF TESTING  ----- 
    
    /**
     * This routine is courtesy of Bartosz Majsak who also solved my Arquillian
     * remote server problem
     */
    @Before
    public void seedDatabase() {
        final String seedDataScript = loadAsString("seed_tables.sql");

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
