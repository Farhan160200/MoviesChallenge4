package com.farhanfarkaann.movieschallenge4.hargapasar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.farhanfarkaann.movieschallenge4.databinding.ActivityRecyclerBinding

class HargaPasarAdapter(
    private val listMovies: List<HargaPasarData>,
    private val update: (HargaPasarData) -> Unit,
    private val delete: (HargaPasarData) -> Unit
) : RecyclerView.Adapter<HargaPasarAdapter.ViewHolder>() {
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
            tvJudul.text = listMovies[position].nama
            tvDurasi.text = "Rp " + listMovies[position].harga.toString()
            tvSinopsis.text = listMovies[position].deskripsi
//            tvTahun.text = listMovies[position].tahun.toString()
//            tvRating.text = listMovies[position].rating.toString() + " IMDb"


            ivEdit.setOnClickListener {
                update.invoke(listMovies[position])

            }

            ivDelete.setOnClickListener{
                delete.invoke(listMovies[position])
            }
        }
    }
}