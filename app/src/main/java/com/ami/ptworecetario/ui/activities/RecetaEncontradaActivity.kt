package com.ami.ptworecetario.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ami.ptworecetario.databinding.ActivityRecetaEncontradaBinding
import com.example.recetaspiola.ui.adapters.RecetaAdapter
import ui.viewmodels.RecetaViewModel

class RecetaEncontradaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecetaEncontradaBinding
    private val viewModel: RecetaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecetaEncontradaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedIngredientIds = intent.getIntegerArrayListExtra("SELECTED_INGREDIENTS_IDS") ?: arrayListOf()

        setupRecyclerView()
        setupObservers()
        setupAddRecipeButton()

        viewModel.loadRecipesWithIngredients(this, selectedIngredientIds)
    }

    private fun setupRecyclerView() {
        binding.rvListRecetasEncontradas.layoutManager = LinearLayoutManager(this)
        binding.rvListRecetasEncontradas.adapter = RecetaAdapter(arrayListOf()) { receta ->
            val intent = Intent(this, RecetaDetailActivity::class.java).apply {
                putExtra("RECIPE_NAME", receta.recipe.name)
                putExtra("RECIPE_PREPARATION", receta.recipe.preparation)
                putExtra("RECIPE_INGREDIENTS", receta.ingredients.joinToString(", ") { it.name })
            }
            startActivity(intent)
        }
    }

    private fun setupObservers() {
        viewModel.filteredRecipes.observe(this) { recetas ->
            if (recetas.isEmpty()) {
                val intent = Intent(this, NoexisteRecetaActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                (binding.rvListRecetasEncontradas.adapter as RecetaAdapter).setData(recetas)
            }
        }
    }

    private fun setupAddRecipeButton() {
        binding.btnAgregarReceta.setOnClickListener {
            val intent = Intent(this, AddRecetaActivity::class.java)
            intent.putIntegerArrayListExtra("SELECTED_INGREDIENT_IDS", intent.getIntegerArrayListExtra("SELECTED_INGREDIENT_IDS"))
            startActivity(intent)
        }
    }
}
