package com.ami.ptworecetario.db.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import dp.models.Ingrediente
import dp.models.Receta
import dp.models.RecetaIngrediente

data class RecetaConIngredientes(
    @Embedded val recipe: Receta,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(RecetaIngrediente::class, parentColumn = "recipeId", entityColumn = "ingredientId")
    )
    val ingredients: List<Ingrediente>
)