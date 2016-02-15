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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author 1232046
 */
public class ReviewJpaControllerTest {
    
@Deployment
    public static WebArchive deploy(){
        // Use an alternative to the JUnit assert library called AssertJ
        // Need to reference MySQL driver as it is not part of either
        // embedded or remote TomEE
        final File[] dependencies = Maven
                .resolver()
                .loadPomFromFile("pom.xml")
                .resolve("mysql:mysql-connector-java",
                        "org.assertj:assertj-core").withoutTransitivity()
                .asFile();
        
        // For testing Arquillian prefers a resources.xml file over a
        // context.xml
        // Actual file name is resources-mysql-ds.xml in the test/resources
        // folder
        // The SQL script to create the database is also in this folder
        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class)
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                //.addPackage(CSDBookStoreDAOImpl.class.getPackage())
                .addPackage(ReviewJpaController.class.getPackage())
                .addPackage(Review.class.getPackage())
                .addPackage(RollbackFailureException.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/main/setup/glassfish-resources.xml"), "glassfish-resources.xml")
                .addAsResource(new File("src/main/resources/META-INF/persistence.xml"), "META-INF/persistence.xml")
                .addAsResource("seed_tables.sql")
                .addAsLibraries(dependencies);

        return webArchive;
    }
    
    @Resource(name="java:app/jdbc/g3w16")
    private DataSource ds;

    @Inject
    private Review review;
    
    @Inject
    private ReviewJpaController reviewJpaController;
    /**
     * Test of getEntityManager method, of class ReviewJpaController.
     */
    @Test
    public void testGetEntityManager() {
        System.out.println("getEntityManager");
        ReviewJpaController instance = null;
        EntityManager expResult = null;
        EntityManager result = instance.getEntityManager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of create method, of class ReviewJpaController.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Review review = null;
        ReviewJpaController instance = null;
        instance.create(review);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class ReviewJpaController.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Review review = null;
        ReviewJpaController instance = null;
        instance.edit(review);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class ReviewJpaController.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        Integer id = null;
        ReviewJpaController instance = null;
        instance.destroy(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findReviewEntities method, of class ReviewJpaController.
     */
    @Test
    public void testFindReviewEntities_0args() {
        System.out.println("findReviewEntities");
        ReviewJpaController instance = null;
        List<Review> expResult = null;
        List<Review> result = instance.findReviewEntities();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findReviewEntities method, of class ReviewJpaController.
     */
    @Test
    public void testFindReviewEntities_int_int() {
        System.out.println("findReviewEntities");
        int maxResults = 0;
        int firstResult = 0;
        ReviewJpaController instance = null;
        List<Review> expResult = null;
        List<Review> result = instance.findReviewEntities(maxResults, firstResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findReview method, of class ReviewJpaController.
     */
    @Test
    public void testFindReview() {
        System.out.println("findReview");
        Integer id = null;
        ReviewJpaController instance = null;
        Review expResult = null;
        Review result = instance.findReview(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findReviewByUserId method, of class ReviewJpaController.
     */
    @Test
    public void testFindReviewByUserId() {
        System.out.println("findReviewByUserId");
        Integer userId = null;
        ReviewJpaController instance = null;
        Review expResult = null;
        Review result = instance.findReviewByUserId(userId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findReviewByDateSubmitted method, of class ReviewJpaController.
     */
    @Test
    public void testFindReviewByDateSubmitted() {
        System.out.println("findReviewByDateSubmitted");
        Date dateSubmitted = null;
        ReviewJpaController instance = null;
        Review expResult = null;
        Review result = instance.findReviewByDateSubmitted(dateSubmitted);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findReviewByApprovalId method, of class ReviewJpaController.
     */
    @Test
    public void testFindReviewByApprovalId() {
        System.out.println("findReviewByApprovalId");
        Integer approvalId = null;
        ReviewJpaController instance = null;
        Review expResult = null;
        Review result = instance.findReviewByApprovalId(approvalId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findReviewByIsbn method, of class ReviewJpaController.
     */
    @Test
    public void testFindReviewByIsbn() {
        System.out.println("findReviewByIsbn");
        Book isbn = null;
        ReviewJpaController instance = null;
        Review expResult = null;
        Review result = instance.findReviewByIsbn(isbn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReviewCount method, of class ReviewJpaController.
     */
    @Test
    public void testGetReviewCount() {
        System.out.println("getReviewCount");
        ReviewJpaController instance = null;
        int expResult = 0;
        int result = instance.getReviewCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
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
