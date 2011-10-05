/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:05:26
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
