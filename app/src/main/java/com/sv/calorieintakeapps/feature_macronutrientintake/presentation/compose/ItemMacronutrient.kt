package com.sv.calorieintakeapps.feature_macronutrientintake.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sv.calorieintakeapps.nutridesign.font.PoppinsFontFamily
import com.sv.calorieintakeapps.nutridesign.text.NutriText

@Composable
fun ItemMacronutrient(
    modifier: Modifier = Modifier,
    imageRes: Int,
    title: String,
    percentage: String,
    value: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                NutriText(
                    text = title,
                    fontSize = 13.sp,
                    color = Color.Black
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                NutriText(
                    text = percentage,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = PoppinsFontFamily
                )
                NutriText(
                    text = value,
                    fontSize = 13.sp,
                    color = Color.Black
                )

            }
        }
    }
}