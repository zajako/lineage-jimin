/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:09:08
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `spawnlist_light`
-- ----------------------------
DROP TABLE IF EXISTS `spawnlist_light`;
CREATE TABLE `spawnlist_light` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `npcid` int(10) unsigned NOT NULL DEFAULT '0',
  `locx` int(10) unsigned NOT NULL DEFAULT '0',
  `locy` int(10) unsigned NOT NULL DEFAULT '0',
  `mapid` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=265 DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of spawnlist_light
-- ----------------------------
INSERT INTO `spawnlist_light` VALUES ('1', '81177', '33084', '33396', '4');
INSERT INTO `spawnlist_light` VALUES ('2', '81177', '33066', '33395', '4');
INSERT INTO `spawnlist_light` VALUES ('3', '81177', '33049', '33399', '4');
INSERT INTO `spawnlist_light` VALUES ('4', '81177', '33050', '33392', '4');
INSERT INTO `spawnlist_light` VALUES ('5', '81177', '33062', '33418', '4');
INSERT INTO `spawnlist_light` VALUES ('6', '81177', '33068', '33418', '4');
INSERT INTO `spawnlist_light` VALUES ('7', '81177', '33097', '33414', '4');
INSERT INTO `spawnlist_light` VALUES ('8', '81177', '33084', '33367', '4');
INSERT INTO `spawnlist_light` VALUES ('9', '81177', '33084', '33362', '4');
INSERT INTO `spawnlist_light` VALUES ('10', '81177', '33102', '33360', '4');
INSERT INTO `spawnlist_light` VALUES ('11', '81177', '33114', '33360', '4');
INSERT INTO `spawnlist_light` VALUES ('12', '81177', '33125', '33358', '4');
INSERT INTO `spawnlist_light` VALUES ('13', '81177', '33127', '33368', '4');
INSERT INTO `spawnlist_light` VALUES ('14', '81177', '33138', '33370', '4');
INSERT INTO `spawnlist_light` VALUES ('15', '81177', '33122', '33380', '4');
INSERT INTO `spawnlist_light` VALUES ('16', '81177', '33115', '33396', '4');
INSERT INTO `spawnlist_light` VALUES ('17', '81177', '33121', '33330', '4');
INSERT INTO `spawnlist_light` VALUES ('18', '81177', '33121', '33325', '4');
INSERT INTO `spawnlist_light` VALUES ('19', '81177', '33132', '33318', '4');
INSERT INTO `spawnlist_light` VALUES ('20', '81177', '33132', '33326', '4');
INSERT INTO `spawnlist_light` VALUES ('21', '81177', '33145', '33318', '4');
INSERT INTO `spawnlist_light` VALUES ('22', '81177', '33145', '33326', '4');
INSERT INTO `spawnlist_light` VALUES ('23', '81177', '33106', '33332', '4');
INSERT INTO `spawnlist_light` VALUES ('24', '81177', '33100', '33332', '4');
INSERT INTO `spawnlist_light` VALUES ('25', '81177', '33104', '33315', '4');
INSERT INTO `spawnlist_light` VALUES ('26', '81177', '33112', '33315', '4');
INSERT INTO `spawnlist_light` VALUES ('27', '81177', '33610', '33263', '4');
INSERT INTO `spawnlist_light` VALUES ('28', '81177', '33649', '33259', '4');
INSERT INTO `spawnlist_light` VALUES ('29', '81177', '33649', '33253', '4');
INSERT INTO `spawnlist_light` VALUES ('30', '81177', '33654', '33225', '4');
INSERT INTO `spawnlist_light` VALUES ('31', '81177', '33654', '33230', '4');
INSERT INTO `spawnlist_light` VALUES ('32', '81177', '33624', '33216', '4');
INSERT INTO `spawnlist_light` VALUES ('33', '81177', '33621', '33235', '4');
INSERT INTO `spawnlist_light` VALUES ('34', '81177', '33597', '33218', '4');
INSERT INTO `spawnlist_light` VALUES ('35', '81177', '33578', '33219', '4');
INSERT INTO `spawnlist_light` VALUES ('36', '81177', '33558', '33260', '4');
INSERT INTO `spawnlist_light` VALUES ('37', '81177', '33554', '33283', '4');
INSERT INTO `spawnlist_light` VALUES ('38', '81177', '33535', '33277', '4');
INSERT INTO `spawnlist_light` VALUES ('39', '81177', '33535', '33282', '4');
INSERT INTO `spawnlist_light` VALUES ('40', '81177', '33587', '33270', '4');
INSERT INTO `spawnlist_light` VALUES ('41', '81177', '33573', '33304', '4');
INSERT INTO `spawnlist_light` VALUES ('42', '81177', '33608', '33284', '4');
INSERT INTO `spawnlist_light` VALUES ('43', '81177', '33633', '33300', '4');
INSERT INTO `spawnlist_light` VALUES ('44', '81177', '33614', '33324', '4');
INSERT INTO `spawnlist_light` VALUES ('45', '81177', '33614', '33329', '4');
INSERT INTO `spawnlist_light` VALUES ('46', '81177', '33613', '33390', '4');
INSERT INTO `spawnlist_light` VALUES ('47', '81177', '33625', '33409', '4');
INSERT INTO `spawnlist_light` VALUES ('48', '81177', '33604', '33444', '4');
INSERT INTO `spawnlist_light` VALUES ('49', '81177', '33608', '33447', '4');
INSERT INTO `spawnlist_light` VALUES ('50', '81177', '33646', '33426', '4');
INSERT INTO `spawnlist_light` VALUES ('51', '81177', '33652', '33425', '4');
INSERT INTO `spawnlist_light` VALUES ('52', '81178', '33596', '33245', '4');
INSERT INTO `spawnlist_light` VALUES ('53', '81178', '33607', '33245', '4');
INSERT INTO `spawnlist_light` VALUES ('54', '81177', '32584', '32935', '0');
INSERT INTO `spawnlist_light` VALUES ('55', '81177', '32604', '32940', '0');
INSERT INTO `spawnlist_light` VALUES ('56', '81177', '32595', '32910', '0');
INSERT INTO `spawnlist_light` VALUES ('57', '81177', '32588', '32909', '0');
INSERT INTO `spawnlist_light` VALUES ('58', '81177', '32565', '32924', '0');
INSERT INTO `spawnlist_light` VALUES ('59', '81177', '32565', '32931', '0');
INSERT INTO `spawnlist_light` VALUES ('60', '81177', '32571', '32948', '0');
INSERT INTO `spawnlist_light` VALUES ('61', '81177', '32544', '32955', '0');
INSERT INTO `spawnlist_light` VALUES ('62', '81177', '32544', '32962', '0');
INSERT INTO `spawnlist_light` VALUES ('63', '81177', '32553', '32975', '0');
INSERT INTO `spawnlist_light` VALUES ('64', '81177', '32568', '32978', '0');
INSERT INTO `spawnlist_light` VALUES ('65', '81177', '32575', '32978', '0');
INSERT INTO `spawnlist_light` VALUES ('66', '81177', '32589', '32959', '0');
INSERT INTO `spawnlist_light` VALUES ('67', '81177', '32604', '32947', '0');
INSERT INTO `spawnlist_light` VALUES ('68', '81177', '32620', '32952', '0');
INSERT INTO `spawnlist_light` VALUES ('69', '81177', '32620', '32957', '0');
INSERT INTO `spawnlist_light` VALUES ('70', '81177', '32620', '32963', '0');
INSERT INTO `spawnlist_light` VALUES ('71', '81177', '32626', '32967', '0');
INSERT INTO `spawnlist_light` VALUES ('72', '81177', '32637', '32967', '0');
INSERT INTO `spawnlist_light` VALUES ('73', '81177', '32620', '32969', '0');
INSERT INTO `spawnlist_light` VALUES ('74', '81177', '32539', '32941', '0');
INSERT INTO `spawnlist_light` VALUES ('75', '81177', '32540', '32919', '0');
INSERT INTO `spawnlist_light` VALUES ('76', '81177', '32552', '32923', '0');
INSERT INTO `spawnlist_light` VALUES ('77', '81177', '32552', '32926', '0');
INSERT INTO `spawnlist_light` VALUES ('78', '81177', '32530', '32925', '0');
INSERT INTO `spawnlist_light` VALUES ('79', '81177', '33681', '32511', '4');
INSERT INTO `spawnlist_light` VALUES ('80', '81177', '33681', '32522', '4');
INSERT INTO `spawnlist_light` VALUES ('81', '81177', '33712', '32516', '4');
INSERT INTO `spawnlist_light` VALUES ('82', '81177', '33706', '32492', '4');
INSERT INTO `spawnlist_light` VALUES ('83', '81177', '33716', '32471', '4');
INSERT INTO `spawnlist_light` VALUES ('84', '81177', '33728', '32471', '4');
INSERT INTO `spawnlist_light` VALUES ('85', '81177', '33729', '32497', '4');
INSERT INTO `spawnlist_light` VALUES ('86', '81177', '33726', '32520', '4');
INSERT INTO `spawnlist_light` VALUES ('87', '81177', '33736', '32520', '4');
INSERT INTO `spawnlist_light` VALUES ('88', '81177', '32636', '33205', '4');
INSERT INTO `spawnlist_light` VALUES ('89', '81177', '32644', '33218', '4');
INSERT INTO `spawnlist_light` VALUES ('90', '81177', '32620', '33228', '4');
INSERT INTO `spawnlist_light` VALUES ('91', '81177', '32608', '33221', '4');
INSERT INTO `spawnlist_light` VALUES ('92', '81177', '32587', '33197', '4');
INSERT INTO `spawnlist_light` VALUES ('93', '81177', '32587', '33192', '4');
INSERT INTO `spawnlist_light` VALUES ('94', '81177', '32587', '33172', '4');
INSERT INTO `spawnlist_light` VALUES ('95', '81177', '32588', '33162', '4');
INSERT INTO `spawnlist_light` VALUES ('96', '81177', '32611', '33158', '4');
INSERT INTO `spawnlist_light` VALUES ('97', '81177', '32617', '33158', '4');
INSERT INTO `spawnlist_light` VALUES ('98', '81177', '32640', '33176', '4');
INSERT INTO `spawnlist_light` VALUES ('99', '81177', '32619', '33183', '4');
INSERT INTO `spawnlist_light` VALUES ('100', '81177', '33031', '32761', '4');
INSERT INTO `spawnlist_light` VALUES ('101', '81177', '33031', '32773', '4');
INSERT INTO `spawnlist_light` VALUES ('102', '81177', '33029', '32788', '4');
INSERT INTO `spawnlist_light` VALUES ('103', '81177', '33029', '32794', '4');
INSERT INTO `spawnlist_light` VALUES ('104', '81177', '33050', '32813', '4');
INSERT INTO `spawnlist_light` VALUES ('105', '81177', '33067', '32813', '4');
INSERT INTO `spawnlist_light` VALUES ('106', '81177', '33082', '32789', '4');
INSERT INTO `spawnlist_light` VALUES ('107', '81177', '33082', '32775', '4');
INSERT INTO `spawnlist_light` VALUES ('108', '81177', '33082', '32762', '4');
INSERT INTO `spawnlist_light` VALUES ('109', '81177', '33029', '32741', '4');
INSERT INTO `spawnlist_light` VALUES ('110', '81177', '33063', '32722', '4');
INSERT INTO `spawnlist_light` VALUES ('111', '81177', '33069', '32722', '4');
INSERT INTO `spawnlist_light` VALUES ('112', '81177', '33081', '32742', '4');
INSERT INTO `spawnlist_light` VALUES ('113', '81179', '33427', '32850', '4');
INSERT INTO `spawnlist_light` VALUES ('114', '81179', '33384', '32858', '4');
INSERT INTO `spawnlist_light` VALUES ('115', '81179', '33362', '32883', '4');
INSERT INTO `spawnlist_light` VALUES ('116', '81179', '33368', '32890', '4');
INSERT INTO `spawnlist_light` VALUES ('117', '81179', '33411', '32878', '4');
INSERT INTO `spawnlist_light` VALUES ('118', '81179', '33448', '32890', '4');
INSERT INTO `spawnlist_light` VALUES ('119', '81179', '33461', '32891', '4');
INSERT INTO `spawnlist_light` VALUES ('120', '81179', '33467', '32869', '4');
INSERT INTO `spawnlist_light` VALUES ('121', '81179', '33477', '32837', '4');
INSERT INTO `spawnlist_light` VALUES ('122', '81179', '33545', '32844', '4');
INSERT INTO `spawnlist_light` VALUES ('123', '81179', '33545', '32856', '4');
INSERT INTO `spawnlist_light` VALUES ('124', '81179', '33520', '32850', '4');
INSERT INTO `spawnlist_light` VALUES ('125', '81179', '33530', '32894', '4');
INSERT INTO `spawnlist_light` VALUES ('126', '81179', '33518', '32894', '4');
INSERT INTO `spawnlist_light` VALUES ('127', '81179', '33523', '32873', '4');
INSERT INTO `spawnlist_light` VALUES ('128', '81179', '33446', '32826', '4');
INSERT INTO `spawnlist_light` VALUES ('129', '81179', '33346', '32836', '4');
INSERT INTO `spawnlist_light` VALUES ('130', '81179', '33346', '32823', '4');
INSERT INTO `spawnlist_light` VALUES ('131', '81179', '33380', '32814', '4');
INSERT INTO `spawnlist_light` VALUES ('132', '81179', '33364', '32801', '4');
INSERT INTO `spawnlist_light` VALUES ('133', '81179', '33350', '32785', '4');
INSERT INTO `spawnlist_light` VALUES ('134', '81179', '33366', '32776', '4');
INSERT INTO `spawnlist_light` VALUES ('135', '81179', '33373', '32738', '4');
INSERT INTO `spawnlist_light` VALUES ('136', '81179', '33340', '32754', '4');
INSERT INTO `spawnlist_light` VALUES ('137', '81179', '33339', '32739', '4');
INSERT INTO `spawnlist_light` VALUES ('138', '81179', '33342', '32717', '4');
INSERT INTO `spawnlist_light` VALUES ('139', '81179', '33405', '32722', '4');
INSERT INTO `spawnlist_light` VALUES ('140', '81179', '33359', '32691', '4');
INSERT INTO `spawnlist_light` VALUES ('141', '81179', '33333', '32701', '4');
INSERT INTO `spawnlist_light` VALUES ('142', '81179', '33333', '32686', '4');
INSERT INTO `spawnlist_light` VALUES ('143', '81179', '33388', '32667', '4');
INSERT INTO `spawnlist_light` VALUES ('144', '81179', '33417', '32663', '4');
INSERT INTO `spawnlist_light` VALUES ('145', '81179', '33437', '32691', '4');
INSERT INTO `spawnlist_light` VALUES ('146', '81179', '33466', '32662', '4');
INSERT INTO `spawnlist_light` VALUES ('147', '81179', '33491', '32645', '4');
INSERT INTO `spawnlist_light` VALUES ('148', '81179', '33484', '32640', '4');
INSERT INTO `spawnlist_light` VALUES ('149', '81179', '33439', '32652', '4');
INSERT INTO `spawnlist_light` VALUES ('150', '81179', '33418', '32636', '4');
INSERT INTO `spawnlist_light` VALUES ('151', '81179', '33405', '32635', '4');
INSERT INTO `spawnlist_light` VALUES ('152', '81179', '33368', '32643', '4');
INSERT INTO `spawnlist_light` VALUES ('153', '81179', '33357', '32643', '4');
INSERT INTO `spawnlist_light` VALUES ('154', '81179', '33491', '32683', '4');
INSERT INTO `spawnlist_light` VALUES ('155', '81179', '33520', '32694', '4');
INSERT INTO `spawnlist_light` VALUES ('156', '81179', '33531', '32694', '4');
INSERT INTO `spawnlist_light` VALUES ('157', '81179', '33508', '32763', '4');
INSERT INTO `spawnlist_light` VALUES ('158', '81179', '33499', '32763', '4');
INSERT INTO `spawnlist_light` VALUES ('159', '81179', '33514', '32781', '4');
INSERT INTO `spawnlist_light` VALUES ('160', '81179', '33514', '32793', '4');
INSERT INTO `spawnlist_light` VALUES ('161', '81179', '33475', '32809', '4');
INSERT INTO `spawnlist_light` VALUES ('162', '81179', '33439', '32764', '4');
INSERT INTO `spawnlist_light` VALUES ('163', '81179', '33476', '32738', '4');
INSERT INTO `spawnlist_light` VALUES ('164', '81179', '33476', '32730', '4');
INSERT INTO `spawnlist_light` VALUES ('165', '81179', '33451', '32719', '4');
INSERT INTO `spawnlist_light` VALUES ('166', '81180', '32743', '32442', '4');
INSERT INTO `spawnlist_light` VALUES ('167', '81181', '33047', '32809', '4');
INSERT INTO `spawnlist_light` VALUES ('168', '81177', '33625', '33295', '4');
INSERT INTO `spawnlist_light` VALUES ('169', '81177', '33044', '32813', '4');
INSERT INTO `spawnlist_light` VALUES ('170', '81180', '32797', '32284', '4');
INSERT INTO `spawnlist_light` VALUES ('171', '81177', '33492', '33370', '4');
INSERT INTO `spawnlist_light` VALUES ('172', '81177', '33498', '33370', '4');
INSERT INTO `spawnlist_light` VALUES ('173', '81177', '33550', '33370', '4');
INSERT INTO `spawnlist_light` VALUES ('174', '81179', '33462', '32778', '4');
INSERT INTO `spawnlist_light` VALUES ('175', '81160', '33430', '32805', '4');
INSERT INTO `spawnlist_light` VALUES ('176', '81160', '33429', '32822', '4');
INSERT INTO `spawnlist_light` VALUES ('177', '4500000', '33963', '33236', '4');
INSERT INTO `spawnlist_light` VALUES ('178', '4500000', '33913', '33193', '4');
INSERT INTO `spawnlist_light` VALUES ('179', '4500000', '33970', '33226', '4');
INSERT INTO `spawnlist_light` VALUES ('180', '4500000', '33963', '33221', '4');
INSERT INTO `spawnlist_light` VALUES ('181', '4500000', '33970', '33212', '4');
INSERT INTO `spawnlist_light` VALUES ('182', '4500000', '33963', '33207', '4');
INSERT INTO `spawnlist_light` VALUES ('183', '4500000', '33970', '33200', '4');
INSERT INTO `spawnlist_light` VALUES ('184', '4500000', '34018', '33135', '4');
INSERT INTO `spawnlist_light` VALUES ('185', '4500000', '34031', '33135', '4');
INSERT INTO `spawnlist_light` VALUES ('186', '4500000', '34043', '33135', '4');
INSERT INTO `spawnlist_light` VALUES ('187', '4500000', '34052', '33111', '4');
INSERT INTO `spawnlist_light` VALUES ('188', '4500000', '34038', '33111', '4');
INSERT INTO `spawnlist_light` VALUES ('189', '4500000', '34024', '33111', '4');
INSERT INTO `spawnlist_light` VALUES ('190', '4500000', '34102', '33143', '4');
INSERT INTO `spawnlist_light` VALUES ('191', '4500000', '34141', '33146', '4');
INSERT INTO `spawnlist_light` VALUES ('192', '4500000', '34150', '33146', '4');
INSERT INTO `spawnlist_light` VALUES ('193', '4500000', '34160', '33146', '4');
INSERT INTO `spawnlist_light` VALUES ('194', '4500000', '34169', '33155', '4');
INSERT INTO `spawnlist_light` VALUES ('195', '4500000', '34160', '33155', '4');
INSERT INTO `spawnlist_light` VALUES ('196', '4500000', '34150', '33155', '4');
INSERT INTO `spawnlist_light` VALUES ('197', '4500000', '34142', '33155', '4');
INSERT INTO `spawnlist_light` VALUES ('198', '4500000', '33991', '33239', '4');
INSERT INTO `spawnlist_light` VALUES ('199', '4500000', '33963', '33263', '4');
INSERT INTO `spawnlist_light` VALUES ('200', '4500000', '33972', '33270', '4');
INSERT INTO `spawnlist_light` VALUES ('201', '4500000', '33972', '33282', '4');
INSERT INTO `spawnlist_light` VALUES ('202', '4500000', '33974', '33303', '4');
INSERT INTO `spawnlist_light` VALUES ('203', '4500000', '33974', '33321', '4');
INSERT INTO `spawnlist_light` VALUES ('204', '4500000', '33971', '33335', '4');
INSERT INTO `spawnlist_light` VALUES ('205', '4500000', '33959', '33335', '4');
INSERT INTO `spawnlist_light` VALUES ('206', '4500000', '33946', '33335', '4');
INSERT INTO `spawnlist_light` VALUES ('207', '4500000', '33933', '33324', '4');
INSERT INTO `spawnlist_light` VALUES ('208', '4500000', '33933', '33310', '4');
INSERT INTO `spawnlist_light` VALUES ('209', '4500000', '33933', '33299', '4');
INSERT INTO `spawnlist_light` VALUES ('210', '4500000', '33916', '33350', '4');
INSERT INTO `spawnlist_light` VALUES ('211', '4500000', '33915', '33390', '4');
INSERT INTO `spawnlist_light` VALUES ('212', '4500000', '33915', '33405', '4');
INSERT INTO `spawnlist_light` VALUES ('213', '4500000', '33936', '33392', '4');
INSERT INTO `spawnlist_light` VALUES ('214', '4500000', '33937', '33368', '4');
INSERT INTO `spawnlist_light` VALUES ('215', '4500000', '33950', '33381', '4');
INSERT INTO `spawnlist_light` VALUES ('216', '4500000', '33964', '33381', '4');
INSERT INTO `spawnlist_light` VALUES ('217', '4500000', '33968', '33392', '4');
INSERT INTO `spawnlist_light` VALUES ('218', '4500000', '33977', '33381', '4');
INSERT INTO `spawnlist_light` VALUES ('219', '4500000', '33991', '33381', '4');
INSERT INTO `spawnlist_light` VALUES ('220', '4500000', '34011', '33354', '4');
INSERT INTO `spawnlist_light` VALUES ('221', '4500000', '34011', '33345', '4');
INSERT INTO `spawnlist_light` VALUES ('222', '4500000', '34011', '33337', '4');
INSERT INTO `spawnlist_light` VALUES ('223', '4500000', '34011', '33329', '4');
INSERT INTO `spawnlist_light` VALUES ('224', '4500000', '33993', '33335', '4');
INSERT INTO `spawnlist_light` VALUES ('225', '4500000', '33983', '33335', '4');
INSERT INTO `spawnlist_light` VALUES ('226', '4500000', '34025', '33357', '4');
INSERT INTO `spawnlist_light` VALUES ('227', '4500000', '34070', '33381', '4');
INSERT INTO `spawnlist_light` VALUES ('228', '4500000', '34092', '33397', '4');
INSERT INTO `spawnlist_light` VALUES ('229', '4500000', '34095', '33372', '4');
INSERT INTO `spawnlist_light` VALUES ('230', '4500000', '34095', '33360', '4');
INSERT INTO `spawnlist_light` VALUES ('231', '4500000', '34124', '33344', '4');
INSERT INTO `spawnlist_light` VALUES ('232', '4500000', '34116', '33372', '4');
INSERT INTO `spawnlist_light` VALUES ('233', '4500000', '34124', '33361', '4');
INSERT INTO `spawnlist_light` VALUES ('234', '4500000', '34124', '33387', '4');
INSERT INTO `spawnlist_light` VALUES ('235', '4500000', '34137', '33372', '4');
INSERT INTO `spawnlist_light` VALUES ('237', '81177', '33044', '32722', '4');
INSERT INTO `spawnlist_light` VALUES ('238', '81177', '33050', '32722', '4');
INSERT INTO `spawnlist_light` VALUES ('264', '81179', '33485', '32779', '4');
INSERT INTO `spawnlist_light` VALUES ('242', '81177', '33131', '32744', '4');
INSERT INTO `spawnlist_light` VALUES ('243', '81177', '33152', '32747', '4');
INSERT INTO `spawnlist_light` VALUES ('244', '81177', '33152', '32753', '4');
INSERT INTO `spawnlist_light` VALUES ('245', '81177', '33165', '32766', '4');
INSERT INTO `spawnlist_light` VALUES ('246', '81177', '33174', '32766', '4');
INSERT INTO `spawnlist_light` VALUES ('247', '81177', '33147', '32812', '4');
INSERT INTO `spawnlist_light` VALUES ('248', '81177', '33156', '32812', '4');
INSERT INTO `spawnlist_light` VALUES ('249', '81177', '33107', '32775', '4');
INSERT INTO `spawnlist_light` VALUES ('250', '81177', '33107', '32767', '4');
INSERT INTO `spawnlist_light` VALUES ('251', '81160', '33635', '32681', '4');
INSERT INTO `spawnlist_light` VALUES ('252', '81160', '33628', '32674', '4');
INSERT INTO `spawnlist_light` VALUES ('253', '4500002', '33605', '32674', '4');
INSERT INTO `spawnlist_light` VALUES ('254', '4500002', '33605', '32682', '4');
INSERT INTO `spawnlist_light` VALUES ('255', '81160', '33577', '32682', '4');
INSERT INTO `spawnlist_light` VALUES ('256', '81160', '33577', '32674', '4');
INSERT INTO `spawnlist_light` VALUES ('257', '4500002', '33628', '32705', '4');
INSERT INTO `spawnlist_light` VALUES ('258', '4500002', '33635', '32705', '4');
INSERT INTO `spawnlist_light` VALUES ('259', '81160', '33626', '32739', '4');
INSERT INTO `spawnlist_light` VALUES ('260', '81160', '33636', '32739', '4');
INSERT INTO `spawnlist_light` VALUES ('261', '4500002', '33669', '32693', '4');
INSERT INTO `spawnlist_light` VALUES ('262', '4500002', '33658', '32681', '4');
INSERT INTO `spawnlist_light` VALUES ('263', '4500002', '32658', '32674', '4');
