package br.com.douglasmotta.dogapichallenge.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.douglasmotta.dogapichallenge.R
import br.com.douglasmotta.dogapichallenge.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

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

        binding.imageListFormat.run {
            tag = ListFormat.LIST.tag
            setOnClickListener {
                val currentFormatTag = updateListFormatIcon()
                changeListFormat(currentFormatTag)
            }
        }
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
                            val errorState = loadState.refresh as LoadState.Error
                            Timber.e(errorState.error)

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

    private fun updateListFormatIcon(): Int {
        return binding.imageListFormat.run {
            if (tag == ListFormat.LIST.tag) {
                tag = ListFormat.GRID.tag
                setImageResource(ListFormat.GRID.icon)
            } else {
                tag = ListFormat.LIST.tag
                setImageResource(ListFormat.LIST.icon)
            }

            tag as Int
        }
    }

    private fun changeListFormat(listFormatTag: Int) {
        val context = requireContext()
        binding.recyclerDogs.layoutManager = if (listFormatTag == ListFormat.LIST.tag) {
            LinearLayoutManager(context)
        } else GridLayoutManager(context, GRID_SPAN_COUNT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    enum class ListFormat(@StringRes val tag: Int, @DrawableRes val icon: Int) {
        LIST(R.string.list_format_listing, R.drawable.ic_list),
        GRID(R.string.list_format_grid, R.drawable.ic_grid)
    }

    companion object {
        private const val FLIPPER_CHILD_LOADING = 0
        private const val FLIPPER_CHILD_DOG = 1
        private const val FLIPPER_CHILD_ERROR = 2
        private const val GRID_SPAN_COUNT = 2
    }
}