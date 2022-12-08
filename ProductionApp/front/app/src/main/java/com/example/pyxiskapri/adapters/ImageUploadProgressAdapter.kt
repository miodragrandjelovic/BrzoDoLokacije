package com.example.pyxiskapri.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pyxiskapri.R
import com.example.pyxiskapri.models.ImageUploadProgressItem
import com.example.pyxiskapri.models.ProgressRequestBody
import kotlinx.android.synthetic.main.item_image_upload_progress.view.*

class ImageUploadProgressAdapter(private val imageUploadProgressList: ArrayList<ImageUploadProgressItem>): RecyclerView.Adapter<ImageUploadProgressAdapter.ImageUploadProgressViewHolder>() {
    class ImageUploadProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    var uploadListener: ProgressRequestBody.UploadListener = object: ProgressRequestBody.UploadListener{
        override fun onUploadScheduled(fileId: Int, fileName: String) {
            imageUploadProgressList.add(ImageUploadProgressItem(fileName, 0))
            notifyItemInserted(itemCount - 1)
        }

        override fun onProgressUpdate(fileId: Int, percentage: Int) {
            imageUploadProgressList[fileId].percentDone = percentage
            notifyItemChanged(fileId)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageUploadProgressViewHolder {
        return ImageUploadProgressViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.item_image_upload_progress, parent, false))
    }

    override fun onBindViewHolder(holder: ImageUploadProgressViewHolder, position: Int) {
        var currentProgressItem: ImageUploadProgressItem = imageUploadProgressList[position]
        holder.itemView.apply{
            tv_imageName.text = currentProgressItem.fileName
            tv_uploadPercentage.text = currentProgressItem.percentDone.toString()
            pb_imageUpload.progress = currentProgressItem.percentDone
        }
    }

    override fun getItemCount(): Int {
        return imageUploadProgressList.size
    }


}