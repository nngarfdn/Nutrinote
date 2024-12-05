package com.sv.calorieintakeapps.feature_macronutrientintake.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sv.calorieintakeapps.feature_macronutrientintake.presentation.model.FilterMakrotrienUiModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipSheetComponent(
    modifier: Modifier = Modifier,
    items: List<FilterMakrotrienUiModel>,
    selectedItems: List<FilterMakrotrienUiModel> = emptyList(),
    onSelectionChanged: (List<FilterMakrotrienUiModel>) -> Unit,
) {
    var currentSelections by remember { mutableStateOf(selectedItems) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .padding(16.dp)
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items.forEach { item ->
                ChipItem(
                    text = item.title,
                    isSelected = currentSelections.contains(item),
                    onClick = {
                        val updatedSelections = if (currentSelections.contains(item)) {
                            currentSelections - item
                        } else {
                            currentSelections + item
                        }
                        currentSelections = updatedSelections
                        onSelectionChanged(updatedSelections)
                    }
                )
            }
        }
    }
}

@Composable
fun ChipItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .background(
                color = if (isSelected) Color(0xFFB82D00) else MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFFB82D00) else MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
