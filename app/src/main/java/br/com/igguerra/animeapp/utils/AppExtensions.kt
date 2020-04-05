package br.com.igguerra.animeapp.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

object AppExtensions {

    fun Context.toastShow(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun AppCompatActivity.changeTitle(title: String) {
        this.supportActionBar?.title = title
    }

    fun AppCompatActivity.openFragment(fragment: Fragment, contentResId: Int) {
        val transaction = this.supportFragmentManager.beginTransaction()
        transaction.replace(contentResId, fragment)
        transaction.commit()
    }

}