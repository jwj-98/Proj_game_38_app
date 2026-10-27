package com.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetUserInfo {
    private static final GetUserInfo instance = new GetUserInfo();

    public static GetUserInfo getInstance() {
        return instance;
    }
    private GetUserInfo() {  }

    // oracle 계정
    String jdbcUrl = System.getenv().getOrDefault("DB_URL", "jdbc:oracle:thin:@127.0.0.1:1521:XE");
    String userId = System.getenv("DB_USER");
    String userPw = System.getenv("DB_PASSWORD");

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    String sql = "";
    String returns = null;

    public String getUserInfo(String id) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(jdbcUrl, userId, userPw);

            sql = "SELECT * FROM USERINFO WHERE ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
            	//계정찾기 성공
            		returns = rs.getString("ID") +";"+ rs.getString("NICK") +";"+ rs.getString("GOLD");
            } else {	
            	//로그인 실패 DB 조회 불가
                returns = "failed";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {         
            if (pstmt != null)try{pstmt.close();}catch(SQLException ex) {}
            if (conn != null)try{conn.close();}catch(SQLException ex) {}
        }
        return returns;
    }
}