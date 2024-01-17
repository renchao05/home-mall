/*
SQLyog Community v13.2.1 (64 bit)
MySQL - 8.1.0 : Database - mall_sms
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mall_sms` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `mall_sms`;

/*Table structure for table `sms_coupon` */

DROP TABLE IF EXISTS `sms_coupon`;

CREATE TABLE `sms_coupon` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `coupon_type` tinyint(1) DEFAULT NULL COMMENT '优惠卷类型[0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券]',
  `coupon_img` varchar(2000) DEFAULT NULL COMMENT '优惠券图片',
  `coupon_name` varchar(100) DEFAULT NULL COMMENT '优惠卷名字',
  `num` int DEFAULT NULL COMMENT '数量',
  `amount` decimal(18,4) DEFAULT NULL COMMENT '金额',
  `per_limit` int DEFAULT NULL COMMENT '每人限领张数',
  `min_point` decimal(18,4) DEFAULT NULL COMMENT '使用门槛',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `use_type` tinyint(1) DEFAULT NULL COMMENT '使用类型[0->全场通用；1->指定分类；2->指定商品]',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `publish_count` int DEFAULT NULL COMMENT '发行数量',
  `use_count` int DEFAULT NULL COMMENT '已使用数量',
  `receive_count` int DEFAULT NULL COMMENT '领取数量',
  `enable_start_time` datetime DEFAULT NULL COMMENT '可以领取的开始日期',
  `enable_end_time` datetime DEFAULT NULL COMMENT '可以领取的结束日期',
  `code` varchar(64) DEFAULT NULL COMMENT '优惠码',
  `member_level` tinyint(1) DEFAULT NULL COMMENT '可以领取的会员等级[0->不限等级，其他-对应等级]',
  `publish` tinyint(1) DEFAULT NULL COMMENT '发布状态[0-未发布，1-已发布]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='优惠券信息';

/*Data for the table `sms_coupon` */

/*Table structure for table `sms_coupon_history` */

DROP TABLE IF EXISTS `sms_coupon_history`;

CREATE TABLE `sms_coupon_history` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `coupon_id` bigint DEFAULT NULL COMMENT '优惠券id',
  `member_id` bigint DEFAULT NULL COMMENT '会员id',
  `member_nick_name` varchar(64) DEFAULT NULL COMMENT '会员名字',
  `get_type` tinyint(1) DEFAULT NULL COMMENT '获取方式[0->后台赠送；1->主动领取]',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `use_type` tinyint(1) DEFAULT NULL COMMENT '使用状态[0->未使用；1->已使用；2->已过期]',
  `use_time` datetime DEFAULT NULL COMMENT '使用时间',
  `order_id` bigint DEFAULT NULL COMMENT '订单id',
  `order_sn` bigint DEFAULT NULL COMMENT '订单号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='优惠券领取历史记录';

/*Data for the table `sms_coupon_history` */

/*Table structure for table `sms_coupon_spu_category_relation` */

DROP TABLE IF EXISTS `sms_coupon_spu_category_relation`;

CREATE TABLE `sms_coupon_spu_category_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `coupon_id` bigint DEFAULT NULL COMMENT '优惠券id',
  `category_id` bigint DEFAULT NULL COMMENT '产品分类id',
  `category_name` varchar(64) DEFAULT NULL COMMENT '产品分类名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='优惠券分类关联';

/*Data for the table `sms_coupon_spu_category_relation` */

/*Table structure for table `sms_coupon_spu_relation` */

DROP TABLE IF EXISTS `sms_coupon_spu_relation`;

CREATE TABLE `sms_coupon_spu_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `coupon_id` bigint DEFAULT NULL COMMENT '优惠券id',
  `spu_id` bigint DEFAULT NULL COMMENT 'spu_id',
  `spu_name` varchar(255) DEFAULT NULL COMMENT 'spu_name',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='优惠券与产品关联';

/*Data for the table `sms_coupon_spu_relation` */

/*Table structure for table `sms_home_adv` */

DROP TABLE IF EXISTS `sms_home_adv`;

