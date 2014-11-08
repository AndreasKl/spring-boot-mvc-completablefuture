package net.andreaskluth;

import java.io.Serializable;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebContext {

  private HttpServletRequest nativeRequest;
  @SuppressWarnings("unused")
  private HttpServletResponse nativeResponse;

  public WebContext(HttpServletRequest nativeRequest, HttpServletResponse nativeResponse) {
    this.nativeRequest = Objects.requireNonNull(nativeRequest);
    this.nativeResponse = Objects.requireNonNull(nativeResponse);
  }

  public <TValue extends Serializable> void addToSession(String key, TValue value) {
    nativeRequest.getSession(true).setAttribute(key, value);
  }
}
