package com.bangkit.stuntcare.ui.view.profile.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bangkit.stuntcare.ui.component.SectionText
import com.bangkit.stuntcare.ui.navigation.navigator.HomePageScreenNavigator
import com.bangkit.stuntcare.ui.theme.StuntCareTheme

@Composable
fun ProfileScreen(navigator: HomePageScreenNavigator) {
    ProfileContent(navigator = navigator)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    navigator: HomePageScreenNavigator,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        TopAppBar(
            title = {
                Text(text = "Profile", fontWeight = FontWeight.Medium, fontSize = 20.sp)
            },
            navigationIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = modifier
                        .size(24.dp)
                        .clickable { navigator.backNavigation() }
                )
            }
        )
        ProfileCard()
        InformationContent()
        PreferencesContent()
    }
}

@Composable
fun ProfileCard(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = "https://static.everypixel.com/ep-pixabay/0329/8099/0858/84037/3298099085884037069-head.png",  // TODO
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(100.dp)
                .padding(16.dp)
                .clip(CircleShape)
        )
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
        ) {
            Text(text = "Nama Pengguna", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Text(text = "Ini Email", fontWeight = FontWeight.Light, fontSize = 14.sp)
            Text(text = "Ini Nomor Telfon", fontWeight = FontWeight.Light, fontSize = 14.sp)
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Bottom,
            modifier = modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { /*TODO*/ }, modifier = modifier) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileCardSPreview() {
    StuntCareTheme {
        ProfileCard()
    }
}

@Composable
fun InformationContent(
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(4.dp)) {
        SectionText(title = "Other Information", modifier = modifier.padding(12.dp))
        Divider(color = LocalContentColor.current, modifier = modifier
            .fillMaxWidth()
            .width(1.dp))
        OtherInformationContent(title = "FAQ", icon = Icons.Default.Email, modifier = modifier)
        OtherInformationContent(title = "About", icon = Icons.Default.Info, modifier = modifier)
        OtherInformationContent(title = "Rate Us", icon = Icons.Default.Star, modifier = modifier)
    }
}
@Composable
fun PreferencesContent(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(4.dp)
    ) {
        SectionText(title = "Preferences", modifier = modifier.padding(12.dp))
        Divider(color = LocalContentColor.current, modifier = modifier
            .fillMaxWidth()
            .width(1.dp))
        OtherInformationContent(title = "Keluar", icon = Icons.Default.ExitToApp)
    }
}

@Composable
fun OtherInformationContent(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = modifier.padding(8.dp)
        )
        Text(text = title, fontWeight = FontWeight.Normal, fontSize = 12.sp)
        Column (
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxWidth()
        ){
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)
        }
    }
}