CREATE TABLE `sms_home_adv` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) DEFAULT NULL COMMENT '名字',
  `pic` varchar(500) DEFAULT NULL COMMENT '图片地址',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  `click_count` int DEFAULT NULL COMMENT '点击数',
  `url` varchar(500) DEFAULT NULL COMMENT '广告详情连接地址',
  `note` varchar(500) DEFAULT NULL COMMENT '备注',
  `sort` int DEFAULT NULL COMMENT '排序',
  `publisher_id` bigint DEFAULT NULL COMMENT '发布者',
  `auth_id` bigint DEFAULT NULL COMMENT '审核者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='首页轮播广告';

/*Data for the table `sms_home_adv` */

/*Table structure for table `sms_home_subject` */

DROP TABLE IF EXISTS `sms_home_subject`;

CREATE TABLE `sms_home_subject` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(200) DEFAULT NULL COMMENT '专题名字',
  `title` varchar(255) DEFAULT NULL COMMENT '专题标题',
  `sub_title` varchar(255) DEFAULT NULL COMMENT '专题副标题',
  `status` tinyint(1) DEFAULT NULL COMMENT '显示状态',
  `url` varchar(500) DEFAULT NULL COMMENT '详情连接',
  `sort` int DEFAULT NULL COMMENT '排序',
  `img` varchar(500) DEFAULT NULL COMMENT '专题图片地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】';

/*Data for the table `sms_home_subject` */

/*Table structure for table `sms_home_subject_spu` */

DROP TABLE IF EXISTS `sms_home_subject_spu`;

