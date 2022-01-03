package com.example.dietapp.ui.filter

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dietapp.R
import com.example.dietapp.models.Filter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup

abstract class FilterViewModel : ViewModel() {

    var filter: Filter = Filter()
        private set

    fun setFilterOptions(filter: Filter) {
        this.filter = filter
    }

    fun intOrNullToString(value: Int?): String {
        return when (value) {
            null, 0 -> return ""
            else -> value.toString()
        }
    }

    val chips = MutableLiveData(Filter())

    fun setupChips(chipGroup: ChipGroup, context: Context, ranges: List<String>) {
        ranges.forEach { option ->
            val chip = Chip(context)
            val drawable = ChipDrawable.createFromAttributes(
                context,
                null,
                0,
                R.style.FilterChip
            )
            chip.setChipDrawable(drawable)
            chip.text = option
            chip.isClickable = true
            chip.isCheckable = false
            chip.isCloseIconVisible = false

            chip.setOnClickListener {
                when {
                    option.contains(context.getString(R.string.calories)) -> {
                        setFilterOptions(filter.copy(caloriesMin = 0, caloriesMax = null))
                    }
                    option.contains(context.getString(R.string.proteins)) -> {
                        setFilterOptions(filter.copy(proteinsMin = 0, proteinsMax = null))
                    }
                    option.contains(context.getString(R.string.fats)) -> {
                        setFilterOptions(filter.copy(fatsMin = 0, fatsMax = null))
                    }
                    option.contains(context.getString(R.string.carbs)) -> {
                        setFilterOptions(filter.copy(carbsMin = 0, carbsMax = null))
                    }
                    else -> {
                        setFilterOptions(filter.copy(order = 0))
                    }
                }
                chipGroup.removeView(chip)
                chips.postValue(filter)
            }

            chipGroup.addView(chip)
        }
    }
}