package com.db;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuth extends Authenticator{
    PasswordAuthentication pa;
    public MailAuth() {
        String mail_id = System.getenv("MAIL_ID");
        String mail_pw = System.getenv("MAIL_APP_PASSWORD"); // Gmail 앱 비밀번호
        pa = new PasswordAuthentication(mail_id, mail_pw);
    }

    public PasswordAuthentication getPasswordAuthentication() {
        return pa;
    }
}
