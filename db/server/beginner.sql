/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:01:11
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `beginner`
-- ----------------------------
DROP TABLE IF EXISTS `beginner`;
CREATE TABLE `beginner` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `item_id` int(6) NOT NULL DEFAULT '0',
  `count` int(10) NOT NULL DEFAULT '0',
  `charge_count` int(10) NOT NULL DEFAULT '0',
  `enchantlvl` int(6) NOT NULL DEFAULT '0',
  `item_name` varchar(50) NOT NULL DEFAULT '',
  `activate` char(1) NOT NULL DEFAULT 'A',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=81 DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of beginner
-- ----------------------------
INSERT INTO `beginner` VALUES ('54', '450000', '1', '1', '0', '상아탑한손검', 'P');
INSERT INTO `beginner` VALUES ('1', '450001', '1', '1', '0', '상아탑양손검', 'P');
INSERT INTO `beginner` VALUES ('2', '450002', '1', '1', '0', '상아탑도끼', 'P');
INSERT INTO `beginner` VALUES ('76', '425000', '1', '0', '0', '엘모어가죽갑옷', 'T');
INSERT INTO `beginner` VALUES ('4', '450000', '1', '1', '0', '한손검', 'K');
INSERT INTO `beginner` VALUES ('14', '450001', '1', '1', '0', '양손검', 'K');
INSERT INTO `beginner` VALUES ('15', '450002', '1', '1', '0', '도끼', 'K');
INSERT INTO `beginner` VALUES ('75', '425000', '1', '0', '0', '엘모어가죽갑옷', 'D');
INSERT INTO `beginner` VALUES ('18', '450000', '1', '1', '0', '석궁', 'E');
INSERT INTO `beginner` VALUES ('19', '450001', '1', '1', '0', '한손검', 'E');
INSERT INTO `beginner` VALUES ('20', '450007', '1', '1', '0', '활', 'E');
INSERT INTO `beginner` VALUES ('22', '450000', '1', '1', '0', '지팡이', 'W');
INSERT INTO `beginner` VALUES ('23', '450003', '1', '1', '0', '단검', 'W');
INSERT INTO `beginner` VALUES ('24', '450000', '1', '1', '0', '석궁', 'D');
INSERT INTO `beginner` VALUES ('25', '450005', '1', '1', '0', '한손검', 'D');
INSERT INTO `beginner` VALUES ('26', '450007', '1', '1', '0', '단검', 'D');
INSERT INTO `beginner` VALUES ('74', '425000', '1', '0', '0', '엘모어가죽갑옷', 'E');
INSERT INTO `beginner` VALUES ('29', '450000', '1', '1', '0', '한손검', 'T');
INSERT INTO `beginner` VALUES ('30', '450002', '1', '1', '0', '양손검', 'T');
INSERT INTO `beginner` VALUES ('31', '450004', '1', '1', '0', '도끼', 'T');
INSERT INTO `beginner` VALUES ('32', '450007', '1', '1', '0', '석궁', 'B');
INSERT INTO `beginner` VALUES ('33', '450003', '1', '1', '0', '도끼', 'B');
INSERT INTO `beginner` VALUES ('34', '450006', '1', '1', '0', '지팡이', 'B');
INSERT INTO `beginner` VALUES ('35', '40743', '1000', '1', '0', '화살', 'E');
INSERT INTO `beginner` VALUES ('36', '40743', '1000', '0', '0', '화살', 'B');
INSERT INTO `beginner` VALUES ('37', '20028', '1', '0', '5', '투구', 'A');
INSERT INTO `beginner` VALUES ('38', '20082', '1', '0', '5', '티셔츠', 'A');
INSERT INTO `beginner` VALUES ('73', '425000', '1', '0', '0', '엘모어가죽갑옷', 'P');
INSERT INTO `beginner` VALUES ('40', '20173', '1', '0', '5', '장갑', 'A');
INSERT INTO `beginner` VALUES ('41', '20206', '1', '0', '5', '샌달', 'A');
INSERT INTO `beginner` VALUES ('42', '20232', '1', '0', '5', '방패', 'A');
INSERT INTO `beginner` VALUES ('43', '20282', '1', '0', '0', '반지', 'A');
INSERT INTO `beginner` VALUES ('44', '420010', '1', '0', '0', '상아탑의귀걸이', 'A');
INSERT INTO `beginner` VALUES ('45', '40308', '100000', '0', '0', '아데나', 'A');
INSERT INTO `beginner` VALUES ('46', '40011', '100', '0', '0', '고급 체력 회복제', 'A');
INSERT INTO `beginner` VALUES ('47', '40030', '10', '0', '0', '속도향상물약', 'A');
INSERT INTO `beginner` VALUES ('50', '40098', '10', '0', '0', '확인주문서', 'A');
INSERT INTO `beginner` VALUES ('48', '40081', '50', '0', '0', '기란 귀환 주문서', 'A');
INSERT INTO `beginner` VALUES ('49', '40096', '10', '0', '0', '변신주문서', 'A');
INSERT INTO `beginner` VALUES ('51', '40099', '20', '0', '0', '순간이동주문서', 'A');
INSERT INTO `beginner` VALUES ('52', '430005', '1', '0', '0', '회상의 촛불', 'A');
INSERT INTO `beginner` VALUES ('53', '437018', '1', '0', '0', '쫄법사 인형', 'A');
INSERT INTO `beginner` VALUES ('77', '425000', '1', '0', '0', '엘모어가죽갑옷', 'B');
INSERT INTO `beginner` VALUES ('78', '425001', '1', '0', '0', '엘모어로브', 'W');
INSERT INTO `beginner` VALUES ('79', '425001', '1', '0', '0', '엘모어로브', 'B');
INSERT INTO `beginner` VALUES ('80', '425002', '1', '0', '0', '엘모어판금갑옷', 'K');
