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
-- Table structure for `ub_managers`
-- ----------------------------
DROP TABLE IF EXISTS `ub_managers`;
CREATE TABLE `ub_managers` (
  `ub_id` int(10) unsigned NOT NULL DEFAULT '0',
  `ub_manager_npc_id` int(10) unsigned NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of ub_managers
-- ----------------------------
INSERT IGNORE INTO `ub_managers` VALUES 
('1', '50037'),
('1', '50038'),
('2', '50041'),
('2', '50042'),
('3', '50028'),
('3', '50029'),
('4', '50018'),
('4', '50019'),
('5', '50061'),
('5', '50062');
