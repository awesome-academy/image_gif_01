package com.sun.imagegif

import com.sun.imagegif.data.model.Gif
import com.sun.imagegif.data.model.User
import com.sun.imagegif.data.source.local.OnResultDataListener
import com.sun.imagegif.data.source.remote.OnFetchDataJsonListener
import com.sun.imagegif.data.source.repositories.GifRepository
import com.sun.imagegif.ui.detail.DetailContract
import com.sun.imagegif.ui.detail.DetailPresenter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer

@RunWith(MockitoJUnitRunner::class)
class DetailFragmentUnitTest {

    @Mock
    private lateinit var view: DetailContract.View

    @Mock
    private lateinit var repository: GifRepository

    private lateinit var presenter: DetailPresenter

    @Before
    fun setUp() {
        presenter = DetailPresenter(repository)
        presenter.setView(view)
    }

    @Test
    fun `get related success`() {
        val gifs = mutableListOf<Gif>()
        `when`(repository.searchWithGif(anyString(), any())).doAnswer {
            (it.arguments[1] as OnFetchDataJsonListener<MutableList<Gif>>).onSuccess(gifs)
        }
        presenter.getRelated("some")
        verify(view).onGetRelatedSuccess(gifs)
    }

    @Test
    fun `get related error`() {
        val exception = Exception()
        `when`(repository.searchWithGif(anyString(), any())).doAnswer {
            (it.arguments[1] as OnFetchDataJsonListener<MutableList<Gif>>).onError(exception)
        }
        presenter.getRelated("some")
        verify(view).onGetRelatedError(exception)
    }

    @Test
    fun `save gif success`() {
        val gif = Gif(
            "csHrilz0aEtiCEFYLR",
            "https://media3.giphy.com/media/csHrilz0aEtiCEFYLR/giphy.gif?cid=f8c8f42b6qc2panm07j51qah84sbkbtx2axus4pyjhieaw4p&rid=giphy.gif",
            "You Can Do It Dog GIF by Originals",
            User()
        )
        `when`(repository.saveGif(any(), any())).doAnswer {
            (it.arguments[1] as OnResultDataListener<Gif>).onSuccess(gif)
        }
        presenter.saveGif(gif)
        verify(view).onSaveGifSuccess(gif)
    }

    @Test
    fun `save gif error`() {
        val gif = Gif(
            "csHrilz0aEtiCEFYLR",
            "https://media3.giphy.com/media/csHrilz0aEtiCEFYLR/giphy.gif?cid=f8c8f42b6qc2panm07j51qah84sbkbtx2axus4pyjhieaw4p&rid=giphy.gif",
            "You Can Do It Dog GIF by Originals",
            User()
        )
        val errorContentId = R.string.download_failed
        `when`(repository.saveGif(any(), any())).doAnswer {
            (it.arguments[1] as OnResultDataListener<Gif>).onError(errorContentId)
        }
        presenter.saveGif(gif)
        verify(view).onSaveGifError(errorContentId)
    }
}
