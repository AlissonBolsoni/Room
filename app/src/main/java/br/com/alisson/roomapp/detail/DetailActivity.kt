package br.com.alisson.roomapp.detail

import android.os.Bundle
import android.widget.Toast
import br.com.alisson.roomapp.R
import br.com.alisson.roomapp.util.BaseActivity

class DetailActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val i = intent

        //if extra is null, not worth even bothering to set up the MVVM stuff; Kill it with fire.
        if (i.hasExtra(EXTRA_ITEM_ID)) {
            val itemId = i.getStringExtra(EXTRA_ITEM_ID)

            val manager = supportFragmentManager

            var fragment = manager.findFragmentByTag(DETAIL_FRAG) as DetailFragment?

            if (fragment == null) {
                fragment = DetailFragment.newInstance(itemId)
            }

            addFragmentToActivity(
                manager,
                fragment!!,
                R.id.root_activity_detail,
                DETAIL_FRAG
            )

        } else {
            Toast.makeText(this, R.string.error_no_extra_found, Toast.LENGTH_LONG).show()
        }

    }

    companion object {

        private val EXTRA_ITEM_ID = "EXTRA_ITEM_ID"
        private val DETAIL_FRAG = "DETAIL_FRAG"
    }
}
