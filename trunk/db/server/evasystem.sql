/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:04:01
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `evasystem`
-- ----------------------------
DROP TABLE IF EXISTS `evasystem`;
CREATE TABLE `evasystem` (
  `id` int(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `time` datetime DEFAULT NULL,
  `openLoc` int(10) NOT NULL DEFAULT '0',
  `moveLoc` int(10) NOT NULL DEFAULT '0',
  `extend` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of evasystem
-- ----------------------------
INSERT INTO `evasystem` VALUES ('1', '시간의 균열', '2010-03-24 19:00:00', '0', '0', '0');
