
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 繳費記錄
DROP TABLE IF EXISTS `fee_record`;
CREATE TABLE `fee_record`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `state` int(10) NULL DEFAULT 1 COMMENT '状态',
  `account` varchar(50) NOT NULL COMMENT '账号',
  `fee_number` varchar(50) NULL DEFAULT NULL COMMENT '交費代碼',
  `fee_total` float NOT NULL DEFAULT '0' COMMENT '實繳費用',
  `fee_detail` varchar(50) NULL DEFAULT NULL COMMENT '實繳说明',
  `fee_time` datetime(0) NULL DEFAULT NULL COMMENT '繳費时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- 班级费用
DROP TABLE IF EXISTS `fee_info`;
CREATE TABLE `fee_info`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `state` int(10) NULL DEFAULT 1 COMMENT '状态',
  `fee_number` varchar(50) NULL DEFAULT NULL COMMENT '交費代碼',
  `class_name` varchar(50) NULL DEFAULT NULL COMMENT '班级名稱',  
  `class_fee` float NOT NULL DEFAULT '0' COMMENT '班级應交費用',
  `class_fee_record` float NOT NULL DEFAULT '0' COMMENT '班级實交費用',
  `class_fee_detail` varchar(50) NULL DEFAULT NULL COMMENT '班级交費用说明',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '生成时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
INSERT INTO `fee_info` (id,fee_number,class_name,class_fee,class_fee_detail) VALUES (1, 'FEE2018_01', '小11班', '188', '2018秋季課本費用');
INSERT INTO `fee_info` (id,fee_number,class_name,class_fee,class_fee_detail) VALUES (2, 'FEE2018_02', '小11班', '88', '2018秋遊費用');
INSERT INTO `fee_info` (id,fee_number,class_name,class_fee,class_fee_detail) VALUES (3, 'FEE2018_01', '小22班', '288', '2018秋季課本費用');

-- 班级信息
DROP TABLE IF EXISTS `class_info`;
CREATE TABLE `class_info`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `state` int(10) NULL DEFAULT 1 COMMENT '状态', 
  `class_number` varchar(50) NULL DEFAULT NULL COMMENT '班级编号',
  `class_name` varchar(50) NULL DEFAULT NULL COMMENT '班级名称',
  `class_teacher` varchar(50) NULL DEFAULT NULL COMMENT '班级老师',
  `class_school` varchar(50) NULL DEFAULT NULL COMMENT '班级所在学校',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '生成时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
INSERT INTO `class_info` (id,class_number,class_name,class_teacher,class_school) VALUES (1, 'BJ1001', '小11班', '张老师', '北京中心小学');
INSERT INTO `class_info` (id,class_number,class_name,class_teacher,class_school) VALUES (2, 'BJ2001', '小22班', '张老师', '北京中心小学');

-- 学校科目
DROP TABLE IF EXISTS `subject_info`;
CREATE TABLE `subject_info`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `state` int(10) NULL DEFAULT 1 COMMENT '状态', 
  `subject_number` varchar(50) NULL DEFAULT NULL COMMENT '科目编号',
  `subject_name` varchar(50) NULL DEFAULT NULL COMMENT '科目名称',
  `subject_class` varchar(50) NULL DEFAULT NULL COMMENT '科目班级',
  `subject_teacher` varchar(50) NULL DEFAULT NULL COMMENT '科目老师',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '生成时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
INSERT INTO `subject_info` (id,subject_number,subject_name,subject_class,subject_teacher) VALUES (1, 'YW1001', '语文', '小11班', '张老师');
INSERT INTO `subject_info` (id,subject_number,subject_name,subject_class,subject_teacher) VALUES (2, 'SX1001', '数学', '小11班', '张老师');
INSERT INTO `subject_info` (id,subject_number,subject_name,subject_class,subject_teacher) VALUES (3, 'YY1001', '英语', '小11班', '张老师');
INSERT INTO `subject_info` (id,subject_number,subject_name,subject_class,subject_teacher) VALUES (4, 'TY1001', '体育', '小11班', '张老师');
INSERT INTO `subject_info` (id,subject_number,subject_name,subject_class,subject_teacher) VALUES (5, 'ZZ1001', '政治', '小11班', '张老师');

