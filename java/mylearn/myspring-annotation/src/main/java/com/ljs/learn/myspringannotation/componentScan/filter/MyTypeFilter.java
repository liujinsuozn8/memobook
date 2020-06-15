package com.ljs.learn.myspringannotation.componentScan.filter;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

public class MyTypeFilter implements TypeFilter {
    // 返回值：true=匹配成功，false=匹配失败
    // MetadataReader 当前正在扫描的类的信息
    // MetadataReaderFactory 可以获取到其他任何类的信息
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        // 1. 获取当前类注解的信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();

        // 2. 获取当前正在扫描的类的信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();

        // 3. 获取当前类的资源信息 (如: 路径)
        Resource resource = metadataReader.getResource();

        // 4. 获取当前正在扫描的类的类名（全类名）
        String className = classMetadata.getClassName();
        // 输出正在扫描的类名
        System.out.println("-----" + className);

        // 5. 设置过滤规则
        if (className.contains("er")){
            return true;
        }

        return false;
    }
}
