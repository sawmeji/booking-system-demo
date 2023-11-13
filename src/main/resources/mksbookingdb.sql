-- MySQL dump 10.13  Distrib 8.0.33, for macos13 (arm64)
--
-- Host: localhost    Database: testUserdb
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `class_schedule`
--

DROP TABLE IF EXISTS `class_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class_schedule` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `country` varchar(255) DEFAULT NULL,
  `end_time` datetime(6) DEFAULT NULL,
  `start_time` datetime(6) DEFAULT NULL,
  `available_slots` int NOT NULL,
  `class_name` varchar(255) DEFAULT NULL,
  `required_credits` int NOT NULL,
  `waitlist_count` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_schedule`
--

LOCK TABLES `class_schedule` WRITE;
/*!40000 ALTER TABLE `class_schedule` DISABLE KEYS */;
INSERT INTO `class_schedule` VALUES (1,'SG','2023-11-13 01:30:00.000000','2023-11-13 12:30:00.000000',19,'Yoga Class',1,0),(2,'TH','2023-11-13 01:30:00.000000','2023-11-13 12:30:00.000000',20,'Mway-Thai Class',1,0),(3,'TH','2023-11-14 01:30:00.000000','2023-11-14 12:30:00.000000',1,'Dance Class',1,1);
/*!40000 ALTER TABLE `class_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `package_info`
--

DROP TABLE IF EXISTS `package_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `package_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `country` varchar(255) DEFAULT NULL,
  `credits` int NOT NULL,
  `duration_days` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `package_info`
--

LOCK TABLES `package_info` WRITE;
/*!40000 ALTER TABLE `package_info` DISABLE KEYS */;
INSERT INTO `package_info` VALUES (1,'SG',5,3,'Basic',100),(2,'TH',2,3,'Basic',50),(3,'MM',5,3,'Basic',10);
/*!40000 ALTER TABLE `package_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_class_booking`
--

DROP TABLE IF EXISTS `user_class_booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_class_booking` (
  `booking_id` bigint NOT NULL AUTO_INCREMENT,
  `booking_time` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `class_id` bigint NOT NULL,
  `waitlist` bit(1) NOT NULL,
  PRIMARY KEY (`booking_id`),
  KEY `FK3o80mjclfs6fhfhy9s33170o4` (`class_id`),
  CONSTRAINT `FK3o80mjclfs6fhfhy9s33170o4` FOREIGN KEY (`class_id`) REFERENCES `class_schedule` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_class_booking`
--

LOCK TABLES `user_class_booking` WRITE;
/*!40000 ALTER TABLE `user_class_booking` DISABLE KEYS */;
INSERT INTO `user_class_booking` VALUES (1,'2023-11-13 01:20:38.303430',1,1,_binary '\0'),(5,'2023-11-13 01:52:39.537154',1,3,_binary '\0'),(7,NULL,3,3,_binary '');
/*!40000 ALTER TABLE `user_class_booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_package`
--

DROP TABLE IF EXISTS `user_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_package` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiry_date` datetime(6) DEFAULT NULL,
  `purchase_date` datetime(6) DEFAULT NULL,
  `class_schedule_id` bigint DEFAULT NULL,
  `package_info_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpfo93ogiq9dlbea3tyo5g44ws` (`class_schedule_id`),
  KEY `FK5idpay3yhqjav0xx3h4e67c7` (`package_info_id`),
  KEY `FK23wrg2jabxivswndr07og5q0y` (`user_id`),
  CONSTRAINT `FK23wrg2jabxivswndr07og5q0y` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK5idpay3yhqjav0xx3h4e67c7` FOREIGN KEY (`package_info_id`) REFERENCES `package_info` (`id`),
  CONSTRAINT `FKpfo93ogiq9dlbea3tyo5g44ws` FOREIGN KEY (`class_schedule_id`) REFERENCES `class_schedule` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_package`
--

LOCK TABLES `user_package` WRITE;
/*!40000 ALTER TABLE `user_package` DISABLE KEYS */;
INSERT INTO `user_package` VALUES (1,'2023-11-15 23:01:41.885409','2023-11-12 23:01:41.885409',NULL,1,1),(6,'2023-11-15 23:42:52.305617','2023-11-12 23:42:52.305617',NULL,2,1),(7,'2023-11-16 01:23:54.655938','2023-11-13 01:23:54.655938',NULL,2,2),(8,'2023-11-16 01:32:43.825968','2023-11-13 01:32:43.825968',NULL,2,3);
/*!40000 ALTER TABLE `user_package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
  CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,1),(2,1),(3,1);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `verification_token` varchar(255) DEFAULT NULL,
  `verified` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'mary@gmail.com','$2a$10$DH.CYlQjmeYHgU6fRcY/K.F.IN16cP8FgrbZqBcPU8anS5qMu8sl2','mary',NULL,_binary '\0'),(2,'john@gmail.com','$2a$10$tndLZIBE1D8mObpe56fCcutgsvDmih5sHZqjX/.ClZfG.C6t5RQ.2','johnwick',NULL,_binary '\0'),(3,'lucifer@gmail.com','$2a$10$xUnoPgSZ519OoiZR7kMjYe50o6X8vSv45Ky.hEXV0rUDoWrhQC4CW','lucifer',NULL,_binary '\0');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-13  9:56:20
