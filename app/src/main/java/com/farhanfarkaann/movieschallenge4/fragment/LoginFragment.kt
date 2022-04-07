package com.farhanfarkaann.movieschallenge4.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.farhanfarkaann.movieschallenge4.R
import com.farhanfarkaann.movieschallenge4.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    lateinit var prefFile : SharedPreferences
    var isRemembered = false
    companion object{
        const val EMAILISI  = "EMAIL"
        const val PASSWORD = "PASSWORD"
        const val USERNAME = "USERNAME"
        const val   CONFPASSWORD = "CONFPASSWORD"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefFile =  requireActivity().getSharedPreferences("SHARED_PREF",Context.MODE_PRIVATE)
        isRemembered = prefFile.getBoolean("CHECKBOX",false)



        val email2 = prefFile.getString("EMAILIS","")
        val username = prefFile.getString("USERNAME","")
        val pass2 = prefFile.getString("PASSWORD","")

        if (isRemembered){
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }



        binding.btnLogin.setOnClickListener {
            val email: String = binding.etEmail.text.toString()
            val password: String = binding.etPassword.text.toString()
            val checked: Boolean = binding.checkbox.isChecked
            val editor: SharedPreferences.Editor = prefFile.edit()
            editor.putBoolean("CHECKBOX", checked)
            editor.apply()
            if (email.isEmpty() && password.isEmpty()) {
                Toast.makeText(context, "Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()

            } else {
                when {
                    email2 != email -> {
                        binding.tfEmail.error = "Email tidak Terdaftar."
                    }
                    pass2 != password -> {
                        binding.tfPassword.error = "Password  yang anda masukan salah."
                    }
//                email.isEmpty() -> {
//                Toast.makeText(context, "Harap Masukan Email", Toast.LENGTH_SHORT).show()
//                } password.isEmpty() ->{
//                Toast.makeText(context, "Harap Masukan Password", Toast.LENGTH_SHORT).show()
//                }
                    else -> {
                        Toast.makeText(context, "Selamat Datang $username", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                }
            }
        }

        binding.tvRegisterSwitch.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registFragment2)
        }
    }
}
