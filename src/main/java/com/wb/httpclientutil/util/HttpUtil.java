package com.wb.httpclientutil.util;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * Description: 默认使用HttpClientFactory的getHttpClient ()返回的实例，也可以通过构造函数传入其他方法返回的实例，或者自己实现的实例
 * User: future
 * Date: 2017-12-11
 * Time: 13:03
 */
public class HttpUtil {
    private CloseableHttpClient httpClient;

    public HttpUtil () {
        httpClient = HttpClientFactory.getHttpClient();
    }

    public HttpUtil (CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }


    public byte[] get(String url){
        return get(url,null);
    }

    /**
     * 设置Header,指定编码的get请求
     * @param url 请求地址
     * @param headers 消息头
     * @return byte[]
     */
    public byte[] get(String url, Header[] headers){
        byte[] responseContent = null;
        HttpGet get = new HttpGet(url);
        if (headers != null) {
            get.setHeaders(headers);
        }
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity content = response.getEntity();
                responseContent = EntityUtils.toByteArray(content);
                //释放实体
                EntityUtils.consume(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭响应，只有关闭响应才断开连接这步很重要
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    public byte[] post(String url) {
        return post(url, null);
    }

    public byte[] post(String url, List<NameValuePair> postParams){
        return post(url, postParams, null);
    }

    public byte[] post(String url,  List<NameValuePair> postParams, Header[] headers) {
        return post(url, postParams, null, null);
    }

    public byte[] post(String url,  List<NameValuePair> postParams, Header[] headers, String charset) {
        byte[] responseContent = null;
        String finalCharset = null;
        finalCharset = charset != null ? charset : CharsetConstants.UTF8;
        //System.out.println(charset);
        try {
            HttpEntity entity = null;
            if (postParams != null) {
                entity = new UrlEncodedFormEntity(postParams, finalCharset);
            }
            HttpPost post = new HttpPost(url);
            if (headers != null) {
                post.setHeaders(headers);
            }
            if (entity != null) post.setEntity(entity);
            CloseableHttpResponse response = null;
            try {
                response = httpClient.execute(post);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    //Header[] headerws = response.getAllHeaders();
                    HttpEntity content = response.getEntity();
                    //System.out.println(EntityUtils.toString(context, "gbk"));
                    responseContent = EntityUtils.toByteArray(content);
                    //释放实体
                    EntityUtils.consume(content);
                }
                //关闭连接
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseContent;
    }

}
