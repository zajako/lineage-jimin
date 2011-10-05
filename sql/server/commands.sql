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
('에코', '9999', 'L1Echo'),
('셋팅', '9999', 'L1Status'),
('서먼', '9999', 'L1Summon'),
('청소', '9999', 'L1DeleteGroundItem'),
('스킬마스터', '9999', 'L1AddSkill'),
('레벨', '9999', 'L1Level'),
('위치', '9999', 'L1Loc'),
('정보', '9999', 'L1Describe'),
('누구', '9999', 'L1Who'),
('올버프', '9999', 'L1AllBuff'),
('속도', '9999', 'L1Speed'),
('아데나', '9999', 'L1Adena'),
('리셋트랩', '9999', 'L1ResetTrap'),
('리로드트랩', '9999', 'L1ReloadTrap'),
('쇼트랩', '9999', 'L1ShowTrap'),
('이미지', '9999', 'L1GfxId'),
('인벤이미지', '9999', 'L1InvGfxId'),
('피바', '9999', 'L1HpBar'),
('지엠', '9999', 'L1GM'),
('홈타운', '9999', 'L1HomeTown'),
('렙선물', '9999', 'L1LevelPresent'),
('선물', '9999', 'L1Present'),
('종료', '9999', 'L1Shutdown'),
('아이템', '9999', 'L1CreateItem'),
('세트아이템', '9999', 'L1CreateItemSet'),
('버프', '9999', 'L1Buff'),
('스킬', '9999', 'L1Burf'),
('감시', '9999', 'L1Patrol'),
('밴아이피', '9999', 'L1BanIp'),
('채팅', '9999', 'L1Chat'),
('채금', '9999', 'L1ChatNG'),
('강력추방', '9999', 'L1SKick'),
('추방', '9999', 'L1Kick'),
('영구추방', '9999', 'L1PowerKick'),
('계정압류', '9999', 'L1AccountBanKick'),
('변신', '9999', 'L1Poly'),
('소생', '9999', 'L1Ress'),
('킬', '9999', 'L1Kill'),
('귀환', '9999', 'L1GMRoom'),
('출두', '9999', 'L1ToPC'),
('이동', '9999', 'L1Move'),
('날씨', '9999', 'L1ChangeWeather'),
('고스폰', '9999', 'L1ToSpawn'),
('실행', '9999', 'L1Favorite'),
('소환', '9999', 'L1Recall'),
('파티소환', '9999', 'L1PartyRecall'),
('불투명', '9999', 'L1Visible'),
('투명', '9999', 'L1Invisible'),
('스폰', '9999', 'L1SpawnCmd'),
('배치', '9999', 'L1InsertSpawn'),
('설문', '9999', 'L1GMQuestion'),
('액션', '9999', 'L1Action'),
('리로드', '9999', 'L1Reload'),
('타일', '9999', 'L1Tile'),
('뻥', '9999', 'L1UserCalc'),
('검사', '9999', 'L1CheckCharacter'),
('무인추방', '9999', 'L1AutoKick');
