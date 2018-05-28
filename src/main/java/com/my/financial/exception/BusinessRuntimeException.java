package com.my.financial.exception;

import java.util.HashMap;
import java.util.Map;

public class BusinessRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    /**
     * 错误编码与内容的映射
     */
    protected static Map<Integer, String> mapMessage = new HashMap<Integer, String>();

    public static final int SYSTEM_INTERNAL_ERROR = 10001;
    public static final int PARAMETER_ERROR = 10002;
    public static final int RECORD_DUPLICATED = 11001;
    public static final int RECORD_NOTEXIST = 11002;

    static {
        mapMessage.put(SYSTEM_INTERNAL_ERROR, "系统内部错误");
        mapMessage.put(PARAMETER_ERROR, "业务参数异常");
        mapMessage.put(RECORD_DUPLICATED, "记录重复");
        mapMessage.put(RECORD_NOTEXIST, "记录不存在");
    }

    /**
     * 错误编码
     */
    protected int code;

    /**
     * 根据错误编码，获取错误信息，并创建业务运行时异常
     *
     * @param code
     */
    public BusinessRuntimeException(int code) {
        super(getMessage(code));
        this.code = code;
    }

    public BusinessRuntimeException(int code, Throwable e) {
        super(getMessage(code), e);
        this.code = code;
    }

    public BusinessRuntimeException(String message) {
        super(message);
    }

    public BusinessRuntimeException(String message, Throwable e) {
        super(message, e);
    }

    /**
     * 根据错误编码获取错误信息
     *
     * @param code
     *            错误编码
     * @return 错误信息
     */
    public static String getMessage(int code) {
        return mapMessage.get(code);
    }
}
