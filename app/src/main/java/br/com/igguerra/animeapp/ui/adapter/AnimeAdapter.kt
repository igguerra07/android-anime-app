package br.com.igguerra.animeapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.com.igguerra.animeapp.R
import br.com.igguerra.animeapp.model.AnimeItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_anime.view.*

class AnimeAdapter(
    private val anime: List<AnimeItem>,
    private val onItemClick: (AnimeItem) -> Unit
) : RecyclerView.Adapter<AnimeAdapter.AnimeTopViewHolder>() {
    class AnimeTopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.animePoster
        private val episodes: TextView = itemView.animeEpisodes
        private val average: TextView = itemView.animeAverage
        private val title: TextView = itemView.animeTitle
        private val type: TextView = itemView.animeType
         val card: ConstraintLayout = itemView.animeItemContent


        fun bind(anime: AnimeItem) {
            Picasso.get().load(anime.image_url).placeholder(R.drawable.ic_launcher_background).into(poster)
            title.text = anime.title
            episodes.text = ("${anime.episodes} episódios")
            average.text = anime.score.toString()
            type.text = anime.type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeTopViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_anime, parent, false)
        return AnimeTopViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeTopViewHolder, position: Int) {
        holder.bind(anime[position])
        holder.card.setOnClickListener { onItemClick(anime[position]) }
    }

    override fun getItemCount(): Int = anime.size
}