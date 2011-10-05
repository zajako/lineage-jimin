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
-- Table structure for `log_ipcount`
-- ----------------------------
DROP TABLE IF EXISTS `log_ipcount`;
CREATE TABLE `log_ipcount` (
  `ip` varchar(20) NOT NULL DEFAULT '',
  `count` int(5) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of log_ipcount
-- ----------------------------
INSERT IGNORE INTO `log_ipcount` VALUES 
('1', '1'),
('121.139.198.97', '1'),
('121.150.126.97', '8'),
('127.0.0.1', '1'),
('222.239.151.102', '2');
