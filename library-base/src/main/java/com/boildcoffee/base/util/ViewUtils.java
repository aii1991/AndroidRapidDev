package com.boildcoffee.base.util;

import android.graphics.Rect;
import android.view.View;

/**
 * @author zjh
 *         2018/6/8
 */

public class ViewUtils {
    /**
     * 获取view在屏幕上的左上角位置
     * @param view
     * @return int[] int[0]为X轴方向的位置  int[1] 为Y轴方向的位置
     */
    public static int[] getViewLocationOnScreen(View view){
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    /**
     * 获取view可视区域的矩阵
     * @param view
     * @return
     */
    public static Rect getViewGlobalVisibleRect(View view){
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        return rect;
    }

}
