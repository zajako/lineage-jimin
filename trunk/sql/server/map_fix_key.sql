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
-- Table structure for `map_fix_key`
-- ----------------------------
DROP TABLE IF EXISTS `map_fix_key`;
CREATE TABLE `map_fix_key` (
  `locX` smallint(6) unsigned NOT NULL,
  `locY` smallint(6) unsigned NOT NULL,
  `mapId` smallint(6) unsigned NOT NULL,
  PRIMARY KEY (`locX`,`locY`,`mapId`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of map_fix_key
-- ----------------------------
