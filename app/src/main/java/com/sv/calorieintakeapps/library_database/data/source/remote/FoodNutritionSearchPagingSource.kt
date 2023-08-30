package com.sv.calorieintakeapps.library_database.data.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.NilaigiziComApiService
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.response.FoodNutritionSearchResponse
import timber.log.Timber

class FoodNutritionSearchPagingSource(
    private val nilaigiziComApiService: NilaigiziComApiService,
) : PagingSource<Int, FoodNutritionSearchResponse.DataItem>() {
    
    private var query = ""
    
    fun setParams(query: String) {
        this.query = query
    }
    
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FoodNutritionSearchResponse.DataItem> {
        try {
            val nextPageNumber = params.key ?: 1
            val response = nilaigiziComApiService.getFoodNutritionSearch(
                query = query,
                page = nextPageNumber,
                pageSize = params.loadSize,
            )
            @Suppress("UNCHECKED_CAST")
            return LoadResult.Page(
                data = response.data?.data as List<FoodNutritionSearchResponse.DataItem>,
                prevKey = null,
                nextKey = if (response.data.nextPageUrl != null) nextPageNumber + 1 else null,
            )
        } catch (e: Exception) {
            Timber.tag(this::class.java.simpleName).e(e)
            return LoadResult.Error(e)
        }
    }
    
    override fun getRefreshKey(state: PagingState<Int, FoodNutritionSearchResponse.DataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
    
}