package com.tes.materiroom

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.tes.materiroom.databinding.ActivityDetailBinding
import kotlinx.coroutines.GlobalScope

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    var id: Int = 0

    // Dapatkan instance aplikasi kustom Anda
    private val applicationContext by lazy { application as MyApplication }

    // Inisialisasi BukuViewModel (sama dengan MainActivity)
    private val bukuViewModel: BukuViewModel by viewModels {
        BukuViewModel.BukuViewModelFactory(applicationContext.repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //back button
        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // ambil Id dari Bundle
        val bundle = intent.extras
        id = bundle?.getInt("b_id") ?: -1

        if (id == -1) {
            // jika ID tidak ditemukan
            finish()
            return
        }

        // munculkan data berdasarkan id
        bukuViewModel.getBukuById(id).observe(this, Observer { buku ->
            buku?.let {
                // Tampilkan data ke UI
                binding.etJudul.setText(it.judul)
                binding.etPenulis.setText(it.penulis)
            } ?: run {
                // Opsional: Jika buku tidak ditemukan tutup activity
                finish()
            }
        })

        binding.btHapus.setOnClickListener(){
            AlertDialog.Builder(this)
                .setTitle("Hapus Buku")
                .setMessage("Apakah Anda yakin ingin menghapus buku ini?")
                .setPositiveButton("Ya") { dialog, which ->
                    // Anda bisa menggunakan delete(buku) jika Anda punya objek buku lengkap
                    // atau deleteBukuById(id)
                    bukuViewModel.deleteBukuById(id)
                    finish() // Tutup DetailActivity setelah delete
                }
                .setNegativeButton("Tidak", null)
                .show()
        }

        binding.btSimpan.setOnClickListener(){
            val updatedJudul = binding.etJudul.text.toString()
            val updatedPenulis = binding.etPenulis.text.toString()

            if (updatedJudul.isNotBlank() && updatedPenulis.isNotBlank()) {
                val updatedBuku = Buku(id = id, judul = updatedJudul, penulis = updatedPenulis)
                bukuViewModel.update(updatedBuku)
                finish() // Tutup DetailActivity setelah update
            }
        }
    }
}