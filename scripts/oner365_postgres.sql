/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.213.128_postgres
 Source Server Type    : PostgreSQL
 Source Server Version : 140001
 Source Host           : 192.168.213.128:5432
 Source Catalog        : oner365
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 140001
 File Encoding         : 65001

 Date: 14/05/2022 18:25:42
*/


-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS "gen_table";
CREATE TABLE "gen_table" (
  "table_id" serial,
  "table_name" varchar(200) COLLATE "pg_catalog"."default",
  "table_comment" varchar(500) COLLATE "pg_catalog"."default",
  "sub_table_name" varchar(64) COLLATE "pg_catalog"."default",
  "sub_table_fk_name" varchar(64) COLLATE "pg_catalog"."default",
  "class_name" varchar(100) COLLATE "pg_catalog"."default",
  "tpl_category" varchar(200) COLLATE "pg_catalog"."default",
  "package_name" varchar(100) COLLATE "pg_catalog"."default",
  "module_name" varchar(30) COLLATE "pg_catalog"."default",
  "business_name" varchar(30) COLLATE "pg_catalog"."default",
  "function_name" varchar(50) COLLATE "pg_catalog"."default",
  "function_author" varchar(50) COLLATE "pg_catalog"."default",
  "gen_type" char(1) COLLATE "pg_catalog"."default",
  "gen_path" varchar(200) COLLATE "pg_catalog"."default",
  "options" varchar(1000) COLLATE "pg_catalog"."default",
  "create_by" varchar(64) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_by" varchar(64) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "remark" varchar(500) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "gen_table"."table_id" IS '编号';
COMMENT ON COLUMN "gen_table"."table_name" IS '表名称';
COMMENT ON COLUMN "gen_table"."table_comment" IS '表描述';
COMMENT ON COLUMN "gen_table"."sub_table_name" IS '关联子表的表名';
COMMENT ON COLUMN "gen_table"."sub_table_fk_name" IS '子表关联的外键名';
COMMENT ON COLUMN "gen_table"."class_name" IS '实体类名称';
COMMENT ON COLUMN "gen_table"."tpl_category" IS '使用的模板（crud单表操作 tree树表操作）';
COMMENT ON COLUMN "gen_table"."package_name" IS '生成包路径';
COMMENT ON COLUMN "gen_table"."module_name" IS '生成模块名';
COMMENT ON COLUMN "gen_table"."business_name" IS '生成业务名';
COMMENT ON COLUMN "gen_table"."function_name" IS '生成功能名';
COMMENT ON COLUMN "gen_table"."function_author" IS '生成功能作者';
COMMENT ON COLUMN "gen_table"."gen_type" IS '生成代码方式（0zip压缩包 1自定义路径）';
COMMENT ON COLUMN "gen_table"."gen_path" IS '生成路径（不填默认项目路径）';
COMMENT ON COLUMN "gen_table"."options" IS '其它生成选项';
COMMENT ON COLUMN "gen_table"."create_by" IS '创建者';
COMMENT ON COLUMN "gen_table"."create_time" IS '创建时间';
COMMENT ON COLUMN "gen_table"."update_by" IS '更新者';
COMMENT ON COLUMN "gen_table"."update_time" IS '更新时间';
COMMENT ON COLUMN "gen_table"."remark" IS '备注';
COMMENT ON TABLE "gen_table" IS '代码生成业务表';

-- ----------------------------
-- Records of gen_table
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS "gen_table_column";
CREATE TABLE "gen_table_column" (
  "column_id" serial,
  "table_id" int8,
  "column_name" varchar(200) COLLATE "pg_catalog"."default",
  "column_comment" varchar(500) COLLATE "pg_catalog"."default",
  "column_type" varchar(100) COLLATE "pg_catalog"."default",
  "java_type" varchar(500) COLLATE "pg_catalog"."default",
  "java_field" varchar(200) COLLATE "pg_catalog"."default",
  "is_pk" char(1) COLLATE "pg_catalog"."default",
  "is_increment" char(1) COLLATE "pg_catalog"."default",
  "is_required" char(1) COLLATE "pg_catalog"."default",
  "is_insert" char(1) COLLATE "pg_catalog"."default",
  "is_edit" char(1) COLLATE "pg_catalog"."default",
  "is_list" char(1) COLLATE "pg_catalog"."default",
  "is_query" char(1) COLLATE "pg_catalog"."default",
  "query_type" varchar(200) COLLATE "pg_catalog"."default",
  "html_type" varchar(200) COLLATE "pg_catalog"."default",
  "dict_type" varchar(200) COLLATE "pg_catalog"."default",
  "sort" int4,
  "create_by" varchar(64) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_by" varchar(64) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "gen_table_column"."column_id" IS '编号';
COMMENT ON COLUMN "gen_table_column"."table_id" IS '归属表编号';
COMMENT ON COLUMN "gen_table_column"."column_name" IS '列名称';
COMMENT ON COLUMN "gen_table_column"."column_comment" IS '列描述';
COMMENT ON COLUMN "gen_table_column"."column_type" IS '列类型';
COMMENT ON COLUMN "gen_table_column"."java_type" IS 'JAVA类型';
COMMENT ON COLUMN "gen_table_column"."java_field" IS 'JAVA字段名';
COMMENT ON COLUMN "gen_table_column"."is_pk" IS '是否主键（1是）';
COMMENT ON COLUMN "gen_table_column"."is_increment" IS '是否自增（1是）';
COMMENT ON COLUMN "gen_table_column"."is_required" IS '是否必填（1是）';
COMMENT ON COLUMN "gen_table_column"."is_insert" IS '是否为插入字段（1是）';
COMMENT ON COLUMN "gen_table_column"."is_edit" IS '是否编辑字段（1是）';
COMMENT ON COLUMN "gen_table_column"."is_list" IS '是否列表字段（1是）';
COMMENT ON COLUMN "gen_table_column"."is_query" IS '是否查询字段（1是）';
COMMENT ON COLUMN "gen_table_column"."query_type" IS '查询方式（等于、不等于、大于、小于、范围）';
COMMENT ON COLUMN "gen_table_column"."html_type" IS '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）';
COMMENT ON COLUMN "gen_table_column"."dict_type" IS '字典类型';
COMMENT ON COLUMN "gen_table_column"."sort" IS '排序';
COMMENT ON COLUMN "gen_table_column"."create_by" IS '创建者';
COMMENT ON COLUMN "gen_table_column"."create_time" IS '创建时间';
COMMENT ON COLUMN "gen_table_column"."update_by" IS '更新者';
COMMENT ON COLUMN "gen_table_column"."update_time" IS '更新时间';
COMMENT ON TABLE "gen_table_column" IS '代码生成业务表字段';

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS "hibernate_sequence";
CREATE TABLE "hibernate_sequence" (
  "next_val" int8
)
;
COMMENT ON TABLE "hibernate_sequence" IS '序列表';

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for nt_data_source_config
-- ----------------------------
DROP TABLE IF EXISTS "nt_data_source_config";
CREATE TABLE "nt_data_source_config" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "connect_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "db_type" varchar(20) COLLATE "pg_catalog"."default",
  "ds_type" varchar(20) COLLATE "pg_catalog"."default",
  "db_name" varchar(50) COLLATE "pg_catalog"."default",
  "ip_address" varchar(20) COLLATE "pg_catalog"."default",
  "port" int4,
  "user_name" varchar(20) COLLATE "pg_catalog"."default",
  "password" varchar(32) COLLATE "pg_catalog"."default",
  "driver_name" varchar(255) COLLATE "pg_catalog"."default",
  "url" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "dept_code" varchar(40) COLLATE "pg_catalog"."default",
  "dept_name" varchar(40) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "nt_data_source_config"."id" IS '主键';
COMMENT ON COLUMN "nt_data_source_config"."connect_name" IS '连接名称';
COMMENT ON COLUMN "nt_data_source_config"."db_type" IS '数据库类型';
COMMENT ON COLUMN "nt_data_source_config"."ds_type" IS '数据源类型';
COMMENT ON COLUMN "nt_data_source_config"."db_name" IS '数据库名称';
COMMENT ON COLUMN "nt_data_source_config"."ip_address" IS 'ip地址';
COMMENT ON COLUMN "nt_data_source_config"."port" IS '端口';
COMMENT ON COLUMN "nt_data_source_config"."user_name" IS '账号';
COMMENT ON COLUMN "nt_data_source_config"."password" IS '密码';
COMMENT ON COLUMN "nt_data_source_config"."driver_name" IS '驱动名称';
COMMENT ON COLUMN "nt_data_source_config"."url" IS '连接地址';
COMMENT ON COLUMN "nt_data_source_config"."create_time" IS '创建时间';
COMMENT ON COLUMN "nt_data_source_config"."update_time" IS '更新时间';
COMMENT ON COLUMN "nt_data_source_config"."dept_code" IS '部门编号';
COMMENT ON COLUMN "nt_data_source_config"."dept_name" IS '部门名称';
COMMENT ON TABLE "nt_data_source_config" IS '数据源表';

-- ----------------------------
-- Records of nt_data_source_config
-- ----------------------------
BEGIN;
INSERT INTO "nt_data_source_config" VALUES ('4028e591736543e001736547e9950000', 'cloud', 'mysql', 'db', 'cloud', '192.168.1.1', 3306, 'root', '1234', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://192.168.1.1:3306/cloud', '2020-07-19 12:14:38', '2020-07-19 14:29:33', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for nt_gateway_route
-- ----------------------------
DROP TABLE IF EXISTS "nt_gateway_route";
CREATE TABLE "nt_gateway_route" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "filters" varchar(255) COLLATE "pg_catalog"."default",
  "predicates" varchar(255) COLLATE "pg_catalog"."default",
  "route_order" int4 NOT NULL,
  "uri" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "status" int4
)
;
COMMENT ON COLUMN "nt_gateway_route"."id" IS '主键';
COMMENT ON COLUMN "nt_gateway_route"."filters" IS '过滤规则';
COMMENT ON COLUMN "nt_gateway_route"."predicates" IS '表达式';
COMMENT ON COLUMN "nt_gateway_route"."route_order" IS '排序';
COMMENT ON COLUMN "nt_gateway_route"."uri" IS '地址';
COMMENT ON COLUMN "nt_gateway_route"."status" IS '状态';
COMMENT ON TABLE "nt_gateway_route" IS '路由表';

-- ----------------------------
-- Records of nt_gateway_route
-- ----------------------------
BEGIN;
INSERT INTO "nt_gateway_route" VALUES ('oner365-files', '[{"name":"StripPrefix","args":{"parts":"1"}}]', '[{"name":"Path","args":{"pattern":"/files/**"}}]', 1, 'lb://oner365-files', 1);
INSERT INTO "nt_gateway_route" VALUES ('oner365-monitor', '[{"args": {"parts": "1"}, "name": "StripPrefix"}]', '[{"args": {"pattern": "/monitor/**"}, "name": "Path"}]', 1, 'lb://oner365-monitor', 1);
INSERT INTO "nt_gateway_route" VALUES ('oner365-system', '[{"args": {"parts": "1"}, "name": "StripPrefix"}]', '[{"args": {"pattern": "/system/**"}, "name": "Path"}]', 1, 'lb://oner365-system', 1);
INSERT INTO "nt_gateway_route" VALUES ('oner365-elasticsearch', '[{"args": {"parts": "1"}, "name": "StripPrefix"}]', '[{"args": {"pattern": "/elasticsearch/**"}, "name": "Path"}]', 1, 'lb://oner365-elasticsearch', 1);
INSERT INTO "nt_gateway_route" VALUES ('oner365-generator', '[{"args": {"parts": "1"}, "name": "StripPrefix"}]', '[{"args": {"pattern": "/generator/**"}, "name": "Path"}]', 1, 'lb://oner365-generator', 1);
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS "nt_sys_dict_item";
CREATE TABLE "nt_sys_dict_item" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_item_type_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_item_code" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_item_name" varchar(32) COLLATE "pg_catalog"."default",
  "dict_item_order" int4,
  "parent_id" varchar(255) COLLATE "pg_catalog"."default",
  "status" int4
)
;
COMMENT ON COLUMN "nt_sys_dict_item"."id" IS '主键';
COMMENT ON COLUMN "nt_sys_dict_item"."dict_item_type_id" IS '字典类型编号';
COMMENT ON COLUMN "nt_sys_dict_item"."dict_item_code" IS '字典编号';
COMMENT ON COLUMN "nt_sys_dict_item"."dict_item_name" IS '字典名称';
COMMENT ON COLUMN "nt_sys_dict_item"."dict_item_order" IS '排序';
COMMENT ON COLUMN "nt_sys_dict_item"."parent_id" IS '上级id';
COMMENT ON COLUMN "nt_sys_dict_item"."status" IS '状态';
COMMENT ON TABLE "nt_sys_dict_item" IS '字典表';

-- ----------------------------
-- Records of nt_sys_dict_item
-- ----------------------------
BEGIN;
INSERT INTO "nt_sys_dict_item" VALUES ('1101', 'sys_normal_disable', '1', '有效', 1, NULL, '1');
INSERT INTO "nt_sys_dict_item" VALUES ('1102', 'sys_normal_disable', '0', '无效', 2, NULL, '1');
INSERT INTO "nt_sys_dict_item" VALUES ('1103', 'sys_user_sex', 'MALE', '男', 1, NULL, '1');
INSERT INTO "nt_sys_dict_item" VALUES ('1104', 'sys_user_sex', 'FEMALE', '女', 2, NULL, '1');
INSERT INTO "nt_sys_dict_item" VALUES ('1105', 'sys_status', 'YES', '有效', 1, NULL, '1');
INSERT INTO "nt_sys_dict_item" VALUES ('1106', 'sys_status', 'NO', '无效', 2, NULL, '1');
INSERT INTO "nt_sys_dict_item" VALUES ('4028b88174aa011e0174aa0a28c30004', 'sys_task_group', 'DEFAULT', '默认', 1, NULL, '1');
INSERT INTO "nt_sys_dict_item" VALUES ('4028b88174aa011e0174aa0a51bc0005', 'sys_task_group', 'SYSTEM', '系统', 2, NULL, '1');
INSERT INTO "nt_sys_dict_item" VALUES ('4028b88174aa011e0174aa0b35eb0006', 'sys_task_status', 'NORMAL', '正常', 1, NULL, '1');
INSERT INTO "nt_sys_dict_item" VALUES ('4028b88174aa011e0174aa0b66630007', 'sys_task_status', 'PAUSE', '暂停', 2, NULL, '1');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_dict_item_type
-- ----------------------------
DROP TABLE IF EXISTS "nt_sys_dict_item_type";
CREATE TABLE "nt_sys_dict_item_type" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_item_type_name" varchar(32) COLLATE "pg_catalog"."default",
  "dict_type_code" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_item_type_des" varchar(255) COLLATE "pg_catalog"."default",
  "dict_item_type_order" int4,
  "status" int4
)
;
COMMENT ON COLUMN "nt_sys_dict_item_type"."id" IS '主键';
COMMENT ON COLUMN "nt_sys_dict_item_type"."dict_item_type_name" IS '类型名称';
COMMENT ON COLUMN "nt_sys_dict_item_type"."dict_type_code" IS '类型编号';
COMMENT ON COLUMN "nt_sys_dict_item_type"."dict_item_type_des" IS '描述';
COMMENT ON COLUMN "nt_sys_dict_item_type"."dict_item_type_order" IS '排序';
COMMENT ON COLUMN "nt_sys_dict_item_type"."status" IS '状态';
COMMENT ON TABLE "nt_sys_dict_item_type" IS '字典类型表';

