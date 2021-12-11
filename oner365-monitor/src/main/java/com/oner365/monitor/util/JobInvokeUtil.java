package com.oner365.monitor.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oner365.monitor.dto.SysTaskDto;
import com.oner365.monitor.entity.InvokeParam;
import com.oner365.util.DataUtils;

/**
 * 任务执行工具
 *
 * @author zhaoyong
 */
public class JobInvokeUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobInvokeUtil.class);

    private JobInvokeUtil() {

    }

    /**
     * 执行方法
     *
     * @param sysTask 系统任务
     */
    public static void invokeMethod(SysTaskDto sysTask) {
        String invokeTarget = sysTask.getInvokeTarget();
        String beanName = getBeanName(invokeTarget);
        String methodName = getMethodName(invokeTarget);
//        List<Object[]> methodParams = getMethodParams(invokeTarget);
        InvokeParam param = sysTask.getInvokeParam();
        param.setConcurrent(sysTask.getConcurrent());
        param.setTaskId(sysTask.getId());

        try {
            if (!isValidClassName(beanName)) {
                Object bean = SpringUtils.getBean(beanName);
                invokeMethod(bean, methodName, param);
            } else {
                Object bean = Class.forName(beanName).getDeclaredConstructor().newInstance();
                invokeMethod(bean, methodName, param);
            }
        } catch (Exception e) {
            LOGGER.error("invokeMethod error:", e);
        }
    }

    /**
     * 调用任务方法
     *
     * @param bean       目标对象
     * @param methodName 方法名称
     * @param param      方法参数
     */
    private static void invokeMethod(Object bean, String methodName, InvokeParam param)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (param != null) {
            Method method = bean.getClass().getDeclaredMethod(methodName, InvokeParam.class);
            method.invoke(bean, param);
        } else {
            Method method = bean.getClass().getDeclaredMethod(methodName);
            method.invoke(bean);
        }
    }

    /**
     * 调用任务方法
     *
     * @param bean         目标对象
     * @param methodName   方法名称
     * @param methodParams 方法参数
     */
    @SuppressWarnings("unused")
    private static void invokeMethod(Object bean, String methodName, List<Object[]> methodParams)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (methodParams != null && !methodParams.isEmpty()) {
            Method method = bean.getClass().getDeclaredMethod(methodName, getMethodParamsType(methodParams));
            method.invoke(bean, getMethodParamsValue(methodParams));
        } else {
            Method method = bean.getClass().getDeclaredMethod(methodName);
            method.invoke(bean);
        }
    }

    /**
     * 校验是否为为class包名
     *
     * @param invokeTarget 名称
     * @return true是 false否
     */
    public static boolean isValidClassName(String invokeTarget) {
        return StringUtils.countMatches(invokeTarget, ".") > 1;
    }

    /**
     * 获取bean名称
     *
     * @param invokeTarget 目标字符串
     * @return bean名称
     */
    public static String getBeanName(String invokeTarget) {
        String beanName = StringUtils.substringBefore(invokeTarget, "(");
        return StringUtils.substringBeforeLast(beanName, ".");
    }

    /**
     * 获取bean方法
     *
     * @param invokeTarget 目标字符串
     * @return method方法
     */
    public static String getMethodName(String invokeTarget) {
        String methodName = StringUtils.substringBefore(invokeTarget, "(");
        return StringUtils.substringAfterLast(methodName, ".");
    }

    /**
     * 获取method方法参数相关列表
     *
     * @param invokeTarget 目标字符串
     * @return method方法相关参数列表
     */
    public static List<Object[]> getMethodParams(String invokeTarget) {
        String methodStr = StringUtils.substringBetween(invokeTarget, "(", ")");
        if (DataUtils.isEmpty(methodStr)) {
            return Collections.emptyList();
        }
        String[] methodParams = methodStr.split(",");
        List<Object[]> classes = new LinkedList<>();
        for (String methodParam : methodParams) {
            String str = StringUtils.trimToEmpty(methodParam);
            // String字符串类型，包含'
            if (StringUtils.contains(str, "'")) {
                classes.add(new Object[] { StringUtils.replace(str, "'", ""), String.class });
            }
            // boolean布尔类型，等于true或者false
            else if (StringUtils.equals(str, "true") || StringUtils.equalsIgnoreCase(str, "false")) {
                classes.add(new Object[] { Boolean.valueOf(str), Boolean.class });
            }
            // long长整形，包含L
            else if (StringUtils.containsIgnoreCase(str, "L")) {
                classes.add(new Object[] { Long.valueOf(StringUtils.replaceIgnoreCase(str, "L", "")), Long.class });
            }
            // double浮点类型，包含D
            else if (StringUtils.containsIgnoreCase(str, "D")) {
                classes.add(new Object[] { Double.valueOf(StringUtils.replaceIgnoreCase(str, "D", "")), Double.class });
            }
            // 其他类型归类为整形
            else {
                classes.add(new Object[] { Integer.valueOf(str), Integer.class });
            }
        }
        return classes;
    }

    /**
     * 获取参数类型
     *
     * @param methodParams 参数相关列表
     * @return 参数类型列表
     */
    public static Class<?>[] getMethodParamsType(List<Object[]> methodParams) {
        Class<?>[] classes = new Class<?>[methodParams.size()];
        int index = 0;
        for (Object[] os : methodParams) {
            classes[index] = (Class<?>) os[1];
            index++;
        }
        return classes;
    }

    /**
     * 获取参数值
     *
     * @param methodParams 参数相关列表
     * @return 参数值列表
     */
    public static Object[] getMethodParamsValue(List<Object[]> methodParams) {
        Object[] classes = new Object[methodParams.size()];
        int index = 0;
        for (Object[] os : methodParams) {
            classes[index] = os[0];
            index++;
        }
        return classes;
    }
}
