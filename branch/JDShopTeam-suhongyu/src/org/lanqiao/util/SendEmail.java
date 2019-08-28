package org.lanqiao.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

public class SendEmail {
	@Test
	public static void send(String recipient,int emailCode){
		try {
			// TODO Auto-generated method stub
			Properties props = new Properties();
			// 开启debug调试
			props.setProperty("mail.debug", "true");
			// 发送服务器需要身份验证
			props.setProperty("mail.smtp.auth", "true");
			// 设置邮件服务器主机名
			props.setProperty("mail.host", "smtp.qq.com");
			// 发送邮件协议名称
			props.setProperty("mail.transport.protocol", "smtp");

			// 设置环境信息
			Session session = Session.getInstance(props);

			// 创建邮件对象
			Message msg = new MimeMessage(session);
			msg.setSubject("JavaMail测试");
			// 设置邮件内容
			msg.setText("验证码："+emailCode+"");
			// 设置发件人
			msg.setFrom(new InternetAddress("1136471814@qq.com"));

			Transport transport = session.getTransport();
			// 连接邮件服务器
			transport.connect("1136471814", "bjalioouqcimjdha");
			// 发送邮件
			transport.sendMessage(msg, new Address[] { new InternetAddress(recipient)});
			// 关闭连接
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
