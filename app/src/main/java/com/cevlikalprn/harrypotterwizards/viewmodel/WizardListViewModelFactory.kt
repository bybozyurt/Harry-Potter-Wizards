package com.cevlikalprn.harrypotterwizards.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cevlikalprn.harrypotterwizards.repository.WizardRepository

class WizardListViewModelFactory(private val repository: WizardRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WizardListViewModel::class.java)) {
            return WizardListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}