package com.klinechat.klinechatwebrtcexample


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.klinechat.klinechatwebrtcexample.api.RetrofitInstance
import com.klinechat.klinechatwebrtcexample.call.CallActivity
import com.klinechat.klinechatwebrtcexample.databinding.ActivityContactDetailsBinding
import com.squareup.picasso.Picasso
import retrofit2.HttpException
import java.io.IOException

class ContactDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContactDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val did: String = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        val name: String = intent.getStringExtra("contact_name").toString()
        val id: String = intent.getStringExtra("contact_id").toString()
        val contactName = findViewById<TextView>(R.id.contactName)
        val callBtn = findViewById<Button>(R.id.callContact)
        if(id==did){
            contactName.text = name+" ( Current Device _)"
            callBtn.text = "--"
        }else{
            contactName.text = name

            callBtn.setOnClickListener {


                lifecycleScope.launchWhenCreated {

                    val response = try {
                        val callData = CallContactRequest(id, did)
                        RetrofitInstance.api.callContact(callData)
                    } catch(e: IOException){
                        return@launchWhenCreated
                    } catch (e: HttpException) {
                        return@launchWhenCreated
                    }

                    if(response.isSuccessful && response.body() != null){
                        val resp = response.body()!!
                        Log.d("CALL", "call successful")

                        if(resp.room_token!=null) {
                            val intent = Intent(this@ContactDetailsActivity, CallActivity::class.java).apply {
                                putExtra(
                                    CallActivity.KEY_ARGS,
                                    CallActivity.BundleArgs(
                                        "",
                                        resp.room_token
                                    )
                                )
                            }

                            startActivity(intent)
                        }

                    }else{
                        Log.d("CALL", "call failed")
                            Toast.makeText(
                                this@ContactDetailsActivity,
                                "Server error ! ",
                                Toast.LENGTH_LONG
                            ).show()

                    }

                }

            }
        }
        val contactImg = findViewById<ImageView>(R.id.contactImage)
        val url = "https://i.pravatar.cc/600"
        Picasso.get().load(url).into(contactImg)



    }
}