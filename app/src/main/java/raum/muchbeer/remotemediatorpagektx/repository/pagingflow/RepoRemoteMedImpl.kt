package raum.muchbeer.remotemediatorpagektx.repository.pagingflow

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import raum.muchbeer.remotemediatorpagektx.data.local.CacheDatabase
import raum.muchbeer.remotemediatorpagektx.data.local.CacheModel
import raum.muchbeer.remotemediatorpagektx.data.remote.api.DataService
import raum.muchbeer.remotemediatorpagektx.repository.paging.RemoteMediatorDataSource

@ExperimentalPagingApi
class RepoRemoteMedImpl(
    val dataDB:  CacheDatabase,
    val remoteMediatorPageSource : RemoteMediatorDataSource
    ) : RepoRemoteMedDataSource{


    override fun retrieveMergeApis(): Flow<PagingData<CacheModel>> {
        return Pager(
            config = defaultConfig(),
            remoteMediator = remoteMediatorPageSource,
            pagingSourceFactory = {
                dataDB.cacheDao().retrieveApis()
            }
        ).flow
       }

    private fun defaultConfig() : PagingConfig {
        return PagingConfig(
            pageSize = 10,
            prefetchDistance = 10,
            enablePlaceholders = false,
            initialLoadSize = 30,
            maxSize = 50
        )
    }
}