-- 学生成绩报告
DROP TABLE IF EXISTS `student_report`;
CREATE TABLE `student_report`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `state` int(10) NULL DEFAULT 1 COMMENT '状态',
  `student_name` varchar(50) NULL DEFAULT NULL COMMENT '学生姓名',  
  `student_number` varchar(50) NULL DEFAULT NULL COMMENT '学生学号',
  `student_class` varchar(50) NULL DEFAULT NULL COMMENT '学生班级',
  `subject_name` varchar(50) NULL DEFAULT NULL COMMENT '科目名称',
  `subject_number` varchar(50) NULL DEFAULT NULL COMMENT '科目编号',
  `examination_score` int(10) NULL DEFAULT 0 COMMENT '考试成绩',
  `examination_time` datetime(0) NULL DEFAULT NULL COMMENT '考试时间',
  `examination_rank` int(10) NULL DEFAULT 0 COMMENT '考试成绩排名',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '生成时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
INSERT INTO `student_report` (id,student_name,student_number,student_class,subject_number,subject_name,examination_score,examination_time) VALUES (1,'马玉1','XH1001','小11班','YW1001','语文','85','2018-10-06 12:30:28');
INSERT INTO `student_report` (id,student_name,student_number,student_class,subject_number,subject_name,examination_score,examination_time) VALUES (2,'马玉1','XH1001','小11班','SX1001','数学','89','2018-10-06 12:30:28');
INSERT INTO `student_report` (id,student_name,student_number,student_class,subject_number,subject_name,examination_score,examination_time) VALUES (3,'赵体育1','XH1002','小11班','YW1001','语文','89','2018-10-06 12:30:28');
INSERT INTO `student_report` (id,student_name,student_number,student_class,subject_number,subject_name,examination_score,examination_time) VALUES (4,'钱认同1','XH1003','小11班','YW1001','语文','79','2018-10-06 12:30:28');

-- 学生信息
DROP TABLE IF EXISTS `student_info`;
CREATE TABLE `student_info`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `state` int(10) NULL DEFAULT 1 COMMENT '状态',
  `account` varchar(50) NOT NULL COMMENT '账号',
  `student_name` varchar(50) NULL DEFAULT NULL COMMENT '姓名',
  `student_number` varchar(50) NULL DEFAULT NULL COMMENT '学生学号',
  `student_parent` varchar(50) NULL DEFAULT NULL COMMENT '学生父母姓名',    
  `student_class` varchar(50) NULL DEFAULT NULL COMMENT '学生班级',
  `student_department` varchar(50) NULL DEFAULT NULL COMMENT '学生院系',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '生成时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
INSERT INTO `student_info` (id,account,student_name,student_number,student_class) VALUES (1,'test','马玉1','XH1001','小11班');
INSERT INTO `student_info` (id,account,student_name,student_number,student_class) VALUES (2,'test2','赵体育1','XH1002','小11班');
INSERT INTO `student_info` (id,account,student_name,student_number,student_class) VALUES (3,'test3','钱认同1','XH1003','小11班');

-- 注册用戶
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `state` int(10) NULL DEFAULT 1 COMMENT '状态：0=管理员，1=用户或学生',
  `account` varchar(50) NOT NULL COMMENT '账号',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `name` varchar(50) NULL DEFAULT NULL COMMENT '姓名',
  `nick` varchar(50) NULL DEFAULT NULL COMMENT '昵称',
  `sex` int(10) NULL DEFAULT 0 COMMENT '状态：0=女，1=男',
  `age` int(10) NULL DEFAULT 0 COMMENT '年龄',
  `birth_date` varchar(50) NULL DEFAULT NULL COMMENT '出生日期',
  `phone` varchar(50) NULL DEFAULT NULL COMMENT '电话',
  `qq` varchar(50) NULL DEFAULT NULL COMMENT 'QQ',
  `wx` varchar(50) NULL DEFAULT NULL COMMENT 'WX',  
  `email` varchar(50) NULL DEFAULT NULL COMMENT '电邮',
  `address` varchar(250) NULL DEFAULT NULL COMMENT '地址',
  `work_unit` varchar(50) NULL DEFAULT NULL COMMENT '工作单位',
  `nation` varchar(50) NULL DEFAULT NULL COMMENT '民族',
  `cid` varchar(50) NULL DEFAULT NULL COMMENT '证件号码',
  `other` varchar(250) NULL DEFAULT NULL COMMENT '其它',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '登录时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '生成时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
INSERT INTO `user` (id,state,account,password,name) VALUES (1, 0, 'admin', '123', '管理员');
INSERT INTO `user` (id,state,account,password,name) VALUES (2, 0, 'demo', '123', '马化腾');

INSERT INTO `user` (id,state,account,password,name) VALUES (3, 1, 'test', '123', '马玉1');
INSERT INTO `user` (id,state,account,password,name) VALUES (4, 1, 'test2', '123', '赵体育1');
INSERT INTO `user` (id,state,account,password,name) VALUES (5, 1, 'test3', '123', '钱认同1');

SET FOREIGN_KEY_CHECKS = 1;

