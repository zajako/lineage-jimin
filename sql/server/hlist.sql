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
-- Table structure for `hlist`
-- ----------------------------
DROP TABLE IF EXISTS `hlist`;
CREATE TABLE `hlist` (
  `no` int(10) NOT NULL AUTO_INCREMENT,
  `account` varchar(45) DEFAULT '',
  `poplin` varchar(45) NOT NULL DEFAULT '',
  `poppw` varchar(45) DEFAULT '',
  PRIMARY KEY (`no`,`poplin`)
) ENGINE=InnoDB AUTO_INCREMENT=486 DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of hlist
-- ----------------------------
INSERT IGNORE INTO `hlist` VALUES ('1', 'lambor13', 'lambor11', 'flslwl1'),
('4', 'rladbsghksz1', 'dbsghks123', 'dbsghks123'),
('6', 'qkrwjdghk', 'meca1012', 'osok1012'),
('7', 'wertzzz', 'wertzzz', '46851352'),
('8', 'dydrkfl', 'ifreely', '292513'),
('9', 'dydrkfl', 'ghdnjs9012', '901202'),
('10', 'dkswlgp1', 'cownsgml', '0000'),
('11', 'junsheart', 'junsheart', '8728'),
('12', 'lsego1ida', 'lsego1ida', 'lsego1'),
('13', 'dydrkfl', 'battletop', 'eogml4552'),
('15', 'dydrkfl', 'shellwedan', 'rlarlxo'),
('16', 'dydrkfl', 'ghdnjs9090', '901202'),
('17', 'dydrkfl', 'fafzg', 'alska'),
('19', 'lambor13', 'lambor12', 'flslwl2'),
('20', 'ljy8727', 'ssomm1318', 'qldtl2623'),
('21', 'asian666', 'asian666', '01310511'),
('22', 'gu999', 'gu999', 'tlatm38'),
('24', 'yongki88', 'sucki12', '123123'),
('25', 'yongki88', 'yongki88', '123123'),
('26', 'yongki88', 'yongki00', '123123'),
('27', 'yongki88', 'yongki01', '123123'),
('28', 'yongki88', 'yongki02', '123123'),
('32', 'kage', 'dudrms18', '7817317'),
('42', 'jwlee0127', 'jwlee0127', 'dlwodnjs'),
('43', 'jwlee0127', 'shdudtns13', 'dlwodnjs'),
('44', 'jwlee0127', 'dlwodnjs88', 'dlwodnjs'),
('45', 'jwlee0127', 'charisma88', 'dlwodnjs'),
('46', 'jwlee0127', 'pc024', 'skdltn10'),
('47', 'jwlee0127', 'shdudtns14', 'dlwodnjs'),
('48', 'jwlee0127', 'dlwodnjs87', 'dlwodnjs'),
('49', 'dydrkfl', 'battletopa', 'ss8796'),
('50', 'dydrkfl', 'battletopb', 'ss8796'),
('51', 'dydrkfl', 'battletopc', 'ss8796'),
('54', 'kwon12', 'diswltb', '199144'),
('55', 'dlskdud', 'sosgood', 'rldns87'),
('56', 'lambor13', 'lambor13', 'flslwl3'),
('68', 'rlarudals', 'rufjrufj', 'rlarudals'),
('69', 'rlarudals', 'rufjrufjru', 'rlarudals'),
('71', 'rlarudals', 'rufjrufj65', 'rlarudals'),
('73', 'dydrkfl', 'battletop1', 'ss8796'),
('74', 'dydrkfl', 'eros6664', 'alsl1003'),
('76', 'wertzzz', 'wertkkk', '46851352'),
('79', 'babo100430', 'babo100430', '13201320'),
('80', 'fhepajpim', 'jmhe2', '9514'),
('84', 'dydrkfl', 'sinkum123', 'dlwjddk1'),
('89', 'answjddnr', 'erocia', 'mun1611'),
('90', 'darkhunt12', 'darkhunt12', '861204'),
('91', 'tmznptnl', 'tmznjtnl', 'wjdgns86'),
('92', 'wnwhddns', 'jwju', '12341234'),
('93', 'birth0419', 'ymhom', 'alsgh2022'),
('94', 'avav1', 'artlive2', '1111aa'),
('95', 'avav1', 'wj7674', '1111aa'),
('96', 'avav1', 'ms7674', '1111aa'),
('97', 'genius', 'genius2647', '26472647'),
('98', 'dydrkfl', 'pwe5258', '5105258'),
('99', 'nnet0331', 'nnet0331', '1330'),
('100', 'zootyguy1', 'zootyguy1', 'gur34322'),
('101', 'dlqjsdp', 'client1509', 'client1509'),
('103', '535353', 'winwunseok', 'goqudeo'),
('104', 'dpdp7', 'dpdp7', '1622316'),
('106', 'gunhwa1125', 'gunhwa1125', 'rjskrjsk7'),
('113', 'hs9000', 'yabin2000', 'tkfkdgo1'),
('114', 'kimil17', 'yoong102', 'yoo102'),
('117', 'sckkj', 'sckkj', 'wjdqudrnr2'),
('119', 'pamikw', 'pamikw', 'tlsrlfn1'),
('130', 'blueboy03', 'thddudtjq1', 'dpxpahs'),
('137', 'tpdud697', 'kyw697', '697wlwhstm'),
('149', 'guddnr', 'minah6', 'whdghks12'),
('158', 'asmk', 'kjmkorea', 'mko123'),
('159', 'akmk', 'kjmkorea12', 'mko123'),
('169', 'azim11', 'dhwlalss', '34223758'),
('171', 'kiuoonlove', 'kiuoonlove', 'rldbs1'),
('186', 'rannoo', 'dkfjrhs3', '26436351'),
('187', 'mungchunge', 'luckysun88', '01191548816'),
('188', 'mungchunge', 'luckysun12', '01191548816'),
('189', 'whitegod', 'hyungssi22', 'gudskatlr2'),
('190', 'whitegod', 'whitegod22', 'gudskatlr2'),
('197', 'ppoppo', 'tnqls1214', '3141214'),
('210', 'dlskdud', 'sosgoodgg', 'rldns87'),
('212', 'zieotn', 'this1803', '1803this'),
('213', 'zieotn', 'this1804', 'this1803'),
('214', 'zieotn', 'ydk1241', '13652404'),
('215', 'rlaguswls', 'rlaguswlsh', 'asdf00'),
('217', 'qkqh1ek', 'qkqh1ek', 'qkqh0ek'),
('218', 'qkqh1ek', 'hiwjdwodbs', 'hi1234'),
('219', 'rlatndud', 'jed11', 'hssy8490'),
('220', 'rlatndud', 'cky1135', 'ruddydrbghk1'),
('221', 'rlatndud', 'ckdudwh', 'hssy8490'),
('222', 'rlatndud', 'gigafini', '6176152'),
('223', 'zzamilove', 'zzamilove', 'tkdlskan'),
('224', 'dlehdwn', 'dlehdwn123', '890710'),
('225', 'dlehdwn', 'dlehdwn24', '890710'),
('226', 'desperateboy', 'iniceboy', 'isnice'),
('227', 'hyg789', 'rusiper0', '1995528'),
('228', 'dydrltk', 'eorma01', 'd14071'),
('229', 'ghdwoska', 'zaenami', 'zaenam'),
('232', 'qkdtjsska', 'psn001', '04050405'),
('233', 'maksky', 'maksky', '15481592'),
('234', 'maksky', 'ygioop', '15481592'),
('235', 'maksky', 'mak12', '15481592'),
('236', 'ehrbxo', 'dkt5536', 'dowktozl'),
('241', 'swc7531', 'keiloss', '6137keiloss'),
('242', 'swc7531', 'thdrnlsu', '6137ljw'),
('243', 'swc7531', 'dlvlfwns', '6137ljw'),
('244', 'swc7531', 'dhlgkfajsl', '6137ljw'),
('246', 'wertzzz', 'wertddd', '46851352'),
('247', 'wertzzz', 'wertxxx', '46851352'),
('248', 'wjsurdltmf01', 'lim1631', '1006522'),
('250', 'dlwldnd21', 'dlwldnd', 'sksskdi21'),
('251', 'hmhdark10', 'monmn', 'ghd861031'),
('252', 'hmhdark10', 'monmn1', 'ghd861031'),
('253', 'hmhdark10', 'monmn2', 'ghd861031'),
('254', 'endless21c', 'endless21c', 'tkdlek'),
('255', 'endless21c', 'endless22c', 'tkdlek'),
('256', 'endless21c', 'dmsslhj', 'tkdlek'),
('257', 'hyongtwo', 'joewoo88', '3103'),
('261', 'babylus', 'babylus', '5599486'),
('262', 'babylus', 'babylus0', '5599486'),
('263', 'rlatjdfo5530', 'rlatjdfo70', 'rlatjdfo'),
('264', 'rlatjdfo5530', 'rlatjdfo55', 'rlatjdfo'),
('265', 'rlatjdfo5530', 'rlatjdfo17', 'rlatjdfo'),
('266', 'rlatjdfo5530', 'rlatjdfo30', 'rlatjdfo'),
('267', 'rhdtlfdl5588', 'vempire84', 'dbswls01'),
('268', 'ajtwoddl3', 'Wkrsnsaka0', 'vkqfls'),
('269', 'ajtwoddl3', 'Wkrsnsaka1', 'vkqfls'),
('270', 'ajtwoddl3', 'Wkrsnsaka3', '1234rladydtn'),
('274', 'akendls2', 'akendls100', 'l4one2'),
('275', 'rkdgns65', 'qorkdgns', 'qo1103'),
('276', 'park9977', 'psm1111', '7480031a'),
('277', 'rlarudals', 'rufjrufj56', 'rlarudals'),
('283', 'enjoytogether', 'dlsrndlsrn', '77897789'),
('284', 'enjoytogether', 'dlsrnye', '77897789'),
('285', 'enjoytogether', 'dlsrnnara', '77897789'),
('286', 'quddnr0486', 'quddnr', 'gltn77'),
('288', 'vov1998', 'vov1998', 'vov1768'),
('289', 'rmsel5', 'rmsel5', '1540312'),
('290', 'rmsel5', 'rmsel4', 'rmsel5'),
('291', 'rmsel5', 'rmsel2', '1540312'),
('292', 'rmsel5', 'lcm771', 'dlckdals1'),
('293', 'rmsel5', 'rmsel419', '246212'),
('294', 'ehdwlsgusdk', 'changedj', '12345678'),
('295', 'ehdwlsgusdk', 'changedjj', '12345678'),
('296', 'ehdwlsgusdk', 'changedjjj', '12345678'),
('297', 'ehdwlsgusdk', 'love4udj', '12345678'),
('298', 'rlaehgud82', 'rlaehgud82', 'ehgud82'),
('300', 'idh04', 'idh777', 'tmfehd12'),
('301', 'idh04', 'idh7777', 'tmfehd12'),
('302', 'idh04', 'idh77777', 'tmfehd12'),
('303', 'idh04', 'idh777777', 'tmfehd12'),
('304', 'wlsxor', 'jjt1016', '10161004'),
('305', 'yhee00', 'yhee00', 'dydgml'),
('306', 'yhee00', 'yhee12', 'dydgml'),
('307', 'yhee00', 'yhee5521', 'dydgml'),
('308', 'sjparkjh', 'sjparkjh', 'xhakxh'),
('309', 'rltkrltk', 'xoghks1228', '156487'),
('310', 'cjswl008', 'cjswl008', 'spqjekdl'),
('312', 'no810813', 'no8108', '5124415'),
('313', 'dpfneptm', 'tnvjwkd9', 'tnvjwkd9'),
('314', 'qpqpzm', 'dlehdwn23', '890710'),
('315', 'qpqpzm', 'dlehdwn25', '890710'),
('316', 'tjgusdms', 'tjgusdms', 'tjgus123'),
('317', 'poiuyt007', 'poiuyt007', 'wlgns0943'),
('318', 'blue347', 'blue347', '0106'),
('319', 'wjsurdltmf01', 'lim1631', '1006522'),
('320', 'qnqn11', 'qudqn11', 'rmfotj1'),
('321', 'dolmeri20', 'jcy1341', 'jcy1341'),
('322', 'dolmeri20', 'dolmeri12', 'wlgns9230'),
('323', 'aksun1', 'aksun', 'emdtlsdk'),
('324', 'aksun1', 'aksun01', 'emdtls1'),
('325', 'aksun1', 'aksun02', 'emdtls1'),
('326', 'aksun1', 'aksun03', 'emdtls1'),
('329', 'dkdlel21', 'yoonkyoung', 'qudcks'),
('330', 'dkdlel21', 'bis815', 'bis7360'),
('332', 'dydwn', 'pibatda', 'dydwntksdjq'),
('333', 'dydwn', 'pibatda09z', 'dydwntksdjq'),
('334', 'lwm5030', 'lwm5030', '85321'),
('335', 'qjwjddldi', 'syr0486', 'a123456'),
('336', 'kornet85', 'love851013', '1123'),
('337', 'kornet85', 'kornet85', 'whdghks1'),
('339', 'darkhunt12', 'darkhunt5', '861204'),
('340', 'darkhunt12', 'darkhunt55', '861204'),
('344', 'slrlal', 'number1z', '1431'),
('345', 'ppy3765', 'ppy3765', 'qkrgjstn'),
('346', 'dlghgu', 'dlghgu', 'djandl??'),
('349', 'rkdgns65', 'yaseoo', 'ya1103'),
('350', 'rkdgns65', 'ilbondoo', 'qo1103'),
('357', 'lin111', 'pop1ny', 'dudgks'),
('358', 'wlstnr4007', 'aosjrpa12', 'rjaehghs12'),
('359', 'jwj2112', 'jwj2112', 'dnjswo'),
('361', 'akendls2', 'cej0015', 'l4one2'),
('362', 'akendls2', 'kbo6006', 'l4one2'),
('363', 'akendls2', 'dlxksdn', 'l4one2'),
('364', 'akendls2', 'soohozi', 'l4one2'),
('366', 'hyongtwo', 'hyongtwo', '3103po'),
('367', '6440', 'soqkwldlqj', 'qlci123'),
('369', 'gkgkalsl', 'gkgkalsl', '123123'),
('370', 'gkgkalsl', 'gkals965', '123123'),
('371', 'gkgkalsl', 'gktnrtod', '123123'),
('372', 'suhak', 'chtkfl12', 'sldrlal12'),
('373', 'zaczac', 'zaczac', '11111111'),
('374', 'zaczac', 'zaczac1', '11111111'),
('375', 'zaczac', 'zaczac2', '11111111'),
('376', 'zaczac', 'zaczac3', '11111111'),
('377', 'zaczac', 'zaczac4', '11111111'),
('378', 'flsvmfl', 'gaxx', 'gpej99'),
('379', 'flsvmfl', 'leeyuil', 'gpej99'),
('382', 'rlatndud', 'mykent', 'gustmd1'),
('383', 'rlatndud', 'namtaeja1', 'hssy8490'),
('384', 'sewon11', 'sewon11', 'sewon12'),
('385', 'sclove87', 'sclove87', '87070887'),
('386', 'dutnzkdhfl', 'tkfkdgo1', 'qlvk'),
('387', 'skwlsgy0623', 'tkfkdto330', '841224'),
('388', 'skwlsgy0623', 'rudgns84', 'rudgns12'),
('389', 'skwlsgy0623', 'ansgh84', 'ansgh12'),
('390', 'kyoung663', 'tlsrudal63', '2086skm'),
('391', 'sptmdpqk', 'mtyoum', '1dpqkek'),
('392', 'sptmdpqk', 'mtyoum77', '1dpqkek'),
('393', 'tpqkek', 'tpqkek', '94549454'),
('394', 'skflzk', 'skflzk', 'dkcla1'),
('395', 'zkfpehtm', 'zkfpehtm', 'cpfgml1'),
('396', 'tkfwlak18', 'hi3005', '407316'),
('397', 'qnqn11', 'gustnr7894', 'rngudrb'),
('398', 'qnqn11', 'golng0628', 'gudrb12'),
('399', 'qnqn11', 'ssgod123', 'a159753'),
('400', 'dlfror3005', 'chlqkstjr', '1363'),
('401', 'dlfror3005', 'orpptt10', 'rmsdyd11'),
('402', 'dlfror3005', 'psk1578', 'tjsrnr1'),
('403', 'himooni', 'himooni', 'myemye1'),
('404', 'babylus1', 'clubs80', '794280'),
('405', 'boombono', 'boombono', 'b1041521'),
('406', 'snaipo1', 'snaipo1', '1082017'),
('407', 'snaipo1', 'snaipo12', 'HYHY2848'),
('408', 'rose', 'ilaver', 'rose1092'),
('409', '01086429230', 'dolmeri22', 'wlgns9230'),
('410', 'tnlflqkqh', 'tnlfl', 'tnlfl1'),
('411', 'dpdp7', 'dpdp77', '1622316'),
('412', 'dpdp7', 'jilalbyung', '1622316'),
('413', 'qwe2324', 'bb2324', 'rmfotj1'),
('414', '0345499', 'gombau001', '0345499'),
('415', '0345499', 'gombau', '0345499'),
('416', 'pjw528', 'hg3731', '1129'),
('417', 'niceguy', 'niceguyckj', 'niceguy'),
('420', 'loosebox', 'loosebox', '4412534'),
('421', 'ehowl123', 'kkc8641', '32632920'),
('422', 'ehowl123', 'tmdwls8807', 'tmdwls24'),
('424', 'ehowl123', 'dltjdtjr', '1234'),
('425', 'ehowl123', 'gadswind', 'dleodud'),
('426', 'ehowl123', 'ska3668', 'su3668'),
('427', 'ssjlovessj', 'jeewk', 'wldnjsrb1'),
('428', 'dlqeo999', 'irun23456', 'ehfoalvk1'),
('429', 'gurwls9797', 'goodboyhj', 'a1034265'),
('430', 'pjw528', 'pjw528', 'jinwoo528'),
('431', 'gurwls9797', 'kiss6842', 'rlgh12'),
('432', 'gurwls9797', 'apxptm13', 'alstn3263'),
('433', 'akendls2', 'rnfla12', 'l4one2'),
('434', 'akendls2', 'akendls400', 'l4one2'),
('435', 'theniceboy', 'iplayboy85', 'dmden85'),
('436', 'gorhdjjang', 'gorhd', 'qjxj33'),
('438', 'oh0430', 'oh0430', 'sin0430'),
('439', 'tlfdlfkdqksm', 'win1160', 'ajtlak79'),
('440', 'tlfdlfkdqksm', 'win11600', 'ajtlak79'),
('441', 'tlfdlfkdqksm', 'ajtlak82', 'ajtlak79'),
('442', 'ggang2217', 'yokim5698', '6645698'),
('443', 'jitemin', 'jitemin', '2347'),
('444', 'masan18nom', 'masan10nom', 'bestpig'),
('445', 'masan18nom', 'masan11nom', 'bestpig'),
('446', 'slrlal', 'rlacksgkr', 'tkfkd3017'),
('447', 'tmznptnl', 'tmznptnl', 'wjdgns86'),
('448', 'jrsatan', 'dawn1111', 'tkxks691'),
('449', 'tpdud697', 'kyw6971', '697wlwhstm'),
('450', 'tpdud697', 'kyw6972', '697wlwhstm'),
('451', 'tpdud697', 'kyw6973', '697wlwhstm'),
('452', 'tpdud697', 'kyw6974', '697wlwhstm'),
('453', 'tpdud697', 'kyw6975', '697wlwhstm'),
('454', 'hyongtwo', 'joewoo00', '3103po'),
('455', 'osg1984', 'osg1984', 'dhtjdrjs1'),
('456', 'osg1984', 'tntwk00', 'wjddml'),
('457', 'pjw528', 'gkckddhks1', 'changwan1'),
('458', 'zieotn', 'zialdud', 'this1803'),
('459', 'dudrnqkqh', 'kbc9969', '9648aa'),
('460', 'dudrnqkqh', 'kbc0328', '9648aa'),
('461', 'audrnsdk', 'audrnsdk', 'as8197'),
('462', 'chel1000', 'chel1000', '1000'),
('463', 'evastory', 'valentain', 'gksk1286'),
('464', 'evastory', 'ljd2604', 'dlwhdejr'),
('465', 'wusn1', 'sihak001', 'wlgusdn'),
('466', 'evastory', 'ljd1818', 'dlwhdejr'),
('467', 'dudrnqkqh', 'kbc9960', '9648aa'),
('469', 'dmdtn', 'jjangusky', 'tjdwns2'),
('470', 'dmdtn', 'dubug', '3131'),
('471', 'dmdtn', 'ishang', '9797'),
('474', 'wjsurdltmf01', 'lalrary123', '16341634'),
('475', 'rladudgns10', 'gjwjddlek', '791348625'),
('476', 'mua0519', 'rorhrl0519', '19880519'),
('477', 'sclove87', 'sclove87', '87070887'),
('478', 'sclove87', 'sclove0708', '87070887'),
('479', 'sclove87', 'sclove8708', '87070887'),
('480', 'ozdenim', 'ozdenim', 'l102612'),
('481', 'evaflymisa', 'flymisa', 'basaji'),
('482', 'whckdals', 'whckdalsek', 'ckdals2'),
('483', 'adieu', 'adieu2000', '2332'),
('484', 'adieu', 'adieu2001', '23322332'),
('485', 'adieu', 'adieu2002', '23322332');