package com.ami.ptworecetario.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dp.dao.IngredienteDao
import dp.dao.RecetaDao
import dp.models.Ingrediente
import dp.models.Receta
import dp.models.RecetaIngrediente

@Database(
    entities = [
        Ingrediente::class,
        Receta::class,
        RecetaIngrediente::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ingredientDao(): IngredienteDao
    abstract fun recipeDao(): RecetaDao

    companion object {
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "recipes-db"
            ).build()
        }
    }
}