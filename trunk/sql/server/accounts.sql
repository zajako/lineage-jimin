/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:00:40
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `accounts`
-- ----------------------------
DROP TABLE IF EXISTS `accounts`;
CREATE TABLE `accounts` (
  `login` varchar(50) NOT NULL DEFAULT '',
  `password` varchar(50) DEFAULT NULL,
  `lastactive` datetime DEFAULT NULL,
  `access_level` int(11) DEFAULT NULL,
  `ip` varchar(20) NOT NULL DEFAULT '',
  `host` varchar(255) NOT NULL DEFAULT '',
  `banned` int(11) unsigned NOT NULL DEFAULT '0',
  `charslot` int(11) NOT NULL,
  `gamepassword` int(11) NOT NULL,
  `notice` varchar(20) DEFAULT '0',
  `webAccount` varchar(50) DEFAULT NULL,
  `point_time` int(11) NOT NULL DEFAULT '0',
  `Point_time_ready` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`login`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='MyISAM free: 3072 kB';

-- ----------------------------
-- Records of accounts
-- ----------------------------
