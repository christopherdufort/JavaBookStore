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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Ignore;
/**
 *
 * @author Jonas Faure
 */

@RunWith(Arquillian.class)
public class BookTest {

    @Inject
    private BookJpaController bookController;
    @Inject
    private AuthorJpaController authorController;
    @Inject
    private FormatJpaController formatController;
    @Inject
    private GenreJpaController genreController;
    @Inject
    private InvoiceJpaController invoiceController;
    @Inject
    private InvoiceDetailJpaController invoiceDetailController;
    
    /**
     * Test of creation method for entity : Book
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testCreate() throws Exception {
        // TODO: more testing needed to compare fields ..?
        System.out.println("> Book > creation");
        System.out.println("create");
        Calendar cal = GregorianCalendar.getInstance();
        Date date_publish = cal.getTime();
        int unexpectedValue = -1;
        Book book = new Book(
                unexpectedValue,
                "0000000000000",
                "some title",
                "some publisher",
                date_publish,
                666,
                new BigDecimal(13.12),
                true,
                new BigDecimal(-1.)
        );
        book.setAuthorList(
                authorController.findAuthorEntitiesLike("jo")
        );
        book.setFormatList(new ArrayList<>());
        book.getFormatList().add(formatController.findFormat(1));
        book.setGenreList(new ArrayList<>());
        book.getGenreList().add(genreController.findGenre(1));
        bookController.create(book);
        assertFalse(book.getBookId()==unexpectedValue);
    }

    
    /**
     * Test of edition method for entity : Book
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testEdit_splitAssertOnTitle() throws Exception {
        System.out.println("> Book > Edition");
        Calendar cal = GregorianCalendar.getInstance();
        Date date_publish = cal.getTime();
        
        Book book = new Book(
                2,
                "0000000000000",
                "some title",
                "some publisher",
                date_publish,
                666,
                new BigDecimal(13.12),
                true,
                new BigDecimal(-1.)
        );
        book.setAuthorList(
                authorController.findAuthorEntitiesLike("jo")
        );
        book.setFormatList(new ArrayList<>());
        book.getFormatList().add(formatController.findFormat(1));
        book.setGenreList(new ArrayList<>());
        book.getGenreList().add(genreController.findGenre(1));
        book.setReviewList(new ArrayList<>());
        
        bookController.edit(book);
        assertEquals(book.getTitle(), "some title");
        //assertEquals(book.getIsbn(), "0000000000000");
        //assertEquals(book.getPublisher(), "some publisher");
        //assertEquals(book.getPublishDate(), date_publish);
        // TODO: test date_entered too ..
        //assertTrue(book.getPageNumber()== 666);
        //assertEquals(book.getWholesalePrice(), new BigDecimal(13.12));
        //assertTrue(book.getAvailable());
        //assertEquals(book.getOverallRating(), new BigDecimal(-1.));
    }
    
    /**
     * Test of edition method for entity : Book
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testEdit_splitAssertOnIsbn() throws Exception {
        System.out.println("> Book > Edition");
        Calendar cal = GregorianCalendar.getInstance();
        Date date_publish = cal.getTime();
        
        Book book = new Book(
                2,
                "0000000000000",
                "some title",
                "some publisher",
                date_publish,
                666,
                new BigDecimal(13.12),
                true,
                new BigDecimal(-1.)
        );
        book.setAuthorList(
                authorController.findAuthorEntitiesLike("jo")
        );
        book.setFormatList(new ArrayList<>());
        book.getFormatList().add(formatController.findFormat(1));
        book.setGenreList(new ArrayList<>());
        book.getGenreList().add(genreController.findGenre(1));
        book.setReviewList(new ArrayList<>());
        
        bookController.edit(book);
        assertEquals(book.getIsbn(), "0000000000000");
    }
    
    /**
     * Test of edition method for entity : Book
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testEdit_splitAssertOnPublisher() throws Exception {
        System.out.println("> Book > Edition");
        Calendar cal = GregorianCalendar.getInstance();
        Date date_publish = cal.getTime();
        
        Book book = new Book(
                2,
                "0000000000000",
                "some title",
                "some publisher",
                date_publish,
                666,
                new BigDecimal(13.12),
                true,
                new BigDecimal(-1.)
        );
        book.setAuthorList(
                authorController.findAuthorEntitiesLike("jo")
        );
        book.setFormatList(new ArrayList<>());
        book.getFormatList().add(formatController.findFormat(1));
        book.setGenreList(new ArrayList<>());
        book.getGenreList().add(genreController.findGenre(1));
        book.setReviewList(new ArrayList<>());
        
        bookController.edit(book);
        assertEquals(book.getPublisher(), "some publisher");
    }
    
    /**
     * Test of edition method for entity : Book
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testEdit_splitAssertOnPublishDate() throws Exception {
        System.out.println("> Book > Edition");
        Calendar cal = GregorianCalendar.getInstance();
        Date date_publish = cal.getTime();
        
        Book book = new Book(
                2,
                "0000000000000",
                "some title",
                "some publisher",
                date_publish,
                666,
                new BigDecimal(13.12),
                true,
                new BigDecimal(-1.)
        );
        book.setAuthorList(
                authorController.findAuthorEntitiesLike("jo")
        );
        book.setFormatList(new ArrayList<>());
        book.getFormatList().add(formatController.findFormat(1));
        book.setGenreList(new ArrayList<>());
        book.getGenreList().add(genreController.findGenre(1));
        book.setReviewList(new ArrayList<>());
        
        bookController.edit(book);
        assertEquals(book.getPublishDate(), date_publish);
    }
    
    /**
     * Test of edition method for entity : Book
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testEdit_splitAssertOnPageNumber() throws Exception {
        System.out.println("> Book > Edition");
        Calendar cal = GregorianCalendar.getInstance();
        Date date_publish = cal.getTime();
        
        Book book = new Book(
                2,
                "0000000000000",
                "some title",
                "some publisher",
                date_publish,
                666,
                new BigDecimal(13.12),
                true,
                new BigDecimal(-1.)
        );
        book.setAuthorList(
                authorController.findAuthorEntitiesLike("jo")
        );
        book.setFormatList(new ArrayList<>());
        book.getFormatList().add(formatController.findFormat(1));
        book.setGenreList(new ArrayList<>());
        book.getGenreList().add(genreController.findGenre(1));
        book.setReviewList(new ArrayList<>());
        
        bookController.edit(book);
        assertTrue(book.getPageNumber()== 666);
    }
    
    /**
     * Test of edition method for entity : Book
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testEdit_splitAssertOnWholesalePrice() throws Exception {
        System.out.println("> Book > Edition");
        Calendar cal = GregorianCalendar.getInstance();
        Date date_publish = cal.getTime();
        
        Book book = new Book(
                2,
                "0000000000000",
                "some title",
                "some publisher",
                date_publish,
                666,
                new BigDecimal(13.12),
                true,
                new BigDecimal(-1.)
        );
        book.setAuthorList(
                authorController.findAuthorEntitiesLike("jo")
        );
        book.setFormatList(new ArrayList<>());
        book.getFormatList().add(formatController.findFormat(1));
        book.setGenreList(new ArrayList<>());
        book.getGenreList().add(genreController.findGenre(1));
        book.setReviewList(new ArrayList<>());
        
        bookController.edit(book);
        assertEquals(book.getWholesalePrice(), new BigDecimal(13.12));
    }
    
    /**
     * Test of edition method for entity : Book
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testEdit_splitAssertOnAvailable() throws Exception {
        System.out.println("> Book > Edition");
        Calendar cal = GregorianCalendar.getInstance();
        Date date_publish = cal.getTime();
        
        Book book = new Book(
                2,
                "0000000000000",
                "some title",
                "some publisher",
                date_publish,
                666,
                new BigDecimal(13.12),
                true,
                new BigDecimal(-1.)
        );
        book.setAuthorList(
                authorController.findAuthorEntitiesLike("jo")
        );
        book.setFormatList(new ArrayList<>());
        book.getFormatList().add(formatController.findFormat(1));
        book.setGenreList(new ArrayList<>());
        book.getGenreList().add(genreController.findGenre(1));
        book.setReviewList(new ArrayList<>());
        
        bookController.edit(book);
        assertTrue(book.getAvailable());
    }
    
    /**
     * Test of edition method for entity : Book
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testEdit_splitAssertOnOverallRating() throws Exception {
        System.out.println("> Book > Edition");
        Calendar cal = GregorianCalendar.getInstance();
        Date date_publish = cal.getTime();
        
        Book book = new Book(
                2,
                "0000000000000",
                "some title",
                "some publisher",
                date_publish,
                666,
                new BigDecimal(13.12),
                true,
                new BigDecimal(-1.)
        );
        book.setAuthorList(
                authorController.findAuthorEntitiesLike("jo")
        );
        book.setFormatList(new ArrayList<>());
        book.getFormatList().add(formatController.findFormat(1));
        book.setGenreList(new ArrayList<>());
        book.getGenreList().add(genreController.findGenre(1));
        book.setReviewList(new ArrayList<>());
        
        bookController.edit(book);
        assertEquals(book.getOverallRating(), new BigDecimal(-1.));
    }
    
    /**
     * Test of edition method for entity : Book
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testEditIncorrect() throws Exception {
        System.out.println("> Book > Edition ( incorrect )");
        Calendar cal = GregorianCalendar.getInstance();
        Date date_publish = cal.getTime();
        
        Book book = new Book(
                -1,     // invalid ID
                "0000000000000",
                "some title",
                "some publisher",
                date_publish,
                666,
                new BigDecimal(13.12),
                true,
                new BigDecimal(-1.)
        );
        book.setAuthorList(
                authorController.findAuthorEntitiesLike("jo")
        );
        book.setFormatList(new ArrayList<>());
        book.getFormatList().add(formatController.findFormat(1));
        book.setGenreList(new ArrayList<>());
        book.getGenreList().add(genreController.findGenre(1));
     
        try{
            bookController.edit(book);
        }catch (Exception e){
            return;
        }
        fail("this should trigger an exception since the ID does not exists");
    }

    
    /**
     * Test of destruction method for entity : Book
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("> Book > Destruction");
        bookController.destroy(1);
        // below the SQL statement used to check that value 
        
        // SELECT count(book_id) from g3w16.book;
        assertTrue(bookController.getBookCountAvailable()==102);
    }

    
    /**
     * Test of fetching method for entity : Book
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindAllBook() throws Exception {
        System.out.println("> Book > Fetching ( all )");
        List<Book> books = bookController.findBookEntities();
        // below the SQL statement used to check that value 
        
        // SELECT count(book_id) from g3w16.book;
        assertEquals(books.size(), 103);
    }

    /**
     * Test of fetching method for entity : Book
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindAllBookInRange() throws Exception {
        System.out.println("> Book > Fetching ( all, in range) ");
        List<Book> books = bookController.findBookEntities(20, 0);
        assertEquals(books.size(), 20);
    }

    /**
     * Test of fetching method for entity : Book
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindBookById() throws Exception {
        System.out.println("> Book > Fetching ( one, by id )");
        Book book = bookController.findBookEntitiesById(1);
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        assertTrue(book.getBookId() ==    1);
        assertEquals(book.getIsbn(),    "978-1305635166");
        assertEquals(book.getTitle(),    "Microelectronic Circuits: Analysis and Design");
        assertEquals(book.getPublisher(),    "Thomson-Engineering");
        assertEquals(dateFormater.format(book.getPublishDate()),    "2016-01-11 12:00:00");
        assertEquals(book.getPageNumber(),    1360);
        // can't use assertEquals(book.getWholesalePrice(), new BigDecimal("150.00"));
        // because it triggers some strange issue : 
        //  expected = 150.00, actual = 150 
        //  ...
        //  it looks like it's due to the fact that BigDecimal .equals() is broken because of precision issues
        // the same for all method returning BigDecimal
        assertTrue(book.getWholesalePrice().compareTo(    new BigDecimal("150.00")) == 0);
        assertTrue(book.getListPrice().compareTo(    new BigDecimal("317.95")) == 0 );
        assertTrue(book.getSalePrice().compareTo(    new BigDecimal("309.08")) == 0 );
        assertEquals(dateFormater.format(book.getDateEntered()),    "2016-01-30 12:00:00");
        assertTrue(book.getAvailable());
        assertTrue(book.getOverallRating().compareTo(   new BigDecimal("0")) == 0 );
        assertEquals(book.getSynopsis(),    "'Take a ''breadth-first'' approach to learning electronics with a strong emphasis on design and simulation in MICROELECTRONIC CIRCUITS: ANALYSIS AND DESIGN, 3E. This book introduces the general characteristics of circuits (ICs) to prepare you to effectively use circuit design and analysis techniques. The author then offers a more detailed study of devices and circuits and how they operate within ICs. Important circuits are analyzed in worked-out examples to introduce basic techniques and emphasize the effects of parameter variations. More than half of the problems and examples concentrate on design and use software tools extensively. You learn to apply theory to real-world design problems as you master computer simulations for testing and verifying your designs'");
    }

    /**
     * Test of fetching method for entity : Book
     * @author Jonas Faure
     * @throws java.lang.Exception
     */@Test
    public void testFindBookEntitiesByISBN() throws Exception {
        System.out.println("> Book > Fetching ( some, by ISBN )");
        List<Book> books = bookController.findBookEntitiesByISBN("978-1305635166");
        assertEquals(books.size(), 1);
        //TODO: some more testing might be needed
    }

