package com.example.dietapp.ui.mainactivity.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
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

        viewModel.hasInputFocus.observe(viewLifecycleOwner, {
            if (it == false) {
                hideKeyboard()
            }
        })
    }

    private fun setupTextFields() {
        old_password_input.doOnTextChanged { text, _, _, _ ->
            viewModel.setOldPassword(text.toString())
        }

        new_password_input.doOnTextChanged { text, _, _, _ ->
            linearProgressIndicator.progress = viewModel.setNewPassword(text.toString())
        }

        repeat_password_input.doOnTextChanged { text, _, _, _ ->
            viewModel.setRepeatedPassword(text.toString())
        }

        val fields = arrayOf(old_password_input, new_password_input, repeat_password_input)
        fields.forEachIndexed { index, it ->
            it.setOnFocusChangeListener { _, _ ->
                viewModel.hasInputFocus.value = fields.any { field -> field.hasFocus() }

                if (index > 0) {
                    if (viewModel.newPassword != viewModel.repeatedPassword &&
                        !(repeat_password_input.isFocused || new_password_input.isFocused)
                    ) {
                        repeat_password_field.error = "Hasła nie są takie same!"
                    } else {
                        repeat_password_field.error = null
                    }
                }
            }
        }
    }

    private fun initData() {
        old_password_input.setText(viewModel.oldPassword)
        new_password_input.setText(viewModel.newPassword)
        repeat_password_input.setText(viewModel.repeatedPassword)
    }

    override fun onResume() {
        super.onResume()
        initData()
    }
}