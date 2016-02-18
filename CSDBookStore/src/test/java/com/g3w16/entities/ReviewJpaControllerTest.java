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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Ignore;

/**
 *
 * @author 1232046
 */
@RunWith(Arquillian.class)
public class ReviewJpaControllerTest {

    private Logger logger = Logger.getLogger(ReviewJpaControllerTest.class.getName());

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

    @Resource(name = "java:app/jdbc/g3w16")
    private DataSource ds;

    @Inject
    private ReviewJpaController reviewJpaController;

    /**
     * Test of create method, of class ReviewJpaController.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = dateFormater.parse("2016-02-17 16:06:00");
        Review review = new Review();
        review.setReviewText("hello");
        review.setRating(5);
        review.setDateSubmitted(date);
        reviewJpaController.create(review);
        int result = reviewJpaController.getReviewCount();
        assertThat(result).isEqualTo(45);

    }

    /**
     * Test of edit method, of class ReviewJpaController.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Review review = reviewJpaController.findReview(1);
        review.setRating(5);
        reviewJpaController.edit(review);
        Review new_review = reviewJpaController.findReview(1);
        assertThat(new_review.getRating()).isEqualTo(5);
    }

    /**
     * Test of destroy method, of class ReviewJpaController.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        Integer id = 10;
        reviewJpaController.destroy(id);
        int result = reviewJpaController.getReviewCount();
        assertThat(result).isEqualTo(43);
    }

    /**
     * Test of findReviewEntities method, of class ReviewJpaController.
     */
    @Test
    public void testFindReviewEntities_0args() {
        System.out.println("findReviewEntities");
        List<Review> expResult = reviewJpaController.findReviewEntities();
        assertThat(expResult).hasSize(44);

    }

    /**
     * Test of findReviewEntities method, of class ReviewJpaController.
     */
    @Test
    public void testFindReviewEntities_int_int() {
        System.out.println("findReviewEntities");
        int maxResults = 10;
        int firstResult = 0;
        List<Review> expResult = reviewJpaController.findReviewEntities(maxResults, firstResult);
        assertThat(expResult).hasSize(10);
    }

    /**
     * Test of findReview method, of class ReviewJpaController.
     */
    @Test
    public void testFindReview() {
        System.out.println("findReview");
        int reviewId = 1;
        Review expResult = reviewJpaController.findReview(reviewId);
        assertThat(expResult.getReviewId()).isEqualTo(reviewId);
    }

    /**
     * Test of findReviewByUserId method, of class ReviewJpaController.
     */
    @Test
    public void testFindReviewByUserId() {
        System.out.println("findReviewByUserId");
        RegisteredUser user = new RegisteredUser();
        user.setUserId(1);
        List<Review> expResult = reviewJpaController.findReviewByUserId(user);
        assertThat(expResult).hasSize(39);
    }

    /**
     * Test of findReviewByDateSubmitted method, of class ReviewJpaController.
     */
    @Test
    public void testFindReviewByDateSubmitted() throws ParseException {
        System.out.println("findReviewByDateSubmitted");
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = dateFormater.parse("2015-12-24 00:00:00");
        List<Review> expResult = reviewJpaController.findReviewByDateSubmitted(date);
        assertThat(expResult).hasSize(1);
    }

    /**
     * Test of findReviewByApprovalId method, of class ReviewJpaController.
     */
    @Test
    public void testFindReviewByApprovalId() {
        System.out.println("findReviewByApprovalId");
        Approval approval = new Approval();
        approval.setApprovalId(2);
        List<Review> expResult = reviewJpaController.findReviewByApprovalId(approval);
        assertThat(expResult).hasSize(3);
    }

    /**
     * Test of findReviewByIsbn method, of class ReviewJpaController.
     */
    @Test
    public void testFindReviewByIsbn() {
        System.out.println("findReviewByIsbn");
        Book book = new Book();
        book.setIsbn("978-0679600213");
        List<Review> expResult = reviewJpaController.findReviewByIsbn(book);
        assertThat(expResult).hasSize(3);
    }

    /**
     * Test of findReviewByRating method, of class ReviewJpaController.
     */
    @Test
    public void testFindReviewByRating() {
        System.out.println("findReviewByRating");
        List<Review> expResult = reviewJpaController.findReviewByRating(5);
        assertThat(expResult).hasSize(19);
    }

    /**
     * Test of getReviewCount method, of class ReviewJpaController.
     */
    @Test
    public void testGetReviewCount() {
        int result = reviewJpaController.getReviewCount();
        assertThat(result).isEqualTo(44);
    }
    //-----------------------------------------------------END OF TEST METHODS----------------------------------

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
