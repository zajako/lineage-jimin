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
-- Table structure for `castle`
-- ----------------------------
DROP TABLE IF EXISTS `castle`;
CREATE TABLE `castle` (
  `castle_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(45) NOT NULL DEFAULT '',
  `war_time` datetime DEFAULT NULL,
  `tax_rate` int(11) NOT NULL DEFAULT '0',
  `public_money` int(11) NOT NULL DEFAULT '0',
  `public_ready_money` int(11) NOT NULL DEFAULT '0',
  `show_money` int(11) NOT NULL DEFAULT '0',
  `war_basetime` int(11) NOT NULL DEFAULT '0',
  `security` int(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`castle_id`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of castle
-- ----------------------------
INSERT IGNORE INTO `castle` VALUES 
('1', '��Ʈ��', '2008-12-21 13:50:00', '50', '0', '0', '0', '0', '0'),
('2', '��ũ�� ��', '2008-10-18 17:00:00', '50', '0', '0', '0', '0', '0'),
('3', '���ٿ�强', '2008-10-18 17:00:00', '50', '0', '0', '0', '0', '0'),
('4', '�����', '2010-03-23 20:00:00', '50', '0', '0', '0', '0', '0'),
('5', '���̳׼�', '2008-10-18 17:00:00', '50', '0', '0', '0', '0', '0'),
('6', '�������', '2010-03-25 20:00:00', '50', '0', '0', '0', '0', '0'),
('7', '�Ƶ���', '2008-10-18 17:00:00', '50', '0', '0', '0', '0', '0'),
('8', '��Ƶ� ���', '2008-10-18 17:00:00', '50', '0', '0', '0', '0', '0');
