package com.oner365.ldap.factory;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * Ldap Trust
 * 
 * @author zhaoyong
 */
public class LdapTrustManager implements X509TrustManager {
  
  public void checkClientTrusted(X509Certificate[] cert, String authType) {
  }

  public void checkServerTrusted(X509Certificate[] cert, String authType) {
  }

  public X509Certificate[] getAcceptedIssuers() {
    return new X509Certificate[0];
  }
  
}