    /**
     * Test of fetching method for entity : Book
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindBookEntitiesByTitleLike() throws Exception{
        System.out.println("> Book > Fetching ( some, by title )");
        List<Book> books = bookController.findBookEntitiesByTitleLike("Le");
        // below the SQL statement used to check that value
        
        // SELECT count(book_id) FROM g3w16.book WHERE title LIKE "%Le%";
        assertTrue(books.size() == 11);
    }

    /**
     * Test of fetching method for entity : Book
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindBookEntitiesByPublisherLike() throws Exception {
        System.out.println("> Book > Fetching ( some, by publisher )");
        List<Book> books = bookController.findBookEntitiesByPublisherLike("Le");
        // below the SQL statement used to check that value 
        
        // SELECT count(book_id) FROM g3w16.book WHERE publisher LIKE "%Le%";
        assertTrue(books.size() == 12);
    }

    /**
     * Test of fetching method for entity : Book
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindBookEntitiesByFormat() throws Exception{
        System.out.println("> Book > Fetching ( some, by format )");
        Format format = formatController.findFormat(2);
        List<Book> books = bookController.findBookEntitiesByFormat(format);
        // below the SQL statement used to check that value 
        
        // SELECT count(distinct book_id) FROM book_format WHERE format_id=2;
        assertEquals(books.size(), 21);
    }
    
    /**
     * Test of findBookEntitiesByGenre method, of class BookJpaController.
     */
    @Test
    public void testFindBookEntitiesByGenre() throws Exception{
        System.out.println("> Book > Fetching ( some, by genre )");
        Genre genre = genreController.findGenre(2);
        List<Book> books = bookController.findBookEntitiesByGenre(genre);
        // below the SQL statement used to check taht value
        
        // SELECT count(distinct book_id) FROM book_genre WHERE genre_id=2;
        assertEquals(books.size(), 20);
    }

