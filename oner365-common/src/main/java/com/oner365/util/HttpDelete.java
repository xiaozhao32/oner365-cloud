package com.oner365.util;

import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

/**
 * HttpDelete
 *
 * @author liutao
 */
public class HttpDelete extends HttpEntityEnclosingRequestBase {

  public static final String METHOD_NAME = "DELETE";

  @Override
  public String getMethod() {
    return METHOD_NAME;
  }

  public HttpDelete(final String uri) {
    super();
    setURI(URI.create(uri));
  }

  public HttpDelete(final URI uri) {
    super();
    setURI(uri);
  }

  public HttpDelete() {
    super();
  }

}
