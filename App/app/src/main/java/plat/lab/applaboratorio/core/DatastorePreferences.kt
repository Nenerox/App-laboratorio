package plat.lab.applaboratorio.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")
class DatastorePreferences(private val context: Context){
    private val nombreKey = stringPreferencesKey("nombreKey")
    private val isLoggedKey = booleanPreferencesKey("isLoggedKey")

    fun isLoggedIn(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[isLoggedKey] ?: false
        }
    }

    suspend fun saveNombre(nombre: String) {
        context.dataStore.edit { preferences ->
            preferences[nombreKey] = nombre
            preferences[isLoggedKey] = true
        }
    }

    suspend fun getNombre(): String {
        val preferences = context.dataStore.data.first()
        return preferences[nombreKey] ?: ""
    }

    suspend fun clearSession(){
        context.dataStore.edit { preferences ->
            preferences[isLoggedKey] = false
            preferences[nombreKey] = ""
        }
    }
}