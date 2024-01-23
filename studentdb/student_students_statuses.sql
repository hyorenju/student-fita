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
-- Table structure for table `students_statuses`
--

DROP TABLE IF EXISTS `students_statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students_statuses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `note` varchar(200) DEFAULT NULL,
  `time` datetime(6) DEFAULT NULL,
  `status_id` int DEFAULT NULL,
  `student_id` varchar(100) DEFAULT NULL,
  `term_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcpb78wae7peruuh9efi5gtt9b` (`status_id`),
  KEY `FK4b426dhs2jhe938uoatbl22tc` (`student_id`),
  KEY `FKhm9140rm8gh0pwsaqa21y6lov` (`term_id`),
  CONSTRAINT `FK4b426dhs2jhe938uoatbl22tc` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`),
  CONSTRAINT `FKcpb78wae7peruuh9efi5gtt9b` FOREIGN KEY (`status_id`) REFERENCES `statuses` (`id`),
  CONSTRAINT `FKhm9140rm8gh0pwsaqa21y6lov` FOREIGN KEY (`term_id`) REFERENCES `terms` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students_statuses`
--

LOCK TABLES `students_statuses` WRITE;
/*!40000 ALTER TABLE `students_statuses` DISABLE KEYS */;
/*!40000 ALTER TABLE `students_statuses` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-19 16:12:17
