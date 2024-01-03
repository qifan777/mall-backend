-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mall
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `dict`
--

DROP TABLE IF EXISTS `dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dict` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `key_name` varchar(36) NOT NULL,
  `dict_id` int NOT NULL,
  `dict_name` varchar(36) NOT NULL,
  `order_num` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict`
--

LOCK TABLES `dict` WRITE;
/*!40000 ALTER TABLE `dict` DISABLE KEYS */;
INSERT INTO `dict` VALUES ('0710c8a5-021c-404d-b09e-56db95c259e9','2023-11-24 17:16:08.824724','2023-11-24 17:16:08.824724','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','男性',1001,'性别',0),('29a1973c-e6cf-4d28-9828-e7c06aaad1e7','2023-11-24 17:17:25.728648','2023-11-24 17:17:25.728648','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','菜单',1002,'菜单类型',1),('33797262-6b8d-4de9-b8f1-2f50ea344c7e','2023-11-24 17:17:13.699956','2023-11-24 17:17:13.699956','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','目录',1002,'菜单类型',0),('caeec773-7afb-481a-a17f-0e0ea98508bf','2023-11-24 17:16:39.655409','2023-11-24 17:16:39.655409','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','女性',1001,'性别',1),('d0492c11-f7dd-4fed-9b44-57f7a0e21748','2023-11-24 17:16:50.885606','2023-11-24 17:16:50.885606','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','保密',1001,'性别',2);
/*!40000 ALTER TABLE `dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invite_history`
--

DROP TABLE IF EXISTS `invite_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invite_history` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `inviter_id` varchar(36) NOT NULL,
  `invitee_id` varchar(36) DEFAULT NULL,
  `status` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invite_history`
--

