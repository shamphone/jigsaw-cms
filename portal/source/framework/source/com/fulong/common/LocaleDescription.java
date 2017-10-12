package com.fulong.common;

/**
 *
 * <p>Title: WebMaster Core Library</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD</p>
 *
 * @author Lixf
 * @version 1.0
 */
public class LocaleDescription {
  private String language;
  private String value;
  public LocaleDescription() {
    language = null;
    value = null;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getLanguage() {
    return language;
  }

  public String getValue() {
    return value;
  }
  public String toString(){
    StringBuffer buffer=new StringBuffer();
    buffer.append(this.language+"="+value);
    return buffer.toString();
  }
}
