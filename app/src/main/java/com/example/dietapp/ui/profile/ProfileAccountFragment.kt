package com.example.dietapp.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dietapp.R
import com.example.dietapp.utils.HideKeyboard.hideKeyboard
import kotlinx.android.synthetic.main.fragment_profile_account.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ProfileAccountFragment : Fragment() {

    private val viewModel: ProfileViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTextFields()

        change_password_button.setOnClickListener {
        }
    }

    private fun setupTextFields() {
        old_password_input.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setOldPassword(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        new_password_input.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setNewPassword(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        repeat_password_input.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setRepeatedPassword(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        val fields = arrayOf(old_password_input, new_password_input, repeat_password_input)
        fields.forEachIndexed { index, it ->
            it.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    hideKeyboard()
                }
                if (index == fields.size - 1) {
                    if (viewModel.newPassword != viewModel.repeatedPassword && !repeat_password_input.isFocused) {
                        repeat_password_field.error = "Hasła nie są takie same!"
                    } else {
                        repeat_password_field.error = null
                    }
                }
            }
        }
    }
}