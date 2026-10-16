package plat.lab.applaboratorio.core.data

import android.content.Context
import androidx.room.Room

object Dependencias {
    lateinit var database: appDatabase

    fun buildDatabase(context: Context) {
        database = Room.databaseBuilder(
            context,
            appDatabase::class.java,
            "laboratorio.db"
        ).build()
    }

}