package com.ljs.learn.protocol;

import java.io.Serializable;

/**
 * rpc请求协议
 */
public class RequestProtocol implements Serializable {
    // 为了能在网络上传输，必须要可序列化
    private static final long serialVersionUID = -811557520790336965L;

    // 接收接口名、方法名、参数类型列表、参数值列表，并可以用于传输

    // 接口名
    private String interfaceName;

    // 方法名
    private String methodName;

    // 参数类型列表
    private Class<?>[] parameterTypes;

    // 参数值列表
    private Object[] parameterValues;


    // 无参和带参构造器
    public RequestProtocol() {
    }

    public RequestProtocol(String interfaceName, String methodName, Class<?>[] parameterTypes, Object[] parameterValues) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.parameterValues = parameterValues;
    }

    //getter、setter
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameterValues() {
        return parameterValues;
    }

    public void setParameterValues(Object[] parameterValues) {
        this.parameterValues = parameterValues;
    }
}
