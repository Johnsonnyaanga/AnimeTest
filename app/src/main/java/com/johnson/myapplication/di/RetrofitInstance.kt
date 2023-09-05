import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInstance() {


    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.trace.moe/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val traceMoeApi = retrofit.create(TraceMoeApi::class.java)

}