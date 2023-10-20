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
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissions` (
  `id` varchar(100) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
INSERT INTO `permissions` VALUES ('CREATE_POINT','Thêm điểm','ADMIN'),('CREATE_STUDENT','Thêm sinh viên','ADMIN'),('CREATE_STUDENT_STATUS','Thêm trạng thái cho sinh viên','ADMIN'),('DELETE_POINT','Xóa điểm','ADMIN'),('DELETE_STUDENT','Xóa sinh viên','ADMIN'),('DELETE_STUDENT_STATUS','Xoá trạng thái sinh viên','ADMIN'),('EXPORT_POINT','Xuất danh sách điểm','ADMIN'),('EXPORT_STUDENT','Xuất danh sách sinh viên','ADMIN'),('GET_CLASS_STATISTIC','Thống kê lớp','MOD'),('GET_COURSE_STATISTIC','Thống kê khoá','MOD'),('GET_DISPLAY_LIST','Lấy danh sách hiển thị','MOD'),('GET_FACULTY_STATISTIC','Thống kê số sinh viên bỏ học và xin thôi học','MOD'),('GET_MAJOR_STATISTIC','Thống kê ngành','MOD'),('GET_POINT_LIST','Lấy danh sách điểm','ADMIN'),('GET_STUDENT_LIST','Lấy danh sách sinh viên','ADMIN'),('GET_STUDENT_STATISTIC','Thống kê sinh viên','MOD'),('GET_STUDENT_STATUS_LIST','Lấy danh sách trạng thái sinh viên','ADMIN'),('IMPORT_POINT','Import điểm từ excel','ADMIN'),('IMPORT_STUDENT','Import sinh viên từ excel','ADMIN'),('RESTORE_POINT','Khôi phục điểm','ADMIN'),('RESTORE_STUDENT','Khôi phục sinh viên','ADMIN'),('UPDATE_DISPLAY','Cập nhật hiển thị','MOD'),('UPDATE_POINT','Sửa điểm','ADMIN'),('UPDATE_STUDENT','Sửa sinh viên','ADMIN'),('UPDATE_STUDENT_PROFILE','Cập nhật hồ sơ sinh viên','STUDENT'),('UPDATE_STUDENT_STATUS','Sửa trạng thái sinh viên','ADMIN');
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-20 13:25:22
