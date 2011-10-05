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
-- Table structure for `bonesystem`
-- ----------------------------
DROP TABLE IF EXISTS `bonesystem`;
CREATE TABLE `bonesystem` (
  `id` int(2) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `time` datetime DEFAULT NULL,
  `openLoc` int(10) NOT NULL DEFAULT '0',
  `moveLoc` int(10) NOT NULL DEFAULT '0',
  `extend` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of bonesystem
-- ----------------------------
INSERT IGNORE INTO `bonesystem` VALUES 
('1', '½Ã°£ÀÇ ±Õ¿­', '2010-03-24 19:00:00', '0', '0', '0');
