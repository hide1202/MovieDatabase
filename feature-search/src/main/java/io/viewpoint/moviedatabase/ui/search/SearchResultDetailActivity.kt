package io.viewpoint.moviedatabase.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.extensions.dp
import io.viewpoint.moviedatabase.extensions.intentToActivity
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.ui.search.databinding.ActivitySearchResultDetailBinding
import io.viewpoint.moviedatabase.ui.search.viewmodel.MovieSearchResultDetailViewModel
import io.viewpoint.moviedatabase.util.SpaceItemDecoration
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchResultDetailActivity : AppCompatActivity() {
    private val binding: ActivitySearchResultDetailBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_search_result_detail
        )
    }

    private val viewModel: MovieSearchResultDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        val movieId = intent?.getIntExtra(EXTRA_MOVIE_ID, Int.MIN_VALUE)?.takeIf {
            it > Int.MIN_VALUE
        } ?: intent.data?.getQueryParameter(EXTRA_MOVIE_ID)?.toInt()
        val resultArgument = intent?.getSerializableExtra(EXTRA_RESULT_MODEL) as? SearchResultModel
        binding.result = resultArgument

        lifecycleScope.launch {
            val result =
                if (movieId != null) viewModel.loadWithMovieId(movieId)
                else resultArgument?.apply {
                    viewModel.loadWithResult(this)
                } ?: throw IllegalArgumentException()
            binding.result = result
        }

        val genreAdapter = GenreAdapter()
        binding.genreList.addItemDecoration(SpaceItemDecoration(spacing = 4.dp))
        binding.genreList.adapter = genreAdapter
        viewModel.genres.observe(this) {
            genreAdapter.updateGenres(it)
        }

        val keywordAdapter = KeywordAdapter()
        binding.keywordList.addItemDecoration(SpaceItemDecoration(spacing = 4.dp))
        binding.keywordList.adapter = keywordAdapter
        viewModel.keywords.observe(this) {
            keywordAdapter.updateGenres(it)
        }

        val countriesAdapter = CountriesAdapter()
        binding.flags.addItemDecoration(SpaceItemDecoration(spacing = 8.dp))
        binding.flags.adapter = countriesAdapter
        viewModel.countries.observe(this) {
            countriesAdapter.submitList(it)
        }

        val productionCompaniesAdapter = ProductionCompaniesAdapter()
        binding.productionCompanies.addItemDecoration(SpaceItemDecoration(spacing = 16.dp))
        binding.productionCompanies.adapter = productionCompaniesAdapter
        viewModel.productionCompanies.observe(this) {
            productionCompaniesAdapter.submitList(it)
        }

        val creditsAdapter = CreditAdapter()
        binding.credits.addItemDecoration(SpaceItemDecoration(spacing = 16.dp))
        binding.credits.adapter = creditsAdapter
        viewModel.credits.observe(this) {
            creditsAdapter.submitList(it)
        }

        val recommendAdapter = RecommendAdapter()
        with(binding.recommendations) {
            addItemDecoration(SpaceItemDecoration(spacing = 16.dp))
            adapter = recommendAdapter
        }
        viewModel.recommendations.observe(this) {
            recommendAdapter.submitList(it)
        }

        val watchProviderAdapter = WatchProviderAdapter()
        with(binding.watchProviders) {
            addItemDecoration(SpaceItemDecoration(spacing = 8.dp))
            adapter = watchProviderAdapter
        }
        viewModel.watchProviders.observe(this) { model ->
            val providers = model?.providers?.values?.flatten() ?: emptyList()
            watchProviderAdapter.submitList(providers.distinctBy { it.providerId })
        }
    }

    companion object {
        private const val EXTRA_RESULT_MODEL = "resultModel"
        private const val EXTRA_MOVIE_ID = "movieId"

        fun intent(
            context: Context,
            result: SearchResultModel
        ): Intent = intentToActivity<SearchResultDetailActivity>(context)
            .apply {
                putExtra(EXTRA_RESULT_MODEL, result)
            }

        fun withMovieId(
            context: Context,
            movieId: Int
        ): Intent = intentToActivity<SearchResultDetailActivity>(context)
            .apply {
                putExtra(EXTRA_MOVIE_ID, movieId)
            }
    }
}