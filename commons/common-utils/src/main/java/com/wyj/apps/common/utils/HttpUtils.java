package com.wyj.apps.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.*;
import java.util.Map;

/**
 * Created
 * Author: wyj
 * Date: 2018/12/11
 */
public class HttpUtils {



    public static JSONObject get(String path, Map<String, Object> params) {

        if (params != null && !params.isEmpty()) {
            StringBuilder sb = new StringBuilder(path).append("?");

            for (Map.Entry<String, Object> entry : params.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue().toString());
            }

            path = sb.deleteCharAt(sb.length()-1).toString();

        }

        System.out.println("path:" + path);

        JSONObject ret = null;


        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // application/json;charset=UTF-8
            conn.setRequestProperty("accept", "text/html");
            conn.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
            conn.setUseCaches(false);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();

            System.out.println("result:" + sb.toString());

            ret = JSON.parseObject(sb.toString());

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static JSONObject post(String path, JSONObject body) {
        JSONObject ret = null;

        System.out.println("path:" + path +"\nbody:" + body);
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //设置是否想conn输出，因为是个POST请求，参数要放在http正文内，所以这位true
            conn.setDoInput(true);
            //要读取数据，所以设为true，默认true
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            //POST方式不能使用缓存
            conn.setUseCaches(false);
            //设置本次连接的Content-Type，为application/x-www-form-urlencoded
            //表示正文是urlencoded编码过的form参数，下面我们可以看到对正文的内容使用URLEncoder.encode编码
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

            //conn.connected();
            //conn.getOutputStream 会隐式的进行connect
            OutputStream out = conn.getOutputStream();
//            String content = "account=" + URLEncoder.encode("哈哈", "utf-8");
//            content += "$pwd=" + URLEncoder.encode("嘻嘻", "utf-8");
            out.write(body.toJSONString().getBytes());
            out.flush();
            out.close();

            //请求会在getInputStream的时候真正的发出
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            conn.disconnect();

            System.out.println("result:" + sb.toString());
            ret = JSON.parseObject(sb.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;

    }


}
