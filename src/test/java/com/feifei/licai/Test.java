package com.feifei.licai;

import java.util.Arrays;
import java.util.TimeZone;

/**
 * Created by cuisf on 2019/8/6.
 */
public class Test {
    public static void main(String[] args) {
        // 获取可用的时区列表
        String[] ids = TimeZone.getAvailableIDs();
        System.out.println(Arrays.toString(ids));
        // 获取默认时区
        TimeZone my = TimeZone.getDefault();
        System.out.println(my.getID());
        // 获取默认时区的中文名称
        System.out.println(my.getDisplayName());
        // 根据指定ID获取时区的名称
        System.out.println(TimeZone.getTimeZone("CST").getDisplayName());
    }
}
