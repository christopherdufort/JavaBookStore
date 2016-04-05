/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;

import com.g3w16.entities.exceptions.RollbackFailureException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import static org.assertj.core.api.Assertions.assertThat;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Ignore;

/**
 *
 * @author Rita Lazaar
 */

@RunWith(Arquillian.class)
public class InvoiceDetailJpaControllerTest {

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
                .addAsResource("create_and_seed_tables.sql")
                .addAsLibraries(dependencies);

        return webArchive;
    }
    @Resource(name = "java:app/jdbc/g3w16")
    private DataSource ds;

    @Inject
    private InvoiceDetailJpaController invoiceDetailJpaController;

    /**
     * Test of create method, of class InvoiceDetailJpaController.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");

        InvoiceDetail invoiceDetail = new InvoiceDetail(null,5);
        Book book = new Book(2);
        invoiceDetail.setBookId(book);
        invoiceDetail.setBookPrice(BigDecimal.ONE);
        invoiceDetail.setGst(BigDecimal.ONE);
        invoiceDetail.setHst(BigDecimal.ONE);
        invoiceDetail.setInvoiceId(new Invoice(2));
        invoiceDetailJpaController.create(invoiceDetail);

        InvoiceDetail retrieved = invoiceDetailJpaController.findInvoiceDetail(6);

        assertThat(invoiceDetail).isEqualTo(retrieved);

    }

    /**
     * Test of findInvoiceDetailEntities method, of class
     * InvoiceDetailJpaController.
     */
    @Test
    public void testFindInvoiceDetailEntitiesNoArgs() {
        System.out.println("findInvoiceDetailEntities");

        List<InvoiceDetail> result = invoiceDetailJpaController.findInvoiceDetailEntities();
        assertThat(result.size()).isEqualTo(5);

    }
    
    /**
     * Test of findInvoiceDetailEntities method, of class
     * InvoiceDetailJpaController.
     */
    @Test
    public void testFindInvoiceDetailEntitiesWithOne() throws Exception {
        System.out.println("findInvoiceDetailEntities");

        InvoiceDetail invoiceDetail = new InvoiceDetail(null,5);
        Book book = new Book(4);
        invoiceDetail.setBookId(book);
        invoiceDetail.setBookPrice(BigDecimal.ONE);
        invoiceDetail.setGst(BigDecimal.ONE);
        invoiceDetail.setHst(BigDecimal.ONE);
        invoiceDetail.setInvoiceId(new Invoice(3));
        invoiceDetailJpaController.create(invoiceDetail);
        List<InvoiceDetail> result = invoiceDetailJpaController.findInvoiceDetailEntities();
        assertThat(result.size()).isEqualTo(6);

    }

    /**
     * Test of findInvoiceDetailEntities method, of class
     * InvoiceDetailJpaController.
     */
    @Test
    public void testFindInvoiceDetailEntitiesWithNewCreated() throws Exception {
        System.out.println("findInvoiceDetailEntities");

        InvoiceDetail invoiceDetail = new InvoiceDetail(null,5);
        Book book = new Book(2);
        invoiceDetail.setBookId(book);
        invoiceDetail.setBookPrice(BigDecimal.ONE);
        invoiceDetail.setGst(BigDecimal.ONE);
        invoiceDetail.setHst(BigDecimal.ONE);
        invoiceDetail.setInvoiceId(new Invoice(2));
        
        InvoiceDetail invoiceDetail2 = new InvoiceDetail(null,5);
        invoiceDetail2.setBookId(book);
        invoiceDetail2.setBookPrice(BigDecimal.ONE);
        invoiceDetail2.setGst(BigDecimal.ONE);
        invoiceDetail2.setHst(BigDecimal.ONE);
        invoiceDetail2.setInvoiceId(new Invoice(2));
        
        InvoiceDetail invoiceDetail3 = new InvoiceDetail(null,5);
        invoiceDetail3.setBookId(book);
        invoiceDetail3.setBookPrice(BigDecimal.ONE);
        invoiceDetail3.setGst(BigDecimal.ONE);
        invoiceDetail3.setHst(BigDecimal.ONE);
        invoiceDetail3.setInvoiceId(new Invoice(2));
        
        invoiceDetailJpaController.create(invoiceDetail);
        invoiceDetailJpaController.create(invoiceDetail2);
        invoiceDetailJpaController.create(invoiceDetail3);

        List<InvoiceDetail> result = invoiceDetailJpaController.findInvoiceDetailEntities();
        assertThat(result.size()).isEqualTo(8);

    }

    /**
     * Test of findInvoiceDetail method, of class InvoiceDetailJpaController.
     */
    @Test
    public void testFindInvoiceDetail() {
        System.out.println("findInvoiceDetail");

        InvoiceDetail result = invoiceDetailJpaController.findInvoiceDetail(2);
        assertThat(result.getInvoiceDetailId()).isEqualTo(2);
    }
    
    @Test
    public void testFindInvoiceDetailByInvoice() {
        System.out.println("findInvoiceDetail");

       List<InvoiceDetail> result = invoiceDetailJpaController.findInvoiceDetailByInvoice(new Invoice(1));
        assertThat(result.size()).isEqualTo(2);
    }
    
//    @Test 
//    public void testFindInvoiceFails1() {
//    
//        
//        System.out.println("failing find invoice process");
//        
//        List <InvoiceDetail> result = invoiceDetailJpaController.findInvoiceDetailEntities();
//        // all invoice details are present but since they are being deleted
//        // this cannot be found in the database 
//        
//    }
    
     
   
   


    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    

// ---- END OF THE TESTS ---
    /**
     * This routine is courtesy of Bartosz Majsak who also solved my Arquillian
     * remote server problem
     */
    @Before
    public void seedDatabase() {
        final String seedDataScript = loadAsString("create_and_seed_tables.sql");

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

    @After
    public void tearDown() throws Exception {
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
