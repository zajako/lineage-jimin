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
-- Table structure for `castle_soldier`
-- ----------------------------
DROP TABLE IF EXISTS `castle_soldier`;
CREATE TABLE `castle_soldier` (
  `castle_id` int(2) NOT NULL AUTO_INCREMENT,
  `soldier1` int(2) NOT NULL DEFAULT '0',
  `soldier1_npcid` int(6) NOT NULL DEFAULT '0',
  `soldier1_name` varchar(10) NOT NULL,
  `soldier2` int(2) NOT NULL DEFAULT '0',
  `soldier2_npcid` int(6) NOT NULL DEFAULT '0',
  `soldier2_name` varchar(10) NOT NULL,
  `soldier3` int(2) NOT NULL DEFAULT '0',
  `soldier3_npcid` int(6) NOT NULL DEFAULT '0',
  `soldier3_name` varchar(10) NOT NULL,
  `soldier4` int(2) NOT NULL DEFAULT '0',
  `soldier4_npcid` int(6) NOT NULL DEFAULT '0',
  `soldier4_name` varchar(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`castle_id`)
) DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of castle_soldier
-- ----------------------------
INSERT INTO `castle_soldier` VALUES ('4', '0', '0', '$4223', '0', '0', '$4222', '0', '0', '$4224', '0', '0', '$4225');
