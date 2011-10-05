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
-- Table structure for `petitem`
-- ----------------------------
DROP TABLE IF EXISTS `petitem`;
CREATE TABLE `petitem` (
  `item_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `note` varchar(45) NOT NULL DEFAULT '',
  `hitmodifier` int(3) NOT NULL DEFAULT '0',
  `dmgmodifier` int(3) NOT NULL DEFAULT '0',
  `ac` int(3) NOT NULL DEFAULT '0',
  `add_str` int(2) NOT NULL DEFAULT '0',
  `add_con` int(2) NOT NULL DEFAULT '0',
  `add_dex` int(2) NOT NULL DEFAULT '0',
  `add_int` int(2) NOT NULL DEFAULT '0',
  `add_wis` int(2) NOT NULL DEFAULT '0',
  `add_hp` int(10) NOT NULL DEFAULT '0',
  `add_mp` int(10) NOT NULL DEFAULT '0',
  `add_sp` int(10) NOT NULL DEFAULT '0',
  `m_def` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`item_id`)
) ENGINE=MyISAM AUTO_INCREMENT=427110 DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of petitem
-- ----------------------------
INSERT IGNORE INTO `petitem` VALUES 
('427104', '��ɰ��� �̻�', '5', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('427101', '�ĸ��� �̻�', '-3', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('427102', '������ �̻�', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('427103', 'Ȳ���� �̻�', '1', '1', '0', '0', '0', '0', '0', '0', '0', '0', '1', '0'),
('427107', '�Ÿ��� �̻�', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '2', '0'),
('427108', '��ö�� �̻�', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('427109', '�¸��� �̻�', '2', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('427002', '���� ��Ƹ�', '0', '0', '-4', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('427003', '���� ��Ƹ�', '0', '0', '-7', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('427004', '��ö ��Ƹ�', '0', '0', '-8', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('427005', '�̽��� ��Ƹ�', '0', '0', '-12', '0', '0', '0', '1', '1', '0', '0', '0', '10'),
('427006', 'ũ�ν� ��Ƹ�', '0', '0', '-13', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('427007', 'ü�� ��Ƹ�', '0', '0', '-20', '0', '0', '0', '0', '0', '0', '0', '0', '0');
