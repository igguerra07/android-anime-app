package br.com.igguerra.animeapp.application

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import br.com.igguerra.animeapp.R

open class BaseDialogFragment : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Define fullscreen dialog
        setStyle(STYLE_NO_FRAME, R.style.AppTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Define transitions (ENTER/EXIT) animations
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
    }
}