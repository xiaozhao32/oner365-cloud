package com.oner365.data.web.utils;

import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.springframework.http.HttpMethod;

/**
 * HttpDelete
 *
 * @author liutao
 */
public class HttpDelete extends HttpEntityEnclosingRequestBase {

  @Override
  public String getMethod() {
    return HttpMethod.DELETE.name();
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
