package com.bangkit.stuntcare.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.SearchBar
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.stuntcare.ui.theme.StuntCareTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = {
            Text(text = "Cari User")
        },
        shape = MaterialTheme.shapes.large,
        content = {}
    )
}

@Preview (showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ShowTextPreview() {
    StuntCareTheme {
        Text(text = "Wahyuddin")
    }
}

@Preview (showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun ShowTextPreviewLightMode() {
    StuntCareTheme {
        Text(text = "Wahyuddin")
    }
}