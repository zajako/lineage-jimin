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
-- Table structure for `notice`
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` int(30) NOT NULL,
  `message` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT IGNORE INTO `notice` VALUES 
('1', '\\f=Bone 서버 방문을 진심으로 환영합니다.^즐거운 게임 즐겨주세요^ ^'),
('2', '\\f=Bone 서버 방문을 진심으로 환영합니다.^즐거운 게임 즐겨주세요^ ^');
