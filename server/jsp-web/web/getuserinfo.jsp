<%@page import="com.db.GetUserInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
  GetUserInfo getUserInfo = GetUserInfo.getInstance();

  String id = request.getParameter("id");

  String returns;

  if(id == null) {//null check
    returns = "입력하지 않은값이 있습니다.";
  }
  else {
    returns = getUserInfo.getUserInfo(id);
    out.println(returns);
    returns = id + " " + returns;
  }

  System.out.println("access: getuserinfo " + returns);
%>