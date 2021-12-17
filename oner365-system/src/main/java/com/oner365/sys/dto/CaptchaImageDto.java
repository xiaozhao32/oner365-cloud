package com.oner365.sys.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 获取验证码
 * 
 * @author zhaoyong
 *
 */
@ApiModel(value = "验证码")
public class CaptchaImageDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * uuid
   */
  @ApiModelProperty(value = "验证码标识")
  private String uuid;
  
  /**
   * img base64
   */
  @ApiModelProperty(value = "验证码图片")
  private String img;
  
  public CaptchaImageDto() {
    super();
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }
}
