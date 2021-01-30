package app.sram.bikestore.paging

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import app.sram.bikestore.paging.dao.Movies

class MoviesRxAdapter : PagingDataAdapter<Movies.MovieEntity, MovieGridViewHolder>(
    COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGridViewHolder {
        return MovieGridViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MovieGridViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Movies.MovieEntity>() {
            override fun areItemsTheSame(oldItem: Movies.MovieEntity, newItem: Movies.MovieEntity): Boolean {
                return oldItem.movieId == newItem.movieId
            }

            override fun areContentsTheSame(oldItem: Movies.MovieEntity, newItem: Movies.MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}
