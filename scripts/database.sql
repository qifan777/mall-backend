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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `address` varchar(255) NOT NULL,
  `province` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `district` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `real_name` varchar(255) NOT NULL,
  `top` tinyint(1) NOT NULL,
  `details` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES ('a2d7c173-3445-4498-8951-39d2cc3010b2','2024-01-25 15:30:58.465746','2024-01-25 18:25:59.037309','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',26.075455,119.33508,'福建省福州市晋安区五里亭立交桥与连江北路交叉口正南方向100米左右','福建省','福州市','晋安区','13666666666','起凡',0,'1号楼'),('ef42e042-b54b-4888-aad4-79905f2e49d7','2024-01-25 16:22:49.652859','2024-01-25 18:26:01.917410','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',26.051222,119.342255,'福建省福州市台江区江滨中大道366号','福建省','福州市','台江区','13666666666','起凡',1,'501');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `agent`
--

DROP TABLE IF EXISTS `agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agent` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `tenant_id` varchar(32) DEFAULT NULL,
  `agent_no` varchar(58) NOT NULL DEFAULT '' COMMENT '代理商编号',
  `user_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '用户id',
  `level_id` varchar(32) NOT NULL DEFAULT '8' COMMENT '代理等级',
  `parent_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '上级代理id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agent`
--

