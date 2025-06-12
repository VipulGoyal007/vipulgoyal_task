package com.example.vipulgoyaltask.presentation.ui.adapter

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.vipulgoyaltask.R
import com.example.vipulgoyaltask.databinding.RowPortfolioItemBinding
import com.example.vipulgoyaltask.domain.model.PortfolioData


class PortfolioListAdapter(
    private val list:  List<PortfolioData>,
    ) : RecyclerView.Adapter<PortfolioListAdapter.PortfolioItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioItemViewHolder {
        return PortfolioItemViewHolder(
            RowPortfolioItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PortfolioItemViewHolder, position: Int) {
        val row = list[position]

        with(holder.viewBinding) {
                tvSymbol.text = row.symbol

                val ltpLabel = root.context.getString(R.string.ltp_text)
                val ltpValue = root.context.getString(R.string.rupee_sign)+row.ltp.toString()
                tvLtpValue.text = setTextWithTwoColor(ltpLabel,ltpValue)

                val quantityLabel = root.context.getString(R.string.net_qty)
                val quantityValue = row.quantity.toString()
                tvQuantityValue.text = setTextWithTwoColor(quantityLabel,quantityValue)

                val pnl=row.getPL()
                tvPLValue.text = buildString {
                    append(root.context.getString(R.string.pl_text))
                    append(" ")
                    append(root.context.getString(R.string.rupee_sign)+"%.2f".format(pnl))
                }
                val colorRes = if (pnl >= 0) R.color.green else R.color.red
                tvPLValue.setTextColor(ContextCompat.getColor(root.context, colorRes))
            }
    }

    inner class PortfolioItemViewHolder(val viewBinding: RowPortfolioItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)
}

private fun setTextWithTwoColor(value1:String,value2:String): SpannableStringBuilder{
    val spannable = SpannableStringBuilder().apply {
        append(value1)
        setSpan(
            ForegroundColorSpan(Color.GRAY),
            0,
            length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        append(" ")
        val start = length
        append(value2)
        setSpan(
             ForegroundColorSpan(Color.DKGRAY),
            start,
            length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return spannable
}