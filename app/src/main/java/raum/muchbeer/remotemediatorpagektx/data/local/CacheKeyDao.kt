package raum.muchbeer.remotemediatorpagektx.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CacheKeyDao {

 @Insert(onConflict = OnConflictStrategy.REPLACE)
 suspend fun insertCacheKey(data : List<CacheKeyModel>)

 @Query("SELECT * FROM cache_key_tbl WHERE cache_id =:mCache_id")
 suspend fun retrieveCacheKey(mCache_id : Int) : CacheKeyModel

 @Query("DELETE FROM cache_key_tbl")
 suspend fun clearAllCache()
}