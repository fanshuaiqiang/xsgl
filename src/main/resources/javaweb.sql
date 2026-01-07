-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2026-01-07 21:13:35
-- 服务器版本： 5.7.44-log
-- PHP 版本： 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `javaweb`
--
CREATE DATABASE IF NOT EXISTS `javaweb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `javaweb`;

-- --------------------------------------------------------

--
-- 表的结构 `score`
--

DROP TABLE IF EXISTS `score`;
CREATE TABLE IF NOT EXISTS `score` (
  `score_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '成绩ID',
  `stu_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学号',
  `course_id` varchar(250) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程ID',
  `course_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '课程名称',
  `score` int(11) DEFAULT NULL COMMENT '成绩',
  `semester` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学期：第一学期/第二学期',
  `academic_year` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '学年：2024-2025',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`score_id`),
  KEY `idx_stu_id` (`stu_id`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_academic_year` (`academic_year`),
  KEY `idx_semester` (`semester`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='成绩表';

--
-- 插入之前先把表清空（truncate） `score`
--

TRUNCATE TABLE `score`;
--
-- 转存表中的数据 `score`
--

INSERT DELAYED IGNORE INTO `score` (`score_id`, `stu_id`, `course_id`, `course_name`, `score`, `semester`, `academic_year`, `create_time`, `update_time`) VALUES
('3eb9fe215d184f3a533654fe2d67490e', 'fb6329d7ff1744cd951d2d02408da46a', 'C001', 'Java程序设计', 100, '第二学期', '2022-2023', '2026-01-07 09:59:48', '2026-01-07 12:23:12'),
('3eb9fe215d184fdd8a5654fe2d67490e', '2024002', 'C001', 'Java程序设计', 100, '第二学期', '2022-2023', '2026-01-07 09:59:48', '2026-01-07 12:23:12'),
('4771015d52b3476e945bca3b8167305f', 'S17677901390630343', 'C002', 'Java程序设计1', 45, '第一学期', '2024-2025', '2026-01-07 13:05:23', '2026-01-07 13:05:23'),
('99b043ef5d464c159e10653b3e18e547', 'S17677901390630343', 'C001', 'Java程序设计', 44, '第一学期', '2023-2024', '2026-01-07 12:51:48', '2026-01-07 12:51:48'),
('S001', '2024001', 'C001', 'Java程序设计', 85, '第一学期', '2024-2025', '2026-01-07 08:33:38', '2026-01-07 08:33:38'),
('S002', '2024001', 'C002', '数据库原理', 90, '第一学期', '2024-2025', '2026-01-07 08:33:38', '2026-01-07 08:33:38');

-- --------------------------------------------------------

--
-- 表的结构 `student`
--

DROP TABLE IF EXISTS `student`;
CREATE TABLE IF NOT EXISTS `student` (
  `stu_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '学号',
  `stu_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
  `gender` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别：男/女',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `class_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '班级',
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`stu_id`),
  KEY `idx_class_name` (`class_name`),
  KEY `idx_stu_name` (`stu_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学生表';

--
-- 插入之前先把表清空（truncate） `student`
--

TRUNCATE TABLE `student`;
--
-- 转存表中的数据 `student`
--

INSERT DELAYED IGNORE INTO `student` (`stu_id`, `stu_name`, `gender`, `age`, `phone`, `class_name`, `email`, `create_time`, `update_time`) VALUES
('2024001', '张三', '男', 20, '13800138043', '计算机1班', 'student1@school.com', '2026-01-07 08:33:38', '2026-01-07 11:15:56'),
('2024002', '李四', '女', 20, '13800138002', '计算机3班', 'student1@school.com', '2026-01-07 08:33:38', '2026-01-07 12:38:10'),
('fb6329d7ff1744cd951d2d02408da46a', 'test', '女', 11, '13070164849', '111', 'zs399@nau.edu', '2026-01-07 11:41:14', '2026-01-07 12:25:55'),
('S17677901390630343', '赵六', '', 0, '', '', '', '2026-01-07 12:48:59', '2026-01-07 12:48:59');

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户ID',
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `role` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'student' COMMENT '角色：admin-管理员，student-学生',
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  KEY `idx_username` (`username`),
  KEY `idx_role` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

--
-- 插入之前先把表清空（truncate） `user`
--

TRUNCATE TABLE `user`;
--
-- 转存表中的数据 `user`
--

INSERT DELAYED IGNORE INTO `user` (`user_id`, `username`, `password`, `role`, `email`, `create_time`, `update_time`) VALUES
('2024001', '2024001', '123456', 'student', 'student1@school.com', '2026-01-07 08:33:38', '2026-01-07 08:33:38'),
('admin001', 'admin', '1', 'admin', 'admin@school.com', '2026-01-07 08:33:38', '2026-01-07 11:37:47'),
('ea3b96a1402d4648b8b1bb7908d56572', 'S17677901390630343', '1', 'student', 'qqslt515@gmail.com', '2026-01-07 12:49:22', '2026-01-07 12:49:22'),
('fb6329d7ff1744cd951d2d02408da46a', 'test', 'test', 'student', '878169060@qq.com', '2026-01-07 09:13:31', '2026-01-07 09:13:31');

--
-- 限制导出的表
--

--
-- 限制表 `score`
--
ALTER TABLE `score`
  ADD CONSTRAINT `score_ibfk_1` FOREIGN KEY (`stu_id`) REFERENCES `student` (`stu_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
