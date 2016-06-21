package com.abin.lee.camel.message.controller.test;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CamelNativeTopicSendTest {
    public static String httpURL = "http://localhost:9000/camel/camelNativeTopic";

    @Test
    public void testCamelTopicSend() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(httpURL);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("title", "apple"));
        nvps.add(new BasicNameValuePair("content", "121"));

//        httpPost.setHeader("Content-Type", "application/Json");//
//        httpPost.setHeader("accept-Type", "application/Json");//
        HttpResponse httpResponse = null;
        String result = "";
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            httpResponse = httpClient.execute(httpPost);
            BufferedInputStream buffer = new BufferedInputStream(
                    httpResponse.getEntity().getContent());
            byte[] bytes = new byte[1024];
            int line = 0;
            StringBuilder builder = new StringBuilder();
            while ((line = buffer.read(bytes)) != -1) {
                builder.append(new String(bytes, 0, line, "UTF-8"));
            }
            result = builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpPost.isAborted()) {
                httpPost.abort();
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("result=" + result);
    }

}
