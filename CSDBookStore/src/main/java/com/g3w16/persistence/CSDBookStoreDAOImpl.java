/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.g3w16.persistence;

import com.g3w16.beans.AuthorBean;
import com.g3w16.beans.BookBean;
import com.g3w16.beans.FormatBean;
import com.g3w16.beans.GenreBean;
import com.g3w16.beans.InvoiceBean;
import com.g3w16.beans.InvoiceDetailBean;
import com.g3w16.beans.RegisteredUserBean;
import com.g3w16.beans.ReviewBean;
import com.g3w16.beans.SurveyBean;
import com.g3w16.beans.ProvinceBean;
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
     * @param province province name to get bean of
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
            pStatement.setString(1, author.getName());
            result = pStatement.executeUpdate();
            ResultSet resultSet = pStatement.getGeneratedKeys();
            if (resultSet.next()) {
                author.setId(resultSet.getInt(1));
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
            pStatement.setString(1, author.getName());
            pStatement.setInt(2, author.getId());
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
                format.setId(resultSet.getInt(1));
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
            pStatement.setInt(2, format.getId());
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
            pStatement.setString(1, genre.getName());
            result = pStatement.executeUpdate();
            ResultSet resultSet = pStatement.getGeneratedKeys();
            if (resultSet.next()) {
                genre.setId(resultSet.getInt(1));
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
            pStatement.setString(1, genre.getName());
            pStatement.setInt(2, genre.getId());
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
}
