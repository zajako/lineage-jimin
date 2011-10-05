/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:02:25
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `character_quests`
-- ----------------------------
DROP TABLE IF EXISTS `character_quests`;
CREATE TABLE `character_quests` (
  `char_id` int(10) unsigned NOT NULL,
  `quest_id` int(10) unsigned NOT NULL DEFAULT '0',
  `quest_step` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`char_id`,`quest_id`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of character_quests
-- ----------------------------
