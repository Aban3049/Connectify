package com.abanapps.connectify.Screens

import KottieAnimation
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.graphics.vector.ImageVector
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
import connectify.composeapp.generated.resources.Res
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import utils.KottieConstants

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun HomeScreen(navHostController: NavHostController, viewModel: ViewModelApp) {

    val contactsList by viewModel._contactsList.collectAsState(initial = emptyList())
    val updatingContact = remember { mutableStateOf(false) }

    val searchBar = remember {
        mutableStateOf(false)
    }

    val query = remember {
        mutableStateOf("")
    }

    var animation by remember { mutableStateOf("") }


    LaunchedEffect(Unit) {
            animation = Res.readBytes("drawable/contact.json").decodeToString()
    }

    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.JsonString(animation)
    )
    val playing by remember { mutableStateOf(true) }

    val animationState by animateKottieCompositionAsState(
        composition = composition,
        isPlaying = playing,
        iterations = KottieConstants.IterateForever,
        speed = 1f
    )


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navHostController.navigate(Routes.AddContactScreen)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Contact")
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Contacts", fontWeight = FontWeight.Bold, fontSize = 22.sp) },
                navigationIcon = {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = "Menu",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        searchBar.value = !searchBar.value
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(12.dp)) {
            if (contactsList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    KottieAnimation(
                        composition = composition,
                        progress = { animationState.progress },
                        modifier = Modifier.size(300.dp)
                    )
                }

            } else {
                if (searchBar.value) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(0.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(Color.White)
                    ) {
                        androidx.compose.material3.TextField(
                            query.value,
                            onValueChange = { query.value = it },
                            placeholder = {
                                Text("Search")
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent

                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                }
                val contactList = if (query.value.isEmpty()) {
                    contactsList
                } else {
                    contactsList.filter { it.name.contains(query.value, ignoreCase = true) }
                }
                ContactsList(contactList, viewModel, updatingContact)
            }
        }
    }
}


@Composable
fun EmptyContactsMessage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text("No Contacts", modifier = Modifier.align(Alignment.Center), fontSize = 22.sp)
    }
}

@Composable
fun ContactsList(
    contacts: List<Contacts>,
    viewModel: ViewModelApp,
    updatingContact: MutableState<Boolean>
) {
    LazyColumn {
        items(contacts) { contact ->
            ContactCard(contact, viewModel, updatingContact)
        }
    }
}

@Composable
fun ContactCard(
    contact: Contacts,
    viewModel: ViewModelApp,
    updatingContact: MutableState<Boolean>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .clickable { updatingContact.value = true },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(12.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        if (updatingContact.value) {
            UpdateContactBottomSheet(
                id = contact.id ?: return@Card,
                contactName = contact.name,
                contactPhoneNo = contact.phoneNo,
                contactEmail = contact.email,
                contactAddress = contact.address,
                imageUrl = contact.imageUrl,
                viewModel = viewModel,
                bottomSheetState = updatingContact.value
            )
        }

        ContactCardContent(contact, viewModel)
    }
}

@Composable
fun ContactCardContent(contact: Contacts, viewModel: ViewModelApp) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ContactAvatar(contact)
            Spacer(modifier = Modifier.width(10.dp))
            Text(contact.name, style = MaterialTheme.typography.bodyLarge, fontSize = 20.sp)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                viewModel.deleteContact(contact)
            }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
