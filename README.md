# calendar
一个垂直方向的日历选择控件，用于酒店APP入住时间选择。(Kotlin）

[![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)

下载[**DEMO**](https://github.com/sange93/calendar/releases) 体检效果

## 使用说明
### 1、引入
- 方式一：JCenter 引入（推荐）
``` gradle
implementation ''
```
- 方式二：导入aar包到项目lib下
下载 [aar](https://github.com/sange93/calendar/blob/master/file/calendar.aar)
### 两种功能场景
- 场景一：连续的日期选择

![连续的日期选择](https://github.com/sange93/calendar/blob/master/file/video_successive.gif)

示例代码 [SuccessiveTimeActivity.kt](https://github.com/sange93/calendar/blob/master/app/src/main/java/com/example/mycalendar/SuccessiveTimeActivity.kt)
```kotlin
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
```

- 场景二：非连续的日期选择

![非连续的日期选择](https://github.com/sange93/calendar/blob/master/file/video_discontinuous.gif)

示例代码 [DiscontinuousTimeActivity.kt](https://github.com/sange93/calendar/blob/master/app/src/main/java/com/example/mycalendar/DiscontinuousTimeActivity.kt)
```kotlin
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
```