package com.swjtu.gcmformojo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.huawei.android.pushagent.api.PushEventReceiver;

import org.json.JSONObject;

import static com.swjtu.gcmformojo.MyApplication.MYTAG;
import static com.swjtu.gcmformojo.MyApplication.deviceHwToken;
import static com.swjtu.gcmformojo.MyApplication.mySettings;

/**
 * Created by HeiPi on 2017/3/14.
 * 华为推送接收器
 */

public class HwPushReceiver extends PushEventReceiver {


    @Override
    public void onToken(Context context, String token, Bundle extras){
        String belongId = extras.getString("belongId");
        String content = "华为推送token：" + token;
        deviceHwToken = token;
        Log.d(MYTAG, content);
    }

    @Override
    public boolean onPushMsg(Context context, byte[] msg, Bundle bundle) {

        try {
            String remoteMessageOrign = new String(msg, "UTF-8");
            JSONObject remoteMessage = new JSONObject(remoteMessageOrign);

            if(!remoteMessage.has("isAt")) remoteMessage.put("isAt","0");
            if(!remoteMessage.has("senderType")) remoteMessage.put("senderType","1");

            //SharedPreferences Settings =        context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
            String tokenSender = mySettings.getString("push_type","GCM");

            if(tokenSender.equals("HwPush")) {
                Log.d(MYTAG, "华为推送："+remoteMessageOrign);
                MessageUtil.MessageUtilDo(context,remoteMessage.getString("msgId"),remoteMessage.getString("type"),remoteMessage.getString("senderType"),remoteMessage.getString("title"),remoteMessage.getString("message"),remoteMessage.getString("isAt"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
}



