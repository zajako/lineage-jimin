/*
Navicat MySQL Data Transfer

Source Server         : playlive
Source Server Version : 60008
Source Host           : 59.1.238.62:3306
Source Database       : eva

Target Server Type    : MYSQL
Target Server Version : 60008
File Encoding         : 65001

Date: 2011-06-14 10:01:53
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `characters`
-- ----------------------------
DROP TABLE IF EXISTS `characters`;
CREATE TABLE `characters` (
  `account_name` varchar(13) NOT NULL DEFAULT '0',
  `objid` int(11) unsigned NOT NULL DEFAULT '0',
  `char_name` varchar(45) NOT NULL DEFAULT '',
  `level` int(11) unsigned NOT NULL DEFAULT '0',
  `HighLevel` int(11) unsigned NOT NULL DEFAULT '0',
  `Exp` int(10) unsigned NOT NULL DEFAULT '0',
  `MaxHp` int(10) unsigned NOT NULL DEFAULT '0',
  `CurHp` int(10) unsigned NOT NULL DEFAULT '0',
  `MaxMp` int(10) NOT NULL DEFAULT '0',
  `CurMp` int(10) NOT NULL DEFAULT '0',
  `Ac` int(10) NOT NULL DEFAULT '0',
  `Str` int(3) NOT NULL DEFAULT '0',
  `BaseStr` int(3) NOT NULL DEFAULT '0',
  `Con` int(3) NOT NULL DEFAULT '0',
  `BaseCon` int(3) NOT NULL DEFAULT '0',
  `Dex` int(3) NOT NULL DEFAULT '0',
  `BaseDex` int(3) NOT NULL DEFAULT '0',
  `Cha` int(3) NOT NULL DEFAULT '0',
  `BaseCha` int(3) NOT NULL DEFAULT '0',
  `Intel` int(3) NOT NULL DEFAULT '0',
  `BaseIntel` int(3) NOT NULL DEFAULT '0',
  `Wis` int(3) NOT NULL DEFAULT '0',
  `BaseWis` int(3) NOT NULL DEFAULT '0',
  `Status` int(10) unsigned NOT NULL DEFAULT '0',
  `Class` int(10) unsigned NOT NULL DEFAULT '0',
  `Sex` int(10) unsigned NOT NULL DEFAULT '0',
  `Type` int(10) unsigned NOT NULL DEFAULT '0',
  `Heading` int(10) unsigned NOT NULL DEFAULT '0',
  `LocX` int(11) unsigned NOT NULL DEFAULT '0',
  `LocY` int(11) unsigned NOT NULL DEFAULT '0',
  `MapID` int(10) unsigned NOT NULL DEFAULT '0',
  `Food` int(10) unsigned NOT NULL DEFAULT '0',
  `Lawful` int(10) NOT NULL DEFAULT '0',
  `Title` varchar(35) NOT NULL DEFAULT '',
  `ClanID` int(10) unsigned NOT NULL DEFAULT '0',
  `Clanname` varchar(45) NOT NULL DEFAULT '',
  `ClanRank` int(3) NOT NULL DEFAULT '0',
  `BonusStatus` int(10) NOT NULL DEFAULT '0',
  `ElixirStatus` int(10) NOT NULL DEFAULT '0',
  `ElfAttr` int(10) NOT NULL DEFAULT '0',
  `PKcount` int(10) NOT NULL DEFAULT '0',
  `ExpRes` int(10) NOT NULL DEFAULT '0',
  `PartnerID` int(10) NOT NULL DEFAULT '0',
  `AccessLevel` int(10) unsigned NOT NULL DEFAULT '0',
  `OnlineStatus` int(10) unsigned NOT NULL DEFAULT '0',
  `HomeTownID` int(10) NOT NULL DEFAULT '0',
  `Contribution` int(10) NOT NULL DEFAULT '0',
  `Pay` int(10) NOT NULL DEFAULT '0',
  `HellTime` int(10) unsigned NOT NULL DEFAULT '0',
  `Banned` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `Karma` int(10) NOT NULL DEFAULT '0',
  `LastPk` datetime DEFAULT NULL,
  `DeleteTime` datetime DEFAULT NULL,
  `ReturnStat` int(10) NOT NULL DEFAULT '0',
  `GdungeonTime` int(10) NOT NULL DEFAULT '0',
  `Ainhasad_Exp` int(11) NOT NULL DEFAULT '0',
  `Logout_time` datetime DEFAULT NULL,
  PRIMARY KEY (`objid`),
  KEY `key_id` (`account_name`,`char_name`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

-- ----------------------------
-- Records of characters
-- ----------------------------