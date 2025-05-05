package com.ami.ptworecetario.ui.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.ami.ptworecetario.R
import com.ami.ptworecetario.databinding.ActivityAddRecetaBinding
import com.ami.ptworecetario.ui.viewmodels.AddRecetaViewModel
import kotlinx.coroutines.launch
import repositories.IngredienteRepository

class AddRecetaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRecetaBinding
    private val viewModel: AddRecetaViewModel by viewModels()

    private val selectedIngredients = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddRecetaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnAddIngredient.setOnClickListener {
            openIngredientsDialog()
        }

        binding.btnSaveRecipe.setOnClickListener {
            saveRecipe()
        }
    }

    private fun openIngredientsDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.item_ingrediente, null)

        val ingredientesContainer = dialogView.findViewById<LinearLayout>(R.id.ingredientesContainer)
        val etNuevoIngrediente = dialogView.findViewById<EditText>(R.id.etNuevoIngrediente)
        val btnAgregarIngrediente = dialogView.findViewById<Button>(R.id.btnAgregarIngrediente)
        val btnGuardarIngrediente = dialogView.findViewById<Button>(R.id.btnGuardarIngrediente)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        btnAgregarIngrediente.setOnClickListener {
            val isVisible = etNuevoIngrediente.visibility == EditText.VISIBLE
            etNuevoIngrediente.visibility = if (isVisible) EditText.GONE else EditText.VISIBLE
            btnGuardarIngrediente.visibility = if (isVisible) Button.GONE else Button.VISIBLE
        }

        etNuevoIngrediente.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                btnGuardarIngrediente.isEnabled = editable?.toString()?.isNotBlank() == true
            }
        })


        btnGuardarIngrediente.setOnClickListener {
            val nuevoIngrediente = etNuevoIngrediente.text.toString().trim()
            if (nuevoIngrediente.isNotEmpty()) {
                addIngredientCheckBox(ingredientesContainer, nuevoIngrediente)
                selectedIngredients.add(nuevoIngrediente)

                etNuevoIngrediente.setText("")
                etNuevoIngrediente.visibility = EditText.GONE
                btnGuardarIngrediente.visibility = Button.GONE
                btnGuardarIngrediente.isEnabled = false
            } else {
                Toast.makeText(this, "El ingrediente no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launch {
            val ingredients = IngredienteRepository.getIngredientList(this@AddRecetaActivity)

            for (ingredient in ingredients) {
                addIngredientCheckBox(ingredientesContainer, ingredient.name)
            }
        }

        dialog.show()
    }


    private fun addIngredientCheckBox(container: LinearLayout, ingredientName: String) {
        val checkBox = CheckBox(this)
        checkBox.text = ingredientName

        if (selectedIngredients.contains(ingredientName)) {
            checkBox.isChecked = true
        }

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (!selectedIngredients.contains(ingredientName)) {
                    selectedIngredients.add(ingredientName)
                }
            } else {
                selectedIngredients.remove(ingredientName)
            }
        }

        container.addView(checkBox)
    }

    private fun saveRecipe() {
        val recipeName = binding.etRecipeName.text.toString().trim()
        val preparation = binding.etPreparation.text.toString().trim()

        if (recipeName.isEmpty() || preparation.isEmpty() || selectedIngredients.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.saveRecipe(this, recipeName, preparation, selectedIngredients) {
            Toast.makeText(this, "Receta guardada con éxito", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

}
