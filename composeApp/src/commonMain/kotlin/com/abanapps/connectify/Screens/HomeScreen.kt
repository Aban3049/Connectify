package com.abanapps.connectify.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.abanapps.connectify.Navigation.Routes
import com.abanapps.connectify.appDatabase.Contacts
import com.abanapps.connectify.viewModel.ViewModelApp
import com.mohamedrejeb.calf.picker.coil.KmpFileFetcher

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHostController: NavHostController, viewModel: ViewModelApp) {

    val contactsList = viewModel._contactsList.collectAsState(initial = emptyList())

    val imageLoader = ImageLoader.Builder(LocalPlatformContext.current).components {
        add(KmpFileFetcher.Factory())
    }.build()

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {

        }) {

            IconButton(onClick = {
                navHostController.navigate(Routes.AddContactScreen)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    },
        topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                "Contacts",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
            )
        }, navigationIcon = {
            IconButton(onClick = {

            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            }
        }, actions =
        {
            IconButton(onClick = {

            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            }
        })

    }) {
        Column(modifier = Modifier.fillMaxSize().padding(12.dp).padding(it)) {

            if (contactsList.value.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        "No Contacts",
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 22.sp
                    )
                }
            } else {
                LazyColumn {

                    items(contactsList.value) {

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(12.dp),
                            colors = CardDefaults.cardColors(Color.White),
                        ) {

                            Column(modifier = Modifier.fillMaxWidth()) {

                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Box(contentAlignment = Alignment.Center) {

//                                        if (it.imageUrl.isEmpty()) {
                                            Spacer(
                                                modifier = Modifier.size(60.dp).background(
                                                    randomColorGenerator(),
                                                    shape = CircleShape
                                                )
                                            )

                                            Text(
                                                it.name.first().toString(),
                                                fontSize = 25.sp,
                                                color = Color.White
                                            )

//                                        } else {
//                                            AsyncImage(
//                                                imageLoader = imageLoader,
//                                                model = it.imageUrl,
//                                                contentDescription = null,
//                                                modifier = Modifier.size(50.dp).clip(CircleShape),
//                                                contentScale = ContentScale.Crop
//                                            )
//                                        }

                                    }

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Text(
                                        it.name,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontSize = 20.sp
                                    )

                                    Spacer(modifier = Modifier.weight(1f))

                                    IconButton(onClick = {
                                        viewModel.deleteContact(
                                            Contacts(
                                                it.name,
                                                it.email,
                                                it.phoneNo,
                                                it.imageUrl,
                                                it.id
                                            )
                                        )
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = null
                                        )
                                    }

                                }
                            }

                        }


                    }


                }
            }


        }
    }

}


fun randomColorGenerator(): Color {
    return Color(
        red = (0..255).random(),
        green = (0..255).random(),
        blue = (0..255).random()
    )
}