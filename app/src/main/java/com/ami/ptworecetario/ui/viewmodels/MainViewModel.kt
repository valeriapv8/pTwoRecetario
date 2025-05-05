package com.example.recetaspiola.ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ami.ptworecetario.repositories.RecetaRepository
import dp.models.Ingrediente
import dp.models.Receta
import dp.models.RecetaIngrediente
import kotlinx.coroutines.launch
import repositories.IngredienteRepository

class MainViewModel : ViewModel() {

    private val _ingredientsList: MutableLiveData<ArrayList<Ingrediente>> = MutableLiveData(arrayListOf())
    val ingredientsList: LiveData<ArrayList<Ingrediente>> = _ingredientsList

    private val _selectedIngredients: MutableLiveData<ArrayList<Ingrediente>> = MutableLiveData(arrayListOf())
    val selectedIngredients: LiveData<ArrayList<Ingrediente>> = _selectedIngredients

    private val _recipesList = MutableLiveData<ArrayList<Receta>>(arrayListOf())
    val recipesList: LiveData<ArrayList<Receta>> = _recipesList

    fun loadIngredients(context: Context) {
        viewModelScope.launch {
            val prefs = context.getSharedPreferences("ptworecetario_prefs", Context.MODE_PRIVATE)
            val initialized = prefs.getBoolean("initial_data_inserted", false)

            if (!initialized) {
                createInitialData(context)
                prefs.edit().putBoolean("initial_data_inserted", true).apply()
            }

            val updatedIngredients = IngredienteRepository.getIngredientList(context)
            _ingredientsList.postValue(ArrayList(updatedIngredients))
        }
    }

    fun toggleIngredientSelection(ingredient: Ingrediente) {
        val currentList = ArrayList(_selectedIngredients.value ?: arrayListOf())
        if (currentList.contains(ingredient)) {
            currentList.remove(ingredient)
        } else {
            currentList.add(ingredient)
        }
        _selectedIngredients.value = currentList
    }

    fun isIngredientSelected(ingredient: Ingrediente): Boolean {
        return _selectedIngredients.value?.contains(ingredient) ?: false
    }

    private suspend fun createInitialData(context: Context) {
        val initialIngredients = listOf(
            Ingrediente(name = "Tomate"),
            Ingrediente(name = "Cebolla"),
            Ingrediente(name = "Aceite"),
            Ingrediente(name = "Sal"),
            Ingrediente(name = "Pimienta"),
            Ingrediente(name = "Queso"),
            Ingrediente(name = "Pollo"),
            Ingrediente(name = "Arroz"),
            Ingrediente(name = "Carne"),
            Ingrediente(name = "Papa"),
            Ingrediente(name = "Fideo"),
            Ingrediente(name = "Lenteja")
        )

        val allIngredients = IngredienteRepository.getIngredientList(context).toMutableList()

        initialIngredients.forEach { ingredient ->
            if (allIngredients.none { it.name == ingredient.name }) {
                IngredienteRepository.insertIngredient(context, ingredient)
            }
        }

        val updatedIngredients = IngredienteRepository.getIngredientList(context)
        allIngredients.clear()
        allIngredients.addAll(updatedIngredients)

        val initialRecipes = listOf(
            Receta(name = "Escalope de pollo", preparation = "Condimentar pollo con sal, pimienta y aceite. Cocinar en sartén."),
            Receta(name = "Pollo a la plancha", preparation = "Condimentar pollo con sal, pimienta y aceite. Cocinar en sartén."),
            Receta(name = "Papas fritas", preparation = "Cortar papas en tiras, freír en aceite caliente y salar."),
            Receta(name = "Sopa de pollo", preparation = "Hervir pollo con cebolla y sal. Agregar agua y cocinar."),
            Receta(name = "Arroz a la valenciana", preparation = "Cocinar arroz. Aparte, saltear pollo con cebolla y mezclar todo."),
            Receta(name = "Spaghetti", preparation = "Cocinar espagueti en agua hirviendo con sal. Escurrir y servir."),
            Receta(name = "Arroz con carne", preparation = "Cocinar arroz. Aparte, saltear carne con cebolla y mezclar todo."),
            Receta(name = "Papas al horno", preparation = "Cortar papas, condimentar con sal, pimienta y aceite. Hornear.")
        )

        val existingRecipes = RecetaRepository.getAllRecipes(context)

        initialRecipes.forEach { recipe ->
            if (existingRecipes.none { it.name == recipe.name }) {
                val recipeId = RecetaRepository.insertRecipe(context, recipe).toInt()

                val ingredientsForRecipe = when (recipe.name) {
                    "Escalope de pollo" -> listOf("Pollo", "Aceite", "Sal", "Pimienta")
                    "Pollo a la plancha" -> listOf("Pollo", "Aceite", "Sal", "Pimienta")
                    "Arroz a la valenciana" -> listOf("Arroz", "Pollo", "Aceite", "Sal")
                    "Arroz con carne" -> listOf("Arroz", "Carne", "Cebolla", "Aceite", "Sal")
                    "Papas fritas" -> listOf("Papa", "Aceite", "Sal")
                    "Sopa de pollo" -> listOf("Pollo", "Cebolla", "Aceite", "Sal")
                    "Papas al horno" -> listOf("Papa", "Aceite", "Sal", "Pimienta")
                    "Spaghetti" -> listOf("Fideo", "Aceite", "Sal", "Pimienta")
                    else -> listOf()
                }

                ingredientsForRecipe.forEach { ingredientName ->
                    val foundIngredient = allIngredients.find { it.name == ingredientName }
                    foundIngredient?.let { ingredient ->
                        RecetaRepository.insertRecipeIngredientCrossRef(
                            context,
                            RecetaIngrediente(
                                recipeId = recipeId,
                                ingredientId = ingredient.id
                            )
                        )
                    }
                }
            }
        }
    }
}
