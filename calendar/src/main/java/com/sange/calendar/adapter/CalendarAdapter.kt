package com.sange.calendar.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.sange.calendar.R
import com.sange.calendar.model.CalendarDay
import com.sange.calendar.model.CalendarVerticalConfig
import kotlinx.android.synthetic.main.calendar_item_day.view.*

/**
 * 日历适配器(选连续多天)
 *
 * @author ssq
 */
class CalendarAdapter(context: Context) : BaseCalendarAdapter(context) {
    // 日期选择监听
    var rangeChooseListener: OnCalendarRangeChooseListener? = null
    // 选择的开始日期
    private var startDate = 0
    // 选择的结束日期
    private var endDate = 0
    // 开始日期在列表中的下标
    private var startDatePosition = -1
    // 结束日期在列表中的下标
    private var endDatePosition = -1

    override fun setConfig(config: CalendarVerticalConfig) {
        super.setConfig(config)
        startDate = config.startDate
        endDate = config.endDate
    }

    override fun onClickItem(position: Int, itemDay: CalendarDay) {
        if (startDate != 0 && endDate != 0) {
            startDate = 0
            endDate = 0
            startDatePosition = -1
            endDatePosition = -1
        }
        if (startDate == 0) {
            startDate = itemDay.date
            startDatePosition = position
        } else {
            //判断结束位置是不是大于开始位置
            if (itemDay.date <= startDate) {// 如果小于开始位置， 则改变开始位置
                startDate = itemDay.date
                startDatePosition = position
            } else {
                endDate = itemDay.date
                endDatePosition = position
            }
        }
        if (startDate != 0 && endDate != 0) {
            //选取通知
            rangeChooseListener?.onRangeDate(startDate, endDate)
        }
    }

    override fun bindDataOfDay(itemView: View, itemDay: CalendarDay) {
        with(itemView) {
            //判断是不是点击了起始日期
            if (startDate != 0 && startDate == itemDay.date) {
                iv_day_bg.visibility = View.VISIBLE
                iv_day_bg.setBackgroundResource(R.drawable.calendar_bg_selected_start)
                tv_day_small.visibility = View.VISIBLE
                tv_day_small.text = "开始"
                tv_day.setTextColor(config.colorRangeText)
                tv_day_small.setTextColor(config.colorRangeText)
                //动态修改颜色
                (iv_day_bg.background as GradientDrawable).setColor(config.colorStartAndEndBg)
            }
            //判断是不是点击了结束日期
            if (endDate != 0 && endDate == itemDay.date) {
                iv_day_bg.visibility = View.VISIBLE
                iv_day_bg.setBackgroundResource(R.drawable.calendar_bg_selected_end)
                tv_day_small.visibility = View.VISIBLE
                tv_day_small.text = "结束"
                tv_day.setTextColor(config.colorRangeText)
                tv_day_small.setTextColor(config.colorRangeText)
                //动态修改颜色
                (iv_day_bg.background as GradientDrawable).setColor(config.colorStartAndEndBg)
            }
            //判断是不是大于起始日期
            if (startDate != 0 && endDate != 0 && itemDay.date in (startDate + 1) until endDate) {
                iv_day_bg.visibility = View.VISIBLE
                iv_day_bg.setBackgroundResource(R.drawable.calendar_bg_selected_center)
                tv_day.setTextColor(config.colorRangeText)
                tv_day_small.setTextColor(config.colorRangeText)
                //动态修改颜色
                (iv_day_bg.background as GradientDrawable).setColor(config.colorRangeBg)
            }
        }
    }

    /**
     * 日历的范围选取监听
     * @author ssq
     */
    interface OnCalendarRangeChooseListener {
        /**
         * 选取日期
         * @param startDate 开始日期 例：20190901
         * @param endDate 结束日期 例：20190903
         */
        fun onRangeDate(startDate: Int, endDate: Int)
    }
}