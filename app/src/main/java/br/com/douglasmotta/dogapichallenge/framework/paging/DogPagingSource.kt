package br.com.douglasmotta.dogapichallenge.framework.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.douglasmotta.dogapichallenge.data.DogRemoteDataSource
import br.com.douglasmotta.dogapichallenge.domain.model.Dog
import br.com.douglasmotta.dogapichallenge.domain.model.QueryData
import br.com.douglasmotta.dogapichallenge.framework.network.response.toDogDomain
import java.lang.Exception

class DogPagingSource(
    private val remoteDataSource: DogRemoteDataSource,
    private val queryData: QueryData
): PagingSource<Int, Dog>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Dog> {
        return try {
            val page = params.key ?: 0

            val queries = hashMapOf(
                "page" to page.toString(),
                "limit" to LIMIT.toString()
            )

            val query = queryData.query
            if (query.isNotEmpty()) {
                queries["nameStartsWith"] = query
            }

            queries["order"] = queryData.sort.name.lowercase()

            val response = remoteDataSource.fetchDogs(queries)
            if (response.isSuccessful) {
                val headers = response.headers()
                val totalResults = headers["pagination-count"]?.toInt() ?: 0
                val currentPage = headers["pagination-page"]?.toInt() ?: 0

                response.body()?.let { dogResponseList ->
                    LoadResult.Page(
                        data = dogResponseList.map { it.toDogDomain() },
                        prevKey = null,
                        nextKey = if (currentPage * LIMIT < totalResults) {
                            page + 1
                        } else null
                    )
                } ?: LoadResult.Error(Throwable("Empty data"))

            } else {
                LoadResult.Error(Throwable("Generic error"))
            }
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