CREATE TABLE `sms_home_subject_spu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(200) DEFAULT NULL COMMENT '专题名字',
  `subject_id` bigint DEFAULT NULL COMMENT '专题id',
  `spu_id` bigint DEFAULT NULL COMMENT 'spu_id',
  `sort` int DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='专题商品';

/*Data for the table `sms_home_subject_spu` */

/*Table structure for table `sms_member_price` */

DROP TABLE IF EXISTS `sms_member_price`;

CREATE TABLE `sms_member_price` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint DEFAULT NULL COMMENT 'sku_id',
  `member_level_id` bigint DEFAULT NULL COMMENT '会员等级id',
  `member_level_name` varchar(100) DEFAULT NULL COMMENT '会员等级名',
  `member_price` decimal(18,4) DEFAULT NULL COMMENT '会员对应价格',
  `add_other` tinyint(1) DEFAULT NULL COMMENT '可否叠加其他优惠[0-不可叠加优惠，1-可叠加]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=142 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品会员价格';

/*Data for the table `sms_member_price` */

insert  into `sms_member_price`(`id`,`sku_id`,`member_level_id`,`member_level_name`,`member_price`,`add_other`) values 
(10,4,2,'铜牌会员',1389.0000,NULL),
(11,4,3,'银牌会员',1379.0000,NULL),
(12,4,4,'金牌会员',1369.0000,NULL),
(13,5,2,'铜牌会员',1189.0000,NULL),
(14,5,3,'银牌会员',1179.0000,NULL),
(15,5,4,'金牌会员',1169.0000,NULL),
(16,6,2,'铜牌会员',1389.0000,NULL),
(17,6,3,'银牌会员',1379.0000,NULL),
(18,6,4,'金牌会员',1369.0000,NULL),
(19,7,2,'铜牌会员',1189.0000,NULL),
(20,7,3,'银牌会员',1179.0000,NULL),
(21,7,4,'金牌会员',1169.0000,NULL),
(22,8,2,'铜牌会员',1389.0000,NULL),
(23,8,3,'银牌会员',1379.0000,NULL),
(24,8,4,'金牌会员',1369.0000,NULL),
(25,9,2,'铜牌会员',1189.0000,NULL),
(26,9,3,'银牌会员',1179.0000,NULL),
(27,9,4,'金牌会员',1169.0000,NULL),
(28,10,2,'铜牌会员',1389.0000,NULL),
(29,10,3,'银牌会员',1379.0000,NULL),
(30,10,4,'金牌会员',1369.0000,NULL),
(31,11,2,'铜牌会员',1189.0000,NULL),
(32,11,3,'银牌会员',1179.0000,NULL),
(33,11,4,'金牌会员',1169.0000,NULL),
(34,12,2,'铜牌会员',0.0000,NULL),
(35,12,3,'银牌会员',0.0000,NULL),
(36,12,4,'金牌会员',0.0000,NULL),
(37,13,2,'铜牌会员',0.0000,NULL),
(38,13,3,'银牌会员',0.0000,NULL),
(39,13,4,'金牌会员',0.0000,NULL),
(40,14,2,'铜牌会员',0.0000,NULL),
(41,14,3,'银牌会员',0.0000,NULL),
(42,14,4,'金牌会员',0.0000,NULL),
(43,15,2,'铜牌会员',0.0000,NULL),
(44,15,3,'银牌会员',0.0000,NULL),
(45,15,4,'金牌会员',0.0000,NULL),
(46,16,2,'铜牌会员',0.0000,NULL),
(47,16,3,'银牌会员',0.0000,NULL),
(48,16,4,'金牌会员',0.0000,NULL),
(49,17,2,'铜牌会员',0.0000,NULL),
(50,17,3,'银牌会员',0.0000,NULL),
(51,17,4,'金牌会员',0.0000,NULL),
(52,18,2,'铜牌会员',0.0000,NULL),
(53,18,3,'银牌会员',0.0000,NULL),
(54,18,4,'金牌会员',0.0000,NULL),
(55,19,2,'铜牌会员',0.0000,NULL),
(56,19,3,'银牌会员',0.0000,NULL),
(57,19,4,'金牌会员',0.0000,NULL),
(58,24,2,'铜牌会员',1389.0000,NULL),
(59,24,3,'银牌会员',1379.0000,NULL),
(60,24,4,'金牌会员',1369.0000,NULL),
(61,25,2,'铜牌会员',1189.0000,NULL),
(62,25,3,'银牌会员',1179.0000,NULL),
(63,25,4,'金牌会员',1169.0000,NULL),
(64,26,2,'铜牌会员',1389.0000,NULL),
(65,26,3,'银牌会员',1379.0000,NULL),
(66,26,4,'金牌会员',1369.0000,NULL),
(67,27,2,'铜牌会员',1189.0000,NULL),
(68,27,3,'银牌会员',1179.0000,NULL),
(69,27,4,'金牌会员',1169.0000,NULL),
(124,61,2,'铜牌会员',1199.0000,NULL),
(125,61,3,'银牌会员',1099.0000,NULL),
(126,61,4,'金牌会员',999.0000,NULL),
(127,62,2,'铜牌会员',1399.0000,NULL),
(128,62,3,'银牌会员',1299.0000,NULL),
(129,62,4,'金牌会员',1199.0000,NULL),
(130,63,2,'铜牌会员',1199.0000,NULL),
(131,63,3,'银牌会员',1099.0000,NULL),
(132,63,4,'金牌会员',999.0000,NULL),
(133,64,2,'铜牌会员',1399.0000,NULL),
(134,64,3,'银牌会员',1299.0000,NULL),
(135,64,4,'金牌会员',1199.0000,NULL),
(136,65,2,'铜牌会员',1199.0000,NULL),
(137,65,3,'银牌会员',1099.0000,NULL),
(138,65,4,'金牌会员',999.0000,NULL),
(139,66,2,'铜牌会员',1399.0000,NULL),
(140,66,3,'银牌会员',1299.0000,NULL),
(141,66,4,'金牌会员',1199.0000,NULL);

/*Table structure for table `sms_seckill_promotion` */

DROP TABLE IF EXISTS `sms_seckill_promotion`;

CREATE TABLE `sms_seckill_promotion` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) DEFAULT NULL COMMENT '活动标题',
  `start_time` datetime DEFAULT NULL COMMENT '开始日期',
  `end_time` datetime DEFAULT NULL COMMENT '结束日期',
  `status` tinyint DEFAULT NULL COMMENT '上下线状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `user_id` bigint DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='秒杀活动';

/*Data for the table `sms_seckill_promotion` */

/*Table structure for table `sms_seckill_session` */

DROP TABLE IF EXISTS `sms_seckill_session`;

CREATE TABLE `sms_seckill_session` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(200) DEFAULT NULL COMMENT '场次名称',
  `start_time` datetime DEFAULT NULL COMMENT '每日开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '每日结束时间',
  `status` tinyint(1) DEFAULT NULL COMMENT '启用状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='秒杀活动场次';

/*Data for the table `sms_seckill_session` */

insert  into `sms_seckill_session`(`id`,`name`,`start_time`,`end_time`,`status`,`create_time`) values 
(1,'10:00','2022-08-24 15:00:00','2022-08-25 17:00:00',1,'2022-08-23 07:55:01'),
(2,'11:00','2022-08-26 11:00:00','2022-08-26 12:00:00',1,'2022-08-23 07:54:34');

/*Table structure for table `sms_seckill_sku_notice` */

DROP TABLE IF EXISTS `sms_seckill_sku_notice`;

CREATE TABLE `sms_seckill_sku_notice` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint DEFAULT NULL COMMENT 'member_id',
  `sku_id` bigint DEFAULT NULL COMMENT 'sku_id',
  `session_id` bigint DEFAULT NULL COMMENT '活动场次id',
  `subcribe_time` datetime DEFAULT NULL COMMENT '订阅时间',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `notice_type` tinyint(1) DEFAULT NULL COMMENT '通知方式[0-短信，1-邮件]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='秒杀商品通知订阅';

/*Data for the table `sms_seckill_sku_notice` */

/*Table structure for table `sms_seckill_sku_relation` */

DROP TABLE IF EXISTS `sms_seckill_sku_relation`;

CREATE TABLE `sms_seckill_sku_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `promotion_id` bigint DEFAULT NULL COMMENT '活动id',
  `promotion_session_id` bigint DEFAULT NULL COMMENT '活动场次id',
  `sku_id` bigint DEFAULT NULL COMMENT '商品id',
  `seckill_price` decimal(10,0) DEFAULT NULL COMMENT '秒杀价格',
  `seckill_count` decimal(10,0) DEFAULT NULL COMMENT '秒杀总量',
  `seckill_limit` decimal(10,0) DEFAULT NULL COMMENT '每人限购数量',
  `seckill_sort` int DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='秒杀活动商品关联';

