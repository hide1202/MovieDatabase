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
import io.viewpoint.moviedatabase.platform.externsion.getSerializable
import io.viewpoint.moviedatabase.platform.externsion.intentToActivity
import io.viewpoint.moviedatabase.viewmodel.search.MovieSearchResultDetailViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchResultDetailActivity : AppCompatActivity() {
    private val binding: ActivitySearchResultDetailBinding by lazy {
        DataBindingUtil.setContentView<ActivitySearchResultDetailBinding>(
            this,
            R.layout.activity_search_result_detail
        )
    }

    private val result: SearchResultModel by lazy {
        intent?.getSerializable<SearchResultModel>(EXTRA_RESULT_MODEL)
            ?: throw IllegalStateException()
    }

    private val viewModel: MovieSearchResultDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.result = result
        binding.vm = viewModel

        lifecycleScope.launch {
            viewModel.loadWithResult(result)
        }
    }

    companion object {
        private const val EXTRA_RESULT_MODEL = "resultModel"

        fun intent(
            context: Context,
            result: SearchResultModel
        ): Intent = intentToActivity<SearchResultDetailActivity>(context)
            .apply {
                putExtra(EXTRA_RESULT_MODEL, result)
            }
    }
}