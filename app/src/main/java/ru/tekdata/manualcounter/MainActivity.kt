package ru.tekdata.manualcounter

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.putString
import ru.tekdata.manualcounter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("tekdata_ru", Context.MODE_PRIVATE)


        prefs.getString("tekdata_manual_counter", "0")?.let { binding.editText.setText(it) }




        binding.buttonUp.setOnClickListener {
            val buf = binding.editText.text.toString().toInt()
            binding.editText.setText((buf + 1).toString())


                prefs.edit().apply{
                    putString("tekdata_manual_counter", (buf + 1).toString())
                    commit()

                }

        }

        binding.buttonDown.setOnClickListener {
            val buf = binding.editText.text.toString().toInt()
            binding.editText.setText((buf - 1).toString())
        }

    }
}