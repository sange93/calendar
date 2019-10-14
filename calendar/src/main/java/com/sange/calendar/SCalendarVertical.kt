package com.sange.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.sange.calendar.adapter.BaseCalendarAdapter
import com.sange.calendar.adapter.CalendarAdapter
import com.sange.calendar.adapter.CalendarMultipleAdapter
import com.sange.calendar.model.CalendarDay
import com.sange.calendar.model.CalendarEventModel
import com.sange.calendar.model.CalendarMonth
import com.sange.calendar.model.CalendarVerticalConfig
import kotlinx.android.synthetic.main.calendar_layout_vertical.view.*
import kotlinx.android.synthetic.main.calendar_layout_week.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 垂直方向的日历
 * 注意：在代码中必须调用方法setConfig(config: CalendarVerticalConfig)，才可显示日历
 * @author ssq
 */
class SCalendarVertical : LinearLayoutCompat {
    // 日历配置
    private var mConfig = CalendarVerticalConfig.Builder().build()
    // 日历适配器
    private lateinit var mAdapter: BaseCalendarAdapter

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
//        initViews()
    }

    /**
     * 初始化(必须调用)
     */
    fun initViews(adapter: BaseCalendarAdapter) {
        mAdapter = adapter
        //绑定View
        View.inflate(context, R.layout.calendar_layout_vertical, this)
        //初始化RecyclerView
        val gll = GridLayoutManager(context, 7)
        gll.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (mAdapter.getItemViewType(position)) {
                    mAdapter.typeDay -> 1
                    else -> 7// 横跨7列
                }
            }
        }
        rvCalendar.layoutManager = gll
        rvCalendar.adapter = mAdapter
    }

    /**
     * 初始化日历日期
     */
    private fun initCalendarDates() {
        //星期栏的显示和隐藏
        gWeek.visibility = if (mConfig.isShowWeek) {
            tv_week_01.setTextColor(mConfig.colorWeek)
            tv_week_02.setTextColor(mConfig.colorWeek)
            tv_week_03.setTextColor(mConfig.colorWeek)
            tv_week_04.setTextColor(mConfig.colorWeek)
            tv_week_05.setTextColor(mConfig.colorWeek)
            tv_week_06.setTextColor(mConfig.colorWeek)
            tv_week_07.setTextColor(mConfig.colorWeek)
            View.VISIBLE
        } else {
            View.GONE
        }

        // 日期集合
        mAdapter.dataList.clear()
        val calendar = Calendar.getInstance()
        val monthTitleFormat = SimpleDateFormat(mConfig.titleFormat, Locale.CHINA)
        // 记录几个7号
        var count7: Int
        // 记录第几个月
        var month: Int
        val dates = arrayListOf<CalendarDay>()
        // 计算日期
        for (i in 0 until mConfig.countMonth) {// 显示多少个月
            month = calendar.get(Calendar.MONTH)
            // 添加月标题
            mAdapter.dataList.add(CalendarMonth(monthTitleFormat.format(calendar.time), mAdapter.typeMonthHead))
            count7 = 0
            dates.clear()
            //设置为当月的第一天
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            //获取当月第一天是星期几
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1
            //移动到需要绘制的第一天
            calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek)
            //6*7
            while (dates.size < 6 * 7) {
                val date = mAdapter.yyyyMMdd.format(calendar.time)
                dates.add(CalendarDay(date.toInt(), LunarCalendarUtils.solarToLunar(date), calendar.get(Calendar.MONTH) == month, mAdapter.typeDay))
                //包含两个7就多了一行
                if (calendar.get(Calendar.DAY_OF_MONTH) == 7) {
                    count7++
                }
                //向后移动一天
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }
            if (count7 >= 2) {
                mAdapter.dataList.addAll(dates.dropLast(7))
            } else {
                mAdapter.dataList.addAll(dates)
            }
        }
        mAdapter.setConfig(mConfig)
        if (mAdapter is CalendarMultipleAdapter && mConfig.selectedDateList.isNotEmpty()) {
            (mAdapter as CalendarMultipleAdapter).initSelectedDate()
        }
        mAdapter.notifyDataSetChanged()
    }

    /**
     * 设置事件日期(可选)
     * @param eventDates 事件集合
     */
    fun setEventDates(eventDates: ArrayList<CalendarEventModel>) {
        if (eventDates.isEmpty()) return
        for (item in mAdapter.dataList) {
            if (item is CalendarDay) {
                for (eventModel in eventDates) {
                    if (item.date == eventModel.eventDate) {
                        item.eventModel = eventModel
                        break
                    }
                }
            }
        }
        mAdapter.notifyDataSetChanged()
    }

    /**
     * 设置配置文件(必须调用)
     */
    fun setConfig(config: CalendarVerticalConfig) {
        this.mConfig = config
        initCalendarDates()
    }

    /**
     * 设置日期选择监听
     */
    fun setOnCalendarChooseListener(listener: CalendarAdapter.OnCalendarRangeChooseListener) {
        if (mAdapter is CalendarAdapter) {
            (mAdapter as CalendarAdapter).rangeChooseListener = listener
        }
    }

    /**
     * 设置日期选择监听
     */
    fun setOnCalendarChooseListener(listener: CalendarMultipleAdapter.OnCalendarMultipleChooseListener) {
        if (mAdapter is CalendarMultipleAdapter) {
            (mAdapter as CalendarMultipleAdapter).multipleChooseListener = listener
        }
    }

    /**
     * 设置是否可点击选日期
     */
    fun setDateClickable(clickable: Boolean) {
        mAdapter.clickable = clickable
    }
}