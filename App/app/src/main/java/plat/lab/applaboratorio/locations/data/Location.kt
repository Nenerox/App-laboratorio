package plat.lab.applaboratorio.locations.data

import plat.lab.applaboratorio.locations.local.LocationEntity

data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val isFavorite: Boolean = false
)

fun Location.mapToEntity(): LocationEntity {
    return LocationEntity(
        id = id,
        name = name,
        type = type,
        dimension = dimension,
        isFavorite = isFavorite
    )
}