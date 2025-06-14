package com.tes.materiroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BukuViewModel(private val repository: BukuRepository) : ViewModel()  {
    val allBuku: LiveData<List<Buku>>  = repository.allBuku

    fun insert(buku: Buku) = viewModelScope.launch {
        repository.insert(buku)
    }

    fun update(buku: Buku) = viewModelScope.launch {
        repository.update(buku)
    }

    fun delete(buku: Buku) = viewModelScope.launch {
        repository.delete(buku)
    }

    fun deleteBukuById(id: Int) = viewModelScope.launch {
        repository.deleteBukuById(id)
    }

    fun getBukuById(id: Int): LiveData<Buku> {
        return repository.getBukuById(id)
    }

    // Factory untuk membuat ViewModel dengan parameter repository
    class BukuViewModelFactory(private val repository: BukuRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BukuViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return BukuViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}