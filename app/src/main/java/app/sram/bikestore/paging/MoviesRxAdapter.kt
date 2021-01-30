package app.sram.bikestore.paging

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import app.sram.bikestore.paging.dao.MoviesDb

class MoviesRxAdapter : PagingDataAdapter<MoviesDb.MovieEntity, MovieGridViewHolder>(
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
        private val COMPARATOR = object : DiffUtil.ItemCallback<MoviesDb.MovieEntity>() {
            override fun areItemsTheSame(oldItem: MoviesDb.MovieEntity, newItem: MoviesDb.MovieEntity): Boolean {
                return oldItem.placeId == newItem.placeId
            }

            override fun areContentsTheSame(oldItem: MoviesDb.MovieEntity, newItem: MoviesDb.MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}
