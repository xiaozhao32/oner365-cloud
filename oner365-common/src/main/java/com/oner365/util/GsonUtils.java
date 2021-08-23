package com.oner365.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import com.oner365.common.adapter.LocalDateTimeTypeAdapter;
import com.oner365.common.adapter.TimestampTypeAdapter;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

/**
 * Gson工具类
 * @author zhaoyong
 */
public class GsonUtils {

    private GsonUtils() {

    }

    protected static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter()).setDateFormat(DateUtil.FULL_TIME_FORMAT)
            .registerTypeAdapter(Date.class, new DateTypeAdapter()).setDateFormat(DateUtil.FULL_DATE_FORMAT)
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter()).setDateFormat(DateUtil.FULL_TIME_FORMAT)
            .create();

    /***
     * 把对象转化成JSON
     *
     * @param obj 对象
     * @return String
     */
    public static String objectToJson(Object obj) {
        return GSON.toJson(obj);
    }

    /***
     * JSON转对象类型
     *
     * @param json json字符串
     * @param clazz 类
     * @return T
     */
    public static <T> T jsonToBean(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    /***
     * JSON转对象类型
     *
     * @param json json字符串
     * @param type new TypeToken<List<T>>() {}
     * @return T
     */
    public static <T> T jsonToBean(String json, TypeToken<T> type) {
        return GSON.fromJson(json, type.getType());
    }

}
