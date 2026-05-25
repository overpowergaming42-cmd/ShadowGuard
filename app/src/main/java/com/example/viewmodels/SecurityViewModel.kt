package com.example.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.IntruderLog
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SecurityViewModel(private val db: AppDatabase) : ViewModel() {
    
    val intruderLogs: StateFlow<List<IntruderLog>> = db.intruderLogDao().getAllLogs()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun logFailedAttempt() {
        viewModelScope.launch {
            db.intruderLogDao().insertLog(IntruderLog(timestamp = System.currentTimeMillis(), type = "Failed PIN Unlock"))
        }
    }
}

class SecurityViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SecurityViewModel::class.java)) {
            val db = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "shadowguard_db"
            ).build()
            @Suppress("UNCHECKED_CAST")
            return SecurityViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
