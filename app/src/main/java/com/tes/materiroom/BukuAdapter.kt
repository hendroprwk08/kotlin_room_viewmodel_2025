package com.tes.materiroom

import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Secure.putInt
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tes.materiroom.databinding.RvItemsBinding

// gunakan ListAdapter
class BukuAdapter : ListAdapter<Buku, BukuAdapter.MyViewHolder>(BukuDiffCallback()) {

    class MyViewHolder(val binding: RvItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        // Metode 'bind' lebih baik untuk mengikat data ke tampilan
        fun bind(buku: Buku) {
            binding.tvJudul.text = buku.judul
            binding.tvPenulis.text = buku.penulis

            binding.btDetil.setOnClickListener {
                val bundle = Bundle().apply{
                    putInt("b_id", buku.id)
                }

                val intent = Intent(itemView.context, DetailActivity::class.java).apply {
                    putExtras(bundle)
                    setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                itemView.context.startActivity(intent)
            }
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RvItemsBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentBuku = getItem(position)
        holder.bind(currentBuku)
    }

    // Class untuk membandingkan item secara efisien
    private class BukuDiffCallback : DiffUtil.ItemCallback<Buku>() {
        override fun areItemsTheSame(oldItem: Buku, newItem: Buku): Boolean {
            // Membandingkan apakah item yang sama (biasanya berdasarkan ID unik)
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Buku, newItem: Buku): Boolean {
            // Membandingkan apakah konten item sama (jika ada perubahan pada data, bukan hanya ID)
            return oldItem == newItem // Mengandalkan data class equals untuk perbandingan semua properti
        }
    }
}