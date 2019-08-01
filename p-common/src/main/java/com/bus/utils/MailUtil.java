package com.bus.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;



public class MailUtil {

    private static final Log logger = LogFactory.getLog(MailUtil.class);

    public static String host="smtp.qq.com";

    public static String sender="1512180909@qq.com";

    public static String username="1512180909@qq.com";

    public static String password="bcnoqpkhnzjqhedc";


    public static boolean send(Mail mail) {
        // 发送email  
        HtmlEmail email = new HtmlEmail();
        try {
            // 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"  
            email.setHostName(mail.getHost());
            // 字符编码集的设置  
            email.setCharset(Mail.ENCODEING);
            // 收件人的邮箱  
            email.addTo(mail.getReceiver());
            //email.setSSL(true);
            email.setSSLOnConnect(true);
            email.setSslSmtpPort("465");
            // 发送人的邮箱  
            email.setFrom(mail.getSender(), mail.getName());
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码  
            email.setAuthentication(mail.getUsername(), mail.getPassword());
            // 要发送的邮件主题  
            email.setSubject(mail.getSubject());
            // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签  
            email.setMsg(mail.getMessage());
            // 发送  
            email.send();
            if (logger.isDebugEnabled()) {
                logger.debug(mail.getSender() + " 发送邮件到 " + mail.getReceiver());
            }
            return true;
        } catch (EmailException e) {
            e.printStackTrace();
            logger.error(e);
            logger.error(mail.getSender() + " 发送邮件到 " + mail.getReceiver() + " 失败");
            //throw new Ex("邮件发送失败");
        }
        return false;
    }


    /**
     * 发送邮件
     *
     * @param receiver 接收人
     * @param subject  主题
     * @param content  内容
     */
    public static boolean send(String receiver, String subject, String content) {
        Mail mail = new Mail();
        mail.setHost(host); // 设置邮件服务器,如果不用163的,自己找找看相关的

        mail.setUsername(username); // 登录账号,一般都是和邮箱名一样吧  
        mail.setPassword(password); // 发件人邮箱的登录密码  
        mail.setSender(sender);

        mail.setReceiver(receiver); // 接收人  
        mail.setSubject(subject);
        mail.setMessage(content);
        return send(mail);
    }

    public static void main(String[] args){
        send("2575719681@qq.com","王威振邮箱工具类测试","版权声明：本文为博主原创文章，未经博主允许不得转载。<a href='#'>https://blog.csdn.net/</a>");

    }

}
