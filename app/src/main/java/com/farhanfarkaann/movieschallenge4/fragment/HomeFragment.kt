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
import com.farhanfarkaann.movieschallenge4.movies.MoviesAdapter
import com.farhanfarkaann.movieschallenge4.movies.MoviesData
import com.farhanfarkaann.movieschallenge4.room.MoviesDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*


class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var mDB : MoviesDatabase? = null

    lateinit var prefFile : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root


//        fabAdd.setOnClickListener {
////            val keActivityAdd = Intent(this, AddActivity::class.java)
////            startActivity(keActivityAdd)
//            findNavController().navigate(R.id.action.)
//        }
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

        mDB = MoviesDatabase.getInstance(requireContext())

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
               val  mDB = MoviesDatabase.getInstance(requireContext())
                val objectMovies = MoviesData(
                    null,
                    dialogBinding.etJudul.text.toString(),
                    dialogBinding.etDurasi.text.toString().toInt(),
                    dialogBinding.etSinopsis.text.toString(),
                    dialogBinding.etTahun.text.toString().toInt(),
                    dialogBinding.etRating.text.toString().toDouble()
                )

                lifecycleScope.launch(Dispatchers.IO) {
                    val result = mDB?.moviesDao()?.insertStudent(objectMovies)
                    runBlocking(Dispatchers.Main) {
                        if(result != 0.toLong() ){
                            //sukses
                            Toast.makeText(context,"Sukses menambahkan ${objectMovies.judul}",Toast.LENGTH_LONG).show()
                            fetchData()
                            dialog.dismiss()
                        }else{
                            //gagal
                            Toast.makeText(context,"Gagal menambahkan ${objectMovies.judul}",Toast.LENGTH_LONG).show()
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
                    val adapter = MoviesAdapter(
                        it,
                    delete = { moviesData ->
                        AlertDialog.Builder(requireContext()).setPositiveButton("Ya") { p0, p1 ->
                            val mDb = MoviesDatabase.getInstance(requireContext())

                            GlobalScope.async {
                                val result = mDb?.moviesDao()?.deleteStudent(moviesData)

                                activity?.runOnUiThread {
                                    if (result != 0) {
                                        Toast.makeText(
                                            requireContext(),
                                            "Data ${moviesData.judul} berhasil dihapus",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            requireContext(),
                                            "Data ${moviesData.judul} Gagal dihapus",
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
                            .setMessage("Apakah Anda Yakin ingin menghapus film ${moviesData.judul}")
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




                                dialogBinding.etJudul.setText(moviesData!!.judul)
                                dialogBinding.tvId.setText(moviesData!!.id.toString())

                                dialogBinding.etDurasi.setText(moviesData!!.durasi.toString())
                                dialogBinding.etSinopsis.setText(moviesData!!.sinopsis)
                                dialogBinding.etTahun.setText(moviesData!!.tahun.toString())
                                dialogBinding.etRating.setText(moviesData!!.rating.toString())

                            dialogBinding.btnCancel.setOnClickListener {
                                dialog.dismiss()
                            }
                            dialogBinding.btnSave.setOnClickListener {
                                val mDb = MoviesDatabase.getInstance(requireContext())
                                val objectMovies = MoviesData(
                                     dialogBinding.tvId.text.toString().toInt(),
                                    dialogBinding.etJudul.text.toString(),
                                            dialogBinding.etDurasi.text.toString().toInt(),
                                            dialogBinding.etSinopsis.text.toString(),
                                            dialogBinding.etTahun.text.toString().toInt(),
                                            dialogBinding.etRating.text.toString().toDouble(),
                                )
                                lifecycleScope.launch(Dispatchers.IO){
                                    val result = mDb?.moviesDao()?.updateStudent(objectMovies!!)

                                    runBlocking (Dispatchers.Main){
                                        if(result!=0){
                                            Toast.makeText(context,"Sukses mengubah ${objectMovies?.judul}", Toast.LENGTH_LONG).show()
                                            fetchData()
                                            dialog.dismiss()
                                        }else{
                                            Toast.makeText(context,"Gagal mengubah ${objectMovies?.judul}", Toast.LENGTH_LONG)
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
        MoviesDatabase.destroyInstance()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
