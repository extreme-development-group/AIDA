package database;

import config.ServerInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DataBaseConnection {
    // 驱动名及数据库url
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://"+ ServerInfo.MYSQL_IP +":" +
        ServerInfo.MYSQL_PORT + "/" + ServerInfo.DB_NAME +"?useSSL=false&serverTimezone=UTC";
    // 数据库用户名密码
    static final String USER = ServerInfo.DB_USER_NAME;
    static final String PASS = ServerInfo.DB_USER_PASSWORD;
    // 连接对象
    private Connection conn = null;
    private PreparedStatement prestmt = null;

    DataBaseConnection() {
        try {
            // 注册JDBC驱动
            Class.forName(JDBC_DRIVER);
            // 连接数据库
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("DataBase Connected Successfully!");
    }

    // 查询
    public ResultSet getFromDataBase(String sql) {
        ResultSet rs = null;
        try {
            prestmt = conn.prepareStatement(sql);
            // 开始查询
            rs = prestmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return rs;
    }

    // 其他增、删、改操作
    public boolean updateDataBase(String sql) {
        try {
            prestmt = conn.prepareStatement(sql);
            prestmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void close() {
        try {
            if (prestmt != null && !prestmt.isClosed()) {
                prestmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn!=null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("DBConnection closed successfully!");
    }

    public static void main(String[] args) {
        DataBaseConnection demo = new DataBaseConnection();
    }
}
