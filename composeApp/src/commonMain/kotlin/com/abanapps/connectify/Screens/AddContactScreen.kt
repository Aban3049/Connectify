package com.abanapps.connectify.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.abanapps.connectify.appDatabase.Contacts
import com.abanapps.connectify.viewModel.ViewModelApp
import com.mohamedrejeb.calf.io.KmpFile
import com.mohamedrejeb.calf.io.getPath
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.coil.KmpFileFetcher
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import connectify.composeapp.generated.resources.Res
import connectify.composeapp.generated.resources.avatar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun AddContactScreen(viewModelApp: ViewModelApp) {

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
            scope.launch(Dispatchers.IO) {
                files.firstOrNull()?.let {
//                        byteArray = it.readByteArray(context)
                    platformSpecificFile = it
                    platformSpecificFilePath = it.getPath(context) ?: ""
                }
            }

        }
    )

    val imageLoader = ImageLoader.Builder(LocalPlatformContext.current).components {
        add(KmpFileFetcher.Factory())
    }.build()

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {

        Text("Add Contact")

        Spacer(Modifier.height(15.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

            Box(modifier = Modifier.size(120.dp)) {

                AsyncImage(
                    imageLoader = imageLoader,
                    model = platformSpecificFile,
                    placeholder = painterResource(Res.drawable.avatar),
                    modifier = Modifier.size(120.dp),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
                IconButton(onClick = {
                    pickerLauncher.launch()

                }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                    )
                }

            }


        }

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Name") }, modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = phoneNo.value,
            onValueChange = { phoneNo.value = it },
            label = { Text("Ph No") }, modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") }, modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = address.value,
            onValueChange = { address.value = it },
            label = { Text("Address") }, modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        Button(onClick = {
            scope.launch(Dispatchers.IO) {
                platformSpecificFile?.let {
                    viewModelApp.insertContact(
                        Contacts(
                            name = name.value,
                            email = email.value,
                            phoneNo = phoneNo.value,
                            imageUrl = platformSpecificFilePath
                        )
                    )
                }
            }
        }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)) {

            Text("Save Contact")
        }

    }

}

