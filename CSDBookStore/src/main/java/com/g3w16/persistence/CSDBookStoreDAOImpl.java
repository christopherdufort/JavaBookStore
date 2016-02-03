/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.persistence;

import com.g3w16.beans.InvoiceBean;
import com.g3w16.beans.InvoiceDetailBean;
import com.g3w16.beans.RegisteredUserBean;
import com.g3w16.beans.ReviewBean;
import com.g3w16.beans.SurveyBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;

//@Named
//@RequestScoped
public class CSDBookStoreDAOImpl implements CSDBookStoreDAO {

    @Resource(name = "java:app/jdbc/g3w16")
    private DataSource CSDBookStoreSource;

    public CSDBookStoreDAOImpl() {
        super();
    }

    @Override
    public int createInvoice(RegisteredUserBean user) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<InvoiceBean> findAllInvoices() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<InvoiceBean> findAllInvoicesBasedOnUser() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InvoiceBean findInvoiceById(InvoiceBean invoice) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteInvoice(InvoiceBean invoice) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int createInvoiceDetails(InvoiceBean invoice) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<InvoiceDetailBean> findAllInvoiceDetails() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InvoiceBean findInvoiceDetailById(InvoiceDetailBean invoice) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<InvoiceBean> findInvoiceDetailsBasedOnInvoice(InvoiceBean invoice) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteInvoiceDetail(InvoiceBean invoice) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     * @param reviewBean
     * @return 
     * @throws java.sql.SQLException
     */
    @Override
    public int createReview(ReviewBean reviewBean) throws SQLException {
        int result = 0;
        String query = "INSERT INTO review (isbn, date_submitted, client_id, rating, approval_id, review_title, review_text) VALUES(?,?,?,?,?,?,?)";
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            pStatement.setString(1, reviewBean.getIsbn());
            pStatement.setTimestamp(2, Timestamp.valueOf(reviewBean.getDate_submitted()));
            pStatement.setInt(3, reviewBean.getUser_id());
            pStatement.setInt(4, reviewBean.getRating());
            pStatement.setInt(5, reviewBean.getApproval_id());
            pStatement.setString(6, reviewBean.getReview_title());
            pStatement.setString(7, reviewBean.getReview_text());
            result = pStatement.executeUpdate();
            ResultSet resultSet = pStatement.getGeneratedKeys();
            if (resultSet.next()) {
                reviewBean.setReview_id(resultSet.getInt(1));
            }
        }
        return result;
    }

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     * @param review_id
     * @return 
     * @throws java.sql.SQLException 
     */
    @Override
    public ReviewBean getReviewById(int review_id) throws SQLException {
        ReviewBean reviewBean = new ReviewBean();
        String query = "SELECT * FROM review WHERE review_id=?";
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection
                .prepareStatement(query);) {
            pStatement.setInt(1, review_id);

            try (ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    reviewBean = createReviewBeanObject(resultSet);
                }
            }
        }
        return reviewBean;
    }

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     * @param user_id
     * @return 
     * @throws java.sql.SQLException 
     */
    @Override
    public List<ReviewBean> getReviewByUserId(int user_id) throws SQLException {
        List<ReviewBean> reviewList = new ArrayList<>();
        String query = "SELECT * FROM review WHERE user_id=?";
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection
                .prepareStatement(query);) {
            pStatement.setInt(1, user_id);

            try (ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    reviewList.add(createReviewBeanObject(resultSet));
                }
            }
        }
        return reviewList;
    }

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     * @param date_submitted
     * @return 
     * @throws java.sql.SQLException
     */
    @Override
    public List<ReviewBean> getReviewByDateSubmitted(LocalDateTime date_submitted) throws SQLException {
        List<ReviewBean> reviewList = new ArrayList<>();
        String query = "SELECT * FROM review WHERE date_submitted=?";
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection
                .prepareStatement(query);) {
            pStatement.setTimestamp(1, Timestamp.valueOf(date_submitted));

            try (ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    reviewList.add(createReviewBeanObject(resultSet));
                }
            }
        }
        return reviewList;
    }

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     * @param approval_id
     * @return 
     * @throws java.sql.SQLException
     */
    @Override
    public List<ReviewBean> getReviewByApprovalId(int approval_id) throws SQLException {
        List<ReviewBean> reviewList = new ArrayList<>();
        String query = "SELECT * FROM review WHERE approval_id=?";
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection
                .prepareStatement(query);) {
            pStatement.setInt(1, approval_id);

            try (ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    reviewList.add(createReviewBeanObject(resultSet));
                }
            }
        }
        return reviewList;
    }

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     * @param isbn
     * @return 
     * @throws java.sql.SQLException 
     */
    @Override
    public List<ReviewBean> getReviewByIsbn(String isbn) throws SQLException {
        List<ReviewBean> reviewList = new ArrayList<>();
        String query = "SELECT * FROM review WHERE isbn=?";
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection
                .prepareStatement(query);) {
            pStatement.setString(1, isbn);

            try (ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    reviewList.add(createReviewBeanObject(resultSet));
                }
            }
        }
        return reviewList;
    }

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     * @param reviewBean
     * @return 
     * @throws java.sql.SQLException 
     */
    @Override
    public int updateReview(ReviewBean reviewBean) throws SQLException {
        int result = 0;
        ReviewBean found = getReviewById(reviewBean.getReview_id());
        if (found == null) {
            throw new IllegalArgumentException("Can not update, this review does not exist.");
        } else {
            String query = "UPDATE review SET approval_id = ? WHERE review_id = ? ";

            try (Connection connection = CSDBookStoreSource.getConnection();
                    PreparedStatement pStatement = connection
                    .prepareStatement(query);) {
                pStatement.setInt(1, reviewBean.getApproval_id());
                pStatement.setInt(2, reviewBean.getReview_id());
                result = pStatement.executeUpdate();
            }
        }
        return result;
    }

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     * @param review_id
     * @return 
     * @throws java.sql.SQLException
     */
    @Override
    public int deleteReviewByReviewId(int review_id) throws SQLException {
        int result = 0;
        ReviewBean found = getReviewById(review_id);
        if (found == null) {
            throw new IllegalArgumentException("Can not update, this review does not exist.");
        } else {
            String query = "DELETE FROM review WHERE review_id=? ";

            try (Connection connection = CSDBookStoreSource.getConnection();
                    PreparedStatement pStatement = connection
                    .prepareStatement(query);) {
                pStatement.setInt(1, review_id);
                result = pStatement.executeUpdate();
            }
        }
        return result;
    }

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     * @param user_id
     * @return 
     * @throws java.sql.SQLException
     */
    @Override
    public int deleteReviewByUserId(int user_id) throws SQLException {
        int result = 0;
        List<ReviewBean> reviewList = getReviewByUserId(user_id);
        if (reviewList == null) {
            throw new IllegalArgumentException("Can not update, this review does not exist.");
        } else {
            String query = "DELETE FROM review WHERE user_id=? ";
            try (Connection connection = CSDBookStoreSource.getConnection();
                    PreparedStatement pStatement = connection
                    .prepareStatement(query);) {
                pStatement.setInt(1, user_id);
                result = pStatement.executeUpdate();
            }
        }
        return result;
    }

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     * @param isbn
     * @return 
     * @throws java.sql.SQLException
     */
    @Override
    public int deleteReviewByIsbn(String isbn) throws SQLException {
        int result = 0;
        List<ReviewBean> reviewList = getReviewByIsbn(isbn);
        if (reviewList == null) {
            throw new IllegalArgumentException("Can not update, this review does not exist.");
        } else {
            String query = "DELETE FROM review WHERE isbn=? ";
            try (Connection connection = CSDBookStoreSource.getConnection();
                    PreparedStatement pStatement = connection
                    .prepareStatement(query);) {
                pStatement.setString(1, isbn);
                result = pStatement.executeUpdate();
            }
        }
        return result;
    }

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     * @param date_submitted
     * @return 
     * @throws java.sql.SQLException
     */
    @Override
    public int deleteReviewByDateSubmitted(LocalDateTime date_submitted) throws SQLException {
        int result = 0;
        List<ReviewBean> reviewList = getReviewByDateSubmitted(date_submitted);
        if (reviewList == null) {
            throw new IllegalArgumentException("Can not update, this review does not exist.");
        } else {
            String query = "DELETE FROM review WHERE date_submitted=? ";
            try (Connection connection = CSDBookStoreSource.getConnection();
                    PreparedStatement pStatement = connection
                    .prepareStatement(query);) {
                pStatement.setTimestamp(1, Timestamp.valueOf(date_submitted));
                result = pStatement.executeUpdate();
            }
        }
        return result;
    }

    /**
     * CRUD method for Review table
     *
     * @author Xin Ma
     * @version 0.0.6
     */
    private ReviewBean createReviewBeanObject(ResultSet resultSet) throws SQLException {
        ReviewBean reviewBean = new ReviewBean();
        reviewBean.setReview_id(resultSet.getInt("review_id"));
        reviewBean.setDate_submitted(resultSet.getTimestamp("date_submitted").toLocalDateTime());
        reviewBean.setUser_id(resultSet.getInt("user_id"));
        reviewBean.setIsbn(resultSet.getString("isbn"));
        reviewBean.setRating(resultSet.getInt("rating"));
        reviewBean.setApproval_id(resultSet.getInt("approval_id"));
        reviewBean.setReview_title(resultSet.getString("review_title"));
        reviewBean.setReview_text(resultSet.getString("review_text"));
        return reviewBean;
    }
    
    
    /**
     * Survey table select by id.
     * Retrieve one record from the given table based on the primary key
     * 
     * @author Christopher Dufort
     * @version 0.0.7 - last modified 2/3/2016
     * @param id
     * @return the Survey object
     */
    public SurveyBean findById(int id) throws SQLException{
        //If there is no record with the requested id, null object will be returned.
        
        SurveyBean surveyBean = null;
        
        String selectQuery = "SELECT survey_id, question, answer_one, answer_two, answer_three, answer_default FROM survey WHERE ID=?";
       
        // Using try with resources, available since Java 1.7
        // Class that implement the Closable interface created in the
        // parenthesis () will be closed when the block ends.
        try(Connection connection = CSDBookStoreSource.getConnection();
            // You must use PreparedStatements to guard against SQL Injection
            PreparedStatement prepStatement = connection.prepareStatement(selectQuery);) {
            // Only object creation statements can be in the parenthesis so
            // first try-with-resources block ends
            prepStatement.setInt(1,id);
            // A new try-with-resources block for creating the ResultSet object
            
            try (ResultSet resultSet = prepStatement.executeQuery()){
                
                if (resultSet.next()) {
                    surveyBean = new SurveyBean();
                    
                    surveyBean.setSurveyId(id);
                    surveyBean.setAnswerOne(resultSet.getString("answer_one"));
                    surveyBean.setAnswerTwo(resultSet.getString("answer_two"));
                    surveyBean.setAnswerThree(resultSet.getString("answer_three"));
                    surveyBean.setAnswerDefault(resultSet.getString("answer_default"));
                }
            }
        }
        return surveyBean;
    }

}
