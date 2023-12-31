import com.johnson.myapplication.data.image.ImageSearchResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface TraceMoeApi {
    @Multipart
    @POST("search")
    fun searchAnime(@Part file: MultipartBody.Part): Call<ImageSearchResponse>
}