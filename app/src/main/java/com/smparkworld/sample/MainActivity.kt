package com.smparkworld.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.smparkworld.parkdatetimepicker.ParkDateTimePicker
import com.smparkworld.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        binding.btnShow.setOnClickListener {
            ParkDateTimePicker.builder(this)
                .setDateTimeListener { dateTime ->
                    Log.d("Test!!", "dateTime: $dateTime")
                }
                .show()
        }
    }
}