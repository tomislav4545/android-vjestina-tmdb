package agency.five.codebase.android.movieapp

import agency.five.codebase.android.movieapp.data.di.*
import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Log.d("MovieApp", "App started")
        startKoin {
            androidContext(this@MovieApp)
            modules(
                networkModule,
                databaseModule,
                dataModule,
                favoritesModule,
                movieDetailsModule,
                homeModule
            )
        }
    }
}
