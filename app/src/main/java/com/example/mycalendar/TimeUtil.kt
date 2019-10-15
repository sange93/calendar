package com.example.mycalendar

import java.util.*
import kotlin.math.abs

/**
 * 时间工具类
 * @author ssq
 */
object TimeUtil {
    /**
     * 获得两个日期间距多少天
     *
     * @param beginDate
     * @param endDate
     */
    fun getTimeDistance(beginDate: Date, endDate: Date): Int {
        val fromCalendar = Calendar.getInstance()
        fromCalendar.time = beginDate
        fromCalendar.set(Calendar.HOUR_OF_DAY, fromCalendar.getMinimum(Calendar.HOUR_OF_DAY))
        fromCalendar.set(Calendar.MINUTE, fromCalendar.getMinimum(Calendar.MINUTE))
        fromCalendar.set(Calendar.SECOND, fromCalendar.getMinimum(Calendar.SECOND))
        fromCalendar.set(Calendar.MILLISECOND, fromCalendar.getMinimum(Calendar.MILLISECOND))

        val toCalendar = Calendar.getInstance()
        toCalendar.time = endDate
        toCalendar.set(Calendar.HOUR_OF_DAY, fromCalendar.getMinimum(Calendar.HOUR_OF_DAY))
        toCalendar.set(Calendar.MINUTE, fromCalendar.getMinimum(Calendar.MINUTE))
        toCalendar.set(Calendar.SECOND, fromCalendar.getMinimum(Calendar.SECOND))
        toCalendar.set(Calendar.MILLISECOND, fromCalendar.getMinimum(Calendar.MILLISECOND))

        var dayDistance = (toCalendar.time.time - fromCalendar.time.time) / (1000 * 60 * 60 * 24)
        dayDistance = abs(dayDistance)

        return abs(dayDistance).toInt()
    }
}