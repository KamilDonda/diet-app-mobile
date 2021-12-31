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
import com.google.android.material.textview.MaterialTextView
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
            val f = getFilterOptions()
            viewModel.setFilterOptions(f)

            if (validateInputs(f)) {
                viewModel.chips.value = f
                dismiss()
            }
        }

        dialog_cancel.setOnClickListener {
            dismiss()
        }

        dialog_reset.setOnClickListener {
            initData(Filter())
        }

        initData(viewModel.filter)
    }

    private fun initData(f: Filter) {
        filters.indexOf(order.editText?.text.toString())

        order.editText?.setText(filters[f.order])
        calories_start_input.setText(viewModel.intOrNullToString(f.caloriesMin))
        calories_end_input.setText(viewModel.intOrNullToString(f.caloriesMax))
        proteins_start_input.setText(viewModel.intOrNullToString(f.proteinsMin))
        proteins_end_input.setText(viewModel.intOrNullToString(f.proteinsMax))
        fats_start_input.setText(viewModel.intOrNullToString(f.fatsMin))
        fats_end_input.setText(viewModel.intOrNullToString(f.fatsMax))
        carbs_start_input.setText(viewModel.intOrNullToString(f.carbsMin))
        carbs_end_input.setText(viewModel.intOrNullToString(f.carbsMax))
        filter_checkBox.isChecked = f.isChecked

        setupDropdownMenu(filters, order.editText)
    }

    private fun validateInputs(f: Filter): Boolean {
        val a = compareInputs(f.caloriesMin, f.caloriesMax, calories_error)
        val b = compareInputs(f.proteinsMin, f.proteinsMax, proteins_error)
        val c = compareInputs(f.fatsMin, f.fatsMax, fats_error)
        val d = compareInputs(f.carbsMin, f.carbsMax, carbs_error)
        return a && b && c && d
    }

    private fun compareInputs(min: Int, max: Int?, errorView: MaterialTextView): Boolean {
        return if (max != null && max < min) {
            errorView.text = "Wartość końcowa nie może być mniejsza od początkowej"
            errorView.error = ""
            false
        } else {
            errorView.text = null
            errorView.error = null
            true
        }
    }

    private fun getFilterOptions(): Filter {
        val selectedOrderPosition = filters.indexOf(order.editText?.text.toString())
        val caloriesMin = calories_start_input.text.toString().toIntOrNull() ?: 0
        val caloriesMax = calories_end_input.text.toString().toIntOrNull()
        val proteinsMin = proteins_start_input.text.toString().toIntOrNull() ?: 0
        val proteinsMax = proteins_end_input.text.toString().toIntOrNull()
        val fatsMin = fats_start_input.text.toString().toIntOrNull() ?: 0
        val fatsMax = fats_end_input.text.toString().toIntOrNull()
        val carbsMin = carbs_start_input.text.toString().toIntOrNull() ?: 0
        val carbsMax = carbs_end_input.text.toString().toIntOrNull()
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