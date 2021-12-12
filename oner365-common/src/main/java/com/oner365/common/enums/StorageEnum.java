package com.oner365.common.enums;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

/**
 * 枚举 - 文件存储方式
 *
 * @author zhaoyong
 */
public enum StorageEnum implements Serializable {

    /** 本地上传 */
    LOCAL("local", "本地上传"),
    /** Minio上传 */
    MINIO("minio", "Minio上传"),
    /** Fastdfs上传 */
    FDFS("fdfs", "Fastdfs上传");

    /**
     * 编码
     */
    private final String code;

    /**
     * 名称
     */
    private final String name;

    /**
     * 构造方法
     *
     * @param code  编码
     * @param name 名称
     */
    StorageEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * get code
     *
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * get name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 获取枚举
     *
     * @param code 编码
     * @return StatusEnum
     */
    public static StorageEnum getCode(String code) {
        Optional<StorageEnum> result = Arrays.stream(StorageEnum.values())
                .filter(e -> e.getCode().equals(code))
                .findFirst();
        return result.orElse(null);
    }

}
