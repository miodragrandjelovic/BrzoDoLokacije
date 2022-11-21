package com.example.pyxiskapri.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pyxiskapri.R
import com.example.pyxiskapri.activities.ChatMainActivity
import com.example.pyxiskapri.dtos.response.FriendResponse
import com.example.pyxiskapri.utility.UtilityFunctions
import kotlinx.android.synthetic.main.item_friend_in_chat.view.*

class ChatFriendsAdapter(private val friendsList: ArrayList<FriendResponse>): RecyclerView.Adapter<ChatFriendsAdapter.ChatFriendViewHolder>() {
    class ChatFriendViewHolder(friendView: View) : RecyclerView.ViewHolder(friendView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatFriendViewHolder {
        return ChatFriendViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.item_friend_in_chat, parent, false) )
    }

    override fun onBindViewHolder(holder: ChatFriendViewHolder, position: Int) {
        val currentFriend = friendsList[position]

        holder.itemView.apply {

            ll_item_friend_in_chat.setOnClickListener{
                openFriendChat()
            }

            iv_friendAvatar.setImageBitmap(UtilityFunctions.base64ToBitmap(currentFriend.friendImage))
            iv_friendAvatar.setOnClickListener {
                openFriendAccount()
            }

            tv_friendUsername.text = currentFriend.friendUsername
            tv_lastMessage.text = currentFriend.lastMessage
            tv_messageTime.text = currentFriend.lastMessageTime

        }
    }


    private fun openFriendAccount(){
        // OPEN
    }

    private fun openFriendChat(){
        //val intent = Intent(this, ChatMainActivity::class.java);
        //startActivity(intent);
    }






    override fun getItemCount(): Int {
        return friendsList.size
    }








    public fun clearFriendList(){
        friendsList.clear()
        notifyDataSetChanged()
    }

    public fun addFriend(newFriend: FriendResponse){
        friendsList.add(newFriend)
        notifyItemInserted(itemCount)
    }

    public fun addFriendList(newFriendList: ArrayList<FriendResponse>){
        friendsList.addAll(newFriendList)
        notifyItemRangeInserted(friendsList.size, newFriendList.size)
    }







}