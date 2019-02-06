package br.com.alisson.roomapp.create

import android.os.Bundle
import br.com.alisson.roomapp.R
import br.com.alisson.roomapp.util.BaseActivity

class CreateActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)


        val manager = supportFragmentManager

        var fragment = manager.findFragmentByTag(CREATE_FRAG) as CreateFragment?

        if (fragment == null) {
            fragment = CreateFragment.newInstance()
        }

        addFragmentToActivity(
            manager,
            fragment!!,
            R.id.root_activity_create,
            CREATE_FRAG
        )

    }

    companion object {

        private val CREATE_FRAG = "CREATE_FRAG"
    }
}
