package raum.muchbeer.remotemediatorpagektx.representation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import raum.muchbeer.remotemediatorpagektx.data.local.CacheModel
import raum.muchbeer.remotemediatorpagektx.repository.pagingflow.RepoRemoteMedDataSource
import timber.log.Timber
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class MediatorViewModel @Inject constructor(
   val repository : RepoRemoteMedDataSource
) : ViewModel(){

    private val _searchItemState = MutableStateFlow<String>("")


    fun retrieveMediatorList() : Flow<PagingData<CacheModel>> {
        return repository.retrieveMergeApis().cachedIn(viewModelScope)
    }

    fun setSearchQuery(search: String) {
        _searchItemState.value = search
    }


    val searchListLiveData : Flow<PagingData<CacheModel>> = _searchItemState
            .debounce(300)
            .distinctUntilChanged()
            .flatMapLatest { search ->

                 repository.searchApis(search)
    } .catch { throwable ->
            Timber.d("The error occured is : ${throwable.message}")
        }
}