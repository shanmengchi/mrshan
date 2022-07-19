package com.shmc.mrshan.utils;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class SendSignHttpsClient2 {

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
                jsonObject = JSONObject.fromObject(result);
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


}
