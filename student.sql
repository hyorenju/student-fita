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
-- Table structure for table `admin_refreshers`
--

DROP TABLE IF EXISTS `admin_refreshers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_refreshers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiry_date` datetime(6) DEFAULT NULL,
  `token` varchar(200) DEFAULT NULL,
  `admin_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2n4w20rpbeg31lcv6usotjgve` (`admin_id`),
  CONSTRAINT `FKc4v5qejb1au3yy10ah7cgwbji` FOREIGN KEY (`admin_id`) REFERENCES `admins` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_refreshers`
--

LOCK TABLES `admin_refreshers` WRITE;
/*!40000 ALTER TABLE `admin_refreshers` DISABLE KEYS */;
INSERT INTO `admin_refreshers` VALUES (1,'2024-01-19 03:54:57.889579','abe29560-03a0-48b0-896e-0fe9db239f05','admin');
/*!40000 ALTER TABLE `admin_refreshers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admins` (
  `id` varchar(100) NOT NULL,
  `avatar` varchar(1000) DEFAULT NULL,
  `email` varchar(190) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `role_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_47bvqemyk6vlm0w7crc3opdd4` (`email`),
  KEY `FK7h9x5fnfw47l8lkeas20rmsva` (`role_id`),
  CONSTRAINT `FK7h9x5fnfw47l8lkeas20rmsva` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admins`
--

LOCK TABLES `admins` WRITE;
/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
INSERT INTO `admins` VALUES ('admin',NULL,'admin@gmail.com',_binary '\0','Trịnh Thị Nhâm','$2a$10$EMyb99csAAhz4uZ2KXw6juSdMskIrhiK0mN/3Tr4X0LX0/9f90Oze','SUPERADMIN');
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;
UNLOCK TABLES;

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

--
-- Table structure for table `classes_terms`
--

DROP TABLE IF EXISTS `classes_terms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `classes_terms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `excellent` int DEFAULT NULL,
  `fair` int DEFAULT NULL,
  `good` int DEFAULT NULL,
  `medium` int DEFAULT NULL,
  `weak` int DEFAULT NULL,
  `worst` int DEFAULT NULL,
  `class_id` varchar(100) DEFAULT NULL,
  `term_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK70qpo5recj7ssibijbqp8a8p0` (`class_id`),
  KEY `FKavgf24irera6o7mcksm25iull` (`term_id`),
  CONSTRAINT `FK70qpo5recj7ssibijbqp8a8p0` FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`),
  CONSTRAINT `FKavgf24irera6o7mcksm25iull` FOREIGN KEY (`term_id`) REFERENCES `terms` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classes_terms`
--

