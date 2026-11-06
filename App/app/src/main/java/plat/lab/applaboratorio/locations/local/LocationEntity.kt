package plat.lab.applaboratorio.locations.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import plat.lab.applaboratorio.locations.data.Location

@Entity
data class LocationEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val isFavorite: Boolean = false
)


fun LocationEntity.mapToModel(): Location {
    return Location(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        isFavorite = isFavorite
    )
}
