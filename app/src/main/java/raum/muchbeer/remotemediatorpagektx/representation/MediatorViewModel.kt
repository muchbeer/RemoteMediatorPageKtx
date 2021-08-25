package raum.muchbeer.remotemediatorpagektx.representation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import raum.muchbeer.remotemediatorpagektx.data.local.CacheModel
import raum.muchbeer.remotemediatorpagektx.repository.pagingflow.RepoRemoteMedDataSource
import javax.inject.Inject

@HiltViewModel
class MediatorViewModel @Inject constructor(
   val repository : RepoRemoteMedDataSource
) : ViewModel(){

    fun retrieveMediatorList() : Flow<PagingData<CacheModel>> {
        return repository.retrieveMergeApis().cachedIn(viewModelScope)
    }
}