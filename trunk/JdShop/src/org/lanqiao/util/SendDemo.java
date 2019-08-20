package org.lanqiao.util;

import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.shcm.bean.BalanceResult;
import com.shcm.bean.SendResult;
import com.shcm.send.DataApi;
import com.shcm.send.OpenApi;

public class SendDemo {
	private static String sOpenUrl = "http://smsapi.c123.cn/OpenPlatform/OpenApi";
	private static String sDataUrl = "http://smsapi.c123.cn/DataPlatform/DataApi";

	// 接口帐号
	private static final String account = "1001@501326000001";

	// 接口密钥
	private static final String authkey = "5B7181A7A99478FF0E5FD92315DD2B64";

	// 通道组编号
	private static final int cgid = 52;

	// 默认使用的签名编号(未指定签名编号时传此值到服务器)
	private static final int csid = 0;

	public static List<SendResult> sendOnce(String mobile, String content) throws Exception {
		// 发送短信
		return OpenApi.sendOnce(mobile, content, 0, 0, null);
		// return OpenApi.sendOnce(new String[]{"15856912627","15102110086"},
		// "测试内容", 0, 0, null);
		// return OpenApi.sendBatch("15856912627,15102110086", "测试内容{|}测试内容", 0,
		// 0, null);
		// return OpenApi.sendBatch(new String[]{"15856912627","15102110086"},
		// new String[]{"测试内容","测试内容"}, 0, 0, null);
		// return OpenApi.sendParam("15856912627,15102110086", "测试内容{p1}", new
		// String[]{"a{|}b"}, 0, 0, null);
		// return OpenApi.sendParam(new String[]{"15856912627","15102110086"},
		// "测试内容{p1}", new String[]{"a{|}b"}, 0, 0, null);
	}

	public static void sendPhone(String mobile, String content) throws Exception {
		// 发送参数
		OpenApi.initializeAccount(sOpenUrl, account, authkey, cgid, csid);

		// 状态及回复参数
		DataApi.initializeAccount(sDataUrl, account, authkey);

		// 取帐户余额
		BalanceResult br = OpenApi.getBalance();
		if (br.getResult() < 1) {
			System.out.println("获取可用余额失败: " + br.getErrMsg());
			return;
		}
		System.out.println("可用条数: " + br.getRemain());
		List<SendResult> listItem = sendOnce(mobile, content);
		for (SendResult t : listItem) {
			if (t.getResult() < 1) {
				System.out.println("发送提交失败: " + t.getErrMsg());
				return;
			}

			System.out.println("发送成功: 消息编号<" + t.getMsgId() + "> 总数<" + t.getTotal() + "> 余额<" + t.getRemain() + ">");
		}
		/*
		 * // 以下代码演示如何更新接口密钥, UpdateResult ur = OpenApi.updateKey();
		 * if(ur.getResult() < 1) { System.out.println("更新接口密钥失败: " +
		 * br.getErrMsg()); return; } System.out.println("已成功更新密钥,新接口密钥为: " +
		 * ur.getAuthKey());
		 */

		// 以下代码演示如何提交发送短信并获取提交的返回信息

		/*
		 * // 以下代码演示如何循环获取状态报告和回复(真实使用时可以开单独线程或者进程调用,获取一次间隔10秒以上) while(true) {
		 * List<ReportResult> listSendState = DataApi.getSendState(true);
		 * for(ReportResult t:listSendState) { System.out.println("状态报告 => 序号<"
		 * + t.getId() + "> 消息编号<" + t.getMsgId() + "> 手机号码<" + t.getMobile() +
		 * "> 结果<" + (t.getStatus() > 1 ? "发送成功" : t.getStatus() > 0 ? "发送失败" :
		 * "未知状态") + "> 运营商返回<" + t.getDetail() + ">"); }
		 * 
		 * // 取回复 List<ReplyResult> listReply = DataApi.getReply(true);
		 * for(ReplyResult t:listReply) { System.out.println("回复信息 => 序号<" +
		 * t.getId() + "> 消息编号<" + t.getMsgId() + "> 回复时间<" + t.getReplyTime() +
		 * "> 手机号码<" + t.getMobile() + "> 回复内容<" + t.getContent() + ">"); }
		 * Thread.sleep(10000); }
		 */
	}
	public static void sendMile(String mile,String content) {
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
			msg.setSubject("JdShop注册验证");
			// 设置邮件内容
			msg.setText("您的注册验证码为："+content);
			// 设置发件人
			msg.setFrom(new InternetAddress("2819798523@qq.com"));

			Transport transport = session.getTransport();
			// 连接邮件服务器
			transport.connect("2819798523", "rpguxultvhbydejh");
			// 发送邮件
			transport.sendMessage(msg, new Address[] { new InternetAddress(mile) });
			// 关闭连接
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}