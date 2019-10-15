package com.example.mycalendar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 日历demo
 * @author ssq
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvSuccessiveTimeActivity.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SuccessiveTimeActivity::class.java
                )
            )
        }
        tvDiscontinuousTimeActivity.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    DiscontinuousTimeActivity::class.java
                )
            )
        }
    }
}
