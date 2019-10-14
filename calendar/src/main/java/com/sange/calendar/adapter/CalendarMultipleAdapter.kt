package com.sange.calendar.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.sange.calendar.model.CalendarDay
import kotlinx.android.synthetic.main.calendar_item_day.view.*

/**
 * 日历适配器（选择不连续多天）
 * @author ssq
 */
class CalendarMultipleAdapter(context: Context) : BaseCalendarAdapter(context) {
    // 日期选择监听
    var multipleChooseListener: OnCalendarMultipleChooseListener? = null

    /**
     * 初始化默认选中
     */
    fun initSelectedDate() {
        for (item in dataList) {
            if (item is CalendarDay) {
                for (date in config.selectedDateList) {
                    if (item.date == date) {
                        item.isSelected = true
                        break
                    }
                }
            }
        }
    }

    override fun onClickItem(position: Int, itemDay: CalendarDay) {
        itemDay.isSelected = !itemDay.isSelected
        multipleChooseListener?.onClickDate(itemDay.date)
    }

    override fun bindDataOfDay(itemView: View, itemDay: CalendarDay) {
        with(itemView) {
            if (itemDay.isSelected) {
                iv_day_bg.visibility = View.VISIBLE
                tv_day.setTextColor(config.colorMultipleText)
                //动态修改颜色
                (iv_day_bg.background as GradientDrawable).setColor(config.colorMultipleBg)
            }
        }
    }

    /**
     * 日历选取监听
     * @author ssq
     */
    interface OnCalendarMultipleChooseListener {
        /**
         * 选取日期
         * @param date 点击的日期 例：20190901
         */
        fun onClickDate(date: Int)
    }
}