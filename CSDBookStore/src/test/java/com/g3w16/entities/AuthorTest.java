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
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

/**
 *
 * @author jesuisnuageux
 */
@RunWith(Arquillian.class)
public class AuthorTest {
    
    @Inject
    private AuthorJpaController authorController;


    /**
     * Test of creation method for entity : Author
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testCreateAuthor() throws Exception {
        // TODO: further testing should imply books
        System.out.println("> Author > Creation");
        Author author = new Author();
        int unexpectedResult = -1;
        author.setAuthorId(unexpectedResult);
        author.setAuthorName("Jonas Faure");
        authorController.create(author);
        assertFalse(author.getAuthorId()==unexpectedResult);
    }

    /**
     * Test of edition method for entity : Author
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testEditAuthor() throws Exception {
        // TODO: test invalid params too
        System.out.println("> Author > Edition");
        Author author = new Author(
                1, "Jonas Faure"
        );
        author.setBookList(new ArrayList<>());
        authorController.edit(author);
        assertEquals(author.getAuthorName(), "Jonas Faure");
        assertNotEquals(author.getAuthorName(), "Muhammad H. Rashid");
    }
    
    /**
     * Test of edition method for entity : Author
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testEditIncorrectAuthor() throws Exception {
        System.out.println("> Author > Edition ( incorrect )");
        Author author = new Author(
                -1, "Jonas Faure"
        );
        author.setBookList(new ArrayList<>());
        try{
        authorController.edit(author);
        }catch (Exception e){
            return;
        }
        fail("this should trigger an exception since the ID does not exists");
    }

    /**
     * Test of destruction method for entity : Author
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testDestroyAuthor() throws Exception{
        // TODO: do it
        System.out.println("> Author > Destruction");        
        authorController.destroy(1);
        assertTrue(authorController.getAuthorCount()==143);
    }

    /**
     * Test of fetching method for entity : Author
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindAllAuthor() throws Exception{
        // TODO: more testing needed
        System.out.println("> Author > Fetching ( all )");
        List<Author> authors = authorController.findAuthorEntities();
        assertEquals(authors.size(), 144);
    }

    /**
     * Test of fetching method for entity : Author
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindAllInRangeAuthor() throws Exception {
        // TODO: more testing needed
        System.out.println("> Author > Fetching ( all, in range )");
        List<Author> authors = authorController.findAuthorEntities(20, 0);
        assertEquals(authors.size(), 20);
    }

    /**
     * Test of fetching method for entity : Author
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindByIdAuthor() throws Exception {
        // TODO: more testing needed
        System.out.println("> Author > Fetching ( one, by id )");
        Author author = authorController.findAuthor(1);
        assertEquals(author.getAuthorName(), "Muhammad H. Rashid");
        assertTrue(author.getAuthorId() == 1); // <- I would be terrified if it wouldn't pass ..
    }

    /**
     * Test of fetching method for entity : Author
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testCountAuthor() throws Exception {
        // TODO: possibly more thing needed here ..
        System.out.println("> Author > Fetching ( count )");
        assertTrue(144 == authorController.getAuthorCount());
    }
    
    /**
     * Test of fetching method for entity : Author
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindByNameAuthor() throws Exception {
        //TODO: more testing needed
        System.out.println("> Author > Fetching  ( some, by name )");
        List<Author> authors = authorController.findAuthorEntitiesLike("John");
        assertTrue(authors.size() == 2);
    }
    
    /**
     * Test of fetching method for entity : Author
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindByBookAuthor() throws Exception {
        //TODO: more testing needed
        System.out.println("> Author > Fetching ( some, by book )");
        Book book = new Book();
        book.setBookId(12);
        List<Author> authors = authorController.findAuthorEntitiesByBook(book);
        assertTrue(authors.size() == 3);
    }
    
    /**
     * Test of fetching method for entity : Author
     * @author Jonas Faure
     * @throws java.lang.Exception 
     */
    @Test
    public void testFindByGenreAuthor() throws Exception {
        //TODO: more testing needed
        System.out.println("> Author > Fetching ( some, by genre )");
        Genre genre = new Genre();
        genre.setGenreId(4);
        List<Author> authors = authorController.findAuthorEntitiesByGenre(genre);
        // 42 different authors in Genre 'Religion & Spirituality' -> genre_id = 4
        // below the SQL statement used to check that value : 
        
        // USE g3w16;
        // -- count how many different authors there's for genre_id=4
        // SELECT count(DISTINCT author_id) FROM book_author ba WHERE EXISTS(
        //      SELECT 1 FROM book_genre bg WHERE bg.book_id = ba.book_id AND bg.genre_id=4
        // );
        assertTrue(authors.size()==42);
    }
    
    /**
     * Test of fetching method for entity : Author
     * @author Jonas Faure
     * @throws java.lang.Exception 
     */
    @Test
    public void testFindByFormatAuthor() throws Exception {
        //TODO: more testing needed
        System.out.println("> Author > Fetching ( some, by format )");
        Format format = new Format();
        format.setFormatId(2);
        List<Author> authors = authorController.findAuthorEntitiesByFormat(format);
        // 23 diferent authors in Format '.cbr' -> format_id=2
        // below the SQL statement used to check that value :
        
        // USE g3w16;
        // -- count how many different authors there's for format_id=2
        // SELECT count(DISTINCT author_id) FROM book_author ba WHERE EXISTS(
        //     SELECT 1 FROM book_format bf WHERE bf.book_id = ba.book_id AND bf.format_id=2
        // );
        assertTrue(authors.size()==23);
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
