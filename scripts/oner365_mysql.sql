/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_mysql
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : oner365

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 26/10/2021 17:06:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table` (
  `table_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='代码生成业务表';

-- ----------------------------
-- Records of gen_table
-- ----------------------------
BEGIN;
INSERT INTO `gen_table` VALUES (5, 'nt_test_date', '测试时间', NULL, NULL, 'TestDate', 'crud', 'com.oner365.system', 'system', 'date', '测试时间', 'zhaoyong', '0', '/', NULL, 'admin', '2021-10-26 15:03:10', '', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column` (
  `column_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) DEFAULT '' COMMENT '字典类型',
  `sort` int DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='代码生成业务表字段';

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------
BEGIN;
INSERT INTO `gen_table_column` VALUES (27, '5', 'id', '主键', 'int', 'Long', 'id', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2021-10-26 15:03:10', '', NULL);
INSERT INTO `gen_table_column` VALUES (28, '5', 'name', '名称', 'varchar(32)', 'String', 'name', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 2, 'admin', '2021-10-26 15:03:10', '', NULL);
INSERT INTO `gen_table_column` VALUES (29, '5', 'phone', '电话', 'bigint', 'Long', 'phone', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, 'admin', '2021-10-26 15:03:10', '', NULL);
INSERT INTO `gen_table_column` VALUES (30, '5', 'description', '描述', 'text', 'String', 'description', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2021-10-26 15:03:10', '', NULL);
INSERT INTO `gen_table_column` VALUES (31, '5', 'test_date', '测试日期', 'date', 'LocalDate', 'testDate', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'date', '', 5, 'admin', '2021-10-26 15:03:10', '', NULL);
INSERT INTO `gen_table_column` VALUES (32, '5', 'status', '状态', 'varchar(8)', 'String', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', '', 6, 'admin', '2021-10-26 15:03:10', '', NULL);
INSERT INTO `gen_table_column` VALUES (33, '5', 'create_time', '创建时间', 'timestamp', 'LocalDateTime', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 7, 'admin', '2021-10-26 15:03:10', '', NULL);
INSERT INTO `gen_table_column` VALUES (34, '5', 'update_time', '更新时间', 'timestamp', 'LocalDateTime', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 8, 'admin', '2021-10-26 15:03:10', '', NULL);
COMMIT;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL COMMENT '序列'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED COMMENT='序列';

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
BEGIN;
INSERT INTO `hibernate_sequence` VALUES (1);
COMMIT;

-- ----------------------------
-- Table structure for nt_data_source_config
-- ----------------------------
DROP TABLE IF EXISTS `nt_data_source_config`;
CREATE TABLE `nt_data_source_config` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `connect_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据源连接名称',
  `db_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数据库类型',
  `ds_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数据源类型',
  `db_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '数据库名称',
  `ip_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数据库连接地址',
  `port` int DEFAULT NULL COMMENT '端口号',
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `driver_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '驱动名称',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `dept_code` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门编码',
  `dept_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_connect_name` (`connect_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='数据源';

-- ----------------------------
-- Records of nt_data_source_config
-- ----------------------------
BEGIN;
INSERT INTO `nt_data_source_config` VALUES ('4028e591736543e001736547e9950000', 'cloud', 'mysql', 'db', 'cloud', '192.168.1.1', 3306, 'root', '1234', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://192.168.1.1:3306/cloud', '2020-07-19 12:14:38', '2020-07-19 14:29:33', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for nt_gateway_route
-- ----------------------------
DROP TABLE IF EXISTS `nt_gateway_route`;
CREATE TABLE `nt_gateway_route` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `filters` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '过滤规则',
  `predicates` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '表达式',
  `route_order` int NOT NULL COMMENT '排序',
  `uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地址',
  `status` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='路由表';

-- ----------------------------
-- Records of nt_gateway_route
-- ----------------------------
BEGIN;
INSERT INTO `nt_gateway_route` VALUES ('oner365-elasticsearch', '[{\"name\":\"StripPrefix\",\"args\":{\"parts\":\"1\"}}]', '[{\"name\":\"Path\",\"args\":{\"pattern\":\"/elasticsearch/**\"}}]', 1, 'lb://oner365-elasticsearch', '1');
INSERT INTO `nt_gateway_route` VALUES ('oner365-files', '[{\"name\":\"StripPrefix\",\"args\":{\"parts\":\"1\"}}]', '[{\"name\":\"Path\",\"args\":{\"pattern\":\"/files/**\"}}]', 1, 'lb://oner365-files', '1');
INSERT INTO `nt_gateway_route` VALUES ('oner365-generator', '[{\"name\":\"StripPrefix\",\"args\":{\"parts\":\"1\"}}]', '[{\"name\":\"Path\",\"args\":{\"pattern\":\"/generator/**\"}}]', 1, 'lb://oner365-generator', '1');
INSERT INTO `nt_gateway_route` VALUES ('oner365-monitor', '[{\"name\":\"StripPrefix\",\"args\":{\"parts\":\"1\"}}]', '[{\"name\":\"Path\",\"args\":{\"pattern\":\"/monitor/**\"}}]', 1, 'lb://oner365-monitor', '1');
INSERT INTO `nt_gateway_route` VALUES ('oner365-swagger', '[{\"name\":\"StripPrefix\",\"args\":{\"parts\":\"1\"}}]', '[{\"name\":\"Path\",\"args\":{\"pattern\":\"/swagger/**\"}}]', 1, 'lb://oner365-swagger', '1');
INSERT INTO `nt_gateway_route` VALUES ('oner365-system', '[{\"name\":\"StripPrefix\",\"args\":{\"parts\":\"1\"}}]', '[{\"name\":\"Path\",\"args\":{\"pattern\":\"/system/**\"}}]', 1, 'lb://oner365-system', '1');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_dict_item`;
CREATE TABLE `nt_sys_dict_item` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `dict_item_type_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典类型id',
  `dict_item_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典编码',
  `dict_item_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字典名称',
  `dict_item_order` int DEFAULT NULL COMMENT '排序',
  `parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上级id',
  `status` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_dict_item_code` (`dict_item_code`) USING BTREE,
  KEY `idx_dict_item_type_id` (`dict_item_type_id`) USING BTREE,
  CONSTRAINT `fk_dict_item_type_id` FOREIGN KEY (`dict_item_type_id`) REFERENCES `nt_sys_dict_item_type` (`dict_type_code`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字典信息';

-- ----------------------------
-- Records of nt_sys_dict_item
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_dict_item` VALUES ('1101', 'sys_normal_disable', '1', '有效', 1, NULL, '1');
INSERT INTO `nt_sys_dict_item` VALUES ('1102', 'sys_normal_disable', '0', '无效', 2, NULL, '1');
INSERT INTO `nt_sys_dict_item` VALUES ('1103', 'sys_user_sex', '0', '男', 1, NULL, '1');
INSERT INTO `nt_sys_dict_item` VALUES ('1104', 'sys_user_sex', '1', '女', 2, NULL, '1');
INSERT INTO `nt_sys_dict_item` VALUES ('1105', 'sys_status', '1', '有效', 1, NULL, '1');
INSERT INTO `nt_sys_dict_item` VALUES ('1106', 'sys_status', '0', '无效', 2, NULL, '1');
INSERT INTO `nt_sys_dict_item` VALUES ('4028b88174aa011e0174aa0a28c30004', 'sys_task_group', 'DEFAULT', '默认', 1, NULL, '1');
INSERT INTO `nt_sys_dict_item` VALUES ('4028b88174aa011e0174aa0a51bc0005', 'sys_task_group', 'SYSTEM', '系统', 2, NULL, '1');
INSERT INTO `nt_sys_dict_item` VALUES ('4028b88174aa011e0174aa0b35eb0006', 'sys_task_status', '1', '正常', 1, NULL, '1');
INSERT INTO `nt_sys_dict_item` VALUES ('4028b88174aa011e0174aa0b66630007', 'sys_task_status', '0', '暂停', 2, NULL, '1');
INSERT INTO `nt_sys_dict_item` VALUES ('ff808081777196b10177719cac9b0002', 'sys_normal_hidden', '0', '显示', 1, NULL, '1');
INSERT INTO `nt_sys_dict_item` VALUES ('ff808081777196b10177719ccb700003', 'sys_normal_hidden', '1', '隐藏', 2, NULL, '1');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_dict_item_type
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_dict_item_type`;
CREATE TABLE `nt_sys_dict_item_type` (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `dict_item_type_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分类名称',
  `dict_type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类编码',
  `dict_item_type_des` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '分类描述',
  `dict_item_type_order` int DEFAULT NULL COMMENT '排序',
  `status` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_dict_type_code` (`dict_type_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字典分类';

-- ----------------------------
-- Records of nt_sys_dict_item_type
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_dict_item_type` VALUES ('sys_normal_disable', '状态', 'sys_normal_disable', '2222', NULL, '1');
INSERT INTO `nt_sys_dict_item_type` VALUES ('sys_normal_hidden', '是否隐藏', 'sys_normal_hidden', NULL, NULL, '1');
INSERT INTO `nt_sys_dict_item_type` VALUES ('sys_task_group', '任务分组', 'sys_task_group', '任务分组', NULL, '1');
INSERT INTO `nt_sys_dict_item_type` VALUES ('sys_task_status', '任务状态', 'sys_task_status', '11', NULL, '1');
INSERT INTO `nt_sys_dict_item_type` VALUES ('sys_user_sex', '性别', 'sys_user_sex', '111', NULL, '1');
INSERT INTO `nt_sys_dict_item_type` VALUES ('sys_status', '状态', 'sys_status', NULL, NULL, '1');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_fastdfs_file
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_fastdfs_file`;
CREATE TABLE `nt_sys_fastdfs_file` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `file_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名称',
  `display_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '显示名称',
  `file_storage` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '存储方式',
  `file_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件路径',
  `file_suffix` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件后缀',
  `file_size` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件大小',
  `fastdfs_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件地址',
  `is_directory` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否目录',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='文件信息';

-- ----------------------------
-- Records of nt_sys_fastdfs_file
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_fastdfs_file` VALUES ('group1/M00/00/00/wKhlgl711DCAVEQWAAAEQwL8sw0614.txt', 'wKhlgl711DCAVEQWAAAEQwL8sw0614.txt', '123.txt', 'fdfs', 'http://192.168.101.130/group1/M00/00/00/wKhlgl711DCAVEQWAAAEQwL8sw0614.txt', 'txt', '1.1 KB', 'http://192.168.101.130', '0', '2021-02-22 16:52:55');
INSERT INTO `nt_sys_fastdfs_file` VALUES ('group1/M00/00/00/wKhlgl71yDKAHZxUAAeFJC8wk5A780.jpg', 'wKhlgl71yDKAHZxUAAeFJC8wk5A780.jpg', '3242.jpg', 'fdfs', 'http://192.168.101.130/group1/M00/00/00/wKhlgl71yDKAHZxUAAeFJC8wk5A780.jpg', 'jpg', '481 KB', 'http://192.168.101.130', '0', '2021-02-22 16:53:56');
INSERT INTO `nt_sys_fastdfs_file` VALUES ('group1/M00/00/00/wKhlgl72iyaAT0A6AAfMRhRFRow323.pdf', 'wKhlgl72iyaAT0A6AAfMRhRFRow323.pdf', '456.pdf', 'fdfs', 'http://192.168.101.130/group1/M00/00/00/wKhlgl72iyaAT0A6AAfMRhRFRow323.pdf', 'pdf', '499 KB', 'http://192.168.101.130', '0', '2021-02-22 16:50:24');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_job
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_job`;
CREATE TABLE `nt_sys_job` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `job_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '职位名称',
  `job_info` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '职位信息',
  `job_logo` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '职位logo',
  `job_logo_url` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'logo地址',
  `job_order` int NOT NULL COMMENT '排序',
  `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上级id',
  `status` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_job_name` (`job_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户职位';

-- ----------------------------
-- Records of nt_sys_job
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_job` VALUES ('1', '研发部', '管理员', '111', NULL, 1, '1', '1', '2020-04-24 14:31:41', '2021-10-25 16:12:52');
INSERT INTO `nt_sys_job` VALUES ('2', '总监办', '总监', '111', '222', 3, '1', '1', '2021-06-04 14:12:16', '2021-09-14 15:11:17');
INSERT INTO `nt_sys_job` VALUES ('4028b881745e46ae01745e4c3cd90008', '财务部', '财务', '111', '222', 2, NULL, '1', '2020-09-05 20:44:49', '2021-09-13 15:08:26');
INSERT INTO `nt_sys_job` VALUES ('ff8080817cbb7ffa017cbb80c1d70003', '前台', '123', NULL, NULL, 4, NULL, '1', '2021-10-26 15:29:10', '2021-10-26 16:18:49');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_log`;
CREATE TABLE `nt_sys_log` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `operation_ip` varchar(32) DEFAULT NULL COMMENT '操作IP',
  `method_name` varchar(8) DEFAULT NULL COMMENT '请求类型',
  `operation_name` varchar(255) DEFAULT NULL COMMENT '操作名称',
  `operation_path` varchar(255) DEFAULT NULL COMMENT '操作地址',
  `operation_context` text COMMENT '操作内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Table structure for nt_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_menu`;
CREATE TABLE `nt_sys_menu` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `menu_type_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单类型id',
  `menu_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '上级id',
  `component` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '元素',
  `path` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '路径',
  `menu_order` int NOT NULL COMMENT '排序',
  `menu_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'icon',
  `status` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `another_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '别名',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_menu_type_id` (`menu_type_id`) USING BTREE,
  KEY `idx_parent_id` (`parent_id`) USING BTREE,
  CONSTRAINT `fk_sys_menu_menu_type_id` FOREIGN KEY (`menu_type_id`) REFERENCES `nt_sys_menu_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统菜单';

-- ----------------------------
-- Records of nt_sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_menu` VALUES ('101', '1', '系统设置', '-1', 'Layout', '/system', 1, '系统设置', 'system', '1', '2020-05-11 14:00:21', '2020-05-11 14:00:25', NULL);
INSERT INTO `nt_sys_menu` VALUES ('1011', '1', '单位管理', '101', 'system/org/index', '/org', 11, '单位管理', 'tree', '1', '2020-05-11 14:03:06', '2020-05-11 14:03:11', NULL);
INSERT INTO `nt_sys_menu` VALUES ('1012', '1', '角色管理', '101', 'system/role/index', '/role', 12, '角色管理', 'peoples', '1', '2020-05-11 14:08:39', '2020-05-11 14:08:43', NULL);
INSERT INTO `nt_sys_menu` VALUES ('1013', '1', '用户职位', '101', 'system/job/index', '/job', 13, '用户职位', 'post', '1', '2020-08-05 21:48:33', '2020-08-05 21:48:37', NULL);
INSERT INTO `nt_sys_menu` VALUES ('1014', '1', '用户管理', '101', 'system/user/index', '/user', 14, '用户管理', 'user', '1', '2020-09-05 20:30:00', '2020-09-05 20:30:00', NULL);
INSERT INTO `nt_sys_menu` VALUES ('1015', '1', '字典管理', '101', 'system/dict/index', '/dict', 15, '字典管理', 'dict', '1', '2020-09-05 20:30:00', '2020-09-05 20:30:00', NULL);
INSERT INTO `nt_sys_menu` VALUES ('1016', '1', '菜单管理', '101', 'system/menu/index', '/menu', 16, '菜单管理', 'tree-table', '1', '2020-09-05 20:30:00', '2020-09-05 20:30:00', NULL);
INSERT INTO `nt_sys_menu` VALUES ('201', '1', '系统监控', '-1', 'Layout', '/monitor', 2, '系统监控', 'monitor', '1', '2020-09-03 17:20:13', '2020-09-17 20:57:13', NULL);
INSERT INTO `nt_sys_menu` VALUES ('2011', '1', '服务监控', '201', 'monitor/service/index', '/service', 21, '服务监控', 'druid', '1', '2020-09-03 17:21:32', '2020-09-17 20:57:02', NULL);
INSERT INTO `nt_sys_menu` VALUES ('2012', '1', '服务器监控', '201', 'monitor/server/index', '/server', 22, '服务器监控', 'server', '1', '2020-09-03 17:22:24', '2020-09-12 17:27:11', NULL);
INSERT INTO `nt_sys_menu` VALUES ('2013', '1', '定时任务', '201', 'monitor/task/index', '/task', 23, '定时任务', 'job', '1', '2020-09-19 11:57:23', '2020-09-19 11:57:25', NULL);
INSERT INTO `nt_sys_menu` VALUES ('2014', '1', '路由监控', '201', 'monitor/route/index', '/route', 24, '路由监控', 'cascader', '1', '2020-09-19 11:57:23', '2020-09-19 11:57:23', NULL);
INSERT INTO `nt_sys_menu` VALUES ('2015', '1', '缓存监控', '201', 'monitor/cache/index', '/cache', 25, '缓存监控', 'time-range', '1', '2020-12-08 14:00:34', '2020-12-08 14:00:51', NULL);
INSERT INTO `nt_sys_menu` VALUES ('2016', '1', 'ES监控', '201', 'monitor/elasticsearch/index', '/elasticsearch', 26, 'ES监控', 'drag', '1', '2020-12-25 14:47:37', '2020-12-25 14:50:14', NULL);
INSERT INTO `nt_sys_menu` VALUES ('2017', '1', '队列监控', '201', 'monitor/rabbitmq/index', '/rabbitmq', 27, '队列监控', 'example', '1', '2020-12-25 14:48:35', '2020-12-25 14:50:19', NULL);
INSERT INTO `nt_sys_menu` VALUES ('2018', '1', '文档监控', '201', 'monitor/fastdfs/index', '/fastdfs', 28, '文档监控', 'documentation', '1', '2020-12-25 14:49:33', '2020-12-25 14:50:24', NULL);
INSERT INTO `nt_sys_menu` VALUES ('2019', '1', '数据源监控', '201', 'monitor/druid/index', '/druid', 29, NULL, 'druid', '1', '2022-04-18 14:01:13', '2022-04-19 10:53:09', NULL);
INSERT INTO `nt_sys_menu` VALUES ('301', '1', '日志管理', '-1', 'Layout', '/log', 3, '日志管理', 'log', '1', '2020-11-16 15:36:00', '2020-11-16 15:36:00', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3011', '1', '服务日志', '301', 'system/log/index', '/syslog', 31, '服务日志', 'druid', '1', '2020-11-16 15:36:00', '2021-09-12 10:59:39', NULL);
INSERT INTO `nt_sys_menu` VALUES ('401', '1', '生成管理', '-1', 'Layout', '/generator', 4, '生成管理', 'money', '1', '2021-10-26 11:09:27', '2021-10-26 11:10:35', NULL);
INSERT INTO `nt_sys_menu` VALUES ('4011', '1', '生成框架', '401', 'tool/gen/index', '/generator/gen', 1, '生成框架', 'system', '1', '2021-10-26 11:12:05', '2021-10-26 11:17:07', NULL);
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_menu_oper
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_menu_oper`;
CREATE TABLE `nt_sys_menu_oper` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `menu_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单id',
  `operation_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_operation_id` (`operation_id`) USING BTREE,
  KEY `idx_menu_id` (`menu_id`) USING BTREE,
  CONSTRAINT `fk_menu_oper_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `nt_sys_menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_menu_oper_operation_id` FOREIGN KEY (`operation_id`) REFERENCES `nt_sys_operation` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统菜单操作';

-- ----------------------------
-- Table structure for nt_sys_menu_type
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_menu_type`;
CREATE TABLE `nt_sys_menu_type` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `type_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型编码',
  `type_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型名称',
  `status` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_type_code` (`type_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统菜单类型';

-- ----------------------------
-- Records of nt_sys_menu_type
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_menu_type` VALUES ('1', 'nt_sys', '统一用户认证', '1', '2013-01-01 10:00:00', '2021-09-18 13:47:15');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_message
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_message`;
CREATE TABLE `nt_sys_message` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `queue_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '队列类型',
  `queue_key` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '队列标识',
  `message_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型',
  `message_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '名称',
  `type_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '字段id',
  `context` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '内容',
  `send_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '发送者',
  `receive_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '接收者',
  `status` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_message_type` (`message_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统消息';

-- ----------------------------
-- Table structure for nt_sys_operation
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_operation`;
CREATE TABLE `nt_sys_operation` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `operation_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作类型',
  `operation_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作名称',
  `status` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统操作';

-- ----------------------------
-- Records of nt_sys_operation
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_operation` VALUES ('1', 'select', '查询', '1', '2013-01-01 10:00:00', '2013-01-01 10:00:00');
INSERT INTO `nt_sys_operation` VALUES ('2', 'add', '添加', '1', '2013-01-01 10:00:00', '2013-01-01 10:00:00');
INSERT INTO `nt_sys_operation` VALUES ('3', 'edit', '编辑', '1', '2013-01-01 10:00:00', '2020-07-19 13:09:58');
INSERT INTO `nt_sys_operation` VALUES ('4', 'save', '保存', '1', '2013-01-01 10:00:00', '2020-07-19 14:12:47');
INSERT INTO `nt_sys_operation` VALUES ('5', 'delete', '删除', '1', '2013-01-01 10:00:00', '2013-01-01 10:00:00');
INSERT INTO `nt_sys_operation` VALUES ('6', 'imported', '导入', '1', '2013-01-01 10:00:00', '2013-01-01 10:00:00');
INSERT INTO `nt_sys_operation` VALUES ('7', 'exported', '导出', '1', '2013-01-01 10:00:00', '2013-01-01 10:00:00');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_organization`;
CREATE TABLE `nt_sys_organization` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `org_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '单位名称',
  `org_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '机构代码',
  `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '父节点',
  `ancestors` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '编码',
  `org_order` int DEFAULT NULL COMMENT '排序',
  `org_area_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '行政区划代码',
  `org_credit_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '统一社会信用代码',
  `org_logo` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Logo',
  `org_logo_url` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Logo地址',
  `org_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类型',
  `create_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建时间',
  `status` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `business_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '业务负责人姓名',
  `business_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '业务负责人电话',
  `technical_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '技术负责人姓名',
  `technical_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '技术负责人电话',
  `config_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '数据源id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_sys_org_config_id` (`config_id`) USING BTREE,
  KEY `idx_sys_org_org_code` (`org_code`) USING BTREE,
  KEY `idx_sys_org_parent_id` (`parent_id`) USING BTREE,
  CONSTRAINT `fk_sys_org_config_id` FOREIGN KEY (`config_id`) REFERENCES `nt_data_source_config` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='机构信息';

-- ----------------------------
-- Records of nt_sys_organization
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_organization` VALUES ('1', '北京市', '100000000000', '-1', '-1', 1, NULL, NULL, NULL, NULL, '1', '1', '1', '2020-06-11 17:33:58', '2021-09-12 10:50:31', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('110', '北京市', '110000000000', '1', '-1,1', 1, NULL, NULL, NULL, NULL, '1', '1', '1', '2020-06-11 17:33:58', '2021-09-12 10:49:01', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('110101', '北京市东城区', '110101000000', '-1,1,110', '-1', 1, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_role`;
CREATE TABLE `nt_sys_role` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `role_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色编号',
  `role_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `role_des` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色描述',
  `status` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统角色';

-- ----------------------------
-- Records of nt_sys_role
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_role` VALUES ('1', '001', '超管', '权限最大用户', '1', '2018-10-10 16:08:02', '2021-10-26 11:12:23');
INSERT INTO `nt_sys_role` VALUES ('ff80808172a150110172a159d9c40000', '002', '职员', '普通职员', '1', '2020-06-11 11:08:40', '2021-09-12 10:51:15');
INSERT INTO `nt_sys_role` VALUES ('ff80808172a624c00172a67c70c30007', '003', '用户', '普通用户', '0', '2020-06-12 11:04:33', '2021-05-26 10:06:23');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_role_menu`;
CREATE TABLE `nt_sys_role_menu` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `menu_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单id',
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
  `menu_type_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单类型id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_role_menu_menu_id` (`menu_id`) USING BTREE,
  KEY `idx_role_menu_role_id` (`role_id`) USING BTREE,
  KEY `idx_role_menu_menu_type_id` (`menu_type_id`) USING BTREE,
  CONSTRAINT `fk_role_menu_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `nt_sys_menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_role_menu_menu_type_id` FOREIGN KEY (`menu_type_id`) REFERENCES `nt_sys_menu_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_role_menu_role_id` FOREIGN KEY (`role_id`) REFERENCES `nt_sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色菜单权限';

-- ----------------------------
-- Records of nt_sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_role_menu` VALUES ('11101', '101', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('111011', '1011', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('111012', '1012', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('111013', '1013', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('111014', '1014', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('111015', '1015', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('111016', '1016', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('11201', '201', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('112011', '2011', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('112012', '2012', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('112013', '2013', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('112014', '2014', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('112015', '2015', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('112016', '2016', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('112017', '2017', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('112018', '2018', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('112019', '2019', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('11301', '301', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('113011', '3011', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('11401', '401', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('114011', '4011', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c400001101', '101', 'ff80808172a150110172a159d9c40000', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000011011', '1011', 'ff80808172a150110172a159d9c40000', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000011012', '1012', 'ff80808172a150110172a159d9c40000', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000011013', '1013', 'ff80808172a150110172a159d9c40000', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000011014', '1014', 'ff80808172a150110172a159d9c40000', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000011015', '1015', 'ff80808172a150110172a159d9c40000', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000011016', '1016', 'ff80808172a150110172a159d9c40000', '1');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_role_menu_oper
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_role_menu_oper`;
CREATE TABLE `nt_sys_role_menu_oper` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `menu_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单id',
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
  `menu_type_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单类型id',
  `operation_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_role_menu_oper_menu_id` (`menu_id`) USING BTREE,
  KEY `idx_role_menu_oper_operation_id` (`operation_id`) USING BTREE,
  KEY `idx_role_menu_oper_role_id` (`role_id`) USING BTREE,
  KEY `idx_role_menu_oper_menu_type_id` (`menu_type_id`) USING BTREE,
  CONSTRAINT `fk_role_menu_oper_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `nt_sys_menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_role_menu_oper_menu_type_id` FOREIGN KEY (`menu_type_id`) REFERENCES `nt_sys_menu_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_role_menu_oper_operation_id` FOREIGN KEY (`operation_id`) REFERENCES `nt_sys_operation` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_role_menu_oper_role_id` FOREIGN KEY (`role_id`) REFERENCES `nt_sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单操作权限';

-- ----------------------------
-- Records of nt_sys_role_menu_oper
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_role_menu_oper` VALUES ('111101', '101', '1', '1', '1');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_sequence
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_sequence`;
CREATE TABLE `nt_sys_sequence` (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `table_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '表名称',
  `sequence` int DEFAULT NULL COMMENT '序列',
  `status` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='序列';

-- ----------------------------
-- Table structure for nt_sys_task
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_task`;
CREATE TABLE `nt_sys_task` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `task_group` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务组',
  `task_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名称',
  `concurrent` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '线程',
  `invoke_param` json DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '表达式',
  `invoke_target` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '目标',
  `misfire_policy` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '策略',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `execute_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态',
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统任务表';

-- ----------------------------
-- Records of nt_sys_task
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_task` VALUES ('8a818b3774bef2910174bef387ec0000', 'DEFAULT', 'test', '1', '{\"taskId\": \"8a818b3774bef2910174bef387ec0000\", \"taskParam\": {}, \"concurrent\": \"1\", \"taskServerName\": \"oner365-system\"}', '0/10 * * * * ?', 'systemTask.taskRun()', '2', '', '1', '0', NULL, 'liutao', NULL);
INSERT INTO `nt_sys_task` VALUES ('ff80808175cfe8900175d012ecc00002', 'DEFAULT', '删除3天日志', '1', '{\"taskId\": \"ff80808175cfe8900175d012ecc00002\", \"taskParam\": {}, \"concurrent\": null, \"taskServerName\": null}', '0 0 1 * * ?', 'systemTask.taskRun()', '3', NULL, NULL, '0', NULL, 'admin', NULL);
INSERT INTO `nt_sys_task` VALUES ('ff80808175d015c90175d05958e50001', 'DEFAULT', '111', '1', '{\"taskId\": \"ff80808175d015c90175d05958e50001\", \"taskParam\": {}, \"concurrent\": \"1\", \"taskServerName\": \"oner365-monitor\"}', '0/30 * * * * ?', 'systemTask.taskRun()', '3', '321', NULL, '0', NULL, 'admin', NULL);
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_task_log
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_task_log`;
CREATE TABLE `nt_sys_task_log` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `task_group` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务组',
  `task_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名称',
  `task_message` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '任务消息',
  `execute_server_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '服务名称',
  `execute_ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行ip',
  `exception_info` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '异常信息',
  `invoke_target` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求参数',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `stop_time` datetime DEFAULT NULL COMMENT '停止时间',
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='任务日志表';

-- ----------------------------
-- Records of nt_sys_task_log
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_task_log` VALUES ('ff80808175cfe8900175cff033bc0000', 'DEFAULT', 'test', '执行时间：10毫秒', 'oner365-system', '127.0.0.1', NULL, 'systemTask.taskRun()', NULL, NULL, NULL, '1', '2020-11-16 15:23:47', NULL, NULL);
INSERT INTO `nt_sys_task_log` VALUES ('ff80808175cfe8900175d007c2f00001', 'DEFAULT', 'test', '执行时间：7毫秒', 'oner365-system', '127.0.0.1', NULL, 'systemTask.taskRun()', NULL, NULL, NULL, '1', '2020-11-16 15:49:31', NULL, NULL);
INSERT INTO `nt_sys_task_log` VALUES ('ff8080817a7565dd017a756914b20000', 'DEFAULT', 'test', '执行时间：192毫秒', 'oner365-system', '127.0.0.1', NULL, 'systemTask.taskRun()', NULL, NULL, NULL, '1', '2021-07-05 14:44:19', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_user`;
CREATE TABLE `nt_sys_user` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键id',
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `real_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '性别',
  `user_type` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类型',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `id_card` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件号码',
  `last_ip` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '最后登录ip',
  `last_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '电话',
  `user_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户编号',
  `id_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '证件类型',
  `status` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态1有效, 0无效',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  `is_admin` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '是否为管理员',
  `active_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态',
  `default_password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '默认密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_user_name` (`user_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统用户';

-- ----------------------------
-- Records of nt_sys_user
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_user` VALUES ('1', 'admin', 'C4CA4238A0B923820DCC509A6F75849B', '超管', 'https://www.oner365.com/group1/M00/00/04/rBqcll9Qsc-ADt_9AAAk7-agPWw680.jpg', '0', '1', 'admin@qq.com', '110103197707250933', '127.0.0.1', '2021-10-25 15:45:52', '13800138000', '', NULL, '1', '1234567', '1', '1', '123456', '2018-10-09 14:19:44');
INSERT INTO `nt_sys_user` VALUES ('2', 'shy', 'C4CA4238A0B923820DCC509A6F75849B', '张3', '', '0', '1', NULL, NULL, NULL, NULL, '13800138000', NULL, NULL, '1', NULL, '0', '1', '123456', '2020-05-12 09:47:07');
INSERT INTO `nt_sys_user` VALUES ('5', 'ls', 'C4CA4238A0B923820DCC509A6F75849B', '王老师', '', '0', '1', NULL, NULL, NULL, NULL, NULL, '111', NULL, '0', NULL, '1', '1', NULL, '2020-05-12 17:05:23');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_user_job
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_user_job`;
CREATE TABLE `nt_sys_user_job` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `position_order` int NOT NULL COMMENT '排序',
  `status` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `job_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '职位id',
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_job_job_id` (`job_id`) USING BTREE,
  KEY `idx_user_job_user_id` (`user_id`) USING BTREE,
  CONSTRAINT `fk_user_job_job_id` FOREIGN KEY (`job_id`) REFERENCES `nt_sys_job` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_job_user_id` FOREIGN KEY (`user_id`) REFERENCES `nt_sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户职位权限';

-- ----------------------------
-- Records of nt_sys_user_job
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_user_job` VALUES ('ff8080817640f2d4017640f8fca70001', 1, '1', '2020-12-08 14:10:28', NULL, '1', '2');
INSERT INTO `nt_sys_user_job` VALUES ('ff8080817cb668bc017cb669b090000b', 1, '1', '2021-10-25 15:45:52', NULL, '2', '1');
INSERT INTO `nt_sys_user_job` VALUES ('ff8080817cb668bc017cb669b095000c', 1, '1', '2021-10-25 15:45:52', NULL, '1', '1');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_user_org
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_user_org`;
CREATE TABLE `nt_sys_user_org` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `position_order` int NOT NULL COMMENT '排序',
  `status` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `org_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位id',
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_org_org_id` (`org_id`) USING BTREE,
  KEY `idx_user_org_user_id` (`user_id`) USING BTREE,
  CONSTRAINT `fk_user_org_org_id` FOREIGN KEY (`org_id`) REFERENCES `nt_sys_organization` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_org_user_id` FOREIGN KEY (`user_id`) REFERENCES `nt_sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户职位权限';

-- ----------------------------
-- Records of nt_sys_user_org
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_user_org` VALUES ('ff8080817640f2d4017640f8fcb10002', 1, '1', '2020-12-08 14:10:28', NULL, '110101', '2');
INSERT INTO `nt_sys_user_org` VALUES ('ff8080817cb668bc017cb669b09b000d', 1, '1', '2021-10-25 15:45:52', NULL, '1', '1');
INSERT INTO `nt_sys_user_org` VALUES ('ff8080817cb668bc017cb669b09f000e', 1, '1', '2021-10-25 15:45:52', NULL, '110', '1');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_user_role`;
CREATE TABLE `nt_sys_user_role` (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_role_role_id` (`role_id`) USING BTREE,
  KEY `idx_user_role_user_id` (`user_id`) USING BTREE,
  CONSTRAINT `fk_user_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `nt_sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `nt_sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户角色权限';

-- ----------------------------
-- Records of nt_sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_user_role` VALUES ('ff80808172a7b2ce0172bacc3b9c0009', '1', '5');
INSERT INTO `nt_sys_user_role` VALUES ('ff8080817640f2d4017640f8fc990000', 'ff80808172a150110172a159d9c40000', '2');
INSERT INTO `nt_sys_user_role` VALUES ('ff8080817cb668bc017cb669b089000a', '1', '1');
COMMIT;

-- ----------------------------
-- Table structure for nt_test_date
-- ----------------------------
DROP TABLE IF EXISTS `nt_test_date`;
CREATE TABLE `nt_test_date` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `phone` bigint DEFAULT NULL COMMENT '电话',
  `description` text COMMENT '描述',
  `test_date` date DEFAULT NULL COMMENT '测试日期',
  `status` varchar(8) DEFAULT NULL COMMENT '状态',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='测试生成框架';

-- ----------------------------
-- Records of nt_test_date
-- ----------------------------
BEGIN;
INSERT INTO `nt_test_date` VALUES (1, '名字', 12345, '12345', '2010-10-13', '1', '2021-10-26 14:06:18', '2021-10-26 14:43:57');
INSERT INTO `nt_test_date` VALUES (2, '名字', 12345, '12345', NULL, '1', '2021-10-26 14:04:54', '2021-10-26 14:04:54');
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `blob_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS` (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `calendar_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `calendar` blob NOT NULL,
  PRIMARY KEY (`sched_name`,`calendar_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `cron_expression` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time_zone_id` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('ProjectScheduler', 'TASK_CLASS_NAME8a818b3774bef2910174bef387ec0000', 'DEFAULT', '0/10 * * * * ?', 'Asia/Shanghai');
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('ProjectScheduler', 'TASK_CLASS_NAMEff80808175cfe8900175d012ecc00002', 'DEFAULT', '0 0 1 * * ?', 'Asia/Shanghai');
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('ProjectScheduler', 'TASK_CLASS_NAMEff80808175d015c90175d05958e50001', 'DEFAULT', '0/30 * * * * ?', 'Asia/Shanghai');
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `entry_id` varchar(95) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `instance_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `fired_time` bigint NOT NULL,
  `sched_time` bigint NOT NULL,
  `priority` int NOT NULL,
  `state` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `job_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `requests_recovery` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`entry_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `job_class_name` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_durable` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_update_data` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `requests_recovery` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of QRTZ_JOB_DETAILS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('ProjectScheduler', 'TASK_CLASS_NAME8a818b3774bef2910174bef387ec0000', 'DEFAULT', NULL, 'com.oner365.monitor.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F5045525449455373720022636F6D2E6F6E65723336352E6D6F6E69746F722E656E746974792E5379735461736B000000000000000102000E4C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000A6372656174655573657271007E00094C000E63726F6E45787072657373696F6E71007E00094C000D6578656375746553746174757371007E00094C0002696471007E00094C000B696E766F6B65506172616D7400284C636F6D2F6F6E65723336352F6D6F6E69746F722F656E746974792F496E766F6B65506172616D3B4C000C696E766F6B6554617267657471007E00094C000D6D697366697265506F6C69637971007E00094C000672656D61726B71007E00094C000673746174757371007E00094C00097461736B47726F757071007E00094C00087461736B4E616D6571007E00094C000A75706461746554696D6571007E000A787074000131707400066C697574616F74000E302F3130202A202A202A202A203F74000131740020386138313862333737346265663239313031373462656633383765633030303073720026636F6D2E6F6E65723336352E6D6F6E69746F722E656E746974792E496E766F6B65506172616D00000000000000010200044C000A636F6E63757272656E7471007E00094C00067461736B496471007E00094C00097461736B506172616D7400214C636F6D2F616C69626162612F666173746A736F6E2F4A534F4E4F626A6563743B4C000E7461736B5365727665724E616D6571007E000978707400013174002038613831386233373734626566323931303137346265663338376563303030307372001F636F6D2E616C69626162612E666173746A736F6E2E4A534F4E4F626A65637400000000000000010200014C00036D617071007E000378707371007E00053F40000000000010770800000010000000007874000E6F6E65723336352D73797374656D74001973797374656D5461736B2E7461736B4E6F506172616D732829740001327400007400013074000744454641554C5474000474657374707800);
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('ProjectScheduler', 'TASK_CLASS_NAMEff80808175cfe8900175d012ecc00002', 'DEFAULT', NULL, 'com.oner365.monitor.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F5045525449455373720022636F6D2E6F6E65723336352E6D6F6E69746F722E656E746974792E5379735461736B000000000000000102000E4C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000A6372656174655573657271007E00094C000E63726F6E45787072657373696F6E71007E00094C000D6578656375746553746174757371007E00094C0002696471007E00094C000B696E766F6B65506172616D7400284C636F6D2F6F6E65723336352F6D6F6E69746F722F656E746974792F496E766F6B65506172616D3B4C000C696E766F6B6554617267657471007E00094C000D6D697366697265506F6C69637971007E00094C000672656D61726B71007E00094C000673746174757371007E00094C00097461736B47726F757071007E00094C00087461736B4E616D6571007E00094C000A75706461746554696D6571007E000A7870740001317074000561646D696E74000B3020302031202A202A203F70740020666638303830383137356366653839303031373564303132656363303030303273720026636F6D2E6F6E65723336352E6D6F6E69746F722E656E746974792E496E766F6B65506172616D00000000000000010200044C000A636F6E63757272656E7471007E00094C00067461736B496471007E00094C00097461736B506172616D7400214C636F6D2F616C69626162612F666173746A736F6E2F4A534F4E4F626A6563743B4C000E7461736B5365727665724E616D6571007E000978707074002066663830383038313735636665383930303137356430313265636330303030327372001F636F6D2E616C69626162612E666173746A736F6E2E4A534F4E4F626A65637400000000000000010200014C00036D617071007E000378707371007E00053F4000000000001077080000001000000000787074001B73797374656D5461736B2E7461736B44656C6574654C6F6728332974000133707400013074000744454641554C54740010E588A0E999A433E5A4A9E697A5E5BF97707800);
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('ProjectScheduler', 'TASK_CLASS_NAMEff80808175d015c90175d05958e50001', 'DEFAULT', NULL, 'com.oner365.monitor.util.QuartzDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000F5441534B5F50524F5045525449455373720022636F6D2E6F6E65723336352E6D6F6E69746F722E656E746974792E5379735461736B000000000000000102000E4C000A636F6E63757272656E747400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000A6372656174655573657271007E00094C000E63726F6E45787072657373696F6E71007E00094C000D6578656375746553746174757371007E00094C0002696471007E00094C000B696E766F6B65506172616D7400284C636F6D2F6F6E65723336352F6D6F6E69746F722F656E746974792F496E766F6B65506172616D3B4C000C696E766F6B6554617267657471007E00094C000D6D697366697265506F6C69637971007E00094C000672656D61726B71007E00094C000673746174757371007E00094C00097461736B47726F757071007E00094C00087461736B4E616D6571007E00094C000A75706461746554696D6571007E000A7870740001317074000561646D696E74000E302F3330202A202A202A202A203F70740020666638303830383137356430313563393031373564303539353865353030303173720026636F6D2E6F6E65723336352E6D6F6E69746F722E656E746974792E496E766F6B65506172616D00000000000000010200044C000A636F6E63757272656E7471007E00094C00067461736B496471007E00094C00097461736B506172616D7400214C636F6D2F616C69626162612F666173746A736F6E2F4A534F4E4F626A6563743B4C000E7461736B5365727665724E616D6571007E000978707400013174002066663830383038313735643031356339303137356430353935386535303030317372001F636F6D2E616C69626162612E666173746A736F6E2E4A534F4E4F626A65637400000000000000010200014C00036D617071007E000378707371007E00053F40000000000010770800000010000000007874000F6F6E65723336352D6D6F6E69746F7274001D73797374656D5461736B2E7461736B506172616D732827363636362729740001337400033332317400013074000744454641554C54740003313131707800);
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS` (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `lock_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`sched_name`,`lock_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_LOCKS` VALUES ('ProjectScheduler', 'STATE_ACCESS');
INSERT INTO `QRTZ_LOCKS` VALUES ('ProjectScheduler', 'TRIGGER_ACCESS');
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_group`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `instance_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `last_checkin_time` bigint NOT NULL,
  `checkin_interval` bigint NOT NULL,
  PRIMARY KEY (`sched_name`,`instance_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of QRTZ_SCHEDULER_STATE
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_SCHEDULER_STATE` VALUES ('ProjectScheduler', 'bogon1614742494470', 1614746685941, 15000);
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `repeat_count` bigint NOT NULL,
  `repeat_interval` bigint NOT NULL,
  `times_triggered` bigint NOT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `str_prop_1` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `str_prop_2` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `str_prop_3` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `int_prop_1` int DEFAULT NULL,
  `int_prop_2` int DEFAULT NULL,
  `long_prop_1` bigint DEFAULT NULL,
  `long_prop_2` bigint DEFAULT NULL,
  `dec_prop_1` decimal(13,4) DEFAULT NULL,
  `dec_prop_2` decimal(13,4) DEFAULT NULL,
  `bool_prop_1` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `bool_prop_2` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS` (
  `sched_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `job_group` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `next_fire_time` bigint DEFAULT NULL,
  `prev_fire_time` bigint DEFAULT NULL,
  `priority` int DEFAULT NULL,
  `trigger_state` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `trigger_type` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `start_time` bigint NOT NULL,
  `end_time` bigint DEFAULT NULL,
  `calendar_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `misfire_instr` smallint DEFAULT NULL,
  `job_data` blob,
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `QRTZ_JOB_DETAILS` (`sched_name`, `job_name`, `job_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_TRIGGERS` VALUES ('ProjectScheduler', 'TASK_CLASS_NAME8a818b3774bef2910174bef387ec0000', 'DEFAULT', 'TASK_CLASS_NAME8a818b3774bef2910174bef387ec0000', 'DEFAULT', NULL, 1635238940000, -1, 5, 'PAUSED', 'CRON', 1635238936000, 0, NULL, 1, '');
INSERT INTO `QRTZ_TRIGGERS` VALUES ('ProjectScheduler', 'TASK_CLASS_NAMEff80808175cfe8900175d012ecc00002', 'DEFAULT', 'TASK_CLASS_NAMEff80808175cfe8900175d012ecc00002', 'DEFAULT', NULL, 1635267600000, -1, 5, 'PAUSED', 'CRON', 1635238936000, 0, NULL, 2, '');
INSERT INTO `QRTZ_TRIGGERS` VALUES ('ProjectScheduler', 'TASK_CLASS_NAMEff80808175d015c90175d05958e50001', 'DEFAULT', 'TASK_CLASS_NAMEff80808175d015c90175d05958e50001', 'DEFAULT', NULL, 1635238950000, -1, 5, 'PAUSED', 'CRON', 1635238936000, 0, NULL, 2, '');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
