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
-- Table structure for `commands`
-- ----------------------------
DROP TABLE IF EXISTS `commands`;
CREATE TABLE `commands` (
  `name` varchar(255) NOT NULL,
  `access_level` int(10) NOT NULL DEFAULT '9999',
  `class_name` varchar(255) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of commands
-- ----------------------------
INSERT IGNORE INTO `commands` VALUES 
('����', '9999', 'L1Echo'),
('����', '9999', 'L1Status'),
('����', '9999', 'L1Summon'),
('û��', '9999', 'L1DeleteGroundItem'),
('��ų������', '9999', 'L1AddSkill'),
('����', '9999', 'L1Level'),
('��ġ', '9999', 'L1Loc'),
('����', '9999', 'L1Describe'),
('����', '9999', 'L1Who'),
('�ù���', '9999', 'L1AllBuff'),
('�ӵ�', '9999', 'L1Speed'),
('�Ƶ���', '9999', 'L1Adena'),
('����Ʈ��', '9999', 'L1ResetTrap'),
('���ε�Ʈ��', '9999', 'L1ReloadTrap'),
('��Ʈ��', '9999', 'L1ShowTrap'),
('�̹���', '9999', 'L1GfxId'),
('�κ��̹���', '9999', 'L1InvGfxId'),
('�ǹ�', '9999', 'L1HpBar'),
('����', '9999', 'L1GM'),
('ȨŸ��', '9999', 'L1HomeTown'),
('������', '9999', 'L1LevelPresent'),
('����', '9999', 'L1Present'),
('����', '9999', 'L1Shutdown'),
('������', '9999', 'L1CreateItem'),
('��Ʈ������', '9999', 'L1CreateItemSet'),
('����', '9999', 'L1Buff'),
('��ų', '9999', 'L1Burf'),
('����', '9999', 'L1Patrol'),
('�������', '9999', 'L1BanIp'),
('ä��', '9999', 'L1Chat'),
('ä��', '9999', 'L1ChatNG'),
('�����߹�', '9999', 'L1SKick'),
('�߹�', '9999', 'L1Kick'),
('�����߹�', '9999', 'L1PowerKick'),
('�����з�', '9999', 'L1AccountBanKick'),
('����', '9999', 'L1Poly'),
('�һ�', '9999', 'L1Ress'),
('ų', '9999', 'L1Kill'),
('��ȯ', '9999', 'L1GMRoom'),
('���', '9999', 'L1ToPC'),
('�̵�', '9999', 'L1Move'),
('����', '9999', 'L1ChangeWeather'),
('����', '9999', 'L1ToSpawn'),
('����', '9999', 'L1Favorite'),
('��ȯ', '9999', 'L1Recall'),
('��Ƽ��ȯ', '9999', 'L1PartyRecall'),
('������', '9999', 'L1Visible'),
('����', '9999', 'L1Invisible'),
('����', '9999', 'L1SpawnCmd'),
('��ġ', '9999', 'L1InsertSpawn'),
('����', '9999', 'L1GMQuestion'),
('�׼�', '9999', 'L1Action'),
('���ε�', '9999', 'L1Reload'),
('Ÿ��', '9999', 'L1Tile'),
('��', '9999', 'L1UserCalc'),
('�˻�', '9999', 'L1CheckCharacter'),
('�����߹�', '9999', 'L1AutoKick');
