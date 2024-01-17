/*
SQLyog Community v13.2.1 (64 bit)
MySQL - 8.1.0 : Database - mall_wms
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mall_wms` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `mall_wms`;

/*Table structure for table `undo_log` */

DROP TABLE IF EXISTS `undo_log`;

CREATE TABLE `undo_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*Data for the table `undo_log` */

/*Table structure for table `wms_purchase` */

DROP TABLE IF EXISTS `wms_purchase`;

CREATE TABLE `wms_purchase` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `assignee_id` bigint DEFAULT NULL,
  `assignee_name` varchar(255) DEFAULT NULL,
  `phone` char(13) DEFAULT NULL,
  `priority` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  `ware_id` bigint DEFAULT NULL,
  `amount` decimal(18,4) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='采购信息';

/*Data for the table `wms_purchase` */

insert  into `wms_purchase`(`id`,`assignee_id`,`assignee_name`,`phone`,`priority`,`status`,`ware_id`,`amount`,`create_time`,`update_time`) values 
(10,1,'admin','13612345678',NULL,3,NULL,NULL,'2022-07-26 11:11:16','2022-07-26 11:11:43'),
(11,1,'admin','13612345678',NULL,3,NULL,NULL,'2022-07-26 11:13:30','2022-07-26 11:13:52'),
(12,1,'admin','13612345678',NULL,3,NULL,NULL,'2022-07-26 11:19:25','2022-07-26 11:19:49'),
(13,1,'admin','13612345678',NULL,3,NULL,NULL,'2022-07-26 11:30:41','2022-07-26 11:31:06'),
(17,2,'renchao','13862851173',1,3,NULL,NULL,'2022-11-06 03:02:42','2022-11-06 03:17:41'),
(18,2,'renchao','13862851173',0,3,NULL,NULL,'2022-11-06 03:45:05','2022-11-06 03:45:05');

/*Table structure for table `wms_purchase_detail` */

DROP TABLE IF EXISTS `wms_purchase_detail`;

CREATE TABLE `wms_purchase_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `purchase_id` bigint DEFAULT NULL COMMENT '采购单id',
  `sku_id` bigint DEFAULT NULL COMMENT '采购商品id',
  `sku_num` int DEFAULT NULL COMMENT '采购数量',
  `sku_price` decimal(18,4) DEFAULT NULL COMMENT '采购金额',
  `ware_id` bigint DEFAULT NULL COMMENT '仓库id',
  `status` int DEFAULT NULL COMMENT '状态[0新建，1已分配，2正在采购，3已完成，4采购失败]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `wms_purchase_detail` */

insert  into `wms_purchase_detail`(`id`,`purchase_id`,`sku_id`,`sku_num`,`sku_price`,`ware_id`,`status`) values 
(17,17,47,30,NULL,1,3),
(18,18,48,100,NULL,1,1),
(19,18,49,100,NULL,1,1),
(20,18,50,100,NULL,1,1),
(21,18,51,100,NULL,1,1),
(22,18,52,100,NULL,1,1);

/*Table structure for table `wms_ware_info` */

DROP TABLE IF EXISTS `wms_ware_info`;

