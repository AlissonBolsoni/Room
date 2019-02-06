package br.com.alisson.roomapp.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.os.AsyncTask
import br.com.alisson.roomapp.data.ListItem
import br.com.alisson.roomapp.data.ListItemRepository

class ListItemCollectionViewModel internal constructor(private val repository: ListItemRepository) : ViewModel() {

    val listItems: LiveData<List<ListItem>>
        get() = repository.listItems

    fun deleteListItem(listItem: ListItem) {
        val deleteItemTask = DeleteItemTask()
        deleteItemTask.execute(listItem)
    }

    private inner class DeleteItemTask : AsyncTask<ListItem, Void, Void>() {

        override fun doInBackground(vararg item: ListItem): Void? {
            repository.deleteListItem(item[0])
            return null
        }
    }

}

