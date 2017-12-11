package com.wb.httpclientutil.util;


import com.wb.httpclientutil.client.DefaultClientGetter;
import com.wb.httpclientutil.client.HttpsClientGetter;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Created with IntelliJ IDEA.
 * Description: 获取默认HttpClient实例
 * User: future
 * Date: 2017-12-11
 * Time: 11:16
 */
public class HttpClientFactory {

    /**
     * 使用默认的httpclient,不使用代理
     */
    public static CloseableHttpClient getHttpClient () {
        return new DefaultClientGetter().getClient();
    }

    /**
     * 使用默认的httpclient,使用代理
     */
    public static CloseableHttpClient getHttpClient (HttpHost proxy) {
        return new DefaultClientGetter(proxy).getClient();
    }

    /**
     * 使用默认的HttpsClient
     */
    public static CloseableHttpClient getHttpsClient () {
        return new HttpsClientGetter().getHttpsClient();
    }

    /**
     * 使用默认的HttpsClient,使用代理
     */
    public static CloseableHttpClient getHttpsClient (HttpHost proxy) {
        return new HttpsClientGetter(proxy).getHttpsClient();
    }
}