-- ----------------------------
-- Records of nt_sys_dict_item_type
-- ----------------------------
BEGIN;
INSERT INTO "nt_sys_dict_item_type" VALUES ('sys_normal_disable', '状态', 'sys_normal_disable', '22ee22', NULL, '1');
INSERT INTO "nt_sys_dict_item_type" VALUES ('sys_task_group', '任务分组', 'sys_task_group', '任务分组', NULL, '1');
INSERT INTO "nt_sys_dict_item_type" VALUES ('sys_task_status', '任务状态', 'sys_task_status', NULL, NULL, '1');
INSERT INTO "nt_sys_dict_item_type" VALUES ('sys_user_sex', '性别', 'sys_user_sex', '111', NULL, '1');
INSERT INTO "nt_sys_dict_item_type" VALUES ('sys_status', '状态', 'sys_status', NULL, NULL, '1');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_fastdfs_file
-- ----------------------------
DROP TABLE IF EXISTS "nt_sys_fastdfs_file";
CREATE TABLE "nt_sys_fastdfs_file" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6),
  "display_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "fastdfs_url" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "file_storage" varchar(8) COLLATE "pg_catalog"."default" NOT NULL,
  "file_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "file_path" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "file_suffix" varchar(8) COLLATE "pg_catalog"."default" NOT NULL,
  "is_directory" bool,
  "file_size" varchar(64) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of nt_sys_fastdfs_file
-- ----------------------------
BEGIN;
INSERT INTO "nt_sys_fastdfs_file" VALUES ('2022-05-14/timg.jpg', '2022-05-14 17:56:55.882', 'timg.jpg', 'http://192.168.213.128:9001/oner365-springboot', 'MINIO', 'timg.jpg', 'http://192.168.213.128:9001/oner365-springboot/2022-05-14/timg.jpg', 'jpg', 'f', '5.6 KB');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_job
-- ----------------------------
DROP TABLE IF EXISTS "nt_sys_job";
CREATE TABLE "nt_sys_job" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "job_name" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "job_info" varchar(32) COLLATE "pg_catalog"."default",
  "job_logo" varchar(32) COLLATE "pg_catalog"."default",
  "job_logo_url" varchar(32) COLLATE "pg_catalog"."default",
  "job_order" int4 NOT NULL,
  "parent_id" varchar(32) COLLATE "pg_catalog"."default",
  "status" int4 NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "nt_sys_job"."id" IS '主键';
COMMENT ON COLUMN "nt_sys_job"."job_name" IS '职位名称';
COMMENT ON COLUMN "nt_sys_job"."job_info" IS '职位信息';
COMMENT ON COLUMN "nt_sys_job"."job_logo" IS '职位Logo';
COMMENT ON COLUMN "nt_sys_job"."job_logo_url" IS 'Logo地址';
COMMENT ON COLUMN "nt_sys_job"."job_order" IS '排序';
COMMENT ON COLUMN "nt_sys_job"."parent_id" IS '上级id';
COMMENT ON COLUMN "nt_sys_job"."status" IS '状态';
COMMENT ON COLUMN "nt_sys_job"."create_time" IS '创建时间';
COMMENT ON COLUMN "nt_sys_job"."update_time" IS '更新时间';
COMMENT ON TABLE "nt_sys_job" IS '用户职位表';

-- ----------------------------
-- Records of nt_sys_job
-- ----------------------------
BEGIN;
INSERT INTO "nt_sys_job" VALUES ('1', '研发部', '管理员', NULL, NULL, 1, '1', 1, '2020-04-24 14:31:41', '2020-09-05 20:43:36');
INSERT INTO "nt_sys_job" VALUES ('4028b881745e46ae01745e4c3cd90008', '财务部', '财务', NULL, NULL, 2, NULL, 0, '2020-09-05 20:44:49', '2020-09-18 22:36:12');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_config
-- ----------------------------
DROP TABLE IF EXISTS "nt_sys_config";
CREATE TABLE "nt_sys_config" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "config_name" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "config_value" varchar(255) COLLATE "pg_catalog"."default",
  "status" int4 NOT NULL,
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6) NOT NULL,
  "update_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "nt_sys_config"."id" IS '主键';
COMMENT ON COLUMN "nt_sys_config"."config_name" IS '配置名称';
COMMENT ON COLUMN "nt_sys_config"."config_value" IS '配置内容';
COMMENT ON COLUMN "nt_sys_config"."status" IS '状态';
COMMENT ON COLUMN "nt_sys_config"."create_user" IS '创建人';
COMMENT ON COLUMN "nt_sys_config"."create_time" IS '创建时间';
COMMENT ON COLUMN "nt_sys_config"."update_user" IS '更新人';
COMMENT ON COLUMN "nt_sys_config"."update_time" IS '更新时间';
COMMENT ON TABLE "nt_sys_config" IS '系统配置表';
ALTER TABLE "nt_sys_config" ADD CONSTRAINT "nt_sys_config_pkey" PRIMARY KEY ("id");
CREATE UNIQUE INDEX "idx_config_name" ON "nt_sys_config" USING btree (
  "config_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_log
-- ----------------------------
DROP TABLE IF EXISTS "nt_sys_log";
CREATE TABLE "nt_sys_log" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "operation_ip" varchar(32) COLLATE "pg_catalog"."default",
  "method_name" varchar(8) COLLATE "pg_catalog"."default",
  "operation_name" varchar(255) COLLATE "pg_catalog"."default",
  "operation_path" varchar(255) COLLATE "pg_catalog"."default",
  "operation_context" text COLLATE "pg_catalog"."default",
  "create_time" timestamp(6)
)
;
COMMENT ON COLUMN "nt_sys_log"."id" IS '主键';
COMMENT ON COLUMN "nt_sys_log"."operation_ip" IS '操作ip';
COMMENT ON COLUMN "nt_sys_log"."method_name" IS '请求方法';
COMMENT ON COLUMN "nt_sys_log"."operation_name" IS '名称';
COMMENT ON COLUMN "nt_sys_log"."operation_path" IS '请求路径';
COMMENT ON COLUMN "nt_sys_log"."operation_context" IS '请求内容';
COMMENT ON COLUMN "nt_sys_log"."create_time" IS '创建时间';
COMMENT ON TABLE "nt_sys_log" IS '系统日志表';

