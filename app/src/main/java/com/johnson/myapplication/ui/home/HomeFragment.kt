package com.johnson.myapplication.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.johnson.myapplication.adapters.AnimeAdapter
import com.johnson.myapplication.data.Data
import com.johnson.myapplication.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

private val animeListViewModel:AnimeListViewModel by viewModels()

private var _binding: FragmentHomeBinding? = null
    private lateinit var recyclerView:RecyclerView
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

  @SuppressLint("SuspiciousIndentation")
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    val root: View = binding.root

      recyclerView = binding.animeRecycler

      animeListViewModel.getTopAnimes()
      animeListViewModel.animelistResponse.observe(viewLifecycleOwner) {
          binding.progressBarLayout.visibility = GONE
          it.body()?.data?.get(0)?.let { it1 -> Log.d("animationdatar", it1.toString()) }
          it.body()?.data?.let { it1 -> initializeAdapter(it1) }

      }
      homeViewModel.text.observe(viewLifecycleOwner) {
//      textView.text = it
    }
    return root
  }

    fun initializeAdapter(itemList:List<Data>){
        val animeAdapter = AnimeAdapter(itemList)
        recyclerView.adapter = animeAdapter
        val manager = LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager = manager

    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}