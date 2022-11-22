package com.example.pyxiskapri.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.pyxiskapri.R
import com.example.pyxiskapri.adapters.ChatFriendsAdapter
import com.example.pyxiskapri.dtos.response.FriendResponse
import com.example.pyxiskapri.fragments.DrawerNav
import com.example.pyxiskapri.utility.ApiClient
import kotlinx.android.synthetic.main.activity_chat_main.*
import kotlinx.android.synthetic.main.activity_chat_main.btn_home
import kotlinx.android.synthetic.main.activity_chat_main.btn_newPost
import kotlinx.android.synthetic.main.activity_home.*

class ChatMainActivity : AppCompatActivity() {

    private lateinit var apiClient: ApiClient

    private lateinit var friendsAdapter: ChatFriendsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_main)

        apiClient = ApiClient()

        setupNavButtons()

        setupFriendList()
    }

    fun showDrawerMenu(view: View){
        if(view.id == R.id.btn_menu)
            fcv_chatMainDrawerNav.getFragment<DrawerNav>().showDrawer()
    }



    private fun setupNavButtons(){
        setupGoHomeButton()
        setupButtonNewPost()
    }

    private fun setupGoHomeButton(){
        btn_home.setOnClickListener {
            val intent = Intent (this, HomeActivity::class.java);
            startActivity(intent);
        }
    }

    private fun setupButtonNewPost(){
        btn_newPost.setOnClickListener {
            val intent = Intent (this, NewPostActivity::class.java);
            startActivity(intent);
        }
    }


    private fun getDummyData(): ArrayList<FriendResponse>{
        val friendList: ArrayList<FriendResponse> = arrayListOf()

        val lorem: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec in libero sit amet."
        val testUserImage: String = "/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAMCAgMCAgMDAwMEAwMEBQgFBQQEBQoHBwYIDAoMDAsKCwsNDhIQDQ4RDgsLEBYQERMUFRUVDA8XGBYUGBIUFRT/2wBDAQMEBAUEBQkFBQkUDQsNFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBT/wgARCABkAGQDASIAAhEBAxEB/8QAHAABAAIDAQEBAAAAAAAAAAAAAAUIBAYHAQMC/8QAGgEAAgMBAQAAAAAAAAAAAAAAAAMBAgQFBv/aAAwDAQACEAMQAAABsiJsAPj8auBYrHq5rkVvl7WTt822wAAAABxzVe2y+Z1KYC52nrZUzou0aG5F4g+gAAAEH+Pnwrn7+xwuFx9TukQXshetgB0uYAAABFxEHpHO6GzxXKJmr95mdQsGzP8AQb8AAAGNosO0ryT0LI2AgMPKI6xYum9r9FJkMQAc65doUMn0k7B/vEh3Q+/1N6/m5GzV5ztFvdP42c/RYHtNH98tmtMjDOXSbMMPssqLC2dppbHmzQXyJkrqgfSU44jL/8QAJxAAAgICAgEEAAcAAAAAAAAAAwQCBQEGABIgBxETIRAUFRYmMDL/2gAIAQEAAQUC8SmGGP7kre0Nrq5zxn3x/Q40NFW6t827xjw65azyg38tWjQ7Irfj89tmaxtHdJcEpZUx1cnh1jkueaG5MOy+cgjNbdYihaqhcixTJ9bLVFD51quMxsPnDMV5ycCeM5ZJk+M8L959PxfybzMtEqFpWGVs7NmdWjKZzmrw/FHVQwXsvNaeIpBJE7u1TiRhBJea7HUONQQ7582LSK0JVdyZNxe1JmqtGwssk/NSGOIoee9CkNyOxCYqUzjAa0u/nY9OYxYsvI7IlY2O6JLD0QkLWgvtJarZZqbKfIUw6+OtbB+nXlZbq24PxsN1UXk36humgZ5iwzNrtDQbTNcZouDjZFAY79/BjizKXE2zok1rfPn5GWJY40b34kL5OT7PyajFpmqOUY1b9dJfZtxJd8yKZuBB7RXXzLBs4FnXtuap8oWILJV37Uax7k/wVKGOthPKZxjwcqIInPKGMldjiMMQxiAsd+E+sQfOLH//xAAkEQABBAEDBAMBAAAAAAAAAAABAAIDERIgITEEECIyE0FRFP/aAAgBAwEBPwHveprARuv574T4zHzpa38WWKlaZBpZxsg6tiFNL9DQyJ8nqFRZ6rzdsFKzE9x00cZAaLT7x8U1pGxQdewCbGVN0v2xVSoWUQLU4HyJjRjwgBkVQpYj8X//xAAhEQABAwQCAwEAAAAAAAAAAAABAAIRAxASISAxEyIwQf/aAAgBAgEBPwH4veQdLzx2mVA/ridkysclROB4kb2nN/VRZOzwmESHHa9RtM6vJs9plU6eOzYOtJUoFSVJUlSV/8QANBAAAgECBAMFBgUFAAAAAAAAAQIDABEEEiFBIjFREyMwMmEUIEJScYEFEGKRwUNTobHh/9oACAEBAAY/AvdvI6oP1GgvtkevrWUYpfrY2q45eDJPKbRxjMaaebMqco4/lFcLAMu1Br8WXUUuGlg9oyHRi9rDpTGLgkXzRsdfAi/D4btlUEr6miyFZpBzAq8iFa5H6V/FYQZ8quch9fAdsoJSIKT9zXDpagJEDVrCn7UWW8Z/TWFgjvmWW5PS2/gYqZ3spbzE7AV3cyn71beuelWFYyXSyoV+9/8AngBbXGp1F6jkgOZd7LvSOdZGXUVnk7Yowv3b0LMzR7ZuYpd5GmZj9PAiv8tWEXCPioIRcUGX9qY8gBS4pl5Lz6k+BiMJmy4mMMifxXHiWX0FXlxTEqdDrSwzjhPxVDhkOaTEMEoIoyqNAB4CY7DNdV7qbL8Lbf7pVZrSZbG1M887yjbWj2WijTSnlmHeLFeIHYciffzSyLGv6jTdjmlksbaaVivaO97edzIG3oyYKXtoj8DcxWTsCo6mu1xZzkfCOVLi5b9nYhlX5bcqEuFlDjpuPqPcyQAzt83JaBXJCpOgjGppp8VMwjHU00jHJ2ndwpT4dvK3FWmtFm4QNzTW8g5Cr7UrQyNG/VTQg/ErK2045fegQbg7j8p9coCKL1278CW4R0FKo4Yb6DrRT+lGLC3WpFkAM0Nnzb5az4rEBeiX1NCLCo2Hw/qdTS3JYAeY70WPIVnO/KgLXbYUsb97hv7fT6Us8D5kb/FH1NjSRckJtU1tOzThq/Wlnj0fIy/a1DPc3NcXWrbCoUHlbnS+lSSNq35WRyo9K//EACYQAQACAgAFBAMBAQAAAAAAAAEAESExQVFhcYEwkaGxIMHw0RD/2gAIAQEAAT8h/HqqCiGGLalwx14Sx82lgHlIBIKyJ6NoyV7Re8gH83zgW4yty6dItBXJzm2oFoKW9Ox6xCXTSnU5nX0CYqh020vgjARpy+17nYdq1ylTlEsBxmVlDmZmkmx0Rx716CppaM5Q/usK2h4IHAM6ib2oEWnZoQY6cmKq34egT4PggP7uBW35TV1NswqlIz20S9CC94VHB4XoNUO9QKxV4aYG2MGuEv8AuxPmb4cWUd8RgXmx5hcHA+SAPb0KF01a5R7d2MxKgGaItDTuBSBJl8g7Bpfq/Qv8RNbwvnSRWq7vMfJLwuUGXeE1SdK4bmHY2uAuX2uALDQUB6BRFD9zbwPiLWXI5sNzeSJtUztrOjjPKwOB3Df3+b3n8BBNZRKXDeeExseSGVf5LlCXSXfjEGycUCN93HGfuWHPfaYeajfOW6/Wfgipm7fZtjsKj5Hbm2YCmv8AY2LneccX6jDIoR6kPuyVLitwBLkZK/dLhbfMELmyskzYzBqXbw7wGxrEsT/mabkcpm/MysJRv7tlEK6fegNU4W9D7xxD4Waq/u/eD8KsR4yGEop3fjUuNZnY5Uup+AZBGl077ylni2fe4Q9/vlxE5zbOvqBr7nMUQcicBo1OUYVlWZSaqbLFM15l5EAW5gvBQDRMc1oCYRk8pXRgXC3BnMdHG+ccXFdNJ//aAAwDAQACAAMAAAAQ/wD5J9P/AP8A/LKP/wD/APuLsj//APvzib//AO8zwVd/yCoFtW8Q/wDwIH3z/8QAIhEBAAICAgIBBQAAAAAAAAAAAQARIUExURAgYXGBkcHR/9oACAEDAQE/EPC1LQb9bhtBZqPHqQBNxVaOYAeKF9VeK1mCglF7j05/fnX5lvYhWFR3FuMnmyK7c/fqO856+X+EqCyRW/QStTuCryGopUmY8Bo/cocdy2xNU5nGNE6EVbRP/8QAIBEBAAIBBAIDAAAAAAAAAAAAAQARISAxUWEQQXGBof/aAAgBAgEBPxDwtS4N6aDCMBqsiwzDNbtAUM3WlGqoiZD9T41oRui4xRjdZtj2O/laHctU9zvb+RnoZc7JbmNzGzePJOydk//EACUQAQABBAIBAwUBAAAAAAAAAAERACExQVFhcTCBkSChscHREP/aAAgBAQABPxD6ZFXYIV4JzS3H1AiUM4ciYkuUHnEKQYZAcfJQwUEJEcI+jGvEIlBME5XAbUqNulYh8rBhKvMugpOQgM0s3XVhNa6QEF3mnDFl4nqmKFGgybQNvUGilXICAkEidsWF7ehHyFngIEsy4vzU65LwYF0AWGAvJFqXJgZS5t3x8UTwTxaV1ffPmlovIXMJjUeKK1m0JIe8egyoYgpCegk4wqF5WFQHgYiiSEIFKhQ4SpQjGynxZ0xLxMQK/MG/QZ18AhGB6hjma0sGFf2moIAgpwG6Y5CcoT80yeGDMz/alzzGJ0DbIevrcUIS2gcpDnNDDOFCUQQwBDdwvVRtVVUmnL90dbCPM4yL4nxF6ergrL5TYvUxZTETK84kMdP1uKgfV2sif3RkjKABOHdI4VkTRD1uolMlGDwlBJCY0A/ygCVsbIIPAXunoMpRaQyDL3ebTL0SxwkVU9icVcAUEmcGLvzUoUc4CXC02uU8NhXKcP0Se1G6uAAIAD0DeSQsQPHCknUDdK/m0IgE9P8AKI4LplxIt2mKARVzGT2hSWli034nQB4eX1iSAsdfEt/ak3rNsGTBQxgvUUtsuxf2F6G7PkPTYwhJez1SZmE7gvM1xVcfAO/sqGZiYpaAoOW6SlAREtFusi+zpaGTj/RSbRZe4uDRDzTDkuIGhUdpEEbSmJaqJhoVlWKR9NjopFHSwJcy1Zl1ASE/ilKALRcpW2CAIutI0CsKTl2/YjujFJZD8qnvmR46fisC0IM0FZdLchmg4QMIEiJkf8XytC7xyoQOU1NGc1hmHHlLztppUQ1rMrmlMZnSBh4S6aFhgmFwx3AIR25qOEzw4xHDywd0UH0Gy8uGkEuJ8Bh0RZN93QmPangVWG09UzNBYPiroR0wjadHbUgOYdELdbLrDxuh0VF7F8MNn6SpkQmA5IfYvmivWFObAfFMKAGZa3Pmjsbvd3qacqJSBWYEk7p4VS3Q09Fcbg6Kcw2VkFTozIM0BBkA1YtQApwnBxBHBo/d6mMCMsKdiZCqwXgc2K//2Q=="

        friendList.add(FriendResponse(1, "TestUsername123", testUserImage, lorem, "12:34"))
        friendList.add(FriendResponse(2, "TestUsername123", testUserImage, lorem, "20:00"))
        friendList.add(FriendResponse(3, "TestUsername123", testUserImage, lorem, "02:33"))
        friendList.add(FriendResponse(4, "TestUsername123", testUserImage, lorem, "13:15"))
        friendList.add(FriendResponse(5, "TestUsername123", testUserImage, lorem, "06:42"))
        friendList.add(FriendResponse(6, "TestUsername123", testUserImage, lorem, "56:22"))
        friendList.add(FriendResponse(7, "TestUsername123", testUserImage, lorem, "23:23"))
        friendList.add(FriendResponse(8, "TestUsername123", testUserImage, lorem, "11:55"))

        return friendList
    }

    private fun setupFriendList(){
        friendsAdapter = ChatFriendsAdapter(arrayListOf())
        rv_friends.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        (rv_friends.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        rv_friends.adapter = friendsAdapter

        friendsAdapter.addFriendList(getDummyData())

    }




}