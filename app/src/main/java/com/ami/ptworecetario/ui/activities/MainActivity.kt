package com.ami.ptworecetario.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ami.ptworecetario.R
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.ami.ptworecetario.databinding.ActivityMainBinding
import com.example.recetaspiola.ui.adapters.IngredienteAdapter
import com.example.recetaspiola.ui.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private val viewmodel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()

        setupViewModelObservers()

        viewmodel.loadIngredients(this)

        binding.btnSearchReceta.setOnClickListener {
            val selectedIngredientsIds = viewmodel.selectedIngredients.value?.map { it.id } ?: arrayListOf()

            if (selectedIngredientsIds.isEmpty()) {
                Toast.makeText(this, "Por favor selecciona al menos un ingrediente", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, RecetaEncontradaActivity::class.java).apply {
                    putIntegerArrayListExtra("SELECTED_INGREDIENTS_IDS", ArrayList(selectedIngredientsIds))
                }
                startActivity(intent)
            }
        }
    }

    private fun setupViewModelObservers() {
        viewmodel.ingredientsList.observe(this) {
            val adapter = binding.rvIngredientList.adapter as IngredienteAdapter
            adapter.setData(it)
        }

        viewmodel.selectedIngredients.observe(this) { selectedList ->
            Log.d("Selected Ingredients", selectedList.toString())
            binding.rvIngredientList.adapter?.notifyDataSetChanged()
        }
    }

    private fun setupRecyclerView() {
        val adapter = IngredienteAdapter(
            ingredients = arrayListOf(),
            onIngredientClick = { ingredient ->
                viewmodel.toggleIngredientSelection(ingredient)
            },
            isIngredientSelected = { ingredient ->
                viewmodel.isIngredientSelected(ingredient)
            }
        )

        binding.rvIngredientList.apply {
            this.adapter = adapter
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

}