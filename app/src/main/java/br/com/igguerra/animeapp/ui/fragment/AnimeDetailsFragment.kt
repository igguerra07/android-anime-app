package br.com.igguerra.animeapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import androidx.lifecycle.Observer
import br.com.igguerra.animeapp.R
import br.com.igguerra.animeapp.application.BaseDialogFragment
import br.com.igguerra.animeapp.model.Anime
import br.com.igguerra.animeapp.model.AnimeResponse
import br.com.igguerra.animeapp.network.Status
import br.com.igguerra.animeapp.ui.viewmodel.AnimeViewModel
import br.com.igguerra.animeapp.utils.AppExtensions.toastShow
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_anime_details.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnimeDetailsFragment : BaseDialogFragment() {

    private val animeViewModel: AnimeViewModel by sharedViewModel()

    private lateinit var anime: Anime

    private var isFav: Anime? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Request anime details
        animeViewModel.getAnimeById(anime.mal_id)

        animeViewModel.animeFavs.observe(viewLifecycleOwner, Observer {
            isFav = it.find { anime -> anime.mal_id == this.anime.mal_id }
            if (isFav != null) {
                animeDetailsAddButton.text = getString(R.string.remove_favs)
            } else {
                animeDetailsAddButton.text = getString(R.string.add_favs)
            }
        })

        animeViewModel.anime.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    setLoadingState(false)
                    setDetails(it.data!!)
                }
                Status.LOADING -> {
                    setLoadingState(true)
                }
                Status.ERROR   ->  {
                    context?.toastShow(it.message?:getString(R.string.error_operation_invalid))
                    dismiss()
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get params
        arguments?.let {
            anime = it.getParcelable(ANIME_ITEM)!!
        }
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

        animeDetailsBackButton.setOnClickListener {
            this.dismiss()
        }

        animeDetailsAddButton.setOnClickListener {
            animeViewModel.deleteAnime(anime)
            if (isFav != null) {
                context?.toastShow(getString(R.string.msg_remove_favs, anime.title))
            } else {
                animeViewModel.addAnime(anime)
                context?.toastShow(getString(R.string.msg_add_favs, anime.title))
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setDetails(anime: AnimeResponse) {
        anime.let {
            animeDetailsScore.text = it.score.toString()
            animeDetailsFans.text = getString(R.string.fans_count, it.favorites)
            animeDetailsEpisodes.text = getString(R.string.episodes_count, it.episodes)
            animeDetailsDescription.text = it.synopsis
            animeDetailsTitle.text = it.title
            animeDetailsDescription.movementMethod = ScrollingMovementMethod()
            Picasso.get().load(it.image_url).placeholder(R.drawable.ic_launcher_background)
                .into(animeDetailsPoster)

            //TODO Change for YoutubeAPI
            animeDetailsTrailer.settings.javaScriptEnabled = true
            animeDetailsTrailer.settings.pluginState = WebSettings.PluginState.ON
            animeDetailsTrailer.loadUrl(it.trailer_url)
            animeDetailsTrailer.webChromeClient = WebChromeClient()
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        val isVisible = if (isLoading) View.VISIBLE else View.INVISIBLE
        animeDetailsShimmerDesc.visibility = isVisible
        animeDetailsShimmerTitle.visibility = isVisible
        animeDetailsShimmerInfo.visibility = isVisible
        // Animation
        animeDetailsShimmerDesc.showShimmer(isLoading)
        animeDetailsShimmerInfo.showShimmer(isLoading)
        animeDetailsShimmerTitle.showShimmer(isLoading)
    }

    override fun onResume() {
        super.onResume()
        animeDetailsShimmerDesc.startShimmer()
        animeDetailsShimmerInfo.startShimmer()
        animeDetailsShimmerTitle.startShimmer()
    }

    override fun onPause() {
        super.onPause()
        animeDetailsShimmerDesc.stopShimmer()
        animeDetailsShimmerInfo.stopShimmer()
        animeDetailsShimmerTitle.stopShimmer()
    }

    companion object {
        private const val ANIME_ITEM = "ANIME_ITEM"
        @JvmStatic
        fun newInstance(anime: Anime) =
        AnimeDetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ANIME_ITEM, anime)
            }
        }
    }
}