package org.freedombox.freedombox.views.fragments

import android.content.SharedPreferences
import android.os.Bundle
import org.freedombox.freedombox.R
import org.freedombox.freedombox.components.AppComponent
import javax.inject.Inject

class SetupFragment : BaseFragment() {
    val TAG = "SETUP_FRAGMENT"

    @Inject lateinit var sharedPreferences: SharedPreferences


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun getLayoutId() = R.layout.fragment_setup

    companion object {
        fun new(args: Bundle): SetupFragment {
            val fragment = SetupFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun injectFragment(appComponent: AppComponent) = appComponent.inject(this)

}