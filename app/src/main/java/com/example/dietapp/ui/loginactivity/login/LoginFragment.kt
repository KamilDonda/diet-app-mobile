package com.example.dietapp.ui.loginactivity.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.dietapp.R
import com.example.dietapp.database.sharedpreferences.Preferences
import com.example.dietapp.ui.mainactivity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.inject

class LoginFragment : Fragment() {

    private val sharedPreferences: Preferences by inject()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSignInClick()
        login_back_button.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_startFragment)
        }
    }

    private fun setupSignInClick() {
        login_button.setOnClickListener {
            val email = email_input.text?.trim().toString()
            val password = password_input.text?.trim().toString()

            when {
//                email.isEmpty() -> showSnackbar(getString(R.string.email_is_empty))
//                password.isEmpty() -> showSnackbar(getString(R.string.password_is_empty))
                else -> {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener {
                            if (it.user != null)
                            {
                                val intent = Intent(requireContext(), MainActivity::class.java)
                                sharedPreferences.setIsLogged(true)
                                startActivity(intent)
                            }
                        }
                        .addOnFailureListener {
                            //showSnackbar(it.message.toString())
                        }
                }
            }

        }
    }
}