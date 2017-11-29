package com.apkbus.weather.api

import android.app.Activity
import android.text.TextUtils
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.net.ssl.HttpsURLConnection

class ApiHelper {
    companion object {
        var KEY = "520520test"
        val BASE_URL = "http://apicloud.mob.com/v1"

        fun get(mActivity: Activity?, method: String, params: HashMap<String, String>, callBack: ApiCallBack?) {
            Thread(Runnable {
                try {
                    val tempParams = StringBuilder()
                    var pos = 0
                    for (key in params.keys) {
                        if (pos > 0) {
                            tempParams.append("&")
                        }
                        tempParams.append(String.format("%s=%s", key, URLEncoder.encode(params[key], "utf-8")))
                        pos++
                    }
                    val requestUrl = BASE_URL + method + "?" + tempParams.toString()
                    //新建一个URL对象
                    val url = URL(requestUrl)
                    //打开链接
                    val urlConn = url.openConnection() as HttpURLConnection
                    //设置链接主机超时时间
                    urlConn.connectTimeout = 5 * 1000
                    //设置从主机读取数据超时时间
                    urlConn.readTimeout = 5 * 1000
                    //设置是否使用缓存
                    urlConn.useCaches = true
                    //设置请求方式为get
                    urlConn.requestMethod = "GET"
                    //urlConn设置请求头信息
                    //设置请求中的媒体类型信息。
                    urlConn.setRequestProperty("Content-Type", "application/json")
                    //设置客户端与服务连接类型
                    urlConn.addRequestProperty("Connection", "Keep-Alive")
                    // 开始连接
                    urlConn.connect()
                    if (urlConn.responseCode == HttpsURLConnection.HTTP_OK) {
                        //获取返回数据
                        val result = streamToString(urlConn.inputStream)
                        if (mActivity == null) {
                            callBack?.onSuccess(result)
                        } else if (!TextUtils.isEmpty(result) && !mActivity.isFinishing) {
                            mActivity.runOnUiThread {
                                if (callBack != null && !TextUtils.isEmpty(result))
                                    callBack.onSuccess(result)
                            }
                        }
                    } else {
                        if (mActivity == null) {
                            callBack?.onError("返回码：" + urlConn.responseCode)
                        } else if (!mActivity.isFinishing) {
                            mActivity.runOnUiThread {
                                callBack?.onError("返回码：" + urlConn.responseCode)
                            }
                        }
                    }
                    urlConn.disconnect()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }).start()
        }

        fun post(url: String, paramsMap: HashMap<String, String>, callBack: ApiCallBack?) {
            Thread(Runnable {
                try {
                    val baseUrl = BASE_URL + url
                    //合成参数
                    val tempParams = StringBuilder()
                    var pos = 0
                    for (key in paramsMap.keys) {
                        if (pos > 0) {
                            tempParams.append("&")
                        }
                        tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap[key], "utf-8")))
                        pos++
                    }
                    val params = tempParams.toString()
                    // 请求的参数转换为byte数组
                    val postData = params.toByteArray()
                    // 新建一个URL对象
                    val url1 = URL(baseUrl)
                    // 打开一个HttpURLConnection连接
                    val urlConn = url1.openConnection() as HttpURLConnection
                    // 设置连接超时时间
                    urlConn.connectTimeout = 5 * 1000
                    //设置从主机读取数据超时
                    urlConn.readTimeout = 5 * 1000
                    // Post请求必须设置允许输出 默认false
                    urlConn.doOutput = true
                    //设置请求允许输入 默认是true
                    urlConn.doInput = true
                    // Post请求不能使用缓存
                    urlConn.useCaches = false
                    // 设置为Post请求
                    urlConn.requestMethod = "POST"
                    //设置本次连接是否自动处理重定向
                    urlConn.instanceFollowRedirects = true
                    // 配置请求Content-Type
                    urlConn.setRequestProperty("Content-Type", "application/json")
                    // 开始连接
                    urlConn.connect()
                    // 发送请求参数
                    val dos = DataOutputStream(urlConn.outputStream)
                    dos.write(postData)
                    dos.flush()
                    dos.close()
                    // 判断请求是否成功
                    if (urlConn.responseCode == HttpURLConnection.HTTP_OK) {
                        // 获取返回的数据
                        val result = streamToString(urlConn.inputStream)
                        if (!TextUtils.isEmpty(result)) {
                            callBack?.onSuccess(result)
                        }
                    } else {
                        callBack?.onError("返回码：" + urlConn.responseCode)
                    }
                    // 关闭连接
                    urlConn.disconnect()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }).start()
        }

        /**
         * 将输入流转换成字符串
         *
         * @param is 从网络获取的输入流
         */
        private fun streamToString(ios: InputStream): String {
            return try {
                val baos = ByteArrayOutputStream()
                val buffer = ByteArray(1024)
                var len = ios.read(buffer)
                while (len != -1) {
                    baos.write(buffer, 0, len)
                    len = ios.read(buffer)
                }
                baos.close()
                ios.close()
                val byteArray = baos.toByteArray()
                String(byteArray)
            } catch (e: Exception) {
                "Exception"
            }
        }


        /**
         * 获取天气详情
         */
        fun getWeatherDetail(activity: Activity?, province: String?, city: String?, town: String?, callBack: ApiCallBack?) {
            val params = HashMap<String, String>()
            params.put("key", "520520test")
            params.put("province", province!!)
            params.put("city", town!!)
            get(activity, "/weather/query", params, callBack)
        }
    }
}
