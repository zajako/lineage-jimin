/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:09:14
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `spawnlist_model`
-- ----------------------------
DROP TABLE IF EXISTS `spawnlist_model`;
CREATE TABLE `spawnlist_model` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `npcid` int(10) unsigned NOT NULL DEFAULT '0',
  `note` varchar(45) DEFAULT NULL,
  `locx` int(10) unsigned NOT NULL DEFAULT '0',
  `locy` int(10) unsigned NOT NULL DEFAULT '0',
  `mapid` int(10) unsigned NOT NULL DEFAULT '0',
  `heading` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=16384002 DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of spawnlist_model
-- ----------------------------
INSERT INTO `spawnlist_model` VALUES ('101001', '4500318', '오만(1층) 횃불', '32871', '32867', '101', '0');
INSERT INTO `spawnlist_model` VALUES ('101002', '4500318', '오만(1층) 횃불', '32783', '32816', '101', '0');
INSERT INTO `spawnlist_model` VALUES ('101003', '4500318', '오만(1층) 횃불', '32749', '32794', '101', '0');
INSERT INTO `spawnlist_model` VALUES ('101004', '4500318', '오만(1층) 횃불', '32731', '32794', '101', '0');
INSERT INTO `spawnlist_model` VALUES ('101005', '4500318', '오만(1층) 횃불', '32731', '32805', '101', '0');
INSERT INTO `spawnlist_model` VALUES ('101006', '4500318', '오만(1층) 횃불', '32741', '32805', '101', '0');
INSERT INTO `spawnlist_model` VALUES ('101007', '4500318', '오만(1층) 횃불', '32749', '32805', '101', '0');
INSERT INTO `spawnlist_model` VALUES ('101008', '4500318', '오만(1층) 횃불', '32770', '32806', '101', '0');
INSERT INTO `spawnlist_model` VALUES ('101009', '4500318', '오만(1층) 횃불', '32783', '32816', '101', '0');
INSERT INTO `spawnlist_model` VALUES ('101010', '4500318', '오만(1층) 횃불', '32815', '32816', '101', '0');
INSERT INTO `spawnlist_model` VALUES ('101011', '4500318', '오만(1층) 횃불', '32816', '32783', '101', '0');
INSERT INTO `spawnlist_model` VALUES ('101012', '4500318', '오만(1층) 횃불', '32796', '32794', '101', '0');
INSERT INTO `spawnlist_model` VALUES ('101013', '4500318', '오만(1층) 횃불', '32796', '32806', '101', '0');
INSERT INTO `spawnlist_model` VALUES ('101014', '4500318', '오만(1층) 횃불', '32740', '32794', '101', '0');
INSERT INTO `spawnlist_model` VALUES ('101015', '4500318', '오만(1층) 횃불', '32769', '32791', '101', '0');
INSERT INTO `spawnlist_model` VALUES ('101016', '4500318', '오만(1층) 횃불', '32783', '32783', '101', '0');
INSERT INTO `spawnlist_model` VALUES ('301001', '4500318', '오만의 탑 지하수로 횃불', '32660', '32936', '301', '0');
INSERT INTO `spawnlist_model` VALUES ('301002', '4500318', '오만의 탑 지하수로 횃불', '32660', '32930', '301', '0');
INSERT INTO `spawnlist_model` VALUES ('102001', '4500318', '오만(2층) 횃불', '32853', '32851', '102', '0');
INSERT INTO `spawnlist_model` VALUES ('102002', '4500318', '오만(2층) 횃불', '32850', '32859', '102', '0');
INSERT INTO `spawnlist_model` VALUES ('102003', '4500318', '오만(2층) 횃불', '32795', '32796', '102', '0');
INSERT INTO `spawnlist_model` VALUES ('102004', '4500318', '오만(2층) 횃불', '32795', '32806', '102', '0');
INSERT INTO `spawnlist_model` VALUES ('102005', '4500318', '오만(2층) 횃불', '32746', '32734', '102', '0');
INSERT INTO `spawnlist_model` VALUES ('102006', '4500318', '오만(2층) 횃불', '32738', '32734', '102', '0');
INSERT INTO `spawnlist_model` VALUES ('103001', '4500318', '오만(3층) 횃불', '32745', '32748', '103', '0');
INSERT INTO `spawnlist_model` VALUES ('103002', '4500318', '오만(3층) 횃불', '32795', '32795', '103', '0');
INSERT INTO `spawnlist_model` VALUES ('103003', '4500318', '오만(3층) 횃불', '32795', '32806', '103', '0');
INSERT INTO `spawnlist_model` VALUES ('103004', '4500318', '오만(3층) 횃불', '32851', '32841', '103', '0');
INSERT INTO `spawnlist_model` VALUES ('104001', '4500318', '오만(4층) 횃불', '32664', '32878', '104', '0');
INSERT INTO `spawnlist_model` VALUES ('104002', '4500318', '오만(4층) 횃불', '32680', '32848', '104', '0');
INSERT INTO `spawnlist_model` VALUES ('104003', '4500318', '오만(4층) 횃불', '32680', '32878', '104', '0');
INSERT INTO `spawnlist_model` VALUES ('104004', '4500318', '오만(4층) 횃불', '32664', '32848', '104', '0');
INSERT INTO `spawnlist_model` VALUES ('104005', '4500318', '오만(4층) 횃불', '32687', '32855', '104', '0');
INSERT INTO `spawnlist_model` VALUES ('105001', '4500318', '오만(5층) 횃불', '32678', '32869', '105', '0');
INSERT INTO `spawnlist_model` VALUES ('105002', '4500318', '오만(5층) 횃불', '32664', '32869', '105', '0');
INSERT INTO `spawnlist_model` VALUES ('105003', '4500318', '오만(5층) 횃불', '32664', '32855', '105', '0');
INSERT INTO `spawnlist_model` VALUES ('105004', '4500318', '오만(5층) 횃불', '32679', '32854', '105', '0');
INSERT INTO `spawnlist_model` VALUES ('106001', '4500317', '오만(6층) 횃불 *', '33769', '32865', '106', '0');
INSERT INTO `spawnlist_model` VALUES ('107001', '4500317', '오만(7층) 횃불', '32673', '32848', '107', '0');
INSERT INTO `spawnlist_model` VALUES ('107002', '4500317', '오만(7층) 횃불', '32675', '32869', '107', '0');
INSERT INTO `spawnlist_model` VALUES ('107003', '4500317', '오만(7층) 횃불', '32675', '32877', '107', '0');
INSERT INTO `spawnlist_model` VALUES ('107004', '4500317', '오만(7층) 횃불', '32705', '32903', '107', '0');
INSERT INTO `spawnlist_model` VALUES ('107005', '4500317', '오만(7층) 횃불', '32714', '32894', '107', '0');
INSERT INTO `spawnlist_model` VALUES ('108001', '4500317', '오만(8층) 횃불', '32662', '32864', '108', '0');
INSERT INTO `spawnlist_model` VALUES ('4001', '71267', '요숲 집 라이트', '33035', '32335', '4', '0');
INSERT INTO `spawnlist_model` VALUES ('4002', '71267', '요숲 집 라이트', '33034', '32362', '4', '0');
INSERT INTO `spawnlist_model` VALUES ('4003', '71267', '요숲 집 라이트', '33046', '32370', '4', '0');
INSERT INTO `spawnlist_model` VALUES ('4004', '71267', '요숲 집 라이트', '33057', '32372', '4', '0');
INSERT INTO `spawnlist_model` VALUES ('4005', '71267', '요숲 집 라이트', '33067', '32357', '4', '0');
INSERT INTO `spawnlist_model` VALUES ('4006', '71267', '요숲 집 라이트', '33070', '32335', '4', '0');
INSERT INTO `spawnlist_model` VALUES ('4007', '71267', '요숲 집 라이트', '33078', '32323', '4', '0');
INSERT INTO `spawnlist_model` VALUES ('4008', '71267', '요숲 집 라이트', '33073', '32313', '4', '0');
INSERT INTO `spawnlist_model` VALUES ('4009', '71267', '요숲 집 라이트', '33054', '32311', '4', '0');
INSERT INTO `spawnlist_model` VALUES ('4010', '71267', '요숲 집 라이트', '33042', '32320', '4', '0');
INSERT INTO `spawnlist_model` VALUES ('4011', '71267', '요숲 집 라이트', '33043', '32362', '4', '0');
INSERT INTO `spawnlist_model` VALUES ('4012', '71267', '요숲 집 라이트', '33032', '32370', '4', '0');
INSERT INTO `spawnlist_model` VALUES ('4013', '71267', '요숲 집 라이트', '33053', '32367', '4', '0');
INSERT INTO `spawnlist_model` VALUES ('4014', '71267', '요숲 집 라이트', '33062', '32366', '4', '0');
INSERT INTO `spawnlist_model` VALUES ('4015', '71267', '요숲 집 라이트', '33070', '32321', '4', '0');
INSERT INTO `spawnlist_model` VALUES ('4016', '71267', '요숲 집 라이트', '33060', '32309', '4', '0');
INSERT INTO `spawnlist_model` VALUES ('4017', '71267', '요숲 집 라이트', '33036', '32325', '4', '0');
INSERT INTO `spawnlist_model` VALUES ('4018', '71267', '요숲 집 라이트', '33032', '32342', '4', '0');
INSERT INTO `spawnlist_model` VALUES ('301039', '4500317', '오만(9층) 횃불', '32730', '32805', '109', '0');
INSERT INTO `spawnlist_model` VALUES ('301040', '4500317', '오만(9층) 횃불', '32724', '32811', '109', '0');
INSERT INTO `spawnlist_model` VALUES ('52001', '4500314', '기란성내 횃불', '32749', '32792', '52', '0');
INSERT INTO `spawnlist_model` VALUES ('52002', '4500315', '기란성내 횃불', '32742', '32787', '52', '0');
INSERT INTO `spawnlist_model` VALUES ('52003', '4500316', '기란성내 횃불', '32740', '32786', '52', '0');
INSERT INTO `spawnlist_model` VALUES ('52004', '4500317', '기란성내 횃불', '32737', '32789', '52', '0');
INSERT INTO `spawnlist_model` VALUES ('52005', '4500315', '기란성내 횃불', '32738', '32798', '52', '0');
INSERT INTO `spawnlist_model` VALUES ('52006', '4500315', '기란성내 횃불', '32720', '32799', '52', '0');
INSERT INTO `spawnlist_model` VALUES ('52007', '4500317', '기란성내 횃불', '32740', '32821', '52', '0');
INSERT INTO `spawnlist_model` VALUES ('52008', '4500317', '기란성내 횃불', '32722', '32820', '52', '0');
INSERT INTO `spawnlist_model` VALUES ('52009', '4500315', '기란성내 횃불', '32721', '32820', '52', '0');
INSERT INTO `spawnlist_model` VALUES ('52010', '4500315', '기란성내 횃불', '32720', '32810', '52', '0');
INSERT INTO `spawnlist_model` VALUES ('52011', '4500315', '기란성내 횃불', '32712', '32810', '52', '0');
INSERT INTO `spawnlist_model` VALUES ('52012', '4500315', '기란성내 횃불', '32712', '32820', '52', '0');
INSERT INTO `spawnlist_model` VALUES ('304002', '71216', '침묵의 동굴의 빛(small)', '32929', '32804', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304003', '71216', '침묵의 동굴의 빛(small)', '32934', '32804', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304004', '71216', '침묵의 동굴의 빛(small)', '32929', '32792', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304005', '71216', '침묵의 동굴의 빛(small)', '32934', '32792', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304006', '71217', '침묵의 동굴의 빛(door_left', '32941', '32782', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304007', '71218', '침묵의 동굴의 빛(door_righ', '32947', '32790', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304008', '71219', '침묵의 동굴의 빛(plant_sho', '32865', '32848', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304009', '71219', '침묵의 동굴의 빛(plant_sho', '32906', '32939', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304010', '71219', '침묵의 동굴의 빛(plant_sho', '32821', '32949', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304011', '71219', '침묵의 동굴의 빛(plant_sho', '32842', '32963', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304012', '71220', '침묵의 동굴의 빛(plant_two', '32887', '32869', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304013', '71220', '침묵의 동굴의 빛(plant_two', '32831', '32868', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304014', '71221', '침묵의 동굴의 빛(plant_hig', '32912', '32881', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304015', '71221', '침묵의 동굴의 빛(plant_hig', '32891', '32853', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304016', '71221', '침묵의 동굴의 빛(plant_hig', '32888', '32836', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304017', '71222', '침묵의 동굴의 빛(bridge)', '32786', '32901', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304018', '71222', '침묵의 동굴의 빛(bridge)', '32765', '32901', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304019', '71223', '침묵의 동굴의 빛(bridge)', '32765', '32906', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304020', '71223', '침묵의 동굴의 빛(bridge)', '32786', '32906', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304021', '71224', '침묵의 동굴의 빛(bridge)', '32775', '32901', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304022', '71225', '침묵의 동굴의 빛(bridge)', '32775', '32907', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304023', '71226', '침묵의 동굴의 빛(ball)', '32883', '32900', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304024', '71226', '침묵의 동굴의 빛(ball)', '32883', '32907', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304025', '71227', '침묵의 동굴의 빛(ball)', '32868', '32909', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304026', '71227', '침묵의 동굴의 빛(ball)', '32860', '32909', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304027', '71227', '침묵의 동굴의 빛(ball)', '32860', '32900', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304028', '71228', '침묵의 동굴의 빛(ball)', '32846', '32907', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304029', '71228', '침묵의 동굴의 빛(ball)', '32842', '32907', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304030', '71228', '침묵의 동굴의 빛(ball)', '32838', '32907', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304031', '71228', '침묵의 동굴의 빛(ball)', '32834', '32907', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304032', '71228', '침묵의 동굴의 빛(ball)', '32829', '32907', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304033', '71228', '침묵의 동굴의 빛(ball)', '32819', '32907', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304034', '71228', '침묵의 동굴의 빛(ball)', '32814', '32900', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304035', '71228', '침묵의 동굴의 빛(ball)', '32824', '32900', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304036', '71228', '침묵의 동굴의 빛(ball)', '32829', '32900', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304037', '71228', '침묵의 동굴의 빛(ball)', '32838', '32900', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304038', '71228', '침묵의 동굴의 빛(ball)', '32842', '32900', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304039', '71228', '침묵의 동굴의 빛(ball)', '32846', '32900', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304040', '71229', '침묵의 동굴의 빛(lamp)', '32855', '32941', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304041', '71230', '침묵의 동굴의 빛(lamp)', '32861', '32947', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304042', '71229', '침묵의 동굴의 빛(lamp)', '32892', '32832', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304043', '71230', '침묵의 동굴의 빛(lamp)', '32898', '32838', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304044', '71231', '침묵의 동굴의 빛(lamp)', '32891', '32847', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304045', '71231', '침묵의 동굴의 빛(lamp)', '32841', '32953', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304046', '71232', '침묵의 동굴의 빛(lamp)', '32840', '32861', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304047', '71233', '침묵의 동굴의 빛(lamp)', '32875', '32843', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304048', '71234', '침묵의 동굴의 빛(lamp)', '32902', '32932', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304049', '71235', '침묵의 동굴의 빛(lamp)', '32899', '32922', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304050', '71233', '침묵의 동굴의 빛(lamp)', '32922', '32875', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304051', '71232', '침묵의 동굴의 빛(lamp)', '32835', '32947', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304052', '71236', '침묵의 동굴의 빛(lamp)', '32854', '32958', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304053', '71237', '침묵의 동굴의 빛(lamp)', '32844', '32955', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304054', '71243', '침묵의 동굴의 빛(lamp)', '32899', '32847', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304055', '71233', '침묵의 동굴의 빛(lamp)', '32872', '32945', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304056', '71235', '침묵의 동굴의 빛(lamp)', '32871', '32847', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304057', '71234', '침묵의 동굴의 빛(lamp)', '32874', '32857', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304058', '71231', '침묵의 동굴의 빛(lamp)', '32882', '32820', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304059', '71238', '침묵의 동굴의 빛(lamp)', '32912', '32926', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304060', '71237', '침묵의 동굴의 빛(lamp)', '32894', '32849', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304061', '71236', '침묵의 동굴의 빛(lamp)', '32904', '32852', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304062', '71239', '침묵의 동굴의 빛(lamp)', '32854', '32842', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304063', '71240', '침묵의 동굴의 빛(lamp)', '32860', '32848', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90316', '71241', '침묵의 동굴의 빛(lamp)', '32865', '32840', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90317', '71242', '침묵의 동굴의 빛(lamp)', '32860', '32846', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90318', '71239', '침묵의 동굴의 빛(lamp)', '32919', '32857', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90319', '71240', '침묵의 동굴의 빛(lamp)', '32925', '32863', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90320', '71242', '침묵의 동굴의 빛(lamp)', '32925', '32861', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90321', '71241', '침묵의 동굴의 빛(lamp)', '32930', '32855', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90322', '71238', '침묵의 동굴의 빛(lamp)', '32836', '32968', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90323', '71244', '침묵의 동굴의 빛(lamp)', '32875', '32846', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90324', '71245', '침묵의 동굴의 빛(lamp)', '32875', '32853', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90325', '71243', '침묵의 동굴의 빛(lamp)', '32849', '32953', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90326', '71239', '침묵의 동굴의 빛(lamp)', '32891', '32936', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90327', '71240', '침묵의 동굴의 빛(lamp)', '32897', '32942', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90328', '71244', '침묵의 동굴의 빛(lamp)', '32903', '32921', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90329', '71245', '침묵의 동굴의 빛(lamp)', '32903', '32928', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90330', '71246', '침묵의 동굴의 빛', '32883', '32824', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90331', '71246', '침묵의 동굴의 빛', '32895', '32831', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90332', '71246', '침묵의 동굴의 빛', '32899', '32834', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90333', '71246', '침묵의 동굴의 빛', '32892', '32851', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90334', '71246', '침묵의 동굴의 빛', '32904', '32848', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90335', '71246', '침묵의 동굴의 빛', '32872', '32841', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90336', '71246', '침묵의 동굴의 빛', '32839', '32857', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90337', '71246', '침묵의 동굴의 빛', '32918', '32874', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90338', '71246', '침묵의 동굴의 빛', '32915', '32926', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90339', '71246', '침묵의 동굴의 빛', '32869', '32943', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90340', '71246', '침묵의 동굴의 빛', '32858', '32939', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90341', '71246', '침묵의 동굴의 빛', '32862', '32943', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90342', '71246', '침묵의 동굴의 빛', '32855', '32954', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90343', '71246', '침묵의 동굴의 빛', '32842', '32957', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90344', '71246', '침묵의 동굴의 빛', '32840', '32967', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90345', '71246', '침묵의 동굴의 빛', '32865', '32896', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90346', '71246', '침묵의 동굴의 빛', '32874', '32914', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90347', '71246', '침묵의 동굴의 빛', '32862', '32919', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90348', '71246', '침묵의 동굴의 빛', '32834', '32943', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90349', '71246', '침묵의 동굴의 빛', '32895', '32935', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('90350', '71246', '침묵의 동굴의 빛', '32932', '32797', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('304001', '71215', '침묵의 동굴의 수정', '32869', '32900', '304', '5');
INSERT INTO `spawnlist_model` VALUES ('16384000', '16384', '말하는섬 여관 불', '32737', '32793', '16384', '0');
