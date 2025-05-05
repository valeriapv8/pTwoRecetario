package com.ami.ptworecetario.ui.activities

import android.os.Bundle
import com.ami.ptworecetario.R
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ami.ptworecetario.databinding.ActivityRecetaDetailBinding

class RecetaDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecetaDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRecetaDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detail)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val name = intent.getStringExtra("RECIPE_NAME") ?: "Sin nombre"
        val preparation = intent.getStringExtra("RECIPE_PREPARATION") ?: "Sin preparación"
        val ingredients = intent.getStringExtra("RECIPE_INGREDIENTS")?.split(", ")
            ?.joinToString("\n") { "- $it" } ?: "Sin ingredientes"

        binding.textDetailRecipeName.text = name
        binding.textDetailIngredients.text = ingredients
        binding.textDetailCook.text = preparation
    }
}