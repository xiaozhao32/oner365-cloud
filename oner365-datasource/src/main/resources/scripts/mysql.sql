
-- ----------------------------
-- 测试分库分表
-- ----------------------------
CREATE DATABASE IF NOT EXISTS oner365_ds0 DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
use oner365_ds0;

DROP TABLE IF EXISTS `t_order_0`;
CREATE TABLE `t_order_0` (
	`id` varchar(32) NOT NULL COMMENT '编号',
	`order_id` int(11) NOT NULL COMMENT '订单id',
	`user_id` int(11) NOT NULL COMMENT '用户id',
	`status` varchar(32) COMMENT '状态',
	`create_time` datetime NOT NULL,
	PRIMARY KEY (`id`) USING BTREE
) DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_order_1`;
CREATE TABLE `t_order_1` (
	`id` varchar(32) NOT NULL COMMENT '编号',
	`order_id` int(11) NOT NULL COMMENT '订单id',
	`user_id` int(11) NOT NULL COMMENT '用户id',
	`status` varchar(32) COMMENT '状态',
	`create_time` datetime NOT NULL,
	PRIMARY KEY (`id`) USING BTREE
) DEFAULT CHARSET=utf8;

CREATE DATABASE IF NOT EXISTS oner365_ds1 DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
use oner365_ds1;

DROP TABLE IF EXISTS `t_order_0`;
CREATE TABLE `t_order_0` (
	`id` varchar(32) NOT NULL COMMENT '编号',
	`order_id` int(11) NOT NULL COMMENT '订单id',
	`user_id` int(11) NOT NULL COMMENT '用户id',
	`status` varchar(32) COMMENT '状态',
	`create_time` datetime NOT NULL,
	PRIMARY KEY (`id`) USING BTREE
) DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_order_1`;
CREATE TABLE `t_order_1` (
	`id` varchar(32) NOT NULL COMMENT '编号',
	`order_id` int(11) NOT NULL COMMENT '订单id',
	`user_id` int(11) NOT NULL COMMENT '用户id',
	`status` varchar(32) COMMENT '状态',
	`create_time` datetime NOT NULL,
	PRIMARY KEY (`id`) USING BTREE
) DEFAULT CHARSET=utf8;