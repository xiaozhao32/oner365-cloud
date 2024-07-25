package com.oner365.data.web.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oner365.data.commons.constants.PublicConstants;

/***
 * HttpClient工具类
 *
 * @author liutao
 */
public class HttpClientUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);

  /**
   * 将最大连接数增加到
   */
  public static final int MAX_TOTAL = 60;

  /**
   * 将每个路由基础的连接增加到
   */
  public static final int MAX_ROUTE_TOTAL = 30;

  /**
   * 设置超时时间
   */
  public static final int REQUEST_TIMEOUT = 60 * 1000;
  public static final int REQUEST_SOCKET_TIME = 30 * 1000;
  public static final String C_QUARTER = ",";
  public static final String URI_PARAM_SPLIT = "&";
  public static final String URI_PARAM = "=";
  
  private static final int BUFFER_SIZE = PublicConstants.BYTE_SIZE * 8;
  private static final String HEADER_UNKNOWN = "unknown";
  private static final int IP_LENGTH = 15;
  private static final int IP_PART = 4;
  private static final String IP_LOCALHOST = "0:0:0:0:0:0:0:1";

  private static PoolingHttpClientConnectionManager httpClientConnectionManager;

  private HttpClientUtils() {

  }

  private static void init() {
    if (httpClientConnectionManager == null) {
      httpClientConnectionManager = new PoolingHttpClientConnectionManager();
      // 整个连接池最大连接数
      httpClientConnectionManager.setMaxTotal(MAX_TOTAL);
      // 将每个路由基础的连接增加到，默认值是2
      httpClientConnectionManager.setDefaultMaxPerRoute(MAX_ROUTE_TOTAL);
    }
  }

  public static CloseableHttpClient createSslClientDefault() {
    try {
      // 信任所有
      SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
      RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(REQUEST_TIMEOUT)
          .setSocketTimeout(REQUEST_SOCKET_TIME).build();
      SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
      return HttpClients.custom().setSSLSocketFactory(socketFactory).setDefaultRequestConfig(requestConfig).build();
    } catch (KeyManagementException e) {
      LOGGER.error("KeyManagementException", e);
    } catch (NoSuchAlgorithmException e) {
      LOGGER.error("NoSuchAlgorithmException", e);
    } catch (KeyStoreException e) {
      LOGGER.error("KeyStoreException", e);
    }
    return HttpClients.createDefault();
  }

  public static String httpsPost(String path, Map<String, String> headers, String body)
      throws IOException, KeyManagementException, NoSuchAlgorithmException {
    URL url = URI.create(path).toURL();
    HostnameVerifier ignoreHostnameVerifier = (s, sslSession) -> {
      LOGGER.warn("WARNING: Hostname is not matched for cert.");
      return true;
    };
    HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
    HttpsURLConnection.setDefaultSSLSocketFactory(getSslContext().getSocketFactory());
    // 这边是HttpURLConnection
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setDoOutput(true);
    connection.setDoInput(true);
    connection.setRequestProperty(HTTP.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
    headers.keySet().forEach(key -> connection.setRequestProperty(key, headers.get(key)));
    connection.setRequestMethod(HttpMethod.POST.name());
    connection.setUseCaches(false);
    connection.setAllowUserInteraction(true);
    connection.setChunkedStreamingMode(body.getBytes().length);
    connection.connect();
    try (OutputStream outPutStream = connection.getOutputStream();
        InputStream inputStream = new ByteArrayInputStream(body.getBytes());
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        InputStream connectionStream = connection.getInputStream();
        BufferedReader bufferReader = new BufferedReader(
            new InputStreamReader(connectionStream, Charset.defaultCharset()))) {
      byte[] bufferRead = new byte[PublicConstants.BYTE_SIZE * 100];
      int perLength;
      while ((perLength = bis.read(bufferRead)) != -1) {
        byte[] read = Arrays.copyOf(bufferRead, perLength);
        outPutStream.write(read);
        outPutStream.flush();
      }

      String line;
      StringBuilder sb = new StringBuilder();
      while ((line = bufferReader.readLine()) != null) {
        sb.append(line);
      }
      connection.disconnect();
      return sb.toString();
    } catch (Exception e) {
      LOGGER.error("httpsPost error:", e);
    }
    return null;
  }

  private static SSLContext getSslContext() throws NoSuchAlgorithmException, KeyManagementException {
    SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
    sslContext.init(null, new TrustManager[] { new MyX509TrustManager() }, new java.security.SecureRandom());
    return sslContext;
  }

  public static class MyX509TrustManager implements X509TrustManager {

    private X509TrustManager trustManager;

    private TrustStrategy trustStrategy;

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
      this.trustManager.checkClientTrusted(chain, authType);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
      if (!this.trustStrategy.isTrusted(chain, authType)) {
        this.trustManager.checkServerTrusted(chain, authType);
      }
    }

    public X509Certificate[] getAcceptedIssuers() {
      return this.trustManager.getAcceptedIssuers();
    }

  }

  /**
   * 通过连接池获取HttpClient
   *
   * @return CloseableHttpClient
   */
  private static CloseableHttpClient getHttpClient() {
    init();
    RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(REQUEST_TIMEOUT)
        .setSocketTimeout(REQUEST_SOCKET_TIME).build();
    return HttpClients.custom().setConnectionManager(httpClientConnectionManager).setDefaultRequestConfig(requestConfig)
        .build();
  }

  /**
   * GET Request
   *
   * @param url 请求地址
   * @return String
   */
  public static String httpGetRequest(String url) {
    HttpGet httpGet = new HttpGet(url);
    return getResult(httpGet);
  }

  public static String httpGetRequest(String url, Map<String, Object> params) {
    URIBuilder ub = new URIBuilder();
    ub.setPath(url);

    ArrayList<NameValuePair> pairs = covertParams2NameValuePairs(params);
    ub.setParameters(pairs);

    HttpGet httpGet = null;
    try {
      httpGet = new HttpGet(ub.build());
    } catch (URISyntaxException e) {
      LOGGER.error("httpGetRequest error", e);
    }
    return getResult(httpGet);
  }

  public static String httpsGetRequest(String url, Map<String, Object> headers, Map<String, Object> params) {
    URIBuilder ub = new URIBuilder();
    ub.setPath(url);

    ArrayList<NameValuePair> pairs = covertParams2NameValuePairs(params);
    ub.setParameters(pairs);

    HttpGet httpGet = null;
    try {
      httpGet = new HttpGet(ub.build());
      for (Map.Entry<String, Object> param : headers.entrySet()) {
        httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
      }
    } catch (URISyntaxException e) {
      LOGGER.error("httpsGetRequest error", e);
    }
    return getResultHttps(httpGet);
  }

  public static String httpGetRequest(String url, Map<String, Object> headers, Map<String, Object> params) {
    URIBuilder ub = new URIBuilder();
    ub.setPath(url);

    ArrayList<NameValuePair> pairs = covertParams2NameValuePairs(params);
    ub.setParameters(pairs);

    HttpGet httpGet = null;
    try {
      httpGet = new HttpGet(ub.build());
      for (Map.Entry<String, Object> param : headers.entrySet()) {
        httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
      }
    } catch (URISyntaxException e) {
      LOGGER.error("httpGetRequest exception", e);
    }
    return getResult(httpGet);
  }

  /***
   * HTTP POST
   *
   * @param url 请求地址
   * @return String
   */
  public static String httpPostRequest(String url) {
    HttpPost httpPost = new HttpPost(url);
    return getResult(httpPost);
  }

  public static String httpPostRequest(String url, Map<String, Object> params) {
    HttpPost httpPost = new HttpPost(url);
    ArrayList<NameValuePair> pairs = covertParams2NameValuePairs(params);
    httpPost.setEntity(new UrlEncodedFormEntity(pairs, Charset.defaultCharset()));
    return getResult(httpPost);
  }

  public static String httpDeleteRequest(String url, Map<String, Object> headers, Map<String, Object> params) {
    HttpDelete httpDelete = new HttpDelete(url);
    for (Map.Entry<String, Object> param : headers.entrySet()) {
      httpDelete.addHeader(param.getKey(), String.valueOf(param.getValue()));
    }
    ArrayList<NameValuePair> pairs = covertParams2NameValuePairs(params);
    httpDelete.setEntity(new UrlEncodedFormEntity(pairs, Charset.defaultCharset()));
    return getResult(httpDelete);
  }

  public static String httpPostRequest(String url, String requestJson) {
    HttpPost httpPost = new HttpPost(url);
    httpPost.addHeader(HTTP.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    httpPost.setHeader("Accept", MediaType.APPLICATION_JSON_VALUE);
    httpPost.setEntity(new StringEntity(requestJson, Charset.defaultCharset()));
    return getResult(httpPost);
  }

  public static String httpPostRequest(String url, Map<String, Object> headers, String requestJson, String charset) {
    HttpPost httpPost = new HttpPost(url);
    for (Map.Entry<String, Object> param : headers.entrySet()) {
      httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
    }
    httpPost.setEntity(new StringEntity(requestJson, Charset.forName(charset.toUpperCase())));
    return getResult(httpPost);
  }

  /***
   * Http Post请求
   *
   * @param url     请求地址
   * @param params  请求参数
   * @param charset 字符编写,缺省为UTF-8
   * @return String
   */
  public static String httpPostRequest(String url, Map<String, Object> params, String charset) {
    HttpPost httpPost = new HttpPost(url);
    ArrayList<NameValuePair> pairs = covertParams2NameValuePairs(params);
    try {
      httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
    } catch (UnsupportedEncodingException e) {
      LOGGER.error("UnsupportedEncodingException", e);
    }
    return getResult(httpPost);
  }

  public static String httpPostRequest(String url, Map<String, Object> headers, Map<String, Object> params) {
    HttpPost httpPost = new HttpPost(url);

    for (Map.Entry<String, Object> param : headers.entrySet()) {
      httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
    }

    ArrayList<NameValuePair> pairs = covertParams2NameValuePairs(params);
    httpPost.setEntity(new UrlEncodedFormEntity(pairs, Charset.defaultCharset()));
    return getResult(httpPost);
  }

  public static String httpPostRequest(String url, Map<String, Object> headers, Map<String, Object> params,
      String charset) {
    HttpPost httpPost = new HttpPost(url);

    for (Map.Entry<String, Object> param : headers.entrySet()) {
      httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
    }

    ArrayList<NameValuePair> pairs = covertParams2NameValuePairs(params);
    try {
      httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
    } catch (UnsupportedEncodingException e) {
      LOGGER.error("UnsupportedEncodingException", e);
    }
    return getResult(httpPost);
  }

  /***
   * HTTPS POST
   *
   * @param url    请求地址
   * @param params 参数
   * @return String
   */
  public static String httpsPostRequest(String url, Map<String, Object> params) {
    return httpsPostRequest(url, params, Charset.defaultCharset().name());
  }

  public static String httpsPostRequest(String url, Map<String, Object> headers, Map<String, Object> params) {
    return httpsPostRequest(url, headers, params, Charset.defaultCharset().name());
  }

  public static String httpsPostRequest(String url, Map<String, Object> headers, Map<String, Object> params,
      String charset) {
    String result = null;
    try {
      HttpClient httpClient = createSslClientDefault();
      HttpPost httpPost = new HttpPost(url);

      for (Map.Entry<String, Object> param : headers.entrySet()) {
        httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
      }

      // 设置参数
      ArrayList<NameValuePair> pairs = covertParams2NameValuePairs(params);
      httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
      HttpResponse response = httpClient.execute(httpPost);
      if (response != null) {
        HttpEntity resEntity = response.getEntity();
        if (resEntity != null) {
          result = EntityUtils.toString(resEntity, charset);
        }
      }
    } catch (Exception e) {
      LOGGER.error("httpsPostRequest error", e);
    }
    return result;
  }

  public static String httpsPostRequest(String url, Map<String, Object> params, String charset) {
    String result = null;
    try {
      HttpClient httpClient = createSslClientDefault();
      HttpPost httpPost = new HttpPost(url);

      // 设置参数
      ArrayList<NameValuePair> pairs = covertParams2NameValuePairs(params);
      httpPost.setEntity(new UrlEncodedFormEntity(pairs, charset));
      HttpResponse response = httpClient.execute(httpPost);
      if (response != null) {
        HttpEntity resEntity = response.getEntity();
        if (resEntity != null) {
          result = EntityUtils.toString(resEntity, charset);
        }
      }
    } catch (Exception e) {
      LOGGER.error("post request error", e);
    }
    return result;
  }

  public static String httpsPostRequest(String url, Map<String, Object> headers, String requestJson, String charset) {
    String result = null;
    try {
      HttpClient httpClient = createSslClientDefault();
      HttpPost httpPost = new HttpPost(url);

      for (Map.Entry<String, Object> param : headers.entrySet()) {
        httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
      }
      httpPost.setEntity(new StringEntity(requestJson, Charset.forName(charset.toUpperCase())));
      HttpResponse response = httpClient.execute(httpPost);
      if (response != null) {
        HttpEntity resEntity = response.getEntity();
        if (resEntity != null) {
          result = EntityUtils.toString(resEntity, charset);
        }
      }
    } catch (Exception e) {
      LOGGER.error("post request error", e);
    }
    return result;
  }

  private static ArrayList<NameValuePair> covertParams2NameValuePairs(Map<String, Object> params) {
    ArrayList<NameValuePair> pairs = new ArrayList<>();
    for (Map.Entry<String, Object> param : params.entrySet()) {
      pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
    }
    return pairs;
  }

  /**
   * 处理Http请求
   *
   * @param request 请求
   * @return String
   */
  private static String getResult(HttpRequestBase request) {
    CloseableHttpClient httpClient = getHttpClient();
    try {
      CloseableHttpResponse response = httpClient.execute(request);
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        String respContent = EntityUtils.toString(entity, Charset.defaultCharset());
        response.close();
        return respContent;
      }
    } catch (ClientProtocolException e) {
      LOGGER.error("ClientProtocolException", e);
    } catch (IOException e) {
      LOGGER.error("IOException", e);
    }
    return Strings.EMPTY;
  }

  /**
   * 处理Http请求
   *
   * @param request 请求
   * @return String
   */
  private static String getResultHttps(HttpRequestBase request) {
    try {
      CloseableHttpClient httpClient = createSslClientDefault();
      CloseableHttpResponse response = httpClient.execute(request);
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        String respContent = EntityUtils.toString(entity, Charset.defaultCharset());
        response.close();
        return respContent;
      }
    } catch (ClientProtocolException e) {
      LOGGER.error("ClientProtocolException", e);
    } catch (IOException e) {
      LOGGER.error("IOException", e);
    }
    return Strings.EMPTY;
  }

  public static String httpsGetRequest(String url, Map<String, Object> headers) {
    String result = null;
    try {
      HttpClient httpClient = createSslClientDefault();
      HttpGet httpGet = new HttpGet(url);

      for (Map.Entry<String, Object> param : headers.entrySet()) {
        httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
      }

      // 设置参数
      HttpResponse response = httpClient.execute(httpGet);
      if (response != null) {
        HttpEntity resEntity = response.getEntity();
        if (resEntity != null) {
          result = EntityUtils.toString(resEntity, Charset.defaultCharset());
        }
      }
    } catch (Exception e) {
      LOGGER.error("get request error", e);
    }
    return result;
  }
  
  public static String getIpAddress(HttpServletRequest request) {
    String ip = request.getHeader("X-Real-IP");
    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("X-Forwarded-For");
    }
    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || HEADER_UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    if (IP_LOCALHOST.equals(ip)) {
      ip = getLocalhost();
    }
    if (ip != null && ip.length() > IP_LENGTH && ip.contains(C_QUARTER)) {
      ip = StringUtils.substringAfterLast(ip, C_QUARTER);
    }
    return ip;
  }
  
  /**
   * 获取本机ip
   *
   * @return String
   */
  public static String getLocalhost() {
    try {
      InetAddress inet = InetAddress.getLocalHost();
      return inet.getHostAddress();
    } catch (UnknownHostException e) {
      LOGGER.error("Error getLocalhost:", e);
    }
    return null;
  }

  /**
   * 获取本机名称
   *
   * @return String
   */
  public static String getHostName() {
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      LOGGER.error("Error getHostName:", e);
    }
    return "未知";
  }

  private static long getIp2long(String ip) {
    ip = ip.trim();
    String[] ips = ip.split("\\.");
    long ip2long = 0L;
    for (int i = 0; i < IP_PART; ++i) {
      ip2long = ip2long << 8 | Integer.parseInt(ips[i]);
    }
    return ip2long;
  }

  /**
   * 判断ip是否在段内
   *
   * @param ip        ip
   * @param ipSection ip段
   * @return boolean
   */
  public static boolean ipExistsInRange(String ip, String ipSection) {
    ipSection = ipSection.trim();
    ip = ip.trim();
    int idx = ipSection.indexOf('-');
    String beginIp = ipSection.substring(0, idx);
    String endIp = ipSection.substring(idx + 1);
    return getIp2long(beginIp) <= getIp2long(ip) && getIp2long(ip) <= getIp2long(endIp);
  }
  
  /**
   * 下载文件
   *
   * @param file     文件
   * @param fileName 文件名称
   * @return ResponseEntity
   */
  public static ResponseEntity<byte[]> download(File file, String fileName) {
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentLength(file.length());
      headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
      headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, Charset.defaultCharset().name()));
      return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
    } catch (IOException e) {
      LOGGER.error("Error download", e);
    }
    return null;
  }
  
  /**
   * File 转换成 MultipartFile
   *
   * @param file 文件
   * @return MultipartFile
   */
  public static MultipartFile convertMultipartFile(File file) {
    if (file == null) {
      return null;
    }
    FileItemFactory factory = new DiskFileItemFactory(16, null);
    FileItem item = factory.createItem(file.getName(), MediaType.TEXT_PLAIN_VALUE, true, file.getName());
    int bytesRead;
    byte[] buffer = new byte[BUFFER_SIZE];
    try (FileInputStream fis = new FileInputStream(file); OutputStream os = item.getOutputStream()) {
      while ((bytesRead = fis.read(buffer, 0, BUFFER_SIZE)) != -1) {
        os.write(buffer, 0, bytesRead);
      }
    } catch (IOException e) {
      LOGGER.error("Error convertMultipartFile:", e);
    }
    return new CommonsMultipartFile(item);
  }

  /**
   * url参数整理vo
   *
   * @param urlParam url参数字符串
   * @param clazz vo类型
   * @return T
   */
  public static <T>T getUrlParam(String urlParam,Class<T> clazz) {
    List<String> list = Arrays.stream(urlParam.split(URI_PARAM_SPLIT)).collect(Collectors.toList());
    final JSONObject json = new JSONObject();
    list.stream().forEach(s -> {
      String[] param = s.split(URI_PARAM);
      json.put(param[0], param[1]);
    });
    return JSON.toJavaObject(json, clazz);
  }
  
}
