package com.example.vipulgoyaltask.presentation.viewmodel

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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val calculatePortfolioValuesUseCase: CalculatePortfolioValuesUseCase,
    private val getPortfolioUseCase: GetPortfolioUseCase
) : ViewModel() {

    private val _portfolioList = MutableStateFlow<Resource<List<PortfolioData>>>(Resource.Loading())
    val portfolioList get() = _portfolioList.asStateFlow()

    private val _portfolioCalculatedData = MutableStateFlow(PortfolioCalculatedData(0.0, 0.0, 0.0, 0.0))
    val portfolioCalculatedData = _portfolioCalculatedData.asStateFlow()


    fun fetchPortfolioList() = viewModelScope.launch(Dispatchers.IO) {
        getPortfolioUseCase().onStart {
            _portfolioList.emit(Resource.Loading())
        }.catch {
            _portfolioList.emit(Resource.Error(message =it.message.orEmpty()))
        }.collect {
            if(it.isEmpty()){
                _portfolioList.emit(Resource.Error(message = "No Data Found"))

            }else{
                _portfolioCalculatedData.value=calculatePortfolioValuesUseCase.calculatePortfolioValues(it)
                _portfolioList.emit(Resource.Success(it))

            }
         }
    }
}
