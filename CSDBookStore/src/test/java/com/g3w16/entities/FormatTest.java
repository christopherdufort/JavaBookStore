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
import java.util.ArrayList;
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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

/**
 *
 * @author Jonas faure
 */
@RunWith(Arquillian.class)
public class FormatTest {

    @Inject
    private FormatJpaController formatController;


    /**
     * Test of creation method for entity : Format
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testCreateFormat() throws Exception {
        // TODO: further testing should imply books
        System.out.println("> Format > Creation");
        Format format = new Format();
        int unexpectedResult = -1;
        format.setFormatId(unexpectedResult);
        format.setExtension(".lol");
        format.setBookList(new ArrayList<>());
        formatController.create(format);
        assertFalse(format.getFormatId()==unexpectedResult);
    }

    /**
     * Test of edition method for entity : Format
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testEditFormat() throws Exception {
        // TODO: test invalid params too
        System.out.println("> Format > Edition");
        Format format = new Format(
                1, ".lol"
        );
        format.setBookList(new ArrayList<>());
        formatController.edit(format);
        assertEquals(format.getExtension(), ".lol");
        assertNotEquals(format.getExtension(), ".epub");
    }
    
    /**
     * Test of edition method for entity : Format
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testEditIncorrectFormat() throws Exception {
        System.out.println("> Format > Edition ( incorrect )");
        Format format = new Format(
                -1, "Jonas Faure"
        );
        format.setBookList(new ArrayList<>());
        try{
            formatController.edit(format);
        }catch (Exception e){
            return;
        }
        fail("this should trigger an exception since the ID does not exists");
    }

    /**
     * Test of destruction method for entity : Format
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testDestroyFormat() throws Exception{
        System.out.println("> Format > Destruction");        
        formatController.destroy(1);
        assertTrue(formatController.getFormatCount()==10);
    }

    /**
     * Test of fetching method for entity : Format
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindAllFormat() throws Exception{
        // TODO: more testing needed
        System.out.println("> Format > Fetching ( all )");
        List<Format> formats= formatController.findFormatEntities();
        assertEquals(formats.size(), 11);
    }

    /**
     * Test of fetching method for entity : Format
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindAllInRangeFormat() throws Exception {
        // TODO: more testing needed
        System.out.println("> Format > Fetching ( all, in range )");
        List<Format> formats = formatController.findFormatEntities(4, 0);
        assertEquals(formats.size(), 4);
    }

    /**
     * Test of fetching method for entity : Format
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindByIdFormat() throws Exception {
        // TODO: more testing needed
        System.out.println("> Format > Fetching ( one, by id )");
        Format format = formatController.findFormat(1);
        assertEquals(format.getExtension(), ".epub");
        assertTrue(format.getFormatId() == 1); // <- I would be terrified if it wouldn't pass ..
    }

    /**
     * Test of fetching method for entity : Format
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testCountFormat() throws Exception {
        // TODO: possibly more thing needed here ..
        System.out.println("> Format > Fetching ( count )");
        // below the SQL statement used to check this value 
        
        // SELECT COUNT(format_id) FROM format;
        assertTrue(11 == formatController.getFormatCount());
    }
    
    /**
     * Test of fetching method for entity : Format
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindByNameFormat() throws Exception {
        //TODO: more testing needed
        System.out.println("> Format > Fetching  ( some, by name )");
        List<Format> formats = formatController.findFormatEntitiesLike("h");
        assertTrue(formats.size() == 2);
    }
    
    /**
     * Test of fetching method for entity : Format
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindByBookFormat() throws Exception {
        //TODO: more testing needed
        System.out.println("> Format > Fetching ( some, by book )");
        Book book = new Book();
        book.setBookId(1);
        List<Format> formats = formatController.findFormatEntitiesByBook(book);
        assertTrue(formats.size() == 2);
    }
    
    /**
     * Test of fetching method for entity : Format
     * @author Jonas Faure
     * @throws java.lang.Exception 
     */
    @Test
    public void testFindByGenreFormat() throws Exception {
        //TODO: more testing needed
        System.out.println("> Format > Fetching ( some, by genre )");
        Genre genre = new Genre();
        genre.setGenreId(1);
        List<Format> formats = formatController.findFormatEntitiesByGenre(genre);
        // 9 different format in Genre 'Religion & Spirituality' -> genre_id = 1
        // below the SQL statement used to check that value : 
        
        // USE g3w16;
        // -- count how many different authors there's for genre_id=4
        // SELECT count(DISTINCT author_id) FROM book_author ba WHERE EXISTS(
        //      SELECT 1 FROM book_genre bg WHERE bg.book_id = ba.book_id AND bg.genre_id=4
        // );
        assertTrue(formats.size()==9);
    }
    
    /**
     * Test of fetching method for entity : Format
     * @author Jonas Faure
     * @throws java.lang.Exception 
     */
    @Test
    public void testFindByAuthorFormat() throws Exception {
        //TODO: more testing needed
        System.out.println("> Format > Fetching ( some, by author )");
        Author author = new Author();
        author.setAuthorId(5);
        List<Format> formats = formatController.findFormatEntitiesByAuthor(author);
        // 3 different authors in Author 'Peter Weverka' -> author_id=1
        // below the SQL statement used to check that value :
        
        // USE g3w16;
        // -- count how many different authors there's for format_id=2
        // SELECT count(bfformat_id) FROM book_format bf WHERE EXISTS(
        //     SELECT 1 FROM book_author ba WHERE bf.book_id = ba.book_id AND ba.author_id=5
        // );
        assertTrue(formats.size()==3);
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
                .addAsResource("seed_tables.sql")
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
