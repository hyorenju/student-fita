-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: student
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `classes`
--

DROP TABLE IF EXISTS `classes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `classes` (
  `id` varchar(100) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `deputy_secretary` varchar(100) DEFAULT NULL,
  `monitor` varchar(100) DEFAULT NULL,
  `secretary` varchar(100) DEFAULT NULL,
  `vice_monitor` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1jqbn5gdwvg8wsr76sgr8xn5r` (`deputy_secretary`),
  UNIQUE KEY `UK_l8iaesb4sou2ccj6dkcb5o5gq` (`monitor`),
  UNIQUE KEY `UK_rviit3s1fcso44h9s2nioogkb` (`secretary`),
  UNIQUE KEY `UK_ffthhwi8vx19me1hln4bgr7m7` (`vice_monitor`),
  CONSTRAINT `FK1984g30p7xe9oc43wek783m3` FOREIGN KEY (`vice_monitor`) REFERENCES `students` (`id`),
  CONSTRAINT `FK2rxm3mbu1nbjb2q2ibrl75sjj` FOREIGN KEY (`monitor`) REFERENCES `students` (`id`),
  CONSTRAINT `FKgpppedy0ml8ij2lk261mxm8c4` FOREIGN KEY (`secretary`) REFERENCES `students` (`id`),
  CONSTRAINT `FKp3h3eg27w5nfak3t6rh5fvbbl` FOREIGN KEY (`deputy_secretary`) REFERENCES `students` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classes`
--

LOCK TABLES `classes` WRITE;
/*!40000 ALTER TABLE `classes` DISABLE KEYS */;
INSERT INTO `classes` VALUES ('K61CNPMP','K61-Công nghệ phần mềm P',NULL,NULL,NULL,NULL),('K61MMT','K61-Mạng máy tính',NULL,NULL,NULL,NULL),('K61QLTT','K61-Quản lý thông tin',NULL,NULL,NULL,NULL),('K61THA','K61-Tin học A',NULL,NULL,NULL,NULL),('K62CNPM','K62-Công nghệ phần mềm',NULL,NULL,NULL,NULL),('K62CNPMP','K62-Công nghệ phần mềm P',NULL,NULL,NULL,NULL),('K62CNTTA','K62-Công nghệ thông tin A',NULL,NULL,NULL,NULL),('K62HTTT','K62-Hệ thống thông tin',NULL,NULL,NULL,NULL),('K63ATTT','K63-An toàn thông tin',NULL,NULL,NULL,NULL),('K63CNPM','K63-Công nghệ phần mềm',NULL,NULL,NULL,NULL),('K63CNPMP','K63-Công nghệ phần mềm P',NULL,NULL,NULL,NULL),('K63HTTT','K63-Hệ thống thông tin',NULL,NULL,NULL,NULL),('K63MMT','K63-Mạng máy tính',NULL,NULL,NULL,NULL),('K63TH','K63-Tin học',NULL,NULL,NULL,NULL),('K64ATTT','K64-An toàn thông tin',NULL,NULL,NULL,NULL),('K64CNPM','K64-Công nghệ phần mềm',NULL,NULL,NULL,NULL),('K64CNTTA','K64-Công nghệ thông tin',NULL,NULL,NULL,NULL),('K64HTTT','K64-Hệ thống thông tin',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `classes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-19 16:12:12
