package ui.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ami.ptworecetario.db.models.RecetaConIngredientes
import com.ami.ptworecetario.repositories.RecetaRepository
import kotlinx.coroutines.launch

class RecetaViewModel : ViewModel() {
    private val _filteredRecipes = MutableLiveData<List<RecetaConIngredientes>>()
    val filteredRecipes: LiveData<List<RecetaConIngredientes>> = _filteredRecipes

    fun loadRecipesWithIngredients(context: Context, ingredientIds: List<Int>) {
        viewModelScope.launch {
            val allRecipes = RecetaRepository.getAllRecipes(context)
            val recipesWithIngredients = allRecipes.map { recipe ->
                RecetaRepository.getRecipeWithIngredients(context, recipe.id)
            }

            val filteredList = recipesWithIngredients.filter { recipeWithIngredients ->
                ingredientIds.all { selectedId ->
                    recipeWithIngredients.ingredients.any { ingredient ->
                        ingredient.id == selectedId
                    }
                }
            }

            _filteredRecipes.postValue(filteredList)
        }
    }
}
