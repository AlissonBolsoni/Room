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

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import br.com.alisson.roomapp.data.ListItemRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by R_KAY on 8/17/2017.
 */
@Singleton
class CustomViewModelFactory @Inject
constructor(private val repository: ListItemRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ListItemCollectionViewModel::class.java))
            ListItemCollectionViewModel(repository) as T
        else if (modelClass.isAssignableFrom(ListItemViewModel::class.java))
            ListItemViewModel(repository) as T
        else if (modelClass.isAssignableFrom(NewListItemViewModel::class.java))
            NewListItemViewModel(repository) as T
        else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
