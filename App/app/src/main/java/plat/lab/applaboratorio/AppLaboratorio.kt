package plat.lab.applaboratorio

import android.app.Application
import androidx.room.Room
import io.ktor.client.HttpClient
import plat.lab.applaboratorio.character.data.CharacterRepositoryImpl
import plat.lab.applaboratorio.character.domain.CharacterRepository
import plat.lab.applaboratorio.character.network.CharacterApi
import plat.lab.applaboratorio.character.network.KtorCharacterApi
import plat.lab.applaboratorio.core.data.HttpClientFactory
import plat.lab.applaboratorio.core.data.appDatabase
import plat.lab.applaboratorio.locations.data.LocationRepositoryImpl
import plat.lab.applaboratorio.locations.domain.LocationRepository
import plat.lab.applaboratorio.locations.network.KtorLocationApi
import plat.lab.applaboratorio.locations.network.LocationApi

class AppLaboratorio : Application() {

    val database: appDatabase by lazy {
        Room.databaseBuilder(
            this,
            appDatabase::class.java,
            "laboratorio.db"
        ).build()
    }

    val httpClient: HttpClient by lazy {
        HttpClientFactory.create()
    }

    val characterApi: CharacterApi by lazy {
        KtorCharacterApi(httpClient)
    }

    val CharacterRepository: CharacterRepository by lazy {
        CharacterRepositoryImpl(
            api = characterApi,
            dao = database.characterDao()
        )
    }

    val locationApi: LocationApi by lazy {
        KtorLocationApi(httpClient)
    }

    val LocationRepository: LocationRepository by lazy {
        LocationRepositoryImpl(
            api = locationApi,
            dao = database.locationDao()
        )
    }

}