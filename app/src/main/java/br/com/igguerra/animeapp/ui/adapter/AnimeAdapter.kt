package br.com.igguerra.animeapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.igguerra.animeapp.R
import br.com.igguerra.animeapp.model.Anime

class AnimeAdapter(private val animes: List<Anime>): RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {
    class AnimeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(anime: Anime) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_anime, parent, false)
        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(animes[position])
    }

    override fun getItemCount(): Int = animes.size
}