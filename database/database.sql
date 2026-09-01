-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: webst2
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `categoryId` int NOT NULL AUTO_INCREMENT,
  `categoryname` varchar(255) DEFAULT NULL,
  `images` varchar(255) DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`categoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Iphone','abc.jpg',1);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `orderDetailId` int NOT NULL AUTO_INCREMENT,
  `priceAtPurchase` double DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `orderId` int DEFAULT NULL,
  `productId` int DEFAULT NULL,
  PRIMARY KEY (`orderDetailId`),
  KEY `FKdtiviqtl269gtu1t3sc85gku3` (`orderId`),
  KEY `FKc33ogl45yrgi5icd4sxfk4xis` (`productId`),
  CONSTRAINT `FKc33ogl45yrgi5icd4sxfk4xis` FOREIGN KEY (`productId`) REFERENCES `products` (`productId`),
  CONSTRAINT `FKdtiviqtl269gtu1t3sc85gku3` FOREIGN KEY (`orderId`) REFERENCES `orders` (`orderId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES (1,9999999,1,1,1),(2,9999999,1,2,1),(3,9999999,1,3,1),(4,9999999,1,4,1);
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `orderId` int NOT NULL AUTO_INCREMENT,
  `orderDate` datetime(6) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `totalAmount` double DEFAULT NULL,
  `userId` int DEFAULT NULL,
  PRIMARY KEY (`orderId`),
  KEY `FK6co8q7ko456baksb6tdjq2dfv` (`userId`),
  CONSTRAINT `FK6co8q7ko456baksb6tdjq2dfv` FOREIGN KEY (`userId`) REFERENCES `users` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'2026-08-31 13:57:08.282000',1,9999999,2),(2,'2026-08-31 14:03:06.435000',1,9999999,1),(3,'2026-08-31 14:09:01.173000',1,9999999,1),(4,'2026-08-31 14:09:44.052000',1,9999999,2);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `productId` int NOT NULL AUTO_INCREMENT,
  `createdDate` datetime(6) DEFAULT NULL,
  `description` text,
  `image` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `productName` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  `categoryId` int DEFAULT NULL,
  `importPrice` double DEFAULT NULL,
  PRIMARY KEY (`productId`),
  KEY `FKej2ob3ifydf846t2a2tntna4e` (`categoryId`),
  CONSTRAINT `FKej2ob3ifydf846t2a2tntna4e` FOREIGN KEY (`categoryId`) REFERENCES `categories` (`categoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'2026-08-30 17:42:27.676000','hihi','1788111747681.jpg',9999999,'iphone 12',96,1,1,1234567);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `userId` int NOT NULL AUTO_INCREMENT,
  `active` int NOT NULL DEFAULT '1',
  `email` varchar(255) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `otp` varchar(255) DEFAULT NULL,
  `otpExpiry` datetime(6) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,1,'daigianguoi@gmail.com','ha quoc toan',NULL,NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',0),(2,1,'daigianguoi+admin@gmail.com','ha quoc toan',NULL,NULL,'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `videos`
--

DROP TABLE IF EXISTS `videos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `videos` (
  `videoId` varchar(255) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `description` text,
  `poster` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `views` int DEFAULT NULL,
  `categoryId` int DEFAULT NULL,
  PRIMARY KEY (`videoId`),
  KEY `FKog6viaf9sjv9esq70xaae5mh6` (`categoryId`),
  CONSTRAINT `FKog6viaf9sjv9esq70xaae5mh6` FOREIGN KEY (`categoryId`) REFERENCES `categories` (`categoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `videos`
--

LOCK TABLES `videos` WRITE;
/*!40000 ALTER TABLE `videos` DISABLE KEYS */;
INSERT INTO `videos` VALUES ('v01',_binary '\0',NULL,NULL,'test',0,1);
/*!40000 ALTER TABLE `videos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-09-01 12:08:57
