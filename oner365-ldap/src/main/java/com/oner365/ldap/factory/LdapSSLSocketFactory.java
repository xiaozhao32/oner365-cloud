package com.oner365.ldap.factory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ldap Factory
 *
 * @author zhaoyong
 */
public class LdapSSLSocketFactory extends SSLSocketFactory {

    private static final Logger logger = LoggerFactory.getLogger(LdapSSLSocketFactory.class);

    private SSLSocketFactory factory;

    public LdapSSLSocketFactory() {
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[] { new LdapTrustManager() }, new java.security.SecureRandom());
            factory = sslcontext.getSocketFactory();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public static SocketFactory getDefault() {
        return new LdapSSLSocketFactory();
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return factory.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return factory.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket(Socket socket, String string, int num, boolean bool) throws IOException {
        return factory.createSocket(socket, string, num, bool);
    }

    @Override
    public Socket createSocket(String string, int num) throws IOException {
        return factory.createSocket(string, num);
    }

    @Override
    public Socket createSocket(String string, int num, InetAddress netAdd, int i) throws IOException {
        return factory.createSocket(string, num, netAdd, i);
    }

    @Override
    public Socket createSocket(InetAddress netAdd, int num) throws IOException {
        return factory.createSocket(netAdd, num);
    }

    @Override
    public Socket createSocket(InetAddress netAdd1, int num, InetAddress netAdd2, int i) throws IOException {
        return factory.createSocket(netAdd1, num, netAdd2, i);
    }

}
