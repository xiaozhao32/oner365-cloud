package com.oner365.ldap.factory;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

import org.apache.http.ssl.TrustStrategy;

/**
 * Ldap Trust
 * 
 * @author zhaoyong
 */
public class LdapTrustManager implements X509TrustManager {

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
