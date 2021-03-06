/*
 * *
 *  * Copyright (C) 2017 Ryan Kay Open Source Project
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package br.com.alisson.roomapp.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import br.com.alisson.roomapp.data.ListItem
import br.com.alisson.roomapp.data.ListItemRepository

/**
 * Created by R_KAY on 8/3/2017.
 */

class ListItemViewModel internal constructor(private val repository: ListItemRepository) : ViewModel() {

    fun getListItemById(itemId: String): LiveData<ListItem> {
        return repository.getListItemById(itemId)
    }

}

