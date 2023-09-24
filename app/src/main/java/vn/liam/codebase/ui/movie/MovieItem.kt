package vn.liam.codebase.ui.movie

import androidx.databinding.ViewDataBinding
import vn.liam.codebase.BR
import vn.liam.codebase.R
import vn.liam.codebase.base.adapters.BindableRecyclerPagingAdapter
import vn.liam.codebase.base.adapters.BindableViewHolder
import vn.liam.codebase.base.adapters.SimpleRecyclerPagingAdapter
import vn.liam.codebase.base.adapters.SimpleRecyclerPagingItem
import vn.liam.codebase.base.models.MovieModel

class MovieItem(
    override var adapter: BindableRecyclerPagingAdapter,
    val movieModel: MovieModel,
    val onItemViewClickListener: SimpleRecyclerPagingAdapter.ItemViewClickListener
) :
    SimpleRecyclerPagingItem() {
    override fun getItemData(): Any = movieModel

    override fun getItemDataId(): Any = movieModel.movieId!!

    override fun getLayout(): Int = R.layout.movie_item_layout

    override fun getViewHolderProvider(): (binding: ViewDataBinding) -> BindableViewHolder = {
        MovieItemHolder(it)
    }


    inner class MovieItemHolder(val binding: ViewDataBinding) :
        BindableViewHolder(binding) {

        override fun bind(item: Any) {
            when (item) {
                is MovieItem -> {
                    binding.setVariable(BR.movie, item.movieModel)
                    itemView.setOnClickListener {
                        onItemViewClickListener.onItemViewClick(item)
                    }
                    binding.executePendingBindings()
                }
            }
        }
    }

}