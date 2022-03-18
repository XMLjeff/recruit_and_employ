/*
 Navicat Premium Data Transfer

 Source Server         : 谷粒商城
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : 121.199.77.187:3306
 Source Schema         : recruit_and_employ

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 18/03/2022 11:23:41
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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公司表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES (1, '阿里巴巴', '阿里巴巴');
INSERT INTO `company` VALUES (2, '腾讯', '腾讯');
INSERT INTO `company` VALUES (3, '华为', '华为');
INSERT INTO `company` VALUES (4, '字节跳动', '字节跳动');
INSERT INTO `company` VALUES (5, '米哈游', '米哈游');

-- ----------------------------
-- Table structure for company_user
-- ----------------------------
DROP TABLE IF EXISTS `company_user`;
CREATE TABLE `company_user`  (
  `company_id` bigint(0) NOT NULL COMMENT '公司id',
  `user_id` bigint(0) NOT NULL COMMENT '用户id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公司、用户关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of company_user
-- ----------------------------
INSERT INTO `company_user` VALUES (1, 2);
INSERT INTO `company_user` VALUES (2, 3);
INSERT INTO `company_user` VALUES (3, 4);
INSERT INTO `company_user` VALUES (4, 5);
INSERT INTO `company_user` VALUES (5, 6);

-- ----------------------------
-- Table structure for job_seekers
-- ----------------------------
DROP TABLE IF EXISTS `job_seekers`;
CREATE TABLE `job_seekers`  (
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `intended_position` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '意向岗位',
  `intended_place_of_work` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '意向工作地点',
  `salary_expectation` decimal(10, 2) NULL DEFAULT NULL COMMENT '期望薪资',
  `scholarship_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '奖学金信息',
  `introduction` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '个人介绍',
  `resume_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '简历url',
  `work_experience` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作经历',
  `graduation_time` date NULL DEFAULT NULL COMMENT '毕业时间',
  `university` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '院校',
  `major` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '专业',
  `professional_skill` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '专业技能'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '求职者' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of job_seekers
-- ----------------------------
INSERT INTO `job_seekers` VALUES (13, '开发岗位', '上海', 5123.00, 'worker', 'worker', 'http://localhost:9100/recruitAndEmploy/2022/03/18/f372fc51-47a5-447e-b133-79afb7a73ff4.docx', 'worker', '2021-07-02', '清华大学', '计算机', 'worker');

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
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (2, 13, '测试', '2022-03-18 10:11:54');
INSERT INTO `message` VALUES (2, NULL, 'adasd', '2022-03-18 10:43:29');

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
-- Records of phone_exchange
-- ----------------------------
INSERT INTO `phone_exchange` VALUES (2, 615123123, NULL);

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
  `work_experience` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作经验要求',
  `education` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学历要求',
  `job_responsibility` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '岗位职责',
  `job_requirement` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任职要求',
  `position_benefits` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职位福利',
  `working_years` int(0) NULL DEFAULT NULL COMMENT '工作年限',
  PRIMARY KEY (`position_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of position
-- ----------------------------
INSERT INTO `position` VALUES (1, '测试岗位', 1, 1, 12312.00, '测试岗位', '浙江', 2, '测试岗位', '硕士', '测试岗位', '测试岗位', '测试岗位', 2);
INSERT INTO `position` VALUES (2, '开发岗位', 1, 1, 512312.00, '开发岗位', '浙江', 2, '开发岗位', '硕士', '开发岗位', '开发岗位', '开发岗位', 3);
INSERT INTO `position` VALUES (3, '开发岗位', 2, 3, 15231.00, '开发岗位', '浙江', 4, '开发岗位', '硕士', '开发岗位', '开发岗位', '开发岗位', 12);
INSERT INTO `position` VALUES (4, '开发岗位', 2, 4, 5121.00, '开发岗位', '浙江', 5, '开发岗位', '硕士', '开发岗位', '开发岗位', '开发岗位', 5);
INSERT INTO `position` VALUES (5, 'java开发', 2, 5, 1213123.00, 'java开发', '上海', 6, 'java开发', '硕士', 'java开发', 'java开发', 'java开发', 3);
INSERT INTO `position` VALUES (6, '前端工程师', 1, 5, 123123.00, '前端工程师', '浙江', 6, '前端工程师', '硕士', '前端工程师', '前端工程师', '前端工程师', 3);
INSERT INTO `position` VALUES (7, '高级前端工程师', 2, 5, 512312.00, '高级前端工程师', '浙江', 6, '高级前端工程师', '硕士', '高级前端工程师', '高级前端工程师', '高级前端工程师', 4);
INSERT INTO `position` VALUES (8, '产品运维', 1, 3, 5123.00, '产品运维', '浙江', 4, '产品运维', '硕士', '产品运维', '产品运维', '产品运维', 2);
INSERT INTO `position` VALUES (9, '产品经理', 1, 3, 5123.00, '产品经理', '上海', 4, '产品经理', '硕士', '产品经理', '产品经理', '产品经理', 4);
INSERT INTO `position` VALUES (10, '产品经理', 1, 4, 5122.00, '产品经理', '北京', 5, '产品经理', '硕士', '产品经理', '产品经理', '产品经理', 5);

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
-- Records of resume_exchange
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '超级管理员', 0, 0, 18888888888);
INSERT INTO `user` VALUES (2, 'ali', 'e10adc3949ba59abbe56e057f20f883e', 'ali', 1, 3, 615123123);
INSERT INTO `user` VALUES (3, 'tencent', 'e10adc3949ba59abbe56e057f20f883e', 'tencent', 0, 3, 15123123);
INSERT INTO `user` VALUES (4, 'huawei', 'e10adc3949ba59abbe56e057f20f883e', 'huawei', 2, 3, 15123123);
INSERT INTO `user` VALUES (5, 'byte', 'e10adc3949ba59abbe56e057f20f883e', 'byte', 0, 3, 15123123);
INSERT INTO `user` VALUES (6, 'mihuyo', 'e10adc3949ba59abbe56e057f20f883e', 'mihuyo', 1, 3, 5123123);
INSERT INTO `user` VALUES (10, 'admin1', 'e10adc3949ba59abbe56e057f20f883e', 'admin1', 1, 1, 15123123123);
INSERT INTO `user` VALUES (11, 'admin12', 'e10adc3949ba59abbe56e057f20f883e', 'admin12', 1, 1, 124123123);
INSERT INTO `user` VALUES (12, 'admin2', 'e10adc3949ba59abbe56e057f20f883e', 'admin2', 0, 1, 1512312313);
INSERT INTO `user` VALUES (13, 'worker', 'e10adc3949ba59abbe56e057f20f883e', 'worker', 1, 2, 1521123123);

SET FOREIGN_KEY_CHECKS = 1;
