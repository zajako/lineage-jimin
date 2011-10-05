/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:10:53
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `ub_times`
-- ----------------------------
DROP TABLE IF EXISTS `ub_times`;
CREATE TABLE `ub_times` (
  `ub_id` int(10) unsigned NOT NULL DEFAULT '0',
  `ub_time` int(10) unsigned NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of ub_times
-- ----------------------------
INSERT INTO `ub_times` VALUES ('1', '100');
INSERT INTO `ub_times` VALUES ('1', '300');
INSERT INTO `ub_times` VALUES ('1', '900');
INSERT INTO `ub_times` VALUES ('1', '1500');
INSERT INTO `ub_times` VALUES ('1', '2100');
INSERT INTO `ub_times` VALUES ('1', '2300');
INSERT INTO `ub_times` VALUES ('2', '100');
INSERT INTO `ub_times` VALUES ('2', '400');
INSERT INTO `ub_times` VALUES ('2', '700');
INSERT INTO `ub_times` VALUES ('2', '1000');
INSERT INTO `ub_times` VALUES ('2', '1300');
INSERT INTO `ub_times` VALUES ('2', '1600');
INSERT INTO `ub_times` VALUES ('2', '1900');
INSERT INTO `ub_times` VALUES ('2', '2200');
INSERT INTO `ub_times` VALUES ('3', '200');
INSERT INTO `ub_times` VALUES ('3', '500');
INSERT INTO `ub_times` VALUES ('3', '800');
INSERT INTO `ub_times` VALUES ('3', '1100');
INSERT INTO `ub_times` VALUES ('3', '1400');
INSERT INTO `ub_times` VALUES ('3', '1700');
INSERT INTO `ub_times` VALUES ('3', '2000');
INSERT INTO `ub_times` VALUES ('3', '2300');
INSERT INTO `ub_times` VALUES ('4', '100');
INSERT INTO `ub_times` VALUES ('4', '400');
INSERT INTO `ub_times` VALUES ('4', '700');
INSERT INTO `ub_times` VALUES ('4', '1000');
INSERT INTO `ub_times` VALUES ('4', '1300');
INSERT INTO `ub_times` VALUES ('4', '1600');
INSERT INTO `ub_times` VALUES ('4', '1900');
INSERT INTO `ub_times` VALUES ('4', '2200');
INSERT INTO `ub_times` VALUES ('5', '200');
INSERT INTO `ub_times` VALUES ('5', '500');
INSERT INTO `ub_times` VALUES ('5', '800');
INSERT INTO `ub_times` VALUES ('5', '1100');
INSERT INTO `ub_times` VALUES ('5', '1400');
INSERT INTO `ub_times` VALUES ('5', '1700');
INSERT INTO `ub_times` VALUES ('5', '2000');
INSERT INTO `ub_times` VALUES ('5', '2300');
