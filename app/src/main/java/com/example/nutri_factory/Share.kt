package com.example.nutri_factory

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView



class Share : AppCompatActivity() {
    private lateinit var imm:ImageView

    private lateinit var cardView: CardView
    private var isFlipped = false
    private val handler = Handler()
    private val flipInterval: Long = 3000
    private val flipRunnable = object : Runnable {
        override fun run() {
            if (isFlipped) {
                cardView.startAnimation(AnimationUtils.loadAnimation(this@Share, R.anim.flip_card_back_to_front))
                isFlipped = false
            } else {
                cardView.startAnimation(AnimationUtils.loadAnimation(this@Share, R.anim.flip_card_front_to_back))
                isFlipped = true
            }
            handler.postDelayed(this, flipInterval) // Repeat after the specified interval
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        // Initialize the card view for flipping animation
        cardView = findViewById(R.id.CardView)
        imm=findViewById(R.id.imm)
        handler.post(flipRunnable) // Start the flipping animation

        // Setup the share button listener in onCreate
        val buttonShareAppLink: Button = findViewById(R.id.buttonShareAppLink)



        imm.setOnClickListener{
            val intent=Intent(this,Home::class.java)
            startActivity(intent)
        }


        buttonShareAppLink.setOnClickListener {
            Toast.makeText(this, "Share button clicked", Toast.LENGTH_SHORT).show()

            // The Google Drive link to your app's APK
            val apkDownloadLink = "https://drive.google.com/file/d/1fSSg_M9zEVi9kKdPPslppKPCCmWjvEAC/view?usp=sharing"
            shareAppLink("Check out our app: $apkDownloadLink")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop the flip animation to prevent memory leaks
        handler.removeCallbacks(flipRunnable)
    }

    private fun shareAppLink(link: String) {
        val appLinkIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, link)
        }
        startActivity(Intent.createChooser(appLinkIntent, "Share app link"))
    }
}