/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:01:38
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `board_auction`
-- ----------------------------
DROP TABLE IF EXISTS `board_auction`;
CREATE TABLE `board_auction` (
  `house_id` int(10) unsigned NOT NULL DEFAULT '0',
  `house_name` varchar(45) NOT NULL DEFAULT '',
  `house_area` int(10) unsigned NOT NULL DEFAULT '0',
  `deadline` datetime DEFAULT NULL,
  `price` int(10) unsigned NOT NULL DEFAULT '0',
  `location` varchar(45) NOT NULL DEFAULT '',
  `old_owner` varchar(45) NOT NULL DEFAULT '',
  `old_owner_id` int(10) unsigned NOT NULL DEFAULT '0',
  `bidder` varchar(45) NOT NULL DEFAULT '',
  `bidder_id` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`house_id`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of board_auction
-- ----------------------------
INSERT INTO `board_auction` VALUES ('262146', '기란아지트 2', '45', '2010-03-25 09:00:00', '100000', '기란 2번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262148', '기란아지트 4', '45', '2010-03-25 09:00:00', '100000', '기란 4번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262149', '기란아지트 5', '78', '2010-03-25 09:00:00', '100000', '기란 5번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262151', '기란아지트 7', '45', '2010-03-25 09:00:00', '100000', '기란 7번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262155', '기란아지트 11', '78', '2010-03-25 09:00:00', '100000', '기란 11번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262189', '기란아지트 45', '78', '2010-03-25 12:00:00', '100000', '기란 45번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262159', '기란아지트 15', '45', '2010-03-25 09:00:00', '100000', '기란 15번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262161', '기란아지트 17', '45', '2010-03-25 09:00:00', '100000', '기란 17번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262165', '기란아지트 21', '45', '2010-03-25 09:00:00', '100000', '기란 21번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262168', '기란아지트 24', '45', '2010-03-25 09:00:00', '100000', '기란 24번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262169', '기란아지트 25', '45', '2010-03-25 09:00:00', '100000', '기란 25번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262172', '기란아지트 28', '45', '2010-03-25 09:00:00', '100000', '기란 28번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262174', '기란아지트 30', '45', '2010-03-25 09:00:00', '100000', '기란 30번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262178', '기란아지트 34', '45', '2010-03-25 09:00:00', '100000', '기란 34번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262184', '기란아지트 40', '78', '2010-03-25 09:00:00', '100000', '기란 40번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262185', '기란아지트 41', '78', '2010-03-25 09:00:00', '100000', '기란 41번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262186', '기란아지트 42', '45', '2010-03-25 09:00:00', '100000', '기란 42번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('327686', 'Heine 아지트 6', '0', '2010-03-25 09:00:00', '100000', 'Heine 6번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('327687', 'Heine 아지트 7', '0', '2010-03-25 09:00:00', '100000', 'Heine 7번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('327688', 'Heine 아지트 8', '0', '2010-03-25 09:00:00', '100000', 'Heine 8번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('327689', 'Heine 아지트 9', '0', '2010-03-25 09:00:00', '100000', 'Heine 9번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('327690', 'Heine 아지트 10', '0', '2010-03-25 09:00:00', '100000', 'Heine 10번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('524289', '그르딘아지트 1', '48', '2010-03-25 09:00:00', '100000', '그르딘 1번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('524292', '그르딘아지트 4', '48', '2010-03-25 09:00:00', '100000', '그르딘 4번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('524294', '그르딘아지트 6', '40', '2010-03-25 09:00:00', '100000', '그르딘 6번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262160', '기란아지트 16', '78', '2010-03-25 10:00:00', '100000', '기란 16번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262158', '기란아지트 14', '78', '2010-03-26 16:00:00', '100000', '기란 14번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262163', '기란아지트 19', '78', '2010-03-28 00:00:00', '100000', '기란 19번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('327682', '오션월드', '0', '2010-03-25 09:00:00', '100000', '하이네 2번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('327691', '하이네 아지트 11', '0', '2010-03-25 09:00:00', '100000', '하이네 11번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262153', '윌the하임 아파트', '78', '2010-03-26 18:00:00', '100000', '기란 9번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262181', '기란아지트 37', '78', '2010-03-26 21:00:00', '100003', '기란 37번 아지트', '', '0', '악운의군주', '274467850');
INSERT INTO `board_auction` VALUES ('262154', '형들꺼야~', '120', '2010-03-27 03:00:00', '100000', '기란 10번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('524293', '명품백화점', '82', '2010-03-27 19:00:00', '100000', '글루딘 5번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262182', 'ㅡㅡㅡㅡㅡ', '78', '2010-03-24 12:00:00', '100000', '기란 38번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('327684', '영구부대', '0', '2010-03-24 19:00:00', '100000', '하이네 4번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('327683', '하이네 아지트 3', '0', '2010-03-25 21:00:00', '100000', '하이네 3번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262177', '기란아지트 33', '45', '2010-03-25 21:00:00', '100000', '기란 33번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262173', '기란아지트 29', '78', '2010-03-26 13:00:00', '100000', '기란 29번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('327685', '하이네 아지트 5', '0', '2010-03-25 10:00:00', '100000', '하이네 5번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262164', '레전드 of 소녀시대', '78', '2010-03-25 20:00:00', '100000', '기란 20번 아지트', '', '0', '', '0');
INSERT INTO `board_auction` VALUES ('262183', '쉼터', '45', '2010-03-26 00:00:00', '100000', '기란 39번 아지트', '', '0', '', '0');
