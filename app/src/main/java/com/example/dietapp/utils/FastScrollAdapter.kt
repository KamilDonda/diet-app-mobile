package com.example.dietapp.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R

class FastScrollAdapter(private val onClick: (Char) -> Unit) :
    RecyclerView.Adapter<FastScrollAdapter.Holder>() {
    inner class Holder(view: View) : RecyclerView.ViewHolder(view)

    private val _list = ('A'..'Z').toList()
    private val viewSet = mutableSetOf<CheckedTextView>()

    var checkedPosition: Int? = null
        private set

    fun resetCheckedPosition() {
        checkedPosition = null
    }

    override fun getItemCount() = _list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.letter, parent, false
        )
    )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val letterTextView = holder.itemView.findViewById<CheckedTextView>(R.id.letter)
        viewSet.add(letterTextView)

        val letter = _list[position]

        letterTextView.text = letter.toString()

        letterTextView.setOnClickListener {
            checkedPosition = position
            (viewSet.toList()).forEach {
                it.isChecked = false
            }
            letterTextView.isChecked = true
            onClick(letter)
        }

        letterTextView.isChecked = position == checkedPosition
    }
}
