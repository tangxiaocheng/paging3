package app.sram.bikestore.paging

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import app.sram.bikestore.paging.core.GetMoviesRxViewModel
import app.sram.bikestore.paging.di.Injection
import com.adrena.commerce.paging3.R
import com.adrena.commerce.paging3.databinding.FragmentMovieListBinding
import io.reactivex.disposables.CompositeDisposable

class MovieRxRemoteFragment : Fragment() {
    private val disposable = CompositeDisposable()
    private lateinit var binding: FragmentMovieListBinding
    private lateinit var rvAdapter: MoviesRxAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)

        val view = binding.root

        val viewModel =
            ViewModelProvider(this, Injection.provideRxRemoteViewModel(view.context)).get(
                GetMoviesRxViewModel::class.java
            )

        rvAdapter = MoviesRxAdapter()

        binding.list.layoutManager = GridLayoutManager(view.context, 2)
        binding.list.adapter = rvAdapter
        binding.list.adapter = rvAdapter.withLoadStateFooter(
            footer = LoadingGridStateAdapter()
        )
        rvAdapter.addLoadStateListener { loadState ->
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            errorState?.let {
                val context = view.context
                val localizedMessage = it.error.localizedMessage
                showErrorDialog(context, localizedMessage ?: "empty error message")
            }
        }

        disposable.add(
            viewModel.getFavoriteMovies().subscribe {
                rvAdapter.submitData(lifecycle, it)
            }
        )

        return view
    }

    private fun showErrorDialog(context: Context, localizedMessage: String) {
        AlertDialog.Builder(context)
            .setTitle(R.string.error)
            .setMessage(localizedMessage)
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(R.string.retry) { _, _ ->
                rvAdapter.retry()
            }
            .show()
    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }
}
