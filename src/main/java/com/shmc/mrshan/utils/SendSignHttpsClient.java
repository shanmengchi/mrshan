package com.shmc.mrshan.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Set;

public class SendSignHttpsClient {

    /**
     * post请求以及参数是json
     *
     * @param url
     * @param jsonParams
     * @return
     */
    public static JSONObject doPostForJson(String url, JSONObject jsonParams) throws URISyntaxException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonObject = null;
        URIBuilder newBuilder = new URIBuilder(url);
        Set set = jsonParams.keySet();
        for (Object key : set) {
            newBuilder.addParameter((String)key, jsonParams.getString((String)key));
        }
        HttpPost httpPost = new HttpPost(newBuilder.build());
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(180 * 1000).setConnectionRequestTimeout(180 * 1000)
                .setSocketTimeout(180 * 1000).setRedirectsEnabled(true).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type", "application/json");
        try {
//            httpPost.setEntity(new StringEntity(jsonParams, ContentType.create("application/json", "utf-8")));
//            System.out.println("request parameters" + EntityUtils.toString(httpPost.getEntity()));
            System.out.println("httpPost:" + httpPost);
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity());
                System.out.println("result:" + result);
                jsonObject = JSONObject.parseObject(result);
                return jsonObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return jsonObject;
        }
    }


    /**
     * get 方式调用
     *
     * @param url
     * @return
     */
    public static String get(String url) {
        System.out.println("=====================HTTP GET 请求参数 url=" + url);
        InputStream is = null;
        String body = null;
        StringBuilder res = new StringBuilder();
        // 实例化CloseableHttpClient
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            // 添加URL和请求参数
            URIBuilder ub = new URIBuilder(url);
            // 使用get方法添加URL
            HttpGet get = new HttpGet(ub.build());
            // 设置请求超时时间
            RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).build();
            get.setConfig(config);
            //使用http调用远程，获取相应信息
            response = client.execute(get);
            // 获取响应状态码，可根据是否响应正常来判断是否需要进行下一步
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                return "请求失败 statusCode=" + statusCode;
            }
            // 获取响应实体
            HttpEntity entity = response.getEntity();
            // 读取响应内容
            if (entity != null) {
                is = entity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                while ((body = br.readLine()) != null) {
                    res.append(body);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("=====================HTTP GET 请求响应参数 result=" + res.toString());
        return res.toString();
    }



}
