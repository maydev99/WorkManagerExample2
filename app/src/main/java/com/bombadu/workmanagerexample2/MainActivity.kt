package com.bombadu.workmanagerexample2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //If you want to create a constraint
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        //Request to Worker with constraint added
        val request = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setConstraints(constraints).build()

        //Starting the Work Manager with on button click
        button.setOnClickListener {
            WorkManager.getInstance(applicationContext).enqueue(request)
        }


        //Retrieving data from the work manager and apply to a textView
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.id)
            .observe(this, Observer { workInfo ->
                val text = workInfo.outputData.getString("key").toString()
                textView.text = text
            })
    }
}