LOCK TABLES `invite_history` WRITE;
/*!40000 ALTER TABLE `invite_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `invite_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inviter`
--

DROP TABLE IF EXISTS `inviter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inviter` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `code` varchar(36) NOT NULL,
  `qr_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inviter`
--

LOCK TABLES `inviter` WRITE;
/*!40000 ALTER TABLE `inviter` DISABLE KEYS */;
INSERT INTO `inviter` VALUES ('dd660dc5-a7a7-4b6b-80bb-5e56f74f6529','2023-12-14 14:24:31.950166','2023-12-14 14:24:31.950166','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','rBg04Nyf0z','https://my-community.oss-cn-qingdao.aliyuncs.com/rBg04Nyf0z.png');
/*!40000 ALTER TABLE `inviter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `name` varchar(20) NOT NULL,
  `path` varchar(2000) NOT NULL,
  `parent_id` varchar(36) DEFAULT NULL,
  `order_num` int DEFAULT NULL,
  `menu_type` varchar(36) NOT NULL,
  `icon` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES ('138fca6d-59b5-426d-aded-84afa4beeafa','2023-11-26 22:16:29.702641','2023-11-26 23:07:09.048773','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','角色','/role',NULL,NULL,'29a1973c-e6cf-4d28-9828-e7c06aaad1e7','Avatar'),('1e6768da-de89-4eaa-85c2-d42bdbb283a5','2023-12-01 14:09:01.230396','2023-12-01 14:09:01.230396','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','测试菜单','/test',NULL,0,'29a1973c-e6cf-4d28-9828-e7c06aaad1e7','Menu'),('4f304427-7e76-4496-aeb6-cffa30adc6e7','2023-11-26 22:16:14.421361','2023-11-26 23:07:30.939641','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','菜单','/menu',NULL,0,'29a1973c-e6cf-4d28-9828-e7c06aaad1e7','Menu'),('5a15eec1-a4fd-4a27-8a8a-dce62b1492f0','2023-11-26 22:37:07.947343','2023-11-26 22:37:07.947343','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','权限管理','permission',NULL,NULL,'33797262-6b8d-4de9-b8f1-2f50ea344c7e',NULL),('862b65e1-da53-40df-8fbc-58ac5c8c5949','2023-12-01 14:09:01.193884','2023-12-01 14:09:01.193884','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','测试菜单','/test',NULL,0,'29a1973c-e6cf-4d28-9828-e7c06aaad1e7','Menu'),('8dd08373-090f-4a65-ae9d-2cbe9b9e9b3f','2023-12-12 17:01:55.624335','2023-12-12 17:01:55.624335','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','商品','/product',NULL,4,'29a1973c-e6cf-4d28-9828-e7c06aaad1e7','Goods'),('9126f6d3-7b8b-49ad-bf37-18c5f689f8f2','2023-11-26 22:37:57.615351','2023-11-26 22:37:57.615351','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','测试2','/test2','5a15eec1-a4fd-4a27-8a8a-dce62b1492f0',1,'33797262-6b8d-4de9-b8f1-2f50ea344c7e',NULL),('ae6040fe-8897-4a91-aa0d-a42ac7382870','2023-11-26 22:37:25.780177','2023-11-26 22:37:25.780177','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','测试1','/test','5a15eec1-a4fd-4a27-8a8a-dce62b1492f0',2,'29a1973c-e6cf-4d28-9828-e7c06aaad1e7',NULL),('beec6c16-c734-47b1-944a-bd05dab9a1e6','2023-12-12 17:03:03.903818','2023-12-12 17:03:03.903818','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','商品类别','/product-category',NULL,5,'29a1973c-e6cf-4d28-9828-e7c06aaad1e7','Collection'),('d8d093b7-edd7-4e78-b87d-3ccbce2762f9','2023-11-26 22:16:39.172847','2023-11-26 23:07:01.813553','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','用户','/user',NULL,NULL,'29a1973c-e6cf-4d28-9828-e7c06aaad1e7','Avatar'),('fe4b840c-cde0-4d7d-aa41-4ea65cd5d02c','2023-11-26 22:38:11.658542','2023-11-26 22:38:11.658542','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','测试3','/test3','9126f6d3-7b8b-49ad-bf37-18c5f689f8f2',NULL,'29a1973c-e6cf-4d28-9828-e7c06aaad1e7',NULL);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `image` varchar(255) NOT NULL,
  `brand` varchar(255) NOT NULL,
  `category_id` varchar(36) NOT NULL,
  `stock` int NOT NULL,
  `description` text NOT NULL,
  `tags` varchar(255) NOT NULL,
  `features` text NOT NULL,
  `specs` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES ('5df3d5d3-8526-499b-a549-33825f7c0ada','2023-12-13 15:36:18.952800','2023-12-13 16:06:26.473992','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','测试手机',1.00,'https://my-community.oss-cn-qingdao.aliyuncs.com/20231213153554OIP-C.jpg','xxx','ae29e680-632c-434b-b6a8-5966a4a33828',1,'手机的描述','标签1,标签2','22','33'),('bb13075c-9624-4556-b818-a5bdd55ea1f6','2023-12-12 18:03:16.537397','2023-12-12 18:03:16.537397','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','测试',2.00,'https://my-community.oss-cn-qingdao.aliyuncs.com/20231212180229起凡.jpg','测试','638064b6-c023-4d21-acd5-89f8357de4d4',0,'11','111','222','333'),('f833f9ea-007f-485b-9ae9-70c646879624','2023-12-13 15:37:05.164902','2023-12-13 16:06:09.762295','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','测试手机33',2.00,'https://my-community.oss-cn-qingdao.aliyuncs.com/2023121315364313007017414_1007442040.jpg','aaa','ae29e680-632c-434b-b6a8-5966a4a33828',1,'cc','便宜,好用','11','z');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_category`
--

DROP TABLE IF EXISTS `product_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_category` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  `parent_id` int DEFAULT NULL,
  `image` varchar(100) NOT NULL,
  `description` text NOT NULL,
  `sort_order` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_category`
--

LOCK TABLES `product_category` WRITE;
/*!40000 ALTER TABLE `product_category` DISABLE KEYS */;
INSERT INTO `product_category` VALUES ('638064b6-c023-4d21-acd5-89f8357de4d4','2023-12-12 17:04:19.936611','2023-12-12 17:04:19.936611','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','手机',NULL,'https://my-community.oss-cn-qingdao.aliyuncs.com/20231212170416起凡.jpg','测试',1),('ae29e680-632c-434b-b6a8-5966a4a33828','2023-12-12 17:04:39.122854','2023-12-12 17:04:39.122854','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','测试类别2',NULL,'https://my-community.oss-cn-qingdao.aliyuncs.com/20231212170435起凡.jpg','333',1);
/*!40000 ALTER TABLE `product_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `name` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_pk` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('1','2023-11-24 22:38:44.717166','2023-11-24 22:44:31.899147','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','管理员'),('17509f5c-9a6b-429c-b467-cadbd8873d2d','2023-11-24 22:44:51.888117','2023-12-01 10:54:48.145588','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','测试3'),('d5352808-e463-4af9-9252-6db6b7df2ca0','2023-11-26 22:51:53.551857','2023-11-26 23:02:49.919067','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','测试2');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_menu_rel`
--

DROP TABLE IF EXISTS `role_menu_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_menu_rel` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `role_id` varchar(36) NOT NULL,
  `menu_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_id` (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_menu_rel`
--

LOCK TABLES `role_menu_rel` WRITE;
/*!40000 ALTER TABLE `role_menu_rel` DISABLE KEYS */;
INSERT INTO `role_menu_rel` VALUES ('06dd39b4-0426-4bd5-ae89-faf0bd757812','2023-12-01 10:54:48.190908','2023-12-01 10:54:48.190908','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','17509f5c-9a6b-429c-b467-cadbd8873d2d','5a15eec1-a4fd-4a27-8a8a-dce62b1492f0'),('39c2113f-5486-443b-80fd-07b893e9c28f','2023-11-26 22:37:07.954733','2023-11-26 22:37:07.954733','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','1','5a15eec1-a4fd-4a27-8a8a-dce62b1492f0'),('41097e8b-8376-4d21-b36e-50d53561c811','2023-11-26 22:38:11.665406','2023-11-26 22:38:11.665406','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','1','fe4b840c-cde0-4d7d-aa41-4ea65cd5d02c'),('4ac31d4c-594c-4db2-9e32-47f0815a380c','2023-11-26 22:37:25.788690','2023-11-26 22:37:25.788690','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','1','ae6040fe-8897-4a91-aa0d-a42ac7382870'),('58e17199-d63d-4171-83dd-53b22862e29c','2023-12-01 10:54:48.195695','2023-12-01 10:54:48.195695','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','17509f5c-9a6b-429c-b467-cadbd8873d2d','d8d093b7-edd7-4e78-b87d-3ccbce2762f9'),('5cd551e2-8196-49a9-890a-7a974e538912','2023-11-26 22:16:29.711659','2023-11-26 22:16:29.711659','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','1','4f304427-7e76-4496-aeb6-cffa30adc6e7'),('5cd551e2-8196-49a9-890a-7a974e538914','2023-11-26 22:16:29.711659','2023-11-26 22:16:29.711659','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','1','138fca6d-59b5-426d-aded-84afa4beeafa'),('5cd551e2-8196-49a9-890a-7a974e538916','2023-11-26 23:02:49.937360','2023-11-26 23:02:49.937360','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','d5352808-e463-4af9-9252-6db6b7df2ca0','4f304427-7e76-4496-aeb6-cffa30adc6e7'),('7123cdb4-ae01-4d62-98f0-43a983b3b257','2023-11-26 22:37:57.621658','2023-11-26 22:37:57.621658','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','1','9126f6d3-7b8b-49ad-bf37-18c5f689f8f2'),('8c8a483f-b84f-4a8a-ab92-263fc2044198','2023-12-01 10:54:48.180701','2023-12-01 10:54:48.180701','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','17509f5c-9a6b-429c-b467-cadbd8873d2d','138fca6d-59b5-426d-aded-84afa4beeafa'),('b0838f57-7721-4b88-9a13-91dfb03ace6b','2023-11-26 23:02:49.937360','2023-11-26 23:02:49.937360','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','d5352808-e463-4af9-9252-6db6b7df2ca0','138fca6d-59b5-426d-aded-84afa4beeafa'),('b6181a80-3cb9-4c9f-87a2-7961a94d01b4','2023-11-26 22:16:39.188630','2023-11-26 22:16:39.188630','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','1','d8d093b7-edd7-4e78-b87d-3ccbce2762f9'),('b6257c55-6f8d-43c4-8aa6-fa9111eab150','2023-11-26 23:02:49.948068','2023-11-26 23:02:49.948068','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','d5352808-e463-4af9-9252-6db6b7df2ca0','5a15eec1-a4fd-4a27-8a8a-dce62b1492f0'),('bdf34a13-da0c-4698-9a90-2df8827e3fe2','2023-11-26 23:02:49.958275','2023-11-26 23:02:49.958275','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','d5352808-e463-4af9-9252-6db6b7df2ca0','d8d093b7-edd7-4e78-b87d-3ccbce2762f9'),('da0689d7-cf1c-46bc-aed2-9b5b914c49a6','2023-12-12 17:01:55.638519','2023-12-12 17:01:55.638519','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','1','8dd08373-090f-4a65-ae9d-2cbe9b9e9b3f'),('e77d5646-6311-4dc1-9869-b2c840820cec','2023-12-12 17:03:03.907263','2023-12-12 17:03:03.907263','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','1','beec6c16-c734-47b1-944a-bd05dab9a1e6');
/*!40000 ALTER TABLE `role_menu_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `nickname` varchar(20) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `gender` varchar(36) DEFAULT NULL,
  `phone` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('1cb4db50-66fa-4250-9916-73283e536fa0','2023-11-24 17:13:08.309406','2023-12-13 18:29:15.463151','起凡','https://my-community.oss-cn-qingdao.aliyuncs.com/202312131828470yjKKXb9uS4W3cb113099a48efe3a4840105c07e4644.jpeg','0710c8a5-021c-404d-b09e-56db95c259e9','13656987994','$2a$10$UeZxmCdikKwYFK.wk7lp8Oj6ZzoXUZKUhV9qFGXuc74.IqgDP9AU2'),('94bffa42-1f7e-42ca-9388-7194add41b3d','2023-11-25 10:08:25.757533','2023-11-29 17:08:14.631896','起凡','https://my-community.oss-cn-qingdao.aliyuncs.com/20231129170813起凡.jpg','d0492c11-f7dd-4fed-9b44-57f7a0e21748','13656987995','$2a$10$Jg/HX5Nf40i0vtkflDvxNOUABds/XZdba5fkpWIwr22Gpu3jX1Vqi');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role_rel`
--

DROP TABLE IF EXISTS `user_role_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role_rel` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `role_id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_id` (`role_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_rel`
--

LOCK TABLES `user_role_rel` WRITE;
/*!40000 ALTER TABLE `user_role_rel` DISABLE KEYS */;
INSERT INTO `user_role_rel` VALUES ('6cb8dcc8-9e99-4f5c-b89a-9da97f5d34a7','2023-11-29 17:09:43.796985','2023-11-29 17:09:43.796985','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','17509f5c-9a6b-429c-b467-cadbd8873d2d','1cb4db50-66fa-4250-9916-73283e536fa0'),('6ecdd88b-3ed1-4718-a148-1bd9ca5dc81a','2023-11-26 22:17:05.349398','2023-11-26 22:17:05.349398','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','1','1cb4db50-66fa-4250-9916-73283e536fa0'),('993afc3a-46d3-4340-9821-62e23af19622','2023-11-30 14:00:55.336997','2023-11-30 14:00:55.336997','1cb4db50-66fa-4250-9916-73283e536fa0','1cb4db50-66fa-4250-9916-73283e536fa0','d5352808-e463-4af9-9252-6db6b7df2ca0','1cb4db50-66fa-4250-9916-73283e536fa0');
/*!40000 ALTER TABLE `user_role_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_wechat`
--

DROP TABLE IF EXISTS `user_wechat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_wechat` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `open_id` varchar(30) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_wechat`
--

LOCK TABLES `user_wechat` WRITE;
/*!40000 ALTER TABLE `user_wechat` DISABLE KEYS */;
INSERT INTO `user_wechat` VALUES ('2857e316-ff58-4271-ac5e-c6df541673e6','2023-12-13 10:20:42.887448','2023-12-13 10:20:42.887448','oEheF5USRu6Y3qWjpb3wJPBfuejw','1cb4db50-66fa-4250-9916-73283e536fa0');
/*!40000 ALTER TABLE `user_wechat` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-14 22:57:53
