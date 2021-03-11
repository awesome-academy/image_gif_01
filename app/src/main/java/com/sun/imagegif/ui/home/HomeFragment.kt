package com.sun.imagegif.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.imagegif.R
import com.sun.imagegif.data.model.Gif
import com.sun.imagegif.data.source.local.GifLocalDataSource
import com.sun.imagegif.data.source.remote.GifRemoteDataSource
import com.sun.imagegif.data.source.repositories.GifRepository
import com.sun.imagegif.ui.detail.DetailFragment
import com.sun.imagegif.ui.home.adapter.RandomAdapter
import com.sun.imagegif.ui.home.adapter.TrendingAdapter
import com.sun.imagegif.utils.Constant
import com.sun.imagegif.utils.addFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), HomeContract.View {

    private val trendingAdapter by lazy { TrendingAdapter() }

    private val randomAdapter by lazy { RandomAdapter() }

    private val presenter by lazy {
        HomePresenter(
            GifRepository.getInstance(
                GifLocalDataSource.getInstance(requireContext()),
                GifRemoteDataSource.getInstance(),
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        handleEvent()
    }

    override fun onStart() {
        super.onStart()
        presenter.run {
            setView(this@HomeFragment)
            getTrending()
            getRandom()
        }
    }

    override fun onGetTrendingSuccess(gifs: MutableList<Gif>) {
        trendingAdapter.addTrending(gifs)
    }

    override fun onGetRandomSuccess(gifs: MutableList<Gif>) {
        randomAdapter.addRandom(gifs)
    }

    override fun onError(exception: Exception?) = Unit

    private fun initViews() {
        initRecyclerView()
    }

    private fun handleEvent() {
        trendingAdapter.setOnClickItemListener {
            addFragment(DetailFragment.newInstance(Bundle().apply {
                putParcelable(Constant.BUNDLE_GIF, it)
            }), R.id.containerLayout)
        }

        randomAdapter.setOnClickItemListener {
            addFragment(DetailFragment.newInstance(Bundle().apply {
                putParcelable(Constant.BUNDLE_GIF, it)
            }), R.id.containerLayout)
        }
    }

    private fun initRecyclerView() {
        trendingRecyclerView.adapter = trendingAdapter
        randomRecyclerView.adapter = randomAdapter
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
