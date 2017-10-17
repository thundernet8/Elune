/*
 Navicat Premium Data Transfer

 Source Server         : MySql
 Source Server Type    : MySQL
 Source Server Version : 50557
 Source Host           : localhost:3306
 Source Schema         : elune

 Target Server Type    : MySQL
 Target Server Version : 50557
 File Encoding         : 65001

 Date: 17/10/2017 14:58:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_channel
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='频道(板块)';

-- ----------------------------
-- Records of t_channel
-- ----------------------------
BEGIN;
INSERT INTO `t_channel` VALUES (1, 0, 'Tint', '关于Tint主题使用问题反馈', 'tint', NULL, 12955875, 0, 1, 1506486895, 0, '');
INSERT INTO `t_channel` VALUES (2, 0, 'WordPress', 'WordPress相关话题', 'wp', NULL, 3049405, 0, 1, 1506486895, 0, '');
INSERT INTO `t_channel` VALUES (3, 0, '开发', '关于Elune Forum开发，例如API、结构等', 'dev', NULL, 6802666, 0, 1, 1506486895, 0, '');
INSERT INTO `t_channel` VALUES (4, 0, '支持', '获取支持，包括使用反馈、安装、开发插件等', 'support', NULL, 16692557, 0, 1, 1506486895, 0, '');
INSERT INTO `t_channel` VALUES (5, 0, 'UX', '关于Elune Forum的用户体验和界面设计', 'ux', NULL, 9627353, 0, 1, 1506486895, 0, '');
INSERT INTO `t_channel` VALUES (6, 0, 'FAQ', '常见问题', 'faq', NULL, 10002632, 0, 1, 1506486895, 0, '');
INSERT INTO `t_channel` VALUES (7, 0, '公告', '站务公告', 'bulletin', NULL, 14862211, 0, 1, 1506486895, 0, '');
INSERT INTO `t_channel` VALUES (8, 0, '建议', '', 'meta', NULL, 13670038, 0, 1, 16144075, 0, '');
INSERT INTO `t_channel` VALUES (9, 0, '灌水', '灌水闲聊休闲区', 'relax', NULL, 10066329, 0, 1, 16144075, 0, '');
INSERT INTO `t_channel` VALUES (10, 0, '其他', '未定义归类频道', 'mars', NULL, 10066278, 0, 1, 16144075, 0, '');
COMMIT;

-- ----------------------------
-- Table structure for t_favorite
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
-- Table structure for t_notification
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
-- Table structure for t_open
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
-- Table structure for t_option
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
-- Table structure for t_post
-- ----------------------------
DROP TABLE IF EXISTS `t_post`;
CREATE TABLE `t_post` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tid` bigint(20) unsigned NOT NULL COMMENT '话题ID',
  `pid` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '父级Post ID',
  `author_name` varchar(60) NOT NULL COMMENT '评论回复作者',
  `author_id` bigint(20) unsigned NOT NULL,
  `topic_author_name` varchar(60) NOT NULL COMMENT '话题作者',
  `topic_author_id` bigint(20) unsigned NOT NULL,
  `ip` varchar(64) DEFAULT NULL,
  `ua` varchar(200) DEFAULT NULL,
  `content` text NOT NULL COMMENT '评论回复内容(纯文本)',
  `content_html` text NOT NULL COMMENT '评论回复内容(Html)',
  `content_raw` text NOT NULL COMMENT '评论回复内容(DraftJS编辑器原始数据)',
  `upvotes_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '点赞数',
  `downvotes_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '点踩数',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT 'Post类型 1 - 评论 2 - 回复',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT 'Post状态 0 - 删除 1 - 正常',
  `create_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'Post发布时间',
  PRIMARY KEY (`id`),
  KEY `idx_tid` (`tid`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='评论回复';

-- ----------------------------
-- Records of t_post
-- ----------------------------
BEGIN;
INSERT INTO `t_post` VALUES (1, 1, 0, 'thundernet8', 1, 'thundernet8', 1, '207.226.143.125', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36', '@thundernet8#0 first comment.', '<p><a href=\"#thread\" class=\"wysiwyg-mention\" data-mention data-value=\"thundernet8#0\">@thundernet8#0</a> first comment.</p>\n', '{\"entityMap\":{\"0\":{\"type\":\"MENTION\",\"mutability\":\"IMMUTABLE\",\"data\":{\"text\":\"@thundernet8#0\",\"value\":\"thundernet8#0\",\"url\":\"#thread\"}}},\"blocks\":[{\"key\":\"ob2h\",\"text\":\"@thundernet8#0 first comment.\",\"type\":\"unstyled\",\"depth\":0,\"inlineStyleRanges\":[],\"entityRanges\":[{\"offset\":0,\"length\":14,\"key\":0}],\"data\":{}}]}', 0, 0, 1, 1, 1508104654);
INSERT INTO `t_post` VALUES (2, 1, 0, 'thundernet8', 1, 'thundernet8', 1, '218.82.163.155', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36', '@thundernet8#1 reply comment', '<p><a href=\"#post-1\" class=\"wysiwyg-mention\" data-mention data-value=\"thundernet8#1\">@thundernet8#1</a> reply comment</p>\n', '{\"entityMap\":{\"0\":{\"type\":\"MENTION\",\"mutability\":\"IMMUTABLE\",\"data\":{\"text\":\"@thundernet8#1\",\"value\":\"thundernet8#1\",\"url\":\"#post-1\"}}},\"blocks\":[{\"key\":\"ob2h\",\"text\":\"@thundernet8#1 reply comment\",\"type\":\"unstyled\",\"depth\":0,\"inlineStyleRanges\":[],\"entityRanges\":[{\"offset\":0,\"length\":14,\"key\":0}],\"data\":{}}]}', 0, 0, 1, 1, 1508130932);
INSERT INTO `t_post` VALUES (3, 1, 0, 'thundernet8', 1, 'thundernet8', 1, '218.82.163.155', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36', '\n \nimage test', '<p></p>\n<img src=\"https://elune.fuli.news/content/images/2017/10/16/28579a029c88a9d4fd0ad6edbb01d38b.jpg\" alt=\"undefined\" style=\"float:none;height: auto;width: auto\"/>\n<p>image test</p>\n', '{\"entityMap\":{\"0\":{\"type\":\"IMAGE\",\"mutability\":\"MUTABLE\",\"data\":{\"src\":\"https://elune.fuli.news/content/images/2017/10/16/28579a029c88a9d4fd0ad6edbb01d38b.jpg\",\"height\":\"auto\",\"width\":\"auto\"}}},\"blocks\":[{\"key\":\"4i59u\",\"text\":\"\",\"type\":\"unstyled\",\"depth\":0,\"inlineStyleRanges\":[],\"entityRanges\":[],\"data\":{}},{\"key\":\"aja80\",\"text\":\" \",\"type\":\"atomic\",\"depth\":0,\"inlineStyleRanges\":[],\"entityRanges\":[{\"offset\":0,\"length\":1,\"key\":0}],\"data\":{}},{\"key\":\"b7i3v\",\"text\":\"image test\",\"type\":\"unstyled\",\"depth\":0,\"inlineStyleRanges\":[],\"entityRanges\":[],\"data\":{}}]}', 0, 0, 1, 1, 1508132192);
INSERT INTO `t_post` VALUES (4, 1, 0, 'tonglvdou', 2, 'thundernet8', 1, '36.102.83.200', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.110 Safari/537.36', '感觉好高级的样子！！！', '<p>感觉好高级的样子！！！</p>\n', '{\"entityMap\":{},\"blocks\":[{\"key\":\"5j1cm\",\"text\":\"感觉好高级的样子！！！\",\"type\":\"unstyled\",\"depth\":0,\"inlineStyleRanges\":[],\"entityRanges\":[],\"data\":{}}]}', 0, 0, 1, 1, 1508135992);
COMMIT;

-- ----------------------------
-- Table structure for t_tag
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
-- Table structure for t_tagrelation
-- ----------------------------
DROP TABLE IF EXISTS `t_tagrelation`;
CREATE TABLE `t_tagrelation` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `tag_id` int(10) unsigned NOT NULL,
  `topic_id` bigint(20) unsigned NOT NULL,
  `create_time` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_token
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
-- Table structure for t_topic
-- ----------------------------
DROP TABLE IF EXISTS `t_topic`;
CREATE TABLE `t_topic` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `cid` int(10) NOT NULL COMMENT '所属频道',
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '话题标题',
  `author_name` varchar(60) NOT NULL DEFAULT '' COMMENT '作者用户名',
  `author_id` bigint(20) unsigned NOT NULL COMMENT '作者ID',
  `content` longtext NOT NULL COMMENT '帖子正文(纯文本)',
  `content_html` longtext NOT NULL COMMENT '帖子内容(Html)',
  `content_raw` longtext NOT NULL COMMENT '帖子内容(DraftJS编辑器原始数据)',
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
  `poster` varchar(60) NOT NULL DEFAULT '' COMMENT '最后回复者用户名',
  `poster_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '最后回复者ID',
  `factor` int(10) unsigned NOT NULL DEFAULT '60' COMMENT '帖子权重因子',
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`author_id`),
  KEY `idx_cid` (`cid`),
  KEY `idx_post_time` (`post_time`),
  KEY `idx_create_update_time` (`create_time`,`update_time`) USING BTREE,
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='话题';

-- ----------------------------
-- Records of t_topic
-- ----------------------------
BEGIN;
INSERT INTO `t_topic` VALUES (1, 7, '关于Elune Forum', 'thundernet8', 1, 'Elune Forum是一个交流社区，在这里可以讨论Tint主题，WordPress使用开发，网站建设或者是闲聊非政治反动色情的不限分类话题。\n目前网站正在开发中：\n前台使用React + Element\n后台使用Java + 个人开发的MVC框架Razor\nJenkins + Github实现持续集成\n如果有任何问题，请选择相关分类发表您的话题', '<p>Elune Forum是一个交流社区，在这里可以讨论Tint主题，WordPress使用开发，网站建设或者是闲聊非政治反动色情的不限分类话题。</p>\n<p>目前网站正在开发中：</p>\n<ul>\n<li>前台使用React + Element</li>\n<li>后台使用Java + 个人开发的MVC框架Razor</li>\n<li>Jenkins + Github实现持续集成</li>\n</ul>\n<p>如果有任何问题，请选择相关分类发表您的话题</p>\n', '{\"entityMap\":{},\"blocks\":[{\"key\":\"3mlo8\",\"text\":\"Elune Forum是一个交流社区，在这里可以讨论Tint主题，WordPress使用开发，网站建设或者是闲聊非政治反动色情的不限分类话题。\",\"type\":\"unstyled\",\"depth\":0,\"inlineStyleRanges\":[],\"entityRanges\":[],\"data\":{}},{\"key\":\"8db1a\",\"text\":\"目前网站正在开发中：\",\"type\":\"unstyled\",\"depth\":0,\"inlineStyleRanges\":[],\"entityRanges\":[],\"data\":{}},{\"key\":\"454mu\",\"text\":\"前台使用React + Element\",\"type\":\"unordered-list-item\",\"depth\":0,\"inlineStyleRanges\":[],\"entityRanges\":[],\"data\":{}},{\"key\":\"kfp9\",\"text\":\"后台使用Java + 个人开发的MVC框架Razor\",\"type\":\"unordered-list-item\",\"depth\":0,\"inlineStyleRanges\":[],\"entityRanges\":[],\"data\":{}},{\"key\":\"2kijr\",\"text\":\"Jenkins + Github实现持续集成\",\"type\":\"unordered-list-item\",\"depth\":0,\"inlineStyleRanges\":[],\"entityRanges\":[],\"data\":{}},{\"key\":\"a1eub\",\"text\":\"如果有任何问题，请选择相关分类发表您的话题\",\"type\":\"unstyled\",\"depth\":0,\"inlineStyleRanges\":[],\"entityRanges\":[],\"data\":{}}]}', 0, 0, 0, 0, 0, 0, 4, 1, 1, 1507966726, 0, 1508135992, 'tonglvdou', 2, 60);
COMMIT;

-- ----------------------------
-- Table structure for t_user
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
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户状态 0-未激活 1-正常 9-封禁',
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES (1, '6468b605280ed4c63ec169e2d3a1f865', 'thundernet8', 'thundernet8', '813920477@qq.com', '', 1506404634, 0, 1, '', '', 0, 10, 0, 0, 0);
INSERT INTO `t_user` VALUES (2, '5af6f93785dc69d2604812a9e5c601c6', 'tonglvdou', 'tonglvdou', '475511371@qq.com', '', 1508135884, 0, 1, '', '', 0, 10, 0, 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for t_userlog
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
-- Table structure for t_usermeta
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