fun ContactAvatar(contact: Contacts) {

    val imageLoader = ImageLoader.Builder(LocalPlatformContext.current).components {
        add(KmpFileFetcher.Factory())
    }.build()

    Box(contentAlignment = Alignment.Center) {
        if (contact.imageUrl.isEmpty()) {
            Spacer(
                modifier = Modifier
                    .size(60.dp)
                    .background(randomColorGenerator(), shape = CircleShape)
            )
            Text(contact.name.first().toString(), fontSize = 25.sp, color = Color.White)
        } else {
            AsyncImage(
                imageLoader = imageLoader,
                model = contact.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateContactBottomSheet(
    id: Int,
    contactName: String,
    contactPhoneNo: String,
    contactEmail: String,
    contactAddress: String,
    imageUrl: String,
    viewModel: ViewModelApp,
    bottomSheetState: Boolean
) {
    val name = remember { mutableStateOf(contactName) }
    val phoneNo = remember { mutableStateOf(contactPhoneNo) }
    val email = remember { mutableStateOf(contactEmail) }
    val address = remember { mutableStateOf(contactAddress) }
    val imageUrlState = remember { mutableStateOf(imageUrl) }

    var showBottomSheet by remember { mutableStateOf(bottomSheetState) }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false }
        ) {
            UpdateContactForm(
                id = id,
                name = name,
                phoneNo = phoneNo,
                email = email,
                address = address,
                imageUrlState = imageUrlState,
                viewModel = viewModel,
                onClose = { showBottomSheet = false }
            )
        }
    }
}

@Composable
fun UpdateContactForm(
    id: Int,
    name: MutableState<String>,
    phoneNo: MutableState<String>,
    email: MutableState<String>,
    address: MutableState<String>,
    imageUrlState: MutableState<String>,
    viewModel: ViewModelApp,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Update Contact", fontSize = 19.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(15.dp))
        CustomTextField(valueState = name, label = "Name", icon = Icons.Default.Person)
        Spacer(Modifier.height(10.dp))
        CustomTextField(valueState = phoneNo, label = "Phone No", icon = Icons.Default.Phone)
        Spacer(Modifier.height(10.dp))
        CustomTextField(valueState = email, label = "Email", icon = Icons.Default.Email)
        Spacer(Modifier.height(10.dp))
        CustomTextField(valueState = address, label = "Address", icon = Icons.Default.MailOutline)
        Spacer(Modifier.height(15.dp))
        Button(onClick = {
            viewModel.updateContact(
                Contacts(
                    name = name.value,
                    email = email.value,
                    phoneNo = phoneNo.value,
                    imageUrl = imageUrlState.value,
                    address = address.value,
                    id = id
                )
            )
            onClose()
        }) {
            Text("Update Contact")
        }
    }
}

@Composable
fun CustomTextField(valueState: MutableState<String>, label: String, icon: ImageVector) {
    androidx.compose.material3.TextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(label) },
        leadingIcon = { Icon(imageVector = icon, contentDescription = null) },
        modifier = Modifier.fillMaxWidth()
    )
}

fun randomColorGenerator(): Color {
    return Color(
        red = (0..255).random(),
        green = (0..255).random(),
        blue = (0..255).random()
    )
}

