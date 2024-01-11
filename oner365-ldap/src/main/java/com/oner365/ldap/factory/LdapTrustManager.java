package com.oner365.ldap.factory;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * Ldap Trust
 * 
 * @author zhaoyong
 */
public class LdapTrustManager implements X509TrustManager {
  
  @Override
  public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
    // checkClientTrusted
  }

  @Override
  public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
    // checkServerTrusted
  }

  public X509Certificate[] getAcceptedIssuers() {
    return new X509Certificate[0];
  }
  
}
