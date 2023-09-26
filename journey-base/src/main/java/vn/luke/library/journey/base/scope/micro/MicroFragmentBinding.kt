package vn.luke.library.journey.base.scope.micro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import vn.luke.library.journey.base.contract.JRoute

abstract class MicroFragmentBinding<ViewBinding : ViewDataBinding, Route : JRoute>(
    private val layout: Int
) : MicroFragment<Route>(layout) {

    protected lateinit var mBinding: ViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (layout > 0) {
            mBinding = DataBindingUtil.inflate(inflater, layout, container, false)
            return mBinding.root
        }
        return null
    }

}

abstract class MicroDialogFragmentBinding<ViewBinding : ViewDataBinding, Route : JRoute>(
    private val layout: Int
) : MicroDialogFragment<Route>() {

    protected lateinit var mBinding: ViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (layout > 0) {
            mBinding = DataBindingUtil.inflate(inflater, layout, container, false)
            return mBinding.root
        }
        return null
    }

}

abstract class MicroBottomSheetFragmentBinding<ViewBinding : ViewDataBinding, Route : JRoute>(
    private val layout: Int
) : MicroBottomSheetFragment<Route>() {

    protected lateinit var mBinding: ViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (layout > 0) {
            mBinding = DataBindingUtil.inflate(inflater, layout, container, false)
            return mBinding.root
        }
        return null
    }

}