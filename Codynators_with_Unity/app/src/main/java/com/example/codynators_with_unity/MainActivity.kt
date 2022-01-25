package com.example.codynators_with_unity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.unity3d.player.UnityPlayerActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        unityBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, UnityPlayerActivity::class.java))
        }
    }
}