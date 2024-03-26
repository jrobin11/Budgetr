package com.example.budgetr.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Inline function that creates a ViewModel factory. It takes a lambda function 'f' to provide a specific ViewModel instance.
 *
 * @param VM The type of ViewModel to be created.
 * @param f Lambda function that provides the ViewModel instance.
 * @return An instance of the ViewModel factory.
 */
inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
  object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(aClass: Class<T>):T = f() as T
  }