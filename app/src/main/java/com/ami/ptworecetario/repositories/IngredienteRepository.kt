package repositories

import android.content.Context
import com.ami.ptworecetario.db.AppDatabase
import dp.models.Ingrediente

object IngredienteRepository {
    suspend fun insertIngredient(
        context: Context,
        ingredient: Ingrediente
    ) {
        val db = AppDatabase.getInstance(context)
        db.ingredientDao().insertIngredient(ingredient)
    }

    suspend fun getIngredientList(context: Context): List<Ingrediente> {
        return AppDatabase
            .getInstance(context)
            .ingredientDao()
            .getAllIngredients()
    }
}