    /**
     * Test of fetching method for entity : Book
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindBookEntitiesByAuthor() throws Exception{
        System.out.println("> Book > Fetching ( some, by author )");
        Author author = authorController.findAuthor(27);
        List<Book> books = bookController.findBookEntitiesByAuthor(author);
        // below the SQL statement used to check that value 
        
        // SELECT count(distinct book_id) FROM book_author WHERE author_id = 27;
        assertEquals(books.size(), 2);
    }

    /**
     * Test of fetching method for entity : Book
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testGetBookCount() throws Exception{
        System.out.println("> Book > Fetching ( count )");
        assertEquals(bookController.getBookCount(), 103);
    }
    
    /**
     * Test of fetching method for entity : Book
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindBookEntitiesByInvoice() throws Exception{
        System.out.println("> Book > Fetching ( some, by invoice )");
        
    }
    
    /**
     * Test of fetching method for entity : Book
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindBookEntitiesByInvoiceDetail() throws Exception{
        System.out.println("> Book > Fetching ( one, by invoiceDetail )");
        InvoiceDetail invoiceDetail = invoiceDetailController.findInvoiceDetail(1);
        assertEquals(bookController.findBookEntitiesByInvoiceDetail(invoiceDetail).getIsbn(), "978-0894864025");
    }
    
    
    
    
    //
    //         ( do not cross this line )
    //
    //                      |
    //                      V
    //
    // --- UNDER HERE IS SOME SETUP STUFF FOR THE DATABASE --- 
    //
    
    
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
                .addPackage(SurveyJpaController.class.getPackage())
                .addPackage(Survey.class.getPackage())
                .addPackage(RollbackFailureException.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/main/setup/glassfish-resources.xml"), "glassfish-resources.xml")
                .addAsResource(new File("src/main/resources/META-INF/persistence.xml"), "META-INF/persistence.xml")
                .addAsResource("create_and_seed_tables.sql")
                .addAsLibraries(dependencies);

        return webArchive;
    }
    
    @Resource(name="java:app/jdbc/g3w16")
    private DataSource ds;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    
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
        //System.out.println("Seeding works");
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
