/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:08:49
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `spawnlist_castledoor`
-- ----------------------------
DROP TABLE IF EXISTS `spawnlist_castledoor`;
CREATE TABLE `spawnlist_castledoor` (
  `id` int(11) NOT NULL DEFAULT '0',
  `location` varchar(25) NOT NULL DEFAULT '',
  `gfxid` int(11) NOT NULL DEFAULT '0',
  `locx` int(11) NOT NULL DEFAULT '0',
  `locy` int(11) NOT NULL DEFAULT '0',
  `mapid` int(11) NOT NULL DEFAULT '0',
  `direction` int(11) NOT NULL DEFAULT '0',
  `entrancex` int(11) NOT NULL DEFAULT '0',
  `entrancey` int(11) NOT NULL DEFAULT '0',
  `doorsize` int(11) NOT NULL DEFAULT '0',
  `keeper` int(11) NOT NULL DEFAULT '0',
  `att` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of spawnlist_castledoor
-- ----------------------------
INSERT INTO `spawnlist_castledoor` VALUES ('5', 'Dwarf Castle', '1913', '32845', '32812', '66', '0', '32844', '32813', '2', '70995', '32813');
INSERT INTO `spawnlist_castledoor` VALUES ('19', '난성 외성문 7시', '1826', '32780', '32858', '66', '0', '32779', '32859', '4', '70994', '32860');
INSERT INTO `spawnlist_castledoor` VALUES ('18', '난성 외성문 5시', '1827', '32812', '32887', '66', '1', '32811', '32888', '4', '70993', '32813');
INSERT INTO `spawnlist_castledoor` VALUES ('16', '하이네 5시 외다리', '1745', '33524', '33470', '4', '1', '33523', '33471', '2', '70992', '33524');
INSERT INTO `spawnlist_castledoor` VALUES ('15', '기란 외성문 3시 안쪽', '1338', '33654', '32677', '4', '0', '33653', '32678', '2', '70991', '32678');
INSERT INTO `spawnlist_castledoor` VALUES ('13', '기란 외성문 5시 안쪽', '2744', '33631', '32702', '4', '1', '33630', '32703', '4', '70990', '33634');
INSERT INTO `spawnlist_castledoor` VALUES ('14', '기란 외성문 7시 안쪽', '1338', '33609', '32677', '4', '0', '33608', '32678', '2', '70989', '32678');
INSERT INTO `spawnlist_castledoor` VALUES ('6', 'Aden Castle', '1336', '34091', '33203', '4', '1', '34090', '33204', '2', '70996', '34091');
INSERT INTO `spawnlist_castledoor` VALUES ('11', '기란 외성문 7시 밖쪽', '2746', '33580', '32678', '4', '0', '33579', '32679', '4', '70988', '32679');
INSERT INTO `spawnlist_castledoor` VALUES ('9', '윈성 외성문 7시', '2736', '32590', '33409', '4', '0', '32589', '33410', '4', '70987', '33410');
INSERT INTO `spawnlist_castledoor` VALUES ('7', '켄성 외성문 5시', '3234', '33152', '32807', '4', '1', '33151', '32808', '4', '70985', '33153');
INSERT INTO `spawnlist_castledoor` VALUES ('4', 'Heine Castle', '1718', '33523', '33385', '4', '1', '33522', '33386', '2', '70863', '33523');
INSERT INTO `spawnlist_castledoor` VALUES ('17', '하이네 9시쪽 외성문', '1718', '33525', '33344', '4', '1', '33524', '33345', '2', '70862', '33525');
INSERT INTO `spawnlist_castledoor` VALUES ('3', 'Giran Castle', '1336', '33632', '32660', '4', '1', '33631', '32661', '2', '70817', '33632');
INSERT INTO `spawnlist_castledoor` VALUES ('12', '기란 외성문 5시 밖쪽', '2745', '33631', '32734', '4', '1', '33630', '32735', '4', '70800', '33633');
INSERT INTO `spawnlist_castledoor` VALUES ('2', 'Windwood Castle', '339', '32678', '33392', '4', '1', '32677', '33393', '4', '70778', '32678');
INSERT INTO `spawnlist_castledoor` VALUES ('20', '윈성 외성문 5시', '3234', '32625', '33436', '4', '1', '32624', '33437', '4', '70687', '32626');
INSERT INTO `spawnlist_castledoor` VALUES ('1', 'Kent Castle', '339', '33171', '32759', '4', '1', '33170', '32760', '2', '70656', '33171');
INSERT INTO `spawnlist_castledoor` VALUES ('10', '오성 외성문 5시', '2732', '32785', '32323', '4', '1', '32784', '32324', '4', '70600', '32787');
INSERT INTO `spawnlist_castledoor` VALUES ('8', '켄트 외성문 7시', '2736', '33112', '32771', '4', '0', '33111', '32772', '4', '70549', '32772');
INSERT INTO `spawnlist_castledoor` VALUES ('100', '유령의집 대문', '6351', '32727', '32829', '5140', '0', '32726', '32830', '7', '0', '32833');
INSERT INTO `spawnlist_castledoor` VALUES ('101', '유령의집 벽문', '6336', '32762', '32819', '5140', '1', '32761', '32820', '3', '0', '32763');
INSERT INTO `spawnlist_castledoor` VALUES ('102', '유령의집 벽문', '6336', '32761', '32797', '5140', '1', '32760', '32798', '3', '0', '32762');
INSERT INTO `spawnlist_castledoor` VALUES ('103', '유령의집 벽문', '6336', '32760', '32841', '5140', '1', '32759', '32842', '3', '0', '32761');
INSERT INTO `spawnlist_castledoor` VALUES ('104', '유령의집 벽문', '6336', '32759', '32860', '5140', '1', '32758', '32861', '3', '0', '32760');
INSERT INTO `spawnlist_castledoor` VALUES ('105', '유령의집 벽문', '6336', '32807', '32803', '5140', '1', '32806', '32804', '3', '0', '32808');
INSERT INTO `spawnlist_castledoor` VALUES ('106', '유령의집 벽문', '6336', '32841', '32819', '5140', '1', '32840', '32820', '3', '0', '32842');
INSERT INTO `spawnlist_castledoor` VALUES ('107', '유령의집 벽문', '6336', '32846', '32797', '5140', '1', '32845', '32798', '3', '0', '32847');
INSERT INTO `spawnlist_castledoor` VALUES ('108', '유령의집 벽문', '6336', '32856', '32844', '5140', '1', '32855', '32845', '3', '0', '32857');
INSERT INTO `spawnlist_castledoor` VALUES ('109', '유령의집 벽문', '6336', '32869', '32816', '5140', '1', '32868', '32817', '3', '0', '32870');
INSERT INTO `spawnlist_castledoor` VALUES ('110', '유령의집 벽문', '6336', '32870', '32844', '5140', '1', '32869', '32845', '3', '0', '32871');
INSERT INTO `spawnlist_castledoor` VALUES ('111', '유령의집 벽문', '6336', '32841', '32865', '5140', '1', '32840', '32866', '3', '0', '32842');
INSERT INTO `spawnlist_castledoor` VALUES ('112', '유령의집 벽문', '6336', '32856', '32816', '5140', '1', '32855', '32817', '3', '0', '32857');
INSERT INTO `spawnlist_castledoor` VALUES ('113', '유령의집 벽문', '6379', '32766', '32788', '5140', '0', '32765', '32789', '3', '0', '32790');
INSERT INTO `spawnlist_castledoor` VALUES ('114', '유령의집 벽문', '6379', '32766', '32848', '5140', '0', '32765', '32849', '3', '0', '32850');
INSERT INTO `spawnlist_castledoor` VALUES ('115', '유령의집 벽문', '6379', '32763', '32867', '5140', '0', '32762', '32868', '3', '0', '32869');
INSERT INTO `spawnlist_castledoor` VALUES ('116', '유령의집 벽문', '6379', '32785', '32853', '5140', '0', '32784', '32854', '3', '0', '32855');
INSERT INTO `spawnlist_castledoor` VALUES ('117', '유령의집 벽문', '6379', '32790', '32830', '5140', '0', '32789', '32831', '3', '0', '32832');
INSERT INTO `spawnlist_castledoor` VALUES ('118', '유령의집 벽문', '6379', '32791', '32816', '5140', '0', '32790', '32817', '3', '0', '32818');
INSERT INTO `spawnlist_castledoor` VALUES ('119', '유령의집 벽문', '6379', '32793', '32802', '5140', '0', '32792', '32803', '3', '0', '32804');
INSERT INTO `spawnlist_castledoor` VALUES ('120', '유령의집 벽문', '6379', '32778', '32796', '5140', '0', '32777', '32797', '3', '0', '32798');
INSERT INTO `spawnlist_castledoor` VALUES ('121', '유령의집 벽문', '6379', '32788', '32787', '5140', '0', '32787', '32788', '3', '0', '32789');
INSERT INTO `spawnlist_castledoor` VALUES ('122', '유령의집 벽문', '6379', '32803', '32849', '5140', '0', '32802', '32850', '3', '0', '32851');
INSERT INTO `spawnlist_castledoor` VALUES ('123', '유령의집 벽문', '6379', '32811', '32858', '5140', '0', '32810', '32859', '3', '0', '32860');
INSERT INTO `spawnlist_castledoor` VALUES ('124', '유령의집 벽문', '6379', '32817', '32790', '5140', '0', '32816', '32791', '3', '0', '32792');
INSERT INTO `spawnlist_castledoor` VALUES ('125', '유령의집 벽문', '6379', '32817', '32818', '5140', '0', '32816', '32819', '3', '0', '32820');
INSERT INTO `spawnlist_castledoor` VALUES ('126', '유령의집 벽문', '6379', '32833', '32827', '5140', '0', '32832', '32828', '3', '0', '32829');
INSERT INTO `spawnlist_castledoor` VALUES ('127', '유령의집 벽문', '6379', '32840', '32806', '5140', '0', '32839', '32807', '3', '0', '32808');
INSERT INTO `spawnlist_castledoor` VALUES ('128', '유령의집 벽문', '6379', '32841', '32787', '5140', '0', '32840', '32788', '3', '0', '32789');
INSERT INTO `spawnlist_castledoor` VALUES ('129', '유령의집 벽문', '6379', '32864', '32786', '5140', '0', '32863', '32787', '3', '0', '32788');
INSERT INTO `spawnlist_castledoor` VALUES ('130', '유령의집 벽문', '6379', '32763', '32807', '5140', '0', '32762', '32808', '3', '0', '32809');
INSERT INTO `spawnlist_castledoor` VALUES ('131', '유령의집 벽문', '6379', '32861', '32852', '5140', '0', '32860', '32853', '3', '0', '32854');
INSERT INTO `spawnlist_castledoor` VALUES ('132', '유령의집 벽문', '6379', '32852', '32872', '5140', '0', '32851', '32873', '3', '0', '32874');
INSERT INTO `spawnlist_castledoor` VALUES ('133', '유령의집 벽문', '6379', '32835', '32874', '5140', '0', '32834', '32875', '3', '0', '32876');
INSERT INTO `spawnlist_castledoor` VALUES ('134', '유령의집 벽문', '6379', '32809', '32873', '5140', '0', '32808', '32874', '3', '0', '32875');
INSERT INTO `spawnlist_castledoor` VALUES ('135', '유령의집 벽문', '6379', '32801', '32867', '5140', '0', '32800', '32868', '3', '0', '32869');
INSERT INTO `spawnlist_castledoor` VALUES ('136', '유령의집 벽문', '6379', '32789', '32871', '5140', '0', '32788', '32872', '3', '0', '32873');
INSERT INTO `spawnlist_castledoor` VALUES ('137', '유령의집 벽문', '6379', '32800', '32810', '5140', '0', '32799', '32811', '3', '0', '32812');
INSERT INTO `spawnlist_castledoor` VALUES ('138', '유령의집 벽문', '6379', '32830', '32845', '5140', '0', '32829', '32846', '3', '0', '32847');
INSERT INTO `spawnlist_castledoor` VALUES ('139', '유령의집 벽문', '6379', '32848', '32832', '5140', '0', '32847', '32833', '3', '0', '32834');
INSERT INTO `spawnlist_castledoor` VALUES ('140', '유령의집 벽문', '6379', '32768', '32827', '5140', '0', '32767', '32828', '3', '0', '32829');
INSERT INTO `spawnlist_castledoor` VALUES ('141', '유령의집 벽문', '6379', '32858', '32828', '5140', '0', '32857', '32829', '3', '0', '32830');
INSERT INTO `spawnlist_castledoor` VALUES ('142', '유령의집 벽문', '6379', '32778', '32815', '5140', '0', '32777', '32816', '3', '0', '32817');
INSERT INTO `spawnlist_castledoor` VALUES ('143', '유령의집 벽문', '6379', '32817', '32838', '5140', '0', '32816', '32839', '3', '0', '32840');
INSERT INTO `spawnlist_castledoor` VALUES ('144', '유령의집 벽문', '6336', '32812', '32831', '5140', '1', '32811', '32832', '3', '0', '32813');
INSERT INTO `spawnlist_castledoor` VALUES ('145', '펫레이싱 문', '6677', '32762', '32848', '5143', '0', '32761', '32849', '9', '0', '32852');
