/*
Navicat MySQL Data Transfer

Source Server         : 阿里云理财数据库
Source Server Version : 50518
Source Host           : rdsyfmnyqyfmnyq.mysql.rds.aliyuncs.com:3306
Source Database       : lc

Target Server Type    : MYSQL
Target Server Version : 50518
File Encoding         : 65001

Date: 2019-03-19 11:21:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for lc_history
-- ----------------------------
DROP TABLE IF EXISTS `lc_history`;
CREATE TABLE `lc_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `option` int(1) DEFAULT NULL COMMENT '1:投资,2:提现',
  `optionMoney` double(10,2) DEFAULT NULL COMMENT '操作金额,投资用负数,提现用正数',
  `createTime` datetime DEFAULT NULL COMMENT '操作时间',
  `pid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `r_pid` (`pid`),
  CONSTRAINT `r_pid` FOREIGN KEY (`pid`) REFERENCES `lc_project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for lc_project
-- ----------------------------
DROP TABLE IF EXISTS `lc_project`;
CREATE TABLE `lc_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT '投资平台',
  `code` varchar(50) DEFAULT NULL COMMENT '投资编码',
  `type` int(1) DEFAULT NULL COMMENT '1:指数型、2:p2p、3:债券型、4:货币型、5:混合型、6:定期理财、7:养老基金',
  `appname` varchar(50) DEFAULT NULL COMMENT 'app名称',
  `riskLevel` int(1) DEFAULT NULL COMMENT '1:高、2:中、3:低',
  `mark` varchar(50) DEFAULT NULL COMMENT '备注',
  `currentMoney` double(10,2) DEFAULT NULL COMMENT '当前金额',
  `lastUpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `p_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4;
