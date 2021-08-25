package raum.muchbeer.remotemediatorpagektx.data.local

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "api_tbl")
data class CacheModel(
    val body: String,
    val cacheId: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val note: String,
    val status: String,
    val title: String,
)
