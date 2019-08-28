package org.lanqiao.util;

import java.util.List;
import com.shcm.bean.BalanceResult;
import com.shcm.bean.SendResult;
import com.shcm.send.DataApi;
import com.shcm.send.OpenApi;

public class SendDemo {
	private static String sOpenUrl = "http://smsapi.c123.cn/OpenPlatform/OpenApi";
	private static String sDataUrl = "http://smsapi.c123.cn/DataPlatform/DataApi";

	// �ӿ��ʺ�
	private static final String account = "1001@501326000001";
	
	//50132600

	// �ӿ���Կ
	private static final String authkey = "5B7181A7A99478FF0E5FD92315DD2B64";

	// ͨ������
	private static final int cgid = 52;

	// Ĭ��ʹ�õ�ǩ�����(δָ��ǩ�����ʱ����ֵ��������)
	private static final int csid = 0;

	public static List<SendResult> sendOnce(String mobile, String content) throws Exception {
		// ���Ͷ���
		return OpenApi.sendOnce(mobile, content, 0, 0, null);
		// return OpenApi.sendOnce(new String[]{"15856912627","15102110086"},
		// "��������", 0, 0, null);
		// return OpenApi.sendBatch("15856912627,15102110086", "��������{|}��������", 0,
		// 0, null);
		// return OpenApi.sendBatch(new String[]{"15856912627","15102110086"},
		// new String[]{"��������","��������"}, 0, 0, null);
		// return OpenApi.sendParam("15856912627,15102110086", "��������{p1}", new
		// String[]{"a{|}b"}, 0, 0, null);
		// return OpenApi.sendParam(new String[]{"15856912627","15102110086"},
		// "��������{p1}", new String[]{"a{|}b"}, 0, 0, null);
	}

	public static void send(String mobile, String content) throws Exception {
		// ���Ͳ���
		OpenApi.initializeAccount(sOpenUrl, account, authkey, cgid, csid);

		// ״̬���ظ�����
		DataApi.initializeAccount(sDataUrl, account, authkey);

		// ȡ�ʻ����
		BalanceResult br = OpenApi.getBalance();
		if (br.getResult() < 1) {
			System.out.println("��ȡ�������ʧ��: " + br.getErrMsg());
			return;
		}
		System.out.println("��������: " + br.getRemain());
		List<SendResult> listItem = sendOnce(mobile, content);
		for (SendResult t : listItem) {
			if (t.getResult() < 1) {
				System.out.println("�����ύʧ��: " + t.getErrMsg());
				return;
			}

			System.out.println("���ͳɹ�: ��Ϣ���<" + t.getMsgId() + "> ����<" + t.getTotal() + "> ���<" + t.getRemain() + ">");
		}
		/*
		 * // ���´�����ʾ��θ��½ӿ���Կ, UpdateResult ur = OpenApi.updateKey();
		 * if(ur.getResult() < 1) { System.out.println("���½ӿ���Կʧ��: " +
		 * br.getErrMsg()); return; } System.out.println("�ѳɹ�������Կ,�½ӿ���ԿΪ: " +
		 * ur.getAuthKey());
		 */

		// ���´�����ʾ����ύ���Ͷ��Ų���ȡ�ύ�ķ�����Ϣ

		/*
		 * // ���´�����ʾ���ѭ����ȡ״̬����ͻظ�(��ʵʹ��ʱ���Կ������̻߳��߽��̵���,��ȡһ�μ��10������) while(true) {
		 * List<ReportResult> listSendState = DataApi.getSendState(true);
		 * for(ReportResult t:listSendState) { System.out.println("״̬���� => ���<"
		 * + t.getId() + "> ��Ϣ���<" + t.getMsgId() + "> �ֻ�����<" + t.getMobile() +
		 * "> ���<" + (t.getStatus() > 1 ? "���ͳɹ�" : t.getStatus() > 0 ? "����ʧ��" :
		 * "δ֪״̬") + "> ��Ӫ�̷���<" + t.getDetail() + ">"); }
		 * 
		 * // ȡ�ظ� List<ReplyResult> listReply = DataApi.getReply(true);
		 * for(ReplyResult t:listReply) { System.out.println("�ظ���Ϣ => ���<" +
		 * t.getId() + "> ��Ϣ���<" + t.getMsgId() + "> �ظ�ʱ��<" + t.getReplyTime() +
		 * "> �ֻ�����<" + t.getMobile() + "> �ظ�����<" + t.getContent() + ">"); }
		 * Thread.sleep(10000); }
		 */
	}
}