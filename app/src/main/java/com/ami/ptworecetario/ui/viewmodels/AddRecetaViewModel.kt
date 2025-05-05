package com.ami.ptworecetario.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ami.ptworecetario.repositories.RecetaRepository
import dp.models.Ingrediente
import dp.models.Receta
import dp.models.RecetaIngrediente
import kotlinx.coroutines.launch
import repositories.IngredienteRepository

class AddRecetaViewModel : ViewModel() {

    fun saveRecipe(
        context: Context,
        recipeName: String,
        preparation: String,
        ingredientsNames: List<String>,
        onRecipeSaved: () -> Unit
    ) {
        viewModelScope.launch {
            val recipe = Receta(name = recipeName, preparation = preparation)
            val recipeId = RecetaRepository.insertRecipe(context, recipe).toInt()

            ingredientsNames.forEach { ingredientName ->
                val formattedName = ingredientName.replaceFirstChar { it.uppercase() }

                var ingredient = IngredienteRepository.getIngredientList(context)
                    .find { it.name.equals(formattedName, true) }

                if (ingredient == null) {
                    ingredient = Ingrediente(name = formattedName)
                    IngredienteRepository.insertIngredient(context, ingredient)
                    ingredient = IngredienteRepository.getIngredientList(context)
                        .first { it.name.equals(formattedName, true) }
                }

                RecetaRepository.insertRecipeIngredientCrossRef(
                    context,
                    RecetaIngrediente(recipeId = recipeId, ingredientId = ingredient.id)
                )
            }

            onRecipeSaved()
        }
    }
}
