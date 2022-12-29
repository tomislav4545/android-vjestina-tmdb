package agency.five.codebase.android.movieapp.data.di

import agency.five.codebase.android.movieapp.data.database.MovieAppDatabase
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val APP_DATABASE_NAME = "app_database.db"
val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            MovieAppDatabase::class.java,
            APP_DATABASE_NAME,
        ).build()
    }
    single { get<MovieAppDatabase>().favoriteMovieDao() }
}
