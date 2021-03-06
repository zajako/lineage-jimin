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
-- Table structure for `mobgroup`
-- ----------------------------
DROP TABLE IF EXISTS `mobgroup`;
CREATE TABLE `mobgroup` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `note` varchar(255) NOT NULL DEFAULT '',
  `remove_group_if_leader_die` int(10) unsigned NOT NULL DEFAULT '0',
  `leader_id` int(10) unsigned NOT NULL DEFAULT '0',
  `minion1_id` int(10) unsigned NOT NULL DEFAULT '0',
  `minion1_count` int(10) unsigned NOT NULL DEFAULT '0',
  `minion2_id` int(10) unsigned NOT NULL DEFAULT '0',
  `minion2_count` int(10) unsigned NOT NULL DEFAULT '0',
  `minion3_id` int(10) unsigned NOT NULL DEFAULT '0',
  `minion3_count` int(10) unsigned NOT NULL DEFAULT '0',
  `minion4_id` int(10) unsigned NOT NULL DEFAULT '0',
  `minion4_count` int(10) unsigned NOT NULL DEFAULT '0',
  `minion5_id` int(10) unsigned NOT NULL DEFAULT '0',
  `minion5_count` int(10) unsigned NOT NULL DEFAULT '0',
  `minion6_id` int(10) unsigned NOT NULL DEFAULT '0',
  `minion6_count` int(10) unsigned NOT NULL DEFAULT '0',
  `minion7_id` int(10) unsigned NOT NULL DEFAULT '0',
  `minion7_count` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=91 DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of mobgroup
