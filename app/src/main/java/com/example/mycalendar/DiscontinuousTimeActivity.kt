package com.example.mycalendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sange.calendar.adapter.CalendarMultipleAdapter
import com.sange.calendar.model.CalendarVerticalConfig
import kotlinx.android.synthetic.main.activity_discontinuous_time.*

/**
 * 非连续的日期选择
 * @author ssq
 */
class DiscontinuousTimeActivity : AppCompatActivity() {
    private val mBanDateList = arrayListOf<String>()// 禁止日期

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discontinuous_time)
        tvOk.setOnClickListener { tvResult.text = mBanDateList.toString() }
        initCalendar()
    }

    /**
     * 初始化日历
     */
    private fun initCalendar() {
        vCalendar.initViews(CalendarMultipleAdapter(this))// 设置多选适配器
        vCalendar.setOnCalendarChooseListener(object :
            CalendarMultipleAdapter.OnCalendarMultipleChooseListener {
            override fun onClickDate(date: Int) {
                onClickDate(date.toString())
            }
        })
        val banDateList = arrayListOf<Int>()
        for (banDate in mBanDateList) {
            banDateList.add(banDate.toInt())
        }
        // 自定义设置相关
        vCalendar.setConfig(
            CalendarVerticalConfig.Builder()
                .setShowWeek(true)                   //是否显示星期栏
                .setShowLunar(false)                  //是否显示阴历
                .setColorWeek("#333333")             //星期栏的颜色
                .setTitleFormat("yyyy.MM")           //每个月的标题样式
                .setColorTitle("#000000")            //每个月标题的颜色
                .setColorSolar("#333333")            //阳历的颜色
                .setColorLunar("#00ff00")            //阴历的颜色
                .setColorBeforeToday("#CCCCCC")      //今天之前的日期的颜色
                .setColorRangeBg("#FF5A00")        //区间中间的背景颜色 99FF5A00
                .setColorRangeText("#FFFFFF")        //区间文字的颜色
                .setColorStartAndEndBg("#FF5A00")    //开始结束的背景颜色
                .setCountMonth(3)                    //显示多少月(默认6个月)
                .setSelectedDateList(banDateList)// 默认选中日期
                .build()
        )
        // 设置日历是否可点击,默认true
        vCalendar.setDateClickable(true)
    }

    /**
     * 点击日期
     */
    private fun onClickDate(date: String) {
        if (mBanDateList.contains(date)) {
            mBanDateList.remove(date)
        } else {
            mBanDateList.add(date)
        }
    }
}
