package vn.liam.codebase.ui.movie.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import vn.liam.codebase.BR
import vn.liam.codebase.R
import vn.liam.codebase.base.networking.Resource
import vn.liam.codebase.databinding.FragmentMovieDetailsBinding
import vn.luke.library.journey.base.scope.micro.MicroFragmentBinding

@AndroidEntryPoint
class MovieDetailsFragment :
    MicroFragmentBinding<FragmentMovieDetailsBinding, DetailsRoute>(R.layout.fragment_movie_details) {

    private val viewModel: DetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.saveArgs(arguments)
        setupView()
    }

    private fun setupView() {
        mBinding.title = "Movie Details"
        mBinding.toolbar.setNavigationOnClickListener {
            route?.onDetailsGoBack()
        }
        viewModel.movieId?.let {
            viewModel.getMovieById(it).observe(viewLifecycleOwner) { res ->
                when (res) {
                    is Resource.Success -> {
                        showHideErrorContainer(false)
                        val movie = res.data!!
                        movie.imagePosterUrl =
                            "https://image.tmdb.org/t/p/original" + "${movie.poster_path}"
                        mBinding.setVariable(BR.movie, movie)
                        showLoading(false)
                    }

                    is Resource.Error -> {
                        showHideErrorContainer(true)
                        showLoading(false)
                    }

                    is Resource.Loading -> {
                        showLoading(true)
                    }
                }
            }
        }
    }

    private fun showHideErrorContainer(state: Boolean) {
        mBinding.setVariable(BR.showError, state)
    }
    private fun showLoading(isShow: Boolean) {
        mBinding.loadingProgress.visibility = if (isShow) View.VISIBLE else View.GONE
    }

}