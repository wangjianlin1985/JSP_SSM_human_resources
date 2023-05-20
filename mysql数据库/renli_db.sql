/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : renli_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2019-03-04 00:54:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL default '',
  `password` varchar(32) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_contract`
-- ----------------------------
DROP TABLE IF EXISTS `t_contract`;
CREATE TABLE `t_contract` (
  `contractNo` varchar(30) NOT NULL COMMENT 'contractNo',
  `employeeObj` varchar(30) NOT NULL COMMENT '合同员工',
  `htlx` varchar(20) NOT NULL COMMENT '合同类型',
  `startTime` varchar(20) default NULL COMMENT '合同开始时间',
  `endTime` varchar(20) default NULL COMMENT '合同结束时间',
  `content` varchar(8000) NOT NULL COMMENT '合同内容',
  `htwj` varchar(60) NOT NULL COMMENT '合同文件',
  PRIMARY KEY  (`contractNo`),
  KEY `employeeObj` (`employeeObj`),
  CONSTRAINT `t_contract_ibfk_1` FOREIGN KEY (`employeeObj`) REFERENCES `t_employee` (`employeeNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_contract
-- ----------------------------
INSERT INTO `t_contract` VALUES ('SN2984415', 'EM001', '全职合同', '2019-03-03 01:59:38', '2019-03-20 01:59:39', '<p>合同内容测试合同内容测试合同内容测试合同内容测试合同内容测试合同内容测试</p>', 'upload/9fbc366f-4473-4926-b72c-85a7b5f7044b.doc');
INSERT INTO `t_contract` VALUES ('SN2984416', 'EM002', '全职合同', '2019-03-04 00:39:36', '2019-03-04 00:39:45', '<p>测试测试合同测试测试合同测试测试合同测试测试合同测试测试合同测试测试合同测试测试合同测试测试合同</p>', 'upload/160b1df2-28a2-4789-84e0-cc543ec5dd1a.doc');

-- ----------------------------
-- Table structure for `t_department`
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department` (
  `departmentNo` varchar(20) NOT NULL COMMENT 'departmentNo',
  `departmentName` varchar(20) NOT NULL COMMENT '部门名称',
  `zhizhe` varchar(800) NOT NULL COMMENT '部门职责',
  `bornDate` varchar(20) default NULL COMMENT '成立日期',
  PRIMARY KEY  (`departmentNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_department
-- ----------------------------
INSERT INTO `t_department` VALUES ('BM001', '人事部', '管理公司人才', '2019-03-05');
INSERT INTO `t_department` VALUES ('BM002', '市场部', '管理公司市场销售', '2019-03-01');

