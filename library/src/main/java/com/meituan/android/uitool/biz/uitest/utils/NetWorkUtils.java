package com.meituan.android.uitool.biz.uitest.utils;

import android.os.Handler;
import android.os.Looper;

import com.meituan.android.uitool.biz.uitest.base.UiTestUploadResult;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by wenbin on 2018/9/28.
 */

public class NetWorkUtils {
//    public static final String API_SERVER_URL = "http://172.18.37.248:8089/api/uicheck/addImage";
    public static final String API_SERVER_URL = "http://monitor.web.dev.sankuai.com/api/uicheck/addImage"; //上传图片的接口

    private static final int TIME_OUT = 8 * 1000;                          //超时时间
    private static final String CHARSET = "utf-8";                         //编码格式
    private static final String PREFIX = "--";                            //前缀
    private static final String BOUNDARY = UUID.randomUUID().toString();  //边界标识 随机生成
    private static final String LINE_END = "\r\n";                        //换行


    public static boolean uploadImage(final byte[] img, final String user, final OnResponse onResponse) {
        final Handler handler = new Handler(Looper.getMainLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String,String> params = new HashMap<>();
                    params.put("name","rd");
                    params.put("user",user);

                    URL url = new URL(API_SERVER_URL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    con.setUseCaches(false);

                    con.setRequestMethod("POST");
                    con.setReadTimeout(TIME_OUT);
                    con.setConnectTimeout(TIME_OUT);

                    con.setRequestProperty("Connection", "Keep-Alive");
                    con.setRequestProperty("Charset", "utf-8");

                    con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);

                    DataOutputStream dataOutputStream = new DataOutputStream(con.getOutputStream());
                    dataOutputStream.writeBytes(getStrParams(params));

                    dataOutputStream.writeBytes(PREFIX + BOUNDARY + LINE_END);
                    dataOutputStream.writeBytes("Content-Disposition:form-data;name=\"image\";filename=\"rd.png\""+LINE_END);
                    dataOutputStream.writeBytes("Content-Type: image/jpg" + LINE_END);
                    dataOutputStream.writeBytes("Content-Transfer-Encoding: 8bit" + LINE_END);
                    dataOutputStream.writeBytes(LINE_END);

                    dataOutputStream.write(img,0,img.length);
                    dataOutputStream.writeBytes(LINE_END);
                    dataOutputStream.writeBytes(PREFIX + BOUNDARY + PREFIX + LINE_END);
                    dataOutputStream.flush();
                    dataOutputStream.close();
                    int responCode = con.getResponseCode();

                    if (responCode == 200) {
                        StringBuilder builder = new StringBuilder();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                        reader.close();
                        final UiTestUploadResult result = new UiTestUploadResult();
                        JSONObject jsonObject = new JSONObject(builder.toString());
                        result.message = jsonObject.getString("message");
                        result.code = jsonObject.getInt("code");
                        if (onResponse!= null) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onResponse.onSuccess(result);
                                }
                            });
                        }
                    }else {
                        if (onResponse != null) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    onResponse.onFailue();
                                }
                            });
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return false;
    }
    private static String getStrParams(Map<String,String> strParams){
        StringBuilder strSb = new StringBuilder();
        for (Map.Entry<String, String> entry : strParams.entrySet() ){
            strSb.append(PREFIX)
                    .append(BOUNDARY)
                    .append(LINE_END)
                    .append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_END)
                    .append("Content-Type: text/plain; charset=" + CHARSET + LINE_END)
                    .append("Content-Transfer-Encoding: 8bit" + LINE_END)
                    .append(LINE_END)// 参数头设置完以后需要两个换行，然后才是参数内容
                    .append(entry.getValue())
                    .append(LINE_END);
        }
        return strSb.toString();
    }

    public interface OnResponse {
        void onSuccess(UiTestUploadResult result);
        void onFailue();
    }


}
