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
			// ����debug����
			props.setProperty("mail.debug", "true");
			// ���ͷ�������Ҫ�����֤
			props.setProperty("mail.smtp.auth", "true");
			// �����ʼ�������������
			props.setProperty("mail.host", "smtp.qq.com");
			// �����ʼ�Э������
			props.setProperty("mail.transport.protocol", "smtp");

			// ���û�����Ϣ
			Session session = Session.getInstance(props);

			// �����ʼ�����
			Message msg = new MimeMessage(session);
			msg.setSubject("JavaMail����");
			// �����ʼ�����
			msg.setText("��֤�룺"+emailCode+"");
			// ���÷�����
			msg.setFrom(new InternetAddress("1136471814@qq.com"));

			Transport transport = session.getTransport();
			// �����ʼ�������
			transport.connect("1136471814", "bjalioouqcimjdha");
			// �����ʼ�
			transport.sendMessage(msg, new Address[] { new InternetAddress(recipient)});
			// �ر�����
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
