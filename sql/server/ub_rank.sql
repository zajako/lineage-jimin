/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:10:44
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
