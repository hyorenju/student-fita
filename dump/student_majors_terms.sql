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
-- Table structure for table `majors_terms`
--

DROP TABLE IF EXISTS `majors_terms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `majors_terms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `excellent` int DEFAULT NULL,
  `fair` int DEFAULT NULL,
  `good` int DEFAULT NULL,
  `medium` int DEFAULT NULL,
  `weak` int DEFAULT NULL,
  `worst` int DEFAULT NULL,
  `major_id` varchar(100) DEFAULT NULL,
  `term_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd82auhh15s3g2gblsj8m5dl72` (`major_id`),
  KEY `FKgd3ftt364x3civ12j87c8g4yb` (`term_id`),
  CONSTRAINT `FKd82auhh15s3g2gblsj8m5dl72` FOREIGN KEY (`major_id`) REFERENCES `majors` (`id`),
  CONSTRAINT `FKgd3ftt364x3civ12j87c8g4yb` FOREIGN KEY (`term_id`) REFERENCES `terms` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `majors_terms`
--

LOCK TABLES `majors_terms` WRITE;
/*!40000 ALTER TABLE `majors_terms` DISABLE KEYS */;
INSERT INTO `majors_terms` VALUES (46,0,0,1,0,0,0,'CNTT','20161'),(47,0,0,0,1,0,0,'MMT','20161'),(48,0,0,0,1,0,0,'CNTT','20162'),(49,0,0,0,1,0,0,'MMT','20162'),(50,0,2,0,0,0,0,'CNTT','20171'),(51,0,2,0,0,0,0,'MMT','20171'),(52,0,1,0,0,0,0,'TTNT','20171'),(53,0,2,0,0,0,0,'CNTT','20172'),(54,0,0,1,1,0,0,'MMT','20172'),(55,0,1,0,0,0,0,'TTNT','20172'),(56,0,1,0,2,0,0,'CNTT','20181'),(57,1,0,1,0,0,0,'MMT','20181'),(58,0,0,2,0,0,0,'TTNT','20181'),(59,2,0,1,0,0,0,'CNTT','20182'),(60,1,0,0,1,0,0,'MMT','20182'),(61,1,0,1,0,0,0,'TTNT','20182'),(62,1,1,0,3,0,0,'CNTT','20191'),(63,1,1,1,0,0,0,'MMT','20191'),(64,0,0,1,0,0,0,'TTNT','20191'),(65,1,2,1,1,0,0,'CNTT','20192'),(66,2,0,0,1,0,0,'MMT','20192'),(67,1,0,0,0,0,0,'TTNT','20192'),(68,12,16,9,5,0,0,'CNTT','20201'),(69,7,12,4,7,0,0,'MMT','20201'),(70,7,6,6,10,0,0,'TTNT','20201'),(71,14,8,7,13,0,0,'CNTT','20202'),(72,4,13,4,9,0,0,'MMT','20202'),(73,3,12,3,11,0,0,'TTNT','20202'),(74,7,18,6,13,0,0,'CNTT','20211'),(75,5,11,10,6,0,0,'MMT','20211'),(76,5,11,9,7,0,0,'TTNT','20211'),(77,14,12,9,9,0,0,'CNTT','20212'),(78,7,11,4,10,0,0,'MMT','20212'),(79,4,11,3,14,0,0,'TTNT','20212'),(80,2,1,2,0,0,0,'CNTT','20221'),(81,1,1,1,1,0,0,'MMT','20221'),(82,2,1,0,0,0,0,'TTNT','20221'),(83,3,1,0,1,0,0,'CNTT','20222'),(84,0,1,0,3,0,0,'MMT','20222'),(85,0,0,0,3,0,0,'TTNT','20222'),(86,1,3,1,0,0,0,'CNTT','20231'),(87,0,1,0,3,0,0,'MMT','20231'),(88,1,1,1,0,0,0,'TTNT','20231'),(89,1,0,0,0,0,0,'CNTT','20232');
/*!40000 ALTER TABLE `majors_terms` ENABLE KEYS */;
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