-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 121.4.32.77    Database: data_model
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `tb_cdm_attribute`
--

DROP TABLE IF EXISTS `tb_cdm_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_cdm_attribute` (
  `field_id` bigint NOT NULL COMMENT '字段ID',
  `data_type` int NOT NULL COMMENT '数据类型',
  `data_length` int NOT NULL COMMENT '数据长度',
  PRIMARY KEY (`field_id`),
  UNIQUE KEY `field_id_UNIQUE` (`field_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字段的概念数据模型属性';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_cdm_attribute`
--

LOCK TABLES `tb_cdm_attribute` WRITE;
/*!40000 ALTER TABLE `tb_cdm_attribute` DISABLE KEYS */;
INSERT INTO `tb_cdm_attribute` (`field_id`, `data_type`, `data_length`) VALUES (1535620353618472961,4,10),(1542769271598317570,4,255);
/*!40000 ALTER TABLE `tb_cdm_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_entity`
--

DROP TABLE IF EXISTS `tb_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_entity` (
  `id` bigint NOT NULL COMMENT '实体ID',
  `project_id` bigint NOT NULL COMMENT '项目ID',
  `name` varchar(50) NOT NULL COMMENT '实体名',
  `table_name` varchar(50) NOT NULL COMMENT '物理表名',
  `remark` varchar(500) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上次修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='实体表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_entity`
--

LOCK TABLES `tb_entity` WRITE;
/*!40000 ALTER TABLE `tb_entity` DISABLE KEYS */;
INSERT INTO `tb_entity` (`id`, `project_id`, `name`, `table_name`, `remark`, `create_time`, `last_modify_time`) VALUES (1535617804878344194,1535608880359800833,'学生','student','记录学生信息','2022-06-11 21:39:49','2022-06-11 21:39:49'),(1542769257903915010,1542487762953928706,'某某实体','某某实体','某某实体','2022-07-01 15:17:09','2022-07-01 15:17:09'),(1542785469023092738,1542487762953928706,'某某实体','某某实体','某某实体','2022-07-01 16:21:34','2022-07-01 16:21:34'),(1542785776171974657,1542772961524678658,'某某实体','某某实体','某某实体','2022-07-01 16:22:47','2022-07-01 16:22:47');
/*!40000 ALTER TABLE `tb_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_field`
--

DROP TABLE IF EXISTS `tb_field`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_field` (
  `id` bigint NOT NULL COMMENT '字段ID',
  `entity_id` bigint NOT NULL COMMENT '实体ID',
  `field_name` varchar(50) NOT NULL COMMENT '字段名',
  `description` varchar(200) DEFAULT NULL COMMENT '字段描述',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字段表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_field`
--

LOCK TABLES `tb_field` WRITE;
/*!40000 ALTER TABLE `tb_field` DISABLE KEYS */;
INSERT INTO `tb_field` (`id`, `entity_id`, `field_name`, `description`, `remark`, `create_time`) VALUES (1535620353618472961,1535617804878344194,'学号','唯一标识学生','标识','2022-06-11 21:49:57'),(1537322493307105281,1535617804878344194,'选修课程','学生选修',NULL,'2022-06-16 14:33:39'),(1542769271598317570,1542769257903915010,'某某字段','某某字段',NULL,'2022-07-01 15:17:12'),(1542785785193922562,1542785469023092738,'某某字段','某某字段',NULL,'2022-07-01 16:22:49');
/*!40000 ALTER TABLE `tb_field` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_ldm_attribute`
--

DROP TABLE IF EXISTS `tb_ldm_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_ldm_attribute` (
  `field_id` bigint NOT NULL COMMENT '字段ID',
  `foreign_key` int NOT NULL COMMENT '是否逻辑外键',
  `is_search` int NOT NULL DEFAULT '0' COMMENT '是否搜索字段',
  `is_sorted` int NOT NULL DEFAULT '0' COMMENT '是否排序字段',
  `is_unique` int NOT NULL DEFAULT '0' COMMENT '是否逻辑唯一',
  `search_type` int DEFAULT NULL COMMENT '搜索方式',
  PRIMARY KEY (`field_id`),
  UNIQUE KEY `field_id_UNIQUE` (`field_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字段的逻辑数据模型属性';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_ldm_attribute`
--

LOCK TABLES `tb_ldm_attribute` WRITE;
/*!40000 ALTER TABLE `tb_ldm_attribute` DISABLE KEYS */;
INSERT INTO `tb_ldm_attribute` (`field_id`, `foreign_key`, `is_search`, `is_sorted`, `is_unique`, `search_type`) VALUES (1535620353618472961,0,0,0,0,1);
/*!40000 ALTER TABLE `tb_ldm_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_oom_attribute`
--

DROP TABLE IF EXISTS `tb_oom_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_oom_attribute` (
  `field_id` bigint NOT NULL COMMENT '字段ID',
  `add_req` int DEFAULT NULL COMMENT '新增必填',
  `update_req` int DEFAULT NULL COMMENT '更新必填',
  `delete_req` int DEFAULT NULL,
  `query_req` int DEFAULT NULL COMMENT '查询必填',
  `is_enum` int DEFAULT NULL COMMENT '是否为枚举',
  `asso_progress` int DEFAULT NULL COMMENT '关联进度',
  `progress_req` int DEFAULT NULL COMMENT '进度更改为X时必填',
  PRIMARY KEY (`field_id`),
  UNIQUE KEY `field_id_UNIQUE` (`field_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字段的面向对象数据模型属性';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_oom_attribute`
--

LOCK TABLES `tb_oom_attribute` WRITE;
/*!40000 ALTER TABLE `tb_oom_attribute` DISABLE KEYS */;
INSERT INTO `tb_oom_attribute` (`field_id`, `add_req`, `update_req`, `delete_req`, `query_req`, `is_enum`, `asso_progress`, `progress_req`) VALUES (1535620353618472961,1,1,1,1,1,10,1),(1542769271598317570,0,0,0,0,0,NULL,0);
/*!40000 ALTER TABLE `tb_oom_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_pdm_attribute`
--

DROP TABLE IF EXISTS `tb_pdm_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_pdm_attribute` (
  `field_id` bigint NOT NULL COMMENT '字段ID',
  `code` varchar(50) NOT NULL COMMENT '物理列名',
  `data_type` int NOT NULL COMMENT '数据类型',
  `data_length` int NOT NULL COMMENT '数据长度',
  `data_precision` int NOT NULL COMMENT '数据精度',
  `primary_key` int NOT NULL,
  `foreign_key` int NOT NULL COMMENT '物理外键',
  `is_unique` int NOT NULL COMMENT '是否物理唯一',
  `not_null` int NOT NULL COMMENT '是否非空',
  `default_value` varchar(200) DEFAULT NULL COMMENT '字段默认值',
  PRIMARY KEY (`field_id`),
  UNIQUE KEY `field_id_UNIQUE` (`field_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字段的物理数据模型属性';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_pdm_attribute`
--

LOCK TABLES `tb_pdm_attribute` WRITE;
/*!40000 ALTER TABLE `tb_pdm_attribute` DISABLE KEYS */;
INSERT INTO `tb_pdm_attribute` (`field_id`, `code`, `data_type`, `data_length`, `data_precision`, `primary_key`, `foreign_key`, `is_unique`, `not_null`, `default_value`) VALUES (1535620353618472961,'stId',5,10,0,1,1,1,1,NULL);
/*!40000 ALTER TABLE `tb_pdm_attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_project`
--

DROP TABLE IF EXISTS `tb_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_project` (
  `id` bigint NOT NULL COMMENT '项目ID',
  `user_id` varchar(20) NOT NULL COMMENT '用户ID',
  `name` varchar(50) NOT NULL COMMENT '项目名',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '项目创建时间',
  `last_modify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上次修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='项目表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_project`
--

LOCK TABLES `tb_project` WRITE;
/*!40000 ALTER TABLE `tb_project` DISABLE KEYS */;
INSERT INTO `tb_project` (`id`, `user_id`, `name`, `remark`, `create_time`, `last_modify_time`) VALUES (1535608880359800833,'123456','数据建模','数据模型生成','2022-06-11 21:04:21','2022-06-11 21:04:21'),(1542487762953928706,'eardh','某某系统','某某系统','2022-06-30 20:38:35','2022-07-01 16:21:31'),(1542772961524678658,'eardh','某某系统','某某系统','2022-07-01 15:31:52','2022-07-01 15:31:52'),(1542785386609213442,'eardh','某某系统','某某系统','2022-07-01 16:21:14','2022-07-01 16:21:14');
/*!40000 ALTER TABLE `tb_project` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-18 14:15:36
