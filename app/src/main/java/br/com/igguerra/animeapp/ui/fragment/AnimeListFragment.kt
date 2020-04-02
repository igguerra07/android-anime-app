package br.com.igguerra.animeapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.igguerra.animeapp.R
import br.com.igguerra.animeapp.model.AnimeTop
import br.com.igguerra.animeapp.network.Status
import br.com.igguerra.animeapp.ui.adapter.AnimTopAdapter
import br.com.igguerra.animeapp.ui.viewmodel.AnimeViewModel
import br.com.igguerra.animeapp.ui.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_anime_list.*


class AnimeListFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory()
        ).get(AnimeViewModel::class.java)
    }
    private val topList: ArrayList<AnimeTop> = arrayListOf()
    private lateinit var animeAnimTopAdapter: AnimTopAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
                    topList.clear()
                    topList.addAll(it.data!!.top)
                    updateDataSet(animeList)
                }
                Status.ERROR -> {
                    animesLoading.visibility = View.GONE
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
        animeAnimTopAdapter = AnimTopAdapter(topList) {
            AnimeDetailsFragment.newInstance(it.mal_id)
                .show(fragmentManager!!, "DIALOG_FRAGMENT_DETAILS")
        }
        animeList.layoutManager = LinearLayoutManager(requireContext())
        animeList.adapter = animeAnimTopAdapter

        // Request Top Animes
        viewModel.getTopAnimes()

        // Retry request animes
        animeListRetryButton.setOnClickListener {
            viewModel.getTopAnimes()
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
}

