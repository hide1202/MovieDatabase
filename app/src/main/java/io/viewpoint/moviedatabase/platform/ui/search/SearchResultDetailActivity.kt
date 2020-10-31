package io.viewpoint.moviedatabase.platform.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.databinding.ActivitySearchResultDetailBinding
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.platform.externsion.intentToActivity
import io.viewpoint.moviedatabase.viewmodel.search.MovieSearchResultDetailViewModel
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
        }
        val resultArgument = intent?.getSerializableExtra(EXTRA_RESULT_MODEL) as? SearchResultModel

        lifecycleScope.launch {
            val result =
                if (movieId != null) viewModel.loadWithMovieId(movieId)
                else resultArgument ?: throw IllegalArgumentException()
            binding.result = result
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