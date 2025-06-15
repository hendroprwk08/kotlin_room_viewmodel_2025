package com.tes.materiroom

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tes.materiroom.BukuViewModel.BukuViewModelFactory
import com.tes.materiroom.databinding.ActivityMainBinding
import com.tes.materiroom.databinding.InputLayoutBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var inputLayoutBinding: InputLayoutBinding //bind dialog layout
    private lateinit var bukuAdapter: BukuAdapter

    // Dapatkan instance aplikasi kustom Anda
    private val applicationContext by lazy { application as MyApplication }

    // delegate viewModels
    private val bukuViewModel: BukuViewModel by viewModels {
        BukuViewModelFactory(applicationContext.repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        bukuAdapter = BukuAdapter() // Inisialisasi adapter

        binding.contentMain.recyclerView.adapter = bukuAdapter
        binding.contentMain.recyclerView.layoutManager = LinearLayoutManager(this)

        // Mengamati perubahan data dari ViewModel (harus di onCreate)
        bukuViewModel.allBuku.observe(this, Observer { books ->
            books?.let { bukuAdapter.submitList(it) }
        })

        binding.floatingActionButton.setOnClickListener() {
            inputLayoutBinding = InputLayoutBinding.inflate(layoutInflater) //binding

            val builder = AlertDialog.Builder(this)
                .setPositiveButton(R.string.simpan) { dialog, which ->
                    val etjudul = inputLayoutBinding.etJudul.text.toString()
                    val etpenulis = inputLayoutBinding.etPenulis.text.toString()

                    if (etjudul.isNotBlank() && etpenulis.isNotBlank()) {
                        bukuViewModel.insert(Buku(judul = etjudul, penulis = etpenulis))
                    }
                }
                .setNegativeButton(R.string.tidak) { dialog, which -> // Ganti Neutral jadi Negative
                    dialog.cancel()
                }.create()

            builder.setView(inputLayoutBinding.root) //masukkan layout
            builder.setCanceledOnTouchOutside(false)
            builder.show()
        }
    }
}