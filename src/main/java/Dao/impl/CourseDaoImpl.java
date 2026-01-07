package Dao.impl;

import Dao.CourseDao;
import model.Course;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// 课程/成绩接口的具体实现（管理员增删改查，普通用户仅查）
public class CourseDaoImpl implements CourseDao {

        @Override
        public boolean addCourse(Course course) {
                // 管理员专属：新增成绩/课程
                String sql = "INSERT INTO tb_course(course_id,course_name,score,stu_id) VALUES(?,?,?,?)";
                Connection conn = JDBCUtils.getConnection();
                PreparedStatement pstmt = null;
                try {
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, course.getCourseId());
                        pstmt.setString(2, course.getCourseName());
                        pstmt.setInt(3, course.getScore());
                        pstmt.setString(4, course.getStuId());
                        return pstmt.executeUpdate() > 0;
                } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                } finally {
                        JDBCUtils.close(null, pstmt, conn);
                }
        }

        @Override
        public boolean deleteCourse(String courseId) {
                // 管理员专属：删除成绩/课程
                String sql = "DELETE FROM tb_course WHERE course_id=?";
                Connection conn = JDBCUtils.getConnection();
                PreparedStatement pstmt = null;
                try {
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, courseId);
                        return pstmt.executeUpdate() > 0;
                } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                } finally {
                        JDBCUtils.close(null, pstmt, conn);
                }
        }

        @Override
        public boolean updateCourseScore(String courseId, Integer newScore) {
                // 管理员专属：修改成绩
                String sql = "UPDATE tb_course SET score=? WHERE course_id=?";
                Connection conn = JDBCUtils.getConnection();
                PreparedStatement pstmt = null;
                try {
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1, newScore);
                        pstmt.setString(2, courseId);
                        return pstmt.executeUpdate() > 0;
                } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                } finally {
                        JDBCUtils.close(null, pstmt, conn);
                }
        }

        @Override
        public List<Course> getAllCourses() {
                // 所有人：查询所有成绩/课程
                List<Course> list = new ArrayList<>();
                String sql = "SELECT * FROM tb_course";
                Connection conn = JDBCUtils.getConnection();
                PreparedStatement pstmt = null;
                ResultSet rs = null;
                try {
                        pstmt = conn.prepareStatement(sql);
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                                Course c = new Course();
                                c.setCourseId(rs.getString("course_id"));
                                c.setCourseName(rs.getString("courseName"));
                                c.setScore(rs.getInt("score"));
                                c.setStuId(rs.getString("StuId"));
                                list.add(c);
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {
                        JDBCUtils.close(rs, pstmt, conn);
                }
                return list;
        }

        @Override
        public Course getCourseById(String courseId) {
                // 辅助：按课程号查成绩
                String sql = "SELECT * FROM tb_course WHERE courseId=?";
                Connection conn = JDBCUtils.getConnection();
                PreparedStatement pstmt = null;
                ResultSet rs = null;
                try {
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, courseId);
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                                Course c = new Course();
                                c.setCourseId(rs.getString("courseId"));
                                c.setCourseName(rs.getString("courseName"));
                                c.setScore(rs.getInt("score"));
                                c.setStuId(rs.getString("StuId"));
                                return c;
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {
                        JDBCUtils.close(rs, pstmt, conn);
                }
                return null;
        }
}
