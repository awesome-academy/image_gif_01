package com.sun.imagegif.ui.storage

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.sun.imagegif.R
import com.sun.imagegif.data.model.Gif
import com.sun.imagegif.data.source.local.GifLocalDataSource
import com.sun.imagegif.data.source.remote.GifRemoteDataSource
import com.sun.imagegif.data.source.repositories.GifRepository
import com.sun.imagegif.ui.detail.DetailFragment
import com.sun.imagegif.ui.storage.adapter.StorageAdapter
import com.sun.imagegif.utils.Constant
import com.sun.imagegif.utils.addFragment
import kotlinx.android.synthetic.main.fragment_storage.*

class StorageFragment : Fragment(), StorageContract.View {

    private val storageAdapter by lazy { StorageAdapter() }
    private val presenter by lazy {
        StoragePresenter(
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
        return inflater.inflate(R.layout.fragment_storage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        handleEvent()
    }

    override fun onStart() {
        super.onStart()
        presenter.run {
            setView(this@StorageFragment)
            getGifsLocal()
        }
    }

    override fun onGetGifsLocalSuccess(data: MutableList<Gif>) {
        storageAdapter.updateData(data)
    }

    override fun onDeleteGifSuccess(gif: Gif) {
        storageAdapter.deleteGif(gif)
    }

    override fun onError(e: Int) {
        Toast.makeText(context, getString(e), Toast.LENGTH_SHORT).show()
    }

    private fun initViews() {
        initRecyclerViews()
    }

    private fun initRecyclerViews() {
        storageRecyclerVIew.adapter = storageAdapter
    }

    private fun handleEvent() {
        storageAdapter.setOnClickItemListener(object : StorageAdapter.OnClickItemListener {

            override fun onClickItem(gif: Gif) {
                addFragment(DetailFragment.newInstance(Bundle().apply {
                    putParcelable(Constant.BUNDLE_GIF, gif)
                }), R.id.containerLayout)
            }

            override fun onDelete(gif: Gif) {
                showAlertDialog {
                    presenter.deleteGifLocal(gif)
                }
            }
        })
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
                setMessage(getString(R.string.dialog_delete_gif))
                    .setPositiveButton(getString(R.string.answer_yes), it)
                    .setNegativeButton(getString(R.string.answer_no), it)
            }.show()
        }
    }

    companion object {
        fun newInstance() = StorageFragment()
    }
}
