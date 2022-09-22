package ru.tekdata.manualcounter

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import ru.tekdata.manualcounter.databinding.ActivityMainBinding
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    companion object {
        const val NOTIFICATION_ID = 10111
        const val CHANNEL_ID = "channelID121212_remm"
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

            // Создаём уведомление
            val notif = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("Напоминание")
                .setContentText("Пора покормить кота")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            val notificationManager = NotificationManagerCompat.from(this)
            notificationManager.notify(NOTIFICATION_ID, notif)
            println("!!! " + "кот")

            // или
//            with(NotificationManagerCompat.from(this)) {
//                notify(NOTIFICATION_ID, builder.build()) // посылаем уведомление
//            }

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