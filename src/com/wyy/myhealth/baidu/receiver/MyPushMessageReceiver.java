package com.wyy.myhealth.baidu.receiver;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.frontia.api.FrontiaPushMessageReceiver;
import com.wyy.myhealth.MainActivity;
import com.wyy.myhealth.baidu.utlis.Utils;
import com.wyy.myhealth.contants.ConstantS;
import com.wyy.myhealth.utils.NoticeUtils;

/**
 * Push��Ϣ����receiver�����д����Ҫ�Ļص������� һ����˵�� onBind�Ǳ���ģ���������startWork����ֵ��
 * onMessage��������͸����Ϣ�� onSetTags��onDelTags��onListTags��tag��ز����Ļص���
 * onNotificationClicked��֪ͨ�����ʱ�ص��� onUnbind��stopWork�ӿڵķ���ֵ�ص�
 * 
 * ����ֵ�е�errorCode���������£� 0 - Success 10001 - Network Problem 30600 - Internal
 * Server Error 30601 - Method Not Allowed 30602 - Request Params Not Valid
 * 30603 - Authentication Failed 30604 - Quota Use Up Payment Required 30605 -
 * Data Required Not Found 30606 - Request Time Expires Timeout 30607 - Channel
 * Token Timeout 30608 - Bind Relation Not Found 30609 - Bind Number Too Many
 * 
 * �����������Ϸ��ش���ʱ��������Ͳ����������⣬����ͬһ����ķ���ֵrequestId��errorCode��ϵ����׷�����⡣
 * 
 */

public class MyPushMessageReceiver extends FrontiaPushMessageReceiver {

	/** TAG to Log */
	public static final String TAG = MyPushMessageReceiver.class
			.getSimpleName();

	public boolean onbind = false;
	
	private int berrorCode=0;

	/**
	 * ����PushManager.startWork��sdk����push
	 * server�������������������첽�ġ�������Ľ��ͨ��onBind���ء� �������Ҫ�õ������ͣ���Ҫ�������ȡ��channel
	 * id��user id�ϴ���Ӧ��server�У��ٵ���server�ӿ���channel id��user id�������ֻ������û����͡�
	 * 
	 * @param context
	 *            BroadcastReceiver��ִ��Context
	 * @param errorCode
	 *            �󶨽ӿڷ���ֵ��0 - �ɹ�
	 * @param appid
	 *            Ӧ��id��errorCode��0ʱΪnull
	 * @param userId
	 *            Ӧ��user id��errorCode��0ʱΪnull
	 * @param channelId
	 *            Ӧ��channel id��errorCode��0ʱΪnull
	 * @param requestId
	 *            �����˷��������id����׷������ʱ���ã�
	 * @return none
	 */
	@Override
	public void onBind(Context context, int errorCode, String appid,
			String userId, String channelId, String requestId) {
		String responseString = "onBind errorCode=" + errorCode + " appid="
				+ appid + " userId=" + userId + " channelId=" + channelId
				+ " requestId=" + requestId;
		Log.d(TAG, responseString);

		Utils.requestId = requestId;
		Utils.channelId = channelId;
		Utils.appid = appid;
		Utils.userid = userId;
		onbind = true;
		berrorCode=errorCode;
		// �󶨳ɹ��������Ѱ�flag��������Ч�ļ��ٲ���Ҫ�İ�����
		if (errorCode == 0) {
			Utils.setBind(context, true);
			
		}
		updateContent(context, responseString);
		// Demo���½���չʾ���룬Ӧ��������������Լ��Ĵ����߼�
		
	}

