/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:08:59
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `spawnlist_furniture`
-- ----------------------------
DROP TABLE IF EXISTS `spawnlist_furniture`;
CREATE TABLE `spawnlist_furniture` (
  `item_obj_id` int(10) unsigned NOT NULL DEFAULT '0',
  `npcid` int(10) unsigned NOT NULL DEFAULT '0',
  `locx` int(10) NOT NULL DEFAULT '0',
  `locy` int(10) NOT NULL DEFAULT '0',
  `mapid` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`item_obj_id`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of spawnlist_furniture
-- ----------------------------
INSERT INTO `spawnlist_furniture` VALUES ('286723086', '80111', '32781', '32837', '5098');
INSERT INTO `spawnlist_furniture` VALUES ('286723496', '80125', '32777', '32834', '5098');
INSERT INTO `spawnlist_furniture` VALUES ('286734942', '80126', '33397', '32788', '4');
INSERT INTO `spawnlist_furniture` VALUES ('286734943', '80126', '33404', '32788', '4');
INSERT INTO `spawnlist_furniture` VALUES ('286734922', '80116', '32766', '32839', '5098');
INSERT INTO `spawnlist_furniture` VALUES ('286734920', '80116', '32768', '32839', '5098');
INSERT INTO `spawnlist_furniture` VALUES ('286734917', '80121', '32765', '32837', '5098');
INSERT INTO `spawnlist_furniture` VALUES ('286734923', '80116', '32765', '32839', '5098');
INSERT INTO `spawnlist_furniture` VALUES ('286734921', '80116', '32767', '32839', '5098');
INSERT INTO `spawnlist_furniture` VALUES ('286734924', '80116', '32764', '32839', '5098');
INSERT INTO `spawnlist_furniture` VALUES ('286734912', '80121', '32764', '32837', '5098');
INSERT INTO `spawnlist_furniture` VALUES ('286734919', '80116', '32769', '32839', '5098');
INSERT INTO `spawnlist_furniture` VALUES ('286734916', '80121', '32766', '32837', '5098');
INSERT INTO `spawnlist_furniture` VALUES ('286734915', '80121', '32767', '32837', '5098');
INSERT INTO `spawnlist_furniture` VALUES ('286723497', '80125', '32777', '32842', '5098');
INSERT INTO `spawnlist_furniture` VALUES ('286734913', '80121', '32769', '32837', '5098');
INSERT INTO `spawnlist_furniture` VALUES ('286734914', '80121', '32768', '32837', '5098');
INSERT INTO `spawnlist_furniture` VALUES ('286734959', '80125', '32759', '32829', '5098');
INSERT INTO `spawnlist_furniture` VALUES ('286734965', '80120', '32785', '32844', '5098');
INSERT INTO `spawnlist_furniture` VALUES ('304805784', '80112', '32773', '32842', '5089');
INSERT INTO `spawnlist_furniture` VALUES ('304805786', '80122', '32773', '32830', '5089');
INSERT INTO `spawnlist_furniture` VALUES ('304805783', '80112', '32773', '32835', '5089');
INSERT INTO `spawnlist_furniture` VALUES ('304805788', '80123', '32774', '32829', '5089');
INSERT INTO `spawnlist_furniture` VALUES ('304805789', '80123', '32774', '32830', '5089');
INSERT INTO `spawnlist_furniture` VALUES ('304805782', '80111', '32778', '32837', '5089');
INSERT INTO `spawnlist_furniture` VALUES ('304805791', '80118', '32782', '32842', '5089');
INSERT INTO `spawnlist_furniture` VALUES ('304805790', '80118', '32781', '32831', '5089');
INSERT INTO `spawnlist_furniture` VALUES ('304805785', '80121', '32782', '32837', '5089');
INSERT INTO `spawnlist_furniture` VALUES ('311661332', '80117', '32762', '32837', '5106');
INSERT INTO `spawnlist_furniture` VALUES ('314045425', '80116', '33460', '32839', '4');
