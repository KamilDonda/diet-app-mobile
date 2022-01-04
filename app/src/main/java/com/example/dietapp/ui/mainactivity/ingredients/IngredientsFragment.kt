package com.example.dietapp.ui.mainactivity.ingredients

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.example.dietapp.R
import com.example.dietapp.adapters.IngredientsAdapter
import com.example.dietapp.ui.filter.FilterFragment
import com.example.dietapp.utils.FastScrollAdapter
import kotlinx.android.synthetic.main.fragment_ingredients.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class IngredientsFragment : Fragment() {

    private val viewModel: IngredientViewModel by sharedViewModel()
    private lateinit var ingredientsAdapter: IngredientsAdapter
    private lateinit var fastScrollAdapter: FastScrollAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ingredients, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.prepareIngredients()

        ingredientsAdapter = IngredientsAdapter(viewModel)
        ingredients_rv.adapter = ingredientsAdapter

        viewModel.ingredients.observe(viewLifecycleOwner, {
            ingredientsAdapter.setList(it)
        })

        fastScrollAdapter = FastScrollAdapter { onLetterClick(it) }
        fastScroll.adapter = fastScrollAdapter

        setupSearch()
        setupDialog()

        viewModel.chips.observe(viewLifecycleOwner, {
            ingredients_chipGroup.removeAllViewsInLayout()
            val texts = it.getTextsForChips(requireContext(), false).filterNotNull()
            viewModel.setupChips(ingredients_chipGroup, requireContext(), texts, false)
            viewModel.search()
        })
    }

    private fun setupDialog() {
        filter_button.setOnClickListener {
            val dialog = FilterFragment(viewModel, false)

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

    private fun onLetterClick(letter: Char) {
        val smoothScroller: SmoothScroller = object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
        val pos = ingredientsAdapter.getFirstAppearancePosition(letter)
        smoothScroller.targetPosition = pos
        Log.v("ttt", "pos: $pos")
        ingredients_rv.layoutManager!!.startSmoothScroll(smoothScroller)
    }

    override fun onResume() {
        super.onResume()
        searchInput.setText(viewModel.searchText)
        if (viewModel.stateInitialized()) {
            ingredients_rv.layoutManager?.onRestoreInstanceState(
                viewModel.restoreRecyclerViewState()
            )
        }
    }

    override fun onPause() {
        super.onPause()
        ingredients_rv.layoutManager?.onSaveInstanceState()
            ?.let { viewModel.saveRecyclerViewState(it) }
    }
}