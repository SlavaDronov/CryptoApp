package com.example.cryptoapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cryptoapp.databinding.FragmentCoinDetailBinding
import com.squareup.picasso.Picasso

class CoinDetailFragment : Fragment() {

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding get() = _binding?: throw RuntimeException("FragmentCoinDetailBinding is null")
    private val viewModel: CoinViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Кнопка «Закрыть» — сообщает MainActivity, что детали нужно скрыть
        binding.btnClose.setOnClickListener {
            (requireActivity() as MainActivity).closeDetail()
        }

        val fSym = arguments?.getString(ARG_F_SYM) ?: return
        viewModel.getDetailInfo(fSym).observe(viewLifecycleOwner) { coin ->
            with(binding) {
                tvPrice.text = coin.price
                tvMinPrice.text = coin.lowDay
                tvMaxPrice.text = coin.highDay
                tvLastMarket.text = coin.lastMarket
                tvLastUpdate.text = coin.formattedTime
                tvFromSymbol.text = coin.fromSymbol
                tvToSymbol.text = coin.toSymbol
                Picasso.get().load(coin.fullImageUrl).into(ivLogoCoin)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_F_SYM = "fSym"
        fun newInstance(fSym: String) = CoinDetailFragment().apply {
            arguments = Bundle().apply { putString(ARG_F_SYM, fSym) }
        }
    }
}