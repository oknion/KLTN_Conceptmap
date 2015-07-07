/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.6.21 : Database - springtilesopenshift
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`springtilesopenshift` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `springtilesopenshift`;

/*Table structure for table `ccrelationship` */

DROP TABLE IF EXISTS `ccrelationship`;

CREATE TABLE `ccrelationship` (
  `ccrelaId` int(11) NOT NULL AUTO_INCREMENT,
  `srcCcId` double NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `desCcIdr` double NOT NULL,
  `cmId` int(11) NOT NULL,
  PRIMARY KEY (`ccrelaId`),
  KEY `FK_epnhor4g8agy4hhl4b6loxra7` (`cmId`),
  CONSTRAINT `FK_epnhor4g8agy4hhl4b6loxra7` FOREIGN KEY (`cmId`) REFERENCES `conceptmap` (`cmId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ccrelationship` */

/*Table structure for table `ccrelationship_points` */

DROP TABLE IF EXISTS `ccrelationship_points`;

CREATE TABLE `ccrelationship_points` (
  `ccrelaPointId` int(11) NOT NULL AUTO_INCREMENT,
  `orders` int(11) DEFAULT NULL,
  `points` double DEFAULT NULL,
  `Ccrelationship_ccrelaId` int(11) NOT NULL,
  PRIMARY KEY (`ccrelaPointId`),
  KEY `FK_a5pdkxh86w254tk8l9yhyrlc9` (`Ccrelationship_ccrelaId`),
  CONSTRAINT `FK_a5pdkxh86w254tk8l9yhyrlc9` FOREIGN KEY (`Ccrelationship_ccrelaId`) REFERENCES `ccrelationship` (`ccrelaId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ccrelationship_points` */

/*Table structure for table `classes` */

DROP TABLE IF EXISTS `classes`;

CREATE TABLE `classes` (
  `classId` varchar(10) NOT NULL,
  `className` varchar(50) NOT NULL,
  PRIMARY KEY (`classId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `classes` */

/*Table structure for table `concept` */

DROP TABLE IF EXISTS `concept`;

CREATE TABLE `concept` (
  `ccId` int(11) NOT NULL AUTO_INCREMENT,
  `ccName` varchar(50) DEFAULT NULL,
  `ccText` varchar(100) DEFAULT NULL,
  `keyId` double DEFAULT NULL,
  `loc` varchar(50) DEFAULT NULL,
  `cmID` int(11) NOT NULL,
  PRIMARY KEY (`ccId`),
  KEY `FK_s5b0roor50felb5d8yh74kge9` (`cmID`),
  CONSTRAINT `FK_s5b0roor50felb5d8yh74kge9` FOREIGN KEY (`cmID`) REFERENCES `conceptmap` (`cmId`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

/*Data for the table `concept` */

/*Table structure for table `conceptmap` */

DROP TABLE IF EXISTS `conceptmap`;

CREATE TABLE `conceptmap` (
  `cmId` int(11) NOT NULL AUTO_INCREMENT,
  `cmName` varchar(255) DEFAULT NULL,
  `dateCreate` datetime DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `imgString` longtext,
  `isPublic` bit(1) DEFAULT NULL,
  `lastUpdate` datetime DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `score` smallint(6) DEFAULT NULL,
  `answer4task` int(11) DEFAULT NULL,
  `userId` varchar(50) NOT NULL,
  PRIMARY KEY (`cmId`),
  KEY `FK_nyvnjrcq9ug2pu40k7a7h8f4l` (`answer4task`),
  KEY `FK_4qtn58xo9kx29ibxgob7hb0ha` (`userId`),
  CONSTRAINT `FK_4qtn58xo9kx29ibxgob7hb0ha` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`),
  CONSTRAINT `FK_nyvnjrcq9ug2pu40k7a7h8f4l` FOREIGN KEY (`answer4task`) REFERENCES `task` (`taskId`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Data for the table `conceptmap` */

/*Table structure for table `document` */

DROP TABLE IF EXISTS `document`;

CREATE TABLE `document` (
  `documentId` int(11) NOT NULL AUTO_INCREMENT,
  `docBytes` longblob,
  `documentCcId` int(11) DEFAULT NULL,
  `documentName` varchar(50) DEFAULT NULL,
  `docLength` int(11) DEFAULT NULL,
  `docName` varchar(250) DEFAULT NULL,
  `type` longtext,
  `ccrelaId` int(11) DEFAULT NULL,
  `ownccId` int(11) DEFAULT NULL,
  `errorId` int(11) DEFAULT NULL,
  `s3KeyIdString` varchar(50) DEFAULT NULL,
  `s3BucketId` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`documentId`),
  KEY `FK_hfai6l51pkftynny4d56d07qn` (`ccrelaId`),
  KEY `FK_ocuulv8a2hvg6qfqprvp67t4u` (`ownccId`),
  KEY `FK_th4gqln6woks3303heqp2ll2e` (`errorId`),
  CONSTRAINT `FK_hfai6l51pkftynny4d56d07qn` FOREIGN KEY (`ccrelaId`) REFERENCES `ccrelationship` (`ccrelaId`),
  CONSTRAINT `FK_ocuulv8a2hvg6qfqprvp67t4u` FOREIGN KEY (`ownccId`) REFERENCES `concept` (`ccId`),
  CONSTRAINT `FK_th4gqln6woks3303heqp2ll2e` FOREIGN KEY (`errorId`) REFERENCES `error` (`errId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `document` */

/*Table structure for table `error` */

DROP TABLE IF EXISTS `error`;

CREATE TABLE `error` (
  `errId` int(11) NOT NULL AUTO_INCREMENT,
  `descrip` varchar(255) NOT NULL,
  `name` varchar(50) NOT NULL,
  `ofcmId` int(11) NOT NULL,
  PRIMARY KEY (`errId`),
  KEY `FK_n3flwskatxfefwg0gc42qut43` (`ofcmId`),
  CONSTRAINT `FK_n3flwskatxfefwg0gc42qut43` FOREIGN KEY (`ofcmId`) REFERENCES `conceptmap` (`cmId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `error` */

/*Table structure for table `events` */

DROP TABLE IF EXISTS `events`;

CREATE TABLE `events` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_name` varchar(127) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `userid` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `events` */

/*Table structure for table `friends` */

DROP TABLE IF EXISTS `friends`;

CREATE TABLE `friends` (
  `friendid` int(11) NOT NULL AUTO_INCREMENT,
  `sourceuserid` varchar(50) DEFAULT NULL,
  `desuserid` varchar(50) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`friendid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `friends` */

/*Table structure for table `notification` */

DROP TABLE IF EXISTS `notification`;

CREATE TABLE `notification` (
  `notiId` varchar(10) NOT NULL,
  `content` varchar(255) NOT NULL,
  `dateReceive` datetime NOT NULL,
  `isRead` bit(1) NOT NULL,
  `name` varchar(50) NOT NULL,
  `userId` varchar(50) NOT NULL,
  PRIMARY KEY (`notiId`),
  KEY `FK_3nldamdcafg05e75d76rwnjr2` (`userId`),
  CONSTRAINT `FK_3nldamdcafg05e75d76rwnjr2` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `notification` */

/*Table structure for table `sharewith` */

DROP TABLE IF EXISTS `sharewith`;

CREATE TABLE `sharewith` (
  `sharewithId` int(11) NOT NULL AUTO_INCREMENT,
  `shareDate` datetime DEFAULT NULL,
  `shareCmID` int(11) NOT NULL,
  `shareUId` varchar(50) NOT NULL,
  PRIMARY KEY (`sharewithId`),
  KEY `FK_ei560tit6s34fhojwxb5xmr01` (`shareCmID`),
  KEY `FK_17jl75mkwxn5a6j4xleys5ihi` (`shareUId`),
  CONSTRAINT `FK_17jl75mkwxn5a6j4xleys5ihi` FOREIGN KEY (`shareUId`) REFERENCES `user` (`userId`),
  CONSTRAINT `FK_ei560tit6s34fhojwxb5xmr01` FOREIGN KEY (`shareCmID`) REFERENCES `conceptmap` (`cmId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `sharewith` */

/*Table structure for table `task` */

DROP TABLE IF EXISTS `task`;

CREATE TABLE `task` (
  `taskId` int(11) NOT NULL AUTO_INCREMENT,
  `deadLine` datetime DEFAULT NULL,
  `taskDescription` varchar(255) NOT NULL,
  `taskName` varchar(50) NOT NULL,
  `ansCmId` int(11) DEFAULT NULL,
  `ownUserId` varchar(50) NOT NULL,
  `forclass` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`taskId`),
  KEY `FK_byyn4eqcyudi0k5k9ucn9t44y` (`ansCmId`),
  KEY `FK_rv2n0fl2ms4coh1m8fb4skl91` (`ownUserId`),
  CONSTRAINT `FK_byyn4eqcyudi0k5k9ucn9t44y` FOREIGN KEY (`ansCmId`) REFERENCES `conceptmap` (`cmId`),
  CONSTRAINT `FK_rv2n0fl2ms4coh1m8fb4skl91` FOREIGN KEY (`ownUserId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `task` */

/*Table structure for table `taskstu` */

DROP TABLE IF EXISTS `taskstu`;

CREATE TABLE `taskstu` (
  `taskId` int(11) NOT NULL,
  `stuId` varchar(50) NOT NULL,
  PRIMARY KEY (`taskId`,`stuId`),
  KEY `FK_m2h5q24m09nfrqj7s4dscmta7` (`stuId`),
  CONSTRAINT `FK_bgkrqxep54hku9oyetjaqmit0` FOREIGN KEY (`taskId`) REFERENCES `task` (`taskId`),
  CONSTRAINT `FK_m2h5q24m09nfrqj7s4dscmta7` FOREIGN KEY (`stuId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `taskstu` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `userId` varchar(50) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `fullName` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `classId` varchar(10) DEFAULT NULL,
  `sex` bit(1) DEFAULT NULL,
  `s3bucketId` varchar(50) DEFAULT NULL,
  `signInProvider` varchar(20) DEFAULT NULL,
  `mssv` varchar(20) DEFAULT NULL,
  `khoa` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  KEY `FK_ajxch3g75kd7p4v83wr5agjww` (`classId`),
  CONSTRAINT `FK_ajxch3g75kd7p4v83wr5agjww` FOREIGN KEY (`classId`) REFERENCES `classes` (`classId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`userId`,`email`,`fullName`,`password`,`role`,`status`,`classId`,`sex`,`s3bucketId`,`signInProvider`,`mssv`,`khoa`) values ('admin','admin','admin','admin','admin','\0',NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `userconnection` */

DROP TABLE IF EXISTS `userconnection`;

CREATE TABLE `userconnection` (
  `userId` varchar(255) NOT NULL,
  `providerId` varchar(255) NOT NULL,
  `providerUserId` varchar(255) NOT NULL DEFAULT '',
  `rank` int(11) NOT NULL,
  `displayName` varchar(255) DEFAULT NULL,
  `profileUrl` varchar(512) DEFAULT NULL,
  `imageUrl` varchar(512) DEFAULT NULL,
  `accessToken` varchar(255) NOT NULL,
  `secret` varchar(255) DEFAULT NULL,
  `refreshToken` varchar(255) DEFAULT NULL,
  `expireTime` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`userId`,`providerId`,`providerUserId`),
  UNIQUE KEY `UserConnectionRank` (`userId`,`providerId`,`rank`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `userconnection` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
