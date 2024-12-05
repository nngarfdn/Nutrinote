package com.sv.calorieintakeapps.feature_macronutrientintake.presentation.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.sv.calorieintakeapps.feature_macronutrientintake.domain.usecase.MacronutrientIntakeUseCase
import com.sv.calorieintakeapps.feature_macronutrientintake.presentation.model.FilterMakrotrienUiModel
import com.sv.calorieintakeapps.feature_macronutrientintake.presentation.model.listFilterMakrotrien
import com.sv.calorieintakeapps.library_database.domain.model.ItemMakronutrien
import com.sv.calorieintakeapps.library_database.domain.model.MacronutrientIntakePercentage
import com.sv.calorieintakeapps.library_database.domain.model.ReportDomainModel
import com.sv.calorieintakeapps.library_database.vo.Resource

class MacronutrientIntakeResultViewModel(useCase: MacronutrientIntakeUseCase) : ViewModel() {
    
    private val date = MutableLiveData<String>()
    private val _listMakronutrien: MutableLiveData<List<ItemMakronutrien>?> = MutableLiveData(emptyList())
    private val _filteredMakronutrien: MutableLiveData<List<ItemMakronutrien>?> = MutableLiveData(emptyList())
    private val _selectedMakronutrienName: MutableLiveData<List<FilterMakrotrienUiModel>?> = MutableLiveData(listFilterMakrotrien)

    val listMakronutrien: LiveData<List<ItemMakronutrien>?> = _listMakronutrien
    val filteredMakronutrien: LiveData<List<ItemMakronutrien>?> = _filteredMakronutrien
    val selectedMakronutrienName: MutableLiveData<List<FilterMakrotrienUiModel>?> = _selectedMakronutrienName

    init {
        _selectedMakronutrienName.value = listFilterMakrotrien
    }

    fun setListMakronutrien(list: List<ItemMakronutrien>?) {
        _listMakronutrien.value = list
    }

    fun setFilteredMakronutrien(list: List<ItemMakronutrien>?) {
        _filteredMakronutrien.value = list
    }

    fun setSelectedMakronutrienName(list: List<FilterMakrotrienUiModel>?) {
        _selectedMakronutrienName.value = list
    }


    fun setInput(
        date: String,
    ) {
        this.date.value = date
    }
    
    val reports: LiveData<Resource<List<ReportDomainModel>>> =
        date.switchMap { date ->
            useCase.getReportByDate(date).asLiveData()
        }
    
    val macronutrientIntakePercentage: LiveData<Resource<MacronutrientIntakePercentage>> =
        reports.switchMap { result ->
            if (result is Resource.Success) {
                result.data?.let { reports ->
                    useCase.getMacronutrientIntakePercentage(
                        reports = reports,
                    ).asLiveData()
                }
            } else {
                MutableLiveData<Resource<MacronutrientIntakePercentage>>().apply {
                    value = Resource.Loading()
                }
            }
        }
    
}