CREATE TABLE `wms_ware_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '仓库名',
  `address` varchar(255) DEFAULT NULL COMMENT '仓库地址',
  `areacode` varchar(20) DEFAULT NULL COMMENT '区域编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='仓库信息';

/*Data for the table `wms_ware_info` */

insert  into `wms_ware_info`(`id`,`name`,`address`,`areacode`) values 
(1,'1号仓库','南通','000001'),
(2,'2号仓库','杭州','000002');

/*Table structure for table `wms_ware_order_task` */

DROP TABLE IF EXISTS `wms_ware_order_task`;

CREATE TABLE `wms_ware_order_task` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint DEFAULT NULL COMMENT 'order_id',
  `order_sn` varchar(255) DEFAULT NULL COMMENT 'order_sn',
  `consignee` varchar(100) DEFAULT NULL COMMENT '收货人',
  `consignee_tel` char(15) DEFAULT NULL COMMENT '收货人电话',
  `delivery_address` varchar(500) DEFAULT NULL COMMENT '配送地址',
  `order_comment` varchar(200) DEFAULT NULL COMMENT '订单备注',
  `payment_way` tinyint(1) DEFAULT NULL COMMENT '付款方式【 1:在线付款 2:货到付款】',
  `task_status` tinyint DEFAULT NULL COMMENT '任务状态【1-已锁定 2-已解锁 3-扣减】',
  `order_body` varchar(255) DEFAULT NULL COMMENT '订单描述',
  `tracking_no` char(30) DEFAULT NULL COMMENT '物流单号',
  `create_time` datetime DEFAULT NULL COMMENT 'create_time',
  `ware_id` bigint DEFAULT NULL COMMENT '仓库id',
  `task_comment` varchar(500) DEFAULT NULL COMMENT '工作单备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存工作单';

/*Data for the table `wms_ware_order_task` */

insert  into `wms_ware_order_task`(`id`,`order_id`,`order_sn`,`consignee`,`consignee_tel`,`delivery_address`,`order_comment`,`payment_way`,`task_status`,`order_body`,`tracking_no`,`create_time`,`ware_id`,`task_comment`) values 
(8,17,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-08-21 10:41:34',NULL,NULL),
(9,18,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-08-21 22:53:03',NULL,NULL),
(10,19,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-08-22 08:21:28',NULL,NULL),
(11,20,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-08-22 09:41:10',NULL,NULL),
(12,21,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-08-22 11:40:03',NULL,NULL),
(13,22,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-08-22 12:47:36',NULL,NULL),
(14,23,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-08-22 14:08:37',NULL,NULL),
(15,24,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-08-22 14:52:27',NULL,NULL),
(16,25,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,1,NULL,NULL,'2022-08-22 16:25:40',NULL,NULL),
(17,26,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,1,NULL,NULL,'2022-08-22 16:32:43',NULL,NULL),
(18,27,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,1,NULL,NULL,'2022-08-22 17:37:33',NULL,NULL),
(19,28,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-08-22 17:40:03',NULL,NULL),
(20,29,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-08-22 18:17:44',NULL,NULL),
(21,30,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-08-22 18:21:05',NULL,NULL),
(22,31,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-08-22 18:36:48',NULL,NULL),
(23,32,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,1,NULL,NULL,'2022-08-22 18:38:45',NULL,NULL),
(24,33,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-08-25 15:23:30',NULL,NULL),
(27,36,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-08-25 19:35:04',NULL,NULL),
(28,37,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-08-25 20:04:08',NULL,NULL),
(29,38,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-08-26 18:14:59',NULL,NULL),
(31,40,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-08-27 13:46:53',NULL,NULL),
(32,41,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-09-11 10:58:43',NULL,NULL),
(33,42,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-09-11 10:58:54',NULL,NULL),
(34,43,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-09-11 11:58:10',NULL,NULL),
(35,44,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-09-11 12:03:45',NULL,NULL),
(36,45,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-09-11 12:21:26',NULL,NULL),
(37,46,NULL,'任超','13862851173','江苏省南通市海门区三星镇金色阳光花苑','',NULL,2,NULL,NULL,'2022-11-09 06:34:10',NULL,NULL),
(38,47,NULL,'商城宝贝','13862851175','江苏省南通市海门区三星镇','',NULL,2,NULL,NULL,'2022-11-09 07:04:22',NULL,NULL);

/*Table structure for table `wms_ware_order_task_detail` */

DROP TABLE IF EXISTS `wms_ware_order_task_detail`;

CREATE TABLE `wms_ware_order_task_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint DEFAULT NULL COMMENT 'sku_id',
  `sku_name` varchar(255) DEFAULT NULL COMMENT 'sku_name',
  `sku_num` int DEFAULT NULL COMMENT '购买个数',
  `task_id` bigint DEFAULT NULL COMMENT '工作单id',
  `ware_id` bigint DEFAULT NULL COMMENT '仓库id',
  `lock_status` int DEFAULT NULL COMMENT '1-已锁定  2-已解锁  3-扣减',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存工作单';

/*Data for the table `wms_ware_order_task_detail` */