-- ----------------------------
-- Records of nt_sys_log
-- ----------------------------
BEGIN;
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1c1e50180c1c24e620000', '::1', 'POST', 'system', '/system/auth/login', NULL, '2022-05-14 16:49:38.365475');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1caf30180c1cb402c0000', '::1', 'POST', 'system', '/system/auth/login', NULL, '2022-05-14 16:59:24.56194');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1caf30180c1cb42400001', '::1', 'POST', 'route', '/route/list', NULL, '2022-05-14 16:59:25.118995');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1caf30180c1cb424a0002', '::1', 'POST', 'system', '/system/user/list', NULL, '2022-05-14 16:59:25.12033');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1caf30180c1cb42920003', '::1', 'POST', 'monitor', '/monitor/task/list', NULL, '2022-05-14 16:59:25.201528');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1caf30180c1cb99420004', '::1', 'POST', 'system', '/system/role/list', NULL, '2022-05-14 16:59:47.392333');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1cd190180c1cdcb710000', '::1', 'POST', 'system', '/system/job/list', NULL, '2022-05-14 17:02:11.287321');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1cd190180c1cdd0660001', '::1', 'POST', 'system', '/system/user/list', NULL, '2022-05-14 17:02:12.580937');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1cd190180c1cddbcd0002', '::1', 'POST', 'system', '/system/org/user/2', NULL, '2022-05-14 17:02:15.499834');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1cf540180c1cf8bb70000', '::1', 'POST', 'system', '/system/user/list', NULL, '2022-05-14 17:04:06.040464');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1cf540180c1cfa2970001', '::1', 'POST', 'system', '/system/org/user/2', NULL, '2022-05-14 17:04:11.924694');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1cf540180c1cfeb670002', '::1', 'POST', 'system', '/system/org/user/2', NULL, '2022-05-14 17:04:30.566147');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fd5db20000', '::1', 'POST', 'system', '/system/org/list', NULL, '2022-05-14 17:54:08.918641');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fd63a20001', '::1', 'POST', 'system', '/system/org/list', NULL, '2022-05-14 17:54:10.464993');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fd71290002', '::1', 'POST', 'system', '/system/role/list', NULL, '2022-05-14 17:54:13.928306');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fd7f940003', '::1', 'POST', 'system', '/system/menus/role/ff80808172a624c00172a67c70c30007', NULL, '2022-05-14 17:54:17.618337');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fd90b50004', '::1', 'POST', 'system', '/system/menus/role/ff80808172a150110172a159d9c40000', NULL, '2022-05-14 17:54:22.003712');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fda9a40005', '::1', 'POST', 'system', '/system/menus/role/1', NULL, '2022-05-14 17:54:28.387166');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fdc7450006', '::1', 'POST', 'system', '/system/menus/role/ff80808172a150110172a159d9c40000', NULL, '2022-05-14 17:54:35.972359');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fdf1750007', '::1', 'POST', 'system', '/system/role/check', NULL, '2022-05-14 17:54:46.772287');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fdf19f0008', '::1', 'PUT', 'system', '/system/role/save', NULL, '2022-05-14 17:54:46.814366');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fdf2030009', '::1', 'POST', 'system', '/system/role/list', NULL, '2022-05-14 17:54:46.914108');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fe001e000a', '::1', 'POST', 'system', '/system/menus/role/ff80808172a150110172a159d9c40000', NULL, '2022-05-14 17:54:50.525953');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fe0d61000b', '::1', 'POST', 'system', '/system/job/list', NULL, '2022-05-14 17:54:53.920519');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fe2214000c', '::1', 'POST', 'system', '/system/user/list', NULL, '2022-05-14 17:54:59.219802');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fe28c4000d', '::1', 'POST', 'system', '/system/org/user/2', NULL, '2022-05-14 17:55:00.931073');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fe3805000e', '::1', 'POST', 'system', '/system/org/user/2', NULL, '2022-05-14 17:55:04.836711');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fe4655000f', '::1', 'PUT', 'system', '/system/user/save', NULL, '2022-05-14 17:55:08.499932');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fe46ba0014', '::1', 'POST', 'system', '/system/user/list', NULL, '2022-05-14 17:55:08.601942');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fe4d3e0015', '::1', 'POST', 'system', '/system/org/user/1', NULL, '2022-05-14 17:55:10.269142');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fe589f0016', '::1', 'POST', 'system', '/system/org/user/5', NULL, '2022-05-14 17:55:13.182626');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fe6d110017', '::1', 'POST', 'system', '/system/org/user/5', NULL, '2022-05-14 17:55:18.416325');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fe969b0018', '::1', 'POST', 'system', '/system/org/user/5', NULL, '2022-05-14 17:55:29.050668');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1feb0160019', '::1', 'POST', 'system', '/system/org/user/5', NULL, '2022-05-14 17:55:35.573429');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fee1f2001a', '::1', 'PUT', 'system', '/system/user/save', NULL, '2022-05-14 17:55:48.337599');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1fee240001f', '::1', 'POST', 'system', '/system/user/list', NULL, '2022-05-14 17:55:48.415437');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1feeff90020', '::1', 'POST', 'system', '/system/org/user/5', NULL, '2022-05-14 17:55:51.929148');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1ff14880021', '::1', 'POST', 'monitor', '/monitor/task/list', NULL, '2022-05-14 17:56:01.287417');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1ff22c70022', '::1', 'PUT', 'monitor', '/monitor/task/run', NULL, '2022-05-14 17:56:04.934571');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1ff3b060024', '::1', 'POST', 'monitor', '/monitor/taskLog/list', NULL, '2022-05-14 17:56:11.141692');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1ff55ac0025', '::1', 'POST', 'monitor', '/monitor/task/list', NULL, '2022-05-14 17:56:17.963896');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1ff5fba0026', '::1', 'PUT', 'monitor', '/monitor/task/run', NULL, '2022-05-14 17:56:20.537896');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1ff7e730028', '::1', 'POST', 'system', '/system/log/list', NULL, '2022-05-14 17:56:28.403081');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1ff89a90029', '::1', 'POST', 'system', '/system/log/list', NULL, '2022-05-14 17:56:31.272731');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1ff9d1b002a', '::1', 'POST', 'files', '/files/storage/list', NULL, '2022-05-14 17:56:36.250639');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1ffb820002b', '::1', 'POST', 'system', '/system/log/list', NULL, '2022-05-14 17:56:43.168124');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1ffc3f2002c', '::1', 'POST', 'files', '/files/storage/list', NULL, '2022-05-14 17:56:46.193856');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1ffe9aa002d', '::1', 'POST', 'files', '/files/storage/upload', NULL, '2022-05-14 17:56:55.850155');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c1ffe9fd002e', '::1', 'POST', 'files', '/files/storage/list', NULL, '2022-05-14 17:56:55.933055');
INSERT INTO "nt_sys_log" VALUES ('ff80808180c1fc800180c200030f002f', '::1', 'POST', 'files', '/files/storage/list', NULL, '2022-05-14 17:57:02.351216');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS "nt_sys_menu";
CREATE TABLE "nt_sys_menu" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_type_id" varchar(255) COLLATE "pg_catalog"."default",
  "menu_name" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "parent_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "component" varchar(64) COLLATE "pg_catalog"."default",
  "path" varchar(64) COLLATE "pg_catalog"."default",
  "menu_order" int4 NOT NULL,
  "menu_description" varchar(255) COLLATE "pg_catalog"."default",
  "icon" varchar(255) COLLATE "pg_catalog"."default",
  "status" int4,
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "another_name" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "nt_sys_menu"."id" IS '主键';
COMMENT ON COLUMN "nt_sys_menu"."menu_type_id" IS '菜单类型编号';
COMMENT ON COLUMN "nt_sys_menu"."menu_name" IS '菜单名称';
COMMENT ON COLUMN "nt_sys_menu"."parent_id" IS '上级菜单';
COMMENT ON COLUMN "nt_sys_menu"."component" IS '元素';
COMMENT ON COLUMN "nt_sys_menu"."path" IS '路径';
COMMENT ON COLUMN "nt_sys_menu"."menu_order" IS '排序';
COMMENT ON COLUMN "nt_sys_menu"."menu_description" IS '描述';
COMMENT ON COLUMN "nt_sys_menu"."icon" IS 'Logo';
COMMENT ON COLUMN "nt_sys_menu"."status" IS '状态';
COMMENT ON COLUMN "nt_sys_menu"."create_time" IS '创建时间';
COMMENT ON COLUMN "nt_sys_menu"."update_time" IS '更新时间';
COMMENT ON COLUMN "nt_sys_menu"."another_name" IS '别名';
COMMENT ON TABLE "nt_sys_menu" IS '系统菜单表';

-- ----------------------------
-- Records of nt_sys_menu
-- ----------------------------
BEGIN;
INSERT INTO "nt_sys_menu" VALUES ('101', '1', '系统设置', '-1', 'Layout', '/system', 1, '系统设置', 'system', 1, '2020-05-11 14:00:21', '2020-05-11 14:00:25', NULL);
INSERT INTO "nt_sys_menu" VALUES ('1011', '1', '单位管理', '101', 'system/org/index', '/org', 11, '单位管理', 'tree', 1, '2020-05-11 14:03:06', '2020-05-11 14:03:11', NULL);
INSERT INTO "nt_sys_menu" VALUES ('1012', '1', '角色管理', '101', 'system/role/index', '/role', 12, '角色管理', 'peoples', 1, '2020-05-11 14:08:39', '2020-05-11 14:08:43', NULL);
INSERT INTO "nt_sys_menu" VALUES ('1013', '1', '用户职位', '101', 'system/job/index', '/job', 13, '用户职位', 'post', 1, '2020-08-05 21:48:33', '2020-08-05 21:48:37', NULL);
INSERT INTO "nt_sys_menu" VALUES ('1014', '1', '用户管理', '101', 'system/user/index', '/user', 14, '用户管理', 'user', 1, '2020-09-05 20:30:00', '2020-09-05 20:30:00', NULL);
INSERT INTO "nt_sys_menu" VALUES ('1015', '1', '字典管理', '101', 'system/dict/index', '/dict', 15, '字典管理', 'dict', 1, '2020-09-05 20:30:00', '2020-09-05 20:30:00', NULL);
INSERT INTO "nt_sys_menu" VALUES ('1016', '1', '菜单管理', '101', 'system/menu/index', '/menu', 16, '菜单管理', 'tree-table', 1, '2020-09-05 20:30:00', '2020-09-05 20:30:00', NULL);
INSERT INTO "nt_sys_menu" VALUES ('201', '1', '系统监控', '-1', 'Layout', '/monitor', 2, '系统监控', 'monitor', 1, '2020-09-03 17:20:13', '2020-09-17 20:57:13', NULL);
INSERT INTO "nt_sys_menu" VALUES ('2011', '1', '服务监控', '201', 'monitor/service/index', '/service', 21, '服务监控', 'druid', 1, '2020-09-03 17:21:32', '2020-09-17 20:57:02', NULL);
INSERT INTO "nt_sys_menu" VALUES ('2012', '1', '服务器监控', '201', 'monitor/server/index', '/server', 22, '服务器监控', 'server', 1, '2020-09-03 17:22:24', '2020-09-12 17:27:11', NULL);
INSERT INTO "nt_sys_menu" VALUES ('2013', '1', '定时任务', '201', 'monitor/task/index', '/task', 23, '定时任务', 'job', 1, '2020-09-19 11:57:23', '2020-09-19 11:57:25', NULL);
INSERT INTO "nt_sys_menu" VALUES ('2014', '1', '路由监控', '201', 'monitor/route/index', '/route', 24, '路由监控', 'cascader', 1, '2020-09-19 11:57:23', '2020-09-19 11:57:23', NULL);
INSERT INTO "nt_sys_menu" VALUES ('2015', '1', '缓存监控', '201', 'monitor/cache/index', '/cache', 25, NULL, 'time-range', 1, '2020-12-08 14:00:34', '2020-12-08 14:00:51', NULL);
INSERT INTO "nt_sys_menu" VALUES ('2016', '1', 'ES监控', '201', 'monitor/elasticsearch/index', '/elasticsearch', 26, 'ES监控', 'drag', 1, '2020-12-25 14:47:37', '2020-12-25 14:50:14', NULL);
INSERT INTO "nt_sys_menu" VALUES ('2017', '1', '队列监控', '201', 'monitor/rabbitmq/index', '/rabbitmq', 27, '队列监控', 'example', 1, '2020-12-25 14:48:35', '2020-12-25 14:50:19', NULL);
INSERT INTO "nt_sys_menu" VALUES ('2018', '1', '文档监控', '201', 'monitor/fastdfs/index', '/fastdfs', 28, '文档监控', 'documentation', 1, '2020-12-25 14:49:33', '2020-12-25 14:50:24', NULL);
INSERT INTO "nt_sys_menu" VALUES ('2019', '1', '数据源监控', '201', 'monitor/druid/index', '/druid', 29, '数据源监控', 'druid', 1, '2020-12-25 14:49:33', '2020-12-25 14:50:24', NULL);
INSERT INTO "nt_sys_menu" VALUES ('301', '1', '日志管理', '-1', 'Layout', '/log', 3, '日志管理', 'log', 1, '2020-11-16 15:36:00', '2020-11-16 15:36:00', NULL);
INSERT INTO "nt_sys_menu" VALUES ('3011', '1', '服务日志', '301', 'system/log/index', '/syslog', 31, '服务日志', 'druid', 1, '2020-11-16 15:36:00', '2020-11-16 15:36:00', NULL);
INSERT INTO "nt_sys_menu" VALUES ('3012', '1', '应用日志', '301', 'application/log/index', '/application/log', 32, '应用日志', 'bug', 1, '2020-11-16 15:36:00', '2020-11-16 15:36:00', NULL);
INSERT INTO "nt_sys_menu" VALUES ('401', '1', '生成管理', '-1', 'Layout', '/generator', 4, '生成管理', 'money', 1, '2021-10-26 11:09:27', '2021-10-26 11:10:35', NULL);
INSERT INTO "nt_sys_menu" VALUES ('4011', '1', '生成框架', '401', 'tool/gen/index', '/generator/gen', 1, '生成框架', 'system', 1, '2021-10-26 11:12:05', '2021-10-26 11:17:07', NULL);
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_menu_oper
-- ----------------------------
DROP TABLE IF EXISTS "nt_sys_menu_oper";
CREATE TABLE "nt_sys_menu_oper" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "operation_id" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "nt_sys_menu_oper"."id" IS '主键';
COMMENT ON COLUMN "nt_sys_menu_oper"."menu_id" IS '菜单编号';
COMMENT ON COLUMN "nt_sys_menu_oper"."operation_id" IS '操作编号';
COMMENT ON TABLE "nt_sys_menu_oper" IS '菜单操作权限表';

