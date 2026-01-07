package Dao;

import model.User;

// 用户操作接口：规范注册/登录方法
public interface UserDao {
        // 注册用户（普通/管理员）
        Boolean register(User user);
        // 用户登录（验证姓名+密码，返回用户信息）
        User login(String name, String password);
        Boolean checkUserInfo(User user);
        User findUserByName(String UserName);
}
//package Dao;
//
//import lombok.extern.slf4j.Slf4j;
//import model.User;
//import utils.JDBCUtils;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//@Slf4j
//public class UserDAO {
//        public boolean register(User user) {
//                String sql = "INSERT INTO tb_user(name,password,role) VALUES(?,?,?)";
//                Connection conn = JDBCUtils.getConnection();
//                PreparedStatement pstmt = null;
//                try {
//                        pstmt = conn.prepareStatement(sql);
//                        pstmt.setString(1, user.getUserName());
//                        pstmt.setString(1, user.getUserId());
//                        pstmt.setString(2, user.getPassword());
//                        pstmt.setString(3, user.getRole());
//                        return pstmt.executeUpdate() > 0;
//                } catch (SQLException e) {
//                        e.printStackTrace();
//                        return false;
//                } finally {
//                        JDBCUtils.close(null, pstmt, conn);
//                }
//        }
//
//        public  Boolean checkUserInfo(User user) {
//                Connection connection = JDBCUtils.getConnection();
//                String sql = "select * from tb_user where username = ? and password = ?";
//                PreparedStatement pstmt = JDBCUtils.getPreparedStatement(connection, sql);
//                ResultSet resultSet = null;
//                try{
//                        pstmt.setString(1,user.getUserName());
//                        pstmt.setString(1,user.getUserId());
//                        pstmt.setString(2,user.getPassword());
//                        resultSet = pstmt.executeQuery();
//                        if(resultSet.next()) {
//                                return true;
//                        }
//                }catch(SQLException e) {
//                        log.info("校验用户名和密码失败",e);
//                }finally {
//                        JDBCUtils.close(resultSet,pstmt,connection);
//                }
//                return false;
//        }
//}