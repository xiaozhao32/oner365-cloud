package com.oner365.data.commons.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 工具类 - 类加载器
 *
 * @author zhaoyong
 */
public class ClassesUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassesUtil.class);

    /**
     * 基本类型
     */
    private static final Class<?>[] CLASSES = { byte.class, short.class, int.class, long.class, float.class,
            double.class, boolean.class, char.class, Byte.class, Short.class, Integer.class, Long.class, Float.class,
            Double.class, Boolean.class, Character.class, BigDecimal.class, BigInteger.class, String.class,
            Enum.class };

    /**
     * 数组中基本类型
     */
    private static final Class<?>[] ARRAY_CLASSES = { byte[].class, short[].class, int[].class, long[].class,
            float[].class, double[].class, boolean[].class, char[].class, Byte[].class, Short[].class, Integer[].class,
            Long[].class, Float[].class, Double[].class, Boolean[].class, Character[].class, BigDecimal[].class,
            BigInteger[].class, String[].class, Enum[].class };

    /**
     * Generate constructor
     */
    private ClassesUtil() {
    }

    /**
     * 判断是否为原子类型
     * @param paramClass 类
     * @return boolean
     */
    public static <T> boolean isPrimitive(Class<T> paramClass) {
        return Arrays.stream(CLASSES).anyMatch(clazz -> clazz.isAssignableFrom(paramClass));
    }

    /**
     * 判断数组中是否为原子数组类型
     * @param paramClass 类
     * @return boolean
     */
    public static <T> boolean isPrimitiveArray(Class<T> paramClass) {
        return Arrays.stream(ARRAY_CLASSES).anyMatch(clazz -> clazz.isAssignableFrom(paramClass));
    }

    /**
     * 判断是否为原子和原子数组类型
     * @param clazz 类
     * @return boolean
     */
    public static <T> boolean isSpecific(Class<T> clazz) {
        return isPrimitive(clazz) || isPrimitiveArray(clazz);
    }

    /**
     * 判断是否为集合类型
     * @param clazz 类
     * @return boolean
     */
    public static boolean isCollection(Class<?> clazz) {
        boolean result = false;
        if (ClassUtils.isAssignable(Iterable.class, clazz)) {
            result = true;
        }
        return result;
    }

    /**
     * 判断是否为Map类型
     * @param clazz 类
     * @return boolean
     */
    public static boolean isMap(Class<?> clazz) {
        boolean result = false;
        if (ClassUtils.isAssignable(Map.class, clazz)) {
            result = true;
        }
        return result;
    }

    /**
     * 判断对象是否为时间(Date)类型和日期(Calendar)类型
     * @param value 对象
     * @return boolean
     */
    public static boolean isDate(Object value) {
        return value instanceof Date || value instanceof Calendar || value instanceof LocalDate
                || value instanceof LocalDateTime;
    }

    /**
     * 判断类型是否为时间(Date)类型
     * @param clazz 类
     * @return boolean
     */
    public static boolean isDate(Class<?> clazz) {
        boolean result = false;
        if (ClassUtils.isAssignable(java.util.Date.class, clazz) || ClassUtils.isAssignable(Calendar.class, clazz)
                || ClassUtils.isAssignable(LocalDate.class, clazz)
                || ClassUtils.isAssignable(LocalDateTime.class, clazz)) {
            result = true;
        }
        return result;
    }

    /**
     * 判断是否为Blob Clob类型
     * @param clazz 类
     * @return boolean
     */
    public static boolean isSerial(Class<?> clazz) {
        boolean result = false;
        if (ClassUtils.isAssignable(java.sql.Clob.class, clazz)
                || ClassUtils.isAssignable(java.sql.Blob.class, clazz)) {
            result = true;
        }
        return result;
    }

    /**
     * 判断是否为Json类型
     * @param clazz 类
     * @return boolean
     */
    public static boolean isJson(Class<?> clazz) {
        boolean result = false;
        if (ClassUtils.isAssignable(JSON.class, clazz)) {
            result = true;
        }
        return result;
    }

    /**
     * 判断是否为枚举类型
     * @param className 类
     * @return boolean
     */
    public static boolean isEnum(String className) {
        try {
            return Class.forName(className).isEnum();
        }
        catch (ClassNotFoundException e) {
            LOGGER.error("isEnum error ", e);
        }
        return false;
    }

    /**
     * 判断是否为基本类型
     * @param clazz 类
     * @return boolean
     */
    public static boolean isAtomic(Class<?> clazz) {
        return isSpecific(clazz) || isDate(clazz) || isCollection(clazz) || isSerial(clazz) || isJson(clazz);
    }

    /**
     * 判断基本类型
     * @param clazz 类
     * @return String
     */
    public static String getObjectType(Class<?> clazz) {
        if (clazz == null) {
            return StringUtils.EMPTY;
        }
        else if (isAtomic(clazz)) {
            return clazz.getSimpleName();
        }
        else {
            return "Object";
        }
    }

    /**
     * 反射调用方法
     * @param object 兑现
     * @param methodName 方法
     * @param args 参数
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(Object object, String methodName, Object... args) {
        try {
            return (T) MethodUtils.invokeMethod(object, methodName, args);
        }
        catch (Exception e) {
            LOGGER.error("Error invokeMethod:", e);
        }
        return null;
    }

    /**
     * 反射调用方法
     * @param object 对象
     * @param methodName 方法
     * @param args 参数
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(Object object, String methodName, Object[] args, Class<?>[] parameterTypes) {
        try {
            return (T) MethodUtils.invokeMethod(object, methodName, args, parameterTypes);
        }
        catch (Exception e) {
            LOGGER.error("Error invokeMethod:", e);
        }
        return null;
    }

    /**
     * 反射调用静态方法
     * @param clazz 类
     * @param methodName 方法
     * @param args 参数
     * @return Object
     */
    public static <T> Object invokeStaticMethod(Class<T> clazz, String methodName, Object... args) {
        try {
            return MethodUtils.invokeStaticMethod(clazz, methodName, args);
        }
        catch (Exception e) {
            LOGGER.error("Error invokeStaticMethod:", e);
        }
        return null;
    }

    /**
     * 反射调用静态方法
     * @param clazz 类
     * @param methodName 方法
     * @param args 参数
     * @return Object
     */
    public static <T> Object invokeStaticMethod(Class<T> clazz, String methodName, Object[] args,
            Class<?>[] parameterTypes) {
        try {
            return MethodUtils.invokeStaticMethod(clazz, methodName, args, parameterTypes);
        }
        catch (Exception e) {
            LOGGER.error("Error invokeStaticMethod:", e);
        }
        return null;
    }

    /**
     * 获取 get 方法
     * @param clazz 类
     * @return Method[]
     */
    public static <T> List<Method> getGetters(Class<T> clazz) {
        Method[] methods = clazz.getMethods();
        List<Method> getters = new ArrayList<>();
        Arrays.stream(methods)
            .filter(method -> method.getName().startsWith("get") || method.getName().startsWith("is"))
            .forEach(method -> {
                Class<?>[] paramTypes = method.getParameterTypes();
                if (paramTypes.length == 0) {
                    getters.add(method);
                }
            });
        return getters;
    }

    /**
     * 获取 set 方法
     * @param clazz 类
     * @return Method[]
     */
    public static <T> List<Method> getSetters(Class<T> clazz) {
        Method[] methods = clazz.getMethods();
        List<Method> setters = new ArrayList<>();
        Arrays.stream(methods).filter(method -> method.getName().startsWith("set")).forEach(method -> {
            Class<?>[] paramTypes = method.getParameterTypes();
            if (paramTypes.length == 1) {
                setters.add(method);
            }
        });
        return setters;
    }

    /**
     * 获取属性
     * @param method 方法
     * @return String
     */
    public static String getProperty(Method method) {
        PropertyDescriptor pd = BeanUtils.findPropertyForMethod(method);
        if (pd != null) {
            return pd.getName();
        }
        return null;
    }

    /**
     * 转换为时间类型
     * @param value 对象
     * @return Long
     */
    public static Long getTime(Object value) {
        if (value != null) {
            if (value instanceof Date) {
                return ((Date) value).getTime();
            }
            if (value instanceof Calendar) {
                return ((Calendar) value).getTimeInMillis();
            }
        }
        return null;
    }

    /**
     * 获取非基本类型对象
     * @param clazz 类
     * @return List<Class < ?>>
     */
    public static List<Class<?>> findNoneAtomicClass(Class<?> clazz) {
        List<Class<?>> result = new ArrayList<>();
        List<Method> setters = getSetters(clazz);
        setters.forEach(setter -> {
            String property = getProperty(setter);
            if (property == null) {
                return;
            }
            @SuppressWarnings("unchecked")
            Class<? extends Serializable> paramType = (Class<Serializable>) setter.getParameterTypes()[0];
            if (!isAtomic(paramType)) {
                result.add(paramType);
            }
        });
        return result;
    }

}
