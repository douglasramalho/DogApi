package br.com.douglasmotta.dogapichallenge.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.douglasmotta.dogapichallenge.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var dogsAdapter: DogsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHomeBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDogsAdapter()
        collectDogs()
        collectInitialLoadState()
    }

    private fun collectDogs() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dogsPagingData().collect { pagingData ->
                    dogsAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun initDogsAdapter() {
        dogsAdapter = DogsAdapter()
        with(binding.recyclerDogs) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = dogsAdapter.withLoadStateFooter(
                footer = DogsLoadStateAdapter(
                    dogsAdapter::retry
                )
            )
        }
    }

    private fun collectInitialLoadState() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                dogsAdapter.loadStateFlow.collectLatest { loadState ->
                    binding.flipperHome.displayedChild = when (loadState.refresh) {
                        is LoadState.Loading -> {
                            setShimmerVisibility(true)
                            FLIPPER_CHILD_LOADING
                        }
                        is LoadState.NotLoading -> {
                            setShimmerVisibility(false)
                            FLIPPER_CHILD_DOG
                        }
                        is LoadState.Error -> {
                            setShimmerVisibility(false)
                            binding.includeViewCharactersErrorState.buttonRetry.setOnClickListener {
                                dogsAdapter.retry()
                            }
                            FLIPPER_CHILD_ERROR
                        }
                    }
                }
            }
        }
    }

    private fun setShimmerVisibility(visibility: Boolean) {
        binding.includeViewCharactersLoadingState.shimmerCharacters.run {
            isVisible = visibility
            if (visibility) {
                startShimmer()
            } else stopShimmer()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FLIPPER_CHILD_LOADING = 0
        private const val FLIPPER_CHILD_DOG = 1
        private const val FLIPPER_CHILD_ERROR = 2
    }
}