package com.example.cryptoapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.databinding.ItemCoinInfoBinding
import com.example.cryptoapp.domain.CoinPriceInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter(
    private val onCoinClickListener: (CoinPriceInfo) -> Unit
) : ListAdapter<CoinPriceInfo, CoinInfoAdapter.CoinInfoViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemCoinInfoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CoinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CoinInfoViewHolder(
        private val binding: ItemCoinInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(coin: CoinPriceInfo) {
            with(binding) {
                tvSymbols.text = "${coin.fromSymbol}/${coin.toSymbol}"
                tvPrice.text = coin.price
                tvLastUpdate.text = coin.formattedTime
                Picasso.get().load(coin.fullImageUrl).into(ivLogoCoin)
                root.setOnClickListener { onCoinClickListener(coin) }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CoinPriceInfo>() {
        override fun areItemsTheSame(oldItem: CoinPriceInfo, newItem: CoinPriceInfo) =
            oldItem.fromSymbol == newItem.fromSymbol

        override fun areContentsTheSame(oldItem: CoinPriceInfo, newItem: CoinPriceInfo) =
            oldItem == newItem
    }
}