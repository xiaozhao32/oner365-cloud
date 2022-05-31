package com.oner365.util;

import java.net.URL;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ssl工具类
 *
 * @author liutao
 */
public class SslUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(SslUtils.class);
  
  /**
   * 通过url获取https域名的证书publicKey
   *
   * @param sslUrl 域名地址url https://xxx.xxx.com
   * @return String
   */
  public static String getSSLPublicKey(String sslUrl) {
    HttpsURLConnection connection = null;
    try {
      URL url = new URL(sslUrl);
      connection = (HttpsURLConnection) url.openConnection();
      connection.connect();
      Certificate certificate = Arrays.asList(connection.getServerCertificates()).stream().findFirst().get();
      X509Certificate x509Certificate = (X509Certificate) certificate;
      PublicKey publicKey = x509Certificate.getPublicKey();
      connection.disconnect();
      return new String(Base64.getEncoder().encode(publicKey.getEncoded()));
    } catch (Exception e) {
      LOGGER.error("getSSLPublicKey error:{}",e);
    } finally {
      connection.disconnect();
    }
    return null;
  }
  

}
