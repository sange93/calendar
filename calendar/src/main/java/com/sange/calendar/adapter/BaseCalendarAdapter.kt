package com.sange.calendar.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sange.calendar.LunarCalendarUtils
import com.sange.calendar.R
import com.sange.calendar.model.BaseItem
import com.sange.calendar.model.CalendarDay
import com.sange.calendar.model.CalendarMonth
import com.sange.calendar.model.CalendarVerticalConfig
import kotlinx.android.synthetic.main.calendar_item_day.view.*
import kotlinx.android.synthetic.main.calendar_item_month.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 日历适配器基类
 *
 * @author ssq
 */
abstract class BaseCalendarAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // 类型：天
    val typeDay = 0
    // 类型：月标题
    val typeMonthHead = 1

    //日期格式化:yyyyMMdd
    val yyyyMMdd = SimpleDateFormat("yyyyMMdd", Locale.CHINA)
    private val layoutInflater by lazy { LayoutInflater.from(context) }
    // 数据集
    val dataList: MutableList<BaseItem> = arrayListOf()
    // 是否可点击
    var clickable = true
    // 今天的日期 yyyyMMdd Int类型方便判断范围
    private val nowDate by lazy { yyyyMMdd.format(Date()).toInt() }
    protected var config: CalendarVerticalConfig = CalendarVerticalConfig.Builder().build()
        private set
    // 日历
    private val calendar = Calendar.getInstance()

    open fun setConfig(config: CalendarVerticalConfig) {
        this.config = config
    }

    override fun getItemViewType(position: Int): Int = dataList[position].itemType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> DayViewHolder(layoutInflater.inflate(R.layout.calendar_item_day, parent, false))
            else -> MonthHeadViewHolder(layoutInflater.inflate(R.layout.calendar_item_month, parent, false))
        }
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataList[position]
        if (holder is DayViewHolder && data is CalendarDay) {
            holder.bindData(data)
        } else if (holder is MonthHeadViewHolder && data is CalendarMonth) {
            holder.bindData(data, position)
        }
    }

    /**
     * 点击Item
     */
    abstract fun onClickItem(position: Int, itemDay: CalendarDay)

    /**
     * 绑定数据 天item
     */
    abstract fun bindDataOfDay(itemView: View, itemDay: CalendarDay)

    /**
     * 天
     */
    inner class DayViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            // 点击日历Item
            itemView.setOnClickListener(View.OnClickListener {
                val date = (dataList[adapterPosition] as CalendarDay)
                //必须大于今天
                if (date.date < nowDate) {
                    Toast.makeText(context, R.string.calendar_not_before_today, Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                onClickItem(adapterPosition, date)
                //刷新UI
                notifyDataSetChanged()
            })
        }

        fun bindData(data: CalendarDay) {
            with(itemView) {
                //不是本月的隐藏
                if (!data.isCurrentMonth) {
                    visibility = View.INVISIBLE
                    return@with
                }
                visibility = View.VISIBLE
                // 初始化布局
                iv_day_bg.visibility = View.GONE
                tv_day.setTextColor(config.colorSolar)
                tv_day_small.setTextColor(config.colorLunar)
                calendar.time = yyyyMMdd.parse(data.date.toString()) ?: calendar.time
                tv_day.text = calendar.get(Calendar.DAY_OF_MONTH).toString()
                //阴历的显示
                tv_day_small.visibility = if (config.isShowLunar) {
                    val lunarDayString = LunarCalendarUtils.getLunarDayString(data.lunar.lunarDay)
                    tv_day_small.text = lunarDayString
                    View.VISIBLE
                } else {
                    View.GONE
                }
                val itemDate = data.date
                //判断是不是当天
                if (nowDate == itemDate) {
                    tv_day.text = "今天"
                } else if (nowDate > itemDate) {// 小于今天的颜色变灰
                    tv_day.setTextColor(config.colorBeforeToday)
                }
                bindDataOfDay(this, data)
                //修改事件日期的颜色
                if (data.eventModel != null && itemDate >= nowDate) {
                    iv_day_bg.visibility = View.VISIBLE
                    //动态修改颜色
                    (iv_day_bg.background as GradientDrawable).setColor(data.eventModel!!.eventBgColor)
                }
                this.isClickable = clickable
            }
        }
    }

    /**
     * 月标题
     */
    inner class MonthHeadViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(data: CalendarMonth, position: Int) {
            with(itemView) {
                tv_month_title.text = data.title
                v_month_line.visibility = if (position == 0) View.GONE else View.VISIBLE
            }
        }
    }
}