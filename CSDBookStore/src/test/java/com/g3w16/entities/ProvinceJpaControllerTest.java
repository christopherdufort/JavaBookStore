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
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Ignore;

/**
 * Tests for the Province entity.
 * 
 * @author Giuseppe Campanelli
 */
@Ignore
@RunWith(Arquillian.class)
public class ProvinceJpaControllerTest {
    
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
                .addPackage(ProvinceJpaController.class.getPackage())
                .addPackage(Province.class.getPackage())
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
    
    @Inject
    private ProvinceJpaController provinceJpaController;

    /**
     * Test of create method, of class ProvinceJpaController.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Province province = new Province();
        province.setProvince("Test");
        province.setGst(BigDecimal.valueOf(5.0));
        provinceJpaController.create(province);
        assertEquals(province.getProvince(), provinceJpaController.findProvinceByName("Test").getProvince());
    }

    /**
     * Test of edit method, of class ProvinceJpaController.
     */
    @Test
    public void testEdit() throws Exception {
        System.out.println("edit");
        Province province = provinceJpaController.findProvinceById(1);
        province.setProvince("Quebec City");
        provinceJpaController.edit(province);
        assertEquals(province, provinceJpaController.findProvinceByName("Quebec City"));
    }

    /**
     * Test of destroy method, of class ProvinceJpaController.
     */
    @Test
    public void testDestroy() throws Exception {
        System.out.println("destroy");
        Integer id = 2;
        provinceJpaController.destroy(id);
        assertEquals(null, provinceJpaController.findProvinceById(2));
    }

    /**
     * Test of findAll method, of class ProvinceJpaController.
     */
    @Test
    public void testFindAllProvinces() throws Exception {
        System.out.println("findAll");
        ProvinceJpaController instance = new ProvinceJpaController();
        List<Province> expResult = new ArrayList<Province>();
        expResult.add(new Province(1, "Quebec"));
        expResult.add(new Province(2, "Ontario"));
        expResult.add(new Province(3, "New Brunswick"));
        provinceJpaController.destroy(4);
        provinceJpaController.destroy(5);
        provinceJpaController.destroy(6);
        provinceJpaController.destroy(7);
        provinceJpaController.destroy(8);
        provinceJpaController.destroy(9);
        provinceJpaController.destroy(10);
        provinceJpaController.destroy(11);
        provinceJpaController.destroy(12);
        provinceJpaController.destroy(13);
        assertEquals(expResult, provinceJpaController.findAll());
    }

    /**
     * Test of findProvinceById method, of class ProvinceJpaController.
     */
    @Test
    public void testFindProvinceById() {
        System.out.println("findProvinceById");
        int id = 1;
        Province expResult = new Province(id, "Quebec");
        assertEquals(expResult, provinceJpaController.findProvinceById(id));
    }

    /**
     * Test of findProvinceByName method, of class ProvinceJpaController.
     */
    @Test
    public void testFindProvinceByName() {
        System.out.println("findProvinceByName");
        String name = "Ontario";
        Province expResult = new Province(2, name);
        Province result = provinceJpaController.findProvinceByName(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of getProvinceCount method, of class ProvinceJpaController.
     */
    @Test
    public void testGetProvinceCount() {
        System.out.println("getProvinceCount");
        int expResult = 13;
        int result = provinceJpaController.getProvinceCount();
        assertEquals(expResult, result);
    }
    
    //-----------------------------------------------------END OF TEST METHODS----------------------------------
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
