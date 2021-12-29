package com.example.dietapp.utils

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.dietapp.R

fun Fragment.setupDropdownMenu(items: List<String>, view: EditText?) {
    val adapter =
        ArrayAdapter(requireContext(), R.layout.spinner_item, items)
    (view as? AutoCompleteTextView)?.setAdapter(adapter)
}

fun Array<String>.getAsArrayList(): ArrayList<String> {
    val list = ArrayList<String>()
    this.forEach { list.add(it) }
    return list
}