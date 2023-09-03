package com.johnson.myapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.johnson.myapplication.data.AnimeDataResponse
import com.johnson.myapplication.data.Data
import com.johnson.myapplication.repositories.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


/**
 *created by Ronnie Otieno on 20-Dec-20.
 **/

@HiltViewModel
class AnimeListViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {


   val  _animelistResponse = MutableLiveData<Response<AnimeDataResponse>>()
    val animelistResponse:LiveData<Response<AnimeDataResponse>> = _animelistResponse

     fun getTopAnimes() {
        viewModelScope.launch {
            val res  = animeRepository.getTopAnimes()
            _animelistResponse.value = res
        }

    }
}