LOCK TABLES `agent` WRITE;
/*!40000 ALTER TABLE `agent` DISABLE KEYS */;
/*!40000 ALTER TABLE `agent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `agent_level`
--

DROP TABLE IF EXISTS `agent_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agent_level` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `tenant_id` varchar(32) DEFAULT NULL,
  `name` varchar(255) NOT NULL COMMENT '等级名称',
  `rate` decimal(10,2) NOT NULL COMMENT '佣金比例',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agent_level`
--

LOCK TABLES `agent_level` WRITE;
/*!40000 ALTER TABLE `agent_level` DISABLE KEYS */;
/*!40000 ALTER TABLE `agent_level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `agent_rebate_record`
--

DROP TABLE IF EXISTS `agent_rebate_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agent_rebate_record` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `agent_id` varchar(32) NOT NULL COMMENT '代理人id',
  `order_id` varchar(32) NOT NULL COMMENT '返佣订单号',
  `wallet_record_id` varchar(32) NOT NULL COMMENT '钱包流水号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agent_rebate_record`
--

LOCK TABLES `agent_rebate_record` WRITE;
/*!40000 ALTER TABLE `agent_rebate_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `agent_rebate_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `base_order`
--

DROP TABLE IF EXISTS `base_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `base_order` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `payment_id` varchar(36) NOT NULL,
  `address_id` varchar(36) DEFAULT NULL,
  `coupon_user_id` varchar(36) DEFAULT NULL,
  `remark` varchar(255) NOT NULL,
  `status` varchar(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `base_order`
--

LOCK TABLES `base_order` WRITE;
/*!40000 ALTER TABLE `base_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `base_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `threshold_amount` decimal(10,2) NOT NULL,
  `released_quantity` int NOT NULL,
  `effective_date` datetime(6) NOT NULL,
  `expiration_date` datetime(6) NOT NULL,
  `type` varchar(36) NOT NULL,
  `scope` varchar(36) NOT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `discount` decimal(3,2) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `coupon_pk` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES ('11f61057ff4f4e5d84154eb1a3403b30','2024-02-23 09:35:35.858812','2024-03-01 15:55:08.460549','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',100.00,111,'2024-02-24 00:00:00.000000','2024-04-01 00:00:00.000000','DISCOUNT','GENERAL',NULL,9.00,'满100打9折'),('863cbb8f8f3940668b0cfce1d916af46','2024-02-23 09:34:29.627801','2024-03-01 17:46:05.797435','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',100.00,10,'2024-02-14 00:00:00.000000','2024-03-28 00:00:00.000000','REDUCE','GENERAL',5.00,NULL,'满100减5优惠券');
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon_use_record`
--

DROP TABLE IF EXISTS `coupon_use_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon_use_record` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `coupon_user_id` varchar(36) NOT NULL,
  `order_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon_use_record`
--

LOCK TABLES `coupon_use_record` WRITE;
/*!40000 ALTER TABLE `coupon_use_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `coupon_use_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon_user_rel`
--

DROP TABLE IF EXISTS `coupon_user_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon_user_rel` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `receive_type` varchar(36) NOT NULL,
  `coupon_id` varchar(36) NOT NULL,
  `status` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon_user_rel`
--

LOCK TABLES `coupon_user_rel` WRITE;
/*!40000 ALTER TABLE `coupon_user_rel` DISABLE KEYS */;
INSERT INTO `coupon_user_rel` VALUES ('138651abf04c4da0b2ecc0247a89cff3','2024-02-26 14:01:20.727699','2024-02-26 15:42:22.866240','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','GIFT','11f61057ff4f4e5d84154eb1a3403b30','UNUSED','0f07d638-f1bc-4011-88d8-6dc650ab06a7'),('3076bbf8182248dfb550dcc2771000bf','2024-02-26 15:35:53.313190','2024-02-26 15:35:53.313190','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','GIFT','863cbb8f8f3940668b0cfce1d916af46','UNUSED','0f07d638-f1bc-4011-88d8-6dc650ab06a7'),('6d9b32c572084d3081601a3dafb57346','2024-02-26 15:35:53.291193','2024-02-26 15:35:53.291193','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','GIFT','863cbb8f8f3940668b0cfce1d916af46','UNUSED','9426415c-8254-46dd-95fe-35fb4c307713'),('dee3e19e3acb4167855b6d0390e2a1ea','2024-02-26 15:36:25.414854','2024-02-26 15:42:15.599915','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','GIFT','11f61057ff4f4e5d84154eb1a3403b30','EXPIRED','9426415c-8254-46dd-95fe-35fb4c307713');
/*!40000 ALTER TABLE `coupon_user_rel` ENABLE KEYS */;
UNLOCK TABLES;

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
  `key_id` int NOT NULL,
  `key_en_name` varchar(255) NOT NULL,
  `key_name` varchar(36) NOT NULL,
  `dict_id` int NOT NULL,
  `dict_name` varchar(36) NOT NULL,
  `dict_en_name` varchar(255) NOT NULL,
  `order_num` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `dict_pk` (`key_id`,`dict_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict`
--

LOCK TABLES `dict` WRITE;
/*!40000 ALTER TABLE `dict` DISABLE KEYS */;
INSERT INTO `dict` VALUES ('0df1dc2f621745fdb950359795befc5d','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',2,'APPROVED','已同意',1005,'退款状态','REFUND_STATUS',0),('13059f87f7c84d319fd3f6038cedea9b','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',0,'DISCOUNT','折扣券',1006,'优惠券类型','COUPON_TYPE',0),('1a687b7d-9b24-47b0-aa57-e361812dcdf0','2024-01-26 11:10:49.521488','2024-01-26 11:10:49.521488','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',0,'WE_CHAT_PAY','微信支付',1004,'支付类型','PAY_TYPE',0),('1f01fa7b-f162-4376-870d-9207735f658d','2024-01-16 09:33:09.151337','2024-01-16 09:33:09.151337','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',2,'BUTTON','按钮',1002,'菜单类型','MENU_TYPE',2),('29f91a9b6a1f4711b73f27a32de8d10e','2024-02-21 09:02:07.356526','2024-02-21 09:02:07.356526','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',1,'TO_BE_APPROVE','待审批',1005,'退款状态','REFUND_STATUS',0),('2a8a7427-9fb6-4ecb-822c-8b22fd493a93','2024-01-26 11:08:34.090534','2024-01-26 11:08:37.984915','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',4,'CLOSED','已关闭',1003,'商品订单状态','PRODUCT_ORDER_STATUS',0),('3860dff4-7f22-4ded-bc30-19cd1b4bc098','2024-01-16 09:30:39.144272','2024-01-16 09:33:15.663135','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',0,'PAGE','页面',1002,'菜单类型','MENU_TYPE',1),('416c90b4-42e8-4af1-a3f5-7e321c9c3437','2024-01-16 09:32:28.555205','2024-01-16 09:32:28.555205','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',1,'DIRECTORY','目录',1002,'菜单类型','MENU_TYPE',0),('461a361d-073c-4574-aed1-c025e04a81a3','2024-01-26 11:09:32.434369','2024-01-26 11:13:54.428416','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',5,'REFUNDING','退款中',1003,'商品订单状态','PRODUCT_ORDER_STATUS',0),('56e8d930-6953-4f6a-875c-34d5c26802a5','2024-01-26 11:03:49.162351','2024-01-26 11:04:00.418344','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',2,'TO_BE_RECEIVED','待收货',1003,'商品订单状态','PRODUCT_ORDER_STATUS',0),('5c820b53-6545-45fd-8442-22f7e486fc8e','2024-01-26 10:56:45.364997','2024-01-26 11:02:58.744868','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',0,'TO_BE_PAID','待付款',1003,'商品订单状态','PRODUCT_ORDER_STATUS',0),('5fba34ff-760c-453a-9ce3-284ed68710ca','2024-01-14 15:23:55.954376','2024-01-14 15:23:55.954376','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',2,'PRIVATE','保密',1001,'性别','GENDER',0),('7d11e97eb4d7474ca2dab8cb50a32ca8','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',2,'CATEGORY','品类券',1007,'优惠券使用范围','COUPON_SCOPE',0),('7d11e97eb4d7474ca2dab8cb50c32ca8','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',1,'PRODUCT','商品券',1007,'优惠券使用范围','COUPON_SCOPE',0),('7d11e97eb4d7474ca2dab8cb50c34ca1','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',3,'REJECTED','已拒绝',1005,'退款状态','REFUND_STATUS',0),('7d11e97eb4d7474ca2dab8cb50c34ca8','2024-02-21 09:01:02.962067','2024-02-21 09:01:02.962067','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',0,'TO_BE_CREATE','待创建',1005,'退款状态','REFUND_STATUS',0),('7d11e97eb4d7474ca2dab8cb50c34cab','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',1,'USED','已使用',1008,'优惠券使用状态','COUPON_USE_STATUS',0),('7d11e97eb4d7474ca2dab8cb50c34cah','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',0,'GIFT','系统赠送',1009,'优惠券获取方式','COUPON_RECEIVE_TYPE',0),('7d11e97eb4d7474ca2dab8cb50c34caz','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',2,'EXPIRED','已过期',1008,'优惠券使用状态','COUPON_USE_STATUS',0),('7d11e97eb4d7474ca2dab8cb50c34cc8','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',0,'GENERAL','通用券',1007,'优惠券使用范围','COUPON_SCOPE',0),('7d11e97eb4d7474ca2dzb8cb50c34ca1','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',0,'UNUSED','未使用',1008,'优惠券使用状态','COUPON_USE_STATUS',0),('a1a13655-7328-45c3-8cdd-dc0d41ef5792','2024-01-26 11:06:10.939935','2024-01-26 11:06:16.216645','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',3,'TO_BE_EVALUATED','待评价',1003,'商品订单状态','PRODUCT_ORDER_STATUS',0),('a268e25b-b3b7-4fc2-880d-5b97e1acab0b','2024-01-26 11:11:40.133277','2024-01-26 11:11:40.133277','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',1,'ALI_PAY','支付宝',1004,'支付类型','PAY_TYPE',0),('a3caf40e-a1ef-4a77-8096-f467fb14060e','2024-01-10 10:50:18.555224','2024-01-11 15:49:22.959501','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',0,'MALE','男',1001,'性别','GENDER',0),('b3366061-0be1-45f9-98de-5a86753665ce','2024-01-10 13:55:26.468101','2024-01-10 13:55:26.468101','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',1,'FEMALE','女',1001,'性别','GENDER',1),('f0e0c95e7ad249deb9359691d009fd7a','2024-02-18 15:08:27.511795','2024-02-18 15:10:19.268405','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',6,'TO_BE_CREATE','待创建',1003,'商品订单状态','PRODUCT_ORDER_STATUS',0),('f0e0c95e7ad249deb9359691d009fd7c','2024-02-22 15:58:22.896688','2024-02-22 15:58:34.067738','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',1,'REDUCE','满减券',1006,'优惠券类型','COUPON_TYPE',0),('f0e0c95e7ad249deb9359691d009fd7h','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',0,'REBATE','返佣',1010,'钱包操作类型','WALLET_RECORD_TYPE',0),('f0e0c95e7ad249deb9359691d109fd7h','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',1,'RECHARGE','充值',1010,'钱包操作类型','WALLET_RECORD_TYPE',0),('f0e0c95e7ad249deb9359691d109zd5h','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',0,'FIRST','1级',1011,'代理等级','AGENT_LEVEL',0),('f0e0c95e7ad249deb9359691d109zd6h','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',1,'SECOND','2级',1011,'代理等级','AGENT_LEVEL',0),('f0e0c95e7ad249deb9359691d109zd7h','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',2,'WITHDRAW','提现',1010,'钱包操作类型','WALLET_RECORD_TYPE',0),('f0e0c95e7ad249deb9359691d109zd8h','2024-02-21 09:05:02.936174','2024-02-21 09:05:13.494713','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',2,'THIRD','3级',1011,'代理等级','AGENT_LEVEL',0),('fc930d38-0612-4217-91ab-809a5be03656','2024-01-26 11:02:08.987958','2024-01-26 11:02:22.277984','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7',1,'TO_BE_DELIVERED','待发货',1003,'商品订单状态	','PRODUCT_ORDER_STATUS',0);
/*!40000 ALTER TABLE `dict` ENABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES ('00f2290a-cde5-4e95-9294-4e7599b1a3f5','2024-01-16 11:21:02.655367','2024-01-17 10:12:26.403455','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','权限管理','/permission',NULL,0,'DIRECTORY','Lock'),('0c30e2f6-8cef-4ee6-999e-a18af97a3a26','2024-01-16 14:33:09.062999','2024-01-17 10:10:02.922860','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','用户','/user','00f2290a-cde5-4e95-9294-4e7599b1a3f5',1,'PAGE','User'),('16b68322-193d-4be4-b71b-bbc0f18b0d3f','2024-01-16 14:33:42.204639','2024-01-16 20:51:06.437836','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','商品管理','/test',NULL,NULL,'DIRECTORY',NULL),('349b39a5-9500-4c22-9972-f22b935aba42','2024-01-17 10:12:54.930459','2024-01-17 10:13:12.046479','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','角色','/role','00f2290a-cde5-4e95-9294-4e7599b1a3f5',2,'PAGE','Avatar'),('43a710c85f8a43fe9602be9ceed50d1f','2024-02-23 09:33:23.664224','2024-02-23 09:33:23.664224','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','优惠券','/coupon','c9cf9ed3f15d4e20b37c672500311324',0,'PAGE','Ticket'),('6c0d0f67db5d4418b974c685af43f7e0','2024-02-26 14:09:15.022676','2024-02-26 14:09:15.022676','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','用户优惠券','/coupon-user','c9cf9ed3f15d4e20b37c672500311324',1,'PAGE','User'),('8246938d-50f1-4463-9ff1-8f4097df2b7a','2024-01-18 17:02:00.136884','2024-01-18 17:02:00.136884','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','商品管理','/product','16b68322-193d-4be4-b71b-bbc0f18b0d3f',0,'PAGE','Goods'),('839c9fd0-8cf0-4070-a50f-16981bf649f8','2024-01-19 11:12:10.495833','2024-01-19 11:12:10.495833','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','商品SKU','/product-sku','16b68322-193d-4be4-b71b-bbc0f18b0d3f',3,'PAGE','ShoppingCartFull'),('8ea32b3d-90e4-4d76-a777-413917166a32','2024-01-16 11:20:24.133270','2024-01-17 10:13:01.821007','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','菜单','/menu','00f2290a-cde5-4e95-9294-4e7599b1a3f5',3,'PAGE','Menu'),('a350090e-11ff-45d0-88ce-5c3c1b6f9ef4','2024-01-26 10:06:09.672544','2024-01-26 10:06:09.672544','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','系统管理','/system',NULL,0,'DIRECTORY','House'),('c32ce807a69c43e8bdf67e27974920ab','2024-02-20 09:28:14.813332','2024-02-20 09:28:14.813332','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','商品订单','/product-order','16b68322-193d-4be4-b71b-bbc0f18b0d3f',0,'PAGE','Tickets'),('c9cf9ed3f15d4e20b37c672500311324','2024-02-23 09:32:44.008524','2024-02-23 09:32:44.008524','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','优惠券管理','coupon-manage',NULL,4,'DIRECTORY','Ticket'),('d5510c37-c5e9-48b3-b41b-2a0fa710c94e','2024-01-18 14:36:19.589318','2024-01-18 14:36:19.589318','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','商品类别','/product-category','16b68322-193d-4be4-b71b-bbc0f18b0d3f',0,'PAGE','Memo'),('fcf86780-d447-4a60-96b8-4f03c4eddc92','2024-01-26 10:07:04.713450','2024-01-26 10:07:04.713450','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','字典','/dict','a350090e-11ff-45d0-88ce-5c3c1b6f9ef4',0,'PAGE','Notebook');
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `pay_type` varchar(36) NOT NULL,
  `pay_time` datetime DEFAULT NULL,
  `pay_amount` decimal(10,2) NOT NULL,
  `vip_amount` decimal(10,2) NOT NULL,
  `coupon_amount` decimal(10,2) NOT NULL,
  `product_amount` decimal(10,2) NOT NULL,
  `delivery_fee` decimal(10,2) NOT NULL,
  `trade_no` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES ('5973c96d6bc944729a1bedd006be0077','2024-02-18 15:50:29.866406','2024-02-18 15:50:29.866406','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','WE_CHAT_PAY',NULL,0.01,0.00,0.00,0.01,0.00,NULL),('7898af0950d64d8488607485898402d5','2024-02-18 10:50:57.142714','2024-02-18 10:50:57.142714','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','WE_CHAT_PAY',NULL,0.01,0.00,0.00,0.01,0.00,NULL),('89d1146f6d7942ba9f1a026fb3638458','2024-02-07 09:05:54.098899','2024-02-07 09:05:54.098899','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','WE_CHAT_PAY',NULL,1.00,0.00,0.00,0.01,0.00,''),('b4611b841f7342b1870b986922e8db65','2024-03-01 17:46:21.456667','2024-03-01 17:46:21.456667','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','WE_CHAT_PAY',NULL,7994.00,0.00,5.00,7999.00,0.00,NULL),('b8b0f7e5262340a39707947c67bd605e','2024-03-01 15:54:31.704877','2024-03-01 15:54:31.704877','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','WE_CHAT_PAY',NULL,0.01,0.00,0.00,0.01,0.00,NULL),('dbcf63c9d8a54637ade17a61f65a513a','2024-02-19 21:58:00.828088','2024-02-19 21:58:00.828088','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','WE_CHAT_PAY',NULL,0.01,0.00,0.00,0.01,0.00,NULL),('f35fe42738c74691bf9c7dcb5ff21cfc','2024-02-18 15:36:49.774651','2024-02-18 15:36:49.774651','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','WE_CHAT_PAY',NULL,0.01,0.00,0.00,0.01,0.00,NULL);
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
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
  `cover` varchar(255) NOT NULL,
  `brand` varchar(255) NOT NULL,
  `category_id` varchar(36) NOT NULL,
  `stock` int NOT NULL,
  `description` text NOT NULL,
  `tags` varchar(255) NOT NULL,
  `specifications` text NOT NULL,
  `attributes` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES ('04999440-6a62-46ae-8ec3-a25b374fc821','2024-01-19 14:31:27.312976','2024-01-20 14:49:48.728173','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','华为Mate 60 Pro',7999.00,'https://img12.360buyimg.com/n1/s450x450_jfs/t1/235988/15/12433/48078/65a8f4faF38ef4d31/fc8ca7c491173fe4.jpg','华为','a4518fb9-6879-44a5-8ad2-783867ba46d7',1,'4060','[\"性能很好\",\"颜值高\"]','[{\"name\":\"充电功率\",\"values\":[\"80-119W\"]},{\"name\":\"质保期\",\"values\":[\"3年\"]}]','[{\"name\":\"颜色\",\"values\":[\"雅川青\",\"雅丹黑\"]},{\"name\":\"版本\",\"values\":[\"12gb+256gb\",\"12gb+512gb\"]}]'),('04999440-6a62-46ae-8ec3-a25b374fc829','2024-01-19 14:31:27.312976','2024-01-20 14:49:48.728173','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','华为Mate 60 Pro',7999.00,'https://img12.360buyimg.com/n1/s450x450_jfs/t1/235988/15/12433/48078/65a8f4faF38ef4d31/fc8ca7c491173fe4.jpg','华为','a4518fb9-6879-44a5-8ad2-783867ba46d7',1,'4060','[\"性能很好\",\"颜值高\"]','[{\"name\":\"充电功率\",\"values\":[\"80-119W\"]},{\"name\":\"质保期\",\"values\":[\"3年\"]}]','[{\"name\":\"颜色\",\"values\":[\"雅川青\",\"雅丹黑\"]},{\"name\":\"版本\",\"values\":[\"12gb+256gb\",\"12gb+512gb\"]}]'),('fcd61ee7-ff1f-447c-1131-05c6dfea64b0','2024-01-19 14:31:27.312976','2024-01-20 14:49:48.728173','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','华为Mate 60 Pro',7999.00,'https://img12.360buyimg.com/n1/s450x450_jfs/t1/235988/15/12433/48078/65a8f4faF38ef4d31/fc8ca7c491173fe4.jpg','华为','a4518fb9-6879-44a5-8ad2-783867ba46d7',1,'4060','[\"性能很好\",\"颜值高\"]','[{\"name\":\"充电功率\",\"values\":[\"80-119W\"]},{\"name\":\"质保期\",\"values\":[\"3年\"]}]','[{\"name\":\"颜色\",\"values\":[\"雅川青\",\"雅丹黑\"]},{\"name\":\"版本\",\"values\":[\"12gb+256gb\",\"12gb+512gb\"]}]'),('fcd61ee7-ff1f-447c-1131-05c6dfea64b7','2024-01-22 16:25:52.694286','2024-01-20 14:49:48.728173','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','华为Mate 60 Pro',7999.00,'https://img12.360buyimg.com/n1/s450x450_jfs/t1/235988/15/12433/48078/65a8f4faF38ef4d31/fc8ca7c491173fe4.jpg','华为','a4518fb9-6879-44a5-8ad2-783867ba46d7',1,'4060','[\"性能很好\",\"颜值高\"]','[{\"name\":\"充电功率\",\"values\":[\"80-119W\"]},{\"name\":\"质保期\",\"values\":[\"3年\"]}]','[{\"name\":\"颜色\",\"values\":[\"雅川青\",\"雅丹黑\"]},{\"name\":\"版本\",\"values\":[\"12gb+256gb\",\"12gb+512gb\"]}]'),('fcd61ee7-ff1f-448c-1131-05c6dfea64b7','2024-01-19 14:31:27.312976','2024-01-22 16:25:52.694286','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银',4199.00,'https://img10.360buyimg.com/n1/jfs/t1/148460/39/40865/58528/65ad1436F567c3e26/9f121abdd7913e20.jpg','华为（HUAWEI）','9eba1038-5f7d-4440-a1b2-c9bd91ea15b4',7,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','[\"性能很好\",\"颜值高\"]','[{\"name\":\"屏幕比例\",\"values\":[\"16:10\"]},{\"name\":\"能效等级\",\"values\":[\"一级能效\"]},{\"name\":\"屏幕色域\",\"values\":[\"100%sRGB\"]}]','[{\"name\":\"颜色\",\"values\":[\"皓月银\",\"深空灰\"]},{\"name\":\"型号\",\"values\":[\"I5+16GB+512GB\",\"I7+16GB+1T\"]}]'),('fcd61ee7-ff1f-448c-1131-05c6dfec64b7','2024-01-22 16:25:52.694286','2024-01-22 16:25:52.694286','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银',4199.00,'https://img10.360buyimg.com/n1/jfs/t1/148460/39/40865/58528/65ad1436F567c3e26/9f121abdd7913e20.jpg','华为（HUAWEI）','9eba1038-5f7d-4440-a1b2-c9bd91ea15b4',7,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','[\"性能很好\",\"颜值高\"]','[{\"name\":\"屏幕比例\",\"values\":[\"16:10\"]},{\"name\":\"能效等级\",\"values\":[\"一级能效\"]},{\"name\":\"屏幕色域\",\"values\":[\"100%sRGB\"]}]','[{\"name\":\"颜色\",\"values\":[\"皓月银\",\"深空灰\"]},{\"name\":\"型号\",\"values\":[\"I5+16GB+512GB\",\"I7+16GB+1T\"]}]'),('fcd61ee7-ff1f-448c-8131-05c6dfea14b8','2024-01-22 16:25:52.694286','2024-01-22 16:25:52.694286','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','索尼（SONY）PS5 PlayStation5（轻薄版 1TB）数字版 国行PS5游戏机',2999.00,'https://img12.360buyimg.com/n7/jfs/t1/249378/1/3423/71589/65a788faF19f96be1/128abdbb059b0bd9.jpg','索尼（SONY）','9eba1038-5f7d-4440-a1b2-c9bd91ea15b4',7,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','[\"性能很好\",\"颜值高\"]','[{\"name\":\"屏幕比例\",\"values\":[\"16:10\"]},{\"name\":\"能效等级\",\"values\":[\"一级能效\"]},{\"name\":\"屏幕色域\",\"values\":[\"100%sRGB\"]}]','[{\"name\":\"颜色\",\"values\":[\"皓月银\",\"深空灰\"]},{\"name\":\"型号\",\"values\":[\"I5+16GB+512GB\",\"I7+16GB+1T\"]}]'),('fcd61ee7-ff1f-448c-8131-05c6dfea64b7','2024-01-22 16:25:52.694286','2024-01-22 16:25:52.694286','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','狼蛛（AULA） F99客制化机械键盘gasket结构全键热插拔有线蓝牙无线三模办公电竞游戏 星落凝云-收割者轴\n',259.00,'https://img13.360buyimg.com/n7/jfs/t1/249540/23/1636/148199/6593c2c1F5b7c6041/7c04e37a0da39862.jpg','狼蛛（AULA）','9eba1038-5f7d-4440-a1b2-c9bd91ea15b4',7,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','[\"性能很好\",\"颜值高\"]','[{\"name\":\"屏幕比例\",\"values\":[\"16:10\"]},{\"name\":\"能效等级\",\"values\":[\"一级能效\"]},{\"name\":\"屏幕色域\",\"values\":[\"100%sRGB\"]}]','[{\"name\":\"颜色\",\"values\":[\"皓月银\",\"深空灰\"]},{\"name\":\"型号\",\"values\":[\"I5+16GB+512GB\",\"I7+16GB+1T\"]}]'),('fcd61ee7-ff1f-448c-8131-05c6dfea64bc','2024-01-22 16:25:52.694286','2024-01-22 16:25:52.694286','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','狼蛛（AULA） F99客制化机械键盘gasket结构全键热插拔有线蓝牙无线三模办公电竞游戏 星落凝云-收割者轴\n',259.00,'https://img13.360buyimg.com/n7/jfs/t1/249540/23/1636/148199/6593c2c1F5b7c6041/7c04e37a0da39862.jpg','狼蛛（AULA）','9eba1038-5f7d-4440-a1b2-c9bd91ea15b4',7,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','[\"性能很好\",\"颜值高\"]','[{\"name\":\"屏幕比例\",\"values\":[\"16:10\"]},{\"name\":\"能效等级\",\"values\":[\"一级能效\"]},{\"name\":\"屏幕色域\",\"values\":[\"100%sRGB\"]}]','[{\"name\":\"颜色\",\"values\":[\"皓月银\",\"深空灰\"]},{\"name\":\"型号\",\"values\":[\"I5+16GB+512GB\",\"I7+16GB+1T\"]}]'),('fcd61ee7-ff1f-448c-8131-05c6dfea64bz','2024-01-19 14:31:27.312976','2024-01-20 14:49:48.728173','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','华为Mate 60 Pro',7999.00,'https://img12.360buyimg.com/n1/s450x450_jfs/t1/235988/15/12433/48078/65a8f4faF38ef4d31/fc8ca7c491173fe4.jpg','华为','a4518fb9-6879-44a5-8ad2-783867ba46d7',1,'4060','[\"性能很好\",\"颜值高\"]','[{\"name\":\"充电功率\",\"values\":[\"80-119W\"]},{\"name\":\"质保期\",\"values\":[\"3年\"]}]','[{\"name\":\"颜色\",\"values\":[\"雅川青\",\"雅丹黑\"]},{\"name\":\"版本\",\"values\":[\"12gb+256gb\",\"12gb+512gb\"]}]');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_attribute`
--

DROP TABLE IF EXISTS `product_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_attribute` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `values` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `product_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_attribute`
--

LOCK TABLES `product_attribute` WRITE;
/*!40000 ALTER TABLE `product_attribute` DISABLE KEYS */;
INSERT INTO `product_attribute` VALUES ('ea831944-c5a1-4b43-a3d5-3c55d403fb9d','2024-01-18 17:43:24.422948','2024-01-18 17:48:48.329247','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','33211','233','9f26edbe-7066-42f0-97c2-8602972db5c7');
/*!40000 ALTER TABLE `product_attribute` ENABLE KEYS */;
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
  `parent_id` varchar(36) DEFAULT NULL,
  `image` varchar(100) DEFAULT NULL,
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
INSERT INTO `product_category` VALUES ('51eac0d9-a653-4edb-b156-5a7d96648196','2024-01-18 14:40:58.923272','2024-01-18 14:40:58.923272','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','显卡','9eba1038-5f7d-4440-a1b2-c9bd91ea15b4','','显卡',1),('9eba1038-5f7d-4440-a1b2-c9bd91ea15b4','2024-01-18 14:39:20.619126','2024-01-18 14:39:20.619126','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','电脑',NULL,'','电脑',0),('a4518fb9-6879-44a5-8ad2-783867ba46d7','2024-01-18 14:40:06.789083','2024-01-18 14:40:06.789083','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','内存条','9eba1038-5f7d-4440-a1b2-c9bd91ea15b4','','内存条',1);
/*!40000 ALTER TABLE `product_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_order`
--

DROP TABLE IF EXISTS `product_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_order` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `payment_id` varchar(36) NOT NULL,
  `address_id` varchar(36) NOT NULL,
  `status` varchar(36) NOT NULL,
  `remark` text,
  `tracking_number` varchar(50) DEFAULT NULL,
  `coupon_user_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_order`
--

LOCK TABLES `product_order` WRITE;
/*!40000 ALTER TABLE `product_order` DISABLE KEYS */;
INSERT INTO `product_order` VALUES ('0565260b12fb40418c472761f997d833','2024-02-07 09:05:54.114409','2024-02-20 16:03:24.468172','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','89d1146f6d7942ba9f1a026fb3638458','ef42e042-b54b-4888-aad4-79905f2e49d7','TO_BE_RECEIVED','','YT7442137959572',NULL),('1a867a0724d549038900ad97e192a468','2024-02-18 15:36:49.787650','2024-02-20 16:13:39.765585','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','f35fe42738c74691bf9c7dcb5ff21cfc','ef42e042-b54b-4888-aad4-79905f2e49d7','TO_BE_RECEIVED','','YT7442137959572',NULL),('80b4f0c9d760406c9f9ed7924f1d4c14','2024-02-18 15:50:29.880407','2024-02-18 15:50:29.880407','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','5973c96d6bc944729a1bedd006be0077','ef42e042-b54b-4888-aad4-79905f2e49d7','TO_BE_PAID','',NULL,NULL),('9723bc129b0f406794d0ae6189ba2057','2024-02-18 10:50:57.157233','2024-02-18 15:33:00.603908','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','7898af0950d64d8488607485898402d5','ef42e042-b54b-4888-aad4-79905f2e49d7','CLOSED','',NULL,NULL),('9f9f487cec4b401cb89a769afdcc4049','2024-02-19 21:58:00.842349','2024-02-19 21:58:00.842349','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','dbcf63c9d8a54637ade17a61f65a513a','ef42e042-b54b-4888-aad4-79905f2e49d7','TO_BE_PAID','',NULL,NULL),('bc1c1e9380c14918bc0a2dec28525be9','2024-03-01 17:46:21.483693','2024-03-01 17:46:21.483693','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','b4611b841f7342b1870b986922e8db65','ef42e042-b54b-4888-aad4-79905f2e49d7','TO_BE_PAID','',NULL,'3076bbf8182248dfb550dcc2771000bf'),('f3786417ead44ea9b80c2c8f7c1bb380','2024-03-01 15:54:31.715485','2024-03-01 15:54:31.715485','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','b8b0f7e5262340a39707947c67bd605e','ef42e042-b54b-4888-aad4-79905f2e49d7','TO_BE_PAID','',NULL,NULL);
/*!40000 ALTER TABLE `product_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_order_item`
--

DROP TABLE IF EXISTS `product_order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_order_item` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `product_order_id` varchar(36) NOT NULL,
  `product_sku_id` varchar(36) NOT NULL,
  `count` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_order_item`
--

LOCK TABLES `product_order_item` WRITE;
/*!40000 ALTER TABLE `product_order_item` DISABLE KEYS */;
INSERT INTO `product_order_item` VALUES ('11c57f674593409b875a580f05eb9031','2024-03-01 17:46:21.522916','2024-03-01 17:46:21.522916','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','bc1c1e9380c14918bc0a2dec28525be9','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('30f05cc48b7c44dfaadaee7836c0da3c','2024-02-07 09:05:54.124921','2024-02-18 09:58:50.390114','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0565260b12fb40418c472761f997d833','aa8c50ad-7ea7-407f-adbb-d84daa9aa66b',1),('33767114a4964544be06a99c56e0ce3d','2024-02-18 15:36:49.797649','2024-02-18 15:49:42.199027','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','1a867a0724d549038900ad97e192a468','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('40271bb7a3f8425fb25228799908e35e','2024-03-01 15:54:31.722130','2024-03-01 15:54:31.722130','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','f3786417ead44ea9b80c2c8f7c1bb380','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('63fed7f01bec449ca85acd0d36d1151e','2024-02-19 21:58:00.852825','2024-02-19 21:58:00.852825','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','9f9f487cec4b401cb89a769afdcc4049','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('849997c896914e4bb5fc448c5c7c0ce6','2024-02-18 15:50:29.893409','2024-02-18 15:50:29.893409','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','80b4f0c9d760406c9f9ed7924f1d4c14','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('91169746b22041b8bc8e0e9d8adbd09d','2024-02-07 09:05:54.131434','2024-02-18 09:58:50.397115','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0565260b12fb40418c472761f997d833','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1),('e558d28d11694a629e83dbc01cc64327','2024-02-18 10:50:57.167740','2024-02-18 15:33:00.615428','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','9723bc129b0f406794d0ae6189ba2057','8fbe2304-26da-4b18-9654-517ee3d0aa4e',1);
/*!40000 ALTER TABLE `product_order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_sku`
--

DROP TABLE IF EXISTS `product_sku`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_sku` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `values` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `product_id` varchar(255) NOT NULL,
  `cover` varchar(255) DEFAULT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_sku`
--

LOCK TABLES `product_sku` WRITE;
/*!40000 ALTER TABLE `product_sku` DISABLE KEYS */;
INSERT INTO `product_sku` VALUES ('14b496e7-9538-4428-84ae-3f64e55bab0e','2024-01-24 09:41:39.266530','2024-01-24 09:41:39.266530','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"皓月银\",\"I5+16GB+512GB\"]','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','fcd61ee7-ff1f-448c-8131-05c6dfea64b8','https://img10.360buyimg.com/n1/jfs/t1/148460/39/40865/58528/65ad1436F567c3e26/9f121abdd7913e20.jpg',4199.00,13,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银'),('25bf6840-482e-4808-988f-7acec21703ea','2024-01-24 09:41:59.466059','2024-01-24 09:41:59.466059','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"皓月银\",\"I5+16GB+512GB\"]','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','fcd61ee7-ff1f-448c-1131-05c6dfea64b7','https://img10.360buyimg.com/n1/jfs/t1/148460/39/40865/58528/65ad1436F567c3e26/9f121abdd7913e20.jpg',4199.00,4,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银'),('4e24ecdd-cd7c-4b31-83ef-a7fda9a699a4','2024-01-24 09:41:39.351579','2024-01-24 09:41:39.351579','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"深空灰\",\"I7+16GB+1T\"]','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','fcd61ee7-ff1f-448c-8131-05c6dfea64b8','https://img10.360buyimg.com/n1/jfs/t1/148460/39/40865/58528/65ad1436F567c3e26/9f121abdd7913e20.jpg',4199.00,13,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银'),('6dfbceeb-9a6b-4902-a896-be75b5238019','2024-01-23 09:10:57.906429','2024-01-23 09:13:57.814821','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"深空灰\",\"I7+16GB+1T\"]','索尼（SONY）PS5 PlayStation5（轻薄版 1TB）数字版 国行PS5游戏机','fcd61ee7-ff1f-448c-8131-05c6dfea14b8','https://img12.360buyimg.com/n7/jfs/t1/249378/1/3423/71589/65a788faF19f96be1/128abdbb059b0bd9.jpg',3002.00,0,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银'),('746aa96e-3873-4165-9fba-798274c76080','2024-01-19 15:01:05.412403','2024-01-19 15:51:59.842536','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"雅丹黑\",\"12gb+512gb\"]','华为Mate 60 Pro','04999440-6a62-46ae-8ec3-a25b374fc821','https://img12.360buyimg.com/n1/s450x450_jfs/t1/235988/15/12433/48078/65a8f4faF38ef4d31/fc8ca7c491173fe4.jpg',7999.00,1000,'40602'),('8fbe2304-26da-4b18-9654-517ee3d0aa4e','2024-01-19 15:01:05.387563','2024-02-18 15:49:42.195844','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"雅川青\",\"12gb+256gb\"]','华为Mate 60 Pro','04999440-6a62-46ae-8ec3-a25b374fc821','https://img12.360buyimg.com/n1/s450x450_jfs/t1/235988/15/12433/48078/65a8f4faF38ef4d31/fc8ca7c491173fe4.jpg',7999.00,996,'4060'),('95f86932-de63-465b-b8d4-3c67f08ea94a','2024-01-23 09:10:57.896919','2024-01-23 09:14:04.021355','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"深空灰\",\"I5+16GB+512GB\"]','索尼（SONY）PS5 PlayStation5（轻薄版 1TB）数字版 国行PS5游戏机','fcd61ee7-ff1f-448c-8131-05c6dfea14b8','https://img12.360buyimg.com/n7/jfs/t1/249378/1/3423/71589/65a788faF19f96be1/128abdbb059b0bd9.jpg',4500.00,0,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银'),('9fd6c8e4-bcb2-4ac6-93b4-23feaeb802cc','2024-01-24 09:41:59.487426','2024-01-24 09:41:59.487426','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"深空灰\",\"I5+16GB+512GB\"]','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','fcd61ee7-ff1f-448c-1131-05c6dfea64b7','https://img10.360buyimg.com/n1/jfs/t1/148460/39/40865/58528/65ad1436F567c3e26/9f121abdd7913e20.jpg',4199.00,4,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银'),('aa8c50ad-7ea7-407f-adbb-d84daa9aa66b','2024-01-23 09:10:57.867081','2024-02-18 09:58:50.386106','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"皓月银\",\"I5+16GB+512GB\"]','索尼（SONY）PS5 PlayStation5（轻薄版 1TB）数字版 国行PS5游戏机','fcd61ee7-ff1f-448c-8131-05c6dfea14b8','https://img12.360buyimg.com/n7/jfs/t1/249378/1/3423/71589/65a788faF19f96be1/128abdbb059b0bd9.jpg',2999.00,0,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银'),('b51e1a2a-54f3-4d55-95a1-1ef5693519a3','2024-01-23 09:10:57.885625','2024-01-23 09:14:10.238193','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"皓月银\",\"I7+16GB+1T\"]','索尼（SONY）PS5 PlayStation5（轻薄版 1TB）数字版 国行PS5游戏机','fcd61ee7-ff1f-448c-8131-05c6dfea14b8','https://img12.360buyimg.com/n7/jfs/t1/249378/1/3423/71589/65a788faF19f96be1/128abdbb059b0bd9.jpg',23333.00,0,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银'),('b54cc44c-adfe-4d75-ae06-6d778c2d876e','2024-01-24 09:41:39.332214','2024-01-24 09:41:39.332214','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"皓月银\",\"I7+16GB+1T\"]','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','fcd61ee7-ff1f-448c-8131-05c6dfea64b8','https://img10.360buyimg.com/n1/jfs/t1/148460/39/40865/58528/65ad1436F567c3e26/9f121abdd7913e20.jpg',4199.00,13,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银'),('d8446ec8-832b-467d-9a8d-3c08a9e4be4c','2024-01-24 09:41:59.496656','2024-01-24 09:41:59.496656','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"深空灰\",\"I7+16GB+1T\"]','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','fcd61ee7-ff1f-448c-1131-05c6dfea64b7','https://img10.360buyimg.com/n1/jfs/t1/148460/39/40865/58528/65ad1436F567c3e26/9f121abdd7913e20.jpg',4199.00,4,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银'),('df62bb69-c79e-448e-bfbb-415b9ead9c01','2024-01-19 15:01:05.396403','2024-01-19 15:01:05.396403','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"雅川青\",\"12gb+512gb\"]','华为Mate 60 Pro','04999440-6a62-46ae-8ec3-a25b374fc821','https://img12.360buyimg.com/n1/s450x450_jfs/t1/235988/15/12433/48078/65a8f4faF38ef4d31/fc8ca7c491173fe4.jpg',7999.00,1000,'4060'),('f2f212ac-c3ab-4d1c-97d2-aab0e5a11315','2024-01-19 15:01:05.404406','2024-01-19 15:01:05.404406','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"雅丹黑\",\"12gb+256gb\"]','华为Mate 60 Pro','04999440-6a62-46ae-8ec3-a25b374fc821','https://img12.360buyimg.com/n1/s450x450_jfs/t1/235988/15/12433/48078/65a8f4faF38ef4d31/fc8ca7c491173fe4.jpg',7999.00,1000,'4060'),('f9ca2d0d-870a-49da-a765-5d0f2dc366cc','2024-01-24 09:41:59.475569','2024-01-24 09:41:59.475569','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"皓月银\",\"I7+16GB+1T\"]','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','fcd61ee7-ff1f-448c-1131-05c6dfea64b7','https://img10.360buyimg.com/n1/jfs/t1/148460/39/40865/58528/65ad1436F567c3e26/9f121abdd7913e20.jpg',4199.00,4,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银'),('fbcb1044-e2f5-4e79-ba28-58fedefa4270','2024-01-24 09:41:39.342415','2024-01-24 09:41:39.342415','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','[\"深空灰\",\"I5+16GB+512GB\"]','华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银','fcd61ee7-ff1f-448c-8131-05c6dfea64b8','https://img10.360buyimg.com/n1/jfs/t1/148460/39/40865/58528/65ad1436F567c3e26/9f121abdd7913e20.jpg',4199.00,13,'华为MateBook D 14 2024笔记本电脑 13代英特尔酷睿处理器/16:10护眼全面屏 i5 16G 512G 皓月银');
/*!40000 ALTER TABLE `product_sku` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refund_record`
--

DROP TABLE IF EXISTS `refund_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refund_record` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `order_id` varchar(36) NOT NULL,
  `reason` text,
  `tracking_number` varchar(50) DEFAULT NULL,
  `amount` decimal(10,2) NOT NULL,
  `original_amount` decimal(10,2) NOT NULL,
  `status` varchar(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refund_record`
--

LOCK TABLES `refund_record` WRITE;
/*!40000 ALTER TABLE `refund_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `refund_record` ENABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('4a83f185-30bb-4464-9e68-239698e89a5e','2024-01-15 14:43:26.118566','2024-01-18 20:52:02.010268','0f07d638-f1bc-4011-88d8-6dc650ab06a7','9426415c-8254-46dd-95fe-35fb4c307713','普通用户'),('5f785900-d317-4210-979d-d17a40ba8ecc','2024-01-15 14:22:57.274513','2024-02-26 14:09:22.801781','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','管理员'),('da8163bd-67c0-4263-b075-e39d7e595ca9','2024-01-16 15:51:42.833408','2024-01-16 20:52:07.588413','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','测试23');
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_menu_rel`
--

LOCK TABLES `role_menu_rel` WRITE;
/*!40000 ALTER TABLE `role_menu_rel` DISABLE KEYS */;
INSERT INTO `role_menu_rel` VALUES ('0a9bd59886174b0c9597ad7dd35fdb57','2024-02-23 09:33:36.113671','2024-02-23 09:33:36.113671','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','43a710c85f8a43fe9602be9ceed50d1f'),('0dea9143-96a7-4ada-9158-84df2d9a0d8a','2024-01-17 10:37:02.842166','2024-01-17 10:37:02.842166','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','349b39a5-9500-4c22-9972-f22b935aba42'),('18f8e839-65cd-44cf-8e20-675ab562ff72','2024-01-16 15:51:42.848283','2024-01-16 15:51:42.848283','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','da8163bd-67c0-4263-b075-e39d7e595ca9','16b68322-193d-4be4-b71b-bbc0f18b0d3f'),('1a0e54d4-f1de-4e54-aac1-14f96e6892a9','2024-01-17 09:13:36.299660','2024-01-17 09:13:36.299660','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','16b68322-193d-4be4-b71b-bbc0f18b0d3f'),('260a568c2c01431a8b89f9b19a530d67','2024-02-23 09:33:36.108159','2024-02-23 09:33:36.108159','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','c9cf9ed3f15d4e20b37c672500311324'),('2934352c-742d-4041-b577-ac4d5be0af3e','2024-01-18 20:27:46.570354','2024-01-18 20:27:46.570354','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','4a83f185-30bb-4464-9e68-239698e89a5e','16b68322-193d-4be4-b71b-bbc0f18b0d3f'),('4648de11-03d3-4a68-b5e3-a1076fb7ca33','2024-01-18 17:02:15.666791','2024-01-18 17:02:15.666791','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','8246938d-50f1-4463-9ff1-8f4097df2b7a'),('4977d1b0-2e43-4457-9a9c-e99043d3b9ef','2024-01-17 09:13:36.304465','2024-01-17 09:13:36.304465','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','00f2290a-cde5-4e95-9294-4e7599b1a3f5'),('4a8a37da-0132-4d03-93eb-727661ce81b3','2024-01-19 11:12:29.119931','2024-01-19 11:12:29.119931','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','839c9fd0-8cf0-4070-a50f-16981bf649f8'),('680532bf-c941-4cbc-88a1-47a55283f1a9','2024-01-16 15:51:42.853546','2024-01-16 15:51:42.853546','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','da8163bd-67c0-4263-b075-e39d7e595ca9','8ea32b3d-90e4-4d76-a777-413917166a32'),('82768ea3-66f2-4447-9528-9c45f617ef4b','2024-01-18 14:36:40.298395','2024-01-18 14:36:40.298395','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','d5510c37-c5e9-48b3-b41b-2a0fa710c94e'),('9155676edfae4b8fb1710214d33463c7','2024-02-20 09:28:31.647348','2024-02-20 09:28:31.647348','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','c32ce807a69c43e8bdf67e27974920ab'),('9594627e-8c2f-45c7-91c3-ce1c0f9e93c7','2024-01-26 10:07:13.134646','2024-01-26 10:07:13.134646','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','fcf86780-d447-4a60-96b8-4f03c4eddc92'),('9c47cfc6-8333-4cac-bc0e-a027f5dbacd6','2024-01-17 09:13:40.638499','2024-01-17 09:13:40.637983','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','8ea32b3d-90e4-4d76-a777-413917166a32'),('9cc88468-9cf8-400a-beb2-239aaea1bc76','2024-01-18 20:27:46.574487','2024-01-18 20:27:46.574487','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','4a83f185-30bb-4464-9e68-239698e89a5e','8246938d-50f1-4463-9ff1-8f4097df2b7a'),('be1ad609-7c29-4f15-af1b-fcb2443e62c0','2024-01-26 10:07:13.130646','2024-01-26 10:07:13.130646','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','a350090e-11ff-45d0-88ce-5c3c1b6f9ef4'),('c0966b54-bc59-4963-9c3d-d3d68a8b2ce0','2024-01-17 16:42:52.109438','2024-01-17 16:42:52.109438','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','0c30e2f6-8cef-4ee6-999e-a18af97a3a26'),('c960432c-a191-4630-9902-540cefb992f2','2024-01-16 20:51:34.270707','2024-01-16 20:51:34.270707','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','da8163bd-67c0-4263-b075-e39d7e595ca9','00f2290a-cde5-4e95-9294-4e7599b1a3f5'),('d3f5aa75-dbf7-40b9-986c-dde2edf14b08','2024-01-16 20:51:45.829499','2024-01-16 20:51:45.829499','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','da8163bd-67c0-4263-b075-e39d7e595ca9','0c30e2f6-8cef-4ee6-999e-a18af97a3a26'),('dc447935-53ac-4181-95b1-5b6c6fe52900','2024-01-18 20:27:46.578579','2024-01-18 20:27:46.578579','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','4a83f185-30bb-4464-9e68-239698e89a5e','d5510c37-c5e9-48b3-b41b-2a0fa710c94e'),('fbdb4bb46d51411b819733fe1d283ad4','2024-02-26 14:09:22.813874','2024-02-26 14:09:22.813874','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','6c0d0f67db5d4418b974c685af43f7e0');
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('0f07d638-f1bc-4011-88d8-6dc650ab06a7','2024-01-10 10:48:02.758735','2024-01-22 16:11:50.046138','默认用户','https://my-community.oss-cn-qingdao.aliyuncs.com/20240110212158起凡.jpg','PRIVATE','13656987994','$2a$10$pl/GmO3mDaqWjBtmfXzppOFQwnW/jlinORR6.83Lo7QdTuU4uh5AG'),('9426415c-8254-46dd-95fe-35fb4c307713','2024-01-18 20:25:42.052245','2024-01-18 20:27:01.505872','默认用户',NULL,NULL,'13656987995','$2a$10$xp1LPOARpDbUPhNLtjfQMeDWgWS3KRM/7wB4pbya1c9IxVECh.Dv2');
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_rel`
--

LOCK TABLES `user_role_rel` WRITE;
/*!40000 ALTER TABLE `user_role_rel` DISABLE KEYS */;
INSERT INTO `user_role_rel` VALUES ('3b595f1e-50b1-454d-b0d7-4cdb084ef2bc','2024-01-18 20:27:01.511897','2024-01-18 20:27:01.511897','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','4a83f185-30bb-4464-9e68-239698e89a5e','9426415c-8254-46dd-95fe-35fb4c307713'),('c61c1c7f-e845-4e6d-93f4-26f9d76a82fa','2024-01-17 08:44:26.417028','2024-01-17 08:44:26.417028','0f07d638-f1bc-4011-88d8-6dc650ab06a7','0f07d638-f1bc-4011-88d8-6dc650ab06a7','5f785900-d317-4210-979d-d17a40ba8ecc','0f07d638-f1bc-4011-88d8-6dc650ab06a7');
/*!40000 ALTER TABLE `user_role_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_we_chat`
--

DROP TABLE IF EXISTS `user_we_chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_we_chat` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `open_id` varchar(30) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `open_id` (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_we_chat`
--

LOCK TABLES `user_we_chat` WRITE;
/*!40000 ALTER TABLE `user_we_chat` DISABLE KEYS */;
INSERT INTO `user_we_chat` VALUES ('ed3aad89-221f-4fe5-9443-b649b13bfb40','2024-01-22 16:11:50.056072','2024-01-22 16:11:50.056072','oEheF5USRu6Y3qWjpb3wJPBfuejw','0f07d638-f1bc-4011-88d8-6dc650ab06a7');
/*!40000 ALTER TABLE `user_we_chat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wallet`
--

DROP TABLE IF EXISTS `wallet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wallet` (
  `id` varchar(36) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(36) NOT NULL,
  `editor_id` varchar(36) NOT NULL,
  `balance` decimal(10,2) NOT NULL COMMENT '余额',
  `password` varchar(6) DEFAULT NULL COMMENT '钱包密码',
  `user_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallet`
--

LOCK TABLES `wallet` WRITE;
/*!40000 ALTER TABLE `wallet` DISABLE KEYS */;
/*!40000 ALTER TABLE `wallet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wallet_record`
--

DROP TABLE IF EXISTS `wallet_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wallet_record` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime(6) NOT NULL,
  `edited_time` datetime(6) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `editor_id` varchar(32) NOT NULL,
  `wallet_id` varchar(32) NOT NULL COMMENT '钱包id',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  `type` varchar(32) NOT NULL COMMENT '类型如：提现，充值，奖励，返佣等等',
  `description` varchar(255) NOT NULL COMMENT '描述信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallet_record`
--

LOCK TABLES `wallet_record` WRITE;
/*!40000 ALTER TABLE `wallet_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `wallet_record` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-13 20:14:52
