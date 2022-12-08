package com.example.pyxiskapri.custom_view_models

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.pyxiskapri.R
import kotlinx.android.synthetic.main.grade_display.view.*


class GradeDisplayView(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs) {

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.grade_display, this, true)
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