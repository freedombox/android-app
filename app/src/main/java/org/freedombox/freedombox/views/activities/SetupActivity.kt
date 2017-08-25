package org.freedombox.freedombox.views.activities

import android.os.Bundle
import org.freedombox.freedombox.R
import org.freedombox.freedombox.views.fragments.DiscoveryFragment
import org.freedombox.freedombox.views.fragments.SetupFragment

class SetupActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.setup)
        loadFragment(R.id.rootLayout, SetupFragment.new(savedInstanceState ?: Bundle()))
    }
}