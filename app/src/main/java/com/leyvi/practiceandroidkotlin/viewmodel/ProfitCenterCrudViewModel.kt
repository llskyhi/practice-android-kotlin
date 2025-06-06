package com.leyvi.practiceandroidkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leyvi.practiceandroidkotlin.model.entity.ProfitCenter
import com.leyvi.practiceandroidkotlin.repo.ProfitCenterRepo
import kotlinx.coroutines.launch

class ProfitCenterCrudViewModel(
    private val profitCenterRepo: ProfitCenterRepo,
) : ViewModel() {
    fun createProfitCenter(profitCenter: ProfitCenter) {
        viewModelScope.launch {
            profitCenterRepo.create(profitCenter)
        }
    }

    fun updateProfitCenter(profitCenter: ProfitCenter) {
        viewModelScope.launch {
            profitCenterRepo.update(profitCenter)
        }
    }

    val profitCenters: LiveData<List<ProfitCenter>> = profitCenterRepo.getAll()

    fun deleteProfitCenter(profitCenter: ProfitCenter) {
        viewModelScope.launch {
            profitCenterRepo.delete(profitCenter)
        }
    }
}
