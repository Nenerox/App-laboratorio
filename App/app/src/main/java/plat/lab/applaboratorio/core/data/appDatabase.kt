package plat.lab.applaboratorio.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import plat.lab.applaboratorio.character.local.CharacterDao
import plat.lab.applaboratorio.character.local.CharacterEntity
import plat.lab.applaboratorio.locations.local.LocationDao
import plat.lab.applaboratorio.locations.local.LocationEntity

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