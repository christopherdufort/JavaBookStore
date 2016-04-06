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
import static org.assertj.core.api.Assertions.assertThat;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

/**
 * @author Christopher Dufort
 * @version 0.2.4 - Last Modified 2/15/2016
 */
@RunWith(Arquillian.class)
public class AdJpaControllerTest {
    
    @Deployment
    public static WebArchive deploy(){
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
        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                //.addPackage(CSDBookStoreDAOImpl.class.getPackage())
                .addPackage(AdJpaController.class.getPackage())
                .addPackage(Ad.class.getPackage())
//                .addPackage(AuthenticationController.class.getPackage())
//                .addPackage(AlreadyExistingUserException.class.getPackage())
//                .addPackage(AuthBean.class.getPackage())
//                .addPackage(ApprovalConverter.class.getPackage())
//                .addPackage(CartView.class.getPackage())
//                .addPackage(MailBean.class.getPackage())
//                .addPackage(BasicSendAndReceive.class.getPackage())
//                .addPackage(m_adsBackingBean.class.getPackage())
//                .addPackage(Messages.class.getPackage())
//                .addPackage(ConfirmPasswordValidator.class.getPackage())
                .addPackage(RollbackFailureException.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/glassfish-resources.xml"), "glassfish-resources.xml")
                .addAsResource(new File("src/main/resources/META-INF/persistence.xml"), "META-INF/persistence.xml")
                .addAsResource("create_and_seed_tables.sql")
                .addAsLibraries(dependencies);
//                .addPackages(true, new ExcludeRegExpPaths(".*Test.class$"), "CSDBookStore");

        return webArchive;
    }
    
    @Resource(name="java:app/jdbc/g3w16")
    private DataSource ds;

    @Inject
    private AdJpaController adJpaController;
   
    //------------------------------------------------------TEST METHODS-----------------------------------
    // TODO Should have 2-3 test methods per controller method(pass,fail,exception?)
    // TODO should have comments and loggs
    @Test
    public void sccessfulCreateOfAd() throws Exception{
        Ad originalAd = new Ad(1);
        originalAd.setAdFilename("ad1.jpg");
        adJpaController.create(originalAd);
        
        Ad retrievedAd = adJpaController.findAdById(1);
        //asertThat(actual).comparison(expected)
        assertThat(originalAd).isEqualTo(retrievedAd);
    }
    
    @Test
    public void SuccessfulUpdateOfAd() throws Exception{
        //insert a new object into db
        adJpaController.create(new Ad(null,"ad2.jpg"));
        
        //create a new object with same id and different name
        Ad modifiedAd = new Ad(1,"modifiedAd2.jpg");
        
        //update the record in the table based on its id
        adJpaController.edit(modifiedAd);
        
        assertThat(adJpaController.findAdById(1)).isEqualToComparingFieldByField(modifiedAd);      
    }
    
    @Test
    public void SuccessfulDeleteOfAd() throws Exception{
        Ad originalAd = new Ad(1,"ad3.jpg");
        //insert a new object into db
        adJpaController.create(originalAd);
      
        //destroy the record in the table based on its id
        adJpaController.destroy(1);
        
        //should be nothing in table
        assertThat(adJpaController.getAdCount()).isEqualTo(0);      
    }
    
    @Test
    public void SuccessfulFindAllAd() throws Exception{
        //insert several objects into db
        adJpaController.create(new Ad(null,"ad4.jpg"));
        adJpaController.create(new Ad(null,"ad5.jpg"));
        adJpaController.create(new Ad(null,"ad6.jpg"));
        
        //get a list of all the ads in the db
        List<Ad> allAds = adJpaController.findAllAds();
        
        //should be 3 records in the db
        assertThat(allAds.size()).isEqualTo(3);      
    }
    
    @Test
    public void SuccessfulAdEntities() throws Exception{
        //insert several objects into db
        adJpaController.create(new Ad(null,"ad7.jpg"));
        adJpaController.create(new Ad(null,"ad8.jpg"));
        adJpaController.create(new Ad(null,"ad9.jpg"));
        adJpaController.create(new Ad(null,"ad10.jpg"));
        
        //get a list of two objects in the db starting at number 2
        List<Ad> someAds = adJpaController.findAdEntities(2, 2);
        
        //should retrieve 2 records
        assertThat(someAds.size()).isEqualTo(2);      
    }
    @Test
    public void AdEntitiesByFieldEquality() throws Exception{
        //insert several objects into db
        adJpaController.create(new Ad(null,"ad7.jpg"));
        adJpaController.create(new Ad(null,"ad8.jpg"));
        Ad TargetAd = new Ad(null,"ad9.jpg");
        adJpaController.create(TargetAd);
        adJpaController.create(new Ad(null,"ad10.jpg"));
        
        //get a list of two objects in the db starting at number 2
        List<Ad> someAds = adJpaController.findAdEntities(2, 2);
        
        //check that the second thing retrieved is the right file name and id
        assertThat(someAds.get(1)).isEqualToComparingFieldByField(TargetAd);

    }
    
    @Test
    public void SuccessfulFindAdByID() throws Exception{
        //insert new objects into db
        adJpaController.create(new Ad(null,"ad11.jpg"));
        Ad expectedAd = new Ad(null,"ad12.jpg");
        adJpaController.create(expectedAd);
        
        
        //find by id
        Ad actualAd = adJpaController.findAdById(2);
        
        //should find second record with that filename and id
        assertThat(actualAd).isEqualToComparingFieldByField(expectedAd);
        
    }
    
    @Test
    public void SuccessfulFindAdByFilename() throws Exception{
        //insert new objects into db
        adJpaController.create(new Ad(null,"ad13.jpg"));
        
        //find by filename
        Ad foundBean = adJpaController.findAdByFilename("ad13.jpg");
        
        //should find a record with id of 1
        assertThat(foundBean.getAdId()).isEqualTo(1);      
    }
    
    @Test
    public void SuccessfulCount() throws Exception{
        //insert new objects into db
        adJpaController.create(new Ad(null,"ad14.jpg"));
        adJpaController.create(new Ad(null,"ad15.jpg"));
        adJpaController.create(new Ad(null,"ad16.jpg"));
        
        
        //destroy the record in the table based on its id
        adJpaController.destroy(3);
        
        System.out.println("FOUND...." + adJpaController.getAdCount());
        
        //should find 2 records after 3 inserted 1 deleted
        assertThat(adJpaController.getAdCount()).isEqualTo(2);     
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
