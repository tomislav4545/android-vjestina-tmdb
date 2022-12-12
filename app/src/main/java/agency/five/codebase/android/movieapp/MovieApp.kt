package agency.five.codebase.android.movieapp

import agency.five.codebase.android.movieapp.data.di.dataModule
import agency.five.codebase.android.movieapp.data.di.favoritesModule
import agency.five.codebase.android.movieapp.data.di.homeModule
import agency.five.codebase.android.movieapp.data.di.movieDetailsModule
import android.app.Application
import android.util.Log
import org.koin.core.context.startKoin

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Log.d("MovieApp", "App started")
        startKoin {
            modules(
                dataModule,
                favoritesModule,
                movieDetailsModule,
                homeModule
            )
        }
    }
}
