/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:10:29
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `town`
-- ----------------------------
DROP TABLE IF EXISTS `town`;
CREATE TABLE `town` (
  `town_id` int(10) unsigned NOT NULL DEFAULT '0',
  `name` varchar(45) NOT NULL DEFAULT '',
  `leader_id` int(10) unsigned NOT NULL DEFAULT '0',
  `leader_name` varchar(45) DEFAULT NULL,
  `tax_rate` int(10) unsigned NOT NULL DEFAULT '0',
  `tax_rate_reserved` int(10) unsigned NOT NULL DEFAULT '0',
  `sales_money` int(10) unsigned NOT NULL DEFAULT '0',
  `sales_money_yesterday` int(10) unsigned NOT NULL DEFAULT '0',
  `town_tax` int(10) unsigned NOT NULL DEFAULT '0',
  `town_fix_tax` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`town_id`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of town
-- ----------------------------
INSERT INTO `town` VALUES ('1', '말하는섬', '0', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `town` VALUES ('2', '은기사 마을', '0', null, '0', '0', '0', '0', '0', '0');
INSERT INTO `town` VALUES ('3', '글루디오', '0', null, '0', '0', '0', '0', '0', '0');
INSERT INTO `town` VALUES ('4', '화전민 마을', '0', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `town` VALUES ('5', '우즈백', '0', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `town` VALUES ('6', '켄트', '0', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `town` VALUES ('7', '기란', '0', null, '0', '0', '0', '0', '0', '0');
INSERT INTO `town` VALUES ('8', '하이네', '0', null, '0', '0', '0', '0', '0', '0');
INSERT INTO `town` VALUES ('9', '웰던', '0', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `town` VALUES ('10', '오렌', '0', '', '0', '0', '0', '0', '0', '0');
