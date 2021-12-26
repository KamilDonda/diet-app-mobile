package com.example.dietapp.ui.loginactivity.register

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.dietapp.R
import com.example.dietapp.ui.mainactivity.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSignUpClick()
        register_back_button.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_startFragment)
        }
    }

    private fun setupSignUpClick() {
        register_button.setOnClickListener {
            val email = email_input.text?.trim().toString()
            val password = new_password_input.text?.trim().toString()
            val repeatPassword = repeat_password_input.text?.trim().toString()

            when {
//                email.isEmpty() -> showSnackbar(getString(R.string.email_is_empty))
//                password.isEmpty() -> showSnackbar(getString(R.string.password_is_empty))
//                password != repeatPassword -> showSnackbar(getString(R.string.different_passwords))
                else -> {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener {
                            if (it.user != null) {
                                val intent = Intent(requireContext(), MainActivity::class.java)
                                startActivity(intent)
                            }
                        }
                        .addOnFailureListener {
                            Log.w(TAG,"" +it.message.toString())
                        }
                }
            }
        }
    }
}