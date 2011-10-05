/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:01:16
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `beginner_teleport`
-- ----------------------------
DROP TABLE IF EXISTS `beginner_teleport`;
CREATE TABLE `beginner_teleport` (
  `name` varchar(45) NOT NULL DEFAULT '',
  `locx` int(10) unsigned NOT NULL DEFAULT '0',
  `locy` int(10) unsigned NOT NULL DEFAULT '0',
  `mapid` int(10) unsigned NOT NULL DEFAULT '0',
  `randomX` int(10) unsigned NOT NULL DEFAULT '0',
  `randomY` int(10) unsigned NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of beginner_teleport
-- ----------------------------
INSERT INTO `beginner_teleport` VALUES ('\\fR[ 엔피씨 ] 몽환의섬', '33977', '32923', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fR[ 엔피씨 ] 아가타(성당)', '33959', '33364', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fR[ 엔피씨 ] 아슈르(사막)', '32865', '33250', '4', '7', '7');
INSERT INTO `beginner_teleport` VALUES ('\\fR[ 엔피씨 ] 잊섬선착장', '33444', '33474', '4', '10', '10');
INSERT INTO `beginner_teleport` VALUES ('\\fR[ 엔피씨 ] 파르츠(쥬스)', '34019', '33112', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fR[ 엔피씨 ] 판도라', '32644', '32952', '0', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fR[ 엔피씨 ] 해적섬선착장', '32720', '32443', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fT[ 마 을 ] 글루딘', '32629', '32805', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fT[ 마 을 ] 기란', '33430', '32815', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fT[ 마 을 ] 말하는섬', '32574', '32945', '0', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fT[ 마 을 ] 아덴', '34085', '33144', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fT[ 마 을 ] 오렌', '34053', '32287', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fT[ 마 을 ] 우드벡', '32609', '33183', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fT[ 마 을 ] 웰던', '33723', '32489', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fT[ 마 을 ] 은기사마을', '33078', '33394', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fT[ 마 을 ] 침묵의동굴', '32876', '32910', '304', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fT[ 마 을 ] 켄트성마을', '33073', '32798', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fT[ 마 을 ] 하이네', '33600', '33234', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fT[ 마 을 ] 화전민', '32746', '32443', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fW[ 필 드 ] 라우풀신전', '33117', '32937', '4', '6', '6');
INSERT INTO `beginner_teleport` VALUES ('\\fW[ 필 드 ] 악어섬', '33503', '33203', '4', '2', '2');
INSERT INTO `beginner_teleport` VALUES ('\\fW[ 필 드 ] 용계삼거리', '33335', '32449', '4', '12', '14');
INSERT INTO `beginner_teleport` VALUES ('\\fW[ 필 드 ] 용뼈', '33391', '32340', '4', '7', '10');
INSERT INTO `beginner_teleport` VALUES ('\\fW[ 필 드 ] 카오틱신전', '32887', '32652', '4', '5', '5');
INSERT INTO `beginner_teleport` VALUES ('\\fW[ 필 드 ] 화룡의둥지', '33568', '32408', '4', '8', '8');
INSERT INTO `beginner_teleport` VALUES ('\\fW[ 필 드 ] 황혼의산맥', '34270', '33091', '4', '4', '4');
INSERT INTO `beginner_teleport` VALUES ('\\fY[ 던 전 ] 그림자신전', '34266', '32189', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fY[ 던 전 ] 글루딘던전', '32728', '32928', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fY[ 던 전 ] 말하는섬던전', '32476', '32853', '0', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fY[ 던 전 ] 상아탑입구', '34041', '32155', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fY[ 던 전 ] 수련던전1층', '32969', '33512', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fY[ 던 전 ] 수련던전2층', '32926', '33517', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fY[ 던 전 ] 수련던전3층', '32882', '33511', '4', '6', '3');
INSERT INTO `beginner_teleport` VALUES ('\\fY[ 던 전 ] 요정숲던전', '32938', '32283', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fY[ 던 전 ] 욕망의동굴', '32758', '33458', '4', '2', '2');
INSERT INTO `beginner_teleport` VALUES ('\\fY[ 던 전 ] 용던입구1', '33349', '32352', '4', '5', '5');
INSERT INTO `beginner_teleport` VALUES ('\\fY[ 던 전 ] 용던입구2', '33373', '32385', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fY[ 던 전 ] 용던입구3', '33396', '32326', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fY[ 던 전 ] 용던입구4', '33414', '32412', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fY[ 던 전 ] 지하수로', '34149', '33381', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fY[ 던 전 ] 침공로입구', '32563', '33459', '4', '0', '0');
INSERT INTO `beginner_teleport` VALUES ('\\fY[ 던 전 ] 하이네던전', '33627', '33504', '4', '0', '0');
