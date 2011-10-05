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
-- Table structure for `character_soldier`
-- ----------------------------
DROP TABLE IF EXISTS `character_soldier`;
CREATE TABLE `character_soldier` (
  `char_id` int(12) NOT NULL,
  `npc_id` int(12) NOT NULL DEFAULT '0',
  `count` int(4) NOT NULL DEFAULT '0',
  `castle_id` int(4) NOT NULL DEFAULT '0',
  `time` int(18) NOT NULL,
  PRIMARY KEY (`char_id`,`time`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of character_soldier
-- ----------------------------
