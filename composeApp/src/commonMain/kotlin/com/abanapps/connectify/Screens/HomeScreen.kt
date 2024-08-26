package com.abanapps.connectify.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import connectify.composeapp.generated.resources.Res
import connectify.composeapp.generated.resources.avatar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHostController: NavHostController,viewModel: ViewModelApp) {

    val scope = rememberCoroutineScope()

    val contactsList = viewModel._contactsList.collectAsState(initial = emptyList())

    val imageLoader = ImageLoader.Builder(LocalPlatformContext.current).components {
        add(KmpFileFetcher.Factory())
    }.build()

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {

        }) {
            Text("+")
        }
    }, topBar = {
        TopAppBar(title = {
            Text("All Contacts")
        }, actions = {
            IconButton(onClick = {
                navHostController.navigate(Routes.AddContactScreen)
            }) {
                Icon(imageVector = Icons.Default.Phone, contentDescription = null)
            }
        })
    }) {

        Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {

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

                            Column(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                                Row(modifier = Modifier.fillMaxWidth()) {

                                    AsyncImage(
                                        imageLoader = imageLoader,
                                        model = it.imageUrl,
                                        placeholder = painterResource(Res.drawable.avatar),
                                        contentDescription = null,
                                        modifier = Modifier.size(55.dp)
                                    )

                                    Text(it.name)

                                    IconButton(onClick = {

                                        scope.launch(Dispatchers.IO) {
                                            viewModel.deleteContact(
                                                Contacts(
                                                    it.name,
                                                    it.email,
                                                    it.phoneNo,
                                                    it.imageUrl,
                                                    it.id
                                                )
                                            )
                                        }

                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = null
                                        )
                                    }

                                }
                                Text(it.phoneNo)
                            }


                        }

                    }

                }
            }


        }
    }

}