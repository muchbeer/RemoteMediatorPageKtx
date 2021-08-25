package raum.muchbeer.remotemediatorpagektx.data.remote.api

import io.reactivex.rxjava3.core.Single
import raum.muchbeer.remotemediatorpagektx.BuildConfig
import raum.muchbeer.remotemediatorpagektx.data.remote.model.DtOResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface DataService {

    @Headers(BuildConfig.HEADERS)
    @GET(BuildConfig.END_POINT)
    suspend fun retrieveData(
        @Query("page")  pageNum : Int
    ) : Response<DtOResponse>

    @GET(BuildConfig.END_POINT)
    fun retrieveDataRx(
        @Query("page") pageNum: Int
    ) : Single<DtOResponse>
}