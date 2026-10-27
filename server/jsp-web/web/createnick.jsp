<%@page import="com.db.CreateNick"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("utf-8");%>
<%
   CreateNick createNick = CreateNick.getInstance();

   String id = request.getParameter("id");
   String nick = request.getParameter("nick");

   if( (id == null) || (nick == null)  ) {//null check
      System.out.println("access: createnick 입력하지 않은 값이 있습니다");
   }else{
      System.out.println("access: createnick " + id + " " + nick);
      createNick.createNick(id, nick);
   }


%>