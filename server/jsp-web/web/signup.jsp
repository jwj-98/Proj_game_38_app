<%@ page import="com.db.SignupDB" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("utf-8");%>
<%
   SignupDB signupDB = SignupDB.getInstance();

   String name = request.getParameter("name");
   String id = request.getParameter("id");
   String pw = request.getParameter("pw");
   String email = request.getParameter("email");
   String gender = request.getParameter("gender");
   String returns;

   if( (id == null) || (pw == null)  ) {//null check
	   returns = "입력하지 않은값이 있습니다.";
   }
   else {
	   returns = signupDB.signupConnDB(name, id, pw, email, gender);
   }

   System.out.println("access: signup " + returns);

   // 안드로이드로 전송
   out.println(returns);
%>