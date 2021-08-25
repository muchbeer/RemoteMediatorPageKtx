package raum.muchbeer.remotemediatorpagektx.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import raum.muchbeer.remotemediatorpagektx.data.remote.model.PagingModel

@Database(entities = [CacheModel::class, CacheKeyModel::class], version = 1, exportSchema = false)
abstract class CacheDatabase : RoomDatabase() {

    abstract fun cacheDao() : CacheDao
    abstract fun cacheKeyDao() : CacheKeyDao
    companion object {

        private var INSTANCE: CacheDatabase? = null

        fun cacheInstance(context: Context): CacheDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CacheDatabase::class.java,
                        "todo_list_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }

    }

}