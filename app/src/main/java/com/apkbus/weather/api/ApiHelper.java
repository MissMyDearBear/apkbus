package com.apkbus.weather.api;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;


/**
 * description:
 * author: bear .
 * Created date:  2017/10/10.
 */
public class ApiHelper {
    public static final String KEY="217ef599e0890";
   private static String BASE_URL="http://apicloud.mob.com/v1";
   public static void get(final String method,final HashMap<String,String>params,final ApiCallBack callBack){
      new Thread(new Runnable() {
          @Override
          public void run() {
              try {
                  StringBuilder tempParams=new StringBuilder();
                  int pos=0;
                  for(String key:params.keySet()){
                      if(pos>0){
                          tempParams.append("&");
                      }
                      tempParams.append(String.format("%s=%s",key, URLEncoder.encode(params.get(key),"utf-8")));
                      pos++;
                  }
                  String requestUrl=BASE_URL+method+"?"+tempParams.toString();
                  //新建一个URL对象
                  URL url=new URL(requestUrl);
                  //打开链接
                  HttpURLConnection urlConn= (HttpURLConnection) url.openConnection();
                  //设置链接主机超时时间
                  urlConn.setConnectTimeout(5*1000);
                  //设置从主机读取数据超时时间
                  urlConn.setReadTimeout(5*1000);
                  //设置是否使用缓存
                  urlConn.setUseCaches(true);
                  //设置请求方式为get
                  urlConn.setRequestMethod("GET");
                  //urlConn设置请求头信息
                  //设置请求中的媒体类型信息。
                  urlConn.setRequestProperty("Content-Type", "application/json");
                  //设置客户端与服务连接类型
                  urlConn.addRequestProperty("Connection", "Keep-Alive");
                  // 开始连接
                  urlConn.connect();
                  if(urlConn.getResponseCode()== HttpsURLConnection.HTTP_OK){
                      //获取返回数据
                      String result=streamToString(urlConn.getInputStream());
                      if(callBack!=null){
                          callBack.onSuccess(result);
                      }
                  }else{
                      if(callBack!=null){
                          callBack.onError("返回码："+urlConn.getResponseCode());

                      }

                  }
                  urlConn.disconnect();
              } catch (Exception e) {
                  e.printStackTrace();
              }

          }
      }).start();
   }
    public static void post(final String url,final HashMap<String,String>paramsMap, final ApiCallBack callBack){
     new Thread(new Runnable() {
         @Override
         public void run() {
             try {
                 String baseUrl = BASE_URL+url;
                 //合成参数
                 StringBuilder tempParams = new StringBuilder();
                 int pos = 0;
                 for (String key : paramsMap.keySet()) {
                     if (pos > 0) {
                         tempParams.append("&");
                     }
                     tempParams.append(String.format("%s=%s", key,  URLEncoder.encode(paramsMap.get(key),"utf-8")));
                     pos++;
                 }
                 String params =tempParams.toString();
                 // 请求的参数转换为byte数组
                 byte[] postData = params.getBytes();
                 // 新建一个URL对象
                 URL url = new URL(baseUrl);
                 // 打开一个HttpURLConnection连接
                 HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                 // 设置连接超时时间
                 urlConn.setConnectTimeout(5 * 1000);
                 //设置从主机读取数据超时
                 urlConn.setReadTimeout(5 * 1000);
                 // Post请求必须设置允许输出 默认false
                 urlConn.setDoOutput(true);
                 //设置请求允许输入 默认是true
                 urlConn.setDoInput(true);
                 // Post请求不能使用缓存
                 urlConn.setUseCaches(false);
                 // 设置为Post请求
                 urlConn.setRequestMethod("POST");
                 //设置本次连接是否自动处理重定向
                 urlConn.setInstanceFollowRedirects(true);
                 // 配置请求Content-Type
                 urlConn.setRequestProperty("Content-Type", "application/json");
                 // 开始连接
                 urlConn.connect();
                 // 发送请求参数
                 DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
                 dos.write(postData);
                 dos.flush();
                 dos.close();
                 // 判断请求是否成功
                 if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                     // 获取返回的数据
                     String result = streamToString(urlConn.getInputStream());
                     if(callBack!=null){
                         callBack.onSuccess(result);
                     }
                 } else {
                     if(callBack!=null){
                         callBack.onError("返回码："+urlConn.getResponseCode());

                     }
                 }
                 // 关闭连接
                 urlConn.disconnect();
             } catch (Exception e) {
                e.printStackTrace();
             }
         }
     }).start();
    }

    /**
     * 将输入流转换成字符串
     *
     * @param is 从网络获取的输入流
     */
    private static String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            return null;
        }
    }

}
