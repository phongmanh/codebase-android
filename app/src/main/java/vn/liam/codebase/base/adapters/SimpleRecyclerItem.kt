package vn.liam.codebase.base.adapters

import androidx.databinding.ViewDataBinding
import vn.liam.codebase.base.adapters.BindableRecyclerAdapter
import vn.liam.codebase.base.adapters.BindableViewHolder

abstract class SimpleRecyclerItem {

    abstract var adapter: BindableRecyclerAdapter
    abstract fun getLayout(): Int
    fun getViewType() = getLayout()
    abstract fun getViewHolderProvider(): (binding: ViewDataBinding) -> BindableViewHolder

}