package br.com.alisson.roomapp.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.alisson.roomapp.R
import br.com.alisson.roomapp.RoomAppApplication
import br.com.alisson.roomapp.data.ListItem
import br.com.alisson.roomapp.viewmodel.ListItemViewModel

import javax.inject.Inject


class DetailFragment : Fragment() {

    private var dateAndTime: TextView? = null
    private var message: TextView? = null
    private var coloredBackground: ImageView? = null

    private var itemId: String? = null

    @Inject
    internal var viewModelFactory: ViewModelProvider.Factory? = null

    lateinit var listItemViewModel: ListItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity!!.application as RoomAppApplication)
            .getApplicationComponent()!!
            .inject(this)

        val args = arguments

        this.itemId = args!!.getString(EXTRA_ITEM_ID)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Set up and subscribe (observe) to the ViewModel
        listItemViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ListItemViewModel::class.java)

        listItemViewModel.getListItemById(itemId?:"").observe(this, object : Observer<ListItem> {
            override fun onChanged(listItem: ListItem?) {
                if (listItem != null) {
                    dateAndTime!!.text = listItem.itemId
                    message!!.text = listItem.message
                    coloredBackground!!.setImageResource(listItem!!.colorResource)
                }

            }
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_detail, container, false)

        dateAndTime = v.findViewById(R.id.lbl_date_and_time_header)

        message = v.findViewById(R.id.lbl_message_body)


        coloredBackground = v.findViewById(R.id.imv_colored_background)

        return v
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    companion object {

        private val EXTRA_ITEM_ID = "EXTRA_ITEM_ID"


        fun newInstance(itemId: String): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putString(EXTRA_ITEM_ID, itemId)

            fragment.arguments = args
            return fragment
        }
    }

}



