-- ----------------------------
-- Records of nt_sys_menu_oper
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_menu_type
-- ----------------------------
DROP TABLE IF EXISTS "nt_sys_menu_type";
CREATE TABLE "nt_sys_menu_type" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "type_code" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "type_name" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "status" int4,
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "nt_sys_menu_type"."id" IS '主键';
COMMENT ON COLUMN "nt_sys_menu_type"."type_code" IS '类型标识';
COMMENT ON COLUMN "nt_sys_menu_type"."type_name" IS '类型名称';
COMMENT ON COLUMN "nt_sys_menu_type"."status" IS '状态';
COMMENT ON COLUMN "nt_sys_menu_type"."create_time" IS '创建时间';
COMMENT ON COLUMN "nt_sys_menu_type"."update_time" IS '更新时间';
COMMENT ON TABLE "nt_sys_menu_type" IS '菜单类型表';

-- ----------------------------
-- Records of nt_sys_menu_type
-- ----------------------------
BEGIN;
INSERT INTO "nt_sys_menu_type" VALUES ('1', 'nt_sys', '统一用户认证', 1, '2013-01-01 10:00:00', '2013-01-01 10:00:00');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_message
-- ----------------------------
DROP TABLE IF EXISTS "nt_sys_message";
CREATE TABLE "nt_sys_message" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "queue_type" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "queue_key" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "message_type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "message_name" varchar(64) COLLATE "pg_catalog"."default",
  "type_id" varchar(64) COLLATE "pg_catalog"."default",
  "context" varchar(255) COLLATE "pg_catalog"."default",
  "send_user" varchar(32) COLLATE "pg_catalog"."default",
  "receive_user" varchar(32) COLLATE "pg_catalog"."default",
  "status" int4,
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "nt_sys_message"."id" IS '主键';
COMMENT ON COLUMN "nt_sys_message"."queue_type" IS '队列类型';
COMMENT ON COLUMN "nt_sys_message"."queue_key" IS '队列标识';
COMMENT ON COLUMN "nt_sys_message"."message_type" IS '消息类型';
COMMENT ON COLUMN "nt_sys_message"."message_name" IS '消息名称';
COMMENT ON COLUMN "nt_sys_message"."type_id" IS '类型编号';
COMMENT ON COLUMN "nt_sys_message"."context" IS '内容';
COMMENT ON COLUMN "nt_sys_message"."send_user" IS '发送人';
COMMENT ON COLUMN "nt_sys_message"."receive_user" IS '接收人';
COMMENT ON COLUMN "nt_sys_message"."status" IS '状态';
COMMENT ON COLUMN "nt_sys_message"."create_time" IS '创建时间';
COMMENT ON COLUMN "nt_sys_message"."update_time" IS '更新时间';
COMMENT ON TABLE "nt_sys_message" IS '系统消息表';

-- ----------------------------
-- Records of nt_sys_message
-- ----------------------------
BEGIN;
INSERT INTO "nt_sys_message" VALUES ('40288112729c3ca101729cab601d0000', 'sysMessageDirect', 'sysMessageKey', 'test', '测试', NULL, '内容', 'admin', 'admin', 1, '2020-06-10 13:19:20', '2020-06-10 13:19:20');
INSERT INTO "nt_sys_message" VALUES ('ff80808174dcef8f0174dcf068ec0000', 'sysMessageDirect', 'sysMessageKey', 'test', '测试', NULL, '123456', 'admin', 'admin', 1, '2020-09-30 10:56:17', NULL);
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_operation
-- ----------------------------
DROP TABLE IF EXISTS "nt_sys_operation";
CREATE TABLE "nt_sys_operation" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "operation_type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "operation_name" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "status" int4,
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "nt_sys_operation"."id" IS '主键';
COMMENT ON COLUMN "nt_sys_operation"."operation_type" IS '操作类型';
COMMENT ON COLUMN "nt_sys_operation"."operation_name" IS '操作名称';
COMMENT ON COLUMN "nt_sys_operation"."status" IS '状态';
COMMENT ON COLUMN "nt_sys_operation"."create_time" IS '创建时间';
COMMENT ON COLUMN "nt_sys_operation"."update_time" IS '更新时间';
COMMENT ON TABLE "nt_sys_operation" IS '操作权限表';

-- ----------------------------
-- Records of nt_sys_operation
-- ----------------------------
BEGIN;
INSERT INTO "nt_sys_operation" VALUES ('1', 'select', '查询', 1, '2013-01-01 10:00:00', '2013-01-01 10:00:00');
INSERT INTO "nt_sys_operation" VALUES ('2', 'add', '添加', 1, '2013-01-01 10:00:00', '2013-01-01 10:00:00');
INSERT INTO "nt_sys_operation" VALUES ('3', 'edit', '编辑', 1, '2013-01-01 10:00:00', '2020-07-19 13:09:58');
INSERT INTO "nt_sys_operation" VALUES ('4', 'save', '保存', 1, '2013-01-01 10:00:00', '2020-07-19 14:12:47');
INSERT INTO "nt_sys_operation" VALUES ('5', 'delete', '删除', 1, '2013-01-01 10:00:00', '2013-01-01 10:00:00');
INSERT INTO "nt_sys_operation" VALUES ('6', 'imported', '导入', 1, '2013-01-01 10:00:00', '2013-01-01 10:00:00');
INSERT INTO "nt_sys_operation" VALUES ('7', 'exported', '导出', 1, '2013-01-01 10:00:00', '2013-01-01 10:00:00');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_organization
-- ----------------------------
DROP TABLE IF EXISTS "nt_sys_organization";
CREATE TABLE "nt_sys_organization" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "org_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "org_code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "parent_id" varchar(32) COLLATE "pg_catalog"."default",
  "ancestors" varchar(255) COLLATE "pg_catalog"."default",
  "org_order" int4,
  "org_area_code" varchar(255) COLLATE "pg_catalog"."default",
  "org_credit_code" varchar(255) COLLATE "pg_catalog"."default",
  "org_logo" varchar(64) COLLATE "pg_catalog"."default",
  "org_logo_url" varchar(64) COLLATE "pg_catalog"."default",
  "org_type" varchar(32) COLLATE "pg_catalog"."default",
  "create_user" varchar(64) COLLATE "pg_catalog"."default",
  "status" int4 NOT NULL,
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "business_name" varchar(32) COLLATE "pg_catalog"."default",
  "business_phone" varchar(32) COLLATE "pg_catalog"."default",
  "technical_name" varchar(32) COLLATE "pg_catalog"."default",
  "technical_phone" varchar(32) COLLATE "pg_catalog"."default",
  "config_id" varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "nt_sys_organization"."id" IS '主键';
COMMENT ON COLUMN "nt_sys_organization"."org_name" IS '机构名称';
COMMENT ON COLUMN "nt_sys_organization"."org_code" IS '机构编号';
COMMENT ON COLUMN "nt_sys_organization"."parent_id" IS '上级编号';
COMMENT ON COLUMN "nt_sys_organization"."ancestors" IS '总称';
COMMENT ON COLUMN "nt_sys_organization"."org_order" IS '编号';
COMMENT ON COLUMN "nt_sys_organization"."org_area_code" IS '地区';
COMMENT ON COLUMN "nt_sys_organization"."org_credit_code" IS '行政区号';
COMMENT ON COLUMN "nt_sys_organization"."org_logo" IS 'Logo';
COMMENT ON COLUMN "nt_sys_organization"."org_logo_url" IS 'Logo地址';
COMMENT ON COLUMN "nt_sys_organization"."org_type" IS '类型';
COMMENT ON COLUMN "nt_sys_organization"."create_user" IS '创建人';
COMMENT ON COLUMN "nt_sys_organization"."status" IS '状态';
COMMENT ON COLUMN "nt_sys_organization"."create_time" IS '创建时间';
COMMENT ON COLUMN "nt_sys_organization"."update_time" IS '更新时间';
COMMENT ON COLUMN "nt_sys_organization"."business_name" IS '负责人';
COMMENT ON COLUMN "nt_sys_organization"."business_phone" IS '负责人电话';
COMMENT ON COLUMN "nt_sys_organization"."technical_name" IS '技术负责人';
COMMENT ON COLUMN "nt_sys_organization"."technical_phone" IS '负责人电话';
COMMENT ON COLUMN "nt_sys_organization"."config_id" IS '数据源id';
COMMENT ON TABLE "nt_sys_organization" IS '机构表';

