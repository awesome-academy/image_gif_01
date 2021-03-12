package com.sun.imagegif.ui.detail

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.sun.imagegif.R
import com.sun.imagegif.data.model.Gif
import com.sun.imagegif.data.source.local.GifLocalDataSource
import com.sun.imagegif.data.source.remote.GifRemoteDataSource
import com.sun.imagegif.data.source.repositories.GifRepository
import com.sun.imagegif.ui.detail.adapter.RelatedAdapter
import com.sun.imagegif.utils.Constant
import com.sun.imagegif.utils.addFragment
import com.sun.imagegif.utils.loadGifUrl
import com.sun.imagegif.utils.loadImageUrl
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(), DetailContract.View {

    private val relatedAdapter by lazy { RelatedAdapter() }

    private val presenter by lazy {
        DetailPresenter(
            GifRepository.getInstance(
                GifLocalDataSource.getInstance(requireContext()),
                GifRemoteDataSource.getInstance()
            )
        )
    }

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
        handleEvent()
    }

    override fun onStart() {
        super.onStart()
        presenter.setView(this)
    }

    override fun onSaveGifSuccess(gif: Gif) {
        Toast.makeText(context, gif.title, Toast.LENGTH_SHORT).show()
    }

    override fun onSaveGifError(e: Int) {
        Toast.makeText(context, getString(e), Toast.LENGTH_SHORT).show()
    }

    override fun onGetRelatedSuccess(gifs: MutableList<Gif>) {
        relatedAdapter.addRelated(gifs)
    }

    override fun onGetRelatedError(e: Exception?) = Unit

    private fun initViews() {
        initDetailView()
        initRelatedView()
    }

    private fun initDetailView() {
        arguments?.getParcelable<Gif>(Constant.BUNDLE_GIF)?.let { gif ->
            detailsImageView.loadGifUrl(gif.imageUrl)
            userImageView.loadImageUrl(gif.user.avatarUrl)
            titleDetailsTextView.text = gif.title
            userNameTextView.text = gif.user.name
            presenter.getRelated(gif.title)
            downloadImageButton.setOnClickListener {
                showAlertDialog {
                    presenter.saveGif(gif)
                }
            }
        }
    }

    private fun initRelatedView() {
        relatedRecyclerView.adapter = relatedAdapter
    }

    private fun handleEvent() {
        previousImageButton.setOnClickListener {
            fragmentManager?.popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        relatedAdapter.setOnClickItemListener {
            addFragment(DetailFragment.newInstance(Bundle().apply {
                putParcelable(Constant.BUNDLE_GIF, it)
            }), R.id.containerLayout)
        }
    }

    private fun showAlertDialog(onAccept: (() -> Unit)) {
        DialogInterface.OnClickListener { _, type ->
            when (type) {
                DialogInterface.BUTTON_POSITIVE -> {
                    onAccept()
                }
            }
        }.also {
            AlertDialog.Builder(requireContext()).apply {
                setMessage(getString(R.string.dialog_download))
                    .setPositiveButton(getString(R.string.answer_yes), it)
                    .setNegativeButton(getString(R.string.answer_no), it)
            }.show()
        }
    }

    companion object {
        fun newInstance(bundle: Bundle) = DetailFragment().apply {
            arguments = bundle
        }
    }
}
