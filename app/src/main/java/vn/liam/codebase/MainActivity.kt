package vn.liam.codebase

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : MainNavigator() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}