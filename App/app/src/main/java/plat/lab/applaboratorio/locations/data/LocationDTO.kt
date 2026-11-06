package plat.lab.applaboratorio.locations.data

import kotlinx.serialization.Serializable
import plat.lab.applaboratorio.locations.local.LocationEntity

@Serializable
data class LocationDTO(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)

fun LocationDTO.mapToModel(): Location {
    return Location(
        id = id,
        name = name,
        type = type,
        dimension = dimension
    )
}

fun LocationDTO.mapToEntity(): LocationEntity {
    return LocationEntity(
        id = id,
        name = name,
        type = type,
        dimension = dimension
    )
}