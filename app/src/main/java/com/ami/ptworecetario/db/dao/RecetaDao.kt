package dp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.ami.ptworecetario.db.models.RecetaConIngredientes
import dp.models.Receta
import dp.models.RecetaIngrediente

@Dao
interface RecetaDao {

    @Insert
    suspend fun insertRecipe(recipe: Receta): Long

    @Insert
    suspend fun insertRecipeIngredientCrossRef(crossRef: RecetaIngrediente)

    @Transaction
    @Query("SELECT * FROM Receta WHERE id = :recipeId")
    suspend fun getRecipeWithIngredients(recipeId: Int): RecetaConIngredientes

    @Update
    suspend fun updateRecipe(recipe: Receta)

    @Delete
    suspend fun deleteRecipe(recipe: Receta)

    @Query("SELECT * FROM Receta")
    suspend fun getAllRecipes(): List<Receta>
}