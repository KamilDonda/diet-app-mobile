package com.example.dietapp.ui.mainactivity.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.dietapp.R
import com.example.dietapp.adapters.IngredientsAdapter
import com.example.dietapp.ui.filter.FilterFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import kotlinx.android.synthetic.main.fragment_ingredients.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class IngredientsFragment : Fragment() {

    private val viewModel: IngredientViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ingredients, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ingredientsAdapter = IngredientsAdapter(viewModel)
        ingredients_rv.adapter = ingredientsAdapter

        viewModel.ingredients.observe(viewLifecycleOwner, {
            ingredientsAdapter.setList(it)
        })

        setupSearch()
        setupDialog()

        viewModel.chips.observe(viewLifecycleOwner, {
            ingredients_chipGroup.removeAllViewsInLayout()
            val ranges = it.getRanges().filterNotNull()

            ranges.forEach { option ->
                val chip = Chip(context)
                val drawable = ChipDrawable.createFromAttributes(
                    requireContext(),
                    null,
                    0,
                    R.style.FilterChip
                )
                chip.setChipDrawable(drawable)
                chip.text = option
                chip.isClickable = true
                chip.isCheckable = false
                chip.isCloseIconVisible = true

                ingredients_chipGroup.addView(chip)
            }
        })
    }

    private fun setupDialog() {
        filter_button.setOnClickListener {
            val dialog = FilterFragment(viewModel)

            dialog.show(requireActivity().supportFragmentManager, "FILTER_INGREDIENTS")
        }
    }

    private fun setupSearch() {
        searchInput.doOnTextChanged { text, _, _, _ ->
            viewModel.setSearchText(text.toString())
        }

        searchField.setStartIconOnClickListener {
            viewModel.search()
        }
    }

    override fun onResume() {
        super.onResume()
        searchInput.setText(viewModel.searchText)
    }
}