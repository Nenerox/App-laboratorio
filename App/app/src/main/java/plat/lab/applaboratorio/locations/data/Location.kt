package plat.lab.applaboratorio.locations.data

data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)

fun Location.mapToEntity(): LocationEntity {
    return LocationEntity(
        id = id,
        name = name,
        type = type,
        dimension = dimension
    )
}