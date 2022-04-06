package com.farhanfarkaann.movieschallenge4.movies

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.farhanfarkaann.movieschallenge4.databinding.ActivityRecyclerBinding
import com.farhanfarkaann.movieschallenge4.fragment.HomeFragment

class MoviesAdapter(
    private val listMovies: List<MoviesData>,
    private val update: (MoviesData) -> Unit,
    private val delete: (MoviesData) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    class ViewHolder(val itemViewBinding: ActivityRecyclerBinding) : RecyclerView.ViewHolder(itemViewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ActivityRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listMovies.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemViewBinding.apply {
            tvJudul.text = listMovies[position].judul
            tvDurasi.text = listMovies[position].durasi.toString()
            tvSinopsis.text = listMovies[position].sinopsis
            tvTahun.text = listMovies[position].tahun.toString()
            tvRating.text = listMovies[position].rating.toString()


            ivEdit.setOnClickListener {
                update.invoke(listMovies[position])
//                val intentKe = Intent(it.context,RecyclerView::javaClass)
//                val intentKeEditActivity = Intent(it.context,HomeFragment::class.java)
//                intentKeEditActivity.putExtra("movies", listMovies[position])
//                it.context.startActivity(intentKeEditActivity)

            }
            ivDelete.setOnClickListener{
                delete.invoke(listMovies[position])
            }
//            idDetail.setOnClickListener{
//                detail.invoke(listMovies[position])
//            }


//            ivDelete.setOnClickListener {
//                AlertDialog.Builder(it.context).setPositiveButton("Ya") { p0, p1 ->
//                    val mDb = MoviesDatabase.getInstance(holder.itemView.context)
//
//                    GlobalScope.async {
//                        val result = mDb?.moviesDao()?.deleteStudent(listMovies[position])
//
//                        (holder.itemView.context as HomeFragment).run{
//                            if (result != 0) {
//                                Toast.makeText(
//                                    it.context,
//                                    "Data ${listMovies[position].judul} berhasil dihapus",
//                                    Toast.LENGTH_LONG
//                                ).show()
//                            } else {
//                                Toast.makeText(
//                                    it.context,
//                                    "Data ${listMovies[position].judul} Gagal dihapus",
//                                    Toast.LENGTH_LONG
//                                ).show()
//                            }
//                        }
//
//                        (holder.itemView.context as HomeFragment).fetchData()
//                    }
//                }.setNegativeButton("Tidak")
//                { p0, p1 ->
//                    p0.dismiss()
//                }
//                    .setMessage("Apakah Anda Yakin ingin menghapus film ${listMovies[position].judul}")
//                    .setTitle("Konfirmasi Hapus").create().show()
//            }
        }
    }
}