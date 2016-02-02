--Author Xin
DROP DATABASE IF EXISTS CSDBookStore;
CREATE DATABASE CSDBookStore;
--Author Xin
GRANT ALL ON CSDBookStore.* TO g3w16@"%" IDENTIFIED BY "g3w16";
GRANT ALL ON CSDBookStore.* TO g3w16@"localhost" IDENTIFIED BY "g3w16";
--Author Xin
USE CSDBookStore;

--Author Jonas & Chris
DROP TABLE IF EXISTS book_format;
DROP TABLE IF EXISTS format;
DROP TABLE IF EXISTS book_genre;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS book_author;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS book;

--Author Jonas & Chris
CREATE TABLE book(
    book_id INT(6) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(14) NOT NULL UNIQUE,
    title VARCHAR(200) NOT NULL,
    publisher VARCHAR(70) NOT NULL,
    publish_date DATETIME NOT NULL,
    page_number INT(5) NOT NULL,
    wholesale_price NUMERIC(15,2) NOT NULL,
    list_price NUMERIC(15,2),
    sale_price NUMERIC(15,2),
    date_entered DATETIME NOT NULL,
    available BIT(1) NOT NULL,
    overall_rating NUMERIC(2,1) NOT NULL DEFAUlT 0,
    synopsis MEDIUMTEXT
)ENGINE=InnoDB;

--Author Jonas & Chris
CREATE TABLE author(
    author_id INT(6) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    author_name VARCHAR(30) NOT NULL
)ENGINE=InnoDB;

--Author Jonas & Chris
CREATE TABLE book_author(
    book_id INT(6) NOT NULL,
    author_id INT(6) NOT NULL,
    PRIMARY KEY (book_id, author_id),
    CONSTRAINT book_author_book_id_fk FOREIGN KEY (book_id) REFERENCES book(book_id) ON DELETE CASCADE,
    CONSTRAINT book_author_author_id_fk FOREIGN KEY (author_id) REFERENCES author(author_id) ON DELETE CASCADE
)ENGINE=InnoDB;

--Author Jonas & Chris
CREATE TABLE genre(
    genre_id INT(3) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    genre_name VARCHAR(30) NOT NULL
)ENGINE=InnoDB;

--Author Jonas & Chris
CREATE TABLE book_genre(
    book_id INT(6) NOT NULL,
    genre_id INT(3) NOT NULL,
    PRIMARY KEY (book_id, genre_id),
    CONSTRAINT book_genre_book_id_fk FOREIGN KEY (book_id)REFERENCES book(book_id) ON DELETE CASCADE,
    CONSTRAINT book_genre_subgenre_id_fk FOREIGN KEY (genre_id) REFERENCES genre(genre_id) ON DELETE CASCADE
)ENGINE=InnoDB;

--Author Jonas & Chris
CREATE TABLE format(
    format_id INT(6) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    extension VARCHAR(7) NOT NULL UNIQUE
)ENGINE=InnoDB;

--Author Jonas & Chris
CREATE TABLE book_format(
    book_id INT(6) NOT NULL,
    format_id INT(6) NOT NULL,
    PRIMARY KEY (book_id, format_id),
    CONSTRAINT book_id_fk FOREIGN KEY (book_id) REFERENCES book(book_id) ON DELETE CASCADE,
    CONSTRAINT format_id_fk FOREIGN KEY (format_id) REFERENCES format(format_id) ON DELETE CASCADE
)ENGINE=InnoDB;

--Author Joey
DROP TABLE IF EXISTS registered_user;
DROP TABLE IF EXISTS province;
DROP TABLE IF EXISTS title;

--Author Joey
CREATE TABLE title (
    title_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(10) NOT NULL
)ENGINE=InnoDB;

--Author Joey
CREATE TABLE province (
    province_id INT AUTO_INCREMENT PRIMARY KEY,
    province VARCHAR(30) NOT NULL,
    pst DECIMAL(12,2),
    gst DECIMAL(12,2),
    hst DECIMAL(12,2)
)ENGINE=InnoDB;

--Author Joey
CREATE TABLE registered_user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email_address VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(15) NOT NULL,
    title_id INT,
    first_name VARCHAR(25),
    last_name VARCHAR(25),
    company_name VARCHAR(50),
    address_one VARCHAR(50),
    address_two VARCHAR(50),
    city VARCHAR(25),
    province_id INT,
    country VARCHAR(25),
    postal_code VARCHAR(10),
    home_phone VARCHAR(25),
    cell_phone VARCHAR(25),
    last_search_genre INT(6),
    manager boolean DEFAULT FALSE,
    active boolean DEFAULT TRUE,
    FOREIGN KEY (title_id) REFERENCES title(title_id),
    FOREIGN KEY (province_id) REFERENCES province(province_id)
    #FOREIGN KEY (last_search_genre) REFERENCES genre(genre_id)
)ENGINE=InnoDB;


--Author Xin
DROP TABLE IF EXISTS approval;
CREATE TABLE approval (
    approval_id INT NOT NULL AUTO_INCREMENT,
    approval_status VARCHAR(10), 
    PRIMARY KEY  (approval_id)
) ENGINE=InnoDB;

-- Author Xin
DROP TABLE IF EXISTS review;
CREATE TABLE review (
    review_id INT NOT NULL AUTO_INCREMENT,
    isbn VARCHAR(14),
    date_submitted DATE,
    user_id INT,
    rating INT,
    approval_id INT,
    review_title VARCHAR(50)  default '',
    review_text VARCHAR(750) default '',
    PRIMARY KEY  (review_id),
    CONSTRAINT approval_id_FK FOREIGN KEY (approval_id) REFERENCES approval (approval_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT isbn_FK FOREIGN KEY (isbn) REFERENCES book (isbn) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT client_id_FK FOREIGN KEY (user_id) REFERENCES registered_user (user_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

--Author Rita
DROP TABLE IF EXISTS invoice;
CREATE TABLE IF NOT EXISTS invoice (
    sale_number int(11) NOT NULL AUTO_INCREMENT,
    sale_date datetime,
    client_number int(11) NOT NULL,
    total_net_value_of_sale decimal(12,2) DEFAULT NULL,
    pst decimal(12,2),
    gst decimal(12,2) ,
    hst decimal(12,2),
    total_gross_value_of_sale decimal(12,2) DEFAULT NULL,
    PRIMARY KEY (sale_number),
    KEY client_number_index (client_number)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

--Author Rita
DROP TABLE IF EXISTS invoice_details;
CREATE TABLE IF NOT EXISTS invoicedetails (
    invoice_detail_id int(11) NOT NULL AUTO_INCREMENT,
    sale_number int(11) NOT NULL,
    isbn varchar(25) NOT NULL,
    book_price decimal(12,2),
    quantity INT(2) NOT NULL,
    PRIMARY KEY (invoice_detail_id)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

--Author Chris
DROP TABLE IF EXISTS survey;
CREATE TABLE survey(
    survey_id INT(6) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    question VARCHAR(200) NOT NULL UNIQUE,
    answer_one VARCHAR(50) NOT NULL,
    answer_two VARCHAR(50) NOT NULL,
    answer_three VARCHAR(50) NOT NULL,
    answer_default VARCHAR(50) NOT NULL
)ENGINE=InnoDB;
