package com.example.drawit

import android.graphics.BlurMaskFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.ChipGroup

class BlurBottomSheet(private val paintView: PaintView) : BottomSheetDialogFragment() {

    lateinit var choice_chip_group:ChipGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blur_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        choice_chip_group = view.findViewById(R.id.choice_chip_group)

                choice_chip_group.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.normal_chip -> {
                    paintView.doBlur(true)
                    paintView.blurEffect(BlurMaskFilter.Blur.NORMAL)
                    dismiss()
                }
                R.id.inner_chip -> {
                    paintView.doBlur(true)
                    paintView.blurEffect(BlurMaskFilter.Blur.INNER)
                    dismiss()
                }
                R.id.outer_chip -> {
                    paintView.doBlur(true)
                    paintView.blurEffect(BlurMaskFilter.Blur.OUTER)
                    dismiss()
                }
                R.id.solid_chip -> {
                    paintView.doBlur(true)
                    paintView.blurEffect(BlurMaskFilter.Blur.SOLID)
                    dismiss()
                }
                R.id.cancel_chip -> {
                    MainActivity.selectedColor = "#000000"
                    paintView.normal()
                    dismiss()
                }
            }
        }
    }
}