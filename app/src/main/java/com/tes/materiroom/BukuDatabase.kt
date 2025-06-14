package com.tes.materiroom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Buku::class], version = 1)
abstract class BukuDatabase : RoomDatabase() {
    abstract fun bukuDao(): BukuDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: BukuDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): BukuDatabase {

            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BukuDatabase::class.java,
                    "note_database"
                )
                    .addCallback(BukuDatabaseCallback(scope)) // <<< Tambahkan ini
                    .build()
                INSTANCE = instance

                instance  // return instance
            }
        }

        // Callback untuk mengisi data awal saat database pertama kali dibuat
        private class BukuDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) { // <<< PENTING: Lakukan ini di background thread
                        populateDatabase(database.bukuDao())
                    }
                }
            }

            // hanya berjalan saat satu kali database terbentuk
            suspend fun populateDatabase(bukuDao: BukuDao) {
                // Tambahkan data awal di sini untuk sample data
                bukuDao.insert(Buku(judul = "Bumi Manusia", penulis = "Pramoedya Ananta Toer"))
                bukuDao.insert(Buku(judul = "Laskar Pelangi", penulis = "Andrea Hirata"))
                bukuDao.insert(Buku(judul = "Negeri 5 Menara", penulis = "Ahmad Fuadi"))
            }
        }
    }
}