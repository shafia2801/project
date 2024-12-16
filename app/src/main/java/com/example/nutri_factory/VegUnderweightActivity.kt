package com.example.nutri_factory

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class VegUnderweightActivity : AppCompatActivity() {

    private lateinit var storageReference: StorageReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_veg_underweight)

        val scrollView: LinearLayout = findViewById(R.id.scrollViewContent)

        // Initialize Firebase Storage reference
        storageReference = FirebaseStorage.getInstance().reference

        val foodItems = listOf(
            FoodItem("Avocado Toast", "gs://myfirebase-f9939.appspot.com/Avocado Toast.jpg", "Breakfast: 1 slice whole grain bread with 1/2 avocado"),
            FoodItem("Smoothie Bowl", "gs://myfirebase-f9939.appspot.com/smoothie_bowl.jpg", "Breakfast: 1 cup yogurt, 1 banana, 1/2 cup mixed berries, topped with nuts"),
            FoodItem("Peanut Butter Banana Sandwich", "gs://myfirebase-f9939.appspot.com/peanut_butter_banana.jpg", "Snack: 2 slices whole grain bread, 2 tbsp peanut butter, 1 banana"),
            FoodItem("Chickpea Salad", "gs://myfirebase-f9939.appspot.com/chickpea_salad.jpg", "Lunch: 1 cup chickpeas, diced cucumber, tomatoes, and olive oil"),
            FoodItem("Quinoa and Black Bean Bowl", "gs://myfirebase-f9939.appspot.com/quinoa_black_bean.jpg", "Lunch: 1 cup quinoa, 1 cup black beans, diced bell peppers"),
            FoodItem("Vegetable Stir-Fry with Tofu", "gs://myfirebase-f9939.appspot.com/tofu_stir_fry.jpg", "Dinner: 1 cup mixed vegetables, 1/2 cup tofu, served with brown rice"),
            FoodItem("Pasta with Pesto and Spinach", "gs://myfirebase-f9939.appspot.com/pasta_pesto.jpg", "Dinner: 1 cup whole wheat pasta, 2 tbsp pesto, 1 cup spinach"),
            FoodItem("Nut and Seed Trail Mix", "gs://myfirebase-f9939.appspot.com/trail_mix.jpg", "Snack: 1/4 cup mixed nuts and seeds"),
            FoodItem("Cottage Cheese with Fruits", "gs://myfirebase-f9939.appspot.com/cottage_cheese.jpg", "Snack: 1 cup cottage cheese, topped with 1/2 cup fruits")
        )


        for (foodItem in foodItems) {
            val itemView = layoutInflater.inflate(R.layout.item_food, scrollView, false)
            val imageViewFood: ImageView = itemView.findViewById(R.id.imageViewFood)
            val textViewFoodName: TextView = itemView.findViewById(R.id.textViewFoodName)
            val textViewFoodQuantity: TextView = itemView.findViewById(R.id.textViewFoodQuantity)

            textViewFoodName.text = foodItem.name
            textViewFoodQuantity.text = foodItem.quantity

            // Load image using Glide
            loadImageFromStorage(foodItem.imageUrl, imageViewFood)

            scrollView.addView(itemView)
        }
    }

    // Method to load image from Firebase Storage and display in ImageView
    private fun loadImageFromStorage(imagePath: String, imageView: ImageView) {
        val storageRef =
            storageReference.child(imagePath.replace("gs://myfirebase-f9939.appspot.com/", ""))

        storageRef.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(this)
                .load(uri)
                .into(imageView) // Load image into ImageView
        }.addOnFailureListener { exception ->
            Log.e("Firebase", "Failed to retrieve image URL", exception)
        }
    }
}

// Data class updated to use String for image URL
//  data class FoodItem(val name: String, val imageUrl: String, val quantity: String)
