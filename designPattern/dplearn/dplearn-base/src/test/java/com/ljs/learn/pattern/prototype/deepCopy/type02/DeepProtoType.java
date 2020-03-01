package com.ljs.learn.pattern.prototype.deepCopy.type02;

import java.io.*;

public class DeepProtoType implements Serializable {
    private static final long serialVersionUID = 1940210468835235379L;

    public String name;
    // 一个引用类型的成员属性
    public DeepCloneableTarget target;

    public DeepProtoType(String name, DeepCloneableTarget target) {
        this.name = name;
        this.target = target;
    }

    // 深拷贝方式2：通过对象序列化实现深拷贝
    public Object deepClone(){
        try (
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
        ){
            // 将数据写如临时区
            oos.writeObject(this);

            try(
                ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
                ObjectInputStream ois = new ObjectInputStream(bis);
            ) {
                // 使用序列化属性重新构造对象
                return (DeepProtoType)ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
