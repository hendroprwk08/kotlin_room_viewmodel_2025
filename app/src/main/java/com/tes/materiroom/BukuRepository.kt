package com.tes.materiroom

import androidx.lifecycle.LiveData

class BukuRepository(private val bukuDao: BukuDao) {

    val allBuku: LiveData<List<Buku>> = bukuDao.getAll()

    suspend fun insert(buku: Buku) {
        bukuDao.insert(buku)
    }

    suspend fun update(buku: Buku) {
        bukuDao.update(buku)
    }

    suspend fun delete(buku: Buku) {
        bukuDao.delete(buku)
    }

    suspend fun deleteBukuById(id: Int) {
        bukuDao.deleteById(id)
    }

    fun getBukuById(id: Int): LiveData<Buku> {
        return bukuDao.getById(id)
    }
}