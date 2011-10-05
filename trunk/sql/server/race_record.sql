/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:07:26
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `race_record`
-- ----------------------------
DROP TABLE IF EXISTS `race_record`;
CREATE TABLE `race_record` (
  `number` int(5) unsigned NOT NULL DEFAULT '0',
  `win` int(10) unsigned NOT NULL DEFAULT '0',
  `lose` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of race_record
-- ----------------------------
INSERT INTO `race_record` VALUES ('0', '876', '3473');
INSERT INTO `race_record` VALUES ('1', '909', '3468');
INSERT INTO `race_record` VALUES ('2', '815', '3368');
INSERT INTO `race_record` VALUES ('3', '841', '3474');
INSERT INTO `race_record` VALUES ('4', '860', '3315');
INSERT INTO `race_record` VALUES ('5', '860', '3424');
INSERT INTO `race_record` VALUES ('6', '883', '3449');
INSERT INTO `race_record` VALUES ('7', '814', '3370');
INSERT INTO `race_record` VALUES ('8', '878', '3387');
INSERT INTO `race_record` VALUES ('9', '820', '3362');
INSERT INTO `race_record` VALUES ('10', '879', '3439');
INSERT INTO `race_record` VALUES ('11', '852', '3410');
INSERT INTO `race_record` VALUES ('12', '828', '3452');
INSERT INTO `race_record` VALUES ('13', '898', '3436');
INSERT INTO `race_record` VALUES ('14', '849', '3365');
INSERT INTO `race_record` VALUES ('15', '828', '3404');
INSERT INTO `race_record` VALUES ('16', '876', '3408');
INSERT INTO `race_record` VALUES ('17', '797', '3479');
INSERT INTO `race_record` VALUES ('18', '864', '3381');
INSERT INTO `race_record` VALUES ('19', '858', '3458');
