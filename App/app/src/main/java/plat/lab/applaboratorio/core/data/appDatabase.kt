package plat.lab.applaboratorio.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import plat.lab.applaboratorio.character.data.CharacterDao
import plat.lab.applaboratorio.character.data.CharacterEntity
import plat.lab.applaboratorio.locations.data.LocationDao
import plat.lab.applaboratorio.locations.data.LocationEntity

@Database(
    entities = [
        CharacterEntity::class,
        LocationEntity::class
    ],
    version = 1
)
abstract class appDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun locationDao(): LocationDao
}