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
            tvDurasi.text = listMovies[position].durasi.toString() + " Menit"
            tvSinopsis.text = listMovies[position].sinopsis
            tvTahun.text = listMovies[position].tahun.toString()
            tvRating.text = listMovies[position].rating.toString() + " IMDb"


            ivEdit.setOnClickListener {
                update.invoke(listMovies[position])

            }

            ivDelete.setOnClickListener{
                delete.invoke(listMovies[position])
            }
        }
    }
}