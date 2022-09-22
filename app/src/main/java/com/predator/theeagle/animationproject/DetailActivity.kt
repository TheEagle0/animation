package com.predator.theeagle.animationproject

import android.app.NotificationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*

const val DETAIL_ACTIVITY_KEY = "Status"
const val DETAIL_ACTIVITY_FILENAME_KEY = "FileName"

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.cancelAll()
        setDataFromIntent()
        buttonClick()
    }

    private fun buttonClick() {
        button.setOnClickListener {
            finish()
        }
    }

    private fun setDataFromIntent() {
        val fileName = intent.getStringExtra(DETAIL_ACTIVITY_KEY)
        fileNameValue.text = fileName

        val status = intent.getStringExtra(DETAIL_ACTIVITY_FILENAME_KEY)
        statusValue.text = status
    }
}
