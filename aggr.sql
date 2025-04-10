/*
 Navicat Premium Dump SQL

 Source Server         : aliyun_aggr
 Source Server Type    : MySQL
 Source Server Version : 80039 (8.0.39)
 Source Host           : 120.76.53.44:3306
 Source Schema         : aggr

 Target Server Type    : MySQL
 Target Server Version : 80039 (8.0.39)
 File Encoding         : 65001

 Date: 26/03/2025 18:51:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_menu`;
CREATE TABLE `admin_menu`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` bigint NOT NULL,
  `update_time` bigint NOT NULL,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `path` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `parent` int NOT NULL COMMENT '父类菜单，如果是没有父级菜单为0',
  `permission` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '权限标识',
  `sort` int NULL DEFAULT 1 COMMENT '显示顺序',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `el_svg_icon` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `route_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '路由名称',
  `query` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '路由参数',
  `redirect` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '重定向',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `permission_unique`(`permission` ASC) USING BTREE COMMENT '权限需要唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 135 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_menu
-- ----------------------------
INSERT INTO `admin_menu` VALUES (54, 1742396950583, 1742396950583, 'permission', '权限管理', '', '/permission', 'Layout', -1, 'permission', 2, 'M', 'Lock', 'permission', '', NULL);
INSERT INTO `admin_menu` VALUES (55, 1742396950583, 1742396950583, 'user', '用户管理', '', 'user', '/permission/user/index.vue', 54, 'permission-user', 1, 'C', 'UserFilled', 'user', '', NULL);
INSERT INTO `admin_menu` VALUES (56, 1742396950583, 1742396950583, 'role', '角色管理', '', 'role', '/permission/role/index.vue', 54, 'permission-role', 2, 'C', 'Files', 'role', '', NULL);
INSERT INTO `admin_menu` VALUES (57, 1742396950583, 1742396950583, 'permissionData', '权限数据管理', '', 'permissionData', '/permission/permissionData/index.vue', 54, 'permission-permissionData', 3, 'C', 'ScaleToOriginal', 'permissionData', '', NULL);
INSERT INTO `admin_menu` VALUES (58, 1742396950583, 1742396950583, 'user_put', '编辑', '', '', '', 55, 'permission_user_put', 1, 'F', '', '', '', NULL);
INSERT INTO `admin_menu` VALUES (61, 1742396950583, 1742396950583, 'user_del', '删除', '', '', '', 55, 'permission_user_del', 2, 'F', '', '', '', NULL);
INSERT INTO `admin_menu` VALUES (62, 1742396950583, 1742396950583, 'user_add', '新增用户', '', '', '', 55, 'permission_user_add', 3, 'F', '', '', '', NULL);
INSERT INTO `admin_menu` VALUES (63, 1742396950583, 1742396950583, 'user_putpassword', '修改密码', '', '', '', 55, 'permission_user_putpassword', 4, 'F', '', '', '', NULL);
INSERT INTO `admin_menu` VALUES (64, 1742396950583, 1742396950583, 'user_search', '搜索', '', '', '', 55, 'permission_user_search', 5, 'F', '', '', '', NULL);
INSERT INTO `admin_menu` VALUES (65, 1742396950583, 1742396950583, 'role_add', '新增角色', '', '', '', 56, 'permission_role_add', 1, 'F', '', '', '', NULL);
INSERT INTO `admin_menu` VALUES (66, 1742396950583, 1742396950583, 'role_put', '编辑', '', '', '', 56, 'permission_role_put', 2, 'F', '', '', '', NULL);
INSERT INTO `admin_menu` VALUES (67, 1742396950583, 1742396950583, 'role_del', '删除', '', '', '', 56, 'permission_role_del', 3, 'F', '', '', '', NULL);
INSERT INTO `admin_menu` VALUES (68, 1742396950583, 1742396950583, 'role_setpermission', '分配权限', '', '', '', 56, 'permission_role_setpermission', 4, 'F', '', '', '', NULL);
INSERT INTO `admin_menu` VALUES (69, 1742396950583, 1742396950583, 'permission_add', '新增', '', '', '', 57, 'permission_permission_add', 1, 'F', '', '', '', NULL);
INSERT INTO `admin_menu` VALUES (70, 1742396950583, 1742396950583, 'permission_put', '编辑', '', '', '', 57, 'permission_permission_put', 2, 'F', '', '', '', NULL);
INSERT INTO `admin_menu` VALUES (71, 1742396950583, 1742396950583, 'permission_del', '删除', '', '', '', 57, 'permission_permission_del', 3, 'F', '', '', '', NULL);
INSERT INTO `admin_menu` VALUES (73, 1742396950583, 1742396950583, 'home', '首页', 'dashboard', '/', 'Layout', -1, 'home', 1, 'M', '', 'home', '', '/dashboard');
INSERT INTO `admin_menu` VALUES (74, 1742396950583, 1742396950583, 'dashboard', '首页', 'dashboard', 'dashboard', '/dashboard/index.vue', 73, 'dashboard', 1, 'C', '', 'dashboard', '', '');
INSERT INTO `admin_menu` VALUES (75, 1742396950583, 1742396950583, 'merchant', '商户管理', '', '/merchant', 'Layout', -1, 'merchant', 3, 'M', 'User', 'merchant', '', '');
INSERT INTO `admin_menu` VALUES (76, 1742396950583, 1742396950583, 'merchant-list', '商户列表', '', 'list', '/merchant/list/index.vue', 75, 'merchant-list', 1, 'C', 'Memo', 'merchantList', '', '');
INSERT INTO `admin_menu` VALUES (77, 1742396950583, 1742396950583, 'merchant-channel', '商户通道', 'nested', 'channel', '/merchant/channel/index.vue', 75, 'merchant-channel', 2, 'C', '', 'merchantChannel', '', '');
INSERT INTO `admin_menu` VALUES (78, 1742396950583, 1742396950583, 'order', '订单管理', '', '/order', 'Layout', -1, 'order', 4, 'M', 'List', 'order', '', '');
INSERT INTO `admin_menu` VALUES (79, 1742396950583, 1742396950583, 'order-collect', '代收订单', 'collect', 'collect', '/order/collect/index.vue', 78, 'order-collect', 1, 'C', '', 'collect', '', '');
INSERT INTO `admin_menu` VALUES (80, 1742396950583, 1742396950583, 'order-pay', '代付订单', 'pay', 'pay', '/order/pay/index.vue', 78, 'order-pay', 2, 'C', '', 'pay', '', '');
INSERT INTO `admin_menu` VALUES (81, 1742396950583, 1742396950583, 'dollar', '资金管理', 'dollar', '/dollar', 'Layout', -1, 'dollar', 5, 'M', '', 'dollar', '', '');
INSERT INTO `admin_menu` VALUES (82, 1742396950583, 1742396950583, 'dollar-recharge', '充值记录', 'recharge', 'recharge', '/dollar/recharge/index.vue', 81, 'dollar-recharge', 1, 'C', '', 'recharge', '', '');
INSERT INTO `admin_menu` VALUES (83, 1742396950583, 1742396950583, 'dollar-withdraw', '提现记录', 'withdraw', 'withdraw', '/dollar/withdraw/index.vue', 81, 'dollar-withdraw', 2, 'C', '', 'withdraw', '', '');
INSERT INTO `admin_menu` VALUES (84, 1742396950583, 1742396950583, 'dollar-statement', '流水记录', 'statement', 'statement', '/dollar/statement/index.vue', 81, 'dollar-statement', 3, 'C', '', 'statement', '', '');
INSERT INTO `admin_menu` VALUES (85, 1742396950583, 1742396950583, 'dollar-settlement', '结算记录', 'settlement', 'settlement', '/dollar/settlement/index.vue', 81, 'dollar-settlement', 4, 'C', '', 'settlement', '', '');
INSERT INTO `admin_menu` VALUES (86, 1742396950583, 1742396950583, 'system', '系统管理', '', '/system', 'Layout', -1, 'system', 6, 'M', 'Setting', 'system', '', '');
INSERT INTO `admin_menu` VALUES (87, 1742396950583, 1742396950583, 'system-download', '下载中心', '', 'download', '/system/download/index.vue', 86, 'system-download', 1, 'C', 'Download', 'download', '', '');
INSERT INTO `admin_menu` VALUES (88, 1742396950583, 1742396950583, 'system-notice', '通知公告', 'notice', 'notice', '/system/notice/index.vue', 86, 'system-notice', 2, 'C', '', 'notice', '', '');
INSERT INTO `admin_menu` VALUES (90, 1742396950583, 1742396950583, 'merchant-list_search', '搜索', '', '', '', 76, 'merchant-list_search', 1, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (91, 1742396950583, 1742396950583, 'merchant-list_add', '新增商户', '', '', '', 76, 'merchant-list_add', 2, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (92, 1742396950583, 1742396950583, 'merchant-list_config', '配制中心', '', '', '', 76, 'merchant-list_config', 3, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (93, 1742396950583, 1742396950583, 'merchant-list_basic', '配制中心基本信息确定', '', '', '', 76, 'merchant-list_basic', 4, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (94, 1742396950583, 1742396950583, 'merchant-list_currency', '配制中心国家设置确定', '', '', '', 76, 'merchant-list_currency', 5, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (95, 1742396950583, 1742396950583, 'merchant-list_channel', '配制中心通道设置确定', '', '', '', 76, 'merchant-list_channel', 1, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (96, 1742396950583, 1742396950583, 'merchant-channel_search', '搜索', '', '', '', 77, 'merchant-channel_search', 1, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (97, 1742396950583, 1742396950583, 'merchant-channel_config', '配制通道', '', '', '', 77, 'merchant-channel_config', 2, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (98, 1742396950583, 1742396950583, 'order-collect_search', '搜索', '', '', '', 79, 'order-collect_search', 1, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (99, 1742396950583, 1742396950583, 'order-collect_tb', '同步', '', '', '', 79, 'order-collect_tb', 2, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (100, 1742396950583, 1742396950583, 'order-collect_callback', '回调', '', '', '', 79, 'order-collect_callback', 3, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (101, 1742396950583, 1742396950583, 'order-collect_putstate', '修改订单状态', '', '', '', 79, 'order-collect_putstate', 4, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (102, 1742396950583, 1742396950583, 'order-pay_search', '搜索', '', '', '', 80, 'order-pay_search', 1, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (103, 1742396950583, 1742396950583, 'order-pay_tb', '同步', '', '', '', 80, 'order-pay_tb', 2, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (104, 1742396950583, 1742396950583, 'order-pay_callback', '回调', '', '', '', 80, 'order-pay_callback', 3, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (105, 1742396950583, 1742396950583, 'order-pay_putstate', '修改订单状态', '', '', '', 80, 'order-pay_putstate', 4, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (106, 1742396950583, 1742396950583, 'dollar-recharge_search', '搜索', '', '', '', 82, 'dollar-recharge_search', 1, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (107, 1742396950583, 1742396950583, 'dollar-recharge_recharge', '充值', '', '', '', 82, 'dollar-recharge_recharge', 2, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (108, 1742396950583, 1742396950583, 'dollar-withdraw_search', '搜索', '', '', '', 83, 'dollar-withdraw_search', 1, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (109, 1742396950583, 1742396950583, 'dollar-withdraw_pass', '通过', '', '', '', 83, 'dollar-withdraw_pass', 2, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (110, 1742396950583, 1742396950583, 'dollar-withdraw_pay', '打款', '', '', '', 83, 'dollar-withdraw_pay', 3, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (111, 1742396950583, 1742396950583, 'dollar-statement_search', '搜索', '', '', '', 84, 'dollar-statement_search', 1, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (112, 1742396950583, 1742396950583, 'dollar-settlement_search', '搜索', '', '', '', 85, 'dollar-settlement_search', 1, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (113, 1742396950583, 1742396950583, 'dollar-settlement_channel', '通道结算申请', '', '', '', 85, 'dollar-settlement_channel', 2, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (114, 1742396950583, 1742396950583, 'dollar-settlement_merchant', '商户结算申请', '', '', '', 85, 'dollar-settlement_merchant', 2, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (115, 1742396950583, 1742396950583, 'dollar-withdraw_withdraw', '提现申请', '', '', '', 83, 'dollar-withdraw_withdraw', 4, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (116, 1742396950583, 1742396950583, 'permission_user_bind', '绑定商户', '', '', '', 55, 'permission_user_bind', 6, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (117, 1742396950583, 1742396950583, 'merchant-rateGroup', '费率组', 'rate', 'rateGroup', '/merchant/rateGroup/index.vue', 75, 'merchant-rateGroup', 3, 'C', '', 'rateGroup', '', '');
INSERT INTO `admin_menu` VALUES (118, 1742396950583, 1742396950583, 'system-notice_send', '发送', '', '', '', 88, 'system-notice_send', 1, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (119, 1742396950583, 1742396950583, 'merchant-list_changeusdt', '更改Usdt地址', '', '', '', 76, 'merchant-list_changeusdt', 6, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (120, 1742396950583, 1742396950583, 'merchant-rateGroup_add', '新增费率组', '', '', '', 117, 'merchant-rateGroup_add', 1, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (121, 1742396950583, 1742396950583, 'merchant-rateGroup_rule', '新增费率细则', '', '', '', 117, 'merchant-rateGroup_rule', 2, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (122, 1742396950583, 1742396950583, 'merchant-rateGroup_putrule', '修改费率细则', '', '', '', 117, 'merchant-rateGroup_putrule', 3, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (123, 1742396950583, 1742396950583, 'merchant-list_test', '创建测试链接', '', '', '', 76, 'merchant-list_test', 7, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (125, 1742396950583, 1742396950583, 'order-collect_down', '下载', '', '', '', 79, 'order-collect_down', 5, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (126, 1742396950583, 1742396950583, 'order-pay_down', '下载', '', '', '', 80, 'order-pay_down', 5, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (127, 1742396950583, 1742396950583, 'dollar-statement_down', '下载', '', '', '', 84, 'dollar-statement_down', 2, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (128, 1742396950583, 1742396950583, 'merchant-list_resetid', '重置商户密钥', '', '', '', 76, 'merchant-list_resetid', 8, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (129, 1742396950583, 1742396950583, 'merchant-apidocument', 'API文档', 'documentation', 'apidocument', '/merchant/apidocument/index.vue', 75, 'merchant-apidocument', 4, 'C', '', 'apidocument', '', '');
INSERT INTO `admin_menu` VALUES (130, 1742396950583, 1742396950583, 'merchant-list_setip', '配制ip白名单', '', '', '', 76, 'merchant-list_setip', 9, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (131, 1742396950583, 1742396950583, 'merchant-list_globaltest', '全局创建测试链接', '', '', '', 76, 'merchant-list_globaltest', 11, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (132, 1742396950583, 1742396950583, 'order-controversy', '争议订单', 'order', 'controversy', '/order/controversy/index.vue', 78, 'order-controversy', 3, 'C', '', 'controversy', '', '');
INSERT INTO `admin_menu` VALUES (133, 1742396950583, 1742396950583, 'order-controversy_search', '搜索', '', '', '', 132, 'order-controversy_search', 1, 'F', '', '', '', '');
INSERT INTO `admin_menu` VALUES (134, 1742396950583, 1742396950583, 'order-controversy_set', '审核订单', '', '', '', 132, 'order-controversy_set', 2, 'F', '', '', '', '');

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `active` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否激活,1激活，0不激活',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色描述',
  `create_time` bigint NOT NULL COMMENT '创建时间',
  `update_time` bigint NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_unique`(`role_name` ASC) USING BTREE COMMENT '角色名唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统角色表，存储系统中各个角色的信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES (3, '系统管理员', 1, '', 1742396950583, 1742396950583);
INSERT INTO `admin_role` VALUES (4, '内部运营使用', 1, '', 1742396950583, 1742396950583);
INSERT INTO `admin_role` VALUES (8, '财务对账使用', 1, NULL, 1742396950583, 1742396950583);
INSERT INTO `admin_role` VALUES (10, '普通商户使用', 1, NULL, 1742396950583, 1742396950583);
INSERT INTO `admin_role` VALUES (11, '代理使用', 1, '', 1742396950583, 1742396950583);

-- ----------------------------
-- Table structure for admin_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_menu`;
CREATE TABLE `admin_role_menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int NOT NULL COMMENT '角色id',
  `menu_id` int NOT NULL COMMENT '菜单id',
  `create_time` bigint NOT NULL COMMENT '创建时间',
  `update_time` bigint NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_role_id_menu_id`(`role_id` ASC, `menu_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_role_menu
-- ----------------------------
INSERT INTO `admin_role_menu` VALUES (1, 10, 87, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (2, 10, 86, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (3, 10, 112, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (4, 10, 85, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (5, 10, 127, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (6, 10, 111, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (7, 10, 84, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (8, 10, 115, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (9, 10, 108, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (10, 10, 83, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (11, 10, 106, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (12, 10, 82, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (13, 10, 81, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (14, 10, 126, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (15, 10, 104, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (16, 10, 103, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (17, 10, 102, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (18, 10, 80, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (19, 10, 125, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (20, 10, 100, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (21, 10, 99, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (22, 10, 98, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (23, 10, 79, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (24, 10, 78, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (25, 10, 129, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (26, 10, 130, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (27, 10, 128, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (28, 10, 123, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (29, 10, 76, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (30, 10, 75, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (31, 10, 74, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (32, 10, 73, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (33, 3, 118, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (34, 3, 88, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (35, 3, 87, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (36, 3, 86, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (37, 3, 114, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (38, 3, 113, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (39, 3, 112, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (40, 3, 85, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (41, 3, 127, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (42, 3, 111, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (43, 3, 84, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (44, 3, 115, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (45, 3, 110, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (46, 3, 109, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (47, 3, 108, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (48, 3, 83, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (49, 3, 107, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (50, 3, 106, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (51, 3, 82, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (52, 3, 81, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (53, 3, 134, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (54, 3, 133, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (55, 3, 132, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (56, 3, 126, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (57, 3, 105, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (58, 3, 104, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (59, 3, 103, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (60, 3, 102, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (61, 3, 80, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (62, 3, 125, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (63, 3, 101, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (64, 3, 100, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (65, 3, 99, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (66, 3, 98, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (67, 3, 79, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (68, 3, 78, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (69, 3, 129, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (70, 3, 122, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (71, 3, 121, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (72, 3, 120, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (73, 3, 117, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (74, 3, 97, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (75, 3, 96, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (76, 3, 77, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (77, 3, 131, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (78, 3, 130, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (79, 3, 128, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (80, 3, 123, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (81, 3, 119, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (82, 3, 94, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (83, 3, 93, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (84, 3, 92, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (85, 3, 91, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (86, 3, 95, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (87, 3, 90, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (88, 3, 76, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (89, 3, 75, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (90, 3, 71, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (91, 3, 70, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (92, 3, 69, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (93, 3, 57, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (94, 3, 68, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (95, 3, 67, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (96, 3, 66, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (97, 3, 65, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (98, 3, 56, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (99, 3, 116, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (100, 3, 64, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (101, 3, 63, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (102, 3, 62, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (103, 3, 61, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (104, 3, 58, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (105, 3, 55, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (106, 3, 54, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (107, 3, 74, 1742396950583, 1742396950583);
INSERT INTO `admin_role_menu` VALUES (108, 3, 73, 1742396950583, 1742396950583);

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT 1,
  `role_id` int NOT NULL COMMENT '角色id',
  `create_time` bigint NOT NULL,
  `update_time` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_admin_username_uindex`(`user_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user
-- ----------------------------

-- ----------------------------
-- Table structure for auth_token
-- ----------------------------
DROP TABLE IF EXISTS `auth_token`;
CREATE TABLE `auth_token`  (
  `token` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '登录token',
  `create_time` bigint NOT NULL COMMENT '创建时间',
  `update_time` bigint NOT NULL COMMENT '更新时间',
  `user_id` int NOT NULL COMMENT '用户id',
  `device_token` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设备token',
  `expire_time` bigint NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`token`) USING BTREE,
  UNIQUE INDEX `device_token`(`device_token` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of auth_token
-- ----------------------------

-- ----------------------------
-- Table structure for channel
-- ----------------------------
DROP TABLE IF EXISTS `channel`;
CREATE TABLE `channel`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `channel_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '渠道名称',
  `channel_label` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '渠道唯一标识',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'BOTH' COMMENT '类型: PAYIN-代收,PAYOUT-代付,BOTH-都支持',
  `active` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否激活,1启用，0禁止',
  `weigh` int NOT NULL COMMENT '权重(用于分配优先级)',
  `payin_fee_rate` decimal(10, 4) NOT NULL COMMENT '代收手续费比例',
  `payout_fee_rate` decimal(10, 4) NOT NULL COMMENT '代付手续费比例',
  `payout_single_transaction_fee` decimal(10, 4) NOT NULL COMMENT '代付单笔手续费',
  `payin_min_amount` decimal(10, 4) NOT NULL COMMENT '代收最小金额',
  `payin_max_amount` decimal(10, 4) NOT NULL COMMENT '代收最大金额',
  `payout_min_amount` decimal(10, 4) NOT NULL COMMENT '代付最小金额',
  `payout_max_amount` decimal(10, 4) NOT NULL COMMENT '代付最大金额',
  `create_time` bigint NOT NULL COMMENT '创建时间',
  `update_time` bigint NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `channel_type_index`(`type` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '支付渠道表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of channel
-- ----------------------------

-- ----------------------------
-- Table structure for channel_config
-- ----------------------------
DROP TABLE IF EXISTS `channel_config`;
CREATE TABLE `channel_config`  (
  `channel_label` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '渠道唯一标识',
  `domain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付网关域名',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商户id',
  `public_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付平台公钥',
  `private_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商户私钥',
  `active` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否激活,1启用，0禁止',
  `other_config` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '其它配置信息',
  `create_time` bigint NOT NULL COMMENT '创建时间',
  `update_time` bigint NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`channel_label`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '支付渠道配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of channel_config
-- ----------------------------

-- ----------------------------
-- Table structure for event_login
-- ----------------------------
DROP TABLE IF EXISTS `event_login`;
CREATE TABLE `event_login`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `create_time` bigint NOT NULL,
  `username` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `totp` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `success` tinyint NOT NULL,
  `remark` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of event_login
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
