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
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Ignore;

/**
 * Tests for the user entity.
 * 
 * @author Giuseppe Campanelli
 */
@Ignore
@RunWith(Arquillian.class)
public class RegisteredUserJpaControllerTest {
    
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
                .addPackage(RegisteredUserJpaController.class.getPackage())
                .addPackage(RegisteredUser.class.getPackage())
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
    private RegisteredUserJpaController registeredUserJpaController;

    /**
     * Test of create method, of class RegisteredUserJpaController.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        RegisteredUser registeredUser = new RegisteredUser();
        registeredUser.setEmailAddress("nader@nader.nader");
        registeredUser.setPassword("nader");
        registeredUserJpaController.create(registeredUser);
        assertEquals(registeredUser.getEmailAddress(), registeredUserJpaController.findUserByEmail("nader@nader.nader").getEmailAddress());
    }

    /**
     * Test of edit method, of class RegisteredUserJpaController.
     */
    @Test
    public void testEditUpdatePassword() throws Exception {
        System.out.println("edit password");
        RegisteredUser registeredUser = registeredUserJpaController.findUserById(2);
        registeredUser.setPassword("newpassword");
        registeredUserJpaController.edit(registeredUser);
        assertEquals(registeredUser, registeredUserJpaController.findUserById(2));
    }
    
    /**
     * Test of edit method, of class RegisteredUserJpaController.
     */
    @Test
    public void testEditUpdateBillingInfo() throws Exception {
        System.out.println("edit billing info");
        RegisteredUser registeredUser = registeredUserJpaController.findUserById(3);
        registeredUser.setAddressOne("123 apple cove");
        registeredUser.setCellPhone("514-235-3463");
        registeredUser.setFirstName("Corrected first name");
        registeredUserJpaController.edit(registeredUser);
        assertEquals(registeredUser, registeredUserJpaController.findUserById(3));
    }

    /**
     * Test of destroy method, of class RegisteredUserJpaController.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        Integer id = 1;
        registeredUserJpaController.destroy(id);
        assertEquals(null, registeredUserJpaController.findUserById(id));
    }

    /**
     * Test of findAll method, of class RegisteredUserJpaController.
     */
    @Test
    public void testFindAll() {
        System.out.println("findAll");
        List<RegisteredUser> all = registeredUserJpaController.findAll();
        assertEquals(30, all.size());
    }

    /**
     * Test of findUsersWithRange method, of class RegisteredUserJpaController.
     */
    @Test
    public void testFindUsersWithRange() {
        System.out.println("findUsersWithRange");
        int maxResults = 10;
        int firstResult = 5;
        List<RegisteredUser> all = registeredUserJpaController.findUsersWithRange(maxResults, firstResult);
        assertEquals(10, all.size());
    }

    /**
     * Test of findUserById method, of class RegisteredUserJpaController.
     */
    @Test
    public void testFindUserById() {
        System.out.println("findUserById");
        Integer id = 1;
        RegisteredUser expResult = registeredUserJpaController.findUserByEmail("abc123@gmail.com");
        RegisteredUser result = registeredUserJpaController.findUserById(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of findUserByEmail method, of class RegisteredUserJpaController.
     */
    @Test
    public void testFindUserByEmail() {
        System.out.println("findUserByEmail");
        String email = "abc123@gmail.com";
        RegisteredUser expResult = registeredUserJpaController.findUserById(1);
        RegisteredUser result = registeredUserJpaController.findUserByEmail(email);
        assertEquals(expResult, result);
    }

    /**
     * Test of getUsersCount method, of class RegisteredUserJpaController.
     */
    @Test
    public void testGetUsersCount() {
        System.out.println("getUsersCount");
        int expResult = 30;
        int result = registeredUserJpaController.getUsersCount();
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
