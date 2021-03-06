USE g3w16;

-- combined all scrips
DROP TABLE IF EXISTS `survey_answer`;
DROP TABLE IF EXISTS `survey`;
DROP TABLE IF EXISTS `review`;
DROP TABLE IF EXISTS `registered_user`;
DROP TABLE IF EXISTS `title`;
DROP TABLE IF EXISTS `invoice_detail`;
DROP TABLE IF EXISTS `invoice`;
DROP TABLE IF EXISTS `book_genre`;
DROP TABLE IF EXISTS `genre`;
DROP TABLE IF EXISTS `book_format`;
DROP TABLE IF EXISTS `format`;
DROP TABLE IF EXISTS `book_author`;
DROP TABLE IF EXISTS `author`;
DROP TABLE IF EXISTS `book`;
DROP TABLE IF EXISTS `province`;
DROP TABLE IF EXISTS `news_feed`;
DROP TABLE IF EXISTS `approval`;
DROP TABLE IF EXISTS `ad`;

-- TABLE STRUCTURE
--  Table structure for table `ad`

CREATE TABLE IF NOT EXISTS `ad` (
  `ad_id` int(6) NOT NULL,
  `ad_filename` varchar(100) NOT NULL,
  `active` tinyint(1) DEFAULT '0',
  `ad_type` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB;

--  Table structure for table `approval`

CREATE TABLE IF NOT EXISTS `approval` (
  `approval_id` int(11) NOT NULL,
  `approval_status` varchar(10) DEFAULT NULL
) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS `news_feed` (
  `news_feed_id` int(6) NOT NULL,
  `news_feed_link` varchar(200) NOT NULL,
  `active` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS `province` (
  `province_id` int(11) NOT NULL,
  `province` varchar(30) NOT NULL,
  `pst` decimal(12,2) DEFAULT NULL,
  `gst` decimal(12,2) DEFAULT NULL,
  `hst` decimal(12,2) DEFAULT NULL
) ENGINE=InnoDB;

--  Table structure for table `author`

CREATE TABLE IF NOT EXISTS `author` (
  `author_id` int(6) NOT NULL,
  `author_name` varchar(30) NOT NULL
) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS `book_author` (
  `book_id` int(6) NOT NULL,
  `author_id` int(6) NOT NULL
) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS `format` (
  `format_id` int(6) NOT NULL,
  `extension` varchar(7) NOT NULL
) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS `book_format` (
  `book_id` int(6) NOT NULL,
  `format_id` int(6) NOT NULL
) ENGINE=InnoDB;    


CREATE TABLE IF NOT EXISTS `genre` (
  `genre_id` int(3) NOT NULL,
  `genre_name` varchar(30) NOT NULL
) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS `book_genre` (
  `book_id` int(6) NOT NULL,
  `genre_id` int(3) NOT NULL
) ENGINE=InnoDB;

--  Table structure for table `book`

CREATE TABLE IF NOT EXISTS `book` (
  `book_id` int(6) NOT NULL,
  `isbn` varchar(14) NOT NULL,
  `title` varchar(200) NOT NULL,
  `publisher` varchar(70) NOT NULL,
  `publish_date` timestamp NULL,
  `page_number` int(5) NOT NULL,
  `wholesale_price` decimal(15,2) NOT NULL,
  `list_price` decimal(15,2) DEFAULT NULL,
  `sale_price` decimal(15,2) DEFAULT NULL,
  `date_entered` timestamp DEFAULT CURRENT_TIMESTAMP,
  `available` bit(1) NOT NULL,
  `overall_rating` decimal(2,1) NOT NULL DEFAULT '0.0',
  `synopsis` mediumtext
) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS `title` (
  `title_id` int(11) NOT NULL,
  `title` varchar(10) NOT NULL
) ENGINE=InnoDB;


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


CREATE TABLE IF NOT EXISTS `invoice_detail` (
  `invoice_detail_id` int(11) NOT NULL,
  `invoice_id` int(11) NOT NULL,
  `book_id` int(6) NOT NULL,
  `pst` decimal(12,2) DEFAULT NULL,
  `gst` decimal(12,2) DEFAULT NULL,
  `hst` decimal(12,2) DEFAULT NULL,
  `book_price` decimal(12,2) DEFAULT NULL,
  `quantity` int(2) NOT NULL
) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS `invoice` (
  `invoice_id` int(11) NOT NULL,
  `sale_date` timestamp DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL,
  `total_net_value_of_sale` decimal(12,2) DEFAULT NULL,
  `total_gross_value_of_sale` decimal(12,2) DEFAULT NULL
) ENGINE=InnoDB;

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

CREATE TABLE IF NOT EXISTS `survey` (
  `survey_id` int(6) NOT NULL,
  `question` varchar(200) NOT NULL,
  `answer_one` varchar(50) NOT NULL,
  `answer_two` varchar(50) NOT NULL,
  `answer_three` varchar(50) NOT NULL,
  `answer_default` varchar(50) NOT NULL,
  `active` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `survey_answer` (
  `survey_id` INT(6) NOT NULL,
  `session_id` VARCHAR(200) DEFAULT NULL,
  `choice` INT(3) DEFAULT 4
) ENGINE=InnoDB;


--  INDEXES
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
  ADD KEY `user_id_index` (`user_id`);
  
ALTER TABLE `invoice_detail`
  ADD PRIMARY KEY (`invoice_detail_id`),
  ADD KEY `invoice_fk` (`invoice_id`),
  ADD KEY `book_fk` (`book_id`);
  
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

ALTER TABLE `survey_answer`
  ADD PRIMARY KEY (`survey_id`,`session_id`),
  ADD UNIQUE KEY `session_id_UQ` (`session_id`),
  ADD KEY `survey_id_FK` (`survey_id`);
  
-- AUTO INCREMENTS
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
  
-- CONSTRAINTS
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
  ADD CONSTRAINT `invoice_fk` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`invoice_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `book_fk` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `registered_user`
  ADD CONSTRAINT `registered_user_ibfk_1` FOREIGN KEY (`title_id`) REFERENCES `title` (`title_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `registered_user_ibfk_2` FOREIGN KEY (`province_id`) REFERENCES `province` (`province_id`) ON DELETE CASCADE;

ALTER TABLE `review`
  ADD CONSTRAINT `approval_id_FK` FOREIGN KEY (`approval_id`) REFERENCES `approval` (`approval_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `isbn_FK` FOREIGN KEY (`isbn`) REFERENCES `book` (`isbn`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `client_id_FK` FOREIGN KEY (`user_id`) REFERENCES `registered_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `survey_answer`
  ADD CONSTRAINT `survey_id_FK` FOREIGN KEY (`survey_id`) REFERENCES `survey` (`survey_id`);

INSERT INTO `ad` (`ad_id`, `ad_filename`, `active`, `ad_type`) VALUES
(1, 'mcdonalds.com', 1, 0),
(2, 'facebook.com', 0, 1),
(3, 'amazon.com', 0, 1),
(4, 'airfrance.ca', 0, 1),
(5, 'leagueoflegends.com', 0, 1),
(6, 'ligabbva.com', 1, 1),
(7, 'michelin.ca', 0, 0),
(8, 'premierleague.com', 0, 0),
(9, 'starbucks.com', 0, 0),
(10, 'winners.ca', 0, 0);


INSERT INTO `approval` (`approval_id`, `approval_status`) VALUES
(1, 'Approved'),
(2, 'Pending'),
(3, 'Denied');


INSERT INTO `author` (`author_id`, `author_name`) VALUES
(1, 'Muhammad H. Rashid'),
(2, 'Insight Editions'),
(3, 'Mike Meyers'),
(4, 'Jean Andrews'),
(5, 'Peter Weverka'),
(6, 'Sam Newman'),
(7, 'Mark G. Sobell'),
(8, 'Gayle Laakmann McDowell'),
(9, 'Jon Duckett'),
(10, 'Tony Gaddis'),
(11, 'Joseph Albahari'),
(12, 'Paul Deitel'),
(13, 'Harvey M Deitel'),
(14, 'Abbey Deitel'),
(15, 'William Stanek'),
(16, 'Al Sweigart'),
(17, 'David Pogue'),
(18, 'Andreas M. Antonopoulos'),
(19, 'Andrew Faulkner'),
(20, 'Conrad Chavez'),
(21, 'Rob Eisenberg'),
(22, 'Christopher Bennage'),
(23, 'Robert Sedgewick'),
(24, 'Kevin Wayne'),
(25, 'Robert C. Martin'),
(26, 'Daniel Chazin'),
(27, 'Gloria Fowler'),
(28, 'Min Heo'),
(29, 'Brandon Stanton'),
(30, 'Isak Dinesen'),
(31, 'Marty Noble'),
(32, 'Kodansha International'),
(33, 'Abbi Jacobson'),
(34, 'Michael Hutchison'),
(35, 'Dan Kainen'),
(36, 'Carol Kaufmann '),
(37, 'Deborah Howard'),
(38, 'Rick Steves'),
(39, 'Gloria Fowler'),
(40, 'Matthew Harper'),
(41, 'Rick Steves'),
(42, 'Lonely Planet'),
(43, 'Kate T. Williamson'),
(44, 'Erin Moore'),
(45, 'Bill Bryson'),
(46, 'Jason Brooks'),
(47, 'Mark Millar'),
(48, 'Steve McNiven'),
(49, 'Robert Venditti'),
(50, 'Van Jensen'),
(51, 'Brett Booth'),
(52, 'Scott Lobdell'),
(53, 'R.B. Silva'),
(54, 'Michael Green'),
(55, 'Mike Johnson'),
(56, 'Mahmud Asrar'),
(57, 'Brian Posehn'),
(58, 'Gerry Duggan'),
(59, 'Tony Moore'),
(60, 'Robert Kirkman'),
(61, 'Dave Steward'),
(62, 'Charlie Adlard'),
(63, 'Adam Glass'),
(64, 'Federico Dallocchio'),
(65, 'Fernando Dagnino'),
(66, 'Jim Starlin'),
(67, 'George Perez'),
(68, 'Ron Lim'),
(69, 'Brian Michael Bendis'),
(70, 'Chris Bachalo'),
(71, 'ONE'),
(72, 'Yusuke Murata'),
(73, 'Dan Slott'),
(74, 'Humberto Ramos'),
(75, 'Ramon Perez'),
(76, 'Alan Moore'),
(77, 'Brian Bolland'),
(78, 'Scott Snyder'),
(79, 'Greg Capullo'),
(80, 'Rafael Albuquerque'),
(81, 'Mark Waid'),
(82, 'Chris Samnee'),
(83, 'Javier Rodriguez'),
(84, 'Rick Remender'),
(85, 'Stuart Immonen'),
(86, 'Mitra Rahbar'),
(87, 'Doreen Virtue'),
(88, 'Robert Reeves'),
(89, 'Paul Selig'),
(90, 'Tara Brach'),
(91, 'Seyyed Hossein Nasr'),
(92, 'Caner K. Dagli'),
(93, 'Maria Massi Dakake'),
(94, 'Joseph E.B. Lumbard'),
(95, 'Mohammed Rustom'),
(96, 'Mitch Albom'),
(97, 'Pope Francis'),
(98, 'Alberta Hutchinson'),
(99, 'Don Miguel Ruiz'),
(100, 'Michael A. Singer'),
(101, 'Elizabeth Gilbert'),
(102, 'Susan Pesznecker'),
(103, 'Melanie Marquis'),
(104, 'Linda Raedisch'),
(105, 'Blake Octavian Blair'),
(106, 'Diana Rajchel'),
(107, 'James Kambos'),
(108, 'Elizabeth Barrette'),
(109, 'Ellen Dugan'),
(110, 'Barbara Ardinger'),
(111, 'Dallas Jennifer Cobb'),
(112, 'Autumn Damiana'),
(113, 'Tess Whitehurst'),
(114, 'Llewellyn'),
(115, 'Eckhart Tolle'),
(116, 'Viktor E. Frankl'),
(117, 'Melody Beattie'),
(118, 'Emma Mildon'),
(119, 'Pearl Barrett'),
(120, 'Serene Allison'),
(121, 'Rhonda Byrne'),
(122, 'George S. Clason'),
(123, 'Ina May Gaskin'),
(124, 'Gretchen Rubin'),
(125, 'Dr. Angela Porter'),
(126, 'Paula Hawkins'),
(127, 'Andy Weir'),
(128, 'David Lagercrantz'),
(129, 'Karin Slaughter'),
(130, 'James Patterson'),
(131, 'Lisa Jackson'),
(132, 'Sandra Brown'),
(133, 'A. J. Banner'),
(134, 'Oliver Pötzsch'),
(135, 'Kendra Elliot'),
(136, 'John Grisham'),
(137, 'Douglas E. Richards'),
(138, 'Laura Lippman'),
(139, 'Michael Connelly'),
(140, 'J.A. Jance'),
(141, 'David Baldacci'),
(142, 'Sophie Hannah'),
(143, 'Andrew Peterson'),
(144, 'Lee Child'),
(145, 'Lizzie Mary Cullen');

INSERT INTO `book` (`book_id`, `isbn`, `title`, `publisher`, `publish_date`, `page_number`, `wholesale_price`, `list_price`, `sale_price`, `date_entered`, `available`, `overall_rating`, `synopsis`) VALUES
(1, '978-1305635166', 'Microelectronic Circuits: Analysis and Design', 'Thomson-Engineering', '2016-01-11 00:00:00', 1360, '150.00', '317.95', '309.08', '2016-01-30 00:00:00', b'1', '0.0', '''Take a ''''breadth-first'''' approach to learning electronics with a strong emphasis on design and simulation in MICROELECTRONIC CIRCUITS: ANALYSIS AND DESIGN, 3E. This book introduces the general characteristics of circuits (ICs) to prepare you to effectively use circuit design and analysis techniques. The author then offers a more detailed study of devices and circuits and how they operate within ICs. Important circuits are analyzed in worked-out examples to introduce basic techniques and emphasize the effects of parameter variations. More than half of the problems and examples concentrate on design and use software tools extensively. You learn to apply theory to real-world design problems as you master computer simulations for testing and verifying your designs'''),
(2, '978-1608877119', 'The Art of XCOM 2', 'Insight Editions', '2016-01-22 00:00:00', 192, '25.00', '56.95', '50.68', '2016-01-30 00:00:00', b'1', '0.0', 'From the developer behind the Civilization series, XCOM is an award-winning, deeply engrossing strategy game. With the Earth under attack by a super-advanced alien race, players command an elite paramilitary organization called XCOM to repel the extraterrestrial offensive and defend humanity. In The Art of XCOM 2, readers get a behind-the-scenes look at the incredible concept art created for the series and hear from key developers and artists about the challenges, secrets, and rewards of creating this landmark series.'),
(3, '978-1259589515', 'CompTIA A+ Certification All-in-One Exam Guide, Ninth Edition', 'McGraw-Hill Education', '2016-01-04 00:00:00', 1632, '24.00', '74.95', '49.76', '2016-01-30 00:00:00', b'1', '0.0', '''Written by the leading authority on CompTIA A+ certification and training, this self-study book and CD has been thoroughly updated to cover 100% of the exam objectives on the 2015 CompTIA A+ exams. New topics include managing and maintaining cellular devices, including tablets,  configuring operating systems, including Windows 8, Android, and iOS,  and enhanced, mobile-centered security and troubleshooting procedures.'''),
(4, '978-1305266438', 'A+ Guide for IT Technical Support', 'Course Technology', '2016-01-08 00:00:00', 1344, '80.00', '166.95', '166.95', '2016-01-30 00:00:00', b'1', '0.0', 'This step-by-step, highly visual text provides a comprehensive introduction to managing and maintaining computer hardware and software. Written by best-selling author and educator Jean Andrews, A+ Guide to IT Technical Support, 9th Edition closely integrates the CompTIA+ Exam objectives to prepare you for the 220-901 and 220-902 certification exams. The new Ninth Edition also features extensive updates to reflect current technology, techniques, and industry standards in the dynamic, fast-paced field of PC repair and information technology. Each chapter covers both core concepts and advanced topics, organizing material to facilitate practical application and encourage you to learn by doing. The new edition features more coverage of updated hardware, security, virtualization, new coverage of cloud computing, Linux and Mac OS, and increased emphasis on mobile devices. Supported by a wide range of supplemental resources to enhance learning with Lab Manuals, CourseNotes online labs and the optional MindTap that includes online labs, certification test prep and interactive exercises and activities, this proven text offers students an ideal way to prepare for success as a professional IT support technician and administrator.'),
(5, '978-1119038597', 'Windows 10 For Seniors For Dummies', 'For Dummies', '2015-08-17 00:00:00', 336, '15.00', '29.99', '17.99', '2016-01-30 00:00:00', b'1', '0.0', 'If you''re a first-time, over-50 Windows 10 user looking for an authoritative, accessible guide to the basics of this new operating system, look no further thanWindows 10 For Seniors For Dummies. Written by an all-around tech guru and the coauthor ofWindows 8.1 For Seniors For Dummies, it cuts through confusing jargon and covers just what you need to know: navigating the interface with a mouse or a touchscreen, customizing the desktop, managing printers and other external devices, setting up and connecting to simple networks, and storing files in the Cloud. Plus, you''ll find helpful instructions on sending and receiving email, uploading, editing, and downloading pictures, listening to music, playing games, and so much more.'),
(6, '978-1491950357', 'Building Microservices', 'O''Reilly Media', '2015-02-20 00:00:00', 280, '23.00', '72.79', '46.35', '2016-01-30 00:00:00', b'1', '0.0', 'Distributed systems have become more fine-grained in the past 10 years, shifting from code-heavy monolithic applications to smaller, self-contained microservices. But developing these systems brings its own set of headaches. With lots of examples and practical advice, this book takes a holistic view of the topics that system architects and administrators must consider when building, managing, and evolving microservice architectures.'),
(7, '978-0133477436', 'A Practical Guide to Fedora and Red Hat Enterprise Linux (7th Edition)', 'Prentice Hall', '2013-12-31 00:00:00', 1300, '34.00', '68.99', '39.49', '2016-01-30 00:00:00', b'1', '0.0', 'In this comprehensive guide, one of the world?s leading Linux experts brings together all the knowledge and real-world insights you need to master and succeed with today?s versions of Fedora or Red Hat Enterprise Linux. Best-selling author Mark Sobell explains Linux clearly and effectively, focusing on skills you?ll actually need as a user, programmer, or administrator.'),
(8, '978-0984782857', 'Cracking the Coding Interview: 189 Programming Questions and Solutions', 'CareerCup', '2015-07-01 00:00:00', 706, '20.00', '49.95', '39.95', '2016-01-30 00:00:00', b'1', '0.0', 'The #1 best-selling programming interview prep book'),
(9, '978-1118531648', 'JavaScript and JQuery: Interactive Front-End Web Development', 'Wiley', '2014-06-30 00:00:00', 640, '17.00', '47.99', '35.99', '2016-01-30 00:00:00', b'1', '0.0', 'This full-color book adopts a visual approach to teaching JavaScript & jQuery, showing you how to make web pages more interactive and interfaces more intuitive through the use of inspiring code examples, infographics, and photography. The content assumes no previous programming experience, other than knowing how to create a basic web page in HTML & CSS. You''ll learn how to achieve techniques seen on many popular websites (such as adding animation, tabbed panels, content sliders, form validation, interactive galleries, and sorting data)..'),
(10, '978-0132855839', 'Starting Out with Java: From Control Structures through Objects (5th Edition)', 'Pearson', '2012-02-16 00:00:00', 1152, '90.00', '185.75', '180.30', '2016-01-30 00:00:00', b'1', '0.0', 'InStarting Out with Java: From Control Structures through Objects, Gaddis covers procedural programming?control structures and methods?before introducing object-oriented programming. As with all Gaddis texts, clear and easy-to-read code listings, concise and practical real-world examples, and an abundance of exercises appear in every chapter.'),
(11, '978-1449320102', 'C# 5.0 in a Nutshell: The Definitive Reference', 'O''Reilly Media', '2012-06-29 00:00:00', 1064, '40.00', '80.07', '45.21', '2016-01-30 00:00:00', b'1', '0.0', 'When you have a question about C# 5.0 or the .NET CLR, this bestselling guide has precisely the answers you need. Uniquely organized around concepts and use cases, this updated fifth edition features a reorganized section on concurrency, threading, and parallel programming?including in-depth coverage of C# 5.0?s new asynchronous functions.'),
(12, '978-0133764031', 'Android How to Program (2nd Edition)', 'Prentice Hall', '2014-01-10 00:00:00', 736, '74.00', '186.00', '111.60', '2016-01-30 00:00:00', b'1', '0.0', 'The Deitels'' App-driven Approach is simply the best way to master Android programming! The Deitels teach Android programming through seven complete, working Android Apps in the print book and more online. Each chapter presents new concepts through a single App. The authors first provide an introduction to the app, an apptest-driveshowing one or moresample executions,and atechnologies overview. Next, the authors proceed with a detailedcode walkthroughof the app?s source code in which they discuss the programming concepts and demonstrate the functionality of the Android APIs used in the app. The book also has an extensive introduction to programming using the Java language, making this book appropriate for Java courses that want to add an App-programming flavor.'),
(13, '978-0735666313', 'Windows Server 2012 Inside Out', 'Microsoft Press', '2013-01-15 00:00:00', 1584, '31.50', '62.99', '56.06', '2016-01-30 00:00:00', b'1', '0.0', 'Dive in?and discover how to really put Windows Server 2012 to work! This supremely organized reference packs the details you need to plan and manage a Windows Server 2012 implementation?including hundreds of timesaving solutions, troubleshooting tips, and workarounds. Learn how the experts tackle Windows Server 2012?and challenge yourself to new levels of mastery. Topics include'),
(14, '978-1593275990', 'Automate the Boring Stuff with Python: Practical Programming for Total Beginners', 'No Starch Press', '2015-05-01 00:00:00', 504, '15.56', '42.71', '27.15', '2016-01-30 00:00:00', b'1', '0.0', 'InAutomate the Boring Stuff with Python, you''ll learn how to use Python to write programs that do in minutes what would take you hours to do by hand?no prior programming experience required. Once you''ve mastered the basics of programming,'),
(15, '978-1491917954', 'OS X El Capitan: The Missing Manual', 'O''Reilly Media', '2015-11-30 00:00:00', 846, '15.48', '49.90', '34.42', '2016-01-30 00:00:00', b'1', '0.0', 'With El Capitan, Apple brings never-before-seen features to OS X?like a split-screen desktop, improved window controls, and amazing graphics. The new edition of David Pogue''s #1 bestselling Mac book shows you how to use key new features such as swiping gestures, Notes, a new Spotlight search system, the Safari pinning feature, and Split View.'),
(16, '978-1449374044', 'Mastering Bitcoin: Unlocking Digital Cryptocurrencies', 'O''Reilly Media', '2014-12-20 00:00:00', 298, '15.00', '49.90', '16.19', '2016-01-30 00:00:00', b'1', '0.0', 'Want to join the technological revolution that?s taking the world of finance by storm? Mastering Bitcoin is your guide through the seemingly complex world of bitcoin, providing the requisite knowledge to help you participate in the internet of money. Whether you?re building the next killer app, investing in a startup, or simply curious about the technology, this practical book is essential reading.'),
(17, '978-0134308135', 'Adobe Photoshop CC Classroom in a Book (2015 release)', 'Adobe Press', '2015-08-26 00:00:00', 384, '47.39', '74.99', '61.91', '2016-01-30 00:00:00', b'1', '0.0', 'Creative professionals seeking the fastest, easiest, most comprehensive way to learn Adobe Photoshop chooseAdobe Photoshop CC Classroom in a Book (2015 release)from Adobe Press. The 14 project-based lessons in this book show users step-by-step the key techniques for working in Photoshop and how to manipulate images, edit motion-based content, and create image composites.'),
(18, '978-0672329852', 'Sams Teach Yourself WPF in 24 Hours', 'Sams Publishing', '2008-06-19 00:00:00', 480, '19.79', '46.99', '46.99', '2016-01-30 00:00:00', b'1', '0.0', 'In just 24 sessions of one hour or less, you will be able to begin effectively using WPF to solve real-world problems, developing rich user interfaces in less time than you thought possible.'),
(19, '978-0321573513', 'Algorithms (4th Edition)', 'Addison-Wesley Professional', '2011-03-09 00:00:00', 992, '54.39', '111.99', '82.29', '2016-01-30 00:00:00', b'1', '0.0', 'This fourth edition of Robert Sedgewick and Kevin Wayne?sAlgorithmsis the leading textbook on algorithms today and is widely used in colleges and universities worldwide. This book surveys the most important computer algorithms currently in use and provides a full treatment of data structures and algorithms for sorting, searching, graph processing, and string processing --  including fifty algorithms every programmer should know. In this edition, new Java implementations are written in an accessible modular programming style, where all of the code is exposed to the reader and ready to use.'),
(20, '978-0132350884', 'Clean Code: A Handbook of Agile Software Craftsmanship', 'Prentice Hall', '2008-08-01 00:00:00', 464, '31.11', '51.99', '39.32', '2016-01-30 00:00:00', b'1', '0.0', 'Even bad code can function. But if code isn?t clean, it can bring a development organization to its knees. Every year, countless hours and significant resources are lost because of poorly written code. But it doesn?t have to be that way.'),
(21, '978-1889386959', 'Appalachian Trail Data Book 2016', 'Appalachian Trail Conference', '2016-01-04 00:00:00', 96, '4.00', '9.91', '9.73', '2016-01-30 00:00:00', b'1', '0.0', 'No synopsis.'),
(22, '978-1623260453', 'London Coloring Book', 'AMMO Books', '2016-03-15 00:00:00', 64, '7.00', '14.49', '14.49', '2016-01-30 00:00:00', b'1', '0.0', 'Joining the enchanting sights of Paris and the high-energy of excitement of New York, AMMO Books presents the newest locale in our popular City Series: stunning and sophisticated London. Discovering historical highlights and opportunities for adventure, readers will embark on a journey through the pages of COME WITH ME TO LONDON. With newfound knowledge of foggy London town, children can play our memory game featuring illustrations of Big Ben and the London Eye and add vibrant color to black-and-white drawings of double-decker buses, the Tower Bridge, and more in a brand new coloring book. Each image within these three new titles features creative interpretation and intricate details by Min Heo, the talented illustrator of AMMO?s Paris and New York series as well. With a multitude of landmarks to learn about and iconic representations of the city to enjoy, London comes to life and inspires imagination for future travelers of any age.'),
(23, '978-1250038821', 'Humans of New York', 'St. Martin''s Press', '2013-10-15 00:00:00', 432, '6.00', '34.50', '12.23', '2016-01-30 00:00:00', b'1', '0.0', '''Now an instant #1 New York Times bestseller, Humans of New York began in the summer of 2010, when photographer Brandon Stanton set out to create a photographic census of New York City. Armed with his camera, he began crisscrossing the city, covering thousands of miles on foot, all in an attempt to capture New Yorkers and their stories. The result of these efforts was a vibrant blog he called ''''Humans of New York,'''' in which his photos were featured alongside quotes and anecdotes. The blog has steadily grown, now boasting millions of devoted followers. Humans of New York is the book inspired by the blog. With four hundred color photos, including exclusive portraits and all-new stories, Humans of New York is a stunning collection of images that showcases the outsized personalities of New York. Surprising and moving, printed in a beautiful full-color, hardbound edition, Humans of New York is a celebration of individuality and a tribute to the spirit of the city.'''),
(24, '978-0679600213', 'Out of Africa', 'Modern Library', '2013-09-20 00:00:00', 214, '11.00', '30.00', '22.49', '2016-01-30 00:00:00', b'1', '0.0', 'Out of Africa'),
(25, '978-0486805047', 'Creative Haven Hello Cuba! Coloring Book', 'Dover Publications', '2016-05-18 00:00:00', 128, '6.00', '13.50', '13.50', '2016-01-30 00:00:00', b'1', '0.0', '''Sixty-two stunning illustrations pay tribute to the natural beauty and entrancing culture of this fascinating tropical island. Scenes from town and country include images of vintage cars,  tropical wildlife,  cigar labels,  portraits of fruit vendors, farmers, musicians, and dancers,  an array of Cuban fashions,  and much more. Pages are perforated and printed on one side only for easy removal and display. Specially designed for experienced colorists, Hello Cuba! and other Creative Haven coloring books offer an escape to a world of inspiration and artistic fulfillment. Each title is also an effective and fun-filled way to relax and reduce stress.'''),
(26, '978-4770028471', 'Japan Book: Comprehensive Pocket Guide', 'Kodansha International', '2004-05-31 00:00:00', 160, '6.00', '12.23', '12.23', '2016-01-30 00:00:00', b'1', '0.0', 'Japan Book is a Kodansha International publication.'),
(27, '978-1452117331', 'Color this Book: New York City', 'Chronicle Books', '2013-04-23 00:00:00', 32, '6.00', '13.95', '12.29', '2016-01-30 00:00:00', b'1', '0.0', 'From co-star and co-creator of Comedy Central''s Broad City, Abbi Jacobson!Featuring over 30 illustrations by artist and comedienne Abbi Jacobson, this coloring book captures the charm and personality of bustling New York City-from cultural attractions and historic sites to quirky shops and everyday street scenes. A great keepsake for visitors and NY natives of all ages, Color this Book offers hours of coloring fun. Includes Artichoke Pizza, Brooklyn Bridge, Central Park, City Bakery, Greenwich Village, the High Line, the Statue of Liberty, and more!'),
(28, '978-0895561183', 'The Book of Floating: Exploring the Private Sea', 'Gateways Books & Tapes', '2005-01-16 00:00:00', 274, '16.00', '34.95', '33.19', '2016-01-30 00:00:00', b'1', '0.0', 'A thorough and absorbing summary of the healing and therapeutic uses of the floatation tank invented by the author.'),
(29, '978-0761163800', 'Safari: A Photicular Book', 'Workman Publishing Company', '2012-10-02 00:00:00', 32, '13.36', '38.95', '26.95', '2016-01-30 00:00:00', b'1', '0.0', 'Safari is a magical journey. Readers, as if on African safari, encounter eight wild animals that come alive using never-before-seen Photicular technology. Each full-color image is like a 3-D movie on the page, delivering a rich, fluid, immersive visual experience. The result is breathtaking. The cheetah bounds. The gazelle leaps. The African elephant snaps its ears. The gorilla munches the leaves off a branch. It?s mesmerizing, as visually immediate as a National Geographic or Animal Planet special.Accompanying the images is Safari, the guide: It begins with an evocative journal of a safari along the Mara River in Kenya and interweaves the history of safaris. Then for each animal there is a lively, informative essay and an at-a-glance list of important facts. It?s the romance of being on safari?and the thrill of seeing the animals in motion? in a book unlike any other'),
(30, '978-1405924092', 'The Magical City: A Colouring Book', 'Michael Joseph', '2015-11-06 00:00:00', 303, '11.00', '21.99', '19.57', '2016-01-30 00:00:00', b'1', '0.0', 'The Magical City is a brand new colouring book by award-winning illustrator Lizzie Mary Cullen, exploring the hidden magic of cities. Open your mind to the hidden wonder of urban landscapes across the world with this beautifully intricate colouring book. From London to Luxor, follow cobbled pavements through winding streets, look up at skyscrapers soaring to the skies, and gaze over rooftops and dreaming spires. And as you colour and doodle your way through these illustrations, you''ll find hidden details emerge not only on the page but also in the world around you. For fans of Johanna Basford''s The Secret Garden and Millie Marotta''s Animal Kingdom, this is a stunning new colouring book for adults and children alike.'),
(31, '978-9814155083', 'Venice Sketchbook', 'Didier Millet,Csi', '2012-11-16 00:00:00', 96, '13.45', '39.00', '26.99', '2016-01-30 00:00:00', b'1', '0.0', 'Venice, a mosaic of over 100 islands, many connected by the 400 bridges which span its famous canals, is possibly the most romantic city in the world. It began as a village in the marshes and grew into a formidable sea power, dubbed the Queen of the Adriatic. Now its fading glories - the canals and palaces, monuments and churches - battle with the elements, yet remain breathtakingly beautiful. Artist Fabrice Moireau showcases Venice''s grand attractions and hidden charms through his watercolour paintings and pencil sketches.'),
(32, '978-1612382050', 'Rick Steves'' Spanish Phrase Book & Dictionary', 'Avalon Travel Publishing', '2013-12-03 00:00:00', 448, '8.31', '18.95', '16.63', '2016-01-30 00:00:00', b'1', '0.0', '''Informative, concise, and practical, Rick Steves'' Spanish Phrase Book and Dictionary is an essential item for any traveler''s mochila. From ordering tapas in Madrid to making new friends in Costa del Sol, it helps to speak some of the native tongue. Rick Steves, bestselling author of travel guides to Europe, offers well-tested phrases and key words to cover every situation a traveler is likely to encounter. This handy guide provides key phrases for use in everyday circumstances, complete with phonetic spelling, an English-Spanish and Spanish-English dictionary, the latest information on European currency and rail transportation, and even a tear-out cheat sheet for continued language practice as you wait in line at the Guggenheim Bilbao. Informative, concise, and practical, Rick Steves'' Spanish Phrase Book and Dictionary is an essential item for any traveler''s mochila.'''),
(33, '978-1623260484', 'Paris Coloring Book', 'AMMO Books', '2014-10-01 00:00:00', 64, '6.00', '14.49', '12.01', '2016-01-30 00:00:00', b'1', '0.0', 'The first in our new city series of children?s titles celebrates everyone?s favorite city in the world: Paris, the city of lights. The hardcover story book, the memory game, and the coloring book are all illustrated in a hand-drawn, colorful, graphic, and vintage style by California-based illustrator Min Heo. All three titles highlight well-loved landmarks such as the Eiffel Tower, Arc de Triomphe, Notre Dame, Sacr-Couer, the Luxembourg Gardens, the Louvre, and more. They also celebrate important cultural aspects of Paris such as art, architecture, fashion, ballet, and epicurean delights.Pre-schoolers to early readers will enjoy learning a little about French culture and identifying famous Parisian landmarks. This new series is both educational and visually appealing to little kids and design-savvy adults. All three titles together make a perfect gift for would-be travelers of all ages.'),
(34, '978-1518839177', 'Guest Book Vacation: Classic Vacation Guest Book Option', 'CreateSpace Independent Publishing Platform', '2015-10-30 00:00:00', 106, '4.50', '9.50', '9.33', '2016-01-30 00:00:00', b'1', '0.0', '''The ''''Classic Vacation Guest Book Edition'''' was designed with simplicity in mind to cater for that truly, special occasion. The book contains over 400 blank spaces with more than enough room for your guests to leave that unique, personal message. This is something that can be treasured forever, something that you can look back on in future years to help remind you of that wonderful moment in time. PLEASE NOTE: If the color or design of the front cover is not to your taste or does not blend with the occasion in mind, please browse my other ''''Guest Book'''' creations to find a more suitable alternative. Yours Sincerely Matthew Harper'''),
(35, '978-1612382029', 'Rick Steves'' French Phrase Book & Dictionary', 'Avalon Travel Publishing', '2013-11-26 00:00:00', 456, '6.70', '18.95', '13.67', '2016-01-30 00:00:00', b'1', '0.0', '''Informative, concise, and practical, Rick Steves'' French Phrase Book and Dictionary is an essential item for any traveler Bonjour! From ordering a caf au lait in Paris to making new friends in the Loire Valley, it helps to speak some of the native tongue. Rick Steves, bestselling author of travel guides to Europe, offers well-tested phrases and key words to cover every situation a traveler is likely to encounter. This handy guide provides key phrases for use in everyday circumstances and comes complete with phonetic spelling, an English-French and French-English dictionary, the latest information on European currency and rail transportation, and even a tear-out cheat sheet for continued language practice as you wait in line at the Louvre. Informative, concise, and practical, Rick Steves'' French Phrase Book and Dictionary is an essential item for any traveler''s sac  dos.'''),
(36, '978-1743214411', 'Lonely Planet Italian Phrasebook & Dictionary 6th Ed.', 'Lonely Planet', '2015-02-19 00:00:00', 272, '5.50', '13.99', '11.25', '2016-01-30 00:00:00', b'1', '0.0', '''Lonely Planet: The world''s #1 phrasebook publisher* Lonely Planet Italian Phrasebook & Dictionary is your handy passport to culturally enriching travels with the most relevant and useful Italian phrases and vocabulary for all your travel needs. Order an espresso like a local, ask shop keepers about the latest fashions and bargain for fresh produce at the market,  all with your trusted travel companion. With language tools in your back pocket, you can truly get to the heart of wherever you go, so begin your journey now! Get More From Your Trip with Easy-to-Find Phrases for Every Travel Situation!'''),
(37, '978-0811861243', 'A Year in Japan Birthday Book: Dates to Remember Year After Year', 'Chronicle Books', '2008-03-01 00:00:00', 80, '2.50', '14.95', '5.99', '2016-01-30 00:00:00', b'1', '0.0', 'Charming illustrations of everyday Japanese life make this birthday book a distinctive addition to any desktop.'),
(38, '978-0978478414', 'PEI Book of Musts: 101 Places Every Islander Must Visit', 'MacIntyre Purcell Publishing', '2008-10-01 00:00:00', 176, '5.98', '13.95', '13.95', '2016-01-30 00:00:00', b'1', '0.0', 'From potters and painters in Breadalbane to a museum devoted exclusively to seaweed in Miminagash, live music at Baba?s Lounge in Charlottetown to a sleep over at the West Point Lighthouse and the wonders of the Daylily farm in Belfast, this is the MUST list every Islander MUST have. From a beach rich with sand dollars and piping plovers, to the best greasy breakfast for your buck, it is all here. We also get Islanders from across the province to give us their MUST lists. They give us the most romantic beaches . . . Tastiest local recipes . . . Weirdest monuments . . . Best places to go antiquing . . . Coolest cafes. This is the ultimate insider MUST list. If you love Prince Edward Island, you simply MUST have the PEI Book of Musts.'),
(39, '978-0385685719', 'The Road to Little Dribbling: More Notes From a Small Island', 'Doubleday Canada', '2015-10-13 00:00:00', 400, '12.50', '34.95', '25.00', '2016-01-30 00:00:00', b'1', '0.0', 'In 1995, Bill Bryson went on a trip around Britain to celebrate the green and kindly island that had become his home. The hilarious book he wrote about that journey, Notes from a Small Island, became one of the most loved books of recent decades. Now, in this hotly anticipated new travel book, his first in fifteen years and sure to be greeted as the funniest book of the decade, Bryson sets out on a brand-new journey, on a route he dubs the Bryson Line, from Bognor Regis on the south coast to Cape Wrath on the northernmost tip of Scotland.Once again, he will guide us through all that''s best and worst about Britain today-- while doing that incredibly rare thing of making us laugh out loud in public. '),
(40, '978-1780674100', 'London Sketchbook', 'Laurence King Publishing', '2014-10-13 00:00:00', 160, '12.22', '34.95', '25.45', '2016-01-30 00:00:00', b'1', '0.0', 'This follow-up to Jason Brooks''s highly successful Paris Sketchbook is a stunning gift book that brings the big smoke to life through beautiful imagery.From the West End to the Square Mile and Harrods to hipster hang-outs, Brooks explores modern-day London through his unique visual repertoire that unites high fashion, fine art, and traveler''s sketches made on the fly. Although best known for his gorgeous fashion illustrations, which feature regularly in Vogue and Elle, travel has been a recurrent theme in Brooks''s work and, with this new volume, his picturesque adventures continue to amuse and inspire.Part guide book, part illustrated journal, this whimsical take on the cosmopolitan city will appeal to both London lovers and fashionistas. Sumptuous production with different stocks and inks will make this a must for anyone who loves fashion illustration and beautiful books.'),
(41, '978-0785121794', 'Civil War', 'Marvel', '2007-04-11 00:00:00', 208, '5.00', '9.99', '9.99', '2016-01-30 00:00:00', b'1', '0.0', 'The landscape of the Marvel Universe is changing, and it''s time to choose: Whose side are you on? A conflict has been brewing, threatening to pit friend against friend, brother against brother - and all it will take is a single misstep to cost thousands their lives and ignite the fuse! As the war claims its first victims, no one is safe as teams, friendships and families begin to fall apart. The crossover that rewrites the rules, Civil War stars Spider-Man, the New Avengers, the Fantastic Four, the X-Men and the entirety of the Marvel pantheon! Collects Civil War (2006) #1-7.'),
(42, '978-1401258757', 'The Flash Vol. 7 (The New 52)', 'DC Comics', '2016-01-19 00:00:00', 144, '8.00', '16.90', '16.90', '2016-01-30 00:00:00', b'1', '0.0', 'Out of time! The Flash is trapped in a bizarre, lost land terrorized by castaways from the past, present, and future. But that leaves Central City without a hero and, even worse, an all-new murderous Flash shows up to take Barry''s place. With Barry Allen trapped in the Speed Force while an impostor wreaks havoc at home, will the Fastest Man Alive make it back in time to put things right?'),
(43, '978-1401258566', 'Red Hood and the Outlaws Vol. 7 (The New 52)', 'DC Comics', '2016-01-12 00:00:00', 144, '5.00', '9.99', '9.99', '2016-01-30 00:00:00', b'1', '0.0', 'The concluding volume to New York Times best-selling author Scott Lobdell''s run is here in RED HOOD AND THE OUTLAWS VOL. 7. Batman''s former sidekick Jason Todd, now known as the Red Hood, and his running mate Arsenal find their fellow Outlaw, Starfire, missing in action. After discovering who the villainous forces are behind her capture, Jason turns to the only thing he knows can overcome them: venom, the dangerous drug that turns Batman''s rogue Bane into a superpowered killer! Can a superpowered Red Hood overcome Starfire''s kidnappers-- and himself in the process?'),
(44, '978-1401240875', 'Supergirl Vol. 2: Girl in the World (The New 52)', 'DC Comics', '2013-07-16 00:00:00', 144, '6.00', '12.55', '12.55', '2016-01-30 00:00:00', b'1', '0.0', 'Unlike her cousin Superman, Supergirl arrived on Earth from her dead home planet of Krypton fully grown, fully powered and totally confused. Unable to speak anything but her native tongue of Kryptonese, unsure of how she arrived on Earth, and distrustful of the now-adult Superman, Supergirl truly is alone in the world. When Kara is quickly befriended by a Scottish punk rocker named Siobhan?who can mysteriously speak Kryptonese?she?s getting more than a best friend out of the deal. She?s also getting a new ally and an introduction to the strangest enemy she?s ever faced-- the supernatural Black Banshee. But what is Siobhan?s link to this new, immortal threat?'),
(45, '978-0785166801', 'Deadpool - Volume 1: Dead Presidents (Marvel Now)', 'Marvel', '2013-06-11 00:00:00', 136, '5.00', '8.97', '8.97', '2016-01-30 00:00:00', b'1', '0.0', 'Dead former United States presidents, from George Washington to Gerald Ford, have been resurrected-- and that''s bad. The Marvel heroes can''t be the ones to stop them...someone is needed with the reputation, skills and plausible deniability to take out these com-monsters in chiefs. Deadpool time is NOW! Be here as Deadpool de-un-deadifies ex-Presidents left and right...matching wits with Tricky Dick Nixon, fighting a grudge match against Honest Abe Lincoln, and battling Ronald Reagan-- in space! Do you need more than that?! Fine, then: monkeys! And maybe Dr. Strange if you''re good! This is the Deadpool series you will marry someday! In Wade We Trust!'),
(46, '978-1632154026', 'The Walking Dead Volume 24: Life and Death', 'Image Comics', '2015-09-01 00:00:00', 136, '5.00', '9.99', '9.99', '2016-01-30 00:00:00', b'1', '0.0', 'What level of threat do the Whisperers pose to the communities'' safety? Carl''s not waiting around to find out. Also, the return of Michonne. Collects The Walking Dead #139-144.'),
(47, '978-1632152589', 'The Walking Dead Volume 23: Whispers Into Screams', 'Image Comics', '2015-05-12 00:00:00', 136, '5.00', '9.99', '9.99', '2016-01-30 00:00:00', b'1', '0.0', 'A new threat has emerged from within the walker hordes, catching the communities off guard. The time for peace and prosperity is over as a new fight for survival begins. Collects The Walking Dead #133-138.'),
(48, '978-1401235444', 'Suicide Squad Vol. 1: Kicked in the Teeth (The New 52)', 'DC Comics', '2012-07-10 00:00:00', 160, '0.50', '0.99', '0.99', '2016-01-30 00:00:00', b'1', '0.0', 'The story begins with the Suicide Squad defeated, imprisoned and being interrogated about their newest mission. Harley Quinn, King Shark, Deadshot and company must make it out alive without revealing who''s pulling the strings behind their illegal operations. Who will be the first to crack under the pressure? More importantly will they make it all out alive?'),
(49, '978-1401238445', 'Suicide Squad Vol. 2: Basilisk Rising (The New 52)', 'DC Comics', '2013-02-19 00:00:00', 192, '5.00', '9.99', '9.99', '2016-01-30 00:00:00', b'1', '0.0', 'As the surviving Squad members attempt to recover from their disastrous Gotham City mission, we learn a dark secret that hasbeen festering in the team since issue #1: a traitor stalks the Suicide Squad! The saboteur''s mission: Assassinate Amanda Waller,expose the Squad and leave Task Force X in ruins! The Basilisk strikes!'),
(50, '978-0785156598', 'Infinity Gauntlet', 'Marvel', '2011-09-28 00:00:00', 256, '5.00', '9.99', '9.99', '2016-01-30 00:00:00', b'1', '0.0', 'It''s the Avengers, the New Warriors, the X-Men and more against the omnipotent Eternal, Thanos! The Mad Titan has become the most powerful being in the universe, and enslavement or destruction may be the only choices he gives it! The successive Starlin sagas that shook space and time start here!'),
(51, '978-0785192305', 'Uncanny X-Men Vol. 6: Storyville', 'Marvel', '2015-12-22 00:00:00', 136, '5.00', '9.99', '9.99', '2016-01-30 00:00:00', b'1', '0.0', 'CYCLOPS at a crossroads! Magneto...Matthew Malloy...the Last Will and Testament of Charles Xavier...AXIS...all of these pivotal events have driven this already tempestuous mutant to the edge of disaster. Will Scott Summers save himself from ruin or will he make that fateful leap?!'),
(52, '978-0785154907', 'Uncanny X-Men Volume 5: The Omega Mutant', 'Marvel', '2015-04-14 00:00:00', 136, '7.00', '15.12', '15.12', '2016-01-30 00:00:00', b'1', '0.0', '''Original Sin tie-in! The greatest secret of the late Professor Charles Xavier has been revealed! Against such overwhelming power, will the X-Men succeed in holding the line, or will Xavier''s final ''''gift'''' to his children be their undoing? Cyclops must guide his X-Men through the storm that is unleashed, but is the greatest threat to his safety lurking within the dark recesses of his own mind? With one simple act, Cyclops destroyed the life he knew. Now he must remain constantly vigilant in case of attack, while dealing with strangely and frustratingly altered powers. If there''s anyone who would bend over backwards to reteach himself how to use them, it''s Cyclops...but will he regain control of his own personal destiny in time to save his team and his students from Xavier''s terrible secret?'''),
(53, '978-1421585642', 'One-Punch Man, Vol. 1', 'VIZ Media LLC', '2015-09-01 00:00:00', 200, '3.00', '6.39', '6.39', '2016-01-30 00:00:00', b'1', '0.0', 'Nothing about Saitama passes the eyeball test when it comes to superheroes, from his lifeless expression to his bald head to his unimpressive physique. However, this average-looking guy has a not-so-average problem?he just can?t seem to find an opponent strong enough to take on! Every time a promising villain appears, he beats the snot out of ?em with one punch! Can Saitama finally find an opponent who can go toe-to-toe with him and give his life some meaning? Or is he doomed to a life of superpowered boredom?'),
(54, '978-1421585659', 'One-Punch Man, Vol. 2', 'VIZ Media LLC', '2015-09-01 00:00:00', 200, '3.00', '6.39', '6.39', '2016-01-30 00:00:00', b'1', '0.0', 'Nothing about Saitama passes the eyeball test when it comes to superheroes, from his lifeless expression to his bald head to his unimpressive physique. However, this average-looking guy has a not-so-average problem - he just can''t seem to find an opponent strong enough to take on! He''s easily taken out a number of monsters, including a crabby creature, a malicious mosquito girl and a muscly meathead. But his humdrum life takes a drastic turn when he meets Genos - a cyborg who wants to uncover the secret behind his strength!'),
(55, '978-0785195351', 'Amazing Spider-Man Vol. 1', 'Marvel', '2016-01-19 00:00:00', 304, '5.00', '9.99', '9.99', '2016-01-30 00:00:00', b'1', '0.0', '''Peter Parker is back! He''s got a second chance at life, and he''s not wasting a moment of it. But his old foes Electro and the Black Cat are back as well, re-energized and madder than ever! And a new revelation rocks Spider-Man''s world: The radioactive spider that granted Peter his powers bit someone else, too! Who is Silk, and where has she been all these years? Find out as Peter Parker retakes his life, putting the ''''friendly'''' back in the neighborhood, the ''''hero'''' back in ''''super hero'''' - and the ''''amazing'''' back in ''''Spider-Man!'''' Plus, it''s a new chapter in the story you know by heart! He sought revenge, but found responsibility.'''),
(56, '978-1401216672', 'Batman: Killing Joke (DELUXE)', 'DC Comics', '2008-03-19 00:00:00', 64, '5.00', '9.99', '9.99', '2016-01-30 00:00:00', b'1', '0.0', 'According to the grinning engine of madness and mayhem known as The Joker, that''s all that separates the sane from the psychotic. Freed once again from the confines of Arkham Asylum, he''s out to prove his deranged point. And hes going to use Gotham City''s top cop, Commissioner Jim Gordon, and his brilliant and beautiful daughter Barbara to do it.'),
(57, '978-1401235420', 'Batman Vol. 1: The Court of Owls (The New 52)', 'DC Comics', '2013-03-26 00:00:00', 176, '4.00', '7.99', '7.99', '2016-01-30 00:00:00', b'1', '0.0', 'After a series of brutal murders rocks Gotham City, Batman begins to realize that perhaps these crimes go far deeper than appearances suggest. As the Caped Crusader begins to unravel this deadly mystery, he discovers a conspiracy going back to his youth and beyond to the origins of the city he''s sworn to protect. Could the Court of Owls, once thought to be nothing more than an urban legend, be behind the crime and corruption? Or is Bruce Wayne losing his grip on sanity and falling prey to the pressures of his war on crime?'),
(58, '978-9351116615', 'Batman Vol. 2: The City of Owls (The New 52)', 'DC Comics', '2013-10-15 00:00:00', 208, '5.00', '9.99', '9.99', '2016-01-30 00:00:00', b'1', '0.0', 'Gotham''s vigilante protector managed to escape the talons of the Court with his mind and body barely intact. The Dark Knight managed to win the battle with his deadly new aggresors, but certainly not the war. Batman was just the first part of their conquest. Now they have their sights set on something much bigger: Gotham City.'),
(59, '978-0785154129', 'Daredevil Volume 2: West-Case Scenerio', 'Marvel', '2015-03-10 00:00:00', 152, '5.00', '9.99', '9.99', '2016-01-30 00:00:00', b'1', '0.0', 'Original Sin tie-in! When Matt learns something about his past that''s too terrible to accept, he seeks out his mother- only to find her in more trouble than he could possibly have imagined. It''s the untold story of how Matt Murdock''s mother became Sister Maggie, as her biggest sin is finally revealed! Then, Daredevil braves the wilds of Wakanda, takes on a new case with a killer opposition, and faces the Purple Children! One of DD''s oldest foes has unleashed a force that he can''t fight without being swallowed by his darkest moods and thoughts. Is this the beginning of a new, grim chapter in Daredevil''s life? Plus: celebrate Daredevil''s fiftieth anniversary with a look at the future! See if you can spot all the clues we''ve planted for upcoming stories!'),
(60, '978-0785193760', 'All-New Captain America Vol. 1: Hydra Ascendant', 'Marvel', '2015-07-14 00:00:00', 136, '5.00', '9.99', '9.99', '2016-01-30 00:00:00', b'1', '0.0', 'The all-new, spy-fi, high-flying adventures of Sam Wilson, Captain America and Nomad begin here! Hydra is growing. The terrorist band has infiltrated society completely, but what is their ultimate goal? Cap and Nomad''s new partnership is put to the ultimate test as they race to uncover the Sect of the Unknown - but Steve Rogers'' old rogues'' gallery, united by Hydra, gathers to take down the new heroes! And as Nomad stands tall against one of Cap''s oldest foes, the new generations meet: the all-new Captain America battles Sin, daughter of the Red Skull! Cap uncovers the new Hydra''s ultimate goal, but is it too late to stop them? Millions of innocent souls hang in the balance - but, broken and nearly dead from Hydra''s gauntlet, can Cap stop the Great Leveling?'),
(61, '978-0399175503', 'Miraculous Silence: A Journey to Illumination and Healing Through Prayer', 'TarcherPerigee', '2015-12-29 00:00:00', 400, '7.00', '13.99', '13.99', '2016-01-30 00:00:00', b'1', '0.0', '''Regardless of where you are in life?whether you?re celebrating new beginnings, mourning a loss, weathering a hardship, or seeking inspiration?you will find this book of prayers to be the perfect companion. At seventeen, Mitra Rahbar left her homeland due to political unrest. However, she would soon find her way in an unfamiliar land through an ever-deepening prayer life that led her to her soul?s core. Turning outward, she pursued a life of service?first as a social worker and then as a spiritual teacher, healer, and guide. Having worked with students from many walks of life for more than thirty years, Rahbar has a deep understanding of what spiritual seekers long to learn and how best to teach them.In Miraculous Silence, she takes us on a journey into the sacred space of prayer and spiritual healing, providing practical guidance on how to pray and meditate, as well as many of her own prayers to inspire and encourage us. Rahbar also suggests images to visualize and meditate on, mantras to recite in every situation, and stones to aid in the healing process. In these practices, prayers, and inspirations, you will find comfort, illumination, and renewal.'''),
(62, '978-1401945411', 'Nutrition to Intuition', 'Hay House', '2016-01-05 00:00:00', 243, '7.50', '14.99', '9.99', '2016-01-30 00:00:00', b'1', '0.0', 'Now you can learn which foods and beverages will boost your natural intuitive abilities . . . and your physical health! Doreen Virtue and Robert Reeves, N.D., share practical ways for you to enhance your spiritual gifts by making simple dietary changes and additions. You?ll read about how to monitor the life-force energy within your daily meals, drinks, and lifestyle habits so that you can supercharge your intuition and manifestation efforts.In this handy book, Doreen and Robert combine good dietary practices with energizing spiritual techniques. Inside, you?ll discover:How intuition works energetically and physiologically?and the chakras and endocrine systems underlying clairsentience, clairvoyance, clairaudience, and claircognizanceExactly what to eat and drink to honor your uniqueness and sharpen your psychic senses (with recipes for smoothies, snacks, and more to open up your intuitive channels)The spiritual applications of specific herbs and nutritional supplementsThe special signature vibration of each day of the week (and why starting a new eating plan on Monday rarely works)Nutrition for Intuition offers you an array of tools for activating your psychic and healing abilities. As you make these conscious nutritional adjustments, you will clearly perceive the messages and guidance you?re receiving from Heaven and your higher self!'),
(63, '978-0399175701', 'The book of mastery: The master triology: Book 1', 'TarcherPerigee', '2016-01-05 00:00:00', 384, '10.50', '21.00', '17.06', '2016-01-30 00:00:00', b'1', '0.0', '''The first book in channeler Paul Selig?s widely anticipated Mastery Trilogy leads you into an unprecedented journey of self-development, at once building your personal excellence and your ability to improve life for others.The channeled literature of Paul Selig --  who receives clairaudient dictation from unseen intellects called the Guides --  has quickly become the most important and celebrated expression of channeling since A Course In Miracles rose to prominence in the 1970s.Selig?s three previous books --  I Am the Word, The Book of Love and Creation, and The Book of Knowing and Worth --  have won a growing following around the world for their depth, intimacy, and psychological insight. Now, Selig embarks upon an extraordinary new trilogy on the ?Teachings of Mastery? with his inaugural volume: The Book of Mastery. The Book of Mastery provides a deeply practical prescription for heightening your abilities, aptitudes, and sense of personal excellence. The Guides? teachings go much further, however, instructing you how to improve life for others and, ultimately, for global humanity.As the Guides themselves put it:  ?We will tell you this: No one who reads these books will be left unchanged. They will be like molecular systems that reinvigorate and realign and reclaim the reader to themselves in their worth, in their identity and, beyond that, in their physical realm. Underline physical realm if you like. Because the physical realm that we teach in is about to go back to the stone ages unless you all get it together.?'''),
(64, '978-0553386349', 'True Refuge: Finding Peace and Freedom in Your Own Awakened Heart', 'Bantam', '2016-01-12 00:00:00', 320, '10.50', '21.00', '18.69', '2016-01-30 00:00:00', b'1', '0.0', 'How do you cope when facing life-threatening illness, family conflict, faltering relationships, old trauma, obsessive thinking, overwhelming emotion, or inevitable loss? If you?re like most people, chances are you react with fear and confusion, falling back on timeworn strategies: anger, self-judgment, and addictive behaviors. Though these old, conditioned attempts to control our life may offer fleeting relief, ultimately they leave us feeling isolated and mired in pain.There is another way. Beneath the turbulence of our thoughts and emotions exists a profound stillness, a silent awareness capable of limitless love. Tara Brach, author of the award-winning Radical Acceptance, calls this awareness our true refuge, because it is available to every one of us, at any moment, no exceptions. In this book, Brach offers a practical guide to finding our inner sanctuary of peace and wisdom in the midst of difficulty.Based on a fresh interpretation of the three classic Buddhist gateways to freedom?truth, love, and awareness?True Refuge shows us the way not just to heal our suffering, but also to cultivate our capacity for genuine happiness. Through spiritual teachings, guided meditations, and inspirational stories of people who discovered loving presence during times of great struggle, Brach invites us to connect more deeply with our own inner life, one another, and the world around us.True Refuge is essential reading for anyone encountering hardship or crisis, anyone dedicated to a path of spiritual awakening. The book reminds us of our own innate intelligence and goodness, making possible an enduring trust in ourselves and our lives. We realize that what we seek is within us, and regardless of circumstances, ?there is always a way to take refuge in a healing and liberating presence.?');
INSERT INTO `book` (`book_id`, `isbn`, `title`, `publisher`, `publish_date`, `page_number`, `wholesale_price`, `list_price`, `sale_price`, `date_entered`, `available`, `overall_rating`, `synopsis`) VALUES
(65, '978-0061125867', 'The Study Quran: A New Translation and Commentary', 'HarperOne', '2015-11-17 00:00:00', 2048, '37.50', '74.99', '57.60', '2016-01-30 00:00:00', b'1', '0.0', 'An accessible and accurate translation of the Quran that offers a rigorous analysis of its theological, metaphysical, historical, and geographical teachings and backgrounds, and includes extensive study notes, special introductions by experts in the field, and is edited by a top modern Islamic scholar, respected in both the West and the Islamic world.Drawn from a wide range of traditional Islamic commentaries, including Sunni and Shia sources, and from legal, theological, and mystical texts, The Study Quran conveys the enduring spiritual power of the Quran and offers a thorough scholarly understanding of this holy text.Beautifully packaged with a rich, attractive two-color layout, this magnificent volume includes essays by 15 contributors, maps, useful notes and annotations in an easy-to-read two-column format, a timeline of historical events, and helpful indices. With The Study Quran, both scholars and lay readers can explore the deeper spiritual meaning of the Quran, examine the grammar of difficult sections, and explore legal and ritual teachings, ethics, theology, sacred history, and the importance of various passages in Muslim life.With an introduction by its general editor, Seyyed Hossein Nasr, here is a nearly 2,000-page, continuous discussion of the entire Quran that provides a comprehensive picture of how this sacred work has been read by Muslims for over 1,400 years.'),
(66, '978-0062294418', 'The Magic Strings of Frankie Presto: A Novel', 'Harper', '2015-11-10 00:00:00', 512, '16.00', '31.99', '19.19', '2016-01-30 00:00:00', b'1', '0.0', 'Mitch Albom creates his most unforgettable fictional character?Frankie Presto, the greatest guitarist to ever walk the earth?in this magical novel about the bands we join in life and the power of talent to change our lives.In his most stunning novel yet, the voice of Music narrates the tale of its most beloved disciple, young Frankie Presto, a war orphan raised by a blind music teacher in a small Spanish town. At nine years old, Frankie is sent to America in the bottom of a boat. His only possession is an old guitar and six precious strings.But Frankie?s talent is touched by the gods, and his amazing journey weaves him through the musical landscape of the 20th century, from classical to jazz to rock and roll, with his stunning talent affecting numerous stars along the way, including Hank Williams, Elvis Presley, Carole King, Wynton Marsalis and even KISS.Frankie becomes a pop star himself. He makes records. He is adored. But his gift is also his burden, as he realizes, through his music, he can actually affect people?s futures?with one string turning blue whenever a life is altered.At the height of his popularity, Frankie Presto vanishes. His legend grows. Only decades later, does he reappear?just before his spectacular death?to change one last life.With its Forest Gump-like romp through the music world, The Magic Strings of Frankie Presto is a classic in the making. A lifelong musician himself, Mitch Albom delivers a remarkable novel, infused with the message that ?everyone joins a band in this life? and those connections change us all.'),
(67, '978-0399588631', 'The Name of God Is Mercy', 'Random House', '2016-01-12 00:00:00', 176, '17.00', '34.00', '20.40', '2016-01-30 00:00:00', b'1', '0.0', 'In his first book published as Pope, and in conjunction with the Extraordinary Jubilee of Mercy, Pope Francis here invites all humanity to an intimate and personal dialogue on the subject closest to his heart?mercy?which has long been the cornerstone of his faith and is now the central teaching of his papacy.In this conversation with Vatican reporter Andrea Tornielli, Francis explains?through memories from his youth and moving anecdotes from his experiences as a pastor?why ?mercy is the first attribute of God.? God ?does not want anyone to be lost. His mercy is infinitely greater than our sins,? he writes. As well, the Church cannot close the door on anyone, Francis asserts?on the contrary, its duty is to go out into the world to find its way into the consciousness of people so that they can assume responsibility for, and move away from, the bad things they have done.The first Jesuit and the first South American to be elected Bishop of Rome, Pope Francis has traveled around the world spreading God?s message of mercy to the largest crowds in papal history. Clear and profound, The Name of God Is Mercy resonates with this desire to reach all those who are looking for meaning in life, a road to peace and reconciliation, and the healing of physical and spiritual wounds. It is being published in more than eighty countries around the world.'),
(68, '978-0486456942', 'Mystical Mandala Coloring Book', 'Dover Publications', '2007-02-02 00:00:00', 32, '2.63', '5.25', '5.25', '2016-01-30 00:00:00', b'1', '0.0', 'Explore the intricate patterns and symmetrical beauty of these ready-to-color designs! This vibrant collection of drawings was inspired by the hypnotic appeal of the mandala. An ancient form of meditative art, mandalas are complex circular designs that draw the eye inward, toward their centers. These 30 dazzling mandala designs will captivate colorists of all ages, challenging them to create kaleidoscopic effects.'),
(69, '978-1878424310', 'The Four Agreements: A Practical Guide to Personal Freedom', 'Amber-Allen Publishing', '1997-11-01 00:00:00', 138, '9.25', '18.50', '12.87', '2016-01-30 00:00:00', b'1', '0.0', 'In The Four Agreements shamanic teacher and healer Don Miguel Ruiz exposes self-limiting beliefs and presents a simple yet effective code of personal conduct learned from his Toltec ancestors. Full of grace and simple truth, this handsomely designed book makes a lovely gift for anyone making an elementary change in life, and it reads in a voice that you would expect from an indigenous shaman.'),
(70, '978-1572245372', 'The Untethered Soul: The Journey Beyond Yourself', 'New Harbinger Publications', '2007-09-15 00:00:00', 200, '11.98', '23.95', '16.89', '2016-01-30 00:00:00', b'1', '0.0', 'What would it be like to free yourself from limitations and soar beyond your boundaries? What can you do each day to discover inner peace and serenity? The Untethered Soul -now a #1 New York Times bestseller-offers simple yet profound answers to these questions.Whether this is your first exploration of inner space , or you''ve devoted your life to the inward journey, this book will transform your relationship with yourself and the world around you. You''ll discover what you can do to put an end to the habitual thoughts and emotions that limit your consciousness. By tapping into traditions of meditation and mindfulness, author and spiritual teacher Michael A. Singer shows how the development of consciousness can enable us all to dwell in the present moment and let go of painful thoughts and memories thatkeep us from achieving happiness and self-realization.Copublished with the Institute of Noetic Sciences (IONS) The Untethered Soul begins by walking you through your relationship with your thoughts and emotions, helping you uncover the source and fluctuations of your inner energy. It then delves into what you can do to free yourself from the habitual thoughts, emotions, and energy patterns that limit your consciousness. Finally, with perfect clarity, this book opens the door to a life lived in the freedom of your innermost being.The Untethered Soul has already touched the lives of countless readers, and is now available in a special hardcover gift edition with ribbon bookmark -the perfect gift for yourself, a loved one, or anyone who wants a keepsake edition of this remarkable book.'),
(71, '978-1594634710', 'Big Magic: Creative Living Beyond Fear', 'Riverhead Books', '2015-09-22 00:00:00', 288, '14.98', '29.95', '17.97', '2016-01-30 00:00:00', b'1', '0.0', 'Readers of all ages and walks of life have drawn inspiration and empowerment from Elizabeth Gilbert?s books for years. Now this beloved author digs deep into her own generative process to share her wisdom and unique perspective about creativity. With profound empathy and radiant generosity, she offers potent insights into the mysterious nature of inspiration. She asks us to embrace our curiosity and let go of needless suffering. She shows us how to tackle what we most love, and how to face down what we most fear. She discusses the attitudes, approaches, and habits we need in order to live our most creative lives. Balancing between soulful spirituality and cheerful pragmatism, Gilbert encourages us to uncover the ?strange jewels? that are hidden within each of us. Whether we are looking to write a book, make art, find new ways to address challenges in our work,  embark on a dream long deferred, or simply infuse our everyday lives with more mindfulness and passion, Big Magic cracks open a world of wonder and joy.'),
(72, '978-0738734026', 'Llewellyn''s 2016 Witches'' Calendar', 'Llewellyn Publications', '2015-07-08 00:00:00', 28, '8.50', '16.99', '9.34', '2016-01-30 00:00:00', b'1', '0.0', '''Enchanting you for more than fifteen years, Llewellyn?s Witches? Calendar is a daily reminder of the magical nature of the world. Align yourself with the rhythms of the changing seasons, celebrate with rituals and spellwork, and measure your days with magic. This treasury of craft wisdom provides both new and experienced Witches with Moon signs and phases,  planetary motion (including retrogrades),  daily color correspondences,  solar and lunar eclipses,  even lunar gardening tips. With original scratchboard art by Kathleen Edwards and unforgettable spells, essays, and rituals by popular authors, this calendar is a perfect resource for living in balance and harmony. '''),
(73, '978-1577314806', 'The Power of Now: A Guide to Spiritual Enlightenment', 'New World Library', '2004-08-17 00:00:00', 224, '10.75', '21.50', '14.67', '2016-01-30 00:00:00', b'1', '0.0', '''A contemporary spiritual master shows how living in the present leads to a happier, healthier, more fulfilling lifeMuch more than simple principles and platitudes, The Power of Now takes readers on an inspiring spiritual journey to find their true and deepest self and reach the ultimate in personal growth and spirituality: the discovery of truth and light. In the first chapter, Tolle introduces readers to enlightenment and its natural enemy, the mind. He awakens readers to their role as a creator of pain and shows them how to have a pain-free identity by living fully in the present. The journey is thrilling, and along the way, the author shows how to connect to the indestructible essence of our Being, the eternal, ever-present One Life beyond the myriad forms of life that are subject to birth and death.'''' Only after regaining awareness of Being, liberated from Mind and intensely in the Now, is there Enlightenment.'''),
(74, '978-0807014295', 'Man''s Search for Meaning', 'Beacon Press', '2006-06-01 00:00:00', 184, '6.00', '11.99', '10.79', '2016-01-30 00:00:00', b'1', '0.0', '''With a new Foreword by Harold S. Kushner and a new Biographical Afterword by William J. WinsladePsychiatrist Viktor Frankl''s memoir has riveted generations of readers with its descriptions of life in Nazi death camps and its lessons for spiritual survival. Between 1942 and 1945 Frankl labored in four different camps, including Auschwitz, while his parents, brother, and pregnant wife perished. Based on his own experience and the experiences of others he treated later in his practice, Frankl argues that we cannot avoid suffering but we can choose how to cope with it, find meaning in it, and move forward with renewed purpose. Frankl''s theory-known as logotherapy, from the Greek word logos (''''meaning'''')-holds that our primary drive in life is not pleasure, as Freud maintained, but the discovery and pursuit of what we personally find meaningful.At the time of Frankl''s death in 1997, Man''s Search for Meaning had sold more than 10 million copies in twenty-four languages. A 1991 reader survey for the Library of Congress that asked readers to name a ''''book that made a difference in your life'''' found Man''s Search for Meaning among the ten most influential books in America.Beacon Press, the original English-language publisher of Man''s Search for Meaning, is issuing this new paperback edition with a new Foreword, biographical Afterword, jacket, price, and classroom materials to reach new generations of readers.'''),
(75, '978-1401944315', 'Angel Detox: Taking Your Life to a Higher Level Through Releasing Emotional, Physical, and Energetic Toxins', 'Hay House', '2014-01-06 00:00:00', 293, '8.00', '15.99', '1.99', '2016-01-30 00:00:00', b'1', '0.0', 'Work with the Angels to Detox Your Body and EnergyDetoxing with the help of your angels is a gentle way to release impurities from your body, reduce fatigue, and heal addictions. Best-selling author Doreen Virtue and naturopath Robert Reeves teach you simple steps to increase your energy and mental focus, banish bloating, feel and look more youthful, and regain your sense of personal power. In the process, you?ll rid your life of physical toxins, as well as negative emotions and energies.Angel Detox guides you step-by-step on how to detox your diet, lifestyle, and relationships. You?ll learn how to minimize or eliminate cravings for unhealthful food and substances, feel motivated, and enjoy wellness in all areas of your life. This book also includes 7-Day Detox Plans for those wanting to quit smoking or drinking, or to flush out environmental pollution.'),
(76, '978-0894864025', 'Codependent No More: How to Stop Controlling Others and Start Caring for Yourself', 'Hazelden', '1992-05-04 00:00:00', 250, '10.75', '21.50', '16.31', '2016-01-30 00:00:00', b'1', '0.0', '''Is someone else''s problem your problem? If, like so many others, you''ve lost sight of your own life in the drama of tending to someone else''s, you may be codependent - and you may find yourself in this book - Codependent No More. The healing touchstone of millions, this modern classic by one of America''s best-loved and most inspirational authors holds the key to understanding codependency and to unlocking its stultifying hold on your life. With instructive life stories, personal reflections, exercises, and self-tests, Codependent No More is a simple, straightforward, readable map of the perplexing world of codependency - charting the path to freedom and a lifetime of healing, hope, and happiness.Melody Beattie is the author of Beyond Codependency, The Language of Letting Go, Stop Being Mean to Yourself, The Codependent No More Workbook and Playing It by Heart.'''),
(77, '978-1582705248', 'The Soul Searcher''s Handbook: A Modern Girl''s Guide to the New Age World', 'Atria Books/Beyond Words', '2015-11-17 00:00:00', 272, '12.50', '24.99', '20.56', '2016-01-30 00:00:00', b'1', '0.0', 'From aromatherapy and numerology to healing crystals and meditation, this fun and sassy guide to everything body-mind-spirit defines New Age practices for a new generation.What type of crystal should I put in my car for a road trip? Should I Feng Shui my smart phone screen? In this illuminating introduction for the modern-day soul searcher, Emma Mildon shines light on everything your mom didn?t teach you about neo?New Age practices.With something for every type of spiritual seeker, The Soul Searcher?s Handbook offers easy tips, tricks, and how-tos for incorporating everything from dreamology and astrology to mysticism and alternative healing into your everyday life. This handbook is your #1 go-to guide?handy, accessible, entertaining, and packed with all the wisdom you need in one place.Your destiny, gifted to you at birth, is waiting. So plug into the universe, dig your toes into the soil of Mother Earth, and open your soul to your full potential. Regardless of what you?re seeking, The Soul Searcher?s Handbook is your awakening to a more fulfilling and soulful life.'),
(78, '978-1101902639', 'Trim Healthy Mama Plan: The Easy-Does-It Approach to Vibrant Health and a Slim Waistline', 'Harmony', '2015-09-15 00:00:00', 336, '13.00', '25.99', '16.29', '2016-01-30 00:00:00', b'1', '0.0', 'Counting calories is out.  All the food groups are in.  Becoming trim and healthy doesn''t have to be difficult or painstaking anymore.  After trying almost every fad diet out there, Serene Allison and Pearl Barrett, creators of the Trim Healthy Mama movement, took matters into their own hands. Through trial and error and much research, they created the TRIM HEALTHY MAMA PLAN, the breakthrough lifestyle program to help readers of all ages and stages get healthy, slim down and keep off the weight once and for all.  Based on the authors? successful self-published book, this simplified, improved, practical plan shows readers a unique way to lose weight and get healthy by eliminating sugar, and still eating hearty, delicious food. The biblically-sound and highly effective eating approach centers on Satisfying meals (which include more fats and protein) and Energizing meals (which include more carbs and protein), as they are the key to success.  Scrumptious whole, unprocessed foods, including fats, blood sugar friendly grains, proteins, fruits, and vegetables, are eaten in a way that boosts metabolism, yet still fits into anyone?s hectic lifestyle.  It?s family friendly and effective for pregnant and nursing mothers, pre or post- menopausal women, and also those without weight or health issues?even men and growing children.The book includes menu plans, a list of key super foods to eat on plan, time-saving tips, and pantry stocking and lifestyle advice to help readers successfully reach their goals.Join the Trim Healthy Mama movement and along with thousands of others, discover the groundbreaking, easy-does-it, and delicious way to eat for health and weight loss.'),
(79, '978-1582701707', 'The Secret', 'Atria Books/Beyond Words', '2006-11-28 00:00:00', 216, '15.98', '31.95', '20.21', '2016-01-30 00:00:00', b'1', '0.0', 'Fragments of a Great Secret have been found in the oral traditions, in literature, in religions and philosophies throughout the centuries. For the first time, all the pieces of The Secret come together in an incredible revelation that will be life-transforming for all who experience it.In this book, you''ll learn how to use The Secret in every aspect of your life --  money, health, relationships, happiness, and in every interaction you have in the world. You''ll begin to understand the hidden, untapped power that''s within you, and this revelation can bring joy to every aspect of your life.The Secret contains wisdom from modern-day teachers --  men and women who have used it to achieve health, wealth, and happiness. By applying the knowledge of The Secret, they bring to light compelling stories of eradicating disease, acquiring massive wealth, overcoming obstacles, and achieving what many would regard as impossible.'),
(80, '978-0451205360', 'The Richest Man in Babylon', 'Signet', '2012-02-03 00:00:00', 144, '6.25', '12.50', '8.74', '2016-01-30 00:00:00', b'1', '0.0', 'Beloved by millions, this timeless classic holds the key to all you desire and everything you wish to accomplish. This is the book that reveals the secret to personal wealth.The Success Secrets of the Ancients?An Assured Road to Happiness and Prosperity Countless readers have been helped by the famous ?Babylonian parables,? hailed as the greatest of all inspirational works on the subject of thrift, financial planning, and personal wealth. In language as simple as that found in the Bible, these fascinating and informative stories set you on a sure path to prosperity and its accompanying joys. Acclaimed as a modern-day classic, this celebrated bestseller offers an understanding of?and a solution to?your personal financial problems that will guide you through a lifetime. This is the book that holds the secrets to keeping your money?and making more.'),
(81, '978-0553381153', 'Ina May''s Guide to Childbirth: Updated With New Material', 'Bantam', '2003-03-04 00:00:00', 368, '10.00', '20.00', '14.99', '2016-01-30 00:00:00', b'1', '0.0', 'Drawing upon her thirty-plus years of experience, Ina May Gaskin, the nation?s leading midwife, shares the benefits and joys of natural childbirth by showing women how to trust in the ancient wisdom of their bodies for a healthy and fulfilling birthing experience. Based on the female-centered Midwifery Model of Care, Ina May?s Guide to Natural Childbirth gives expectant mothers comprehensive information on everything from the all-important mind-body connection to how to give birth without technological intervention. '),
(82, '978-1443414562', 'The Happiness Project', 'HarperCollins Publishers', '2012-04-24 00:00:00', 336, '9.00', '17.99', '11.08', '2016-01-30 00:00:00', b'1', '0.0', 'What if you could change your life without really changing your life? On the outside, Gretchen Rubin had it all --  a good marriage, healthy children and a successful career --  but she knew something was missing. Determined to end that nagging feeling, she set out on a year-long quest to learn how to better enjoy the life she already had.Each month, Gretchen pursued a different set of resolutions --  go to sleep earlier, tackle a nagging task, bring people together, take time to be silly --  along with dozens of other goals. She read everything from classical philosophy to cutting-edge scientific studies, from Winston Churchill to Oprah, developing her own definition of happiness and a plan for how to achieve it. She kept track of which resolutions worked and which didn?t, sharing her stories and collecting those of others through her blog (created to fulfill one of Mar?s resolutions). Bit by bit, she began to appreciate and amplify the happiness in her life.The Happiness Project is the engaging, relatable and inspiring result of the author?s twelve-month adventure in becoming a happier person. Written with a wicked sense of humour and sharp insight, Gretchen Rubin?s story will inspire readers to embrace the pleasure in their lives and remind them how to have fun.'),
(83, '978-0486793276', 'Creative Haven Entangled Coloring Book', 'Dover Publications', '2015-04-22 00:00:00', 64, '4.00', '8.00', '8.00', '2016-01-30 00:00:00', b'1', '0.0', 'This collection of more than 30 original patterns was inspired by Zentangle, a method of creating repetitive patterns that promotes relaxation as well as creative expression. The sweeping, free-form line designs incorporate flowers, stars, and geometric shapes. Pages are perforated and printed on one side only for easy removal and display. Specially designed for experienced colorists, Entangled and other Creative Haven coloring books offer an escape to a world of inspiration and artistic fulfillment. Each title is also an effective and fun-filled way to relax and reduce stress.'),
(84, 'B00M60RKW8', 'The Girl on the Train', 'Doubleday Canada', '2015-01-06 00:00:00', 320, '13.99', '9.99', '9.99', '2016-01-30 00:00:00', b'1', '0.0', 'Three women, three men, connected through marriage or infidelity. Each is to blame for something. But only one is a killer in this nail-biting, stealthy psychological thriller about human frailty and obsession. Just what goes on in the houses you pass by every day? Rachel takes the same commuter train every morning and evening, rattling over the same junctions, flashing past the same townhouses.The train stops at the same signal every day, and she sees the same couple, breakfasting on their roof terrace. Jason and Jess, as she calls them, seem so happy. Then one day Rachel sees someone new in their garden. Soon after, Rachel sees the woman she calls Jess on the news. Jess has disappeared. Through the ensuing police investigation, Rachel is drawn deeper into the lives of the couple she learns are really Megan and Scott Hipwell. As she befriends Scott, Rachel pieces together what really happened the day Megan disappeared. But when Megan''s body is found, Rachel finds herself the chief suspect in the case. Plunged into a world of betrayals, secrets and deceptions, Rachel must confront the facts about her own past and her own failed marriage. A sinister and twisting story that will keep you guessing at every turn, The Girl on the Train is a high-speed chase for the truth.'),
(85, 'B00EMXBDMA', 'The Martian: A Novel', 'Broadway Books', '2014-02-11 00:00:00', 385, '9.99', '9.99', '9.99', '2016-01-30 00:00:00', b'1', '0.0', '''Six days ago, astronaut Mark Watney became one of the first people to walk on Mars. Now, he''s sure he''ll be the first person to die there.After a dust storm nearly kills him and forces his crew to evacuate while thinking him dead, Mark finds himself stranded and completely alone with no way to even signal Earth that he?s alive?and even if he could get word out, his supplies would be gone long before a rescue could arrive. Chances are, though, he won''t have time to starve to death. The damaged machinery, unforgiving environment, or plain-old ''''human error'''' are much more likely to kill him first. But Mark isn''t ready to give up yet. Drawing on his ingenuity, his engineering skills?and a relentless, dogged refusal to quit?he steadfastly confronts one seemingly insurmountable obstacle after the next. Will his resourcefulness be enough to overcome the impossible odds against him?'''),
(86, 'B00TL3Z3HC', 'The Girl in the Spider''s Web', 'Viking', '2015-09-01 00:00:00', 416, '15.99', '9.99', '9.99', '2016-01-30 00:00:00', b'1', '0.0', 'The girl is back. September 2015. See what happens next.In this adrenaline-charged, up-to-the moment political thriller, Stieg Larsson?s Lisbeth Salander and Mikael Blomkvist are back. The troubled genius hacker and crusading journalist thrilled the world in The Girl with the Dragon Tattoo, The Girl Who Played with Fire, and The Girl Who Kicked the Hornet?s Nest?the trilogy that has sold more than 75 million copies worldwide.'),
(87, 'B00VES8D6K', 'Pretty Girls', 'William Morrow', '2015-09-29 00:00:00', 421, '4.99', '4.99', '4.99', '2016-01-30 00:00:00', b'1', '0.0', '''Lee Child says it?s ?stunning? certain to be a book of the year.? Kathy Reichs calls it ?extraordinary? a major achievement.? Jeffery Deaver says that ?fiction doesn''t get any better than this.? Gillian Flynn says of Karin Slaughter: ?I?d follow her anywhere.? See for yourself what these #1 New York Times-bestselling authors are talking about. Sisters. Strangers. Survivors. More than twenty years ago, Claire and Lydia?s teenaged sister Julia vanished without a trace. The two women have not spoken since, and now their lives could not be more different. Claire is the glamorous trophy wife of an Atlanta millionaire. Lydia, a single mother, dates an ex-con and struggles to make ends meet. But neither has recovered from the horror and heartbreak of their shared loss?a devastating wound that''s cruelly ripped open when Claire''s husband is killed. The disappearance of a teenage girl and the murder of a middle-aged man, almost a quarter-century apart: what could connect them? Forming a wary truce, the surviving sisters look to the past to find the truth, unearthing the secrets that destroyed their family all those years ago . . . and uncovering the possibility of redemption, and revenge, where they least expect it. Powerful, poignant, and utterly gripping, packed with indelible characters and unforgettable twists, Pretty Girls is a masterful novel from one of the finest writers working today.'''),
(88, 'B00U6DNYLI', 'Cross Justice', 'Little, Brown and Company', '2015-11-23 00:00:00', 420, '34.99', '25.98', '25.98', '2016-01-30 00:00:00', b'1', '0.0', '''The toughest cases hit closest to home.  Alex Cross left his hometown, and some awful family tragedies, for a better life with Nana Mama in Washington, DC. He hasn''t looked back.  Now his cousin Stefan has been accused of a horrible, unthinkable murder, and Cross drives south with Bree, Nana Mama, Jannie, and Ali to Starksville, North Carolina, for the first time in thirty-five years. Back home, he discovers a once proud community down on its luck, and local residents who don''t welcome him with open arms. As Cross steps into his family home, the horrors of his childhood flood back-- and he learns that they''re not really over. He brings all his skill to finding out the truth about his cousin''s case. But truth is hard to come by in a town where no one feels safe to speak. Chasing his ghosts takes Cross all the way down to the sugarcane fields of Florida, where he gets pulled into a case that has local cops needing his kind of expertise: a string of socialite murders with ever more grisly settings. He''s chasing too many loose ends-- a brutal killer, the truth about his own past, and justice for his cousin-- and any one of the answers might be fatal. In Cross Justice, Alex Cross confronts the deadliest-- and most personal-- case of his career. It''s a propulsive, edge-of-your-seat thrill ride that proves you can go home again-- but it just might kill you. '''),
(89, 'B0180SIXZW', 'Deep Freeze', 'Zebra', '2015-09-14 00:00:00', 512, '5.84', '5.84', '5.84', '2016-01-30 00:00:00', b'1', '0.0', 'When beautiful Jenna Hughes moves with her two children to a small Oregon town in bestseller Jackson''s well-crafted romantic thriller, the retired movie star expects to start a quiet new life, but a psychotic fan''s deadly obsession soon throws the tranquil community into chaos. One by one, women are kidnapped by a serial killer who hopes to mold them into the likeness of his favorite actress, and Jenna knows that it''s only a matter of time before she''s the next victim. Jackson (The Morning After) weaves together various subplots, including Jenna''s relationship with her rebellious teenage daughter and the inevitable love interest with the rugged local cop, to create a fast-paced and surprisingly complex story. Unfortunately, the focus on the villain''s absolute evil rather than on his humanity doesn''t allow him to be as disturbing as he could be, but most of the other smalltown characters are more believable. The depiction of the protagonist, a woman who desperately wants her life to be as normal as possible, reaches an unusual depth, and the crisp, skillful language infuses every sentence with suspense. Despite some predictable moments and over-the-top torture scenes, this chilling novel about the high price of fame will keep readers riveted. '),
(90, 'B003L786FK', 'Tough Customer', 'Simon & Schuster', '2010-08-10 00:00:00', 516, '1.99', '1.99', '1.99', '2016-01-30 00:00:00', b'1', '0.0', '''When a hardened cop turned Private Investigator gets a call about a woman he hasn?t seen in thirty years, he must race to Texas to stop the stalker terrorizing her. A blistering new thriller from the bestselling author of Smash Cut. Colleagues, friends, and lovers know Dodge Hanley as a private investigator who doesn?t let rules get in his way?in his private life as well as his professional one. If he breaks a heart, or bends the law in order to catch a criminal, he does so without hesitation or apology. That?s why he?s the first person Caroline King?who after a thirty-year separation continues to haunt his dreams?asks for help when a deranged stalker attempts to murder their daughter?the daughter Dodge has never met. He has a whole bagful of grudging excuses for wishing to ignore Caroline?s call, and one compelling reason to drop everything and fly down to Texas: guilt. Dodge?s mind may be a haze of disturbing memories and bad decisions, but he arrives in Houston knowing with perfect clarity that his daughter, Berry, is in danger. She has become the object of desire of a coworker, a madman, and genius with a penchant for puzzles and games who has spent the past year making Berry?s life hell, and who now has vowed to kill her. Dodge joins forces with local deputy sheriff Ski Nyland, but the alarming situation goes from bad to worse when the stalker begins to claim other victims and leaves an ominous trail of clues as he lethally works his way toward Berry. Sensing the killer drawing nearer, Dodge, who?s survived vicious criminals and his own self-destructive impulses, realizes that this time he?s in for the fight of his life. From acclaimed bestselling author Sandra Brown, Tough Customer is a heart-pounding tale about obsession and murder, the fragile nature of relationships, and, possibly, second chances. '''),
(91, '1503944433', 'The Good Neighbor', 'Lake Union Publishing', '2015-09-01 00:00:00', 206, '1.99', '1.99', '1.99', '2016-01-30 00:00:00', b'1', '0.0', '''From a phenomenal new voice in suspense fiction comes a book that will forever change the way you look at the people closest to you? Shadow Cove, Washington, is the kind of town everyone dreams about?quaint streets, lush forests, good neighbors. That?s what Sarah thinks as she settles into life with her new husband, Dr. Johnny McDonald. But all too soon she discovers an undercurrent of deception. And one October evening when Johnny is away, sudden tragedy destroys Sarah?s happiness. Dazed and stricken with grief, she and Johnny begin to rebuild their shattered lives. As she picks up the pieces of her broken home, Sarah discovers a shocking secret that forces her to doubt everything she thought was true?about her neighbors, her friends, and even her marriage. With each stunning revelation, Sarah must ask herself, Can we ever really know the ones we love?'''),
(92, '054774501X', 'The Hangman''s Daughter (A Hangman''s Daughter Tale Book 1)', 'AmazonCrossing', '2010-12-07 00:00:00', 448, '7.99', '7.99', '7.99', '2016-01-30 00:00:00', b'1', '0.0', 'Germany, 1660: When a dying boy is pulled from the river with a mark crudely tattooed on his shoulder, hangman Jakob Kuisl is called upon to investigate whether witchcraft is at play. So begins The Hangman''s Daughter-- the chillingly detailed, fast-paced historical thriller from German television screenwriter, Oliver Ptzsch-- a descendent of the Kuisls, a famous Bavarian executioner clan. '),
(93, 'B00Y0PIEZY', 'Known (A Bone Secrets Novel)', 'Montlake Romance', '2016-01-19 00:00:00', 348, '8.72', '5.99', '5.99', '2016-01-30 00:00:00', b'1', '0.0', '''After a brutal snowstorm in the Cascade Mountains, Chris Jacobs discovers two things he never expected to see: the charred shell of a cabin, and Gianna Trask and her sixteen-year-old daughter huddled in an SUV, having barely escaped from their burning vacation rental. Still scarred from a childhood ordeal, Chris knows there?s something sinister about the scene?it?s the stench of burned flesh. Forced to wait out the blizzard in their rescuer?s retreat, medical examiner Gianna Trask wonders if her hidden past has finally caught up with her. When a body is found in the destroyed cabin?s ashes and a forest ranger is brutally murdered, both Gianna and Chris must confront their secrets if they want to escape the violent threat lurking outside. In the fifth book in the Bone Secrets series, Kendra Elliot leads readers on a dangerous, twisting journey of two lives forever changed by a fiery snowstorm in the mountains.'''),
(94, 'B00UEKRTW8', 'Rogue Lawyer', 'Doubleday', '2015-10-20 00:00:00', 354, '15.99', '15.99', '15.99', '2016-01-30 00:00:00', b'1', '0.0', '''On the right side of the law. Sort of. Sebastian Rudd is not your typical street lawyer. He works out of a customized bulletproof van, complete with Wi-Fi, a bar, a small fridge, fine leather chairs, a hidden gun compartment, and a heavily armed driver. He has no firm, no partners, no associates, and only one employee, his driver, who?s also his bodyguard, law clerk, confidant, and golf caddy. He lives alone in a small but extremely safe penthouse apartment, and his primary piece of furniture is a vintage pool table. He drinks small-batch bourbon and carries a gun. Sebastian defends people other lawyers won?t go near: a drug-addled, tattooed kid rumored to be in a satanic cult, who is accused of molesting and murdering two little girls,  a vicious crime lord on death row,  a homeowner arrested for shooting at a SWAT team that mistakenly invaded his house.  Why these clients? Because he believes everyone is entitled to a fair trial, even if he, Sebastian, has to cheat to secure one. He hates injustice, doesn?t like insurance companies, banks, or big corporations,  he distrusts all levels of government and laughs at the justice system?s notions of ethical behavior. Sebastian Rudd is one of John Grisham?s most colorful, outrageous, and vividly drawn characters yet. Gritty, witty, and impossible to put down, Rogue Lawyer showcases the master of the legal thriller at his very best. '''),
(95, 'B00HVGGH76', 'Mind''s Eye', 'Paragon Press', '2014-01-11 00:00:00', 362, '7.59', '7.59', '7.59', '2016-01-30 00:00:00', b'1', '0.0', '''Nick Hall can''t remember who he is, but a number of people seem desperate to kill him. Soon Hall learns that advanced electronics have been implanted in his brain, allowing him to surf the web with his thoughts and read minds. But who inserted the implants? And why? And why has he been marked for death? As Hall races to find answers, he comes to learn that far more is at stake than just his life. Because his actions can either catapult civilization to new heights-- or bring about its total collapse. Extrapolated from actual research on thought-controlled web surfing, Mind''s Eye is a roller-coaster ride of a thriller that raises a number of intriguing, and sometimes chilling, possibilities about a future that is just around the corner. Be sure to read the next Nick Hall thriller, BrainWeb, available now. ''''Richards is a tremendous new talent'''' (Stephen Coonts) who can ''''keep you turning the pages all night long'''' (Douglas Preston) '''),
(96, 'B004CFA9DM', 'The Girl in the Green Raincoat: A Tess Monaghan Novel', 'William Morrow', '2011-01-18 00:00:00', 193, '1.99', '1.99', '1.99', '2016-01-30 00:00:00', b'1', '0.0', '''In the third trimester of her pregnancy, Baltimore private investigator Tess Monaghan is under doctor''s orders to remain immobile. Bored and restless, reduced to watching the world go by outside her window, she takes small comfort in the mundane events she observes . . . like the young woman in a green raincoat who walks her dog at the same time every day. Then one day the dog is running free and its owner is nowhere to be seen. Certain that something is terribly wrong, and incapable of leaving well enough alone, Tess is determined to get to the bottom of the dog walker''s abrupt disappearance, even if she must do so from her own bedroom. But her inquisitiveness is about to fling open a dangerous Pandora''s box of past crimes and troubling deaths . . . and she''s not only putting her own life in jeopardy but also her unborn child''s. Previously serialized in the New York Times, and now published in book form for the very first time, The Girl in the Green Raincoat is a masterful Hitchcockian thriller from one of the very best in the business: multiple award-winner Laura Lippman.'''),
(97, 'B00U6DNZOY', 'The Crossing', 'Little, Brown and Company', '2015-11-03 00:00:00', 401, '14.99', '14.99', '14.99', '2016-01-30 00:00:00', b'1', '0.0', '''Detective Harry Bosch has retired from the LAPD, but his half-brother, defense attorney Mickey Haller, needs his help. A woman has been brutally murdered in her bed and all evidence points to Haller''s client, a former gang member turned family man. Though the murder rap seems ironclad, Mickey is sure it''s a setup. Bosch doesn''t want anything to do with crossing the aisle to work for the defense. He feels it will undo all the good he''s done in his thirty years as a homicide cop. But Mickey promises to let the chips fall where they may. If Harry proves that his client did it, under the rules of discovery, they are obliged to turn over the evidence to the prosecution. Though it goes against all his instincts, Bosch reluctantly takes the case. The prosecution''s file just has too many holes and he has to find out for himself: if Haller''s client didn''t do it, then who did? With the secret help of his former LAPD partner Lucy Soto, Harry starts digging. Soon his investigation leads him inside the police department, where he realizes that the killer he''s been tracking has also been tracking him.'''),
(98, '161109898X', 'Buried (A Bone Secrets Novel)', 'Montlake Romance', '2013-03-26 00:00:00', 362, '1.99', '1.99', '1.99', '2016-01-30 00:00:00', b'1', '0.0', '''Eighteen years ago, Chris Jacobs walked out of the forest, the lone survivor of a school bus load of children who?d vanished two years before. His memory was gone, his body beaten and emaciated. Today, the sad remains of the missing children have been discovered along with evidence that they were held captive for years. But investigative reporter Michael Brody?s brother is still missing. He sets out to question Chris, hoping his memory has returned. Constant fear of being found by his kidnapper has driven Chris into hiding. The only lead Michael has is Chris?s sister, Jamie. As they race to find Chris, Michael and Jamie somehow find each other among the decades of wreckage. But locating Chris may not be so easy. Now grown, his scars go far deeper than skin. In Buried, the next thrilling Bone Secrets novel from bestselling author Kendra Elliot, a damaged hero digs deep into his terrifying past?and unearths a chance at love for the future.'''),
(99, 'B00LD1OUPQ', 'Cold Betrayal: An Ali Reynolds Novel', 'Touchstone', '2015-03-10 00:00:00', 353, '1.99', '1.99', '1.99', '2016-01-30 00:00:00', b'1', '0.0', '''Revenge isn?t the only dish served cold... Ali?s longtime friend and Taser-carrying nun, Sister Anselm, rushes to the bedside of a young pregnant woman hospitalized for severe injuries after she was hit by a car on a deserted Arizona highway. The girl had been running away from The Family, a polygamous cult with no patience for those who try to leave its ranks. Something about her strikes a chord in Sister Anselm, reminding her of a case she worked years before when another young girl wasn?t so lucky. Meanwhile, married life agrees with Ali. But any hopes that she and her husband, B. Simpson, will finally slow down and relax now that they?ve tied the knot are dashed when Ali?s new daughter-in-law approaches her, desperate for help. The girl?s grandmother, Betsy, is in danger: she?s been receiving anonymous threats, and someone even broke into her home and turned on the gas burners in the middle of the night. But the local police think the elderly woman?s just not as sharp as she used to be. While Ali struggles to find a way to protect Betsy before it?s too late, Sister Anselm needs her help as well, and the two race the clock to uncover the secrets that The Family has hidden for so long?before someone comes back to bury them forever. '''),
(100, '446677418', 'Absolute Power', 'Grand Central Publishing', '2001-06-15 00:00:00', 528, '7.99', '7.99', '7.99', '2016-01-30 00:00:00', b'1', '0.0', '''Casting the president of the United States as a crazed villain isn''t a new idea?Fletcher Knebel worked it 30 years ago, in Night of Camp David?but in this sizzler of a first novel, Baldacci, a D.C. attorney, proves that the premise still has long legs. The action begins when a grizzled professional cat burglar gets trapped inside the bedroom closet of one of the world''s richest men, only to witness, through a one-way mirror, two Secret Service agents kill the billionaire''s trampy young wife as she tries to fight off the drunken sexual advances of the nation''s chief executive. Running for his life, but not before he picks up a bloodstained letter opener that puts the president at the scene of the crime, the burglar becomes the target of a clandestine manhunt orchestrated by leading members of the executive branch. Meanwhile, Jack Graham, once a public defender and now a high-powered corporate attorney, gets drawn into the case because the on-the-lam burglar just happens to be the father of his former financee, a crusading Virginia prosecutor. Embroidering the narrative through assorted plot whorls are the hero''s broken romance,  his conflict over selling out for financial success,  the prosecutor''s confused love-hate for her burglar father,  the relentless investigation by a northern Virginia career cop,  the dilemma of government agents trapped in a moral catch-22,  the amoral ambitions of a sexy White House Chief of Staff,  and the old burglar''s determination to bring down the ruthless president. Meanwhile, lurking at the novel''s center like a venomous spider is the sociopathic president. Baldacci doesn''t peer too deeply into his characters'' souls, and his prose is merely functional?in both respects, he''s much closer to Grisham than to, say, Forsyth,  but he''s also a first-rate storyteller who grabs readers by their lapels right away and won''t let go until they''ve finished his enthralling yarn.'''),
(101, 'B00PFXLEMK', 'Woman with a Secret', 'William Morrow', '2015-08-04 00:00:00', 389, '0.99', '0.99', '0.99', '2016-01-30 00:00:00', b'1', '0.0', '''She''s a wife. She''s a mother. She isn''t who you think she is. Nicki Clements has secrets, just like anybody else?secrets she keeps from her children, from her husband, from everyone who knows her. Secrets she shares with only one person: A stranger she''s never seen. A person whose voice she''s never heard. And then Nicki is arrested for murder. The murder of a man she doesn''t know. As a pair of husband-and-wife detectives investigate her every word, and as the media circle like sharks, all Nicki''s secrets are laid bare?illusions and deceptions that she has kept up for years. And even the truth might not be enough to save her. For although Nicki isn''t guilty of homicide, she''s far from innocent. . . .'''),
(102, 'B00H3QQGV6', 'Ready to Kill (The Nathan McBride Series Book 4)', 'Thomas & Mercer', '2014-04-29 00:00:00', 348, '7.26', '7.26', '7.26', '2016-01-30 00:00:00', b'1', '0.0', '''When a mysterious note referencing a top-secret US operation is tossed over the wall of the embassy in Nicaragua, Nathan McBride and his old pal Harv are called out of retirement by CIA Director Rebecca Cantrell and sent to Central America. Cantrell wants the situation resolved quickly and knows that Nathan is the man to do it,  after all, he has a history with the place. The jungle he and Harv are about to land in is the same one that Nathan barely escaped with his life decades before, an ordeal that left him physically and psychologically scarred. To make it out alive a second time, Nathan will have to face down his own demons and square off with a ruthless killer who learned from the best, Nathan himself. '''),
(103, 'B00R04MDAE', 'Make Me', 'Delacorte Press', '2015-09-08 00:00:00', 417, '15.99', '15.99', '15.99', '2016-01-30 00:00:00', b'1', '0.0', '''?Why is this town called Mother?s Rest?? That?s all Reacher wants to know. But no one will tell him. It?s a tiny place hidden in a thousand square miles of wheat fields, with a railroad stop, and sullen and watchful people, and a worried woman named Michelle Chang, who mistakes him for someone else: her missing partner in a private investigation she thinks must have started small and then turned lethal. Reacher has no particular place to go, and all the time in the world to get there, and there?s something about Chang . . . so he teams up with her and starts to ask around. He thinks: How bad can this thing be? But before long he?s plunged into a desperate race through LA, Chicago, Phoenix, and San Francisco, and through the hidden parts of the internet, up against thugs and assassins every step of the way?right back to where he started, in Mother?s Rest, where he must confront the worst nightmare he could imagine. Walking away would have been easier. But as always, Reacher?s rule is: If you want me to stop, you?re going to have to make me.''');


INSERT INTO `book_author` (`book_id`, `author_id`) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10),
(11, 11),
(12, 12),
(12, 13),
(12, 14),
(13, 15),
(14, 16),
(15, 17),
(16, 18),
(17, 19),
(17, 20),
(18, 21),
(18, 22),
(19, 23),
(19, 24),
(20, 25),
(21, 26),
(22, 27),
(33, 27),
(22, 28),
(23, 29),
(24, 30),
(25, 31),
(26, 32),
(27, 33),
(28, 34),
(29, 35),
(29, 36),
(30, 145),
(31, 37),
(32, 38),
(35, 38),
(22, 39),
(33, 39),
(34, 40),
(32, 41),
(35, 41),
(36, 42),
(37, 43),
(38, 44),
(39, 45),
(40, 46),
(41, 47),
(41, 48),
(42, 49),
(42, 50),
(42, 51),
(43, 52),
(43, 53),
(44, 54),
(44, 55),
(44, 56),
(45, 57),
(45, 58),
(45, 59),
(46, 60),
(47, 60),
(46, 61),
(47, 61),
(46, 62),
(47, 62),
(48, 63),
(49, 63),
(48, 64),
(49, 65),
(50, 66),
(50, 67),
(50, 68),
(51, 69),
(52, 69),
(51, 70),
(52, 70),
(53, 71),
(54, 71),
(53, 72),
(54, 72),
(55, 73),
(55, 74),
(55, 75),
(56, 76),
(56, 77),
(57, 78),
(58, 78),
(57, 79),
(58, 79),
(58, 80),
(59, 81),
(59, 82),
(59, 83),
(60, 84),
(60, 85),
(61, 86),
(62, 87),
(75, 87),
(62, 88),
(75, 88),
(63, 89),
(64, 90),
(65, 91),
(65, 92),
(65, 93),
(65, 94),
(65, 95),
(66, 96),
(67, 97),
(68, 98),
(69, 99),
(70, 100),
(71, 101),
(72, 102),
(72, 103),
(72, 104),
(72, 105),
(72, 106),
(72, 107),
(72, 108),
(72, 109),
(72, 110),
(72, 111),
(72, 112),
(72, 113),
(72, 114),
(73, 115),
(74, 116),
(76, 117),
(77, 118),
(78, 119),
(78, 120),
(79, 121),
(80, 122),
(81, 123),
(82, 124),
(83, 125),
(84, 126),
(85, 127),
(86, 128),
(87, 129),
(88, 130),
(89, 131),
(90, 132),
(91, 133),
(92, 134),
(93, 135),
(98, 135),
(94, 136),
(95, 137),
(96, 138),
(97, 139),
(99, 140),
(100, 141),
(101, 142),
(102, 143),
(103, 144);

INSERT INTO `format` (`format_id`, `extension`) VALUES
(2, '.cbr'),
(3, '.chm'),
(4, '.docx'),
(1, '.epub'),
(9, '.html'),
(5, '.ibooks'),
(6, '.mobi'),
(10, '.rtf'),
(11, '.txt'),
(8, 'pdb'),
(7, 'pdf');

INSERT INTO `book_format` (`book_id`, `format_id`) VALUES
(21, 2),
(22, 2),
(23, 2),
(24, 2),
(25, 2),
(26, 2),
(27, 2),
(28, 2),
(29, 2),
(30, 2),
(31, 2),
(32, 2),
(33, 2),
(34, 2),
(35, 2),
(36, 2),
(37, 2),
(38, 2),
(39, 2),
(40, 2),
(41, 2),
(1, 3),
(3, 3),
(5, 3),
(7, 3),
(8, 3),
(13, 3),
(16, 3),
(17, 3),
(21, 3),
(23, 3),
(24, 3),
(26, 3),
(28, 3),
(31, 3),
(33, 3),
(35, 3),
(36, 3),
(38, 3),
(39, 3),
(42, 3),
(43, 3),
(45, 3),
(47, 3),
(49, 3),
(52, 3),
(53, 3),
(54, 3),
(56, 3),
(58, 3),
(59, 3),
(62, 3),
(63, 3),
(64, 3),
(65, 3),
(66, 3),
(67, 3),
(69, 3),
(71, 3),
(72, 3),
(73, 3),
(74, 3),
(78, 3),
(82, 3),
(84, 3),
(85, 3),
(86, 3),
(87, 3),
(90, 3),
(93, 3),
(94, 3),
(96, 3),
(97, 3),
(100, 3),
(102, 3),
(1, 4),
(2, 4),
(3, 4),
(5, 4),
(6, 4),
(7, 4),
(8, 4),
(11, 4),
(12, 4),
(15, 4),
(17, 4),
(18, 4),
(19, 4),
(22, 4),
(25, 4),
(27, 4),
(28, 4),
(30, 4),
(31, 4),
(41, 4),
(42, 4),
(43, 4),
(44, 4),
(45, 4),
(47, 4),
(48, 4),
(49, 4),
(50, 4),
(51, 4),
(54, 4),
(56, 4),
(57, 4),
(58, 4),
(59, 4),
(60, 4),
(62, 4),
(63, 4),
(67, 4),
(69, 4),
(70, 4),
(74, 4),
(75, 4),
(76, 4),
(77, 4),
(78, 4),
(79, 4),
(81, 4),
(82, 4),
(83, 4),
(89, 4),
(92, 4),
(93, 4),
(98, 4),
(99, 4),
(101, 4),
(102, 4),
(103, 4),
(3, 5),
(4, 5),
(5, 5),
(7, 5),
(8, 5),
(9, 5),
(10, 5),
(11, 5),
(12, 5),
(19, 5),
(20, 5),
(21, 5),
(22, 5),
(23, 5),
(27, 5),
(28, 5),
(30, 5),
(31, 5),
(33, 5),
(35, 5),
(36, 5),
(40, 5),
(43, 5),
(44, 5),
(47, 5),
(49, 5),
(52, 5),
(54, 5),
(55, 5),
(57, 5),
(58, 5),
(61, 5),
(63, 5),
(64, 5),
(66, 5),
(68, 5),
(70, 5),
(71, 5),
(73, 5),
(75, 5),
(76, 5),
(77, 5),
(78, 5),
(79, 5),
(80, 5),
(81, 5),
(82, 5),
(83, 5),
(84, 5),
(85, 5),
(86, 5),
(87, 5),
(88, 5),
(91, 5),
(92, 5),
(93, 5),
(94, 5),
(95, 5),
(96, 5),
(98, 5),
(99, 5),
(100, 5),
(2, 6),
(3, 6),
(9, 6),
(10, 6),
(13, 6),
(14, 6),
(15, 6),
(17, 6),
(19, 6),
(20, 6),
(22, 6),
(23, 6),
(26, 6),
(29, 6),
(30, 6),
(32, 6),
(33, 6),
(34, 6),
(35, 6),
(36, 6),
(37, 6),
(41, 6),
(42, 6),
(43, 6),
(45, 6),
(46, 6),
(50, 6),
(51, 6),
(52, 6),
(53, 6),
(54, 6),
(55, 6),
(57, 6),
(59, 6),
(60, 6),
(61, 6),
(63, 6),
(64, 6),
(66, 6),
(68, 6),
(69, 6),
(71, 6),
(72, 6),
(75, 6),
(76, 6),
(79, 6),
(80, 6),
(81, 6),
(82, 6),
(83, 6),
(84, 6),
(85, 6),
(89, 6),
(90, 6),
(95, 6),
(96, 6),
(97, 6),
(98, 6),
(100, 6),
(101, 6),
(2, 7),
(18, 7),
(19, 7),
(46, 7),
(48, 7),
(50, 7),
(52, 7),
(91, 7),
(92, 7),
(93, 7),
(96, 7),
(98, 7),
(2, 8),
(53, 8),
(55, 8),
(58, 8),
(59, 8),
(65, 8),
(101, 8),
(103, 8),
(2, 9),
(4, 9),
(6, 9),
(7, 9),
(8, 9),
(20, 9),
(30, 9),
(66, 9),
(67, 9),
(68, 9),
(69, 9),
(71, 9),
(10, 10),
(11, 10),
(12, 10),
(14, 10),
(18, 10),
(30, 10),
(74, 10),
(78, 10),
(79, 10),
(81, 10),
(100, 10),
(15, 11),
(16, 11),
(17, 11),
(84, 11),
(86, 11),
(88, 11),
(89, 11),
(90, 11),
(100, 11);

INSERT INTO `genre` (`genre_id`, `genre_name`) VALUES
(1, 'Computers & Technology'),
(2, 'Travel'),
(3, 'Comics & Graphic Novels'),
(4, 'Religion & Spirituality'),
(5, 'Mystery, Thriller & Suspense');

INSERT INTO `book_genre` (`book_id`, `genre_id`) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(11, 1),
(12, 1),
(13, 1),
(14, 1),
(15, 1),
(16, 1),
(17, 1),
(18, 1),
(19, 1),
(20, 1),
(21, 2),
(22, 2),
(23, 2),
(24, 2),
(25, 2),
(26, 2),
(27, 2),
(28, 2),
(29, 2),
(30, 2),
(31, 2),
(32, 2),
(33, 2),
(34, 2),
(35, 2),
(36, 2),
(37, 2),
(38, 2),
(39, 2),
(40, 2),
(41, 3),
(42, 3),
(43, 3),
(44, 3),
(45, 3),
(46, 3),
(47, 3),
(48, 3),
(49, 3),
(50, 3),
(51, 3),
(52, 3),
(53, 3),
(54, 3),
(55, 3),
(56, 3),
(57, 3),
(58, 3),
(59, 3),
(60, 3),
(61, 4),
(62, 4),
(63, 4),
(64, 4),
(65, 4),
(66, 4),
(67, 4),
(68, 4),
(69, 4),
(70, 4),
(71, 4),
(72, 4),
(73, 4),
(74, 4),
(75, 4),
(76, 4),
(77, 4),
(78, 4),
(79, 4),
(80, 4),
(81, 4),
(82, 4),
(83, 4),
(84, 5),
(85, 5),
(86, 5),
(87, 5),
(88, 5),
(89, 5),
(90, 5),
(91, 5),
(92, 5),
(93, 5),
(94, 5),
(95, 5),
(96, 5),
(97, 5),
(98, 5),
(99, 5),
(100, 5),
(101, 5),
(102, 5),
(103, 5);






INSERT INTO `invoice` (`invoice_id`, `sale_date`, `user_id`, `total_net_value_of_sale`, `total_gross_value_of_sale`) VALUES
(1, '2016-02-01 00:00:00', 1, '16.31', '18.45'),
(2, '2016-02-02 00:00:00', 2, '20.56', '24.12'),
(3, '2016-02-03 00:00:00', 3, '48.87', '62.12'),
(4, '2016-02-04 00:00:00', 4, '20.56', '24.12'),
(5, '2016-02-05 00:00:00', 5, '8.74', '9.81'),
(6, '2016-02-06 00:00:00', 6, '9.99', '13.28');

INSERT INTO `invoice_detail` (`invoice_detail_id`, `invoice_id`, `book_id`, `pst`, `gst`, `hst`, `book_price`, `quantity`) VALUES
(1, 1, 76, '1.63', '0.82', '0.00', '16.31', 1),
(2, 2, 77, '2.05', '1.03', '0.00', '20.56', 1),
(3, 3, 78, '4.87', '2.44', '0.00', '16.29', 1),
(4, 4, 77, '2.05', '1.03', '0.00', '20.56', 1),
(5, 5, 80, '0.87', '0.44', '0.00', '8.74', 1);


INSERT INTO `province` (`province_id`, `province`, `pst`, `gst`, `hst`) VALUES
(1, 'Quebec', '9.98', '5.00', '0.00'),
(2, 'Ontario', '0.00', '5.00', '8.00'),
(3, 'New Brunswick', '0.00', '5.00', '8.00'),
(4, 'Nova Scotia', '0.00', '5.00', '10.00'),
(5, 'Prince Edward Island', '0.00', '5.00', '9.00'),
(6, 'Newfoundland and Labrador', '0.00', '5.00', '8.00'),
(7, 'Manitoba', '8.00', '5.00', '0.00'),
(8, 'Saskatchewan', '5.00', '5.00', '0.00'),
(9, 'Alberta', '0.00', '5.00', '0.00'),
(10, 'British Columbia', '7.00', '5.00', '0.00'),
(11, 'Yukon', '0.00', '5.00', '0.00'),
(12, 'Northwest Territories', '0.00', '5.00', '0.00'),
(13, 'Nunavut', '0.00', '5.00', '0.00');

INSERT INTO `survey` (`survey_id`, `question`, `answer_one`, `answer_two`, `answer_three`, `answer_default`, `active`) VALUES
(1, 'What is your favorite type of book?', 'Electronic copy', 'Hard Cover', 'Paperback', 'Something else', true),
(2, 'Have you ever written a book?', 'Yes atleast one', 'No never', 'Im writing one', 'It''s a secret', false),
(3, 'What is the most you have ever paid for a book?', 'Atleast 100', 'Atleast 50', 'Atleast 5', 'I never pay for books', false),
(4, 'Are you reading a book now?', 'Yes', 'No', 'Does a blog count', 'Define the term book', false),
(5, 'Do you keep a reading journal/list?', 'Yes its full', 'Yes but its empty', 'No', 'I want to', false),
(6, 'Is there anything in your shopping cart now?', 'Yes im buying stuff', 'No I will buy stuff soon', 'I already bought a book', 'This is a trick to get me to buy books', false);


INSERT INTO `title` (`title_id`, `title`) VALUES
(1, 'Mr.'),
(2, 'Ms.'),
(3, 'Mrs.'),
(4, 'Miss.'),
(5, 'Dr.');

INSERT INTO `registered_user` (`user_id`, `title_id`, `last_name`, `first_name`, `company_name`, `address_one`, `address_two`, `city`, `province_id`, `country`, `postal_code`, `home_phone`, `cell_phone`, `email_address`, `password`, `manager`, `active`) VALUES
(1, 1, 'Cummerata', 'Felicia', 'Dawson College', '3456 Sherbrooke west', NULL, 'Montreal', 1, 'Canada', 'H4B1N1', '514-123-1234', '514-235-8924', 'abc123@gmail.com', 'abc123', false, true),
(2, 3, 'Grayce', 'Grayce', 'Mcgill University', '5658 Fielding', NULL, 'Montreal ', 1, 'Canada', 'H3F2N4', '514-369-5689', '514-566-7415', 'cde456@gmail.com', 'cde456', false, true),
(3, 2, 'Lucinda', 'Hintz', 'Concrodia University', '5689 Newman', NULL, 'Lasalle', 1, 'Canada', 'H2T4N8', NULL, '514-569-7777', 'fgh111@hotmail.com', 'fgh111', false, true),
(4, 5, 'Jamal', 'Hartmann', 'IBM', '8996 Milan', NULL, 'Brossade', 1, 'Canada', 'H5J2N2', NULL, '514-996-4452', 'jik345@facebook.com', 'jik345', false, true),
(5, 5, 'Brennan', 'Muller', 'HP', '896  Avenue Wood', NULL, 'Montreal ', 1, 'Canada', 'H8G9D3', '514-566-8912', NULL, 'lop999@cloud.com', 'lop999', false, true),
(6, 1, 'Christodoulopoulos', 'Dimitri', '-', '123 Maple Street', '-', 'Montreal', 1, 'Canada', 'H8E 4Y2', '(514)-234-3521', '(514)-938-1345', 'dchristodoulopoulos@gmail.com', 'chrissocool1', false, true),
(7, 1, 'Gallan', 'Chris', '-', '253 Cobalt Drive', '-', 'Vancouver', 10, 'Canada', 'R3N 3G9', '(604)-499-2495', '-', 'christhegamer@outlook.com', 'v1d30gam35', false, true),
(8, 2, 'Flemming', 'Jessica', '-', '1080 Belview Road', '-', 'Toronto', 2, 'Canada', 'Y9F 8T8', '(416)-136-1245', '(416)-889-8889', 'jessicaflemming@yahoo.com', 'flem', false, true),
(9, 1, 'Terry', 'George', 'Terry Inc.', '45 Bell Air Street', '-', 'Ottowa', 2, 'Canada', 'W1T 3R4', '-', '(343)-535-7647', 'terryincorporated@hotmail.com', 'terryforpres', false, true),
(10, 1, 'Draxler', 'Julian', '-', '974 Compton Drive', '834 Emirate Road', 'Edmonton', 9, 'Canada', 'Q4R 3E2', '(780)-123-5553', '(780)-484-3682', 'soccerfan1000@gmail.com', 'aresenal123', false, true),
(11, 2, 'MacKinnon', 'Michelle', 'York University', '4700 Keele St', NULL, 'Toronto', 1, 'Canada', ' M3J 1P3', '416 -736-2100', '416-736-5177', 'michelle.mackinnon@yorku.ca', 'lolipop626', false, true),
(12, 1, 'Fortin', 'Alexandre', 'Remax', '11104 Montée Sainte-Marianne', NULL, 'Mirabel', 1, 'Canada', 'J7J 2N4', '450-475-0911', '514-549-9468', 'alexandre.fortin@remaxquebec.com', 'downbutnotout23', false, true),
(13, 5, 'Forlini', 'Anna', 'Vancouver General', '899 W 12th Ave', NULL, 'Vancouver', 10, 'Canada', 'V5Z 1M9', '604-875-4111', '604-875-4073', 'anna.forlini@vgh.ca', 'nevergoingtogiveyouup12', false, true),
(14, 1, 'Bingham', 'Richard', 'Canadian Forces', '3rd Canadian Division Support Base Edmonton', 'PO Box 10500 Station Forces ', 'Edmonton', 9, 'Canada', 'T5J 4J5', '780-973-4011', NULL, 'richard_bingham@caf.ca', 'pastwords', false, true),
(15, 1, 'Dufort', 'Christopher', 'Dawson College', '3040 Rue Sherbrooke O', '4001 de Maisonneuve West', 'Montreal', 1, 'Canada', 'H3Z 1A4', '514-931-8731', '514 933-1234', 'christopher.dufort@dawsoncollege.qc.ca', 'password', true, true),
(16, 1, 'Campanelli', 'Giuseppe', 'Dawson College', '3040 Rue Sherbrooke O', '4001 de Maisonneuve West', 'Montreal', 1, 'Canada', 'H3Z 1A4', '514-931-8731', '514 933-1234', 'acmilanfanforever@gmail.com', 'password', true, true),
(17 ,1, 'Lazaar', 'Rita', 'Dawson College', '3040 Rue Sherbrooke O', '4001 de Maisonneuve West', 'Montreal', 1, 'Canada', 'H3Z 1A4', '514-931-8731', '514 933-1234', 'rita.lazr@gmail.com', 'password', true, true),
(18, 1, 'Ma', 'Xin', 'Dawson College', '3040 Rue Sherbrooke O', '4001 de Maisonneuve West', 'Montreal', 1, 'Canada', 'H3Z 1A4', '514-931-8731', '514 933-1234', 'maxin911.ca@gmail.com', 'password', true, true),
(19, 1, 'Xin', 'Ma', 'None', '4323, rue Sainte Catherine', NULL, 'Montreal', 1, 'Canada', 'HZ2 323', '(514) 436 4345', '+34644334455', 'jsmith@gmail.com', 'jcool', false, true),
(20, 4, 'Lam', 'Charlotte', 'None', '634, rue Maisonneuve', NULL, 'Montreal', 1, 'Canada', 'H3Z 233', '(514) 323 4223', '+34345343445', 'clam@gmail.com', 'clam man', false, true),
(21, 1, 'Martin', 'Pierre', 'Couette Corporation', '3234, rue Ontario E', NULL, 'Montreal', 1, 'Canada', 'H5Z 232', '(514) 323 3243', '+34545663453', 'pmartin@gmail.com', 'the martian', false, true),
(22, 1, 'Faure', 'Jonas', 'None', '8, rue Saint Lucien', NULL, 'Ottawa',2, 'Canada', '60000', '+33344543532', '+33744334455', 'jonas.faure.etu@gmail.com', 'password', true, true),
(23, 4, 'Roy', 'Charles', 'JCDZ', '1, Air India Road', NULL, 'Toronto', 2, 'Canada', '400032', '+64343433442', '+67653546434', 'petitchatontropmignon@gmail.com', 'petitpassword', false, true),
(24, 1, 'Consumer', 'Dawson', 'Dawson College', '3040 Rue Sherbrooke O', NULL, 'Montreal', 1, 'Canada', 'H3Z 1A4', '514-931-8731', '514 933-1234', 'cst.send@gmail.com', 'dawsoncollege', false, true),
(25, 1, 'Manager', 'Dawson', 'Dawson College', '3040 Rue Sherbrooke O', NULL, 'Montreal', 1, 'Canada', 'H3Z 1A4', '514-931-8731', '514 933-1234', 'cst.receive@gmail.com', 'collegedawson', true, true),
(26, 1, 'Crowne', 'Michael', 'FutureShop', '123 Something', '—', 'Montreal', 1, 'Canada', 'H3H3O0', '514-235-5435', '514-235-5423', 'abc@gmail.com', '123456', false, true),
(27, 1, 'McLaughlin', 'John', 'BestBuy', '24454 Where St.', '—', 'Montreal', 1, 'Canada', 'K3N3X6', '514-534-5365', '514-534-5332', 'something@gmail.com', '123456abc', false, true),
(28, 1, 'Tremblay', 'Matthew', '—', '43 Amazing St.', '—', 'Montreal', 1, 'Canada', 'M4N6L3', '514-978-4565', '514-978-4545', 'email1@gmail.com', '123abc456', false, true),
(29, 3, 'Cartwright', 'Sylvia', '—', '98 County St.', '432 Bob St.', 'Quebec', 1, 'Canada', 'M3M5FK', '418-543-6787', '418-543-6487', 'rew@gmail.com', 'something1', false, true),
(30, 3, 'Shackleton', 'Ashley', 'Dawson College', '234 NewAddress St.', '—', 'Quebec', 1, 'Canada', 'H1S1R9', '418-434-6542', '418-434-6122', 'ash_ley@gmail.com', 'dawsoncollege', false, true),
(31, 1, 'Account', 'Purchase', 'Dawson College', '1234 main street.', '—', 'Montreal', 1, 'Canada', 'H2S1R7', '514-123-4569', '514-123-4567', 'bibliotech.receive@gmail.com', 'sofa3brick', false, true);

INSERT INTO review (isbn, date_submitted, user_id, rating, approval_id, review_title, review_text) VALUES
('978-0134308135', '2015-12-24', '1', '4', '1', '... PC Kindle to use for a class and was easy enough to follow along', 'Purchased this book for my PC Kindle to use for a class and was easy enough to follow along. However I will admit I had to reread some sections as it seemed I would miss a key phrase that would throw off the whole section if not done. Lesson learned, use on regular Kindle instead of trying to bounce back and forth. The only thing that would have made it better was a downloadable cheat sheet of the keyboard codes. So often I would have to try to remember that holding two keys would do an action versus using the mouse to find the tool to click on it. That being said, if you have never used Photoshop CC or need a serious refresher this book should be of help. Hoping that they come out with other additions to keep the skills I have learned fresh as a way of practicing while working to finish my degree.'),
('978-0134308135', '2015-11-05', '1', '2', '1', 'Identical to 2014 Edition at Twice the Price', 'Failure to disclose that this book is identical in every way to the 2014 release, which can be bought a lot cheaper. The new updates to CC 2015 are not included in this edition. The illustrations are identical to the 2014 edition as well. Adobe should be more transparent. This ended up as a waste of time, and I returned to the Book to Amazon, since I have the 2014 edition.'),
('978-0134308135', '2015-11-06', '2', '4', '1', 'Buy the book', 'Its a basic Photoshop book which gave me the foundation to move forward. I use it as a reference and a refresher course. The course is well worth the money.'),
('978-1449374044', '2015-08-02', '1', '5', '1', 'Une référence pour toute personne qui désire découvrir le pourquoi et comment de bitcoin.', 'Certains chapitres rentrent en détail dans le fonctionnement du protocole et peuvent être dur à suivre pour un novice. Heureusement il n’est pas indispensable de comprendre tout à 100% pour avoir une bonne idée de comment cette crypto monnaie fonctionne et quel est son potentiel pour révolutionner le monde de la finance et l’interne'),
('978-1449374044', '2015-02-21', '1', '3', '1', 'Very good for the newcomers to the electronic currencies out there', 'Helpful information for the regular bitcoin enthusiast. Hard core users usually know more than the book contents .. it''s hard to come out with a book for geeks, telling them something new in a domain they''re into.'),
('978-1449320102', '2012-12-16', '3', '5', '1', 'Very comprehensive reference manual', 'I am an experienced Java developer, but I haven''t used the .NET languages and tools yet. I purchased this book to get familiar with what C# provides. I hadn''t noticed that the thing is 1000+ pages, so it isn''t so convenient to put in my bag to read on the commute to work. The content is great so far. I''m slowly reading it from start to finish because I want to get a good overview of C#''s features, however, I think it would be a great reference book for someone who already has C# experience. It does a good job of specifying what version of C# introduced each feature.'),
('978-1449320102', '2013-04-07', '1', '5', '1', 'Best C# Reference', 'This is the best reference on C# that I have seen. I purchased the C# pocket reference from same author. I have enjoyed the explanations in this book. Kudos to the author. Beginners should use this book as a follow up once they master the fundamentals of C#.'),
('978-1119038597', '2015-09-22', '1', '4', '1', 'As a senior "dummy, " I need all the help I can get', 'I was not too sure about this initially, but it is beyond my expectations. The font being larger is great, and the wording is very simple.'),
('978-0132855839', '2013-02-15', '5', '5', '1', 'Needed for class', 'This was the book prescribed by my professor and it is an excellent source for learning Java, especially for beginners.'),
('978-0132855839', '2013-02-26', '1', '4', '1', 'Great book for the beginner!', 'This book is a really good book. It is very well organized and easy to understand. This a good starting point to learn Java. I was nervous about learning Java but after all Java is not difficult and this book makes the difference.'),
('978-0321573513', '2013-03-18', '1', '4', '1', 'Livre de référence', 'Il s''agit d''un ouvrage de référence, très complet. Il couvre de à peu près tous les sujets liés à l''algorithmique, propose des exercies pour entrer dans les détails. Je regrette jusqte qu''il soit moins drôle à lire que les ouvrages de la collection _Head fist on_ te que les exercices ne soient pas corrigés (il y a parfois des indications)'),
('978-1101902639', '2015-01-01', '1', '4', '2', 'Helped me a lot…', 'Helped me a lot, I no longer want to kill myself, so much happyness in my life now it''s awesome I can''t resist tell you people that this book can reaaaaaaaaally change your life !'),
('978-1582701707', '2015-01-02', '2', '1', '3', 'Crap', 'Crap. No worthy a penny.'),
('978-0451205360', '2015-01-03', '1', '3', '1', 'Not so bad.', 'Not so bad. Makes me want to travel a bit, discover new thing. It really puts thing into perspective. But it''s still poorly written so only 3 stars !'),
('978-0785193760', '2016-01-12', '1', '2', '1', 'Dissapointed.', 'Dissapointed with the appointment of a new Captain America.'),
('978-1421585642', '2015-12-13', '5', '4', '1', 'Great.', 'Great manga! Just as good as the show.'),
('978-1421585642', '2015-11-22', '1', '5', '1', 'Get it!', 'Just got into this type of manga and love it!'),
('978-1401258566', '2015-10-02', '1', '4', '1', 'Great Book!', 'Great book! Can''t wait for the movie!'),
('978-1401258566', '2015-07-14', '1', '4', '1', 'Very Pleased..', 'Very pleased with the action is in book.'),
('978-1401258566', '2016-01-20', '1', '5', '1', 'Best..', 'Couldn''t have asked for anything better!'),
('978-1632154026', '2015-06-21', '1', '3', '1', 'Good..', 'Good but the TV show is better.'),
('978-0785121794', '2015-08-11', '1', '5', '1', 'Stocked..', 'Stoked for the movie after reading this!'),
('978-0785121794', '2016-01-06', '1', '2', '1', 'Meh.', 'Not a big fan of the Civil War storyline.'),
('978-1405924092', '2016-01-16', '1', '5', '1', 'I bought 10..', 'I bought 10 of these to give as gifts after meeting the artist, Lizzie Mary Cullen, on an airplane. All of the recipients are LOVING their books! The artistry is really lovely and as an avid traveller, I love that all of the pictures are of inspiring cities with fabulous details that are fun to "find" as you colour. '),
('978-1405924092', '2016-01-16', '1', '2', '2', 'Not what I expected..', 'Not what I expected, fun but childish '),
('978-0679600213', '2002-08-08', '1', '5', '1', '5 stars', 'Isak Dinesen (Karen Blixen) has been elevated to star status by the feminists for her independent stance and courage, but don''t read this book because of that. Don''t look for the tragic story of her misguided marriage and the heartbreak and barrenness it brought her, or for descriptions of her love affair with adventurer Denys Finch-Hatton. None of that appears here. Instead, "Out of Africa" is a storytelling book woven in the imaginative Danish style. Dinesen''s finely tuned sensitivity is revealed here, as well as her (again typically Danish) well-developed gift for friendship with many kinds of people. In her case this gift extends to African animals as well, like Lulu, the beautiful gazelle who graced her plantation for years. Her descriptions of the Kenya of her day are exquisitely written, factual and magical at the same time. Africa is the star of the book, not Dinesen herself, not the tribespeople or the colonials, not her struggles with raising coffee in land "a little too high", nor her political dealings with the government officials. Her writing evokes the Africa she knew well and loved deeply.'),
('978-0679600213', '2003-05-08', '1', '4', '1', '4 stars', 'Isak Dinesen''s novel Out of Africa is a recollection of her time spent in Africa while struggling to cope with the immensely different cultures and struggling to run a coffee farm at too high of an altitude. This book is a collection of her stories most of them about her adventures shared with lover Denys Finch-Hatton. Many of the stories are very dangerous, like when they go lion hunting. These stories show the wild side that Dinesen posses. These stories are in no chronological order and at times make the book hard to follow. The best part of the book is the astounding imagery used. The imagery describes the breathtaking views from the on top the Ngong hills and allows you to feel the lack of oxygen, smell the coffee plants and feel the strong African sun beating down upon your skin. The down side to this book is, even after experiencing many adventurers with Dinesen you will probably feel that you do not know much about her personality. This is due to lack of character development since she is telling the story and never describes herself. You do however learn about the struggle she faces being a European woman living in a minority, in a place with very different and diverse cultures. She has to adapt to these cultures and even though she finds her European traditions very different from those of the Africans, she realizes that there is some common ground between the two. Even though this book can be at times hard to follow I highly recommend reading it. The magnificent imagery makes up for the down sides to the book and causes you to realize why Dinesen fell in love with Africa. You will probably find yourself falling in live with Africa and its people just as Dinesen did. A truly remarkable book. '),
('978-0679600213', '2001-12-31', '1', '5', '1', 'After reading this book..', 'After reading this book, Hemingway said in an interview that Dinesen is more deserving of the Nobel prize. It should be remembered that this remark did not come from a modest man, but from someone who was fond of talking about beating Tolstoy in the ring, having defeated Stendhal. Nor, for that matter, was Hemingway known for respecting women.But being a learned and disciplined writer,Hemingway was after all able to appreciate good stuff when he saw it. Literary excellence is rare indeed, and here, in this book, you have it in the unadulterated form. Dinesen undoubtedly had something to say, but more importantly the means-- or should I say the genius-- to say it. Out of Africa would do very well as a textbook of English prose. Now in some of the other review I found words like "colonial," "racist," "conservationist," and so on. Of course, the reader should not be distracted by these words, but read the book first and form her independent opinion. Meanwhile, my opinion, clearly personal and subjective and limited by my time and place and social class and sex (oops,i mean gender) and whatever you''d like, is that these reviewers don''t know what they are talking about. So buy this book and forget about them. Or if you don''t want to take the risk, borrow it from the library first. Then you''ll want to buy it.'),
('978-0761163800', '2013-03-14', '1', '5', '1', 'Bought for my 3 year old son..', 'We bought this for my 3 year old son who loves animals. He can''t get enough of this book just for the pictures only. There is so much detail written about each animal it gives us tons to talk about. This is a very special book that I''m sure his is going to love even when he is older. '),
('978-0761163800', '2013-12-27', '1', '5', '1', 'Great Value!', 'I saw this in a retail store (selling for a ridiculous price) so I purchased it on Amazon and I''m sure glad I did. What a fascinating animated book. It enthralled everyone all night. I would highly recommend this book as a gift for any child who likes animals. '),
('978-0761163800', '2014-01-05', '1', '5', '1', 'Fantastic', 'What a fantastic book. The pictures are amazing as they come alive before your eyes. The information on each animal is wonderful. I would recommend this book to any animal lover young and old.'),
('978-1612382029', '2014-04-05', '1', '5', '1', 'Good for beginners', 'My French is a little rusty so before I headed to Paris I purchased Rick Steves'' phrase book. I was glad I had it along. It was useful in restaurants to decipher that one word that made all the difference in whether or not I wanted to order something. I like that it was small enough to fit in my pocket. This and Rick Steves'' Paris 2014 are the only books you need for that Paris trip. '),
('978-1612382029', '2014-06-23', '1', '5', '1', 'The best', 'Since my college French has gotten very rusty, I decided to order Steves'' French Phrase Book and Dictionary in preparation for a trip to France later this year. Since the book is divided into categories, it is easy to find the words or phrases one needs to handle whatever situation is at hand. Following each phrase is the pronunciation. He also deals with the colloquial way to say many things and that is a big help. While most people in France do speak English, they always appreciate it when we Americans struggle to use their language and I plan to study this book at length before my trip. '),
('978-1612382029', '2014-06-06', '1', '5', '1', 'The best', 'Rick Steves'' books and advice are simply "the best" books. His pronunciation guide is great. We are using his book as a guide for our French group.'),
('978-0385685719', '2015-11-03', '1', '1', '3', 'Garbage', 'A shockingly uninteresting read. Bryson starts out by establishing the longest straight line one can traverse through Great Britain without crossing the sea, dubs it "the Bryson Line" and suggests he will more or less follow this course, then proceeds to do nothing of the sort. By halfway through the book he has merely puttered around the south between London and Cornwall, moving essentially perpendicular to his "line". Along the way he moans about how places he has been decades before have not stayed just the way he remembered (even listing specific hotels and restaurants that are no longer there, like this has any bearing on anything). Occasionally he throws in some weak attempts at humour, but the whole thing comes off an a grumpy old man who forgot where he was going and is generally annoyed that the world has dared to move on. To be fair, I will say that I gave up halfway through the book, maybe the last half is better, but I think I gave it a chance to prove itself, and it failed. '),
('978-0385685719', '2015-12-07', '1', '5', '1', 'Enjoyed very much', 'I have read all of Mr. Bryson''s books and enjoyed every one. Great stories, lessons and above all humor. He is a gold mine of trivia but trivia that is informative and interesting. An evening with a Bryson book is well spent. '),
('978-0385685719', '2015-11-22', '1', '4', '1', 'Unusual but wonderful.', 'Bill''s usual humour and wonderful descriptions of English countryside and lives of the people. Hope more of this type are forthcoming. '),
('B00EMXBDMA', '2012-01-25', '1', '5', '1', 'Amazing!', 'Best book I have ever read! Suspenseful, amazingly touching. '),
('B00EMXBDMA', '2012-01-26', '1', '3.5', '1', 'It was okay', 'Not the best book I have read. The end was to obvious, the character was too obnoxious. '),
('B00EMXBDMA', '2012-01-27', '1', '4.1', '1', 'Nice book', 'I was enjoying the whole time I was reading it.'),
('B00LD1OUPQ', '2012-01-28', '1', '1.5', '1', 'Horrible', 'No suspense, no profoundness, no consistency. Nothing. Horrible story.'),
('161109898X', '2012-01-29', '1', '2', '2', 'Not one of the best', 'You know the book is not that good when you rather sleep than read it. '),
('B004CFA9DM', '2012-01-30', '1', '3.8', '1', 'I wished the ending was different', 'I really wished it ended differently. It would have been nice to not expect the ending. To avoid having a good ending. '),
('B00M60RKW8', '2012-01-31', '1', '4.9', '1', 'Almost perfect ! Almost!', 'One of my favorite books. I dont care what everyone else thinks. It was really worth the time I’ve spent on it. ');

INSERT INTO `news_feed` (`news_feed_id`, `news_feed_link`, `active`) VALUES
(1, 'http://www.bookbrowse.com/rss/book_news.rss', true),
(2, 'http://www.bookbrowse.com/rss/bookbrowse_free_newsletter.rss', false),
(3, 'http://www.bookbrowse.com/rss/newest_reader_reviews.rss', false),
(4, 'http://www.bookbrowse.com/rss/member_ezines.rss', false);
