package com.example.pyxiskapri.models

import android.os.Handler
import android.os.Looper
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream


class ProgressRequestBody(private var fileId: Int, private var file: File, private var content_type: String, private var listener: UploadListener): RequestBody() {

    private val DEFAULT_BUFFER_SIZE = 2048

    interface UploadListener {
        fun onUploadScheduled(fileId: Int, fileName: String)
        fun onProgressUpdate(fileId: Int, percentage: Int)
    }

    init {
        listener.onUploadScheduled(fileId, file.name)
    }

    override fun contentType(): MediaType? {
        return "$content_type/*".toMediaTypeOrNull();
    }

    override fun contentLength(): Long {
        return file.length()
    }

    override fun writeTo(sink: BufferedSink) {
        val fileLength: Long = file.length()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val inputStream = FileInputStream(file)
        var uploaded: Long = 0

        inputStream.use { fileInputStream ->
            var read: Int
            val handler = Handler(Looper.getMainLooper())
            while (fileInputStream.read(buffer).also { read = it } != -1) {
                // update progress on UI thread
                uploaded += read.toLong()
                sink.write(buffer, 0, read)
                handler.post(ProgressUpdater(uploaded, fileLength))
            }
        }

    }


    private inner class ProgressUpdater(private val uploaded: Long, private val total: Long) : Runnable {
        override fun run() {
            listener.onProgressUpdate(fileId, (100 * uploaded / total).toInt())
        }
    }


}