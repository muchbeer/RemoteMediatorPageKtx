package raum.muchbeer.remotemediatorpagektx.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import raum.muchbeer.remotemediatorpagektx.data.remote.api.DataInstance
import raum.muchbeer.remotemediatorpagektx.data.remote.api.DataService
import raum.muchbeer.remotemediatorpagektx.repository.paging.RemoteDataSource
import raum.muchbeer.remotemediatorpagektx.repository.pagingflow.RepositoryDataSource
import raum.muchbeer.remotemediatorpagektx.repository.pagingflow.RepositoryImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideDataCollectionService() : DataService {
        return DataInstance().dataInstance()
    }

    @Singleton
    @Provides
    fun provideRemotePagingSource(dataService: DataService) : RemoteDataSource {
        return RemoteDataSource(dataService)
    }

    @Singleton
    @Provides
    fun provideRemoteRemoteDataSource(remoteDataSource: RemoteDataSource) : RepositoryDataSource {
        return RepositoryImp(remoteDataSource)
    }

}