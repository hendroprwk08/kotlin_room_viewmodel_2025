package com.tes.materiroom

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {
    // Menggunakan CoroutineScope untuk siklus hidup aplikasi
    val applicationScope = CoroutineScope(SupervisorJob())

    // Lazy inisialisasi database dan repository
    val database by lazy { BukuDatabase.getDatabase(this, applicationScope) } // <<< Perubahan di sini
    val repository by lazy { BukuRepository(database.bukuDao()) }

    override fun onCreate() {
        super.onCreate()
        // Anda bisa menambahkan kode inisialisasi lain di sini jika diperlukan
        // Contoh data awal bisa dipindahkan ke BukuDatabase.Callback onCreate
    }
}