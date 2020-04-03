package br.com.igguerra.animeapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.igguerra.animeapp.R
import br.com.igguerra.animeapp.model.AnimeItem
import br.com.igguerra.animeapp.network.Status
import br.com.igguerra.animeapp.ui.adapter.AnimeAdapter
import br.com.igguerra.animeapp.ui.viewmodel.AnimeViewModel
import br.com.igguerra.animeapp.ui.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_anime_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class AnimeListFragment : Fragment(), CoroutineScope{

    val fullData: ArrayList<AnimeItem> = arrayListOf()

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory()
        ).get(AnimeViewModel::class.java)
    }
    private val itemList: ArrayList<AnimeItem> = arrayListOf()
    private lateinit var animeAnimeAdapter: AnimeAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        // Setup observable
        viewModel.topList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    animesLoading.visibility = View.VISIBLE
                    animeListCardError.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    animeListCardError.visibility = View.GONE
                    animesLoading.visibility = View.GONE
                    fullData.clear()
                    fullData.addAll(it.data?.top?: arrayListOf())
                    itemList.clear()
                    itemList.addAll(it.data?.top?: arrayListOf())
                    updateDataSet(animeList)
                }
                Status.ERROR -> {
                    animesLoading.visibility = View.GONE
                    animeListCardError.visibility = View.VISIBLE
                    animeListError.text = it.message
                }
            }
        })

        viewModel.searchResult.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    animeList.visibility = View.INVISIBLE
                    animesLoading.visibility = View.VISIBLE
                    animeListCardError.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    animeList.visibility = View.VISIBLE
                    animeListCardError.visibility = View.GONE
                    animesLoading.visibility = View.GONE
                    itemList.clear()
                    itemList.addAll(it.data!!.results)
                    updateDataSet(animeList)
                }
                Status.ERROR -> {
                    animesLoading.visibility = View.GONE
                    animeList.visibility = View.VISIBLE
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
        return inflater.inflate(R.layout.fragment_anime_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Setup RecyclerView
        animeAnimeAdapter = AnimeAdapter(itemList) {
            AnimeDetailsFragment.newInstance(it.mal_id, it)
                .show(fragmentManager!!, "DIALOG_FRAGMENT_DETAILS")
        }
        animeList.layoutManager = LinearLayoutManager(requireContext())
        animeList.adapter = animeAnimeAdapter

        // Request Top Animes
        viewModel.getTopAnimes()

        // Retry request animes
        animeListRetryButton.setOnClickListener {
            viewModel.getTopAnimes()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val menuItem = menu.findItem(R.id.menu_item_search)
        val search = menuItem.actionView as SearchView
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            var searchFor = ""
            override fun onQueryTextSubmit(query: String?): Boolean {

                search.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(searchFor === newText) return false
                searchFor = newText?:""
                launch {
                    delay(300)
                    if (searchFor != newText) return@launch
                    if(searchFor.isNotEmpty() && searchFor.length >= 3) {
                        viewModel.searchAnime(searchFor)
                    } else {
                        if (fullData != itemList) {
                            itemList.clear()
                            itemList.addAll(fullData)
                            updateDataSet(animeList)
                        }

                    }
                }
                return true
            }


        })
        search.setOnCloseListener {
            if (fullData != itemList) {
                itemList.clear()
                itemList.addAll(fullData)
                updateDataSet(animeList)
            }
            search.clearFocus()
            return@setOnCloseListener false
        }
    }

    private fun updateDataSet(recyclerView: RecyclerView) {
        val context: Context = recyclerView.context
        val controller: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_anim_fall_down)
        recyclerView.layoutAnimation = controller
        recyclerView.adapter!!.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

}

