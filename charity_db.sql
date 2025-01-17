-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: charity_db
-- ------------------------------------------------------
-- Server version	8.0.38

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                           `parent_ID` int DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `parent_ID` (`parent_ID`),
                           CONSTRAINT `address_ibfk_1` FOREIGN KEY (`parent_ID`) REFERENCES `address` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'USA',NULL),(2,'India',NULL),(3,'United Kingdom',NULL),(4,'Australia',NULL),(5,'California',1),(6,'New York',1),(7,'San Francisco',2),(8,'Maharashtra',6),(9,'Delhi',6),(10,'Mumbai',7),(11,'England',3),(12,'Scotland',3),(13,'London',11),(14,'Manchester',11),(15,'Edinburgh',12),(16,'Glasgow',12),(17,'New South Wales',4),(18,'Victoria',4),(19,'Sydney',4);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assigned_tasks`
--

DROP TABLE IF EXISTS `assigned_tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assigned_tasks` (
                                  `person_id` int NOT NULL,
                                  `task_id` int NOT NULL,
                                  KEY `person_id` (`person_id`),
                                  KEY `task_id` (`task_id`),
                                  CONSTRAINT `assigned_tasks_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`) ON DELETE CASCADE,
                                  CONSTRAINT `assigned_tasks_ibfk_2` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assigned_tasks`
--

