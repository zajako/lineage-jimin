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
INSERT IGNORE INTO `spawnlist_castledoor` VALUES 
('5', 'Dwarf Castle', '1913', '32845', '32812', '66', '0', '32844', '32813', '2', '70995', '32813'),
('19', '난성 외성문 7시', '1826', '32780', '32858', '66', '0', '32779', '32859', '4', '70994', '32860'),
('18', '난성 외성문 5시', '1827', '32812', '32887', '66', '1', '32811', '32888', '4', '70993', '32813'),
('16', '하이네 5시 외다리', '1745', '33524', '33470', '4', '1', '33523', '33471', '2', '70992', '33524'),
('15', '기란 외성문 3시 안쪽', '1338', '33654', '32677', '4', '0', '33653', '32678', '2', '70991', '32678'),
('13', '기란 외성문 5시 안쪽', '2744', '33631', '32702', '4', '1', '33630', '32703', '4', '70990', '33634'),
('14', '기란 외성문 7시 안쪽', '1338', '33609', '32677', '4', '0', '33608', '32678', '2', '70989', '32678'),
('6', 'Aden Castle', '1336', '34091', '33203', '4', '1', '34090', '33204', '2', '70996', '34091'),
('11', '기란 외성문 7시 밖쪽', '2746', '33580', '32678', '4', '0', '33579', '32679', '4', '70988', '32679'),
('9', '윈성 외성문 7시', '2736', '32590', '33409', '4', '0', '32589', '33410', '4', '70987', '33410'),
('7', '켄성 외성문 5시', '3234', '33152', '32807', '4', '1', '33151', '32808', '4', '70985', '33153'),
('4', 'Heine Castle', '1718', '33523', '33385', '4', '1', '33522', '33386', '2', '70863', '33523'),
('17', '하이네 9시쪽 외성문', '1718', '33525', '33344', '4', '1', '33524', '33345', '2', '70862', '33525'),
('3', 'Giran Castle', '1336', '33632', '32660', '4', '1', '33631', '32661', '2', '70817', '33632'),
('12', '기란 외성문 5시 밖쪽', '2745', '33631', '32734', '4', '1', '33630', '32735', '4', '70800', '33633'),
('2', 'Windwood Castle', '339', '32678', '33392', '4', '1', '32677', '33393', '4', '70778', '32678'),
('20', '윈성 외성문 5시', '3234', '32625', '33436', '4', '1', '32624', '33437', '4', '70687', '32626'),
('1', 'Kent Castle', '339', '33171', '32759', '4', '1', '33170', '32760', '2', '70656', '33171'),
('10', '오성 외성문 5시', '2732', '32785', '32323', '4', '1', '32784', '32324', '4', '70600', '32787'),
('8', '켄트 외성문 7시', '2736', '33112', '32771', '4', '0', '33111', '32772', '4', '70549', '32772'),
('100', '유령의집 대문', '6351', '32727', '32829', '5140', '0', '32726', '32830', '7', '0', '32833'),
('101', '유령의집 벽문', '6336', '32762', '32819', '5140', '1', '32761', '32820', '3', '0', '32763'),
('102', '유령의집 벽문', '6336', '32761', '32797', '5140', '1', '32760', '32798', '3', '0', '32762'),
('103', '유령의집 벽문', '6336', '32760', '32841', '5140', '1', '32759', '32842', '3', '0', '32761'),
('104', '유령의집 벽문', '6336', '32759', '32860', '5140', '1', '32758', '32861', '3', '0', '32760'),
('105', '유령의집 벽문', '6336', '32807', '32803', '5140', '1', '32806', '32804', '3', '0', '32808'),
('106', '유령의집 벽문', '6336', '32841', '32819', '5140', '1', '32840', '32820', '3', '0', '32842'),
('107', '유령의집 벽문', '6336', '32846', '32797', '5140', '1', '32845', '32798', '3', '0', '32847'),
('108', '유령의집 벽문', '6336', '32856', '32844', '5140', '1', '32855', '32845', '3', '0', '32857'),
('109', '유령의집 벽문', '6336', '32869', '32816', '5140', '1', '32868', '32817', '3', '0', '32870'),
('110', '유령의집 벽문', '6336', '32870', '32844', '5140', '1', '32869', '32845', '3', '0', '32871'),
('111', '유령의집 벽문', '6336', '32841', '32865', '5140', '1', '32840', '32866', '3', '0', '32842'),
('112', '유령의집 벽문', '6336', '32856', '32816', '5140', '1', '32855', '32817', '3', '0', '32857'),
('113', '유령의집 벽문', '6379', '32766', '32788', '5140', '0', '32765', '32789', '3', '0', '32790'),
('114', '유령의집 벽문', '6379', '32766', '32848', '5140', '0', '32765', '32849', '3', '0', '32850'),
('115', '유령의집 벽문', '6379', '32763', '32867', '5140', '0', '32762', '32868', '3', '0', '32869'),
('116', '유령의집 벽문', '6379', '32785', '32853', '5140', '0', '32784', '32854', '3', '0', '32855'),
('117', '유령의집 벽문', '6379', '32790', '32830', '5140', '0', '32789', '32831', '3', '0', '32832'),
('118', '유령의집 벽문', '6379', '32791', '32816', '5140', '0', '32790', '32817', '3', '0', '32818'),
('119', '유령의집 벽문', '6379', '32793', '32802', '5140', '0', '32792', '32803', '3', '0', '32804'),
('120', '유령의집 벽문', '6379', '32778', '32796', '5140', '0', '32777', '32797', '3', '0', '32798'),
('121', '유령의집 벽문', '6379', '32788', '32787', '5140', '0', '32787', '32788', '3', '0', '32789'),
('122', '유령의집 벽문', '6379', '32803', '32849', '5140', '0', '32802', '32850', '3', '0', '32851'),
('123', '유령의집 벽문', '6379', '32811', '32858', '5140', '0', '32810', '32859', '3', '0', '32860'),
('124', '유령의집 벽문', '6379', '32817', '32790', '5140', '0', '32816', '32791', '3', '0', '32792'),
('125', '유령의집 벽문', '6379', '32817', '32818', '5140', '0', '32816', '32819', '3', '0', '32820'),
('126', '유령의집 벽문', '6379', '32833', '32827', '5140', '0', '32832', '32828', '3', '0', '32829'),
('127', '유령의집 벽문', '6379', '32840', '32806', '5140', '0', '32839', '32807', '3', '0', '32808'),
('128', '유령의집 벽문', '6379', '32841', '32787', '5140', '0', '32840', '32788', '3', '0', '32789'),
('129', '유령의집 벽문', '6379', '32864', '32786', '5140', '0', '32863', '32787', '3', '0', '32788'),
('130', '유령의집 벽문', '6379', '32763', '32807', '5140', '0', '32762', '32808', '3', '0', '32809'),
('131', '유령의집 벽문', '6379', '32861', '32852', '5140', '0', '32860', '32853', '3', '0', '32854'),
('132', '유령의집 벽문', '6379', '32852', '32872', '5140', '0', '32851', '32873', '3', '0', '32874'),
('133', '유령의집 벽문', '6379', '32835', '32874', '5140', '0', '32834', '32875', '3', '0', '32876'),
('134', '유령의집 벽문', '6379', '32809', '32873', '5140', '0', '32808', '32874', '3', '0', '32875'),
('135', '유령의집 벽문', '6379', '32801', '32867', '5140', '0', '32800', '32868', '3', '0', '32869'),
('136', '유령의집 벽문', '6379', '32789', '32871', '5140', '0', '32788', '32872', '3', '0', '32873'),
('137', '유령의집 벽문', '6379', '32800', '32810', '5140', '0', '32799', '32811', '3', '0', '32812'),
('138', '유령의집 벽문', '6379', '32830', '32845', '5140', '0', '32829', '32846', '3', '0', '32847'),
('139', '유령의집 벽문', '6379', '32848', '32832', '5140', '0', '32847', '32833', '3', '0', '32834'),
('140', '유령의집 벽문', '6379', '32768', '32827', '5140', '0', '32767', '32828', '3', '0', '32829'),
('141', '유령의집 벽문', '6379', '32858', '32828', '5140', '0', '32857', '32829', '3', '0', '32830'),
('142', '유령의집 벽문', '6379', '32778', '32815', '5140', '0', '32777', '32816', '3', '0', '32817'),
('143', '유령의집 벽문', '6379', '32817', '32838', '5140', '0', '32816', '32839', '3', '0', '32840'),
('144', '유령의집 벽문', '6336', '32812', '32831', '5140', '1', '32811', '32832', '3', '0', '32813'),
('145', '펫레이싱 문', '6677', '32762', '32848', '5143', '0', '32761', '32849', '9', '0', '32852');
