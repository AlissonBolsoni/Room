package br.com.alisson.roomapp.data

import android.arch.lifecycle.LiveData

import javax.inject.Inject

class ListItemRepository @Inject
constructor(private val listItemDao: ListItemDao) {


    val listItems: LiveData<List<ListItem>>
        get() = listItemDao.getListItems()

    fun getListItemById(itemId: String): LiveData<ListItem> {
        return listItemDao.getListItemById(itemId)
    }

    fun insertListItem(listItem: ListItem): Long? {
        return listItemDao.insertListItem(listItem)
    }

    fun deleteListItem(listItem: ListItem) {
        listItemDao.deleteListItem(listItem)
    }

}