-- ----------------------------
INSERT IGNORE INTO `mobgroup` VALUES ('1', '블랙 나이트 -수색대-(8)', '0', '81066', '81066', '7', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('2', '엘 모어 좀비 제너럴+엘 모어 좀비 솔저(2)+엘 모어 좀비 위저드(2)', '0', '45248', '45216', '2', '45224', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('3', '엘 모어 좀비 제너럴+엘 모어 좀비 솔저+엘 모어 좀비 위저드(2)', '0', '45248', '45216', '1', '45224', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('4', '엘 모어 좀비 제너럴+엘 모어 좀비 솔저(2)+엘 모어 좀비 위저드', '0', '45248', '45216', '2', '45224', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('5', '고블린(3)', '0', '45008', '45008', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('6', '호브고브린+고블린(3)', '0', '45140', '45008', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('7', '에르다+후로팅아이(2)', '0', '45215', '45068', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('8', '오크 fighter+오크(2)+오크아챠(2)', '0', '45082', '45017', '2', '45019', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('9', '파이어 에그(4)', '0', '45206', '45206', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('10', '가스트로드+가스트(3)', '0', '45346', '45213', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('11', '좀비(16)', '0', '45065', '45065', '15', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('12', 'mermaid+마만(3)', '0', '45154', '45940', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('13', '카트+블랙 나이트(8)', '1', '45600', '45165', '8', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('14', '앙금 파+멜키 올+바르타자르+세마 ', '0', '45488', '45497', '1', '45473', '1', '45464', '1', '0', '0', '0', '0', '0', '0', '0', '0'),
('15', '네크로만서+굴(4)+후로팅아이(2)', '1', '45456', '45157', '4', '45068', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('16', '브락크에르다+스파르트이(2)', '1', '45545', '45161', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('17', '드레이크(2)', '0', '45529', '45529', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('18', '켄라우헬+케레니스', '0', '45680', '45678', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('19', '바란카+라이아', '0', '45651', '45677', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('20', '맘보 래빗+맘보 래빗의 부하(3)', '1', '45534', '45366', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('21', '맘보 래빗+맘보 래빗의 부하(3)', '1', '45535', '45366', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('22', '심연의 주+대지의 송곳니+빙하의 송곳니+화염의 아+풍의 송곳니', '0', '45646', '45416', '1', '45419', '1', '45424', '1', '45418', '1', '0', '0', '0', '0', '0', '0'),
('23', '뮤탄트드렛드스파이다(3)', '0', '45348', '45348', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('24', '메듀사(3)', '0', '45380', '45380', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('25', '키메라(3)', '0', '45407', '45407', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('26', '코카트리스(3)', '0', '81173', '81173', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('27', '사큐바스(3)', '0', '45394', '45394', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('28', '다이어 울프(3)', '0', '45409', '45409', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('29', '이비르게이자(3)', '0', '45406', '45406', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('30', '댄싱 소도(3)', '0', '45386', '45386', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('31', '악몽(3)', '0', '45440', '45440', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('32', '공포 파이어 에그(3)', '0', '45384', '45384', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('33', '호라케르베로스(3)', '0', '45471', '45471', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('34', '렛서데이몬(3)', '0', '45481', '45481', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('35', '이후리트(3)', '0', '45515', '45515', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('36', '젯드리스케르톤파이크(3)', '0', '45403', '45403', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('37', '젯드리그르(3)', '0', '45454', '45454', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('38', '데드 리스 펄 토이(3)', '0', '45455', '45455', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('39', '젯드리스케르톤마크스만(3)', '0', '45494', '45494', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('40', '카오스 엘 모어 제너럴(3)', '0', '45541', '45541', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('41', '불길의 정령 케르베로스(3)', '0', '45520', '45520', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('42', '불길의 아시타지오(3)', '0', '45572', '45572', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('43', '다크바닝아챠(3)', '0', '45532', '45532', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('44', '파트제니스크인(3)', '0', '45581', '45581', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('45', '마키스반파이아(3)', '0', '45604', '45604', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('46', '피아존비로드(3)', '0', '45589', '45589', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('47', '불의 대정령(2)', '0', '45622', '45622', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('48', '물의 대정령(2)', '0', '45620', '45620', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('49', '슈노바+헤비리자드만(2)', '1', '45492', '81174', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('50', '블랙 나이트(8)', '0', '45165', '45165', '7', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('51', '백작 친위대장+가이드 독(2)', '1', '46024', '46019', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('52', '순찰병(욕망)1-3', '0', '46082', '46083', '1', '46084', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('53', '순찰병(욕망)4-6', '0', '46085', '46086', '1', '46087', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('54', '순찰병(욕망)7-9', '0', '46088', '46089', '1', '46090', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('55', '순찰병(그림자의 신전)1-3', '0', '46097', '46098', '1', '46099', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('56', '순찰병(그림자의 신전)4-6', '0', '46100', '46101', '1', '46102', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('57', '순찰병(그림자의 신전)6-7', '0', '46103', '46104', '1', '46105', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('58', '아비스싱+아비스그르(2)', '0', '45539', '45501', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('59', '아비스싱+아비스아챠(2)', '0', '45539', '45502', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('60', '아비스타트르드라곤+아비 스크러브 맨(2)', '0', '45422', '45297', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('61', '아비스게이자+아비스리자드만(3)', '0', '45423', '45374', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('62', '쟈이안트그레이브가디안+그레이브가디안나이트+그레이브가디안메이지+그레이브가디안 ', '0', '45603', '45537', '1', '45530', '1', '45498', '1', '0', '0', '0', '0', '0', '0', '0', '0'),
('63', '얼음여왕근위병(창)-3', '0', '46135', '46135', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('64', '얼음여왕근위병(활)-3', '0', '46138', '46138', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('65', '얼음여왕근위병(창)-3', '0', '46139', '46139', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('66', '얼음여왕근위병(활)-3', '0', '46137', '46137', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('67', '얼음여왕+얼음시녀(3)', '0', '46141', '46140', '3', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('69', '라스타바드 조련사+블랙타이거(2)', '0', '45448', '45836', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('70', '발록의 분신+데몬+베레스+바포메트+레서데몬+서큐버스퀸+사큐버스	', '0', '45315', '45647', '1', '45579', '1', '45569', '1', '45482', '1', '45450', '1', '45391', '1', '0', '0'),
('71', '코라프트프리스트(5)', '0', '45570', '45571', '1', '45582', '1', '45587', '1', '45605', '1', '0', '0', '0', '0', '0', '0'),
('80', '해츨링(남)+난쟁이족일꾼+난쟁이족보초병', '0', '4038006', '4038008', '1', '4038009', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('81', '해츨링(여)+난쟁이족일꾼+난쟁이족보초병', '0', '4038007', '4038008', '1', '4038009', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('82', '난쟁이족주술사(1)+난쟁이족장군(2)+유니드래곤(땅2)', '0', '4038013', '4038012', '2', '4038014', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('83', '난쟁이족주술사(1)+난쟁이족장군(2)+유니드래곤(불2)', '0', '4038013', '4038012', '2', '4038015', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('84', '난쟁이족주술사(1)+난쟁이족장군(2)+유니드래곤(물2)', '0', '4038013', '4038012', '2', '4038016', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('85', '난쟁이족주술사(1)+난쟁이족장군(2)+유니드래곤(바람2)', '0', '4038013', '4038012', '2', '4038017', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('86', '난쟁이족주술사(1)+난쟁이족장군(2)+테라드래곤(땅2)', '0', '4038013', '4038012', '2', '4038018', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('87', '난쟁이족주술사(1)+난쟁이족장군(2)+테라드래곤(불2)', '0', '4038013', '4038012', '2', '4038019', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('88', '난쟁이족주술사(1)+난쟁이족장군(2)+테라드래곤(물2)', '0', '4038013', '4038012', '2', '4038020', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('89', '난쟁이족주술사(1)+난쟁이족장군(2)+테라드래곤(바람2)', '0', '4038013', '4038012', '2', '4038021', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'),
('90', '난쟁이족주술사(1)+난쟁이족장군(2)+스컬드래곤(2)', '0', '4038013', '4038012', '2', '4038022', '2', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0');
