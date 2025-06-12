package com.example.vipulgoyaltask.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vipulgoyaltask.core.Resource
import com.example.vipulgoyaltask.domain.model.PortfolioCalculatedData
import com.example.vipulgoyaltask.domain.model.PortfolioData
import com.example.vipulgoyaltask.domain.usecase.CalculatePortfolioValuesUseCase
import com.example.vipulgoyaltask.domain.usecase.GetPortfolioUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val calculatePortfolioValuesUseCase: CalculatePortfolioValuesUseCase,
    private val getPortfolioUseCase: GetPortfolioUseCase
) : ViewModel() {

    private val _summary = MutableLiveData<PortfolioCalculatedData>()
    val summary: LiveData<PortfolioCalculatedData> = _summary
    lateinit var portfolioCalculatedData: PortfolioCalculatedData


    private val _getPortfolioList = MutableStateFlow(listOf<PortfolioData>())
    val getPortfolioList = _getPortfolioList.asStateFlow()
    private val _showLoader = MutableStateFlow(false)
    val showLoader = _showLoader.asStateFlow()
    private val _errorData = MutableStateFlow("")
    val errorData = _errorData.asStateFlow()


    fun fetchPortfolioList() {
        viewModelScope.launch(Dispatchers.IO) {
            _showLoader.value = true
            getPortfolioUseCase().collect{
                when (it) {
                    is Resource.Loading -> {
                        _showLoader.value = true
                    }

                    is Resource.Success -> {
                        _showLoader.value = false
                        if(it.data.isNotEmpty()){
                            portfolioCalculatedData=calculatePortfolioValuesUseCase.calculatePortfolioValues(it.data)
                            _getPortfolioList.value = it.data}

                    }

                    is Resource.Error -> {
                        _showLoader.value = false
                        _errorData.value=it.message?:""
                    }
                }
            }
        }
    }
}
