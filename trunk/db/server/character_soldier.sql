/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:02:35
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
