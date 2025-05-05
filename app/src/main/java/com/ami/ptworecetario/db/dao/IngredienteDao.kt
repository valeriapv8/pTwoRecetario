package dp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dp.models.Ingrediente

@Dao
interface IngredienteDao {
    @Query("Select * from Ingrediente")
    suspend fun getAllIngredients(): List<Ingrediente>

    @Query("Select * from Ingrediente where id = :id")
    suspend fun getIngredientById(id: Int): Ingrediente

    @Insert
    suspend fun insertIngredient(ingredient: Ingrediente): Long

    @Update
    suspend fun updateIngredient(ingredient: Ingrediente)

    @Delete
    suspend fun deleteIngredient(ingredient: Ingrediente)
}

