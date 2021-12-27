package com.example.dietapp.ui.loginactivity.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.dietapp.R
import com.example.dietapp.services.FirebaseRepository
import com.example.dietapp.services.User
import com.example.dietapp.ui.mainactivity.MainActivity
import com.example.dietapp.utils.PasswordUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.new_password_input
import kotlinx.android.synthetic.main.fragment_register.repeat_password_input

class RegisterFragment : Fragment() {

    private val repository = FirebaseRepository()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 123

    protected fun showSnackbar(message: String, length: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(requireView(), message, length).show()
    }

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
        setupSignInWithGoogleClick()
        setupTextFields()
        register_back_button.setOnClickListener {
            it.findNavController().navigate(R.id.action_registerFragment_to_startFragment)
        }
    }

    //Logowanie przy pomocy email i hasła
    private fun setupSignUpClick() {
        register_button.setOnClickListener {
            val email = email_input.text?.trim().toString()
            val password = new_password_input.text?.trim().toString()
            val repeatPassword = repeat_password_input.text?.trim().toString()

            when {
                email.isEmpty() -> showSnackbar(getString(R.string.email_is_empty))
                password.isEmpty() -> showSnackbar(getString(R.string.password_is_empty))
                password != repeatPassword -> showSnackbar(getString(R.string.different_passwords))
                else -> {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener {
                            if (it.user != null) {
                                val intent = Intent(requireContext(), MainActivity::class.java)
                                val user = User(it.user!!.uid)
                                repository.createUser(user)
                                startActivity(intent)
                            }
                        }
                        .addOnFailureListener {
                            showSnackbar(it.message.toString())
                        }
                }
            }
        }
    }

    //Pasek siły hasła
    var newPassword: String = ""
        private set

    fun setNewPassword(password: String): Int {
        this.newPassword = password
        return PasswordUtil.securityLevel(newPassword)
    }

    private fun setupTextFields() {
        new_password_input.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                linearProgressIndicatorRegister.progress = setNewPassword(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })
    }

    //Uwierzytelnianie przy pomocy konta google
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val token = task.getResult(ApiException::class.java)!!.idToken!!
                val credential = GoogleAuthProvider.getCredential(token, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            if (it.result!!.user != null) {
                                val user = User(it.result!!.user!!.uid)
                                repository.createUserWithGoogle(user)
                            }
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            showSnackbar(it.exception?.message.toString())
                        }
                    }
            } catch (e: ApiException) {
                showSnackbar(e.message.toString())
            }
        }
    }

    private fun setupSignInWithGoogleClick() {
        register_google.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(
                signInIntent,
                RC_SIGN_IN
            )
        }
    }
}