package com.mtinashe.myposts.ui.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mtinashe.myposts.data.api.repositories.PostsRepository
import com.mtinashe.myposts.data.api.repositories.SuspendingPostRepository
import com.mtinashe.myposts.ui.viewmodels.PostsViewModel

@Suppress("UNCHECKED_CAST")
class PostsViewModelFactory(
    private val repository: SuspendingPostRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostsViewModel(repository) as T
    }

}