/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:01:57
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
) ENGINE=MyISAM AUTO_INCREMENT=1413 DEFAULT CHARSET=euckr COMMENT='MyISAM free: 10240 kB; MyISAM free: 10240 kB';

-- ----------------------------
-- Records of character_buddys
-- ----------------------------
