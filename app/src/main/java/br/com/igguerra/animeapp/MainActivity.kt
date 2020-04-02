package br.com.igguerra.animeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.fragment.app.Fragment
import br.com.igguerra.animeapp.ui.fragment.AnimeListFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.elevation = 0F
        // Setup bottom nav options
        mainBottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item_animes -> openFragment(AnimeListFragment())
                R.id.menu_item_favs -> openFragment(Fragment())
                else -> throw IllegalArgumentException("Fragment class not found")
            }
            return@setOnNavigationItemSelectedListener true
        }
        // Set first fragment
        mainBottomNav.selectedItemId = R.id.menu_item_animes
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    /**
     *
     * @param fragment
     */
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainContent, fragment)
        transaction.commit()
    }
}
