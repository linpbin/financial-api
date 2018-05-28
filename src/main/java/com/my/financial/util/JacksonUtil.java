package com.my.financial.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.financial.exception.BusinessRuntimeException;

public final class JacksonUtil {


    public static ObjectMapper objectMapper = new ObjectMapper();


    /**
     * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
     * (1)转换为普通JavaBean：readValue(json,Student.class)
     * (2)转换为List,如List<Student>,将第二个参数传递为Student
     * [].class.然后使用Arrays.asList();方法把得到的数组转换为特定类型的List
     *
     * @param jsonStr
     * @param valueType
     * @return
     */
    public static <T> T readValue(String jsonStr, Class<T> valueType) {
        try {
            return objectMapper.readValue(jsonStr, valueType);
        } catch (Exception e) {
            throw new BusinessRuntimeException("JacksonUtil.readValue(String,Class<T>)执行异常", e);
        }
    }


    /**
     * json数组转List
     *
     * @param jsonStr
     * @param valueTypeRef
     * @return
     */
    public static <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef) {  //eg:readValue(str,new TypeReference<List<Student>>(){})  调用
        try {
            return objectMapper.readValue(jsonStr, valueTypeRef);
        } catch (Exception e) {
            throw new BusinessRuntimeException("JacksonUtil.readValue(String,TypeReference<T>)执行异常", e);
        }
    }



    /**
     * 把JavaBean转换为json字符串
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new BusinessRuntimeException("JacksonUtil.toJSon执行异常", e);
        }
    }


}
