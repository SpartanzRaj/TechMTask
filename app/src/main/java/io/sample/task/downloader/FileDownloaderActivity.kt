package io.sample.task.downloader

import android.Manifest
import android.content.pm.*
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.*
import android.widget.*
import androidx.core.app.*
import androidx.lifecycle.*
import androidx.work.*
import io.sample.task.R

class FileDownloaderActivity : AppCompatActivity() {
    private val KEY_FILE_NAME = "file_name"
    private val KEY_FILE_TYPE = "file_type"
    private val KEY_FILE_URI = "file_uri"
    private val KEY_FILE_URL = "file_url"

    private lateinit var workManager: WorkManager
    private lateinit var downloadStatus: TextView
    private lateinit var btnStartDownload: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_downloader)
        
        workManager = WorkManager.getInstance(this)
        downloadStatus = findViewById(R.id.downloadStatus)
        btnStartDownload = findViewById(R.id.downloaderBtn)

        btnStartDownload.setOnClickListener {
            if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                startDownloadingFile()
            }else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),1001)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            startDownloadingFile()
        }else{
            downloadStatus.text = getString(R.string.permission)
        }
    }

    private fun startDownloadingFile() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .build()
        val data = Data.Builder()

        data.apply {
            putString(KEY_FILE_NAME, "sample.pdf")
            putString(KEY_FILE_URL, "http://www.africau.edu/images/default/sample.pdf")
            putString(KEY_FILE_TYPE, "PDF")
        }

        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(FileDownloadWorker::class.java)
            .setConstraints(constraints)
            .setInputData(data.build())
            .build()

        workManager.enqueue(oneTimeWorkRequest)

        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
            .observe(this) { info ->
                info?.let {
                    when (it.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            val uri = it.outputData.getString(KEY_FILE_URI)
                            uri?.let {
                                downloadStatus.text = "Downloaded successfully. ${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/sample.pdf"
                                downloadStatus.visibility = View.VISIBLE
                            }
                        }
                        WorkInfo.State.FAILED -> {

                            downloadStatus.text = "Download in failed"
                        }
                        WorkInfo.State.RUNNING -> {
                            downloadStatus.text = "Download in progress.."
                        }
                        else -> {

                        }
                    }
                }
            }
    }
}