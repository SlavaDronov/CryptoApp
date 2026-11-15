package com.example.cryptoapp.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val isLandscape: Boolean
        get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.list_container, CoinPriceListFragment())
                .commit()
        }

        // В портрете detail_container скрыт, в ландшафте — виден
        if (!isLandscape) {
            binding.detailContainer.visibility = View.GONE
        }
    }

    fun showDetail(fSym: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.detail_container, CoinDetailFragment.newInstance(fSym))
            .addToBackStack(null)
            .commit()

        if (!isLandscape) {
            binding.listContainer.visibility = View.GONE
            binding.detailContainer.visibility = View.VISIBLE
        }
    }

    fun closeDetail() {
        if (!isLandscape) {
            binding.listContainer.visibility = View.VISIBLE
            binding.detailContainer.visibility = View.GONE
        } else {
            // В ландшафте просто очищаем правый контейнер
            supportFragmentManager.beginTransaction()
                .remove(supportFragmentManager.findFragmentById(R.id.detail_container) ?: return)
                .commit()
        }
        supportFragmentManager.popBackStack()
    }

    override fun onBackPressed() {
        if (!isLandscape && binding.detailContainer.visibility == View.VISIBLE) {
            closeDetail()
        } else {
            super.onBackPressed()
        }
    }
}