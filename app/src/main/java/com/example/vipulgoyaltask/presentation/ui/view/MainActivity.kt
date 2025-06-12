package com.example.vipulgoyaltask.presentation.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vipulgoyaltask.R
import com.example.vipulgoyaltask.core.NetworkUtil
import com.example.vipulgoyaltask.databinding.ActivityMainBinding
import com.example.vipulgoyaltask.presentation.ui.adapter.PortfolioListAdapter
import com.example.vipulgoyaltask.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: PortfolioListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        viewModel.fetchPortfolioList()
        registerCollectors()
    }

    private fun registerCollectors(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showLoader.collect {value->
                    mBinding.progressBar.isVisible = value
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getPortfolioList.collect {value->
                    if(value.isNotEmpty()) {
                        mBinding.tvNoRecordFound.isVisible= false
                        mBinding.rvPortfolioList.isVisible = true
                        mBinding.rvPortfolioList.layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = PortfolioListAdapter(value)
                        mBinding.rvPortfolioList.adapter = adapter
                        setPortFolioSummaryData()
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errorData.collect {value->
                    if(value.isNotEmpty()){
                        mBinding.tvNoRecordFound.isVisible= true
                        if (!NetworkUtil(this@MainActivity).getConnectivityStatus()) {
                            Toast.makeText(
                                this@MainActivity,
                                getString(R.string.please_check_your_internet_connection),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                }}
            }
        }
    }

    private fun setPortFolioSummaryData(){
        mBinding.run {
        viewModel.portfolioCalculatedData.let {
            val rupee = getString(R.string.rupee_sign)
            tvCurrentValue.text = rupee.plus("${it.currentValue}")
            tvInvestmentValue.text = rupee.plus("${it.totalInvestment}")

            tvTodayPLValue.text = buildString {
                append(rupee)
                append("%.2f".format(it.todayPNL))
            }

            tvTotalPLValue.text = buildString {
                append(rupee)
                append("%.2f".format(it.totalPNL))
            }
        }
        portfolioSummaryBottom.visibility=View.VISIBLE
        clBottomView.setOnClickListener {
            if(clShowHideView.isVisible) {
                clShowHideView.visibility = View.GONE
                ivArrow.rotation=-180f
            }
            else {
                clShowHideView.visibility = View.VISIBLE
                ivArrow.rotation=0f
            }
        }
    }}
}