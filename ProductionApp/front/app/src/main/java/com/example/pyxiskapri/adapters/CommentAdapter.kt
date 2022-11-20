package com.example.pyxiskapri.adapters

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.*
import android.widget.ExpandableListView.OnGroupClickListener
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.request.NewReplyRequest
import com.example.pyxiskapri.dtos.response.CommentResponse
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.models.CommentExpandableListItem
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.UtilityFunctions
import kotlinx.android.synthetic.main.item_comment.view.*
import kotlinx.android.synthetic.main.item_reply.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentAdapter(var commentList: ArrayList<CommentExpandableListItem>, var context: Context) : BaseExpandableListAdapter(), OnGroupClickListener {

    private val apiClient: ApiClient = ApiClient()
    var layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater






    public fun AddCommentList(commentResponses: ArrayList<CommentResponse>){
        for(commentResponse: CommentResponse in commentResponses)
            commentList.add(CommentExpandableListItem(commentResponse))
        notifyDataSetChanged()
    }

    public fun AddComment(commentResponse: CommentResponse){
        commentList.add(CommentExpandableListItem(commentResponse))
        notifyDataSetChanged()
    }

    public fun AddReplyList(commentResponses: ArrayList<CommentResponse>, groupPosition: Int){
        for(commentResponse: CommentResponse in commentResponses)
            commentList[groupPosition].replyList.add(commentResponse)
        notifyDataSetChanged()
    }

    public fun AddReply(commentResponse: CommentResponse, groupPosition: Int){
        commentList[groupPosition].replyList.add(commentResponse)
        notifyDataSetChanged()
    }










    override fun getGroupCount(): Int {
        return commentList.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return commentList[groupPosition].replyList.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return commentList[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return commentList[groupPosition].replyList[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true;
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }

    override fun onGroupClick(parent: ExpandableListView?, v: View?, groupPosition: Int, id: Long): Boolean {
        return true
    }









    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var comment: CommentExpandableListItem = commentList[groupPosition]

        var view: View? = convertView
        if(view == null)
            view = layoutInflater.inflate(R.layout.item_comment, parent, false)

        view?.apply {
            // Header
            iv_commenterAvatar.setImageBitmap(UtilityFunctions.base64ToBitmap(comment.commenterImage))
            tv_commenterUsername.text = comment.commenterUsername
            tv_commentDate.text = comment.creationDate

            // Comment
            tv_commentText.text = comment.commentText

            // Like-Dislike text/display
            tv_commentLikeCount.text = comment.likeCount.toString()
            tv_commentDislikeCount.text = comment.dislikeCount.toString()

            when (comment.likeStatus) {
                1 -> {
                    btn_commentLike.setColorFilter(
                        ContextCompat.getColor(context, R.color.gold),
                        PorterDuff.Mode.SRC_IN
                    )
                    btn_commentDislike.setColorFilter(
                        ContextCompat.getColor(context, R.color.white),
                        PorterDuff.Mode.SRC_IN
                    )
                }
                -1 -> {
                    btn_commentDislike.setColorFilter(
                        ContextCompat.getColor(context, R.color.gold),
                        PorterDuff.Mode.SRC_IN
                    )
                    btn_commentLike.setColorFilter(
                        ContextCompat.getColor(context, R.color.white),
                        PorterDuff.Mode.SRC_IN
                    )
                }
                else -> {
                    btn_commentDislike.setColorFilter(
                        ContextCompat.getColor(context, R.color.white),
                        PorterDuff.Mode.SRC_IN
                    )
                    btn_commentLike.setColorFilter(
                        ContextCompat.getColor(context, R.color.white),
                        PorterDuff.Mode.SRC_IN
                    )
                }
            }

            // OPEN REPLIES
            if(comment.replyCount == 0)
                btn_commentShowReplies.alpha = 0f
            else
            {
                btn_commentShowReplies.alpha = 1f
                tv_commentReplyCount.text = buildString {
                    append(comment.replyCount.toString())
                    append(" replies")
                }
                // Open replies button
                btn_commentShowReplies.setOnClickListener {
                    if (isExpanded)
                        (parent as ExpandableListView).collapseGroup(groupPosition)
                    else
                        (parent as ExpandableListView).expandGroup(groupPosition)
                }
            }

            // Like-Dislike Buttons
            btn_commentLike.setOnClickListener {

                when(comment.likeStatus){
                    1 -> {
                        comment.likeStatus = 0
                        comment.likeCount -= 1
                    }
                    -1 -> {
                        comment.likeStatus = 1
                        comment.likeCount += 1
                        comment.dislikeCount -= 1
                    }
                    else -> {
                        comment.likeStatus = 1
                        comment.likeCount += 1
                    }
                }


                likeComment(commentList[groupPosition].id)
                notifyDataSetChanged()
            }
            btn_commentDislike.setOnClickListener {

                when (comment.likeStatus) {
                    -1 -> {
                        comment.likeStatus = 0
                        comment.dislikeCount -= 1
                    }
                    1 -> {
                        comment.likeStatus = -1
                        comment.dislikeCount += 1
                        comment.likeCount -= 1
                    }
                    else -> {
                        comment.likeStatus = -1
                        comment.dislikeCount += 1
                    }
                }

                dislikeComment(comment.id)
                notifyDataSetChanged()
            }

            // Open replies button
            if(isExpanded)
                iv_down.rotation = 180f
            else
                iv_down.rotation = 0f


            // REPLY
            btn_replyToComment.setOnClickListener {
                newReplyDialog(comment.id)
            }

        }

        return view!!
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val reply: CommentResponse = commentList[groupPosition].replyList[childPosition]

        var view: View? = convertView
        if(view == null)
            view = layoutInflater.inflate(R.layout.item_reply, parent, false)

        view?.apply {
            // Header
            iv_replierAvatar.setImageBitmap(UtilityFunctions.base64ToBitmap(reply.commenterImage))
            tv_replierUsername.text = reply.commenterUsername
            tv_replyDate.text = reply.creationDate

            // Comment
            tv_replyText.text = reply.commentText

            // Like-Dislike
            tv_replyLikeCount.text = reply.likeCount.toString()
            tv_replyDislikeCount.text = reply.dislikeCount.toString()

            when (reply.likeStatus) {
                1 -> {
                    btn_replyLike.setColorFilter(
                        ContextCompat.getColor(context, R.color.gold),
                        PorterDuff.Mode.SRC_IN
                    )
                    btn_replyDislike.setColorFilter(
                        ContextCompat.getColor(context, R.color.white),
                        PorterDuff.Mode.SRC_IN
                    )
                }
                -1 -> {
                    btn_replyDislike.setColorFilter(
                        ContextCompat.getColor(context, R.color.gold),
                        PorterDuff.Mode.SRC_IN
                    )
                    btn_replyLike.setColorFilter(
                        ContextCompat.getColor(context, R.color.white),
                        PorterDuff.Mode.SRC_IN
                    )
                }
                else -> {
                    btn_replyDislike.setColorFilter(
                        ContextCompat.getColor(context, R.color.white),
                        PorterDuff.Mode.SRC_IN
                    )
                    btn_replyLike.setColorFilter(
                        ContextCompat.getColor(context, R.color.white),
                        PorterDuff.Mode.SRC_IN
                    )
                }
            }

            // REPLY
            btn_replyToReply.setOnClickListener {
                newReplyDialog(reply.id, reply.commenterUsername)
            }

            // Like-Dislike Buttons
            btn_replyLike.setOnClickListener {
                when(reply.likeStatus){
                    1 -> {
                        reply.likeStatus = 0
                        reply.likeCount -= 1
                    }
                    -1 -> {
                        reply.likeStatus = 1
                        reply.likeCount += 1
                        reply.dislikeCount -= 1
                    }
                    else -> {
                        reply.likeStatus = 1
                        reply.likeCount += 1
                    }
                }


                likeComment(commentList[groupPosition].id)
                notifyDataSetChanged()
            }
            btn_replyDislike.setOnClickListener {

                when (reply.likeStatus) {
                    -1 -> {
                        reply.likeStatus = 0
                        reply.dislikeCount -= 1
                    }
                    1 -> {
                        reply.likeStatus = -1
                        reply.dislikeCount += 1
                        reply.likeCount -= 1
                    }
                    else -> {
                        reply.likeStatus = -1
                        reply.dislikeCount += 1
                    }
                }


                dislikeComment(commentList[groupPosition].id)
                notifyDataSetChanged()
            }

        }

        return view!!
    }






    private fun newReplyDialog(commentId: Int, replyToUsername: String? = ""){
        val dialog = Dialog(context)

        dialog.setContentView(R.layout.dialog_new_reply)
        dialog.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()

        var dialogUserAvatar: ImageView = dialog.findViewById(R.id.iv_dialogUserAvatar)
        var dialogReplyText: EditText = dialog.findViewById(R.id.et_dialogNewCommentText)
        var dialogPostReplyButton: ConstraintLayout = dialog.findViewById(R.id.btn_postComment)

        // SET USER IMAGE


        if(replyToUsername != "") {
            dialogReplyText.setText(
                buildString {
                    append("@")
                    append(replyToUsername.toString())
                    append(" ")
                }
            )
        }

        dialogPostReplyButton.setOnClickListener{
            val newReplyRequest = NewReplyRequest(
                commentId = commentId,
                commentText = dialogReplyText.text.toString()
            )

            sendNewReplyRequest(newReplyRequest)
        }



        dialogReplyText.requestFocus()

    }

    private fun sendNewReplyRequest(newReplyRequest: NewReplyRequest){
        apiClient.getCommentService(context).addNewReply(newReplyRequest)
            .enqueue(object : Callback<MessageResponse> {
                override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                    if(response.isSuccessful) {
                        // RADI NESTO
                    }

                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.d(
                        "CommentAdapter",
                        "Nije implementiran onFailure za addNewReply api zahtev!"
                    )
                }

            }
        )
    }

    private fun likeComment(commentId: Int){
        apiClient.getCommentService(context).likeComment(commentId)
            .enqueue(object : Callback<MessageResponse> {
                override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                    if(response.isSuccessful) {
                        // RADI NESTO
                    }

                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.d(
                        "CommentAdapter",
                        "Nije implementiran onFailure za likeComment api zahtev!"
                    )
                }

            }
            )
    }

    private fun dislikeComment(commentId: Int){

        apiClient.getCommentService(context).dislikeComment(commentId)
            .enqueue(object : Callback<MessageResponse> {
                override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                    if(response.isSuccessful) {
                        // RADI NESTO
                    }

                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.d(
                        "CommentAdapter",
                        "Nije implementiran onFailure za dislikeComment api zahtev!"
                    )
                }

            }
            )
    }

}