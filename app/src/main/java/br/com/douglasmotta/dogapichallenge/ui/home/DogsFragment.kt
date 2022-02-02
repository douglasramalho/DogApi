package br.com.douglasmotta.dogapichallenge.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import br.com.douglasmotta.dogapichallenge.databinding.FragmentDogsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class DogsFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentDogsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DogsViewModel by viewModels()

    private lateinit var dogsAdapter: DogsAdapter

    private lateinit var order: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDogsBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOrderSpinner()
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

    private fun initOrderSpinner() {
        order = getString(R.string.order_default_item)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.order_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerOrder.also {
                it.adapter = adapter
                it.onItemSelectedListener = this
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

    private fun collectDogs() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dogsPagingData(order = order).collect { pagingData ->
                    dogsAdapter.submitData(pagingData)
                }
            }
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

    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ) {
        order = parent?.getItemAtPosition(position) as String
        collectDogs()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        // Do nothing
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