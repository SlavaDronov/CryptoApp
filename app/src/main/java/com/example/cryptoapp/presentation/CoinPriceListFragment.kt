package com.example.cryptoapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.cryptoapp.databinding.FragmentCoinPriceListBinding

class CoinPriceListFragment : Fragment() {

    private var _binding: FragmentCoinPriceListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CoinViewModel by activityViewModels()

    private val adapter = CoinInfoAdapter { coin ->
        (requireActivity() as MainActivity).showDetail(coin.fromSymbol)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinPriceListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCoinPriceList.adapter = adapter
        viewModel.priceList.observe(viewLifecycleOwner) { adapter.submitList(it) }
        android.util.Log.d("CoinPriceListFragment", "onViewCreated, adapter attached")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}