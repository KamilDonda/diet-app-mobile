package com.example.dietapp.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.dietapp.R
import com.example.dietapp.models.Filter
import com.example.dietapp.utils.getAsArrayList
import com.example.dietapp.utils.setupDropdownMenu
import kotlinx.android.synthetic.main.fragment_filter.*

class FilterFragment(private val viewModel: FilterViewModel) : DialogFragment() {

    private lateinit var filters: ArrayList<String>

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

        filters = resources.getStringArray(R.array.filter_order).getAsArrayList()

        dialog_ok.setOnClickListener {
            viewModel.setFilterOptions(getFilterOptions())
            dismiss()
        }

        dialog_cancel.setOnClickListener {
            dismiss()
        }

        order.editText?.setText(filters[0])
        setupDropdownMenu(filters, order.editText)
    }

    private fun getFilterOptions(): Filter {
        val selectedOrderPosition = filters.indexOf(order.editText?.text.toString())
        val caloriesMin = calories_start_input.text.toString().toIntOrNull() ?: 0
        val caloriesMax = calories_end_input.text.toString().toIntOrNull() ?: 0
        val proteinsMin = proteins_start_input.text.toString().toIntOrNull() ?: 0
        val proteinsMax = proteins_end_input.text.toString().toIntOrNull() ?: 0
        val fatsMin = fats_start_input.text.toString().toIntOrNull() ?: 0
        val fatsMax = fats_end_input.text.toString().toIntOrNull() ?: 0
        val carbsMin = carbs_start_input.text.toString().toIntOrNull() ?: 0
        val carbsMax = carbs_end_input.text.toString().toIntOrNull() ?: 0
        val isChecked = filter_checkBox.isChecked
        return Filter(
            selectedOrderPosition,
            caloriesMin,
            caloriesMax,
            proteinsMin,
            proteinsMax,
            fatsMin,
            fatsMax,
            carbsMin,
            carbsMax,
            isChecked
        )
    }
}