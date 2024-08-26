package com.abanapps.connectify

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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.abanapps.connectify.Navigation.NavGraph
import com.abanapps.connectify.appDatabase.Contacts
import com.abanapps.connectify.appDatabase.ContactsDatabase
import com.abanapps.connectify.repo.Repo
import com.abanapps.connectify.viewModel.ViewModelApp
import com.mohamedrejeb.calf.io.KmpFile
import com.mohamedrejeb.calf.io.getPath
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.coil.KmpFileFetcher
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(db: ContactsDatabase) {

    val dao = db.getDao()
    val repo = Repo(dao)
    val viewModel = ViewModelApp(repo)
    MaterialTheme {
        NavGraph(viewModel)
    }

}


@Composable
fun ImagePicker() {
    val scope = rememberCoroutineScope()
    val context = com.mohamedrejeb.calf.core.LocalPlatformContext.current

    var byteArray by remember {
        mutableStateOf(ByteArray(0))
    }

    var platformSpecificFilePath by remember {
        mutableStateOf("")
    }

    var platformSpecificFile by remember {
        mutableStateOf<KmpFile?>(null)
    }

    val pickerLauncher = rememberFilePickerLauncher(
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Multiple,
        onResult = { files ->
            scope.launch {
                files.firstOrNull()?.let {
//                        byteArray = it.readByteArray(context)
                    platformSpecificFile = it
                    platformSpecificFilePath = it.getPath(context) ?: ""
                }
            }

        }
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            pickerLauncher.launch()
        }, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text("Open File Picker")
        }

        val imageLoader = ImageLoader.Builder(LocalPlatformContext.current).components {
            add(KmpFileFetcher.Factory())
        }.build()

        AsyncImage(
            imageLoader = imageLoader,
            model = platformSpecificFile,
            modifier = Modifier.size(300.dp),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Text("File Path:  $platformSpecificFilePath", style = MaterialTheme.typography.bodySmall)


    }
}
