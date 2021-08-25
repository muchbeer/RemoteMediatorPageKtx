package raum.muchbeer.remotemediatorpagektx.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import raum.muchbeer.remotemediatorpagektx.data.local.CacheDao
import raum.muchbeer.remotemediatorpagektx.data.local.CacheDatabase
import raum.muchbeer.remotemediatorpagektx.data.local.CacheKeyDao
import raum.muchbeer.remotemediatorpagektx.data.remote.api.DataService
import raum.muchbeer.remotemediatorpagektx.repository.paging.RemoteMediatorDataSource
import raum.muchbeer.remotemediatorpagektx.repository.pagingflow.RepoRemoteMedDataSource
import raum.muchbeer.remotemediatorpagektx.repository.pagingflow.RepoRemoteMedImpl
import javax.inject.Singleton


@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : CacheDatabase {
        return CacheDatabase.cacheInstance(context)     }

    @Singleton
    @Provides
    fun provideCacheDao(dataDB: CacheDatabase) : CacheDao {
        return dataDB.cacheDao()     }

    @Singleton
    @Provides
    fun provideCacheKeyDao(dataDB: CacheDatabase) : CacheKeyDao {
        return dataDB.cacheKeyDao()     }

    @Singleton
    @Provides
    fun provideMediatorSource(dataService: DataService, dataDB: CacheDatabase) :
                       RemoteMediatorDataSource {
        return RemoteMediatorDataSource(dataService, dataDB)
    }

    @Singleton
    @Provides
    fun provideRepositoryMediatorSource(
        dataDB: CacheDatabase, remoteMediator : RemoteMediatorDataSource) : RepoRemoteMedDataSource {
        return RepoRemoteMedImpl(dataDB, remoteMediator)
    }
}