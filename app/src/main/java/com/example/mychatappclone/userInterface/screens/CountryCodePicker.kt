package com.example.mychatappclone.userInterface.screens

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryCodePicker(
    selectedCountry: String,
    onCountrySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val countries = listOf(
        "United States" to "+1",
        "United Kingdom" to "+44",
        "India" to "+91",
        "Egypt" to "+20",
        "Germany" to "+49"
    )

    // Use ExposedDropdownMenuBox to manage the menu dropdown and its anchor
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        // TextField for the selected country code
        OutlinedTextField(
            value = selectedCountry,
            onValueChange = { /* Read-only */ },
            label = { Text("Country Code") },
            modifier = Modifier
                .menuAnchor() // Make sure the dropdown is properly anchored
                .width(120.dp)
                .height(57.dp),
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        )

        // Dropdown menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            countries.forEach { (country, code) ->
                DropdownMenuItem(
                    text = { Text(text = "$country ($code)") },
                    onClick = {
                        onCountrySelected(code)
                        expanded = false
                    }
                )
            }
        }
    }
}