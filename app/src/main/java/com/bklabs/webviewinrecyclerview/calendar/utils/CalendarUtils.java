/*
 * ファイル：CalendarUtils.java
 * 概要：Set height item day in month view calendar
 * ライセンス：
 * 著作権：Copyright(c) 2019 SoftBank Corp.
 *         All rights are reserved by SoftBank Corp., whether the whole or part of the source code including any modifications.
 */

package com.bklabs.webviewinrecyclerview.calendar.utils;

import android.content.Context;
import android.content.res.TypedArray;

import com.bklabs.webviewinrecyclerview.R;


/**
 * sch-cal.
 *
 * @author Systena
 * @version 1.0
 */
public class CalendarUtils {
    /**
     * get height of recycler view calendar
     *
     * @param mContext
     * @return int
     */
    private static int getHeightCalendar(Context mContext,int total) {
        return (mContext.getResources().getDisplayMetrics().heightPixels
                - mContext.getResources().getDimensionPixelSize(R.dimen.height_note_channel)
                - mContext.getResources().getDimensionPixelSize(R.dimen.height_room_channel)
                - mContext.getResources().getDimensionPixelSize(R.dimen.height_title_calendar)
                - mContext.getResources().getDimensionPixelSize(R.dimen.height_day_calendar)
                - mContext.getResources().getDimensionPixelSize(R.dimen.height_view_calendar) * total
                - mContext.getResources().getDimensionPixelSize(R.dimen.bottom_bar_height)
                - getActionBarSize(mContext)
                - getStatusBarHeight(mContext));
    }

    private static int getActionBarSize(Context context) {
        TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return actionBarSize;
    }

    /**
     * set height item in recycler view calendar
     *
     * @param mContext
     * @param totalColumn total column
     * @return int
     */
    public static int setHeightItemCalendar(Context mContext, int totalColumn) {
        return getHeightCalendar(mContext,totalColumn) / totalColumn;
    }

    public static int setWidthItemCalendar(Context mContext) {
        return mContext.getResources().getDisplayMetrics().heightPixels / 7;
    }

    /**
     * get height of status bar
     *
     * @param mContext
     * @return int
     */
    public static int getStatusBarHeight(Context mContext) {
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
