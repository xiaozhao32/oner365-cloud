package com.oner365.data.commons.enums;

/**
 * 枚举 - 文件存储方式
 *
 * @author zhaoyong
 */
public enum StorageEnum implements BaseEnum {

    /** 本地上传 */
    LOCAL("本地上传"),
    /** Minio上传 */
    MINIO("Minio上传"),
    /** Fdfs上传 */
    FDFS("Fdfs上传");

    /**
     * 名称
     */
    private final String name;

    /**
     * 构造方法
     * @param name 名称
     */
    StorageEnum(String name) {
        this.name = name;
    }

    /**
     * get name
     * @return name
     */
    @Override
    public String getName() {
        return name;
    }

}
