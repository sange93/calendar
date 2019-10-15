package com.example.mycalendar

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sange.calendar.adapter.CalendarAdapter
import com.sange.calendar.model.CalendarEventModel
import com.sange.calendar.model.CalendarVerticalConfig
import kotlinx.android.synthetic.main.activity_successive_time.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 连续的日期选择
 * @author ssq
 */
class SuccessiveTimeActivity : AppCompatActivity() {
    private val mSdf = SimpleDateFormat("yyyyMMdd", Locale.CHINA)
    private val mBanDateList = arrayListOf<String>()// 禁止日期
    private val mEventDates by lazy { arrayListOf<CalendarEventModel>() }// 禁止日期事件
    private var mStartDate = ""// 记录选择的开始日期
    private var mEndDate = ""// 记录选择的结束日期
    private var mDayCount = 0// 选择的总天数
    private var mErrorMsg = ""// 错误信息
    private var mClickable = true// 是否可点击选时间

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_successive_time)
        tvOk.setOnClickListener { onClickOk() }
        initCalendar()
    }

    /**
     * 初始化日历
     */
    private fun initCalendar() {
        vCalendar.initViews(CalendarAdapter(this))// 设置为连选的适配器
        // 区间选取完成监听
        vCalendar.setOnCalendarChooseListener(object :
            CalendarAdapter.OnCalendarRangeChooseListener {
            override fun onRangeDate(startDate: Int, endDate: Int) {
                onChooseDate(startDate, endDate)
            }
        })
        val startDate = if (mStartDate.isNotEmpty()) mSdf.parse(mStartDate) else null
        val endDate = if (mEndDate.isNotEmpty()) mSdf.parse(mEndDate) else null
        if (startDate != null && endDate != null) {
            mDayCount = TimeUtil.getTimeDistance(startDate, endDate)
        }
        // 自定义设置相关
        vCalendar.setConfig(
            CalendarVerticalConfig.Builder()
                .setShowWeek(true)                   //是否显示星期栏
                .setShowLunar(false)                 //是否显示阴历
                .setColorWeek("#333333")             //星期栏的颜色
                .setTitleFormat("yyyy.MM")           //每个月的标题样式
                .setColorTitle("#000000")            //每个月标题的颜色
                .setColorSolar("#333333")            //阳历的颜色
                .setColorLunar("#00ff00")            //阴历的颜色
                .setColorBeforeToday("#CCCCCC")      //今天之前的日期的颜色
                .setColorRangeBg("#FF5A00")          //区间中间的背景颜色 99FF5A00
                .setColorRangeText("#FFFFFF")        //区间文字的颜色
                .setColorStartAndEndBg("#FF5A00")    //开始结束的背景颜色
                .setCountMonth(3)                    //显示多少月(默认6个月)
                .setStartDate(stringToInt(mStartDate))    // 默认开始日期
                .setEndDate(stringToInt(mEndDate))        // 默认结束日期
                .build()
        )
        // 事件日期集合
        val mEventDates = arrayListOf<CalendarEventModel>()
        //设置日历相关事件
        for (banDate in mBanDateList) {
            val eventModel = CalendarEventModel()
            eventModel.eventDate = banDate.toInt()
            eventModel.eventInfo = "休"
            eventModel.eventBgColor = Color.parseColor("#CCCCCC")
            eventModel.eventTextColor = Color.parseColor("#FF0000")
            mEventDates.add(eventModel)
        }
        //设置事件
        vCalendar.setEventDates(mEventDates)
        // 设置日历是否可点击,默认true
        vCalendar.setDateClickable(mClickable)
    }

    /**
     * 选中日期
     */
    private fun onChooseDate(startDate: Int, endDate: Int) {
        mErrorMsg = ""
        for (eventDate in mEventDates) {
            val eventTime = mSdf.format(eventDate.eventDate).toInt()
            if (eventTime in startDate..endDate) {
                mErrorMsg =
                    getString(R.string.tip_choose_ban_date, mSdf.format(eventDate.eventDate))
                showToast(mErrorMsg)
                return
            }
        }
        mStartDate = startDate.toString()
        mEndDate = endDate.toString()
        val start = mSdf.parse(mStartDate)
        val end = mSdf.parse(mEndDate)
        if (start != null && end != null) {
            mDayCount = TimeUtil.getTimeDistance(start, end)
        }
    }

    /**
     * string转Int
     */
    private fun stringToInt(string: String): Int = try {
        string.toInt()
    } catch (e: Exception) {
        0
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 点击确定
     */
    private fun onClickOk() {
        if (mErrorMsg.isNotEmpty()) {
            showToast(mErrorMsg)
            return
        }
        if (mStartDate.isEmpty() || mEndDate.isEmpty()) {
            showToast(getString(R.string.tip_please_choose_time))
            return
        }
        tvResult.text = getString(R.string.format_result, mStartDate, mEndDate, mDayCount)
    }
}
