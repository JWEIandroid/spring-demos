package com.example.thread.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.Writer;

public class JsonMapperUtil {

    private static final Logger logger = LogManager.getLogger(JsonMapperUtil.class);

    public static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
     * <p>
     * 如需读取集合如List/Map, 且不是List<String>这种简单类型时使用如下语句,使用後面的函數.
     */
    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return MAPPER.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
     * <p>
     * 如需读取集合如List/Map, 且不是List<String>時, 先用constructParametricType(List.class,MyBean.class)構造出JavaTeype,再調用本函數.
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return (T) MAPPER.readValue(jsonString, javaType);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 转换为jsonList
     * @param jsonString  需要转换的数据json字符串
     * @param listTypeCls 转换的数组类型
     * @param customBeanCls   转换为自定义bean
     * @param <T>
     * @return
     */
    public static <T> T fromJsonList(String jsonString, Class<?> listTypeCls ,Class<?> customBeanCls) {
        JavaType javaType = JsonMapperUtil.constructParametricType(listTypeCls,customBeanCls);
        return fromJson(jsonString, javaType);
    }

    /**
     * 转换为jsonList
     * @param jsonObject  需要转换的数组json串对象
     * @param listTypeCls 转换的数组类型
     * @param customBeanCls   转换为自定义bean
     * @param <T>
     * @return
     */
    public static <T> T fromJsonList(Object jsonObject, Class<?> listTypeCls ,Class<?> customBeanCls) {
        JavaType javaType = JsonMapperUtil.constructParametricType(listTypeCls,customBeanCls);
        return fromJson(toJson(jsonObject), javaType);
    }

    /**
     * 構造泛型的Type如List<MyBean>, Map<String,MyBean>
     */
    public static JavaType constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
        return MAPPER.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }

    /**
     * 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]".
     */
    public static String toJson(Object object) {

        try {
            return MAPPER.writeValueAsString(object);
        } catch (IOException e) {
            logger.warn("write to json string error:" + object, e);
            return null;
        }
    }

    /**
     * 将对象转换为JSON流
     *
     * @param writer Writer
     * @param value  对象
     */
    public static void writeValue(Writer writer, Object value) {
        Assert.notNull(writer, "write不能为空");
        Assert.notNull(value, "写入对象不能为空");
        try {
            MAPPER.writeValue(writer, value);
        } catch (JsonGenerationException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 将对象转换为 url 参数形式
     * 例如 a=b&c=d
     *
     * @param obj
     * @return
     */
    public static String toUri(Object obj) {
        return MAPPER.convertValue(obj, UriFormat.class).toString();
    }
}
