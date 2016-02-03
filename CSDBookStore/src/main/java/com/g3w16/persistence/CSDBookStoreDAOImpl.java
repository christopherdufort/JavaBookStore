/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.persistence;

import com.g3w16.beans.InvoiceBean;
import com.g3w16.beans.InvoiceDetailBean;
import com.g3w16.beans.RegisteredUser;
import com.g3w16.beans.ReviewBean;
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

/**
 *
 * @author Rita Lazaar
 * @version : 0.0.3
 */
public class CSDBookStoreDAOImpl implements CSDBookStoreDAO {

    @Resource(name = "java:app/jdbc/CSDBookStore")
    private DataSource CSDBookStoreSource;

    public CSDBookStoreDAOImpl() {
        super();
    }

    /**
     *
     * This method allows to create a new invoice.
     *
     * @author Rita Lazaar
     * @param invoice
     * @return
     * @throws SQLException
     */
    @Override
    public int createInvoice(InvoiceBean invoice) throws SQLException {

        int result = 0;

        String createInvoice = "INSERT INTO invoice ( sale_date, user_number, total_net_value_sale, total_gross_value_sale) VALUES (  ?, ?, ?,?);";

        try (Connection conn = CSDBookStoreSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(createInvoice)) {

            ps.setTimestamp(1, Timestamp.valueOf(invoice.getSaleDate()));
            ps.setInt(2, invoice.getUserNumber());
            ps.setDouble(3, invoice.getTotalNetValueOfSale());

//            ps.setDouble(4, invoice.getPST());
//            ps.setDouble(5, invoice.getGST());
//            ps.setDouble(5, invoice.getHST());
            ps.setDouble(6, invoice.getTotalGrossValueOfSale());

            result = ps.executeUpdate();

        }

        return result;

    }

