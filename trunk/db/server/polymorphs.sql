/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:07:21
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `polymorphs`
-- ----------------------------
DROP TABLE IF EXISTS `polymorphs`;
CREATE TABLE `polymorphs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `polyid` int(11) DEFAULT NULL,
  `minlevel` int(11) DEFAULT NULL,
  `weaponequip` int(11) DEFAULT NULL,
  `armorequip` int(11) DEFAULT NULL,
  `isSkillUse` int(11) DEFAULT NULL,
  `cause` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7342 DEFAULT CHARSET=euckr COMMENT='MyISAM free: 10240 kB';

-- ----------------------------
-- Records of polymorphs
-- ----------------------------
INSERT INTO `polymorphs` VALUES ('2501', 'jack o lantern', '2501', '100', '751', '417', '0', '7');
INSERT INTO `polymorphs` VALUES ('2388', 'death', '2388', '100', '0', '32', '0', '7');
INSERT INTO `polymorphs` VALUES ('3952', 'vampire', '3952', '100', '0', '32', '0', '7');
INSERT INTO `polymorphs` VALUES ('4767', 'rabbit', '4767', '100', '0', '4095', '0', '7');
INSERT INTO `polymorphs` VALUES ('4769', 'carrot rabbit', '4769', '100', '0', '4095', '0', '7');
INSERT INTO `polymorphs` VALUES ('4928', 'high collie', '4928', '100', '0', '2', '1', '7');
INSERT INTO `polymorphs` VALUES ('4929', 'high raccoon', '4929', '100', '0', '2', '1', '7');
INSERT INTO `polymorphs` VALUES ('938', 'beagle', '938', '100', '0', '2', '1', '7');
INSERT INTO `polymorphs` VALUES ('2064', 'snowman', '2064', '100', '0', '1027', '1', '7');
INSERT INTO `polymorphs` VALUES ('4227', 'hakama', '4227', '100', '0', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3750', 'haregi', '3750', '100', '0', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3664', 'baranka', '3664', '100', '704', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('5719', 'manekineko', '5719', '100', '2047', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6010', 'red orc', '6010', '100', '0', '1', '0', '7');
INSERT INTO `polymorphs` VALUES ('6089', 'drake morph', '6089', '100', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4000', 'knight vald morph', '4000', '100', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4001', 'iris morph', '4001', '100', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4002', 'paperman morph', '4002', '100', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4004', 'succubus queen morph', '4004', '100', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('5184', 'red uniform', '5184', '100', '0', '8', '1', '7');
INSERT INTO `polymorphs` VALUES ('5186', 'blue uniform', '5186', '100', '0', '8', '1', '7');
INSERT INTO `polymorphs` VALUES ('4186', 'robber bone', '4186', '100', '751', '4095', '0', '7');
INSERT INTO `polymorphs` VALUES ('4188', 'robber bone head', '4188', '100', '751', '4095', '0', '7');
INSERT INTO `polymorphs` VALUES ('4190', 'robber bone soldier', '4190', '100', '256', '4095', '0', '7');
INSERT INTO `polymorphs` VALUES ('6002', 'spirit of earth boss', '6002', '100', '8', '0', '1', '7');
INSERT INTO `polymorphs` VALUES ('6080', 'princess horse', '6080', '100', '16', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6094', 'prince horse', '6094', '100', '16', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6137', 'death 52', '6137', '52', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6140', 'darkelf 52', '6140', '52', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6136', 'barlog 52', '6136', '52', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6139', 'general 52', '6141', '52', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6138', 'assassin 52', '6138', '52', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6267', 'neo black knight', '6267', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6268', 'neo black mage', '6268', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6269', 'neo black scouter', '6269', '55', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6279', 'neo black assassin', '6279', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6142', 'death 55', '6142', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6145', 'darkelf 55', '6145', '55', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6141', 'barlog 55', '6141', '55', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6144', 'general 55', '6144', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6143', 'assassin 55', '6143', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6270', 'neo silver knight', '6270', '60', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6271', 'neo silver mage', '6271', '60', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6272', 'neo silver scouter', '6272', '60', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6280', 'neo silver assassin', '6280', '60', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6147', 'death 60', '6147', '60', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6150', 'darkelf 60', '6150', '60', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6146', 'barlog 60', '6146', '60', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6149', 'general 60', '6149', '60', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6148', 'assassin 60', '6148', '60', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6273', 'neo gold knight', '6273', '65', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6274', 'neo gold mage', '6274', '65', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6275', 'neo gold scouter', '6275', '65', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6281', 'neo gold assassin', '6281', '65', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6152', 'death 65', '6152', '65', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6155', 'darkelf 65', '6155', '65', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6151', 'barlog 65', '6151', '65', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6154', 'general 65', '6154', '65', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6153', 'assassin 65', '6153', '65', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6276', 'neo platinum knight', '6276', '70', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6277', 'neo platinum mage', '6277', '70', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6278', 'neo platinum scouter', '6278', '70', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6282', 'neo platinum assassin', '6282', '70', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6157', 'death 70', '6157', '70', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6160', 'darkelf 70', '6160', '70', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6156', 'barlog 70', '6156', '70', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6159', 'general 70', '6159', '70', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6158', 'assassin 70', '6158', '70', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6180', 'unicorn A', '6180', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6181', 'unicorn B', '6181', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6182', 'unicorn C', '6182', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6183', 'unicorn D', '6183', '100', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6184', 'bear A', '6184', '100', '749', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6185', 'bear B', '6185', '100', '749', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6186', 'bear C', '6186', '100', '749', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6187', 'bear D', '6187', '100', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6188', 'mini white dog A', '6188', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6189', 'mini white dog B', '6189', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6190', 'mini white dog C', '6190', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6191', 'mini white dog D', '6191', '100', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6192', 'ratman A', '6192', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6193', 'ratman B', '6193', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6194', 'ratman C', '6194', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6195', 'ratman D', '6195', '100', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6196', 'pet tiger A', '6196', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6197', 'pet tiger B', '6197', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6198', 'pet tiger C', '6198', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6199', 'pet tiger D', '6199', '100', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6200', 'dillo A', '6200', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6201', 'dillo B', '6201', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6202', 'dillo C', '6202', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6203', 'dillo D', '6203', '100', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6204', 'mole A', '6204', '100', '256', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6205', 'mole B', '6205', '100', '256', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6206', 'mole C', '6206', '100', '256', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6207', 'mole D', '6207', '100', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6208', 'darkelf thief A', '6208', '100', '1007', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6209', 'darkelf thief B', '6209', '100', '1007', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6210', 'darkelf thief C', '6210', '100', '1007', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6211', 'darkelf thief D', '6211', '100', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6212', 'ken lauhel A', '6212', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6213', 'ken lauhel B', '6213', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6214', 'ken lauhel C', '6214', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6215', 'ken lauhel D', '6215', '100', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6216', 'kelenis A', '6216', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6217', 'kelenis B', '6217', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6218', 'kelenis C', '6218', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6219', 'kelenis D', '6219', '100', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6220', 'slave A', '6220', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6221', 'slave B', '6221', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6222', 'slave C', '6222', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6223', 'slave D', '6223', '100', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6224', 'dofleganger boss A', '6224', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6225', 'dofleganger boss B', '6225', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6226', 'dofleganger boss C', '6226', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6227', 'dofleganger boss D', '6227', '100', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6228', 'lich A', '6228', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6229', 'lich B', '6229', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6230', 'lich C', '6230', '100', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6231', 'lich D', '6231', '100', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6232', 'woman1 A', '6232', '100', '0', '4095', '0', '10');
INSERT INTO `polymorphs` VALUES ('6233', 'woman1 B', '6233', '100', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6234', 'woman2 A', '6234', '100', '0', '4095', '0', '10');
INSERT INTO `polymorphs` VALUES ('6235', 'woman2 B', '6235', '100', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6236', 'woman3 A', '6236', '100', '0', '4095', '0', '10');
INSERT INTO `polymorphs` VALUES ('6237', 'woman3 B', '6237', '100', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6238', 'woman4 A', '6238', '100', '0', '4095', '0', '10');
INSERT INTO `polymorphs` VALUES ('6239', 'woman4 B', '6239', '100', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6240', 'woman5 A', '6240', '100', '0', '4095', '0', '10');
INSERT INTO `polymorphs` VALUES ('6241', 'woman5 B', '6241', '100', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6242', 'noblewoman A', '6242', '100', '0', '4095', '0', '10');
INSERT INTO `polymorphs` VALUES ('6243', 'noblewoman B', '6243', '100', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6284', 'Haunted House jack o lantern', '6284', '100', '0', '0', '0', '7');
INSERT INTO `polymorphs` VALUES ('3902', 'Kelenis Morph', '3902', '100', '43', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3903', 'Ken Lauhel Morph', '3903', '100', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('5645', 'Halloween Pumpkin', '5645', '100', '2047', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('5976', 'high bear', '5976', '100', '751', '2', '0', '7');
INSERT INTO `polymorphs` VALUES ('6086', 'Rabor Born Head', '6086', '100', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6087', 'Rabor Born archer', '6087', '100', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6088', 'Rabor Born knife', '6088', '100', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6400', 'Halloween jack o lantern', '6400', '100', '2047', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6406', '아덴근위대 변신터번', '6406', '100', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6697', '하피', '6697', '100', '751', '4095', '0', '7');
INSERT INTO `polymorphs` VALUES ('6698', '블랙 위자드', '6698', '100', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('945', 'milkcow', '945', '1', '0', '0', '0', '7');
INSERT INTO `polymorphs` VALUES ('947', 'deer', '947', '1', '0', '0', '0', '7');
INSERT INTO `polymorphs` VALUES ('979', 'wild boar', '979', '1', '0', '0', '1', '7');
INSERT INTO `polymorphs` VALUES ('3906', 'orc', '3906', '1', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3860', 'bow orc', '3860', '1', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3861', 'goblin', '3861', '1', '751', '913', '0', '7');
INSERT INTO `polymorphs` VALUES ('3862', 'kobolds', '3862', '1', '751', '913', '1', '7');
INSERT INTO `polymorphs` VALUES ('3863', 'dwarf', '3863', '1', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3864', 'orc fighter', '3864', '1', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3865', 'werewolf', '3865', '1', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3904', 'stone golem', '3904', '1', '751', '145', '1', '7');
INSERT INTO `polymorphs` VALUES ('29', 'floating eye', '29', '1', '0', '0', '1', '7');
INSERT INTO `polymorphs` VALUES ('1037', 'giant ant', '1037', '1', '0', '136', '0', '7');
INSERT INTO `polymorphs` VALUES ('1039', 'giant ant soldier', '1039', '1', '0', '136', '0', '7');
INSERT INTO `polymorphs` VALUES ('3866', 'gandi orc', '3866', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3867', 'rova orc', '3867', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3868', 'atuba orc', '3868', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3869', 'dudamara orc', '3869', '10', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3870', 'neruga orc', '3870', '10', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2374', 'skeleton polymorph', '2374', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2377', 'skeleton pike polymorph', '2377', '10', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3871', 'skeleton archer polymorph', '3871', '10', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2376', 'skeleton axeman polymorph', '2376', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('95', 'shelob', '95', '10', '0', '0', '0', '7');
INSERT INTO `polymorphs` VALUES ('3872', 'zombie', '3872', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3873', 'ghoul', '3873', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2378', 'spartoi polymorph', '2378', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3874', 'lycanthrope', '3874', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('146', 'ungoliant', '146', '10', '0', '0', '0', '7');
INSERT INTO `polymorphs` VALUES ('3875', 'ghast', '3875', '10', '751', '945', '1', '7');
INSERT INTO `polymorphs` VALUES ('3876', 'bugbear', '3876', '10', '751', '945', '1', '7');
INSERT INTO `polymorphs` VALUES ('951', 'cerberus', '951', '15', '0', '0', '0', '7');
INSERT INTO `polymorphs` VALUES ('1047', 'scorpion', '1047', '15', '0', '0', '0', '7');
INSERT INTO `polymorphs` VALUES ('3877', 'ogre', '3877', '15', '1791', '913', '1', '7');
INSERT INTO `polymorphs` VALUES ('3878', 'troll', '3878', '15', '751', '545', '1', '7');
INSERT INTO `polymorphs` VALUES ('3879', 'elder', '3879', '15', '751', '0', '1', '7');
INSERT INTO `polymorphs` VALUES ('3880', 'king bugbear', '3880', '15', '751', '945', '1', '7');
INSERT INTO `polymorphs` VALUES ('2323', 'orc scout polymorph', '2323', '15', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2386', 'minotaur i morph', '2386', '15', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2387', 'giant a morph', '2387', '15', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2385', 'yeti morph', '2385', '15', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3630', 'cyclops', '3630', '40', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3631', 'griffon', '3631', '40', '0', '32', '1', '7');
INSERT INTO `polymorphs` VALUES ('3632', 'cockatrice', '3632', '40', '0', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3633', 'ettin', '3633', '40', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3881', 'dark elder', '3881', '45', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3884', 'necromancer3', '3884', '45', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3885', 'necromancer4', '3885', '45', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3882', 'necromancer1', '3882', '45', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3883', 'necromancer2', '3883', '45', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3634', 'assassin', '3634', '45', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3887', 'darkelf carrier', '3887', '45', '1791', '4095', '0', '7');
INSERT INTO `polymorphs` VALUES ('3888', 'baphomet', '3888', '50', '751', '954', '1', '7');
INSERT INTO `polymorphs` VALUES ('3905', 'beleth', '3905', '50', '751', '954', '1', '7');
INSERT INTO `polymorphs` VALUES ('3889', 'demon', '3889', '51', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3784', 'death knight', '6137', '52', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2284', 'dark elf polymorph', '2284', '52', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3945', 'gelatincube', '3945', '15', '751', '0', '1', '7');
INSERT INTO `polymorphs` VALUES ('3950', 'middle oum', '3950', '15', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2384', 'succubus morph', '2384', '40', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4918', 'bandit bow morph', '4918', '40', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4925', 'elmor soldier morph', '4925', '40', '1080', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3886', 'lesser demon', '3886', '45', '751', '4095', '0', '7');
INSERT INTO `polymorphs` VALUES ('4917', 'darkelf ranger morph', '4917', '45', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4920', 'elmor general morph', '4920', '45', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3102', 'great minotaur morph', '3102', '50', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4919', 'darkelf guard morph', '4919', '50', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4922', 'guardian armor morph', '4922', '50', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4924', 'darkelf spear morph', '4924', '50', '1080', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4926', 'darkelf wizard morph', '4926', '50', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3101', 'black knight chief morph', '3101', '51', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4923', 'black knight morph', '4923', '51', '1080', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3126', 'fire bowman morph', '3126', '51', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3103', 'barlog morph', '3103', '52', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4921', 'darkelf general morph', '4921', '52', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4932', 'assassin master morph', '4932', '52', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3890', 'ancient black knight morph', '3890', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3891', 'ancient black mage morph', '3891', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('7126', '베히모스남', '7126', '100', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('7127', '베히모스여', '7127', '100', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('7128', '실베리아남', '7128', '100', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('7129', '실베리아여', '7129', '100', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2382', '근위', '6406', '100', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('7338', 'spearm 55', '7338', '55', '1080', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('7339', 'spearm 60', '7339', '60', '1080', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('7340', 'spearm 65', '7340', '65', '1080', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('7341', 'spearm 70', '7341', '70', '1080', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('7332', 'spearm 52', '7332', '52', '1080', '4095', '1', '7');
