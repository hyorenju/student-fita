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
-- Table structure for table `courses_terms`
--

DROP TABLE IF EXISTS `courses_terms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses_terms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `excellent` int DEFAULT NULL,
  `fair` int DEFAULT NULL,
  `good` int DEFAULT NULL,
  `medium` int DEFAULT NULL,
  `weak` int DEFAULT NULL,
  `worst` int DEFAULT NULL,
  `course_id` varchar(100) DEFAULT NULL,
  `term_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtmxec2gsd9lesy45qqxex8c22` (`course_id`),
  KEY `FKce76at52ju06cdtskfy9yeqwb` (`term_id`),
  CONSTRAINT `FKce76at52ju06cdtskfy9yeqwb` FOREIGN KEY (`term_id`) REFERENCES `terms` (`id`),
  CONSTRAINT `FKtmxec2gsd9lesy45qqxex8c22` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses_terms`
--

LOCK TABLES `courses_terms` WRITE;
/*!40000 ALTER TABLE `courses_terms` DISABLE KEYS */;
/*!40000 ALTER TABLE `courses_terms` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-19 16:12:13
