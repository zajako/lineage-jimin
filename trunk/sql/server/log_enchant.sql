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
-- Table structure for `log_enchant`
-- ----------------------------
DROP TABLE IF EXISTS `log_enchant`;
CREATE TABLE `log_enchant` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `char_id` int(10) NOT NULL DEFAULT '0',
  `item_id` int(10) unsigned NOT NULL DEFAULT '0',
  `old_enchantlvl` int(3) NOT NULL DEFAULT '0',
  `new_enchantlvl` int(3) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `key_id` (`char_id`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='MyISAM free: 10240 kB; MyISAM free: 10240 kB';

-- ----------------------------
-- Records of log_enchant
-- ----------------------------
