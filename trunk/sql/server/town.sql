/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 60008
Source Host           : localhost:3306
Source Database       : bone

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-10-05 19:40
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
INSERT IGNORE INTO `town` VALUES 
('1', '말하는섬', '0', '', '0', '0', '0', '0', '0', '0'),
('2', '은기사 마을', '0', null, '0', '0', '0', '0', '0', '0'),
('3', '글루디오', '0', null, '0', '0', '0', '0', '0', '0'),
('4', '화전민 마을', '0', '', '0', '0', '0', '0', '0', '0'),
('5', '우즈백', '0', '', '0', '0', '0', '0', '0', '0'),
('6', '켄트', '0', '', '0', '0', '0', '0', '0', '0'),
('7', '기란', '0', null, '0', '0', '0', '0', '0', '0'),
('8', '하이네', '0', null, '0', '0', '0', '0', '0', '0'),
('9', '웰던', '0', '', '0', '0', '0', '0', '0', '0'),
('10', '오렌', '0', '', '0', '0', '0', '0', '0', '0');
