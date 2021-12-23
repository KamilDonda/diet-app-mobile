package com.example.dietapp.ui.loginactivity.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.dietapp.R
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : Fragment() {

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
}