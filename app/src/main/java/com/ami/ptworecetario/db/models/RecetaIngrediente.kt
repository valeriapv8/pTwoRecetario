package dp.models

import androidx.room.Entity

@Entity(primaryKeys = ["recipeId", "ingredientId"])
data class RecetaIngrediente(
    val recipeId: Int,
    val ingredientId: Int
)
