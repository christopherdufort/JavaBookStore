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
public class GenreTest {
    
    @Inject
    private GenreJpaController genreController;


    /**
     * Test of creation method for entity : Genre
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testCreateGenre() throws Exception {
        // TODO: further testing should imply books
        System.out.println("> Genre > Creation");
        Genre genre = new Genre();
        int unexpectedResult = -1;
        genre.setGenreId(unexpectedResult);
        genre.setGenreName("Medieval hentaï");
        genre.setBookList(new ArrayList<>());
        genreController.create(genre);
        assertFalse(genre.getGenreId()==unexpectedResult);
    }

    /**
     * Test of edition method for entity : Genre
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testEditGenre() throws Exception {
        // TODO: test invalid params too
        System.out.println("> Genre > Edition");
        Genre genre = new Genre(
                1, "Medieval hentaï"
        );
        genre.setBookList(new ArrayList<>());
        genreController.edit(genre);
        assertEquals(genre.getGenreName(), "Medieval hentaï");
        assertNotEquals(genre.getGenreName(), "Computers & Technology");
    }
    
    /**
     * Test of edition method for entity : Genre
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testEditIncorrectGenre() throws Exception {
        System.out.println("> Genre > Edition ( incorrect )");
        Genre genre = new Genre(
                -1, "Medieval hentaï"
        );
        genre.setBookList(new ArrayList<>());
        try{
            genreController.edit(genre);
        }catch (Exception e){
            return;
        }
        fail("this should trigger an exception since the ID does not exists");
    }

    /**
     * Test of destruction method for entity : Genre
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testDestroyGenre() throws Exception{
        System.out.println("> Genre > Destruction");        
        genreController.destroy(1);
        assertTrue(genreController.getGenreCount()==4);
    }

    /**
     * Test of fetching method for entity : Genre
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindAllGenre() throws Exception{
        // TODO: more testing needed
        System.out.println("> Genre > Fetching ( all )");
        List<Genre> genres= genreController.findGenreEntities();
        assertEquals(genres.size(), 5);
    }

    /**
     * Test of fetching method for entity : Genre
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindAllInRangeGenre() throws Exception {
        // TODO: more testing needed
        System.out.println("> Genre > Fetching ( all, in range )");
        List<Genre> genres = genreController.findGenreEntities(4, 0);
        assertEquals(genres.size(), 4);
    }

    /**
     * Test of fetching method for entity : Genre
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindByIdGenre() throws Exception {
        // TODO: more testing needed
        System.out.println("> Genre > Fetching ( one, by id )");
        Genre genre = genreController.findGenre(1);
        assertEquals(genre.getGenreName(), "Computers & Technology");
        assertTrue(genre.getGenreId() == 1); // <- I would be terrified if it wouldn't pass ..
    }

    /**
     * Test of fetching method for entity : Genre
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testCountGenre() throws Exception {
        // TODO: possibly more thing needed here ..
        System.out.println("> Genre > Fetching ( count )");
        // below the SQL statement used to check this value 
        
        // SELECT COUNT(genre_id) FROM genre;
        assertTrue(5 == genreController.getGenreCount());
    }
    
    /**
     * Test of fetching method for entity : Genre
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindByNameGenre() throws Exception {
        //TODO: more testing needed
        System.out.println("> Genre > Fetching  ( some, by name )");
        // below the SQL statement used to check this value
        
        // SELECT * FROM genre WHERE genre_name LIKE "%co%";
        List<Genre> genres = genreController.findGenreEntitiesLike("co");
        assertTrue(genres.size() == 2);
    }
    
    /**
     * Test of fetching method for entity : Genre
     * @author Jonas Faure
     * @throws java.lang.Exception
     */
    @Test
    public void testFindByBookGenre() throws Exception {
        //TODO: more testing needed
        System.out.println("> Genre > Fetching ( some, by book )");
        Book book = new Book();
        book.setBookId(1);
        List<Genre> genres = genreController.findGenreEntitiesByBook(book);
        // below the SQL statement used to check this value
        
        // SELECT * FROM genre g WHERE EXISTS(
        //     SELECT 1 FROM book_genre bg WHERE g.genre_id=bg.genre_id AND bg.book_id=2
        // );
        assertTrue(genres.size() == 1);
        assertEquals(genres.get(0).getGenreName(), "Computers & Technology");
    }
    
    /**
     * Test of fetching method for entity : Genre
     * @author Jonas Faure
     * @throws java.lang.Exception 
     */
    @Test
    public void testFindByFormatGenre() throws Exception {
        //TODO: more testing needed
        System.out.println("> Genre > Fetching ( some, by genre )");
        Format format = new Format();
        format.setFormatId(2);
        List<Genre> genres = genreController.findGenreEntitiesByFormat(format);
        // 2 different genre in format '.cbr' -> format_id=2
        // below the SQL statement used to check that value : 
        
        // USE g3w16;
        // -- count how many different genre there's for format_id=2
        // SELECT * FROM genre g WHERE EXISTS(
	//  SELECT 1 FROM book_genre bg WHERE bg.genre_id = g.genre_id AND EXISTS(
	// 	SELECT 1 FROM book_format bf WHERE bg.book_id = bf.book_id AND bf.format_id=2
        //  )
        // );
        assertTrue(genres.size()==2);
    }
    
    /**
     * Test of fetching method for entity : Genre
     * @author Jonas Faure
     * @throws java.lang.Exception 
     */
    @Test
    public void testFindByAuthorGenre() throws Exception {
        //TODO: more testing needed
        System.out.println("> Genre > Fetching ( some, by author )");
        Author author = new Author();
        author.setAuthorId(1);
        List<Genre> genres = genreController.findGenreEntitiesByAuthor(author);
        // 1 different genre in Author 'Muhammad H. Rashid' -> author_id=1
        // below the SQL statement used to check that value :
        
        // USE g3w16;
        // -- count how many different authors there's for genre_id=2
        // SELECT * FROM genre g WHERE EXISTS(
        //     SELECT 1 FROM book_genre bg WHERE bg.genre_id = g.genre_id AND EXISTS(
	// 	SELECT 1 FROM book_author ba WHERE bg.book_id = ba.book_id AND ba.author_id=1
        //     )
        // );
        assertTrue(genres.size()==1);
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
