package com.example.nutri_factory

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class Contact : AppCompatActivity() {
    private lateinit var imag: ImageView
    private lateinit var image: ImageView
    private lateinit var imageview: ImageView
    private lateinit var im:ImageView
    private lateinit var tt: TextView
    private lateinit var ttt: TextView
    private lateinit var tttt: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        imag=findViewById(R.id.image)
        image=findViewById(R.id.image1)
        imageview=findViewById(R.id.image2)
        tt=findViewById(R.id.tt)
        ttt=findViewById(R.id.ttt)
        tttt=findViewById(R.id.tttt)
        im=findViewById(R.id.im)

        imag.setOnClickListener {
            val url = Uri.parse("https://www.instagram.com/shafia2801/")
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(url)
            startActivity(i)
        }
        tt.setOnClickListener {
            val url = Uri.parse("https://www.instagram.com/shafia2801/")
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(url)
            startActivity(i)
        }
        image.setOnClickListener {
            val url = Uri.parse("https://www.linkedin.com/in/shafia2801")
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(url)
            startActivity(i)
        }
        ttt.setOnClickListener {
            val url = Uri.parse("https://www.linkedin.com/in/shafia2801")
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(url)
            startActivity(i)
        }
        imageview.setOnClickListener {
            val url = Uri.parse("https://mail.google.com/mail/u/0/?fs=1&bcc=shafia2801@gmail.com&tf=cm")
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(url)
            startActivity(i)
        }
        tttt.setOnClickListener {
            val url = Uri.parse("https://mail.google.com/mail/u/0/?fs=1&bcc=shafia2801@gmail.com&tf=cm")
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(url)
            startActivity(i)
        }
        im.setOnClickListener{
            val intent=Intent(this,Home::class.java)
            startActivity(intent)
        }
    }
    }
