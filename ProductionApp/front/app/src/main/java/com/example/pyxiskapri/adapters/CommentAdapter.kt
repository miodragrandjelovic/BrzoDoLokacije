package com.example.pyxiskapri.adapters

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.response.CommentResponse
import com.example.pyxiskapri.utility.UtilityFunctions
import kotlinx.android.synthetic.main.activity_user_profile.view.*
import kotlinx.android.synthetic.main.item_comment.view.*
import kotlinx.android.synthetic.main.item_post.view.*

class CommentAdapter(var commentsList: ArrayList<CommentResponse>, var context: Context) : BaseAdapter() {

    override fun getCount(): Int {
        return commentsList.size
    }

    override fun getItem(position: Int): Any {
        return commentsList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    var layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View? = convertView
        if(view == null)
            view = layoutInflater.inflate(R.layout.item_comment, parent, false)

        val comment = commentsList[position]

        /* Old variable assignment
        // Header
        var posterAvatarView: ImageView = view?.findViewById(R.id.iv_posterAvatar)!!
        var posterUsernameView: TextView = view?.findViewById(R.id.tv_username)!!
        var creationDateView: TextView = view?.findViewById(R.id.tv_postDate)!!
        // Comment
        var commentView: TextView = view?.findViewById(R.id.tv_commentText)!!
        // Like
        var likeCommentButton: ImageView = view?.findViewById(R.id.btn_commentLike)!!
        var likeCommentCount: TextView = view?.findViewById(R.id.tv_commentLikeCount)!!
        // Dislike
        var dislikeCommentButton: ImageView = view?.findViewById(R.id.btn_commentDislike)!!
        var dislikeCommentCount: TextView = view?.findViewById(R.id.tv_commentDislikeCount)!!
        // Reply
        var replyButton: LinearLayout = view?.findViewById(R.id.btn_commentShowReplies)!!
        var commentReplyCount: TextView = view?.findViewById(R.id.tv_commentReplyCount)!!
        */

        view?.apply {

            // Header
            iv_posterAvatar.setImageBitmap(UtilityFunctions.base64ToBitmap(comment.commenterImage))
            tv_posterUsername.text = comment.commenterUsername
            tv_postDate.text = comment.creationDate

            // Comment
            tv_commentText.text = comment.commentText

            // Like
            tv_commentLikeCount.text = comment.likeCount.toString()
            if(comment.likeStatus == 1)
                btn_commentLike.setColorFilter(ContextCompat.getColor(context, R.color.gold), PorterDuff.Mode.SRC_IN);

            // Dislike
            tv_commentDislikeCount.text = comment.dislikeCount.toString()
            if(comment.likeStatus == -1)
                btn_commentDislike.setColorFilter(ContextCompat.getColor(context, R.color.gold), PorterDuff.Mode.SRC_IN);

            tv_commentReplyCount.text = buildString {
                append(comment.replyCount.toString())
                append(" replies")
            }

        }


        return view!!
    }

}