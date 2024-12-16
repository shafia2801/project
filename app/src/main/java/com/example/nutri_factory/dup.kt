package com.example.nutri_factory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class dup : AppCompatActivity() {



        private lateinit var storageReference: StorageReference
        private lateinit var imageView1: ImageView
        private lateinit var imageView2: ImageView
        private lateinit var firestore: FirebaseFirestore

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_dup)

            // Initialize Firebase Storage and Firestore references
            storageReference = FirebaseStorage.getInstance().reference
            firestore = FirebaseFirestore.getInstance()

            // Initialize ImageViews
            imageView1 = findViewById(R.id.imageView1)
            imageView2 = findViewById(R.id.imageView2)

            // Fetch diet data from Firestore and load images
            fetchDietData("bmi_range_18_25") // Replace with appropriate BMI range
        }

        // Method to fetch diet data from Firestore and display images
        private fun fetchDietData(bmiRange: String) {
            val dietRef = firestore.collection("diets").document(bmiRange)

            dietRef.get().addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val foodList = document.get("food") as? List<String> ?: emptyList()
                    val image1Url = document.getString("image")
                    val image2Url = document.getString("image2")

                    // Load images using Glide
                    if (image1Url != null) {
                        loadImageFromStorage(image1Url, imageView1)
                    }
                    if (image2Url != null) {
                        loadImageFromStorage(image2Url, imageView2)
                    }

                    // Optionally, display food items or other data
                    // For example:
                    // textViewFoodList.text = foodList.joinToString("\n")
                } else {
                    Log.d("Firestore", "No such document")
                }
            }.addOnFailureListener { exception ->
                Log.e("Firestore", "Error getting documents", exception)
            }
        }

        // Method to load image from Firebase Storage and display in ImageView
        private fun loadImageFromStorage(imagePath: String, imageView: ImageView) {
            val storageRef = storageReference.child(imagePath.replace("gs://myfirebase-f9939.appspot.com/", ""))

            storageRef.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(this)
                    .load(uri)
                    .into(imageView) // Load image into ImageView
            }.addOnFailureListener { exception ->
                Log.e("Firebase", "Failed to retrieve image URL", exception)
            }
        }
    }
