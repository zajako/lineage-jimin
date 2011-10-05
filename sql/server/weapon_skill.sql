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
-- Table structure for `weapon_skill`
-- ----------------------------
DROP TABLE IF EXISTS `weapon_skill`;
CREATE TABLE `weapon_skill` (
  `weapon_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `note` varchar(255) DEFAULT NULL,
  `probability` int(11) unsigned NOT NULL DEFAULT '0',
  `fix_damage` int(11) unsigned NOT NULL DEFAULT '0',
  `random_damage` int(11) unsigned NOT NULL DEFAULT '0',
  `area` int(11) NOT NULL DEFAULT '0',
  `skill_id` int(11) unsigned NOT NULL DEFAULT '0',
  `skill_time` int(11) unsigned NOT NULL DEFAULT '0',
  `effect_id` int(11) unsigned NOT NULL DEFAULT '0',
  `effect_target` int(11) unsigned NOT NULL DEFAULT '0',
  `arrow_type` int(11) unsigned NOT NULL DEFAULT '0',
  `attr` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`weapon_id`)
) ENGINE=MyISAM AUTO_INCREMENT=415017 DEFAULT CHARSET=euckr COMMENT='MyISAM free: 10240 kB';

-- ----------------------------
-- Records of weapon_skill
-- ----------------------------
INSERT IGNORE INTO `weapon_skill` VALUES 
('47', '침묵 소도', '2', '0', '0', '0', '64', '16', '2177', '0', '0', '0'),
('54', '카트소드', '15', '28', '25', '0', '0', '0', '10', '0', '0', '8'),
('58', '데스나이트후레임브레이드', '7', '65', '15', '0', '0', '0', '1811', '0', '0', '2'),
('76', '론두듀아르브레이드', '15', '25', '25', '0', '0', '0', '1805', '0', '0', '1'),
('121', '아이스 여왕 스탭', '25', '85', '55', '0', '0', '0', '1810', '0', '0', '4'),
('203', '바르로그의 투 핸드 소도', '15', '80', '90', '2', '0', '0', '762', '0', '0', '2'),
('205', '달의 장궁', '6', '5', '0', '0', '0', '0', '6288', '0', '1', '0'),
('256', '할로윈 펌프킨 롱 소도', '8', '20', '15', '0', '0', '0', '2750', '0', '0', '1'),
('257', '할로윈 롱 소도', '8', '20', '15', '0', '0', '0', '2750', '0', '0', '1'),
('258', '얼티메이트 할로윈 롱 소도', '8', '20', '15', '0', '0', '0', '2750', '0', '0', '1'),
('412000', '뇌신검', '8', '30', '20', '0', '0', '0', '10', '0', '0', '8'),
('412003', '아크메이지의 지팡이', '7', '0', '0', '0', '56', '64', '2230', '0', '0', '0'),
('412004', '혹한의 창', '7', '20', '15', '3', '0', '0', '1804', '0', '0', '4'),
('412005', '광풍의 도끼', '7', '25', '15', '4', '0', '0', '758', '0', '0', '8'),
('413101', '악마왕의 양손검', '7', '0', '0', '0', '56', '20', '2230', '0', '0', '0'),
('413102', '악마왕의 이도류', '7', '0', '0', '0', '56', '20', '2230', '0', '0', '0'),
('413103', '악마왕의 지팡이', '7', '0', '0', '0', '56', '20', '2230', '0', '0', '0'),
('413104', '악마왕의 창', '7', '0', '0', '0', '56', '20', '2230', '0', '0', '0'),
('413105', '악마왕의 활', '7', '0', '0', '0', '56', '20', '2230', '0', '1', '0'),
('44', '고대 다크엘프의 검', '0', '0', '0', '0', '11', '30', '745', '0', '0', '0'),
('413106', '할로윈 지팡이', '8', '20', '15', '0', '167', '4', '7849', '0', '0', '0');
