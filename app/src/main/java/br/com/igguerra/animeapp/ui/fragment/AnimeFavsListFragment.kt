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
import br.com.igguerra.animeapp.model.AnimeItem
import br.com.igguerra.animeapp.ui.adapter.AnimeAdapter
import br.com.igguerra.animeapp.ui.viewmodel.AnimeViewModel
import br.com.igguerra.animeapp.ui.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_anime_favs.*

class AnimeFavsListFragment: Fragment() {

    private val viewModel by lazy { ViewModelProvider(this, ViewModelFactory()).get(AnimeViewModel::class.java) }
    private lateinit var favsAdapter: AnimeAdapter
    private val animes: ArrayList<AnimeItem> = arrayListOf()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.animeFavs.observe(viewLifecycleOwner, Observer {
            animes.clear()
            animes.addAll(it)
            updateDataSet(animeFavsList)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_anime_favs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
        favsAdapter = AnimeAdapter(animes) {}
        animeFavsList.layoutManager = LinearLayoutManager(requireContext())
        animeFavsList.adapter = favsAdapter
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