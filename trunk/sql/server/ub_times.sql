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
INSERT IGNORE INTO `ub_times` VALUES 
('1', '100'),
('1', '300'),
('1', '900'),
('1', '1500'),
('1', '2100'),
('1', '2300'),
('2', '100'),
('2', '400'),
('2', '700'),
('2', '1000'),
('2', '1300'),
('2', '1600'),
('2', '1900'),
('2', '2200'),
('3', '200'),
('3', '500'),
('3', '800'),
('3', '1100'),
('3', '1400'),
('3', '1700'),
('3', '2000'),
('3', '2300'),
('4', '100'),
('4', '400'),
('4', '700'),
('4', '1000'),
('4', '1300'),
('4', '1600'),
('4', '1900'),
('4', '2200'),
('5', '200'),
('5', '500'),
('5', '800'),
('5', '1100'),
('5', '1400'),
('5', '1700'),
('5', '2000'),
('5', '2300');