//fun HomeScreen(navHostController: NavHostController, viewModel: ViewModelApp) {
//
//    val contactsList = viewModel._contactsList.collectAsState(initial = emptyList())
//
//    val imageLoader = ImageLoader.Builder(LocalPlatformContext.current).components {
//        add(KmpFileFetcher.Factory())
//    }.build()
//
//    val updatingContact = remember {
//        mutableStateOf(false)
//    }
//
//    Scaffold(floatingActionButton = {
//        FloatingActionButton(onClick = {
//
//        }) {
//
//            IconButton(onClick = {
//                navHostController.navigate(Routes.AddContactScreen)
//            }) {
//                Icon(imageVector = Icons.Default.Add, contentDescription = null)
//            }
//        }
//    },
//        topBar = {
//            CenterAlignedTopAppBar(title = {
//                Text(
//                    "Contacts",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 22.sp,
//                )
//            }, navigationIcon = {
//                IconButton(onClick = {
//
//                }) {
//                    Icon(
//                        imageVector = Icons.AutoMirrored.Filled.List,
//                        contentDescription = null,
//                        modifier = Modifier.size(28.dp)
//                    )
//                }
//            }, actions =
//            {
//                IconButton(onClick = {
//
//                }) {
//                    Icon(
//                        imageVector = Icons.Default.Search,
//                        contentDescription = null,
//                        modifier = Modifier.size(28.dp)
//                    )
//                }
//            })
//
//        }) {
//        Column(modifier = Modifier.fillMaxSize().padding(12.dp).padding(it)) {
//
//            if (contactsList.value.isEmpty()) {
//                Box(modifier = Modifier.fillMaxSize()) {
//                    Text(
//                        "No Contacts",
//                        modifier = Modifier.align(Alignment.Center),
//                        fontSize = 22.sp
//                    )
//                }
//            } else {
//                LazyColumn {
//
//                    items(contactsList.value) {
//
//                        Card(
//                            modifier = Modifier.fillMaxWidth()
//                                .clickable {
//                                    updatingContact.value = true
//                                },
//                            shape = RoundedCornerShape(12.dp),
//                            elevation = CardDefaults.cardElevation(12.dp),
//                            colors = CardDefaults.cardColors(Color.White),
//                        ) {
//
//                            if (updatingContact.value) {
//                                UpdateContactBottomSheet(
//                                    id = it.id!!,
//                                    contactName = it.name,
//                                    contactPhoneNo = it.phoneNo,
//                                    contactEmail = it.email,
//                                    contactAddress = it.address,
//                                    imageUrl = it.imageUrl,
//                                    viewModel = viewModel,
//                                    bottomSheetState = updatingContact.value
//                                )
//                            }
//
//                            Column(modifier = Modifier.fillMaxWidth()) {
//
//                                Row(
//                                    modifier = Modifier.fillMaxWidth().padding(10.dp),
//                                    verticalAlignment = Alignment.CenterVertically
//                                ) {
//
//                                    Box(contentAlignment = Alignment.Center) {
//
////                                        if (it.imageUrl.isEmpty()) {
//                                        Spacer(
//                                            modifier = Modifier.size(60.dp).background(
//                                                randomColorGenerator(),
//                                                shape = CircleShape
//                                            )
//                                        )
//
//                                        Text(
//                                            it.name.first().toString(),
//                                            fontSize = 25.sp,
//                                            color = Color.White
//                                        )
//
////                                        } else {
////                                            AsyncImage(
////                                                imageLoader = imageLoader,
////                                                model = it.imageUrl,
////                                                contentDescription = null,
////                                                modifier = Modifier.size(50.dp).clip(CircleShape),
////                                                contentScale = ContentScale.Crop
////                                            )
////                                        }
//
//                                    }
//
//                                    Spacer(modifier = Modifier.width(10.dp))
//
//                                    Text(
//                                        it.name,
//                                        style = MaterialTheme.typography.bodyLarge,
//                                        fontSize = 20.sp
//                                    )
//
//                                    Spacer(modifier = Modifier.weight(1f))
//
//                                    IconButton(onClick = {
//                                        viewModel.deleteContact(
//                                            Contacts(
//                                                it.name,
//                                                it.email,
//                                                it.phoneNo,
//                                                it.imageUrl,
//                                                it.address,
//                                                it.id
//                                            )
//                                        )
//                                    }) {
//                                        Icon(
//                                            imageVector = Icons.Default.Delete,
//                                            contentDescription = null
//                                        )
//                                    }
//
//                                }
//                            }
//
//                        }
//
//
//                    }
//
//
//                }
//            }
//
//
//        }
//    }
//
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun UpdateContactBottomSheet(
//    id: Int,
//    contactName: String,
//    contactPhoneNo: String,
//    contactEmail: String,
//    contactAddress: String,
//    imageUrl: String,
//    viewModel: ViewModelApp,
//    bottomSheetState: Boolean
//) {
//
//    val name = remember {
//        mutableStateOf(contactName)
//    }
//
//    val phoneNo = remember {
//        mutableStateOf(contactPhoneNo)
//    }
//
//    val email = remember {
//        mutableStateOf(contactEmail)
//    }
//
//    val address = remember {
//        mutableStateOf(contactAddress)
//    }
//
//    val imageUrlState = remember {
//        mutableStateOf(imageUrl)
//    }
//
//    val sheetState = rememberModalBottomSheetState()
//    var showBottomSheet by remember {
//        mutableStateOf(bottomSheetState)
//    }
//
//    if (showBottomSheet) {
//
//        ModalBottomSheet(sheetState = sheetState, onDismissRequest = {
//            showBottomSheet = false
//        }) {
//
//            Column(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//
//                Text("Update Contact", fontSize = 19.sp, color = Color.Black)
//
//                Spacer(modifier = Modifier.height(15.dp))
//
//                TextField(
//                    value = name.value,
//                    onValueChange = { name.value = it },
//                    label = "Name",
//                    placeHolder = "Enter Name",
//                    Icon = Icons.Default.Person
//                )
//
//                Spacer(Modifier.height(10.dp))
//
//                TextField(
//                    value = phoneNo.value,
//                    onValueChange = { phoneNo.value = it },
//                    label = "Ph No",
//                    placeHolder = "Enter Phone No",
//                    Icons.Default.Phone
//                )
//
//                Spacer(Modifier.height(10.dp))
//
//                TextField(
//                    value = email.value,
//                    onValueChange = { email.value = it },
//                    label = "Email",
//                    placeHolder = "Enter Email",
//                    Icon = Icons.Default.Email
//                )
//
//                Spacer(Modifier.height(10.dp))
//
//                TextField(
//                    value = address.value,
//                    onValueChange = { address.value = it },
//                    label = "Address",
//                    placeHolder = "Enter Address",
//                    Icon = Icons.Default.MailOutline
//                )
//
//                Button(onClick = {
//                    viewModel.updateContact(
//                        Contacts(
//                            name = name.value,
//                            email = email.value,
//                            phoneNo = phoneNo.value,
//                            imageUrl = imageUrlState.value,
//                            address = address.value,
//                            id = id
//                        )
//                    )
//                }) {
//                    Text("Update Contact")
//                }
//
//
//            }
//
//        }
//
//
//    }
//
//
//}
//
//
//fun randomColorGenerator(): Color {
//    return Color(
//        red = (0..255).random(),
//        green = (0..255).random(),
//        blue = (0..255).random()
//    )
//}