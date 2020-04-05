package br.com.igguerra.animeapp.application

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.igguerra.animeapp.utils.AppExtensions.changeTitle

open class BaseFragment(): Fragment() {
    fun changeTitle(title: String) {
        (activity as AppCompatActivity).changeTitle(title)
    }
}