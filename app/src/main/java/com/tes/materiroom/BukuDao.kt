package com.tes.materiroom

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BukuDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(buku: Buku)

    @Delete
    suspend fun delete(buku: Buku)

    @Update
    suspend fun update(buku: Buku)

    @Query("DELETE FROM buku WHERE id = :bukuId")
    suspend fun deleteById(bukuId: Int)

    // tanpa suspend karena LiveData
    @Query("SELECT * FROM buku")
    fun getAll(): LiveData<List<Buku>>

    // tanpa suspend karena LiveData
    @Query("SELECT * FROM buku WHERE id = :bukuId")
    fun getById(bukuId: Int):LiveData<Buku>

}