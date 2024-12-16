package com.example.nutri_factory

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class VegNormalWeightActivity : AppCompatActivity() {


    private lateinit var storageReference: StorageReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_veg_normal_weight)

        val scrollView: LinearLayout = findViewById(R.id.scrollViewContent)

        // Initialize Firebase Storage reference
        storageReference = FirebaseStorage.getInstance().reference

        val foodItems = listOf(
            FoodItem("Smoothie Bowl", "gs://myfirebase-f9939.appspot.com/smoothie_bowl.jpg", "Breakfast: 1 bowl with banana, spinach, and almond milk"),
            FoodItem("Oatmeal with Fruits", "gs://myfirebase-f9939.appspot.com/oatmeal.jpg", "Breakfast: 1 cup cooked oatmeal topped with berries and nuts"),
            FoodItem("Chickpea Salad", "gs://myfirebase-f9939.appspot.com/chickpea_salad.jpg", "Lunch: 1 cup chickpeas, diced cucumber, tomatoes, and olive oil"),
            FoodItem("Vegetable Stir-Fry", "gs://myfirebase-f9939.appspot.com/tofu_stir_fry.jpg", "Lunch: 2 cups mixed vegetables stir-fried with tofu"),
            FoodItem("Quinoa Bowl", "gs://myfirebase-f9939.appspot.com/quinoa_bowl.jpg", "Lunch: 1 cup quinoa topped with roasted vegetables"),
            FoodItem("Vegetable Soup", "gs://myfirebase-f9939.appspot.com/vegetable_soup.jpg", "Dinner: 1 bowl of vegetable soup with whole grain bread"),
            FoodItem("Pasta with Marinara Sauce", "gs://myfirebase-f9939.appspot.com/pasta_marinara.jpg", "Dinner: 1 cup whole wheat pasta with marinara sauce and vegetables"),
            FoodItem("Cottage Cheese with Pineapple", "gs://myfirebase-f9939.appspot.com/cottage_cheese.jpg", "Snack: 1 cup cottage cheese topped with pineapple chunks"),
            FoodItem("Trail Mix", "gs://myfirebase-f9939.appspot.com/trail_mix.jpg", "Snack: 1/4 cup mixed nuts and dried fruits")
        )


        // Populate ScrollView with food items
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

