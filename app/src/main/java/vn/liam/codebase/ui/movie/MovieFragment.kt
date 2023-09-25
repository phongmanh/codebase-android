package vn.liam.codebase.ui.movie

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import vn.liam.codebase.R
import vn.liam.codebase.base.adapters.SimpleRecyclerPagingAdapter
import vn.liam.codebase.base.adapters.SimpleRecyclerPagingItem
import vn.liam.codebase.databinding.FragmentHomeBinding
import vn.luke.library.journey.base.scope.micro.MicroFragmentBinding

@AndroidEntryPoint
class MovieFragment : MicroFragmentBinding<FragmentHomeBinding, MovieRoute>(
    R.layout.fragment_home
), SimpleRecyclerPagingAdapter.ItemViewClickListener {
    private val viewModel: MovieViewModel by viewModels()

    private lateinit var mAdapter: SimpleRecyclerPagingAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()

    }


    private var previousJob: Job? = null
    private fun makeAdapter() {
        previousJob?.cancel()
        previousJob = viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.getMovies().onEach { pagingData ->
//                    val item: PagingData<SimpleRecyclerPagingItem> =
//                        pagingData.map { movie ->
//                            movie.imagePosterUrl =
//                                "https://image.tmdb.org/t/p/original" + "${movie.poster_path}"
//                            MovieItem(
//                                mAdapter,
//                                movie,
//                                this@MovieFragment
//                            )
//                        }
                    mAdapter.submitData(pagingData.map { movie ->
                        movie.imagePosterUrl =
                            "https://image.tmdb.org/t/p/original" + "${movie.poster_path}"
                        MovieItem(
                            mAdapter,
                            movie,
                            this@MovieFragment
                        )
                    })
                }.collect()
            }
        }
    }

    private fun setupView() {
        mBinding.lifecycleOwner = viewLifecycleOwner
        headerTitle(false)
        mAdapter = SimpleRecyclerPagingAdapter()
        mBinding.rvMovie.apply {
            itemAnimator = null
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
            makeAdapter()
        }
        //  -------------------------------------
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                mAdapter.loadStateFlow.onEach {
                    it.decideOnState(
                        showLoading = { loading ->
                            if (loading) {
                                mBinding.progressLinear.visibility = View.VISIBLE
                                mBinding.showError = false
                            } else {
                                mBinding.progressLinear.visibility = View.GONE
                            }
                        },
                        showEmptyState = { isEmpty ->
                            if (isEmpty) {
                                mBinding.showError = true
                            } else {
                                mBinding.progressLinear.visibility = View.GONE
                                mBinding.progressCircular.visibility = View.GONE
                                mBinding.showError = false
                            }
                        },
                        showErrorMessage = { error ->
                            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
                        }
                    )
                }.collect()
            }
        }


        mBinding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                isRefreshing = false
                mAdapter.retry()
                makeAdapter()
            }
        }

        viewModel.searchQuery.observe(viewLifecycleOwner) {
            headerTitle(!it.isNullOrBlank())
            makeAdapter()
        }
        searchViewHanding()

    }

    private fun searchViewHanding() {
        mBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setSearchQuery(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) viewModel.setSearchQuery(null)
                return false
            }

        })
    }

    private fun headerTitle(isSearch: Boolean) {
        if (isSearch) {
            mBinding.title = getString(R.string.search_result_title)
        } else {
            mBinding.title = getString(R.string.trending_title)
        }
    }

    override fun onItemViewClick(itemView: SimpleRecyclerPagingItem) {
        // Movie Details
        val item = itemView as MovieItem
        route?.onMovieSelected(item.movieModel)
    }

    private inline fun CombinedLoadStates.decideOnState(
        showLoading: (Boolean) -> Unit,
        showEmptyState: (Boolean) -> Unit,
        showErrorMessage: (String) -> Unit
    ) {
        showLoading(refresh is LoadState.Loading)

        showEmptyState(
            source.append is LoadState.NotLoading
                    && source.append.endOfPaginationReached
                    && mAdapter.itemCount == 0
        )

        val errorState = source.append as? LoadState.Error
            ?: source.prepend as? LoadState.Error
            ?: source.refresh as? LoadState.Error
            ?: append as? LoadState.Error
            ?: prepend as? LoadState.Error
            ?: refresh as? LoadState.Error

        errorState?.let {
            if (mAdapter.itemCount == 0) {
                showErrorMessage(it.error.message ?: "UnknownError")
            }
        }
    }

}
