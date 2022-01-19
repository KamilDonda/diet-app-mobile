package com.example.dietapp.ui.mainactivity.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.dietapp.R
import com.example.dietapp.utils.ArrayUtil
import com.example.dietapp.utils.HideKeyboard.hideKeyboard
import com.example.dietapp.utils.PasswordUtil
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
//            val user = FirebaseAuth.getInstance().currentUser
            val oldPassword = old_password_input.text?.trim().toString()
            val password = new_password_input.text?.trim().toString()
            val repeatPassword = repeat_password_input.text?.trim().toString()

            when {
                oldPassword.isEmpty() -> showSnackbar(getString(R.string.old_password_is_empty))
                password.isEmpty() -> showSnackbar(getString(R.string.password_is_empty))
                password != repeatPassword -> showSnackbar(getString(R.string.different_passwords))
                else -> {
                    val user = Firebase.auth.currentUser!!

                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, oldPassword)

                    user.reauthenticate(credential)
                        .addOnCompleteListener { Log.d(TAG, "User re-authenticated.") }

                    user.updatePassword(password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            showSnackbar("Hasło zostało zmienione!")
                        } else {
                            showSnackbar("Coś poszło nie tak :(")
                        }
                        Log.v(TAG, task.toString())
                    }.addOnFailureListener {
                        Log.v(TAG, it.stackTraceToString())
                    }
                }
            }
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
            val progress = viewModel.setNewPassword(text.toString())
            linearProgressIndicator.progress = progress
            val index = PasswordUtil.getIndex(progress)
            password_strength.text = ArrayUtil.getArrayList(
                R.array.password_strength,
                requireContext()
            )[index]
            password_strength.setTextColor(PasswordUtil.getColor(index))
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

    private fun showSnackbar(message: String, length: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(requireView(), message, length).show()
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    companion object {
        const val TAG = "FIREBASE_AUTHENTICATION"
    }
}