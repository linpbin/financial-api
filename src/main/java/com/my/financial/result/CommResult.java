package com.my.financial.result;

public class CommResult<T> {
    private int resultCode; //返回结果状态码
    private String resultMsg; //结果描述信息
    private T data; //数据


    /**
     * 默认的成功对象，无须提供数据，统一共享实例，用作无须提供返回数据的方法返回值
     */
    public static final CommResult SUCCESS = new CommResult();


    public CommResult() {
        this(null);
    }


    /**
     * Rest Response Success
     *
     * @param data
     */
    public CommResult(T data) {
        resultCode = 0;
        resultMsg = "success";
        this.data = data;
    }


    /**
     * Rest Response Error
     *
     * @param resultCode
     * @param resutMessage
     */
    public CommResult(int resultCode, String resutMessage) {
        this.resultCode = resultCode;
        this.resultMsg = resutMessage;
    }


    public CommResult(int resultCode, String resutMsg, T data) {
        this.resultCode = resultCode;
        this.resultMsg = resutMsg;
        this.data = data;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RestResponse [resultCode=" + resultCode + ", resultMsg=" + resultMsg + ", data=" + data + "]";
    }
}
