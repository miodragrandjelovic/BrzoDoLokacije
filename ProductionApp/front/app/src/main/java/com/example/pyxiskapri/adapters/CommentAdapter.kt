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
import com.example.pyxiskapri.dtos.request.NewCommentRequest
import com.example.pyxiskapri.dtos.response.CommentResponse
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.models.CommentExpandableListItem
import com.example.pyxiskapri.utility.ApiClient
import com.example.pyxiskapri.utility.SessionManager
import com.example.pyxiskapri.utility.UtilityFunctions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_open_post.*
import kotlinx.android.synthetic.main.item_comment.view.*
import kotlinx.android.synthetic.main.item_reply.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CommentAdapter(var commentList: ArrayList<CommentExpandableListItem>, var postId: Int, var postOwner: String, var context: Context) : BaseExpandableListAdapter(), OnGroupClickListener {

    private val apiClient: ApiClient = ApiClient()
    var layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    private lateinit var replyDialog: Dialog


    public fun addCommentList(commentResponses: ArrayList<CommentResponse>){
        for(commentResponse: CommentResponse in commentResponses)
            commentList.add(CommentExpandableListItem(commentResponse))
        notifyDataSetChanged()
    }

    public fun addComment(commentResponse: CommentResponse){
        commentList.add(CommentExpandableListItem(commentResponse))
        notifyDataSetChanged()
    }

    public fun addReplyList(commentResponses: ArrayList<CommentResponse>, groupPosition: Int){
        for(commentResponse: CommentResponse in commentResponses)
            commentList[groupPosition].replyList.add(commentResponse)
        notifyDataSetChanged()
    }

    public fun addReply(commentResponse: CommentResponse, groupPosition: Int){
        commentList[groupPosition].replyCount++
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
        return commentList[groupPosition].id.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return commentList[groupPosition].replyList[childPosition].id.toLong()
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
            Picasso.get().load(UtilityFunctions.getFullImagePath(comment.commenterImage)).into(iv_commenterAvatar)
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
                newReplyDialog(comment.id, groupPosition)
            }

            if(postOwner == comment.commenterUsername)
                tv_owner.visibility = View.VISIBLE
            else
                tv_owner.visibility = View.GONE
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
            Picasso.get().load(UtilityFunctions.getFullImagePath(reply.commenterImage)).into(iv_replierAvatar)
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
                newReplyDialog(commentList[groupPosition].id, groupPosition, reply.commenterUsername)
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


                likeComment(reply.id)
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


                dislikeComment(reply.id)
                notifyDataSetChanged()
            }

            if(postOwner == reply.commenterUsername)
                tv_ownerReply.visibility = View.VISIBLE
            else
                tv_ownerReply.visibility = View.GONE

        }

        return view!!
    }






    private fun newReplyDialog(commentId: Int, groupPosition: Int, replyToUsername: String? = ""){
        replyDialog = Dialog(context)

        replyDialog.setContentView(R.layout.dialog_new_reply)
        replyDialog.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        replyDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        replyDialog.show()

        var dialogUserAvatar: ImageView = replyDialog.findViewById(R.id.iv_dialogUserAvatar)
        val dialogReplyText: EditText = replyDialog.findViewById(R.id.et_dialogNewCommentText)
        val dialogPostReplyButton: ConstraintLayout = replyDialog.findViewById(R.id.btn_addTag)

        // SET USER IMAGE
        Picasso.get().load(UtilityFunctions.getFullImagePath(SessionManager(context).fetchUserData()!!.profileImagePath)).into(dialogUserAvatar)

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
            val newReplyRequest = NewCommentRequest(
                postId = postId,
                parentId = commentId,
                commentText = dialogReplyText.text.toString()
            )

            sendNewReplyRequest(newReplyRequest, groupPosition)
        }



        dialogReplyText.requestFocus()

    }

    private fun sendNewReplyRequest(newReplyRequest: NewCommentRequest, originalCommentPosition: Int){
        apiClient.getCommentService(context).addNewComment(newReplyRequest)
            .enqueue(object : Callback<MessageResponse> {
                override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                    if(!response.isSuccessful)
                        return

                    val userData = SessionManager(context).fetchUserData()!!
                    val replyEditText: EditText = replyDialog.findViewById(R.id.et_dialogNewCommentText)

                    val replyToAdd = CommentResponse(
                        id = response.body()!!.message.toInt(),
                        commenterImage = userData.profileImagePath,
                        commenterUsername = userData.username,
                        commentText = replyEditText.text.toString(),
                        creationDate = SimpleDateFormat("dd-MMM-yy HH:mm:ss").format(Calendar.getInstance().time),
                        likeStatus = 0,
                        likeCount = 0,
                        dislikeCount = 0,
                        replyCount = 0,
                        replies = arrayListOf()
                    )

                    addReply(replyToAdd, originalCommentPosition)

                    replyDialog.dismiss()
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