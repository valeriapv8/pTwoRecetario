package com.ami.ptworecetario.repositories

import android.content.Context
import com.ami.ptworecetario.db.AppDatabase
import com.ami.ptworecetario.db.models.RecetaConIngredientes
import dp.models.Receta
import dp.models.RecetaIngrediente

object RecetaRepository {

    suspend fun insertRecipe(context: Context, recipe: Receta): Long {
        val db = AppDatabase.getInstance(context)
        return db.recipeDao().insertRecipe(recipe)
    }

    suspend fun insertRecipeIngredientCrossRef(
        context: Context,
        crossRef: RecetaIngrediente
    ) {
        val db = AppDatabase.getInstance(context)
        db.recipeDao().insertRecipeIngredientCrossRef(crossRef)
    }

    suspend fun getRecipeWithIngredients(
        context: Context,
        recipeId: Int
    ): RecetaConIngredientes {
        val db = AppDatabase.getInstance(context)
        return db.recipeDao().getRecipeWithIngredients(recipeId)
    }

    suspend fun updateRecipe(context: Context, recipe: Receta) {
        val db = AppDatabase.getInstance(context)
        db.recipeDao().updateRecipe(recipe)
    }

    suspend fun deleteRecipe(context: Context, recipe: Receta) {
        val db = AppDatabase.getInstance(context)
        db.recipeDao().deleteRecipe(recipe)
    }

    suspend fun getAllRecipes(context: Context): List<Receta> {
        val db = AppDatabase.getInstance(context)
        return db.recipeDao().getAllRecipes()
    }
}