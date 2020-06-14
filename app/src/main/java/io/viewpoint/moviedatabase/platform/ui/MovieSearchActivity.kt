package io.viewpoint.moviedatabase.platform.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.viewpoint.moviedatabase.R

class MovieSearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_search)
    }

    companion object {
        fun intent(context: Context): Intent =
            Intent(context, MovieSearchActivity::class.java)
    }
}