LOCK TABLES `assigned_tasks` WRITE;
/*!40000 ALTER TABLE `assigned_tasks` DISABLE KEYS */;
INSERT INTO `assigned_tasks` VALUES (1,1),(1,6),(2,2),(2,5),(3,4),(3,9),(4,7),(4,8);
/*!40000 ALTER TABLE `assigned_tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign`
--

DROP TABLE IF EXISTS `campaign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `campaign` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `title` varchar(255) NOT NULL,
                            `description` varchar(255) NOT NULL,
                            `goal_amount` double NOT NULL,
                            `collected_amount` double NOT NULL,
                            `start_date` date NOT NULL,
                            `end_date` date NOT NULL,
                            `status` varchar(255) NOT NULL,
                            `creator` int NOT NULL,
                            PRIMARY KEY (`id`),
                            KEY `campaign_staff_person_id_fk` (`creator`),
                            CONSTRAINT `campaign_staff_person_id_fk` FOREIGN KEY (`creator`) REFERENCES `staff` (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign`
--

LOCK TABLES `campaign` WRITE;
/*!40000 ALTER TABLE `campaign` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donation`
--

DROP TABLE IF EXISTS `donation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donation` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `date` date NOT NULL,
                            `person_id` int NOT NULL,
                            `donation_type` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            KEY `person_id` (`person_id`),
                            CONSTRAINT `donation_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donation`
--

LOCK TABLES `donation` WRITE;
/*!40000 ALTER TABLE `donation` DISABLE KEYS */;
/*!40000 ALTER TABLE `donation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `in_kind_donation`
--

DROP TABLE IF EXISTS `in_kind_donation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `in_kind_donation` (
                                    `donation_id` int NOT NULL,
                                    `quantity` int NOT NULL,
                                    `type` varchar(255) NOT NULL,
                                    `address_id` int DEFAULT NULL,
                                    PRIMARY KEY (`donation_id`),
                                    KEY `in_kind_donation_address_id_fk` (`address_id`),
                                    CONSTRAINT `in_kind_donation_address_id_fk` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `in_kind_donation`
--

LOCK TABLES `in_kind_donation` WRITE;
/*!40000 ALTER TABLE `in_kind_donation` DISABLE KEYS */;
/*!40000 ALTER TABLE `in_kind_donation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `money_donation`
--

DROP TABLE IF EXISTS `money_donation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `money_donation` (
                                  `donation_id` int NOT NULL,
                                  `amount` double DEFAULT NULL,
                                  `campaign_id` int DEFAULT NULL,
                                  PRIMARY KEY (`donation_id`),
                                  KEY `money_donation_campaign_id_fk` (`campaign_id`),
                                  CONSTRAINT `money_donation_campaign_id_fk` FOREIGN KEY (`campaign_id`) REFERENCES `campaign` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `money_donation`
--

LOCK TABLES `money_donation` WRITE;
/*!40000 ALTER TABLE `money_donation` DISABLE KEYS */;
/*!40000 ALTER TABLE `money_donation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                          `gender` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                          `phone_number` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                          `email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                          `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                          `national_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                          `date_of_birth` date NOT NULL,
                          `is_active` tinyint(1) NOT NULL,
                          `address_id` int DEFAULT NULL,
                          `user_type` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `person_pk` (`email`),
                          UNIQUE KEY `person_pk_2` (`phone_number`),
                          KEY `address_id` (`address_id`),
                          CONSTRAINT `person_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,'John Doe','Male','1234567890','john.doe@example.com','password123','12345678901234','1985-05-15',1,13,'staff'),(2,'Jane Smith','Female','9876543210','jane.smith@example.com','password456','23456789012345','1990-03-22',1,18,'staff'),(3,'Alice Johnson','Female','1122334455','alice.johnson@example.com','password789','34567890123456','1995-07-10',1,9,'volunteer'),(4,'Bob Williams','Male','5566778899','bob.williams@example.com','password012','45678901234567','1988-11-30',1,7,'volunteer');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skill`
--

DROP TABLE IF EXISTS `skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skill` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                         `description` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skill`
--

LOCK TABLES `skill` WRITE;
/*!40000 ALTER TABLE `skill` DISABLE KEYS */;
INSERT INTO `skill` VALUES (1,'Programming','Ability to write and debug code in various programming languages'),(2,'Communication','Excellent verbal and written communication skills'),(3,'Problem Solving','Ability to analyze issues and come up with effective solutions'),(4,'Time Management','Skill in managing time effectively to meet deadlines'),(5,'Teamwork','Capability to work effectively as part of a team');
/*!40000 ALTER TABLE `skill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
                         `person_id` int NOT NULL,
                         `position` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                         `department` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                         KEY `person_id` (`person_id`),
                         CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (1,'Manager','Human Resources'),(2,'Coordinator','Operations');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                        `description` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (1,'Prepare Monthly Report','Compile and analyze monthly data for all departments.'),(2,'Organize Volunteer Meeting','Arrange a meeting to discuss the upcoming events with volunteers.'),(3,'Update Database','Ensure all records are up to date and remove duplicates.'),(4,'Conduct Training Session','Provide training for new volunteers on charity operations.'),(5,'Plan Fundraising Event','Organize a charity event to raise funds for the organization.'),(6,'Design Marketing Material','Create posters and flyers for the upcoming campaign.'),(7,'Coordinate Logistics','Arrange transportation and equipment for charity distribution.'),(8,'Follow Up with Donors','Send thank-you emails and updates to all donors.'),(9,'Develop New Strategy','Propose and document a strategy to increase volunteer engagement.'),(10,'Audit Financial Records','Review and verify all financial records for accuracy.');
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `volunteer_skills`
--

DROP TABLE IF EXISTS `volunteer_skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `volunteer_skills` (
                                    `person_id` int NOT NULL,
                                    `skill_id` int NOT NULL,
                                    KEY `person_id` (`person_id`),
                                    KEY `skill_id` (`skill_id`),
                                    CONSTRAINT `volunteer_skills_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`) ON DELETE CASCADE,
                                    CONSTRAINT `volunteer_skills_ibfk_2` FOREIGN KEY (`skill_id`) REFERENCES `skill` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `volunteer_skills`
--

LOCK TABLES `volunteer_skills` WRITE;
/*!40000 ALTER TABLE `volunteer_skills` DISABLE KEYS */;
INSERT INTO `volunteer_skills` VALUES (3,1),(3,2),(3,4),(4,3),(4,5),(4,2);
/*!40000 ALTER TABLE `volunteer_skills` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-17  7:08:39
