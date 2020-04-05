package br.com.igguerra.animeapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.igguerra.animeapp.R
import br.com.igguerra.animeapp.application.BaseFragment
import br.com.igguerra.animeapp.application.Constants
import br.com.igguerra.animeapp.model.Anime
import br.com.igguerra.animeapp.ui.adapter.AnimeAdapter
import br.com.igguerra.animeapp.ui.viewmodel.AnimeViewModel
import kotlinx.android.synthetic.main.fragment_anime_favs.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnimeFavsListFragment : BaseFragment() {

    private val animeViewModel: AnimeViewModel by viewModel()

    private lateinit var favsAdapter: AnimeAdapter

    private val favs: ArrayList<Anime> = arrayListOf()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        animeViewModel.animeFavs.observe(viewLifecycleOwner, Observer {
            favs.clear()
            favs.addAll(it)
            updateDataSet(animeFavsList)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        changeTitle(getString(R.string.title_favs))
        return inflater.inflate(R.layout.fragment_anime_favs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Setup recyclerview
        favsAdapter = AnimeAdapter(favs) {
            AnimeDetailsFragment.newInstance(it).show(parentFragmentManager, Constants.FRAG_FAVS_TAG)
        }
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