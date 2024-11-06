package com.sv.calorieintakeapps.feature_reporting.presentation

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.NilaigiziComApiService
import com.sv.calorieintakeapps.library_database.data.source.remote.nilaigizicom.response.FoodNutritionSearchResponse
import com.sv.calorieintakeapps.library_database.data.source.remote.urt.UrtFood
import timber.log.Timber

class UrtFoodSearchPagingSource(
    private val nilaigiziComApiService: NilaigiziComApiService,
) : PagingSource<Int, UrtFood>() {
    
    private var query = ""
    private var token = ""
    
    fun setParams(query: String, token: String) {
        this.query = query
        this.token = token
    }
    
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UrtFood> {
        try {
            val nextPageNumber = params.key ?: 1
            val response = nilaigiziComApiService.getFoodNutritionSearch(
                query = query,
                page = nextPageNumber,
                pageSize = params.loadSize,
                token = token,
            )
            @Suppress("UNCHECKED_CAST")
            return LoadResult.Page(
                data = response.data?.data as List<UrtFood>,
                prevKey = null,
                nextKey = if (response.data.nextPageUrl != null) nextPageNumber + 1 else null,
            )
        } catch (e: Exception) {
            Timber.tag(this::class.java.simpleName).e(e)
            return LoadResult.Error(e)
        }
    }
    
    override fun getRefreshKey(state: PagingState<Int, UrtFood>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
    
}