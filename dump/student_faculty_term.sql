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
-- Table structure for table `faculty_term`
--

DROP TABLE IF EXISTS `faculty_term`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faculty_term` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dropout_with_permission` int DEFAULT NULL,
  `dropout_without_permission` int DEFAULT NULL,
  `excellent` int DEFAULT NULL,
  `fair` int DEFAULT NULL,
  `good` int DEFAULT NULL,
  `medium` int DEFAULT NULL,
  `weak` int DEFAULT NULL,
  `worst` int DEFAULT NULL,
  `term_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_43qmvb6c7wcbtmygc45i2b8b4` (`term_id`),
  CONSTRAINT `FKtinbgubx7lth63b3s9fy15x7k` FOREIGN KEY (`term_id`) REFERENCES `terms` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty_term`
--

LOCK TABLES `faculty_term` WRITE;
/*!40000 ALTER TABLE `faculty_term` DISABLE KEYS */;
INSERT INTO `faculty_term` VALUES (48,0,0,0,0,1,1,0,0,'20161'),(49,0,0,0,0,0,2,0,0,'20162'),(50,0,0,0,5,0,0,0,0,'20171'),(51,0,0,0,3,1,1,0,0,'20172'),(52,1,5,1,1,3,2,0,0,'20181'),(53,7,6,4,0,2,1,0,0,'20182'),(54,12,16,2,2,2,3,0,0,'20191'),(55,4,1,4,2,1,2,0,0,'20192'),(56,24,20,26,34,19,22,0,0,'20201'),(57,0,0,21,33,14,33,0,0,'20202'),(58,0,0,17,40,25,26,0,0,'20211'),(59,0,0,25,34,16,33,0,0,'20212'),(60,0,0,5,3,3,1,0,0,'20221'),(61,0,0,3,2,0,7,0,0,'20222'),(62,0,0,2,5,2,3,0,0,'20231'),(63,0,0,1,0,0,0,0,0,'20232');
/*!40000 ALTER TABLE `faculty_term` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-11 16:56:05
