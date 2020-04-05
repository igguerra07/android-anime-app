package br.com.igguerra.animeapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.igguerra.animeapp.R
import br.com.igguerra.animeapp.application.BaseFragment
import br.com.igguerra.animeapp.application.Constants
import br.com.igguerra.animeapp.model.Anime
import br.com.igguerra.animeapp.network.Status
import br.com.igguerra.animeapp.ui.adapter.AnimeAdapter
import br.com.igguerra.animeapp.ui.viewmodel.AnimeViewModel
import br.com.igguerra.animeapp.utils.AppExtensions.changeTitle
import kotlinx.android.synthetic.main.fragment_anime_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class AnimeListFragment : BaseFragment(), CoroutineScope {

    private val animeViewModel: AnimeViewModel by sharedViewModel()

    private lateinit var animeAnimeAdapter: AnimeAdapter

    private val list: ArrayList<Anime> = arrayListOf()
    private val reset: ArrayList<Anime> = arrayListOf()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        // Setup observable
        animeViewModel.topList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    animesLayout.isRefreshing = true
                    animeListCardError.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    animesLayout.isRefreshing = false
                    animeListCardError.visibility = View.GONE
                    reset.clear()
                    reset.addAll(it.data?.top ?: arrayListOf())
                    updateDataSet(it.data?.top, animeList)
                }
                Status.ERROR -> {
                    animesLayout.isRefreshing = false
                    animeListCardError.visibility = View.VISIBLE
                    animeListError.text = it.message
                }
            }
        })

        animeViewModel.searchResult.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    animesLayout.isRefreshing = true
                    animeListCardError.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    animesLayout.isRefreshing = false
                    animeListCardError.visibility = View.GONE
                    updateDataSet(it.data?.results, animeList)
                }
                Status.ERROR -> {
                    animesLayout.isRefreshing = false
                    animeListCardError.visibility = View.VISIBLE
                    animeListError.text = it.message
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        changeTitle(getString(R.string.title_top))
        return inflater.inflate(R.layout.fragment_anime_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Setup RecyclerView
        animeAnimeAdapter = AnimeAdapter(list) {
            AnimeDetailsFragment.newInstance(it)
                .show(parentFragmentManager, Constants.FRAG_FAVS_TAG)
        }
        animeList.layoutManager = LinearLayoutManager(requireContext())
        animeList.adapter = animeAnimeAdapter

        // Retry request animes
        animeListRetryButton.setOnClickListener {
            animeViewModel.getTopAnimes()
        }

        //Styling progress
        animesLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary)

        // Set progress event
        animesLayout.setOnRefreshListener {
            animeViewModel.getTopAnimes()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        val menuItem = menu.findItem(R.id.menu_item_search)
        val searchView = menuItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            private var searchFor = ""

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == searchFor) return true
                searchFor = newText!!
                launch {
                    delay(600)
                    if (newText != searchFor) return@launch
                    if (searchFor.length >= 3) {
                        animeViewModel.searchAnime(newText)
                    } else {
                        if(reset != list) {
                            updateDataSet(reset, animeList)
                        }
                    }
                }
                return true
            }
        })

        searchView.setOnCloseListener {
            searchView.clearFocus()
            return@setOnCloseListener false
        }
    }


    private fun updateDataSet(anime: List<Anime>?, recyclerView: RecyclerView) {
        val context: Context = recyclerView.context
        val controller: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_anim_fall_down)
        // updates items
        list.clear()
        list.addAll(anime ?: arrayListOf())

        // updates adapter
        recyclerView.layoutAnimation = controller
        recyclerView.adapter?.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

}

