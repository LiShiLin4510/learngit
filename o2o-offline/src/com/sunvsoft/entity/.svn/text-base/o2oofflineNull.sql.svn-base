/*
Navicat MySQL Data Transfer

Source Server         : o2o
Source Server Version : 50539
Source Host           : localhost:3306
Source Database       : o2ooffline

Target Server Type    : MYSQL
Target Server Version : 50539
File Encoding         : 65001

Date: 2015-12-29 17:02:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `account_data`
-- ----------------------------
DROP TABLE IF EXISTS `account_data`;
CREATE TABLE `account_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) DEFAULT NULL,
  `goods_num` int(11) DEFAULT NULL,
  `goods_amount` decimal(20,2) DEFAULT NULL,
  `loan_type` varchar(11) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `remark` varchar(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `date` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account_data
-- ----------------------------

-- ----------------------------
-- Table structure for `backup_log`
-- ----------------------------
DROP TABLE IF EXISTS `backup_log`;
CREATE TABLE `backup_log` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `diskName` varchar(20) DEFAULT NULL,
  `hasBackuped` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of backup_log
-- ----------------------------

-- ----------------------------
-- Table structure for `es_classes`
-- ----------------------------
DROP TABLE IF EXISTS `es_classes`;
CREATE TABLE `es_classes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(10) DEFAULT NULL,
  `classes` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of es_classes
-- ----------------------------

-- ----------------------------
-- Table structure for `es_goods`
-- ----------------------------
DROP TABLE IF EXISTS `es_goods`;
CREATE TABLE `es_goods` (
  `goods_id` int(8) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `unit` varchar(20) DEFAULT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of es_goods
-- ----------------------------

-- ----------------------------
-- Table structure for `es_goods_spec`
-- ----------------------------
DROP TABLE IF EXISTS `es_goods_spec`;
CREATE TABLE `es_goods_spec` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `spec_id` int(8) DEFAULT NULL,
  `spec_value_id` int(8) DEFAULT NULL,
  `goods_id` int(8) DEFAULT NULL,
  `product_id` int(8) DEFAULT NULL,
  `spec_img` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of es_goods_spec
-- ----------------------------

-- ----------------------------
-- Table structure for `es_member`
-- ----------------------------
DROP TABLE IF EXISTS `es_member`;
CREATE TABLE `es_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `card_id` varchar(255) DEFAULT NULL,
  `real_name` varchar(100) DEFAULT NULL,
  `id_type` int(20) DEFAULT '0',
  `id_number` varchar(100) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `mail` varchar(100) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `operator` varchar(100) DEFAULT NULL,
  `issuer_date` bigint(20) DEFAULT NULL,
  `accepted_date` bigint(20) DEFAULT NULL,
  `card_state` int(20) DEFAULT NULL,
  `card_integration` varchar(255) DEFAULT NULL,
  `wechat` varchar(50) DEFAULT NULL,
  `qq` varchar(20) DEFAULT NULL,
  `whatsapp` varchar(50) DEFAULT NULL,
  `facebook` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Records of es_member
-- ----------------------------

-- ----------------------------
-- Table structure for `es_order`
-- ----------------------------
DROP TABLE IF EXISTS `es_order`;
CREATE TABLE `es_order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sn` varchar(50) DEFAULT NULL,
  `cardNumber` varchar(50) DEFAULT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `cash_register` varchar(20) DEFAULT NULL,
  `pos_num` varchar(20) DEFAULT NULL,
  `status` smallint(1) DEFAULT NULL,
  `payment_id` int(8) DEFAULT NULL,
  `payment_name` varchar(50) DEFAULT NULL,
  `payment_type` varchar(50) DEFAULT NULL,
  `paymoney` decimal(20,2) DEFAULT NULL,
  `goods` longtext,
  `create_time` bigint(20) DEFAULT NULL,
  `goods_amount` decimal(20,2) DEFAULT NULL,
  `order_amount` decimal(20,2) DEFAULT NULL,
  `goods_num` int(8) DEFAULT NULL,
  `remark` longtext,
  `disabled` varchar(1) DEFAULT NULL,
  `complete_time` int(11) DEFAULT NULL,
  `store_id` varchar(50) DEFAULT NULL,
  `parent_id` int(10) DEFAULT NULL,
  `currency` varchar(3) DEFAULT NULL,
  `price_zh` decimal(20,2) DEFAULT NULL,
  `date` varchar(10) DEFAULT NULL,
  `classes` int(11) DEFAULT NULL,
  `balance_status` int(11) DEFAULT NULL,
  `cardAmount` decimal(20,2) DEFAULT NULL,
  `cashAmount` decimal(20,2) DEFAULT NULL,
  `refNo` varchar(50) DEFAULT NULL,
  `cerNo` varchar(50) DEFAULT NULL,
  `export_status` int(11) DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL,
  `taxes` decimal(20,2) DEFAULT NULL,
  `allowance`  decimal(20,2) DEFAULT 0,
  `coupon`  decimal(20,2) DEFAULT 0, 
  `discountAmount`  decimal(20,2) DEFAULT 0, 
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of es_order
-- ----------------------------

-- ----------------------------
-- Table structure for `es_order_items`
-- ----------------------------
DROP TABLE IF EXISTS `es_order_items`;
CREATE TABLE `es_order_items` (
  `item_id` int(8) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) DEFAULT NULL,
  `goods_id` int(8) DEFAULT NULL,
  `product_id` int(8) DEFAULT NULL,
  `cat_id` int(8) DEFAULT NULL,
  `num` int(8) DEFAULT NULL,
  `bar_code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` decimal(20,2) DEFAULT NULL,
  `state` smallint(1) DEFAULT NULL,
  `currency` varchar(3) DEFAULT NULL,
  `export_status` int(11) DEFAULT NULL,
  `sn` varchar(50) DEFAULT NULL,
  `taxes` decimal(20,2) DEFAULT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of es_order_items
-- ----------------------------

-- ----------------------------
-- Table structure for `es_product`
-- ----------------------------
DROP TABLE IF EXISTS `es_product`;
CREATE TABLE `es_product` (
  `product_id` int(8) NOT NULL AUTO_INCREMENT,
  `goods_id` int(8) DEFAULT NULL,
  `name` varchar(500) DEFAULT NULL,
  `bar_code` varchar(50) DEFAULT NULL,
  `price` decimal(20,2) DEFAULT NULL,
  `whprice_ru` decimal(20,2) DEFAULT NULL,
  `specs` longtext,
  `tax_system`  int(11) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of es_product
-- ----------------------------

-- ----------------------------
-- Table structure for `es_sellback_list`
-- ----------------------------
DROP TABLE IF EXISTS `es_sellback_list`;
CREATE TABLE `es_sellback_list` (
  `id` int(11) NOT NULL,
  `tradeno` varchar(100) DEFAULT NULL,
  `tradestatus` smallint(1) DEFAULT NULL,
  `ordersn` varchar(100) DEFAULT NULL,
  `regoperator` varchar(100) DEFAULT NULL,
  `regtime` bigint(20) DEFAULT NULL,
  `alltotal_pay` decimal(20,2) DEFAULT NULL,
  `member_id` varchar(100) DEFAULT NULL,
  `refund_way` varchar(255) DEFAULT NULL,
  `total` varchar(50) DEFAULT NULL,
  `export_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of es_sellback_list
-- ----------------------------

-- ----------------------------
-- Table structure for `es_shift`
-- ----------------------------
DROP TABLE IF EXISTS `es_shift`;
CREATE TABLE `es_shift` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_code` char(100) DEFAULT NULL,
  `begin_time` bigint(20) DEFAULT NULL,
  `classes` int(11) DEFAULT NULL,
  `exit_time` bigint(20) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `date` char(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of es_shift
-- ----------------------------

-- ----------------------------
-- Table structure for `es_shift_day`
-- ----------------------------
DROP TABLE IF EXISTS `es_shift_day`;
CREATE TABLE `es_shift_day` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_code` char(100) DEFAULT NULL,
  `date` char(10) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of es_shift_day
-- ----------------------------

-- ----------------------------
-- Table structure for `es_spec_values`
-- ----------------------------
DROP TABLE IF EXISTS `es_spec_values`;
CREATE TABLE `es_spec_values` (
  `spec_value_id` int(8) NOT NULL AUTO_INCREMENT,
  `spec_id` int(8) DEFAULT NULL,
  `spec_value` varchar(100) DEFAULT NULL,
  `spec_image` varchar(255) DEFAULT NULL,
  `spec_order` int(8) DEFAULT NULL,
  `spec_type` smallint(1) DEFAULT NULL,
  `spec_value_ru` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`spec_value_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of es_spec_values
-- ----------------------------

-- ----------------------------
-- Table structure for `es_user`
-- ----------------------------
DROP TABLE IF EXISTS `es_user`;
CREATE TABLE `es_user` (
  `userid` int(8) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `realname` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of es_user
-- ----------------------------

-- ----------------------------
-- Table structure for `order_id_get`
-- ----------------------------
DROP TABLE IF EXISTS `order_id_get`;
CREATE TABLE `order_id_get` (
  `id` bigint(8) NOT NULL DEFAULT '0',
  `order_id` bigint(8) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_id_get
-- ----------------------------

-- ----------------------------
-- Table structure for `es_member_address`
-- ----------------------------
DROP TABLE IF EXISTS `es_member_address`;
CREATE TABLE `es_member_address` (
  `addr_id` int(8) NOT NULL AUTO_INCREMENT,
  `member_id` varchar(50) NOT NULL DEFAULT '0',
  `province_id` int(11) DEFAULT NULL,
  `city_id` int(11) DEFAULT NULL,
  `region_id` int(11) DEFAULT NULL,
  `region` varchar(50) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `province` varchar(50) DEFAULT NULL,
  `addr` varchar(255) DEFAULT NULL,
  `zip` varchar(20) DEFAULT NULL,
  `tel` varchar(30) DEFAULT NULL,
  `mobile` varchar(30) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `def_addr` int(11) DEFAULT '0',
  `name` varchar(50) DEFAULT NULL,
  `export_status` int(1) DEFAULT NULL,
  `country` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`addr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `es_store_pro`;
CREATE TABLE `es_store_pro` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `store_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `original_price` decimal(10,2) DEFAULT NULL,
  `store_price` decimal(10,2) DEFAULT NULL,
  `taxes` decimal(20,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `es_sellback_goodslist`;
CREATE TABLE `es_sellback_goodslist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recid` int(8) DEFAULT NULL,
  `goods_id` int(8) DEFAULT NULL,
  `product_id` int(8) DEFAULT NULL,
  `ship_num` int(8) DEFAULT NULL,
  `price` decimal(20,2) DEFAULT NULL,
  `return_num` int(8) DEFAULT NULL,
  `storage_num` int(8) DEFAULT NULL,
  `goods_remark` varchar(100) DEFAULT NULL,
  `export_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

ALTER TABLE `es_order_items`
ADD COLUMN `tax_system` int(11) NULL AFTER `taxes`,
ADD COLUMN `unit`  varchar(20) NULL AFTER `tax_system`,
ADD COLUMN `spec_value` longtext NULL AFTER `unit`;

ALTER TABLE `es_member`
ADD COLUMN `store_id`  int(11) NULL AFTER `facebook`,
ADD COLUMN `member_id`  int(11) NULL AFTER `store_id`;

ALTER TABLE `es_store_pro`
ADD COLUMN `goods_index`  int(11) NULL AFTER `taxes`;

ALTER TABLE `es_product`
ADD COLUMN `goods_taxes`  decimal(20,2) NULL AFTER `tax_system`;

ALTER TABLE `es_goods`
ADD COLUMN `thumbnail`  varchar(255) NULL AFTER `unit`;

ALTER TABLE `es_goods`
ADD COLUMN `thumbnail`  varchar(255) NULL AFTER `unit`;

/* 添加打折allowance、抵金券coupon字段在es_order表中  shl 2017/2/8 */
ALTER TABLE `es_order`
ADD COLUMN `allowance`  decimal(20,2) NULL AFTER `taxes`;
ALTER TABLE `es_order`
ADD COLUMN `coupon`  decimal(20,2) NULL AFTER `allowance`;

/* 添加优惠金额discountAmount字段在es_order表中  shl 2017/2/15 */
ALTER TABLE `es_order`
ADD COLUMN `discountAmount`  decimal(20,2) NULL AFTER `coupon`;