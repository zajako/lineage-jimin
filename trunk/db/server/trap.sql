/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:10:33
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `trap`
-- ----------------------------
DROP TABLE IF EXISTS `trap`;
CREATE TABLE `trap` (
  `id` int(8) NOT NULL,
  `note` varchar(64) DEFAULT NULL,
  `type` varchar(64) NOT NULL,
  `gfxId` int(4) NOT NULL,
  `isDetectionable` tinyint(1) NOT NULL,
  `base` int(4) NOT NULL,
  `dice` int(4) NOT NULL,
  `diceCount` int(4) NOT NULL,
  `poisonType` char(1) NOT NULL DEFAULT 'n',
  `poisonDelay` int(4) NOT NULL DEFAULT '0',
  `poisonTime` int(4) NOT NULL DEFAULT '0',
  `poisonDamage` int(4) NOT NULL DEFAULT '0',
  `monsterNpcId` int(4) NOT NULL DEFAULT '0',
  `monsterCount` int(4) NOT NULL DEFAULT '0',
  `teleportX` int(4) NOT NULL DEFAULT '0',
  `teleportY` int(4) NOT NULL DEFAULT '0',
  `teleportMapId` int(4) NOT NULL DEFAULT '0',
  `skillId` int(4) NOT NULL DEFAULT '0',
  `skillTimeSeconds` int(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of trap
-- ----------------------------
INSERT INTO `trap` VALUES ('1', '트라바사미', 'L1DamageTrap', '1065', '1', '10', '10', '1', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('2', '힐', 'L1HealingTrap', '1074', '0', '10', '10', '1', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('3', '데미지독', 'L1PoisonTrap', '1066', '1', '0', '0', '0', 'd', '0', '5000', '10', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('4', '침묵독', 'L1PoisonTrap', '1066', '1', '0', '0', '0', 's', '0', '0', '10', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('5', '마비독', 'L1PoisonTrap', '1066', '1', '0', '0', '0', 'p', '5000', '5000', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('6', '날씨', 'L1DamageTrap', '1085', '1', '10', '10', '1', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('7', '바늘(옆)', 'L1DamageTrap', '1070', '1', '10', '10', '1', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('8', 'TOI4F monster', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45348', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('9', 'TOI8F monster', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45407', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('10', 'TOI14F monster', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45394', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('11', 'TOI18F monster', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45406', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('12', 'TOI24F monster', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45384', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('13', 'TOI28F monster', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45471', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('14', 'TOI34F monster', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45403', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('15', 'TOI38F monster', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45455', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('16', 'TOI44F monster', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45514', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('17', 'TOI48F monster', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45522', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('18', 'TOI54F monster', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45524', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('19', 'TOI64F monster', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45528', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('20', 'TOI74F monster', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45503', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('21', 'TOI78F monster', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45532', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('22', 'TOI84F monster', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45586', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('23', 'TOI94F monster', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45621', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('24', 'IT4F 아이안고렘', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45372', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('25', 'IT4F 미믹크', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45141', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('26', 'IT4F 리빙 아모', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45322', '2', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('27', 'IT5F 아이안고렘', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45372', '3', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('28', 'IT5F 미믹크', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45141', '3', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('29', 'IT5F 리빙 아모', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45322', '3', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('30', 'IT6F 실루엣', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45162', '3', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('31', 'IT6F 데스', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45221', '3', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('32', 'IT7F 실루엣', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45162', '4', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('33', 'IT7F 데스', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45221', '4', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('34', 'IT8F 아이안고렘', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45372', '5', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('35', 'IT8F 실루엣', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45162', '5', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('36', 'IT8F 데스', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45221', '5', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('37', 'IT8F 리빙 아모', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '45322', '5', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('38', '흐랑코 미로 러버 뼈 솔저', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '46057', '4', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('39', '흐랑코 미로 라바본아챠', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '46058', '4', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('40', '흐랑코 미로 러버 얼간이', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '46059', '4', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('41', '흐랑코 미로 러버 뼈 나이프', 'L1MonsterTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '46056', '4', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('42', '흐랑코 미로 스타트 지점', 'L1TeleportTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '4', '32679', '32742', '482', '0', '0');
INSERT INTO `trap` VALUES ('43', '디에고가 닫힌 뇌스타트 지점', 'L1TeleportTrap', '0', '0', '0', '0', '0', 'n', '0', '0', '0', '0', '0', '32736', '32800', '483', '0', '0');
INSERT INTO `trap` VALUES ('44', '도깨비 저택 파라라이즈', 'L1SkillTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '33', '0');
INSERT INTO `trap` VALUES ('45', '도깨비 저택 블라인드', 'L1SkillTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '20', '5');
INSERT INTO `trap` VALUES ('46', '도깨비 저택 슬로우', 'L1SkillTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '29', '5');
INSERT INTO `trap` VALUES ('47', '도깨비 저택 헤이 파업', 'L1SkillTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '43', '5');
INSERT INTO `trap` VALUES ('48', '도깨비 저택 텔레포트', 'L1TeleportTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '32730', '32829', '5140', '0', '0');
INSERT INTO `trap` VALUES ('49', '도깨비 저택 텔레포트', 'L1TeleportTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '32749', '32813', '5140', '0', '0');
INSERT INTO `trap` VALUES ('50', '도깨비 저택 텔레포트', 'L1TeleportTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '32747', '32867', '5140', '0', '0');
INSERT INTO `trap` VALUES ('51', '도깨비 저택 텔레포트', 'L1TeleportTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '32785', '32819', '5140', '0', '0');
INSERT INTO `trap` VALUES ('52', '도깨비 저택 텔레포트', 'L1TeleportTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '32797', '32869', '5140', '0', '0');
INSERT INTO `trap` VALUES ('70', '유령의집 문작동해골', 'L1GhostHouseTrap', '6334', '0', '0', '0', '0', 'z', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('71', '유령의집 종료라인', 'L1GhostHouseTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('80', '스타트라인', 'L1PetRacingTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('81', '바퀴체크1', 'L1PetRacingTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('82', '바퀴체크2', 'L1PetRacingTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('83', '바퀴체크3', 'L1PetRacingTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('85', '변신트랩', 'L1PetRacingTrap', '6675', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('86', '변신트랩(노이펙)', 'L1PetRacingTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('87', '속도트랩', 'L1PetRacingTrap', '6674', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('90', '체크포인트1-1', 'L1PetRacingTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('91', '체크포인트1-2', 'L1PetRacingTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('92', '체크포인트2-1', 'L1PetRacingTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('93', '체크포인트2-2', 'L1PetRacingTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('94', '체크포인트3-1', 'L1PetRacingTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('95', '체크포인트3-2', 'L1PetRacingTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('96', '체크포인트4-1', 'L1PetRacingTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('97', '체크포인트4-2', 'L1PetRacingTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
INSERT INTO `trap` VALUES ('88', '속도트랩(노이펙)', 'L1PetRacingTrap', '0', '0', '0', '0', '0', '-', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
