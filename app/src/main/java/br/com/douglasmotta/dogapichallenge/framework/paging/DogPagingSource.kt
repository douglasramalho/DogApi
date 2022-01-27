package br.com.douglasmotta.dogapichallenge.framework.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.douglasmotta.dogapichallenge.data.DogRemoteDataSource
import br.com.douglasmotta.dogapichallenge.domain.model.Dog
import br.com.douglasmotta.dogapichallenge.framework.network.response.toDogDomain
import java.lang.Exception

class DogPagingSource(
    private val remoteDataSource: DogRemoteDataSource,
    private val query: String
): PagingSource<Int, Dog>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Dog> {
        return try {
            val page = params.key ?: 0

            val queries = hashMapOf(
                "page" to page.toString(),
                "limit" to LIMIT.toString()
            )

            if (query.isNotEmpty()) {
                queries["nameStartsWith"] = query
            }

            val response = remoteDataSource.searchDogs(queries)

            LoadResult.Page(
                data = response.map { it.toDogDomain() },
                prevKey = null,
                nextKey = page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Dog>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIMIT) ?: anchorPage?.nextKey?.minus(LIMIT)
        }
    }

    companion object {
        private const val LIMIT = 10
    }
}