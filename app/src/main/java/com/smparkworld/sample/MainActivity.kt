package com.smparkworld.sample

import android.os.Bundle
import android.widget.Toast
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
        binding.btnShowDate.setOnClickListener {
            showDatePicker()
        }
        binding.btnShowTime.setOnClickListener {
            showTimePicker()
        }
        binding.btnShowDateTime.setOnClickListener {
            showDateTimePicker()
        }
    }

    private fun showDatePicker() {
        ParkDateTimePicker.builder(this)
            .setDateListener { date ->
                Toast.makeText(this, date.toString(), Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun showTimePicker() {
        ParkDateTimePicker.builder(this)
            .setTimeListener { time ->
                Toast.makeText(this, time.toString(), Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun showDateTimePicker() {
        ParkDateTimePicker.builder(this)
            .setDateTimeListener { dateTime ->
                Toast.makeText(this, dateTime.toString(), Toast.LENGTH_SHORT).show()
            }
            .show()
    }
}