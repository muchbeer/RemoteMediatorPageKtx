package raum.muchbeer.remotemediatorpagektx.data.remote.model


data class PagingModel(
  val total_page : Int = 0,
  val current_page : Int = 0,
  val data : List<DbPagingModel>
) {

    val endOfPage = total_page == current_page

}
data class DbPagingModel(
    val body: String,
    val created_at: String,
    val id: String,
    val note: String,
    val status: String,
    val title: String,
    val updated_at: String,
    val user_id: String
)