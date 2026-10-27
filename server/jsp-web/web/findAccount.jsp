<%@page import="com.db.FindAccount"%>
<%@ page import="com.db.MailSend" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("UTF-8");
%>
<html>
<head>
</head>
<body>
<%
    FindAccount findAccount = FindAccount.getInstance();

    String name = request.getParameter("name");
    String email = request.getParameter("email");


    String returns = findAccount.findAccount(name, email);

    if (returns.contains("failed")) {
        out.println(returns);
        System.out.println("access: findAcc " + name + email + returns);
    }else {
        out.println("done");
        System.out.println("access: findAcc " + name + email + "done");

        MailSend mailSend = new MailSend();

        mailSend.MailSend(email, returns);
    }
%>
</body>
</html>
