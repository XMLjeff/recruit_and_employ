/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : recruit_and_employ

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 14/03/2022 21:49:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company`  (
  `company_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '公司id',
  `company_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `company_detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '公司详情',
  PRIMARY KEY (`company_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公司表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for company_user
-- ----------------------------
DROP TABLE IF EXISTS `company_user`;
CREATE TABLE `company_user`  (
  `company_id` bigint(0) NOT NULL COMMENT '公司id',
  `user_id` bigint(0) NOT NULL COMMENT '用户id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公司、用户关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for job_seekers
-- ----------------------------
DROP TABLE IF EXISTS `job_seekers`;
CREATE TABLE `job_seekers`  (
  `user_id` bigint(0) NOT NULL COMMENT '用户id（主键）',
  `intended_position` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '意向岗位',
  `intended_place_of_work` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '意向工作地点',
  `salary_expectation` decimal(10, 2) NULL DEFAULT NULL COMMENT '期望薪资',
  `scholarship_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '奖学金信息',
  `introduction` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '个人介绍',
  `resume_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '简历url',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '求职者' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `sender_id` bigint(0) NULL DEFAULT NULL COMMENT '留言发送者',
  `recipient_id` bigint(0) NULL DEFAULT NULL COMMENT '留言接收者',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '留言信息',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '留言表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for phone_exchange
-- ----------------------------
DROP TABLE IF EXISTS `phone_exchange`;
CREATE TABLE `phone_exchange`  (
  `sender_id` bigint(0) NULL DEFAULT NULL COMMENT '电话发送者id',
  `phone` bigint(0) NULL DEFAULT NULL COMMENT '电话',
  `recipient_id` bigint(0) NULL DEFAULT NULL COMMENT '电话接收者id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '电话交换' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for position
-- ----------------------------
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position`  (
  `position_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '岗位id',
  `position_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '岗位名称',
  `position_category` tinyint(0) NULL DEFAULT NULL COMMENT '岗位类别（1表示校招；2表示社招）',
  `company_id` bigint(0) NULL DEFAULT NULL COMMENT '公司id',
  `position_salary` decimal(10, 2) NULL DEFAULT NULL COMMENT '岗位薪资',
  `position_detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '岗位详情',
  `place_of_work` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作地点',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '招聘人的id',
  PRIMARY KEY (`position_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for resume_exchange
-- ----------------------------
DROP TABLE IF EXISTS `resume_exchange`;
CREATE TABLE `resume_exchange`  (
  `sender_id` bigint(0) NULL DEFAULT NULL COMMENT '简历上传者id',
  `recipient_id` bigint(0) NULL DEFAULT NULL COMMENT '简历接收者id',
  `resume_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '简历url'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '简历交换' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `sex` tinyint(0) NULL DEFAULT NULL COMMENT '性别：0：男；1：女，2：未知',
  `role` tinyint(0) NULL DEFAULT NULL COMMENT '角色：0：超级管理员；1：管理员；2：求职者；3：公司',
  `phone_num` bigint(0) NULL DEFAULT NULL COMMENT '电话号码',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
