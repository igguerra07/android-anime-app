package br.com.igguerra.animeapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.com.igguerra.animeapp.R
import br.com.igguerra.animeapp.model.Anime
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_anime.view.*

class AnimeAdapter(
    private val anime: List<Anime>,
    private val onItemClick: (Anime) -> Unit
) : RecyclerView.Adapter<AnimeAdapter.AnimeTopViewHolder>() {

    class AnimeTopViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.animePoster
        private val episodes: TextView = itemView.animeEpisodes
        private val average: TextView = itemView.animeAverage
        private val title: TextView = itemView.animeTitle
        private val type: TextView = itemView.animeType
        private val content: ConstraintLayout = itemView.animeItemContent
        private val ctx: Context = context

        fun bind(anime: Anime, onClick: (Anime) -> Unit) {
            Picasso.get().load(anime.image_url).placeholder(R.drawable.ic_launcher_background).into(poster)
            title.text = anime.title
            episodes.text = ctx.getString(R.string.episodes_count, anime.episodes)
            average.text = anime.score.toString()
            type.text = anime.type
            content.setOnClickListener { onClick(anime) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeTopViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_anime, parent, false)
        return AnimeTopViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: AnimeTopViewHolder, position: Int) {
        holder.bind(anime[position]) {
            onItemClick(it)
        }
    }

    override fun getItemCount(): Int = anime.size
}