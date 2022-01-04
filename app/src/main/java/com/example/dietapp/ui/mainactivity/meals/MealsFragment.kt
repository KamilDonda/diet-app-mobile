package com.example.dietapp.ui.mainactivity.meals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.dietapp.R
import com.example.dietapp.adapters.MealsAdapter
import com.example.dietapp.ui.filter.FilterFragment
import kotlinx.android.synthetic.main.fragment_meals.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MealsFragment : Fragment() {

    private val viewModel: MealViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.prepareMeals()

        val mealsAdapter = MealsAdapter(viewModel)
        meals_rv.adapter = mealsAdapter

        viewModel.meals.observe(viewLifecycleOwner, {
            mealsAdapter.setList(it)
        })

        setupSearch()
        setupDialog()

        viewModel.chips.observe(viewLifecycleOwner, {
            meals_chipGroup.removeAllViewsInLayout()
            val texts = it.getTextsForChips(requireContext()).filterNotNull()
            viewModel.setupChips(meals_chipGroup, requireContext(), texts)
            viewModel.search()
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
        if (viewModel.stateInitialized()) {
            meals_rv.layoutManager?.onRestoreInstanceState(
                viewModel.restoreRecyclerViewState()
            )
        }
    }

    override fun onPause() {
        super.onPause()
        meals_rv.layoutManager?.onSaveInstanceState()?.let { viewModel.saveRecyclerViewState(it) }
    }
}