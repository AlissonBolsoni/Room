package br.com.alisson.roomapp.create


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import br.com.alisson.roomapp.R
import br.com.alisson.roomapp.RoomAppApplication
import br.com.alisson.roomapp.data.ListItem
import br.com.alisson.roomapp.list.ListActivity
import br.com.alisson.roomapp.viewmodel.NewListItemViewModel
import com.viewpagerindicator.CirclePageIndicator
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class CreateFragment : Fragment() {

    private var drawablePager: ViewPager? = null
    private var pageIndicator: CirclePageIndicator? = null
    private var back: ImageButton? = null
    private var done: ImageButton? = null
    private var messageInput: EditText? = null

    private var pagerAdapter: PagerAdapter? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var newListItemViewModel: NewListItemViewModel? = null

    val date: String
        get() {

            val currentDate = Calendar.getInstance().time

            val format = SimpleDateFormat("yyyy/MM/dd/kk:mm:ss")

            return format.format(currentDate)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        (activity!!.application as RoomAppApplication)
            .getApplicationComponent()!!
            .inject(this)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Set up and subscribe (observe) to the ViewModel
        newListItemViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewListItemViewModel::class.java!!)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_create, container, false)

        back = v.findViewById<View>(R.id.imb_create_back) as ImageButton

        back!!.setOnClickListener { startListActivity() }

        done = v.findViewById<View>(R.id.imb_create_done) as ImageButton
        done!!.setOnClickListener {
            val listItem = ListItem(
                date,
                messageInput!!.text.toString(),
                getDrawableResource(drawablePager!!.currentItem)
            )
            newListItemViewModel!!.addNewItemToDatabase(listItem)

            startListActivity()
        }

        messageInput = v.findViewById<View>(R.id.edt_create_message) as EditText

        drawablePager = v.findViewById<View>(R.id.vp_create_drawable) as ViewPager

        pagerAdapter = DrawablePagerAdapter()
        drawablePager!!.adapter = pagerAdapter

        pageIndicator = v.findViewById<View>(R.id.vpi_create_drawable) as CirclePageIndicator
        pageIndicator!!.setViewPager(drawablePager)

        return v
    }

    fun getDrawableResource(pagerItemPosition: Int): Int {
        when (pagerItemPosition) {
            0 -> return R.drawable.red_drawable
            1 -> return R.drawable.blue_drawable
            2 -> return R.drawable.green_drawable
            3 -> return R.drawable.yellow_drawable
            else -> return 0
        }
    }

    private fun startListActivity() {
        startActivity(Intent(activity, ListActivity::class.java))
    }

    private inner class DrawablePagerAdapter : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val inflater = LayoutInflater.from(activity)
            val pagerItem = inflater.inflate(
                R.layout.item_drawable_pager,
                container,
                false
            ) as ImageView

            when (position) {
                0 -> pagerItem.setImageResource(R.drawable.red_drawable)
                1 -> pagerItem.setImageResource(R.drawable.blue_drawable)
                2 -> pagerItem.setImageResource(R.drawable.green_drawable)
                3 -> pagerItem.setImageResource(R.drawable.yellow_drawable)
            }

            container.addView(pagerItem)
            return pagerItem
        }

        override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
            collection.removeView(view as View)
        }

        override fun getCount(): Int {
            return 4
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }
    }

    companion object {

        fun newInstance(): CreateFragment {
            return CreateFragment()
        }
    }
}// Required empty public constructor
