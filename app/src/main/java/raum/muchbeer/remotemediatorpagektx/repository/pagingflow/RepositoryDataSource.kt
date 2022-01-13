package raum.muchbeer.remotemediatorpagektx.repository.pagingflow

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import raum.muchbeer.remotemediatorpagektx.data.remote.model.DbPagingModel
import raum.muchbeer.remotemediatorpagektx.data.remote.model.PagingModel

interface RepositoryDataSource {
    fun retrieveApis() : Flow<PagingData< DbPagingModel>>
}