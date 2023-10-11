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
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admins` (
  `id` varchar(100) NOT NULL,
  `avatar` varchar(1000) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `role_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7h9x5fnfw47l8lkeas20rmsva` (`role_id`),
  CONSTRAINT `FK7h9x5fnfw47l8lkeas20rmsva` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admins`
--

LOCK TABLES `admins` WRITE;
/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
INSERT INTO `admins` VALUES ('kiki123','https://storage.googleapis.com/student-4a760.appspot.com/59621b24-8b28-4af5-b46a-7ce8a9775f48.jpg?GoogleAccessId=firebase-adminsdk-60z3z@student-4a760.iam.gserviceaccount.com&Expires=6543694221&Signature=I2HV4cRu2J9tuDOrIBPcDxpqsfJ1KAxyxQddl2TrH5uNsgyplu29qNrybHvfl5EKFDglvoyMzIDoMecfvtN8QqOVEBn%2BQ0MvxfkH5U5AbkkQFin%2BagPnHVKxMKNjokqwX7LZXAQtH%2BYP7cFVLmdSOWxtxNJbR0nDtH0g%2F0fuQ%2F5nOUgHE5y70CDc7JwuByn3jzW6C86jQ%2Fi%2FsSssQDvgh3h%2BfIRof7gftPguLh1f0FP9EEDGfqCEUShU5xe5Qmr9vNFORr9eICMKLWuBmyzAGUWOel%2B32X2AzHXhdv3z0ohAwxk08NpKh7udiExk%2FqfUHF%2FyKIu2ShwmJvkJidC%2FJQ%3D%3D','kiki@gmail.com',_binary '\0','Cam Hiếu','$2a$10$/uwT28Jy04F365e1kG/wL.l8UTTHbfB7.soA13f8U3kZ.jJJ.Alwi','SUPERADMIN');
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-11 16:56:02
