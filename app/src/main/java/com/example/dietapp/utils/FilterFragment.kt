package com.example.dietapp.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.dietapp.R
import kotlinx.android.synthetic.main.fragment_filter.*

class FilterFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog_ok.setOnClickListener {
            dismiss()
        }

        dialog_cancel.setOnClickListener {
            dismiss()
        }

        initData()

        setupDropdownMenu(
            listOf(
                "Nazwa: A - Z",
                "Nazwa: Z - A",
                "Kalorie: rosnąco",
                "Kalorie: malejąco",
                "Białko: rosnąco",
                "Białko: malejąco",
                "Węglowodany:\nrosnąco",
                "Węglowodany:\nmalejąco",
                "Tłuszcze: rosnąco",
                "Tłuszcze: malejąco",
            ), order.editText
        )
    }

    private fun initData() {
        order.editText?.setText("Nazwa: A - Z")
    }
}