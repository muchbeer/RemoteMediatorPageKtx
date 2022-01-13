package raum.muchbeer.remotemediatorpagektx.repository.pagingflow

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import raum.muchbeer.remotemediatorpagektx.data.remote.model.DbPagingModel
import raum.muchbeer.remotemediatorpagektx.data.remote.model.PagingModel
import raum.muchbeer.remotemediatorpagektx.repository.paging.RemoteDataSource
import javax.inject.Inject

class RepositoryImp (
    private val remotePageSource : RemoteDataSource
) : RepositoryDataSource {

    override fun retrieveApis(): Flow<PagingData<DbPagingModel>> {
     return Pager(
         config = defaultConfig(),
         pagingSourceFactory = { remotePageSource }
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