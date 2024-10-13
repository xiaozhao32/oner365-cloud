
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_master_slave
-- ----------------------------
DROP TABLE IF EXISTS `t_master_slave`;
CREATE TABLE `t_master_slave` (
  `id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `test_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for t_order_0
-- ----------------------------
DROP TABLE IF EXISTS `t_order_0`;
CREATE TABLE `t_order_0` (
  `id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `order_id` int NOT NULL,
  `status` int DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for t_order_1
-- ----------------------------
DROP TABLE IF EXISTS `t_order_1`;
CREATE TABLE `t_order_1` (
  `id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `order_id` int NOT NULL,
  `status` int DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;
