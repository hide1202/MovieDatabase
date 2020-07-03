package io.viewpoint.moviedatabase.platform.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.databinding.ActivitySearchResultDetailBinding
import io.viewpoint.moviedatabase.model.ui.SearchResultModel
import io.viewpoint.moviedatabase.platform.externsion.getSerializable

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.result = result
    }

    companion object {
        private const val EXTRA_RESULT_MODEL = "resultModel"

        fun intent(
            context: Context,
            result: SearchResultModel
        ): Intent = Intent(context, SearchResultDetailActivity::class.java)
            .apply {
                putExtra(EXTRA_RESULT_MODEL, result)
            }
    }
}