/*Data for the table `sms_seckill_sku_relation` */

insert  into `sms_seckill_sku_relation`(`id`,`promotion_id`,`promotion_session_id`,`sku_id`,`seckill_price`,`seckill_count`,`seckill_limit`,`seckill_sort`) values 
(1,NULL,1,8,1000,1,1,0),
(2,NULL,1,9,999,1,1,0),
(3,NULL,2,11,2999,1,1,0),
(4,NULL,2,9,2099,10,1,0);

/*Table structure for table `sms_sku_full_reduction` */

DROP TABLE IF EXISTS `sms_sku_full_reduction`;

CREATE TABLE `sms_sku_full_reduction` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint DEFAULT NULL COMMENT 'spu_id',
  `full_price` decimal(18,4) DEFAULT NULL COMMENT '满多少',
  `reduce_price` decimal(18,4) DEFAULT NULL COMMENT '减多少',
  `add_other` tinyint(1) DEFAULT NULL COMMENT '是否参与其他优惠',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品满减信息';

/*Data for the table `sms_sku_full_reduction` */

insert  into `sms_sku_full_reduction`(`id`,`sku_id`,`full_price`,`reduce_price`,`add_other`) values 
(4,4,2000.0000,200.0000,1),
(5,5,2000.0000,200.0000,0),
(6,6,2000.0000,200.0000,1),
(7,7,2000.0000,200.0000,1),
(8,8,2000.0000,200.0000,1),
(9,9,2000.0000,200.0000,0),
(10,10,2000.0000,200.0000,1),
(11,11,2000.0000,200.0000,1),
(12,12,0.0000,0.0000,0),
(13,13,0.0000,0.0000,0),
(14,14,0.0000,0.0000,0),
(15,15,0.0000,0.0000,0),
(16,16,0.0000,0.0000,0),
(17,17,0.0000,0.0000,0),
(18,18,0.0000,0.0000,0),
(19,19,0.0000,0.0000,0),
(20,24,2000.0000,200.0000,1),
(21,25,2000.0000,200.0000,0),
(22,26,2000.0000,200.0000,1),
(23,27,2000.0000,200.0000,1),
(42,61,1000.0000,50.0000,1),
(43,62,1000.0000,50.0000,1),
(44,63,1000.0000,50.0000,1),
(45,64,1000.0000,50.0000,0),
(46,65,1000.0000,50.0000,1),
(47,66,1000.0000,50.0000,1);

