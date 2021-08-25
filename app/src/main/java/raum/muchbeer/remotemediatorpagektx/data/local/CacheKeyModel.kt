package raum.muchbeer.remotemediatorpagektx.data.local

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "cache_key_tbl")
data class CacheKeyModel(
    @PrimaryKey
    val cache_id : Long,
    val prevKey : Int?,
    val nextKey : Int?
)