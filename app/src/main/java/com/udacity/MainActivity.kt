package com.udacity

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0
    private var NotificationId = 0
    private var name = ""
    private  val channelId = "channelId"
    private  val channelName = "channelName"


    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createChanel(
            channelId,
            channelName
        )

        custom_button.setOnClickListener {
            if (radioGroup.checkedRadioButtonId != -1) {
                val checkedRadioButtonId = radioGroup.checkedRadioButtonId
                checkButton(findViewById(checkedRadioButtonId))
                // change status of button
                custom_button.setButtonStatus(ButtonState.Clicked)
            } else {
                Toast.makeText(this, "Please select a file", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkButton(view: View) {
//        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
//            val radio = findViewById<RadioButton>(i)
//            when (radio.id) {
//                R.id.glide_btn -> {
//                    Toast.makeText(this, "Glide", Toast.LENGTH_SHORT).show()
//                    URL = "https://github.com/bumptech/glide/archive/refs/heads/master.zip"
//                    name = "Glide"
//                    download()
//                }
//                R.id.picasso_btn -> {
//                    Toast.makeText(this, "Picasso", Toast.LENGTH_SHORT).show()
//                    URL = "https://github.com/square/picasso/archive/refs/heads/master.zip"
//                    name = "Picasso"
//                    download()
//                }
//                R.id.circle_btn -> {
//                    Toast.makeText(this, "Circle", Toast.LENGTH_SHORT).show()
//                    URL = "https://github.com/hdodenhof/CircleImageView/archive/refs/heads/master.zip"
//                    name = "Circle"
//                    download()
//                }
//            }
//        }
        if (view.id == R.id.circle_btn){
            Toast.makeText(this, "Wait ...", Toast.LENGTH_SHORT).show()
                    URL = "https://github.com/hdodenhof/CircleImageView/archive/refs/heads/master.zip"
                    name = "Circle"
                    download()


        }

        if (view.id == R.id.glide_btn){
            Toast.makeText(this, "Wait ...", Toast.LENGTH_SHORT).show()
                    URL = "https://github.com/bumptech/glide/archive/refs/heads/master.zip"
                    name = "Glide"
                    download()
            }

        if (view.id == R.id.picasso_btn) {
            Toast.makeText(this, "Wait ...", Toast.LENGTH_SHORT).show()
                    URL = "https://github.com/square/picasso/archive/refs/heads/master.zip"
                    name = "Picasso"
                    download()
        }
    }


    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (id == downloadID) {
                notificationManager.sendNotification("Download Completed", applicationContext
                ,"success",name)
                custom_button.setButtonStatus(ButtonState.Completed)
            }
        }
    }
    private fun download() {
        val request =
            DownloadManager.Request(Uri.parse(URL))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)

        custom_button.setButtonStatus(ButtonState.Loading)
        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.


    }

    private fun createChanel(channelId: String, channelName: String) {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             val notificationChannel =NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
             notificationChannel.enableLights(true)
             notificationChannel.lightColor = Color.RED
             notificationChannel.enableVibration(true)
             notificationManager.createNotificationChannel(notificationChannel)

         }
    }

    companion object {
        private var URL =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
    }


}
