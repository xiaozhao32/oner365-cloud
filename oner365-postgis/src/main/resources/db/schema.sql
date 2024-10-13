-- ----------------------------
-- Table structure for t_position
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_position";
CREATE TABLE "public"."t_position" (
  "id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "position_name" varchar(32) COLLATE "pg_catalog"."default",
  "postgis_type" varchar(16) COLLATE "pg_catalog"."default",
  "position_point" geometry(POINT, 4326),
  "position_polygon" geometry(POLYGON, 4326),
  "position_line_string" geometry(LINESTRING, 4326),
  "position_multi_point" geometry(MULTIPOINT, 4326),
  "position_multi_line_string" geometry(MULTILINESTRING, 4326),
  "position_multi_polygon" geometry(MULTIPOLYGON, 4326),
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;

-- ----------------------------
-- Records of t_position
-- ----------------------------
BEGIN;
INSERT INTO "public"."t_position" ("id", "position_name", "postgis_type", "position_point", "create_time", "update_time") VALUES ('5f4149f0-710f-45be-a34c-ead09ecf5490', 'point', 'POINT', '0101000020E61000009FCDAACFD5145E407E8AE3C0AB493E40', '2024-08-22 15:01:36.682', '2024-08-22 15:01:36.682');
INSERT INTO "public"."t_position" ("id", "position_name", "postgis_type", "position_polygon", "create_time", "update_time") VALUES ('5f4149f0-710f-45be-a34c-ead09ecf5491', 'polygon', 'POLYGON', '0103000020E610000001000000050000000000000000000000000000000000000000000000000024400000000000000000000000000000244000000000000024400000000000000000000000000000244000000000000000000000000000000000', '2024-08-22 15:39:00.465', '2024-08-22 15:46:33.211');
INSERT INTO "public"."t_position" ("id", "position_name", "postgis_type", "position_line_string", "create_time", "update_time") VALUES ('5f4149f0-710f-45be-a34c-ead09ecf5492', 'linestring', 'LINESTRING', '0102000020E610000005000000D0EA37A29AC651C00FD603035B284540FEFCFB379AC651C0C0503E9F5B284540FFDDDD4D96C651C033AC3B284F2845402C7C643E8CC651C027D4465F58284540B03124FF85C651C0A206D85949284540', '2024-08-22 15:39:00.465', '2024-08-22 15:46:33.211');
INSERT INTO "public"."t_position" ("id", "position_name", "postgis_type", "position_multi_point", "create_time", "update_time") VALUES ('5f4149f0-710f-45be-a34c-ead09ecf5493', 'multipoint', 'MULTIPOINT', '0104000020E61000000200000001010000009A99999999D957C0CDCCCCCCCCCC3D40010100000000000000000058C00000000000003E40', '2024-08-22 15:39:00.465', '2024-08-22 15:46:33.211');
INSERT INTO "public"."t_position" ("id", "position_name", "postgis_type", "position_multi_line_string", "create_time", "update_time") VALUES ('5f4149f0-710f-45be-a34c-ead09ecf5494', 'multilinestring', 'MULTILINESTRING', '0105000020E610000002000000010200000005000000D0EA37A29AC651C00FD603035B284540FEFCFB379AC651C0C0503E9F5B284540FFDDDD4D96C651C033AC3B284F2845402C7C643E8CC651C027D4465F58284540B03124FF85C651C0A206D8594928454001020000000400000070B2795F4ECB51C0D02E98D21532454036D121C24ECB51C0ECACF96A18324540550B84844DCB51C05BC2ECD218324540569E59214DCB51C0CE808A3A16324540', '2024-08-22 15:39:00.465', '2024-08-22 15:46:33.211');
INSERT INTO "public"."t_position" ("id", "position_name", "postgis_type", "position_multi_polygon", "create_time", "update_time") VALUES ('5f4149f0-710f-45be-a34c-ead09ecf5495', 'multipolygon', 'MULTIPOLYGON', '0106000020E610000002000000010300000001000000050000006285C7C15ECB51C0ED88FC0DF531454028A46F245FCB51C009075EA6F731454047DED1E65DCB51C0781C510EF83145404871A7835DCB51C0EBDAEE75F53145406285C7C15ECB51C0ED88FC0DF53145400103000000010000000500000070B2795F4ECB51C0D02E98D21532454036D121C24ECB51C0ECACF96A18324540550B84844DCB51C05BC2ECD218324540569E59214DCB51C0CE808A3A1632454070B2795F4ECB51C0D02E98D215324540', '2024-08-22 15:39:00.465', '2024-08-22 15:46:33.211');
COMMIT;

-- ----------------------------
-- Primary Key structure for table t_position
-- ----------------------------
ALTER TABLE "public"."t_position" ADD CONSTRAINT "t_position_pkey" PRIMARY KEY ("id");