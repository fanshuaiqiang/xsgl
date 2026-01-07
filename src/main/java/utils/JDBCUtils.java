package utils;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {
    private static Properties props;
    private static DruidDataSource dataSource;

    static {
        try {
            props = new Properties();
            InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("database.properties");
            if (is == null) {
                throw new RuntimeException("找不到database.properties配置文件");
            }
            props.load(is);
            is.close();
            
            dataSource = new DruidDataSource();
            dataSource.setDriverClassName(props.getProperty("db.driver"));
            dataSource.setUrl(props.getProperty("db.url"));
            dataSource.setUsername(props.getProperty("db.username"));
            dataSource.setPassword(props.getProperty("db.password"));
            dataSource.setInitialSize(Integer.parseInt(props.getProperty("db.initialSize")));
            dataSource.setMinIdle(Integer.parseInt(props.getProperty("db.minIdle")));
            dataSource.setMaxActive(Integer.parseInt(props.getProperty("db.maxActive")));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("初始化数据库连接池失败", e);
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取数据库连接失败", e);
        }
    }

        /**
         * 获取数据库preparedStatement 执行对象
         * @param connection 数据库连接对象
         * @param sql 预编译sql语句
         * @return
         */
        public static PreparedStatement getPreparedStatement(Connection connection, String sql) {
                PreparedStatement pStatement = null;
                try{
                        if (connection != null) {
                                pStatement = connection.prepareStatement(sql);
                        }
                }catch (SQLException e) {
                        e.printStackTrace();
                }
                return pStatement;
        }

        /**
         * 关闭数据库连接
         * @param resultSet
         * @param pStatement
         * @param connection
         */
        public static void close(ResultSet resultSet, PreparedStatement pStatement, Connection connection) {
                try{
                        if(resultSet != null){
                                resultSet.close();
                        }
                        if(pStatement  != null){
                                pStatement.close();
                        }
                        if(connection != null){
                                connection.close();
                        }
                }catch(SQLException e){
                        e.printStackTrace();
                }
        }

}