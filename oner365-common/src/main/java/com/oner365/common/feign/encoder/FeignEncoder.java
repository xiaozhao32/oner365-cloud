package com.oner365.common.feign.encoder;

import java.lang.reflect.Type;
import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import feign.RequestTemplate;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

/**
 * Feign调用编码解析
 * @author zhaoyong
 */
public class FeignEncoder extends SpringFormEncoder {

    public FeignEncoder() {
        super(new Encoder.Default());
    }

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) {
        if (object != null) {
            Collection<String> collection = template.headers().get(HttpHeaders.CONTENT_TYPE);
            if (collection == null) {
                template.body(JSON.toJSONString(object, SerializerFeature.WriteDateUseDateFormat,
                        SerializerFeature.WriteNullListAsEmpty));
            } else if (collection.contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
                super.encode(object, bodyType, template);
            } else {
                template.body(JSON.toJSONString(object, SerializerFeature.WriteDateUseDateFormat,
                        SerializerFeature.WriteNullListAsEmpty));
            }
        }
    }
}
