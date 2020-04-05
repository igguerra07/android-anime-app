package br.com.igguerra.animeapp.ui.activity

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import br.com.igguerra.animeapp.R
import br.com.igguerra.animeapp.ui.fragment.AnimeFavsListFragment
import br.com.igguerra.animeapp.ui.fragment.AnimeListFragment
import br.com.igguerra.animeapp.ui.viewmodel.AnimeViewModel
import br.com.igguerra.animeapp.utils.AppExtensions.openFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val animeViewModel: AnimeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Setup fragments
        val animeList   = AnimeListFragment()
        val animeFavs =  AnimeFavsListFragment()

        // Remove elevation from toolbar
        supportActionBar?.elevation = 0F

        // Setup bottom nav options
        mainBottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item_animes -> openFragment(animeList, R.id.mainContent)
                R.id.menu_item_favs   -> openFragment(animeFavs, R.id.mainContent)
                else -> throw IllegalArgumentException("Fragment class not found")
            }
            return@setOnNavigationItemSelectedListener true
        }

        // Set first fragment
        mainBottomNav.selectedItemId =
            R.id.menu_item_animes

        // Request Anime's Trends
        animeViewModel.getTopAnimes()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }
}
