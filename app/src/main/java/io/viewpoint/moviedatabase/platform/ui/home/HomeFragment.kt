package io.viewpoint.moviedatabase.platform.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.databinding.FragmentHomeBinding
import io.viewpoint.moviedatabase.platform.externsion.dp
import io.viewpoint.moviedatabase.platform.ui.search.MovieSearchActivity
import io.viewpoint.moviedatabase.platform.ui.setting.SettingActivity
import io.viewpoint.moviedatabase.platform.util.SpaceItemDecoration
import io.viewpoint.moviedatabase.viewmodel.main.MainViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setting.setOnClickListener {
            context?.run {
                startActivity(SettingActivity.intent(this))
            }
        }

        binding.search.setOnClickListener {
            context?.run {
                startActivity(MovieSearchActivity.intent(this))
            }
        }

        val adapter =
            PopularAdapter()
        binding.popularList.adapter = adapter
        binding.popularList.addItemDecoration(
            SpaceItemDecoration(16.dp)
        )
        viewModel.popular.observe(viewLifecycleOwner, Observer {
            adapter.updateResults(it)
        })
    }
}