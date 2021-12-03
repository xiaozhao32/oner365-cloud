package com.oner365.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;


/***
 * HttpClient工具类
 *
 * @author liutao
 */
public class HttpClientUtils {

  // 将最大连接数增加到
  private static final int MAX_TOTAL = 60;
  // 将每个路由基础的连接增加到
  private static final int MAX_ROUTE_TOTAL = 30;

  //设置超时时间
  private static final int REQUEST_TIMEOUT = 60 * 1000;
  private static final int REQUEST_SOCKET_TIME = 30 * 1000;
  private static final String UTF_8 = "UTF-8";

  private static PoolingHttpClientConnectionManager httpClientConnectionManager;
  private static final String EMPTY_STR = "";

  private static void init() {
    if (httpClientConnectionManager == null) {
      httpClientConnectionManager = new PoolingHttpClientConnectionManager();
      httpClientConnectionManager.setMaxTotal(MAX_TOTAL);// 整个连接池最大连接数
      httpClientConnectionManager.setDefaultMaxPerRoute(MAX_ROUTE_TOTAL); // 将每个路由基础的连接增加到，默认值是2
    }
  }

  private static CloseableHttpClient createSSLClientDefault() {
    try {
      // 信任所有
      SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (TrustStrategy) (chain, authType) -> true).build();
      RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(REQUEST_TIMEOUT).setSocketTimeout(REQUEST_SOCKET_TIME).build();
      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
      return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(requestConfig).build();
    } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
      e.printStackTrace();
    }
    return HttpClients.createDefault();
  }

  public static String httpsPost(String path, Map<String, String> headers, String body) throws IOException {
    URL url = new URL(path);
    HostnameVerifier ignoreHostnameVerifier = (s, sslsession) -> {
      System.out.println("WARNING: Hostname is not matched for cert.");
      return true;
    };
    HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
    HttpsURLConnection.setDefaultSSLSocketFactory(getSSLContext().getSocketFactory());
    //这边是HttpURLConnection
    OutputStream outPutStream;
    BufferedReader bufferReader;
    InputStream inputStream;
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setDoOutput(true);
    connection.setDoInput(true);
    connection.setRequestProperty("Content-Type",
            "application/octet-stream");
    headers.keySet().forEach(key -> connection.setRequestProperty(key, headers.get(key)));
    connection.setRequestMethod("POST");
    connection.setUseCaches(false);
    connection.setAllowUserInteraction(true);
    connection.setChunkedStreamingMode(body.getBytes().length);
    connection.connect();
    outPutStream = connection.getOutputStream();
    int perLength;
    inputStream = new ByteArrayInputStream(body.getBytes());
    BufferedInputStream bis = new BufferedInputStream(inputStream);
    byte[] bufferRead = new byte[1024 * 100];
    while ((perLength = bis.read(bufferRead)) != -1) {
      byte[] read = Arrays.copyOf(bufferRead, perLength);
      outPutStream.write(read);
      outPutStream.flush();
    }
    bufferReader = new BufferedReader(new InputStreamReader(
            connection.getInputStream(), StandardCharsets.UTF_8));
    String line;
    StringBuilder sb = new StringBuilder();
    while ((line = bufferReader.readLine()) != null) {
      sb.append(line);
    }
    return sb.toString();
  }

  private static SSLContext getSSLContext() {
    SSLContext sslcontext = null;
    try {
      sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
      sslcontext.init(null, new TrustManager[]{new MyX509TrustManager()}, new java.security.SecureRandom());
    } catch (NoSuchAlgorithmException | KeyManagementException | NoSuchProviderException e) {
      e.printStackTrace();
    }
    return sslcontext;
  }

  private static class MyX509TrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] arg0, String arg1) {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] arg0, String arg1) {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
      return null;
    }

  }

  /**
   * 通过连接池获取HttpClient
   *
   * @return CloseableHttpClient
   */
  private static CloseableHttpClient getHttpClient() {
    init();
    RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(REQUEST_TIMEOUT).setSocketTimeout(REQUEST_SOCKET_TIME).build();
    return HttpClients.custom().setConnectionManager(httpClientConnectionManager).setDefaultRequestConfig(requestConfig).build();
  }

  /**
   * @param url 地址
   * @return String 结果
   */
  public static String httpGetRequest(String url) {
    HttpGet httpGet = new HttpGet(url);
    return getResult(httpGet);
  }

  public static String httpGetRequest(String url, Map<String, Object> params) {
    URIBuilder ub = new URIBuilder();
    ub.setPath(url);

    ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
    ub.setParameters(pairs);

    HttpGet httpGet = null;
    try {
      httpGet = new HttpGet(ub.build());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return getResult(httpGet);
  }

  public static String httpsGetRequest(String url, Map<String, Object> headers, Map<String, Object> params) {
    URIBuilder ub = new URIBuilder();
    ub.setPath(url);

    ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
    ub.setParameters(pairs);

    HttpGet httpGet = null;
    try {
      httpGet = new HttpGet(ub.build());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    for (Map.Entry<String, Object> param : headers.entrySet()) {
      assert httpGet != null;
      httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
    }
    return getResultHttps(httpGet);
  }


  public static String httpGetRequest(String url, Map<String, Object> headers, Map<String, Object> params) {
    URIBuilder ub = new URIBuilder();
    ub.setPath(url);

    ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
    ub.setParameters(pairs);

    HttpGet httpGet = null;
    try {
      httpGet = new HttpGet(ub.build());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    for (Map.Entry<String, Object> param : headers.entrySet()) {
      assert httpGet != null;
      httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
    }
    return getResult(httpGet);
  }

  /***
   * HTTP POST
   * @param url 地址
   * @return String 结果字符串
   */
  public static String httpPostRequest(String url) {
    HttpPost httpPost = new HttpPost(url);
    return getResult(httpPost);
  }

  public static String httpPostRequest(String url, Map<String, Object> params) {
    HttpPost httpPost = new HttpPost(url);
    ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
    httpPost.setEntity(new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8));
    return getResult(httpPost);
  }

  public static String httpDeleteRequest(String url, Map<String, Object> headers, Map<String, Object> params) {
    HttpDelete httpDelete = new HttpDelete(url);
    for (Map.Entry<String, Object> param : headers.entrySet()) {
      httpDelete.addHeader(param.getKey(), String.valueOf(param.getValue()));
    }
    ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
    httpDelete.setEntity(new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8));
    return getResult(httpDelete);
  }

  public static String httpPostRequest(String url, String requestJson) {
    HttpPost httpPost = new HttpPost(url);
    httpPost.addHeader("Content-type", "application/json; charset=utf-8");
    httpPost.setHeader("Accept", "application/json");
    httpPost.setEntity(new StringEntity(requestJson, StandardCharsets.UTF_8));
    return getResult(httpPost);
  }

  public static String httpPostRequest(String url, Map<String, Object> headers, String requestJson, String chartset) {
    HttpPost httpPost = new HttpPost(url);
    for (Map.Entry<String, Object> param : headers.entrySet()) {
      httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
    }
    httpPost.setEntity(new StringEntity(requestJson, Charset.forName(chartset.toUpperCase())));
    return getResult(httpPost);
  }

  /***
   * Http Post请求
   * @param url 地址
   * @param params 请求参数
   * @param chartset 字符编写,缺省为UTF-8
   * @return String 结果字符串
   */
  public static String httpPostRequest(String url, Map<String, Object> params, String chartset) {
    HttpPost httpPost = new HttpPost(url);
    ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
    try {
      httpPost.setEntity(new UrlEncodedFormEntity(pairs, chartset));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return getResult(httpPost);
  }

  public static String httpPostRequest(String url, Map<String, Object> headers, Map<String, Object> params) {
    HttpPost httpPost = new HttpPost(url);

    for (Map.Entry<String, Object> param : headers.entrySet()) {
      httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
    }

    ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
    httpPost.setEntity(new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8));
    return getResult(httpPost);
  }

  public static String httpPostRequest(String url, Map<String, Object> headers, Map<String, Object> params, String chartset) {
    HttpPost httpPost = new HttpPost(url);

    for (Map.Entry<String, Object> param : headers.entrySet()) {
      httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
    }

    ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
    try {
      httpPost.setEntity(new UrlEncodedFormEntity(pairs, chartset));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return getResult(httpPost);
  }



  public static String httpsPostRequest(String url, Map<String, Object> headers, Map<String, Object> params) {
    HttpClient httpClient;
    HttpPost httpPost;
    String result = null;
    try {
      httpClient = createSSLClientDefault();
      httpPost = new HttpPost(url);

      for (Map.Entry<String, Object> param : headers.entrySet()) {
        httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
      }

      //设置参数
      ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
      httpPost.setEntity(new UrlEncodedFormEntity(pairs, HttpClientUtils.UTF_8));
      HttpResponse response = httpClient.execute(httpPost);
      if (response != null) {
        HttpEntity resEntity = response.getEntity();
        if (resEntity != null) {
          result = EntityUtils.toString(resEntity, HttpClientUtils.UTF_8);
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return result;
  }

  public static String httpsPostRequest(String url, Map<String, Object> params) {
    HttpClient httpClient;
    HttpPost httpPost;
    String result = null;
    try {
      httpClient = createSSLClientDefault();
      httpPost = new HttpPost(url);

      //设置参数
      ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
      httpPost.setEntity(new UrlEncodedFormEntity(pairs, HttpClientUtils.UTF_8));
      HttpResponse response = httpClient.execute(httpPost);
      if (response != null) {
        HttpEntity resEntity = response.getEntity();
        if (resEntity != null) {
          result = EntityUtils.toString(resEntity, HttpClientUtils.UTF_8);
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return result;
  }

  public static String httpsPostRequest(String url, Map<String, Object> headers, String requestJson,
                                        String chartset) {
    String result = null;
    HttpClient httpClient;
    HttpPost httpPost;
    try {
      httpClient = createSSLClientDefault();
      httpPost = new HttpPost(url);

      for (Map.Entry<String, Object> param : headers.entrySet()) {
        httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
      }
      httpPost.setEntity(new StringEntity(requestJson, Charset.forName(chartset.toUpperCase())));
      HttpResponse response = httpClient.execute(httpPost);
      if (response != null) {
        HttpEntity resEntity = response.getEntity();
        if (resEntity != null) {
          result = EntityUtils.toString(resEntity, chartset);
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return result;
  }

  private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
    ArrayList<NameValuePair> pairs = new ArrayList<>();
    for (Map.Entry<String, Object> param : params.entrySet()) {
      pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
    }
    return pairs;
  }

  /**
   * 处理Http请求
   *
   * @param request 请求参数
   * @return respContent 结果字符串
   */
  private static String getResult(HttpRequestBase request) {
    // CloseableHttpClient httpClient = HttpClients.createDefault();
    CloseableHttpClient httpClient = getHttpClient();
    try {
      CloseableHttpResponse response = httpClient.execute(request);
      // response.getStatusLine().getStatusCode();
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        // long len = entity.getContentLength();// -1 表示长度未知
        //String result = EntityUtils.toString(entity);
        String respContent = EntityUtils.toString(entity, "UTF-8");
        response.close();
        // httpClient.close();
        return respContent;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return EMPTY_STR;
  }


  /**
   * 处理Http请求
   *
   * @param request 请求参数
   * @return respContent 结果字符串
   */
  private static String getResultHttps(HttpRequestBase request) {
    // CloseableHttpClient httpClient = HttpClients.createDefault();
    CloseableHttpClient httpClient ;
    try {
      httpClient = createSSLClientDefault();
      CloseableHttpResponse response = httpClient.execute(request);
      // response.getStatusLine().getStatusCode();
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        // long len = entity.getContentLength();// -1 表示长度未知
        //String result = EntityUtils.toString(entity);
        String respContent = EntityUtils.toString(entity, "UTF-8");
        response.close();
        // httpClient.close();
        return respContent;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return EMPTY_STR;
  }

  public static String httpsGetRequest(String url, Map<String, Object> headers) {
    HttpClient httpClient;
    HttpGet httpGet;
    String result = null;
    try {
      httpClient = createSSLClientDefault();
      httpGet = new HttpGet(url);

      for (Map.Entry<String, Object> param : headers.entrySet()) {
        httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
      }

      //设置参数
      HttpResponse response = httpClient.execute(httpGet);
      if (response != null) {
        HttpEntity resEntity = response.getEntity();
        if (resEntity != null) {
          result = EntityUtils.toString(resEntity, "UTF-8");
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return result;
  }


}
