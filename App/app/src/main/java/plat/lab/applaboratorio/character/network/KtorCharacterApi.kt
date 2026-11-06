package plat.lab.applaboratorio.character.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import plat.lab.applaboratorio.character.data.CharacterDTO

private const val BASE_URL = "https://rickandmortyapi.com/api"
@Serializable
private data class CharacterResponseDTO(
    val results: List<CharacterDTO>
)
class KtorCharacterApi(private val httpClient: HttpClient): CharacterApi {
    override suspend fun getCharacters(): List<CharacterDTO> {
        val response = httpClient.get("$BASE_URL/character")

        if (response.status == HttpStatusCode.OK) {
            return response.body<CharacterResponseDTO>().results
        } else {
            return emptyList()
        }
    }
}