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


            checkButton()

        }
    }

    private fun checkButton() {
        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            val radio = findViewById<RadioButton>(i)
            when(radio){
                glide_btn ->{
                    Toast.makeText(this, "Glide", Toast.LENGTH_SHORT).show()
                    URL = "https://github.com/bumptech/glide/archive/refs/heads/master.zip"
                    name = "Glide"
                    download()
                }
                picasso_btn ->{
                    Toast.makeText(this, "Picasso", Toast.LENGTH_SHORT).show()
                    URL = "https://github.com/square/picasso/archive/refs/heads/master.zip"
                    name = "Picasso"
                    download()
                }
                circle_btn ->{
                    Toast.makeText(this, "Circle", Toast.LENGTH_SHORT).show()
                    URL = "https://github.com/hdodenhof/CircleImageView/archive/refs/heads/master.zip"
                    name = "Circle"
                    download()
                }

            }
        }
    }


    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (id == downloadID) {
                notificationManager.sendNotification("Download Completed", applicationContext
                ,"success",name)
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
        private const val CHANNEL_ID = "channelId"

    }


}
