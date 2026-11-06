package plat.lab.applaboratorio.locations.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import plat.lab.applaboratorio.locations.data.LocationDTO

private const val BASE_URL = "https://rickandmortyapi.com/api"
@Serializable
private data class LocationResponseDTO(
    val results: List<LocationDTO>
)
class KtorLocationApi(private val httpClient: HttpClient): LocationApi {
    override suspend fun getLocations(): List<LocationDTO> {
        val response = httpClient.get("$BASE_URL/location")

        if (response.status == HttpStatusCode.OK) {
            return response.body<LocationResponseDTO>().results
        } else {
            return emptyList()
        }
    }
}
