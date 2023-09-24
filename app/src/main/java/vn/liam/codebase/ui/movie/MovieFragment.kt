package vn.liam.codebase.ui.movie

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import vn.liam.codebase.R
import vn.liam.codebase.base.adapters.SimpleRecyclerPagingAdapter
import vn.liam.codebase.base.adapters.SimpleRecyclerPagingItem
import vn.liam.codebase.databinding.FragmentHomeBinding
import vn.luke.library.journey.base.contract.JRoute
import vn.luke.library.journey.base.scope.micro.MicroFragmentBinding

@AndroidEntryPoint
class MovieFragment : MicroFragmentBinding<FragmentHomeBinding, JRoute>(
    R.layout.fragment_home
), SimpleRecyclerPagingAdapter.ItemViewClickListener {
    private val viewModel: MovieViewModel by activityViewModels()

    private lateinit var mAdapter: SimpleRecyclerPagingAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()

    }

    private fun setupView() {
        mBinding.lifecycleOwner = viewLifecycleOwner
        headerTitle(false)
        mAdapter = SimpleRecyclerPagingAdapter()
        mBinding.rvMovie.apply {
            itemAnimator = null
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    viewModel.trendingMovie.onEach { pagingData ->
                        val item: PagingData<SimpleRecyclerPagingItem> = pagingData.map { movie ->
                            movie.imagePosterUrl =
                                "https://image.tmdb.org/t/p/original" + "${movie.poster_path}"
                            MovieItem(
                                mAdapter,
                                movie,
                                this@MovieFragment
                            )
                        }
                        mAdapter.submitData(item)
                    }.collect()
                }
            }
        }

        //  -------------------------------------
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                mAdapter.loadStateFlow.onEach {
                    run {
                        if (it.append is LoadState.Loading) {
                            mBinding.progressLinear.visibility = View.VISIBLE
                        } else if (it.refresh is LoadState.Loading) {
                            mBinding.progressLinear.visibility = View.GONE
                            mBinding.progressCircular.visibility = View.VISIBLE
                        } else {
                            mBinding.progressLinear.visibility = View.GONE
                            mBinding.progressCircular.visibility = View.GONE
                            if (it.refresh is LoadState.Error || it.append is LoadState.Error) {
                                //Error handing
                            } else {
                                // Something went wrong
                            }
                        }
                        mBinding.executePendingBindings()
                    }
                }.collect()
            }
        }


        mBinding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                isRefreshing = false
                mAdapter.refresh()
            }
        }

        viewModel.searchQuery.observe(viewLifecycleOwner) {
            headerTitle(!it.isNullOrBlank())
            mAdapter.refresh()
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
                return false
            }

        })

        mBinding.searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)?.let {
            it.setOnClickListener {
                //Clear query
                mBinding.searchView.setQuery("", false);
                //Collapse the action view
                mBinding.searchView.onActionViewCollapsed();
                viewModel.setSearchQuery(null)
            }
        }
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
        Toast.makeText(
            requireContext(),
            "Movie selected: ${item.movieModel.movieId}.",
            Toast.LENGTH_LONG
        ).show()
    }

}