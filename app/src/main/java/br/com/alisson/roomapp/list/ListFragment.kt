package br.com.alisson.roomapp.list


import android.app.ActivityOptions
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.transition.Fade
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import br.com.alisson.roomapp.R
import br.com.alisson.roomapp.RoomAppApplication
import br.com.alisson.roomapp.create.CreateActivity
import br.com.alisson.roomapp.data.ListItem
import br.com.alisson.roomapp.detail.DetailActivity
import br.com.alisson.roomapp.viewmodel.ListItemCollectionViewModel
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject


class ListFragment : Fragment() {

    private var listOfData: MutableList<ListItem>? = null

    private var recyclerView: RecyclerView? = null
    private var adapter: CustomAdapter? = null
    private var toolbar: Toolbar? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var listItemCollectionViewModel: ListItemCollectionViewModel

    /*------------------------------- Lifecycle -------------------------------*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity!!.application as RoomAppApplication)
            .getApplicationComponent()!!
            .inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Set up and subscribe (observe) to the ViewModel
        listItemCollectionViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ListItemCollectionViewModel::class.java!!)

        listItemCollectionViewModel.listItems.observe(this, Observer<List<ListItem>> { listItems ->
            if (listOfData == null) {
                setListData(listItems)
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_list, container, false)

        recyclerView = v.findViewById<View>(R.id.rec_list_activity) as RecyclerView
        toolbar = v.findViewById<View>(R.id.tlb_list_activity) as Toolbar

        toolbar!!.setTitle(R.string.title_toolbar)
        toolbar!!.setLogo(R.drawable.ic_view_list_white_24dp)
        toolbar!!.titleMarginStart = 72

        val fabulous = v.findViewById<View>(R.id.fab_create_new_item) as FloatingActionButton

        fabulous.setOnClickListener { startCreateActivity() }

        return v
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    fun startDetailActivity(itemId: String, viewRoot: View) {
        val container = activity
        val i = Intent(container, DetailActivity::class.java)
        i.putExtra(EXTRA_ITEM_ID, itemId)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            container!!.window.enterTransition = Fade(Fade.IN)
            container.window.enterTransition = Fade(Fade.OUT)

            val options = ActivityOptions
                .makeSceneTransitionAnimation(
                    container,
                    Pair(
                        viewRoot.findViewById(R.id.imv_list_item_circle),
                        getString(R.string.transition_drawable)
                    ),
                    Pair(
                        viewRoot.findViewById(R.id.lbl_message),
                        getString(R.string.transition_message)
                    ),
                    Pair(
                        viewRoot.findViewById(R.id.lbl_date_and_time),
                        getString(R.string.transition_time_and_date)
                    )
                )

            startActivity(i, options.toBundle())

        } else {
            startActivity(i)
        }
    }

    fun startCreateActivity() {
        startActivity(Intent(activity, CreateActivity::class.java))
    }


    fun setListData(listOfData: List<ListItem>?) {
        this.listOfData = ArrayList(listOfData)

        val layoutManager = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = layoutManager
        adapter = CustomAdapter()
        recyclerView!!.adapter = adapter


        val itemDecoration = DividerItemDecoration(
            recyclerView!!.context,
            layoutManager.orientation
        )

        itemDecoration.setDrawable(
            ContextCompat.getDrawable(
                activity!!,
                R.drawable.divider_white
            )!!
        )

        recyclerView!!.addItemDecoration(
            itemDecoration
        )


        val itemTouchHelper = ItemTouchHelper(createHelperCallback())
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }


    /*-------------------- RecyclerView Boilerplate ----------------------*/

    private inner class CustomAdapter : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {//6

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.CustomViewHolder {
            val v = layoutInflater!!.inflate(R.layout.item_data, parent, false)
            return CustomViewHolder(v)
        }

        override fun onBindViewHolder(holder: CustomAdapter.CustomViewHolder, position: Int) {
            //11. and now the ViewHolder data
            val currentItem = listOfData!![position]

            holder.coloredCircle.setImageResource(currentItem.colorResource)


            holder.message.text = currentItem.message

            holder.dateAndTime.text = currentItem.itemId

            holder.loading.visibility = View.INVISIBLE
        }

        override fun getItemCount(): Int {
            // 12. Returning 0 here will tell our Adapter not to make any Items. Let's fix that.
            return listOfData!!.size
        }

        internal inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {

            //10. now that we've made our layouts, let's bind them
            val coloredCircle: CircleImageView
            val dateAndTime: TextView
            val message: TextView
            val container: ViewGroup
            val loading: ProgressBar

            init {
                this.coloredCircle = itemView.findViewById<View>(R.id.imv_list_item_circle) as CircleImageView
                this.dateAndTime = itemView.findViewById<View>(R.id.lbl_date_and_time) as TextView
                this.message = itemView.findViewById<View>(R.id.lbl_message) as TextView
                this.loading = itemView.findViewById<View>(R.id.pro_item_data) as ProgressBar

                this.container = itemView.findViewById<View>(R.id.root_list_item) as ViewGroup
                /*
                We can pass "this" as an Argument, because "this", which refers to the Current
                Instance of type CustomViewHolder currently conforms to (implements) the
                View.OnClickListener interface. I have a Video on my channel which goes into
                Interfaces with Detailed Examples.

                Search "Android WTF: Java Interfaces by Example"
                 */
                this.container.setOnClickListener(this)
            }

            override fun onClick(v: View) {
                //getAdapterPosition() get's an Integer based on which the position of the current
                //ViewHolder (this) in the Adapter. This is how we get the correct Data.
                val listItem = listOfData!![this.adapterPosition]

                startDetailActivity(listItem.itemId?:"", v)

            }
        }

    }

    private fun createHelperCallback(): ItemTouchHelper.Callback {
        return object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            //not used, as the first parameter above is 0
            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                listItemCollectionViewModel.deleteListItem(
                    listOfData!![position]
                )

                //ensure View is consistent with underlying data
                listOfData!!.removeAt(position)
                adapter!!.notifyItemRemoved(position)


            }
        }
    }

    companion object {

        private val EXTRA_ITEM_ID = "EXTRA_ITEM_ID"

        fun newInstance(): ListFragment {
            return ListFragment()
        }
    }
}
