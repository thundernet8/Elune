/*
 Navicat Premium Data Transfer

 Source Server         : MySql
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost
 Source Database       : elune

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : utf-8

 Date: 09/14/2017 00:42:08 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_channel`
-- ----------------------------
DROP TABLE IF EXISTS `t_channel`;
CREATE TABLE `t_channel` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '父级ID, 0 表示为顶级频道节点',
  `title` varchar(30) NOT NULL COMMENT '频道名称',
  `description` varchar(255) NOT NULL COMMENT '频道描述',
  `slug` varchar(50) NOT NULL COMMENT '英文简写',
  `cover_img` varchar(100) DEFAULT NULL COMMENT '频道封面图',
  `main_color` int(10) unsigned DEFAULT NULL COMMENT '主色',
  `topics_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '帖子数量',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '频道状态 0 - 删除 1 - 正常',
  `create_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '更新时间',
  `hosts` varchar(255) NOT NULL DEFAULT '' COMMENT '频道主持 多个id以逗号分隔',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_title` (`title`),
  UNIQUE KEY `idx_slug` (`slug`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_update_time` (`update_time`),
  KEY `idx_pid` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='频道(板块)';

-- ----------------------------
--  Table structure for `t_favorite`
-- ----------------------------
DROP TABLE IF EXISTS `t_favorite`;
CREATE TABLE `t_favorite` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL,
  `type` tinyint(4) NOT NULL COMMENT 'Favorite类型 1 - 话题 2 - 用户 3 - 频道',
  `event_id` varchar(64) CHARACTER SET latin1 NOT NULL,
  `action` tinyint(4) NOT NULL COMMENT '动作类型 1 - 收藏 2 - 喜欢 3 - 关注',
  `create_time` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收藏/关注/喜欢操作集合';

-- ----------------------------
--  Table structure for `t_notification`
-- ----------------------------
DROP TABLE IF EXISTS `t_notification`;
CREATE TABLE `t_notification` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) NOT NULL COMMENT '通知类型 1 - 回复 2 - @ 3 - 点赞 4 - 收藏 5 - 帖子置顶 6 - 帖子精华',
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '阅读状态 0 - 未读 1 - 已读',
  `create_time` int(10) unsigned NOT NULL DEFAULT '0',
  `from` varchar(50) NOT NULL,
  `to` varchar(50) NOT NULL,
  `event_id` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_to_user` (`to`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='站内提醒';

-- ----------------------------
--  Table structure for `t_open`
-- ----------------------------
DROP TABLE IF EXISTS `t_open`;
CREATE TABLE `t_open` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) NOT NULL COMMENT 'OpenId类型 1 - QQ 2 - 微信 3 - 微博 4 - 谷歌 5 - Github 6 - Facebook 7 - Twitter',
  `uid` int(11) NOT NULL,
  `openid` varchar(20) NOT NULL,
  `access_token` varchar(50) NOT NULL,
  `refresh_token` varchar(50) DEFAULT NULL,
  `create_time` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方平台连接信息';

-- ----------------------------
--  Table structure for `t_option`
-- ----------------------------
DROP TABLE IF EXISTS `t_option`;
CREATE TABLE `t_option` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `value` text CHARACTER SET utf8mb4 NOT NULL,
  `auto_load` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否自动加载 0 - 否 1 - 是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_post`
-- ----------------------------
DROP TABLE IF EXISTS `t_post`;
CREATE TABLE `t_post` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tid` bigint(20) unsigned NOT NULL COMMENT '话题ID',
  `pid` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '父级Post ID',
  `author` varchar(60) CHARACTER SET utf8mb4 NOT NULL COMMENT '评论回复作者',
  `author_id` bigint(20) unsigned NOT NULL,
  `owner` varchar(60) CHARACTER SET utf8mb4 NOT NULL COMMENT '话题作者',
  `owner_id` bigint(20) unsigned NOT NULL,
  `ip` varchar(64) DEFAULT NULL,
  `ua` varchar(200) DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 NOT NULL COMMENT '评论回复内容',
  `type` tinyint(4) NOT NULL COMMENT 'Post类型 1 - 评论 2 - 回复',
  `status` tinyint(4) NOT NULL COMMENT 'Post状态 0 - 删除 1 - 正常',
  `create_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'Post发布时间',
  PRIMARY KEY (`id`),
  KEY `idx_tid` (`tid`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论回复';

-- ----------------------------
--  Table structure for `t_tag`
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(30) NOT NULL COMMENT '标签名称',
  `slug` varchar(30) NOT NULL COMMENT '英文简写',
  `topics_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '拥有该标签的话题数量',
  `create_time` int(10) unsigned NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_title` (`title`),
  UNIQUE KEY `idx_slug` (`slug`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签';

-- ----------------------------
--  Table structure for `t_token`
-- ----------------------------
DROP TABLE IF EXISTS `t_token`;
CREATE TABLE `t_token` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '关联用户ID 如果没有用户则为0',
  `token` varchar(255) NOT NULL,
  `type` tinyint(4) NOT NULL COMMENT 'Token用途类型 1 - 注册激活 2 - 重置密码 3 - 验证邮箱',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '使用状态 0 - 未使用 1 - 已使用',
  `create_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `expiry_time` int(10) unsigned NOT NULL COMMENT '失效时间',
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='临时性的一些验证Token';

-- ----------------------------
--  Table structure for `t_topic`
-- ----------------------------
DROP TABLE IF EXISTS `t_topic`;
CREATE TABLE `t_topic` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `cid` int(10) NOT NULL COMMENT '所属频道',
  `title` varchar(255) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '话题标题',
  `author` varchar(60) NOT NULL DEFAULT '' COMMENT '作者用户名',
  `author_id` bigint(20) unsigned NOT NULL COMMENT '作者ID',
  `content` longtext CHARACTER SET utf8mb4 NOT NULL COMMENT '帖子正文',
  `is_pinned` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否置顶 0 - 否 1 - 是',
  `is_essence` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否精华帖 0 - 否 1 - 是',
  `views_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '阅读数',
  `upvotes_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '点赞数',
  `downvotes_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '点踩数',
  `favorites_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '收藏数',
  `posts_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '评论回复数',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '帖子状态 0 - 删除 1 - 正常',
  `comment_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '评论状态 0 - 锁定禁止评论 1 - 正常',
  `create_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '更新时间',
  `post_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最后回复时间',
  `factor` int(10) unsigned NOT NULL DEFAULT '60' COMMENT '帖子权重因子',
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`author_id`),
  KEY `idx_cid` (`cid`),
  KEY `idx_post_time` (`post_time`),
  KEY `idx_create_update_time` (`create_time`,`update_time`) USING BTREE,
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='话题';

-- ----------------------------
--  Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `username` varchar(60) NOT NULL,
  `nickname` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `url` varchar(100) NOT NULL DEFAULT '' COMMENT '用户个人主页',
  `join_time` int(10) NOT NULL DEFAULT '0' COMMENT '注册时间',
  `last_seen` int(10) NOT NULL DEFAULT '0' COMMENT '上次登录等操作时间',
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `bio` varchar(255) CHARACTER SET utf8mb4 NOT NULL DEFAULT '',
  `avatar` varchar(100) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '用户头像地址',
  `update_time` int(10) NOT NULL DEFAULT '0' COMMENT '记录更新时间',
  `role_id` tinyint(4) NOT NULL DEFAULT '10' COMMENT '用户角色 0-系统管理员 1-管理员 2-9保留区间 10-普通用户',
  `topics_count` int(10) NOT NULL DEFAULT '0' COMMENT '发布的主题数量',
  `posts_count` int(10) NOT NULL DEFAULT '0' COMMENT '发布的回复评论数量',
  `articles_count` int(10) NOT NULL DEFAULT '0' COMMENT '发布的文章数量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`),
  UNIQUE KEY `idx_email` (`email`),
  KEY `idx_join_time` (`join_time`),
  KEY `idx_update_time` (`update_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
--  Table structure for `t_userlog`
-- ----------------------------
DROP TABLE IF EXISTS `t_userlog`;
CREATE TABLE `t_userlog` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL,
  `type` tinyint(3) unsigned NOT NULL COMMENT '日志事件类型',
  `before` longtext CHARACTER SET utf8mb4 NOT NULL COMMENT '操作之前的值',
  `after` longtext CHARACTER SET utf8mb4 NOT NULL COMMENT '操作之后的值',
  `ip` varchar(64) DEFAULT NULL,
  `ua` varchar(200) DEFAULT NULL,
  `create_time` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户操作日志';

-- ----------------------------
--  Table structure for `t_usermeta`
-- ----------------------------
DROP TABLE IF EXISTS `t_usermeta`;
CREATE TABLE `t_usermeta` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL,
  `key` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `value` longtext CHARACTER SET utf8mb4 NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户附加信息';

SET FOREIGN_KEY_CHECKS = 1;
