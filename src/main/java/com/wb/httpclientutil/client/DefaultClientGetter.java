package com.wb.httpclientutil.client;

import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Created with IntelliJ IDEA.
 * Description: 默认的简单配置的HttpClient
 * User: future
 * Date: 2017-12-11
 * Time: 10:50
 */
public class DefaultClientGetter {

    private CloseableHttpClient client;
    private static final String USER_AGENT=
            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Foxy/1; .NET CLR 2.0.50727; MEGAUPLOAD 1.0)";
    private static final int POOL_SIZE=200;
    private static final int MAX_REDIRECTS=10;
    private RequestConfig config;
    private CookieStore cookieStore;

    public DefaultClientGetter(){
        cookieStore = new BasicCookieStore();
        RequestConfig config=RequestConfig.custom().setMaxRedirects(MAX_REDIRECTS)
                .setCookieSpec(CookieSpecs.STANDARD)
                .setCircularRedirectsAllowed(false)
                .setConnectTimeout(300000)
                .setSocketTimeout(300000)
                .build();
    }

    /**
     * 设置代理主机
     * @param proxy 代理
     */
    public DefaultClientGetter(HttpHost proxy){
        cookieStore = new BasicCookieStore();
        RequestConfig config=RequestConfig.custom().setMaxRedirects(MAX_REDIRECTS)
                .setCookieSpec(CookieSpecs.STANDARD)
                .setCircularRedirectsAllowed(false)
                .setConnectTimeout(300000)
                .setSocketTimeout(300000)
                .setProxy(proxy)
                .build();
    }

    public  CloseableHttpClient getClient(){
        client = HttpClients.custom().setDefaultCookieStore(cookieStore).setUserAgent(USER_AGENT).setMaxConnTotal(POOL_SIZE)
                .setMaxConnPerRoute(POOL_SIZE).setDefaultRequestConfig(config).build();
        return client;
    }

}
