package com.abanapps.connectify.Utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun TextField(value: String, onValueChange: (String) -> Unit, label: String, placeHolder: String,Icon:ImageVector) {


    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Gray,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = Color.DarkGray,
        ),
        placeholder = {
            Text(text = placeHolder)
        },
        label = {
            Text(label)
        },
        leadingIcon = {
            Image(imageVector = Icon, contentDescription = null, colorFilter = ColorFilter.tint(Color.Black))
        }
    )

}