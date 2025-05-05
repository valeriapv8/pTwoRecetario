package dp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Receta(
    var name: String,
    var preparation: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}