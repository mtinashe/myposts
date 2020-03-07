package com.mtinashe.myposts.app

import android.app.Application
import com.mtinashe.myposts.data.api.repositories.PostsRepository
import com.mtinashe.myposts.data.db.AppDatabase
import com.mtinashe.myposts.ui.viewmodels.factories.PostsViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MyPostsApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyPostsApplication))

        bind() from singleton { AppDatabase(instance()) }

        bind() from singleton {
            PostsRepository(instance())
        }

        bind() from singleton { instance<AppDatabase>().postDao() }

        bind() from provider {
            PostsViewModelFactory(instance())
        }
    }
}
