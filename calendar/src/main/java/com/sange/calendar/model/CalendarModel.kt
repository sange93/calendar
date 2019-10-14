package com.sange.calendar.model

import android.graphics.Color

/**
 * model基类
 * @param itemType item类型
 */
open class BaseItem(val itemType: Int)

/**
 * 天
 * @param date 日期 yyyyMMdd
 * @param lunar 农历
 * @param isCurrentMonth 是否为本月日期
 * @param type 类型
 */
class CalendarDay(val date: Int, val lunar: Lunar, val isCurrentMonth: Boolean, type: Int) : BaseItem(type) {
    // 事件
    var eventModel: CalendarEventModel? = null
    // 是否选中（不连续多选时使用）
    var isSelected = false
}

/**
 * 月标题
 * @param title 标题
 * @param type 类型
 */
class CalendarMonth(val title: String, type: Int) : BaseItem(type)

/**
 * 当天事件的Model
 * @author ssq
 */
class CalendarEventModel {
    //事件事件
    var eventDate = 0
    //事件背景颜色
    var eventBgColor: Int = Color.parseColor("#FF00FF")
    //事件文字的颜色
    var eventTextColor: Int = Color.parseColor("#FF0000")
    //事件的文字
    var eventInfo: String = ""
}

/**
 * 农历
 * @author ssq
 */
class Lunar {
    var isLeap: Boolean = false
    var lunarDay: Int = 0
    var lunarMonth: Int = 0
    var lunarYear: Int = 0
}

/**
 * 阳历
 * @author ssq
 */
class Solar(var solarYear: Int, var solarMonth: Int, var solarDay: Int)