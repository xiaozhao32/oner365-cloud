package com.oner365.data.commons.adapter;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.commons.util.DateUtil;

/**
 * Timestamp适配器
 *
 * @author zhaoyong
 */
public class TimestampTypeAdapter implements JsonSerializer<Timestamp>, JsonDeserializer<Timestamp> {

    private static final ThreadLocal<SimpleDateFormat> SIMPLE_DATE_FORMAT = ThreadLocal
            .withInitial(() -> new SimpleDateFormat(DateUtil.FULL_TIME_FORMAT));

    /**
     * remove method
     */
    public static void remove() {
        SIMPLE_DATE_FORMAT.remove();
    }

    @Override
    public JsonElement serialize(Timestamp src, Type arg1, JsonSerializationContext arg2) {
        String dateFormatAsString = SIMPLE_DATE_FORMAT.get().format(new Date(src.getTime()));
        return new JsonPrimitive(dateFormatAsString);
    }

    @Override
    public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        if (!(json instanceof JsonPrimitive)) {
            throw new JsonParseException("The date should be a string value");
        }
        try {
            if (DataUtils.isEmpty(json.getAsString())) {
                return null;
            }
            else {
                Date date = SIMPLE_DATE_FORMAT.get().parse(json.getAsString());
                return new Timestamp(date.getTime());
            }
        }
        catch (ParseException e) {
            throw new JsonParseException(e);
        }
    }

}
