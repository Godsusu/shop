package org.lanqiao.util;

import java.util.List;
import com.shcm.bean.BalanceResult;
import com.shcm.bean.SendResult;
import com.shcm.send.DataApi;
import com.shcm.send.OpenApi;

public class SendDemo {
	private static String sOpenUrl = "http://smsapi.c123.cn/OpenPlatform/OpenApi";
	private static String sDataUrl = "http://smsapi.c123.cn/DataPlatform/DataApi";

	// 接口帐号
	private static final String account = "1001@501326000001";
	
	//50132600

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

	public static void send(String mobile, String content) throws Exception {
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
}