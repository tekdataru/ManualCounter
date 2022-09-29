package ru.tekdata.manualcounter

import android.R.attr.value
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.tekdata.manualcounter.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    companion object {
        const val NOTIFICATION_ID = 10111
        const val NOTIFICATION_CHANNEL_ID = "channelID121212_remm"
        const val NOTIFICATION_CHANNEL_NAME_TEKDATA = "channelID121212_remm"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("tekdata_ru", Context.MODE_PRIVATE)
        prefs.getString("tekdata_manual_counter", "0")?.let { binding.editText.setText(it) }





        binding.buttonUp.setOnClickListener {
            val buf = binding.editText.text.toString().toInt() + 1
            binding.editText.setText(buf.toString())

                prefs.edit().apply{
                    putString("tekdata_manual_counter", buf.toString())
                    commit()
                }


            //Работаем с уведомлениями+
            val intent = Intent(this, AfterNotification1::class.java)
            intent.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                //Toast.makeText(this@MainActivity, "adfasdf", Toast.LENGTH_LONG).show()
            }

            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val contentView = RemoteViews(packageName, R.layout.activity_after_notification1)
            //Работаем с уведомлениями-


            // Создаём уведомление+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = NOTIFICATION_CHANNEL_NAME_TEKDATA
                val descriptionText = NOTIFICATION_CHANNEL_NAME_TEKDATA
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
                val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)
            }

            val notif = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_back_hand_24)
                //.setContent(contentView)
                //.setContentTitle("Напоминание")
                //.setContentText("Пора покормить кота")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .build()

            val notificationManager = NotificationManagerCompat.from(this)
            notificationManager.notify(NOTIFICATION_ID, notif)
            println("!!! " + "кот")

            // или
//            with(NotificationManagerCompat.from(this)) {
//                notify(NOTIFICATION_ID, builder.build()) // посылаем уведомление
//            }

            // Создаём уведомление-

        }

        binding.buttonDown.setOnClickListener {
            val buf = binding.editText.text.toString().toInt() - 1
            binding.editText.setText(buf.toString())

            prefs.edit().apply {
                putString("tekdata_manual_counter", buf.toString())
                commit()
            }
        }

        binding.buttonClear.setOnClickListener {
            if (binding.buttonClear.text.toString() == "0") {
                binding.buttonClear.text = "??"
                return@setOnClickListener
            }

            binding.buttonClear.text = "0"

            val buf = 0
            binding.editText.setText(buf.toString())

            prefs.edit().apply {
                putString("tekdata_manual_counter", buf.toString())
                commit()
            }
        }

        binding.buttonClose.setOnClickListener{
            System.exit(0)
        }

    }


}

fun sendPushLikeTest(someContext: Context?, textToNotify: String){
    //Эта функция показывает тост и уведомление по нажатию на лайк
    //Пока что отключим ее
    //return

    //Toast.makeText(someContext, "Like on post (id ${textToNotify})!", Toast.LENGTH_LONG).show()
    val channelId = "remote"

    val sccc = (someContext as Context)
    sccc.let {

    }

    val notification = NotificationCompat.Builder(someContext, channelId)
        .setSmallIcon(ru.tekdata.manualcounter.R.drawable.ic_baseline_back_hand_24)
        .setContentTitle("It is Like! (or unlike)")
        .setContentText(textToNotify)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()

    NotificationManagerCompat.from(someContext)
        .notify(101, notification)



}