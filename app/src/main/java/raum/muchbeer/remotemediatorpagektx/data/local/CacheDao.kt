package raum.muchbeer.remotemediatorpagektx.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import raum.muchbeer.remotemediatorpagektx.data.remote.model.PagingModel

@Dao
interface CacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApi(data : List<CacheModel>)

    @Query("SELECT * FROM api_tbl ORDER by id ASC")
    fun retrieveApis() : PagingSource<Int, CacheModel>

    @Query("DELETE FROM api_tbl")
    suspend fun clearAllApi()
}