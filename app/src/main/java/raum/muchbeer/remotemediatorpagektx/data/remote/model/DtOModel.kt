package raum.muchbeer.remotemediatorpagektx.data.remote.model

import com.google.gson.annotations.SerializedName

data class DtOModel(
    @SerializedName("body")
    val body: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("note")
    val note: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updated_at: String,
    @SerializedName("user_id")
    val user_id: String
)