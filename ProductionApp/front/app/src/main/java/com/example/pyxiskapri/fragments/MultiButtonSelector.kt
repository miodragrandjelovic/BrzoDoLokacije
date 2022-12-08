package com.example.pyxiskapri.fragments

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.pyxiskapri.R
import kotlinx.android.synthetic.main.fragment_multi_button_selector.*
import kotlinx.android.synthetic.main.fragment_multi_button_selector.view.*

class MultiButtonSelector : Fragment() {

    public var value: Int = 20
    private lateinit var previousButton: FrameLayout
    private var changing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun activateMultiButton(multiButton: FrameLayout){
        if(changing)
            return
        changing = true
        value = 10
        previousButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#252529"))
        multiButton.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.red))
        previousButton = multiButton
        changing = false
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_multi_button_selector, container, false)

        previousButton = view.btn_2

        view?.apply {
            btn_0.setOnClickListener{
                activateMultiButton(btn_0)
            }
            btn_1.setOnClickListener{
                activateMultiButton(btn_1)
            }
            btn_2.setOnClickListener{
                activateMultiButton(btn_2)
            }
            btn_3.setOnClickListener{
                activateMultiButton(btn_3)
            }
            btn_4.setOnClickListener{
                activateMultiButton(btn_4)
            }
        }

        return view
    }

}