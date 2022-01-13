package raum.muchbeer.remotemediatorpagektx.repository.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import raum.muchbeer.remotemediatorpagektx.data.local.CacheDatabase
import raum.muchbeer.remotemediatorpagektx.data.local.CacheKeyModel
import raum.muchbeer.remotemediatorpagektx.data.local.CacheMapper
import raum.muchbeer.remotemediatorpagektx.data.local.CacheModel
import raum.muchbeer.remotemediatorpagektx.data.remote.api.DataService
import raum.muchbeer.remotemediatorpagektx.data.remote.model.DbPagingModel
import raum.muchbeer.remotemediatorpagektx.data.remote.model.DtOMapper
import raum.muchbeer.remotemediatorpagektx.data.remote.model.DtOResponse
import raum.muchbeer.remotemediatorpagektx.data.remote.model.PagingModel
import java.io.InvalidObjectException

@ExperimentalPagingApi
class RemoteMediatorDataSource(
   val dataService: DataService,
  val  dataDb : CacheDatabase
) : RemoteMediator<Int, CacheModel>(), DtOMapper<DtOResponse, PagingModel>,
            CacheMapper<DbPagingModel, CacheModel>{
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CacheModel>
    ): MediatorResult {

        val page = when(loadType) {
            /*PagingData content being refreshed, which can be a result of PagingSource invalidation,
            refresh that may contain content updates, or the initial load.*/
            LoadType.REFRESH -> {
                val keys = getKeyForClosestToCurrentItem(state)
                keys?.nextKey?.minus(1) ?: 1
            }
            /*Load at the start of a PagingData.*/
            LoadType.PREPEND -> {
                val keys = getKeyForFirstItem(state) ?:
                  return MediatorResult.Error(InvalidObjectException("No first Item"))

                 val prevKey = keys.prevKey ?:
                            return MediatorResult.Success(endOfPaginationReached = true)
                prevKey
            }
            /*Load at the end of a PagingData.*/
            LoadType.APPEND -> {
                val keys = getKeyForLastItem(state) ?:
                    return MediatorResult.Error(InvalidObjectException("No last item"))

                val nextKey = keys.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                nextKey
            }
        }

      return  try {
            val response = dataService.retrieveData(pageNum = page)
            if (response.isSuccessful) {
                val pagingData = mapFromResponseToModel(response.body()!!)

                dataDb.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        dataDb.cacheDao().clearAllApi()
                        dataDb.cacheKeyDao().clearAllCache()
                    }

                    val prevKey = if (page==1) null else page -1
                    val nextKey = if (pagingData.endOfPage) null else page + 1

                    val keysList = pagingData.data.map {
                        CacheKeyModel(
                            cache_id = it.id.toLong(),
                            prevKey = prevKey,
                            nextKey = nextKey
                        )
                    }

                    dataDb.cacheKeyDao().insertCacheKey(keysList)
                    dataDb.cacheDao().insertApi(mapFromDtoModelToCacheModel(pagingData.data))
                }

                 MediatorResult.Success(pagingData.endOfPage)
            } else return MediatorResult.Error(InvalidObjectException(response.message()))
        } catch (e : Exception) {
            MediatorResult.Error(e)
        }

    }

    override fun mapFromDtoModelToCacheModel(model: List<DbPagingModel>): List<CacheModel> {
            return model.map {
                CacheModel(
                    cacheId = it.id.toLong(),
                    body = it.body,
                    note = it.note,
                    status = it.status,
                    title = it.title
                )
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

    private suspend fun getKeyForFirstItem(state: PagingState<Int, CacheModel>) : CacheKeyModel? {
        return state.pages.firstOrNull() {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let {
            dataDb.cacheKeyDao().retrieveCacheKey(it.cacheId.toInt())
        }
    }

    private suspend fun getKeyForLastItem(state: PagingState<Int, CacheModel>) : CacheKeyModel? {
        return state.pages.lastOrNull() {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let {
            dataDb.cacheKeyDao().retrieveCacheKey(it.cacheId.toInt())
        }
    }

    private suspend fun getKeyForClosestToCurrentItem(state: PagingState<Int, CacheModel>) : CacheKeyModel? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.cacheId?.let {
                dataDb.cacheKeyDao().retrieveCacheKey(it.toInt())
            }
        }
    }
}