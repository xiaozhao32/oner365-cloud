package com.oner365.common.adapter;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * LocalDateTime适配器
 * 
 * @author zhaoyong
 */
public class LocalDateTimeTypeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

  private static final ThreadLocal<DateFormat> LOCAL = new ThreadLocal<>();

  /**
   * get method
   *
   * @return DateFormat
   */
  public static DateFormat getDateFormat() {
    if (LOCAL.get() == null) {
      LOCAL.set(new SimpleDateFormat(DateUtil.FULL_TIME_FORMAT));
    }
    return LOCAL.get();
  }

  /**
   * remove method
   */
  public static void remove() {
    LOCAL.remove();
  }

  @Override
  public JsonElement serialize(LocalDateTime src, Type arg1, JsonSerializationContext arg2) {
    String dateFormatAsString = getDateFormat().format(new Date(DateUtil.localDateTimeToDate(src).getTime()));
    return new JsonPrimitive(dateFormatAsString);
  }

  @Override
  public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
    if (!(json instanceof JsonPrimitive)) {
      throw new JsonParseException("The localDateTime should be a string value");
    }
    try {
      if (DataUtils.isEmpty(json.getAsString())) {
        return null;
      } else {
        Date date = getDateFormat().parse(json.getAsString());
        return DateUtil.dateToLocalDateTime(date);
      }
    } catch (ParseException e) {
      throw new JsonParseException(e);
    }
  }

}
