package io.viewpoint.moviedatabase.platform.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.viewpoint.moviedatabase.R
import io.viewpoint.moviedatabase.databinding.FragmentHomeBinding
import io.viewpoint.moviedatabase.platform.externsion.dp
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

        val popularAdapter = PopularAdapter()
        binding.popularList.adapter = ConcatAdapter(
            LabelAdapter(getString(R.string.popular_header)),
            MovieListAdapter(popularAdapter)
        )
        viewModel.popular.observe(viewLifecycleOwner, Observer {
            popularAdapter.updateResults(it)
        })
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}