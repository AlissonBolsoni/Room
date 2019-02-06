package br.com.alisson.roomapp.list

import android.view.View
import br.com.alisson.roomapp.data.ListItem


interface ViewInterface {

    fun startDetailActivity(dateAndTime: String, message: String, colorResource: Int, viewRoot: View)

    fun setUpAdapterAndView(listOfData: List<ListItem>)

    fun addNewListItemToView(newItem: ListItem)

    fun deleteListItemAt(position: Int)

    fun showUndoSnackbar()

    fun insertListItemAt(temporaryListItemPosition: Int, temporaryListItem: ListItem)
}
