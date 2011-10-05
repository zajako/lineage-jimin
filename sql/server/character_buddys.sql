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
-- Table structure for `character_buddys`
-- ----------------------------
DROP TABLE IF EXISTS `character_buddys`;
CREATE TABLE `character_buddys` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `char_id` int(10) NOT NULL DEFAULT '0',
  `buddy_id` int(10) unsigned NOT NULL DEFAULT '0',
  `buddy_name` varchar(45) NOT NULL,
  PRIMARY KEY (`char_id`,`buddy_id`),
  KEY `key_id` (`id`)
) DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of character_buddys
-- ----------------------------