-- ----------------------------
-- Records of nt_sys_organization
-- ----------------------------
BEGIN;
INSERT INTO "nt_sys_organization" VALUES ('1', '北京市', '100000000000', '-1', '-1', 1, NULL, NULL, NULL, NULL, NULL, NULL, 1, '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "nt_sys_organization" VALUES ('110', '北京市', '110000000000', '1', '-1,1', 1, NULL, NULL, NULL, NULL, NULL, NULL, 1, '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO "nt_sys_organization" VALUES ('110101', '北京市东城区', '110101000000', '-1,1,110', '-1', 1, NULL, NULL, NULL, NULL, NULL, NULL, 1, '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_role
-- ----------------------------
DROP TABLE IF EXISTS "nt_sys_role";
CREATE TABLE "nt_sys_role" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "role_code" varchar(32) COLLATE "pg_catalog"."default",
  "role_name" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "role_des" varchar(32) COLLATE "pg_catalog"."default",
  "status" int4,
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "nt_sys_role"."id" IS '主键';
COMMENT ON COLUMN "nt_sys_role"."role_code" IS '角色编号';
COMMENT ON COLUMN "nt_sys_role"."role_name" IS '角色名称';
COMMENT ON COLUMN "nt_sys_role"."role_des" IS '角色描述';
COMMENT ON COLUMN "nt_sys_role"."status" IS '状态';
COMMENT ON COLUMN "nt_sys_role"."create_time" IS '创建时间';
COMMENT ON COLUMN "nt_sys_role"."update_time" IS '更新时间';
COMMENT ON TABLE "nt_sys_role" IS '角色表';

-- ----------------------------
-- Records of nt_sys_role
-- ----------------------------
BEGIN;
INSERT INTO "nt_sys_role" VALUES ('1', '22222222', '超管', '权限最大用户', 1, '2018-10-10 16:08:02', '2020-12-25 14:49:43');
INSERT INTO "nt_sys_role" VALUES ('ff80808172a624c00172a67c70c30007', '003', '用户', '普通用户', 0, '2020-06-12 11:04:33', '2021-05-26 10:06:23');
INSERT INTO "nt_sys_role" VALUES ('ff80808172a150110172a159d9c40000', '002', '职员', '普通职员', 1, '2020-06-11 11:08:40', '2022-05-14 17:54:46.82979');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS "nt_sys_role_menu";
CREATE TABLE "nt_sys_role_menu" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_id" varchar(255) COLLATE "pg_catalog"."default",
  "role_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_type_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "nt_sys_role_menu"."id" IS '主键';
COMMENT ON COLUMN "nt_sys_role_menu"."menu_id" IS '菜单id';
COMMENT ON COLUMN "nt_sys_role_menu"."role_id" IS '角色id';
COMMENT ON COLUMN "nt_sys_role_menu"."menu_type_id" IS '类型id';
COMMENT ON TABLE "nt_sys_role_menu" IS '角色菜单表';

-- ----------------------------
-- Records of nt_sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO "nt_sys_role_menu" VALUES ('11101', '101', '1', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('111011', '1011', '1', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('111012', '1012', '1', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('111013', '1013', '1', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('111014', '1014', '1', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('111015', '1015', '1', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('111016', '1016', '1', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('11201', '201', '1', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('112011', '2011', '1', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('112012', '2012', '1', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('112013', '2013', '1', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('112014', '2014', '1', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('112015', '2015', '1', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('112016', '2016', '1', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('112017', '2017', '1', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('112018', '2018', '1', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('112019', '2019', '1', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('11301', '301', '1', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('113011', '3011', '1', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('113012', '3012', '1', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('11401', '401', '1', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('114011', '4011', '1', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('ff80808172a150110172a159d9c400001401', '401', 'ff80808172a150110172a159d9c40000', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('ff80808172a150110172a159d9c4000014011', '4011', 'ff80808172a150110172a159d9c40000', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('ff80808172a150110172a159d9c4000012017', '2017', 'ff80808172a150110172a159d9c40000', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('ff80808172a150110172a159d9c4000012016', '2016', 'ff80808172a150110172a159d9c40000', '1');
INSERT INTO "nt_sys_role_menu" VALUES ('ff80808172a150110172a159d9c400001201', '201', 'ff80808172a150110172a159d9c40000', '1');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_role_menu_oper
-- ----------------------------
DROP TABLE IF EXISTS "nt_sys_role_menu_oper";
CREATE TABLE "nt_sys_role_menu_oper" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "operation_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "role_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "menu_type_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "nt_sys_role_menu_oper"."id" IS '主键';
COMMENT ON COLUMN "nt_sys_role_menu_oper"."menu_id" IS '菜单id';
COMMENT ON COLUMN "nt_sys_role_menu_oper"."operation_id" IS '操作id';
COMMENT ON COLUMN "nt_sys_role_menu_oper"."role_id" IS '角色id';
COMMENT ON COLUMN "nt_sys_role_menu_oper"."menu_type_id" IS '类型id';
COMMENT ON TABLE "nt_sys_role_menu_oper" IS '角色菜单操作权限表';

-- ----------------------------
-- Records of nt_sys_role_menu_oper
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_sequence
-- ----------------------------
DROP TABLE IF EXISTS "nt_sys_sequence";
CREATE TABLE "nt_sys_sequence" (
  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "table_name" varchar(32) COLLATE "pg_catalog"."default",
  "sequence" int4,
  "status" varchar(8) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6)
)
;
COMMENT ON COLUMN "nt_sys_sequence"."id" IS '主键';
COMMENT ON COLUMN "nt_sys_sequence"."table_name" IS '表名';
COMMENT ON COLUMN "nt_sys_sequence"."sequence" IS '序号';
COMMENT ON COLUMN "nt_sys_sequence"."status" IS '状态';
COMMENT ON COLUMN "nt_sys_sequence"."create_time" IS '创建时间';
COMMENT ON TABLE "nt_sys_sequence" IS '序列表';

-- ----------------------------
-- Records of nt_sys_sequence
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_task
-- ----------------------------
DROP TABLE IF EXISTS "nt_sys_task";
CREATE TABLE "nt_sys_task" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "task_group" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "task_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "concurrent" varchar(1) COLLATE "pg_catalog"."default",
  "invoke_param" varchar COLLATE "pg_catalog"."default",
  "cron_expression" varchar(255) COLLATE "pg_catalog"."default",
  "invoke_target" varchar(1000) COLLATE "pg_catalog"."default" NOT NULL,
  "misfire_policy" int4,
  "remark" varchar(500) COLLATE "pg_catalog"."default",
  "execute_status" int4,
  "status" int4,
  "create_time" timestamp(6),
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "nt_sys_task"."id" IS '主键';
COMMENT ON COLUMN "nt_sys_task"."task_group" IS '任务组';
COMMENT ON COLUMN "nt_sys_task"."task_name" IS '任务名称';
COMMENT ON COLUMN "nt_sys_task"."concurrent" IS '线程';
COMMENT ON COLUMN "nt_sys_task"."invoke_param" IS '参数';
COMMENT ON COLUMN "nt_sys_task"."cron_expression" IS '表达式';
COMMENT ON COLUMN "nt_sys_task"."invoke_target" IS '目标';
COMMENT ON COLUMN "nt_sys_task"."misfire_policy" IS '策略';
COMMENT ON COLUMN "nt_sys_task"."remark" IS '备注';
COMMENT ON COLUMN "nt_sys_task"."execute_status" IS '状态';
COMMENT ON COLUMN "nt_sys_task"."status" IS '状态';
COMMENT ON COLUMN "nt_sys_task"."create_time" IS '创建时间';
COMMENT ON COLUMN "nt_sys_task"."create_user" IS '创建人';
COMMENT ON COLUMN "nt_sys_task"."update_time" IS '更新时间';
COMMENT ON TABLE "nt_sys_task" IS '系统任务表';

-- ----------------------------
-- Records of nt_sys_task
-- ----------------------------
BEGIN;
INSERT INTO "nt_sys_task" VALUES ('8a818b3774bef2910174bef387ec0000', 'DEFAULT', 'test', '1', '{"taskId": "8a818b3774bef2910174bef387ec0000", "taskParam": {}, "concurrent": null, "taskServerName": "oner365-system"}', '0/10 * * * * ?', 'systemTask.taskRun()', 0, '', 1, 0, NULL, 'liutao', NULL);
INSERT INTO "nt_sys_task" VALUES ('ff80808175cfe8900175d012ecc00002', 'DEFAULT', '删除3天日志', '1', '{"taskId": null, "taskParam": {}, "concurrent": null, "taskServerName": null}', '0 0 1 * * ?', 'systemTask.taskDeleteLog(3)', 3, NULL, NULL, 0, NULL, 'admin', NULL);
INSERT INTO "nt_sys_task" VALUES ('ff80808175d015c90175d05958e50001', 'DEFAULT', '111', '1', '{"taskId": null, "taskParam": null, "concurrent": null, "taskServerName": null}', '0 0 1 * * ?', 'systemTask.taskParams(''121'')', 3, NULL, NULL, 0, NULL, 'admin', NULL);
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_task_log
-- ----------------------------
DROP TABLE IF EXISTS "nt_sys_task_log";
CREATE TABLE "nt_sys_task_log" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "task_group" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "task_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "task_message" varchar(500) COLLATE "pg_catalog"."default",
  "executeip" varchar(255) COLLATE "pg_catalog"."default",
  "execute_server_name" varchar(255) COLLATE "pg_catalog"."default",
  "execute_ip" varchar(64) COLLATE "pg_catalog"."default",
  "exception_info" varchar(2000) COLLATE "pg_catalog"."default",
  "invoke_target" varchar(500) COLLATE "pg_catalog"."default" NOT NULL,
  "remark" varchar(500) COLLATE "pg_catalog"."default",
  "start_time" timestamp(6),
  "stop_time" timestamp(6),
  "status" int4,
  "create_time" timestamp(6),
  "create_user" varchar(32) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "nt_sys_task_log"."id" IS '主键';
COMMENT ON COLUMN "nt_sys_task_log"."task_group" IS '任务组';
COMMENT ON COLUMN "nt_sys_task_log"."task_name" IS '任务名称';
COMMENT ON COLUMN "nt_sys_task_log"."task_message" IS '任务消息';
COMMENT ON COLUMN "nt_sys_task_log"."executeip" IS '执行ip';
COMMENT ON COLUMN "nt_sys_task_log"."execute_server_name" IS '服务名称';
COMMENT ON COLUMN "nt_sys_task_log"."execute_ip" IS '执行ip';
COMMENT ON COLUMN "nt_sys_task_log"."exception_info" IS '异常信息';
COMMENT ON COLUMN "nt_sys_task_log"."invoke_target" IS '请求参数';
COMMENT ON COLUMN "nt_sys_task_log"."remark" IS '备注';
COMMENT ON COLUMN "nt_sys_task_log"."start_time" IS '开始时间';
COMMENT ON COLUMN "nt_sys_task_log"."stop_time" IS '停止时间';
COMMENT ON COLUMN "nt_sys_task_log"."status" IS '状态';
COMMENT ON COLUMN "nt_sys_task_log"."create_time" IS '创建时间';
COMMENT ON COLUMN "nt_sys_task_log"."create_user" IS '创建人';
COMMENT ON COLUMN "nt_sys_task_log"."update_time" IS '更新时间';
COMMENT ON TABLE "nt_sys_task_log" IS '任务日志表';

-- ----------------------------
-- Records of nt_sys_task_log
-- ----------------------------
BEGIN;
INSERT INTO "nt_sys_task_log" VALUES ('ff80808175cfe8900175cff033bc0000', 'DEFAULT', 'test', '执行时间：10毫秒', NULL, 'oner365-system', '127.0.0.1', NULL, 'systemTask.taskRun()', NULL, NULL, NULL, 1, '2020-11-16 15:23:47', NULL, NULL);
INSERT INTO "nt_sys_task_log" VALUES ('ff80808175cfe8900175d007c2f00001', 'DEFAULT', 'test', '执行时间：7毫秒', NULL, 'oner365-system', '127.0.0.1', NULL, 'systemTask.taskRun()', NULL, NULL, NULL, 1, '2020-11-16 15:49:31', NULL, NULL);
INSERT INTO "nt_sys_task_log" VALUES ('ff80808175d015c90175d019bced0000', 'DEFAULT', 'test', '执行时间：219毫秒', NULL, 'oner365-system', '127.0.0.1', NULL, 'systemTask.taskRun()', NULL, NULL, NULL, 1, '2020-11-16 16:09:09', NULL, NULL);
INSERT INTO "nt_sys_task_log" VALUES ('ff80808180c1fc800180c1ff23cc0023', 'DEFAULT', 'test', '执行时间：184毫秒', NULL, 'oner365-monitor', '127.0.0.1', NULL, 'systemTask.taskRun()', NULL, NULL, NULL, 1, '2022-05-14 17:56:05.18', NULL, NULL);
INSERT INTO "nt_sys_task_log" VALUES ('ff80808180c1fc800180c1ff5fd70027', 'DEFAULT', '删除3天日志', '执行时间：1毫秒', NULL, 'oner365-monitor', '127.0.0.1', NULL, 'systemTask.taskDeleteLog(3)', NULL, NULL, NULL, 1, '2022-05-14 17:56:20.567', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_user
-- ----------------------------
DROP TABLE IF EXISTS "nt_sys_user";
CREATE TABLE "nt_sys_user" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "user_name" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "real_name" varchar(32) COLLATE "pg_catalog"."default",
  "avatar" varchar(255) COLLATE "pg_catalog"."default",
  "sex" int4,
  "user_type" int4,
  "email" varchar(32) COLLATE "pg_catalog"."default",
  "id_card" varchar(32) COLLATE "pg_catalog"."default",
  "last_ip" varchar(32) COLLATE "pg_catalog"."default",
  "last_time" timestamp(6),
  "phone" varchar(32) COLLATE "pg_catalog"."default",
  "user_code" varchar(32) COLLATE "pg_catalog"."default",
  "id_type" varchar(8) COLLATE "pg_catalog"."default",
  "status" int4,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "is_admin" varchar(8) COLLATE "pg_catalog"."default",
  "active_status" int4,
  "default_password" varchar(32) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6)
)
;
COMMENT ON COLUMN "nt_sys_user"."id" IS '主键';
COMMENT ON COLUMN "nt_sys_user"."user_name" IS '账号';
COMMENT ON COLUMN "nt_sys_user"."password" IS '密码';
COMMENT ON COLUMN "nt_sys_user"."real_name" IS '姓名';
COMMENT ON COLUMN "nt_sys_user"."avatar" IS '头像';
COMMENT ON COLUMN "nt_sys_user"."sex" IS '性别';
COMMENT ON COLUMN "nt_sys_user"."user_type" IS '类型';
COMMENT ON COLUMN "nt_sys_user"."email" IS '邮箱';
COMMENT ON COLUMN "nt_sys_user"."id_card" IS '身份证';
COMMENT ON COLUMN "nt_sys_user"."last_ip" IS '登录ip';
COMMENT ON COLUMN "nt_sys_user"."last_time" IS '登录时间';
COMMENT ON COLUMN "nt_sys_user"."phone" IS '电话';
COMMENT ON COLUMN "nt_sys_user"."user_code" IS '编号';
COMMENT ON COLUMN "nt_sys_user"."id_type" IS '证件类型';
COMMENT ON COLUMN "nt_sys_user"."status" IS '状态';
COMMENT ON COLUMN "nt_sys_user"."remark" IS '备注';
COMMENT ON COLUMN "nt_sys_user"."is_admin" IS '是否管理员';
COMMENT ON COLUMN "nt_sys_user"."active_status" IS '状态';
COMMENT ON COLUMN "nt_sys_user"."default_password" IS '默认密码';
COMMENT ON COLUMN "nt_sys_user"."create_time" IS '创建时间';
COMMENT ON TABLE "nt_sys_user" IS '用户表';

-- ----------------------------
-- Records of nt_sys_user
-- ----------------------------
BEGIN;
INSERT INTO "nt_sys_user" VALUES ('1', 'admin', 'C4CA4238A0B923820DCC509A6F75849B', '超管', 'https://www.oner365.com/group1/M00/00/04/rBqcll9Qsc-ADt_9AAAk7-agPWw680.jpg', 1, 1, 'admin@qq.com', '110103197707250933', '::1', '2022-05-14 16:59:24.619262', '13800138000', '', 'beijing', 1, '123', '1', 1, '123456', '2018-10-09 14:19:44');
INSERT INTO "nt_sys_user" VALUES ('2', 'shy', 'C4CA4238A0B923820DCC509A6F75849B', '张3', '', 1, 1, NULL, NULL, '::1', '2022-05-14 17:55:08.515101', '13800138000', NULL, NULL, 1, NULL, '0', 1, '123456', '2020-05-12 09:47:07');
INSERT INTO "nt_sys_user" VALUES ('5', 'ls', 'C4CA4238A0B923820DCC509A6F75849B', '王老师', '', 1, 1, NULL, NULL, '::1', '2022-05-14 17:55:48.352216', '13800138001', NULL, NULL, 0, NULL, '1', 1, NULL, '2020-05-12 17:05:23');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_user_job
-- ----------------------------
DROP TABLE IF EXISTS "nt_sys_user_job";
CREATE TABLE "nt_sys_user_job" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "position_order" int4 NOT NULL,
  "status" int4 NOT NULL,
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "job_id" varchar(255) COLLATE "pg_catalog"."default",
  "user_id" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "nt_sys_user_job"."id" IS '主键';
COMMENT ON COLUMN "nt_sys_user_job"."position_order" IS '排序';
COMMENT ON COLUMN "nt_sys_user_job"."status" IS '状态';
COMMENT ON COLUMN "nt_sys_user_job"."create_time" IS '创建时间';
COMMENT ON COLUMN "nt_sys_user_job"."update_time" IS '更新时间';
COMMENT ON COLUMN "nt_sys_user_job"."job_id" IS '职位编号';
COMMENT ON COLUMN "nt_sys_user_job"."user_id" IS '用户编号';
COMMENT ON TABLE "nt_sys_user_job" IS '用户职位表';

-- ----------------------------
-- Records of nt_sys_user_job
-- ----------------------------
BEGIN;
INSERT INTO "nt_sys_user_job" VALUES ('4028b881745ea0f901745ea77e07001a', 1, 1, '2020-09-05 22:24:29', NULL, '1', '1');
INSERT INTO "nt_sys_user_job" VALUES ('ff80808180c1fc800180c1fe46810011', 1, 1, '2022-05-14 17:55:08.515101', '2022-05-14 17:55:08.515101', '1', '2');
INSERT INTO "nt_sys_user_job" VALUES ('ff80808180c1fc800180c1fee20c001c', 1, 1, '2022-05-14 17:55:48.352216', '2022-05-14 17:55:48.352216', '1', '5');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_user_org
-- ----------------------------
DROP TABLE IF EXISTS "nt_sys_user_org";
CREATE TABLE "nt_sys_user_org" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "position_order" int4 NOT NULL,
  "status" int4 NOT NULL,
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "org_id" varchar(255) COLLATE "pg_catalog"."default",
  "user_id" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "nt_sys_user_org"."id" IS '主键';
COMMENT ON COLUMN "nt_sys_user_org"."position_order" IS '排序';
COMMENT ON COLUMN "nt_sys_user_org"."status" IS '状态';
COMMENT ON COLUMN "nt_sys_user_org"."create_time" IS '创建时间';
COMMENT ON COLUMN "nt_sys_user_org"."update_time" IS '更新时间';
COMMENT ON COLUMN "nt_sys_user_org"."org_id" IS '机构编号';
COMMENT ON COLUMN "nt_sys_user_org"."user_id" IS '用户编号';
COMMENT ON TABLE "nt_sys_user_org" IS '用户机构权限表';

-- ----------------------------
-- Records of nt_sys_user_org
-- ----------------------------
BEGIN;
INSERT INTO "nt_sys_user_org" VALUES ('ff8080817640f2d4017640f8fcb10001', 1, 1, '2020-12-08 14:10:28', NULL, '110', '1');
INSERT INTO "nt_sys_user_org" VALUES ('ff80808180c1fc800180c1fe46890012', 1, 1, '2022-05-14 17:55:08.515101', '2022-05-14 17:55:08.515101', '1', '2');
INSERT INTO "nt_sys_user_org" VALUES ('ff80808180c1fc800180c1fe468d0013', 1, 1, '2022-05-14 17:55:08.515101', '2022-05-14 17:55:08.515101', '110', '2');
INSERT INTO "nt_sys_user_org" VALUES ('ff80808180c1fc800180c1fee211001d', 1, 1, '2022-05-14 17:55:48.352216', '2022-05-14 17:55:48.352216', '1', '5');
INSERT INTO "nt_sys_user_org" VALUES ('ff80808180c1fc800180c1fee213001e', 1, 1, '2022-05-14 17:55:48.352216', '2022-05-14 17:55:48.352216', '110', '5');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS "nt_sys_user_role";
CREATE TABLE "nt_sys_user_role" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "role_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "user_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL
)
;
COMMENT ON COLUMN "nt_sys_user_role"."id" IS '主键';
COMMENT ON COLUMN "nt_sys_user_role"."role_id" IS '角色编号';
COMMENT ON COLUMN "nt_sys_user_role"."user_id" IS '用户编号';
COMMENT ON TABLE "nt_sys_user_role" IS '用户角色权限表';

-- ----------------------------
-- Records of nt_sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO "nt_sys_user_role" VALUES ('4028b881745ea0f901745ea77dfa0018', '1', '1');
INSERT INTO "nt_sys_user_role" VALUES ('4028b881745ea0f901745ea77dfe0019', 'ff80808172a150110172a159d9c40000', '1');
INSERT INTO "nt_sys_user_role" VALUES ('ff80808180c1fc800180c1fe46770010', 'ff80808172a150110172a159d9c40000', '2');
INSERT INTO "nt_sys_user_role" VALUES ('ff80808180c1fc800180c1fee208001b', '1', '5');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS "qrtz_blob_triggers";
CREATE TABLE "qrtz_blob_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "blob_data" bytea
)
;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS "qrtz_calendars";
CREATE TABLE "qrtz_calendars" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "calendar_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "calendar" bytea NOT NULL
)
;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS "qrtz_cron_triggers";
CREATE TABLE "qrtz_cron_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "cron_expression" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "time_zone_id" varchar(80) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
BEGIN;
INSERT INTO "qrtz_cron_triggers" VALUES ('ProjectScheduler', 'TASK_CLASS_NAME8a818b3774bef2910174bef387ec0000', 'DEFAULT', '0/10 * * * * ?', 'Asia/Shanghai');
INSERT INTO "qrtz_cron_triggers" VALUES ('ProjectScheduler', 'TASK_CLASS_NAMEff80808175cfe8900175d012ecc00002', 'DEFAULT', '0 0 1 * * ?', 'Asia/Shanghai');
INSERT INTO "qrtz_cron_triggers" VALUES ('ProjectScheduler', 'TASK_CLASS_NAMEff80808175d015c90175d05958e50001', 'DEFAULT', '0 0 1 * * ?', 'Asia/Shanghai');
INSERT INTO "qrtz_cron_triggers" VALUES ('quartzScheduler', 'TASK_CLASS_NAME8a818b3774bef2910174bef387ec0000', 'DEFAULT', '0/10 * * * * ?', 'Asia/Shanghai');
INSERT INTO "qrtz_cron_triggers" VALUES ('quartzScheduler', 'TASK_CLASS_NAMEff80808175cfe8900175d012ecc00002', 'DEFAULT', '0 0 1 * * ?', 'Asia/Shanghai');
INSERT INTO "qrtz_cron_triggers" VALUES ('quartzScheduler', 'TASK_CLASS_NAMEff80808175d015c90175d05958e50001', 'DEFAULT', '0 0 1 * * ?', 'Asia/Shanghai');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS "qrtz_fired_triggers";
CREATE TABLE "qrtz_fired_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "entry_id" varchar(95) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "instance_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "fired_time" int8 NOT NULL,
  "sched_time" int8 NOT NULL,
  "priority" int4 NOT NULL,
  "state" varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
  "job_name" varchar(200) COLLATE "pg_catalog"."default",
  "job_group" varchar(200) COLLATE "pg_catalog"."default",
  "is_nonconcurrent" varchar(10) COLLATE "pg_catalog"."default",
  "requests_recovery" bool
)
;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS "qrtz_job_details";
CREATE TABLE "qrtz_job_details" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "job_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "job_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(250) COLLATE "pg_catalog"."default",
  "job_class_name" varchar(250) COLLATE "pg_catalog"."default" NOT NULL,
  "is_durable" varchar(10) COLLATE "pg_catalog"."default" NOT NULL,
  "is_nonconcurrent" varchar(10) COLLATE "pg_catalog"."default" NOT NULL,
  "is_update_data" varchar(10) COLLATE "pg_catalog"."default" NOT NULL,
  "requests_recovery" varchar(10) COLLATE "pg_catalog"."default" NOT NULL,
  "job_data" bytea
)
;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
BEGIN;
INSERT INTO "qrtz_job_details" VALUES ('quartzScheduler', 'TASK_CLASS_NAME8a818b3774bef2910174bef387ec0000', 'DEFAULT', NULL, 'com.oner365.monitor.util.QuartzDisallowConcurrentExecution', 'false', 'true', 'false', 'false', E'\\254\\355\\000\\005sr\\000\\025org.quartz.JobDataMap\\237\\260\\203\\350\\277\\251\\260\\313\\002\\000\\000xr\\000&org.quartz.utils.StringKeyDirtyFlagMap\\202\\010\\350\\303\\373\\305](\\002\\000\\001Z\\000\\023allowsTransientDataxr\\000\\035org.quartz.utils.DirtyFlagMap\\023\\346.\\255(v\\012\\316\\002\\000\\002Z\\000\\005dirtyL\\000\\003mapt\\000\\017Ljava/util/Map;xp\\001sr\\000\\021java.util.HashMap\\005\\007\\332\\301\\303\\026`\\321\\003\\000\\002F\\000\\012loadFactorI\\000\\011thresholdxp?@\\000\\000\\000\\000\\000\\014w\\010\\000\\000\\000\\020\\000\\000\\000\\001t\\000\\017TASK_PROPERTIESsr\\000"com.oner365.monitor.dto.SysTaskDto\\000\\000\\000\\000\\000\\000\\000\\001\\002\\000\\016L\\000\\012concurrentt\\000\\022Ljava/lang/String;L\\000\\012createTimet\\000\\020Ljava/util/Date;L\\000\\012createUserq\\000~\\000\\011L\\000\\016cronExpressionq\\000~\\000\\011L\\000\\015executeStatust\\000%Lcom/oner365/common/enums/StatusEnum;L\\000\\002idq\\000~\\000\\011L\\000\\016invokeParamDtot\\000(Lcom/oner365/monitor/dto/InvokeParamDto;L\\000\\014invokeTargetq\\000~\\000\\011L\\000\\015misfirePolicyt\\000-Lcom/oner365/monitor/enums/MisfirePolicyEnum;L\\000\\006remarkq\\000~\\000\\011L\\000\\006statust\\000*Lcom/oner365/monitor/enums/TaskStatusEnum;L\\000\\011taskGroupq\\000~\\000\\011L\\000\\010taskNameq\\000~\\000\\011L\\000\\012updateTimeq\\000~\\000\\012xpt\\000\\0011pt\\000\\006liutaot\\000\\0160/10 * * * * ?~r\\000#com.oner365.common.enums.StatusEnum\\000\\000\\000\\000\\000\\000\\000\\000\\022\\000\\000xr\\000\\016java.lang.Enum\\000\\000\\000\\000\\000\\000\\000\\000\\022\\000\\000xpt\\000\\003YESt\\000 8a818b3774bef2910174bef387ec0000pt\\000\\024systemTask.taskRun()~r\\000+com.oner365.monitor.enums.MisfirePolicyEnum\\000\\000\\000\\000\\000\\000\\000\\000\\022\\000\\000xq\\000~\\000\\024t\\000\\007DEFAULTt\\000\\000~r\\000(com.oner365.monitor.enums.TaskStatusEnum\\000\\000\\000\\000\\000\\000\\000\\000\\022\\000\\000xq\\000~\\000\\024t\\000\\005PAUSEt\\000\\007DEFAULTt\\000\\004testpx\\000');
INSERT INTO "qrtz_job_details" VALUES ('quartzScheduler', 'TASK_CLASS_NAMEff80808175cfe8900175d012ecc00002', 'DEFAULT', NULL, 'com.oner365.monitor.util.QuartzDisallowConcurrentExecution', 'false', 'true', 'false', 'false', E'\\254\\355\\000\\005sr\\000\\025org.quartz.JobDataMap\\237\\260\\203\\350\\277\\251\\260\\313\\002\\000\\000xr\\000&org.quartz.utils.StringKeyDirtyFlagMap\\202\\010\\350\\303\\373\\305](\\002\\000\\001Z\\000\\023allowsTransientDataxr\\000\\035org.quartz.utils.DirtyFlagMap\\023\\346.\\255(v\\012\\316\\002\\000\\002Z\\000\\005dirtyL\\000\\003mapt\\000\\017Ljava/util/Map;xp\\001sr\\000\\021java.util.HashMap\\005\\007\\332\\301\\303\\026`\\321\\003\\000\\002F\\000\\012loadFactorI\\000\\011thresholdxp?@\\000\\000\\000\\000\\000\\014w\\010\\000\\000\\000\\020\\000\\000\\000\\001t\\000\\017TASK_PROPERTIESsr\\000"com.oner365.monitor.dto.SysTaskDto\\000\\000\\000\\000\\000\\000\\000\\001\\002\\000\\016L\\000\\012concurrentt\\000\\022Ljava/lang/String;L\\000\\012createTimet\\000\\020Ljava/util/Date;L\\000\\012createUserq\\000~\\000\\011L\\000\\016cronExpressionq\\000~\\000\\011L\\000\\015executeStatust\\000%Lcom/oner365/common/enums/StatusEnum;L\\000\\002idq\\000~\\000\\011L\\000\\016invokeParamDtot\\000(Lcom/oner365/monitor/dto/InvokeParamDto;L\\000\\014invokeTargetq\\000~\\000\\011L\\000\\015misfirePolicyt\\000-Lcom/oner365/monitor/enums/MisfirePolicyEnum;L\\000\\006remarkq\\000~\\000\\011L\\000\\006statust\\000*Lcom/oner365/monitor/enums/TaskStatusEnum;L\\000\\011taskGroupq\\000~\\000\\011L\\000\\010taskNameq\\000~\\000\\011L\\000\\012updateTimeq\\000~\\000\\012xpt\\000\\0011pt\\000\\005admint\\000\\0130 0 1 * * ?pt\\000 ff80808175cfe8900175d012ecc00002pt\\000\\033systemTask.taskDeleteLog(3)~r\\000+com.oner365.monitor.enums.MisfirePolicyEnum\\000\\000\\000\\000\\000\\000\\000\\000\\022\\000\\000xr\\000\\016java.lang.Enum\\000\\000\\000\\000\\000\\000\\000\\000\\022\\000\\000xpt\\000\\004NONEp~r\\000(com.oner365.monitor.enums.TaskStatusEnum\\000\\000\\000\\000\\000\\000\\000\\000\\022\\000\\000xq\\000~\\000\\026t\\000\\005PAUSEt\\000\\007DEFAULTt\\000\\020\\345\\210\\240\\351\\231\\2443\\345\\244\\251\\346\\227\\245\\345\\277\\227px\\000');
INSERT INTO "qrtz_job_details" VALUES ('quartzScheduler', 'TASK_CLASS_NAMEff80808175d015c90175d05958e50001', 'DEFAULT', NULL, 'com.oner365.monitor.util.QuartzDisallowConcurrentExecution', 'false', 'true', 'false', 'false', E'\\254\\355\\000\\005sr\\000\\025org.quartz.JobDataMap\\237\\260\\203\\350\\277\\251\\260\\313\\002\\000\\000xr\\000&org.quartz.utils.StringKeyDirtyFlagMap\\202\\010\\350\\303\\373\\305](\\002\\000\\001Z\\000\\023allowsTransientDataxr\\000\\035org.quartz.utils.DirtyFlagMap\\023\\346.\\255(v\\012\\316\\002\\000\\002Z\\000\\005dirtyL\\000\\003mapt\\000\\017Ljava/util/Map;xp\\001sr\\000\\021java.util.HashMap\\005\\007\\332\\301\\303\\026`\\321\\003\\000\\002F\\000\\012loadFactorI\\000\\011thresholdxp?@\\000\\000\\000\\000\\000\\014w\\010\\000\\000\\000\\020\\000\\000\\000\\001t\\000\\017TASK_PROPERTIESsr\\000"com.oner365.monitor.dto.SysTaskDto\\000\\000\\000\\000\\000\\000\\000\\001\\002\\000\\016L\\000\\012concurrentt\\000\\022Ljava/lang/String;L\\000\\012createTimet\\000\\020Ljava/util/Date;L\\000\\012createUserq\\000~\\000\\011L\\000\\016cronExpressionq\\000~\\000\\011L\\000\\015executeStatust\\000%Lcom/oner365/common/enums/StatusEnum;L\\000\\002idq\\000~\\000\\011L\\000\\016invokeParamDtot\\000(Lcom/oner365/monitor/dto/InvokeParamDto;L\\000\\014invokeTargetq\\000~\\000\\011L\\000\\015misfirePolicyt\\000-Lcom/oner365/monitor/enums/MisfirePolicyEnum;L\\000\\006remarkq\\000~\\000\\011L\\000\\006statust\\000*Lcom/oner365/monitor/enums/TaskStatusEnum;L\\000\\011taskGroupq\\000~\\000\\011L\\000\\010taskNameq\\000~\\000\\011L\\000\\012updateTimeq\\000~\\000\\012xpt\\000\\0011pt\\000\\005admint\\000\\0130 0 1 * * ?pt\\000 ff80808175d015c90175d05958e50001pt\\000\\034systemTask.taskParams(''121'')~r\\000+com.oner365.monitor.enums.MisfirePolicyEnum\\000\\000\\000\\000\\000\\000\\000\\000\\022\\000\\000xr\\000\\016java.lang.Enum\\000\\000\\000\\000\\000\\000\\000\\000\\022\\000\\000xpt\\000\\004NONEp~r\\000(com.oner365.monitor.enums.TaskStatusEnum\\000\\000\\000\\000\\000\\000\\000\\000\\022\\000\\000xq\\000~\\000\\026t\\000\\005PAUSEt\\000\\007DEFAULTt\\000\\003111px\\000');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS "qrtz_locks";
CREATE TABLE "qrtz_locks" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "lock_name" varchar(40) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
BEGIN;
INSERT INTO "qrtz_locks" VALUES ('quartzScheduler', 'TRIGGER_ACCESS');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS "qrtz_paused_trigger_grps";
CREATE TABLE "qrtz_paused_trigger_grps" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS "qrtz_scheduler_state";
CREATE TABLE "qrtz_scheduler_state" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "instance_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "last_checkin_time" int8 NOT NULL,
  "checkin_interval" int8 NOT NULL
)
;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS "qrtz_simple_triggers";
CREATE TABLE "qrtz_simple_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "repeat_count" int8 NOT NULL,
  "repeat_interval" int8 NOT NULL,
  "times_triggered" int8 NOT NULL
)
;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS "qrtz_simprop_triggers";
CREATE TABLE "qrtz_simprop_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "str_prop_1" varchar(512) COLLATE "pg_catalog"."default",
  "str_prop_2" varchar(512) COLLATE "pg_catalog"."default",
  "str_prop_3" varchar(512) COLLATE "pg_catalog"."default",
  "int_prop_1" int4,
  "int_prop_2" int4,
  "long_prop_1" int8,
  "long_prop_2" int8,
  "dec_prop_1" numeric(13,4),
  "dec_prop_2" numeric(13,4),
  "bool_prop_1" varchar(1) COLLATE "pg_catalog"."default",
  "bool_prop_2" varchar(1) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS "qrtz_triggers";
CREATE TABLE "qrtz_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "job_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "job_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(250) COLLATE "pg_catalog"."default",
  "next_fire_time" int8,
  "prev_fire_time" int8,
  "priority" int4,
  "trigger_state" varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_type" varchar(8) COLLATE "pg_catalog"."default" NOT NULL,
  "start_time" int8 NOT NULL,
  "end_time" int8,
  "calendar_name" varchar(200) COLLATE "pg_catalog"."default",
  "misfire_instr" int2,
  "job_data" bytea
)
;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
BEGIN;
INSERT INTO "qrtz_triggers" VALUES ('quartzScheduler', 'TASK_CLASS_NAMEff80808175d015c90175d05958e50001', 'DEFAULT', 'TASK_CLASS_NAMEff80808175d015c90175d05958e50001', 'DEFAULT', NULL, 1652547600000, -1, 5, 'PAUSED', 'CRON', 1652522018000, 0, NULL, 2, E'\\\\x');
INSERT INTO "qrtz_triggers" VALUES ('quartzScheduler', 'TASK_CLASS_NAME8a818b3774bef2910174bef387ec0000', 'DEFAULT', 'TASK_CLASS_NAME8a818b3774bef2910174bef387ec0000', 'DEFAULT', NULL, 1652522020000, -1, 5, 'PAUSED', 'CRON', 1652522018000, 0, NULL, 0, E'\\\\x');
INSERT INTO "qrtz_triggers" VALUES ('quartzScheduler', 'TASK_CLASS_NAMEff80808175cfe8900175d012ecc00002', 'DEFAULT', 'TASK_CLASS_NAMEff80808175cfe8900175d012ecc00002', 'DEFAULT', NULL, 1652547600000, -1, 5, 'PAUSED', 'CRON', 1652522018000, 0, NULL, 2, E'\\\\x');
COMMIT;

-- ----------------------------
-- Primary Key structure for table gen_table
-- ----------------------------
ALTER TABLE "gen_table" ADD CONSTRAINT "gen_table_pkey" PRIMARY KEY ("table_id");

-- ----------------------------
-- Primary Key structure for table gen_table_column
-- ----------------------------
ALTER TABLE "gen_table_column" ADD CONSTRAINT "gen_table_column_pkey" PRIMARY KEY ("column_id");

-- ----------------------------
-- Indexes structure for table nt_data_source_config
-- ----------------------------
CREATE UNIQUE INDEX "uk_dsc_connect_name" ON "nt_data_source_config" USING btree (
  "connect_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table nt_data_source_config
-- ----------------------------
ALTER TABLE "nt_data_source_config" ADD CONSTRAINT "nt_data_source_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_gateway_route
-- ----------------------------
ALTER TABLE "nt_gateway_route" ADD CONSTRAINT "nt_gateway_route_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_dict_item
-- ----------------------------
ALTER TABLE "nt_sys_dict_item" ADD CONSTRAINT "nt_sys_dict_item_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table nt_sys_dict_item_type
-- ----------------------------
CREATE UNIQUE INDEX "nt_sys_dict_item_type_dict_type_code_uindex" ON "nt_sys_dict_item_type" USING btree (
  "dict_type_code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table nt_sys_dict_item_type
-- ----------------------------
ALTER TABLE "nt_sys_dict_item_type" ADD CONSTRAINT "nt_sys_dict_item_type_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_fastdfs_file
-- ----------------------------
ALTER TABLE "nt_sys_fastdfs_file" ADD CONSTRAINT "nt_sys_fastdfs_file_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table nt_sys_job
-- ----------------------------
CREATE UNIQUE INDEX "uk_nsj_job_name" ON "nt_sys_job" USING btree (
  "job_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table nt_sys_job
-- ----------------------------
ALTER TABLE "nt_sys_job" ADD CONSTRAINT "nt_sys_job_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_log
-- ----------------------------
ALTER TABLE "nt_sys_log" ADD CONSTRAINT "nt_sys_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table nt_sys_menu
-- ----------------------------
CREATE INDEX "idx_menu_type_id" ON "nt_sys_menu" USING btree (
  "menu_type_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_parent_id" ON "nt_sys_menu" USING btree (
  "parent_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table nt_sys_menu
-- ----------------------------
ALTER TABLE "nt_sys_menu" ADD CONSTRAINT "nt_sys_menu_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_menu_oper
-- ----------------------------
ALTER TABLE "nt_sys_menu_oper" ADD CONSTRAINT "nt_sys_menu_oper_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table nt_sys_menu_type
-- ----------------------------
CREATE UNIQUE INDEX "uk_smt_type_code" ON "nt_sys_menu_type" USING btree (
  "type_code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table nt_sys_menu_type
-- ----------------------------
ALTER TABLE "nt_sys_menu_type" ADD CONSTRAINT "nt_sys_menu_type_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table nt_sys_message
-- ----------------------------
CREATE INDEX "idx_message_type" ON "nt_sys_message" USING btree (
  "message_type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table nt_sys_message
-- ----------------------------
ALTER TABLE "nt_sys_message" ADD CONSTRAINT "nt_sys_message_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_operation
-- ----------------------------
ALTER TABLE "nt_sys_operation" ADD CONSTRAINT "nt_sys_operation_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table nt_sys_organization
-- ----------------------------
CREATE INDEX "idx_sys_org_org_code" ON "nt_sys_organization" USING btree (
  "org_code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_sys_org_parent_id" ON "nt_sys_organization" USING btree (
  "parent_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table nt_sys_organization
-- ----------------------------
ALTER TABLE "nt_sys_organization" ADD CONSTRAINT "nt_sys_organization_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_role
-- ----------------------------
ALTER TABLE "nt_sys_role" ADD CONSTRAINT "nt_sys_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_role_menu
-- ----------------------------
ALTER TABLE "nt_sys_role_menu" ADD CONSTRAINT "nt_sys_role_menu_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_role_menu_oper
-- ----------------------------
ALTER TABLE "nt_sys_role_menu_oper" ADD CONSTRAINT "nt_sys_role_menu_oper_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_sequence
-- ----------------------------
ALTER TABLE "nt_sys_sequence" ADD CONSTRAINT "nt_sys_sequence_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_task
-- ----------------------------
ALTER TABLE "nt_sys_task" ADD CONSTRAINT "nt_sys_task_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_task_log
-- ----------------------------
ALTER TABLE "nt_sys_task_log" ADD CONSTRAINT "nt_sys_task_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table nt_sys_user
-- ----------------------------
CREATE INDEX "idx_user_user_name" ON "nt_sys_user" USING btree (
  "user_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_ssf_file_storage" ON "nt_sys_fastdfs_file" USING btree (
  "file_storage" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table nt_sys_user
-- ----------------------------
ALTER TABLE "nt_sys_user" ADD CONSTRAINT "nt_sys_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_user_job
-- ----------------------------
ALTER TABLE "nt_sys_user_job" ADD CONSTRAINT "nt_sys_user_job_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_user_org
-- ----------------------------
ALTER TABLE "nt_sys_user_org" ADD CONSTRAINT "nt_sys_user_org_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table nt_sys_user_role
-- ----------------------------
ALTER TABLE "nt_sys_user_role" ADD CONSTRAINT "nt_sys_user_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table qrtz_blob_triggers
-- ----------------------------
ALTER TABLE "qrtz_blob_triggers" ADD CONSTRAINT "QRTZ_BLOB_TRIGGERS_pkey" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Primary Key structure for table qrtz_calendars
-- ----------------------------
ALTER TABLE "qrtz_calendars" ADD CONSTRAINT "QRTZ_CALENDARS_pkey" PRIMARY KEY ("sched_name", "calendar_name");

-- ----------------------------
-- Primary Key structure for table qrtz_cron_triggers
-- ----------------------------
ALTER TABLE "qrtz_cron_triggers" ADD CONSTRAINT "QRTZ_CRON_TRIGGERS_pkey" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Primary Key structure for table qrtz_fired_triggers
-- ----------------------------
ALTER TABLE "qrtz_fired_triggers" ADD CONSTRAINT "QRTZ_FIRED_TRIGGERS_pkey" PRIMARY KEY ("sched_name", "entry_id");

-- ----------------------------
-- Primary Key structure for table qrtz_job_details
-- ----------------------------
ALTER TABLE "qrtz_job_details" ADD CONSTRAINT "QRTZ_JOB_DETAILS_pkey" PRIMARY KEY ("sched_name", "job_name", "job_group");

-- ----------------------------
-- Primary Key structure for table qrtz_locks
-- ----------------------------
ALTER TABLE "qrtz_locks" ADD CONSTRAINT "QRTZ_LOCKS_pkey" PRIMARY KEY ("sched_name", "lock_name");

-- ----------------------------
-- Primary Key structure for table qrtz_paused_trigger_grps
-- ----------------------------
ALTER TABLE "qrtz_paused_trigger_grps" ADD CONSTRAINT "QRTZ_PAUSED_TRIGGER_GRPS_pkey" PRIMARY KEY ("sched_name", "trigger_group");

-- ----------------------------
-- Primary Key structure for table qrtz_scheduler_state
-- ----------------------------
ALTER TABLE "qrtz_scheduler_state" ADD CONSTRAINT "QRTZ_SCHEDULER_STATE_pkey" PRIMARY KEY ("sched_name", "instance_name");

-- ----------------------------
-- Primary Key structure for table qrtz_simple_triggers
-- ----------------------------
ALTER TABLE "qrtz_simple_triggers" ADD CONSTRAINT "QRTZ_SIMPLE_TRIGGERS_pkey" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Primary Key structure for table qrtz_simprop_triggers
-- ----------------------------
ALTER TABLE "qrtz_simprop_triggers" ADD CONSTRAINT "QRTZ_SIMPROP_TRIGGERS_pkey" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Indexes structure for table qrtz_triggers
-- ----------------------------
CREATE INDEX "sched_name" ON "qrtz_triggers" USING btree (
  "sched_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "job_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "job_group" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table qrtz_triggers
-- ----------------------------
ALTER TABLE "qrtz_triggers" ADD CONSTRAINT "QRTZ_TRIGGERS_pkey" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Foreign Keys structure for table nt_sys_dict_item
-- ----------------------------
ALTER TABLE "nt_sys_dict_item" ADD CONSTRAINT "idx_dict_item_type_id" FOREIGN KEY ("dict_item_type_id") REFERENCES "nt_sys_dict_item_type" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table nt_sys_menu
-- ----------------------------
ALTER TABLE "nt_sys_menu" ADD CONSTRAINT "fk_sm_menu_type_id" FOREIGN KEY ("menu_type_id") REFERENCES "nt_sys_menu_type" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table nt_sys_menu_oper
-- ----------------------------
ALTER TABLE "nt_sys_menu_oper" ADD CONSTRAINT "idx_menu_id" FOREIGN KEY ("menu_id") REFERENCES "nt_sys_menu" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "nt_sys_menu_oper" ADD CONSTRAINT "idx_operation_id" FOREIGN KEY ("operation_id") REFERENCES "nt_sys_operation" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table nt_sys_organization
-- ----------------------------
ALTER TABLE "nt_sys_organization" ADD CONSTRAINT "idx_sys_org_config_id" FOREIGN KEY ("config_id") REFERENCES "nt_data_source_config" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table nt_sys_role_menu
-- ----------------------------
ALTER TABLE "nt_sys_role_menu" ADD CONSTRAINT "idx_role_menu_menu_id" FOREIGN KEY ("menu_id") REFERENCES "nt_sys_menu" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "nt_sys_role_menu" ADD CONSTRAINT "idx_role_menu_menu_type_id" FOREIGN KEY ("menu_type_id") REFERENCES "nt_sys_menu_type" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "nt_sys_role_menu" ADD CONSTRAINT "idx_role_menu_role_id" FOREIGN KEY ("role_id") REFERENCES "nt_sys_role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table nt_sys_role_menu_oper
-- ----------------------------
ALTER TABLE "nt_sys_role_menu_oper" ADD CONSTRAINT "idx_role_menu_oper_menu_id" FOREIGN KEY ("menu_id") REFERENCES "nt_sys_menu" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "nt_sys_role_menu_oper" ADD CONSTRAINT "idx_role_menu_oper_menu_type_id" FOREIGN KEY ("menu_type_id") REFERENCES "nt_sys_menu_type" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "nt_sys_role_menu_oper" ADD CONSTRAINT "idx_role_menu_oper_operation_id" FOREIGN KEY ("operation_id") REFERENCES "nt_sys_operation" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "nt_sys_role_menu_oper" ADD CONSTRAINT "idx_role_menu_oper_role_id" FOREIGN KEY ("role_id") REFERENCES "nt_sys_role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table nt_sys_user_job
-- ----------------------------
ALTER TABLE "nt_sys_user_job" ADD CONSTRAINT "idx_user_job_job_id" FOREIGN KEY ("job_id") REFERENCES "nt_sys_job" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "nt_sys_user_job" ADD CONSTRAINT "idx_user_job_user_id" FOREIGN KEY ("user_id") REFERENCES "nt_sys_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table nt_sys_user_org
-- ----------------------------
ALTER TABLE "nt_sys_user_org" ADD CONSTRAINT "idx_user_org_org_id" FOREIGN KEY ("org_id") REFERENCES "nt_sys_organization" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "nt_sys_user_org" ADD CONSTRAINT "idx_user_org_user_id" FOREIGN KEY ("user_id") REFERENCES "nt_sys_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table nt_sys_user_role
-- ----------------------------
ALTER TABLE "nt_sys_user_role" ADD CONSTRAINT "idx_user_role_role_id" FOREIGN KEY ("role_id") REFERENCES "nt_sys_role" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "nt_sys_user_role" ADD CONSTRAINT "idx_user_role_user_id" FOREIGN KEY ("user_id") REFERENCES "nt_sys_user" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table qrtz_blob_triggers
-- ----------------------------
ALTER TABLE "qrtz_blob_triggers" ADD CONSTRAINT "qrtz_blob_triggers_ibfk_1" FOREIGN KEY ("sched_name", "trigger_name", "trigger_group") REFERENCES "qrtz_triggers" ("sched_name", "trigger_name", "trigger_group") ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;
