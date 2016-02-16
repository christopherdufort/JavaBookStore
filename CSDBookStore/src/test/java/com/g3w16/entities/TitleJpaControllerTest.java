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
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Giuseppe Campanelli
 */
public class TitleJpaControllerTest {
    
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
    private TitleJpaController titleJpaController;

    /**
     * Test of create method, of class TitleJpaController.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Title title = new Title();
        title.setTitle("Test");
        titleJpaController.create(title);
        assertEquals(title.getTitle(), titleJpaController.findTitleByName("Test").getTitle());
    }

    /**
     * Test of edit method, of class TitleJpaController.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Title title = new Title(1, "Mr.");
        title.setTitle("Mister");
        titleJpaController.edit(title);
        assertEquals(title, titleJpaController.findTitleByName("Mister"));
    }

    /**
     * Test of destroy method, of class TitleJpaController.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        Integer id = 5;
        titleJpaController.destroy(id);
        assertEquals(null, titleJpaController.findTitleById(5));
    }

    /**
     * Test of findTitleEntities method, of class TitleJpaController.
     */
    @Test
    public void testFindAllTitles() {
        System.out.println("findTitleEntities");
        List<Title> expResult = new ArrayList<Title>();
        expResult.add(new Title(1, "Mr."));
        expResult.add(new Title(2, "Ms."));
        expResult.add(new Title(3, "Mrs."));
        expResult.add(new Title(4, "Miss."));
        expResult.add(new Title(5, "Dr."));
        List<Title> result = titleJpaController.findAll();
        assertEquals(expResult, result);
    }

    /**
     * Test of findTitle method, of class TitleJpaController.
     */
    @Test
    public void testFindTitleById() {
        System.out.println("findTitle");
        Integer id = 1;
        Title expResult = new Title(1, "Mr.");
        Title result = titleJpaController.findTitleById(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of findProvinceByName method, of class TitleJpaController.
     */
    @Test
    public void testFindTitleByName() {
        System.out.println("findProvinceByName");
        String title = "Mr.";
        Title expResult = new Title(1, "Mr.");
        Title result = titleJpaController.findTitleByName(title);
        assertEquals(expResult, result);
    }

    /**
     * Test of getTitleCount method, of class TitleJpaController.
     */
    @Test
    public void testGetTitleCount() {
        System.out.println("getTitleCount");
        int expResult = 5;
        int result = titleJpaController.getTitleCount();
        assertEquals(expResult, result);
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
