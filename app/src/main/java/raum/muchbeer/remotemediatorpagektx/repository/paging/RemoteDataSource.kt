package raum.muchbeer.remotemediatorpagektx.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import raum.muchbeer.remotemediatorpagektx.data.remote.api.DataService
import raum.muchbeer.remotemediatorpagektx.data.remote.model.DbPagingModel
import raum.muchbeer.remotemediatorpagektx.data.remote.model.DtOMapper
import raum.muchbeer.remotemediatorpagektx.data.remote.model.DtOResponse
import raum.muchbeer.remotemediatorpagektx.data.remote.model.PagingModel
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException
import javax.inject.Inject

class RemoteDataSource (val dataService: DataService) :
    PagingSource<Int, DbPagingModel>(),
           DtOMapper<DtOResponse, PagingModel> {


    override fun getRefreshKey(state: PagingState<Int, DbPagingModel>): Int? {
            //IN case of refresh key then get the recent access position
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DbPagingModel> {

        val currentPage = params.key ?: 1

      return  try {

          val response = dataService.retrieveData(currentPage)
       if (response.isSuccessful) {
           val dataModel = mapFromResponseToModel(response.body()!!)
           LoadResult.Page(
               data = dataModel.data,
               prevKey = if (currentPage == 1) null else currentPage - 1,
               nextKey = if (dataModel.endOfPage) null else currentPage + 1
           )
       }
          else  LoadResult.Error(InvalidObjectException(response.message()))
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun mapFromResponseToModel(response: DtOResponse): PagingModel {

        return with(response) {
            PagingModel(
                total_page = last_page,
                current_page = current_page,
                data = data.map {
                    DbPagingModel(
                        id = it.id,
                        body = it.body,
                        created_at = it.created_at,
                        note = it.note,
                        status = it.status,
                        title = it.title,
                        updated_at = it.updated_at,
                        user_id = it.user_id
                    )
                }
            )
        }

    }
}