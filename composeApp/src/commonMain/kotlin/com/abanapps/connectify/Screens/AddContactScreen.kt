package com.abanapps.connectify.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.abanapps.connectify.Utils.TextField
import com.abanapps.connectify.appDatabase.Contacts
import com.abanapps.connectify.viewModel.ViewModelApp
import com.mohamedrejeb.calf.io.KmpFile
import com.mohamedrejeb.calf.io.getPath
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.coil.KmpFileFetcher
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactScreen(navHostController: NavHostController, viewModelApp: ViewModelApp) {

    val phoneNo = remember {
        mutableStateOf("")
    }

    val name = remember {
        mutableStateOf("")
    }

    val email = remember {
        mutableStateOf("")
    }

    val address = remember {
        mutableStateOf("")
    }

    val imageUrl = remember {
        mutableStateOf("")
    }

    val isLoading = viewModelApp.isLoading.collectAsState()

    val scope = rememberCoroutineScope()
    val context = com.mohamedrejeb.calf.core.LocalPlatformContext.current

    var platformSpecificFilePath by remember {
        mutableStateOf("")
    }

    var platformSpecificFile by remember {
        mutableStateOf<KmpFile?>(null)
    }

    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single,
        onResult = { files ->
            scope.launch {
                files.firstOrNull()?.let {
                    platformSpecificFile = it
                    platformSpecificFilePath = it.getPath(context) ?: ""
                }
            }

        }
    )

    val imageLoader = ImageLoader.Builder(LocalPlatformContext.current).components {
        add(KmpFileFetcher.Factory())
    }.build()

    Scaffold(topBar = {

        CenterAlignedTopAppBar(title = {
            Text(
                "Add Contacts",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
            )
        }, navigationIcon = {
            IconButton(onClick = {
                navHostController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            }
        })
    })

    { innerPadding ->

        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(start = 14.dp, end = 14.dp)
        ) {


            Spacer(Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

                Box(modifier = Modifier.size(120.dp), contentAlignment = Alignment.Center) {

                    Spacer(
                        modifier = Modifier.size(120.dp).clip(CircleShape).background(Color.Gray)
                    )
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = Color.White
                    )

                    AsyncImage(
                        imageLoader = imageLoader,
                        model = platformSpecificFile,
                        modifier = Modifier.size(120.dp).clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )

                    FloatingActionButton(
                        onClick = { pickerLauncher.launch() },
                        modifier = Modifier.offset {
                            IntOffset(x = 40, y = 35)
                        }.clip(CircleShape), containerColor = Color.Black) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = Color.White
                        )
                    }

                }


            }

            Spacer(Modifier.height(10.dp))

            TextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = "Name",
                placeHolder = "Enter Name",
                Icon = Icons.Default.Person
            )

            Spacer(Modifier.height(10.dp))

            TextField(
                value = phoneNo.value,
                onValueChange = { phoneNo.value = it },
                label = "Ph No",
                placeHolder = "Enter Phone No",
                Icons.Default.Phone
            )

            Spacer(Modifier.height(10.dp))

            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = "Email",
                placeHolder = "Enter Email",
                Icon = Icons.Default.Email
            )

            Spacer(Modifier.height(10.dp))

            TextField(
                value = address.value,
                onValueChange = { address.value = it },
                label = "Address",
                placeHolder = "Enter Address",
                Icon = Icons.Default.LocationOn
            )

            Spacer(Modifier.height(10.dp))

            Button(onClick = {

                platformSpecificFile?.let {
                    viewModelApp.insertContact(
                        Contacts(
                            name = name.value,
                            email = email.value,
                            phoneNo = phoneNo.value,
                            address = address.value,
                            imageUrl = platformSpecificFilePath
                        )
                    )
                }

            }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp)) {
                if (isLoading.value) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text("Save Contact")
                }

            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(platformSpecificFilePath)


        }

    }


}