	/**
	 * ����͸����Ϣ�ĺ�����
	 * 
	 * @param context
	 *            ������
	 * @param message
	 *            ���͵���Ϣ
	 * @param customContentString
	 *            �Զ�������,Ϊ�ջ���json�ַ���
	 */
	@SuppressWarnings("unused")
	@Override
	public void onMessage(Context context, String message,
			String customContentString) {
		String messageString = message;

		Log.d(TAG, messageString);

		// �Զ������ݻ�ȡ��ʽ��mykey��myvalue��Ӧ͸����Ϣ����ʱ�Զ������������õļ���ֵ
		if (customContentString != null
				& TextUtils.isEmpty(customContentString)) {
			JSONObject customJson = null;
			try {
				customJson = new JSONObject(customContentString);
				String myvalue = null;
				if (customJson.isNull("mykey")) {
					myvalue = customJson.getString("mykey");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Demo���½���չʾ���룬Ӧ��������������Լ��Ĵ����߼�
		updateContent(context, messageString);
	}

	/**
	 * ����֪ͨ����ĺ�����ע������֪ͨ���û����ǰ��Ӧ���޷�ͨ���ӿڻ�ȡ֪ͨ�����ݡ�
	 * 
	 * @param context
	 *            ������
	 * @param title
	 *            ���͵�֪ͨ�ı���
	 * @param description
	 *            ���͵�֪ͨ������
	 * @param customContentString
	 *            �Զ������ݣ�Ϊ�ջ���json�ַ���
	 */
	@SuppressWarnings("unused")
	@Override
	public void onNotificationClicked(Context context, String title,
			String description, String customContentString) {
		String notifyString = "֪ͨ��� title=\"" + title + "\" description=\""
				+ description + "\" customContent=" + customContentString;
		Log.d(TAG, notifyString);

		// �Զ������ݻ�ȡ��ʽ��mykey��myvalue��Ӧ֪ͨ����ʱ�Զ������������õļ���ֵ
		if (customContentString != null
				& TextUtils.isEmpty(customContentString)) {
			JSONObject customJson = null;
			try {
				customJson = new JSONObject(customContentString);
				String myvalue = null;
				if (customJson.isNull("mykey")) {
					myvalue = customJson.getString("mykey");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Demo���½���չʾ���룬Ӧ��������������Լ��Ĵ����߼�
		updateContent(context, notifyString);
	}

	/**
	 * setTags() �Ļص�������
	 * 
	 * @param context
	 *            ������
	 * @param errorCode
	 *            �����롣0��ʾĳЩtag�Ѿ����óɹ�����0��ʾ����tag�����þ�ʧ�ܡ�
	 * @param successTags
	 *            ���óɹ���tag
	 * @param failTags
	 *            ����ʧ�ܵ�tag
	 * @param requestId
	 *            ������������͵������id
	 */
	@Override
	public void onSetTags(Context context, int errorCode,
			List<String> sucessTags, List<String> failTags, String requestId) {
		String responseString = "onSetTags errorCode=" + errorCode
				+ " sucessTags=" + sucessTags + " failTags=" + failTags
				+ " requestId=" + requestId;
		Log.d(TAG, responseString);

		// Demo���½���չʾ���룬Ӧ��������������Լ��Ĵ����߼�
		// updateContent(context, responseString);
		getTagMsg(context, responseString, errorCode);

	}

	/**
	 * delTags() �Ļص�������
	 * 
	 * @param context
	 *            ������
	 * @param errorCode
	 *            �����롣0��ʾĳЩtag�Ѿ�ɾ���ɹ�����0��ʾ����tag��ɾ��ʧ�ܡ�
	 * @param successTags
	 *            �ɹ�ɾ����tag
	 * @param failTags
	 *            ɾ��ʧ�ܵ�tag
	 * @param requestId
	 *            ������������͵������id
	 */
	@Override
	public void onDelTags(Context context, int errorCode,
			List<String> sucessTags, List<String> failTags, String requestId) {
		String responseString = "onDelTags errorCode=" + errorCode
				+ " sucessTags=" + sucessTags + " failTags=" + failTags
				+ " requestId=" + requestId;
		Log.d(TAG, responseString);

		// Demo���½���չʾ���룬Ӧ��������������Լ��Ĵ����߼�
		// updateContent(context, responseString);
	}

	/**
	 * listTags() �Ļص�������
	 * 
	 * @param context
	 *            ������
	 * @param errorCode
	 *            �����롣0��ʾ�о�tag�ɹ�����0��ʾʧ�ܡ�
	 * @param tags
	 *            ��ǰӦ�����õ�����tag��
	 * @param requestId
	 *            ������������͵������id
	 */
	@Override
	public void onListTags(Context context, int errorCode, List<String> tags,
			String requestId) {
		String responseString = "onListTags errorCode=" + errorCode + " tags="
				+ tags;
		Log.d(TAG, responseString);

		// Demo���½���չʾ���룬Ӧ��������������Լ��Ĵ����߼�
		updateContent(context, responseString);
	}

	/**
	 * PushManager.stopWork() �Ļص�������
	 * 
	 * @param context
	 *            ������
	 * @param errorCode
	 *            �����롣0��ʾ�������ͽ�󶨳ɹ�����0��ʾʧ�ܡ�
	 * @param requestId
	 *            ������������͵������id
	 */
	@Override
	public void onUnbind(Context context, int errorCode, String requestId) {
		String responseString = "onUnbind errorCode=" + errorCode
				+ " requestId = " + requestId;
		Log.d(TAG, responseString);

		// ��󶨳ɹ�������δ��flag��
		if (errorCode == 0) {
			Utils.setBind(context, false);
		}
		// Demo���½���չʾ���룬Ӧ��������������Լ��Ĵ����߼�
		updateContent(context, responseString);
	}

	private void updateContent(Context context, String content) {
		Log.d(TAG, "updateContent");
		// String logText = "" + Utils.logStringCache;
		//
		// if (!logText.equals("")) {
		// logText += "\n";
		// }

		// SimpleDateFormat sDateFormat = new SimpleDateFormat("HH-mm");
		// logText += sDateFormat.format(new Date()) + ": ";
		// logText += content;

		Utils.logStringCache = content;

		if (onbind) {
			onbind = false;
			Intent intent = new Intent();
			intent.putExtra("errorCode", berrorCode);
			intent.setAction(ConstantS.BAIDU_ONBIND);
			context.sendBroadcast(intent);

		} else {

			Intent intent = new Intent();
			intent.setClass(context, MainActivity.class);

			// intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

			Intent intent2 = new Intent();
			intent2.setAction("com.wyy.bing");
			// intent2.setAction(ConstantS.NEW_LOGIN_ACTION);
			parseJson(context, content, intent2, intent);

			// context.startActivity(intent);
			context.sendBroadcast(intent2);

		}

	}

	private void getTagMsg(Context context, String content, int errcode) {

		if (0 == errcode) {
			// ToastUtils.showLong(context, "ע��ɹ�");
			return;
		}
//		ToastUtils.showLong(context, "������:" + errcode);
	}

	private void parseJson(Context context, String json, Intent intent,
			Intent intent2) {

		if (null == json) {
			return;
		}

		Log.i(TAG, "��Ϣ:" + json);

		try {
			JSONObject jObject = new JSONObject(json);
			String type = jObject.getString("type");
			if (type.equals(ConstantS.ADD_FOODS_COMMENT)) {
				intent2.setAction(ConstantS.NEW_FOOD_COMMENT);
				Utils.add_Foods_Comm = json;
				Utils.mstlList.add(json);
				intent2.putExtra("msg", json);
				intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent2);
				NoticeUtils.notice(context, Utils.mstlList.size() + "������Ϣ", ConstantS.NEW_FOOD_COMMENT_ID);
			} else if (type.equals(ConstantS.FIRST_LOGIN)
					|| type.equals("message")) {
				intent.setAction(ConstantS.NEW_LOGIN_ACTION);
				Utils.response = jObject.getString("content");
				Utils.loginList.add(Utils.response);
				NoticeUtils.notice(context, Utils.loginList.size() + "������Ϣ", ConstantS.NEW_LOGIN_ACTION_ID);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}