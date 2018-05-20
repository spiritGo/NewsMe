package com.example.spirit.template.utils;

public class DesityUtil {
    public static int dip2px(float dip) {
        float density = ConstanceUtil.getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }
}
