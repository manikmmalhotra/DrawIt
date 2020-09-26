package com.example.drawit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EraserBottomSheet(private val paintView: PaintView) : BottomSheetDialogFragment() {

    lateinit var i_stroke_width: ImageView
    lateinit var ii_stroke_width: ImageView
    lateinit var iii_stroke_width: ImageView
    lateinit var iv_stroke_width: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eraser_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        i_stroke_width = view.findViewById(R.id.i_stroke_width)
        ii_stroke_width = view.findViewById(R.id.ii_stroke_width)
        iii_stroke_width = view.findViewById(R.id.iii_stroke_width)
        iv_stroke_width = view.findViewById(R.id.iv_stroke_width)


        i_stroke_width.setOnClickListener {
            paintView.setEraser(10)
            MainActivity.selectedColor = "#ffffff"
            dismiss()
        }

        ii_stroke_width.setOnClickListener {
            paintView.setEraser(15)
            MainActivity.selectedColor = "#ffffff"
            dismiss()
        }

        iii_stroke_width.setOnClickListener {
            paintView.setEraser(35)
            MainActivity.selectedColor = "#ffffff"
            dismiss()
        }

        iv_stroke_width.setOnClickListener {
            paintView.setEraser(50)
            MainActivity.selectedColor = "#ffffff"
            dismiss()
        }
    }
}