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
('47', 'ħ�� �ҵ�', '2', '0', '0', '0', '64', '16', '2177', '0', '0', '0'),
('54', 'īƮ�ҵ�', '15', '28', '25', '0', '0', '0', '10', '0', '0', '8'),
('58', '��������Ʈ�ķ��Ӻ극�̵�', '7', '65', '15', '0', '0', '0', '1811', '0', '0', '2'),
('76', '�еε�Ƹ��극�̵�', '15', '25', '25', '0', '0', '0', '1805', '0', '0', '1'),
('121', '���̽� ���� ����', '25', '85', '55', '0', '0', '0', '1810', '0', '0', '4'),
('203', '�ٸ��α��� �� �ڵ� �ҵ�', '15', '80', '90', '2', '0', '0', '762', '0', '0', '2'),
('205', '���� ���', '6', '5', '0', '0', '0', '0', '6288', '0', '1', '0'),
('256', '�ҷ��� ����Ų �� �ҵ�', '8', '20', '15', '0', '0', '0', '2750', '0', '0', '1'),
('257', '�ҷ��� �� �ҵ�', '8', '20', '15', '0', '0', '0', '2750', '0', '0', '1'),
('258', '��Ƽ����Ʈ �ҷ��� �� �ҵ�', '8', '20', '15', '0', '0', '0', '2750', '0', '0', '1'),
('412000', '���Ű�', '8', '30', '20', '0', '0', '0', '10', '0', '0', '8'),
('412003', '��ũ�������� ������', '7', '0', '0', '0', '56', '64', '2230', '0', '0', '0'),
('412004', 'Ȥ���� â', '7', '20', '15', '3', '0', '0', '1804', '0', '0', '4'),
('412005', '��ǳ�� ����', '7', '25', '15', '4', '0', '0', '758', '0', '0', '8'),
('413101', '�Ǹ����� ��հ�', '7', '0', '0', '0', '56', '20', '2230', '0', '0', '0'),
('413102', '�Ǹ����� �̵���', '7', '0', '0', '0', '56', '20', '2230', '0', '0', '0'),
('413103', '�Ǹ����� ������', '7', '0', '0', '0', '56', '20', '2230', '0', '0', '0'),
('413104', '�Ǹ����� â', '7', '0', '0', '0', '56', '20', '2230', '0', '0', '0'),
('413105', '�Ǹ����� Ȱ', '7', '0', '0', '0', '56', '20', '2230', '0', '1', '0'),
('44', '��� ��ũ������ ��', '0', '0', '0', '0', '11', '30', '745', '0', '0', '0'),
('413106', '�ҷ��� ������', '8', '20', '15', '0', '167', '4', '7849', '0', '0', '0');
