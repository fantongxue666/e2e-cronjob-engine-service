/*
 Navicat Premium Data Transfer

 Source Server         : 【大有中城】E2E电商系统
 Source Server Type    : MySQL
 Source Server Version : 50640
 Source Host           : 172.16.2.221:3306
 Source Schema         : cronjob_engine

 Target Server Type    : MySQL
 Target Server Version : 50640
 File Encoding         : 65001

 Date: 14/01/2022 10:46:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cron_info
-- ----------------------------
DROP TABLE IF EXISTS `cron_info`;
CREATE TABLE `cron_info`  (
  `cron_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `service_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器id',
  `cron_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '定时任务名称',
  `cron_expression` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '正则表达式',
  `cron_uri` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '定时任务执行的逻辑接口uri',
  `cron_status` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '1：已开启    2：已停止',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `status` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '1：正常    0：删除',
  `memo` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`cron_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of cron_info
-- ----------------------------
INSERT INTO `cron_info` VALUES ('1234623423436324', 'g51h234io5g123', '测试任务2555', '0/2 * * * * ?', '/user/list2', '2', '2022-01-12 16:28:45', '2022-01-14 09:30:40', '1', '测试定时任务，每五秒运行一次');
INSERT INTO `cron_info` VALUES ('123462346324', 'g51h234io5g123234', '测试任务3333', '0/6 * * * * ?', '/user/list', '2', '2022-01-12 16:28:45', '2022-01-13 18:25:15', '1', '测试定时任务，每五秒运行一次');

-- ----------------------------
-- Table structure for service_info
-- ----------------------------
DROP TABLE IF EXISTS `service_info`;
CREATE TABLE `service_info`  (
  `service_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `service_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器名称',
  `service_ip` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器ip地址',
  `service_port` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务端口号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `status` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '1：正常    0：删除',
  PRIMARY KEY (`service_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of service_info
-- ----------------------------
INSERT INTO `service_info` VALUES ('faefe07997994e828eb6492ba525de45', '1231222222', '12312', '12', '2022-01-13 15:31:50', '2022-01-13 15:39:07', '0');
INSERT INTO `service_info` VALUES ('g51h234io5g123', '221服务器', 'http://172.16.2.221', '80', '2022-01-13 12:02:05', '2022-01-13 12:02:07', '1');
INSERT INTO `service_info` VALUES ('g51h234io5g123234', '115服务器', 'http://172.16.2.115', '80', '2022-01-13 12:02:05', '2022-01-13 12:02:07', '1');

SET FOREIGN_KEY_CHECKS = 1;
