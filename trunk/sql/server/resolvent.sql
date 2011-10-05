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
-- Table structure for `resolvent`
-- ----------------------------
DROP TABLE IF EXISTS `resolvent`;
CREATE TABLE `resolvent` (
  `item_id` int(10) NOT NULL DEFAULT '0',
  `note` varchar(45) NOT NULL,
  `crystal_count` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`item_id`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of resolvent
-- ----------------------------
INSERT IGNORE INTO `resolvent` VALUES 
('1', '오크족 단검', '2'),
('200001', '오크족 단검', '2'),
('21', '오크족 검', '2'),
('2', '악운의 단검', '2'),
('200002', '악운의 단검', '2'),
('23', '넓은 검', '3'),
('26', '난쟁이족 검', '25'),
('27', '언월도', '150'),
('100027', '언월도', '150'),
('200027', '언월도', '150'),
('25', '은검', '180'),
('100025', '은검', '180'),
('32', '그라디우스', '250'),
('200032', '그라디우스', '250'),
('31', '장검', '300'),
('52', '양손검', '1800'),
('100052', '양손검', '1800'),
('200052', '양손검', '1800'),
('93', '삼지창', '2'),
('91', '오크족 창', '5'),
('95', '기병창', '6'),
('100095', '기병창', '6'),
('96', '창', '7'),
('94', '파르티잔', '50'),
('104', '포챠드', '800'),
('102', '루선해머', '1000'),
('100102', '루선해머', '1000'),
('107', '크림슨 랜스', '20000'),
('100107', '크림슨 랜스', '20000'),
('137', '아탐', '1'),
('136', '도끼', '2'),
('138', '몽둥이', '3'),
('139', '도리깨', '4'),
('140', '철퇴', '9'),
('143', '전투 도끼', '70'),
('100143', '전투 도끼', '70'),
('145', '광전사의 도끼', '100'),
('146', '모닝스타', '350'),
('100146', '모닝스타', '350'),
('142', '은도끼', '800'),
('148', '대형 도끼', '1200'),
('149', '미노타우르스 도끼', '1200'),
('151', '데몬 액스', '20000'),
('100151', '데몬 액스', '20000'),
('128', '참나무 지팡이', '30'),
('126', '마나의 지팡이', '900'),
('132', '신관의 지팡이', '10000'),
('100132', '신관의 지팡이', '10000'),
('171', '오크족 활', '3'),
('200171', '오크족활', '3'),
('172', '활', '5'),
('100172', '활', '5'),
('169', '사냥꾼 활', '500'),
('100169', '사냥꾼 활', '500'),
('20182', '장갑', '800'),
('120182', '장갑', '800'),
('20162', '가죽 장갑', '800'),
('20188', '푸른 해적 장갑', '800'),
('20177', '암령의 장갑', '10000'),
('20189', '풍령의 장갑', '10000'),
('20181', '염령의 장갑', '10000'),
('20172', '빙령의 장갑', '10000'),
('20034', '오크족 투구', '15'),
('220034', '오크족 투구', '15'),
('20043', '투구', '20'),
('120043', '투구', '20'),
('220043', '투구', '20'),
('20007', '난쟁이족 철 투구', '60'),
('20011', '마법 방어 투구', '250'),
('120011', '마법 방어 투구', '250'),
('20089', '가죽 갑옷', '15'),
('20135', '오크족 고리 갑옷', '20'),
('220135', '오크족 고리 갑옷', '20'),
('20147', '징박힌 가죽 갑옷', '30'),
('220147', '징박힌 가죽 갑옷', '30'),
('20096', '고리 갑옷', '50'),
('20136', '오크족 사슬 갑옷', '80'),
('220136', '오크족 사슬 갑옷', '80'),
('20114', '무명 로브', '100'),
('20139', '요정족 흉갑', '100'),
('20122', '비늘 갑옷', '200'),
('220122', '비늘 갑옷', '200'),
('20124', '뼈갑옷', '200'),
('20149', '청동 판금 갑옷', '1600'),
('120149', '청동 판금 갑옷', '1600'),
('20115', '미늘 갑옷', '2000'),
('220115', '미늘 갑옷', '2000'),
('20101', '띠 갑옷', '2500'),
('120101', '띠 갑옷', '2500'),
('220101', '띠 갑옷', '2500'),
('20143', '잊혀진 판금 갑옷', '3700'),
('20142', '잊혀진 비늘 갑옷', '3700'),
('20140', '잊혀진 가죽 갑옷', '3700'),
('20141', '잊혀진 로브', '3700'),
('20072', '오크족 망토', '5'),
('20052', '난쟁이족 망토', '10'),
('20056', '마법 망토', '20'),
('120056', '마법 망토', '20'),
('220056', '마법 망토', '20'),
('20205', '부츠', '200'),
('20217', '푸른 해적 부츠', '300'),
('20306', '낡은 신체의 벨트', '1500'),
('120306', '낡은 신체의 벨트', '1500'),
('20307', '낡은 영혼의 벨트', '1500'),
('120307', '낡은 영혼의 벨트', '1500'),
('20308', '낡은 정신의 벨트', '1500'),
('120308', '낡은 정신의 벨트', '1500'),
('20312', '신체의 벨트', '3000'),
('120312', '신체의 벨트', '3000'),
('20316', '영혼의 벨트', '3000'),
('120316', '영혼의 벨트', '3000'),
('20319', '정신의 벨트', '3000'),
('120319', '정신의 벨트', '3000'),
('20321', '트롤의 벨트', '3000'),
('120321', '트롤의 벨트', '3000'),
('20266', '지식의 목걸이', '1000'),
('120266', '지식의 목걸이', '1000'),
('20263', '오크 투사의 목걸이', '6500'),
('20284', '소환 조종 반지', '200000'),
('40010', '빨간 물약', '3'),
('140010', '빨간 물약', '3'),
('240010', '빨간 물약', '3'),
('40011', '주홍 물약', '25'),
('140011', '주홍 물약', '25'),
('40012', '맑은 물약', '37'),
('140012', '맑은 물약', '37'),
('40019', '농축 체력 회복제', '4'),
('40020', '농축 고급 체력 회복제', '25'),
('40021', '농축 강력 체력 회복제', '56'),
('40017', '비취 물약', '7'),
('40013', '초록 물약', '20'),
('140013', '초록 물약', '20'),
('40018', '강화 초록 물약', '150'),
('140018', '강화 초록 물약', '150'),
('40016', '지혜의 물약', '60'),
('140016', '지혜의 물약', '60'),
('40014', '용기의 물약', '80'),
('140014', '용기의 물약', '80'),
('40015', '파란 물약', '100'),
('140015', '파란 물약', '100'),
('40068', '엘븐 와퍼', '50'),
('140068', '엘븐 와퍼', '50'),
('40317', '숫돌', '15'),
('40126', '확인 주문서', '6'),
('40100', '순간이동 주문서', '7'),
('140100', '순간이동 주문서', '7'),
('40119', '저주 풀기 주문서', '11'),
('140119', '저주 풀기 주문서', '11'),
('40079', '귀환 주문서', '9'),
('40089', '부활 주문서', '150'),
('140089', '부활 주문서', '150'),
('40088', '변신 주문서', '130'),
('140088', '변신 주문서', '130'),
('40090', '빈 주문서 (레벨 1)', '10'),
('40091', '빈 주문서 (레벨 2)', '40'),
('40092', '빈 주문서 (레벨 3)', '100'),
('40093', '빈 주문서 (레벨 4)', '180'),
('40094', '빈 주문서 (레벨 5)', '500'),
('40170', '마법서 (파이어볼)', '160'),
('40171', '마법서 (인챈트 덱스터리티)', '160'),
('40172', '마법서 (웨폰 브레이크)', '160'),
('40173', '마법서 (뱀파이어릭 터치)', '160'),
('40174', '마법서 (슬로우)', '160'),
('40175', '마법서 (카운터 매직)', '160'),
('40176', '마법서 (메디테이션)', '160'),
('40177', '마법서 (어스 재일)', '160'),
('40178', '마법서 (커스: 패럴라이즈)', '180'),
('40179', '마법서 (콜 라이트닝)', '180'),
('40180', '마법서 (그레이터 힐)', '180'),
('40181', '마법서 (테이밍 몬스터)', '180'),
('40182', '마법서 (리무브 커스)', '180'),
('40183', '마법서 (콘 오브 콜드)', '180'),
('40184', '마법서 (마나 드레인)', '180'),
('40185', '마법서 (다크니스)', '180'),
('40186', '마법서 (크리에이트 좀비)', '200'),
('40187', '마법서 (인챈트 마이티)', '200'),
('40188', '마법서 (헤이스트)', '200'),
('40189', '마법서 (캔슬레이션)', '200'),
('40190', '마법서 (이럽션)', '200'),
('40191', '마법서 (선 버스트)', '200'),
('40192', '마법서 (위크니스)', '200'),
('40193', '마법서 (블레스 웨폰)', '200'),
('40210', '마법서 (라이트닝 스톰)', '280'),
('40211', '마법서 (포그 오브 슬리핑)', '280'),
('40212', '마법서 (셰이프 체인지)', '280'),
('40213', '마법서 (이뮨 투 함)', '280'),
('40214', '마법서 (매스 텔레포트)', '280'),
('40215', '마법서 (파이어 스톰)', '280'),
('40216', '마법서 (디케이 포션)', '280'),
('40217', '마법서 (카운터 디텍션)', '280'),
('40265', '흑정령의 수정 (블라인드 하이딩)', '50'),
('40266', '흑정령의 수정 (인챈트 베놈)', '50'),
('40267', '흑정령의 수정 (쉐도우 아머)', '50'),
('40268', '흑정령의 수정 (브링 스톤)', '50'),
('40269', '흑정령의 수정 (드레스 마이티)', '50'),
('40270', '흑정령의 수정 (무빙 악셀레이션)', '200'),
('40271', '흑정령의 수정 (버닝 스피릿츠)', '200'),
('40272', '흑정령의 수정 (다크 블라인드)', '200'),
('40273', '흑정령의 수정 (베놈 레지스트)', '200'),
('40274', '흑정령의 수정 (드레스 덱스터리티)', '200'),
('40275', '흑정령의 수정 (더블 브레이크)', '500'),
('40276', '흑정령의 수정 (언케니 닷지)', '500'),
('40277', '흑정령의 수정 (쉐도우 팽)', '500'),
('40278', '흑정령의 수정 (파이널 번)', '500'),
('40279', '흑정령의 수정 (드레스 이베이젼)', '500'),
('40164', '기술서 (쇼크 스턴)', '1000'),
('40007', '흑단 막대', '2'),
('40006', '소나무 막대', '2'),
('140006', '소나무 막대', '2'),
('40008', '단풍나무 막대', '2'),
('140008', '단풍나무 막대', '2'),
('40524', '검은 혈흔', '2'),
('40515', '정령의 돌', '10'),
('40044', '다이아몬드', '50'),
('40047', '에메랄드', '50'),
('40045', '루비', '50'),
('40046', '사파이어', '50'),
('40048', '고급 다이아몬드', '100'),
('40051', '고급 에메랄드', '100'),
('40049', '고급 루비', '100'),
('40050', '고급 사파이어', '100'),
('40496', '미스릴 원석', '6'),
('40408', '철괴', '2'),
('40499', '버섯포자의 즙', '2'),
('40444', '블랙 미스릴 원석', '80'),
('40443', '블랙 미스릴', '120'),
('40442', '브롭의 위액', '2'),
('40899', '강철 원석', '2'),
('40486', '화산재', '2'),
('40514', '정령의 눈물', '10'),
('40397', '키메라의 가죽(용)', '2'),
('40398', '키메라의 가죽(산양)', '2'),
('40399', '키메라의 가죽(사자)', '2'),
('40400', '키메라의 가죽(뱀)', '2'),
('40437', '딥플라워 줄기', '2'),
('40483', '플레이트웜의 껍질', '2'),
('40438', '박쥐 송곳니', '2'),
('40431', '두더지의 가죽', '3'),
('40434', '딜로의 꼬리', '3'),
('40424', '늑대 가죽', '5'),
('40490', '흑정령석', '8'),
('40459', '스콜피온 껍질', '10'),
('40472', '켈베로스 털', '120'),
('40419', '거대무리안 거미줄', '150'),
('40512', '오염된 엔트의 줄기', '2'),
('40510', '오염된 엔트의 껍질', '3'),
('40511', '오염된 엔트의 열매', '105'),
('40986', '시버인의 앞니', '50'),
('40978', '시버인의 꼬리 : 흙', '100'),
('40979', '시버인의 꼬리 : 물', '100'),
('40980', '시버인의 꼬리 : 불', '100'),
('40981', '시버인의 꼬리 : 공기', '100'),
('40982', '시버인의 가죽 : 흙', '200'),
('40983', '시버인의 가죽 : 물', '200'),
('40984', '시버인의 가죽 : 불', '200'),
('40985', '시버인의 가죽 : 공기', '200'),
('40407', '뼈조각', '2'),
('40999', '다크엘프 병사의 배지', '2'),
('41343', '파푸리온의 혈흔', '2'),
('40612', '아투바 오크 마법서', '2'),
('40610', '네루가 오크 마법서', '2'),
('40611', '두다 마라 오크 마법서', '2'),
('40609', '간디 오크 마법서', '2'),
('40432', '디아드 주문서 조각', '5'),
('41038', '항해일지 1장', '10'),
('41039', '항해일지 2장', '10'),
('41040', '항해일지 3장', '10'),
('41041', '항해일지 4장', '10'),
('41042', '항해일지 5장', '10'),
('41043', '항해일지 6장', '10'),
('41044', '항해일지 7장', '10'),
('41045', '항해일지 8장', '10'),
('41046', '항해일지 9장', '10'),
('41047', '항해일지 10장', '10'),
('40329', '원주민 토템', '10'),
('140329', '원주민 토템', '10'),
('40041', '인어의 비늘', '20'),
('41342', '메두사의 피', '200'),
('41206', '날이 빠진 무기', '600'),
('40678', '영혼석 파편', '1000'),
('40718', '혈석 파편', '1000'),
('20281', '변신 조종 반지', '200000'),
('120281', '변신 조종 반지', '200000'),
('20288', '순간이동 조종 반지', '400000'),
('120288', '순간이동 조종 반지', '400000'),
('20016', '맘보 모자', '10000'),
('120016', '맘보 모자', '10000'),
('40087', '무기 마법 주문서', '7500'),
('140087', '무기 마법 주문서', '7500'),
('240087', '무기 마법 주문서', '7500'),
('20154', '판금 갑옷', '3700'),
('120154', '판금 갑옷', '3700'),
('40074', '갑옷 마법 주문서', '3100'),
('140074', '갑옷 마법 주문서', '3100'),
('240074', '갑옷 마법 주문서', '3100'),
('40330', '무한의 화살통', '3000'),
('20023', '바람의 투구', '2200'),
('20063', '보호 망토', '2000'),
('20132', '어둠의 로브', '2000'),
('20295', '저주받은 사파이어 반지', '2000'),
('20293', '저주받은 다이아몬드 반지', '2000'),
('20264', '완력의 목걸이', '1000'),
('120264', '완력의 목걸이', '1000'),
('20267', '지혜의 목걸이', '1000'),
('120267', '지혜의 목걸이', '1000'),
('20032', '어둠의 머리띠', '1000'),
('187', '라스타바드 크로스 보우', '1000'),
('20412', '고뇌의 목걸이', '1000'),
('41243', '라스타바드 보급품 자루', '1000'),
('40221', '마법서 (매스 슬로우)', '280'),
('20304', '화령의 반지', '250'),
('120304', '화령의 반지', '250'),
('20285', '수령의 반지', '250'),
('120285', '수령의 반지', '250'),
('20300', '지령의 반지', '250'),
('120300', '지령의 반지', '250'),
('20302', '풍령의 반지', '250'),
('120302', '풍령의 반지', '250'),
('41151', '아쿠아 프로텍트', '200'),
('40055', '최고급 에메랄드', '107'),
('40053', '최고급 루비', '115'),
('40054', '최고급 사파이어', '100'),
('20260', '아이리스의 목걸이', '3000'),
('40458', '빛나는 비늘', '2'),
('125', '마력의 지팡이', '900'),
('98', '넓은 창', '15'),
('100098', '넓은 창', '15'),
('20242', '큰 방패', '150'),
('120242', '큰 방패', '150'),
('20237', '우럭하이 방패', '9'),
('220237', '우럭하이 방패', '9'),
('20239', '작은 방패', '6'),
('40001', '등잔', '1'),
('40002', '랜턴', '7'),
('40232', '정령의 수정 (레지스트 매직)', '100'),
('40233', '정령의 수정 (바디 투 마인드)', '100'),
('40234', '정령의 수정 (텔레포트 투 마더)', '100'),
('40235', '정령의 수정 (클리어 마인드)', '200'),
('40236', '정령의 수정 (레지스트 엘리멘트)', '200'),
('40237', '정령의 수정 (리턴 투 네이쳐)', '300'),
('40238', '정령의 수정 (블러드 투 소울)', '300'),
('40239', '정령의 수정 (프로텍션 프롬 엘리멘트)', '300'),
('40240', '정령의 수정 (트리플 애로우)', '300'),
('40241', '정령의 수정 (엘리멘탈 폴다운)', '500'),
('40242', '정령의 수정 (이레이즈 매직)', '500'),
('40243', '정령의 수정 (서먼 레서 엘리멘탈)', '500'),
('40244', '정령의 수정 (에어리어 오브 사일런스)', '700'),
('40245', '정령의 수정 (서먼 그레이터 엘리멘탈)', '700'),
('40246', '정령의 수정 (카운터 미러)', '700'),
('40247', '정령의 수정 (어스 스킨)', '300'),
('40248', '정령의 수정 (인탱글)', '300'),
('40249', '정령의 수정 (어스 바인드)', '500'),
('40250', '정령의 수정 (블레스 오브 어스)', '500'),
('40251', '정령의 수정 (아이언 스킨)', '700'),
('40252', '정령의 수정 (엑조틱 바이탈라이즈)', '700'),
('40253', '정령의 수정 (워터 라이프)', '300'),
('40254', '정령의 수정 (네이쳐스 터치)', '500'),
('40255', '정령의 수정 (네이쳐스 블레싱)', '700'),
('40256', '정령의 수정 (파이어 웨폰)', '300'),
('40257', '정령의 수정 (블레스 오브 파이어)', '500'),
('40258', '정령의 수정 (버닝 웨폰)', '700'),
('40259', '정령의 수정 (엘리멘탈 파이어)', '700'),
('40260', '정령의 수정 (윈드 샷)', '300'),
('40261', '정령의 수정 (윈드 워크)', '300'),
('40262', '정령의 수정 (아이 오브 스톰)', '500'),
('40263', '정령의 수정 (스톰 샷)', '700'),
('40264', '정령의 수정 (윈드 셰클)', '700'),
('41149', '정령의 수정 (소울 오브 프레임)', '700'),
('41150', '정령의 수정 (어디셔널 파이어)', '700'),
('41152', '정령의 수정 (폴루트 워터)', '700'),
('41153', '정령의 수정 (스트라이커 게일)', '700'),
('20210', '어둠의 부츠', '10000'),
('20070', '어둠의 망토', '20000'),
('210000', '용기사의 서판 (드래곤 스킨)', '20'),
('210001', '용기사의 서판 (버닝 슬래쉬)', '20'),
('210002', '용기사의 서판 (가드 브레이크)', '20'),
('210003', '용기사의 서판 (마그마 브레스)', '20'),
('210004', '용기사의 서판 (각성:안타라스)', '20'),
('210005', '용기사의 서판 (블러드러스트)', '20'),
('210006', '용기사의 서판 (포우 슬레이어)', '20'),
('210007', '용기사의 서판 (피어)', '20'),
('210008', '용기사의 서판 (쇼크 스킨)', '20'),
('210009', '용기사의 서판 (각성:파푸리온)', '20'),
('210010', '용기사의 서판 (모탈 바디)', '20'),
('210011', '용기사의 서판 (썬더 그랩)', '20'),
('210012', '용기사의 서판 (호러 오브 데스)', '20'),
('210013', '용기사의 서판 (프리징 브레스)', '20'),
('210014', '용기사의 서판 (각성:발라카스)', '20'),
('210015', '기억의 수정 (미러 이미지)', '20'),
('210016', '기억의 수정 (컨퓨전)', '20'),
('210017', '기억의 수정 (스매쉬)', '20'),
('210018', '기억의 수정 (일루션:오거)', '20'),
('210019', '기억의 수정 (큐브:이그니션)', '20'),
('210020', '기억의 수정 (컨센트레이션)', '20'),
('210021', '기억의 수정 (마인드 브레이크)', '20'),
('210022', '기억의 수정 (본 브레이크)', '20'),
('210023', '기억의 수정 (일루션:리치)', '20'),
('210024', '기억의 수정 (큐브:퀘이크)', '20'),
('210025', '기억의 수정 (페이션스)', '20'),
('210026', '기억의 수정 (판타즘)', '20'),
('210027', '기억의 수정 (암 브레이커)', '20'),
('210028', '기억의 수정 (일루션:다이아몬드 골렘)', '20'),
('210029', '기억의 수정 (큐브:쇼크)', '20'),
('210030', '기억의 수정 (인사이트)', '20'),
('210031', '기억의 수정 (패닉)', '20'),
('210032', '기억의 수정 (조이 오브 페인)', '20'),
('210033', '기억의 수정 (일루션:아바타)', '20'),
('210034', '기억의 수정 (큐브:밸런스)', '20'),
('40163', '금빛 열쇠', '1'),
('40538', '구울의 손톱', '2'),
('40539', '구울의 이빨', '2'),
('40555', '비밀방 열쇠', '2'),
('40564', '생명의 비밀', '2'),
('40601', '터틀 드래곤의 껍질', '2'),
('40605', '해골바가지', '2'),
('41207', '선원의 유해', '3'),
('40435', '딥 플라워의 꽃 봉오리', '3'),
('40436', '딥 플라워의 뿌리', '3'),
('40453', '비스트 서머너의 채찍', '3'),
('40484', '플레이트웜의 독주머니', '3'),
('40485', '플레이트웜의 송곳니', '3'),
('40516', '큰 녹색 수정', '10'),
('40517', '큰 붉은 수정', '10'),
('40518', '큰 푸른 수정', '10'),
('40471', '정령의 파편', '10'),
('40104', '오만의 탑 11층 이동 주문서', '20'),
('40105', '오만의 탑 21층 이동 주문서', '20'),
('40106', '오만의 탑 31층 이동 주문서', '20'),
('40107', '오만의 탑 41층 이동 주문서', '20'),
('40108', '오만의 탑 51층 이동 주문서', '20'),
('40109', '오만의 탑 61층 이동 주문서', '20'),
('40110', '오만의 탑 71층 이동 주문서', '20'),
('40111', '오만의 탑 81층 이동 주문서', '20'),
('40112', '오만의 탑 91층 이동 주문서', '20'),
('40433', '딜로 발톱', '20'),
('40614', '광물 수거 문서', '30'),
('41154', '어둠의 비늘', '50'),
('41221', '다크엘프 주머니', '100'),
('40304', '마프르의 유산', '100'),
('40305', '파아그리오의 유산', '100'),
('40306', '에바의 유산', '100'),
('40307', '사이하의 유산', '100'),
('40460', '아시타지오의 재', '150'),
('153', '강철 크로우', '200'),
('40470', '정령 조각', '300'),
('40430', '대공동의 수정', '300'),
('64', '대검', '8000'),
('100064', '대검', '8000'),
('20013', '마법의 투구 : 신속', '2200'),
('20014', '마법의 투구 : 치유', '1500'),
('20015', '마법의 투구 : 힘', '1800'),
('41', '일본도', '2000'),
('100041', '일본도', '2000'),
('20110', '마법 방어 사슬 갑옷', '800'),
('40022', '고대의 체력 회복제', '5'),
('40023', '고대의 고급 체력 회복제', '31'),
('40024', '고대의 강력 체력 회복제', '62'),
('210036', '유그드라 열매', '50'),
('29', '은장검', '1300'),
('37', '다마스커스 검', '500'),
('100029', '은장검', '1300'),
('100037', '다마스커스 검', '500'),
('103', '미늘창', '200'),
('100103', '미늘창', '200'),
('129', '마법사의 지팡이', '375'),
('20125', '사슬 갑옷', '600'),
('220125', '사슬 갑옷', '600'),
('20231', '사각 방패', '700'),
('20006', '기사의 면갑', '500'),
('40406', '고급피혁', '4'),
('20229', '반사 방패', '2000'),
('20289', '심연의 반지', '10000'),
('120289', '심연의 반지', '10000'),
('20067', '신관의 망토', '500'),
('20303', '항마의 반지', '200'),
('20309', '빛나는 신체의 벨트', '1000'),
('120309', '빛나는 신체의 벨트', '1000'),
('20310', '빛나는 영혼의 벨트', '1000'),
('120310', '빛나는 영혼의 벨트', '1000'),
('20311', '빛나는 정신의 벨트', '1000'),
('120311', '빛나는 정신의 벨트', '1000'),
('20256', '민첩의 목걸이', '1000'),
('120256', '민첩의 목걸이', '1000'),
('20313', '어둠의 벨트', '1000'),
('20317', '오우거의 벨트', '3000'),
('120317', '오우거의 벨트', '3000'),
('20030', '신관의 투구', '350'),
('20176', '신관의 장갑', '2640'),
('20175', '수정 장갑', '800'),
('20233', '신관의 마력서', '5600'),
('20208', '신관의 부츠', '500'),
('20129', '신관의 로브', '1500'),
('20113', '무관의 갑옷', '1500'),
('20058', '무관의 망토', '500'),
('20228', '무관의 방패', '3000'),
('20201', '무관의 부츠', '500'),
('20168', '무관의 장갑', '2640'),
('20020', '무관의 투구', '350'),
('220154', '판금 갑옷', '2200'),
('20294', '저주받은 루비 반지', '2000'),
('20296', '저주받은 에메랄드 반지', '2000'),
('120254', '매력의 목걸이', '1000'),
('120268', '체력의 목걸이', '1000'),
('20254', '매력의 목걸이', '1000'),
('20268', '체력의 목걸이', '1000'),
('20244', '낡은 매력의 목걸이', '500'),
('20245', '낡은 민첩의 목걸이', '500'),
('20246', '낡은 완력의 목걸이', '500'),
('20247', '낡은 지식의 목걸이', '500'),
('20248', '낡은 지혜의 목걸이', '500'),
('20249', '낡은 체력의 목걸이', '500'),
('120244', '낡은 매력의 목걸이', '500'),
('120245', '낡은 민첩의 목걸이', '500'),
('120246', '낡은 완력의 목걸이', '500'),
('120247', '낡은 지식의 목걸이', '500'),
('120248', '낡은 지혜의 목걸이', '500'),
('120249', '낡은 체력의 목걸이', '500'),
('20213', '짧은 부츠', '30'),
('40675', '어둠의 광석', '75'),
('40676', '어둠의 숨결', '75'),
('69', '구리빛 이도류', '80'),
('20060', '바다의 망토', '80'),
('40427', '다크엘프 주머니', '100'),
('40220', '마법서 (그레이트 리절렉션)', '280'),
('40222', '마법서 (디스인티그레이트)', '280'),
('40219', '마법서 (미티어 스트라이크)', '280'),
('40223', '마법서 (앱솔루트 배리어)', '280'),
('40224', '마법서 (어드밴스 스피릿)', '280'),
('40218', '마법서 (크리에이트 매지컬 웨폰)', '280'),
('40225', '마법서 (프리징 블리자드)', '280'),
('120115', '미늘 갑옷', '2000'),
('20166', '데스나이트의 장갑', '30000'),
('200041', '일본도', '2000'),
('40165', '리덕션아머', '1000'),
('40200', '마법서(버서커스)', '220'),
('20199', '라스타바드 부츠', '100');
