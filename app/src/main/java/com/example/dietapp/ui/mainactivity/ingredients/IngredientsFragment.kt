package com.example.dietapp.ui.mainactivity.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.dietapp.R
import com.example.dietapp.adapters.IngredientsAdapter
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