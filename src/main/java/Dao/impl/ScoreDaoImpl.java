package Dao.impl;

import Dao.ScoreDao;
import model.Score;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScoreDaoImpl implements ScoreDao {

    @Override
    public boolean addScore(Score score) {
        String sql = "INSERT INTO score(score_id, stu_id, course_id, course_name, score, semester, academic_year) VALUES(?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, score.getScoreId());
            pstmt.setString(2, score.getStuId());
            pstmt.setString(3, score.getCourseId());
            pstmt.setString(4, score.getCourseName());
            pstmt.setInt(5, score.getScore());
            pstmt.setString(6, score.getSemester());
            pstmt.setString(7, score.getAcademicYear());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtils.close(null, pstmt, conn);
        }
    }

    @Override
    public boolean deleteScore(String scoreId) {
        String sql = "DELETE FROM score WHERE score_id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, scoreId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtils.close(null, pstmt, conn);
        }
    }

    @Override
    public boolean updateScore(String scoreId, Integer newScore) {
        String sql = "UPDATE score SET score=? WHERE score_id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newScore);
            pstmt.setString(2, scoreId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtils.close(null, pstmt, conn);
        }
    }

    @Override
    public List<Score> getAllScores() {
        List<Score> list = new ArrayList<>();
        String sql = "SELECT s.*, st.stu_name FROM score s LEFT JOIN student st ON s.stu_id = st.stu_id";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Score score = new Score();
                score.setScoreId(rs.getString("score_id"));
                score.setStuId(rs.getString("stu_id"));
                score.setStuName(rs.getString("stu_name"));
                score.setCourseId(rs.getString("course_id"));
                score.setCourseName(rs.getString("course_name"));
                score.setScore(rs.getInt("score"));
                score.setSemester(rs.getString("semester"));
                score.setAcademicYear(rs.getString("academic_year"));
                score.setCreateTime(rs.getTimestamp("create_time"));
                score.setUpdateTime(rs.getTimestamp("update_time"));
                list.add(score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return list;
    }

    @Override
    public Score getScoreById(String scoreId) {
        String sql = "SELECT * FROM score WHERE score_id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, scoreId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Score score = new Score();
                score.setScoreId(rs.getString("score_id"));
                score.setStuId(rs.getString("stu_id"));
                score.setCourseId(rs.getString("course_id"));
                score.setCourseName(rs.getString("course_name"));
                score.setScore(rs.getInt("score"));
                score.setSemester(rs.getString("semester"));
                score.setAcademicYear(rs.getString("academic_year"));
                score.setCreateTime(rs.getTimestamp("create_time"));
                score.setUpdateTime(rs.getTimestamp("update_time"));
                return score;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return null;
    }

    @Override
    public List<Score> getScoresByStudentId(String stuId) {
        List<Score> list = new ArrayList<>();
        String sql = "SELECT s.*, st.stu_name FROM score s LEFT JOIN student st ON s.stu_id = st.stu_id WHERE s.stu_id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stuId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Score score = new Score();
                score.setScoreId(rs.getString("score_id"));
                score.setStuId(rs.getString("stu_id"));
                score.setStuName(rs.getString("stu_name"));
                score.setCourseId(rs.getString("course_id"));
                score.setCourseName(rs.getString("course_name"));
                score.setScore(rs.getInt("score"));
                score.setSemester(rs.getString("semester"));
                score.setAcademicYear(rs.getString("academic_year"));
                score.setCreateTime(rs.getTimestamp("create_time"));
                score.setUpdateTime(rs.getTimestamp("update_time"));
                list.add(score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return list;
    }

    @Override
    public List<Score> getAllScoresByPage(int page, int pageSize) {
        List<Score> list = new ArrayList<>();
        String sql = "SELECT s.*, st.stu_name FROM score s LEFT JOIN student st ON s.stu_id = st.stu_id LIMIT ?, ?";
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
                Score score = new Score();
                score.setScoreId(rs.getString("score_id"));
                score.setStuId(rs.getString("stu_id"));
                score.setStuName(rs.getString("stu_name"));
                score.setCourseId(rs.getString("course_id"));
                score.setCourseName(rs.getString("course_name"));
                score.setScore(rs.getInt("score"));
                score.setSemester(rs.getString("semester"));
                score.setAcademicYear(rs.getString("academic_year"));
                score.setCreateTime(rs.getTimestamp("create_time"));
                score.setUpdateTime(rs.getTimestamp("update_time"));
                list.add(score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return list;
    }

    @Override
    public int getTotalScoreCount() {
        String sql = "SELECT COUNT(*) FROM score";
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
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return 0;
    }

    @Override
    public List<Score> getScoresByStudentIdByPage(String stuId, int page, int pageSize) {
        List<Score> list = new ArrayList<>();
        String sql = "SELECT s.*, st.stu_name FROM score s LEFT JOIN student st ON s.stu_id = st.stu_id WHERE s.stu_id=? LIMIT ?, ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stuId);
            int offset = (page - 1) * pageSize;
            pstmt.setInt(2, offset);
            pstmt.setInt(3, pageSize);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Score score = new Score();
                score.setScoreId(rs.getString("score_id"));
                score.setStuId(rs.getString("stu_id"));
                score.setStuName(rs.getString("stu_name"));
                score.setCourseId(rs.getString("course_id"));
                score.setCourseName(rs.getString("course_name"));
                score.setScore(rs.getInt("score"));
                score.setSemester(rs.getString("semester"));
                score.setAcademicYear(rs.getString("academic_year"));
                score.setCreateTime(rs.getTimestamp("create_time"));
                score.setUpdateTime(rs.getTimestamp("update_time"));
                list.add(score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return list;
    }

    @Override
    public int getScoreCountByStudentId(String stuId) {
        String sql = "SELECT COUNT(*) FROM score WHERE stu_id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stuId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return 0;
    }
    
    @Override
    public List<Score> searchScores(String keyword, String semester, String academicYear, int page, int pageSize) {
        List<Score> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT s.*, st.stu_name FROM score s LEFT JOIN student st ON s.stu_id = st.stu_id WHERE 1=1");
        List<Object> params = new ArrayList<>();
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (s.stu_id LIKE ? OR st.stu_name LIKE ? OR s.course_name LIKE ?)");
            String searchKeyword = "%" + keyword.trim() + "%";
            params.add(searchKeyword);
            params.add(searchKeyword);
            params.add(searchKeyword);
        }
        
        if (semester != null && !semester.isEmpty()) {
            sql.append(" AND s.semester = ?");
            params.add(semester);
        }
        
        if (academicYear != null && !academicYear.isEmpty()) {
            sql.append(" AND s.academic_year = ?");
            params.add(academicYear);
        }
        
        sql.append(" LIMIT ?, ?");
        params.add((page - 1) * pageSize);
        params.add(pageSize);
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Score score = new Score();
                score.setScoreId(rs.getString("score_id"));
                score.setStuId(rs.getString("stu_id"));
                score.setStuName(rs.getString("stu_name"));
                score.setCourseId(rs.getString("course_id"));
                score.setCourseName(rs.getString("course_name"));
                score.setScore(rs.getInt("score"));
                score.setSemester(rs.getString("semester"));
                score.setAcademicYear(rs.getString("academic_year"));
                score.setCreateTime(rs.getTimestamp("create_time"));
                score.setUpdateTime(rs.getTimestamp("update_time"));
                list.add(score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return list;
    }
    
    @Override
    public int getSearchScoreCount(String keyword, String semester, String academicYear) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM score s LEFT JOIN student st ON s.stu_id = st.stu_id WHERE 1=1");
        List<Object> params = new ArrayList<>();
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (s.stu_id LIKE ? OR st.stu_name LIKE ? OR s.course_name LIKE ?)");
            String searchKeyword = "%" + keyword.trim() + "%";
            params.add(searchKeyword);
            params.add(searchKeyword);
            params.add(searchKeyword);
        }
        
        if (semester != null && !semester.isEmpty()) {
            sql.append(" AND s.semester = ?");
            params.add(semester);
        }
        
        if (academicYear != null && !academicYear.isEmpty()) {
            sql.append(" AND s.academic_year = ?");
            params.add(academicYear);
        }
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            pstmt = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs, pstmt, conn);
        }
        return 0;
    }
}
