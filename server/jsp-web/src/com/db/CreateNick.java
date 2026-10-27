package com.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateNick {
    private static final CreateNick instance = new CreateNick();

    public static CreateNick getInstance() {
        return instance;
    }
    private CreateNick() {  }

    // oracle 계정
    String jdbcUrl = System.getenv().getOrDefault("DB_URL", "jdbc:oracle:thin:@127.0.0.1:1521:XE");
    String userId = System.getenv("DB_USER");
    String userPw = System.getenv("DB_PASSWORD");

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    String sql = "";

    public void createNick(String id, String nick) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(jdbcUrl, userId, userPw);

            sql = "update USERINFO set NICK = ? where ID= ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nick);
            pstmt.setString(2, id);

            rs = pstmt.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
            if (conn != null)try {conn.close();} catch (SQLException ex) {}
        }

    }
}