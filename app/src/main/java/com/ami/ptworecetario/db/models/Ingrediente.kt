package dp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ingrediente(
    var name: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}