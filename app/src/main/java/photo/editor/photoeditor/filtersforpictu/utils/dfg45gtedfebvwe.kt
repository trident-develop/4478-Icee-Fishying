package photo.editor.photoeditor.filtersforpictu.utils

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface dfg45gtedfebvwe {
    @GET
    suspend fun fvh5yhngfr(
        @Url url: String,
        @QueryMap params: Map<String, String>,
        @Header("Accept-Language") language: String
    ): Response<ResponseBody>

    @GET
    suspend fun rsthfsdgvbg4t(
        @Url url: String,
        @QueryMap params: Map<String, String>
    ): Response<ResponseBody>
}