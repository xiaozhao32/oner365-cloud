package com.oner365.sys.dto;

import java.io.Serializable;

/**
 * 获取验证码
 *
 * @author zhaoyong
 *
 */
public class CaptchaImageDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    private String uuid;

    /**
     * img base64
     */
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
