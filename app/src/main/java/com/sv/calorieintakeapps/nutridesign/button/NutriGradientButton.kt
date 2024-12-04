package com.sv.calorieintakeapps.nutridesign.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sv.calorieintakeapps.R
import kotlinx.coroutines.delay

@Composable
fun NutriSelectorButton(
    text: String ,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val disabledColor = Color(0xFFD3D3D3)
    val defaultGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFA91005),
            Color(0xFFD3650F)
        )
    )
    val pressedGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFD02215),
            Color(0xFFEB781D)
        )
    )

    // State to manage current background brush
    var currentBrush by remember { mutableStateOf(defaultGradient) }

    // Handle press interaction
    val interactionSource = remember { MutableInteractionSource() }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    currentBrush = pressedGradient
                }
                is PressInteraction.Release, is PressInteraction.Cancel -> {
                    // Delay to allow the pressed state to be visible
                    delay(100)
                    currentBrush = defaultGradient
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                brush = if (enabled) currentBrush else Brush.verticalGradient(
                    colors = listOf(disabledColor, disabledColor)
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = if (enabled) Color.White else Color.Gray,
            fontFamily = FontFamily(
                Font(R.font.poppins_semibold, FontWeight.SemiBold)
            ),
            fontWeight = FontWeight.SemiBold
        )
    }
}