package com.sange.calendar.model

import android.graphics.Color

/**
 * 垂直方向的配置信息
 * @author ssq
 */
class CalendarVerticalConfig private constructor() {
    //是否显示阴历
    var isShowLunar = true
        private set
    //是否显示星期栏
    var isShowWeek = true
        private set
    //每个月标题的样式
    var titleFormat = "yyyy年MM月"
        private set
    //每个月标题的颜色
    var colorTitle = Color.parseColor("#282828")
        private set
    //星期栏的颜色
    var colorWeek = Color.parseColor("#5E5E5E")
        private set
    //阳历的颜色
    var colorSolar = Color.parseColor("#282828")
        private set
    //阴历的颜色
    var colorLunar = Color.parseColor("#979797")
        private set
    //今天之前的日期的颜色
    var colorBeforeToday = Color.parseColor("#979797")
        private set
    //开始结束的背景颜色
    var colorStartAndEndBg = Color.parseColor("#df0e0e0e")
        private set
    //区间中间的背景颜色
    var colorRangeBg = Color.parseColor("#d0353535")
        private set
    //区间文字的颜色
    var colorRangeText = Color.parseColor("#FFFFFF")
        private set
    // 多选的背景颜色
    var colorMultipleBg = Color.parseColor("#CCCCCC")
    private set
    // 多选的文字颜色
    var colorMultipleText = Color.parseColor("#333333")
        private set
    //显示多少月(默认6个月)
    var countMonth = 6
        private set
    // 选中的开始时间
    var startDate = 0
        private set
    // 选中的结束时间
    var endDate = 0
        private set
    // 选中的日期集合（不连续多选）
    val selectedDateList = arrayListOf<Int>()

    class Builder {
        private val calendarConfig: CalendarVerticalConfig = CalendarVerticalConfig()

        fun setShowLunar(showLunar: Boolean): Builder {
            calendarConfig.isShowLunar = showLunar
            return this
        }

        fun setShowWeek(showWeek: Boolean): Builder {
            calendarConfig.isShowWeek = showWeek
            return this
        }

        fun setTitleFormat(titleFormat: String): Builder {
            calendarConfig.titleFormat = titleFormat
            return this
        }

        fun setColorTitle(colorTitle: String): Builder {
            calendarConfig.colorTitle = Color.parseColor(colorTitle)
            return this
        }

        fun setColorWeek(colorWeek: String): Builder {
            calendarConfig.colorWeek = Color.parseColor(colorWeek)
            return this
        }

        fun setColorSolar(colorSolar: String): Builder {
            calendarConfig.colorSolar = Color.parseColor(colorSolar)
            return this
        }

        fun setColorLunar(colorLunar: String): Builder {
            calendarConfig.colorLunar = Color.parseColor(colorLunar)
            return this
        }

        fun setColorBeforeToday(colorBeforeToday: String): Builder {
            calendarConfig.colorBeforeToday = Color.parseColor(colorBeforeToday)
            return this
        }

        fun setColorStartAndEndBg(colorStartAndEndBg: String): Builder {
            calendarConfig.colorStartAndEndBg = Color.parseColor(colorStartAndEndBg)
            return this
        }

        fun setColorRangeBg(colorRangeBg: String): Builder {
            calendarConfig.colorRangeBg = Color.parseColor(colorRangeBg)
            return this
        }

        fun setColorRangeText(colorRangeText: String): Builder {
            calendarConfig.colorRangeText = Color.parseColor(colorRangeText)
            return this
        }

        fun setColorMultipleBg(colorMultipleBg: String): Builder {
            calendarConfig.colorMultipleBg = Color.parseColor(colorMultipleBg)
            return this
        }

        fun setColorMultipleText(colorMultipleText: String): Builder {
            calendarConfig.colorMultipleText = Color.parseColor(colorMultipleText)
            return this
        }

        fun setCountMonth(countMonth: Int): Builder {
            calendarConfig.countMonth = countMonth
            return this
        }

        /**
         * 设置选中的开始时间
         * @param startDate 开始时间
         */
        fun setStartDate(startDate: Int): Builder {
            calendarConfig.startDate = startDate
            return this
        }

        /**
         * 设置选中的结束时间
         * @param endDate 结束时间
         */
        fun setEndDate(endDate: Int): Builder {
            calendarConfig.endDate = endDate
            return this
        }

        /**
         * 设置选中的日期（不连续多选功能）
         */
        fun setSelectedDateList(selectedDateList: ArrayList<Int>): Builder{
            calendarConfig.selectedDateList.clear()
            calendarConfig.selectedDateList.addAll(selectedDateList)
            return this
        }

        fun build(): CalendarVerticalConfig {
            return calendarConfig
        }
    }
}
