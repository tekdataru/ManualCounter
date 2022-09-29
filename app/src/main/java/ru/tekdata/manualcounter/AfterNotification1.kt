package ru.tekdata.manualcounter

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.tekdata.manualcounter.databinding.ActivityAfterNotification1Binding


class AfterNotification1 : AppCompatActivity() {

    companion object {
        const val NOTIFICATION_ID = 10111
        const val NOTIFICATION_CHANNEL_ID = "channelID121212_remm"
        const val NOTIFICATION_CHANNEL_NAME_TEKDATA = "channelID121212_remm"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAfterNotification1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        //Toast.makeText(this, "2222222222222222", Toast.LENGTH_LONG).show()

        binding.button.setOnClickListener{
            System.exit(0)
        }

        val intent = Intent(this, MainActivity::class.java)
        // To pass any data to next activity
        intent.putExtra("keyIdentifier", 1)
        // start your next activity


        var actNumbCount = 0
        val prefs = getSharedPreferences("tekdata_ru", Context.MODE_PRIVATE)
        val tekdataManCount = prefs.getString("tekdata_manual_counter", "0")

        actNumbCount = tekdataManCount.toString().toInt() + 1

        //actNumbCount++

        prefs.edit().apply {
            putString("tekdata_manual_counter", actNumbCount.toString())
            commit()
        }

        showTekdataNotif("Tekdata 1 now count: " + actNumbCount)
        //System.exit(0)


        //Thread.sleep(100)
        finish()
        //startActivity(intent)

    }

    fun showTekdataNotif( contentText : String = "Пора покормить кота"){
        //Работаем с уведомлениями+
        val intent = Intent(this, AfterNotification2::class.java)
        intent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            //Toast.makeText(this@MainActivity, "adfasdf", Toast.LENGTH_LONG).show()
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val contentView = RemoteViews(packageName, R.layout.activity_after_notification2)
        //Работаем с уведомлениями-


        // Создаём уведомление+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = MainActivity.NOTIFICATION_CHANNEL_NAME_TEKDATA
            val descriptionText = MainActivity.NOTIFICATION_CHANNEL_NAME_TEKDATA
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(MainActivity.NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        val notif = NotificationCompat.Builder(this, MainActivity.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_back_hand_24)
            //.setContent(contentView)
            //.setContentTitle("Напоминание")
            .setContentText(contentText)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(MainActivity.NOTIFICATION_ID, notif)
        println("!!! " + "кот")
        //Thread.sleep(100)



    }
}

