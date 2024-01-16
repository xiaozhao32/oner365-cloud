package com.oner365.data.commons.util;

import java.net.URI;
import java.net.URL;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Optional;

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
  
  private SslUtils() {
  }
  
  /**
   * 通过url获取https域名的证书publicKey
   *
   * @param sslUrl 域名地址url https://xxx.xxx.com
   * @return String
   */
  public static String getSslPublicKey(String sslUrl) {
    HttpsURLConnection connection = null;
    try {
      URL url = new URI(sslUrl).toURL();
      connection = (HttpsURLConnection) url.openConnection();
      connection.connect();
      Optional<Certificate> optional = Arrays.stream(connection.getServerCertificates()).findFirst();
      if(optional.isPresent()) {
        Certificate certificate = optional.get();
        X509Certificate x509Certificate = (X509Certificate) certificate;
        PublicKey publicKey = x509Certificate.getPublicKey();
        return Base64Utils.encodeBase64String(publicKey.getEncoded());
      }
    } catch (Exception e) {
      LOGGER.error("getSSLPublicKey error:",e);
    } finally {
      if(connection != null) {
        connection.disconnect();
      }
    }
    return null;
  }

}
