/*
SQLyog Community v13.2.1 (64 bit)
MySQL - 8.1.0 : Database - mall_oms
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mall_oms` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `mall_oms`;

/*Table structure for table `oms_order` */

DROP TABLE IF EXISTS `oms_order`;

CREATE TABLE `oms_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint DEFAULT NULL COMMENT 'member_id',
  `order_sn` char(32) DEFAULT NULL COMMENT '订单号',
  `coupon_id` bigint DEFAULT NULL COMMENT '使用的优惠券',
  `create_time` datetime DEFAULT NULL COMMENT 'create_time',
  `member_username` varchar(200) DEFAULT NULL COMMENT '用户名',
  `total_amount` decimal(18,4) DEFAULT NULL COMMENT '订单总额',
  `pay_amount` decimal(18,4) DEFAULT NULL COMMENT '应付总额',
  `freight_amount` decimal(18,4) DEFAULT NULL COMMENT '运费金额',
  `promotion_amount` decimal(18,4) DEFAULT NULL COMMENT '促销优化金额（促销价、满减、阶梯价）',
  `integration_amount` decimal(18,4) DEFAULT NULL COMMENT '积分抵扣金额',
  `coupon_amount` decimal(18,4) DEFAULT NULL COMMENT '优惠券抵扣金额',
  `discount_amount` decimal(18,4) DEFAULT NULL COMMENT '后台调整订单使用的折扣金额',
  `pay_type` tinyint DEFAULT NULL COMMENT '支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】',
  `source_type` tinyint DEFAULT NULL COMMENT '订单来源[0->PC订单；1->app订单]',
  `status` tinyint DEFAULT NULL COMMENT '订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】',
  `delivery_company` varchar(64) DEFAULT NULL COMMENT '物流公司(配送方式)',
  `delivery_sn` varchar(64) DEFAULT NULL COMMENT '物流单号',
  `auto_confirm_day` int DEFAULT NULL COMMENT '自动确认时间（天）',
  `integration` int DEFAULT NULL COMMENT '可以获得的积分',
  `growth` int DEFAULT NULL COMMENT '可以获得的成长值',
  `bill_type` tinyint DEFAULT NULL COMMENT '发票类型[0->不开发票；1->电子发票；2->纸质发票]',
  `bill_header` varchar(255) DEFAULT NULL COMMENT '发票抬头',
  `bill_content` varchar(255) DEFAULT NULL COMMENT '发票内容',
  `bill_receiver_phone` varchar(32) DEFAULT NULL COMMENT '收票人电话',
  `bill_receiver_email` varchar(64) DEFAULT NULL COMMENT '收票人邮箱',
  `receiver_name` varchar(100) DEFAULT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(32) DEFAULT NULL COMMENT '收货人电话',
  `receiver_post_code` varchar(32) DEFAULT NULL COMMENT '收货人邮编',
  `receiver_province` varchar(32) DEFAULT NULL COMMENT '省份/直辖市',
  `receiver_city` varchar(32) DEFAULT NULL COMMENT '城市',
  `receiver_region` varchar(32) DEFAULT NULL COMMENT '区',
  `receiver_detail_address` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `note` varchar(500) DEFAULT NULL COMMENT '订单备注',
  `confirm_status` tinyint DEFAULT NULL COMMENT '确认收货状态[0->未确认；1->已确认]',
  `delete_status` tinyint DEFAULT NULL COMMENT '删除状态【0->未删除；1->已删除】',
  `use_integration` int DEFAULT NULL COMMENT '下单时使用的积分',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime DEFAULT NULL COMMENT '确认收货时间',
  `comment_time` datetime DEFAULT NULL COMMENT '评价时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单';

/*Data for the table `oms_order` */

insert  into `oms_order`(`id`,`member_id`,`order_sn`,`coupon_id`,`create_time`,`member_username`,`total_amount`,`pay_amount`,`freight_amount`,`promotion_amount`,`integration_amount`,`coupon_amount`,`discount_amount`,`pay_type`,`source_type`,`status`,`delivery_company`,`delivery_sn`,`auto_confirm_day`,`integration`,`growth`,`bill_type`,`bill_header`,`bill_content`,`bill_receiver_phone`,`bill_receiver_email`,`receiver_name`,`receiver_phone`,`receiver_post_code`,`receiver_province`,`receiver_city`,`receiver_region`,`receiver_detail_address`,`note`,`confirm_status`,`delete_status`,`use_integration`,`payment_time`,`delivery_time`,`receive_time`,`comment_time`,`modify_time`) values 
(17,2,'2022082110413392',NULL,'2022-08-21 10:41:34','renchao05',2798.0000,2801.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,2,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-08-21 10:41:34'),
(18,2,'2022082122530272',NULL,'2022-08-21 22:53:03','renchao05',2798.0000,2801.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,4,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-08-21 22:53:03'),
(19,2,'2022082208212702',NULL,'2022-08-22 08:21:27','renchao05',2798.0000,2801.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,4,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-08-22 08:21:27'),
(20,2,'2022082209411002',NULL,'2022-08-22 09:41:10','renchao05',2798.0000,2801.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,4,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-08-22 09:41:10'),
(21,2,'2022082211400202',NULL,'2022-08-22 11:40:02','renchao05',3997.0000,4000.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,4,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-08-22 11:40:02'),
(22,2,'2022082212473572',NULL,'2022-08-22 12:47:36','renchao05',3997.0000,4000.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,4,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-08-22 12:47:36'),
(23,2,'2022082214083692',NULL,'2022-08-22 14:08:37','renchao05',3997.0000,4000.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,4,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-08-22 14:08:37'),
(24,2,'2022082214522622',NULL,'2022-08-22 14:52:26','renchao05',3997.0000,4000.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,4,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-08-22 14:52:26'),
(25,2,'2022082216253932',NULL,'2022-08-22 16:25:39','renchao05',3997.0000,4000.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,1,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-08-22 16:25:39'),
(26,2,'2022082216324312',NULL,'2022-08-22 16:32:43','renchao05',3997.0000,4000.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,1,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-08-22 16:32:43'),
(27,2,'2022082217373252',NULL,'2022-08-22 17:37:33','renchao05',3997.0000,4000.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,1,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-08-22 17:37:33'),
(28,2,'2022082217400252',NULL,'2022-08-22 17:40:03','renchao05',3997.0000,4000.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,1,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-08-22 17:40:03'),
(29,2,'2022082218174402',NULL,'2022-08-22 18:17:44','renchao05',3997.0000,4000.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,4,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-08-22 18:17:44'),
(30,2,'2022082218210492',NULL,'2022-08-22 18:21:05','renchao05',3997.0000,4000.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,4,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-08-22 18:21:05'),
(31,2,'2022082218364812',NULL,'2022-08-22 18:36:48','renchao05',3997.0000,4000.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,4,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-08-22 18:36:48'),
(32,2,'2022082218384452',NULL,'2022-08-22 18:38:45','renchao05',3997.0000,4000.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,1,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-08-22 18:38:45'),
(33,2,'2022082515232952',NULL,'2022-08-25 15:23:30','renchao05',3997.0000,4000.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,4,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-08-25 15:23:30'),
(36,2,'2022082519350432',NULL,'2022-08-25 19:35:04','renchao05',2798.0000,2801.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,4,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-08-25 19:35:04'),
(37,2,'2022082520035792',NULL,'2022-08-25 20:04:07','renchao05',4197.0000,4200.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,4,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-08-25 20:04:07'),
(38,2,'2022082618145882',NULL,'2022-08-26 18:14:59','renchao05',4197.0000,4200.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,4,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-08-26 18:14:59'),
(40,2,'2022082713465252',NULL,'2022-08-27 13:46:53','renchao05',5596.0000,5599.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,4,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-08-27 13:46:53'),
(42,2,'2022091118585362',NULL,'2022-09-11 10:58:54','renchao05',3997.0000,4000.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,4,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-09-11 10:58:54'),
(43,2,'2022091119580942',NULL,'2022-09-11 11:58:09','renchao05',3997.0000,4000.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,4,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-09-11 11:58:09'),
(44,2,'2022091120034412',NULL,'2022-09-11 12:03:44','renchao05',3997.0000,4000.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,4,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-09-11 12:03:44'),
(45,2,'2022091120212572',NULL,'2022-09-11 12:21:26','renchao05',3997.0000,4000.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,4,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-09-11 12:21:26'),
(46,2,'2022110914340872',NULL,'2022-11-09 06:34:09','renchao05',269.0000,272.0000,3.0000,0.0000,0.0000,0.0000,0.0000,1,0,4,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'任超','13862851173',NULL,'江苏省','南通市','海门区','三星镇金色阳光花苑','',0,0,0,NULL,NULL,NULL,NULL,'2022-11-09 06:34:09'),
(47,5,'2022110915042215',NULL,'2022-11-09 07:04:22','wangdaoyue',999.0000,1004.0000,5.0000,0.0000,0.0000,0.0000,0.0000,1,0,4,NULL,NULL,7,0,0,0,NULL,NULL,NULL,NULL,'商城宝贝','13862851175',NULL,'江苏省','南通市','海门区','三星镇','',0,0,0,NULL,NULL,NULL,NULL,'2022-11-09 07:04:22');

/*Table structure for table `oms_order_item` */

DROP TABLE IF EXISTS `oms_order_item`;

CREATE TABLE `oms_order_item` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint DEFAULT NULL COMMENT 'order_id',
  `order_sn` char(32) DEFAULT NULL COMMENT 'order_sn',
  `spu_id` bigint DEFAULT NULL COMMENT 'spu_id',
  `spu_name` varchar(255) DEFAULT NULL COMMENT 'spu_name',
  `spu_pic` varchar(500) DEFAULT NULL COMMENT 'spu_pic',
  `spu_brand` varchar(200) DEFAULT NULL COMMENT '品牌',
  `category_id` bigint DEFAULT NULL COMMENT '商品分类id',
  `sku_id` bigint DEFAULT NULL COMMENT '商品sku编号',
  `sku_name` varchar(255) DEFAULT NULL COMMENT '商品sku名字',
  `sku_pic` varchar(500) DEFAULT NULL COMMENT '商品sku图片',
  `sku_price` decimal(18,4) DEFAULT NULL COMMENT '商品sku价格',
  `sku_quantity` int DEFAULT NULL COMMENT '商品购买的数量',
  `sku_attrs_vals` varchar(500) DEFAULT NULL COMMENT '商品销售属性组合（JSON）',
  `promotion_amount` decimal(18,4) DEFAULT NULL COMMENT '商品促销分解金额',
  `coupon_amount` decimal(18,4) DEFAULT NULL COMMENT '优惠券优惠分解金额',
  `integration_amount` decimal(18,4) DEFAULT NULL COMMENT '积分优惠分解金额',
  `real_amount` decimal(18,4) DEFAULT NULL COMMENT '该商品经过优惠后的分解金额',
  `gift_integration` int DEFAULT NULL COMMENT '赠送积分',
  `gift_growth` int DEFAULT NULL COMMENT '赠送成长值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单项信息';

/*Data for the table `oms_order_item` */

insert  into `oms_order_item`(`id`,`order_id`,`order_sn`,`spu_id`,`spu_name`,`spu_pic`,`spu_brand`,`category_id`,`sku_id`,`sku_name`,`sku_pic`,`sku_price`,`sku_quantity`,`sku_attrs_vals`,`promotion_amount`,`coupon_amount`,`integration_amount`,`real_amount`,`gift_integration`,`gift_growth`) values 
(20,NULL,'2022082110413392',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,2,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(21,NULL,'2022082122530272',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,2,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(22,NULL,'2022082208212702',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,2,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(23,NULL,'2022082209411002',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,2,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(24,NULL,'2022082211400202',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//e8bf30fe-8122-4d7a-8a3c-ed9630ed3676_23cd65077f12f7f5.jpg',1199.0000,1,'颜色:黑色;型号:6GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(25,NULL,'2022082211400202',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,2,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(26,NULL,'2022082212473572',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//e8bf30fe-8122-4d7a-8a3c-ed9630ed3676_23cd65077f12f7f5.jpg',1199.0000,1,'颜色:黑色;型号:6GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(27,NULL,'2022082212473572',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,2,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(28,NULL,'2022082214083692',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//e8bf30fe-8122-4d7a-8a3c-ed9630ed3676_23cd65077f12f7f5.jpg',1199.0000,1,'颜色:黑色;型号:6GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(29,NULL,'2022082214083692',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,2,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(30,NULL,'2022082214522622',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//e8bf30fe-8122-4d7a-8a3c-ed9630ed3676_23cd65077f12f7f5.jpg',1199.0000,1,'颜色:黑色;型号:6GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(31,NULL,'2022082214522622',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,2,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(32,NULL,'2022082216253932',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//e8bf30fe-8122-4d7a-8a3c-ed9630ed3676_23cd65077f12f7f5.jpg',1199.0000,1,'颜色:黑色;型号:6GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(33,NULL,'2022082216253932',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,2,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(34,NULL,'2022082216324312',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//e8bf30fe-8122-4d7a-8a3c-ed9630ed3676_23cd65077f12f7f5.jpg',1199.0000,1,'颜色:黑色;型号:6GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(35,NULL,'2022082216324312',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,2,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(36,NULL,'2022082217373252',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//e8bf30fe-8122-4d7a-8a3c-ed9630ed3676_23cd65077f12f7f5.jpg',1199.0000,1,'颜色:黑色;型号:6GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(37,NULL,'2022082217373252',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,2,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(38,NULL,'2022082217400252',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//e8bf30fe-8122-4d7a-8a3c-ed9630ed3676_23cd65077f12f7f5.jpg',1199.0000,1,'颜色:黑色;型号:6GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(39,NULL,'2022082217400252',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,2,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(40,NULL,'2022082218174402',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//e8bf30fe-8122-4d7a-8a3c-ed9630ed3676_23cd65077f12f7f5.jpg',1199.0000,1,'颜色:黑色;型号:6GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(41,NULL,'2022082218174402',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,2,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(42,NULL,'2022082218210492',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//e8bf30fe-8122-4d7a-8a3c-ed9630ed3676_23cd65077f12f7f5.jpg',1199.0000,1,'颜色:黑色;型号:6GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(43,NULL,'2022082218210492',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,2,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(44,NULL,'2022082218364812',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//e8bf30fe-8122-4d7a-8a3c-ed9630ed3676_23cd65077f12f7f5.jpg',1199.0000,1,'颜色:黑色;型号:6GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(45,NULL,'2022082218364812',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,2,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(46,NULL,'2022082218384452',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//e8bf30fe-8122-4d7a-8a3c-ed9630ed3676_23cd65077f12f7f5.jpg',1199.0000,1,'颜色:黑色;型号:6GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(47,NULL,'2022082218384452',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,2,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(48,NULL,'2022082515232952',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,11,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 黑色 6GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//e8bf30fe-8122-4d7a-8a3c-ed9630ed3676_23cd65077f12f7f5.jpg',1199.0000,1,'颜色:黑色;型号:6GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(49,NULL,'2022082515232952',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,2,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(52,NULL,'2022082519350432',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,2,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(53,NULL,'2022082520035792',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,3,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(54,NULL,'2022082618145882',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,3,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(56,NULL,'2022082713465252',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,4,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(59,NULL,'2022091118585362',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,2,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(60,NULL,'2022091118585362',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,9,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 6GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//3baf69c9-ae6f-4474-835f-2ae42e17f85c_8bf441260bffa42f.jpg',1199.0000,1,'颜色:白色;型号:6GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(61,NULL,'2022091119580942',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,2,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(62,NULL,'2022091119580942',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,9,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 6GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//3baf69c9-ae6f-4474-835f-2ae42e17f85c_8bf441260bffa42f.jpg',1199.0000,1,'颜色:白色;型号:6GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(63,NULL,'2022091120034412',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,2,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(64,NULL,'2022091120034412',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,9,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 6GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//3baf69c9-ae6f-4474-835f-2ae42e17f85c_8bf441260bffa42f.jpg',1199.0000,1,'颜色:白色;型号:6GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(65,NULL,'2022091120212572',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,8,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 8GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//d44891ae-b552-497e-8892-b7d3218c82ed_0d40c24b264aa511.jpg',1399.0000,2,'颜色:白色;型号:8GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(66,NULL,'2022091120212572',12,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁','','2',225,9,'Redmi 10A 5000mAh大电量 1300万AI相机 八核处理器 指纹解锁 白色 6GB+128GB 智能手机 小米 红米','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-07-23//3baf69c9-ae6f-4474-835f-2ae42e17f85c_8bf441260bffa42f.jpg',1199.0000,1,'颜色:白色;型号:6GB+128GB',0.0000,0.0000,0.0000,0.0000,0,0),
(67,NULL,'2022110914340872',33,'简约轻奢 四季通用 亲肤面料四季通用沙发垫-童话屋','','14',399,114,'简约轻奢 四季通用 亲肤面料四季通用沙发垫-童话屋 绿色 90x90cm','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-11-07/859c94ff-f206-4370-8007-0fec66198942_童话屋绿1.jpg',269.0000,1,'颜色分类:绿色;尺寸:90x90cm',0.0000,0.0000,0.0000,0.0000,0,0),
(68,NULL,'2022110915042215',25,'80支全棉大提花羽绒被 白鹅绒被子被芯春秋被加厚冬被','','12',401,64,'80支全棉大提花羽绒被 白鹅绒被子被芯春秋被加厚冬被 白色 220x240cm','https://gulimall-renchao.oss-cn-hangzhou.aliyuncs.com/2022-11-07/22e14380-e88e-47af-81b1-a1f641885f35_白色3.jpg',999.0000,1,'颜色:白色;尺寸:220x240cm',0.0000,0.0000,0.0000,0.0000,0,0);

/*Table structure for table `oms_order_operate_history` */

DROP TABLE IF EXISTS `oms_order_operate_history`;

CREATE TABLE `oms_order_operate_history` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint DEFAULT NULL COMMENT '订单id',
  `operate_man` varchar(100) DEFAULT NULL COMMENT '操作人[用户；系统；后台管理员]',
  `create_time` datetime DEFAULT NULL COMMENT '操作时间',
  `order_status` tinyint DEFAULT NULL COMMENT '订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】',
  `note` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单操作历史记录';

/*Data for the table `oms_order_operate_history` */

/*Table structure for table `oms_order_return_apply` */

DROP TABLE IF EXISTS `oms_order_return_apply`;

CREATE TABLE `oms_order_return_apply` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint DEFAULT NULL COMMENT 'order_id',
  `sku_id` bigint DEFAULT NULL COMMENT '退货商品id',
  `order_sn` char(32) DEFAULT NULL COMMENT '订单编号',
  `create_time` datetime DEFAULT NULL COMMENT '申请时间',
  `member_username` varchar(64) DEFAULT NULL COMMENT '会员用户名',
  `return_amount` decimal(18,4) DEFAULT NULL COMMENT '退款金额',
  `return_name` varchar(100) DEFAULT NULL COMMENT '退货人姓名',
  `return_phone` varchar(20) DEFAULT NULL COMMENT '退货人电话',
  `status` tinyint(1) DEFAULT NULL COMMENT '申请状态[0->待处理；1->退货中；2->已完成；3->已拒绝]',
  `handle_time` datetime DEFAULT NULL COMMENT '处理时间',
  `sku_img` varchar(500) DEFAULT NULL COMMENT '商品图片',
  `sku_name` varchar(200) DEFAULT NULL COMMENT '商品名称',
  `sku_brand` varchar(200) DEFAULT NULL COMMENT '商品品牌',
  `sku_attrs_vals` varchar(500) DEFAULT NULL COMMENT '商品销售属性(JSON)',
  `sku_count` int DEFAULT NULL COMMENT '退货数量',
  `sku_price` decimal(18,4) DEFAULT NULL COMMENT '商品单价',
  `sku_real_price` decimal(18,4) DEFAULT NULL COMMENT '商品实际支付单价',
  `reason` varchar(200) DEFAULT NULL COMMENT '原因',
  `description述` varchar(500) DEFAULT NULL COMMENT '描述',
  `desc_pics` varchar(2000) DEFAULT NULL COMMENT '凭证图片，以逗号隔开',
  `handle_note` varchar(500) DEFAULT NULL COMMENT '处理备注',
  `handle_man` varchar(200) DEFAULT NULL COMMENT '处理人员',
  `receive_man` varchar(100) DEFAULT NULL COMMENT '收货人',
  `receive_time` datetime DEFAULT NULL COMMENT '收货时间',
  `receive_note` varchar(500) DEFAULT NULL COMMENT '收货备注',
  `receive_phone` varchar(20) DEFAULT NULL COMMENT '收货电话',
  `company_address` varchar(500) DEFAULT NULL COMMENT '公司收货地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单退货申请';

/*Data for the table `oms_order_return_apply` */

/*Table structure for table `oms_order_return_reason` */

DROP TABLE IF EXISTS `oms_order_return_reason`;

CREATE TABLE `oms_order_return_reason` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(200) DEFAULT NULL COMMENT '退货原因名',
  `sort` int DEFAULT NULL COMMENT '排序',
  `status` tinyint(1) DEFAULT NULL COMMENT '启用状态',
  `create_time` datetime DEFAULT NULL COMMENT 'create_time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='退货原因';

/*Data for the table `oms_order_return_reason` */

/*Table structure for table `oms_order_setting` */

DROP TABLE IF EXISTS `oms_order_setting`;

CREATE TABLE `oms_order_setting` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `flash_order_overtime` int DEFAULT NULL COMMENT '秒杀订单超时关闭时间(分)',
  `normal_order_overtime` int DEFAULT NULL COMMENT '正常订单超时时间(分)',
  `confirm_overtime` int DEFAULT NULL COMMENT '发货后自动确认收货时间（天）',
  `finish_overtime` int DEFAULT NULL COMMENT '自动完成交易时间，不能申请退货（天）',
  `comment_overtime` int DEFAULT NULL COMMENT '订单完成后自动好评时间（天）',
  `member_level` tinyint DEFAULT NULL COMMENT '会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单配置信息';

/*Data for the table `oms_order_setting` */

/*Table structure for table `oms_payment_info` */

DROP TABLE IF EXISTS `oms_payment_info`;

CREATE TABLE `oms_payment_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_sn` char(32) DEFAULT NULL COMMENT '订单号（对外业务号）',
  `order_id` bigint DEFAULT NULL COMMENT '订单id',
  `alipay_trade_no` varchar(50) DEFAULT NULL COMMENT '支付宝交易流水号',
  `total_amount` decimal(18,4) DEFAULT NULL COMMENT '支付总金额',
  `subject` varchar(200) DEFAULT NULL COMMENT '交易内容',
  `payment_status` varchar(20) DEFAULT NULL COMMENT '支付状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `confirm_time` datetime DEFAULT NULL COMMENT '确认时间',
  `callback_content` varchar(4000) DEFAULT NULL COMMENT '回调内容',
  `callback_time` datetime DEFAULT NULL COMMENT '回调时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付信息表';

/*Data for the table `oms_payment_info` */

insert  into `oms_payment_info`(`id`,`order_sn`,`order_id`,`alipay_trade_no`,`total_amount`,`subject`,`payment_status`,`create_time`,`confirm_time`,`callback_content`,`callback_time`) values 
(1,'2022082217373252',NULL,'2022082222001482320501841911',3997.0000,NULL,'TRADE_SUCCESS','2022-08-22 17:37:55','2022-08-22 17:38:04',NULL,'2022-08-22 17:38:06'),
(2,'2022082217400252',NULL,'2022082222001482320501841471',3997.0000,NULL,'TRADE_SUCCESS','2022-08-22 17:41:54','2022-08-22 17:42:03',NULL,'2022-08-22 17:42:04'),
(3,'2022082218384452',NULL,'2022082222001482320501841780',3997.0000,NULL,'TRADE_SUCCESS','2022-08-22 18:38:55','2022-08-22 18:38:59',NULL,'2022-08-22 18:39:00');

/*Table structure for table `oms_refund_info` */

DROP TABLE IF EXISTS `oms_refund_info`;

CREATE TABLE `oms_refund_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_return_id` bigint DEFAULT NULL COMMENT '退款的订单',
  `refund` decimal(18,4) DEFAULT NULL COMMENT '退款金额',
  `refund_sn` varchar(64) DEFAULT NULL COMMENT '退款交易流水号',
  `refund_status` tinyint(1) DEFAULT NULL COMMENT '退款状态',
  `refund_channel` tinyint DEFAULT NULL COMMENT '退款渠道[1-支付宝，2-微信，3-银联，4-汇款]',
  `refund_content` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='退款信息';

/*Data for the table `oms_refund_info` */

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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
