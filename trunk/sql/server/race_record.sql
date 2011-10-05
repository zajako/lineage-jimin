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
INSERT IGNORE INTO `race_record` VALUES 
('0', '876', '3473'),
('1', '909', '3468'),
('2', '815', '3368'),
('3', '841', '3474'),
('4', '860', '3315'),
('5', '860', '3424'),
('6', '883', '3449'),
('7', '814', '3370'),
('8', '878', '3387'),
('9', '820', '3362'),
('10', '879', '3439'),
('11', '852', '3410'),
('12', '828', '3452'),
('13', '898', '3436'),
('14', '849', '3365'),
('15', '828', '3404'),
('16', '876', '3408'),
('17', '797', '3479'),
('18', '864', '3381'),
('19', '858', '3458');
