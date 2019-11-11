
CREATE DATABASE /*!32312 IF NOT EXISTS*/`rfid_position` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `rfid_position`;

/*Table structure for table `rfid_cardinfor` */

DROP TABLE IF EXISTS `rfid_cardinfor`;

CREATE TABLE `rfid_cardinfor` (
  `objuid` varchar(32) NOT NULL,
  `physicalid` varchar(12) DEFAULT NULL COMMENT '物理卡号',
  `cardid` varchar(12) DEFAULT NULL COMMENT '卡编号（自定义卡号，和物理卡号对应）',
  `card_name` varchar(20) DEFAULT NULL COMMENT '卡名称',
  `card_status` int(2) DEFAULT '0' COMMENT '卡状态:0 正常 1 挂失 2 废弃',
  `open_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开卡时间',
  PRIMARY KEY (`objuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `rfid_cardstate` */

DROP TABLE IF EXISTS `rfid_cardstate`;

CREATE TABLE `rfid_cardstate` (
  `objuid` varchar(32) NOT NULL,
  `carduid` varchar(32) DEFAULT NULL COMMENT '关联卡UID',
  `state` int(2) DEFAULT '0' COMMENT '卡信号状态 0 未激活 1已激活',
  `triggeruid` varchar(32) DEFAULT NULL COMMENT '关联触发器UID',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '状态变更时间',
  PRIMARY KEY (`objuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `rfid_cardstatechangelog` */

DROP TABLE IF EXISTS `rfid_cardstatechangelog`;

CREATE TABLE `rfid_cardstatechangelog` (
  `objuid` varchar(32) NOT NULL,
  `carduid` varchar(32) DEFAULT NULL COMMENT '关联卡UID',
  `oldstate` int(2) DEFAULT NULL COMMENT '旧状态',
  `newstate` int(2) DEFAULT NULL COMMENT '新状态',
  `triggeruid` varchar(32) DEFAULT NULL COMMENT '关联触发器UID',
  `changetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '状态变化时间',
  PRIMARY KEY (`objuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `rfid_cardtrigdata` */

DROP TABLE IF EXISTS `rfid_cardtrigdata`;

CREATE TABLE `rfid_cardtrigdata` (
  `objuid` varchar(32) NOT NULL,
  `carduid` varchar(12) DEFAULT NULL COMMENT '卡号',
  `triggeruid` varchar(32) DEFAULT NULL COMMENT '触发器UID',
  `datadesc` text COMMENT '数据明细',
  `triggertime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`objuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `rfid_paras` */

DROP TABLE IF EXISTS `rfid_paras`;

CREATE TABLE `rfid_paras` (
  `objuid` varchar(32) NOT NULL,
  `paras_key` varchar(255) DEFAULT NULL,
  `paras_value` varchar(255) DEFAULT NULL,
  `note` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`objuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `rfid_tcpipconnect` */

DROP TABLE IF EXISTS `rfid_tcpipconnect`;

CREATE TABLE `rfid_tcpipconnect` (
  `objuid` varchar(32) NOT NULL,
  `inport` int(5) DEFAULT NULL COMMENT '客户端端口',
  `inip` varchar(32) DEFAULT NULL COMMENT '客户端IP',
  `connecttype` int(2) DEFAULT NULL COMMENT '连接类型 0 阅读器到应用 1 应用到明济服务',
  `connecttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '连接时间',
  `closetime` datetime DEFAULT NULL COMMENT '断开时间',
  PRIMARY KEY (`objuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `rfid_trigger` */

DROP TABLE IF EXISTS `rfid_trigger`;

CREATE TABLE `rfid_trigger` (
  `objuid` varchar(32) NOT NULL,
  `triggerid` varchar(12) DEFAULT NULL COMMENT '触发器物理编号',
  `triggername` varchar(60) DEFAULT NULL COMMENT '安装触发器区域名称',
  `positionx` varchar(20) DEFAULT NULL COMMENT 'X坐标',
  `positiony` varchar(20) DEFAULT NULL COMMENT 'Y坐标',
  `positionz` varchar(20) DEFAULT NULL COMMENT 'Z坐标',
  PRIMARY KEY (`objuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
