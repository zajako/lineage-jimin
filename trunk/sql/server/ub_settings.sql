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
-- Table structure for `ub_settings`
-- ----------------------------
DROP TABLE IF EXISTS `ub_settings`;
CREATE TABLE `ub_settings` (
  `ub_id` int(10) unsigned NOT NULL DEFAULT '0',
  `ub_name` varchar(45) NOT NULL DEFAULT '',
  `ub_mapid` int(10) unsigned NOT NULL DEFAULT '0',
  `ub_area_x1` int(10) unsigned NOT NULL DEFAULT '0',
  `ub_area_y1` int(10) unsigned NOT NULL DEFAULT '0',
  `ub_area_x2` int(10) unsigned NOT NULL DEFAULT '0',
  `ub_area_y2` int(10) unsigned NOT NULL DEFAULT '0',
  `min_lvl` int(10) unsigned NOT NULL DEFAULT '0',
  `max_lvl` int(10) unsigned NOT NULL DEFAULT '0',
  `max_player` int(10) unsigned NOT NULL DEFAULT '0',
  `enter_royal` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `enter_knight` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `enter_mage` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `enter_elf` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `enter_darkelf` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `enter_dragonknight` tinyint(3) NOT NULL DEFAULT '0',
  `enter_blackwizard` tinyint(3) NOT NULL DEFAULT '0',
  `enter_male` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `enter_female` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `use_pot` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `hpr_bonus` int(10) NOT NULL DEFAULT '0',
  `mpr_bonus` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ub_id`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of ub_settings
-- ----------------------------
INSERT IGNORE INTO `ub_settings` VALUES 
('1', '기란 콜로세움', '88', '33494', '32724', '33516', '32746', '52', '99', '20', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '0'),
('2', '웰던 콜로세움', '98', '32682', '32878', '32717', '32913', '45', '60', '20', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '0'),
('3', '글루디오 콜로세움', '92', '32682', '32878', '32717', '32913', '31', '51', '20', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '0'),
('4', '말하는섬 콜로세움', '91', '32682', '32878', '32717', '32913', '25', '44', '20', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '0'),
('5', '은기사 콜로세움', '95', '32682', '32878', '32717', '32913', '1', '30', '20', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '0', '0');
