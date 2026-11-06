package plat.lab.applaboratorio.character.data

import kotlinx.serialization.Serializable
import plat.lab.applaboratorio.Character
import plat.lab.applaboratorio.character.local.CharacterEntity

@Serializable
data class CharacterDTO(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String
)

fun CharacterDTO.mapToModel(): Character {
    return Character(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        image = image
    )
}

fun CharacterDTO.mapToEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        image = image
    )
}