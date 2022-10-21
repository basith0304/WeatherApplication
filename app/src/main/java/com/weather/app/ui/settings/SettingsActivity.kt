package com.weather.app.ui.settings

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.weather.app.R
import com.weather.app.common.Notification
import com.weather.app.databinding.ActivitySettingsBinding
import com.weather.app.ui.weather.WeatherViewModel
import java.util.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding= ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onResume() {
        super.onResume()

        binding.metric.setOnClickListener{
            binding.timePicker.visibility = View.GONE
            bottomSheetDialog()
        }

        binding.notification.setOnClickListener{
            binding.timePicker.visibility = ViewGroup.VISIBLE
        }

        binding.timePicker.setOnTimeChangedListener{ _, hour, minute -> var hour = hour
            var am_pm = ""
            // AM_PM decider logic
            when {hour == 0 -> { hour += 12
                am_pm = "AM"
            }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> { hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }
            if (binding.txtValueSchedule != null) {
                val hour = if (hour < 10) hour else hour
                val min = if (minute < 10) "0$minute" else minute
                // display format of time
                val msg = "$hour:$min $am_pm"
                binding.txtValueSchedule.text = msg
                binding.txtValueSchedule.visibility = ViewGroup.VISIBLE
            }
        }
    }

    private fun bottomSheetDialog(){
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_metric, null)
        val btnClose = view.findViewById<ImageButton>(R.id.imageButton)
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun showAlert(time: Long, title: String, message: String)
    {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title: " + title +
                        "\nMessage: " + message +
                        "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date))
            .setPositiveButton("Okay"){_,_ ->}
            .show()
    }

}