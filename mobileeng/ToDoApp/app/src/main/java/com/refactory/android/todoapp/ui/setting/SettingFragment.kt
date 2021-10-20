package com.refactory.android.todoapp.ui.setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.refactory.android.todoapp.R
import com.refactory.android.todoapp.databinding.ActivityLoginBinding
import com.refactory.android.todoapp.databinding.FragmentSettingBinding
import com.refactory.android.todoapp.ui.login.LoginActivity

class SettingFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        checkUser()

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            checkUser()
        }
    }

    private fun checkUser(){
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }
    }
}