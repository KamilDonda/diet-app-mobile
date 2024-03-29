package com.example.dietapp.ui.loginactivity.start

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.dietapp.R
import com.example.dietapp.services.LoginService
import com.example.dietapp.sharedpreferences.Preferences
import com.example.dietapp.ui.mainactivity.MainActivity
import kotlinx.android.synthetic.main.fragment_start.*
import org.koin.android.ext.android.inject

class StartFragment : Fragment() {

    private val sharedPreferences: Preferences by inject()
    private val loginService: LoginService by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        start_login_button.setOnClickListener {
            it.findNavController().navigate(R.id.action_startFragment_to_loginFragment)
        }

        start_register_button.setOnClickListener {
            it.findNavController().navigate(R.id.action_startFragment_to_registerFragment)
        }
    }

    override fun onStart() {
        super.onStart()

        if (sharedPreferences.getIsLogged()) {
            progressBar.visibility = View.VISIBLE
            start_login_button.isEnabled = false
            start_register_button.isEnabled = false
            loginService.synchronize(sharedPreferences.getUserId()).observe(viewLifecycleOwner, {
                if (it) {
                    val intent = Intent(context, MainActivity::class.java)
                    this.startActivity(intent)
                    progressBar.visibility = View.GONE
                    start_login_button.isEnabled = true
                    start_register_button.isEnabled = true
                }
            })
        }
    }
}