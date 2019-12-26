package com.github.hwhaocool.mm.common.utils;

import java.lang.reflect.ParameterizedType;

import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 泛型工具类
 * 
 * @author tianjp
 *
 */
@SuppressWarnings("rawtypes")
public class GenericsUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericsUtils.class);

    private GenericsUtils() {
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型. 
     * 如public BookManager extends GenricManager
     *
     * @param clazz The class to introspect
     * @return the first generic declaration, or <code>Object.class</code> if cannot be determined
     */
    
    public static Class getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    
    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型. 
     * 如public BookManager extends GenricManager<Book>
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or <code>Object.class</code> if cannot be determined
     */
    public static Class getSuperClassGenricType(Class clazz, int index) {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            LOGGER.warn("{}'s superclass not ParameterizedType", clazz.getSimpleName());
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            LOGGER.warn("Index {},  Size of {}'s Parameterized Type {}", index, clazz.getSimpleName(), params.length);
            return Object.class;
        }
        
        if (!(params[index] instanceof Class)) {
            LOGGER.warn("{} not set the actual class on superclass generic parameter", clazz.getSimpleName());
            return Object.class;
        }
        return (Class) params[index];
    }
}
