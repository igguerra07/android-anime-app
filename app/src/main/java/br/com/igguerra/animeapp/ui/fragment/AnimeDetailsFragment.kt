package br.com.igguerra.animeapp.ui.fragment

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.igguerra.animeapp.R
import br.com.igguerra.animeapp.model.AnimeItem
import br.com.igguerra.animeapp.model.AnimeResponse
import br.com.igguerra.animeapp.network.Status
import br.com.igguerra.animeapp.ui.viewmodel.AnimeViewModel
import br.com.igguerra.animeapp.ui.viewmodel.ViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_anime_details.*
import kotlinx.android.synthetic.main.item_anime.*

class AnimeDetailsFragment : DialogFragment() {

    private var animeId: Int = -1
    private lateinit var animeItem: AnimeItem
    private var isFav: AnimeItem? = null
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory()
        ).get(AnimeViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.animeFavs.observe(viewLifecycleOwner, Observer {
            isFav = it.find { animeItem -> animeItem.mal_id == animeId }
            if (isFav != null) {
                animeDetailsTextButton.text = ("Remover Favoritos")
            } else {
                animeDetailsTextButton.text = ("Adicionar Favoritos")
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.AppTheme)

        arguments?.let {
            animeId = it.getInt(ANIME_ID)
            animeItem = it.getParcelable(ANIME_ITEM)!!
        }
        viewModel.anime.observe(activity!!, Observer {
            when (it.status) {
                Status.LOADING -> setLoadingState(true)
                Status.SUCCESS -> {
                    setLoadingState(false)
                    setDetails(it.data!!)
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_anime_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        //
        viewModel.getAnimeById(animeId)

        //
        animeDetailsBackButton.setOnClickListener {
            this.dismiss()
        }

        animeDetailsCardButton.setOnClickListener {
            viewModel.deleteAnime(animeItem)
            if (isFav != null) {
                Toast.makeText(
                    requireContext(),
                    "O anime ${animeItem.title} foi removido aos favoritos",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.addAnime(animeItem)
                Toast.makeText(
                    requireContext(),
                    "O anime ${animeItem.title} foi adicionado dos favoritos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    /**
     *
     * @param anime
     */
    private fun setDetails(anime: AnimeResponse) {
        anime.let {
            animeDetailsScoreValue.text = it.score.toString()
            animeDetailsFans.text = ("${it.favorites} Fans")
            animeDetailsEpisodes.text = ("${it.episodes} episodes")
            animeDetailsDesc.text = it.synopsis
            animeDetailsTitle.text = it.title
            animeDetailsDesc.movementMethod = ScrollingMovementMethod()
            Picasso.get().load(it.image_url).placeholder(R.drawable.ic_launcher_background)
                .into(animeDetailsPoster)

            //TODO Change for YoutubeAPI
            animeTrailerView.settings.javaScriptEnabled = true
            animeTrailerView.settings.pluginState = WebSettings.PluginState.ON
            animeTrailerView.loadUrl(it.trailer_url)
            animeTrailerView.webChromeClient = WebChromeClient()
        }
    }

    /**
     *
     * @param isLoading
     */
    private fun setLoadingState(isLoading: Boolean) {
        val isVisible = if (isLoading) View.VISIBLE else View.INVISIBLE
        shimmerLoadingCotent.visibility = isVisible
        shimmerTitle.visibility = isVisible
        shimmerInfo.visibility = isVisible

        shimmerLoadingCotent.showShimmer(isLoading)
        shimmerInfo.showShimmer(isLoading)
        shimmerTitle.showShimmer(isLoading)
    }

    override fun onResume() {
        super.onResume()
        shimmerLoadingCotent.startShimmer()
        shimmerInfo.startShimmer()
        shimmerTitle.startShimmer()
    }

    override fun onPause() {
        super.onPause()
        shimmerLoadingCotent.stopShimmer()
        shimmerInfo.stopShimmer()
        shimmerTitle.stopShimmer()
    }

    companion object {
        private const val ANIME_ID = "ANIME_ID"
        private const val ANIME_ITEM = "ANIME_ITEM"

        @JvmStatic
        fun newInstance(id: Int, animeItem: AnimeItem) =
            AnimeDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ANIME_ID, id)
                    putParcelable(ANIME_ITEM, animeItem)
                }
            }
    }
}