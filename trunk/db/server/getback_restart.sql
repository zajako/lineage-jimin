/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:04:10
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `getback_restart`
-- ----------------------------
DROP TABLE IF EXISTS `getback_restart`;
CREATE TABLE `getback_restart` (
  `area` int(10) NOT NULL DEFAULT '0',
  `note` varchar(50) DEFAULT NULL,
  `locx` int(10) NOT NULL DEFAULT '0',
  `locy` int(10) NOT NULL DEFAULT '0',
  `mapid` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`area`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of getback_restart
-- ----------------------------
INSERT INTO `getback_restart` VALUES ('70', '잊을 수 있었던 섬', '32828', '32848', '70');
INSERT INTO `getback_restart` VALUES ('75', '상아의 탑:1계', '34047', '32283', '4');
INSERT INTO `getback_restart` VALUES ('76', '상아의 탑:2계', '34047', '32283', '4');
INSERT INTO `getback_restart` VALUES ('77', '상아의 탑:3계', '34047', '32283', '4');
INSERT INTO `getback_restart` VALUES ('78', '상아의 탑:4계', '34047', '32283', '4');
INSERT INTO `getback_restart` VALUES ('79', '상아의 탑:5계', '34047', '32283', '4');
INSERT INTO `getback_restart` VALUES ('80', '상아의 탑:6계', '34047', '32283', '4');
INSERT INTO `getback_restart` VALUES ('81', '상아의 탑:7계', '34047', '32283', '4');
INSERT INTO `getback_restart` VALUES ('82', '상아의 탑:8계', '34047', '32283', '4');
INSERT INTO `getback_restart` VALUES ('88', 'Giran Colosseum', '33442', '32797', '0');
INSERT INTO `getback_restart` VALUES ('91', 'Talking island Colosseum', '32580', '32931', '4');
INSERT INTO `getback_restart` VALUES ('92', 'Gludio Colosseum', '32612', '32734', '0');
INSERT INTO `getback_restart` VALUES ('95', 'Silver knight Colosseum', '33080', '33392', '4');
INSERT INTO `getback_restart` VALUES ('98', 'Welldone Colosseum', '33705', '32504', '4');
INSERT INTO `getback_restart` VALUES ('101', '오만의 탑 1F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('102', '오만의 탑 2F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('103', '오만의 탑 3F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('104', '오만의 탑 4F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('105', '오만의 탑 5F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('106', '오만의 탑 6F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('107', '오만의 탑 7F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('108', '오만의 탑 8F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('109', '오만의 탑 9F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('110', '오만의 탑 10F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('111', '오만의 탑 11F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('112', '오만의 탑 12F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('113', '오만의 탑 13F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('114', '오만의 탑 14F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('115', '오만의 탑 15F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('116', '오만의 탑 16F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('117', '오만의 탑 17F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('118', '오만의 탑 18F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('119', '오만의 탑 19F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('120', '오만의 탑 20F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('121', '오만의 탑 21F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('122', '오만의 탑 22F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('123', '오만의 탑 23F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('124', '오만의 탑 24F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('125', '오만의 탑 25F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('126', '오만의 탑 26F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('127', '오만의 탑 27F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('128', '오만의 탑 28F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('129', '오만의 탑 29F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('130', '오만의 탑 30F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('131', '오만의 탑 31F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('132', '오만의 탑 32F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('133', '오만의 탑 33F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('134', '오만의 탑 34F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('135', '오만의 탑 35F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('136', '오만의 탑 36F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('137', '오만의 탑 37F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('138', '오만의 탑 38F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('139', '오만의 탑 39F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('140', '오만의 탑 40F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('141', '오만의 탑 41F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('142', '오만의 탑 42F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('143', '오만의 탑 43F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('144', '오만의 탑 44F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('145', '오만의 탑 45F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('146', '오만의 탑 46F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('147', '오만의 탑 47F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('148', '오만의 탑 48F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('149', '오만의 탑 49F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('150', '오만의 탑 50F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('151', '오만의 탑 51F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('152', '오만의 탑 52F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('153', '오만의 탑 53F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('154', '오만의 탑 54F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('155', '오만의 탑 55F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('156', '오만의 탑 56F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('157', '오만의 탑 57F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('158', '오만의 탑 58F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('159', '오만의 탑 59F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('160', '오만의 탑 60F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('161', '오만의 탑 61F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('162', '오만의 탑 62F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('163', '오만의 탑 63F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('164', '오만의 탑 64F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('165', '오만의 탑 65F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('166', '오만의 탑 66F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('167', '오만의 탑 67F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('168', '오만의 탑 68F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('169', '오만의 탑 69F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('170', '오만의 탑 70F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('171', '오만의 탑 71F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('172', '오만의 탑 72F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('173', '오만의 탑 73F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('174', '오만의 탑 74F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('175', '오만의 탑 75F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('176', '오만의 탑 76F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('177', '오만의 탑 77F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('178', '오만의 탑 78F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('179', '오만의 탑 79F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('180', '오만의 탑 80F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('181', '오만의 탑 81F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('182', '오만의 탑 82F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('183', '오만의 탑 83F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('184', '오만의 탑 84F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('185', '오만의 탑 85F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('186', '오만의 탑 86F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('187', '오만의 탑 87F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('188', '오만의 탑 88F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('189', '오만의 탑 89F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('190', '오만의 탑 90F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('191', '오만의 탑 91F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('192', '오만의 탑 92F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('193', '오만의 탑 93F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('194', '오만의 탑 94F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('195', '오만의 탑 95F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('196', '오만의 탑 96F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('197', '오만의 탑 97F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('198', '오만의 탑 98F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('199', '오만의 탑 99F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('200', '오만의 탑 100F', '32781', '32816', '101');
INSERT INTO `getback_restart` VALUES ('303', '몽환의 섬', '33976', '32936', '4');
INSERT INTO `getback_restart` VALUES ('451', '라스타바성:집회장 1F', '32744', '32818', '450');
INSERT INTO `getback_restart` VALUES ('452', '라스타바성:돌격대 훈련장 1F', '32744', '32818', '450');
INSERT INTO `getback_restart` VALUES ('453', '라스타바성:마수군왕의 집무실 1F', '32744', '32818', '450');
INSERT INTO `getback_restart` VALUES ('454', '라스타바성:야수 조교실 1F', '32744', '32818', '450');
INSERT INTO `getback_restart` VALUES ('455', '라스타바성:야수 훈련실 1F', '32744', '32818', '450');
INSERT INTO `getback_restart` VALUES ('456', '라스타바성:마수소환실 1F', '32744', '32818', '450');
INSERT INTO `getback_restart` VALUES ('460', '라스타바성:흑마법 훈련장 2F', '32667', '32863', '457');
INSERT INTO `getback_restart` VALUES ('461', '라스타바성:흑마법 연구실 2F', '32667', '32863', '457');
INSERT INTO `getback_restart` VALUES ('462', '라스타바성:마령군왕의 집무실 2F', '32667', '32863', '457');
INSERT INTO `getback_restart` VALUES ('463', '라스타바성:마령군왕의 서재 2F', '32667', '32863', '457');
INSERT INTO `getback_restart` VALUES ('464', '라스타바성:정령 소환실 2F', '32667', '32863', '457');
INSERT INTO `getback_restart` VALUES ('465', '라스타바성:정령의 생식지 2F', '32667', '32863', '457');
INSERT INTO `getback_restart` VALUES ('466', '라스타바성:암의 정령 연구실 2F', '32667', '32863', '457');
INSERT INTO `getback_restart` VALUES ('470', '라스타바성:악령의 제단 3F', '32671', '32855', '467');
INSERT INTO `getback_restart` VALUES ('471', '라스타바성:데빌 로드의 제단 3F', '32671', '32855', '467');
INSERT INTO `getback_restart` VALUES ('472', '라스타바성:용병 훈련장 3F', '32671', '32855', '467');
INSERT INTO `getback_restart` VALUES ('473', '라스타바성:명법군의 훈련장 3F', '32671', '32855', '467');
INSERT INTO `getback_restart` VALUES ('474', '라스타바성:오옴 실험실 3F', '32671', '32855', '467');
INSERT INTO `getback_restart` VALUES ('475', '라스타바성:명법군왕의 집무실 3F', '32671', '32855', '467');
INSERT INTO `getback_restart` VALUES ('476', '라스타바성:중앙 컨트롤 룸 3F', '32671', '32855', '467');
INSERT INTO `getback_restart` VALUES ('477', '라스타바성:데빌 로드의 용병실 3F', '32671', '32855', '467');
INSERT INTO `getback_restart` VALUES ('478', '라스타바성:출입 금지 에리어 3F', '32671', '32855', '467');
INSERT INTO `getback_restart` VALUES ('490', '라스타바성:지하 훈련장 B1F', '32744', '32818', '450');
INSERT INTO `getback_restart` VALUES ('491', '라스타바성:지하 통로 B1F', '32744', '32818', '450');
INSERT INTO `getback_restart` VALUES ('492', '라스타바성:암살군왕의 집무실 B1F', '32744', '32818', '450');
INSERT INTO `getback_restart` VALUES ('493', '라스타바성:지하 컨트롤 룸 B1F', '32744', '32818', '450');
INSERT INTO `getback_restart` VALUES ('494', '라스타바성:지하 처형장 B1F', '32744', '32818', '450');
INSERT INTO `getback_restart` VALUES ('495', '라스타바성:지하 결투장 B1F', '32744', '32818', '450');
INSERT INTO `getback_restart` VALUES ('496', '라스타바성:지하소굴 B1F', '32744', '32818', '450');
INSERT INTO `getback_restart` VALUES ('530', '라스타바성:그란카인의 신전/케이나의 방', '32744', '32792', '536');
INSERT INTO `getback_restart` VALUES ('531', '라스타바성:맥주 타스/바로메스/엔디아스의 방', '32744', '32792', '536');
INSERT INTO `getback_restart` VALUES ('532', '라스타바성:정원/이데아의 방', '32744', '32792', '536');
INSERT INTO `getback_restart` VALUES ('533', '라스타바성:티아메스/라미아스/바로드 의 방', '32744', '32792', '536');
INSERT INTO `getback_restart` VALUES ('534', '라스타바성:카산드라/단테스 의 방', '32744', '32792', '536');
INSERT INTO `getback_restart` VALUES ('535', '다크 에르프의 성지', '32744', '32792', '536');
INSERT INTO `getback_restart` VALUES ('550', '배의 묘지:지상층', '32844', '32694', '550');
INSERT INTO `getback_restart` VALUES ('551', '배의 묘지:대형 선내 1층', '32844', '32694', '550');
INSERT INTO `getback_restart` VALUES ('552', '배의 묘지:대형 선내 1층(수중)', '32844', '32694', '550');
INSERT INTO `getback_restart` VALUES ('554', '배의 묘지:대형 선내 2층', '32844', '32694', '550');
INSERT INTO `getback_restart` VALUES ('555', '배의 묘지:대형 선내 2층(수중)', '32844', '32694', '550');
INSERT INTO `getback_restart` VALUES ('557', '배의 묘지:선내', '32844', '32694', '550');
INSERT INTO `getback_restart` VALUES ('558', '배의 묘지:심해층', '32844', '32694', '550');
INSERT INTO `getback_restart` VALUES ('600', '욕망의 동굴 외주부', '32608', '33178', '4');
INSERT INTO `getback_restart` VALUES ('601', '욕망의 동굴 로비', '32608', '33178', '4');
INSERT INTO `getback_restart` VALUES ('608', '야히의 실험실', '34053', '32284', '4');
INSERT INTO `getback_restart` VALUES ('777', '버땅(공간 외)', '34043', '32184', '4');
INSERT INTO `getback_restart` VALUES ('5140', '도깨비 저택', '32624', '32813', '4');
INSERT INTO `getback_restart` VALUES ('5124', 'Fishing place', '32815', '32809', '5124');
INSERT INTO `getback_restart` VALUES ('5125', 'petmatch place', '32628', '32781', '4');
INSERT INTO `getback_restart` VALUES ('5131', 'petmatch place', '32628', '32781', '4');
INSERT INTO `getback_restart` VALUES ('5132', 'petmatch place', '32628', '32781', '4');
INSERT INTO `getback_restart` VALUES ('5133', 'petmatch place', '32628', '32781', '4');
INSERT INTO `getback_restart` VALUES ('5134', 'petmatch place', '32628', '32781', '4');
INSERT INTO `getback_restart` VALUES ('5', 'Talking Island Ship to Aden Mainland', '32631', '32983', '0');
INSERT INTO `getback_restart` VALUES ('6', 'Aden Mainland Ship to Talking Island', '32543', '32728', '4');
INSERT INTO `getback_restart` VALUES ('83', 'Aden Mainland Ship to Forgotten Island', '33426', '33499', '4');
INSERT INTO `getback_restart` VALUES ('84', 'Forgotten Island Ship to Aden Mainland', '32936', '33057', '70');
INSERT INTO `getback_restart` VALUES ('446', 'Ship Pirate island to Hidden dock', '32297', '33087', '440');
INSERT INTO `getback_restart` VALUES ('447', 'Ship Hidden dock to Pirate island', '32750', '32874', '445');
INSERT INTO `getback_restart` VALUES ('16384', 'Talking Island Hotel', '32599', '32931', '0');
INSERT INTO `getback_restart` VALUES ('16896', 'Talking Island Hotel', '32599', '32931', '0');
INSERT INTO `getback_restart` VALUES ('17408', 'Gludio Hotel', '32631', '32761', '4');
INSERT INTO `getback_restart` VALUES ('17920', 'Gludio Hotel', '32631', '32761', '4');
INSERT INTO `getback_restart` VALUES ('18432', 'Giran Hotel', '33437', '32790', '4');
INSERT INTO `getback_restart` VALUES ('18944', 'Giran Hotel', '33437', '32790', '4');
INSERT INTO `getback_restart` VALUES ('19456', 'Oren Hotel', '34067', '32254', '4');
INSERT INTO `getback_restart` VALUES ('19968', 'Oren Hotel', '34067', '32254', '4');
INSERT INTO `getback_restart` VALUES ('20480', 'Windawood Hotel', '32627', '33167', '4');
INSERT INTO `getback_restart` VALUES ('20992', 'Windawood Hotel', '32627', '33167', '4');
INSERT INTO `getback_restart` VALUES ('21504', 'SKT Hotel', '33115', '33379', '4');
INSERT INTO `getback_restart` VALUES ('22016', 'SKT Hotel', '33115', '33379', '4');
INSERT INTO `getback_restart` VALUES ('22528', 'Heine Hotel', '33604', '33276', '4');
INSERT INTO `getback_restart` VALUES ('23040', 'Heine Hotel', '33604', '33276', '4');
INSERT INTO `getback_restart` VALUES ('780', '테베 사막', '32614', '32735', '4');
INSERT INTO `getback_restart` VALUES ('781', '테베 피라미드', '32614', '32735', '4');
INSERT INTO `getback_restart` VALUES ('782', '테베 제단', '32614', '32735', '4');
INSERT INTO `getback_restart` VALUES ('5143', '펫레이싱', '32614', '32735', '4');
INSERT INTO `getback_restart` VALUES ('5153', '데스매치', '32614', '32735', '4');
INSERT INTO `getback_restart` VALUES ('248', '기란성 1층', '32736', '32802', '52');
INSERT INTO `getback_restart` VALUES ('249', '기란성 2층', '32736', '32802', '52');
INSERT INTO `getback_restart` VALUES ('250', '기란성 3층', '32736', '32802', '52');
INSERT INTO `getback_restart` VALUES ('251', '기란성 4층', '32736', '32802', '52');
INSERT INTO `getback_restart` VALUES ('257', '아덴성 1층', '32895', '32535', '300');
INSERT INTO `getback_restart` VALUES ('258', '아덴성 2층', '32895', '32535', '300');
INSERT INTO `getback_restart` VALUES ('259', '아덴성 3층', '32895', '32535', '300');
INSERT INTO `getback_restart` VALUES ('778', '버땅(차원 문 지상)', '32608', '33178', '4');
INSERT INTO `getback_restart` VALUES ('779', '버땅(차원 문 해저)', '32608', '33178', '4');
INSERT INTO `getback_restart` VALUES ('2', '말하는섬2층', '32665', '32790', '2');
INSERT INTO `getback_restart` VALUES ('783', '티칼사원', '32614', '32735', '4');
INSERT INTO `getback_restart` VALUES ('784', '티칼사원내부', '32614', '32735', '4');
INSERT INTO `getback_restart` VALUES ('613', '수상한마을', '34049', '32316', '4');
INSERT INTO `getback_restart` VALUES ('53', '기던1', '33437', '32790', '4');
INSERT INTO `getback_restart` VALUES ('54', '기던2', '33437', '32790', '4');
INSERT INTO `getback_restart` VALUES ('55', '기던3', '33437', '32790', '4');
INSERT INTO `getback_restart` VALUES ('56', '기던4', '33437', '32790', '4');
INSERT INTO `getback_restart` VALUES ('621', '수상한마을(리뉴얼)', '34049', '32316', '4');
