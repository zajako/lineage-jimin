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
INSERT IGNORE INTO `util_racer` VALUES 
('1', '73', '191'),
('2', '58', '196'),
('3', '55', '201'),
('4', '58', '200'),
('5', '63', '196'),
('6', '97', '160'),
('7', '64', '199'),
('8', '64', '188'),
('9', '65', '184'),
('10', '58', '209'),
('11', '76', '176'),
('12', '58', '195'),
('13', '65', '209'),
('14', '80', '194'),
('15', '72', '197'),
('16', '62', '197'),
('17', '58', '190'),
('18', '66', '212'),
('19', '65', '193'),
('20', '75', '184'),
('21', '70', '181'),
('22', '59', '210'),
('23', '60', '187'),
('24', '64', '206'),
('25', '66', '184'),
('26', '66', '197'),
('27', '59', '216'),
('28', '61', '193'),
('29', '60', '194'),
('30', '61', '202');
