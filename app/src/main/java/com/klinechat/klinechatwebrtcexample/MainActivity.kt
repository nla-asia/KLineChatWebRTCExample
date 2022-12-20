package com.klinechat.klinechatwebrtcexample

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.klinechat.klinechatwebrtcexample.databinding.MainActivityBinding

// imports from library
import com.klinechat.webrtcsdk.MainViewModel
import com.klinechat.webrtcsdk.util.requestNeededPermissions


class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = MainActivityBinding.inflate(layoutInflater)

        val urlString = "wss://webrtc.klinechat.com"
        val tokenString = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ2aWRlbyI6eyJyb29tSm9pbiI6dHJ1ZSwicm9vbSI6IllvbG8iLCJjYW5QdWJsaXNoIjp0cnVlLCJjYW5TdWJzY3JpYmUiOnRydWV9LCJpYXQiOjE2NzE1NDczMTUsIm5iZiI6MTY3MTU0NzMxNSwiZXhwIjoxNjcxNTY4OTE1LCJpc3MiOiJBUEk0WENrdVdndlFnVWUiLCJzdWIiOiIwOTc5MDU1Mjc3OSIsImp0aSI6IjA5NzkwNTUyNzc5In0.S9-0iFPFlqh7hb4utowYA_7b4mps87wSBxP80Pr43dE"


        binding.run {

            deviceId.text = SpannableStringBuilder("Your deviceID : $urlString")

            connectButton.setOnClickListener {
                val intent = Intent(this@MainActivity, CallActivity::class.java).apply {
                    putExtra(
                        CallActivity.KEY_ARGS,
                        CallActivity.BundleArgs(
                            urlString,
                            tokenString
                        )
                    )
                }

                startActivity(intent)
            }



        }

        setContentView(binding.root)

        requestNeededPermissions()
    }
}