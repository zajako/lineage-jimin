/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:04:25
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `items_race`
-- ----------------------------
DROP TABLE IF EXISTS `items_race`;
CREATE TABLE `items_race` (
  `item_id` int(10) unsigned NOT NULL DEFAULT '0',
  `name` varchar(45) NOT NULL DEFAULT '',
  `item_type` varchar(40) NOT NULL DEFAULT '',
  `material` varchar(45) NOT NULL DEFAULT '',
  `inv_gfxid` int(10) NOT NULL DEFAULT '0',
  `ground_gfxid` int(10) unsigned NOT NULL DEFAULT '0',
  `name_id` varchar(30) NOT NULL DEFAULT '0',
  `weight` int(10) unsigned NOT NULL DEFAULT '0',
  `price` int(10) unsigned NOT NULL DEFAULT '0',
  `consume` int(10) NOT NULL DEFAULT '0',
  `piles` int(10) NOT NULL DEFAULT '0',
  `hungry` int(10) NOT NULL DEFAULT '0',
  `minhp_power` int(10) NOT NULL DEFAULT '0',
  `maxhp_power` int(10) NOT NULL DEFAULT '0',
  `tic_hp` int(10) NOT NULL DEFAULT '0',
  `tic_mp` int(10) NOT NULL DEFAULT '0',
  `continuous` int(10) NOT NULL DEFAULT '0',
  `use_royal` int(10) unsigned NOT NULL DEFAULT '0',
  `use_knight` int(10) unsigned NOT NULL DEFAULT '0',
  `use_elf` int(10) unsigned NOT NULL DEFAULT '0',
  `use_mage` int(10) unsigned NOT NULL DEFAULT '0',
  `use_darkelf` int(10) unsigned NOT NULL DEFAULT '0',
  `add_str` int(10) unsigned NOT NULL DEFAULT '0',
  `add_dex` int(10) unsigned NOT NULL DEFAULT '0',
  `add_con` int(10) unsigned NOT NULL DEFAULT '0',
  `add_int` int(10) unsigned NOT NULL DEFAULT '0',
  `add_wis` int(10) unsigned NOT NULL DEFAULT '0',
  `add_cha` int(10) unsigned NOT NULL DEFAULT '0',
  `spellpower` int(10) unsigned NOT NULL DEFAULT '0',
  `minmp_power` int(10) NOT NULL DEFAULT '0',
  `maxmp_power` int(10) NOT NULL DEFAULT '0',
  `tilley` int(10) NOT NULL DEFAULT '0',
  `haste` int(10) NOT NULL DEFAULT '0',
  `bravery` int(10) NOT NULL DEFAULT '0',
  `lvl` int(10) NOT NULL DEFAULT '0',
  `effect_id` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`item_id`)
) ENGINE=MyISAM AUTO_INCREMENT=50 DEFAULT CHARSET=euckr COMMENT='InnoDB free: 16384 kB; InnoDB free: 16384 kB; InnoDB free: 1';

-- ----------------------------
-- Records of items_race
-- ----------------------------
