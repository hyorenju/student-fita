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
-- Table structure for table `students_terms`
--

DROP TABLE IF EXISTS `students_terms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students_terms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `average_point_10` float DEFAULT NULL,
  `average_point_4` float DEFAULT NULL,
  `credits_accumulated` int DEFAULT NULL,
  `credits_not_passed` int DEFAULT NULL,
  `credits_passed` int DEFAULT NULL,
  `credits_registered` int DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `point_accumulated_10` float DEFAULT NULL,
  `point_accumulated_4` float DEFAULT NULL,
  `training_point` int DEFAULT NULL,
  `student_id` varchar(100) DEFAULT NULL,
  `term_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcl2fw1k4vt8fn68w0j3250xnh` (`student_id`),
  KEY `FK6wmdb2tnhtda2sa46s7uo9qxj` (`term_id`),
  CONSTRAINT `FK6wmdb2tnhtda2sa46s7uo9qxj` FOREIGN KEY (`term_id`) REFERENCES `terms` (`id`),
  CONSTRAINT `FKcl2fw1k4vt8fn68w0j3250xnh` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students_terms`
--

LOCK TABLES `students_terms` WRITE;
/*!40000 ALTER TABLE `students_terms` DISABLE KEYS */;
/*!40000 ALTER TABLE `students_terms` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-19 16:12:15
