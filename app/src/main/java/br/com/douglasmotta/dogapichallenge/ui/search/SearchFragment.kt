package br.com.douglasmotta.dogapichallenge.ui.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.douglasmotta.dogapichallenge.R
import br.com.douglasmotta.dogapichallenge.databinding.FragmentSearchBinding
import br.com.douglasmotta.dogapichallenge.domain.ResultStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    private lateinit var searchAdapter: SearchAdapter

    private lateinit var searchView: SearchView
    private var queryTextListener: SearchView.OnQueryTextListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSearchBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBreedsAdapter()
    }

    private fun initBreedsAdapter() {
        searchAdapter = SearchAdapter()
        with(binding.recyclerBreeds) {
            setHasFixedSize(true)
            adapter = searchAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem: MenuItem? = menu.findItem(R.id.action_search)
        val searchManager =
            requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager

        if (searchItem != null) {
            (searchItem.actionView as SearchView).let { searchView ->
                this.searchView = searchView

                searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(requireActivity().componentName)
                )

                queryTextListener = object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let {
                            lifecycleScope.launch {
                                viewLifecycleOwner.lifecycle.repeatOnLifecycle(
                                    Lifecycle.State.STARTED
                                ) {
                                    viewModel.searchBreeds(it).collect {
                                        when (it) {
                                            is ResultStatus.Success -> {
                                                searchAdapter.submitList(it.data)
                                            }
                                            is ResultStatus.Error -> {
                                                Timber.e(it.throwable)
                                            }
                                            else -> {
                                                // Do nothing
                                            }
                                        }
                                    }
                                }
                            }

                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {

                        return true
                    }

                }

                searchView.setOnQueryTextListener(queryTextListener)
            }
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> false
            else -> {
                searchView.setOnQueryTextListener(queryTextListener)
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}