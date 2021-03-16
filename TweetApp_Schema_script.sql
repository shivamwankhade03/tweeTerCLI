DROP database if exists tweetapp;

--
-- Create databse
--
Create database tweetapp;

--
-- Use databse
--

use tweetapp;
--
-- Table structure for table `user_details`
--

DROP TABLE IF EXISTS `user_details`;

CREATE TABLE `user_details` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `gender` varchar(8) NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`userid`)
) ;

DROP TABLE IF EXISTS `tweets`;

CREATE TABLE `tweets` (
  `tweet_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id_fk` int(11) NOT NULL,
  `tweets` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`tweet_id`),
  KEY `user_id_fk_idx` (`user_id_fk`),
  CONSTRAINT `user_id_fk` FOREIGN KEY (`user_id_fk`) REFERENCES `user_details` (`userid`)
) ;
--
-- Table structure for table `user_details`
--

