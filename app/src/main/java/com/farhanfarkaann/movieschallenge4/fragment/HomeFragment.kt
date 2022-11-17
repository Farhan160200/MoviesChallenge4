package com.farhanfarkaann.movieschallenge4.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.farhanfarkaann.movieschallenge4.R
import com.farhanfarkaann.movieschallenge4.databinding.ActivityAddBinding
import com.farhanfarkaann.movieschallenge4.databinding.ActivityEditBinding
import com.farhanfarkaann.movieschallenge4.databinding.FragmentHomeBinding
import com.farhanfarkaann.movieschallenge4.hargapasar.HargaPasarAdapter
import com.farhanfarkaann.movieschallenge4.hargapasar.HargaPasarData
import com.farhanfarkaann.movieschallenge4.room.HargaPasarDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*


class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var mDB : HargaPasarDatabase? = null

    lateinit var prefFile : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root

    }
    override fun onResume() {
        super.onResume()
        fetchData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefFile = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val username = prefFile.getString("USERNAME","")
        binding.tvUser.setText(username)


        binding.btnLogout.setOnClickListener{
            AlertDialog.Builder(requireContext()).setPositiveButton("Ya") { p0, p1 ->
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                val editor = prefFile.edit()
                editor.clear()
                editor.apply()
                binding.tvUser.setText("")
                binding.btnLogout.setText("Login")
                Toast.makeText(context, "anda berhasil logout", Toast.LENGTH_SHORT).show()

            }.setNegativeButton("Tidak"
            ) { p0, p1 ->
                p0.dismiss()
            }
                .setMessage("Apakah Anda Yakin ingin Logout").setTitle("Konfirmasi Logout").create().show()
        }

        mDB = HargaPasarDatabase.getInstance(requireContext())

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        fetchData()


        binding.fabAdd.setOnClickListener{
            val dialogBinding = ActivityAddBinding.inflate(LayoutInflater.from(requireContext()))
            val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
            dialogBuilder.setView(dialogBinding.root)
            val dialog = dialogBuilder.create()
            dialog.setCancelable(false)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.LTGRAY))
            dialogBinding.btnCancel.setOnClickListener{
                dialog.dismiss()
            }

            dialogBinding.btnSave.setOnClickListener {
               val  mDB = HargaPasarDatabase.getInstance(requireContext())
                val objectMovies = HargaPasarData(
                    null,
                    dialogBinding.etJudul.text.toString(),
                    dialogBinding.etDurasi.text.toString().toInt(),
                    dialogBinding.etSinopsis.text.toString(),

                )

                lifecycleScope.launch(Dispatchers.IO) {
                    val result = mDB?.moviesDao()?.insertStudent(objectMovies)
                    runBlocking(Dispatchers.Main) {
                        if(result != 0.toLong() ){
                            //sukses
                            Toast.makeText(context,"Sukses menambahkan ${objectMovies.nama}",Toast.LENGTH_LONG).show()
                            fetchData()
                            dialog.dismiss()
                        }else{
                            //gagal
                            Toast.makeText(context,"Gagal menambahkan ${objectMovies.nama}",Toast.LENGTH_LONG).show()
                            dialog.dismiss()
                        }

                    }
                }
            }
            dialog.show()
    }
}
    fun fetchData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val listMovies = mDB?.moviesDao()?.getAllStudent()
            activity?.runOnUiThread {
                listMovies?.let {
                    val adapter = HargaPasarAdapter(
                        it,
                    delete = { moviesData ->
                        AlertDialog.Builder(requireContext()).setPositiveButton("Ya") { p0, p1 ->
                            val mDb = HargaPasarDatabase.getInstance(requireContext())

                            GlobalScope.async {
                                val result = mDb?.moviesDao()?.deleteStudent(moviesData)

                                activity?.runOnUiThread {
                                    if (result != 0) {
                                        Toast.makeText(
                                            requireContext(),
                                            "Data ${moviesData.nama} berhasil dihapus",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            requireContext(),
                                            "Data ${moviesData.nama} Gagal dihapus",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                               fetchData()
                            }
                        }.setNegativeButton("Tidak")
                        { p0, p1 ->
                            p0.dismiss()
                        }
                            .setMessage("Apakah Anda Yakin ingin menghapus film ${moviesData.nama}")
                            .setTitle("Konfirmasi Hapus")
                            .create()
                            .show()
                    },
                        update = {
                            moviesData ->
                            val dialogBinding = ActivityEditBinding.inflate(LayoutInflater.from(requireContext()))
                            val dialogBuilder = AlertDialog.Builder(requireContext())
                            dialogBuilder.setView(dialogBinding.root)
                            val dialog = dialogBuilder.create()
                            dialog.setCancelable(false)
                            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.LTGRAY))
                            dialogBinding.tvTitleEdit.text = " Update FIlm"




                                dialogBinding.etJudul.setText(moviesData!!.nama)
                                dialogBinding.tvId.setText(moviesData!!.id.toString())

                                dialogBinding.etDurasi.setText(moviesData!!.harga.toString())
                                dialogBinding.etSinopsis.setText(moviesData!!.deskripsi)

                            dialogBinding.btnCancel.setOnClickListener {
                                dialog.dismiss()
                            }
                            dialogBinding.btnSave.setOnClickListener {
                                val mDb = HargaPasarDatabase.getInstance(requireContext())
                                val objectMovies = HargaPasarData(

                                    dialogBinding.tvId.text.toString().toInt(),
                                    dialogBinding.etJudul.text.toString(),
                                    dialogBinding.etDurasi.text.toString().toInt(),
                                    dialogBinding.etSinopsis.text.toString(),
                                )
                                lifecycleScope.launch(Dispatchers.IO){
                                    val result = mDb?.moviesDao()?.updateStudent(objectMovies!!)

                                    runBlocking (Dispatchers.Main){
                                        if(result!=0){
                                            Toast.makeText(context,"Sukses mengubah ${objectMovies?.nama}", Toast.LENGTH_LONG).show()
                                            fetchData()
                                            dialog.dismiss()
                                        }else{
                                            Toast.makeText(context,"Gagal mengubah ${objectMovies?.nama}", Toast.LENGTH_LONG)
                                                .show()
                                            dialog.dismiss()
                                        }
                                    }
                                }
                            }
                            dialog.show()
                        }
                    )
                    binding.recyclerView.adapter = adapter
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        HargaPasarDatabase.destroyInstance()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
