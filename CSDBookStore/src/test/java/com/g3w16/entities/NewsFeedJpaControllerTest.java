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
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;

/**
 * @author Christopher Dufort
 * @version 0.2.8 - Last Modified 2/24/2016
 */
@RunWith(Arquillian.class)
public class NewsFeedJpaControllerTest {
    
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
        final WebArchive webArchive = ShrinkWrap.create(WebArchive.class)
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                //.addPackage(CSDBookStoreDAOImpl.class.getPackage())
                .addPackage(NewsFeedJpaController.class.getPackage())
                .addPackage(NewsFeed.class.getPackage())
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
    private NewsFeedJpaController newsFeedJpaController;
   
    //------------------------------------------------------TEST METHODS-----------------------------------
    // TODO Should have 2-3 test methods per controller method(pass,fail,exception?)
    // TODO should have comments and loggs
    @Test
    public void sccessfulCreateOfNewsFeed() throws Exception{
        NewsFeed originalNewsFeed = new NewsFeed(1);
        originalNewsFeed.setNewsFeedLink("www.booknews.com");
        newsFeedJpaController.create(originalNewsFeed);
        
        NewsFeed retrievedNewsFeed= newsFeedJpaController.findNewsFeedById(1);
        //asertThat(actual).comparison(expected)
        assertThat(originalNewsFeed).isEqualTo(retrievedNewsFeed);
    }
    
    @Test
    public void successfulUpdateOfNewsFeed() throws Exception{
        //insert a new object into db
        newsFeedJpaController.create(new NewsFeed(null,"www.booknews2.com"));
        
        //create a new object with same id and different name
        NewsFeed modifiedNewsFeed = new NewsFeed(1,"www.modifiedbooknews2.com");
        
        //update the record in the table based on its id
        newsFeedJpaController.edit(modifiedNewsFeed);
        
        assertThat(newsFeedJpaController.findNewsFeedById(1)).isEqualToComparingFieldByField(modifiedNewsFeed);      
    }
    
    @Test
    public void successfulDeleteOfNewsFeed() throws Exception{
        NewsFeed originalNewsFeed = new NewsFeed(1,"www.deletemenews.com");
        //insert a new object into db
        newsFeedJpaController.create(originalNewsFeed);
      
        //destroy the record in the table based on its id
        newsFeedJpaController.destroy(1);
        
        //should be nothing in table
        assertThat(newsFeedJpaController.getNewsFeedCount()).isEqualTo(0);      
    }
    
    @Test
    public void successfulFindAllNewsFeed() throws Exception{
        //insert several objects into db
        newsFeedJpaController.create(new NewsFeed(1,"www.news1.com"));
        newsFeedJpaController.create(new NewsFeed(2,"www.news2.com"));
        newsFeedJpaController.create(new NewsFeed(3,"www.news3.com"));
        
        //get a list of all the news feeds in the db
        List<NewsFeed> allFeeds = newsFeedJpaController.findAllNewsFeeds();
        
        //should be 3 records in the db
        assertThat(allFeeds.size()).isEqualTo(3); 
    }
    
    @Test
    public void successfulNewsFeedEntities() throws Exception{
        //insert several objects into db
        newsFeedJpaController.create(new NewsFeed(1,"www.countmenews1.com"));
        newsFeedJpaController.create(new NewsFeed(2,"www.countmenews2.com"));
        newsFeedJpaController.create(new NewsFeed(3,"www.countmenews3.com"));
        newsFeedJpaController.create(new NewsFeed(4,"www.countmenews4.com"));
        
        //get a list of two objects in the db starting at number 2
        List<NewsFeed> someFeeds = newsFeedJpaController.findNewsFeedPagination(2, 2);
      
        //should retrieve 2 records
        assertThat(someFeeds.size()).isEqualTo(2);      
    }
    @Test
    public void newsFieldEntitiesByFieldEquality() throws Exception{
        //insert several objects into db
        newsFeedJpaController.create(new NewsFeed(1,"www.newsfield1.com"));
        newsFeedJpaController.create(new NewsFeed(2,"www.newsfield2.com"));
        
        NewsFeed targetNewsFeed = new NewsFeed(3,"www.newsfield3.com");
        newsFeedJpaController.create(targetNewsFeed);
        newsFeedJpaController.create(new NewsFeed(4,"www.newsfield4.com"));
        
        //get a list of two objects in the db starting at number 2
        List<NewsFeed> someFeeds = newsFeedJpaController.findNewsFeedPagination(2, 1);
        System.out.println("!!!!!!!!!!!! in the db there are " + newsFeedJpaController.getNewsFeedCount());
        System.out.println("!!!!!!!!!!!! some feeds" + someFeeds.get(0).getNewsFeedId());
        System.out.println("!!!!!!!!!!!! some feeds" + someFeeds.get(1).getNewsFeedId());
        
        //check that the second thing retrieved is the right file name and id
        assertThat(someFeeds.get(1)).isEqualToComparingFieldByField(targetNewsFeed);

    }
    
    @Test
    public void successfulFindNewsFeedByID() throws Exception{
        //insert new objects into db
        newsFeedJpaController.create(new NewsFeed(1,"www.newsFeedId1.com"));
        NewsFeed expectedNewsFeed = new NewsFeed(2,"www.newsFeedId2.com");
        newsFeedJpaController.create(expectedNewsFeed);
        
        
        //find by id
        NewsFeed actualNewsFeed = newsFeedJpaController.findNewsFeedById(2);
        
        //should find second record with that filename and id
        assertThat(actualNewsFeed).isEqualToComparingFieldByField(expectedNewsFeed);
        
    }
    
    @Test
    public void successfulFindNewsFeedByLink() throws Exception{
        //insert new objects into db
          newsFeedJpaController.create(new NewsFeed(1,"www.NewsFeedLink.com"));
        
        //find by news feed link
       NewsFeed foundNewsFeed = newsFeedJpaController.findNewsFeedByLink("www.NewsFeedLink.com");
               
        //should find a record with id of 1
        assertThat(foundNewsFeed.getNewsFeedId()).isEqualTo(1);      
    }
    
    @Test
    public void successfulNewsFeedCount() throws Exception{
        //insert new objects into db
        newsFeedJpaController.create(new NewsFeed(1,"www.countmenews1.com"));
        newsFeedJpaController.create(new NewsFeed(2,"www.countmenews2.com"));
        newsFeedJpaController.create(new NewsFeed(3,"www.deletemenews.com"));

        //destroy the record in the table based on its id
        newsFeedJpaController.destroy(3);
        
        //should find 2 records after 3 inserted 1 deleted
        assertThat(newsFeedJpaController.getNewsFeedCount()).isEqualTo(2);     
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
