/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.persistence;

import com.g3w16.beans.AdBean;
import com.g3w16.beans.AuthorBean;
import com.g3w16.beans.BookBean;
import com.g3w16.beans.FormatBean;
import com.g3w16.beans.GenreBean;
import com.g3w16.beans.InvoiceBean;
import com.g3w16.beans.InvoiceDetailBean;
import com.g3w16.beans.NewsFeedBean;
import com.g3w16.beans.RegisteredUserBean;
import com.g3w16.beans.ReviewBean;
import com.g3w16.beans.SurveyBean;
import com.g3w16.beans.ProvinceBean;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 *
 * @author Rita Lazaar
 * @version : 0.0.3
 */
@Named
@RequestScoped
public class CSDBookStoreDAOImpl implements CSDBookStoreDAO {

    @Resource(name = "java:app/jdbc/g3w16")
    private DataSource CSDBookStoreSource;

    public CSDBookStoreDAOImpl() {
        super();
    }

    /**
     *
     * This method gets all the provinces.
     *
     * @author Giuseppe Campanelli
     * @return list of all provinces
     * @throws SQLException
     */
    @Override
    public List<ProvinceBean> findAllProvinces() throws SQLException {
        List<ProvinceBean> provinces = new ArrayList<>();

        String select = "SELECT name, gst, pst, hst FROM province;";

        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(select);
                ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                ProvinceBean province = new ProvinceBean();
                province.setName(resultSet.getString("name"));
                province.setGst(resultSet.getDouble("gst"));
                province.setPst(resultSet.getDouble("pst"));
                province.setHst(resultSet.getDouble("hst"));

                provinces.add(province);
            }
        }
        return provinces;
    }

    /**
     *
     * This method gets a province bean by name.
     *
     * @author Giuseppe Campanelli
     * @param name province name to get bean of
     * @return province bean
     * @throws SQLException
     */
    public ProvinceBean findProvinceByName(String name) throws SQLException {
        ProvinceBean newProvince = new ProvinceBean();

        String select = "SELECT name, gst, pst, hst FROM province WHERE name = ?";

        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(select);) {
            ps.setString(1, name);
            try (ResultSet resultSet = ps.executeQuery()) {

                if (resultSet.next()) {
                    newProvince.setName(resultSet.getString("name"));
                    newProvince.setGst(resultSet.getDouble("gst"));
                    newProvince.setPst(resultSet.getDouble("pst"));
                    newProvince.setHst(resultSet.getDouble("hst"));
                }
            }
        }
        return newProvince;
    }

    /**
     *
     * This method gets all titles.
     *
     * @author Giuseppe Campanelli
     * @return list of all titles
     * @throws SQLException
     */
    public List<String> findAllTitles() throws SQLException {
        List<String> titles = new ArrayList<>();

        String select = "SELECT title FROM title;";

        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(select);
                ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                titles.add(resultSet.getString("title"));
            }
        }
        return titles;
    }

    /**
     *
     * This method gets a province bean by name.
     *
     * @author Giuseppe Campanelli
     * @param emailAddress email of user
     * @param password pass of user
     * @return last id inserted
     * @throws SQLException
     */
    @Override
    public int createRegisteredUser(String emailAddress, String password) throws SQLException {
        int result = 0;

        String createRegisteredUser = "INSERT INTO user (email_address, password) VALUES (?, ?);";

        try (Connection conn = CSDBookStoreSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(createRegisteredUser)) {

            ps.setString(1, emailAddress);
            ps.setString(2, password);

            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     *
     * This method gets all the registered users.
     *
     * @author Giuseppe Campanelli
     * @return list of all users
     * @throws SQLException
     */
    @Override
    public List<RegisteredUserBean> findAllUsers() throws SQLException {
        List<RegisteredUserBean> users = new ArrayList<>();

        String select = "SELECT user_id, email_address, password, title_id, first_name, last_name, "
                + "company_name, address_one, address_two, city, province_id, country, postal_code, "
                + "home_phone, cell_phone, manager, active FROM registered_user;";

        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(select);
                ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                RegisteredUserBean user = createUser(resultSet);
                users.add(user);
            }
        }
        return users;
    }

    /**
     *
     * This method gets a user by id.
     *
     * @author Giuseppe Campanelli
     * @param id id of user to get
     * @return registered user bean
     * @throws SQLException
     */
    @Override
    public RegisteredUserBean findUserById(int id) throws SQLException {
        RegisteredUserBean user = new RegisteredUserBean();

        String select = "SELECT user_id, email_address, password, title_id, first_name, last_name, "
                + "company_name, address_one, address_two, city, province_id, country, postal_code, "
                + "home_phone, cell_phone, manager, active FROM registered_user WHERE user_id = ?;";

        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(select)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    user = createUser(resultSet);
                }
            }
        }
        return user;
    }

    /**
     *
     * This method updates a users account status.
     *
     * @author Giuseppe Campanelli
     * @param id id of the user
     * @param isActive account status
     * @return rows affected
     * @throws SQLException
     */
    @Override
    public int setAccountStatus(int id, boolean isActive) throws SQLException {
        int result = 0;
        String update = "UPDATE user SET active = ? WHERE user_id = ? ";

        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(update);) {
            ps.setBoolean(1, isActive);
            ps.setInt(2, id);
            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     *
     * This method updates a users manager status.
     *
     * This method gets a
     *
     * @author Giuseppe Campanelli
     * @param id id of the user
     * @param isManager manager status
     * @return rows affected
     * @throws SQLException
     */
    @Override
    public int setManagerStatus(int id, boolean isManager) throws SQLException {
        int result = 0;
        String update = "UPDATE user SET manager = ? WHERE user_id = ? ";

        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(update);) {
            ps.setBoolean(1, isManager);
            ps.setInt(2, id);
            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     *
     * This method updates a users billing info.
     *
     * @author Giuseppe Campanelli
     * @param updatedUser user to update
     * @param titleIndex index position in list box which matches db position
     * @param provinceIndex index position in list box which matches db position
     * @return rows affected
     * @throws SQLException
     */
    @Override
    public int updateUserBilling(RegisteredUserBean updatedUser, int titleIndex, int provinceIndex) throws SQLException {
        int result = 0;
        String update = "UPDATE user SET title_id = ?, first_name = ?, last_name = ?, company_name = ?, "
                + "address_one = ?, address_two = ?, city = ?, province_id = ?, country = ?, "
                + "postal_code = ?, home_phone = ?, cell_phone = ? WHERE user_id = ? ";

        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(update);) {
            ps.setInt(1, titleIndex);
            ps.setString(2, updatedUser.getFirstName());
            ps.setString(3, updatedUser.getLastName());
            ps.setString(4, updatedUser.getCompanyName());
            ps.setString(5, updatedUser.getAddressOne());
            ps.setString(6, updatedUser.getAddressTwo());
            ps.setString(7, updatedUser.getCity());
            ps.setInt(8, provinceIndex);
            ps.setString(9, updatedUser.getCountry());
            ps.setString(10, updatedUser.getPostalCode());
            ps.setString(11, updatedUser.getHomePhone());
            ps.setString(12, updatedUser.getCellPhone());

            result = ps.executeUpdate();
        }
        return result;
    }

    /**
     *
     * This method updates a users password.
     *
     * @author Giuseppe Campanelli
     * @param id id of the user
     * @param password new password
     * @return
     * @throws SQLException
     */
    @Override
    public int updateUserPassword(int id, String password) throws SQLException {
        int result = 0;
        String update = "UPDATE user SET password = ? WHERE user_id = ? ";

        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(update);) {
            ps.setString(1, password);
            ps.setInt(2, id);
            result = ps.executeUpdate();
        }
        return result;
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

    /**
     * Survey table select by id. Retrieve one record from the given table based
     * on the primary key
     *
     * @author Christopher Dufort
     * @version 0.0.7 - last modified 2/3/2016
     * @param id
     * @return the Survey object
     */
    @Override
    public SurveyBean findSurveyById(int id) throws SQLException {
        //If there is no record with the requested id, null object will be returned.
        SurveyBean surveyBean = null;

        String selectQuery = "SELECT survey_id, question, answer_one, answer_two, answer_three, answer_default FROM survey WHERE ID=?";

        // Using try with resources, available since Java 1.7
        // Class that implement the Closable interface created in the
        // parenthesis () will be closed when the block ends.
        try (Connection connection = CSDBookStoreSource.getConnection();
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement prepStatement = connection.prepareStatement(selectQuery);) {
            // Only object creation statements can be in the parenthesis so
            // first try-with-resources block ends
            prepStatement.setInt(1, id);
            // A new try-with-resources block for creating the ResultSet object

            try (ResultSet resultSet = prepStatement.executeQuery()) {

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
    /**
     * Ad table select by id. Retrieve one record from the given table based
     * on the primary key
     *
     * @author Christopher Dufort
     * @version 0.1.6 - last modified 2/7/2016
     * @param id
     * @return the Ad object
     * @throws java.sql.SQLException
     */
    @Override
    public AdBean findAdById(int id) throws SQLException{
        //If there is no record with the requested id, null object will be returned.
        AdBean adBean = null;

        String selectQuery = "SELECT ad_id, ad_filename FROM ad WHERE ID=?";

        // Using try with resources, available since Java 1.7
        // Class that implement the Closable interface created in the parenthesis () will be closed when the block ends.
        try (Connection connection = CSDBookStoreSource.getConnection();
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement prepStatement = connection.prepareStatement(selectQuery);) {
            // Only object creation statements can be in the parenthesis so first try-with-resources block ends
            prepStatement.setInt(1, id);
            // A new try-with-resources block for creating the ResultSet object
            try (ResultSet resultSet = prepStatement.executeQuery()) {

                if (resultSet.next()) {
                    adBean = new AdBean();

                    adBean.setAdId(id);
                    adBean.setAdFilename(resultSet.getString("ad_filename"));
                }
            }
        }
        return adBean;
    }
    
    /**
     * NewsFeed table select by id. Retrieve one record from the given table based
     * on the primary key
     *
     * @author Christopher Dufort
     * @version 0.1.6 - last modified 2/7/2016
     * @param id
     * @return the NewsFeed object
     * @throws java.sql.SQLException
     */
    @Override
    public NewsFeedBean findNewsFeedById(int id) throws SQLException{
        //If there is no record with the requested id, null object will be returned.
        NewsFeedBean newsFeedBean = null;

        String selectQuery = "SELECT news_feed_id, news_feed_link FROM news_feed WHERE ID=?";

        // Using try with resources, available since Java 1.7
        // Class that implement the Closable interface created in the parenthesis () will be closed when the block ends.
        try (Connection connection = CSDBookStoreSource.getConnection();
                // You must use PreparedStatements to guard against SQL Injection
                PreparedStatement prepStatement = connection.prepareStatement(selectQuery);) {
            // Only object creation statements can be in the parenthesis so first try-with-resources block ends
            prepStatement.setInt(1, id);
            // A new try-with-resources block for creating the ResultSet object
            try (ResultSet resultSet = prepStatement.executeQuery()) {

                if (resultSet.next()) {
                    newsFeedBean = new NewsFeedBean();

                    newsFeedBean.setNewsFeedId(id);
                    newsFeedBean.setNewsFeedLink(resultSet.getString("news_feed_link"));
                }
            }
        }
        return newsFeedBean;
    }
    
    private RegisteredUserBean createUser(final ResultSet resultSet) throws SQLException {
        int titleId, provinceId;

        RegisteredUserBean user = new RegisteredUserBean();
        user.setClientId(resultSet.getInt("user_id"));
        user.setEmailAddress(resultSet.getString("email_address"));
        user.setPassword(resultSet.getString("password"));
        titleId = resultSet.getInt("title_id");
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setCompanyName(resultSet.getString("company_name"));
        user.setAddressOne(resultSet.getString("address_one"));
        user.setAddressTwo(resultSet.getString("address_two"));
        user.setCity(resultSet.getString("city"));
        provinceId = resultSet.getInt("province_id");
        user.setCountry(resultSet.getString("country"));
        user.setPostalCode(resultSet.getString("postal_code"));
        user.setHomePhone(resultSet.getString("home_phone"));
        user.setCellPhone(resultSet.getString("cell_phone"));
        user.setIsManager(resultSet.getBoolean("manager"));
        user.setIsActive(resultSet.getBoolean("active"));

        ProvinceBean province = new ProvinceBean();
        String select = "SELECT name, gst, pst, hst FROM province WHERE name = ?;";

        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement ps = connection.prepareStatement(select)) {
            ps.setInt(1, provinceId);
            try (
                    ResultSet resultSetTaxes = ps.executeQuery()) {

                if (resultSetTaxes.next()) {
                    province.setName(resultSetTaxes.getString("name"));
                    province.setGst(resultSetTaxes.getDouble("gst"));
                    province.setPst(resultSetTaxes.getDouble("pst"));
                    province.setHst(resultSetTaxes.getDouble("hst"));
                }
            }

            user.setProvince(province);

            String title = "";
            select = "SELECT title FROM title WHERE title_id = ?;";

            try (PreparedStatement ps2 = connection.prepareStatement(select)) {
                ps2.setInt(1, titleId);
                try (ResultSet resultSetTitle = ps2.executeQuery()) {

                    if (resultSetTitle.next()) {
                        province.setName(resultSetTitle.getString("name"));
                        province.setGst(resultSetTitle.getDouble("gst"));
                        province.setPst(resultSetTitle.getDouble("pst"));
                        province.setHst(resultSetTitle.getDouble("hst"));
                    }
                }
                user.setTitle(title);

                return user;
            }
        }
    }

    @Override
    public int createAuthor(AuthorBean author) throws SQLException {
        String query = "INSERT INTO author (author_name) VALUES(?);";
        int result = 0;
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            pStatement.setString(1, author.getAuthor_name());
            result = pStatement.executeUpdate();
            ResultSet resultSet = pStatement.getGeneratedKeys();
            if (resultSet.next()) {
                author.setAuthor_id(resultSet.getInt(1));
            }
        }
        return result;
    }

    @Override
    public int deleteAuthorByAuthorId(int authorId) throws SQLException {
        int result = 0;
        String query = "DELETE FROM author WHERE author_id=?;";
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(query);) {
            pStatement.setInt(1, authorId);
            result = pStatement.executeUpdate();
        }
        return result;
    }

    @Override
    public int updateAuthor(AuthorBean author) throws SQLException {
        int result = 0;
        String query = "UPDATE author SET author_name = ? WHERE author_id=?";
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(query);) {
            pStatement.setString(1, author.getAuthor_name());
            pStatement.setInt(2, author.getAuthor_id());
            result = pStatement.executeUpdate();
        }
        return result;
    }

    @Override
    public List<AuthorBean> getAllAuthor() throws SQLException {
        List<AuthorBean> authors = new ArrayList<>();
        String query = "SELECT * FROM author";
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(query);) {
            ResultSet resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                authors.add(new AuthorBean(
                        resultSet.getInt("author_id"),
                        resultSet.getString("author_name")
                ));
            }
        }
        return authors;
    }

    @Override
    public List<AuthorBean> getAuthorByBook(BookBean book) throws SQLException {
        ArrayList<AuthorBean> authors = new ArrayList<>();
        String query_book_author = "SELECT * FROM author A WHERE EXISTS(SELECT 1 FROM book_author BA WHERE BA.book_id=? AND BA.author_id=A.author_id)";
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(query_book_author);) {
            ResultSet resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                authors.add(
                        new AuthorBean(
                                new Integer(resultSet.getInt("author_id")),
                                new String(resultSet.getString("author_name"))
                        )
                );
            }
        }
        return authors;
    }

    @Override
    public int createFormat(FormatBean format) throws SQLException {
        String query = "INSERT INTO format (extension) VALUES(?);";
        int result = 0;
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            pStatement.setString(1, format.getExtension());
            result = pStatement.executeUpdate();
            ResultSet resultSet = pStatement.getGeneratedKeys();
            if (resultSet.next()) {
                format.setFormat_id(resultSet.getInt(1));
            }
        }
        return result;
    }

    @Override
    public int updateFormat(FormatBean format) throws SQLException {
        int result = 0;
        String query = "UPDATE format SET extension = ? WHERE format_id=?";
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(query);) {
            pStatement.setString(1, format.getExtension());
            pStatement.setInt(2, format.getFormat_id());
            result = pStatement.executeUpdate();
        }
        return result;
    }

    @Override
    public int deleteById(int formatId) throws SQLException {
        int result = 0;
        String query = "DELETE FROM format WHERE format_id=?;";
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(query);) {
            pStatement.setInt(1, formatId);
            result = pStatement.executeUpdate();
        }
        return result;
    }

    @Override
    public List<FormatBean> getAllFormat() throws SQLException {
        List<FormatBean> formats = new ArrayList<>();
        String query = "SELECT * FROM format";
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(query);) {
            ResultSet resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                formats.add(new FormatBean(
                        resultSet.getInt("format_id"),
                        resultSet.getString("extension")
                ));
            }
        }
        return formats;
    }

    @Override
    public List<FormatBean> getFormatByBook(BookBean book) throws SQLException {
        List<FormatBean> formats = new ArrayList<>();
        String query_book_author = "SELECT * FROM format F WHERE EXISTS(SELECT 1 FROM book_format BA WHERE BA.book_id=? AND BA.format_id=F.format_id)";
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(query_book_author);) {
            ResultSet resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                formats.add(
                        new FormatBean(
                                new Integer(resultSet.getInt("format_id")),
                                new String(resultSet.getString("extension"))
                        )
                );
            }
        }
        return formats;
    }

    @Override
    public int createGenre(GenreBean genre) throws SQLException {
        String query = "INSERT INTO genre (genre_name) VALUES(?);";
        int result = 0;
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            pStatement.setString(1, genre.getGenre_name());
            result = pStatement.executeUpdate();
            ResultSet resultSet = pStatement.getGeneratedKeys();
            if (resultSet.next()) {
                genre.setGenre_id(resultSet.getInt(1));
            }
        }
        return result;
    }

    @Override
    public int updateGenre(GenreBean genre) throws SQLException {
        int result = 0;
        String query = "UPDATE genre SET genre_name = ? WHERE genre_id=?";
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(query);) {
            pStatement.setString(1, genre.getGenre_name());
            pStatement.setInt(2, genre.getGenre_id());
            result = pStatement.executeUpdate();
        }
        return result;
    }

    @Override
    public int deleteGenreById(int genreId) throws SQLException {
        int result = 0;
        String query = "DELETE FROM genre WHERE genre_id=?;";
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(query);) {
            pStatement.setInt(1, genreId);
            result = pStatement.executeUpdate();
        }
        return result;
    }

    @Override
    public List<GenreBean> getAllGenre() throws SQLException {
        List<GenreBean> genres = new ArrayList<>();
        String query = "SELECT * FROM genre";
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(query);) {
            ResultSet resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                genres.add(new GenreBean(
                        resultSet.getInt("genre_id"),
                        resultSet.getString("genre_name")
                ));
            }
        }
        return genres;
    }

    @Override
    public List<GenreBean> getGenreByBook(BookBean book) throws SQLException {
        List<GenreBean> genres = new ArrayList<>();
        String query_book_author = "SELECT * FROM genre G WHERE EXISTS(SELECT 1 FROM book_genre BA WHERE BA.book_id=? AND BA.genre_id=G.genre_id)";
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(query_book_author);) {
            ResultSet resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                genres.add(
                        new GenreBean(
                                resultSet.getInt("genre_id"),
                                resultSet.getString("genre_name")
                        )
                );
            }
        }
        return genres;
    }

    @Override
    public int createBook(BookBean book, List<AuthorBean> authors, List<FormatBean> formats, List<GenreBean> genres) throws SQLException {
        String queryBook = "INSERT INTO book (isbn,title,publisher,publish_date,"
                + "page_number,wholesale_price,list_price,sale_price"
                + "date_entered,available,synopsis )"
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        String queryInsertBookAuthor = "INSERT INTO book_author (book_id, author_id)"
                + "VALUES(?,?)";
        String queryInsertBookFormat = "INSERT INTO book_format (book_id, format_id)"
                + "VALUES(?,?)";
        String queryInsertBookGenre = "INSERT INTO book_genre (book_id, genre_id)"
                + "VALUES(?,?";
        int result = 0;
        Date today = null;
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatementBook = connection.prepareStatement(queryBook, Statement.RETURN_GENERATED_KEYS);) {
            // Create book
            pStatementBook.setString(1, book.getIsbn());
            pStatementBook.setString(2, book.getTitle());
            pStatementBook.setString(3, book.getPublisher());
            pStatementBook.setDate(4, book.getPublish_date());
            pStatementBook.setInt(5, book.getPages());
            pStatementBook.setDouble(6, book.getWholesale_price());
            pStatementBook.setDouble(7, book.getList_price());
            pStatementBook.setDouble(8, book.getSale_price());
            pStatementBook.setDate(9, today);
            pStatementBook.setBoolean(10, book.isAvailable());
            pStatementBook.setString(11, book.getSynopsis());

            result = pStatementBook.executeUpdate();
            ResultSet resultSet = pStatementBook.getGeneratedKeys();
            if (resultSet.next()) {
                book.setBook_id(resultSet.getInt(1));
            }

            // Create relation between book & author
            for (AuthorBean author : authors) {
                try (PreparedStatement pStatementBookAuthor = connection.prepareStatement(queryInsertBookAuthor)) {
                    pStatementBookAuthor.setInt(1, book.getId());
                    pStatementBookAuthor.setInt(2, author.getAuthor_id());
                    result += pStatementBookAuthor.executeUpdate();
                }
            }
            // Create relation between book & format
            for (FormatBean format : formats) {
                try(PreparedStatement pStatementBookFormat = connection.prepareStatement(queryInsertBookFormat)){
                    pStatementBookFormat.setInt(1, book.getId());
                    pStatementBookFormat.setInt(2, format.getFormat_id());
                    result += pStatementBookFormat.executeUpdate();
                }
            }
            // Create relation between book & genre
            for (GenreBean genre : genres){
                try(PreparedStatement pStatementBookGenre = connection.prepareStatement(queryInsertBookGenre)){
                    pStatementBookGenre.setInt(1, book.getId());
                    pStatementBookGenre.setInt(2, genre.getGenre_id());
                    result += pStatementBookGenre.executeUpdate();
                }
            }
        }

        return result;
    }

    @Override
    public int updateBook(BookBean book, List<AuthorBean> authors, List<FormatBean> formats, List<GenreBean> genres) throws SQLException {
        String queryBook = "UPDATE book SET isbn=?,title=?,publisher=?,publish_date=?,"
                + "page_number=?,wholesale_price=?,list_price=?,sale_price=?"
                + "date_entered=?,available=?,synopsis=? )"
                + "WHERE book_id=?";
        String queryPurgeBookAuthor = "DELETE FROM book_author WHERE book_id=?";
        String queryInsertBookAuthor = "INSERT INTO book_author (book_id, author_id)"
                + "VALUES(?,?)";
        String queryPurgeBookFormat = "DELETE FROM book_format WHERE book_id=?";
        String queryInsertBookFormat = "INSERT INTO book_format (book_id, format_id)"
                + "VALUES(?,?)";
        String queryPurgeBookGenre = "DELETE FROM book_genre WHERE book_id=?";
        String queryInsertBookGenre = "INSERT INTO book_genre (book_id, genre_id)"
                + "VALUES(?,?";
        int result = 0;
        Date today = null;
        try (Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatementBook = connection.prepareStatement(queryBook, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement pStatementPurgeBookAuthor = connection.prepareStatement(queryPurgeBookAuthor);
                PreparedStatement pStatementPurgeBookFormat = connection.prepareStatement(queryPurgeBookFormat);
                PreparedStatement pStatementPurgeBookGenre = connection.prepareStatement(queryPurgeBookGenre);) {
            // Update Book
            pStatementBook.setString(1, book.getIsbn());
            pStatementBook.setString(2, book.getTitle());
            pStatementBook.setString(3, book.getPublisher());
            pStatementBook.setDate(4, book.getPublish_date());
            pStatementBook.setInt(5, book.getPages());
            pStatementBook.setDouble(6, book.getWholesale_price());
            pStatementBook.setDouble(7, book.getList_price());
            pStatementBook.setDouble(8, book.getSale_price());
            pStatementBook.setDate(9, today);
            pStatementBook.setBoolean(10, book.isAvailable());
            pStatementBook.setString(11, book.getSynopsis());
            pStatementBook.setInt(12, book.getId());

            result = pStatementBook.executeUpdate();
            ResultSet resultSet = pStatementBook.getGeneratedKeys();
            if (resultSet.next()) {
                book.setBook_id(resultSet.getInt(1));
            }

            // Purge existing relation between book & author
            pStatementPurgeBookAuthor.setInt(1,book.getId());
            result+=pStatementPurgeBookAuthor.executeUpdate();
            // Create relation between book & author
            for (AuthorBean author : authors) {
                try (PreparedStatement pStatementBookAuthor = connection.prepareStatement(queryInsertBookAuthor)) {
                    pStatementBookAuthor.setInt(1, book.getId());
                    pStatementBookAuthor.setInt(2, author.getAuthor_id());
                    result += pStatementBookAuthor.executeUpdate();
                }
            }
            // Purge existing relation between book & format
            pStatementPurgeBookFormat.setInt(1,book.getId());
            result+=pStatementPurgeBookFormat.executeUpdate();
            // Create relation between book & format
            for (FormatBean format : formats) {
                try(PreparedStatement pStatementBookFormat = connection.prepareStatement(queryInsertBookFormat)){
                    pStatementBookFormat.setInt(1, book.getId());
                    pStatementBookFormat.setInt(2, format.getFormat_id());
                    result += pStatementBookFormat.executeUpdate();
                }
            }
            // Purge existing relation between book & author
            pStatementPurgeBookGenre.setInt(1,book.getId());
            result+=pStatementPurgeBookGenre.executeUpdate();
            // Create relation between book & genre
            for (GenreBean genre : genres){
                try(PreparedStatement pStatementBookGenre = connection.prepareStatement(queryInsertBookGenre)){
                    pStatementBookGenre.setInt(1, book.getId());
                    pStatementBookGenre.setInt(2, genre.getGenre_id());
                    result += pStatementBookGenre.executeUpdate();
                }
            }
        }

        return result;
    }

    @Override
    public int deleteBook(int book_id) throws SQLException {
        int result = 0;
        String queryBook = "UPDATE book SET availble=FALSE WHERE book_id=?";
        try(Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatementBook = connection.prepareStatement(queryBook);){
            //delete book
            pStatementBook.setInt(1, book_id);
            result+= pStatementBook.executeUpdate();
        }
        return result;
    }

    @Override
    public List<BookBean> getAllBook() throws SQLException {
        String query = "SELECT * FROM book";
        String queryAuthors = "SELET * FROM author a WHERE EXISTS(SELECT 1 FROM book_author ba WHERE ba.book_id=? AND ba.author_id=a.author_id)";
        String queryFormats = "SELET * FROM format f WHERE EXISTS(SELECT 1 FROM book_format bf WHERE bf.book_id=? AND bf.format_id=f.format_id)";
        String queryGenres = "SELET * FROM genre g WHERE EXISTS(SELECT 1 FROM book_genre bg WHERE bg.book_id=? AND bg.genre_id=g.genre_id)";
        List<BookBean> books = new ArrayList<>();
        try(Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(query);
                PreparedStatement pStatementAuthors = connection.prepareStatement(queryAuthors);
                PreparedStatement pStatementFormats = connection.prepareStatement(queryFormats);
                PreparedStatement pStatementGenres = connection.prepareStatement(queryGenres)){
            ResultSet resultSet = pStatement.executeQuery();
            while(resultSet.next()){
                BookBean book = new BookBean(
                        resultSet.getInt("book_id"),
                        resultSet.getString("isbn"),
                        resultSet.getString("title"),
                        resultSet.getString("publisher"),
                        resultSet.getDate("publish_date"),
                        resultSet.getInt("page_number"),
                        resultSet.getDouble("wholesale_price"),
                        resultSet.getDouble("list_price"),
                        resultSet.getDouble("sale_price"),
                        resultSet.getDate("date_entered"),
                        resultSet.getBoolean("available"),
                        resultSet.getDouble("overall_rating"),
                        resultSet.getString("synopsis")
                );
                // gathering data about authors, formats && genres related w/ book
                pStatementAuthors.setInt(1, book.getId());
                ResultSet resultSetAuthors = pStatementAuthors.executeQuery();
                while(resultSetAuthors.next()){
                    book.getAuthors().add(new AuthorBean(
                            resultSetAuthors.getInt("author_id"),
                            resultSetAuthors.getString("author_name")
                    ));
                }
                pStatementFormats.setInt(1, book.getId());
                ResultSet resultSetFormats = pStatementFormats.executeQuery();
                while(resultSetFormats.next()){
                    book.getFormats().add(new FormatBean(
                            resultSetFormats.getInt("format_id"),
                            resultSetFormats.getString("extension")
                    ));
                }
                pStatementGenres.setInt(1, book.getId());
                ResultSet resultSetGenres = pStatementGenres.executeQuery();
                while(resultSetGenres.next()){
                    book.getGenres().add(new GenreBean(
                            resultSetGenres.getInt("genre_id"),
                            resultSetGenres.getString("genre_name")
                    ));
                }
                // adding it to the result of our function :) It would be too bad to forget
                books.add(book);
            }
        }
        return books;
    }

    @Override
    public List<BookBean> getBookByAuthors(AuthorBean... authors) throws SQLException {
        // building select query
        String query;
        if (authors.length>0){
            query = "SELECT * FROM book b WHERE EXISTS(SELECT 1 FROM book_author ba WHERE ba.book_id=b.book_id AND (";
            for (int i=0; i<authors.length; i++){
                query+=" ba.author_id=? ";
                if (i<authors.length-1){
                    query+=" OR ";
                }
                query+= " )";
            }
        }else{
            return getAllBook();
        }
        String queryAuthors = "SELET * FROM author a WHERE EXISTS(SELECT 1 FROM book_author ba WHERE ba.book_id=? AND ba.author_id=a.author_id)";
        String queryFormats = "SELET * FROM format f WHERE EXISTS(SELECT 1 FROM book_format bf WHERE bf.book_id=? AND bf.format_id=f.format_id)";
        String queryGenres = "SELET * FROM genre g WHERE EXISTS(SELECT 1 FROM book_genre bg WHERE bg.book_id=? AND bg.genre_id=g.genre_id)";
        List<BookBean> books = new ArrayList<>();
        // selecting books from db
        try(Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(query);
                PreparedStatement pStatementAuthors = connection.prepareStatement(queryAuthors);
                PreparedStatement pStatementFormats = connection.prepareStatement(queryFormats);
                PreparedStatement pStatementGenres = connection.prepareStatement(queryGenres);){
            for(int i = 0; i<authors.length; i++){
                pStatement.setInt(i+1, authors[i].getAuthor_id());
            }
            ResultSet resultSet = pStatement.executeQuery();
            while(resultSet.next()){
                BookBean book = new BookBean(
                        resultSet.getInt("book_id"),
                        resultSet.getString("isbn"),
                        resultSet.getString("title"),
                        resultSet.getString("publisher"),
                        resultSet.getDate("publish_date"),
                        resultSet.getInt("page_number"),
                        resultSet.getDouble("wholesale_price"),
                        resultSet.getDouble("list_price"),
                        resultSet.getDouble("sale_price"),
                        resultSet.getDate("date_entered"),
                        resultSet.getBoolean("available"),
                        resultSet.getDouble("overall_rating"),
                        resultSet.getString("synopsis")
                );
                // gathering data about authors, formats && genres related w/ book
                pStatementAuthors.setInt(1, book.getId());
                ResultSet resultSetAuthors = pStatementAuthors.executeQuery();
                while(resultSetAuthors.next()){
                    book.getAuthors().add(new AuthorBean(
                            resultSetAuthors.getInt("author_id"),
                            resultSetAuthors.getString("author_name")
                    ));
                }
                pStatementFormats.setInt(1, book.getId());
                ResultSet resultSetFormats = pStatementFormats.executeQuery();
                while(resultSetFormats.next()){
                    book.getFormats().add(new FormatBean(
                            resultSetFormats.getInt("format_id"),
                            resultSetFormats.getString("extension")
                    ));
                }
                pStatementGenres.setInt(1, book.getId());
                ResultSet resultSetGenres = pStatementGenres.executeQuery();
                while(resultSetGenres.next()){
                    book.getGenres().add(new GenreBean(
                            resultSetGenres.getInt("genre_id"),
                            resultSetGenres.getString("genre_name")
                    ));
                }
                // adding it to the result of our function :) It would be too bad to forget
                books.add(book);
            }
        }
        return books;
    }

    @Override
    public List<BookBean> getBookByFormats(FormatBean... formats) throws SQLException {
        // building select query
        String query;
        if (formats.length>0){
            query = "SELECT * FROM book b WHERE EXISTS(SELECT 1 FROM book_format bf WHERE bf.book_id=b.book_id AND (";
            for (int i=0; i<formats.length; i++){
                query+=" bf.format_id=? ";
                if (i<formats.length-1){
                    query+=" OR ";
                }
                query+= " )";
            }
        }else{
            return getAllBook();
        }
        String queryAuthors = "SELET * FROM author a WHERE EXISTS(SELECT 1 FROM book_author ba WHERE ba.book_id=? AND ba.author_id=a.author_id)";
        String queryFormats = "SELET * FROM format f WHERE EXISTS(SELECT 1 FROM book_format bf WHERE bf.book_id=? AND bf.format_id=f.format_id)";
        String queryGenres = "SELET * FROM genre g WHERE EXISTS(SELECT 1 FROM book_genre bg WHERE bg.book_id=? AND bg.genre_id=g.genre_id)";
        List<BookBean> books = new ArrayList<>();
        // selecting books from db
        try(Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(query);
                PreparedStatement pStatementAuthors = connection.prepareStatement(queryAuthors);
                PreparedStatement pStatementFormats = connection.prepareStatement(queryFormats);
                PreparedStatement pStatementGenres = connection.prepareStatement(queryGenres);){
            for(int i = 0; i<formats.length; i++){
                pStatement.setInt(i+1, formats[i].getFormat_id());
            }
            ResultSet resultSet = pStatement.executeQuery();
            while(resultSet.next()){
                // FIXME: List of Authors, Formats & Genres aren't initialize
                BookBean book = new BookBean(
                        resultSet.getInt("book_id"),
                        resultSet.getString("isbn"),
                        resultSet.getString("title"),
                        resultSet.getString("publisher"),
                        resultSet.getDate("publish_date"),
                        resultSet.getInt("page_number"),
                        resultSet.getDouble("wholesale_price"),
                        resultSet.getDouble("list_price"),
                        resultSet.getDouble("sale_price"),
                        resultSet.getDate("date_entered"),
                        resultSet.getBoolean("available"),
                        resultSet.getDouble("overall_rating"),
                        resultSet.getString("synopsis")
                );
                
                // gathering data about authors, formats && genres related w/ book
                pStatementAuthors.setInt(1, book.getId());
                ResultSet resultSetAuthors = pStatementAuthors.executeQuery();
                while(resultSetAuthors.next()){
                    book.getAuthors().add(new AuthorBean(
                            resultSetAuthors.getInt("author_id"),
                            resultSetAuthors.getString("author_name")
                    ));
                }
                pStatementFormats.setInt(1, book.getId());
                ResultSet resultSetFormats = pStatementFormats.executeQuery();
                while(resultSetFormats.next()){
                    book.getFormats().add(new FormatBean(
                            resultSetFormats.getInt("format_id"),
                            resultSetFormats.getString("extension")
                    ));
                }
                pStatementGenres.setInt(1, book.getId());
                ResultSet resultSetGenres = pStatementGenres.executeQuery();
                while(resultSetGenres.next()){
                    book.getGenres().add(new GenreBean(
                            resultSetGenres.getInt("genre_id"),
                            resultSetGenres.getString("genre_name")
                    ));
                }
                // adding it to the result of our function :) It would be too bad to forget
                books.add(book);
            }
        }
        return books;
    }

    @Override
    public List<BookBean> getBookByGenres(GenreBean... genres) throws SQLException {
        // building select query
        String query;
        if (genres.length>0){
            query = "SELECT * FROM book b WHERE EXISTS(SELECT 1 FROM book_genre bg WHERE bg.book_id=b.book_id AND (";
            for (int i=0; i<genres.length; i++){
                query+=" bg.genre_id=? ";
                if (i<genres.length-1){
                    query+=" OR ";
                }
                query+= " )";
            }
        }else{
            return getAllBook();
        }
        String queryAuthors = "SELET * FROM author a WHERE EXISTS(SELECT 1 FROM book_author ba WHERE ba.book_id=? AND ba.author_id=a.author_id)";
        String queryFormats = "SELET * FROM format f WHERE EXISTS(SELECT 1 FROM book_format bf WHERE bf.book_id=? AND bf.format_id=f.format_id)";
        String queryGenres = "SELET * FROM genre g WHERE EXISTS(SELECT 1 FROM book_genre bg WHERE bg.book_id=? AND bg.genre_id=g.genre_id)";
        List<BookBean> books = new ArrayList<>();
        // selecting books from db
        try(Connection connection = CSDBookStoreSource.getConnection();
                PreparedStatement pStatement = connection.prepareStatement(query);
                PreparedStatement pStatementAuthors = connection.prepareStatement(queryAuthors);
                PreparedStatement pStatementFormats = connection.prepareStatement(queryFormats);
                PreparedStatement pStatementGenres = connection.prepareStatement(queryGenres);){
            for(int i = 0; i<genres.length; i++){
                pStatement.setInt(i+1, genres[i].getGenre_id());
            }
            ResultSet resultSet = pStatement.executeQuery();
            while(resultSet.next()){
                // FIXME: List of Authors, Formats & Genres aren't initialize
                BookBean book = new BookBean(
                        resultSet.getInt("book_id"),
                        resultSet.getString("isbn"),
                        resultSet.getString("title"),
                        resultSet.getString("publisher"),
                        resultSet.getDate("publish_date"),
                        resultSet.getInt("page_number"),
                        resultSet.getDouble("wholesale_price"),
                        resultSet.getDouble("list_price"),
                        resultSet.getDouble("sale_price"),
                        resultSet.getDate("date_entered"),
                        resultSet.getBoolean("available"),
                        resultSet.getDouble("overall_rating"),
                        resultSet.getString("synopsis")
                );
                
                // gathering data about authors, formats && genres related w/ book
                pStatementAuthors.setInt(1, book.getId());
                ResultSet resultSetAuthors = pStatementAuthors.executeQuery();
                while(resultSetAuthors.next()){
                    book.getAuthors().add(new AuthorBean(
                            resultSetAuthors.getInt("author_id"),
                            resultSetAuthors.getString("author_name")
                    ));
                }
                pStatementFormats.setInt(1, book.getId());
                ResultSet resultSetFormats = pStatementFormats.executeQuery();
                while(resultSetFormats.next()){
                    book.getFormats().add(new FormatBean(
                            resultSetFormats.getInt("format_id"),
                            resultSetFormats.getString("extension")
                    ));
                }
                pStatementGenres.setInt(1, book.getId());
                ResultSet resultSetGenres = pStatementGenres.executeQuery();
                while(resultSetGenres.next()){
                    book.getGenres().add(new GenreBean(
                            resultSetGenres.getInt("genre_id"),
                            resultSetGenres.getString("genre_name")
                    ));
                }
                // adding it to the result of our function :) It would be too bad to forget
                books.add(book);
            }
        }
        return books;
    }

    @Override
    public int createBook(BookBean book) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<BookBean> getBookByTitle(String title) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BookBean getBookByISBN(String isbn) throws SQLException {
         throw new UnsupportedOperationException("Not finished "); //To change body of generated methods, choose Tools | Templates.
//        //If there is no record with the requested id, null object will be returned.
//        BookBean bookBean = null;
//        List<AuthorBean> authorBeans = null;
//        List<FormatBean> formatBeans = null;
//        List<GenreBean> genreBeans = null;
//
//        String selectBookQuery = "SELECT book_id, isbn, title, publisher, publish_date, page_number,"
//                + " wholesale_price, list_price, sale_price, date_entered, available, overall_rating, synopsis,"
//                + " author_id, author_name"
//                + " FROM book b JOIN book_author ba ON b.book_id=ba.book_id JOIN author a ON ba.author_id ="
//                + " WHERE isbn=?";
//        
//        String selectFormatQuery = "SELECT format_id FROM book_format where book_id=?";
//        String selectAuthorQuery = "SELECT author_id FROM book_author where book_id=?";
//        String SelectGenreQuery = "SELECT genre_id FROM book_genre where book_id=?";
//        
//        String selectedAuthorNameQuery = "SELECT author_name FROM author WHERE author_id=?";
//        String selectExtensionQuery = "SELECT extension FROM format WHERE format_id=?";
//        String SelectGenreNameQuery = "SELECT genre_name FROM genre WHERE genre_id=?";
//
//        // Using try with resources, available since Java 1.7
//        // Class that implement the Closable interface created in the
//        // parenthesis () will be closed when the block ends.
//        try (Connection connection = CSDBookStoreSource.getConnection();
//                // You must use PreparedStatements to guard against SQL Injection
//                PreparedStatement ps1 = connection.prepareStatement(selectBookQuery);
//                PreparedStatement ps2 = connection.prepareStatement(selectFormatQuery);
//                PreparedStatement ps3 = connection.prepareStatement(selectAuthorQuery);
//                PreparedStatement ps4 = connection.prepareStatement(SelectGenreQuery); ) {
//            
//            // Only object creation statements can be in the parenthesis so
//            // first try-with-resources block ends
//            prepStatement1.setString(1, isbn);
//            // A new try-with-resources block for creating the ResultSet object
//            try (ResultSet resultSet = prepStatement1.executeQuery()) {
//
//                if (resultSet.next()) {
//                    bookBean = new BookBean(
//                        resultSet.getInt("book_id"),
//                        isbn,
//                        resultSet.getString("title"),
//                        resultSet.getString("publisher"),
//                        resultSet.getDate("publish_date"),
//                        resultSet.getInt("page_number"),
//                        resultSet.getDouble("wholesale_price"),
//                        resultSet.getDouble("list_price"),
//                        resultSet.getDouble("sale_price"),
//                        resultSet.getDate("date_entered"),
//                        resultSet.getBoolean("available"),
//                        resultSet.getDouble("overall_rating"),
//                        resultSet.getString("synopsis")     
//                    ); 
//                }
//            }
//             //create author objects for each author of the book
//            prepStatement2.setString(1, bookBean.getIsbn());
//            try(ResultSet resultSet = prepStatement2.executeQuery();){
//                
//                 if (resultSet.next()) {
//                     authorBeans = new ArrayList<>();
//                     
//                     while(resultSet.next()){
//                         authorBeans.add(new AuthorBean())
//                     }
//                 }      
//            }
//               
//                //create format objects for each format of the book
//                prepStatement3.setString(1, bookBean.getIsbn());  
//                prepStatement3.executeQuery();
//                //create genre objects for each genre of the book 
//                prepStatement4.setString(1, bookBean.getIsbn()); 
//                prepStatement4.executeQuery();
//                    
//                    
//                    
//                    bookBean.getAuthors()
//                    bookBean.getFormats()
//                    bookBean.getGenres()
//
//        }//end of connection try block
//        return bookBean;
    }

    @Override
    public List<BookBean> getBookByPublisher(String publisher) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