LOCK TABLES `classes_terms` WRITE;
/*!40000 ALTER TABLE `classes_terms` DISABLE KEYS */;
/*!40000 ALTER TABLE `classes_terms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `id` varchar(100) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES ('61','Khoá 61'),('62','Khoá 62'),('63','Khoá 63'),('64','Khoá 64'),('65','Khoá 65'),('66','Khoá 66'),('67','Khoá 67'),('68','Khoá 68');
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

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

--
-- Table structure for table `displays`
--

DROP TABLE IF EXISTS `displays`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `displays` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(4000) DEFAULT NULL,
  `img` varchar(1000) DEFAULT NULL,
  `location` varchar(200) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `displays`
--

LOCK TABLES `displays` WRITE;
/*!40000 ALTER TABLE `displays` DISABLE KEYS */;
/*!40000 ALTER TABLE `displays` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documents`
--

DROP TABLE IF EXISTS `documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documents` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_checked` bit(1) DEFAULT NULL,
  `link` varchar(1000) DEFAULT NULL,
  `post_date` datetime(6) DEFAULT NULL,
  `title` varchar(1000) DEFAULT NULL,
  `type` varchar(200) DEFAULT NULL,
  `student_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1men4i9k1s1t8bfm9clnsv9ko` (`student_id`),
  CONSTRAINT `FK1men4i9k1s1t8bfm9clnsv9ko` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documents`
--

LOCK TABLES `documents` WRITE;
/*!40000 ALTER TABLE `documents` DISABLE KEYS */;
/*!40000 ALTER TABLE `documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculty_term`
--

DROP TABLE IF EXISTS `faculty_term`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faculty_term` (
  `id` int NOT NULL AUTO_INCREMENT,
  `admission` int DEFAULT NULL,
  `dropout_with_permission` int DEFAULT NULL,
  `excellent` int DEFAULT NULL,
  `fair` int DEFAULT NULL,
  `forced_out` int DEFAULT NULL,
  `good` int DEFAULT NULL,
  `graduate` int DEFAULT NULL,
  `medium` int DEFAULT NULL,
  `weak` int DEFAULT NULL,
  `worst` int DEFAULT NULL,
  `term_id` varchar(100) DEFAULT NULL,
  `year_id` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_43qmvb6c7wcbtmygc45i2b8b4` (`term_id`),
  UNIQUE KEY `UK_mbs67xy4r2xcpjpbcqllvy93c` (`year_id`),
  CONSTRAINT `FKk4kcfj8vuf1w51892gxuj95oh` FOREIGN KEY (`year_id`) REFERENCES `school_years` (`id`),
  CONSTRAINT `FKtinbgubx7lth63b3s9fy15x7k` FOREIGN KEY (`term_id`) REFERENCES `terms` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty_term`
--

LOCK TABLES `faculty_term` WRITE;
/*!40000 ALTER TABLE `faculty_term` DISABLE KEYS */;
/*!40000 ALTER TABLE `faculty_term` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `majors`
--

DROP TABLE IF EXISTS `majors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `majors` (
  `id` varchar(100) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `total_credits` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `majors`
--

LOCK TABLES `majors` WRITE;
/*!40000 ALTER TABLE `majors` DISABLE KEYS */;
INSERT INTO `majors` VALUES ('ATTT','An toàn thông tin',130),('CNPM','Công nghệ phần mềm',130),('CNTT','Công nghệ thông tin',130),('HTTT','Hệ thống thông tin',130),('MMT','Mạng máy tính và truyền thông dữ liệu',130),('POHE - CNPM','Công nghệ phần mềm hướng nghề nghiệp',130),('QLTT','Quản lý thông tin',130),('TH','Tin học',130),('TTNT','Khoa học dữ liệu và trí tuệ nhân tạo',130);
/*!40000 ALTER TABLE `majors` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `majors_terms`
--

LOCK TABLES `majors_terms` WRITE;
/*!40000 ALTER TABLE `majors_terms` DISABLE KEYS */;
/*!40000 ALTER TABLE `majors_terms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `news` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` longtext,
  `is_deleted` bit(1) DEFAULT NULL,
  `post_date` datetime(6) DEFAULT NULL,
  `title` varchar(1000) DEFAULT NULL,
  `type` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
INSERT INTO `permissions` VALUES ('CREATE_POINT','Thêm điểm','ADMIN'),('CREATE_STUDENT','Thêm sinh viên','ADMIN'),('CREATE_STUDENT_STATUS','Thêm trạng thái cho sinh viên','ADMIN'),('DELETE_POINT','Xóa điểm','ADMIN'),('DELETE_STUDENT','Xóa sinh viên','ADMIN'),('DELETE_STUDENT_STATUS','Xoá trạng thái sinh viên','ADMIN'),('EXPORT_POINT','Xuất danh sách điểm','ADMIN'),('EXPORT_STUDENT','Xuất danh sách sinh viên','ADMIN'),('EXPORT_STUDENT_STATUS','Xuất danh sách trạng thái sinh viên','ADMIN'),('GET_CLASS_STATISTIC','Thống kê lớp','MOD'),('GET_COURSE_STATISTIC','Thống kê khoá','MOD'),('GET_DISPLAY_LIST','Lấy danh sách hiển thị','MOD'),('GET_FACULTY_STATISTIC','Thống kê số sinh viên bỏ học và xin thôi học','MOD'),('GET_MAJOR_STATISTIC','Thống kê ngành','MOD'),('GET_POINT_LIST','Lấy danh sách điểm','ADMIN'),('GET_STUDENT_LIST','Lấy danh sách sinh viên','ADMIN'),('GET_STUDENT_STATISTIC','Thống kê sinh viên','MOD'),('GET_STUDENT_STATUS_LIST','Lấy danh sách trạng thái sinh viên','ADMIN'),('IMPORT_POINT','Import điểm từ excel','ADMIN'),('IMPORT_STUDENT','Import sinh viên từ excel','ADMIN'),('IMPORT_STUDENT_STATUS','Import trạng thái sinh viên từ excel','ADMIN'),('RESTORE_POINT','Khôi phục điểm','ADMIN'),('RESTORE_STUDENT','Khôi phục sinh viên','ADMIN'),('UPDATE_DISPLAY','Cập nhật hiển thị','MOD'),('UPDATE_MONITOR_PROFILE','Cập nhật hồ sơ sinh viên','MONITOR'),('UPDATE_POINT','Sửa điểm','ADMIN'),('UPDATE_STUDENT','Sửa sinh viên','ADMIN'),('UPDATE_STUDENT_PROFILE','Cập nhật hồ sơ sinh viên','STUDENT'),('UPDATE_STUDENT_STATUS','Sửa trạng thái sinh viên','ADMIN');
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `point_annual`
--

DROP TABLE IF EXISTS `point_annual`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `point_annual` (
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
  `school_year` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkwc61ng4r1qbv5twi0okrjawh` (`student_id`),
  KEY `FKlaej9l27pwo337h0f5qfvsh5m` (`school_year`),
  CONSTRAINT `FKkwc61ng4r1qbv5twi0okrjawh` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`),
  CONSTRAINT `FKlaej9l27pwo337h0f5qfvsh5m` FOREIGN KEY (`school_year`) REFERENCES `school_years` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `point_annual`
--

LOCK TABLES `point_annual` WRITE;
/*!40000 ALTER TABLE `point_annual` DISABLE KEYS */;
/*!40000 ALTER TABLE `point_annual` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` varchar(100) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES ('ADMIN','Quản trị viên'),('MOD','Kiểm duyệt viên'),('MONITOR','Lớp trưởng'),('STUDENT','Sinh viên'),('SUPERADMIN','Quản trị viên cấp cao');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles_permissions`
--

DROP TABLE IF EXISTS `roles_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles_permissions` (
  `role_id` varchar(100) NOT NULL,
  `permission_id` varchar(100) NOT NULL,
  KEY `FKbx9r9uw77p58gsq4mus0mec0o` (`permission_id`),
  KEY `FKqi9odri6c1o81vjox54eedwyh` (`role_id`),
  CONSTRAINT `FKbx9r9uw77p58gsq4mus0mec0o` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`),
  CONSTRAINT `FKqi9odri6c1o81vjox54eedwyh` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles_permissions`
--

LOCK TABLES `roles_permissions` WRITE;
/*!40000 ALTER TABLE `roles_permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `roles_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `school_years`
--

DROP TABLE IF EXISTS `school_years`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `school_years` (
  `id` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `school_years`
--

LOCK TABLES `school_years` WRITE;
/*!40000 ALTER TABLE `school_years` DISABLE KEYS */;
/*!40000 ALTER TABLE `school_years` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statuses`
--

DROP TABLE IF EXISTS `statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `statuses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statuses`
--

LOCK TABLES `statuses` WRITE;
/*!40000 ALTER TABLE `statuses` DISABLE KEYS */;
/*!40000 ALTER TABLE `statuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_refreshers`
--

DROP TABLE IF EXISTS `student_refreshers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_refreshers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiry_date` datetime(6) DEFAULT NULL,
  `token` varchar(200) DEFAULT NULL,
  `student_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_k7b8ruy1i7iq9ngu2dl9b9b7x` (`student_id`),
  CONSTRAINT `FKlnw7dy9lmj7eln4bnbihd9wj3` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_refreshers`
--

LOCK TABLES `student_refreshers` WRITE;
/*!40000 ALTER TABLE `student_refreshers` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_refreshers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `id` varchar(100) NOT NULL,
  `avatar` varchar(1000) DEFAULT NULL,
  `dob` datetime(6) DEFAULT NULL,
  `email` varchar(190) DEFAULT NULL,
  `family_situation` varchar(200) DEFAULT NULL,
  `father_name` varchar(200) DEFAULT NULL,
  `father_phone_number` varchar(200) DEFAULT NULL,
  `gender` varchar(200) DEFAULT NULL,
  `home_town` varchar(200) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `last_name` varchar(200) DEFAULT NULL,
  `mother_name` varchar(200) DEFAULT NULL,
  `mother_phone_number` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `phone_number` varchar(200) DEFAULT NULL,
  `residence` varchar(200) DEFAULT NULL,
  `surname` varchar(200) DEFAULT NULL,
  `class_id` varchar(100) DEFAULT NULL,
  `course_id` varchar(100) DEFAULT NULL,
  `major_id` varchar(100) DEFAULT NULL,
  `role_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_e2rndfrsx22acpq2ty1caeuyw` (`email`),
  KEY `FKhnslh0rm5bthlble8vjunbnwe` (`class_id`),
  KEY `FK6jiqckumc6tm0h9otqbtqhgnr` (`course_id`),
  KEY `FKi04cc0278a1f49g0995mnuo63` (`major_id`),
  KEY `FKs33errciqfkiwe8slr6y4jwm7` (`role_id`),
  CONSTRAINT `FK6jiqckumc6tm0h9otqbtqhgnr` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`),
  CONSTRAINT `FKhnslh0rm5bthlble8vjunbnwe` FOREIGN KEY (`class_id`) REFERENCES `classes` (`id`),
  CONSTRAINT `FKi04cc0278a1f49g0995mnuo63` FOREIGN KEY (`major_id`) REFERENCES `majors` (`id`),
  CONSTRAINT `FKs33errciqfkiwe8slr6y4jwm7` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

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

--
-- Table structure for table `terms`
--

DROP TABLE IF EXISTS `terms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `terms` (
  `id` varchar(100) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `terms`
--

LOCK TABLES `terms` WRITE;
/*!40000 ALTER TABLE `terms` DISABLE KEYS */;
INSERT INTO `terms` VALUES ('20161','Học kỳ 1 - năm 2016'),('20162','Học kỳ 2 - năm 2016'),('20171','Học kỳ 1 - năm 2017'),('20172','Học kỳ 2 - năm 2017'),('20181','Học kỳ 1 - năm 2018'),('20182','Học kỳ 2 - năm 2018'),('20191','Học kỳ 1 - năm 2019'),('20192','Học kỳ 2 - năm 2019'),('20201','Học kỳ 1 - năm 2020'),('20202','Học kỳ 2 - năm 2020'),('20211','Học kỳ 1 - năm 2023'),('20212','Học kỳ 2 - năm 2021'),('20221','Học kỳ 1 - năm 2022'),('20222','Học kỳ 2 - năm 2022'),('20231','Học kỳ 1 - năm 2023'),('20232','Học kỳ 2 - năm 2023');
/*!40000 ALTER TABLE `terms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trash_admins`
--

DROP TABLE IF EXISTS `trash_admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trash_admins` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `time` datetime(6) DEFAULT NULL,
  `admin_id` varchar(100) DEFAULT NULL,
  `deleted_by` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ovc3exgmc49dwx4a2rpb6dp20` (`admin_id`),
  KEY `FK3fpoj8lpukx2lmddx0nwgrrno` (`deleted_by`),
  CONSTRAINT `FK3fpoj8lpukx2lmddx0nwgrrno` FOREIGN KEY (`deleted_by`) REFERENCES `admins` (`id`),
  CONSTRAINT `FKdet09043e55tj0f1hnv0q1u3g` FOREIGN KEY (`admin_id`) REFERENCES `admins` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trash_admins`
--

LOCK TABLES `trash_admins` WRITE;
/*!40000 ALTER TABLE `trash_admins` DISABLE KEYS */;
/*!40000 ALTER TABLE `trash_admins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trash_news`
--

DROP TABLE IF EXISTS `trash_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trash_news` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `time` datetime(6) DEFAULT NULL,
  `deleted_by` varchar(100) DEFAULT NULL,
  `news_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8kcc3416dpiay4hkvunbkvo0b` (`news_id`),
  KEY `FKjm09oeotw6v31mviw2ink8s66` (`deleted_by`),
  CONSTRAINT `FKjm09oeotw6v31mviw2ink8s66` FOREIGN KEY (`deleted_by`) REFERENCES `admins` (`id`),
  CONSTRAINT `FKy4of20e3tkqirm298ev59vmn` FOREIGN KEY (`news_id`) REFERENCES `news` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trash_news`
--

LOCK TABLES `trash_news` WRITE;
/*!40000 ALTER TABLE `trash_news` DISABLE KEYS */;
/*!40000 ALTER TABLE `trash_news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trash_point_annual`
--

DROP TABLE IF EXISTS `trash_point_annual`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trash_point_annual` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `time` datetime(6) DEFAULT NULL,
  `deleted_by` varchar(100) DEFAULT NULL,
  `point_annual_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rrgmjdk3f2yvfmrkkb5tg4pld` (`point_annual_id`),
  KEY `FKl7y7i7jmon44rj74g33ecxsfy` (`deleted_by`),
  CONSTRAINT `FKl7y7i7jmon44rj74g33ecxsfy` FOREIGN KEY (`deleted_by`) REFERENCES `admins` (`id`),
  CONSTRAINT `FKnjv3r9oc5edvx57nap7dosq59` FOREIGN KEY (`point_annual_id`) REFERENCES `point_annual` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trash_point_annual`
--

LOCK TABLES `trash_point_annual` WRITE;
/*!40000 ALTER TABLE `trash_point_annual` DISABLE KEYS */;
/*!40000 ALTER TABLE `trash_point_annual` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trash_points`
--

DROP TABLE IF EXISTS `trash_points`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trash_points` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `time` datetime(6) DEFAULT NULL,
  `deleted_by` varchar(100) DEFAULT NULL,
  `point_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_d68dktsjcdrrou9vjhmkbnafw` (`point_id`),
  KEY `FKp2vginyxk8fbtys53dttka5j9` (`deleted_by`),
  CONSTRAINT `FKk0fmrmeky3qgnrefsnqpqjnle` FOREIGN KEY (`point_id`) REFERENCES `students_terms` (`id`),
  CONSTRAINT `FKp2vginyxk8fbtys53dttka5j9` FOREIGN KEY (`deleted_by`) REFERENCES `admins` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trash_points`
--

LOCK TABLES `trash_points` WRITE;
/*!40000 ALTER TABLE `trash_points` DISABLE KEYS */;
/*!40000 ALTER TABLE `trash_points` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trash_students`
--

DROP TABLE IF EXISTS `trash_students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trash_students` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `time` datetime(6) DEFAULT NULL,
  `deleted_by` varchar(100) DEFAULT NULL,
  `student_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qhc2wldeck9mulg3u10yafu8h` (`student_id`),
  KEY `FK814m7b56n46xscthse093bbw2` (`deleted_by`),
  CONSTRAINT `FK814m7b56n46xscthse093bbw2` FOREIGN KEY (`deleted_by`) REFERENCES `admins` (`id`),
  CONSTRAINT `FKnj6txl2v24mclaiwfu7ymqyuw` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trash_students`
--

LOCK TABLES `trash_students` WRITE;
/*!40000 ALTER TABLE `trash_students` DISABLE KEYS */;
/*!40000 ALTER TABLE `trash_students` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-19 16:14:09
