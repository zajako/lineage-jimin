/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:10:57
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `util_racer`
-- ----------------------------
DROP TABLE IF EXISTS `util_racer`;
CREATE TABLE `util_racer` (
  `레이서번호` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `승리횟수` int(10) NOT NULL DEFAULT '0',
  `패횟수` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`레이서번호`)
) ENGINE=MyISAM AUTO_INCREMENT=59 DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of util_racer
-- ----------------------------
INSERT INTO `util_racer` VALUES ('1', '73', '191');
INSERT INTO `util_racer` VALUES ('2', '58', '196');
INSERT INTO `util_racer` VALUES ('3', '55', '201');
INSERT INTO `util_racer` VALUES ('4', '58', '200');
INSERT INTO `util_racer` VALUES ('5', '63', '196');
INSERT INTO `util_racer` VALUES ('6', '97', '160');
INSERT INTO `util_racer` VALUES ('7', '64', '199');
INSERT INTO `util_racer` VALUES ('8', '64', '188');
INSERT INTO `util_racer` VALUES ('9', '65', '184');
INSERT INTO `util_racer` VALUES ('10', '58', '209');
INSERT INTO `util_racer` VALUES ('11', '76', '176');
INSERT INTO `util_racer` VALUES ('12', '58', '195');
INSERT INTO `util_racer` VALUES ('13', '65', '209');
INSERT INTO `util_racer` VALUES ('14', '80', '194');
INSERT INTO `util_racer` VALUES ('15', '72', '197');
INSERT INTO `util_racer` VALUES ('16', '62', '197');
INSERT INTO `util_racer` VALUES ('17', '58', '190');
INSERT INTO `util_racer` VALUES ('18', '66', '212');
INSERT INTO `util_racer` VALUES ('19', '65', '193');
INSERT INTO `util_racer` VALUES ('20', '75', '184');
INSERT INTO `util_racer` VALUES ('21', '70', '181');
INSERT INTO `util_racer` VALUES ('22', '59', '210');
INSERT INTO `util_racer` VALUES ('23', '60', '187');
INSERT INTO `util_racer` VALUES ('24', '64', '206');
INSERT INTO `util_racer` VALUES ('25', '66', '184');
INSERT INTO `util_racer` VALUES ('26', '66', '197');
INSERT INTO `util_racer` VALUES ('27', '59', '216');
INSERT INTO `util_racer` VALUES ('28', '61', '193');
INSERT INTO `util_racer` VALUES ('29', '60', '194');
INSERT INTO `util_racer` VALUES ('30', '61', '202');
