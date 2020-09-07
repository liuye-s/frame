CREATE TABLE `client_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  `sex` int(11) DEFAULT NULL COMMENT '性别',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `birthday` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '生日',
  `education` int(11) DEFAULT NULL COMMENT '学历',
  `school_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '学校名称',
  `addr` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  `family_addr` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '家庭地址',
  `we_chat` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '微信号',
  `job` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '职业',
  `height` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '身高',
  `constellation` int(11) DEFAULT NULL COMMENT '星座',
  `income` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '年收入',
  `interest` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '兴趣爱好',
  `Introduce_flag` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '自我介绍标签',
  `Introduce_desc` varchar(2550) COLLATE utf8_bin DEFAULT NULL COMMENT '自我介绍描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `client_user_hope` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `age_hope` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '年龄要求；两位数据逗号分隔拼接',
  `height_hope` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '身高要求',
  `accept_dis_addr_love` int(11) DEFAULT NULL COMMENT '是否接受异地恋(1：接受；0不接受)',
  `introduce_flag_hope` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '期望标签(通过查询字典表，多个自我介绍逗号拼接)',
  `desc_hope` varchar(2550) COLLATE utf8_bin DEFAULT NULL COMMENT '期望描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(11) DEFAULT NULL COMMENT '状态(1:待匹配；2：完成匹配；3：失效)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `client_match_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `man_id` int(11) DEFAULT NULL COMMENT '男方id',
  `woman_id` int(11) DEFAULT NULL COMMENT '女方id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `sys_attach` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `key_id` int(11) DEFAULT NULL COMMENT '外键id',
  `type` int(11) DEFAULT NULL COMMENT '附件类型',
  `site` int(11) DEFAULT NULL COMMENT '附件区域',
  `addr` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '附件地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `sys_dic` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '编码',
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '值',
  `sort` int(11) DEFAULT NULL COMMENT '排序号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `sys_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `message_id` int(11) DEFAULT NULL COMMENT '通知消息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

