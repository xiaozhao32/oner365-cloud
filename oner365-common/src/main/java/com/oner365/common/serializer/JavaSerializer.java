package com.oner365.common.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.hibernate.cache.CacheException;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 标准的 Java 序列化
 * @author zhaoyong
 */
public class JavaSerializer<T> implements RedisSerializer<T> {

    @SuppressWarnings("unused")
    private final Class<T> clazz;

    public JavaSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T obj) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(obj);
            return os.toByteArray();
        } catch (IOException e) {
            throw new CacheException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T deserialize(byte[] bytes) {
        if (bytes != null && bytes.length != 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
                return (T)ois.readObject();
            } catch (ClassNotFoundException | IOException e) {
                throw new CacheException(e);
            }
        }
        return null;
    }

}
