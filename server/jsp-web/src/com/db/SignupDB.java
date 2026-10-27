package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SignupDB {
    private static final SignupDB instance = new SignupDB();

    public static SignupDB getInstance() {
        return instance;
    }
    private SignupDB() {  }

    // oracle 계정
    String jdbcUrl = System.getenv().getOrDefault("DB_URL", "jdbc:oracle:thin:@127.0.0.1:1521:XE");
    String jdbcId = System.getenv("DB_USER");
    String jdbcPw = System.getenv("DB_PASSWORD");

    Connection conn = null;
    PreparedStatement pstmt = null;
    PreparedStatement pstmt2 = null;
    ResultSet rs = null;

    String sql = "";
    String sql2 = "";
    String returns = "실패";

    public String signupConnDB(String name, String id, String pw, String email, String gender) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(jdbcUrl, jdbcId, jdbcPw);

            Calendar cal = new GregorianCalendar();
            Timestamp ts = new Timestamp(cal.getTimeInMillis());
            
            sql = "SELECT ID FROM USERINFO WHERE ID = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                returns = "이미 존재하는 아이디 입니다.";
            } else {
                sql2 = "INSERT INTO USERINFO VALUES(?,?,?,?,?,?,?,?)";
                pstmt2 = conn.prepareStatement(sql2);
                pstmt2.setString(1, name);
                pstmt2.setString(2, id);
                pstmt2.setString(3, pw);
                pstmt2.setString(4, email);
                pstmt2.setString(5, gender);
                pstmt2.setString(6, "nick");
                pstmt2.setDouble(7, 100000000);
                pstmt2.setTimestamp(8, ts);
                pstmt2.executeUpdate();
                returns = "회원 가입이 완료되었습니다 !";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt2 != null)try{pstmt2.close();}catch(SQLException ex){}
            if (pstmt != null)try {pstmt.close();}catch(SQLException ex){}
            if (conn != null)try {conn.close();}catch(SQLException ex){}
        }
        return returns;
    }
}