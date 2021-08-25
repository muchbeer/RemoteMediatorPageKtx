package raum.muchbeer.remotemediatorpagektx.repository.pagingflow

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import raum.muchbeer.remotemediatorpagektx.data.local.CacheModel

interface RepoRemoteMedDataSource {
    fun retrieveMergeApis() : Flow<PagingData<CacheModel>>
}