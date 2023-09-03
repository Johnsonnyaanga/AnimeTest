package com.johnson.myapplication.repositories

import com.johnson.myapplication.api.AnimeApi
import com.johnson.myapplication.data.AnimeDataResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


/**
 *created by Ronnie Otieno on 20-Dec-20.
 **/

/**
 * Repository used to access data being loaded from network call
 */

@Singleton
class AnimeRepository @Inject constructor(private val animeApi: AnimeApi) : BaseRepository() {

    //Returning the fetched data as flow

//    fun getPokemon(searchString: String?) = Pager(
//        config = PagingConfig(enablePlaceholders = false, pageSize = 25),
//        pagingSourceFactory = {
//            PokemonDataSource(pokemonApi, searchString)
//        }
//    ).flow

    suspend fun getTopAnimes(): Response<AnimeDataResponse>  {
         return  animeApi.getTopAnimes()

    }


}