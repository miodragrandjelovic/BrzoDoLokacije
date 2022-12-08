package com.example.pyxiskapri.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.request.GradeRequest
import com.example.pyxiskapri.dtos.response.MessageResponse
import com.example.pyxiskapri.dtos.response.PostResponse
import com.example.pyxiskapri.utility.ApiClient
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_grade_selector.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GradeSelectorFragment : Fragment() {

    public var gradedPostId: Int = 0
    private var grade: Int = 0

    private var opened: Boolean = false

    private var apiClient: ApiClient = ApiClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_grade_selector, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        handleInputs()
        hideSelector()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun handleInputs(){
        btn_displayEmoji.setOnClickListener {
            if(opened)
                hideSelector()
            else
                showSelector()
        }

        btn_grade1.setOnClickListener{ selectGrade(1) }
        btn_grade2.setOnClickListener{ selectGrade(2) }
        btn_grade3.setOnClickListener{ selectGrade(3) }
        btn_grade4.setOnClickListener{ selectGrade(4) }
        btn_grade5.setOnClickListener{ selectGrade(5) }
    }

    private fun showSelector(){
        iv_selectionBackground.visibility = View.VISIBLE
        ll_selectionEmojis.visibility = View.VISIBLE
        opened = true
    }

    private fun hideSelector(){
        iv_selectionBackground.visibility = View.INVISIBLE
        ll_selectionEmojis.visibility = View.INVISIBLE
        opened = false
    }

    private fun selectGrade(selectedGrade: Int){
        // REMOVE GRADE
        if(grade == selectedGrade){
            btn_displayEmoji.setImageResource(R.drawable.emoji_unset)
            grade = 0
        }

        // CHAGE GRADE
        else{
            when(selectedGrade){
                1 -> btn_displayEmoji.setImageResource(R.drawable.emoji_crying)
                2 -> btn_displayEmoji.setImageResource(R.drawable.emoji_sad)
                3 -> btn_displayEmoji.setImageResource(R.drawable.emoji_neutral)
                4 -> btn_displayEmoji.setImageResource(R.drawable.emoji_happy)
                5 -> btn_displayEmoji.setImageResource(R.drawable.emoji_amazed)
            }
            grade = selectedGrade
        }

        sendGradeChangeRequest()

        hideSelector()
    }

    private fun setGradeSelectorInitialGrade(initialGrade: Int){
        when(initialGrade){
            0 -> btn_displayEmoji.setImageResource(R.drawable.emoji_unset)
            1 -> btn_displayEmoji.setImageResource(R.drawable.emoji_crying)
            2 -> btn_displayEmoji.setImageResource(R.drawable.emoji_sad)
            3 -> btn_displayEmoji.setImageResource(R.drawable.emoji_neutral)
            4 -> btn_displayEmoji.setImageResource(R.drawable.emoji_happy)
            5 -> btn_displayEmoji.setImageResource(R.drawable.emoji_amazed)
        }
    }

    private fun sendGradeChangeRequest(){
        if(gradedPostId < 1 || grade > 5)
            return

        val gradeRequest = GradeRequest(
            postId = gradedPostId,
            grade = grade
        )
        apiClient.getPostService(requireContext()).setLike(gradeRequest)
            .enqueue(object: Callback<MessageResponse>{
                override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                    if(response.isSuccessful())
                        Log.d("GRADE STATE: ", "CHANGED")
                }
                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.d("GRADE STATE: ", "REQUEST FAILED")
                }
            })
    }


}