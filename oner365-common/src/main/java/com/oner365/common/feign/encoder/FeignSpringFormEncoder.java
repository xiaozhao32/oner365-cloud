package com.oner365.common.feign.encoder;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import feign.RequestTemplate;
import feign.codec.Encoder;
import feign.form.ContentType;
import feign.form.FormEncoder;
import feign.form.MultipartFormContentProcessor;
import feign.form.spring.SpringManyMultipartFilesWriter;
import feign.form.spring.SpringSingleMultipartFileWriter;

/**
 * Feign调用解析
 * @author zhaoyong
 */
public class FeignSpringFormEncoder extends FormEncoder {
    /**
     * Constructor with the default Feign encoder as a delegate.
     */
    public FeignSpringFormEncoder() {
        this(new Default());
    }

    /**
     * Constructor with specified delegate encoder.
     *
     * @param delegate
     *            delegate encoder, if this encoder couldn't encode object.
     */
    public FeignSpringFormEncoder(Encoder delegate) {
        super(delegate);

        MultipartFormContentProcessor processor = (MultipartFormContentProcessor) getContentProcessor(
                ContentType.MULTIPART);
        processor.addWriter(new SpringSingleMultipartFileWriter());
        processor.addWriter(new SpringManyMultipartFilesWriter());
    }

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) {
    	 if (bodyType.equals(MultipartFile.class)) {
            MultipartFile file = (MultipartFile) object;
            Map<String, Object> data = Collections.singletonMap(file.getName(), object);
            super.encode(data, MAP_STRING_WILDCARD, template);
            return;
        } else if (bodyType.equals(MultipartFile[].class)) {
            MultipartFile[] file = (MultipartFile[]) object;
            if(file != null) {
                Map<String, Object> data = Collections.singletonMap("file", object);
                super.encode(data, MAP_STRING_WILDCARD, template);
                return;
            }
        } else if (bodyType.equals(File[].class)) {
            File[] file = (File[]) object;
            if (file != null) {
                Map<String, Object> data = Collections.singletonMap("file", object);
                super.encode(data, MAP_STRING_WILDCARD, template);
                return;
            }
        } else if (bodyType.equals(File.class)) {
            File file = (File) object;
            if (file != null) {
                Map<String, Object> data = Collections.singletonMap("file", object);
                super.encode(data, MAP_STRING_WILDCARD, template);
                return;
            }
        }
        super.encode(object, bodyType, template);
    }

}