-- ----------------------------
-- Table structure for `t_employee`
-- ----------------------------
DROP TABLE IF EXISTS `t_employee`;
CREATE TABLE `t_employee` (
  `employeeNo` varchar(30) NOT NULL COMMENT 'employeeNo',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `departmentObj` varchar(20) NOT NULL COMMENT '所在部门',
  `positionName` varchar(20) NOT NULL COMMENT '职位名称',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `birthDate` varchar(20) default NULL COMMENT '出生日期',
  `empPhoto` varchar(60) NOT NULL COMMENT '员工照片',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `address` varchar(80) default NULL COMMENT '家庭地址',
  `jianli` varchar(8000) NOT NULL COMMENT '个人简历',
  `regTime` varchar(20) default NULL COMMENT '注册时间',
  PRIMARY KEY  (`employeeNo`),
  KEY `departmentObj` (`departmentObj`),
  CONSTRAINT `t_employee_ibfk_1` FOREIGN KEY (`departmentObj`) REFERENCES `t_department` (`departmentNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_employee
-- ----------------------------
INSERT INTO `t_employee` VALUES ('EM001', '123', 'BM001', '人事经理', '王曦彤', '女', '2019-03-05', 'upload/462b81bc-44b8-46fa-9739-4de6cb3e8026.jpg', '13590830842', '四川成都跳蹬河路', '<p>2018毕业于电子科技大学本科学历</p><p>2018-2019 XXX软件公司任职</p>', '2019-03-03 01:55:17');
INSERT INTO `t_employee` VALUES ('EM002', '123', 'BM002', '销售总监', '张晓丽', '女', '2019-03-01', 'upload/d94ca38a-6871-49b8-a702-d6c63dbf6f1f.jpg', '13980094252', '四川南充滨江路11号', '<p>2017年毕业于南开大学软件系</p><p>2017-2018.12任职于XXX软件公司</p>', '2019-03-04 00:28:45');

-- ----------------------------
-- Table structure for `t_jixiao`
-- ----------------------------
DROP TABLE IF EXISTS `t_jixiao`;
CREATE TABLE `t_jixiao` (
  `jixiaoId` int(11) NOT NULL auto_increment COMMENT '绩效id',
  `year` varchar(20) NOT NULL COMMENT '考核年份',
  `jidu` varchar(20) NOT NULL COMMENT '考核季度',
  `employeeObj` varchar(30) NOT NULL COMMENT '考核员工',
  `khjg` varchar(20) NOT NULL COMMENT '考核结果',
  `khbz` varchar(800) default NULL COMMENT '考核备注',
  PRIMARY KEY  (`jixiaoId`),
  KEY `employeeObj` (`employeeObj`),
  CONSTRAINT `t_jixiao_ibfk_1` FOREIGN KEY (`employeeObj`) REFERENCES `t_employee` (`employeeNo`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_jixiao
-- ----------------------------
INSERT INTO `t_jixiao` VALUES ('1', '2019', '第一季度', 'EM001', '优', 'test');
INSERT INTO `t_jixiao` VALUES ('2', '2019', '第二季度', 'EM001', '优', '');
INSERT INTO `t_jixiao` VALUES ('3', '2019', '第三季度', 'EM001', '优', 'test ');
INSERT INTO `t_jixiao` VALUES ('4', '2019', '第一季度', 'EM002', '优', '测试');
INSERT INTO `t_jixiao` VALUES ('5', '2019', '第二季度', 'EM002', '良', '--');
INSERT INTO `t_jixiao` VALUES ('6', '2019', '第三季度', 'EM002', '优', '');

-- ----------------------------
-- Table structure for `t_job`
-- ----------------------------
DROP TABLE IF EXISTS `t_job`;
CREATE TABLE `t_job` (
  `jobId` int(11) NOT NULL auto_increment COMMENT '岗位id',
  `departmentObj` varchar(20) NOT NULL COMMENT '招聘部门',
  `positionName` varchar(50) NOT NULL COMMENT '招聘岗位',
  `personNum` int(11) NOT NULL COMMENT '招聘人数',
  `needSpecial` varchar(500) NOT NULL COMMENT '需求专业',
  `xueli` varchar(20) NOT NULL COMMENT '学历',
  `zpdx` varchar(50) NOT NULL COMMENT '招聘对象',
  `jobMemo` varchar(800) default NULL COMMENT '招聘备注',
  `addTime` varchar(20) default NULL COMMENT '发布时间',
  PRIMARY KEY  (`jobId`),
  KEY `departmentObj` (`departmentObj`),
  CONSTRAINT `t_job_ibfk_1` FOREIGN KEY (`departmentObj`) REFERENCES `t_department` (`departmentNo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_job
-- ----------------------------
INSERT INTO `t_job` VALUES ('1', 'BM001', '人事部助理', '2', '企业管理专业', '本科', '应届毕业生', '无需工作经验，五险一金提供', '2019-03-03 01:56:01');
INSERT INTO `t_job` VALUES ('2', 'BM002', '销售人员', '10', '市场营销，电子商务专业等', '本科', '应届毕业生', '善于人际沟通', '2019-03-04 00:30:54');

-- ----------------------------
-- Table structure for `t_salary`
-- ----------------------------
DROP TABLE IF EXISTS `t_salary`;
CREATE TABLE `t_salary` (
  `salaryId` int(11) NOT NULL auto_increment COMMENT '薪酬id',
  `year` varchar(20) NOT NULL COMMENT '年份',
  `month` varchar(20) NOT NULL COMMENT '月份',
  `employeeObj` varchar(30) NOT NULL COMMENT '员工',
  `baseWage` float NOT NULL COMMENT '基本薪金',
  `zfbt` float NOT NULL COMMENT '住房补贴',
  `cybz` float NOT NULL COMMENT '餐饮补助',
  `jtbz` float NOT NULL COMMENT '交通补助',
  `qtbz` float NOT NULL COMMENT '其他补助',
  `qqjj` float NOT NULL COMMENT '全勤奖金',
  `qjcs` int(11) NOT NULL COMMENT '请假次数',
  `cdcs` int(11) NOT NULL COMMENT '迟到次数',
  `kgcs` int(11) NOT NULL COMMENT '旷工次数',
  `kqkc` float NOT NULL COMMENT '考勤扣除',
  `gskc` float NOT NULL COMMENT '个税扣除',
  `sjgz` float NOT NULL COMMENT '实际工资',
  `ffrq` varchar(20) NOT NULL COMMENT '发放日期',
  PRIMARY KEY  (`salaryId`),
  KEY `employeeObj` (`employeeObj`),
  CONSTRAINT `t_salary_ibfk_1` FOREIGN KEY (`employeeObj`) REFERENCES `t_employee` (`employeeNo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_salary
-- ----------------------------
INSERT INTO `t_salary` VALUES ('1', '2019', '2', 'EM001', '3000', '500', '200', '100', '50', '300', '2', '1', '0', '100', '300', '3750', '2019-03-01');
INSERT INTO `t_salary` VALUES ('2', '2019', '2', 'EM002', '4500', '300', '180', '120', '100', '280', '2', '1', '0', '100', '350', '5030', '--');

-- ----------------------------
-- Table structure for `t_train`
-- ----------------------------
DROP TABLE IF EXISTS `t_train`;
CREATE TABLE `t_train` (
  `trainId` int(11) NOT NULL auto_increment COMMENT '培训id',
  `trainName` varchar(50) NOT NULL COMMENT '培训名称',
  `trainPurpose` varchar(500) NOT NULL COMMENT '培训目的',
  `startTime` varchar(20) default NULL COMMENT '开始时间',
  `endTime` varchar(20) default NULL COMMENT '结束时间',
  `teacher` varchar(20) NOT NULL COMMENT '讲师',
  `personNum` int(11) NOT NULL COMMENT '培训人数',
  `trainContent` varchar(8000) NOT NULL COMMENT '培训内容',
  `trainMemo` varchar(500) default NULL COMMENT '培训备注',
  PRIMARY KEY  (`trainId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_train
-- ----------------------------
INSERT INTO `t_train` VALUES ('1', '2019新人培训', '熟悉公司基本业务', '2019-03-03 01:56:25', '2019-03-05 00:00:00', '王子轩', '30', '<p>讲解公司日常事务处理</p>', '测试');
INSERT INTO `t_train` VALUES ('2', '销售培训', '提高销售技巧，创造公司利益', '2019-03-03 00:33:09', '2019-03-13 00:33:13', '李晓天', '20', '<p>讲解公司客户的营销技巧</p>', '测试培训');

-- ----------------------------
-- Table structure for `t_ygfl`
-- ----------------------------
DROP TABLE IF EXISTS `t_ygfl`;
CREATE TABLE `t_ygfl` (
  `flId` int(11) NOT NULL auto_increment COMMENT '记录id',
  `year` varchar(20) NOT NULL COMMENT '福利年份',
  `employeeObj` varchar(30) NOT NULL COMMENT '福利员工',
  `fljj` float NOT NULL COMMENT '福利奖金',
  `sfff` varchar(20) NOT NULL COMMENT '是否发放',
  `ffrq` varchar(20) NOT NULL COMMENT '发放日期',
  PRIMARY KEY  (`flId`),
  KEY `employeeObj` (`employeeObj`),
  CONSTRAINT `t_ygfl_ibfk_1` FOREIGN KEY (`employeeObj`) REFERENCES `t_employee` (`employeeNo`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_ygfl
-- ----------------------------
INSERT INTO `t_ygfl` VALUES ('8', '2019', 'EM001', '5000', '是', '2019-12-04');
INSERT INTO `t_ygfl` VALUES ('9', '2019', 'EM002', '3000', '否', '--');

-- ----------------------------
-- Table structure for `t_ygjc`
-- ----------------------------
DROP TABLE IF EXISTS `t_ygjc`;
CREATE TABLE `t_ygjc` (
  `jcId` int(11) NOT NULL auto_increment COMMENT '奖惩id',
  `jclx` varchar(20) NOT NULL COMMENT '奖惩类型',
  `title` varchar(50) NOT NULL COMMENT '奖惩标题',
  `content` varchar(800) NOT NULL COMMENT '奖惩内容',
  `employeeObj` varchar(30) NOT NULL COMMENT '奖惩员工',
  `jcsj` varchar(20) default NULL COMMENT '奖惩时间',
  PRIMARY KEY  (`jcId`),
  KEY `employeeObj` (`employeeObj`),
  CONSTRAINT `t_ygjc_ibfk_1` FOREIGN KEY (`employeeObj`) REFERENCES `t_employee` (`employeeNo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_ygjc
-- ----------------------------
INSERT INTO `t_ygjc` VALUES ('1', '员工奖励', '拾金不昧精神', '捡到同事手机归还了，奖励100', 'EM001', '2019-03-03 01:59:10');
INSERT INTO `t_ygjc` VALUES ('2', '员工处罚', '乱扔垃圾破坏环境', '在公司乱扔水果皮，罚款50', 'EM002', '2019-03-04 14:38:56');
