package com.example.pyxiskapri.custom_view_models

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import com.example.pyxiskapri.R
import com.example.pyxiskapri.dtos.request.GradeRequest
import com.example.pyxiskapri.dtos.response.GradeResponse
import com.example.pyxiskapri.utility.ApiClient
import kotlinx.android.synthetic.main.grade_selector.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GradeSelectorView(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs) {
    lateinit var gradeDisplay: GradeDisplayView
    var gradedPostId: Int = 0

    private var grade: Int = 0

    private var opened: Boolean = false
    private var apiClient: ApiClient = ApiClient()

    init{
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.grade_selector, this, true)

        handleInputs()
        hideSelector()
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

        val animSet = AnimatorSet()
        animSet.playTogether(
            ObjectAnimator.ofFloat(iv_selectionBackground, "alpha", 1f ).setDuration(200),
            ObjectAnimator.ofFloat(btn_grade1, "scaleX", 1f ).setDuration(250),
            ObjectAnimator.ofFloat(btn_grade2, "scaleX", 1f ).setDuration(200),
            ObjectAnimator.ofFloat(btn_grade3, "scaleX", 1f ).setDuration(150),
            ObjectAnimator.ofFloat(btn_grade4, "scaleX", 1f ).setDuration(100),
            ObjectAnimator.ofFloat(btn_grade5, "scaleX", 1f ).setDuration(50),
            ObjectAnimator.ofFloat(btn_grade1, "scaleY", 1f ).setDuration(250),
            ObjectAnimator.ofFloat(btn_grade2, "scaleY", 1f ).setDuration(200),
            ObjectAnimator.ofFloat(btn_grade3, "scaleY", 1f ).setDuration(150),
            ObjectAnimator.ofFloat(btn_grade4, "scaleY", 1f ).setDuration(100),
            ObjectAnimator.ofFloat(btn_grade5, "scaleY", 1f ).setDuration(50)
        )
        animSet.doOnEnd { opened = true }
        animSet.start()
    }

    private fun hideSelector(){
        val animSet = AnimatorSet()

        animSet.playTogether(
            ObjectAnimator.ofFloat(iv_selectionBackground, "alpha", 0f ).setDuration(200),
            ObjectAnimator.ofFloat(btn_grade1, "scaleX", 0f ).setDuration(250),
            ObjectAnimator.ofFloat(btn_grade2, "scaleX", 0f ).setDuration(200),
            ObjectAnimator.ofFloat(btn_grade3, "scaleX", 0f ).setDuration(150),
            ObjectAnimator.ofFloat(btn_grade4, "scaleX", 0f ).setDuration(100),
            ObjectAnimator.ofFloat(btn_grade5, "scaleX", 0f ).setDuration(50),
            ObjectAnimator.ofFloat(btn_grade1, "scaleY", 0f ).setDuration(250),
            ObjectAnimator.ofFloat(btn_grade2, "scaleY", 0f ).setDuration(200),
            ObjectAnimator.ofFloat(btn_grade3, "scaleY", 0f ).setDuration(150),
            ObjectAnimator.ofFloat(btn_grade4, "scaleY", 0f ).setDuration(100),
            ObjectAnimator.ofFloat(btn_grade5, "scaleY", 0f ).setDuration(50)
        )
        animSet.doOnEnd {
            opened = false
            iv_selectionBackground.visibility = View.INVISIBLE
            ll_selectionEmojis.visibility = View.INVISIBLE
        }
        animSet.start()
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

    public fun setGradeSelectorInitialGrade(initialGrade: Int){
        grade = initialGrade
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
        apiClient.getPostService(context).setLike(gradeRequest)
            .enqueue(object: Callback<GradeResponse> {
                override fun onResponse(call: Call<GradeResponse>, response: Response<GradeResponse>) {
                    if(response.isSuccessful && ::gradeDisplay.isInitialized && response.body() != null)
                        gradeDisplay.setGradeDisplay(response.body()!!.averageGrade, response.body()!!.gradesCount)
                }
                override fun onFailure(call: Call<GradeResponse>, t: Throwable) {
                    Log.d("GRADE STATE: ", "REQUEST FAILED")
                }
            })
    }

}