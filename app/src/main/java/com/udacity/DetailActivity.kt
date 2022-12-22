package com.udacity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        var status = intent.getStringExtra("status")
        var name = intent.getStringExtra("name")
        name_text.text = name
        success_text_view.text = status
        back_button.setOnClickListener {
            finish()
        }

    }

}
