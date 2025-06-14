package com.tes.materiroom

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "buku")
data class Buku(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val judul: String,
    val penulis: String
)