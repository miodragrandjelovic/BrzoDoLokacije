package com.example.pyxiskapri.adapters

import android.content.Context
import android.graphics.PorterDuff
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.response.CommentResponse
import com.example.pyxiskapri.models.CommentExpandableListItem
import com.example.pyxiskapri.utility.UtilityFunctions
import kotlinx.android.synthetic.main.item_comment.view.*
import kotlinx.android.synthetic.main.item_reply.view.*

class CommentAdapter(var commentList: ArrayList<CommentExpandableListItem>, var context: Context) : BaseExpandableListAdapter() {

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

            // Like
            tv_commentLikeCount.text = comment.likeCount.toString()
            if (comment.likeStatus == 1)
                btn_commentLike.setColorFilter(
                    ContextCompat.getColor(context, R.color.gold),
                    PorterDuff.Mode.SRC_IN
                );

            // Dislike
            tv_commentDislikeCount.text = comment.dislikeCount.toString()
            if (comment.likeStatus == -1)
                btn_commentDislike.setColorFilter(
                    ContextCompat.getColor(context, R.color.gold),
                    PorterDuff.Mode.SRC_IN
                );

            if(comment.replyCount == 0)
                btn_commentShowReplies.isVisible = false
            else
                tv_commentReplyCount.text = buildString {
                    append(comment.replyCount.toString())
                    append(" replies")
                }
        }

        return view!!
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        var reply: CommentResponse = commentList[groupPosition].replyList[childPosition]

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

            // Like / Dislike
            tv_replyLikeCount.text = reply.likeCount.toString()
            tv_replyDislikeCount.text = reply.dislikeCount.toString()
            if (reply.likeStatus == 1)
                btn_replyLike.setColorFilter(
                    ContextCompat.getColor(context, R.color.gold),
                    PorterDuff.Mode.SRC_IN
                );
            else if (reply.likeStatus == -1)
                btn_replyDislike.setColorFilter(
                    ContextCompat.getColor(context, R.color.gold),
                    PorterDuff.Mode.SRC_IN
                );
        }

        return view!!
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }

    /* LIST VIEW ADAPTER
    override fun getCount(): Int {
        return commentList.size
    }

    override fun getItem(position: Int): Any {
        return commentList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View? = convertView
        if(view == null)
            view = layoutInflater.inflate(R.layout.item_comment, parent, false)

        val comment = commentList[position]

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
    */

}