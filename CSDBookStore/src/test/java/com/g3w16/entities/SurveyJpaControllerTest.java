/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.entities;


import com.g3w16.entities.Survey;
import com.g3w16.entities.SurveyJpaController;
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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertTrue;

/**
 * @author Christopher
 */
@RunWith(Arquillian.class)
public class SurveyJpaControllerTest {
    
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

    @Inject
    private SurveyJpaController surveyJpaController;
    
    //------------------------------------------------------TEST METHODS-----------------------------------------
    // TODO Should have 2-3 test methods per controller method(pass,fail,exception?)
    // TODO should have comments and loggs
    @Test
    public void successfulCreateSurvey() throws Exception{
        Survey expectedSurvey = new Survey(null,"Q", "A1", "A2","A3","AD");
        surveyJpaController.create(expectedSurvey);
        
        Survey actualSurvey = surveyJpaController.findSurveyById(7);
        assertThat(actualSurvey).isEqualTo(expectedSurvey);
    }
    @Test
    public void successfulUpdateSurvey() throws Exception{
        Survey expectedSurvey = new Survey(7,"Q", "A1", "A2","A3","AD");
        surveyJpaController.create(expectedSurvey);
        
        Survey modifiedSurvey = new Survey(7,"Modified", "A1", "A2","A3","AD");
        
        surveyJpaController.edit(modifiedSurvey);

        assertThat(surveyJpaController.findSurveyById(7)).isEqualToComparingFieldByField(modifiedSurvey);
    }
    
    @Test
    public void successfulDestroySurvey() throws Exception{
        Survey toBeDestroyed = new Survey(7,"Q", "A1", "A2","A3","AD");
        surveyJpaController.create(toBeDestroyed);
        
        surveyJpaController.destroy(7);
       
        assertThat(surveyJpaController.getSurveyCount()).isEqualTo(6);
    }
    
    @Test
    public void successfulFindAllSurvey() throws Exception{
        //Find all the surveys in the db
        List<Survey> allSurveys = surveyJpaController.findAllSurveys();
        
        //should be 6 records int he db
        assertThat(allSurveys.size()).isEqualTo(6);  
    }
    @Test
    public void successfulSurveyEntities() throws Exception{
         //Find 4 surveys in the db starting at 2 
        List<Survey> someSurveys = surveyJpaController.findSurveyEntities(4,2);
        
        List<Integer> expectedListOfId = new ArrayList<>();
        List<Integer> actualListOfId = new ArrayList<>();
        
        for( Survey survey : someSurveys){
            actualListOfId.add(survey.getSurveyId());
        }
        assertThat(actualListOfId).isEqualTo(expectedListOfId);
    }
    @Test
    public void sucessfulFindSurveyById() throws Exception{
        
        //find a specific survey by its primary key
        Survey foundSurvey = surveyJpaController.findSurveyById(4);
        
        //Check that the value of one of its records is as expected.
        assertThat(foundSurvey.getAnswerOne()).isEqualTo("Yes");
    }
    
    @Test
    public void sucessfulCountOfSurvey() throws Exception{
        //current 6 things in the database;
        Survey seventhSurvey = new Survey(7,"Q", "A1", "A2","A3","AD");
        surveyJpaController.create(seventhSurvey);
        
        assertThat(surveyJpaController.getSurveyCount()).isEqualTo(7);
        
    }
    //-----------------------------------------------------END OF TEST METHODS-----------------------------------
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
