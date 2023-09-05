package com.johnson.myapplication.ui.dashboard

import RetrofitInstance
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.FileUtils
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.johnson.myapplication.R
import com.johnson.myapplication.adapters.AnimeAdapter
import com.johnson.myapplication.adapters.SearchAdapter
import com.johnson.myapplication.data.Data
import com.johnson.myapplication.data.image.ImageSearchResponse
import com.johnson.myapplication.data.image.Result
import com.johnson.myapplication.databinding.FragmentDashboardBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


class DashboardFragment : Fragment() {

private var _binding: FragmentDashboardBinding? = null
    private val PICK_FILE_REQUEST_CODE = 123
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

    _binding = FragmentDashboardBinding.inflate(inflater, container, false)
    val root: View = binding.root
      val uploadButton = root.findViewById<Button>(R.id.uploadButton)
      uploadButton.setOnClickListener {
          openFilePicker()
      }
    dashboardViewModel.text.observe(viewLifecycleOwner) {
//      textView.text = it
    }










    return root
  }

    private fun uploadImage(filePath:String?){
        // Replace 'imageFilePath' with the actual file path of the image you want to upload.
        val imageFile = File(filePath)

        val requestFile: RequestBody =
            imageFile.asRequestBody("image/*".toMediaTypeOrNull())

        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
        val ret = RetrofitInstance()

        val call: Call<ImageSearchResponse> = ret.traceMoeApi.searchAnime(body)

        call.enqueue(object : Callback<ImageSearchResponse> {
            override fun onResponse(call: Call<ImageSearchResponse>, response: Response<ImageSearchResponse>) {
                if (response.isSuccessful) {

                    // Handle the API response here (response.body() contains the data).
                    val responseBody = response.body()?.result

                    if (responseBody != null) {
                        initializeAdapter(responseBody)
                    }


                    Log.d("datafrompost", responseBody?.get(0)!!.filename)

                } else {
                    // Handle the error here.
                }
            }

            override fun onFailure(call: Call<ImageSearchResponse>, t: Throwable) {
                Log.d("datafromposterror",t.message.toString())

            }
        })

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            if (data != null) {

                val selectedFileUri = data.data
                Log.d("gggggguri",selectedFileUri.toString())
                val filePath = getRealPathFromURI(selectedFileUri!!,requireActivity())
                Log.d("gggggguripath",filePath.toString())



                if (filePath != null) {
                    // Use the 'filePath' here for further processing

                    uploadImage(filePath)
                }
            }
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE)
    }

    fun getRealPathFromURI(uri: Uri, context: Context): String? {
        val returnCursor = context.contentResolver.query(uri, null, null, null, null)
        val nameIndex =  returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        val sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        val size = returnCursor.getLong(sizeIndex).toString()
        val file = File(context.filesDir, name)
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            var read = 0
            val maxBufferSize = 1 * 1024 * 1024
            val bytesAvailable: Int = inputStream?.available() ?: 0
            //int bufferSize = 1024;
            val bufferSize = Math.min(bytesAvailable, maxBufferSize)
            val buffers = ByteArray(bufferSize)
            while (inputStream?.read(buffers).also {
                    if (it != null) {
                        read = it
                    }
                } != -1) {
                outputStream.write(buffers, 0, read)
            }
            Log.e("File Size", "Size " + file.length())
            inputStream?.close()
            outputStream.close()
            Log.e("File Path", "Path " + file.path)

        } catch (e: java.lang.Exception) {
            Log.e("Exception", e.message!!)
        }
        return file.path
    }

    fun initializeAdapter(itemList:List<Result>){
        binding.mainLayout.visibility = GONE
        binding.resultLayout.visibility = VISIBLE
        val searchAdapter = SearchAdapter(itemList)
        binding.animeRecycler.adapter = searchAdapter
        val manager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL,false)
        binding.animeRecycler.layoutManager = manager

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}