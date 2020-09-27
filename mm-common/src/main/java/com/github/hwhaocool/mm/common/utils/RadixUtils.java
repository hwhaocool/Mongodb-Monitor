package com.github.hwhaocool.mm.common.utils;

public class RadixUtils {
    
    public static String humanRead(int value) {
        
        return humanRead((long)value);
    }
    
    public static String humanRead(long value) {
        if (value < 1024) {
            return value + "Byte";
        }
        
        value = value >> 10;
        if (value < 1024) {
            return value + "K";
        }
        
        value = value >> 10;
        if (value < 1024) {
            return value + "M";
        }
        
        value = value >> 10;
        return value + "G";
    }

}
