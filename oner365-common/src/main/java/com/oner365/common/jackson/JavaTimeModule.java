package com.oner365.common.jackson;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.oner365.util.DataUtils;
import com.oner365.util.DateUtil;

/**
 * JavaTimeModule
 * 
 * @author zhaoyong
 *
 */
public class JavaTimeModule extends SimpleModule {
  private static final long serialVersionUID = 1L;

  public JavaTimeModule() {
    super(PackageVersion.VERSION);

    addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DateUtil.FULL_TIME_FORMAT)));
    addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DateUtil.FULL_DATE_FORMAT)));
    addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DateUtil.LONG_TIME_FORMAT)));
    addSerializer(Instant.class, new InstantCustomSerializer(DateTimeFormatter.ofPattern(DateUtil.FULL_TIME_FORMAT)));

    addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DateUtil.FULL_TIME_FORMAT)));
    addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DateUtil.FULL_DATE_FORMAT)));
    addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DateUtil.LONG_TIME_FORMAT)));
    addDeserializer(Instant.class, new InstantCustomDeserializer());
  }

  class InstantCustomSerializer extends JsonSerializer<Instant> {
    private DateTimeFormatter format;

    private InstantCustomSerializer(DateTimeFormatter formatter) {
      this.format = formatter;
    }

    @Override
    public void serialize(Instant instant, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
        throws IOException {
      if (instant != null) {
        String jsonValue = format.format(instant.atZone(ZoneId.systemDefault()));
        jsonGenerator.writeString(jsonValue);
      }
    }
  }

  class InstantCustomDeserializer extends JsonDeserializer<Instant> {

    @Override
    public Instant deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
      String dateString = parser.getText().trim();
      if (DataUtils.isEmpty(dateString)) {
        Date pareDate = DateUtil.stringToDate(dateString, DateUtil.FULL_TIME_FORMAT);
        if (pareDate != null) {
          return pareDate.toInstant();
        }
      }
      return null;
    }

  }
}
