package plat.lab.applaboratorio.locations.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)


fun LocationEntity.mapToModel(): Location {
    return Location(
        id = id,
        name = name,
        type = type,
        dimension = dimension
    )
}
