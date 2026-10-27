<%@page import="com.db.SigninDB"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   SigninDB signinDB = SigninDB.getInstance();
	
   String id = request.getParameter("id");
   String pw = request.getParameter("pw");
   String returns = "";

   if( (id == null) || (pw == null)  ) {//null check
      returns = "입력하지 않은값이 있습니다.";
   }
   else {
      returns = signinDB.signinDB(id, pw);
      out.println(returns);
      returns = id + " " + returns;
   }

   System.out.println("access: signin " + returns);

   //output return

%>