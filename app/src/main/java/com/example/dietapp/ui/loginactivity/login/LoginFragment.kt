package com.example.dietapp.ui.loginactivity.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.dietapp.R
import com.example.dietapp.database.models.User
import com.example.dietapp.services.FirebaseService
import com.example.dietapp.ui.mainactivity.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment : Fragment() {

    private val firebaseService = FirebaseService()
    private val viewModel: LoginViewModel by sharedViewModel()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 123

    protected fun showSnackbar(message: String, length: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(requireView(), message, length).show()
        progressBar.visibility = View.GONE
        login_button.isEnabled = true
        login_google.isEnabled = true
    }

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
        setupTextFields()
        setupSignInWithGoogleClick()
        login_back_button.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_startFragment)
        }
    }

    private fun setupSignInClick() {
        login_button.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val email = email_input.text?.trim().toString()
            val password = password_input.text?.trim().toString()

            when {
                email.isEmpty() -> showSnackbar(getString(R.string.email_is_empty))
                password.isEmpty() -> showSnackbar(getString(R.string.password_is_empty))
                else -> {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener {
                            if (it.user != null) {
                                viewModel.login(it.user!!.uid).observe(viewLifecycleOwner, {
                                    login(it)
                                })
                            }
                        }
                        .addOnFailureListener {
                            showSnackbar("Coś poszło nie tak :(")
                        }
                }
            }

        }
    }

    private fun setupTextFields() {
        email_input.doOnTextChanged { text, _, _, _ ->
            viewModel.setEmail(text.toString())
        }

        password_input.doOnTextChanged { text, _, _, _ ->
            viewModel.setPassword(text.toString())
        }
    }

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
                                firebaseService.createUserWithGoogle(user)
                                viewModel.login(user.uid).observe(viewLifecycleOwner, {
                                    login(it)
                                })
                            }
                        } else {
                            showSnackbar("Coś poszło nie tak :(")
                        }
                    }
            } catch (e: ApiException) {
                showSnackbar("Coś poszło nie tak :(")
            }
        }
    }

    private fun setupSignInWithGoogleClick() {
        login_google.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(
                signInIntent,
                RC_SIGN_IN
            )
        }
    }

    private fun initData() {
        email_input.setText(viewModel.email)
        password_input.setText(viewModel.password)
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    private fun login(canLogIn: Boolean) {
        login_button.isEnabled = false
        login_google.isEnabled = false

        if (canLogIn) {
            val intent = Intent(context, MainActivity::class.java)
            this.startActivity(intent)
            login_button.isEnabled = true
            login_google.isEnabled = true
            progressBar.visibility = View.GONE
        }
    }
}