package io.viewpoint.moviedatabase.ui.common

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.designsystem.MovieDatabaseTheme
import io.viewpoint.moviedatabase.extensions.intentToActivity
import javax.inject.Inject
import kotlin.reflect.KClass

@AndroidEntryPoint
class MovieListActivity : AppCompatActivity() {
    @Inject
    internal lateinit var services: Set<@JvmSuppressWildcards MovieListProvider>

    @Inject
    internal lateinit var viewModelFactory: MovieListViewModel.Factory

    private val viewModel: MovieListViewModel by viewModels(factoryProducer = {
        val providerClassName = intent.getStringExtra(EXTRA_PROVIDER)
        val service = services.first { it::class.qualifiedName == providerClassName }
        MovieListViewModel.viewModelFactory(viewModelFactory, service)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MovieDatabaseTheme {
                MovieListScreen(
                    movies = viewModel.movieList,
                    onCloseClick = ::finish,
                )
            }
        }
    }

    companion object {
        private const val EXTRA_PROVIDER = "provider"

        fun intent(context: Context, provider: KClass<out MovieListProvider>) =
            intentToActivity<MovieListActivity>(context)
                .apply {
                    putExtra(EXTRA_PROVIDER, provider.qualifiedName)
                }
    }
}