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
-- Table structure for `ub_rank`
-- ----------------------------
DROP TABLE IF EXISTS `ub_rank`;
CREATE TABLE `ub_rank` (
  `ub_id` int(10) NOT NULL DEFAULT '0',
  `char_name` varchar(45) CHARACTER SET euckr NOT NULL,
  `score` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ub_rank
-- ----------------------------
