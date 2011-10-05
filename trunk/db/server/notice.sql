/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:06:48
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `notice`
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` int(30) NOT NULL,
  `message` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES ('1', '\\f=EVA Server ^               PlayEva.net^ ^');
