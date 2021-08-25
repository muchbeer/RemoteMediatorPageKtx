package raum.muchbeer.remotemediatorpagektx.representation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import raum.muchbeer.remotemediatorpagektx.repository.pagingflow.RepositoryDataSource
import raum.muchbeer.remotemediatorpagektx.repository.pagingflow.RepositoryImp
import javax.inject.Inject

@HiltViewModel
class RemoteViewModel @Inject constructor(repository: RepositoryDataSource) : ViewModel() {

    val retrieveApi = repository.retrieveApis().cachedIn(viewModelScope)
}