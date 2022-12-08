package com.example.pyxiskapri.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pyxiskapri.R
import kotlinx.android.synthetic.main.fragment_grade_display.*

class GradeDisplay : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_grade_display, container, false)
    }

    public fun setGradeDisplay(averageGrade: Double, gradesCount: Int){
        tv_averageGrade.text = String.format("%.2f", averageGrade)

        when(gradesCount){
            0 -> tv_gradesCount.text = "No grades yet"
            1 -> tv_gradesCount.text = StringBuilder().append(gradesCount).append(" ").append("grade")
            else -> tv_gradesCount.text = StringBuilder().append(gradesCount).append(" ").append("grades")
        }

        if(averageGrade == 0.00)
            iv_gradeEmoji.setImageResource(R.drawable.emoji_unset)
        else if(averageGrade < 1.5)
            iv_gradeEmoji.setImageResource(R.drawable.emoji_crying)
        else if(averageGrade < 2.5)
            iv_gradeEmoji.setImageResource(R.drawable.emoji_sad)
        else if(averageGrade < 3.5)
            iv_gradeEmoji.setImageResource(R.drawable.emoji_neutral)
        else if(averageGrade < 4.5)
            iv_gradeEmoji.setImageResource(R.drawable.emoji_happy)
        else if(averageGrade <= 5.0)
            iv_gradeEmoji.setImageResource(R.drawable.emoji_amazed)
    }

}