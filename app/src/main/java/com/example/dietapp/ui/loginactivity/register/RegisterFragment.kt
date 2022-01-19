package com.example.dietapp.ui.loginactivity.register

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
import com.example.dietapp.utils.ArrayUtil
import com.example.dietapp.utils.PasswordUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by sharedViewModel()
    private val firebaseService = FirebaseService()
    private val auth = FirebaseAuth.getInstance()
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 123

    private fun showSnackbar(message: String, length: Int = Snackbar.LENGTH_LONG) {
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
                                val user = User(it.user!!.uid)
                                firebaseService.createUser(user)
                                viewModel.login(user.uid).observe(viewLifecycleOwner, {
                                    if (it) {
                                        val intent = Intent(context, MainActivity::class.java)
                                        this.startActivity(intent)
                                    }
                                })
                            }
                        }
                        .addOnFailureListener {
                            showSnackbar(it.message.toString())
                        }
                }
            }
        }
    }

    private fun setupTextFields() {
        email_input.doOnTextChanged { text, _, _, _ ->
            viewModel.setEmail(text.toString())
        }

        new_password_input.doOnTextChanged { text, _, _, _ ->
            val progress = viewModel.setNewPassword(text.toString())
            linearProgressIndicatorRegister.progress = progress
            val index = PasswordUtil.getIndex(progress)
            password_strength2.text = ArrayUtil.getArrayList(
                R.array.password_strength,
                requireContext()
            )[index]
            password_strength2.setTextColor(PasswordUtil.getColor(index))
        }

        repeat_password_input.doOnTextChanged { text, _, _, _ ->
            viewModel.setRepeatedPassword(text.toString())
        }
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
                                firebaseService.createUserWithGoogle(user)
                                viewModel.login(user.uid).observe(viewLifecycleOwner, {
                                    if (it) {
                                        val intent = Intent(context, MainActivity::class.java)
                                        this.startActivity(intent)
                                    }
                                })
                            }
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

    private fun initData() {
        email_input.setText(viewModel.email)
        new_password_input.setText(viewModel.newPassword)
        repeat_password_input.setText(viewModel.repeatedPassword)
    }

    override fun onResume() {
        super.onResume()
        initData()
    }
}