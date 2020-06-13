package com.bombadu.workmanagerexample2

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private lateinit var outputData: Data

    override fun doWork(): Result {
        //Start OKHttp call
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")


            val myText = response.body!!.string()
            println(myText)

            outputData = createOutputData(myText)

        }
        return Result.success(outputData)
    }

    //Creates Output Data from OKHttp Response
        private fun createOutputData(text: String): Data {
        return Data.Builder()
            .putString("key", text) //'key' used in MainActivity getWorkInfoByLiveData
            .build()
    }

    companion object {
        private const val url =
            "https://newsapi.org/v1/articles?source=engadget&apiKey=e784da02bc1c452ebf5d10a1df98a162"
    }
}