package br.com.alisson.roomapp.list

import android.os.Bundle
import android.support.v4.app.FragmentManager
import br.com.alisson.roomapp.R
import br.com.alisson.roomapp.util.BaseActivity

class ListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val manager = supportFragmentManager

        var fragment = manager.findFragmentByTag(LIST_FRAG) as ListFragment?

        if (fragment == null) {
            fragment = ListFragment.newInstance()
        }

        addFragmentToActivity(
            manager,
            fragment!!,
            R.id.root_activity_list,
            LIST_FRAG
        )

    }

    companion object {

        private val LIST_FRAG = "LIST_FRAG"
    }
}
