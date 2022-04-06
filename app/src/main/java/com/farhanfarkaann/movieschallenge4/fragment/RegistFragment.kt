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
import com.farhanfarkaann.movieschallenge4.databinding.FragmentRegistBinding

class RegistFragment : Fragment() {

    private lateinit var binding: FragmentRegistBinding
    private val sharedPrefFile  = "kotlinsharedpreference"
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
        binding = FragmentRegistBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences : SharedPreferences =
            requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        binding.btnDaftar.setOnClickListener {
            val username : String = binding.etUsername.text.toString()
            val emailIsi : String = binding.etEmail.text.toString()
            val password : String  = binding.etPassword.text.toString()
            val confPassword : String = binding.etConfirmPass.text.toString()
            val editor : SharedPreferences.Editor = sharedPreferences.edit()

            when{
                username.isEmpty() -> {
                    binding.tfUsername.error = getString(com.google.android.material.R.string.error_icon_content_description)
                }
                emailIsi.isEmpty() -> {
                    binding.tfEmail.error = "Tidak Boleh Kosong!"
                }
                password.isEmpty() -> {
                    binding.tfPassword.error = "Tidak Bolek Kosong!"
                }
                confPassword.isEmpty() -> {
                    binding.tfConfirmPass.error = " Tidak Boleh Kosong!"
                }
                password != confPassword -> {
                    Toast.makeText(context, "Password Tidak Sama ", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    editor.putString("USERNAME",username)
                    editor.putString("EMAILIS",emailIsi)
                    editor.putString("PASSWORD",password)
                    editor.putString(CONFPASSWORD, confPassword)
                    editor.apply()
                    Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show()

//                    val intentHomePage = Intent(this,MainActivity::class.java)
//                    startActivity(intentHomePage)
                    findNavController().navigate(R.id.action_registFragment2_to_loginFragment)

                }
            }
        }
    }

}