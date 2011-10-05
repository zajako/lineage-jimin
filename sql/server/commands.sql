/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:03:27
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
INSERT INTO `commands` VALUES ('에코', '9999', 'L1Echo');
INSERT INTO `commands` VALUES ('셋팅', '9999', 'L1Status');
INSERT INTO `commands` VALUES ('서먼', '9999', 'L1Summon');
INSERT INTO `commands` VALUES ('청소', '9999', 'L1DeleteGroundItem');
INSERT INTO `commands` VALUES ('스킬마스터', '9999', 'L1AddSkill');
INSERT INTO `commands` VALUES ('레벨', '9999', 'L1Level');
INSERT INTO `commands` VALUES ('위치', '9999', 'L1Loc');
INSERT INTO `commands` VALUES ('정보', '9999', 'L1Describe');
INSERT INTO `commands` VALUES ('누구', '9999', 'L1Who');
INSERT INTO `commands` VALUES ('올버프', '9999', 'L1AllBuff');
INSERT INTO `commands` VALUES ('속도', '9999', 'L1Speed');
INSERT INTO `commands` VALUES ('아데나', '9999', 'L1Adena');
INSERT INTO `commands` VALUES ('리셋트랩', '9999', 'L1ResetTrap');
INSERT INTO `commands` VALUES ('리로드트랩', '9999', 'L1ReloadTrap');
INSERT INTO `commands` VALUES ('쇼트랩', '9999', 'L1ShowTrap');
INSERT INTO `commands` VALUES ('이미지', '9999', 'L1GfxId');
INSERT INTO `commands` VALUES ('인벤이미지', '9999', 'L1InvGfxId');
INSERT INTO `commands` VALUES ('피바', '9999', 'L1HpBar');
INSERT INTO `commands` VALUES ('지엠', '9999', 'L1GM');
INSERT INTO `commands` VALUES ('홈타운', '9999', 'L1HomeTown');
INSERT INTO `commands` VALUES ('렙선물', '9999', 'L1LevelPresent');
INSERT INTO `commands` VALUES ('선물', '9999', 'L1Present');
INSERT INTO `commands` VALUES ('종료', '9999', 'L1Shutdown');
INSERT INTO `commands` VALUES ('아이템', '9999', 'L1CreateItem');
INSERT INTO `commands` VALUES ('세트아이템', '9999', 'L1CreateItemSet');
INSERT INTO `commands` VALUES ('버프', '9999', 'L1Buff');
INSERT INTO `commands` VALUES ('스킬', '9999', 'L1Burf');
INSERT INTO `commands` VALUES ('감시', '9999', 'L1Patrol');
INSERT INTO `commands` VALUES ('밴아이피', '9999', 'L1BanIp');
INSERT INTO `commands` VALUES ('채팅', '9999', 'L1Chat');
INSERT INTO `commands` VALUES ('채금', '9999', 'L1ChatNG');
INSERT INTO `commands` VALUES ('강력추방', '9999', 'L1SKick');
INSERT INTO `commands` VALUES ('추방', '9999', 'L1Kick');
INSERT INTO `commands` VALUES ('영구추방', '9999', 'L1PowerKick');
INSERT INTO `commands` VALUES ('계정압류', '9999', 'L1AccountBanKick');
INSERT INTO `commands` VALUES ('변신', '9999', 'L1Poly');
INSERT INTO `commands` VALUES ('소생', '9999', 'L1Ress');
INSERT INTO `commands` VALUES ('킬', '9999', 'L1Kill');
INSERT INTO `commands` VALUES ('귀환', '9999', 'L1GMRoom');
INSERT INTO `commands` VALUES ('출두', '9999', 'L1ToPC');
INSERT INTO `commands` VALUES ('이동', '9999', 'L1Move');
INSERT INTO `commands` VALUES ('날씨', '9999', 'L1ChangeWeather');
INSERT INTO `commands` VALUES ('고스폰', '9999', 'L1ToSpawn');
INSERT INTO `commands` VALUES ('실행', '9999', 'L1Favorite');
INSERT INTO `commands` VALUES ('소환', '9999', 'L1Recall');
INSERT INTO `commands` VALUES ('파티소환', '9999', 'L1PartyRecall');
INSERT INTO `commands` VALUES ('불투명', '9999', 'L1Visible');
INSERT INTO `commands` VALUES ('투명', '9999', 'L1Invisible');
INSERT INTO `commands` VALUES ('스폰', '9999', 'L1SpawnCmd');
INSERT INTO `commands` VALUES ('배치', '9999', 'L1InsertSpawn');
INSERT INTO `commands` VALUES ('설문', '9999', 'L1GMQuestion');
INSERT INTO `commands` VALUES ('액션', '9999', 'L1Action');
INSERT INTO `commands` VALUES ('리로드', '9999', 'L1Reload');
INSERT INTO `commands` VALUES ('타일', '9999', 'L1Tile');
INSERT INTO `commands` VALUES ('뻥', '9999', 'L1UserCalc');
INSERT INTO `commands` VALUES ('검사', '9999', 'L1CheckCharacter');
INSERT INTO `commands` VALUES ('무인추방', '9999', 'L1AutoKick');
