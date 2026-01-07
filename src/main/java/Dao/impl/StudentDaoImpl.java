package Dao.impl;

import Dao.StudentDao;
import model.Student;
import utils.JDBCUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StudentDaoImpl implements StudentDao {
    private static final Logger logger = LoggerFactory.getLogger(StudentDaoImpl.class);

    @Override
    public boolean addStudent(Student student) {
     //   String stuId = "S" + System.currentTimeMillis() + String.format("%04d", (int)(Math.random() * 10000));
       // student.setStuId(stuId);

        String sql = "INSERT INTO student(stu_id, stu_name, gender, age, phone, class_name, email) VALUES(?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getStuId());
            pstmt.setString(2, student.getStuName());
            pstmt.setString(3, student.getGender());
            pstmt.setInt(4, student.getAge());
            pstmt.setString(5, student.getPhone());
            pstmt.setString(6, student.getClassName());
            pstmt.setString(7, student.getEmail());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtils.close(null, pstmt, conn);
        }
    }

    @Override
    public boolean deleteStudent(String stuId) {
        String sql = "DELETE FROM student WHERE stu_id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stuId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtils.close(null, pstmt, conn);
        }
    }

    @Override
    public List<Student> getStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setStuId(rs.getString("stu_id"));
                student.setStuName(rs.getString("stu_name"));
                student.setGender(rs.getString("gender"));
                student.setAge(rs.getInt("age"));
                student.setPhone(rs.getString("phone"));
                student.setClassName(rs.getString("class_name"));
                student.setEmail(rs.getString("email"));
                student.setCreateTime(rs.getTimestamp("create_time"));
                student.setUpdateTime(rs.getTimestamp("update_time"));
                list.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return list;
    }

    @Override
    public boolean updateStudentphone(String stuId, String newPhone) {
        String sql = "UPDATE student SET phone=? WHERE stu_id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newPhone);
            pstmt.setString(2, stuId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtils.close(null, pstmt, conn);
        }
    }

    @Override
    public boolean updateStudentPhoneAndEmail(String stuId, String phone, String email) {
        String sql = "UPDATE student SET phone=?, email=? WHERE stu_id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phone);
            pstmt.setString(2, email);
            pstmt.setString(3, stuId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtils.close(null, pstmt, conn);
        }
    }
    
    @Override
    public boolean updateStudent(Student student) {
        String sql = "UPDATE student SET stu_name=?, gender=?, age=?, phone=?, class_name=?, email=? WHERE stu_id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getStuName());
            pstmt.setString(2, student.getGender());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getPhone());
            pstmt.setString(5, student.getClassName());
            pstmt.setString(6, student.getEmail());
            pstmt.setString(7, student.getStuId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtils.close(null, pstmt, conn);
        }
    }
    
    public Student getStudentById(String stuId) {
        String sql = "SELECT * FROM student WHERE stu_id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stuId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Student student = new Student();
                student.setStuId(rs.getString("stu_id"));
                student.setStuName(rs.getString("stu_name"));
                student.setGender(rs.getString("gender"));
                student.setAge(rs.getInt("age"));
                student.setPhone(rs.getString("phone"));
                student.setClassName(rs.getString("class_name"));
                student.setEmail(rs.getString("email"));
                student.setCreateTime(rs.getTimestamp("create_time"));
                student.setUpdateTime(rs.getTimestamp("update_time"));
                return student;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return null;
    }

    @Override
    public List<Student> getStudentsByPage(int page, int pageSize) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student LIMIT ?, ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            int offset = (page - 1) * pageSize;
            pstmt.setInt(1, offset);
            pstmt.setInt(2, pageSize);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setStuId(rs.getString("stu_id"));
                student.setStuName(rs.getString("stu_name"));
                student.setGender(rs.getString("gender"));
                student.setAge(rs.getInt("age"));
                student.setPhone(rs.getString("phone"));
                student.setClassName(rs.getString("class_name"));
                student.setEmail(rs.getString("email"));
                student.setCreateTime(rs.getTimestamp("create_time"));
                student.setUpdateTime(rs.getTimestamp("update_time"));
                list.add(student);
            }
        } catch (SQLException e) {
            logger.error("分页查询学生失败", e);
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return list;
    }

    @Override
    public List<Student> searchStudents(String keyword, int page, int pageSize) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student WHERE stu_id LIKE ? OR stu_name LIKE ? LIMIT ?, ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            String searchKeyword = "%" + keyword + "%";
            pstmt.setString(1, searchKeyword);
            pstmt.setString(2, searchKeyword);
            int offset = (page - 1) * pageSize;
            pstmt.setInt(3, offset);
            pstmt.setInt(4, pageSize);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setStuId(rs.getString("stu_id"));
                student.setStuName(rs.getString("stu_name"));
                student.setGender(rs.getString("gender"));
                student.setAge(rs.getInt("age"));
                student.setPhone(rs.getString("phone"));
                student.setClassName(rs.getString("class_name"));
                student.setEmail(rs.getString("email"));
                student.setCreateTime(rs.getTimestamp("create_time"));
                student.setUpdateTime(rs.getTimestamp("update_time"));
                list.add(student);
            }
        } catch (SQLException e) {
            logger.error("搜索学生失败，keyword: " + keyword, e);
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return list;
    }

    @Override
    public int getTotalStudentCount() {
        String sql = "SELECT COUNT(*) FROM student";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("获取学生总数失败", e);
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return 0;
    }

    @Override
    public int getSearchStudentCount(String keyword) {
        String sql = "SELECT COUNT(*) FROM student WHERE stu_id LIKE ? OR stu_name LIKE ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            String searchKeyword = "%" + keyword + "%";
            pstmt.setString(1, searchKeyword);
            pstmt.setString(2, searchKeyword);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("获取搜索学生数量失败，keyword: " + keyword, e);
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return 0;
    }
}
