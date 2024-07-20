/*
 Navicat Premium Data Transfer

 Source Server         : 课程设计
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : train

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 30/09/2022 20:22:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `user_id` int NOT NULL DEFAULT 0 COMMENT '暂时用不到',
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `departure_time` datetime NULL DEFAULT NULL COMMENT '发车日期',
  `arrival_time` datetime NULL DEFAULT NULL COMMENT '到站日期',
  `destination` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '终点站',
  `origin_station` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '起点站',
  `car` int NULL DEFAULT NULL COMMENT '车厢',
  `ticket` int NULL DEFAULT NULL COMMENT '剩余票数',
  `seat` int NULL DEFAULT NULL COMMENT '座位',
  `period` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `ticket_remain` int NULL DEFAULT NULL,
  `ticket_now` int NULL DEFAULT NULL,
  `time_string` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `date_string` varchar(255) CHARACTER SET utf32 COLLATE utf32_general_ci NULL DEFAULT NULL,
  `name1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (0, 9, 'G7', 200.00, '2022-09-09 10:00:00', '2022-09-09 12:20:00', '南京', '北京', 3, 60, 20, '2小时20分', 60, 1, '2022-09-09 10:00:00', '2022-09-09', NULL);
INSERT INTO `book` VALUES (0, 10, 'G117', 50.00, '2022-09-09 12:40:00', '2022-09-09 14:00:00', '上海', '南京', 5, 75, 15, '1小时20分', 75, 0, '2022-09-09 12:40:00', '2022-09-09', NULL);
INSERT INTO `book` VALUES (0, 11, 'G118', 50.00, '2022-09-09 12:55:00', '2022-09-09 14:20:00', '上海', '南京', 5, 50, 10, '1小时25分', 50, 1, '2022-09-09 12:55:00', '2022-09-09', NULL);
INSERT INTO `book` VALUES (0, 12, 'G119', 45.00, '2022-09-09 04:03:00', '2022-09-09 05:22:00', '上海', '南京', 4, 60, 15, '1小时19分', 0, 0, '2022-09-09 04:03:00', '2022-09-09', NULL);
INSERT INTO `book` VALUES (0, 13, 'G120', 190.00, '2022-10-02 00:00:00', '2022-10-02 02:00:00', '杭州', '北京', 4, 40, 10, '2小时0分', 39, 1, '2022-10-02 00:00:00', '2022-10-02', NULL);
INSERT INTO `book` VALUES (0, 14, 'G121', 55.00, '2022-10-02 02:25:00', '2022-10-02 03:00:00', '上海', '杭州', 1, 5, 5, '35分', 4, 1, '2022-10-02 02:25:00', '2022-10-02', NULL);
INSERT INTO `book` VALUES (0, 16, 'G100', 100.00, '2022-10-03 08:00:00', '2022-10-03 11:01:00', '北京', '济南', 3, 3, 1, '3小时1分', 3, 1, '2022-10-03 08:00:00', '2022-10-03', NULL);
INSERT INTO `book` VALUES (0, 17, 'G100', 100.00, '2022-10-03 08:00:00', '2022-10-03 11:01:00', '北京', '济南', 3, 9, 3, '3小时1分', 9, 2, '2022-10-03 08:00:00', '2022-10-03', NULL);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `pid` int NULL DEFAULT NULL COMMENT '父节点id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '资源路径',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `icon` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 96 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 'Home', '/home', '主页', 'el-icon-house');
INSERT INTO `permission` VALUES (3, 'Category', '/category', '分类管理', 'el-icon-menu');
INSERT INTO `permission` VALUES (9, 'User', '/user', '用户管理', 'el-icon-user');
INSERT INTO `permission` VALUES (10, 'Permission', '/permisssion', '权限菜单', 'el-icon-menu');
INSERT INTO `permission` VALUES (11, 'Role', '/role', '角色管理', 'el-icon-s-custom');
INSERT INTO `permission` VALUES (13, 'Person', '/person', '个人信息', '');
INSERT INTO `permission` VALUES (14, 'Password', '/password', '修改密码', NULL);
INSERT INTO `permission` VALUES (16, 'BookManage', '/bookManage', '修改车票', 'el-icon-document');
INSERT INTO `permission` VALUES (89, 'TicketList', '/ticketList', '查看车票', 'el-icon-tickets');
INSERT INTO `permission` VALUES (90, 'Book', '/book', '订票', 'el-icon-files');
INSERT INTO `permission` VALUES (93, 'Transfer', '/transfer', '换乘', 'el-icon-tickets');
INSERT INTO `permission` VALUES (94, 'Order', '/order', '订单', 'el-icon-s-order');
INSERT INTO `permission` VALUES (95, 'Test', '/test', 'test', NULL);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'admin', '管理员');
INSERT INTO `role` VALUES (2, 'user', '普通用户');
INSERT INTO `role` VALUES (4, '1', '1');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `role_id` int NOT NULL COMMENT '角色id',
  `permission_id` int NOT NULL COMMENT '资源id',
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 1);
INSERT INTO `role_permission` VALUES (1, 9);
INSERT INTO `role_permission` VALUES (1, 10);
INSERT INTO `role_permission` VALUES (1, 11);
INSERT INTO `role_permission` VALUES (1, 13);
INSERT INTO `role_permission` VALUES (1, 14);
INSERT INTO `role_permission` VALUES (1, 16);
INSERT INTO `role_permission` VALUES (1, 89);
INSERT INTO `role_permission` VALUES (1, 90);
INSERT INTO `role_permission` VALUES (1, 93);
INSERT INTO `role_permission` VALUES (1, 94);
INSERT INTO `role_permission` VALUES (2, 1);
INSERT INTO `role_permission` VALUES (2, 9);
INSERT INTO `role_permission` VALUES (2, 13);
INSERT INTO `role_permission` VALUES (2, 14);
INSERT INTO `role_permission` VALUES (2, 90);
INSERT INTO `role_permission` VALUES (2, 93);
INSERT INTO `role_permission` VALUES (2, 94);
INSERT INTO `role_permission` VALUES (2, 95);

-- ----------------------------
-- Table structure for sold
-- ----------------------------
DROP TABLE IF EXISTS `sold`;
CREATE TABLE `sold`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `book_id` int NULL DEFAULT NULL,
  `ticket` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sold
-- ----------------------------
INSERT INTO `sold` VALUES (1, 3, 1);
INSERT INTO `sold` VALUES (2, 3, 2);
INSERT INTO `sold` VALUES (3, 1, 2);
INSERT INTO `sold` VALUES (4, 2, 1);

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `transport_price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '暂时没用',
  `discount` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '优惠金额 暂时没用',
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '总价',
  `pay_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '实付款',
  `order_no` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单编号',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户账户',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `pay_time` timestamp NULL DEFAULT NULL COMMENT '支付时间',
  `state` int NOT NULL DEFAULT 0 COMMENT '状态',
  `origin_station` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '起点站',
  `destination` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '终点站',
  `departure_time` datetime NULL DEFAULT NULL COMMENT '发车时间',
  `arrival_time` datetime NULL DEFAULT NULL COMMENT '到站时间',
  `car` int NULL DEFAULT NULL COMMENT '车厢号',
  `seat` int NULL DEFAULT NULL COMMENT '座号',
  `ticket` int NULL DEFAULT NULL,
  `book_id` int NULL DEFAULT NULL,
  `transfer_station` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `departure_time1` datetime NULL DEFAULT NULL,
  `arrival_time1` datetime NULL DEFAULT NULL,
  `car1` int NULL DEFAULT NULL,
  `seat1` int NULL DEFAULT NULL,
  `ticket1` int NULL DEFAULT NULL,
  `Book_id1` int NULL DEFAULT 0,
  `name1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 177 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES (0.00, 0.00, 108, 'z121', 60.00, 60.00, '1566997198766399488', 1, 'admin', '2022-09-07 02:18:10', '2022-09-06 15:31:40', 3, '上海', '重庆', '2022-09-06 01:09:00', '2022-09-06 02:06:49', 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, NULL);
INSERT INTO `t_order` VALUES (0.00, 0.00, 110, 'z121', 60.00, 60.00, '1566997216210509824', 1, 'admin', '2022-09-06 18:33:08', '2022-09-06 18:33:08', 3, '上海', '重庆', '2022-09-06 01:09:00', '2022-09-06 02:06:49', 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, NULL);
INSERT INTO `t_order` VALUES (0.00, 0.00, 138, 'z120', 60.00, 60.00, '1567500023611084800', 1, 'admin', '2022-09-07 21:12:15', '2022-09-07 21:12:14', 3, '上海', '北京', '2022-09-08 04:00:00', '2022-09-08 08:00:00', 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, NULL);
INSERT INTO `t_order` VALUES (0.00, 0.00, 144, 'G121', 55.00, 55.00, '1567509855567106048', 1, 'admin', '2022-09-07 21:48:24', '2022-09-07 21:48:22', 3, '杭州', '上海', '2022-09-13 02:25:00', '2022-09-13 03:00:00', 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, NULL);
INSERT INTO `t_order` VALUES (0.00, 0.00, 154, 'G121', 55.00, 55.00, '1567543910610350080', 1, 'admin', '2022-09-08 00:02:46', '2022-09-08 00:02:47', 3, '杭州', '上海', '2022-09-13 02:25:00', '2022-09-13 03:00:00', 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, NULL);
INSERT INTO `t_order` VALUES (0.00, 0.00, 156, 'G121', 55.00, 55.00, '1567550905056759808', 1, 'admin', '2022-09-08 08:16:02', '2022-09-08 08:16:00', 3, '杭州', '上海', '2022-09-13 02:25:00', '2022-09-13 03:00:00', 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, NULL);
INSERT INTO `t_order` VALUES (0.00, 0.00, 167, 'G121', 55.00, 55.00, '1567703850482208768', 13, 'zhang', '2022-09-08 10:38:19', NULL, 0, '杭州', '上海', '2022-09-11 10:25:00', '2022-09-11 11:00:00', 1, 3, 3, 14, NULL, NULL, NULL, 0, 0, 0, 0, NULL);
INSERT INTO `t_order` VALUES (0.00, 0.00, 168, 'G100', 100.00, 100.00, '1567703972419014656', 13, 'zhang', '2022-09-08 10:39:09', '2022-09-08 10:39:07', 3, '济南', '北京', '2022-09-09 04:00:00', '2022-09-09 07:01:00', 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, NULL);
INSERT INTO `t_order` VALUES (0.00, 0.00, 170, 'G100', 100.00, 100.00, '1567704452683599872', 13, 'zhang', '2022-09-08 10:40:42', '2022-09-08 10:40:43', 1, '济南', '北京', '2022-09-10 16:00:00', '2022-09-10 19:01:00', 1, 1, 1, 17, NULL, NULL, NULL, 0, 0, 0, 0, NULL);
INSERT INTO `t_order` VALUES (0.00, 0.00, 173, 'G100', 100.00, 100.00, '1575803809753276416', 1, 'admin', '2022-09-30 19:05:20', '2022-09-30 19:05:19', 3, '济南', '北京', '2022-10-02 00:00:00', '2022-10-02 03:01:00', 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, NULL);
INSERT INTO `t_order` VALUES (0.00, 0.00, 175, 'G100', 100.00, 100.00, '1575817823891824640', 1, 'admin', '2022-09-30 20:00:21', '2022-09-30 20:00:21', 3, '济南', '北京', '2022-10-03 08:00:00', '2022-10-03 11:01:00', 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, 0, 0, NULL);
INSERT INTO `t_order` VALUES (0.00, 0.00, 176, 'G120', 245.00, 245.00, '1575820237499867136', 1, 'admin', '2022-09-30 20:09:56', NULL, 0, '北京', '上海', '2022-10-02 00:00:00', '2022-10-02 03:00:00', 1, 1, 1, 13, '杭州', '2022-10-02 02:25:00', '2022-10-02 02:00:00', 1, 1, 1, 14, 'G121');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '票类型',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `account` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '账户余额',
  `identify` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '身份证号码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '$2a$10$.cxJ637DYA1PRSzfPmBO0efS1W2eTQefS51b83Nr5xgRNAW0ffgjO', '1', 32, '未知', '无', '未知', '无', NULL, '无');
INSERT INTO `user` VALUES (13, 'zhang', '$2a$10$h6gfZRMvGFjjQH6HrQf.Q.fvu3jjMc0cv/sGpSanobht5iwCFbucS', '张三', 20, '男', '济南', '学生', '12345678912', NULL, '123456789123456789');
INSERT INTO `user` VALUES (17, '1', '$2a$10$6ofadD3ywmD62KkEVzKaB.FCzyWVGxv6ULuR8C5LjIv1a.xxWWKCK', '1', 1, '男', '1', NULL, NULL, 0.00, NULL);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` int NOT NULL COMMENT '用户id',
  `role_id` int NOT NULL COMMENT '角色id',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (13, 2);
INSERT INTO `user_role` VALUES (17, 2);

SET FOREIGN_KEY_CHECKS = 1;
