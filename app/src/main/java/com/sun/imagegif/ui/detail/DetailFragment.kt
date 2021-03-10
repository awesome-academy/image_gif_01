package com.sun.imagegif.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.imagegif.R
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        handleEvent()
    }

    private fun handleEvent() {
        previousImageButton.setOnClickListener {
            fragmentManager?.popBackStack()
        }
    }

    companion object {
        fun newInstance() = DetailFragment()
    }
}
