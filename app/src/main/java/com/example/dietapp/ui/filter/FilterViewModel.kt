package com.example.dietapp.ui.filter

import androidx.lifecycle.ViewModel
import com.example.dietapp.models.Filter

abstract class FilterViewModel : ViewModel() {

    var filter: Filter = Filter()
        private set

    fun setFilterOptions(filter: Filter) {
        this.filter = filter
    }
}