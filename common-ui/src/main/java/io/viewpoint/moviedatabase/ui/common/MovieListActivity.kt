package io.viewpoint.moviedatabase.ui.common

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.extensions.intentToActivity
import io.viewpoint.moviedatabase.ui.common.databinding.ActivityMovieListBinding
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.KClass

@AndroidEntryPoint
class MovieListActivity : AppCompatActivity() {
    private val binding: ActivityMovieListBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_movie_list)
    }

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

        binding.close.setOnClickListener {
            finish()
        }

        val adapter = MovieListAdapter()
        binding.movieList.adapter = adapter
        viewModel.movieList.observe(this) {
            lifecycleScope.launch {
                adapter.submitData(it)
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