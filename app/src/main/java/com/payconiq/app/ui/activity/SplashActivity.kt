package com.payconiq.app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.payconiq.app.R
import com.payconiq.app.ui.activity.user.list.UserListActivity
import kotlinx.coroutines.Runnable

class SplashActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())
    private var runnable : Runnable = Runnable { navigateToUserList() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


            handler.apply {
                postDelayed(runnable, 3000)
            }


    }

    private fun navigateToUserList() {

        startActivity(Intent(this, UserListActivity::class.java))
        handler.removeCallbacks(runnable)
        finish()
        Log.e("","")
    }
}