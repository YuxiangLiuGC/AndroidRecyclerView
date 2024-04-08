package com.example.productapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductViewModel : ViewModel() {
    private val _clickCounts = MutableLiveData<MutableMap<String, Int>>()
    val clickCounts: LiveData<MutableMap<String, Int>> = _clickCounts
    private val _viewedProducts = MutableLiveData<MutableSet<String>>()
    val viewedProducts: LiveData<MutableSet<String>> = _viewedProducts


    init {
        _clickCounts.value = mutableMapOf()
    }

    fun recordClick(productName: String) {
        val currentClicks = _clickCounts.value ?: mutableMapOf()
        val clicks = currentClicks.getOrDefault(productName, 0)
        currentClicks[productName] = clicks + 1
        _clickCounts.value = currentClicks
        Log.d("zander", (clickCounts.value?.get(productName) ?: 0).toString())
    }

    fun recordViewed(productId: String) {
        val currentViewedProducts = _viewedProducts.value ?: mutableSetOf()
        currentViewedProducts.add(productId)
        _viewedProducts.value = currentViewedProducts
    }
}