    /**
     * This method allows to find all invoices existing in database.
     *
     * @author Rita Lazaar
     * @return
     * @throws SQLException
     */
    @Override
    public List<InvoiceBean> findAllInvoices() throws SQLException {
        List<InvoiceBean> invoices = new ArrayList<>();

        String select = "SELECT sale_number, sale_date, user_number, total_net_value_sale, total_gross_value_sale FROM invoice;";

        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(select);
                ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                InvoiceBean invoice = new InvoiceBean();
                invoice.setSaleNumber(resultSet.getInt("sale_number"));
                invoice.setSaleDate(resultSet.getTimestamp("sale_date").toLocalDateTime());
                invoice.setUserNumber(resultSet.getInt("user_number"));
                invoice.setTotalNetValueOfSale(resultSet.getDouble("total_net_value_sale"));

                // delete the taxes 
//                invoice.setPST(resultSet.getDouble("PST"));
//                invoice.setGST(resultSet.getDouble("GST"));
//                invoice.setHST(resultSet.getDouble("HST"));
                invoice.setTotalGrossValueOfSale(resultSet.getDouble("total_gross_value_sale"));

                invoices.add(invoice);

            }

        }
        return invoices;
    }

    /**
     *
     * This method allows to find all invoices related to a specific user.
     *
     * @author Rita Lazaar
     * @param userNumber
     * @return
     * @throws SQLException
     */
    @Override
    public List<InvoiceBean> findAllInvoicesBasedOnUser(int userNumber) throws SQLException {

        List<InvoiceBean> invoices = new ArrayList<>();

        String select = "SELECT sale_number, sale_date, user_number, total_net_value_sale, total_gross_value_sale FROM invoice WHERE user_number = ?;";

        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(select);) {

            ps.setInt(1, userNumber);

            try (ResultSet resultSet = ps.executeQuery()) {

                while (resultSet.next()) {

                    InvoiceBean invoice = new InvoiceBean();
                    invoice.setSaleNumber(resultSet.getInt("sale_number"));
                    invoice.setSaleDate(resultSet.getTimestamp("sale_date").toLocalDateTime());
                    invoice.setUserNumber(resultSet.getInt("user_number"));
                    invoice.setTotalNetValueOfSale(resultSet.getDouble("total_net_value_sale"));

                    //delete the taxes 
//                invoice.setPST(resultSet.getDouble("PST"));
//                invoice.setGST(resultSet.getDouble("GST"));
//                invoice.setHST(resultSet.getDouble("HST"));
                    //
                    invoice.setTotalGrossValueOfSale(resultSet.getDouble("total_gross_value_sale"));

                    invoices.add(invoice);
                }
            }

        }
        return invoices;
    }

    /**
     *
     * This method allows to find one invoice based on its id.
     *
     * @author Rita Lazaar
     * @param saleNumber
     * @return
     * @throws SQLException
     */
    @Override
    public InvoiceBean findInvoiceById(int id) throws SQLException {

        InvoiceBean invoice = new InvoiceBean();

        String select = "SELECT sale_number, sale_date, user_number, total_net_value_sale, total_gross_value_sale FROM invoice WHERE sale_number = ?;";

        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(select);) {

            ps.setInt(1, id);

            try (ResultSet resultSet = ps.executeQuery()) {

                if (resultSet.next()) {

                    invoice.setSaleNumber(resultSet.getInt("sale_number"));
                    invoice.setSaleDate(resultSet.getTimestamp("sale_date").toLocalDateTime());
                    invoice.setUserNumber(resultSet.getInt("user_number"));
                    invoice.setTotalNetValueOfSale(resultSet.getDouble("total_net_value_sale"));

                    //delete the taxes 
//                invoice.setPST(resultSet.getDouble("PST"));
//                invoice.setGST(resultSet.getDouble("GST"));
//                invoice.setHST(resultSet.getDouble("HST"));
                    //
                    invoice.setTotalGrossValueOfSale(resultSet.getDouble("total_gross_value_sale"));

                }
            }

        }
        return invoice;
    }

    /**
     *
     * This method allows to delete an invoice, if needed.
     *
     * @author Rita Lazaar
     * @param saleNumber
     * @return
     * @throws SQLException
     */
    @Override
    public int deleteInvoice(int saleNumber) throws SQLException {
        int result = 0;

        String delete = "DELETE FROM invoice WHERE sale_number = ?";

        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(delete);) {
            ps.setInt(1, saleNumber);
            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     *
     * This method allows to create the details related to an invoice. This thus
     * represents one item in the order and its information.
     *
     * @author Rita Lazaar
     * @param invoiceDetail
     *
     * @return
     * @throws SQLException
     */
    @Override
    public int createInvoiceDetails(InvoiceDetailBean invoiceDetail) throws SQLException {

        int result = 0;

        String createInvoice = "INSERT INTO invoicedetails (invoicedetail_id, sale_number, ISBN, book_price, PST, GST,  HST) VALUES (?, ?, ?, ?,?, ?, ?);";

        try (Connection conn = CSDBookStoreSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(createInvoice)) {

            ps.setInt(1, invoiceDetail.getInvoiceDetailId());
            ps.setInt(2, invoiceDetail.getSaleNumber());
            ps.setString(3, invoiceDetail.getISBN());
            ps.setDouble(4, invoiceDetail.getBookPrice());
            ps.setDouble(5, invoiceDetail.getPST());
            ps.setDouble(6, invoiceDetail.getGST());
            ps.setDouble(7, invoiceDetail.getHST());

            result = ps.executeUpdate();

        }

        return result;
    }

    /**
     *
     * This method allows to find all invoice details existing in the database.
     *
     * @author Rita Lazaar
     * @return @throws SQLException
     */
    @Override
    public List<InvoiceDetailBean> findAllInvoiceDetails() throws SQLException {
        List<InvoiceDetailBean> invoiceDetails = new ArrayList<>();

        String select = "SELECT invoicedetail_id, sale_number, ISBN, book_price, PST, GST,HST FROM invoicedetails;";

        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(select);
                ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                InvoiceDetailBean invoiceDetail = new InvoiceDetailBean();
                invoiceDetail.setSaleNumber(resultSet.getInt("sale_number"));
                invoiceDetail.setISBN(resultSet.getString("ISBN"));
                invoiceDetail.setBookPrice(resultSet.getDouble("book_price"));
                invoiceDetail.setPST(resultSet.getDouble("PST"));
                invoiceDetail.setGST(resultSet.getDouble("GST"));
                invoiceDetail.setHST(resultSet.getDouble("HST"));

                invoiceDetails.add(invoiceDetail);
            }
        }

        return invoiceDetails;
    }

    /**
     *
     * This method allows to find an invoice detail, or item, based on its id.
     *
     * @author Rita Lazaar
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public InvoiceDetailBean findInvoiceDetailById(int id) throws SQLException {

        InvoiceDetailBean invoiceD = new InvoiceDetailBean();

        String select = "SELECT invoicedetail_id, sale_number, ISBN, book_price, PST, GST, HST FROM invoicedetails WHERE invoicedetail_id = ?;";

        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(select);) {

            ps.setInt(1, id);

            try (ResultSet resultSet = ps.executeQuery()) {

                if (resultSet.next()) {

                    invoiceD.setSaleNumber(resultSet.getInt("sale_number"));
                    invoiceD.setISBN(resultSet.getString("ISBN"));
                    invoiceD.setBookPrice(resultSet.getDouble("book_price"));
                    invoiceD.setPST(resultSet.getDouble("PST"));
                    invoiceD.setGST(resultSet.getDouble("GST"));
                    invoiceD.setHST(resultSet.getDouble("HST"));
                    //

                }
            }

        }
        return invoiceD;
    }

    /**
     *
     * This method allows to find all invoice details based on the invoice
     * number.
     *
     * @author Rita Lazaar
     * @param saleNumber
     * @return
     * @throws SQLException
     */
    @Override
    public List<InvoiceDetailBean> findInvoiceDetailsBasedOnInvoice(int saleNumber) throws SQLException {

        List<InvoiceDetailBean> invoiceDetails = new ArrayList<>();

        String select = "SELECT invoicedetail_id, sale_number, ISBN, book_price, PST, GST,HST FROM invoicedetails WHERE sale_number =? ;";

        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(select);) {

            ps.setInt(1, saleNumber);

            try (ResultSet resultSet = ps.executeQuery()) {

                while (resultSet.next()) {

                    InvoiceDetailBean invoiceDetail = new InvoiceDetailBean();
                    invoiceDetail.setSaleNumber(resultSet.getInt("sale_number"));
                    invoiceDetail.setISBN(resultSet.getString("ISBN"));
                    invoiceDetail.setBookPrice(resultSet.getDouble("book_price"));
                    invoiceDetail.setPST(resultSet.getDouble("PST"));
                    invoiceDetail.setGST(resultSet.getDouble("GST"));
                    invoiceDetail.setHST(resultSet.getDouble("HST"));

                    invoiceDetails.add(invoiceDetail);
                }
            }

        }

        return invoiceDetails;
    }

    /**
     * This method allows to delete the invoice detail based on its id.
     *
     * @author Rita Lazaar
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public int deleteInvoiceDetail(int id) throws SQLException {
       
        int result = 0;

        String delete = "DELETE FROM invoicedetails WHERE invoicedetail_id = ?";

        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(delete);) {
            ps.setInt(1, id);
            result = ps.executeUpdate();
        }
        return result;
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

}
