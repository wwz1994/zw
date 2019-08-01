package com.bus.utils;


import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * Created by 66 on 2018/3/5.
 */
public class SendMail {

    private static SendMail send;
    //协议
    private String smtp = "smtp";
    // 发送服务器服务器
    private String host = "smtp.qq.com";
    //邮件发送人
    private String sendName;
    //邮件发送人端口
    private String sendPort = "465";
    //邮件发送人名
    private String userName;
    // 邮件发送人密码
    private String password;

    private String nickName;


    /**
     * 邮件发送的方法
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     * @return 成功或失败
     */
    public boolean send(String to, String subject, String content) {

        // 第一步：创建Session
        Properties props = new Properties();
        // 指定邮件的传输协议，smtp(Simple Mail Transfer Protocol 简单的邮件传输协议)
        props.put("mail.transport.protocol", smtp);
        // 指定邮件发送服务器服务器 "smtp.qq.com"
        props.put("mail.host", host);
        // 指定邮件的发送人(您用来发送邮件的服务器，比如您的163\sina等邮箱)
        props.put("mail.from", sendName);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.socketFactory.port", sendPort);

        Session session = Session.getDefaultInstance(props);

        // 开启调试模式
        session.setDebug(true);
        try {
            // 第二步：获取邮件发送对象
            Transport transport = session.getTransport();
            // 连接邮件服务器，链接您的163、sina邮箱，用户名（不带@163.com，登录邮箱的邮箱账号，不是邮箱地址）、密码
            transport.connect(userName, password);
            Address toAddress = new InternetAddress(to);

            // 第三步：创建邮件消息体
            MimeMessage message = new MimeMessage(session);
            //设置自定义发件人昵称

            message.setFrom(new InternetAddress(nickName + " <" + sendName + ">"));
            //设置发信人
            // message.setFrom(new InternetAddress(sendName));
            // 邮件的主题
            message.setSubject(subject);
            //收件人
            message.addRecipient(Message.RecipientType.TO, toAddress);
            /*//抄送人
            Address ccAddress = new InternetAddress("first.lady@whitehouse.gov");
            message.addRecipient(Message.RecipientType.CC, ccAddress);*/
            // 邮件的内容
            message.setContent(content, "text/html;charset=utf-8");
            // 邮件发送时间
            message.setSentDate(new Date());

            // 第四步：发送邮件
            // 第一个参数：邮件的消息体
            // 第二个参数：邮件的接收人，多个接收人用逗号隔开（test1@163.com,test2@sina.com）
            transport.sendMessage(message, InternetAddress.parse(to));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getSendPort() {
        return sendPort;
    }

    public void setSendPort(String sendPort) {
        this.sendPort = sendPort;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        try {
            this.nickName = javax.mail.internet.MimeUtility.encodeText(nickName);
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {
        SendMail p = new SendMail();
        p.send("987927981@qq.com", "呵呵", "呵呵");
    }
}