insert  into `wms_ware_order_task_detail`(`id`,`sku_id`,`sku_name`,`sku_num`,`task_id`,`ware_id`,`lock_status`) values 
(8,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,8,1,2),
(9,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,9,1,2),
(10,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,10,1,2),
(11,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,11,1,2),
(12,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米',1,12,2,2),
(13,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,12,1,2),
(14,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米',1,13,2,2),
(15,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,13,1,2),
(16,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米',1,14,2,2),
(17,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,14,1,2),
(18,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米',1,15,2,2),
(19,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,15,1,2),
(20,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米',1,16,2,1),
(21,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,16,1,1),
(22,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米',1,17,2,1),
(23,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,17,1,1),
(24,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米',1,18,2,1),
(25,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,18,1,1),
(26,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米',1,19,2,2),
(27,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,19,1,2),
(28,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米',1,20,2,2),
(29,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,20,1,2),
(30,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米',1,21,2,2),
(31,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,21,1,2),
(32,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米',1,22,2,2),
(33,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,22,1,2),
(34,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米',1,23,2,1),
(35,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,23,1,1),
(36,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米',1,24,2,2),
(37,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,24,1,2),
(38,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,27,1,2),
(39,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',3,28,1,2),
(40,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',3,29,1,2),
(41,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',4,31,1,2),
(42,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,32,1,2),
(43,9,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 6GB+128GB 智能手机 小米 红米',1,32,2,2),
(44,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,33,1,2),
(45,9,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 6GB+128GB 智能手机 小米 红米',1,33,2,2),
(46,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,34,1,2),
(47,9,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 6GB+128GB 智能手机 小米 红米',1,34,2,2),
(48,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,35,1,2),
(49,9,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 6GB+128GB 智能手机 小米 红米',1,35,2,2),
(50,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米',2,36,1,2),
(51,9,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 6GB+128GB 智能手机 小米 红米',1,36,2,2),
(52,114,'简约轻奢 四季通用 亲肤面料四季通用沙发垫-童话屋 绿色 90x90cm',1,37,1,2),
(53,64,'80支全棉大提花羽绒被 白鹅绒被子被芯春秋被加厚冬被 白色 220x240cm',1,38,1,2);

/*Table structure for table `wms_ware_sku` */

DROP TABLE IF EXISTS `wms_ware_sku`;

CREATE TABLE `wms_ware_sku` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint DEFAULT NULL COMMENT 'sku_id',
  `ware_id` bigint DEFAULT NULL COMMENT '仓库id',
  `stock` int DEFAULT NULL COMMENT '库存数',
  `sku_name` varchar(200) DEFAULT NULL COMMENT 'sku_name',
  `stock_locked` int DEFAULT '0' COMMENT '锁定库存',
  PRIMARY KEY (`id`),
  KEY `sku_id` (`sku_id`) USING BTREE,
  KEY `ware_id` (`ware_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品库存';

/*Data for the table `wms_ware_sku` */

insert  into `wms_ware_sku`(`id`,`sku_id`,`ware_id`,`stock`,`sku_name`,`stock_locked`) values 
(1,8,1,128,'红米',8),
(2,9,2,55,'红米',0),
(4,10,1,11,NULL,0),
(7,11,2,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB',4),
(8,38,2,10,NULL,0),
(9,47,1,30,'2021新款100支澳棉数码印花系列四件套第三波 亲肤澳棉四件套全棉套件 繁星 200x230cm',0),
(10,48,1,50,'2021新款100支澳棉数码印花系列四件套第三波 亲肤澳棉四件套全棉套件 繁星 220x240cm',0),
(11,49,1,100,'2021新款100支澳棉数码印花系列四件套第三波亲肤澳棉四件套全棉套件布朗尼200x230cm',0),
(12,50,1,100,'2021新款100支澳棉数码印花系列四件套第三波亲肤澳棉四件套全棉套件布朗尼220x240cm',0),
(13,51,1,100,'2021新款100支澳棉数码印花系列四件套第三波亲肤澳棉四件套全棉套件豹纹200x230cm',0),
(14,52,1,100,'2021新款100支澳棉数码印花系列四件套第三波亲肤澳棉四件套全棉套件豹纹220x240cm',0),
(15,53,1,100,'A类针织棉暖豆豆大豆被亲肤透气柔软舒适冬被被子皎玉粉200x230cm',0),
(16,54,1,100,'A类针织棉暖豆豆大豆被亲肤透气柔软舒适冬被被子皎玉粉220x240cm',0),
(17,55,1,100,'A类针织棉暖豆豆大豆被亲肤透气柔软舒适冬被被子兰波青200x230cm',0),
(18,56,1,100,'A类针织棉暖豆豆大豆被亲肤透气柔软舒适冬被被子兰波青220x240cm',0),
(19,57,1,100,'A类针织棉暖豆豆大豆被亲肤透气柔软舒适冬被被子莫蓝迪200x230cm',0),
(20,58,1,100,'A类针织棉暖豆豆大豆被亲肤透气柔软舒适冬被被子莫蓝迪220x240cm',0),
(21,59,1,100,'A类针织棉暖豆豆大豆被亲肤透气柔软舒适冬被被子珠暮白200x230cm',0),
(22,60,1,100,'A类针织棉暖豆豆大豆被亲肤透气柔软舒适冬被被子珠暮白220x240cm',0),
(23,61,1,100,'A类针织棉暖豆豆大豆被亲肤透气柔软舒适冬被被子烟沉灰200x230cm',0),
(24,62,1,100,'A类针织棉暖豆豆大豆被亲肤透气柔软舒适冬被被子烟沉灰220x240cm',0),
(25,63,1,100,'80支全棉大提花羽绒被白鹅绒被子被芯春秋被加厚冬被白色200x230cm',0),
(26,64,1,100,'80支全棉大提花羽绒被白鹅绒被子被芯春秋被加厚冬被白色220x240cm',0),
(27,65,1,100,'80支全棉大提花羽绒被白鹅绒被子被芯春秋被加厚冬被粉色200x230cm',0),
(28,66,1,100,'80支全棉大提花羽绒被白鹅绒被子被芯春秋被加厚冬被粉色220x240cm',0),
(29,67,1,100,'80支全棉大提花羽绒被白鹅绒被子被芯春秋被加厚冬被红色200x230cm',0),
(30,68,1,100,'80支全棉大提花羽绒被白鹅绒被子被芯春秋被加厚冬被红色220x240cm',0),
(31,69,1,100,'80支全棉大提花羽绒被白鹅绒被子被芯春秋被加厚冬被黄色200x230cm',0),
(32,70,1,100,'80支全棉大提花羽绒被白鹅绒被子被芯春秋被加厚冬被黄色220x240cm',0),
(33,71,1,100,'新品畅销水洗棉羽绒被子白鸭绒被鹅绒被芯冬被草绿方格200x230cm',0),
(34,72,1,100,'新品畅销水洗棉羽绒被白鸭绒被鹅绒被芯冬被子粉色200x230cm',0),
(35,73,1,100,'新品畅销水洗棉羽绒被子白鸭绒被鹅绒被芯冬被橘红方格200x230cm',0),
(36,74,1,100,'新品畅销水洗棉羽绒被白鸭绒被鹅绒被芯冬被子咖啡方格200x230cm',0),
(37,75,1,100,'新品畅销水洗棉羽绒被白鸭绒被鹅绒被芯冬被子普蓝方格200x230cm',0),
(38,76,1,100,'2022新款莫代尔贡缎提花桑蚕丝夏被春秋被冬被被子被芯灰色200x230cm',0),
(39,77,1,100,'2022新款莫代尔贡缎提花桑蚕丝夏被春秋被冬被被子被芯灰色220x240cm',0),
(40,78,1,100,'2022新款莫代尔贡缎提花桑蚕丝夏被春秋被冬被被子被芯粉色200x230cm',0),
(41,79,1,100,'2022新款莫代尔贡缎提花桑蚕丝夏被春秋被冬被被子被芯粉色220x240cm',0),
(42,80,1,100,'2022新款莫代尔贡缎提花桑蚕丝夏被春秋被冬被被子被芯白色200x230cm',0),
(43,81,1,100,'2022新款莫代尔贡缎提花桑蚕丝夏被春秋被冬被被子被芯白色220x240cm',0),
(44,82,1,100,'全棉100支磨毛四件套高档经典纯棉磨毛高精密绣花套件柠檬黄200x230cm',0),
(45,83,1,100,'全棉100支磨毛四件套高档经典纯棉磨毛高精密绣花套件柠檬黄220x240cm',0),
(46,84,1,100,'全棉100支磨毛四件套高档经典纯棉磨毛高精密绣花套件奶白橘200x230cm',0),
(47,85,1,100,'全棉100支磨毛四件套高档经典纯棉磨毛高精密绣花套件奶白橘220x240cm',0),
(48,86,1,100,'全棉100支磨毛四件套高档经典纯棉磨毛高精密绣花套件流光粉200x230cm',0),
(49,87,1,100,'全棉100支磨毛四件套高档经典纯棉磨毛高精密绣花套件流光粉220x240cm',0),
(50,88,1,100,'新款60s兰精LF天丝四件套系列--赫拉芥末黄200x230cm',0),
(51,89,1,100,'新款60s兰精LF天丝四件套系列--赫拉芥末黄220x240cm',0),
(52,90,1,100,'新款60s兰精LF天丝四件套系列--赫拉焦糖黄200x230cm',0),
(53,91,1,100,'新款60s兰精LF天丝四件套系列--赫拉焦糖黄220x240cm',0),
(54,92,1,100,'新款60s兰精LF天丝四件套系列--赫拉灰蓝200x230cm',0),
(55,93,1,100,'新款60s兰精LF天丝四件套系列--赫拉灰蓝220x240cm',0),
(56,94,1,100,'新款60s兰精LF天丝四件套系列--赫拉茶绿200x230cm',0),
(57,95,1,100,'新款60s兰精LF天丝四件套系列--赫拉茶绿220x240cm',0),
(58,96,1,100,'希尔顿嘉悦纤维枕头枕芯48*74cm-中枕中枕',0),
(59,97,1,100,'希尔顿嘉悦纤维枕头枕芯48*74cm-中枕低枕',0),
(60,98,1,100,'希尔顿嘉悦纤维枕头枕芯48*74cm-中枕高枕',0),
(61,99,1,100,'8S护颈羽丝枕枕头枕芯48*74cm-8S护颈羽丝枕48x74cm',0),
(62,100,1,100,'新品雪尼尔提花中式沙发垫柔软舒适蕾丝花边雪尼尔沙发垫爱丽斯90x160cm',0),
(63,101,1,100,'新品雪尼尔提花中式沙发垫柔软舒适蕾丝花边雪尼尔沙发垫爱丽斯90x90cm',0),
(64,102,1,100,'新品雪尼尔提花中式沙发垫柔软舒适蕾丝花边雪尼尔沙发垫宝石蓝90x160cm',0),
(65,103,1,100,'新品雪尼尔提花中式沙发垫柔软舒适蕾丝花边雪尼尔沙发垫宝石蓝90x90cm',0),
(66,104,1,100,'新品雪尼尔提花中式沙发垫柔软舒适蕾丝花边雪尼尔沙发垫朵朵花开90x160cm',0),
(67,105,1,100,'新品雪尼尔提花中式沙发垫柔软舒适蕾丝花边雪尼尔沙发垫朵朵花开90x90cm',0),
(68,106,1,100,'新品雪尼尔提花中式沙发垫柔软舒适蕾丝花边雪尼尔沙发垫翡翠绿90x160cm',0),
(69,107,1,100,'新品雪尼尔提花中式沙发垫柔软舒适蕾丝花边雪尼尔沙发垫翡翠绿90x90cm',0),
(70,108,1,100,'简约轻奢四季通用亲肤面料四季通用沙发垫-童话屋粉色90x90cm',0),
(71,109,1,100,'简约轻奢四季通用亲肤面料四季通用沙发垫-童话屋粉色90x160cm',0),
(72,110,1,100,'简约轻奢四季通用亲肤面料四季通用沙发垫-童话屋灰色90x90cm',0),
(73,111,1,100,'简约轻奢四季通用亲肤面料四季通用沙发垫-童话屋灰色90x160cm',0),
(74,112,1,100,'简约轻奢四季通用亲肤面料四季通用沙发垫-童话屋蓝色90x90cm',0),
(75,113,1,100,'简约轻奢四季通用亲肤面料四季通用沙发垫-童话屋蓝色90x160cm',0),
(76,114,1,100,'简约轻奢四季通用亲肤面料四季通用沙发垫-童话屋绿色90x90cm',0),
(77,115,1,100,'简约轻奢四季通用亲肤面料四季通用沙发垫-童话屋绿色90x160cm',0),
(78,116,1,100,'简约轻奢四季通用亲肤面料四季通用沙发垫-童话屋米色90x90cm',0),
(79,117,1,100,'简约轻奢四季通用亲肤面料四季通用沙发垫-童话屋米色90x160cm',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
