/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:07:02
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `npcchat`
-- ----------------------------
DROP TABLE IF EXISTS `npcchat`;
CREATE TABLE `npcchat` (
  `npc_id` int(10) unsigned NOT NULL DEFAULT '0',
  `chat_timing` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `note` varchar(45) NOT NULL DEFAULT '',
  `start_delay_time` int(10) NOT NULL DEFAULT '0',
  `chat_id1` varchar(45) NOT NULL DEFAULT '',
  `chat_id2` varchar(45) NOT NULL DEFAULT '',
  `chat_id3` varchar(45) NOT NULL DEFAULT '',
  `chat_id4` varchar(45) NOT NULL DEFAULT '',
  `chat_id5` varchar(45) NOT NULL DEFAULT '',
  `chat_interval` int(10) unsigned NOT NULL DEFAULT '0',
  `is_shout` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `is_world_chat` tinyint(1) NOT NULL DEFAULT '0',
  `is_repeat` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `repeat_interval` int(10) unsigned NOT NULL DEFAULT '0',
  `game_time` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`npc_id`,`chat_timing`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of npcchat
-- ----------------------------
INSERT INTO `npcchat` VALUES ('45473', '0', '바르타자르(출현시)', '0', '$339', '', '', '', '', '0', '1', '0', '1', '20000', '0');
INSERT INTO `npcchat` VALUES ('45488', '0', '앙금 파(출현시)', '5000', '$340', '', '', '', '', '0', '1', '0', '1', '20000', '0');
INSERT INTO `npcchat` VALUES ('45497', '0', '멜키 올(출현시)', '10000', '$341', '', '', '', '', '0', '1', '0', '1', '20000', '0');
INSERT INTO `npcchat` VALUES ('45464', '0', '세마(출현시)', '15000', '$342', '', '', '', '', '0', '1', '0', '1', '20000', '0');
INSERT INTO `npcchat` VALUES ('45545', '0', '브락크에르다(출현시)', '0', '$993', '', '', '', '', '0', '1', '0', '1', '10000', '0');
INSERT INTO `npcchat` VALUES ('45545', '1', '브락크에르다(사망시)', '0', '$995', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45600', '0', '카트(출현시)', '0', '$275', '$279', '$281', '$285', '$287', '5000', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45600', '1', '카트(사망시)', '0', '$302', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45264', '2', '하피(하늘에서 내렸을 때)', '0', '$994', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45573', '0', '바포멧트(출현시)', '0', '$825', '', '', '', '', '0', '1', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45931', '1', '물의 정령(HC)(사망시)', '0', '$5167', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45935', '0', '저주해진 메듀사(HC)(출현시)', '0', '$5169', '', '', '', '', '0', '0', '0', '0', '10000', '0');
INSERT INTO `npcchat` VALUES ('45941', '1', '저주해진 무녀 사엘(HC)(사망시)', '0', '$5166', '', '', '', '', '0', '1', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45942', '0', '저주해진 물의 대정령(HC)(출현시)', '0', '$5170', '', '', '', '', '0', '1', '0', '0', '10000', '0');
INSERT INTO `npcchat` VALUES ('45943', '1', '카프(HC)(사망시)', '0', '$5165', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('46083', '0', '보초병(렛서데이몬)', '0', '$5016', '', '', '', '', '0', '1', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('46098', '0', '보초병(사제창)', '0', '$5014', '', '', '', '', '0', '1', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45577', '1', '여단장 다크 판사(사망시)', '0', '$3892', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45607', '1', '마수단장 조개 바(사망시)', '0', '$3900', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45863', '1', '마령군왕라이아(사망시)', '0', '$3908', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45676', '1', '명법군왕헤르바인(사망시)', '0', '$3923', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45648', '1', '암살군왕스레이브(사망시)', '0', '$3940', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45588', '1', '사단장 신크레어(사망시)', '0', '$3901', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45602', '1', '마법 단장 카르미엘(사망시)', '0', '$3903', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45608', '1', '용병 대장 메파이스트(사망시)', '0', '$3930', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45612', '1', '신관장 바운티(사망시)', '0', '$3912', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45844', '1', '마수군왕바란카(사망시)', '0', '$3895', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45615', '1', '명법단장 크리파스(사망시)', '0', '$3917', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45585', '1', '암살 단장 치우침 이즈(사망시)', '0', '$3939', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45574', '1', '친위대장 가이토(사망시)', '0', '$3938', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45492', '1', '슈노바(사망시)', '0', '$3943', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45548', '1', '호세(사망시)', '0', '$4030', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45955', '1', '대법관 케이나(사망시)', '0', '$4625', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45959', '1', '대법관 이데아(사망시)', '0', '$4626', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45963', '1', '부제사장 카산드라(사망시)', '0', '$3888', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('81175', '0', '질문(방문)자 쿠쟈크(출현시)', '0', '$5325', '', '', '', '', '0', '1', '0', '1', '15000', '0');
INSERT INTO `npcchat` VALUES ('81175', '1', '질문(방문)자 쿠쟈크(사망시)', '0', '$5327', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45458', '1', '드레이크(사망시)', '0', '$3603', '', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('200069', '1', '오크밀사', '0', '$6127', '$6128', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('200070', '1', '오크밀사', '0', '$6127', '$6128', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('200071', '1', '오크밀사', '0', '$6127', '$6128', '', '', '', '0', '0', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45601', '0', '데스나이트', '0', '$404', '', '', '', '', '0', '1', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('45110', '0', '봉인된 서큐버스 퀸', '0', '$1754', '', '', '', '', '0', '1', '0', '0', '0', '0');
INSERT INTO `npcchat` VALUES ('70506', '0', '루바(노섬)', '0', '$1735', '$1736', '', '', '', '10000', '1', '0', '1', '30000', '0');
INSERT INTO `npcchat` VALUES ('70518', '0', '티오(숨계)', '0', '$1733', '$1734', '', '', '', '10000', '1', '0', '1', '30000', '0');
INSERT INTO `npcchat` VALUES ('70755', '0', '상아탑마법사(2238)', '0', '$1886', '$1887', '$1888', '', '', '20000', '0', '0', '1', '30000', '0');
INSERT INTO `npcchat` VALUES ('4409000', '0', '상아탑마법사(2237)', '0', '$1883', '$1884', '$1885', '', '', '20000', '0', '0', '1', '30000', '0');
INSERT INTO `npcchat` VALUES ('4409001', '0', '페이퍼맨(2239)', '0', '$1880', '$1881', '$1882', '', '', '20000', '0', '0', '1', '30000', '0');
INSERT INTO `npcchat` VALUES ('4030002', '1', '추석버그베어사망시', '0', '$7962', '', '', '', '', '0', '0', '0', '0', '0', '0');
