package io.viewpoint.moviedatabase.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.designsystem.MovieDatabaseTheme
import io.viewpoint.moviedatabase.extensions.intentToActivity
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.viewmodel.MovieSearchResultDetailViewModel

@AndroidEntryPoint
class SearchResultDetailActivity : AppCompatActivity() {
    private val viewModel: MovieSearchResultDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MovieDatabaseTheme {
                val result by viewModel.result.collectAsState()
                val uiState by viewModel.uiState.collectAsState()

                MovieDetailScreen(
                    result = result,
                    uiState = uiState,
                    onInvertWantToSeeCommand = viewModel.invertWantToSeeCommand,
                )
            }
        }
    }

    companion object {
        const val EXTRA_RESULT_MODEL = "resultModel"
        const val EXTRA_MOVIE_ID = "movieId"

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