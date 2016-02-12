USE g3w16;

--TABLE STRUCTURE
-- Table structure for table `ad`
DROP TABLE IF EXISTS `ad`;
CREATE TABLE IF NOT EXISTS `ad` (
  `ad_id` int(6) NOT NULL,
  `ad_filename` varchar(100) NOT NULL
) ENGINE=InnoDB;

-- Table structure for table `approval`
DROP TABLE IF EXISTS `approval`;
CREATE TABLE IF NOT EXISTS `approval` (
  `approval_id` int(11) NOT NULL,
  `approval_status` varchar(10) DEFAULT NULL
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `news_feed`;
CREATE TABLE IF NOT EXISTS `news_feed` (
  `news_feed_id` int(6) NOT NULL,
  `news_feed_link` varchar(200) NOT NULL
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `province`;
CREATE TABLE IF NOT EXISTS `province` (
  `province_id` int(11) NOT NULL,
  `province` varchar(30) NOT NULL,
  `pst` decimal(12,2) DEFAULT NULL,
  `gst` decimal(12,2) DEFAULT NULL,
  `hst` decimal(12,2) DEFAULT NULL
) ENGINE=InnoDB;

-- Table structure for table `author`
DROP TABLE IF EXISTS `author`;
CREATE TABLE IF NOT EXISTS `author` (
  `author_id` int(6) NOT NULL,
  `author_name` varchar(30) NOT NULL
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `book_author`;
CREATE TABLE IF NOT EXISTS `book_author` (
  `book_id` int(6) NOT NULL,
  `author_id` int(6) NOT NULL
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `format`;
CREATE TABLE IF NOT EXISTS `format` (
  `format_id` int(6) NOT NULL,
  `extension` varchar(7) NOT NULL
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `book_format`;
CREATE TABLE IF NOT EXISTS `book_format` (
  `book_id` int(6) NOT NULL,
  `format_id` int(6) NOT NULL
) ENGINE=InnoDB;    

DROP TABLE IF EXISTS `genre`;
CREATE TABLE IF NOT EXISTS `genre` (
  `genre_id` int(3) NOT NULL,
  `genre_name` varchar(30) NOT NULL
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `book_genre`;
CREATE TABLE IF NOT EXISTS `book_genre` (
  `book_id` int(6) NOT NULL,
  `genre_id` int(3) NOT NULL
) ENGINE=InnoDB;

-- Table structure for table `book`
DROP TABLE IF EXISTS `book`;
CREATE TABLE IF NOT EXISTS `book` (
  `book_id` int(6) NOT NULL,
  `isbn` varchar(14) NOT NULL,
  `title` varchar(200) NOT NULL,
  `publisher` varchar(70) NOT NULL,
  `publish_date` timestamp NOT NULL,
  `page_number` int(5) NOT NULL,
  `wholesale_price` decimal(15,2) NOT NULL,
  `list_price` decimal(15,2) DEFAULT NULL,
  `sale_price` decimal(15,2) DEFAULT NULL,
  `date_entered` timestamp DEFAULT CURRENT_TIMESTAMP,
  `available` bit(1) NOT NULL,
  `overall_rating` decimal(2,1) NOT NULL DEFAULT '0.0',
  `synopsis` mediumtext
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `title`;
CREATE TABLE IF NOT EXISTS `title` (
  `title_id` int(11) NOT NULL,
  `title` varchar(10) NOT NULL
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `registered_user`;
CREATE TABLE IF NOT EXISTS `registered_user` (
  `user_id` int(11) NOT NULL,
  `email_address` varchar(50) NOT NULL,
  `password` varchar(25) NOT NULL,
  `title_id` int(11) DEFAULT NULL,
  `first_name` varchar(25) DEFAULT NULL,
  `last_name` varchar(25) DEFAULT NULL,
  `company_name` varchar(50) DEFAULT NULL,
  `address_one` varchar(50) DEFAULT NULL,
  `address_two` varchar(50) DEFAULT NULL,
  `city` varchar(25) DEFAULT NULL,
  `province_id` int(11) DEFAULT NULL,
  `country` varchar(25) DEFAULT NULL,
  `postal_code` varchar(10) DEFAULT NULL,
  `home_phone` varchar(25) DEFAULT NULL,
  `cell_phone` varchar(25) DEFAULT NULL,
  `manager` tinyint(1) DEFAULT '0',
  `active` tinyint(1) DEFAULT '1'
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `invoice_detail`;
CREATE TABLE IF NOT EXISTS `invoice_detail` (
  `invoice_detail_id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `isbn` varchar(25) NOT NULL,
  `pst` decimal(12,2) DEFAULT NULL,
  `gst` decimal(12,2) DEFAULT NULL,
  `hst` decimal(12,2) DEFAULT NULL,
  `book_price` decimal(12,2) DEFAULT NULL,
  `quantity` int(2) NOT NULL
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `invoice`;
CREATE TABLE IF NOT EXISTS `invoice` (
  `invoice_id` int(11) NOT NULL,
  `sale_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `user_number` int(11) NOT NULL,
  `total_net_value_of_sale` decimal(12,2) DEFAULT NULL,
  `total_gross_value_of_sale` decimal(12,2) DEFAULT NULL
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `review`;
CREATE TABLE IF NOT EXISTS `review` (
  `review_id` int(11) NOT NULL,
  `isbn` varchar(14) DEFAULT NULL,
  `date_submitted` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` int(11) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `approval_id` int(11) DEFAULT NULL,
  `review_title` varchar(200) DEFAULT '',
  `review_text` varchar(1750) DEFAULT ''
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `survey`;
CREATE TABLE IF NOT EXISTS `survey` (
  `survey_id` int(6) NOT NULL,
  `question` varchar(200) NOT NULL,
  `answer_one` varchar(50) NOT NULL,
  `answer_two` varchar(50) NOT NULL,
  `answer_three` varchar(50) NOT NULL,
  `answer_default` varchar(50) NOT NULL
) ENGINE=InnoDB;


-- INDEXES
ALTER TABLE `ad`
  ADD PRIMARY KEY (`ad_id`),
  ADD UNIQUE KEY `ad_filename` (`ad_filename`);
  
ALTER TABLE `approval`
  ADD PRIMARY KEY (`approval_id`);
  
ALTER TABLE `author`
  ADD PRIMARY KEY (`author_id`);
  
ALTER TABLE `book`
  ADD PRIMARY KEY (`book_id`),
  ADD UNIQUE KEY `isbn` (`isbn`),
  ADD INDEX (title),
  ADD INDEX (publisher),
  ADD INDEX (isbn);
  
ALTER TABLE `book_author`
  ADD PRIMARY KEY (`book_id`,`author_id`),
  ADD KEY `book_author_author_id_fk` (`author_id`);
  
ALTER TABLE `book_format`
  ADD PRIMARY KEY (`book_id`,`format_id`),
  ADD KEY `format_id_fk` (`format_id`);
  
ALTER TABLE `book_genre`
  ADD PRIMARY KEY (`book_id`,`genre_id`),
  ADD KEY `book_genre_subgenre_id_fk` (`genre_id`);
  
ALTER TABLE `format`
  ADD PRIMARY KEY (`format_id`),
  ADD UNIQUE KEY `extension` (`extension`);
  
ALTER TABLE `genre`
  ADD PRIMARY KEY (`genre_id`);
  
ALTER TABLE `invoice`
  ADD PRIMARY KEY (`invoice_id`),
  ADD KEY `user_number_index` (`user_number`);
  
ALTER TABLE `invoice_detail`
  ADD PRIMARY KEY (`invoice_detail_id`),
  ADD KEY `invoice_fk` (`invoice_id`);
  
ALTER TABLE `news_feed`
  ADD PRIMARY KEY (`news_feed_id`),
  ADD UNIQUE KEY `news_feed_link` (`news_feed_link`);
  
ALTER TABLE `province`
  ADD PRIMARY KEY (`province_id`);
  
ALTER TABLE `registered_user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `email_address` (`email_address`),
  ADD KEY `title_id` (`title_id`),
  ADD KEY `province_id` (`province_id`);
  
ALTER TABLE `review`
  ADD PRIMARY KEY (`review_id`),
  ADD KEY `approval_id_FK` (`approval_id`),
  ADD KEY `isbn_FK` (`isbn`),
  ADD KEY `client_id_FK` (`user_id`);
  
ALTER TABLE `survey`
  ADD PRIMARY KEY (`survey_id`),
  ADD UNIQUE KEY `question` (`question`);
  
ALTER TABLE `title`
  ADD PRIMARY KEY (`title_id`);
  
--AUTO INCREMENTS
ALTER TABLE `ad`
  MODIFY `ad_id` int(6) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1;
  
ALTER TABLE `approval`
  MODIFY `approval_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1;
  
ALTER TABLE `author`
  MODIFY `author_id` int(6) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1;
  
ALTER TABLE `book`
  MODIFY `book_id` int(6) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1;
  
ALTER TABLE `format`
  MODIFY `format_id` int(6) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1;
  
ALTER TABLE `genre`
  MODIFY `genre_id` int(3) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1;
  
ALTER TABLE `invoice`
  MODIFY `invoice_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1;
  
ALTER TABLE `invoice_detail`
  MODIFY `invoice_detail_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1;
  
ALTER TABLE `news_feed`
  MODIFY `news_feed_id` int(6) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1;
  
ALTER TABLE `province`
  MODIFY `province_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1;
  
ALTER TABLE `registered_user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1;
  
ALTER TABLE `review`
  MODIFY `review_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1;
  
ALTER TABLE `survey`
  MODIFY `survey_id` int(6) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1;
  
ALTER TABLE `title`
  MODIFY `title_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=1;
  
--CONSTRAINTS
ALTER TABLE `book_author`
  ADD CONSTRAINT `book_author_book_id_fk` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `book_author_author_id_fk` FOREIGN KEY (`author_id`) REFERENCES `author` (`author_id`) ON DELETE CASCADE;

ALTER TABLE `book_format`
  ADD CONSTRAINT `book_id_fk` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `format_id_fk` FOREIGN KEY (`format_id`) REFERENCES `format` (`format_id`) ON DELETE CASCADE;

ALTER TABLE `book_genre`
  ADD CONSTRAINT `book_genre_book_id_fk` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `book_genre_subgenre_id_fk` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`) ON DELETE CASCADE;

ALTER TABLE `invoice_detail`
  ADD CONSTRAINT `invoice_fk` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`invoice_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `registered_user`
  ADD CONSTRAINT `registered_user_ibfk_1` FOREIGN KEY (`title_id`) REFERENCES `title` (`title_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `registered_user_ibfk_2` FOREIGN KEY (`province_id`) REFERENCES `province` (`province_id`) ON DELETE CASCADE;

ALTER TABLE `review`
  ADD CONSTRAINT `approval_id_FK` FOREIGN KEY (`approval_id`) REFERENCES `approval` (`approval_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `isbn_FK` FOREIGN KEY (`isbn`) REFERENCES `book` (`isbn`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `client_id_FK` FOREIGN KEY (`user_id`) REFERENCES `registered_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;
  