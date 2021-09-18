package com.cevlikalprn.harrypotterwizards.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cevlikalprn.harrypotterwizards.models.Wizard
import com.cevlikalprn.harrypotterwizards.models.WizardItem
import com.cevlikalprn.harrypotterwizards.network.HarryPotterApi
import com.cevlikalprn.harrypotterwizards.repository.WizardRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class WizardListViewModel(private val repository: WizardRepository) : ViewModel() {

    private val _wizards: MutableLiveData<Wizard> = MutableLiveData()
    val wizards: LiveData<Wizard>
        get() = _wizards

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    init {
        loadWizards()
    }

    private fun loadWizards() {
        viewModelScope.launch {
            getWizards()
        }
    }

    private suspend fun getWizards() {
        try {
            val response = repository.getWizards()
            if (response.isSuccessful) {
                _wizards.value = response.body()
            } else {
                _errorMessage.value = response.message()
            }
        } catch (e: Exception) {

            e.printStackTrace()
        }
    }

}