/*Table structure for table `sms_sku_ladder` */

DROP TABLE IF EXISTS `sms_sku_ladder`;

CREATE TABLE `sms_sku_ladder` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint DEFAULT NULL COMMENT 'spu_id',
  `full_count` int DEFAULT NULL COMMENT '满几件',
  `discount` decimal(4,2) DEFAULT NULL COMMENT '打几折',
  `price` decimal(18,4) DEFAULT NULL COMMENT '折后价',
  `add_other` tinyint(1) DEFAULT NULL COMMENT '是否叠加其他优惠[0-不可叠加，1-可叠加]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品阶梯价格';

/*Data for the table `sms_sku_ladder` */

insert  into `sms_sku_ladder`(`id`,`sku_id`,`full_count`,`discount`,`price`,`add_other`) values 
(4,4,1000,0.90,NULL,1),
(5,5,1000,0.90,NULL,0),
(6,6,1000,0.90,NULL,1),
(7,7,1000,0.90,NULL,1),
(8,8,1000,0.90,NULL,1),
(9,9,1000,0.90,NULL,0),
(10,10,1000,0.90,NULL,1),
(11,11,1000,0.90,NULL,1),
(12,12,0,0.00,NULL,0),
(13,13,0,0.00,NULL,0),
(14,14,0,0.00,NULL,0),
(15,15,0,0.00,NULL,0),
(16,16,0,0.00,NULL,0),
(17,17,0,0.00,NULL,0),
(18,18,0,0.00,NULL,0),
(19,19,0,0.00,NULL,0),
(20,24,1000,0.90,NULL,1),
(21,25,1000,0.90,NULL,0),
(22,26,1000,0.90,NULL,1),
(23,27,1000,0.90,NULL,1),
(42,61,1,0.95,NULL,1),
(43,62,1,0.95,NULL,1),
(44,63,1,0.95,NULL,1),
(45,64,1,0.95,NULL,0),
(46,65,1,0.95,NULL,1),
(47,66,1,0.95,NULL,1);

/*Table structure for table `sms_spu_bounds` */

DROP TABLE IF EXISTS `sms_spu_bounds`;

CREATE TABLE `sms_spu_bounds` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `spu_id` bigint DEFAULT NULL,
  `grow_bounds` decimal(18,4) DEFAULT NULL COMMENT '成长积分',
  `buy_bounds` decimal(18,4) DEFAULT NULL COMMENT '购物积分',
  `work` tinyint(1) DEFAULT NULL COMMENT '优惠生效情况[1111（四个状态位，从右到左）;0 - 无优惠，成长积分是否赠送;1 - 无优惠，购物积分是否赠送;2 - 有优惠，成长积分是否赠送;3 - 有优惠，购物积分是否赠送【状态位0：不赠送，1：赠送】]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品spu积分设置';

/*Data for the table `sms_spu_bounds` */

insert  into `sms_spu_bounds`(`id`,`spu_id`,`grow_bounds`,`buy_bounds`,`work`) values 
(5,11,20.0000,20.0000,15),
(6,12,20.0000,20.0000,15),
(7,13,30.0000,65.0000,15),
(8,14,30.0000,65.0000,15),
(9,15,30.0000,65.0000,15),
(10,16,20.0000,20.0000,15),
(11,17,43.0000,33.0000,15),
(12,18,55.0000,44.0000,15),
(16,24,16.0000,14.0000,15);

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
